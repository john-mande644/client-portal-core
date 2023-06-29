package com.owd.dc.warehouse.orderInfo;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.TimeStamp;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Dec 29, 2010
 * Time: 9:23:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class loadAction extends ActionSupport {
    private String orderNum;
    private String orderId="";
    private OwdOrder order;
    private List<packageOrderBean> thePackage = new ArrayList<packageOrderBean>();
    private pickBean pickbean;
    private List<OrderEvent> events;
    private List<pickErrorBean> putAways = new ArrayList<pickErrorBean>();


     public String execute(){
          try{
              if(orderNum.length()>5||orderId.length()>5){
                 thePackage = new ArrayList<packageOrderBean>();
                  order = new OwdOrder();
                  
                  loadThePackages(HibernateSession.currentSession(),orderId);
                  loadEvents();
                  loadPutAways();
              }
          } catch(Exception e){
               addActionError(e.getMessage());
          }



         return "success";

     }
    public void loadPutAways() throws Exception{
          String sql = " select packer, picker, inventory_id, inventory_num, qty, reported_time from w_pick_error where order_fkey = :id";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("id", order.getOrderId());
        List resulst = q.list();
        if(resulst.size()>0){
            for(Object row:resulst){
                Object[] data = (Object[])row;
                pickErrorBean peb = new pickErrorBean();
                peb.setPacker(data[0].toString());
                peb.setPicker(data[1].toString());
                peb.setInventory_id(data[2].toString());
                peb.setInventory_num(data[3].toString());
                peb.setQty(data[4].toString());
                peb.setReported_time(data[5].toString());
                putAways.add(peb);

            }
        }
    }
    public void loadEvents(){
        try{
        String hql = "from OrderEvent where orderFkey = :fkey";

        Query q = HibernateSession.currentSession().createQuery(hql);
        q.setParameter("fkey", order.getOrderId());

        events = q.list();
        }catch (Exception e){

        }
    }
    public String pickInfo(){
         try{
        if(orderId != null){
            System.out.println("doing pickbeanstuff");
         loadPickBean(HibernateSession.currentSession());



        } else{
           addActionError("Unable to load for blank id");
        }
         }catch(Exception e){
             addActionError(e.getMessage());
         }


        return "success";
    }
    public static void main(String[] args){
            try{
                loadAction l = new loadAction();
                Session sess = HibernateSession.currentSession();
              //  System.out.println(l.getPackerName("51",sess));
                l.orderNum = "19126071";
               // l.orderId= "4037655";

                l.loadPickBean(sess);
                System.out.println(l.pickbean.getPickBy());
                System.out.println(l.pickbean.getNubmerPicks());
              //l.loadThePackages(sess);
             //System.out.println(l.thePackage.get(0).getPackages().get(0).getIpAddress());
               //      System.out.println(l.thePackage.get(0).getPackages().get(0).getPackedLines().size());
        }  catch(Exception e){
              e.printStackTrace();
            }

    }
    private void loadPickBean(Session sess) {
        try{
          String   history_id ;
           String sql = "select *,datediff(second,start_pick_time,end_pick_time) from order_pick_history where order_id = :orderId";
            Query q = sess.createSQLQuery(sql);
q.setString("orderId",orderId);
            List results = q.list();
            pickbean = new pickBean();
            if (results.size()>0){
              Object data = results.get(0);
              Object[] row = (Object[]) data;
             history_id = row[0].toString();
                pickbean.setPickBy(row[2].toString());
                pickbean.setNubmerPicks(row[3].toString());
                pickbean.setStartTime(row[4].toString());
                pickbean.setEndTime(row[5].toString());
                pickbean.setReplenish(row[6].toString());
               pickbean.setPickTime(Integer.parseInt(row[8].toString())/60 + "");


                String itemsql = "SELECT\n" +
                        "    dbo.order_pick_item_history.first_scan_time,\n" +
                        "    dbo.order_pick_item_history.all_units_picked,\n" +
                        "    dbo.order_pick_item_history.inventory_id,\n" +
                        "    dbo.order_pick_item_history.units_picked,\n" +
                        "    dbo.order_pick_item_history.replenish_time,\n" +
                        "    dbo.owd_inventory.inventory_num\n" +
                        "FROM\n" +
                        "    dbo.order_pick_item_history\n" +
                        "INNER JOIN dbo.owd_inventory\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.order_pick_item_history.inventory_id = dbo.owd_inventory.inventory_id\n" +
                        "    )\n" +
                        "WHERE\n" +
                        "    dbo.order_pick_item_history.pick_history_id = :history";
                Query qq = sess.createSQLQuery(itemsql);
                qq.setString("history",history_id);
                List rr = qq.list();
                for (Object rows:rr){
                    pickItemsBean pib = new pickItemsBean();
                    Object[] datas = (Object[]) rows;
                    pib.setFirstScan(datas[0].toString());
                    pib.setLastScan(datas[1].toString());

                    pib.setInventoryId(datas[2].toString());
                    pib.setInventoryNum(datas[5].toString());
                    pib.setUnitsPicked(datas[3].toString());
                    pib.setReplenishTime(datas[4].toString());
                    pickbean.getPickItems().add(pib);
                    
                }




            } else{
                addActionError("Not pick History found");
            }


        } catch (Exception e){
            addActionError(e.getMessage());
        }




    }
    private List<packageLineBean> getPackedLines(String id, Session sess){
        List<packageLineBean> plbList = new ArrayList<packageLineBean>();
        String sql = "select owd_line_item_fkey, pack_quantity from package_line where package_fkey = :id";
        Query q = sess.createSQLQuery(sql);
        q.setString("id",id);
        List results = q.list();
        if (results.size()>0){
            for(Object row:results){
                Object[] data = (Object[]) row;
                packageLineBean plb = new packageLineBean();
                plb.setPackQuantity(getData(data[1]));
                plb.setLineItem((OwdLineItem) sess.load(OwdLineItem.class, Integer.decode(getData(data[0]))));
                plbList.add(plb);


            }


        }


        return plbList;
    }
   private List<packageBean> getPackages(String id, Session sess) throws Exception{
       List<packageBean> pbList = new ArrayList<packageBean>();
          String sql = "select id, owd_boxtypes_fkey, pack_barcode, weight_lbs, ship_time, order_track_fkey, ip_address, gss_shipment, depth, width, height " +
                  "from package where package_order_fkey = :idd";
       Query q = sess.createSQLQuery(sql);
       q.setString("idd",id);
       List results = q.list();
       if (results.size()>0){
           System.out.println("we have packages");
           int i = 1;
           for(Object row:results){
                              Object[] data = (Object[]) row;
               packageBean pb = new packageBean();
               pb.setPackageNumber(i+"");
               i = i+1;
               pb.setId(getData(data[0]));
               pb.setBox((OwdBoxtypes) sess.load(OwdBoxtypes.class, Integer.decode(getData(data[1]))));
               pb.setBoxId(getData(data[1]));
               pb.setPackBarcode(getData(data[2]));
               pb.setWeight(getData(data[3]));
               pb.setShipTime(getData(data[4]));
               if(getData(data[5]).length()>3){
                   pb.setTracking((OwdOrderTrack) sess.load(OwdOrderTrack.class, Integer.decode(getData(data[5]))));

               }
               pb.setIpAddress(getData(data[6]));
               if(getData(data[7]).equals("1")){
                   pb.setGSS(true);
               }  else{
                   pb.setGSS(false);
               }
               pb.setBoxDepth(getData(data[8]));
               pb.setBoxWidth(getData(data[9]));
               pb.setBoxHeight(getData(data[10]));
               pb.setPackedLines(getPackedLines(pb.getId(),sess));

               pbList.add(pb);


           }
       }





       return pbList;
   }
   private void loadPackageBean(Session sess) throws Exception{
          String sql = "select id, packer_ref, pack_start, pack_end, is_void, void_time, void_by, num_packs, packs_shipped, packType from package_order where owd_order_fkey = :fkey order by pack_start DESC";
       Query q = sess.createSQLQuery(sql);
       q.setString("fkey",order.getOrderId().toString());
       List results = q.list();
       if (results.size()>0){
           System.out.println("We are getting Packages");
          System.out.println(results.size());
           for (Object row : results){
               System.out.println("wft");
               packageOrderBean pob = new packageOrderBean();
               Object[] data = (Object[]) row;
               pob.setId(getData(data[0]));
               pob.setPackerRef(getData(data[1]));
               pob.setPackerName(getPackerName(getData(data[1]),sess));
               pob.setPackStart(getData(data[2]));
               pob.setPackEnd(getData(data[3]));
               System.out.println("packer" + pob.getPackerName());
               
               if (getData(data[4]).equals("1")){
                   pob.setVoid(true);
               }else{
                   pob.setVoid(false);
               }
               pob.setVoidTime(getData(data[5]));
               pob.setVoidBy(getPackerName(getData(data[6]),sess));
               pob.setNumberOfPackages(getData(data[7]));
               pob.setPackagesShipped(getData(data[8]));
               pob.setPackType(getData(data[9]));
               pob.setPackages(getPackages(pob.getId(),sess));
               System.out.println(pob.getPackEnd());
               System.out.println(pob.getId());
               thePackage.add(pob);
               
           }
       }


   }
    private String getData(Object o){
        String s = "";
        try{
             s = o.toString();
        }    catch(Exception e){

        }
        return s;
    }
    private void loadThePackages(Session sess,String orderId) throws Exception{
        try{
            TimeStamp ts1 = new TimeStamp("Start");
            if(orderId.length()==0){
                String sql = "execute sp_getOrderIdFromOrderNum :orderNum";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("orderNum", orderNum);

                order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(q.list().get(0).toString()));



             System.out.println(order.getBillAddressOne());
            } else{
                order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));

            }


             ts1.print("end");       
        } catch(Exception e){
            throw new Exception("Unable to load order for order Number "+ orderNum);
        }

           loadPackageBean(sess);


    }
    private String getPackerName(String id, Session sess) {
        try{
            String sql = "select firstname +' ' + lastname as name from w_logins where cardnumber = :id";
            Query q = sess.createSQLQuery(sql);
            q.setString("id",id);
            List results = q.list();
           
            if (results.size()>0){
                return results.get(0).toString();
            }
        } catch(Exception ex){
            ex.printStackTrace();
            return "Unable to get name";
        }

     return "Nothign found";

    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public OwdOrder getOrder() {
        return order;
    }

    public void setOrder(OwdOrder order) {
        this.order = order;
    }

    public List<packageOrderBean> getThePackage() {
        return thePackage;
    }

    public void setThePackage(List<packageOrderBean> thePackage) {
        this.thePackage = thePackage;
    }

    public pickBean getPickbean() {
        return pickbean;
    }

    public void setPickbean(pickBean pickbean) {
        this.pickbean = pickbean;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<OrderEvent> getEvents() {
        return events;
    }

    public void setEvents(List<OrderEvent> events) {
        this.events = events;
    }

    public List<pickErrorBean> getPutAways() {
        return putAways;
    }

    public void setPutAways(List<pickErrorBean> putAways) {
        this.putAways = putAways;
    }
}