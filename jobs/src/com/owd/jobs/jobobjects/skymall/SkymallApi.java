package com.owd.jobs.jobobjects.skymall;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.order.*;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.LogableException;
import com.owd.jobs.jobobjects.batchimporters.SkymallOrdersData;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import ipworks.DirEntry;
import ipworks.Ftp;
import org.hibernate.engine.spi.SessionImplementor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 10/7/14.
 */
public class SkymallApi {
private final static Logger log =  LogManager.getLogger();

    String ftpHost = "";
    String ftpUser = "";
    String ftpPass = "";
    int clientId = 112;

    private String upsAcct = null;

    public SkymallApi(String host, String user, String pass) {
        ftpHost = host ;
        ftpUser = user ;
        ftpPass = pass ;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPass() {
        return ftpPass;
    }

    public void setFtpPass(String ftpPass) {
        this.ftpPass = ftpPass;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getUpsAcct() {
        return upsAcct;
    }

    public void setUpsAcct(String upsAcct) {
        this.upsAcct = upsAcct;
    }

    public static void main(String[] args) throws Exception {
        SkymallApi api = new SkymallApi("skypartners.skymall.com","bso","X#-39T+K");
        api.setClientId(524);
        api.setUpsAcct("R2224X");
      //  api.checkForOrdersFromFtp();
        api.sendShippedReport(new OrderStatus(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),"7928734"));

    }


    public void checkForOrdersFromFtp() {

        int newOrders = 0;
        Ftp ftp = new Ftp();

        //   System.setProperty("ftp.proxyHost", "172.16.1.1");
        //   System.setProperty("ftp.proxyPort", "2121");

        try {
            ftp.setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());
            ftp.setRemoteHost(getFtpHost());
            ftp.setUser(getFtpUser());
            ftp.setPassword(getFtpPass());
            ftp.setRemotePath("Out");
            ftp.setTransferMode(Ftp.tmBinary);
          //  ftp.setPassive(false);
            //login
            ftp.logon();
            // get a list of all .txt files
            // ftp.setRemoteFile("*.txt");
            ftp.listDirectoryLong();

            DirEntry[] h = new DirEntry[ftp.getDirList().size()];
            ftp.getDirList().toArray(h);

            log.debug("found "+h.length+" files!");
            //step through each file
            for (DirEntry e : h) {
                // make sure we are dealing with a file
                log.debug("" + e.getFileName());
                StringBuffer sbx = new StringBuffer();

                if (!e.getIsDir() && e.getFileName().startsWith(ftpUser.toUpperCase())) {

                    ftp.setRemoteFile(e.getFileName());
                    long originalSize = ftp.getFileSize();
                    Thread.sleep(1000L);
                    ftp.setRemoteFile(e.getFileName());
                    long currentSize = ftp.getFileSize();
                    log.debug("current=" + currentSize);
                    int loops = 0;
                    while (currentSize != originalSize && loops < 200) {
                        Thread.sleep(1000L);
                        originalSize = currentSize;
                        currentSize = ftp.getFileSize();
                        loops++;
                    }
                    if (loops > 199) {
                        throw new Exception("ftp timeout waiting for data");
                    }

                    ByteArrayOutputStream file = new ByteArrayOutputStream();
                    ftp.setDownloadStream(file);
                    //downlaod file
                    ftp.download();
                    DateFormat df = new SimpleDateFormat("MMM dd, yyyy h:mm:ss a");

                    Date fileDate = df.parse(ftp.getFileTime());

                    log.debug("time:" + df.parse(ftp.getFileTime()));

                    log.debug(file.toString());
                    //process file
                    DelimitedReader orders = new CSVReader(new BufferedReader(new StringReader(file.toString())), false);

                    log.debug(orders.getRowCount());

                    List results = processDataFile(orders);

                    ftp.renameFile("./processed/" + e.getFileName());


                    // log.debug(sb.toString());
                    log.debug("Sending ack file");
                    String newData = getAckFile(orders);
                    log.debug(newData);

                    ftp.setRemotePath("/In/");
                    ftp.setRemoteFile(ftpUser.toUpperCase()+Calendar.getInstance().getTimeInMillis()+".csv");
                    ftp.setUploadStream(new ByteArrayInputStream(newData.getBytes()));
                    ftp.upload();

                    Iterator it = results.iterator();

                    while (it.hasNext()) {
                        sbx.append("\r\n" + it.next());
                    }
                    Vector emailsx = new Vector();
                    emailsx.add("casetracker@owd.com");

                    if (results.size() > 0 && sbx.toString().indexOf("Communications link failure") < 0) {
                        Mailer.postMailMessage("OWD-Skymall Import Status Notification", "The following orders may need attention:\r\n\r\n" + sbx.toString(), emailsx, "do-not-reply@owd.com");
                        //alerts@dogeared.com
                    } else {
                        Date now = Calendar.getInstance().getTime();

                        if ((now.getTime() - fileDate.getTime()) > (1000 * 60 * 30)) {
                            // throw new Exception("Still testing!!!");

//                          DONE: Delete file
                           // ftp.renameFile(e.getFileName());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new LogableException(ex, ex.getMessage(),
                    "TS:"+Calendar.getInstance().getTimeInMillis(),
                    getClientId()+"",
                    this.getClass().getName(),
                    LogableException.errorTypes.ORDER_IMPORT);        } finally {
            try {
                ftp.logoff();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public void sendShippedReport(OrderStatus order) throws Exception {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        StringBuffer sb = new StringBuffer();
        sb.append(ftpUser.toUpperCase()+"\r\n");

        for(LineItem line:(Vector<LineItem>)order.items)
        {
            sb.append("SM,SN,"+order.orderReference.replaceAll("SKY","")+","+
                    line.client_ref_num+","+
                    (line.client_part_no.equals("EYEPUTT")?"EYEPUTTERF14":line.client_part_no)+",,"+
                    line.quantity_actual+","+sdf.format(Calendar.getInstance().getTime())+","+(order.shipping.carr_service.startsWith("USPS")?"USPS":"UPS")+","+((com.owd.core.business.order.Package)order.packages.get(0)).tracking_no+"\r\n");
        }

        log.debug(sb.toString());
        Ftp ftp = new Ftp();
        try{

            ftp.setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());
            ftp.setRemoteHost(getFtpHost());
            ftp.setUser(getFtpUser());
            ftp.setPassword(getFtpPass());
            ftp.setTransferMode(Ftp.tmBinary);
            //  ftp.setPassive(false);
            //login
            ftp.logon();

        ftp.setRemotePath("/In/");
        ftp.setRemoteFile(ftpUser.toUpperCase()+Calendar.getInstance().getTimeInMillis()+".csv");
        ftp.setUploadStream(new ByteArrayInputStream(sb.toString().getBytes()));
        ftp.upload();

        }catch(Exception ex)
        {
            throw ex;
        }
        finally
        {
            ftp.logoff();
        }

    }

    private String getAckFile(DelimitedReader data) throws Exception {

         StringBuffer sb = new StringBuffer();
         sb.append(ftpUser.toUpperCase()+"\r\n");

          for(int row=0;row<data.getRowCount();row++)
          {
              log.debug("loading row "+row);
             sb.append("SM,AN,"+data.getStrValue(SkymallOrdersData.korder_id, row,"")+","+
                     data.getStrValue(SkymallOrdersData.kline_item_numer, row,"")+","+
                     data.getStrValue(SkymallOrdersData.ksku, row,"")+",,"+
                     data.getStrValue(SkymallOrdersData.kquantity, row,"")+"\r\n");
          }

return sb.toString();
       


    }
    private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();

        SkymallOrdersData dataHandler = new SkymallOrdersData();
        dataHandler.init(data);

        //log.debug("toprocess " + dataHandler.getOrderCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {

                List resultL = processOrder(dataHandler, getClientId()+"", OrderXMLDoc.kRejectBackOrder, orderIndex);
                //log.debug(resultL);

                if (resultL.toString().indexOf(",") > 0 || resultL.toString().toUpperCase().indexOf("BACKORDER") >= 0 ||
                        resultL.toString().toUpperCase().indexOf("ON HOLD") >= 0) {
                    //log.debug("adding to result list");
                    resultsList.add(resultL);
                }
                //log.debug("processed " + (orderIndex + 1));
                //record success
            } catch (Exception ex) {
                //record error
                //ex.printStackTrace();
                //log.debug("Uncaught error at row " + orderIndex + ":" + ex.getMessage());
            }
        }
        return resultsList;
    }

    private List processOrder(SkymallOrdersData dataHandler, String clientID, String backorderRule, int orderIndex) {
        //returns list of two elements - client Order ID and response
        List response = new ArrayList();
        //add new
        response.add(dataHandler.getOrderReference(orderIndex));

        try {
            Order order = new Order(clientID);
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            //order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.backorder_rule = backorderRule;

            order.is_future_ship=1;
            dataHandler.loadOrder(order, orderIndex);
            //log.debug("after load order");

            order.recalculateBalance();
            log.debug("IS PAID:" + order.is_paid);
             order.is_paid=1;

            if (order.is_paid == 1) {
                //paid for - push it through!
                order.bill_cc_type = "CK";
                order.check_num = "999999";
                order.paid_amount = order.total_order_cost;
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;
            }
            order.order_type = SkymallOrdersData.Order_Type;
            //log.debug("Shipping 3rd Party");
            try {
                String sm = order.getShippingInfo().carr_service;
                if(sm.equals("EXP"))
                {

                    order.getShippingInfo().setShipOptions("UPS 2nd Day Air","Third Party Billing",getUpsAcct());                }
                else if (sm.equals("NX") || sm.equals("NXD"))
                {

                    order.getShippingInfo().setShipOptions("UPS Next Day Air Saver","Third Party Billing",getUpsAcct());                }
                else
                {
                    order.getShippingInfo().setShipOptions("UPS Ground","Third Party Billing",getUpsAcct());
                }

                order.shippercontact = "James Specht";
                order.shippercompany = "Skymall";
                order.shipperaddress_one = "1520 E. Pima St.";
                order.shipperaddress_two = "";
                order.shippercity = "Phoenix";
                order.shipperstate = "AZ";
                order.shipperzip = "85034";
                order.shipperphone = "6025283215";

            } catch (Exception exs) {
                order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
                order.is_future_ship = 1;
                CasetrackerAPI.createCasetrackerCase("Client ID (" + order.clientID + ") order " + order.order_refnum + " received on hold", "Unable to determine ship method; check and verify import setup", Integer.parseInt(order.clientID));
                order.hold_reason = "Unable to determine ship method; that method is unknown to the importer. Assign to IT Work Orders if needed.";
            }


            String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, true);
            log.debug("reference=" + reference);

            if (reference == null) {
                if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1
                        &&
                        (order.last_payment_error + " " + order.last_error).indexOf("no physical items") < 1) {
                    log.debug("reporting error on import");
                    throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                } else {
                    //duplicate
                }
            } else {
                log.debug("got valid order import");

                if (order.is_future_ship == 1) {
                    response.add("[HELD ORDER] " + order.hold_reason);
                }
                if (order.is_backorder == 1) {
                    StringBuffer sb = new StringBuffer();
                    response.add("[BACKORDER] " + sb);
                }
            }
        } catch (Exception
                ex) {
            ex.printStackTrace();
            response.add("[NOT IMPORTED] " + ex.getMessage());
        } finally {

        }
        return response;
    }

}
