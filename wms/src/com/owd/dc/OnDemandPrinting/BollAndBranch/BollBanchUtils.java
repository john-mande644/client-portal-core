package com.owd.dc.OnDemandPrinting.BollAndBranch;

import com.owd.core.TimeStamp;
import com.owd.dc.OnDemandPrinting.DemandPrintingUtils;
import com.owd.dc.OnDemandPrinting.OnDemandInfoBean;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Query;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 7/13/14
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BollBanchUtils {

    public static void main(String[] args){
        try{
           OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,7455127);
            System.out.println(onDemandOrder(order));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean printGiftCard(String orderId, String printer) throws Exception{
        boolean good = false;
           OwdOrder order = (OwdOrder)HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(orderId));
           giftMessage message = new giftMessage();
        message.setMessage(order.getGiftMessage());
        DemandPrintingUtils.printBollBranchGiftCard(message,printer);
        good = true;

        return good;
    }
    public static OnDemandInfoBean isOnDemandOrderViaOrderNum(String orderNum) throws Exception{
        TimeStamp ts1 = new TimeStamp("Start");
        String sql = "from OwdOrder where orderNum = :orderNum";
        Query q = HibernateSession.currentSession().createQuery(sql);
        q.setString("orderNum",orderNum);

        OwdOrder order = (OwdOrder) q.list().get(0);



        ts1.print("end");
        return isOnDemandOrder(order);

    }
    public static OnDemandInfoBean isOnDemandOrder(OwdOrder order) throws Exception{
        OnDemandInfoBean ib;
        if(onDemandOrder(order)){
         ib = getInfo();
            ib.setOrderId(order.getOrderId().toString());
            ib.setOnDemand(true);
        }
        else{
          ib = new OnDemandInfoBean();
            ib.setOnDemand(false);
        }
            return ib;
    }
    private static OnDemandInfoBean getInfo(){
        OnDemandInfoBean ib = new OnDemandInfoBean();
        ib.setPackMessage("Please verify the Gift Card has been printed with the gift message.");
        ib.setPickMessage("This order needs to have gift message printed on card. Please take to appropriate place.");
        return ib;
    }
    private static boolean onDemandOrder(OwdOrder order) throws Exception{
        boolean ondemand = false;
        String clientFkey = order.getClientFkey().toString();
           if(clientFkey.equals("55")||clientFkey.equals("489")){
               System.out.println("Step on true");


               //check for right group
                if(clientFkey.equals("489") & !order.getGroupName().equals("bollandbranch")){
                    System.out.println("Not the right sneakpeeq code");
                                        return ondemand;
                }
               if(order.getGiftMessage().length()==0){
                   return ondemand;
               }

              //check for right sku
               if(clientFkey.equals("55")){
                   for(OwdLineItem li : order.getLineitems()){
                       if(li.getInventoryNum().equals("Ebay items")){
                            ondemand = true;
                           return ondemand;
                       }
                   }
               }
               if(clientFkey.equals("489")){
                   for(OwdLineItem li : order.getLineitems()){
                       if(li.getInventoryNum().equals("P142232")){
                           ondemand = true;
                           return ondemand;
                       }
                   }
               }



           }

        return ondemand;
    }
}
