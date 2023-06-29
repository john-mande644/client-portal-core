package com.owd.jobs.clients;

import com.opencsv.CSVWriter;
import com.owd.LogableException;
import com.owd.core.Mailer;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import org.hibernate.Query;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by danny on 5/10/2017.
 */
public class LyftMailHouseOrdersDailyFileJob extends OWDStatefulJob{


    public static void main(String[] args){
        run();
    }

    public void internalExecute(){


        boolean proceed = setShippingFlag();
        if(proceed) {
            CreateFileForSku("LYFT153");
            CreateFileForSku("LYFT157");
            CreateFileForSku("LYFT215");
            CreateFileForSku("LYFT230");
            CreateFileForSku("LYFT260");
            CreateFileForSku("LYFT360");
            CreateFileForSku("ADT Decal");

           processLyftMHOrders();
        }else{
            System.out.println("Nothing here");
        }

    }



    public void CreateFileForSku(String sku){


//USA order only
        String sql = "SELECT\n" +
                "                       owd_order.order_refnum,\n" +
                "                    dbo.owd_order_ship_info.ship_first_name + ' '+ \n" +
                "                    dbo.owd_order_ship_info.ship_last_name as \"name\",\n" +
                "                    dbo.owd_order_ship_info.ship_address_one,\n" +
                "                    dbo.owd_order_ship_info.ship_address_two,\n" +
                "                    dbo.owd_order_ship_info.ship_city,\n" +
                "                    dbo.owd_order_ship_info.ship_state,\n" +
                "                    dbo.owd_order_ship_info.ship_zip\n" +
                "                FROM\n" +
                "                    dbo.owd_order\n" +
                "                INNER JOIN\n" +
                "                    dbo.owd_order_ship_info\n" +
                "                ON\n" +
                "                    (\n" +
                "                        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey)\n" +
                "                 INNER JOIN \n" +
                "                 owd_line_item\n" +
                "                 on( owd_line_item.order_fkey = owd_order.order_id)       \n" +
                "                WHERE\n" +
                "                    dbo.owd_order.client_fkey = 529\n" +
                "                AND dbo.owd_order.group_name = 'MH'\n" +
                "                AND dbo.owd_order.order_status = 'At Warehouse'\n" +
                "                and inventory_num = '"+sku+"'  and is_shipping = 1 and ship_country = 'USA';";
        try{
            ResultSet rs = HibernateSession.getResultSet(sql);
            if(rs.isBeforeFirst()) {
                StringWriter s = new StringWriter();
                CSVWriter writer = new CSVWriter(s);
                writer.writeAll(rs, true);
                DateFormat df = new SimpleDateFormat("yyyyMMdd");

                Calendar aCalendar = Calendar.getInstance();
                String stamp = df.format(aCalendar.getTime());


                Object[] cc = new Object[3];
                cc[0] = "wayne@usprinting.com";
                cc[1] = "gabriel.rodriguez@owd.com";

                cc[2] = "jasonm@usprinting.com";

                Mailer.sendMailWithAttachment("Lyft Mailhouse " + sku + " " + stamp, "Attached are todays orders for " + sku, "info@usprinting.com", cc, s.toString().getBytes(), "Lyft_" + sku + "-" + stamp + ".csv", "text/csv");
                //  Mailer.sendMailWithAttachment("Lyft Mailhouse "+sku+" " + stamp, "Attached are todays orders for "+sku, "wayne@usprinting.com", s.toString().getBytes(), "Lyft_" + df.format(aCalendar.getTime()) + "-"+stamp+".csv", "text/csv");
                //  Mailer.sendMailWithAttachment("Lyft Mailhouse "+sku+" " + stamp, "Attached are todays orders for "+sku, "gail@owd.com", s.toString().getBytes(), "Lyft_" + df.format(aCalendar.getTime()) + "-"+stamp+".csv", "text/csv");
            }



            //CANADA order only
           sql = "SELECT\n" +
                    "                        owd_order.order_refnum,\n" +
                    "                    dbo.owd_order_ship_info.ship_first_name + ' '+ \n" +
                    "                    dbo.owd_order_ship_info.ship_last_name as \"name\",\n" +
                    "                    dbo.owd_order_ship_info.ship_address_one,\n" +
                    "                    dbo.owd_order_ship_info.ship_address_two,\n" +
                    "                    dbo.owd_order_ship_info.ship_city,\n" +
                    "                    dbo.owd_order_ship_info.ship_state,\n" +
                    "                    dbo.owd_order_ship_info.ship_zip\n" +
                    "                FROM\n" +
                    "                    dbo.owd_order\n" +
                    "                INNER JOIN\n" +
                    "                    dbo.owd_order_ship_info\n" +
                    "                ON\n" +
                    "                    (\n" +
                    "                        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey)\n" +
                    "                 INNER JOIN \n" +
                    "                 owd_line_item\n" +
                    "                 on( owd_line_item.order_fkey = owd_order.order_id)       \n" +
                    "                WHERE\n" +
                    "                    dbo.owd_order.client_fkey = 529\n" +
                    "                AND dbo.owd_order.group_name = 'MH'\n" +
                    "                AND dbo.owd_order.order_status = 'At Warehouse'\n" +
                    "                and inventory_num = '"+sku+"'  and is_shipping = 1 and ship_country = 'CANADA';";

                 rs = HibernateSession.getResultSet(sql);
                if(rs.isBeforeFirst()) {
                    StringWriter s = new StringWriter();
                    CSVWriter writer = new CSVWriter(s);
                    writer.writeAll(rs, true);
                    DateFormat df = new SimpleDateFormat("yyyyMMdd");

                    Calendar aCalendar = Calendar.getInstance();
                    String stamp = df.format(aCalendar.getTime());


                    Object[] cc = new Object[3];
                    cc[0] = "wayne@usprinting.com";
                    cc[1] = "gabriel.rodriguez@owd.com";

                    cc[2] = "jasonm@usprinting.com";

                    Mailer.sendMailWithAttachment("Lyft Canada Mailhouse " + sku + " " + stamp, "Attached are todays orders for " + sku, "info@usprinting.com", cc, s.toString().getBytes(), "Lyft_CANADA_" + sku + "-" + stamp + ".csv", "text/csv");
                    //  Mailer.sendMailWithAttachment("Lyft Mailhouse "+sku+" " + stamp, "Attached are todays orders for "+sku, "wayne@usprinting.com", s.toString().getBytes(), "Lyft_" + df.format(aCalendar.getTime()) + "-"+stamp+".csv", "text/csv");
                    //  Mailer.sendMailWithAttachment("Lyft Mailhouse "+sku+" " + stamp, "Attached are todays orders for "+sku, "gail@owd.com", s.toString().getBytes(), "Lyft_" + df.format(aCalendar.getTime()) + "-"+stamp+".csv", "text/csv");
                }


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void processLyftMHOrders(){

        try{
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Query q = HibernateSession.currentSession().createSQLQuery("select order_id from owd_order where is_shipping=1 and order_status = 'At Warehouse' and client_fkey = 529 and group_name = 'MH'");
            List l = q.list();
            System.out.println(l.size());

            System.out.println(df.format(Calendar.getInstance().getTime()));

            for(Object row: l){
                markAsShipped(529,row.toString(),df.format(Calendar.getInstance().getTime()));
            }



        }catch (Exception e){
            e.printStackTrace();

        }




    }



    public static boolean markAsShipped(int clientId,String orderId,String addToTracking){
        System.out.println("Doing: "+ orderId);
        boolean success = false;
        String updateSQL = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                + "msn,is_void,reported,email_sent) VALUES (:orderId,1,:tracking,:weight,:billed,:cost,convert(datetime,convert(varchar,getdate(),101)),getdate(),\'Manual\',getdate(),\'Manual\',0,0, 0,0)";
        try {




            System.out.println("WE have this order Id" + orderId);
            Query qq = HibernateSession.currentSession().createSQLQuery(updateSQL);
            qq.setParameter("orderId",orderId);
            qq.setParameter("tracking",orderId+" "+addToTracking);
            qq.setParameter("weight",0);
            qq.setParameter("billed",0);
            qq.setParameter("cost",0);
            int i = qq.executeUpdate();

            if(i>0) {
                String sql = "exec update_order_shipment_info :orderId";
                Query qqq = HibernateSession.currentSession().createSQLQuery(sql);
                qqq.setParameter("orderId", orderId);
                int ii = qqq.executeUpdate();
                HibUtils.commit(HibernateSession.currentSession());
                success = true;
            }




        }catch (Exception e){
            e.printStackTrace();
        }


        return success;
    }


    public static boolean setShippingFlag(){
        boolean success = false;
        try{
            Query q = HibernateSession.currentSession().createSQLQuery("update owd_order set is_shipping = 1 where client_fkey = 529 and group_name = 'MH' and order_status = 'At Warehouse'");
            int i = q.executeUpdate();

            if(i>0) {
                HibUtils.commit(HibernateSession.currentSession());
                success = true;

            }
        }catch (Exception e){
            throw new LogableException(e.getMessage(),"Error with Lyft Daily file","","529","Daily Job", LogableException.errorTypes.INTERNAL);

        }
        return success;
    }


}
