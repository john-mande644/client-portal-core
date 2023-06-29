package com.owd.core.managers;


import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.*;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 27, 2007
 * Time: 10:00:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderManagementTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

    Session sess = null;
    Order order = null;
    //standard test client, ACME BBQ
    public static String kitItemSKU = "KIT-AUTO-TEST";
    public static String kitItemSKU2 = "KIT-AUTO-TEST2";

    public OrderManagementTest(String test) {
        super(test);
    }

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {

        log.debug("test setup!");


        ConnectionManager.databaseName = "OWD";
        sess = HibernateSession.currentSession();
        log.debug("got sess");

        order = new Order("112");  //standard test client, ACME BBQ
        Contact billContact = new Contact();
        Address billAddress = new Address();
        billContact.setName("Test test");
        billContact.setEmail("junit_test@owd.com");
        billContact.setPhone("6058457172");
        billAddress.address_one = "123 Main St";
        billAddress.city = "Mobridge";
        billAddress.state = "SD";
        billAddress.zip = "57601";
        order.setBillingAddress(billAddress);
        order.setBillingContact(billContact);
        order.getShippingInfo().setShippingAddress(billAddress);
        order.getShippingInfo().setShippingContact(billContact);
        order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
        String code = FacilitiesManager.getLocationCodeForClient(112);
        order.setFacilityCode("ALL".equals(code)?"DC1":code);
        order.setFacilityPolicy(order.getFacilityCode());
        log.debug("made order");
        Inventory item = Inventory.dbloadByPart( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),"TestItemOWD0001", "112");
        item.addToAvailabilityAtFacility(100,order.getFacilityCode());
        log.debug("item 1 done");
       //  Inventory vitem = new Inventory("112");
       // vitem.inventory_num="TestItemOWDVirtual0001";
       // vitem.is_active=1;
       // vitem.setAvailability(1000);
       // vitem.is_auto_inventory=1;
       // vitem.dbsave( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection());
        log.debug("item 2 done");
        createTestKits();
        log.debug("end setup!");

    }

      void createTestKits() throws Exception {

          log.debug("creating kits");
        Inventory kitItem = Inventory.dbloadByPart( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),kitItemSKU, "112");
           log.debug("creating kits 1");
          Inventory kitItem2 = Inventory.dbloadByPart( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),kitItemSKU2, "112");
           log.debug("creating kits 2");
        Inventory kitComponent1 = Inventory.dbloadByPart( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),"BOOK-TEST", "112");
           log.debug("creating kits 3");
        Inventory kitComponent2 = Inventory.dbloadByPart( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),"AUTO-STICKER-TEST", "112");
          

          log.debug("got kit records");
        PreparedStatement ps = HibernateSession.getPreparedStatement("delete from owd_inventory_required_skus where inventory_fkey=?");
        ps.setInt(1,kitItem.inventory_id);
        ps.execute();
          ps.setInt(1,kitItem2.inventory_id);
        ps.execute();
        ps = HibernateSession.getPreparedStatement("insert into owd_inventory_required_skus(inventory_fkey,req_inventory_fkey,req_inventory_quant) VALUES (?,?,1)");
        ps.setInt(1,kitItem.inventory_id);
          ps.setInt(2,kitComponent1.inventory_id);
        ps.execute();
          ps.setInt(1,kitItem2.inventory_id);
          ps.setInt(2,kitComponent1.inventory_id);
        ps.execute();
        ps = HibernateSession.getPreparedStatement("insert into owd_inventory_required_skus(inventory_fkey,req_inventory_fkey,req_inventory_quant) VALUES (?,?,1)");
           ps.setInt(1,kitItem.inventory_id);
          ps.setInt(2,kitComponent2.inventory_id);
        ps.execute();
          ps.setInt(1,kitItem2.inventory_id);
          ps.setInt(2,kitComponent2.inventory_id);
        ps.execute();
          log.debug("kits constructed");
          String code = FacilitiesManager.getLocationCodeForClient(112);
          order.setFacilityCode("ALL".equals(code)?"DC1":code);
          order.setFacilityPolicy(order.getFacilityCode());
        kitItem.setAvailabilityAtFacility(0,order.getFacilityCode());
        kitComponent1.setAvailabilityAtFacility(100,order.getFacilityCode());
        kitComponent2.setAvailabilityAtFacility(100,order.getFacilityCode());
          log.debug("kit availability set");
        sess.flush();
          log.debug("flushed");
        HibUtils.commit(sess);
    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {

        try {
            HibUtils.rollback(HibernateSession.currentSession());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }

    public static TestSuite suite() {
        return new TestSuite(OrderManagementTest.class);
    }

         public void testCreateOrderAllVirtualAsHeldBackorder() throws Exception {

             order.noVirtualOnlyOrders = false;
        order.backorder_rule=OrderXMLDoc.kHoldBackOrder;
        order.addLineItem("TestItemOWDVirtual0001", "1", "0.00", "0.00", "", "", "");

             log.debug("Order Reference: "+order.saveNewOrder(OrderUtilities.kHoldForPayment, true));

        OwdOrder dbOrder = saveOrder();
        assertEquals("Order not released or status incorrect", "On Hold", dbOrder.getOrderStatus());

        OwdLineItem line = (OwdLineItem) dbOrder.getLineitems().toArray()[0];
        assertEquals("SKU not saved as intended inventory part number", line.getInventoryNum(), "TestItemOWDVirtual0001");

        assertEquals("Quantity released is incorrect", 0, line.getQuantityActual().intValue());


        sess.flush();
        HibUtils.rollback(sess);


    }
         public void testCreateOrderAllVirtualAsActiveBackorder() throws Exception {

             order.noVirtualOnlyOrders = false;
        order.backorder_rule=OrderXMLDoc.kBackOrderAll;
        order.addLineItem("TestItemOWDVirtual0001", "1", "0.00", "0.00", "", "", "");

        order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

        OwdOrder dbOrder = saveOrder();
        assertEquals("Order not released or status incorrect", "Backorder (Active)", dbOrder.getOrderStatus());

        OwdLineItem line = (OwdLineItem) dbOrder.getLineitems().toArray()[0];
        assertEquals("SKU not saved as intended inventory part number", line.getInventoryNum(), "TestItemOWDVirtual0001");

        assertEquals("Quantity released is incorrect", 0, line.getQuantityActual().intValue());


        sess.flush();
        HibUtils.rollback(sess);


    }

    public void testCreateOrderAllVirtualAsRejectedBackorder() throws Exception {

             order.noVirtualOnlyOrders = false;
         order.backorder_rule=OrderXMLDoc.kRejectBackOrder;
         order.addLineItem("TestItemOWDVirtual0001", "1", "0.00", "0.00", "", "", "");

       // log.debug(":"+order.skuList);

         String ref = order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

            //    log.debug(":xxx:"+order.skuList);
         assertEquals("Order did not record proper exception for OOS","Insufficient inventory for SKU TestItemOWDVirtual0001".trim(),order.last_error.trim());


    //    log.debug(":"+order.skuList);
         assertEquals("SKU not saved as intended inventory part number", "TestItemOWDVirtual0001", ((LineItem)order.skuList.elementAt(0)).client_part_no);

         assertEquals("Quantity released is incorrect", 0, ((LineItem)order.skuList.elementAt(0)).quantity_actual);


         sess.flush();
         HibUtils.rollback(sess);


     }


    public void testCreateOrderAllInStock() throws Exception {


        order.addLineItem("TestItemOWD0001", "1", "0.00", "0.00", "", "", "");

        order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

        OwdOrder dbOrder = saveOrder();
        assertEquals("Order not released or status incorrect", "At Warehouse", dbOrder.getOrderStatus());

        assertEquals("Multiple Lines returned when only one added", 1, dbOrder.getLineitems().size());
        OwdLineItem line = (OwdLineItem) dbOrder.getLineitems().toArray()[0];
        assertEquals("SKU not saved as intended inventory part number", line.getInventoryNum(), "TestItemOWD0001");

        assertEquals("Quantity released is incorrect", 1, line.getQuantityActual().intValue());
        assertEquals("Quantity requested is incorrect", 1, line.getQuantityRequest().intValue());
        assertEquals("Quantity backordered is incorrect", 0, line.getQuantityBack().intValue());


        sess.flush();
        HibUtils.rollback(sess);


    }

    OwdOrder saveOrder() throws Exception, SQLException {
        sess.flush();
        HibUtils.commit(sess);

        OwdOrder dbOrder = (OwdOrder) sess.load(OwdOrder.class, new Integer(order.orderID));
        assertNotNull("Order not retrieved from database", dbOrder);
        return dbOrder;
    }


     public void testCreateOrderAllInStockOneUnitKit() throws Exception {


        createTestKits();

        order.addLineItem("KIT-AUTO-TEST", "2", "0.00", "0.00", "", "", "");

        order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

        OwdOrder dbOrder = saveOrder();
        assertEquals("Order not released or status incorrect", "At Warehouse", dbOrder.getOrderStatus());
        assertEquals("Kit items not added properly", 3, dbOrder.getLineitems().size());
        Iterator it = dbOrder.getLineitems().iterator();
        while (it.hasNext()) {

            OwdLineItem line = (OwdLineItem) it.next();
            String sku = line.getInventoryNum();

            if (sku.equals(kitItemSKU)) {
                assertEquals("Parent flag not set on kit SKU", line.getIsParent().intValue(), 1);
                assertNull("Parent line key set improperly on kit SKU", line.getParentKey());
            } else {
                assertEquals("Parent flag set on kit component SKU", line.getIsParent().intValue(), 0);
                assertNotNull("Parent line key set improperly on kit SKU", line.getParentKey());
            }

            assertEquals("Quantity released is incorrect", 2, line.getQuantityActual().intValue());
            assertEquals("Quantity requested is incorrect", 2, line.getQuantityRequest().intValue());
            assertEquals("Quantity backordered is incorrect", 0, line.getQuantityBack().intValue());
        }


    }
    public void testCreateOrderAllInStockTwoUnitKit() throws Exception {


        createTestKits();

        order.addLineItem("KIT-AUTO-TEST", "2", "0.00", "0.00", "", "", "");

        order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

        OwdOrder dbOrder = saveOrder();
        assertEquals("Order not released or status incorrect", "At Warehouse", dbOrder.getOrderStatus());
        assertEquals("Kit items not added properly", 3, dbOrder.getLineitems().size());
        Iterator it = dbOrder.getLineitems().iterator();
        while (it.hasNext()) {

            OwdLineItem line = (OwdLineItem) it.next();
            String sku = line.getInventoryNum();

            if (sku.equals(kitItemSKU)) {
                assertEquals("Parent flag not set on kit SKU", line.getIsParent().intValue(), 1);
                assertNull("Parent line key set improperly on kit SKU", line.getParentKey());
            } else {
                assertEquals("Parent flag set on kit component SKU", line.getIsParent().intValue(), 0);
                assertNotNull("Parent line key set improperly on kit SKU", line.getParentKey());
            }

            assertEquals("Quantity released is incorrect", 2, line.getQuantityActual().intValue());
            assertEquals("Quantity requested is incorrect", 2, line.getQuantityRequest().intValue());
            assertEquals("Quantity backordered is incorrect", 0, line.getQuantityBack().intValue());
        }


    }

    public void testCreateOrderSomeInStockKit() throws Exception {


        createTestKits();
        String code = FacilitiesManager.getLocationCodeForClient(112);
        order.setFacilityCode("ALL".equals(code)?"DC1":code);
        order.setFacilityPolicy(order.getFacilityCode());

        Inventory.dbloadByPart("BOOK-TEST", "112").setAvailabilityAtFacility(1,order.getFacilityCode());

        order.addLineItem("KIT-AUTO-TEST", "3", "0.00", "0.00", "", "", "");
        order.backorder_rule = OrderXMLDoc.kPartialShip;
        order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

        OwdOrder dbOrder = saveOrder();
        assertEquals("Order not released or status incorrect", "At Warehouse", dbOrder.getOrderStatus());
        assertEquals("Kit items not added properly", 3, dbOrder.getLineitems().size());
        Iterator it = dbOrder.getLineitems().iterator();
        while (it.hasNext()) {

            OwdLineItem line = (OwdLineItem) it.next();
            String sku = line.getInventoryNum();

            if (sku.equals(kitItemSKU)) {
                assertEquals("Parent flag not set on kit SKU", line.getIsParent().intValue(), 1);
                assertNull("Parent line key set improperly on kit SKU", line.getParentKey());
                assertEquals("Quantity released is incorrect", 1, line.getQuantityActual().intValue());
                assertEquals("Quantity requested is incorrect", 1, line.getQuantityRequest().intValue());
                assertEquals("Quantity backordered is incorrect", 2, line.getQuantityBack().intValue());
            } else {
                assertEquals("Parent flag set on kit component SKU", line.getIsParent().intValue(), 0);
                assertNotNull("Parent line key set improperly on kit SKU", line.getParentKey());
                assertEquals("Quantity released is incorrect", 1, line.getQuantityActual().intValue());
                assertEquals("Quantity requested is incorrect", 1, line.getQuantityRequest().intValue());
                assertEquals("Quantity backordered is incorrect", 2, line.getQuantityBack().intValue());
            }


        }


    }


    public void testCreateOrderNoneInStockKit() throws Exception {


        createTestKits();
        String code = FacilitiesManager.getLocationCodeForClient(112);
        order.setFacilityCode("ALL".equals(code)?"DC1":code);
        order.setFacilityPolicy(order.getFacilityCode());
        Inventory.dbloadByPart("BOOK-TEST", "112").setAvailabilityAtFacility(0,order.getFacilityCode());

        order.addLineItem("KIT-AUTO-TEST", "3", "0.00", "0.00", "", "", "");


        order.backorder_rule = OrderXMLDoc.kPartialShip;
        order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

        OwdOrder dbOrder = saveOrder();
        assertEquals("Order not released or status incorrect", "Backorder (Active)", dbOrder.getOrderStatus());
        assertEquals("Kit items not added properly", 3, dbOrder.getLineitems().size());
        Iterator it = dbOrder.getLineitems().iterator();
        while (it.hasNext()) {

            OwdLineItem line = (OwdLineItem) it.next();
            String sku = line.getInventoryNum();

            if (sku.equals(kitItemSKU)) {
                assertEquals("Parent flag not set on kit SKU", line.getIsParent().intValue(), 1);
                assertNull("Parent line key set improperly on kit SKU", line.getParentKey());
                assertEquals("Quantity released is incorrect", 0, line.getQuantityActual().intValue());
                assertEquals("Quantity requested is incorrect", 3, line.getQuantityRequest().intValue());
                assertEquals("Quantity backordered is incorrect", 0, line.getQuantityBack().intValue());
            } else {
                assertEquals("Parent flag set on kit component SKU", line.getIsParent().intValue(), 0);
                assertNotNull("Parent line key set improperly on kit SKU", line.getParentKey());
                assertEquals("Quantity released is incorrect", 0, line.getQuantityActual().intValue());
                assertEquals("Quantity requested is incorrect", 3, line.getQuantityRequest().intValue());
                assertEquals("Quantity backordered is incorrect", 0, line.getQuantityBack().intValue());
            }


        }


    }


 public void testReleaseOrderForcedBackorder() throws Exception {


        createTestKits();
     String code = FacilitiesManager.getLocationCodeForClient(112);
     order.setFacilityCode("ALL".equals(code)?"DC1":code);
     order.setFacilityPolicy(order.getFacilityCode());
        Inventory.dbloadByPart("TestItemOWD0001", "112").setAvailabilityAtFacility(0,order.getFacilityCode());
        Inventory.dbloadByPart("TestItemOWD0002", "112").setAvailabilityAtFacility(0,order.getFacilityCode());
        Inventory.dbloadByPart("TestItemOWD0003", "112").setAvailabilityAtFacility(0,order.getFacilityCode());

        order.addLineItem("TestItemOWD0001", "1", "0.00", "0.00", "", "", "");
        order.addLineItem("TestItemOWD0002", "1", "0.00", "0.00", "", "", "");
        order.addLineItem("TestItemOWD0003", "1", "0.00", "0.00", "", "", "");

        order.backorder_rule = OrderXMLDoc.kPartialShip;
        order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

        OwdOrder dbOrder = saveOrder();
        assertEquals("Order not released or status incorrect", "Backorder (Active)", dbOrder.getOrderStatus());
               assertEquals("Items not added properly", 3, dbOrder.getLineitems().size());

        Inventory.dbloadByPart("TestItemOWD0001", "112").setAvailabilityAtFacility(0,order.getFacilityCode());
        Inventory.dbloadByPart("TestItemOWD0002", "112").setAvailabilityAtFacility(1,order.getFacilityCode());
        Inventory.dbloadByPart("TestItemOWD0003", "112").setAvailabilityAtFacility(1,order.getFacilityCode());

          Iterator it = dbOrder.getLineitems().iterator();
               while (it.hasNext()) {

                   OwdLineItem line = (OwdLineItem) it.next();
                   String sku = line.getInventoryNum();

                   if (sku.equals("TestItemOWD0003")) {
                       assertEquals("Quantity requested is incorrect after initial order save", 1, line.getQuantityRequest().intValue());
                       assertEquals("Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                   } else  if (sku.equals("TestItemOWD0001"))
                   {
                       assertEquals("Quantity requested is incorrect after initial order save", 1, line.getQuantityRequest().intValue());
                                             assertEquals("Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                   }else  if (sku.equals("TestItemOWD0002"))
                   {
                       assertEquals("Quantity requested is incorrect after initial order save", 1, line.getQuantityRequest().intValue());
                                             assertEquals("Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                   }


               }

     //log.debug("loading status for order "+dbOrder.getOrderId());
        OrderStatus status = new OrderStatus(""+dbOrder.getOrderId());



          for (int i = 0; i < status.items.size(); i++) {
                        //update line item quantities
                        LineItem item = (LineItem) status.items.elementAt(i);
                        if(item.client_part_no.equals("TestItemOWD0002"))
                        {
                           item.force_backorder_quantity = true;

                            item.quantity_backordered = item.quantity_request;
                         item.quantity_request=0;
                        }

                    }

        OrderUtilities.updateLineItemsForAvailability( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(), status.items, OrderXMLDoc.kPartialShip, false,FacilitiesManager.getFacilityForCode(status.shipLocation).getId() );

       // Order.shipExistingOrder(new OrderStatus(dbOrder.getOrderId()+""));
        OrderUtilities.shipExistingOrder( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),status);

             sess.flush();
        HibUtils.commit(sess);
        sess.evict(dbOrder);

        dbOrder = (OwdOrder) sess.load(OwdOrder.class,dbOrder.getOrderId());

        assertEquals("Order not released or status incorrect", "At Warehouse", dbOrder.getOrderStatus());

               Iterator itx = dbOrder.getLineitems().iterator();
               while (itx.hasNext()) {

                   OwdLineItem line = (OwdLineItem) itx.next();
                   String sku = line.getInventoryNum();
                   log.debug("SKUX:"+line.getInventoryNum()+" Act/Req/Back:"+line.getQuantityActual()+"/"+line.getQuantityRequest()+"/"+line.getQuantityBack());
                    if (sku.equals("TestItemOWD0003")) {
                        assertEquals("[TestItemOWD0003]Quantity requested is incorrect after order release", 1, line.getQuantityRequest().intValue());
                                          assertEquals("[TestItemOWD0003]Quantity backordered is incorrect after order release", 0, line.getQuantityBack().intValue());
                                          assertEquals("[TestItemOWD0003]Quantity released is incorrect after order release", 1, line.getQuantityActual().intValue());

                   } else  if (sku.equals("TestItemOWD0001"))
                   {
                       assertEquals("[TestItemOWD0001]Quantity requested is incorrect after order release", 0, line.getQuantityRequest().intValue());
                                         assertEquals("[TestItemOWD0001]Quantity backordered is incorrect after order release", 1, line.getQuantityBack().intValue());
                                         assertEquals("[TestItemOWD0001]Quantity released is incorrect after order release", 0, line.getQuantityActual().intValue());

                                     }else  if (sku.equals("TestItemOWD0002"))
                   {
                       assertEquals("[TestItemOWD0002]Quantity requested is incorrect after order release", 0, line.getQuantityRequest().intValue());
                       assertEquals("[TestItemOWD0002]Quantity backordered is incorrect after order release", 1, line.getQuantityBack().intValue());
                       assertEquals("[TestItemOWD0002]Quantity released is incorrect after order release", 0, line.getQuantityActual().intValue());

                   }


               }




    }

     public void testReleaseOrderPartialForcedBackorder() throws Exception {



         createTestKits();
         String code = FacilitiesManager.getLocationCodeForClient(112);
         order.setFacilityCode("ALL".equals(code)?"DC1":code);
         order.setFacilityPolicy(order.getFacilityCode());
         Inventory.dbloadByPart("TestItemOWD0001", "112").setAvailabilityAtFacility(0,order.getFacilityCode());
         Inventory.dbloadByPart("TestItemOWD0002", "112").setAvailabilityAtFacility(0,order.getFacilityCode());
         Inventory.dbloadByPart("TestItemOWD0003", "112").setAvailabilityAtFacility(0,order.getFacilityCode());

         order.addLineItem("TestItemOWD0001", "1", "0.00", "0.00", "", "", "");
         order.addLineItem("TestItemOWD0002", "3", "0.00", "0.00", "", "", "");
         order.addLineItem("TestItemOWD0003", "1", "0.00", "0.00", "", "", "");

         order.backorder_rule = OrderXMLDoc.kPartialShip;
         order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

         OwdOrder dbOrder = saveOrder();
         assertEquals("Order not released or status incorrect", "Backorder (Active)", dbOrder.getOrderStatus());
                assertEquals("Items not added properly", 3, dbOrder.getLineitems().size());

         Inventory.dbloadByPart("TestItemOWD0001", "112").setAvailabilityAtFacility(0,order.getFacilityCode());
         Inventory.dbloadByPart("TestItemOWD0002", "112").setAvailabilityAtFacility(3,order.getFacilityCode());
         Inventory.dbloadByPart("TestItemOWD0003", "112").setAvailabilityAtFacility(1,order.getFacilityCode());

           Iterator it = dbOrder.getLineitems().iterator();
                while (it.hasNext()) {

                    OwdLineItem line = (OwdLineItem) it.next();
                    String sku = line.getInventoryNum();

                    if (sku.equals("TestItemOWD0003")) {
                        assertEquals("Quantity requested is incorrect after initial order save", 1, line.getQuantityRequest().intValue());
                        assertEquals("Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                    } else  if (sku.equals("TestItemOWD0001"))
                    {
                        assertEquals("Quantity requested is incorrect after initial order save", 1, line.getQuantityRequest().intValue());
                                              assertEquals("Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                    }else  if (sku.equals("TestItemOWD0002"))
                    {
                        assertEquals("Quantity requested is incorrect after initial order save", 3, line.getQuantityRequest().intValue());
                                              assertEquals("Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                    }


                }

         OrderStatus status = new OrderStatus(""+dbOrder.getOrderId());



           for (int i = 0; i < status.items.size(); i++) {
                         //update line item quantities
                         LineItem item = (LineItem) status.items.elementAt(i);
                         if(item.client_part_no.equals("TestItemOWD0002"))
                         {
                            item.force_backorder_quantity = true;

                             item.quantity_backordered = 2;
                             item.quantity_request=1;
                         }
                     }

         OrderUtilities.updateLineItemsForAvailability( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(), status.items, OrderXMLDoc.kPartialShip, false,FacilitiesManager.getFacilityForCode(status.shipLocation).getId() );

         OrderUtilities.shipExistingOrder( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),status);

              sess.flush();
         HibUtils.commit(sess);
         sess.evict(dbOrder);

         dbOrder = (OwdOrder) sess.load(OwdOrder.class,dbOrder.getOrderId());

         assertEquals("Order not released or status incorrect", "At Warehouse", dbOrder.getOrderStatus());

                Iterator itx = dbOrder.getLineitems().iterator();
                while (itx.hasNext()) {

                    OwdLineItem line = (OwdLineItem) itx.next();
                    String sku = line.getInventoryNum();
                    log.debug("SKU:"+line.getInventoryNum()+" Act/Req/Back:"+line.getQuantityActual()+"/"+line.getQuantityRequest()+"/"+line.getQuantityBack());
                     if (sku.equals("TestItemOWD0003")) {
                         assertEquals("[TestItemOWD0003]Quantity requested is incorrect after order release", 1, line.getQuantityRequest().intValue());
                                           assertEquals("[TestItemOWD0003]Quantity backordered is incorrect after order release", 0, line.getQuantityBack().intValue());
                                           assertEquals("[TestItemOWD0003]Quantity released is incorrect after order release", 1, line.getQuantityActual().intValue());

                    } else  if (sku.equals("TestItemOWD0001"))
                    {
                        assertEquals("[TestItemOWD0001]Quantity requested is incorrect after order release", 0, line.getQuantityRequest().intValue());
                                          assertEquals("[TestItemOWD0001]Quantity backordered is incorrect after order release", 1, line.getQuantityBack().intValue());
                                          assertEquals("[TestItemOWD0001]Quantity released is incorrect after order release", 0, line.getQuantityActual().intValue());

                                      }else  if (sku.equals("TestItemOWD0002"))
                    {
                        assertEquals("[TestItemOWD0002]Quantity requested is incorrect after order release", 1, line.getQuantityRequest().intValue());
                        assertEquals("[TestItemOWD0002]Quantity backordered is incorrect after order release", 2, line.getQuantityBack().intValue());
                        assertEquals("[TestItemOWD0002]Quantity released is incorrect after order release", 1, line.getQuantityActual().intValue());

                    }


                }




    }

    public void testReleaseOrderPartialForcedBackorderKit() throws Exception {



           createTestKits();
        String code = FacilitiesManager.getLocationCodeForClient(112);
        order.setFacilityCode("ALL".equals(code)?"DC1":code);
        order.setFacilityPolicy(order.getFacilityCode());
           Inventory.dbloadByPart("TestItemOWD0001", "112").setAvailabilityAtFacility(0,order.getFacilityCode());
           Inventory.dbloadByPart("BOOK-TEST", "112").setAvailabilityAtFacility(0,order.getFacilityCode());


           order.addLineItem("KIT-AUTO-TEST", "2", "0.00", "0.00", "", "", "");
           order.addLineItem("TestItemOWD0001", "3", "0.00", "0.00", "", "", "");
           order.addLineItem("BOOK-TEST", "2", "0.00", "0.00", "", "", "");

           order.backorder_rule = OrderXMLDoc.kPartialShip;
           order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

           OwdOrder dbOrder = saveOrder();
           assertEquals("Order not released or status incorrect", "Backorder (Active)", dbOrder.getOrderStatus());
                  assertEquals("Items not added properly", 5, dbOrder.getLineitems().size());

           Inventory.dbloadByPart("TestItemOWD0001", "112").setAvailabilityAtFacility(1,order.getFacilityCode());
           Inventory.dbloadByPart("BOOK-TEST", "112").setAvailabilityAtFacility(2,order.getFacilityCode());

             Iterator it = dbOrder.getLineitems().iterator();
                  while (it.hasNext()) {

                      OwdLineItem line = (OwdLineItem) it.next();
                      String sku = line.getInventoryNum();

                      if (sku.equals("BOOK-TEST") && line.getParentKey()!=null) //kit item
                           {
                          assertEquals("Quantity requested is incorrect after initial order save", 2, line.getQuantityRequest().intValue());
                          assertEquals("Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                      } else  if (sku.equals("TestItemOWD0001"))
                      {
                          assertEquals("Quantity requested is incorrect after initial order save", 3, line.getQuantityRequest().intValue());
                          assertEquals("Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                      }else if (sku.equals("BOOK-TEST") && line.getParentKey()==null) //non kit item
                      {
                          assertEquals("Quantity requested is incorrect after initial order save", 2, line.getQuantityRequest().intValue());
                          assertEquals("Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                      }


                  }

           OrderStatus status = new OrderStatus(""+dbOrder.getOrderId());



             for (int i = 0; i < status.items.size(); i++) {
                           //update line item quantities
                           LineItem item = (LineItem) status.items.elementAt(i);
                           if(item.client_part_no.equals("BOOK-TEST") && item.parent_line==null)
                           {
                              item.force_backorder_quantity = true;

                               item.quantity_backordered = 1;
                               item.quantity_request=1;
                           }
                 if(item.client_part_no.equals(kitItemSKU) )
                           {
                              item.force_backorder_quantity = true;

                               item.quantity_backordered = 1;
                               item.quantity_request=1;
                           }
                       }

           OrderUtilities.updateLineItemsForAvailability( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(), status.items, OrderXMLDoc.kPartialShip, false,FacilitiesManager.getFacilityForCode(status.shipLocation).getId() );

           OrderUtilities.shipExistingOrder( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),status);

                sess.flush();
           HibUtils.commit(sess);
           sess.evict(dbOrder);

           dbOrder = (OwdOrder) sess.load(OwdOrder.class,dbOrder.getOrderId());

           assertEquals("Order not released or status incorrect", "At Warehouse", dbOrder.getOrderStatus());

                  Iterator itx = dbOrder.getLineitems().iterator();
                  while (itx.hasNext()) {

                      OwdLineItem line = (OwdLineItem) itx.next();
                      String sku = line.getInventoryNum();
                      //log.debug("SKU:"+line.getInventoryNum()+" Act/Req/Back:"+line.getQuantityActual()+"/"+line.getQuantityRequest()+"/"+line.getQuantityBack());
                      if (sku.equals("BOOK-TEST") && line.getParentKey()!=null) //kit item
                           {
                          assertEquals("Quantity requested is incorrect after initial order release", 1, line.getQuantityRequest().intValue());
                          assertEquals("Quantity backordered is incorrect after initial order release", 1, line.getQuantityBack().intValue());

                      }else  if (sku.equals(kitItemSKU))
                      {
                          assertEquals("Quantity requested is incorrect after initial order release", 1, line.getQuantityRequest().intValue());
                          assertEquals("Quantity backordered is incorrect after initial order release", 1, line.getQuantityBack().intValue());

                      } else  if (sku.equals("TestItemOWD0001"))
                      {
                          assertEquals("Quantity requested is incorrect after initial order release", 1, line.getQuantityRequest().intValue());
                          assertEquals("Quantity backordered is incorrect after initial order release", 2, line.getQuantityBack().intValue());

                      }else if (sku.equals("BOOK-TEST") && line.getParentKey()==null) //non kit item
                      {
                          assertEquals("Quantity requested is incorrect after release", 1, line.getQuantityRequest().intValue());
                          assertEquals("Quantity backordered is incorrect after initial order release", 1, line.getQuantityBack().intValue());

                      }


                  }




      }

     public void testReleaseOrderPartialForcedBackorderOverlappingKits() throws Exception {



           createTestKits();
         String code = FacilitiesManager.getLocationCodeForClient(112);
         order.setFacilityCode("ALL".equals(code)?"DC1":code);
         order.setFacilityPolicy(order.getFacilityCode());
           Inventory.dbloadByPart("TestItemOWD0001", "112").setAvailabilityAtFacility(0,order.getFacilityCode());
           Inventory.dbloadByPart("BOOK-TEST", "112").setAvailabilityAtFacility(0,order.getFacilityCode());


           order.addLineItem(kitItemSKU, "2", "0.00", "0.00", "", "", "");
         order.addLineItem(kitItemSKU2, "1", "0.00", "0.00", "", "", "");
           order.addLineItem("TestItemOWD0001", "3", "0.00", "0.00", "", "", "");
           order.addLineItem("BOOK-TEST", "4", "0.00", "0.00", "", "", "");

           order.backorder_rule = OrderXMLDoc.kPartialShip;
           order.saveNewOrder(OrderUtilities.kHoldForPayment, true);

           OwdOrder dbOrder = saveOrder();
           assertEquals("Order not released or status incorrect", "Backorder (Active)", dbOrder.getOrderStatus());
                  assertEquals("Items not added properly", 8, dbOrder.getLineitems().size());

           Inventory.dbloadByPart("TestItemOWD0001", "112").setAvailabilityAtFacility(1,order.getFacilityCode());
           Inventory.dbloadByPart("BOOK-TEST", "112").setAvailabilityAtFacility(3,order.getFacilityCode());

             Iterator it = dbOrder.getLineitems().iterator();
                  while (it.hasNext()) {

                      OwdLineItem line = (OwdLineItem) it.next();
                      String sku = line.getInventoryNum();

                      if (sku.equals("BOOK-TEST") && line.getParentKey()!=null) //kit item
                           {
                          //assertEquals("[BOOK-TEST(kit)]Quantity requested is incorrect after initial order save", 2, line.getQuantityRequest());
                          assertEquals("[BOOK-TEST(kit)]Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                      } else  if (sku.equals("TestItemOWD0001"))
                      {
                          assertEquals("[TestItemOWD0001]Quantity requested is incorrect after initial order save", 3, line.getQuantityRequest().intValue());
                          assertEquals("[TestItemOWD0001]Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                      }else if (sku.equals("BOOK-TEST") && line.getParentKey()==null) //non kit item
                      {
                          assertEquals("[BOOK-TEST(nonkit)]Quantity requested is incorrect after initial order save", 4, line.getQuantityRequest().intValue());
                          assertEquals("[BOOK-TEST(nonkit)]Quantity backordered is incorrect after initial order save", 0, line.getQuantityBack().intValue());

                      }

                      assertEquals("["+line.getInventoryNum()+"]Quantity actual is incorrect after initial order save", 0, line.getQuantityActual().intValue());

                  }

           OrderStatus status = new OrderStatus(""+dbOrder.getOrderId());



             for (int i = 0; i < status.items.size(); i++) {
                           //update line item quantities
                           LineItem item = (LineItem) status.items.elementAt(i);
                           if(item.client_part_no.equals("BOOK-TEST") && item.parent_line==null)
                           {
                              item.force_backorder_quantity = true;

                               item.quantity_backordered = 3;
                               item.quantity_request-=item.quantity_backordered;
                           }
                 if(item.client_part_no.equals(kitItemSKU) )
                           {
                              item.force_backorder_quantity = true;

                               item.quantity_backordered = 1;
                               item.quantity_request-=item.quantity_backordered;
                           }
                       }

          OrderUtilities.updateLineItemsForAvailability( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(), status.items, OrderXMLDoc.kPartialShip, false,FacilitiesManager.getFacilityForCode(status.shipLocation).getId() );

           OrderUtilities.shipExistingOrder( ((SessionImplementor)sess).getJdbcConnectionAccess().obtainConnection(),status);

                sess.flush();
           HibUtils.commit(sess);
           sess.evict(dbOrder);

           dbOrder = (OwdOrder) sess.load(OwdOrder.class,dbOrder.getOrderId());

           assertEquals("Order not released or status incorrect", "At Warehouse", dbOrder.getOrderStatus());

                  Iterator itx = dbOrder.getLineitems().iterator();
                  while (itx.hasNext()) {

                      OwdLineItem line = (OwdLineItem) itx.next();
                      String sku = line.getInventoryNum();
                      //log.debug("SKU:"+line.getInventoryNum()+" Act/Req/Back:"+line.getQuantityActual()+"/"+line.getQuantityRequest()+"/"+line.getQuantityBack());
                      if (sku.equals("BOOK-TEST") && line.getParentKey()!=null) //kit item
                           {
                          assertEquals("[BOOK-TEST(kit)]Quantity requested is incorrect after initial order release", 1, line.getQuantityRequest().intValue());

                      }else  if (sku.equals(kitItemSKU))
                      {
                          assertEquals("["+kitItemSKU+"]Quantity requested is incorrect after initial order release", 1, line.getQuantityRequest().intValue());
                          assertEquals("["+kitItemSKU+"]Quantity backordered is incorrect after initial order release", 1, line.getQuantityBack().intValue());

                      } else  if (sku.equals(kitItemSKU2))
                      {
                          assertEquals("["+kitItemSKU2+"]Quantity requested is incorrect after initial order release", 1, line.getQuantityRequest().intValue());
                          assertEquals("["+kitItemSKU2+"]Quantity backordered is incorrect after initial order release", 0, line.getQuantityBack().intValue());

                      }else  if (sku.equals("TestItemOWD0001"))
                      {
                          assertEquals("[TestItemOWD0001]Quantity requested is incorrect after initial order release", 1, line.getQuantityRequest().intValue());
                          assertEquals("[TestItemOWD0001]Quantity backordered is incorrect after initial order release", 2, line.getQuantityBack().intValue());

                      }else if (sku.equals("BOOK-TEST") && line.getParentKey()==null) //non kit item
                      {
                          assertEquals("[BOOK-TEST(nonkit)]Quantity requested is incorrect after release", 1, line.getQuantityRequest().intValue());
                          assertEquals("[BOOK-TEST(nonkit)]Quantity backordered is incorrect after initial order release", 3, line.getQuantityBack().intValue());

                      }

                      assertEquals("["+line.getInventoryNum()+"]Quantity actual != quantity requested after initial order release", line.getQuantityActual().intValue(), line.getQuantityRequest().intValue());



                  }




      }
}
