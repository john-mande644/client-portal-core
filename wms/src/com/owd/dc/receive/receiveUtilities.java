package com.owd.dc.receive;

import com.owd.connectship.soap.ObjectFactory;
import com.owd.core.business.asn.ASNFactory;
import com.owd.core.business.asn.ASNManager;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.SerialNumberManager;
import com.owd.dc.inventory.beans.invBean;
import com.owd.dc.packing.AutoBatchPrinting.PackingABUtils;
import com.owd.dc.receive.beans.asnSearchBean;
import com.owd.dc.receive.beans.countInfoBean;
import com.owd.dc.receive.beans.receivedCountBean;
import com.owd.dc.receive.beans.searchBean;
import com.owd.dc.warehouse.vendorCompliance.labelsMaker.*;
import com.owd.dc.receiving.beans.countListBean;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.sql.Clob;
import java.util.*;

import static com.owd.core.managers.InventoryManager.checkAndSetBulkySkuForOwdInventory;


/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 15, 2006
 * Time: 3:35:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class receiveUtilities {
    private final static Logger log =  LogManager.getLogger();
    public static void main(String[] args){
        try{
           // getReceive("20683","DC1")

          //  System.out.println(getAsnItemsJson("83955"));
            System.out.println(getAsnSearchDataTablesJson("DC1"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Receive getReceive(String asnId,String facility) throws Exception{
         Receive rcv = null;
          Asn asn = (Asn) HibernateSession.currentSession().load(Asn.class, Integer.valueOf(asnId));

          if (asn == null) throw new Exception("ASN not found for ID " + asnId);
        if(!asn.getFacilityCode().equals(facility)){
            throw new Exception("You are not in the right warehouse to receive this ASN. ASN is marked for : "+asn.getFacilityCode());
        }
         Criteria crit = HibernateSession.currentSession().createCriteria(Receive.class);
            crit.setMaxResults(100);

            crit.add(Expression.eq("asn", asn));
            crit.add(Expression.eq("createdBy", "Pending"));
            List orderList = crit.list();
         if(orderList.size()>0) {
        rcv = (Receive) orderList.get(0);
        System.out.println("Pending receive found " + rcv.getId());
         } else{
         System.out.println("creating new pending receive for asn "+ asn.getId() );
          rcv = ASNFactory.getNewPendingReceive(asn);

        ASNManager.savePendingReceive(HibernateSession.currentSession(),rcv);
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
         }
        return rcv;

      }
    public static ReceiveItem getReceiveItemFromInvId(String rcvId, String invId) throws Exception{
        String itemId = null;
        Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvId));

        Criteria crit = HibernateSession.currentSession().createCriteria(ReceiveItem.class);
        crit.setMaxResults(10);
        crit.add(Expression.eq("receive", rcv));
        crit.add(Expression.eq("inventoryFkey", Integer.valueOf(invId)));
        System.out.println("1");
        List itemList = crit.list();
        System.out.println("2");
        if(itemList.size()==0){
            throw new Exception("Id: "+invId+" is not found for this ASN");
        }
        return (ReceiveItem) itemList.get(0);
    }
    public static String getReceiveItemIdFromInvId(String rcvId, String invId) throws Exception{
        String itemId = null;
        Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(rcvId));

        Criteria crit = HibernateSession.currentSession().createCriteria(ReceiveItem.class);
        crit.setMaxResults(10);
        crit.add(Expression.eq("receive", rcv));
        crit.add(Expression.eq("inventoryFkey", Integer.valueOf(invId)));
        System.out.println("1");
        List itemList = crit.list();
        System.out.println("2");
        if(itemList.size()==0){
            throw new Exception("Id: "+invId+" is not found for this ASN");
        }
        ReceiveItem ri = (ReceiveItem) itemList.get(0);
        System.out.println("3");
        itemId = ri.getId().toString();
        System.out.println("4");
        System.out.println("item id " +itemId);

        return itemId;
    }
    public static invBean loadInventoryBean(String invId,String facility)throws Exception{
        invBean ib = new invBean();
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,  Integer.valueOf(invId));
        ib.setDescription(inv.getDescription());
        ib.setInventoryId(inv.getInventoryId().toString());
        ib.setInventoryNum(inv.getInventoryNum());
        ib.setQtyOnHand(OrderUtilities.getAvailableInventory(invId, FacilitiesManager.getFacilityForCode(facility).getId())+"");
        if(inv.getUpcCode().length()>2||inv.getIsbnCode().length()>2){
            ib.setHasBarcode(true);
            if(inv.getUpcCode().length()>2)
                ib.setUpcCode(inv.getUpcCode());
        }
        ib.setNotes(inv.getNotes());
        return ib;
    }

    public static String getAsnSearchDataTablesJson(String facility){
        String s="";
        String sql = "execute sp_loadFacilityASNSearchDataTable :facility";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("facility",facility);
            List results = q.list();
            List<asnSearchBean> asns = new ArrayList<asnSearchBean>();
            for(Object row:results){
                Object[] data = (Object[]) row;
                asnSearchBean asb = new asnSearchBean();
                asb.setAsnId(data[0].toString());
                asb.setClientRef(data[1].toString());
                asb.setClientPo(data[2].toString());
                asb.setCompanyName(data[3].toString());
                asb.setExpectDate(data[4].toString());
                asb.setGroupName(data[5].toString());

                asb.setAllInventoryNum(PackingABUtils.convertClobToString((Clob) data[6]));
                asb.setAllTitle(PackingABUtils.convertClobToString((Clob) data[7]));
                asb.setAllDescription(PackingABUtils.convertClobToString((Clob) data[8]));

                asb.setCreatedDate(data[9].toString());

                asns.add(asb);



            }
            s = "{\"data\":"+jsonUtilties.getJsonFromObject(asns)+"}";

        }catch(Exception e){
            e.printStackTrace();
        }


        return s;
    }

    public static String getAsnItemsJson(String asnId){
        String s = "";
        try{
            Asn a = (Asn) HibernateSession.currentSession().load(Asn.class,Integer.parseInt(asnId));

       List<searchBean> ail = new ArrayList<searchBean>();
            for(AsnItem ai:a.getAsnItems()){
                searchBean sb = new searchBean();
                sb.setInventoryFkey(ai.getInventoryFkey()+"");
                sb.setInventory_num(ai.getInventoryNum());
                sb.setTitle(ai.getTitle());
                sb.setDescription(ai.getDescription());
                sb.setExpected(ai.getExpected()+"");
                ail.add(sb);


            }

            System.out.println(ail.size());
            s = "{\"data\":"+jsonUtilties.getJsonFromObject(ail)+"}";




        }catch (Exception e){
            s = "Error: " + e.getMessage();
            e.printStackTrace();
        }

     return s;


    }
    public static boolean updateLotCodeCount(int receiveItemId, String lotCode, String qty,String damaged) throws Exception{
        boolean good = false;
          String sql = "select id from owd_lot_receive_item where receive_item_fkey = :receiveItemId and lot_value = :lotCode";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("receiveItemId",receiveItemId);
        q.setParameter("lotCode",lotCode);
        List results = q.list();
        if(results.size()>0){
            String id = results.get(0).toString();
         log.debug("We found a match update qty");
          String updateSql = "update owd_lot_receive_item set qty_change = qty_change + :qty, qty_damage = qty_damage+:damage where id = :id";
            Query qu = HibernateSession.currentSession().createSQLQuery(updateSql);
            qu.setParameter("qty",qty);
            qu.setParameter("id",id);
            qu.setParameter("damage",damaged);
            int i = qu.executeUpdate();
            if(i>0) good = true;
        }else{
         log.debug("Nothing was found, create new record");
            String insert = "insert into owd_lot_receive_item (receive_item_fkey, lot_value,qty_change,qty_damage) values (:receiveItemId, :lotCode, :qty,:damaged);" ;
            Query qi = HibernateSession.currentSession().createSQLQuery(insert);
            qi.setParameter("receiveItemId",receiveItemId);
            qi.setParameter("lotCode",lotCode);
            qi.setParameter("qty",qty);
            qi.setParameter("damaged",damaged);
            int i = qi.executeUpdate();
            if(i>0) good = true;
        }
        return good;
    }

    public static boolean updateReceiveItem(ReceiveItem ri, String employeeId, String employeeName, String count, String damaged,String notes, String weight, String length, String width, String height ,String lotCode) throws Exception{
        //check for existing record, update or insert appropriately. Then set weight if needed.
       System.out.println("Update receive item");

        Boolean good = false;
        String checkSql = "select id from receive_item_user where receive_item_fkey = :itemFkey and timeclock_id = :employeeId";
        Query q = HibernateSession.currentSession().createSQLQuery(checkSql);
        q.setParameter("itemFkey",ri.getId());
        q.setParameter("employeeId",employeeId);
        List results = q.list();
        if (results.size()>0){
            System.out.println("We have found and item to update for receive");
            String id = results.get(0).toString();
            String updateSql = "update receive_item_user set qty_receive = qty_receive + :count, qty_damage = qty_damage + :damaged where id = :id";
            Query qu = HibernateSession.currentSession().createSQLQuery(updateSql);
            qu.setParameter("count",count);
            qu.setParameter("damaged",damaged);
            qu.setParameter("id",id);
            int i = qu.executeUpdate();
            if (i==1) good = true;

        }else{
            System.out.println("We are going to insert");
           String insertSql = "INSERT INTO receive_item_user ( receive_item_fkey, timeclock_id, name, qty_receive, notes, qty_damage) VALUES (:itemFkey, :employeeId, :employeeName, :count, :notes, :damaged);";
            Query qi = HibernateSession.currentSession().createSQLQuery(insertSql);
            qi.setParameter("itemFkey",ri.getId());
            qi.setParameter("employeeId",employeeId);
            qi.setParameter("employeeName",employeeName);
            qi.setParameter("count",count);
            qi.setParameter("notes",notes);
            qi.setParameter("damaged",damaged);
            int i = qi.executeUpdate();
            if(i==1) good = true;
        }

        if(
            !weight.equals("0") &&
            !length.equals("0") &&
            !width.equals("0") &&
            !height.equals("0")
        ){
            System.out.println("Setting weight");
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, ri.getInventoryFkey());
            if(
                    (null == inv.getWeightLbs() || inv.getWeightLbs() == 0.0f || inv.getWeightLbs().compareTo(new Float(weight))!=0) ||
                    (null == inv.getLength() || inv.getLength() == 0.0f || inv.getLength().compareTo(new Float(length))!=0) ||
                    (null == inv.getWidth() || inv.getWidth() == 0.0f || inv.getWidth().compareTo(new Float(width))!=0) ||
                    (null == inv.getHeight() || inv.getHeight() == 0.0f || inv.getHeight().compareTo(new Float(height))!=0)
                    ){
                inv.setDimensionsSetDate(new Date());
                log.debug("Setting Owd Dimensions");
                inv.setIsOwdDimensions(true);
            }
            inv.setWeightLbs(new Float(weight));
            inv.setLength(new Float((length)));
            inv.setWidth(new Float((width)));
            inv.setHeight(new Float((height)));
            inv.setCubicFeet((inv.getLength()/12) * (inv.getWidth()/12) * (inv.getHeight()/12));
        }
        if(good) {
            log.debug("checking for lots");
            if(null!= lotCode && lotCode.length()>0){
              good =   updateLotCodeCount(ri.getId(),lotCode,count,damaged);
            }
            if(good){
                System.out.println("We are good, commit changes");
                HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());
                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, ri.getInventoryFkey());
                checkAndSetBulkySkuForOwdInventory(inv);
            }

        }
return good;

    }

    public static void enterNewSerial(String serial,  String riId) throws Exception{
        ReceiveItem ri= (ReceiveItem) HibernateSession.currentSession().load(ReceiveItem.class, Integer.valueOf(riId));

       SerialNumberManager.addSerialNumberToReceiveItem(HibernateSession.currentSession(),ri,serial);
       HibernateSession.currentSession().flush();
       HibUtils.commit(HibernateSession.currentSession());

    }

    public static boolean allSerialRecorded(String riId, String count) throws Exception{
        boolean done = false;
           ReceiveItem ri= (ReceiveItem) HibernateSession.currentSession().load(ReceiveItem.class, Integer.valueOf(riId));
           if (count.equals(ri.getSerials().size()+"")){
               done = true;
           }
        System.out.println("Number serials recorded "+ri.getSerials().size());
        return done;

    }

    public static String getSkuWeight(String itemId) throws Exception{
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(itemId));
        if(inv.getWeightLbs()==null) return "0";
        return inv.getWeightLbs()+"";
    }

    public static String getSkuWidth(String itemId) throws Exception{
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(itemId));
        if(inv.getWidth()==null) return "0";
        return inv.getWidth()+"";
    }

    public static String getSkuLength(String itemId) throws Exception{
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(itemId));
        if(inv.getLength()==null) return "0";
        return inv.getLength()+"";
    }

    public static String getSkuHeight(String itemId) throws Exception{
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(itemId));
        if(inv.getHeight()==null) return "0";
        return inv.getHeight()+"";
    }


  public static boolean needWeightForReceiveItem(String itemId) throws Exception{
     boolean getWeight=false;

      OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(itemId));
      if (inv.getWeightLbs().compareTo(new Float(0.0))==0){
         getWeight = true;
      }

      return getWeight;
  }

    public static boolean needMeasurementsForReceiveItem(String itemId) throws Exception{
        boolean getMeasurements=false;
        System.out.println("needMeasurementsForReceiveItem() start");
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(itemId));
        if (inv.getWeightLbs().compareTo(new Float(0.0))==0){
            getMeasurements = true;
        }
        System.out.println("needMeasurementsForReceiveItem() weight");
        System.out.println("needMeasurementsForReceiveItem()" + inv.getWidth());
        if (null == inv.getWidth()){
            getMeasurements = true;
        }
        System.out.println("needMeasurementsForReceiveItem() width");
        if (null == inv.getLength()){
            getMeasurements = true;
        }
        System.out.println("needMeasurementsForReceiveItem() length");
        if (null == inv.getHeight()){
            getMeasurements = true;
        }
        System.out.println("needMeasurementsForReceiveItem() height");
        return getMeasurements;
    }

  public static List<receivedCountBean> getReceivedItems(Receive rcv) throws Exception{

         List<receivedCountBean> cl = new ArrayList<receivedCountBean>();
      Iterator it = rcv.getReceiveItems().iterator();
      for(ReceiveItem ri:rcv.getReceiveItems()){
          if(ri.getQtyReceive()>0|| ri.getQtyDamage()>0) {
              receivedCountBean rcb = new receivedCountBean();
              rcb.setInventoryId(ri.getInventoryFkey());
              rcb.setQtyReceive(ri.getQtyReceive());
              rcb.setQtyDamaged(ri.getQtyDamage());
              rcb.setNotes(ri.getNotes());
              cl.add(rcb);
          }

      }


        return cl;

      }
  public static countInfoBean getCountedItem(String receiveItemId, String employeeId)throws Exception{
      countInfoBean cib = new countInfoBean();
      ReceiveItem ri = (ReceiveItem) HibernateSession.currentSession().load(ReceiveItem.class,Integer.valueOf(receiveItemId));
      cib.setQtyReceiveTotal(ri.getQtyReceive());
      cib.setQtyDamagedTotal(ri.getQtyDamage());
      String sql = "select qty_receive, qty_damage from receive_item_user where receive_item_fkey = :receiveItemId and timeclock_id = :employeeId";
      Query q = HibernateSession.currentSession().createSQLQuery(sql);
      q.setParameter("receiveItemId",receiveItemId);
      q.setParameter("employeeId", employeeId);
      q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
      List results = q.list();
      System.out.println(results.size());
      if(results.size()>0) {
          Map info = (Map) results.get(0);
          cib.setQtyReceiveEmployee(Integer.parseInt(info.get("qty_receive").toString()));
          cib.setQtyDamagedEmployee(Integer.parseInt(info.get("qty_damage").toString()));
      }else{
          cib.setQtyReceiveEmployee(0);
          cib.setQtyDamagedEmployee(0);
      }

      return cib;
  }

  public static void saveObject(Session sess, Object o) throws Exception{
      sess.save(o);
      sess.flush();
      HibUtils.commit(sess);

  }


}
