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
import com.owd.core.managers.PackingManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Query;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class domBackorderReleaseTest {



    @BeforeClass
    public static void beforeAllTestMethods() {
        System.out.println("Invoked once before all test methods");
        System.setProperty("com.owd.environment", "test");
    }

    @Test
    public void BAckorderAllTest(){
        //Set inventory to 0
        //Place order, verify it is on backorder and facility is Closest
        //SEt inventory to 1 in DC7
        //Release backorder verify it releases to DC7
        OwdInventory inv;
        String testSku = "DOMTestBO";
        try {
            OwdInventory inv2 = InventoryManager.getOwdInventoryForClientAndSku("55", testSku);

        }catch (Exception e){
            if(e.getMessage().contains("SKU values are case-sensitive.")){
                try {
                    InventoryManager.createInventoryItemForClient("55", testSku, "DOM test sku BO", "5", false, "", "", "");

                }catch (Exception ex){
                    ex.printStackTrace();
                    fail();
                }

            }
        }
        try {
            inv = InventoryManager.getOwdInventoryForClientAndSku("55", testSku);
            //Load Inventory 5 for DC1 5 for DC7
            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(inv.getInventoryId(), 55, 0, OWDUtilities.getSQLDateForToday(), "domTest1" + Calendar.getInstance().getTimeInMillis(), "DC7", HibernateSession.currentSession());
            HibernateSession.currentSession().flush();
            HibernateSession.closeSession();
            //Verify Inventory levels in DC's are correct.
            assertEquals("Starting Inventory Level for DC1 not correct", 0, InventoryManager.getStockLevelForFacility(inv.getInventoryId(), "DC7"));
            Order order1 = loadDC1ClosestAddressBackorderAll("55");
            order1.addLineItem(inv.getInventoryNum(),1,5.00f,5.00f,"Test Item","","");
            order1.setFacilityCode("CLOSEST");
            order1.recalculateBalance();

            String ref = order1.saveNewOrder(OrderUtilities.kHoldForPayment,false);
            System.out.println("The order ID: "+ref);
            OwdOrder oo = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(order1.orderID));
            assertEquals("Backorder (Active)",oo.getOrderStatus());
            assertEquals("CLOSEST",oo.getFacilityCode());

            //Set inventory to 1 in DC7
            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(inv.getInventoryId(), 55, 1, OWDUtilities.getSQLDateForToday(), "domTest1" + Calendar.getInstance().getTimeInMillis(), "DC7", HibernateSession.currentSession());
            HibernateSession.currentSession().flush();
            HibernateSession.closeSession();
            //Verify Inventory levels in DC's are correct.
            assertEquals("Starting Inventory Level for DC1 not correct", 1, InventoryManager.getStockLevelForFacility(inv.getInventoryId(), "DC7"));


            OrderUtilities.releaseBackorder(ConnectionManager.getConnection(),oo.getOrderId()+"", oo.getClientFkey()+"");
            HibernateSession.currentSession().refresh(oo);
            assertEquals("At Warehouse",oo.getOrderStatus());
            assertEquals("DC7",oo.getFacilityCode());



        }catch (Exception ex){
            ex.printStackTrace();
            fail();
        }

    }

    @Test
    public void PartialShipTest(){
        //Set inventory to 0
        //Place order, verify it is on backorder and facility is Closest
        //SEt inventory to 1 in DC7
        //Release backorder verify it releases to DC7
        OwdInventory inv;
        String testSku = "DOMTestBO2";
        try {
            OwdInventory inv2 = InventoryManager.getOwdInventoryForClientAndSku("55", testSku);

        }catch (Exception e){
            if(e.getMessage().contains("SKU values are case-sensitive.")){
                try {
                    InventoryManager.createInventoryItemForClient("55", testSku, "DOM test sku BO", "5", false, "", "", "");

                }catch (Exception ex){
                    ex.printStackTrace();
                    fail();
                }

            }
        }
        try {
            inv = InventoryManager.getOwdInventoryForClientAndSku("55", testSku);
            //Load Inventory 5 for DC1 5 for DC7
            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(inv.getInventoryId(), 55, 1, OWDUtilities.getSQLDateForToday(), "domTest1" + Calendar.getInstance().getTimeInMillis(), "DC7", HibernateSession.currentSession());
            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(inv.getInventoryId(), 55, 1, OWDUtilities.getSQLDateForToday(), "domTest1" + Calendar.getInstance().getTimeInMillis(), "DC1", HibernateSession.currentSession());
            HibernateSession.currentSession().flush();
            HibernateSession.closeSession();
            //Verify Inventory levels in DC's are correct.
            assertEquals("Starting Inventory Level for DC1 not correct", 1, InventoryManager.getStockLevelForFacility(inv.getInventoryId(), "DC7"));
            assertEquals("Starting Inventory Level for DC1 not correct", 1, InventoryManager.getStockLevelForFacility(inv.getInventoryId(), "DC1"));

            Order order1 = loadDC1ClosestAddressBackorderAll("55");
            order1.backorder_rule = OrderXMLDoc.kPartialShip;
            order1.addLineItem(inv.getInventoryNum(),2,5.00f,5.00f,"Test Item","","");
            order1.setFacilityCode("CLOSEST");
            order1.recalculateBalance();

            String ref = order1.saveNewOrder(OrderUtilities.kHoldForPayment,false);
            System.out.println("The order ID: "+ref);
            OwdOrder oo = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(order1.orderID));
            assertEquals("At Warehouse",oo.getOrderStatus());
            assertEquals("DC1",oo.getFacilityCode());

             assertEquals("Starting Inventory Level for DC1 not correct", 1, InventoryManager.getStockLevelForFacility(inv.getInventoryId(), "DC7"));


            PackingManager.packAndShip(Integer.parseInt(order1.orderID),true, 4, 0.00, "000000000000000", false);


            Query q = HibernateSession.currentSession().createSQLQuery("select order_id from owd_order where order_num = :orderNum");
            q.setParameter("orderNum",order1.backorder_order_num);
            List l = q.list();

            String backID =  l.get(0).toString();
            System.out.println("Backorder ID " + backID);

            OrderUtilities.releaseBackorder(ConnectionManager.getConnection(),backID+"", oo.getClientFkey()+"");
            OwdOrder backOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(backID));
            assertEquals("At Warehouse",backOrder.getOrderStatus());
            assertEquals("DC7",backOrder.getFacilityCode());



        }catch (Exception ex){
            ex.printStackTrace();
            fail();
        }

    }





    // Helper methods
    public Order  loadDC1ClosestAddressBackorderAll(String clientId) throws Exception{
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



        o.backorder_rule = OrderXMLDoc.kBackOrderAll;
        o.setShippingMethodName("Ground");
        // o.group_name = "Amazon";
        o.bill_cc_type = "CK";
        o.is_paid = 1;
        return o;
    }
}
