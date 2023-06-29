package com.owd.dc.warehouse.statusScroller;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import org.hibernate.Query;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 5/16/14
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class statusScrollerUtils {

    public static void main(String[] args){
             try{
               System.out.println(getAnnouncements("DC1"));
              /*   XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
                     public HierarchicalStreamWriter createWriter(Writer writer) {
                         return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
                     }
                 });
                 scrollerMessageBean smb = new scrollerMessageBean();
                 smb.setMessage("hello");
                 smb.setId("123");
                 System.out.println(xstream.toXML(smb));*/

                statusScrollerData data = loadData("DC1");
             //    statusScrollerData data = new statusScrollerData();
               // futureLoadClientSummary("DC1","2014-06-05",data);
                 //System.out.println(data.getOrderSummaryByClient().size());

              /*  for (statusOrderSummary sos:data.getOrderSummaryByClient()){
                    System.out.println(sos.getItemGroup1());
                    System.out.println(sos.getOrders());
                    System.out.println(sos.getOrdersLeftToShip());

                }
                 for (statusOrderSummary sos:data.getOrderSummaryByMethod()){
                     System.out.println(sos.getItemGroup1());
                     System.out.println(sos.getOrders());
                     System.out.println(sos.getOrdersLeftToShip());

                 }*/
                 for (statusOrderSummary sos:data.getFutureOrderSummaryByClient()){
                     System.out.println(sos.getItemGroup1());
                     System.out.println(sos.getOrders());
                     System.out.println(sos.getOrdersLeftToShip());

                 }
             } catch (Exception e){

                 e.printStackTrace();
             }

    }
    public static String addMessage(String priority,String startDate, String endDate, String message, String facility)throws Exception{
          String added = "";

        String sql = "insert into wMessages (priority,startDate,endDate,message,facility) OUTPUT INSERTED.id values (:priority,:startDate,:endDate,:message,:facility)" +
                ";";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("priority",priority);
        q.setParameter("startDate",startDate);
        q.setParameter("endDate",endDate);
        q.setParameter("message",message);
        q.setParameter("facility",facility);
        List results = q.list();
        if(results.size()>0){
            HibUtils.commit(HibernateSession.currentSession());
            System.out.println(results.get(0));
            added = results.get(0).toString();
        }    else{
            throw new Exception("Error adding record, please try again");
        }
        return added;
    }
    public static boolean deleteMessage(String id) throws Exception{
        boolean deleted = false;
                String sql = "delete from wMessages where id = :id";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("id",id);
        int results = q.executeUpdate();

        if (results > 0){
            HibUtils.commit(HibernateSession.currentSession());
            deleted = true;
        }
        return deleted;
    }
    public static boolean updateMessageRecord(String id, int column, String value ) throws Exception{
        boolean updated = false;
                 String sql = "";
        switch (column){
            case 2:
                sql = "update wMessages set priority = :value where id = :id";
                break;
            case 3:
                sql = "update wMessages set startDate = :value where id = :id";
                break;
            case 4:
                sql = "update wMessages set endDate = :value where id = :id";
                break;
            case 1:
                sql = "update wMessages set message = :value where id = :id";
                break;
            case 5:
                sql = "update wMessages set facility = :value where id = :id";
                break;
            default:
                return updated;
        }
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("value",value);
        q.setParameter("id",id);
        int results = q.executeUpdate();
        if (results > 0 ) {
            updated = true;
            HibUtils.commit(HibernateSession.currentSession());
        }

        return updated;
    }
    public static JSONscrollerMessageBean loadTableData(){
          JSONscrollerMessageBean smb = new JSONscrollerMessageBean();
           List<scrollerMessageBean> theList = new ArrayList<scrollerMessageBean>();

        try{
           String sql = "select id,facility,priority,StartDate,endDate,message from wMessages";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List resulst = q.list();
            if(resulst.size()>0){
                for (Object row:resulst){
                    Object[] data = (Object[]) row;
                    scrollerMessageBean bean = new scrollerMessageBean();
                    bean.setId(data[0].toString());
                    bean.setFacility(data[1].toString());
                    bean.setPriority(Integer.parseInt(data[2].toString()));
                    bean.setStart(data[3].toString());
                    bean.setEnd(data[4].toString());
                    bean.setMessage(data[5].toString());
                theList.add(bean);
                }
                smb.setData(theList);
            } else{
                smb.setError("WE have no messages to load");
            }




        }catch (Exception e){
            e.printStackTrace();
            smb.setError(e.getMessage());
        }

        return smb;
    }
    private static String getDateLoadedString(){
        SimpleDateFormat dff = new SimpleDateFormat("HH:mm MM-dd-yyyy");
        Calendar cal = Calendar.getInstance();
        return dff.format(cal.getTime());
    }
    private static String getToday(){
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return dff.format(cal.getTime());
    }
    private static String getTomorrow(){
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
        switch(cal.get(Calendar.DAY_OF_WEEK)){
            case 1:
                System.out.println("1");
                cal.add(Calendar.DATE, 2);
                break;
            case 7:
                System.out.println("7");
                cal.add(Calendar.DATE,3);
                break;
            default:
                System.out.println("default");
                cal.add(Calendar.DATE,1);
                break;
        }

        System.out.println(dff.format(cal.getTime()));
         return dff.format(cal.getTime());
    }
    public static statusScrollerData loadData(String facility) throws Exception{
        System.out.println("Getting data for :" + facility);
      statusScrollerData data = new statusScrollerData();

            loadClientSummary(facility,getTomorrow(),data);
        System.out.println("This is the count");
       System.out.println(data.getSumClientCount());
             loadMethodSummary(facility,getTomorrow(),data);
              futureLoadClientSummary(facility,getTomorrow(),data);
                        data.setAnnounceList(getAnnouncements(facility));
        data.setLoadedDate(getDateLoadedString());
        return data;

    }
    private static List<String> getAnnouncements(String facility) throws Exception {
        List<String> announce = new ArrayList<String>();
                 String sql = "select message from wMessages where facility = :facility and startDate <= :today and endDate > :today";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        q.setParameter("today",getToday());
         List results = q.list();
        if (results.size()>0){
            for (Object data : results){
                announce.add(data.toString());
            }
        }
        return announce;
    }
    private static void futureLoadClientSummary(String facility, String date,statusScrollerData sum) throws Exception{
        System.out.println("We are doing future with date : "+date);
        List<statusOrderSummary> summary = new ArrayList<statusOrderSummary>();
        String sql = "with thedata(company_name, group_name, carr_service, packsShipped, order_id, shipped,picked, order_num, ship_country, post_date, name, SLA, shipping_hold, wh_hold, Lines, Units,Packed)\n" +
                "As\n" +
                "(\n" +
                "select  company_name, isnull(group_name,''),\n" +
                "  isnull(carr_service,'LTL') as carr_service, \n" +
                " CASE WHEN max(o.ship_packs)>0 THEN 1 ELSE 0 END,  \n" +
                " order_id\n" +
                " , CASE WHEN max(o.shipped_on) = convert(smalldatetime,convert(varchar,getdate(),101)) THEN 1 else 0 END, \n" +
                "case when max(pick_status)=2 then 1 else 0 end,\n" +
                " o.order_num, \n" +
                " ship_country, \n" +
                " o.post_date, \n" +
                " ISNULL(ship_first_name ,'')+' '+isnull(ship_last_name,''),\n" +
                "ISNULL(max(case when patindex('%:%', customer_vendor_no)>0 THEN \n" +
                "ltrim(substring(customer_vendor_no,patindex('%: %', customer_vendor_no)+1,99)) ELSE\n" +
                " customer_vendor_no END),'2000-01-01 00:00:00') as [SLA Ship By] ,\n" +
                "max(is_on_shipping_hold+ISNULL(is_on_wh_hold,0)),\n" +
                "case when isnull(max(is_on_wh_hold),0)=1 THEN max(wh_hold_reason) ELSE '' END as 'wh_hold'\n" +
                ",count(distinct(line_item_id)) as 'Lines', sum(quantity_actual) as 'Units',\n" +
                "case when pack_end is null  then 0 else 1 end as 'Packed'\n" +
                "from owd_order o (NOLOCK) \n" +
                "left outer join package_order po (NOLOCK) on order_id=owd_order_fkey and po.is_void=0\n" +
                "join owd_line_item li (NOLOCK) on li.order_fkey=o.order_id and quantity_actual>0 and li.is_parent=0 and li.inventory_num not like 'KIT-%' and li.inventory_num not like 'KITITEM%'\n" +
                "join owd_client (NOLOCK) on client_id=o.client_fkey and  o.facility_code = :facility and is_active=1\n" +
                "left outer join owd_order_ship_holds h (NOLOCK) on order_id=h.order_fkey and is_on_wh_hold=1\n" +
                "join owd_order_ship_info (NOLOCK) \n" +
                "left outer join ( (select distinct method_code as 'methodname', pickup_time,cutoff_time from owd_ship_methods (NOLOCK) ) \n" +
                "union (select distinct old_carrier_code  as 'methodname',\n" +
                "pickup_time,cutoff_time from owd_ship_methods  (NOLOCK) where old_carrier_code <> '') ) as methods \n" +
                "  on methodname=carr_service_ref_num  \n" +
                "on order_id=owd_order_ship_info.order_fkey \n" +
                "join shipdays (NOLOCK) \n" +
                "on shipdate=convert(datetime,convert(varchar,o.post_date,101)) \n" +
                "left outer join owd_ship_cutoffs (NOLOCK) \n" +
                "on o.client_fkey=owd_ship_cutoffs.client_fkey and o.is_void=0 and carr_service_ref_num=ship_method\n" +
                " where  customer_vendor_no is not null and facility_code = :facility and (o.order_status='At Warehouse' and o.is_void=0) or (o.shipped_on=convert(smalldatetime,convert(varchar,getdate(),101)))\n" +
                " group by company_name,group_name, \n" +
                " isnull(carr_service,'LTL'), order_id, o.order_num, \n" +
                " ship_country, o.shipped_on,o.pick_status,\n" +
                " o.post_date, pack_end,\n" +
                " isnull(ship_first_name,'')+' '+ISNULL(ship_last_name,'')\n" +
                "\n" +
                "\n" +
                ")\n" +
                "\n" +
                "select company_name, group_name, count(order_id) as orders, sum(shipped) as shippedOrders, count(order_id)  - sum(shipped) as leftToShip, sum(packed) as packedOrders,\n" +
                "sum(picked) as pickedOrders,\n" +
                "sum(case when picked = 0 and shipped = 0 THEN 1 ELSE 0 END) as Unpicked,\n" +
                "sum(case when packed = 1 and shipped = 0 THEN 1 ELSE 0 END) as packedUnshipped\n" +
                "  from thedata where shipping_hold = 0 and  sla > :date  \n" +
                "group by company_name, group_name \n" +
                "having sum(shipped) < count(order_id)\n" +
                "order by leftToShip desc";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);

        q.setParameter("facility",facility);
        q.setParameter("date",date);
       // System.out.println(q.getQueryString());
        //System.out.println(q.toString());
        List results = q.list();
        if  (results.size()>0){
            for(Object row:results){
                Object[] data = (Object[]) row;
                statusOrderSummary sos = new statusOrderSummary();
                sos.setItemGroup1(data[0].toString());
                sos.setItemGroup2(data[1].toString());
                sos.setOrders(Integer.parseInt(data[2].toString()));
                sos.setShippedOrders(Integer.parseInt(data[3].toString()));
                sos.setOrdersLeftToShip(Integer.parseInt(data[4].toString()));
                sum.setFutureSumClientCount(sum.getFutureSumClientCount()+Integer.parseInt(data[4].toString()));
                sos.setPackedOrders(Integer.parseInt(data[5].toString()));
                sos.setPickedOrders(Integer.parseInt(data[6].toString()));
                sos.setUnpickedOrders(Integer.parseInt(data[7].toString()));
                sos.setPackedUnshippedOrders(Integer.parseInt(data[8].toString()));
                summary.add(sos);
            }
        } else{
            System.out.println("We are done!! do soemthign");
        }


        System.out.println(summary.size());
       sum.setFutureOrderSummaryByClient( summary);


    }
    private static void loadClientSummary(String facility, String date,statusScrollerData sum) throws Exception{
        System.out.println("Doing client summary with date: "+ date);
            List<statusOrderSummary> summary = new ArrayList<statusOrderSummary>();
             String sql = "with thedata(company_name, group_name, carr_service, packsShipped, order_id, shipped,picked, order_num, ship_country, post_date, name, SLA, shipping_hold, wh_hold, Lines, Units,Packed)\n" +
                     "As\n" +
                     "(\n" +
                     "select  company_name, isnull(group_name,''),\n" +
                     "  isnull(carr_service,'LTL') as carr_service, \n" +
                     " CASE WHEN max(o.ship_packs)>0 THEN 1 ELSE 0 END,  \n" +
                     " order_id\n" +
                     " , CASE WHEN max(o.shipped_on) = convert(smalldatetime,convert(varchar,getdate(),101)) THEN 1 else 0 END, \n" +
                     "case when max(pick_status)=2 then 1 else 0 end,\n" +
                     " o.order_num, \n" +
                     " ship_country, \n" +
                     " o.post_date, \n" +
                     " ISNULL(ship_first_name ,'')+' '+isnull(ship_last_name,''),\n" +
                     "ISNULL(max(case when patindex('%:%', customer_vendor_no)>0 THEN \n" +
                     "ltrim(substring(customer_vendor_no,patindex('%: %', customer_vendor_no)+1,99)) ELSE\n" +
                     " customer_vendor_no END),'2000-01-01 00:00:00') as [SLA Ship By] ,\n" +
                     "max(is_on_shipping_hold+ISNULL(is_on_wh_hold,0)),\n" +
                     "case when isnull(max(is_on_wh_hold),0)=1 THEN max(wh_hold_reason) ELSE '' END as 'wh_hold'\n" +
                     ",count(distinct(line_item_id)) as 'Lines', sum(quantity_actual) as 'Units',\n" +
                     "case when pack_end is null  then 0 else 1 end as 'Packed'\n" +
                     "from owd_order o (NOLOCK) \n" +
                     "left outer join package_order po (NOLOCK) on order_id=owd_order_fkey and po.is_void=0\n" +
                     "join owd_line_item li (NOLOCK) on li.order_fkey=o.order_id and quantity_actual>0 and li.is_parent=0 and li.inventory_num not like 'KIT-%' and li.inventory_num not like 'KITITEM%'\n" +
                     "join owd_client (NOLOCK) on client_id=o.client_fkey and  o.facility_code=:facility and is_active=1\n" +
                     "left outer join owd_order_ship_holds h (NOLOCK) on order_id=h.order_fkey and is_on_wh_hold=1\n" +
                     "join owd_order_ship_info (NOLOCK) \n" +
                     "left outer join ( (select distinct method_code as 'methodname', pickup_time,cutoff_time from owd_ship_methods (NOLOCK) ) \n" +
                     "union (select distinct old_carrier_code  as 'methodname',\n" +
                     "pickup_time,cutoff_time from owd_ship_methods  (NOLOCK) where old_carrier_code <> '') ) as methods \n" +
                     "  on methodname=carr_service_ref_num  \n" +
                     "on order_id=owd_order_ship_info.order_fkey \n" +
                     "join shipdays (NOLOCK) \n" +
                     "on shipdate=convert(datetime,convert(varchar,o.post_date,101)) \n" +
                     "left outer join owd_ship_cutoffs (NOLOCK) \n" +
                     "on o.client_fkey=owd_ship_cutoffs.client_fkey and o.is_void=0 and carr_service_ref_num=ship_method\n" +
                     " where  customer_vendor_no is not null and facility_code=:facility and (o.order_status='At Warehouse' and o.is_void=0) or (o.shipped_on=convert(smalldatetime,convert(varchar,getdate(),101)))\n" +
                     " group by company_name,group_name, \n" +
                     " isnull(carr_service,'LTL'), order_id, o.order_num, \n" +
                     " ship_country, o.shipped_on,o.pick_status,\n" +
                     " o.post_date, pack_end,\n" +
                     " isnull(ship_first_name,'')+' '+ISNULL(ship_last_name,'')\n" +
                     "\n" +
                     "\n" +
                     ")\n" +
                     "\n" +
                     "select company_name, group_name, count(order_id) as orders, sum(shipped) as shippedOrders, count(order_id)  - sum(shipped) as leftToShip, sum(packed) as packedOrders,\n" +
                     "sum(picked) as pickedOrders,\n" +
                     "sum(case when picked = 0 and shipped = 0 THEN 1 ELSE 0 END) as Unpicked,\n" +
                     "sum(case when packed = 1 and shipped = 0 THEN 1 ELSE 0 END) as packedUnshipped\n" +
                     "  from thedata where shipping_hold = 0 and sla < :date  \n" +
                     "group by company_name, group_name \n" +
                     "having sum(shipped) < count(order_id)\n" +
                     "order by leftToShip desc";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        q.setParameter("date",date);
        List results = q.list();
        if  (results.size()>0){
          for(Object row:results){
              Object[] data = (Object[]) row;
              statusOrderSummary sos = new statusOrderSummary();
              sos.setItemGroup1(data[0].toString());
              sos.setItemGroup2(data[1].toString());
              sos.setOrders(Integer.parseInt(data[2].toString()));
              sos.setShippedOrders(Integer.parseInt(data[3].toString()));
              sos.setOrdersLeftToShip(Integer.parseInt(data[4].toString()));
              sum.setSumClientCount(sum.getSumClientCount() + Integer.parseInt(data[4].toString()));
              sos.setPackedOrders(Integer.parseInt(data[5].toString()));
              sos.setPickedOrders(Integer.parseInt(data[6].toString()));
              sos.setUnpickedOrders(Integer.parseInt(data[7].toString()));
              sos.setPackedUnshippedOrders(Integer.parseInt(data[8].toString()));
               summary.add(sos);
          }
        } else{
            System.out.println("We are done!! do soemthign");
        }

        sum.setOrderSummaryByClient(summary);




    }

    private static void loadMethodSummary(String facility, String date, statusScrollerData sum) throws Exception{
        System.out.println("Doing method summary with date : "+date);
        List<statusOrderSummary> summary = new ArrayList<statusOrderSummary>();
        String sql = "with thedata(company_name, group_name, carr_service, packsShipped, order_id, shipped,picked, order_num, ship_country, post_date, name, SLA, shipping_hold, wh_hold, Lines, Units,Packed)\n" +
                "As\n" +
                "(\n" +
                "select  company_name, isnull(group_name,''),\n" +
                "  isnull(carr_service,'LTL') as carr_service, \n" +
                " CASE WHEN max(o.ship_packs)>0 THEN 1 ELSE 0 END,  \n" +
                " order_id\n" +
                " , CASE WHEN max(o.shipped_on) = convert(smalldatetime,convert(varchar,getdate(),101)) THEN 1 else 0 END, \n" +
                "case when max(pick_status)=2 then 1 else 0 end,\n" +
                " o.order_num, \n" +
                " ship_country, \n" +
                " o.post_date, \n" +
                " ISNULL(ship_first_name ,'')+' '+isnull(ship_last_name,''),\n" +
                "ISNULL(max(case when patindex('%:%', customer_vendor_no)>0 THEN \n" +
                "ltrim(substring(customer_vendor_no,patindex('%: %', customer_vendor_no)+1,99)) ELSE\n" +
                " customer_vendor_no END),'2000-01-01 00:00:00') as [SLA Ship By] ,\n" +
                "max(is_on_shipping_hold+ISNULL(is_on_wh_hold,0)),\n" +
                "case when isnull(max(is_on_wh_hold),0)=1 THEN max(wh_hold_reason) ELSE '' END as 'wh_hold'\n" +
                ",count(distinct(line_item_id)) as 'Lines', sum(quantity_actual) as 'Units',\n" +
                "case when pack_end is null  then 0 else 1 end as 'Packed'\n" +
                "from owd_order o (NOLOCK) \n" +
                "left outer join package_order po (NOLOCK) on order_id=owd_order_fkey and po.is_void=0\n" +
                "join owd_line_item li (NOLOCK) on li.order_fkey=o.order_id and quantity_actual>0 and li.is_parent=0 and li.inventory_num not like 'KIT-%' and li.inventory_num not like 'KITITEM%'\n" +
                "join owd_client (NOLOCK) on client_id=o.client_fkey and  o.facility_code=:facility and is_active=1\n" +
                "left outer join owd_order_ship_holds h (NOLOCK) on order_id=h.order_fkey and is_on_wh_hold=1\n" +
                "join owd_order_ship_info (NOLOCK) \n" +
                "left outer join ( (select distinct method_code as 'methodname', pickup_time,cutoff_time from owd_ship_methods (NOLOCK) ) \n" +
                "union (select distinct old_carrier_code  as 'methodname',\n" +
                "pickup_time,cutoff_time from owd_ship_methods  (NOLOCK) where old_carrier_code <> '') ) as methods \n" +
                "  on methodname=carr_service_ref_num  \n" +
                "on order_id=owd_order_ship_info.order_fkey \n" +
                "join shipdays (NOLOCK) \n" +
                "on shipdate=convert(datetime,convert(varchar,o.post_date,101)) \n" +
                "left outer join owd_ship_cutoffs (NOLOCK) \n" +
                "on o.client_fkey=owd_ship_cutoffs.client_fkey and o.is_void=0 and carr_service_ref_num=ship_method\n" +
                " where  customer_vendor_no is not null and facility_code=:facility and (o.order_status='At Warehouse' and o.is_void=0) or (o.shipped_on=convert(smalldatetime,convert(varchar,getdate(),101)))\n" +
                " group by company_name,group_name, \n" +
                " isnull(carr_service,'LTL'), order_id, o.order_num, \n" +
                " ship_country, o.shipped_on,o.pick_status,\n" +
                " o.post_date, pack_end,\n" +
                " isnull(ship_first_name,'')+' '+ISNULL(ship_last_name,'')\n" +
                "\n" +
                "\n" +
                ")\n" +
                "\n" +
                "select carr_service, sla, count(order_id) as orders, sum(shipped) as shippedOrders, count(order_id)  - sum(shipped) as leftToShip, sum(packed) as packedOrders,\n" +
                "sum(picked) as pickedOrders,\n" +
                "sum(case when picked = 0 and shipped = 0 THEN 1 ELSE 0 END) as Unpicked,\n" +
                "sum(case when packed = 1 and shipped = 0 THEN 1 ELSE 0 END) as packedUnshipped\n" +
                "  from thedata where shipping_hold = 0 and sla < :date  \n" +
                "group by carr_service, sla \n" +
                "having sum(shipped) < count(order_id)\n" +
                "order by sla ";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("facility",facility);
        q.setParameter("date",date);
        List results = q.list();
        if  (results.size()>0){
            for(Object row:results){
                Object[] data = (Object[]) row;
                statusOrderSummary sos = new statusOrderSummary();
                sos.setItemGroup1(data[0].toString());
                sos.setItemGroup2(data[1].toString());
                sos.setOrders(Integer.parseInt(data[2].toString()));
                sos.setShippedOrders(Integer.parseInt(data[3].toString()));
                sos.setOrdersLeftToShip(Integer.parseInt(data[4].toString()));
                sum.setSumMethodCount(sum.getSumMethodCount()+Integer.parseInt(data[4].toString()));
                sos.setPackedOrders(Integer.parseInt(data[5].toString()));
                sos.setPickedOrders(Integer.parseInt(data[6].toString()));
                sos.setUnpickedOrders(Integer.parseInt(data[7].toString()));
                sos.setPackedUnshippedOrders(Integer.parseInt(data[8].toString()));
                summary.add(sos);
            }
        } else{
            System.out.println("We are done!! do soemthign");
        }



       sum.setOrderSummaryByMethod(summary);


    }
}
