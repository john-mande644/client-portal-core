package com.owd.web.internal.intravexbills;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.ConnectionManager;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


class IntravexProcessorThread implements Runnable {
    private final static Logger log =  LogManager.getLogger();

    public BufferedReader reader = new BufferedReader(new StringReader("sdf"));
    public HttpSession theSession = null;
    public UploadIntravexBillData uploader = null;

    public void start() {
        Thread t = new Thread(this,"InvoiceProcessor");
        t.start();

    }
       public static void main (String[] args)     throws Exception
        {

            IntravexProcessorThread processor = new IntravexProcessorThread();
            processor.start();
        }


    public void run() {


        Connection cxn = null;
        PreparedStatement stmt = null;

        try {

            cxn = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
            uploader.processReader(theSession);
             HibUtils.commit(HibernateSession.currentSession());

            stmt = cxn.prepareStatement("execute intravexFixDiscounts");
            stmt.executeUpdate();
            stmt.close();
            HibUtils.commit(HibernateSession.currentSession());

            log.debug("sending update message");
            theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "\r<P>Finding bundled hundredweight shipments...");
            String invoiceNumber = "";
         

            //confirm hundredweight/multiship masters
                stmt = cxn.prepareStatement("update intravexebills set discount=totaldiscount, bookrate=abs(totaldiscount)+totalcharge where needs_review is null");
                stmt.executeUpdate();
                stmt.close();

            //confirm hundredweight/multiship masters
         /*   stmt = cxn.prepareStatement("update intravexebills set heavyflag=1 from intravexebills" +
                    " join owd_order_track t" +
                    " join owd_order o on order_id=t.order_fkey" +
                    " on tracking_no=tracking " +
                    " where needs_review is null" +
                    " and ship_packs>1 and sourcetype='Host'");
            stmt.executeUpdate();
            stmt.close();*/

            stmt = cxn.prepareStatement("update intravexebills set is_multiship=1 from intravexebills e \n" +
                            " where heavyFlag>0 and needs_review  is null");
                    stmt.executeUpdate();
                    stmt.close();

            

            //confirm hundredweight/multiship masters
            stmt = cxn.prepareStatement("update intravexebills set is_multiship=1  from intravexebills e \n" +
                    "join (select reference1 as ref1,sum(totalcharge) as grpttl,count(*) as packs from intravexebills where needs_review is null and is_multiship=0 \n" +
                    " and heavyFlag=0 and reference1<>'' group by reference1 having count(*)>1) as grps on grps.ref1=reference1 \n" +
                    "and totalcharge=grpttl and needs_review is null and is_multiship=0 and totalcharge>0 and deliverydate is null");
            stmt.executeUpdate();
            stmt.close();




             stmt = cxn.prepareStatement("update intravexebills set discount=ABS(discount) from intravexebills e "
                    + " where needs_review  is null");
            stmt.executeUpdate();
            stmt.close();
            //log.debug(" checking subquery");
            //clear hundredweight items with masters
            stmt = cxn.prepareStatement("update intravexebills set item_value=0.00,needs_review=0,\n" +
                    "parent_ups_key=\n" +
                    "(select top 1 id from intravexebills e3 where e3.reference1=intravexebills.reference1 and e3.needs_review is null \n" +
                    "  and e3.is_multiship=1  and e3.reference1<>'' and e3.totalcharge<>0.00 order by id desc ) \n" +
                    "from intravexebills\n" +
                    "where\n" +
                    "reference1<>'' and totalcharge=0.00 and is_multiship<>1 and\n" +
                    " reference1 in (select distinct reference1 from intravexebills where is_multiship=1 and needs_review is null)");
            stmt.executeUpdate();
            stmt.close();
            /*
          New logic for multiship

          --continue to ignore individual records    (mark as not needing review with 0 for bill_owd and bill_client)
          --update tracking number field with lead tracking from package list

          if order identified:
          --collect item_value for total billed original for shipment
          --apply to item_value for master record
          --process normally

          if no order identified
          -- treat as needing review for single master record
            */
            stmt = cxn.prepareStatement("select distinct reference1,invoicenumber from intravexebills " +
                    "where needs_review is null and is_multiship=1 \n");
            List<String> multiList = new ArrayList<String>();
            List<String> invoiceList = new ArrayList<String>();
            ResultSet rs = stmt.executeQuery();
            //log.debug("Executed query 1");
            while (rs.next()) {
                multiList.add(rs.getString(1));
                invoiceList.add(rs.getString(2));
                log.debug("Executed query 1 : got " + rs.getString(2) + " packages for multiship key " + rs.getString(1));

            }

            rs.close();
            stmt.close();

            stmt = cxn.prepareStatement("update intravexebills " +
                          "set needs_review=0, bill_owd=1,bill_client=0,item_value=0.00 where sourcetype in ('Service Charge','Void Credits')" +
                    " \n");
                  stmt.executeUpdate();
                  //log.debug("Executed query 1");

                  stmt.close();

              //skip calculations for orders under shipping guarantee
               stmt = cxn.prepareStatement("update intravexebills set  needs_review=0, bill_owd=1,bill_client=0,item_value=0.00 where intravexebills.id in (select distinct (i.id) from intravexebills i" +
                       "    join" +
                       " owd_order_track t" +
                       "        join  owd_order o" +
                       "             join owd_order_charges c " +
                       "                 on c.order_fkey=o.order_id and owd_guarantee_flag=1 and ship_bill_cost<>0 " +
                       "         on t.order_fkey=o.order_id" +
                       "     on t.tracking_no=tracking and needs_review=1)" +
                    " \n");
                  stmt.executeUpdate();
                  //log.debug("Executed query 1");

                  stmt.close();

            if (multiList.size() > 0) {
                for (int i = 0; i < multiList.size(); i++) {


                    //get detail info based on looking up tracking numbers associated with this multiship record group and pulling order data from any matches
                    String getOrderDataSQL = "select o.client_fkey,min(t.order_fkey),convert(money,sum(total_billed)) from owd_order_track t " +
                            " join owd_order o on o.order_id=t.order_fkey join intravexebills \n" +
                            " on tracking_no=tracking where reference1=? and invoicenumber=? and needs_review=0 and t.is_void=0 and parent_ups_key is not null" +
                            " and ship_date>dateadd(month,-9,getdate()) group by  o.client_fkey";
                    stmt = cxn.prepareStatement(getOrderDataSQL);
                    String reference1 = (String) multiList.get(i);
                    stmt.setString(1, reference1);
                    stmt.setString(2, invoiceList.get(i));
                    log.debug("checking miscline3 value " + reference1);
                    rs = stmt.executeQuery();
                    while (rs.next()) {

                        log.debug("got values for " + reference1 + " " + rs.getInt(1) + ":" + rs.getInt(2) + ":" + rs.getFloat(3));
                        String updater = "update intravexebills set bill_client=1,bill_owd=0,client_fkey=?,order_fkey=?,item_value=?" +
                                " where is_multiship=1 and reference1=? and totalcharge<>0 and needs_review is null";

                        String updater2 = "update intravexebills set bill_client=1,bill_owd=0, client_fkey=?,order_fkey=?,item_value=0.00" +
                                " where is_multiship=1 and reference1=? and totalcharge=0 and  needs_review is null";
                        PreparedStatement ps = cxn.prepareStatement(updater);
                        PreparedStatement ps2 = cxn.prepareStatement(updater2);
                        ps.setInt(1, rs.getInt(1));
                        ps.setInt(2, rs.getInt(2));
                        ps.setFloat(3, rs.getFloat(3));
                        ps.setString(4, reference1);

                        ps2.setInt(1, rs.getInt(1));
                        ps2.setInt(2, rs.getInt(2));
                        ps2.setString(3, reference1);
                        //log.debug("run update 1");
                        ps.executeUpdate();
                        //log.debug("run update 2");
                        ps2.executeUpdate();
                        ps.close();
                        ps2.close();
                    }
                    rs.close();
                    stmt.close();


                }     //end loop for each distinct multi-order shipment


            }



                        stmt = cxn.prepareStatement("update intravexebills " +
                          "set needs_review=0, bill_owd=1,bill_client=0,item_value=0.00,ups_status='delete' where totalcharge=0" +
                    " \n");
                  stmt.executeUpdate();
                  log.debug("Executed query 1");

                  stmt.close();


            stmt = cxn.prepareStatement("update intravexebills set bill_owd=1,needs_review=0 where servicetype='fedex smartpost' and reference1='' and needs_review is null\n" +
                    "            and sourcetype='Manual Airbill'" +
                    " \n");
            stmt.executeUpdate();
            log.debug("Executed query 1");

            stmt.close();

           



             theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "\r<P>Matching items to orders...");
            //match with orders/clients based on reference number
            //  stmt = cxn.prepareStatement("update ups_ebill set order_fkey=order_id,client_fkey=owd_order.client_fkey from ups_ebill e left outer join owd_order on order_num = reference1 and is_void=0 where needs_review is null");
            //  stmt.executeUpdate();
            //  stmt.close();

            //  theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
          //  theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>Matching items to packages and orders...");

            log.debug("match package key through tracking number");
            stmt = cxn.prepareStatement("update intravexebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id from intravexebills  "
                    + "join owd_order_track t join owd_order o on t.order_fkey = order_id on tracking=tracking_no and is_multiship=0 and t.is_void=0 and tracking <> '' where needs_review is null"
                    +" and dateadd(day,-45,getdate())<shipped_on ");
            stmt.executeUpdate();
            stmt.close();

            log.debug("match package key through tracking number UPS Mail Innovations");
            stmt = cxn.prepareStatement("update intravexebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id from intravexebills  "
                    + "join owd_order_track t join owd_order o on t.order_fkey = order_id on reference1=tracking_no and is_multiship=0 and t.is_void=0 and tracking <> '' where needs_review is null  and carrierName = 'UPS Mail Innovations'"
                    +" and dateadd(day,-45,getdate())<shipped_on ");
            stmt.executeUpdate();
            stmt.close();

            //now smartpost
            log.debug("match package key through tracking number");
            stmt = cxn.prepareStatement("update intravexebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id from intravexebills  "
                    + "join owd_order_track t join owd_order o on t.order_fkey = order_id on '92'+tracking=tracking_no and is_multiship=0 and t.is_void=0 and tracking <> '' where needs_review is null"
                    +" and dateadd(day,-45,getdate())<shipped_on ");
            stmt.executeUpdate();
            stmt.close();


            stmt = cxn.prepareStatement("\n" +
                    "update intravexebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id\n" +
                    "from  intravexebills e (NOLOCK)\n" +
                    "                  join owd_order o (NOLOCK)\n" +
                    "                  left outer join owd_order_track t (NOLOCK) on t.order_fkey=o.order_id and t.is_void=0 and t.line_index=1\n" +
                    "                  on  (order_refnum=replace(reference1,' ','.'))\n" +
                    "                  where is_multiship=0 and needs_review is null and e.client_fkey is null\n" +
                    "                   and dateadd(day,-45,getdate())<shipped_on;\n" +
                    "                   \n" +
                    "update intravexebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id\n" +
                    "from  intravexebills e (NOLOCK)\n" +
                    "                  join owd_order o (NOLOCK)\n" +
                    "                  left outer join owd_order_track t (NOLOCK) on t.order_fkey=o.order_id and t.is_void=0 and t.line_index=1\n" +
                    "                  on  (\n" +
                    "                 ( (reference1=replace(order_refnum,'.','')) )\n" +
                    "                  )\n" +
                    "                  where is_multiship=0 and needs_review is null and e.client_fkey is null\n" +
                    "                   and dateadd(day,-45,getdate())<shipped_on ;\n" +
                    "                   \n" +
                    "update intravexebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id\n" +
                    "from  intravexebills e (NOLOCK)\n" +
                    "                  join owd_order o (NOLOCK)\n" +
                    "                  left outer join owd_order_track t (NOLOCK) on t.order_fkey=o.order_id and t.is_void=0 and t.line_index=1\n" +
                    "                  on  reference1=order_refnum\n" +
                    "                 \n" +
                    "                  where is_multiship=0 and needs_review is null and e.client_fkey is null\n" +
                    "                   and dateadd(day,-45,getdate())<shipped_on ;\n" +
                    "                   \n" +
                    "update intravexebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id\n" +
                    "from  intravexebills e (NOLOCK)\n" +
                    "                  join owd_order o (NOLOCK)\n" +
                    "                  left outer join owd_order_track t (NOLOCK) on t.order_fkey=o.order_id and t.is_void=0 and t.line_index=1\n" +
                    "                  on  ( len(reference1)=30 AND (order_refnum like (reference1+'%'))) \n" +
                    "                  where is_multiship=0 and needs_review is null and e.client_fkey is null\n" +
                    "                   and dateadd(day,-45,getdate())<shipped_on ;\n" +
                    "                   \n" +
                    "update intravexebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id\n" +
                    "from  intravexebills e (NOLOCK)\n" +
                    "                  join owd_order o (NOLOCK)\n" +
                    "                  left outer join owd_order_track t (NOLOCK) on t.order_fkey=o.order_id and t.is_void=0 and t.line_index=1\n" +
                    "                  on  (  (reference1='' and order_refnum=reference2)\n" +
                    "                  )\n" +
                    "                  where is_multiship=0 and needs_review is null and e.client_fkey is null\n" +
                    "                   and dateadd(day,-45,getdate())<shipped_on ;\n" +
                    "                   \n" +
                    "update intravexebills  set package_fkey=order_track_id, client_fkey=o.client_fkey, order_fkey=order_id\n" +
                    "from  intravexebills e (NOLOCK)\n" +
                    "                  join owd_order o (NOLOCK)\n" +
                    "                  left outer join owd_order_track t (NOLOCK) on t.order_fkey=o.order_id and t.is_void=0 and t.line_index=1\n" +
                    "                  on  ((convert(varchar,o.order_id))=reference1\n" +
                    "                )\n" +
                    "                  where is_multiship=0 and needs_review is null and e.client_fkey is null\n" +
                    "                   and dateadd(day,-45,getdate())<shipped_on ;\n" +
                    "update intravexebills set client_fkey = o.client_fkey, order_fkey=o.order_id, sourceType='SmartPostReturns'\n" +
                    "   \n" +
                    "FROM\n" +
                    "    dbo.intravexebills e\n" +
                    "INNER JOIN\n" +
                    "    dbo.order_ship_info2\n" +
                    "ON\n" +
                    "    (\n" +
                    "        e.tracking = dbo.order_ship_info2.return_tracking)\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order o\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.order_ship_info2.order_fkey = o.order_id) where \n" +
                    "        is_multiship=0 and needs_review is null and e.client_fkey is null and importDate > '2019-08-14' and servicetype='fedex smartpost' ;" +
                    "update intravexebills set client_fkey = o.client_fkey, order_fkey=o.order_id, sourceType='MailInnovationsReturns'\n" +
                    "   \n" +
                    "FROM\n" +
                    "    dbo.intravexebills e\n" +
                    "INNER JOIN\n" +
                    "    dbo.order_ship_info2\n" +
                    "ON\n" +
                    "    (\n" +
                    "        e.reference1 = dbo.order_ship_info2.return_tracking)\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order o\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.order_ship_info2.order_fkey = o.order_id) where \n" +
                    "        is_multiship=0 and needs_review is null and e.client_fkey is null and importDate > '2019-08-14' and servicetype='UPS Mail Innovations Returns' ;");
            stmt.executeUpdate();
            stmt.close();



            /*     theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Done!<BR>");
                            theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "Fixing TeamDepot virtual shipments...");


            */


            theSession.setAttribute("updateMessage", theSession.getAttribute("updateMessage") + "\r<P>Applying discounts...");

            applyDiscountsToUnreviewedRecords(cxn);

            log.debug("fix bad discount values");
            stmt = cxn.prepareStatement("update intravexebills set discount=(bookrate-total_billed) from intravexebills e "
                    + "join owd_order_track v  on v.order_track_id=e.package_fkey and v.is_void=0 and client_fkey is not null " +
                    "and is_multiship=0 and discount=0.00 and charges<>0 " +
                    "and (bookrate-total_billed)>0 and sourcetype not in ('return service','Adjustments','charge back','import invoice')" +
                    "and package_fkey is not null and parent_ups_key is null"
                    + " where  needs_review is null");
            stmt.executeUpdate();
            stmt.close();

            stmt = cxn.prepareStatement("update intravexebills set item_value=0 where order_fkey in (select order_fkey from vw_zerocost_ships) and needs_review=null");
            stmt.executeUpdate();

            stmt.close();

            log.debug("clear no discount exact billing records");
            stmt = cxn.prepareStatement("update intravexebills set bill_owd=1,bill_client=0,needs_review=0 from intravexebills e "
                    + "join owd_order_track v  on v.order_track_id=e.package_fkey and v.is_void=0 and client_fkey is not null " +
                    "and is_multiship=0 and discount=0.00 and charges<>0 " +
                    "and (bookrate-total_billed)=0 and bookrate>0 and total_billed>0 and sourcetype not in ('return service','Adjustments','charge back','import invoice')" +
                    "and package_fkey is not null and parent_ups_key is null"
                    + " where  needs_review is null");
            stmt.executeUpdate();
            stmt.close();


            log.debug("updates non-master multiship records to zero");
            stmt = cxn.prepareStatement("update intravexebills set bill_client=1,needs_review=0, item_value=(case when discount=0.00 then 0.00 else ISNULL(total_billed,0.00) end) from intravexebills e "
                    + "join owd_order_track v  on v.order_track_id=e.package_fkey and v.is_void=0 and client_fkey is not null " +
                    "and is_multiship=0 " +
                    "and package_fkey is not null and parent_ups_key is null"
                    + " where  needs_review is null and e.order_fkey not in (select order_fkey from vw_zerocost_ships) ");
            stmt.executeUpdate();
            stmt.close();

            stmt = cxn.prepareStatement("update intravexebills set item_value=shipped_cost,is_multiship=1\n" +
                               " from intravexebills \n" +
                               "    join owd_order o\n" +
                               "         join (select order_fkey from intravexebills where item_value>0 group by order_fkey having count(*)=1) as singles on singles.order_fkey=order_id\n" +
                               " on order_id=intravexebills.order_fkey and ship_packs>1 and item_value>0 and is_multiship=0 and sourcetype='Worldwide Service' where order_id not in (select order_fkey from vw_zerocost_ships) ");
                     stmt.executeUpdate();
                     stmt.close();


            log.debug("mark all items that are always assigned to OWD");
            stmt = cxn.prepareStatement("update intravexebills set bill_owd=1,item_value=0.00,needs_review=0,client_fkey=55 from intravexebills e "
                    + " where sourcetype in (\'Service Charge\') "
                    + " and needs_review is null");
            stmt.executeUpdate();
            stmt.close();



            log.debug("mark and clear all items that are automatically billed as-is");
            stmt = cxn.prepareStatement("update intravexebills set bill_client=1,item_value=0.00,needs_review=0 from intravexebills e "
                    + "where client_fkey is not null and ISNULL(item_value,0.00)=0.00 "
                    + " and needs_review is null");
            stmt.executeUpdate();
            stmt.close();


            //deal with the weird ones


            log.debug("complete processing - mark all remaining items as needing review");
            stmt = cxn.prepareStatement("update intravexebills set needs_review = 0 where needs_review is null and client_fkey is not null and order_fkey is not null and is_multiship=1");
            stmt.executeUpdate();

             stmt.close();

            log.debug("complete processing - mark all remaining items as needing review");
            stmt = cxn.prepareStatement("update intravexebills set needs_review = 1 where needs_review is null");
            stmt.executeUpdate();

             stmt.close();



HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception tex) {
            tex.printStackTrace();
        } finally {
            try {
                cxn.close();
            } catch (Exception sex) {
            }
            try {
                stmt.close();
            } catch (Exception sex) {
            }
            theSession.setAttribute("inprogress",null) ;

        }

    }

     protected static void applyDiscountsToUnreviewedRecords(Connection cxn) throws SQLException {
             PreparedStatement stmt;

        //clear all affected records
        stmt = cxn.prepareStatement("update intravexebills set client_incentive=0.00,client_incentive_pct=0.00  "
                     + " where needs_review is null");
             stmt.executeUpdate();

          //set the incentive share total value
             stmt = cxn.prepareStatement("update intravexebills set client_incentive=(isnull(discount_share_pct*(discount),0.00)-0.18) "
                                       + " from intravexebills join owd_client on client_id=client_fkey and needs_review is null");
             stmt.executeUpdate();

        //calculate client percentage of original charge
             stmt = cxn.prepareStatement("update intravexebills set client_incentive_pct=round(client_incentive/(bookrate),2)  "
                     + " where  (client_incentive>0 or client_incentive < 0) and needs_review is null");
             stmt.executeUpdate();

        //correct for any over-incentivizing...
             stmt = cxn.prepareStatement("update intravexebills set client_incentive =discount " +
                     "where ABS(discount)<ABS(client_incentive) and needs_review is null");
             stmt.executeUpdate();



        stmt.close();
         }

}


