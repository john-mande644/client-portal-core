package com.owd.core.business.order.distributedOrderManagement;

import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.business.order.ShippingInfo;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.InventoryManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdOrder;
import org.junit.Test;
import org.junit.BeforeClass;

import java.util.Calendar;

import static org.junit.Assert.*;





public class domOrdersTests {


    @BeforeClass
    public static void beforeAllTestMethods() {
        System.out.println("Invoked once before all test methods");
        System.setProperty("com.owd.environment", "test");
    }


    @Test
    public void DC1specificWarehouseTest(){
        OwdInventory inv;
        String testSku = "DOMTest1";
        try {
            OwdInventory inv2 = InventoryManager.getOwdInventoryForClientAndSku("55", testSku);

        }catch (Exception e){
            if(e.getMessage().contains("SKU values are case-sensitive.")){
                try {
                    InventoryManager.createInventoryItemForClient("55", testSku, "DOM test sku", "5", false, "", "", "");

                }catch (Exception ex){
                    ex.printStackTrace();
                    fail();
                }

            }
        }
        try {
            inv = InventoryManager.getOwdInventoryForClientAndSku("55", testSku);
            //Load Inventory 5 for DC1 5 for DC7
            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(inv.getInventoryId(), 55, 5, OWDUtilities.getSQLDateForToday(), "domTest1" + Calendar.getInstance().getTimeInMillis(), "DC1", HibernateSession.currentSession());
            HibernateSession.currentSession().flush();
            HibernateSession.closeSession();
            //Verify Inventory levels in DC's are correct.
            assertEquals("Starting Inventory Level for DC1 not correct", 5, InventoryManager.getStockLevelForFacility(inv.getInventoryId(), "DC1"));
            Order order1 = loadDC1ClosestAddress("55");
            order1.addLineItem(inv.getInventoryNum(),1,5.00f,5.00f,"Test Item","","");
            order1.setFacilityCode("DC1");
            order1.recalculateBalance();

            String ref = order1.saveNewOrder(OrderUtilities.kHoldForPayment,false);
            System.out.println("The order ID: "+ref);
            OwdOrder oo = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(order1.orderID));
            assertEquals("DC1",oo.getFacilityCode());
        }catch (Exception ex){
            ex.printStackTrace();
            fail();
        }

        }
    @Test
    public void DC1SingleUnitDC7MultiUnitCLOSESTTest(){
        /* Test Plan
        Make Sure test sku exists, create if it does not.
        Set quantity in DC1 to 5 set qty in DC7 to 5.
        Place order for 1 unit with Mobridge address.
        Order should post to DC1.

        Place second order for 5 to Mobridge address.
        Order should post to DC7

        Void orders so Test can be re-run
         */
        OwdInventory inv;
        String testSku = "DOMTest1";
        try {
            OwdInventory inv2 = InventoryManager.getOwdInventoryForClientAndSku("55", testSku);

        }catch (Exception e){
            if(e.getMessage().contains("SKU values are case-sensitive.")){
                try {
                    InventoryManager.createInventoryItemForClient("55", testSku, "DOM test sku", "5", false, "", "", "");

                }catch (Exception ex){
                    ex.printStackTrace();
                    fail();
                }

            }
        }
        try {
            inv = InventoryManager.getOwdInventoryForClientAndSku("55", testSku);
            //Load Inventory 5 for DC1 5 for DC7
            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(inv.getInventoryId(), 55, 5, OWDUtilities.getSQLDateForToday(), "domTest1" + Calendar.getInstance().getTimeInMillis(), "DC1", HibernateSession.currentSession());
            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(inv.getInventoryId(), 55, 5, OWDUtilities.getSQLDateForToday(), "domTest1" + Calendar.getInstance().getTimeInMillis(), "DC7", HibernateSession.currentSession());
            HibernateSession.currentSession().flush();
            HibernateSession.closeSession();
            //Verify Inventory levels in DC's are correct.
            assertEquals("Starting Inventory Level for DC1 not correct",5,InventoryManager.getStockLevelForFacility(inv.getInventoryId(),"DC1"));
            assertEquals("Starting Inventory Level for DC7 not correct",5,InventoryManager.getStockLevelForFacility(inv.getInventoryId(),"DC7"));
            Order order1 = loadDC1ClosestAddress("55");
            order1.addLineItem(inv.getInventoryNum(),1,5.00f,5.00f,"Test Item","","");
            order1.setFacilityCode("CLOSEST");
            order1.recalculateBalance();

          String ref = order1.saveNewOrder(OrderUtilities.kHoldForPayment,false);
            System.out.println("The order ID: "+ref);
            OwdOrder oo = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(order1.orderID));
            assertEquals("DC1",oo.getFacilityCode());


            // Do order for 5 units, should goto DC7
            Order order2 = loadDC1ClosestAddress("55");
            order2.addLineItem(inv.getInventoryNum(),5,5.00f,5.00f,"Test Item","","");
            order2.setFacilityCode("CLOSEST");
            order2.recalculateBalance();

            String ref2 = order2.saveNewOrder(OrderUtilities.kHoldForPayment,false);
            System.out.println("The order ID: "+ref2);
            OwdOrder ooo = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(order2.orderID));
            assertEquals("DC7",ooo.getFacilityCode());



        }catch (Exception e){
            e.printStackTrace();
            fail();
        }






    }

    // Helper methods
    public Order  loadDC1ClosestAddress(String clientId) throws Exception{
        Order o = new Order(clientId);
        ShippingInfo info = new ShippingInfo();
        Address shipa = new Address();
        shipa.company_name = "DOM Test";
        shipa.setAddress_one("10 1st Ave e");
        shipa.setCity("Mobridge");
        shipa.setState("SD");
        shipa.setZip("57601");
        info.setShippingAddress(shipa);
        Contact c = new Contact();
        c.setName("Test Order");
        info.setShippingContact(c);
        o.setShippingInfo(info);

        o.nameOverride = "Shipping Name";
        o.phoneOverride = "605-845-9999";
        o.address1Override = "10 1st Ave E";
        o.address2Override = "";
        o.stateOverride = "SD";
        o.cityOverride = "Mobridge";
        o.zipOverride = "57601";
        o.business_order = true;



        o.backorder_rule = OrderXMLDoc.kRejectBackOrder;
        o.setShippingMethodName("Ground");
        // o.group_name = "Amazon";
        o.bill_cc_type = "CK";
        o.is_paid = 1;
        return o;
    }
}
