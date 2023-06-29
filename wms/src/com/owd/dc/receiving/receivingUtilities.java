package com.owd.dc.receiving;

import com.owd.core.business.asn.ASNFactory;
import com.owd.core.business.asn.ASNManager;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.SerialNumberManager;
import com.owd.hibernate.*;
import com.owd.dc.inventory.beans.invBean;
import com.owd.dc.receiving.forms.receiveForm;
import com.owd.dc.receiving.beans.countListBean;
import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.Receive;
import com.owd.hibernate.generated.ReceiveItem;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Mar 15, 2006
 * Time: 3:35:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class receivingUtilities {
    public static void main(String[] args){
        try{
            getReceive("20683","DC1");
            
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
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
         }
        return rcv;

      }

     public static String getReceiveItemFromInvId(String rcvId, String invId) throws Exception{
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
        return ib;
    }


    public static void updateReceiveItem(receiveForm rf, ReceiveItem ri) throws Exception{

        ri.setQtyDamage(Integer.parseInt(rf.getDamaged())+ri.getQtyDamage());
      ri.setQtyReceive(Integer.parseInt(rf.getCount())+ri.getQtyReceive());
        if(!rf.getWeight().equals("0")){
            System.out.println("Setting weight");
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(rf.getInvId()));
            inv.setWeightLbs(new Float(rf.getWeight()));
        }
        HibernateSession.currentSession().flush();
        com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());


    }

    public static void enterNewSerial(String serial,  String riId) throws Exception{
        ReceiveItem ri= (ReceiveItem) HibernateSession.currentSession().load(ReceiveItem.class, Integer.valueOf(riId));

       SerialNumberManager.addSerialNumberToReceiveItem(HibernateSession.currentSession(),ri,serial);
       HibernateSession.currentSession().flush();
       com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());

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
      return inv.getWeightLbs()+"";
  }

    
  public static boolean needWeightForReceiveItem(String itemId) throws Exception{
     boolean getWeight=false;

      OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, Integer.valueOf(itemId));
      if (inv.getWeightLbs().compareTo(new Float(0.0))==0){
         getWeight = true;
      }

      return getWeight;
  }

  public static List getReceivedItems(Receive rcv) throws Exception{

         List cl = new ArrayList();
      Iterator it = rcv.getReceiveItems().iterator();
        while(it.hasNext()){
           ReceiveItem ri = (ReceiveItem) it.next();
            if(ri.getQtyReceive()>0==true){
              countListBean clb = new countListBean();
                clb.setSku(ri.getInventoryFkey()+"");
                clb.setCount(ri.getQtyReceive()+"");
                clb.setDamage(ri.getQtyDamage()+"");
                cl.add(clb);
            }
         }
        return cl;

      }
  public static String getCountedItem(String rcvId)throws Exception{
      ReceiveItem ri = (ReceiveItem) HibernateSession.currentSession().load(ReceiveItem.class,Integer.valueOf(rcvId));
      return ri.getQtyReceive()+""; 
  }

  public static void saveObject(Session sess, Object o) throws Exception{
      sess.save(o);
      sess.flush();
      com.owd.hibernate.HibUtils.commit(sess);
      
  }


}
