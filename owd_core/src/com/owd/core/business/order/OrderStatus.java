package com.owd.core.business.order;


import com.owd.core.OWDUtilities;
import com.owd.core.TimeStamp;
import com.owd.core.business.Address;
import com.owd.core.business.Client;
import com.owd.core.business.Contact;
import com.owd.core.business.order.beans.suppliesBean;
import com.owd.core.business.order.billing.shippingCharges;
import com.owd.core.business.order.distributedOrderManagement.DOMUtilities;
import com.owd.core.managers.AddressManager;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.payment.FinancialTransaction;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdLineItemExemptions;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class OrderStatus

{
//private final static Logger log =  LogManager.getLogger();

    public static final String kByBillLastName = "Billing last name";

    public static final String kByShipLastName = "Shipping last name";

    public static final String kByBillZip = "Billing zip";

    public static final String kByShipZip = "Shipping zip";

    public static final String kByOrderRef = "OWD Order Reference";

    public static final String kByOrderID = "Order ID";

    public static final String kByOrderClientReference = "Client Order Reference";

    public static final String kByEmail = "Customer Email";

    public String load_error = null;

    public Date post_date = null;


    public Vector items = new Vector();

    public Map serialItemMap = new HashMap();

    public Vector packages = new Vector();

    public java.util.List events = new java.util.ArrayList();

    public java.util.List comments = new java.util.ArrayList();


    public java.util.List transactions = new java.util.ArrayList();


    public OrderStatus backorder = null;


    public boolean hasBackorder = false;

    public boolean isBackorder = false;

    public String backorderNum = "";

    public float balance = 0;

    public boolean is_void = false;

    public boolean is_on_hold = false;

    public boolean is_shipped = false;

    public boolean is_posted = false;

    public boolean is_unpicked = false;

    public ShippingInfo shipping = null;

    public String orderReference = "";
    public String orderBarcode = "";

    public String original_ordernum = "";

    public String orderDate = "";

    public String OWDorderReference = "";

    public String order_id = "";

    public String coupon = "";

    public String po_num = "";

    public String order_type = "";

    public String bill_cc_type = "";

    public String client_id = "";


    public float order_sub_total = 0;

    public float discount = 0;

    public float tax_amount = 0;

    public float ship_handling_fee = 0;

    public float order_total = 0;

    public String warehouse_status="UNKNOWN";

    public String group_name = "";

    public String order_location = "";


    public Address billAddress = new Address();

    public Contact billContact = new Contact();


    public Address shipperAddress = new Address();

    public Contact shipperContact = new Contact();


    public String packageRef = "";

    public String shipLocation="DC1";
    public String shipPolicy="DC1";

    public String packingInstructions = "";

    public String template ="";

    public boolean isShipping = false;
    public boolean business_order = false;
    public List<OwdLineItemExemptions> lineItemExemptions = new ArrayList<>();
    public List<suppliesBean> supplies = new ArrayList<>();

    public String aesItn = "";
    public int shipInfo2Id = 0;
    public String dduDDP = "";
    //public boolean isUnpicked = true;

    public String getLocation()
    {
        return shipLocation;
    }
    public String getShipPolicy()
    {
        return shipPolicy;
    }


    public boolean getIsUS() {
        if(shipping == null || shipping.shipAddress == null)
            return true;
        String country = shipping.shipAddress.country;
        if (country.equalsIgnoreCase("USA")
                || country.equalsIgnoreCase("US") ||
                country.equalsIgnoreCase("UNITED STATES") ||
                country.equalsIgnoreCase("UNITED_STATES"))
            return true;
        else
            return false;

    }

    public OrderStatus(Connection cxn, String orderID) throws Exception


    {

        order_id = orderID;

        dbload(cxn, orderID);

    }


    public OrderStatus(Connection cxn, String orderID, String clientID) throws Exception


    {

        order_id = orderID;

        dbload(cxn, orderID, clientID);

    }


    public void unpostOrder() throws Exception {

        if(is_shipped || isShipping)
        {
            throw new Exception("Cannot unpost order - order is already shipped or in the process of shipping");
        }
        Connection cxn = null;
        Statement stmt = null;
        try {

            cxn = ConnectionManager.getConnection();

            try {
                Throwable stack = new Throwable();
                stack.fillInStackTrace();
              //  log.debug("UNPOSTORDER TRACE " + order_id);
                stack.printStackTrace();
            } catch (Exception exxx) {
              //  log.debug("UNPOSTORDER TRACE " + order_id);
                exxx.printStackTrace();
            }
            String esql = "exec unpost_order " + order_id;

            stmt = cxn.createStatement();

            stmt.executeUpdate(esql);

/***************************
 COMMIT
 ***************************/
            cxn.commit();

            is_posted = false;
            is_on_hold = true;

        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            OWDUtilities.debugApp(ex);
            ex.printStackTrace();
            cxn.rollback();
            throw ex;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            ConnectionManager.freeConnection(cxn);
        }

    }

    public static final String kNetServicesServer = "it.owd.com:8081/wms/manifesting/ship";
/*
    public void postAndShipVirtualOrder() throws Exception {



           try {

               Session sess =  HibernateSession.currentSession();

               //verify all virtual

               //post fake package and package_order records

               //get package barcode

               //update line items

               //update order post date and status

               //update database

               log.debug("Posting to URL: " + "http://" + kNetServicesServer);
               WebResource loader = new WebResource("http://" + kNetServicesServer, WebResource.kGETMethod);


               loader.addParameter("fn", "ss");

               loader.addParameter("order_fkey", order_id);

               loader.addParameter("line_index", "1");

               loader.addParameter("tracking_no", "NONE");

               loader.addParameter("weight", "0.01");

               //Billed to client
               loader.addParameter("total_billed", "0.00");

               //OWD's cost
               loader.addParameter("cost_of_goods", "0.00");

               loader.addParameter("msn", "0");

               //log.debug("Posting shipment, shipper is: "+(String)getValueAsString(ShipConfig.CURRENT_SHIPPER));

               loader.addParameter("shipper", "OWD");


               loader.addParameter("facility_code", "DC1");

               loader.addParameter("ip_address", "0.0.0.0");

               loader.addParameter("package_barcode", (String) getValueAsString("PACKAGE_BARCODE"));


               loader.addParameter("ins_value", "0.00");

               loader.addParameter("ins_cost", "0.00");

               loader.addParameter("weight_notes", "");
               loader.addParameter("customs_docs", "0");

               BufferedReader reader = loader.getResource();

               log.debug("saving shipment");

               TabReader responseData = new TabReader(reader, false);

               log.debug("Post shipment response");
               for (int i = 0; i < responseData.getRowCount(); i++) {
                   for (int j = 0; j < responseData.getRowSize(i); j++) {
                       log.debug(j + ":" + i + "-" + responseData.getStrValue(j, i, ""));
                   }
               }
               if (responseData.getRowCount() < 1)

                   throw new Exception("Server not updated - Server did not respond. Unable to ship.");

               if (responseData.getRowSize(0) < 1)

                   throw new Exception("Server not updated - Server responded incorrectly. Unable to ship.");

               String response = ((String) responseData.getStrValue(0, 0, "ERROR")).trim();

               if (response.equals("ERROR"))

                   throw new Exception((String) responseData.getStrValue(0, 1, "Unknown"));


           } catch (Exception ex) {

               ex.printStackTrace();

               throw new Exception("Server not updated - Server did not respond. Unable to ship.");


           }


       }*/

    public void unpickOrder() throws Exception {
        Connection cxn = null;
        Statement stmt = null;
        try {

            cxn = ConnectionManager.getConnection();


            String esql = "exec unpick_order " + order_id;

            stmt = cxn.createStatement();

            stmt.executeUpdate(esql);

/***************************
 COMMIT
 ***************************/
            cxn.commit();

            is_unpicked = true;

        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            OWDUtilities.debugApp(ex);
            ex.printStackTrace();
            cxn.rollback();
            throw ex;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            ConnectionManager.freeConnection(cxn);
        }

    }

    public void reprintOrder() throws Exception {
        Connection cxn = null;
        Statement stmt = null;
        try {

            cxn = ConnectionManager.getConnection();


            String esql = "exec reprint_order " + order_id;

            stmt = cxn.createStatement();

            stmt.executeUpdate(esql);

/***************************
 COMMIT
 ***************************/
            cxn.commit();

        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            OWDUtilities.debugApp(ex);
            ex.printStackTrace();
            cxn.rollback();
            throw ex;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            ConnectionManager.freeConnection(cxn);
        }

    }


    public void voidOrder() throws Exception {
        Connection cxn = null;
        Statement stmt = null;
        try {

            cxn = ConnectionManager.getConnection();

            voidOrder(cxn);

            try {
                Throwable stack = new Throwable();
                stack.fillInStackTrace();
              //  log.debug("VOIDORDER TRACE " + order_id);
                stack.printStackTrace();
            } catch (Exception exxx) {
              //  log.debug("VOIDORDER TRACE " + order_id);
                exxx.printStackTrace();
            }

            is_on_hold = false;
            is_posted = false;
            is_unpicked = true;
            is_void = true;

        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            OWDUtilities.debugApp(ex);
            ex.printStackTrace();
            cxn.rollback();
            throw ex;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            ConnectionManager.freeConnection(cxn);
        }

    }

    private Map<String,String> tagMap = null;

    public static Map<String,String> getTagMapForOrderId(int orderId)
    {
        TreeMap<String, String> atagMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
            try {
                ResultSet rs = HibernateSession.getResultSet("select name,value " +
                        "from owd_tags where type='ORDER' and " +
                        "external_id=" + orderId);

                while (rs.next()) {
                    atagMap.put(rs.getString(1), rs.getString(2));

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // HibernateSession.closeSession();
            }

        return atagMap;


    }

    public synchronized Map<String,String> getTagMap()
    {
        if(tagMap==null) {
            tagMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
            try {
                ResultSet rs = HibernateSession.getResultSet("select name,value " +
                        "from owd_tags where type='ORDER' and " +
                        "external_id=" + order_id);

                while (rs.next()) {
                    tagMap.put(rs.getString(1), rs.getString(2));

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                // HibernateSession.closeSession();
            }
        }
        return tagMap;


    }

    public float getTotalShipmentWeight()

    {

        float shipWeight = (float) 0.00;


        if (packages.size() > 0)

        {

            for (int i = 0; i < packages.size(); i++)

            {

                Package pkg = (Package) packages.elementAt(i);

                if ("0".equals(pkg.is_void))

                {

                    shipWeight += pkg.weight;

                }

            }

        }


        return OWDUtilities.roundFloat(shipWeight);


    }


    public String getStatusString()

    {

        StringBuffer sb = new StringBuffer();

        if (is_void) return "Void";

        if (is_posted)

        {
            if (isShipping)

            {

                sb.append("Shipping");

            } else
            if (is_shipped)

            {

                sb.append("Shipped");

            } else {

                sb.append("At&nbsp;Warehouse");

            }

            //not posted

            if (is_on_hold)

            {

                sb.append(" On&nbsp;Hold");

            }

        } else

        {

            //not posted

            if (is_on_hold)

            {

                sb.append("On&nbsp;Hold");

                if (isBackorder)

                {

                    sb.append(" Backorder");

                }

            } else {

                if (isBackorder)

                {

                    sb.append("Pending&nbsp;Backorder");

                } else

                {

                    if (balance > 0)

                        sb.append("Unshipped&nbsp;(may need payment)");

                    else

                        sb.append("Unshipped&nbsp;(unposted or backordered)");

                }

            }

        }


        return sb.toString();

    }


    private void dbload(Connection cxn, String orderID) throws Exception


    {

        dbload(cxn, orderID, null);

    }


    private void dbload(Connection cxn, String orderID, String clientID) throws Exception

    {

        Statement stmt = null;

        ResultSet rs = null;

        String shipID = null;

        TimeStamp ts = new TimeStamp();
        TimeStamp tsall = new TimeStamp();
        try {

            StringBuffer sql = new StringBuffer();

            sql.append("select is_void,is_backorder,is_future_ship,ISNULL(order_balance,0),"

                    + "ISNULL(backorder_order_num,\'\'),ISNULL(s.order_ship_info_id,0),ISNULL(post_date,\'\'),"

                    + "ISNULL(order_refnum,\'NA\'),ISNULL(created_date,\'\'),ISNULL(order_num,\'NA\'),ISNULL(original_order_num,\'\') ,"

                    + "ISNULL(coupon,\'\'), ISNULL(po_num,\'\'), ISNULL(order_type,\'\'), client_fkey, ISNULL(post_date,'1900-1-1'), ISNULL(pick_status,0), ISNULL(owd_order.ship_packs,0)," +
                     " ISNULL(owd_order.order_num_barcode,''),ISNULL(facility_code,'DC1'),ISNULL(group_name,''),ISNULL(facility_policy,'DC1'),ISNULL(packing_instructions,\'\'), ISNULL(is_shipping,0) as is_shipping, ISNULL(license_plate,'') as order_location, ISNULL(code,'') as template, business_order " +
                    ",ISNULL(s2.order_ship_info2_id,0) as order_ship_info2_id, ISNULL(s2.aes_itn,'') as  aes_itn, " +
                    "CASE WHEN ISNULL(no_customs_account,0) = 0 THEN 'DDU' ELSE 'DDP' END AS ddu_ddp  " +
                    "from owd_order (NOLOCK) " +
                    "join owd_order_ship_info s (NOLOCK) on order_id=order_fkey " +
                    "Left outer join owd_order_template t on order_id = t.order_fkey " +
                    "left outer join order_ship_info2 s2  (NOLOCK)  on owd_order.order_id = s2.order_fkey " +
                    "where order_id = " + orderID);

            if (clientID != null) {

                sql.append(" and client_fkey = " + clientID);

            }

            //   //log.debug(sql);
            stmt = cxn.createStatement();

          //  log.debug("status by id sql=" + sql);

            stmt.execute(sql.toString());


            rs = stmt.getResultSet();

            if (rs.next()) {

                is_void = (1 == rs.getInt(1));

                isBackorder = (1 == rs.getInt(2));

                is_on_hold = (1 == rs.getInt(3));

                balance = rs.getFloat(4);

                backorderNum = rs.getString(5);

                shipID = rs.getString(6);

                order_id = orderID;

                is_posted = (rs.getString(7).trim().length() > 6);

                if (rs.getString(7).indexOf("1900") >= 0)

                    is_posted = false;

                orderReference = rs.getString(8);

                orderDate = rs.getString(9);

                OWDorderReference = rs.getString(10);

                original_ordernum = rs.getString(11);

                coupon = rs.getString(12);

                po_num = rs.getString(13);

                order_type = rs.getString(14);

                client_id = rs.getString(15);

                post_date = rs.getDate(16);

                is_unpicked = (0 == rs.getInt(17));
                is_shipped = (0 != rs.getInt(18));


                warehouse_status = (rs.getInt(17)==0?"PENDING":rs.getInt(17)==1?"PICKING":"PICKED");

                orderBarcode = rs.getString(19);

                shipLocation = rs.getString(20);
                group_name = rs.getString(21);
                shipPolicy = rs.getString(22);
                packingInstructions = rs.getString(23);
                isShipping = rs.getInt(24)==1;
                order_location = rs.getString(25);
                template = rs.getString(26);
                business_order = rs.getBoolean(27);


                clientID = client_id;

                aesItn = rs.getString("aes_itn");
                shipInfo2Id = rs.getInt("order_ship_info2_id");
                dduDDP = rs.getString("ddu_ddp");

            }

            rs.close();

            stmt.close();
            ts.print("OrderStatus Order Info");
            ts = new TimeStamp();


            sql = new StringBuffer();

            sql.append("select ISNULL(order_sub_total,0.0),ISNULL(discount,0.0),ISNULL(tax_amount,0.0),ISNULL(ship_handling_fee,0.0),ISNULL(order_total,0.0)" +

                    ",ISNULL(bill_last_name,\'\') " +

                    ",ISNULL(bill_first_name,\'\') " +

                    ",ISNULL(bill_address_one,\'\') " +

                    ",ISNULL(bill_address_two,\'\') " +

                    ",ISNULL(bill_city,\'\') " +

                    ",ISNULL(bill_state,\'\') " +

                    ",ISNULL(bill_zip,\'\') " +

                    ",ISNULL(bill_country,\'\') " +

                    ",ISNULL(bill_company_name,\'\') " +

                    ",ISNULL(bill_title,\'\') " +

                    ",ISNULL(bill_phone_num,\'\') " +

                    ",ISNULL(bill_fax_num,\'\') " +

                    ",ISNULL(bill_email_address,\'\'),ISNULL(cc_type,\'CL\') from owd_order (NOLOCK) where order_id = " + orderID);


            if (clientID != null)

            {

                sql.append(" and client_fkey = " + clientID);

            }


            if (order_id.length() > 0)

            {

                stmt = cxn.createStatement();

                stmt.execute(sql.toString());


                rs = stmt.getResultSet();

                if (rs.next())

                {

                    order_sub_total = rs.getFloat(1);

                    discount = rs.getFloat(2);

                    tax_amount = rs.getFloat(3);

                    ship_handling_fee = rs.getFloat(4);

                    order_total = rs.getFloat(5);

                    billContact = new Contact(rs.getString(7) + " " + rs.getString(6), rs.getString(16), rs.getString(17), rs.getString(18), "");

                    billAddress = new Address(rs.getString(14), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13));

                    bill_cc_type = rs.getString(19);

                }

                rs.close();

                stmt.close();

                ts.print("OrderStatus Order Total Info");
                ts = new TimeStamp();
                stmt = cxn.createStatement();

                stmt.execute("select ISNULL(shipper_name,\'\'),ISNULL(shipper_company,\'\'),ISNULL(shipper_address_one,\'\')," +

                        "ISNULL(shipper_address_two,\'\'),ISNULL(shipper_city,\'\'),ISNULL(shipper_state,\'\'),ISNULL(shipper_zip,\'\'),ISNULL(shipper_phone,\'\'),ISNULL(package_ref,\'\') from order_ship_info2 (NOLOCK) where order_fkey = " + order_id);


                rs = stmt.getResultSet();

                if (rs.next())

                {


                    shipperContact = new Contact(rs.getString(1), rs.getString(8), "", "", "");

                    shipperAddress = new Address(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), "USA");

                    packageRef = rs.getString(9);


                }

                rs.close();

                stmt.close();

                ts.print("OrderStatus Order Contact Info");
                ts = new TimeStamp();


                shipping = ShippingInfo.getShippingInfoForID(cxn, shipID);


                if (shipping == null) {
                    shipping = new ShippingInfo();
                }
                ts.print("OrderStatus order shipment info");
                ts = new TimeStamp();


                items = LineItem.getItemsForOrder(cxn, orderID);
                ts.print("OrderStatus Line Item Info");
                ts = new TimeStamp();
               // log.debug("getting serial item map");
                serialItemMap = LineItem.getItemSerialMapForOrder(cxn, orderID);
             //   log.debug("got serial item map:" + serialItemMap);

                ts.print("OrderStatus Item Serial Map");
                ts = new TimeStamp();




                packages = Package.getPackagesForOrder(cxn, orderID);
                ts.print("OrderStatus package info");
                ts = new TimeStamp();


                transactions = FinancialTransaction.getTransactionsForOrder(cxn, orderID);
                ts.print("OrderStatus Payment Info");
                ts = new TimeStamp();
                lineItemExemptions = getLineItemExemptionsFromOrderId(orderID);
                supplies = getSuppliesUsedForOrderId(orderID);


                Vector tempEvents = Event.getEventssForOrder(cxn, orderID);

                for (int i = 0; i < tempEvents.size(); i++)

                {

                    Event event = (Event) tempEvents.elementAt(i);

                    if (event.event_type == Event.kEventTypeComment)

                    {

                        comments.add(event);

                    } else

                    {

                        events.add(event);

                    }

                }

                ts.print("OrderStatus Event Info");


                /*  is_shipped = false;



                                for (int i=0;i<packages.size();i++)

                                {

                                    if ( ((Package)packages.elementAt(i)).is_void.equals("0"))

                                        is_shipped = true;

                                }
                */


                if (backorderNum.length() > 4)

                {

                    //    //log.debug("found bo :"+backorderNum+":");

                    hasBackorder = true;

                    String backID = ConnectionManager.getOrderKeyID(cxn, backorderNum);

                    backorder = new OrderStatus(cxn, backID);

                }

            }


        } catch (Exception ex)

        {

            load_error = ex.getMessage();

            ex.printStackTrace();

            throw ex;
        } finally

        {

            try {
                rs.close();
            } catch (Exception e) {
            }

            try {
                stmt.close();
            } catch (Exception e) {
            }
            tsall.print("OrderStatus Total Load Time");
        }

    }


    public void addComment(String message) throws Exception

    {
        addComment(message, null);
    }

    public void addComment(String message, String user) throws Exception {


        if (message == null) message = "";

        if (message.trim().length() > 0)

        {

            Event.addOrderEvent(new Integer(order_id).intValue(), Event.kEventTypeComment, message, user);

            comments = new ArrayList();

            try {

                Vector tempEvents = Event.getEventssForOrder(order_id);

                for (int i = 0; i < tempEvents.size(); i++)

                {

                    Event event = (Event) tempEvents.elementAt(i);

                    if (event.event_type == Event.kEventTypeComment)

                    {

                        comments.add(event);

                    }

                }


            } catch (Exception ex) {
            }

        }


    }

    public void changeLocation(String newLoc) throws Exception {
        try {

            if(is_posted)
            {
                throw new Exception("Cannot change location on an altready posted order; unpost to make changes");
            }

            if(!FacilitiesManager.getActiveFacilityCodes().contains(newLoc)&& !DOMUtilities.getAvailableDomMethods().contains(newLoc))
            {
                throw new Exception("Facility code "+newLoc+" is invalid");
            }
            String sql = "update owd_order set facility_code=?, facility_policy=? where order_id=? and post_date is null";

            PreparedStatement stmt =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(sql);


            stmt.setString(1, newLoc);
            stmt.setString(2, newLoc);
            stmt.setInt(3, new Integer(order_id));

            stmt.executeUpdate();

            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            shipLocation=newLoc;
            shipPolicy=newLoc;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            // HibernateSession.closeSession();
        }


    }

    public void clearHoldAndSetBackorder() throws Exception {
        try {

            String sql = "update owd_order set is_future_ship=0, is_backorder=1 where order_id=?";

            PreparedStatement stmt =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(sql);


            stmt.setInt(1, new Integer(order_id));

            stmt.executeUpdate();

            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {

        } finally {
            // HibernateSession.closeSession();
        }


    }

    public  void removeLineItem(String itemIDStr) throws Exception {
        int itemID = 0;


       // log.debug("1");

        if(is_posted || is_shipped || is_void)
        {
            throw new Exception("Order status "+getStatusString()+" cannot be edited. Order must be unposted before editing - item cannot be removed");
        }

       // log.debug("1");
        try
        {
            itemID = Integer.parseInt(itemIDStr);
        }   catch(Exception itemex)
        {
            throw new Exception("Item ID "+itemIDStr+" not valid - item cannot be removed");
        }

      //  log.debug("1");
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.decode(order_id));


     //   log.debug("1");
        List<OwdLineItem> itemlist = new ArrayList<OwdLineItem>();
        for(OwdLineItem lineitem: order.getLineitems())
        {
            if(itemID == lineitem.getLineItemId())
            {
                itemlist.add(lineitem);
            } else if(itemID == (lineitem.getParentKey()==null?-1:lineitem.getParentKey()))
            {
                itemlist.add(lineitem);
            }
        }

      //  log.debug("1");
        if(itemlist.size()==0)
        {
            throw new Exception("Item ID "+itemIDStr+" not found in order - item cannot be removed");
        }

      //  log.debug("1");
        if(itemlist.size()==order.getLineitems().size())
        {
            throw new Exception("You cannot remove the last item from an order - add a new item before removing this one");
        }

      //  log.debug("1");
        //got valid item
        order.getLineitems().removeAll(itemlist);
        for(OwdLineItem lineitem:itemlist)
        {
            HibernateSession.currentSession().delete(lineitem);

        }
      //  log.debug("1");
        OrderStatus.applyNewInvoiceTotalsToOwdOrder(order);

        HibernateSession.currentSession().save(order);
      //  log.debug("1");
        HibUtils.commit(HibernateSession.currentSession());
    }

    public void removeDuplicateSKUs() throws Exception {

        String sql = "select inventory_num,count(*), sum(quantity_request), sum(quantity_actual), sum(quantity_back)\n" +
                "                from owd_line_item where order_fkey=" + order_id + " group by inventory_num having count(*) =2";

        String sql2 = "set rowcount 1; delete from owd_line_item where inventory_num=? and order_fkey=?; set rowcount 0;";
        PreparedStatement stmt =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(sql2);
        String sql3 = "update owd_line_item " +
                "set quantity_request=?, quantity_actual=?, quantity_back=? " +
                "where inventory_num=?" +
                " and order_fkey=?";

        PreparedStatement stmt2 =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(sql3);

        //HibernateSession.currentSession().beginTransaction();
        ResultSet rs =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(sql).executeQuery();

        while (rs.next()) {
            //  //log.debug("Got SKU "+rs.getString(1)+" count "+rs.getString(2)+" : "+rs.getString(3)+"?"+rs.getString(4)+"?"+rs.getString(5));


            stmt.setString(1, rs.getString(1));
            stmt.setInt(2, new Integer(order_id).intValue());

            stmt.executeUpdate();
            //   //log.debug("got stmt 1 done");


            stmt2.setInt(1, rs.getInt(3));
            stmt2.setInt(2, rs.getInt(4));
            stmt2.setInt(3, rs.getInt(5));
            stmt2.setString(4, rs.getString(1));
            stmt2.setInt(5, new Integer(order_id).intValue());

            stmt2.executeUpdate();
            //  //log.debug("got stmt 2 done");
        }


        /*   rs =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(sql).executeQuery();

        while(rs.next())
        {
          //log.debug("Got SKU "+rs.getString(1)+" count "+rs.getString(2)+" : "+rs.getString(3)+"?"+rs.getString(4)+"?"+rs.getString(5));


        }*/

        HibUtils.commit(HibernateSession.currentSession());


    }
/*

    public static void addSimpleLineItemToExistingOrder(int orderID, int clientID, String inventoryNum, int qtyAdd) throws Exception {
        try {

            int tempIDIndex = 0;

            Vector<LineItem> kitList = new Vector<LineItem>();

            LineItem item = new LineItem(inventoryNum, qtyAdd, 0.00f, 0.00f * qtyAdd, "", "", "", 0.00f, "");

            item.verifyInventory("" + clientID);
            kitList.add(item);


            Map<Integer, Integer> idMap = new TreeMap<Integer, Integer>();

            if (0 >= kitList.size()) {
                throw new Exception("No valid line items in order - order could not be saved!");
            }
            //add line items
            for (int i = 0; i < kitList.size(); i++) {

                LineItem itemk = ((LineItem) kitList.elementAt(i));
                //((LineItem)skuList.elementAt(i)).updateQuantities(avail,backorder_rule);
                itemk.order_fkey = orderID + "";
                itemk.line_item_id = "0";


                log.debug("saving line: " + itemk);

                itemk.dbsave( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());
                if (itemk.is_parent.intValue() == 1) {
                    idMap.put(new Integer(itemk.tempID), new Integer(itemk.line_item_id));
                }

            }
            //log.debug("got id map after saving items:"+idMap);

            for (int i = 0; i < kitList.size(); i++) {

                LineItem itemk = ((LineItem) kitList.elementAt(i));
                //log.debug("checking item "+itemk.client_part_no+" for parent");
                if (itemk.parent_line != null)

                {
                    //log.debug("found parent for tempID="+itemk.parent_line);
                    itemk.parent_line = (Integer) idMap.get(itemk.parent_line);
                    itemk.dbupdate( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());
                }
                HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());



                //items.addAll(kitList);
            }
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            //Mailer.sendMail("Save order caught error",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","do_not_reply@owd.com");
            ex.printStackTrace();

            try {
                HibUtils.rollback(HibernateSession.currentSession());
            } catch (Exception exm) {

                exm.printStackTrace();
            }
            throw ex;
        } finally {

            // HibernateSession.closeSession();

        }
    }
*/

    public static void addLineItemToExistingOrder(int orderID, int clientID, String inventoryNum, String description, int qtyAdd, float itemCost, float lineDiscount) throws Exception {
        System.out.println("in addLineItem");

        try {
            int tempIDIndex = 0;

            Vector<LineItem> kitList = new Vector<LineItem>();

            LineItem item = new LineItem(inventoryNum, qtyAdd, itemCost, itemCost * qtyAdd, description, "", "", lineDiscount, "");

            item.verifyInventory("" + clientID);
            kitList.add(item);


            ////.debug("checking for kit");
            Map reqItemMap = LineItem.getRequiredItemsForInventoryID(new Integer(item.inventory_fkey));
         //   log.debug("got kit items " + reqItemMap);
            if (reqItemMap.size() > 0) {

              //  log.debug("got kit list " + reqItemMap.size());
                //is a kit
                tempIDIndex++;
                item.tempID = tempIDIndex;
                item.is_parent = new Integer(1);

                Object[] keys = reqItemMap.keySet().toArray();
             //   log.debug("got keys " + keys);
                for (int mapIndex = 0; mapIndex < keys.length; mapIndex++) {
                  //  log.debug("map index " + mapIndex);
                    Inventory invRecord = Inventory.getInventoryForID("" + ((Integer) (keys[mapIndex])));

                    LineItem kititem = new LineItem(invRecord.inventory_num, ((int) (
                            ((Integer) reqItemMap.get(((Integer) (keys[mapIndex])))).intValue() * item.quantity_request)),
                            0.00f, 0.00f, invRecord.description, invRecord.item_color, invRecord.item_size);
                    kititem.setInventory(invRecord);
                    kititem.verifyInventory("" + clientID);
                    kititem.parent_line = new Integer(item.tempID);
                    kitList.add(kititem);

                }
              //  log.debug("kit done");

            }


            Map<Integer, Integer> idMap = new TreeMap<Integer, Integer>();

            if (0 >= kitList.size()) {
                throw new Exception("No valid line items in order - order could not be saved!");
            }
            //add line items
            for (int i = 0; i < kitList.size(); i++) {

                LineItem itemk = ((LineItem) kitList.elementAt(i));
                //((LineItem)skuList.elementAt(i)).updateQuantities(avail,backorder_rule);
                itemk.order_fkey = orderID + "";
                itemk.line_item_id = "0";


            //    log.debug("saving line: " + itemk);

                itemk.dbsave( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());
                if (itemk.is_parent.intValue() == 1) {
                    idMap.put(new Integer(itemk.tempID), new Integer(itemk.line_item_id));
                }

            }
            //log.debug("got id map after saving items:"+idMap);

            for (int i = 0; i < kitList.size(); i++) {

                LineItem itemk = ((LineItem) kitList.elementAt(i));
                //log.debug("checking item "+itemk.client_part_no+" for parent");
                if (itemk.parent_line != null)

                {
                    //log.debug("found parent for tempID="+itemk.parent_line);
                    itemk.parent_line = (Integer) idMap.get(itemk.parent_line);
                    itemk.dbupdate( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());
                }
                HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());
                OwdOrder order = (OwdOrder) HibernateSession.currentSession().get(OwdOrder.class, new Integer(orderID));

                if (itemCost != 0.00f || lineDiscount != 0.00f) {
                    //  applyNewInvoiceTotalsToOwdOrder(order);
                }
                HibernateSession.currentSession().save(order);
                HibernateSession.currentSession().flush();
                HibUtils.commit(HibernateSession.currentSession());


                //items.addAll(kitList);
            }
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().get(OwdOrder.class, new Integer(orderID));

            applyNewInvoiceTotalsToOwdOrder(order);

            HibernateSession.currentSession().save(order);
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
            HibernateSession.currentSession().evict(order);
        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            //Mailer.sendMail("Save order caught error",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","do_not_reply@owd.com");
            ex.printStackTrace();

            try {
                HibUtils.rollback(HibernateSession.currentSession());
            } catch (Exception exm) {

                exm.printStackTrace();
            }
            throw ex;
        } finally {

            // HibernateSession.closeSession();
            System.out.println("addlineitem finally");
        }
    }


    public static void applyNewInvoiceTotalsToOwdOrder(OwdOrder order) throws Exception {

        float total_product_cost = 0.00f;

        for (int i = 0; i < order.getLineitems().size(); i++) {
         //   log.debug("adding item " + ((OwdLineItem) order.getLineitems().toArray()[i]).getInventoryNum() + ":" +
         //           ((OwdLineItem) order.getLineitems().toArray()[i]).getLineItemId() + " " +
         //           ((OwdLineItem) order.getLineitems().toArray()[i]).getTotalPrice().floatValue());
            total_product_cost += ((OwdLineItem) order.getLineitems().toArray()[i]).getTotalPrice().floatValue();
        }
        total_product_cost = OWDUtilities.roundFloat(total_product_cost);
      //  log.debug("total:" + total_product_cost);
        order.setOrderSubTotal(new BigDecimal(total_product_cost));
        order.setOrderTotal(new BigDecimal(OWDUtilities.roundFloat(order.getOrderSubTotal().floatValue() + order.getShipHandlingFee().floatValue()
                + (null == order.getTaxAmount() ? 0.00f : order.getTaxAmount().floatValue()) + order.getGiftWrapFee().floatValue() + (Math.abs(order.getDiscount().floatValue()) * -1.00f))));


    }

    public int getLineCount()

    {

        int count = 0;


        for (int i = 0; i < items.size(); i++)

        {

            if (((LineItem) items.elementAt(i)).quantity_request > 0)

                count++;

        }


        return count;

    }

    public int getRequestQuantityForSKU(String sku) {

        int count = 0;
        if (sku == null) sku = "";


        for (int i = 0; i < items.size(); i++) {

            if (sku.equals(((LineItem) items.elementAt(i)).client_part_no))

                count += ((LineItem) items.elementAt(i)).quantity_request;

        }


        return count;

    }

    public int getShippedQuantityForSKU(String sku) {

        int count = 0;
        if (sku == null) sku = "";


        for (int i = 0; i < items.size(); i++) {

            if (sku.equals(((LineItem) items.elementAt(i)).client_part_no))

                count += ((LineItem) items.elementAt(i)).quantity_actual;

        }


        return count;

    }

    public OrderStatus() {


    }


    public OrderStatus(String orderID)

    {

        this(orderID, null);

    }




    public static void main(String[] args) {



        try{
          //  System.setProperty("com.owd.environment","test");
            OrderStatus o = new OrderStatus("17001775");
            System.out.println(o.business_order);
            System.out.println(o.supplies.size());
            System.out.println(o.lineItemExemptions.size());
           /* List<String> l = new ArrayList<String>();

            l.add("11443725");


            for(String s:l){

                addLineItemToExistingOrder(Integer.parseInt(s),Integer.parseInt("345"),"841469173856","",1,0.0f,0.0f);


            }*/




        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public List<shippingCharges> getShippingCharges(){
        List<shippingCharges> charges = new ArrayList<shippingCharges>();


            String sql = "select * from sp_bill_shipping where order_id = :orderId order by tracking, recorded_date";
            try {
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                q.setParameter("orderId",order_id);
                List results = q.list();
                if(results.size()>0){
                    for(Object row:results){
                        Map data = (Map) row;
                        shippingCharges sCharge = new shippingCharges();
                        sCharge.setActivity(data.get("activity").toString());
                        sCharge.setAmount(Double.parseDouble(data.get("amount").toString()));
                        sCharge.setRecordedDate(data.get("recorded_date").toString());
                        sCharge.setTransactionId(data.get("trans_id").toString());
                        sCharge.setTracking(data.get("tracking").toString());
                        charges.add(sCharge);

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        return charges;
    }

    public Order createOrderFromOrderStatus(Order order, boolean includeLineItems) throws Exception {
        //passed in order should have been initialized with default field values including client ID
        order.getShippingInfo().setShippingAddress(Address.createFromStorableString(shipping.shipAddress.toStorableString()));
        order.getShippingInfo().setShippingContact(Contact.createFromStorableString(shipping.shipContact.toStorableString()));
        order.setBillingAddress(Address.createFromStorableString(billAddress.toStorableString()));
        order.setBillingContact(Contact.createFromStorableString(billContact.toStorableString()));
        try
        {
        order.getShippingInfo().setShipOptions(shipping.carr_service, shipping.carr_freight_terms, shipping.third_party_refnum);
        }catch(Exception ex)
        {
            order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
        }

        if (includeLineItems) {
            for (LineItem item : (Vector<LineItem>) items) {
                if (item.parent_line == null) {
                    order.addLineItem(item.client_part_no, "" + (item.quantity_request + item.quantity_backordered), "" + item.sku_price, "0.00", item.description, item.color, item.size);
                }
            }
        }

        order.setFacilityPolicy(shipPolicy);
        order.setFacilityCode(shipLocation);
        order.setBusinessOrder(business_order);

        return order;

    }

 public Order createCallTagEmailLabelOrder(Address pickupAddress, Contact pickupContact, String shipMethod, float predictedWeight) throws Exception {
        Order order = createOrderFromOrderStatus(Client.getClientForID(client_id).getClientPolicy().createInitializedOrder(), false);

        if (!(shipMethod.startsWith("UPS"))) {
            throw new Exception("Invalid ship method " + shipMethod + " requested; must be a UPS method");
        }
        if(pickupContact.getEmail().trim().length()<3)
        {
         throw new Exception("No valid email address provided");
}
        order.getShippingInfo().setShippingAddress(pickupAddress);
        order.getShippingInfo().setShippingContact(pickupContact);
        order.getShippingInfo().setShipOptions(shipMethod, "Prepaid", "");
        order.getShippingInfo().call_tag = pickupContact.getEmail();
        order.getShippingInfo().ss_call_tag = "1";

        try {
            AddressManager.checkAndCorrectAddress(pickupAddress, "UPS.GND");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Invalid pickup address for UPS Ground - check values and resubmit");
        }

        if (!LineItem.SKUExists(client_id, "CALLTAG-EMAIL", true)) {
            //create it
            Inventory item = new Inventory(client_id);
            item.price = 0.00f;
            item.description = "Calltag (UPS Returns Service - Label Emailed to Customer) service placeholder";
            item.inventory_num = "CALLTAG-EMAIL";
            item.is_active = 0;
            item.is_auto_inventory = 0;

            item.dbsave( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

            HibUtils.commit(HibernateSession.currentSession());
        }
        Inventory item = Inventory.dbloadByPart("CALLTAG-EMAIL", client_id);
        item.is_auto_inventory = 0;
        item.is_active = 0;
            item.price = 0.00f;
        item.description = "Calltag (UPS Returns Service - Label Emailed to Customer) service placeholder";
        item.addToAvailabilityAtFacility( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), 1, order.getFacilityCode());
        item.dbupdate( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());


        HibUtils.commit(HibernateSession.currentSession());


        order.addLineItem("CALLTAG-EMAIL", "1", "0.00", "0.00", "UPS Return Email Label Service", "", "" + predictedWeight + " lbs");
        order.order_type = order_type;
        order.order_refnum = orderReference + "-RETURN-LABEL";
        order.noduplicates = false;
        order.noVirtualOnlyOrders = false;
        order.order_type = "OWD Order Entry";
        order.printSlip = false;
        return order;

    }

    public Order createCallTagOrder(Address pickupAddress, Contact pickupContact, String shipMethod, float predictedWeight) throws Exception {
        Order order = createOrderFromOrderStatus(Client.getClientForID(client_id).getClientPolicy().createInitializedOrder(), false);

        if (!(shipMethod.startsWith("UPS"))) {
            throw new Exception("Invalid ship method " + shipMethod + " requested; must be a UPS method");
        }
        order.getShippingInfo().setShippingAddress(pickupAddress);
        order.getShippingInfo().setShippingContact(pickupContact);
        order.getShippingInfo().setShipOptions(shipMethod, "Prepaid", "");
        order.getShippingInfo().call_tag = this.OWDorderReference;
        order.getShippingInfo().ss_call_tag = "1";

        try {
            AddressManager.checkAndCorrectAddress(pickupAddress, "UPS.GND");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Invalid pickup address for UPS Ground - check values and resubmit");
        }

        if (!LineItem.SKUExists(client_id, "CALLTAG", true)) {
            //create it
            Inventory item = new Inventory(client_id);
            item.price = 0.00f;
            item.description = "Calltag (UPS Returns Service - 3 Pickup Attempts) service placeholder";
            item.inventory_num = "CALLTAG";
            item.is_active = 0;
            item.is_auto_inventory = 0;

            item.dbsave( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

            HibUtils.commit(HibernateSession.currentSession());
        }
        Inventory item = Inventory.dbloadByPart("CALLTAG", client_id);
        item.is_auto_inventory = 0;
        item.is_active = 0;
            item.price = 0.00f;
        item.description = "Calltag (UPS Returns Service - 3 Pickup Attempts) service placeholder";
        item.addToAvailabilityAtFacility( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), 1, order.getFacilityCode());
        item.dbupdate( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

        HibUtils.commit(HibernateSession.currentSession());


        order.addLineItem("CALLTAG", "1", "0.00", "0.00", "UPS Return Pickup Service", "", "" + predictedWeight + " lbs");
        order.order_type = order_type;
        order.order_refnum = orderReference + "-RETURN-CALLTAG";
        order.noduplicates = false;
        order.noVirtualOnlyOrders = false;
        order.order_type = "OWD Order Entry";
        order.printSlip = false;
        return order;

    }

    public OrderStatus(String orderID, String clientID)

    {

        Connection cxn = null;

        ////////log.debug("getting OS for id "+orderID);

        try {

            cxn = ConnectionManager.getConnection();


            dbload(cxn, orderID, clientID);

        } catch (Exception ex)

        {

            ex.printStackTrace();

        } finally

        {


            try {
                cxn.close();
            } catch (Exception e) {
            }

        }


    }


    static public Vector getOrderStatusByKey(String byType, String key, String clientID)

    {

        return getOrderStatusByKey(byType, key, clientID, true);
    }

    static public Vector getOrderStatusByKey(String byType, String key, String clientID, boolean excludeBackorders)

    {


        Vector<String> orderIDs = new Vector<String>();

        Vector<OrderStatus> orders = new Vector<OrderStatus>();


        String whereStmt = null;

        if (byType.equals(kByBillLastName))

        {

            whereStmt = "where bill_last_name like \'" + key + "\'";


        } else if (byType.equals(kByShipLastName))

        {

            whereStmt = "where ship_last_name like \'" + key + "\'";


        } else if (byType.equals(kByBillZip))

        {

            whereStmt = "where bill_zip like \'" + key + "\'";


        } else if (byType.equals(kByShipZip))

        {

            whereStmt = "where ship_zip like \'" + key + "\'";


        } else if (byType.equals(kByOrderRef))

        {

            whereStmt = "where order_num = \'" + key + "\'";


        } else if (byType.equals(kByOrderID))

        {

            whereStmt = "where order_id = " + key + "";


        } else if (byType.equals(kByOrderClientReference))

        {

            whereStmt = "where order_refnum = \'" + key + "\'";


        } else if (byType.equals(kByEmail))

        {

            whereStmt = "where bill_email_address = \'" + key + "\'";


        } else {

            return orders;

        }


        if (clientID != null)

            whereStmt = whereStmt + " AND client_fkey = " + clientID + (excludeBackorders ? " and is_backorder=0 " : "");


        Connection cxn = null;

        Statement stmt = null;

        ResultSet rs = null;


        try {

            cxn = ConnectionManager.getConnection();


            stmt = cxn.createStatement();


            //   //log.debug("select order_id from owd_order "+whereStmt+" order by order_id DESC");
            stmt.execute("select order_id from owd_order " + whereStmt + " order by order_id DESC");

            rs = stmt.getResultSet();

            while (rs.next())

            {

                orderIDs.addElement(rs.getString(1));


            }

            rs.close();

            stmt.close();


            for (int i = 0; i < orderIDs.size(); i++)

            {
                //  //log.debug("adding order id "+orderIDs.elementAt(i));
                orders.addElement(new OrderStatus(cxn, (String) orderIDs.elementAt(i)));

            }


        } catch (Exception ex)

        {


        } finally

        {

            try {
                rs.close();
            } catch (Exception e) {
            }

            try {
                stmt.close();
            } catch (Exception e) {
            }

            try {
                cxn.close();
            } catch (Exception e) {
            }

        }


        return orders;


    }


    public void voidOrder(Connection cxn) throws Exception {
        Statement stmt = null;
        try {

            if ((is_void)) {
                throw new Exception("Order is already voided - cannot void a second time!");
            } else {
                if (is_posted) {
                    throw new Exception("The order has already been released for shipping - unpost the order before attempting to void it.");
                } else {


                    String esql = "exec void_order " + order_id;

                    stmt = cxn.createStatement();

                    stmt.executeUpdate(esql);

                    /***************************
                     COMMIT
                     ***************************/
                    cxn.commit();
                }
            }
        } catch (Throwable th) {
            Exception ex;

            if (th instanceof Exception) {
                ex = (Exception) th;
            } else {
                ex = new Exception(th.toString());
            }
            ex.printStackTrace();
            cxn.rollback();
            throw ex;
        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }

        }

    }

    public Map<String,String> getBillingCharges() throws Exception{
        Map<String,String> charges = new TreeMap<String, String>();
        try{
            String sql = "select * from sp_bill_pickpack where oid = :orderId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            q.setParameter("orderId",order_id);
            List results = q.list();
            if(results.size()>0){
                Map data = (Map) results.get(0);
                double total =0.0;
                charges.put("Picks",data.get("Additional Picks").toString());
                charges.put("Packs",data.get("packages").toString());
                charges.put("Inserts",data.get("Inserts").toString());
                charges.put("Packaging Fees",data.get("packageCost").toString());
                total += Double.parseDouble(data.get("packageCost").toString());
                charges.put("International Fees",data.get("International Fees").toString());
                total +=  Double.parseDouble(data.get("International Fees").toString());
                charges.put("Pack Fees",data.get("Order Fees").toString());
                total +=  Double.parseDouble(data.get("Order Fees").toString());
                charges.put("Pick Fees",data.get("Additional Pick Fees").toString());
                total +=  Double.parseDouble(data.get("Additional Pick Fees").toString());
                charges.put("Insert Fees",data.get("Insert Fees").toString());
                total += Double.parseDouble(data.get("Insert Fees").toString());
                charges.put("Order Change Fees",data.get("Order Change Fees").toString());
                total +=  Double.parseDouble(data.get("Order Change Fees").toString());
                charges.put("Total",total+"");
            }



        }catch(Exception e){
                e.printStackTrace();
        }
        return charges;
    }

    private List<OwdLineItemExemptions> getLineItemExemptionsFromOrderId(String orderId){
        List<OwdLineItemExemptions> lines = new ArrayList<>();

        try{
            Criteria criteria = HibernateSession.currentSession().createCriteria(OwdLineItemExemptions.class);
            criteria.add(Restrictions.eq("orderFkey", Integer.parseInt(orderId)));
            lines = (List<OwdLineItemExemptions>) criteria.list();

        }catch(Exception e){
            e.printStackTrace();
        }

        return lines;
    }
    private List<suppliesBean> getSuppliesUsedForOrderId(String orderId){
        List<suppliesBean> supplies = new ArrayList<>();
        try{
            String sql = "execute getSuppliesUsedForOrderId :orderId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            q.setParameter("orderId",orderId);
            List l = q.list();
            for(Object row: l){
                Map data = (Map) row;
                suppliesBean sb = new suppliesBean();
                sb.setSku(data.get("inventory_num").toString());
                sb.setDescription(data.get("description").toString());
                sb.setType(data.get("group_type").toString());
                sb.setQuantity(data.get("quantity").toString());
                supplies.add(sb);
            }
        }catch (Exception e ){
           e.printStackTrace();
        }
        return supplies;
    }

}
