package com.owd.jobs;

import com.owd.jobs.jobobjects.billing.FlatRateShipping.FlatRateShippingBillingUtilities;
import com.owd.jobs.jobobjects.billing.specialRules.processSpecialRules;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.client.ClientManager;
import com.owd.core.business.intacct.IntacctRequest;
import com.owd.core.business.intacct.IntacctResponse;
import com.owd.core.business.intacct.IntacctUtilities;
import com.owd.core.business.intacct.Invoice;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.LogableException;
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class DailyShippingUpdateJob extends OWDStatefulJob{
private final static Logger log =  LogManager.getLogger();


    public DailyShippingUpdateJob() {
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Interface.
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * <p/>
     * Called by the <code>{@link org.quartz.Scheduler}</code> when a <code>{@link org.quartz.Trigger}</code>
     * fires that is associated with the <code>Job</code>.
     * </p>
     *
     * @throws org.quartz.JobExecutionException
     *          if there is an exception while executing the job.
     */

    public static void main(String[] args) throws Exception {
       // ClientManager.sendAMClientShippingStatusNotifications();
          run();

       }


     public void internalExecute() {
        try {
            Session sess = HibernateSession.currentSession();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date billDate = cal.getTime();

            Date today = Calendar.getInstance().getTime();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            log.debug("Starting owdbill_sweep");
            PreparedStatement stmt = HibernateSession.getPreparedStatement("exec dbo.owdbill_sweep_daily_shipping ?");
            stmt.setString(1, df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
                HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());
            log.debug("End owdbill_sweep");

            log.debug("Starting all inclusive");
            //This will remove  all shipping charges from being billed automatically for all inclusive. They are billed weekly through another way
            stmt = HibernateSession.getPreparedStatement("update owdbill_shipping_trans set original_amount = amount, amount = 0.0 where id in (\n" +
                    "\n" +
                    "SELECT\n" +
                    "\n" +
                    "                        dbo.owdbill_shipping_trans.id\n" +
                    "                 \n" +
                    "                    FROM\n" +
                    "                        dbo.owdbill_shipping_trans\n" +

                    "                    INNER JOIN\n" +
                    "                        dbo.owd_client\n" +
                    "                    ON\n" +
                    "                        (\n" +
                    "                            dbo.owdbill_shipping_trans.client_fkey = dbo.owd_client.client_id)\n" +
                   
                    "                    WHERE\n" +
                    "                        dbo.owdbill_shipping_trans.recorded_date = ?\n" +
                    "                    AND dbo.owd_client.AllInclusive = 1\n" +
                    "                    AND dbo.owdbill_shipping_trans.activity_date >= owd_client.AllInclusive_begin_date" +
                    "                    AND dbo.owdbill_shipping_trans.activity_desc NOT LIKE 'RETURN%');");
                stmt.setString(1,df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

            log.debug("Ending all inclusive");
            log.debug("Starting Clear Carrier Adjustments for Flat Rate");
            //This will remove carrier adjustments from FlatRatePricing orders. Actual shipping charges will be added daily with activity id of 2.
            stmt = HibernateSession.getPreparedStatement("update owdbill_shipping_trans set original_amount = amount, amount = 0.0 where id in (\n" +
                    "\n" +
                    "SELECT\n" +
                    "    dbo.owdbill_shipping_trans.id\n" +
                    "FROM\n" +
                    "    dbo.owd_order\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order_ship_info\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey)\n" +
                    "INNER JOIN\n" +
                    "    dbo.owdbill_shipping_trans\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order.order_id = dbo.owdbill_shipping_trans.order_fkey)\n" +
                    "WHERE\n" +
                    " dbo.owdbill_shipping_trans.recorded_date = ? AND \n    " +
                    " dbo.owdbill_shipping_trans.activity_type_fkey = '1'\n" +
                    "AND dbo.owd_order_ship_info.carr_service_ref_num LIKE 'COM_OWD_FLATRATE%'\n" +
                    "AND dbo.owdbill_shipping_trans.activity_desc NOT LIKE 'RETURN%' and amount <> 0.0 );");

            stmt.setString(1,df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            log.debug("end Clear Carrier Adjustments for Flat Rate");

            log.debug("Starting SmartPost Returns");
            //Update SmartPost Returns Pricing
            stmt = HibernateSession.getPreparedStatement("update owdbill_shipping_trans set original_amount = amount, amount = dbo.udf_owd_service_level_flatrate_getPriceForOrderId(order_fkey,8) where activity_type_fkey = 8 and recorded_date = ?");

            stmt.setString(1,df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

            log.debug("end SmartPost Returns");
            log.debug("Starting Mail Innovations Returns");
            //Update Mail Innovations Returns Pricing
            stmt = HibernateSession.getPreparedStatement("update owdbill_shipping_trans set original_amount = amount, amount = dbo.udf_owd_service_level_flatrate_getPriceForOrderId(order_fkey,9) where activity_type_fkey = 9 and recorded_date = ?");

            stmt.setString(1,df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            log.debug("End Mail Innovations Returns");

            log.debug("Starting FlatRate Duties and Taxes");

            //This will re-apply duty and taxes to any Intravex Records for Flat Rate Shipping Orders.
            FlatRateShippingBillingUtilities.findAndUpdateDutyAndTaxesForFlatRateShipmentsForDate(df.format(today));


            log.debug("End FlatRate Duties and Taxes");
            // Update Third party fees for client and group

            log.debug("Starting Third party fees");
            stmt = HibernateSession.getPreparedStatement("exec billing_updateThirdPartyForDate ?");
            stmt.setString(1, df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

            log.debug("end Third party fees");
            log.debug("Starting Flat RAte Third party fees");

            stmt = HibernateSession.getPreparedStatement("exec billing_update_flatRate_ThirdPartyForDate ?");
            stmt.setString(1, df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            log.debug("end Flat RAte Third party fees");

            log.debug("Starting Third Party Deliverr");
            stmt = HibernateSession.getPreparedStatement("exec billing_updateThirdPartyForDateDeliverrAPI ?");
            stmt.setString(1, df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            log.debug("End Third Party Deliverr");


            log.debug("Starting Flat Rate Extra Charges Saturday");
            //Doing Flat Rate Extra Charges. Saturday, Signature Etc..

            stmt = HibernateSession.getPreparedStatement("exec billing_updateSaturdayFeeForDate ?");
            stmt.setString(1, df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

            log.debug("End Flat Rate Extra Charges Saturday");

            log.debug("Starting Flat Rate Extra Charges Signature");

            stmt = HibernateSession.getPreparedStatement("exec billing_updateSignatureFeeForDate ?");
            stmt.setString(1, df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            log.debug("End Flat Rate Extra Charges Signature");

            log.debug("Starting Flat Rate Extra Charges ExpressPO");
            stmt = HibernateSession.getPreparedStatement("exec billing_updateExpressPOFeeForDate ?");
            stmt.setString(1, df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            log.debug("End Flat Rate Extra Charges ExpressPO");

            log.debug("Starting Flat Rate Extra Charges PassportDutiesTAxes");

            stmt = HibernateSession.getPreparedStatement("exec billing_updatePassportDutiesTaxesForDate ?");
            stmt.setString(1, df.format(billDate));
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

            log.debug("End Flat Rate Extra Charges PassportDutiesTAxes");

            log.debug("Starting Special Billing");

            //Process Special Billing for Shipping.
            processSpecialRules.processShippingRules(df.format(billDate));
            log.debug("end Special Billing");


            if (7 == Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                log.debug("Invoice Day!");
                //invoice day!!
                stmt = HibernateSession.getPreparedStatement("update owdbill_shipping_trans set invoice_date=? where invoice_date is null and recorded_date<=?");

                stmt.setString(1, df.format(billDate));
                stmt.setString(2, df.format(billDate));
                stmt.execute();
                HibUtils.commit(HibernateSession.currentSession());
                HibernateSession.closePreparedStatement();



                HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());
                    stmt = HibernateSession.getPreparedStatement("update owd_client  set shipping_balance=shipping_balance+" +
                    "(select isnull(sum(isnull(amount,0.00)),0.00) from owdbill_shipping_trans t where t.client_fkey=owd_client.client_id and invoice_date=?)");

                stmt.setString(1, df.format(billDate));
            stmt.execute();
             HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
             HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());



            }

            log.debug("After Invoice check and Run");

            Calendar calp = Calendar.getInstance();
            calp.add(Calendar.DATE, -6);
            Date billDatep = calp.getTime();
            postInvoicesForBillDate(billDatep);
            //update transaction records from Intacct (also updates account balances) -- warning: may fail if Intacct is unavailable!!
            log.debug("updating client transactions");
            ClientManager.updateClientIntacctTransactions();
            log.debug("client transactions updated");
            //update unbilled shipping totals per client
            stmt = HibernateSession.getPreparedStatement("update owd_client  set shipping_unbilled=" +
                    "(select isnull(sum(isnull(amount,0.00)),0.00) from owdbill_shipping_trans t where t.client_fkey=owd_client.client_id and invoice_date is null)");

            stmt.execute();
             HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();

            //send AM notifications

       ClientManager.sendAMClientShippingStatusNotifications();


        } catch (Exception ex) {
            ex.printStackTrace();
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:"+Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    this.getClass().getName(),
                    LogableException.errorTypes.BILLING);
        } finally {
            HibernateSession.closeSession();
        }
    }





    private static void postInvoicesForBillDate(Date billDate) throws Exception {
        //get result set and post invoices

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        log.debug("Billing for " + df.format(billDate));
        ResultSet rs = HibernateSession.getResultSet("select shipper_symbol,activity_name,location_code,sum(amount),min(recorded_date),max(recorded_date),client_id\n" +
                ",max(invoice_date) as invdate from owdbill_shipping_trans s join owd_client on client_id = client_fkey join owdbill_activity_types t\n" +
                "on t.id=activity_type_fkey where invoice_date>='" + df.format(billDate) + "' and trans_fkey is null and len(shipper_symbol)>0\n" +
                "group by shipper_symbol,activity_name,location_code, client_id\n" +
                "order by shipper_symbol,activity_name,location_code,client_id");
        String currClient = "";
        String currClientID = "0";
        Map updateMap = new TreeMap();
        Map<String,Date> invDates = new TreeMap<String,Date>();

        Invoice inv = null;
        while (rs.next()) {

            invDates.put(rs.getString(7),rs.getDate("invdate"));
            //calculate and insert invoices to intacct
            if (currClient.equals(rs.getString(1))) {
                //add item
                Invoice.InvoiceItem item = inv.new InvoiceItem();

                item.setAmount(OWDUtilities.roundFloat(rs.getFloat(4)));
                item.setDepartmentid(Invoice.kDeptIDShipping);
                item.setGlaccountno("4005");
                item.setLocationid(rs.getString(3));
                item.setMemo(rs.getString(2));

                inv.getLineitems().add(item);
            } else {

                if (inv != null) {
                    //post invoice
                    try {

                        //log.debug(inv.getCreateInvoiceXML());
                        String key = postIntacctInvoice(inv);
                        updateMap.put(currClientID, key);


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                currClient = rs.getString(1);
                currClientID = rs.getString(7);
                inv = new Invoice();
                inv.setCustomerid(currClient + "_1");
                inv.setDatecreated(invDates.get(currClientID));
                inv.setDatedue(Calendar.getInstance().getTime());
                inv.setDescription("Weekly Shipping Account Invoice");
                inv.setInvoiceno("SH_" + Invoice.dfInvoiceNo.format(inv.getDatecreated()));

                Invoice.InvoiceItem item = inv.new InvoiceItem();

                item.setAmount(OWDUtilities.roundFloat(rs.getFloat(4)));
                item.setDepartmentid(Invoice.kDeptIDShipping);
                item.setGlaccountno("4005");
                item.setLocationid(rs.getString(3));
                item.setMemo(rs.getString(2));

                inv.getLineitems().add(item);

            }

            //update owd records with invoice id/key?


        }

        if (inv != null) {
            //post invoice
            try {

                //log.debug(inv.getCreateInvoiceXML());
                String key = postIntacctInvoice(inv);
                updateMap.put(currClientID, key);

            } catch (Exception ex) {
                ex.printStackTrace();
                LogableException le = new LogableException(ex, ex.getMessage(),
                        "TS:"+Calendar.getInstance().getTimeInMillis(),
                        OWDUtilities.getStackTraceAsString(ex),
                        DailyShippingUpdateJob.class.getName(),
                        LogableException.errorTypes.BILLING);
            }
        }

        log.debug("updating invoice key for: " + updateMap.keySet());
        log.debug("Map:" + updateMap);
        log.debug("dateMap:" + invDates);
        Iterator it = updateMap.keySet().iterator();
        while (it.hasNext()) {
            String cid = (String) it.next();
            PreparedStatement stmt = HibernateSession.getPreparedStatement("update owdbill_shipping_trans " +
                    "set trans_fkey=? where invoice_date=? and client_fkey=?");

            stmt.setInt(1, new Integer((String) updateMap.get(cid)).intValue());
            stmt.setString(2, df.format(invDates.get(cid)));
            stmt.setInt(3, new Integer(cid).intValue());
            stmt.execute();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();
            log.debug("committed invoice update for :" + updateMap.get(cid));
        }
    }

    private static String postIntacctInvoice(Invoice inv) throws Exception {
        IntacctRequest req = new IntacctRequest();
        req.setUseControlID(true);
        req.setControlID(inv.getCustomerid() + "-" + inv.getInvoiceno());
        req.addFunction(inv.getCreateInvoiceXML());
        IntacctResponse resp = IntacctUtilities.intacctAPI(req);
        Document doc = resp.getRawResponse();

        Transformer serializer =
                TransformerFactory.newInstance().newTransformer();
        serializer.setOutputProperty(
                OutputKeys.OMIT_XML_DECLARATION, "yes");


        serializer.transform(
                new DOMSource(doc),
                new StreamResult(System.out));

        if (resp.isOk()) {


            String key = "";

            NodeIterator nlstatus = XPathAPI.selectNodeIterator(doc, "/response/operation/result//key");
            Node ns;
            while ((ns = nlstatus.nextNode()) != null) {
                // Serialize the found nodes to System.out

                key = XPathAPI.eval(ns, ".").toString();


            }

            return key;
        } else {
            //todo - if intacct reports that the invoice already exists, retrieve it, compare amounts to ensure totals are equal, and retrieve
            //trans_fkey value as though posting were successful.
            throw new Exception("Error posting invoice to Intacct");
        }
    }




}
