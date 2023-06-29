package com.owd.core.business.client;

import com.owd.core.business.autoship.AutoShipFactory;
import com.owd.core.business.autoship.autoShipItem;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.hibernate.generated.OwdOrderAutoItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Mar 27, 2008
 * Time: 1:57:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShineResearchClientPolicy extends DefaultClientPolicy{
private final static Logger log =  LogManager.getLogger();
        List<autoShipItem> subscriptionItems = new ArrayList();

    public void addItemToList(String sku, float cost, int qty, String desc)
   {
       Iterator it = subscriptionItems.iterator();
       while(it.hasNext()
               )
       {
          autoShipItem item = (autoShipItem) it.next();
           if(sku.equals(item.getSku()) && cost==item.getPrice())
           {
               item.getQty();
               int oldqty = item.getQty();
               item.setQty(oldqty += qty);
               return;
           }
       }

       //not returned, so have new item

      autoShipItem newitem = new autoShipItem();
       newitem.setSku(sku);
       newitem.setPrice(cost);
       newitem.setQty(qty);
      newitem.setDescription(desc);
     subscriptionItems.add(newitem);


   }

     public void addLineItemToOrder(Order order, String sku, String description, String price, String quantity) throws Exception {
        log.debug("Doing add item xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        order.addLineItem(sku, quantity, price, "0.00", description, "", "");


    }






     public void saveNewOrder(Order order, boolean testing) throws Exception {

        if(order.containsSKU("relax1-sub")){
          Vector skus = order.skuList;
             log.debug("dkdkdkdkdkdkdkdkdkdkdkdkdkdkdkdkdkkkkkkkkkkkkkkk");


            for(int i=0;i<skus.size();i++){
                LineItem item = (LineItem) skus.elementAt(i);
                log.debug("tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
              if(item.client_part_no.contains("-sub")){
                  addItemToList(item.client_part_no,item.sku_price,(int)item.quantity_request,item.description);


              }

            }
             if(subscriptionItems.size()>0){
              for(autoShipItem it:subscriptionItems){
                order.deleteLineItemForSKU(it.getSku());
                 LineItem item = order.getLineItem(it.getSku().replaceAll("-sub",""),it.getQty()+"",it.getPrice()+"","0.00",it.getDescription(),"","");
                 item.insertedItem=true;
                  order.skuList.add(item);
              //  it.setSku(it.getSku().replaceAll("-sub",""));
              }

           }
           log.debug("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");

        }
        order.saveNewOrder(OrderUtilities.kRequirePayment, testing);
       log.debug("Should be good order checking for auto");
       if(subscriptionItems.size()>0){

           createSubscription(order,"6.95");

       }

    }

    public  void createSubscription(Order o, String shipCost) throws Exception {


            Session sess = HibernateSession.currentSession();
            OwdOrderAuto auto = AutoShipFactory.getNewAutoShip(383);
            auto.setBillName(o.getBillingContact().getName());
            auto.setBillPhone(o.getBillingContact().getPhone());
            auto.setBillEmail(o.getBillingContact().getEmail());
            auto.setBillAddressOne(o.getBillingAddress().address_one);
            auto.setBillAddressTwo(o.getBillingAddress().address_two);
            auto.setBillCity(o.getBillingAddress().city);
            auto.setBillState(o.getBillingAddress().state);
            auto.setBillZip(o.getBillingAddress().zip);
            auto.setBillCountry(o.getBillingAddress().country);

            auto.setShipName(o.getShippingContact().getName());
            auto.setShipPhone(o.getShippingContact().getPhone());
            auto.setShipEmail(o.getShippingContact().getEmail());
            auto.setShipAddressOne(o.getShippingAddress().address_one);
            auto.setShipAddressTwo(o.getShippingAddress().address_two);
            auto.setShipCity(o.getShippingAddress().city);
            auto.setShipState(o.getShippingAddress().state);
            auto.setShipZip(o.getShippingAddress().zip);
            auto.setShipCountry(o.getShippingAddress().country);
             auto.setShipMethod(o.getShippingInfo().carr_service);
            auto.setShipInterval(new Integer(30));
            auto.setRequirePayment(new Integer(1));
            //auto.setShipCost(new BigDecimal(o.total_shipping_cost));
            // auto.setShipMethod(o.getShippingInfo().carr_service);
          
            auto.setRequirePayment(new Integer(1));
            auto.setShipCost(new BigDecimal(shipCost));

            auto.setCreatedBy("OWD Manual Entry");
            auto.setCreated(Calendar.getInstance().getTime());
            auto.setStatus(new Integer(0));
            auto.setClientFkey(Integer.decode(o.clientID));
            auto.setCalendarUnit("day");
           OwdOrder source = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,new Integer(o.getID()));
        if(source!=null)
        {
          auto.getSourceOrders().add(source);
        }
            auto.setOrderSource(o.order_type);
           Calendar expected = Calendar.getInstance();


            expected.add(Calendar.DATE, 25);

            auto.setNextShipmentDate(expected.getTime());
            auto.setOrderSource(o.order_type);
            auto.setCcNum(o.cc_num);
            auto.setCcExpMon(new Integer(o.cc_exp_mon));
            auto.setCcExpYear(new Integer(o.cc_exp_year));
            sess.saveOrUpdate(auto);

        float subtotal = 0.00f;
        int units = 0;

            for(int i=0;i<subscriptionItems.size();i++)
            {
                autoShipItem item = (autoShipItem) subscriptionItems.get(i);

            OwdOrderAutoItem autoitem = new OwdOrderAutoItem();
            autoitem.setSku(item.getSku());
            autoitem.setUnitPrice(new BigDecimal(item.getPrice()));
            autoitem.setQuantity(item.getQty());

                units+=item.getQty();
                subtotal+= (item.getQty()*item.getQty());

            autoitem.setOwdOrderAuto(auto);
            auto.getOwdOrderAutoItems().add(autoitem);
            sess.save(autoitem);

            }






            sess.saveOrUpdate(auto);
          sess.flush();
      HibUtils.commit(sess);
            //  saveOrUpdateAutoShip(auto, "New subscription  created from import file " + "\r\n");
            //  log.debug("Created new subscription!   Status is " + AutoShipManager.getAutoShipStatus("" + auto.getStatus()));")


        }

    public float getShippingCost(Order order, ShippingOption shipOption) throws Exception {

           if(order.getShippingInfo().shipperAddress.isInternational())
           {
               return 24.95f;
           }

         return 6.95f;
    } 

   public boolean allowSalesTaxEditing(Order order) {
        return false;
    }
     public boolean allowUnitPriceEditing(Order order) {
        return false;
    }
    
   public float getSalesTaxValue(Order order) throws Exception {
        order.tax_pct = (float) 0.00f;
        order.shipping_taxable = true;
        float totalTax = 0.00f;

        if ("CA".equalsIgnoreCase(order.getShippingAddress().state) || "California".equalsIgnoreCase(order.getShippingAddress().state)) {
            order.tax_pct = (float) 0.085000;
        }
        if ("SD".equalsIgnoreCase(order.getShippingAddress().state) || "South Dakota".equalsIgnoreCase(order.getShippingAddress().state)) {
            order.tax_pct = (float) 0.060000;
        }


                totalTax += order.total_product_cost * order.tax_pct;



        

        if (totalTax < 0.01f) {
            order.tax_pct = 0.00f;
            totalTax = 0.00f;
        } else {
            totalTax += order.total_shipping_cost * order.tax_pct;
        }


        return totalTax;

    }
}
