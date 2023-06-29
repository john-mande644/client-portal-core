package com.owd.dc.warehouse.supplyTracking;

import com.owd.core.Mailer;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.dbUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.dc.HandheldUtilities;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventoryHistory;
import com.owd.hibernate.generated.OwdReceive;
import com.owd.hibernate.generated.OwdReceiveItem;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 9/18/12
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class supplyTrackingUtils {
    private static String getItemsSql = "SELECT\n" +
            "    dbo.supply_tracking.inventory_id,\n" +
            "    dbo.owd_inventory.inventory_num,\n" +
            "    dbo.owd_inventory.description,\n" +
            "    dbo.supply_tracking.email,\n" +
            "    dbo.supply_tracking.sort_order,\n" +
            "    dbo.supply_tracking.active,\n" +
            "    dbo.supply_tracking.threshold,\n" +
            "    dbo.supply_tracking.facility,\n" +
            "    dbo.supply_tracking.group_type,\n" +
            "    dbo.supply_tracking.count_type\n" +
            "FROM\n" +
            "    dbo.supply_tracking\n" +
            "INNER JOIN dbo.owd_inventory\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.supply_tracking.inventory_id = dbo.owd_inventory.inventory_id\n" +
            "    )\n" +
            "order by group_type, sort_order, inventory_num";

    public static String getNameforId(String id, Session sess) throws Exception{
        String sql = "select firstname + ' '+lastname from w_logins where cardnumber = :id";
           Query q = sess.createSQLQuery(sql);
        q.setParameter("id",id);
        List results = q.list();
        if (results.size()>0){
            return results.get(0).toString();

        }

        throw new Exception("Unable to find id");

    }
    public static List<supplyTrackingBean> loadItemsForGroup(String groupType, String facility, Session sess) throws Exception{
        System.out.printf("Group type %s: Facility = %s",groupType,facility);
        List<supplyTrackingBean> stbList = new ArrayList<supplyTrackingBean>();
                String sql = "select inventory_id from supply_tracking where group_type = :groupType and facility = :facility and active= 1 order by sort_order";
        Query q = sess.createSQLQuery(sql);
        q.setParameter("groupType",groupType);
        q.setParameter("facility",facility);
        List results = q.list();
        for (Object row:results){
          try {
               supplyTrackingBean stb = new supplyTrackingBean();
               stb.load(Integer.parseInt(row.toString()), sess);
               stbList.add(stb);
           }catch(Exception e){
               System.out.printf("This is the error: %s%n",e.getMessage());
           }
        }



        return stbList;
    }
     public static List<String> loadFacilities(Session sess){
         List<String> l = new ArrayList<String>();
        try{
            String sql = "select loc_code from owd_facilities where is_active = 1 order by loc_code";
            Query q = sess.createSQLQuery(sql);
            l = q.list();

        } catch(Exception e){
         e.printStackTrace();
        }
        return l;
     }
    public static List<String> loadEmails(Session sess){
        List<String> l = new ArrayList<String>();
        try{
            String sql = "select email from supply_tracking group by email order by email";
            Query q = sess.createSQLQuery(sql);
            l = q.list();

        } catch(Exception e){
         e.printStackTrace();
        }
        return l;
    }
    public static List<String> loadGroupTypes(Session sess, String facility){
           List<String> l = new ArrayList<String>();
           try{
               String sql = "select group_type from supply_tracking where facility = :facility group by group_type order by group_type";
               Query q = sess.createSQLQuery(sql);
               q.setParameter("facility",facility);
               l = q.list();

           } catch(Exception e){
            e.printStackTrace();
           }
           return l;
       }

    public static List<String> loadCountType(Session sess){
        List<String> l = new ArrayList<String>();
        try{
            String sql = "select count_type from supply_tracking group by count_type order by count_type";
            Query q = sess.createSQLQuery(sql);
            l = q.list();

        } catch(Exception e){
         e.printStackTrace();
        }
        return l;
    }
    public static JSONsupplyTrackingbean getTrackedItems(Session sess){
        List<supplyTrackingBean> items = new ArrayList<supplyTrackingBean>();
        JSONsupplyTrackingbean jsonbean = new JSONsupplyTrackingbean();
        try{

          Query q = sess.createSQLQuery(getItemsSql);
            List l = q.list();
            for(Object row: l){
                Object[] data = (Object[]) row;
              supplyTrackingBean stb = new supplyTrackingBean();


                          stb.setInventoryId((Integer) data[0]);
                         stb.setInventoryNum(data[1].toString());
                         stb.setDescription(data[2].toString());
                         stb.setEmail(data[3].toString());
                         stb.setSort_order( (Integer) data[4]);
                         stb.setActive((Integer) data[5] == 1);
                          stb.setThreshold((Integer) data[6]);
                          stb.setFacility(data[7].toString());
                          stb.setGroupType(data[8].toString());
                          stb.setCountType(data[9].toString());
                items.add(stb);
            }

        }catch (Exception e){
            jsonbean.setError(e.getMessage());
            e.printStackTrace();
        }

        jsonbean.setItems(items);
        return jsonbean;
    }
    public static void main(String args[]){
        try{
           /*   List<supplyTrackingBean> lis = loadItemsForGroup("Labels","DC1",HibernateSession.currentSession());
            for(supplyTrackingBean stb : lis){
                System.out.println(stb.getInventoryNum());
            }*/
           /* supplyTrackingBean stb = new supplyTrackingBean();
            stb.load(36351,HibernateSession.currentSession());
             sendThresholdEmailsifNeeded(stb,HibernateSession.currentSession());*/
            System.out.println(getNameforId("51",HibernateSession.currentSession()));
           // saveAdjust("danny","36351",2,HibernateSession.currentSession());
          //  List<String> l = loadEmails(HibernateSession.currentSession());
           // System.out.println(l);
            //System.out.println(loadCountType(HibernateSession.currentSession()));
            // System.out.println(loadGroupTypes(HibernateSession.currentSession()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static JSONcheckIdBean checkIdToAdd(String invId,Session sess){
        JSONcheckIdBean JCIB = new JSONcheckIdBean();
        OwdInventory owdI = new OwdInventory();
        owdI = (OwdInventory) sess.load(OwdInventory.class,Integer.parseInt(invId));
        Query q = sess.createSQLQuery("select inventory_id from supply_tracking where inventory_id = :invId");
        q.setParameter("invId",invId);
        List l = q.list();
        if (l.size()>0){
            JCIB.setError(owdI.getInventoryNum() + " has already been added here");
        }else{
            JCIB.setInvId(owdI.getInventoryId()+"");
            JCIB.setInventoryNum(owdI.getInventoryNum());
            JCIB.setDescription(owdI.getDescription());
        }



        return JCIB;
    }
    public static void sendThresholdEmailsifNeeded(supplyTrackingBean stb,Session sess,String facility){
        try{
           OwdInventory owdInv = (OwdInventory) sess.load(OwdInventory.class,stb.getInventoryId());
            int onHand = OrderUtilities.getAvailableInventory(owdInv.getInventoryId()+"",FacilitiesManager.getFacilityForCode(facility).getId());
            if (onHand<=stb.getThreshold()){
                System.out.println("Doing Threshold Email");
                String subject = "OWD Supply Tracking: "+stb.getInventoryNum() + " threshold alert";
                StringBuffer body = new StringBuffer();
                body.append("Our inventory System indicates that the following Sku has hit the threshold level that was set\r\n\r\n");
                body.append("Sku: ");
                body.append(stb.getInventoryNum());
                body.append("\r\nCurrent Inventory Level: ");
                body.append(onHand);
                body.append("\r\nThreshold Level: ");
                body.append(stb.getThreshold());
                Vector to = new Vector();
               String[] tos = stb.getEmail().split(",");
                for(String s: tos){
                    to.add(s);
                }

                Mailer.postMailMessage(subject,body.toString(), to,"robots@owd.com");

            }


        } catch(Exception e){
            e.printStackTrace();
        }



    }
    public static void saveAdjust(String user,String invId, int qty, Session sess,String facility) throws Exception{
           OwdReceive rcv = new OwdReceive();


               String DATE_FORMAT = "MM/dd/yy HH:mm:ss";
               java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
                 OwdInventory inv = HandheldUtilities.loadInv(Integer.valueOf(invId));
        //todo set to facility oh
          int onHand = OrderUtilities.getAvailableInventory(invId,FacilitiesManager.getFacilityForCode(facility).getId());
        if(onHand==0){

            throw new Exception("On hand is 0 you cannot remove any inventory. Contact your supervisor or IT");
        }
        if(onHand<qty){
            throw new Exception("This will take you negative naughty, naughty. Contact your supervisor or IT");
        }
               rcv.setBlNum("");

               rcv.setCarrier("");
               rcv.setCreatedBy(user);
               rcv.setCreatedDate((Calendar.getInstance().getTime()));
               rcv.setDriver("");
               rcv.setIsVoid(false);
               rcv.setNotes("Adjustment for Supplies used.");
               rcv.setOwdClient(inv.getOwdClient());
               rcv.setReceiveDate(Calendar.getInstance().getTime());
               rcv.setReceiveUser(user);
               rcv.setRefNum(invId + "/" + Calendar.getInstance().getTime());
               rcv.setTimeIn(Calendar.getInstance().getTime());
               rcv.setType("Adjustment");
               rcv.setFacilityCode(facility);
               sess.save(rcv);

               System.out.println("ReceiveId "+rcv.getReceiveId());

                 OwdReceiveItem ri = new OwdReceiveItem();
                   ri.setCreatedBy(user);
                   ri.setCreatedDate(Calendar.getInstance().getTime());
                   System.out.println("1");
                   ri.setDescription(inv.getDescription());
                   System.out.println("2");
                   ri.setInventoryNum(inv.getInventoryNum());
                   System.out.println("3");
                 /* if(af.getFormItems()[i].getUnusable().equals("yes")){
                      ri.setIsUnusable(true);

                   ri.setIsVoid(0);*/
                   System.out.println("4");
                   ri.setItemStatus("New");
                   System.out.println("5");
                   ri.setOwdInventory((OwdInventory) sess.load(OwdInventory.class, inv.getInventoryId()));
                   System.out.println("6");
                   if(qty==0){
                       throw new Exception("You must have a quanity for sku " + inv.getInventoryNum());
                   }
                   ri.setQuantity(new Integer(0-qty));
               ri.setIsVoid(0);
                   System.out.println("7");
                  /* ri.setReturnReason(af.getFormItems()[i].getReturnReason());*/
                   System.out.println("8");
                   ri.setOwdReceive(rcv);
                   sess.save(ri);
                   System.out.println("9");
                  // rcv.getOwdReceiveItems().add(ri);
                 OwdInventoryHistory invOh = new OwdInventoryHistory();
                                   invOh.setInventoryFkey(ri.getOwdInventory().getInventoryId());
                                   invOh.setQtyChange(ri.getQuantity());
                                   invOh.setReceiveItemFkey(ri.getReceiveItemId());
                                   invOh.setNote("supplyUtil.saveAdjust");
        invOh.setFacility(FacilitiesManager.getFacilityForCode(facility));
                                   sess.save(invOh);

               rcv.setTransactionNum("OWDADJ-"+ dbUtilities.getNextID(((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(), "Receive"));
               sess.save(rcv);
               System.out.println("10");
              sess.flush();
               System.out.println("11");
               com.owd.hibernate.HibUtils.commit(sess);
               sess.evict(invOh);



       }
}
