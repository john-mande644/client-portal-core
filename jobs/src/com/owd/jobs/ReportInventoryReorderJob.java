package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.ExcelUtils;
import com.owd.core.MailAddressValidator;
import com.owd.core.Mailer;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportInventoryReorderJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public ReportInventoryReorderJob() {
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

     public void internalExecute() {
        try {
            Session sess = HibernateSession.currentSession();

            //get clients that have the alert flag set for low inventory

            List clientList = sess.createQuery("from OwdClient as clients where is_active=1 and low_inventory_alert=1").list();
            //log.debug("Found " + clientList.size() + " clients to check for low inventory...");
            Iterator clients = clientList.iterator();
            while (clients.hasNext()) {

                OwdClient client = (OwdClient) clients.next();
                log.debug("cking reorder points for "+client.getCompanyName());
                //for each client, get list of inventory items that have crossed the threshold since last check
               //log.debug("checking "+client.getCompanyName());
                StringBuffer alertText = new StringBuffer();
                StringBuffer alertAttachment = new StringBuffer();
                ResultSet items = HibernateSession.getResultSet("select inventory_num, ISNULL(supp_name,ISNULL(mfr_part_num,'')),qty_reorder,ISNULL(case when is_auto_inventory=1 then 99999 else qty_on_hand end,0),\n" +
                        "                        ISNULL(qty_high_alert,0) as 'old level', i.description, i.inventory_id, \n" +
                        "(select ISNULL(sum(expected-received),0) from asn_items (NOLOCK) join asn (NOLOCK) on asn.id=asn_items.asn_fkey and inventory_fkey=i.inventory_id and status in (0,2) and asn.client_fkey=i.client_fkey and expected>=received) as 'x'\n" +
                        "                        from owd_inventory i (NOLOCK) \n" +
                        "                        left outer join owd_supplier (NOLOCK) on supplier_id=supp_fkey\n" +
                        "                        join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey\n" +
                        "                        where qty_reorder>0 and qty_on_hand <= qty_reorder and ISNULL(qty_high_alert,0)>qty_reorder\n" +
                        "                        and is_active=1 and i.client_fkey=" + client.getClientId());

                boolean foundItems = false;
                alertAttachment.append("Inventory Reorder Alerts\n\nSKU,Description,Supplier,Reorder Level,Current Stock,On Order (from OWD ASNs)\n");
                alertText.append("The following SKUs have dropped to or below their reorder levels.\n\nSKUS\nDetails are attached to this email as a "
                        + "comma-delimited text file, suitable for opening in Excel or other spreadsheet or database program.");
                StringBuffer skuList = new StringBuffer();
                skuList.append("SKU,Description,Reorder Level,Current Stock\n");

                Map itemMap = new TreeMap();
                while (items.next()) {
                    foundItems = true;

                    alertAttachment.append(ExcelUtils.getCellValue(items.getString(1)) + ",");
                    alertAttachment.append(ExcelUtils.getCellValue(items.getString(6)) + ",");
                    alertAttachment.append(ExcelUtils.getCellValue(items.getString(2)) + ",");
                    alertAttachment.append(ExcelUtils.getCellValue(items.getString(3)) + ",");
                    alertAttachment.append(ExcelUtils.getCellValue(items.getString(4)) + ",");
                    alertAttachment.append(ExcelUtils.getCellValue(items.getString(8)) + "\n");
                    itemMap.put(new Integer(items.getInt(7)), new Integer(items.getInt(4)));

                    skuList.append(items.getString(1) + ",");
                    skuList.append(items.getString(6) + ",");
                    skuList.append(items.getString(3) + ",");
                    skuList.append(items.getString(4) + "\n");

                    runSneakpeeqCaseBreakdownRule(items.getString(1));

                }

                String alertStr = alertText.toString();
                     log.debug("alert:"+alertStr);
                log.debug(""+skuList);
                alertStr = alertStr.replace("SKUS",skuList.toString());
                    log.debug("alert2:"+alertStr);
                HibernateSession.closeStatement();
                //create email alert for list if non-zero
                if (foundItems) {
                    String amEmail = "owditadmin@owd.com";
                    if (client != null) {
                        if (client.getAmEmail().length() > 5) {
                            amEmail = client.getAmEmail();

                        }
                    }

                    String clientAddresses = client.getLowInventoryEmail();

                    Vector ccs = new Vector();


                    if (clientAddresses.indexOf("@") > 0) {

                        StringTokenizer tokens = new StringTokenizer(clientAddresses, ",");

                        while (tokens.hasMoreTokens()) {

                            String addr = tokens.nextToken();
                            //log.debug("cking email |"+addr+"|");
                            try {
                                MailAddressValidator.validate(addr);
                            } catch (Exception ea) {
                                //log.debug("email address "+addr+" not valid!");
                                addr = null;
                            }

                            if (addr != null) {
                                ccs.addElement(addr);
                            }
                        }

                    }
                    //send email to AM and indicated client addresses


                    Mailer.sendMailWithAttachment("OWD Inventory Alert for " + client.getCompanyName(),
                            alertStr, amEmail, (ccs == null ? null : (ccs.toArray())), alertAttachment.toString().getBytes(), "alert.csv", "application/octet-stream");



                }

            }

            //update inventory history with today's current stock level for comparison on next run
            PreparedStatement stmt = HibernateSession.getPreparedStatement("update owd_inventory \n" +
                    "set qty_high_alert = ISNULL(case when is_auto_inventory=1 then 99999 else qty_on_hand end,0) from owd_inventory_oh join owd_inventory on  inventory_id=inventory_fkey where qty_high_alert is null or \n" +
                    " (qty_reorder>0 and ((qty_on_hand>qty_reorder and qty_high_alert<=qty_reorder) or (qty_on_hand<=qty_reorder and qty_high_alert>qty_reorder))) \n" +
                    " ");
            stmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {
             ex.printStackTrace();

        } finally {
            HibernateSession.closeSession();
        }
    }

    public static void main(String[] args) {
        run();
    }

    public static void runSneakpeeqCaseBreakdownRule(String sku)
    {

        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;


        try {
            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement("select s.*, eid , cid  from sp_casebreaks s \n" +
                    "join (select inventory_num,inventory_id as eid from owd_inventory i where i.client_fkey=489)\n" +
                    "as estuff on estuff.inventory_num=eachsku\n" +
                    "join (select inventory_num,inventory_id as cid from owd_inventory i where i.client_fkey=489)\n" +
                    "as cstuff on cstuff.inventory_num=casesku\n" +
                    "\n" +
                    "where eachsku=?");
            stmt.setString(1,sku);
            stmt.executeQuery();

            rs = stmt.getResultSet();

            if(rs.next())
            {
                String casesku = rs.getString("casesku");
                int caseqty = rs.getInt("caseqty");
                int case_breakqty = rs.getInt("case_breakqty");
                int eid = rs.getInt("eid");
                int cid = rs.getInt("cid");
                StringBuffer body = new StringBuffer();
                body.append("\r\nThe inventory level of "+sku+" has fallen below a critical level. Please create a work order to breakdown cases to eaches as follows:\r\n\r\n");
                body.append("Break "+case_breakqty+" units of "+casesku+" ["+cid+"] (the case sku) into eaches. This should result in "+(case_breakqty*caseqty)+" new units of "+sku+" ["+eid+"] being created.\r\n\r\n");
                body.append(case_breakqty+" units of "+casesku+" ["+cid+"] --breakdown--> "+(case_breakqty*caseqty)+" units of "+sku+" ["+eid+"]");


                Mailer.sendMail("Sneakpeeq Case Breakdown Work Order for " + sku,
                        body.toString(), "thoven@owd.com", "donotreply@owd.com");


            }

        }catch(Throwable th)
        {
            th.printStackTrace();
        }finally
        {
            try{rs.close();}catch(Exception ex){}
            try{stmt.close();}catch(Exception ex){}
            try{cxn.close();}catch(Exception ex){}

        }
    }


}
