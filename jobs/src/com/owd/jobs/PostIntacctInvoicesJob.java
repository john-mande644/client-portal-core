package com.owd.jobs;

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
import org.apache.xpath.XPathAPI;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

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
public class PostIntacctInvoicesJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public PostIntacctInvoicesJob() {
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


    public void internalExecute()

    {
        try {
            Session sess = HibernateSession.currentSession();

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -6);
            Date billDate = cal.getTime();

            postInvoicesForBillDate(billDate);

            //update transaction records from Intacct (also updates account balances)

            ClientManager.updateClientIntacctTransactions();

            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());


        } catch (Exception ex) {
            ex.printStackTrace();
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

      /*  Transformer serializer =
                TransformerFactory.newInstance().newTransformer();
        serializer.setOutputProperty(
                OutputKeys.OMIT_XML_DECLARATION, "yes");


        serializer.transform(
                new DOMSource(doc),
                new StreamResult(System.out));*/

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

    public static void main(String[] args) {
        // DailyShippingUpdateJob.internalExecute();

        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -6);
            Date billDate = cal.getTime();

         //   postInvoicesForBillDate(billDate);

            //update transaction records from Intacct (also updates account balances)
            log.debug("starting trans update");
            ClientManager.updateClientIntacctTransactions();
            log.debug("ending trans update");
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.closePreparedStatement();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
