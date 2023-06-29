//updated on Jun 13, 2002 by liheng AGAIN
package com.owd.core.business.order;

import com.owd.LogableException;
import com.owd.OWDShippingAPI.Returns.ReturnUtilities;
import com.owd.core.*;
import com.owd.core.business.Address;
import com.owd.core.business.Client;
import com.owd.core.business.Contact;
import com.owd.core.business.EventFeeds;
import com.owd.core.business.order.beans.lineItemExemptions;
import com.owd.core.business.order.distributedOrderManagement.DOMUtilities;
import com.owd.core.business.order.distributedOrderManagement.Model.domFillable;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.payment.FinancialTransaction;
import com.owd.core.ruleAdapters.DroolsDescriptor;
import com.owd.core.ruleAdapters.OrderPostCreateRuleAdapter;
import com.owd.core.ruleAdapters.OrderRuleAdapter;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.fedEx.SmartPostReturn;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import com.owd.hibernate.generated.WInvRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

/*
	Order lifespan
	
	createorder
	->



*/

public class Order implements Serializable, OrderRuleAdapter, OrderPostCreateRuleAdapter {
    private final static Logger log =  LogManager.getLogger();

    Contact customerContact = new Contact();
    Address customerAddress = new Address();
    ShippingInfo shipInfo = new ShippingInfo();
   public  List<lineItemExemptions> lineExemptions= new ArrayList<lineItemExemptions>();
    String facilityCode = "XXX";
    String originalFacilityCode = "";

    public boolean isWorkOrder=false;

    @Override
    public String getClientReference() {
        return getClientOrderReference();
    }

    @Override
    public String getOWDReference() {
        return orderNum;
    }

    @Override
    public String getShipMethodName() {
        return getShippingInfo().carr_service;
    }

    // Sean 2020/2/5
    @Override
    public String getComments() {
        return getShippingInfo().comments;
    }

    @Override
    public void setGiftMessage(String messager) {
        gift_message = messager;
    }

    @Override
    public void setPostDateHoursDelay(int hours){
        postDateHoursDelay = hours;
    }



    @Override
    public void injectWorkOrder(String subject, String body) {

        try {
            PreparedStatement psedi = HibernateSession.getPreparedStatement("insert into case_queue (subject,body,brand,facility,clientid) VALUES (?,?,?,?,?);");
            psedi.setString(1, subject);
            psedi.setString(2, body);
            psedi.setString(3, getGroupName());
            psedi.setString(4, getFacilityCode());
            psedi.setInt(5, Integer.parseInt(clientID));
            psedi.executeUpdate();
            psedi.close();
            if (!testing) {
                HibUtils.commit(HibernateSession.currentSession());

            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
            LogableException le = new LogableException(ex, "Error saving work order","TS:"+Calendar.getInstance().getTimeInMillis(), ""+getClientID(), "order post creation rule "+orderNum+"/"+getClientOrderReference(), LogableException.errorTypes.INTERNAL);

        }
    }
    @Override
    public String getPONumber(){
        return po_num;
    }

    @Override
    public String getDamagedReturnItemsString(String orderRefNum, Integer ClientId){
        StringBuilder sb = new StringBuilder();
            try{
                String sql = "SELECT\n" +
                        "    dbo.owd_receive_item.inventory_num,\n" +
                        "    dbo.owd_receive_item.quantity,\n" +
                        "    dbo.owd_receive_item.item_status\n" +
                        "FROM\n" +
                        "    dbo.owd_receive\n" +
                        "LEFT OUTER JOIN\n" +
                        "    dbo.owd_receive_item\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_receive.receive_id = dbo.owd_receive_item.receive_fkey)\n" +
                        "RIGHT OUTER JOIN\n" +
                        "    dbo.owd_order\n" +
                        "ON\n" +
                        "    (\n" +
                        "        dbo.owd_receive.ref_num = dbo.owd_order.order_num)\n" +
                        "WHERE\n" +
                        "    (dbo.owd_receive_item.item_status = 'damaged' or return_reason like '%damaged%')\n" +
                        "AND dbo.owd_receive.client_fkey = :clientId " +
                        "AND dbo.owd_order.order_refnum = :orderRefNum ";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("orderRefNum",orderRefNum);

                q.setParameter("clientId",clientID);
                List results = q.list();
                for(Object row:results){
                    Object[] data = (Object[]) row;
                   sb.append(data[0].toString());
                    sb.append(":  ");
                    sb.append(Integer.parseInt(data[1].toString()));
                    sb.append("\r\n");

                    }
            }catch (Exception e){
                log.debug("returnLookupError",e.getMessage());
            }
        return sb.toString();
    }

    public String getFacilityCode() {
        return facilityCode;
    }

    public void setFacilityCode(String facilityCode) {
        this.facilityCode = facilityCode;
    }

    String facilityPolicy="";

    public String getFacilityPolicy() {
        return facilityPolicy;
    }

    public void setFacilityPolicy(String facilityPolicy) {
        this.facilityPolicy = facilityPolicy;
    }

    public String created_by = "";//added espeicially for Orion

    boolean droolsRulesetApplied = false;

    //added on Dec 09, 2002 to allow subscription
    public boolean create_auto_ship = false;
    public boolean is_auto_ship = false;
    public int auto_start_day = Calendar.getInstance().get(Calendar.DATE);
    public int auto_start_mon = Calendar.getInstance().get(Calendar.MONTH) + 1;
    public int interval_num = 3;
    public String interval_unit = "M";

    public String contactID = "";
    public String template = "";
    public String templatedata = "";

    public boolean shipping_taxable = true;

    public int auto_start_year = Calendar.getInstance().get(Calendar.YEAR);

    public String last_payment_error = "";
    public String last_error = "";
    public String hold_reason = "";
    public String group_name = "";
    public boolean completed = false;

    public boolean noduplicates = true;
    public boolean testing = false;

    //owd_line_item
    public Vector skuList = new Vector();
   // public Vector old_skuList = new Vector();

    //owd_order_track
    public Vector packageList = new Vector();

    //owd_payments
    public Vector paymentList = new Vector();

    public String coupon_code = "";

    public String ship_call_date = "";
    public String ship_operator = "";
    public String post_date = null;


    public float total_product_cost = 0;
    public float total_shipping_cost = 0;
    public float total_tax_cost = 0;
    public float total_order_cost = 0;
    public float total_gift_wrap = 0;
    public float order_balance = 0;
    public float tax_pct = 0;

    public float discount = 0;
    public float discount_pct = 0;

    public float credit = 0;

    public int is_paid = 0;
    public String paid_date = "";
    public float paid_amount = 0;

    public String bill_cc_type = "CC";
    public String old_bill_cc_type = "CC";

    public String cc_num = "";
    public int cc_exp_mon = 1;
    public int cc_exp_year = 2000;

    public String cc_auth_code = "";
    public String cc_trans_code = "";
    public double cc_auth_amount = 0.00;


    public String check_num = "";
    public String ck_acct = "";
    public String ck_bank = "";
    public String ck_number = "";

    public String ship_call_time = "";//not used

    public String is_gift = "0";
    public String gift_message = "";

    public String po_num = "";

    public String clientID = "";
    public String orderNum = "";
    public String order_refnum = "";
    public String old_refnum = order_refnum;
    public String order_type = "";

    public int is_void = 0;
    public int is_backorder = 0;
    public int is_future_ship = 0;
    public int old_future_ship = 0;

    public String backorder_order_num = null;

    public String customer_num = "";
    public String actual_order_date = "";

    public String payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
    public String backorder_rule = OrderXMLDoc.kBackOrderAll;
    public String validation_rule = OrderXMLDoc.kHoldOrder;

    public boolean forcePayment = false;

    public String orderID = "0";
    public int prt_pick_reqd = 0;
    public int prt_pack_reqd = 1;
    public int prt_invoice_reqd = 0;

    //1.1 API stuff
    public String shippercontact = "";
    public String shippercompany = "";
    public String shipperaddress_one = "";
    public String shipperaddress_two = "";
    public String shippercity = "";
    public String shipperstate = "";
    public String shipperzip = "";
    public String shipperphone = "";
    public String shipperacct = "";
    public String shipperbilling_ref = "";
    public String taxcontact = "";
    public String taxcompany = "";
    public String taxaddress_one = "";
    public String taxaddress_two = "";
    public String taxcity = "";
    public String taxstate = "";
    public String taxzip = "";
    public String taxphone = "";
    public String taxaccount = "";
    public int importerOfRecord = 0;
    public String companyOverride ="";
    public String nameOverride = "";
    public String phoneOverride = "";
    public String address1Override = "";
    public String address2Override = "";
    public String cityOverride = "";
    public String stateOverride = "";
    public String zipOverride = "";
    public String externalShippingKey = "";
    public boolean business_order = false;


    public String bestway = "";

    public boolean preserveShippingCosts = false;
    public boolean processCouponDiscount = true;

    public boolean printSlip = true;

    public String   avs_response = "";
    public String   cvv_response = "";

    private Properties ruleProperties = new Properties();


    public String cc_cvv_code = "";

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    private String keyCode = "";
    public String packInstructions = "";

    private Map<String, String> tagMap = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

    @Override
    public void setDDPFlag(int flag){
        setForceFedexTaxBillingToConsignee(flag);
    }
    //if the client has a third party account setup in their client setup this will cause that account ot be used for taxes and duties
    // If no account is setup or for other than fedex, it will force duties and taxes paid to be used.
    private int forceFedexTaxBillingToConsignee=0;


    public int getForceFedexTaxBillingToConsignee() {
        return forceFedexTaxBillingToConsignee;
    }

    public void setForceFedexTaxBillingToConsignee(int forceFedexTaxBillingToConsignee) {
        this.forceFedexTaxBillingToConsignee = forceFedexTaxBillingToConsignee;
    }
    
    @Override
    public void addTagToOrder(String name, String value)throws Exception{
        addTag(name,value);
    }
    

    public void addTag(String name, String value) throws Exception
    {
          if(name==null){throw new Exception("Custom value name cannot be null");}
        if(value==null){throw new Exception("Custom value contents cannot be null");}
        if(name.getBytes().length>254){throw new Exception("Custom value name must be 254 characters or less");}
        if(value.getBytes().length>640000){throw new Exception("Custom value contents must be 640000 characters or less");}
        Scanner scanner = new Scanner(name);
        String validationResult = scanner.findInLine("[^A-Za-z0-9_\\-\\.]+");
        if (validationResult != null) {
            // Invalid character found.
            throw new Exception("Invalid character found in custom value name: " + validationResult);
        }
        if(value.trim().getBytes().length<1){throw new Exception("Custom value contents must contain at least one non-whitespace character");}

        tagMap.put(name.toUpperCase(),value);

    }

    public boolean tagExists(String name)
    {
        return tagMap.containsKey(name);
    }

    public Map<String, String> getTagMap()
    {
        return tagMap;
    }



    public Order(String aclientID) {
        clientID = aclientID;

        try {
        String code = FacilitiesManager.getLocationCodeForClient(Integer.parseInt(clientID));
       if(!FacilitiesManager.isClientMultiFacility(Integer.parseInt(clientID)))
       {
           setFacilityCode(code);
           setFacilityPolicy(code);
       }  else
       {
           setFacilityCode(FacilitiesManager.getActiveFacilityCodes().get(0));
           setFacilityPolicy(getFacilityCode());
       }


        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public boolean hasGiftMessage() {

        if(gift_message == null) gift_message ="";
        if(gift_message.trim().length()>3)
        {
            return true;
        }
        return false;
    }

    public void setPackingInstructions(String instructions)
    {
        if(instructions != null)
        {
       packInstructions = instructions;
    }
    }


    public String getTagValue(String key) {
        return getTagMap().get(key);
    }


    public float getTotalCustomsValue()
    {
        float customsValue = 0.00f;
        try {
            for (int i = 0; i < skuList.size(); i++) {
                customsValue += ((LineItem) skuList.get(i)).dec_item_value;
            }

        }catch(Exception ex)
        {

        }
        return customsValue;
    }

    public boolean getIsUS() {
        if(getShippingAddress() == null)
            return true;
        String country = getShippingAddress().country;
        if (country.equalsIgnoreCase("USA")
                || country.equalsIgnoreCase("US") ||
                country.equalsIgnoreCase("UNITED STATES") ||
                country.equalsIgnoreCase("UNITED_STATES"))
            return true;
        else
            return false;

    }

    public void mergeItemPackingInstructionsToOrder()
    {
        String packInfo = "";
        List<String> pastInstructions= new ArrayList<String>();

        try {
            for (int i = 0; i < skuList.size(); i++) {
                String itemPackInfo = ((LineItem) skuList.get(i)).getInventory().packing_instructions;
                if(itemPackInfo!=null)
                {
                    if(itemPackInfo.length()>4)
                    {
                        packInfo = packInfo+"===== "+((LineItem) skuList.get(i)).client_part_no+" ======\r\n"+itemPackInfo+"\r\n";
                        pastInstructions.add(itemPackInfo.trim());

                    }
                }
            }
            if(packInfo.length()>0)
            {
                packInstructions = packInfo;
            }
        }catch(Exception ex)
        {

        }

    }



    public Order(OwdOrder order, boolean copyLineItems) throws Exception {
        customerContact = new Contact();
        customerContact.setName(order.getBillFirstName()+" "+order.getBillLastName());
        customerContact.setFax(order.getBillFaxNum());
        customerContact.setPhone(order.getBillPhoneNum());
        customerContact.setEmail(order.getBillEmailAddress());

        customerAddress = new Address();
        customerAddress.company_name = order.getBillCompanyName();
        customerAddress.address_one = order.getBillAddressOne();
        customerAddress.address_two = order.getBillAddressTwo();
        customerAddress.city = order.getBillCity();
        customerAddress.state = order.getBillState();
        customerAddress.zip = order.getBillZip();
        customerAddress.country = order.getBillCountry();

        shipInfo = new ShippingInfo();
        shipInfo.setShipOptions(order.getShipinfo().getCarrService(),order.getShipinfo().getCarrFreightTerms(),order.getShipinfo().getThirdPartyRefnum());
        shipInfo.call_tag = order.getShipinfo().getCallTag();
        shipInfo.customs_desc = order.getShipinfo().getCustomsDesc();
        shipInfo.ss_tracking = order.getShipinfo().isSsTracking()?"1":"0";
        shipInfo.whse_notes = order.getShipinfo().getWhseNotes();
        shipInfo.comments = order.getShipinfo().getComments();



          //owd_line_item
          skuList = new Vector();
         // public Vector old_skuList = new Vector();


          ship_operator = order.getSalesperson();

          bill_cc_type = "CC";

          cc_num = order.getCcNum();
          cc_exp_mon = order.getCcExpMon();
          cc_exp_year = order.getCcExpYear();


        if(copyLineItems)
        {

          total_tax_cost = order.getTaxAmount().floatValue();
          total_shipping_cost = order.getShipHandlingFee().floatValue();
           for(OwdLineItem item:order.getLineitems())
           {
               addLineItem(item.getInventoryNum(),item.getQuantityRequest()+item.getQuantityBack(),item.getPrice().floatValue(),
                       0.00f,item.getDescription(),item.getItemColor(),item.getItemSize());
           }

        }
        recalculateBalance();
          po_num = order.getPoNum();
          clientID = order.getClientFkey()+"";
          order_refnum = order.getOrderNum();
          order_type = order.getOrderType();


          prt_pick_reqd = order.isPrtPickReqd()?1:0;
          prt_pack_reqd = order.isPrtPackReqd()?1:0;
          prt_invoice_reqd = order.isPrtInvoiceReqd()?1:0;
        if((prt_pick_reqd+prt_pack_reqd+prt_invoice_reqd)<1)
        {
            prt_pack_reqd=1;
        }

        facilityCode=order.getFacilityCode();
        facilityPolicy=order.getFacilityPolicy();
        business_order= order.isBusinessOrder();
    }

    /**
     * Returns client ID.
     *
     * @return
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * JWH
     * @param sku
     * @return
     */
    public boolean containsAnySKU(List<String> sku) {

       // log.debug("In contains any SKU, got array size:"+sku.size());
        for (int i = 0; i < sku.size(); i++) {
          //  log.debug(i);
           // log.debug("containsAnySKU: "+i+" : "+sku.get(i));
            if (containsSKU(sku.get(i))) {
                return true;
            }
        }
        return false;
    }


    public int getRequestedUnitsofSkusInList(List<String> skus) {

         int units = 0;
        // log.debug("In contains any SKU, got array size:"+sku.size());
        for (LineItem line:(Vector<LineItem>)skuList) {
            if(skus.contains(line.client_part_no))
            {
                units += line.quantity_request;
            }

        }
        return units;
    }

    /**
     * JWH
     * @param sku
     * @return
     */
    @DroolsDescriptor(description = "Contains all included Skus", parameterType = "String", returnType = "boolean")
    public boolean containsAllSKU(List<String> sku) {

        for (int i = 0; i < sku.size(); i++) {
            if (!containsSKU(sku.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * JWH
     * @param sku
     * @return
     */
    public boolean notContainsAnySKU(List<String> sku) {

        for (int i = 0; i < sku.size(); i++) {
            if (!containsSKU(sku.get(i))) {
                return true;
            }
        }
        return false;
    }



    /**
     * JWH
     * @param sku
     * @return
     */
    public boolean notContainsAllSKU(List<String> sku) {

        for (int i = 0; i < sku.size(); i++) {
            if (containsSKU(sku.get(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * Returns shipping info whse_notes check for "Automatic Delivery"
     * @return
     */
    public boolean isAutoDelivery() {
        try {
            return getShippingInfo().whse_notes.contains("AUTOMATIC DELIVERY");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *
     * @return
     */
    public int getRecurrenceCount() {

        int recurrence = 0;

        try {
            if (isAutoDelivery()) {
                recurrence = 1;
                ResultSet rs = HibernateSession.getResultSet("select count(distinct(order_id)) from owd_order join owd_order_ship_info s on order_id=order_fkey\n" +
                        "where client_fkey=146 and is_void=0 and whse_notes like '%AUTOMATIC DELIVERY%' \n" +
                        "and bill_first_name='" + OWDUtilities.getFirstNameFromWholeName(this.getBillingContact().getName()) + "' and " +
                        "bill_last_name='" + OWDUtilities.getLastNameFromWholeName(this.getBillingContact().getName()) + "' and " +
                        "bill_address_one='" + this.getBillingAddress().address_one + "' and datediff(day,getdate(),post_date)>(-1*8*30)");
                if (rs.next()) {
                    recurrence = rs.getInt(1) + 1;
                }
            }
        } catch (Exception e) {
        }
        return recurrence;
    }

    /**
     * Puts property or updates if it already exists.
     * JWH
     *
     * @param name
     * @param value
     */
    public void setProperty(String name, String value) {

        if (ruleProperties.getProperty(name) == null) {
            ruleProperties.put(name, value);
        } else {
            ruleProperties.setProperty(name, value);
        }
    }

    /**
     * Returns property value.
     * JWH
     *
     * @param name
     * @return
     */
    public String getProperty(String name) {
        return ruleProperties.getProperty(name);
    }

    /**
     * Check if property exists. If it does returns value, otherwise returns default value.
     * This enable drools to handle different types if there is no property entry.
     * JWH
     *
     * @param name
     * @param defaultValue
     * @return
     */
    public String getProperty(String name, String defaultValue) {
        String value = ruleProperties.getProperty(name);

        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public void sendMail(String title, String message, String emailAddress) {
        try {
            Mailer.sendMail(title, message, emailAddress);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    public long getStockLevelForInventoryId(int iid, String facilityCode)
    {
        try {
           return  OrderUtilities.getAvailableInventory(""+iid, FacilitiesManager.getFacilityForCode(facilityCode).getId());
        }catch(Exception ex)
        {
            return 0;
        }
    }



    public Object executeRuleMethod(String methodName, List<Object> parms) {
        Class<?>[] types = this.getTypes(methodName, parms);

        return execMethod(methodName, types, parms);
    }

    private Class<?>[] getTypes(String methodName, List<Object> parms) {

        try {
            Class<?>[] types = new Class<?>[parms.size()];

            for(int i=0; i < parms.size(); i++) {
                if ("equals".equals(methodName))
                    types[i] = Object.class;
                else {
                    types[i] = parms.get(i).getClass();
                }

            }
            return types;
        } catch (Exception e) {
            System.err.println("Error RuleAdapter.getTypes: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param methodName
     * @param types
     * @param values
     * @return
     */
    private Object execMethod(String methodName, Class<?>[] types, List<Object> values) {

        try {
            this.getClass().getMethods();
            Method m = this.getClass().getMethod(methodName, types);
            return m.invoke(this, values);
        } catch (Exception e) {
            System.err.println("Error RuleAdapter.execMethod: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean withinDateRange(String startDate, String endDate) {

        try {
            SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");

            Date dtStart = dt1.parse(startDate);
            Date dtEnd = dt1.parse(endDate);

            Calendar cs = new GregorianCalendar();
            cs.setTime(dtStart);

            Calendar ce = new GregorianCalendar();
            ce.setTime(dtEnd);

            Calendar now = GregorianCalendar.getInstance();
            Date nowd = now.getTime();

            if (now.getTimeInMillis() >= cs.getTimeInMillis() && now.getTimeInMillis() < ce.getTimeInMillis()) {
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean withinDateRangeUsingFormat(String startDate, String endDate, String format) {

        try {
            SimpleDateFormat dt1 = new SimpleDateFormat(format);

            Date dtStart = dt1.parse(startDate);
            Date dtEnd = dt1.parse(endDate);

            Calendar cs = new GregorianCalendar();
            cs.setTime(dtStart);

            Calendar ce = new GregorianCalendar();
            ce.setTime(dtEnd);

            Calendar now = GregorianCalendar.getInstance();
            Date nowd = now.getTime();

            if (now.getTimeInMillis() >= cs.getTimeInMillis() && now.getTimeInMillis() < ce.getTimeInMillis()) {
                return true;
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * Added for drools use JWH
     * @param sku
     */
    public void addInsertItemIfAvailableRecursive(List<String> sku) {
        try {
            for (int i = 0; i < sku.size(); i++) {
                addInsertItemIfAvailable(sku.get(i), 1);

                if (!containsSKU(sku.get(i))) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Added for drools use JWH
     * @param sku
     */
    public void addInsertItemIfAvailableDrools(String sku, int quantity) {
        try {
            addInsertItemIfAvailable(sku, quantity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns discount.
     *
     * @return
     */
    public float getDiscount() {
        return this.discount;
    }

    /**
     * Returns order_refnum
     *
     * @return
     */
    public String getOrderRefNum() {
        return order_refnum;
    }

    public void copyNewOrderVars(Order toOrder) throws Exception {
        if (!(orderID.equals("0"))) {
            throw new Exception("Can't copy from saved order");
        }
        toOrder.customerContact = Contact.createFromStorableString(customerContact.toStorableString());
        toOrder.customerAddress = Address.createFromStorableString(customerAddress.toStorableString());
        shipInfo.copyNewSIVars(toOrder.shipInfo);

        toOrder.last_payment_error = last_payment_error;
        toOrder.last_error = last_error;
        toOrder.hold_reason = hold_reason;
        toOrder.completed = completed;

        toOrder.testing = testing;

        //owd_line_item
        toOrder.skuList = new Vector();

        //owd_order_track
        toOrder.packageList = new Vector();

        //owd_payments
        toOrder.paymentList = new Vector();

        toOrder.coupon_code = coupon_code;

        toOrder.ship_call_date = ship_call_date;
        toOrder.ship_operator = ship_operator;
        toOrder.post_date = post_date;


        toOrder.total_product_cost = total_product_cost;
        toOrder.total_shipping_cost = total_shipping_cost;
        toOrder.total_tax_cost = total_tax_cost;
        toOrder.total_order_cost = total_order_cost;
        toOrder.total_gift_wrap = total_gift_wrap;
        toOrder.order_balance = order_balance;
        toOrder.tax_pct = tax_pct;

        toOrder.discount = discount;


        toOrder.is_paid = is_paid;
        toOrder.paid_date = paid_date;
        toOrder.paid_amount = paid_amount;

        toOrder.bill_cc_type = bill_cc_type;

        toOrder.cc_num = cc_num;
        toOrder.cc_exp_mon = cc_exp_mon;
        toOrder.cc_exp_year = cc_exp_year;

        toOrder.ship_call_time = ship_call_time;//not used

        toOrder.is_gift = is_gift;
        toOrder.gift_message = gift_message;

        toOrder.po_num = po_num;
        toOrder.clientID = clientID;
        toOrder.orderNum = orderNum;
        toOrder.order_refnum = order_refnum;
        toOrder.old_refnum = old_refnum;
        toOrder.order_type = order_type;
        toOrder.credit = credit;

        toOrder.is_void = is_void;
        toOrder.is_backorder = is_backorder;
        toOrder.is_future_ship = is_future_ship;

        toOrder.backorder_order_num = backorder_order_num;

        toOrder.customer_num = customer_num;
        toOrder.actual_order_date = actual_order_date;

        toOrder.payment_status = payment_status;
        toOrder.backorder_rule = backorder_rule;
        toOrder.validation_rule = validation_rule;

        toOrder.forcePayment = forcePayment;

        toOrder.orderID = orderID;
        toOrder.prt_pick_reqd = prt_pick_reqd;
        toOrder.prt_pack_reqd = prt_pack_reqd;
        toOrder.prt_invoice_reqd = prt_invoice_reqd;
        toOrder.template = template;

        //1.1 API stuff
        toOrder.shippercontact = shippercontact;
        toOrder.shippercompany = shippercompany;
        toOrder.shipperaddress_one = shipperaddress_one;
        toOrder.shipperaddress_two = shipperaddress_two;
        toOrder.shippercity = shippercity;
        toOrder.shipperstate = shipperstate;
        toOrder.shipperzip = shipperzip;
        toOrder.shipperphone = shipperphone;
        toOrder.shipperacct = shipperacct;
        toOrder.shipperbilling_ref = shipperbilling_ref;

        toOrder.taxcontact = taxcontact;
        toOrder.taxcompany = taxcompany;
        toOrder.taxaddress_one = taxaddress_one;
        toOrder.taxaddress_two = taxaddress_two;
        toOrder.taxcity = taxcity;
        toOrder.taxstate = taxstate;
        toOrder.taxzip = taxzip;
        toOrder.taxphone = taxphone;
        toOrder.taxaccount = taxaccount;
        toOrder.importerOfRecord = importerOfRecord;

        toOrder.bestway = bestway;
        toOrder.contactID = contactID;
        toOrder.cc_cvv_code = cc_cvv_code;
        toOrder.facilityPolicy = facilityPolicy;
        toOrder.facilityCode = facilityCode;
        toOrder.group_name = group_name;
        toOrder.business_order = business_order;



    }

    public String getID() {
        return orderID;
    }

    public static void main(String[] args) throws Exception {
       // System.setProperty("com.owd.environment","test");

        Order order = new Order("55");
        order.addLineItem("Ebay Items","1","0.00","0.00","","","");
        order.addLineItem("LotTesting","5","2.00","0.00","","","");
        ShippingInfo info = new ShippingInfo();
        Address shipa = new Address();
        shipa.company_name = "testing";
        shipa.setAddress_one("10 1st Ave e");
        shipa.setCity("Mobridge");
        shipa.setState("SD");
        shipa.setZip("57601");
        info.setShippingAddress(shipa);
        Contact c = new Contact();
        c.setName("Test Order");
        info.setShippingContact(c);
        order.setShippingInfo(info);

        order.nameOverride = "Shipping Name";
        order.phoneOverride = "605-845-9999";
        order.address1Override = "10 1st Ave E";
        order.address2Override = "";
        order.stateOverride = "SD";
        order.cityOverride = "Mobridge";
        order.zipOverride = "57601";
        order.business_order = true;
        order.holdNewOrder();


        order.backorder_rule = OrderXMLDoc.kRejectBackOrder;
        order.setShippingMethodName("Ground");
       // order.group_name = "Amazon";
        order.bill_cc_type = "CK";
        order.is_paid = 1;
        order.recalculateBalance();
        try{

            System.out.println(order.getCountByLineItemInventoryField("defaultFacilityCode","DC1",true));

                System.out.println(order.getCountByLineItemInventoryField("defaultFacilityCode","DC1",true)==order.getQuantityForSKU("Ebay Items")+order.getQuantityForSKU("LotTesting"));
            order.saveNewOrder(OrderUtilities.kRequirePayment,false);
        } catch(Exception e){
            e.printStackTrace();
        }




        //order.updateShipMethodAfterTime("9:50","UPS 2nd Day Air");
        //SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
       // Calendar c = Calendar.getInstance();
       // c.add(Calendar.HOUR,-2);
       // Date now = parser.parse(parser.format( Calendar.getInstance().getTime()));
     //  System.out.println(order.getCountByLineItemInventoryField("inventory_num","z",true));
     //   System.out.println("thething".contains(""));

        //System.out.println( order.getRequestedUnitsofSkusWithInventoryTagValue("GIFTBAG349839", "1") );
        //System.out.println( order.getRequestedUnitsofSkusWithInventoryTagValue("GIFTBAG349840", "1") );
        //System.out.println(order.getDamagedReturnItemsString("12945491",489));

    }

    public boolean isNew() {
        return ("0".equals(orderID)) ? true : false;
    }

    public void recalculateBalance() throws Exception {

        total_product_cost = 0;

        for (int i = 0; i < skuList.size(); i++) {
            total_product_cost += ((LineItem) skuList.elementAt(i)).getLineTotal();
        }
        total_product_cost = OWDUtilities.roundFloat(total_product_cost);

        if (!preserveShippingCosts) {
            if (total_product_cost == 0) total_shipping_cost = 0;
        }

        float actual_tax_basis = total_product_cost + (Math.abs(discount) * -1);
        if (shipping_taxable) actual_tax_basis += total_shipping_cost;

        if (actual_tax_basis > 0) {
            if (total_tax_cost > 0 && tax_pct <= 0.00) {
                //  //log.debug("recalculating tax pct");
                tax_pct = OWDUtilities.roundFloat(total_tax_cost / actual_tax_basis, 4);
            }
            //  //log.debug("final tax pct="+tax_pct);
            total_tax_cost = OWDUtilities.roundFloat(tax_pct * actual_tax_basis);
        }
        total_order_cost = OWDUtilities.roundFloat(total_product_cost + total_shipping_cost + total_tax_cost + total_gift_wrap + (Math.abs(discount) * -1));


        order_balance = OWDUtilities.roundFloat((total_order_cost) - paid_amount);


    }


      public  void placeOnHoldAfterSaving(String message, String source) throws Exception {

        OrderStatus status = new OrderStatus(orderID);
        log.debug("reject order ID "+orderID+" with message "+message);
          if (!(status.is_posted))
          {
        try {

            if (!status.is_unpicked) {
                status.unpickOrder();
                Event.addOrderEvent(Integer.parseInt(orderID), Event.kEventTypeHandling, "Pick cleared as part of unposting process by " + source, source);
            } else {
                // throw new Exception("Order is not currently in posted status; cannot unpost this order.");
            }
           }
        catch (Exception exx) {
            exx.printStackTrace();
        } finally {

        }
               try {
            if (status.is_posted) {
                status.unpostOrder();
                Event.addOrderEvent(Integer.parseInt(orderID), Event.kEventTypeHandling, "Order unposted after posting by " + source, source);
            } else {
                throw new Exception("Order is not currently in posted status; cannot unpost this order.");
            }
        }
        catch (Exception exx) {
            exx.printStackTrace();
        } finally {

        }

           try{
            Query q = HibernateSession.currentSession().createSQLQuery("delete from owd_print_queue3 where order_id="+Integer.parseInt(orderID));

                q.executeUpdate();
               HibUtils.commit(HibernateSession.currentSession());
          }catch(Exception e){

              e.printStackTrace();
          }

        StringBuffer sb = new StringBuffer();
        sb.append(message);
        log.debug("adding event "+sb.toString());
        //add event
        Event.addOrderEvent(Integer.parseInt(orderID), Event.kEventTypeHandling, sb.toString(), source);
        log.debug("event added "+sb.toString());
        //notify project mailbox
          }

    }
    /**
     * getBackorderPercentage
     * returns percent of units in order that will be/are backordered
     */
    public float getBackorderPercentage() {
        float pct = 0;
        int request = 0;
        int backordered = 0;

        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();
            OrderUtilities.updateLineItemsForAvailability(cxn, skuList, backorder_rule, true, FacilitiesManager.getFacilityForCode(getFacilityCode()).getId());

            for (int i = 0; i < skuList.size(); i++) {
                LineItem item = (LineItem) skuList.elementAt(i);

                backordered += item.quantity_backordered;

                request += (item.quantity_request + item.quantity_backordered);
                item.resetQuantities();
            }
        } catch (Exception ex) {
             ex.printStackTrace();
        } finally {
            ConnectionManager.freeConnection(cxn);
        }


        if (request > 0)
            pct = ((float) backordered) / ((float) request);
        return pct;
    }

    /**
     * getLineCount
     * returns number of line items with shipping quantities > zero
     * *
     * ONLY VALID AFTER CALLING RECALCULATE QUANTITIES PRIOR TO POSTING ORDER
     */
    public int getLineCount() {
        int count = 0;

        for (int i = 0; i < skuList.size(); i++) {
            if (((LineItem) skuList.elementAt(i)).quantity_actual > 0)
                count++;
        }

        return count;
    }

    //set the line item's ordering quantity to the value in this order
    public void clearSkus() {
        skuList = new Vector();
    }

     protected int findNextInsertedLineItem() {
        for (int i = 0; i < skuList.size(); i++) {
            if (((LineItem) (skuList.elementAt(i))).insertedItem) {
                return i;
            }
        }
        return -1;
    }

     protected void deleteInsertedLineItems() throws ArrayIndexOutOfBoundsException {
          //method called internally when saveNewOrder fails
         //remove items marked as automatic inserts that will be reinserted when order is resaved
        int anIndex = findNextInsertedLineItem();
        while( anIndex > -1)
        {
            skuList.remove(anIndex);
            anIndex = findNextInsertedLineItem();
        }

         //remove formal kit BOM items (added in dbSaveInternal)
          Vector badkitlist = new Vector();
                for (int i = 0; i < skuList.size(); i++) {
                    if (((LineItem) skuList.elementAt(i)).parent_line != null) {
                        badkitlist.add(((LineItem) skuList.elementAt(i)));
                    }
                    ((LineItem) skuList.elementAt(i)).is_parent = 0;
                }

                skuList.removeAll(badkitlist);
    }

    public void deleteLineItemForSKU(String aSKU) throws ArrayIndexOutOfBoundsException {
       int anIndex = findLineItemForSKU(aSKU);
        while( anIndex > -1)
        {
            skuList.remove(anIndex);
            anIndex = findLineItemForSKU(aSKU);
        }
    }

    //set the line item's ordering quantity to the value in this order
    public void setLineItemReqQty(String aSKU, long new_qty) throws ArrayIndexOutOfBoundsException {
        int itemIndex = findLineItemForSKU(aSKU);
        ((LineItem) skuList.elementAt(itemIndex)).quantity_request = new_qty;
    }

    //find a line item in this order by SKU

    public int findLineItemForSKU(String aSKU) {
        for (int i = 0; i < skuList.size(); i++) {
            if (((LineItem) (skuList.elementAt(i))).client_part_no.equals(aSKU)) {
                return i;
            }
        }
        return -1;
    }

    //get the requested quantity of a line item for SKU
    public long getLineItemReqQtyForSKU(String aSKU) throws ArrayIndexOutOfBoundsException {
        int itemIndex = findLineItemForSKU(aSKU);
        return ((LineItem) skuList.elementAt(itemIndex)).quantity_request;

    }

    public void setShippingInfo(ShippingInfo ashipInfo) {
        shipInfo = ashipInfo;
    }


    public void setBillingAddress(Address anAddress) {
        customerAddress = anAddress;
    }

    public void setBillingContact(Contact aContact) {
        customerContact = aContact;
    }

    public ShippingInfo getShippingInfo() {
        return shipInfo;
    }

    public Address getShippingAddress() {
        return getShippingInfo().shipAddress;
    }

    public Contact getShippingContact() {
        return getShippingInfo().shipContact;
    }

    public Address getBillingAddress() {
        return customerAddress;
    }

    public String getCarrier() {
        return getShippingInfo().carr_service;
    }

    public Contact getBillingContact() {
        return customerContact;
    }


    synchronized public String saveNewOrder(String payment_rule, boolean testPayments) {
        last_error = "";
        //log.debug(":::saving order");
        //check that our arguments are valid
        if (null == payment_rule || (!(payment_rule.equals(OrderUtilities.kHoldForPayment)) && !(payment_rule.equals(OrderUtilities.kRequirePayment)))) {
            last_error = "Supplied payment rule invalid";
        }

        if(order_refnum!=null)
        {
            if(order_refnum.length()>80)
            {
                completed = false;
                last_error = ("Order reference cannot be more than 50 characters long");
                return null;
            }
        }
        //todo insert DOM info here
        originalFacilityCode = getFacilityCode();

        if(getFacilityCode().equals("CLOSEST")){
            try {
                List<domFillable> facilities = DOMUtilities.getClosestAndFillablePercentList(this);
                if(facilities.size()>0){
                    setFacilityCode(facilities.get(0).getLocCode());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if (getFacilityCode().equals("")) {
            try {
                log.debug("Order came in without default facility code");
                Connection cxn = ConnectionManager.getConnection();
                Client client = Client.getClientForID(cxn, clientID);
                setFacilityCode(client.defaultFacilityCode);
            } catch (Exception ex) {
                log.error(ex.getMessage(), "Failed to get default facility code");
            }

        }

        //try to save order
        if (!completed) {
            try {
                // //log.debug("4:bct=" + bill_cc_type);
                // //log.debug("4:ifs=" + is_future_ship);
             //    log.debug("::"+skuList);
                if(!FacilitiesManager.getActiveFacilityCodes().contains(getFacilityCode()))
                {
                    throw new Exception("Unable to save order; facility code not set");
                }
                dbsave();
         //       log.debug(":x:"+skuList);
                //mark
                //  //log.debug("5:ifs=" + is_future_ship);

                //  //log.debug("5:bct=" + bill_cc_type);
                 if(last_error.indexOf("reference already exists")>=0)
                 {
                     log.debug("FAILED DUE TO DUPLICATE REFERENCE");
                                               // voidOrder();
                                                resetOrderRef();
                                                for (int i = 0; i < skuList.size(); i++) {
                                                    ((LineItem) skuList.elementAt(i)).resetQuantities();
                                                }
                                                order_refnum = null;
                                                orderNum = "";
                                                is_void = 0;
                                                orderID = "0";
                                                is_future_ship = 0;
                                                is_backorder = 0;
                                                completed = false;
                                                post_date = null;
                                                bill_cc_type = old_bill_cc_type;
                                                last_error = last_payment_error + " " + last_error;
                     deleteInsertedLineItems();
                                            
                 }  else{
                //completed is true if no Exception was thrown when saving
                if (testing && completed) {
                    //log.debug("completed order!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    if (order_balance > 0f && (!(bill_cc_type.equals("CL"))) && (!(bill_cc_type.equals("PO"))) && (!(bill_cc_type.equals("FREE"))) && (!(bill_cc_type.equals("CK")))) {
                        if (cc_num != null) {
                            boolean validCard = false;
                            try {
                                validCard = CreditCard.isValid(CreditCard.parseDirtyLong(cc_num));
                            } catch (Exception ex) {
                            }

                            if (validCard) {
                                is_paid = 1;
                                paid_date = OWDUtilities.getSQLDateTimeForToday();
                                paid_amount = order_balance;
                                order_balance = 0;
                                recalculateBalance();
                                boolean isBackorder = false;
                                for (int i = 0; i < skuList.size(); i++) {
                                    if (((LineItem) skuList.elementAt(i)).quantity_backordered >= 1) {
                                        isBackorder = true;
                                    }
                                }

                                if (isBackorder == true) {
                                    backorder_order_num = ConnectionManager.getNextID("Order");
                                }

                            } else {
                                Event.addOrderEvent(new Integer(orderID).intValue(), Event.kEventTypeComment, "CC number not recognized as valid", null);

                                hold_reason = hold_reason + " CC number not valid";
                                is_future_ship = 1;
                            }
                        } else {
                            hold_reason = hold_reason + " CC number not valid";
                            is_future_ship = 1;
                        }
                    }else
                    {
                        order_balance=0;
                    }

                    if (is_future_ship == 0) {
                        post_date = OWDUtilities.getSQLDateTimeForToday();


                    }


                } else {
                    // //log.debug("bill_type="+bill_cc_type);
                    if (bill_cc_type.equals("CL")) {
                        boolean validCard = false;
                        try {
                            validCard = CreditCard.isValid(CreditCard.parseDirtyLong(cc_num));
                        } catch (Exception ex) {
                        }
                        //     //log.debug("valid:"+cc_num+":"+validCard+"-"+cc_auth_code+"-"+cc_trans_code+"_"+cc_auth_amount);
                        if (validCard && cc_trans_code.length() > 0 && cc_auth_code.length() > 0 && cc_auth_amount > 0.00) {
                            insertExternalCCTransaction();

                        } else {

                        }
                        order_balance=0;
                    }

                    if (is_future_ship == 1 && (!forcePayment)) {
                        //order placed on hold - do not process payment now
                        //    //log.debug("forcepayment for future ship not true");
                    } else if (order_balance > 0f) {
                        //order OK to go
                        //log.debug("checking if payment needed for bill type" + bill_cc_type);
                        //attempt payment
                        if (order_balance > 0 && (!(bill_cc_type.equals("CL"))) && (!(bill_cc_type.equals("PO"))) && (!(bill_cc_type.equals("FREE"))) && (!(bill_cc_type.equals("CK")))) {
                            //   //log.debug("attempting payment collection");
                            if (cc_num != null && !bill_cc_type.equals("ECK")) {
                                boolean validCard = false;
                                try {
                                    validCard = CreditCard.isValid(CreditCard.parseDirtyLong(cc_num));
                                } catch (Exception ex) {
                                }
                                //mark
                                if (validCard) {
                                    try {
                                        //   //log.debug("calling getFunds");
                                        getFunds(testPayments); //CC only right now
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        last_error = ex.getMessage();
                                        setOnHold();
                                        Event.addOrderEvent(new Integer(orderID).intValue(), Event.kEventTypeHandling, "Order set on hold : "+last_error, null);

                                    }
                                } else {
                                    Event.addOrderEvent(new Integer(orderID).intValue(), Event.kEventTypeComment, "CC number not recognized as valid", null);

                                    last_error = "CC number not valid:1";
                                    setOnHold();
                                }
                            } else if (bill_cc_type.equals("ECK")) {
                                //log.debug("doing echeck payment");

                                try {

                                    //   //log.debug("calling getFunds");
                                    getFunds(testPayments); //CC only right now
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    last_error = ex.getMessage();
                                    setOnHold();
                                    Event.addOrderEvent(new Integer(orderID).intValue(), Event.kEventTypeHandling, "Order set on hold : "+last_error, null);

                                }

                            } else {
                                Event.addOrderEvent(new Integer(orderID).intValue(), Event.kEventTypeComment, "CC number not recognized as valid", null);

                                last_error = "CC number not valid:2";
                                setOnHold();
                            }
                        }

                        //  //log.debug("6:bct=" + bill_cc_type);

                        if (order_balance > 0) {
                            //  //log.debug("payment not obtained - " + last_error + ":" + last_payment_error);
                            if (payment_rule.equals(OrderUtilities.kRequirePayment)) {
                                //payment problem
                                log.debug("FAILED DUE TO PAYMENT RULE");

                                try
                                {
                                    voidOrder();
                                }catch(Exception exv)
                                {
                                    exv.printStackTrace();
                                   
                                }

                                resetOrderRef();
                                for (int i = 0; i < skuList.size(); i++) {
                                    ((LineItem) skuList.elementAt(i)).resetQuantities();
                                }
                                  deleteInsertedLineItems();
                                order_refnum = null;
                                orderNum = "";
                                is_void = 0;
                                orderID = "0";
                                is_future_ship = 0;
                                is_backorder = 0;
                                completed = false;
                                post_date = null;
                                bill_cc_type = old_bill_cc_type;
                                last_error = last_payment_error + " " + last_error;
                            } else {
                                //hold order to resolve payment
                                setOnHold();

                                last_error = last_payment_error + " " + last_error + ": Payment not available:1";
                            }
                        }
                    }

                    //log.debug("7:bct=" + bill_cc_type);


                    if (is_future_ship == 0 && order_balance <= 0 && post_date == null && completed) {
                        postToWarehouse();
                    }


                }
                 }
                     if(last_error.length()>2)
                     {
                   try {
                       // Mailer.sendMail("saveorder error (" + clientID + ") " + order_refnum, last_error + "\n\nin saveNewOrder" , "owditadmin@owd.com", "neworders@owd.com");
                    } catch (Exception e) {
                    }
                     }
            } catch (Exception ex) {
                //order was rejected without saving
                 //todo
             //   log.debug(":xx:"+skuList);
                last_error = last_error + " " + ex.getMessage();
                ex.printStackTrace();

                if (!(ex instanceof DuplicateOrderException)) {
                    try {

            //    log.debug(":xx1:"+skuList);
                        setOnHold();

            //    log.debug(":xx2:"+skuList);
                    } catch (Exception e) {
                              e.printStackTrace();
                    }
                    try {
                        Mailer.sendMail("saveorder error (" + clientID + ") " + order_refnum, last_error + "\n\n" + OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "neworders@owd.com");
                    } catch (Exception e) {
                    }
                }
                order_refnum = null;

            }
        }

        //todo CC insert external transaction


        return order_refnum;


    }

    private void insertExternalCCTransaction() throws Exception {

        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();
            FinancialTransaction ft = new FinancialTransaction("0", orderID, "0", "", "", "", "",
                    order_balance, "", FinancialTransaction.TRANS_OK,
                    FinancialTransaction.TRANS_CC + "", 0, 0, "-1",
                    OWDUtilities.getLastNameFromWholeName(customerContact.getName()),
                    OWDUtilities.getFirstNameFromWholeName(customerContact.getName()),
                    customerAddress.address_one, customerAddress.address_two,
                    customerAddress.zip, customerAddress.company_name,
                    customerAddress.city, customerAddress.country,
                    customerAddress.state, customerContact.getEmail(), customerContact.getPhone(),
                    orderNum, "" + total_shipping_cost, "" + total_tax_cost, "", "", "", "", "",
                    OWDUtilities.getExpDateStr(cc_exp_mon, cc_exp_year), cc_num, "", "", "", "", "", "", 0, 0, OWDUtilities.getSQLDateTimeForToday());
            ft.amount = (float) cc_auth_amount;
            ft.type = "" + FinancialTransaction.transTypeAuthCaptureRequest;
            ft.proc_trans_id = cc_trans_code;

            ft.trans_time = OWDUtilities.getSQLDateTimeForToday();

            ft.proc_auth_code = cc_auth_code;

            ft.proc_avs_code = "";

            ft.status = FinancialTransaction.TRANS_OK;

            ft.error_reponse = "";

            ft.is_processed = 1;

            ft.proc_cvv_code = "";
            ft.dbsave(cxn);

            Event.addOrderEvent(cxn, new Integer(orderID).intValue(), Event.kEventTypeGeneral, "CC transaction " + cc_trans_code + " inserted from external data supplied by client", "XML API");
            cxn.commit();

        } catch (Exception e) {
            cxn.rollback();
            throw e;
        } finally {
            try {
                cxn.close();
            } catch (Exception ex) {
                throw ex;
            }
        }

    }

    public boolean validated() {
        if (bill_cc_type.equals("CK"))
            return false;

        if (cc_num.length() < 12)
            return false;

        if (cc_exp_year == 0 || cc_exp_mon == 0)
            return false;

        int count = 0;

        for (int i = 0; i < skuList.size(); i++) {
            if (((LineItem) skuList.elementAt(i)).quantity_request > 0)
                count++;
        }

        if (count < 1)
            return false;

        return true;
    }

    private void validateData() {
        boolean bad = false;


        if (bad)
            is_future_ship = 1;

    }

    public void addItem(LineItem item) {
        boolean notfound = true;

        /* for (int i = 0; i < skuList.size(); i++) {
            if (((LineItem) skuList.get(i)).client_part_no.equals(item.client_part_no)) {
                notfound = false;
                ((LineItem) skuList.get(i)).quantity_request += item.quantity_request;
            }
        }*/

        if (notfound) {
            skuList.addElement(item);
        }
    }

    public void addLineItem(String clientSKU, int quant, float itemPrice, float linePrice, String desc, String color, String size, float dvalue, String cdesc) throws Exception {

        LineItem item = new LineItem(clientSKU, quant, itemPrice, linePrice, desc, color, size, dvalue, cdesc);
        item.verifyInventory(clientID);
        skuList.addElement(item);
    }

    public void addLineItem(String clientSKU, int quant, float itemPrice, float linePrice, String desc, String color, String size) throws Exception {

        addLineItem(clientSKU, quant, itemPrice, linePrice, desc, color, size, "");

    }

    public void addLineItem(String clientSKU, int quant, float itemPrice, float linePrice, String desc, String color, String size, String note) throws Exception {

        LineItem item = new LineItem(clientSKU, quant, itemPrice, linePrice, desc, color, size);
        if (note != null) {
            if (note.trim().length() > 0) {
                item.long_desc = note.trim();
            }
        }
        item.verifyInventory(clientID);
        skuList.addElement(item);
    }

    public void addLineItem(String clientSKU, String quant, String itemPrice, String linePrice, String desc, String color, String size, String note) throws Exception {

        int i_quant = 0;
        float f_itemPrice = 0;
        float f_linePrice = 0;

        try {
            i_quant = new Integer(quant).intValue();
        } catch (Exception ex) {           ex.printStackTrace();
        }
        try {
            f_itemPrice = new Float(itemPrice).floatValue();
        } catch (Exception ex) {         ex.printStackTrace();
        }
        try {
            f_linePrice = new Float(linePrice).floatValue();
        } catch (Exception ex) {          ex.printStackTrace();

            f_linePrice = OWDUtilities.roundFloat(((float) i_quant) * f_itemPrice);
        }
        if (f_itemPrice > 0 && f_linePrice == 0) {
            f_linePrice = OWDUtilities.roundFloat(((float) i_quant) * f_itemPrice);
        }

        if (f_itemPrice > 0 && f_linePrice < 0) {
            f_linePrice = 0.00f;
        }

        addLineItem(clientSKU, i_quant, f_itemPrice, f_linePrice, desc, color, size, note);

    }

    public void addLineItem(String clientSKU, String quant, String itemPrice, String linePrice, String desc, String color, String size) throws Exception {

        int i_quant = 0;
        float f_itemPrice = 0;
        float f_linePrice = 0;

        try {
            i_quant = new Integer(quant).intValue();
        } catch (Exception ex) {         ex.printStackTrace();
        }
        try {
            f_itemPrice = new Float(itemPrice).floatValue();
        } catch (Exception ex) {          ex.printStackTrace();
        }
        try {
            f_linePrice = new Float(linePrice).floatValue();
        } catch (Exception ex) {         ex.printStackTrace();

            f_linePrice = OWDUtilities.roundFloat(((float) i_quant) * f_itemPrice);
        }
        if (f_itemPrice > 0 && f_linePrice == 0) {
            f_linePrice = OWDUtilities.roundFloat(((float) i_quant) * f_itemPrice);
        }

        if (f_itemPrice > 0 && f_linePrice < 0) {
            f_linePrice = 0.00f;
        }

        addLineItem(clientSKU, i_quant, f_itemPrice, f_linePrice, desc, color, size);

    }

    public LineItem getLineItem(String clientSKU, String quant, String itemPrice, String linePrice, String desc, String color, String size) throws Exception {

        int i_quant = 0;
        float f_itemPrice = 0;
        float f_linePrice = 0;

        try {
            i_quant = new Integer(quant).intValue();
        } catch (Exception ex) {          ex.printStackTrace();
        }
        try {
            f_itemPrice = new Float(itemPrice).floatValue();
        } catch (Exception ex) {         ex.printStackTrace();
        }
        try {
            f_linePrice = new Float(linePrice).floatValue();
        } catch (Exception ex) {        ex.printStackTrace();

            f_linePrice = OWDUtilities.roundFloat(((float) i_quant) * f_itemPrice);
        }
        if (f_itemPrice > 0 && f_linePrice == 0) {
            f_linePrice = OWDUtilities.roundFloat(((float) i_quant) * f_itemPrice);
        }

        if (f_itemPrice > 0 && f_linePrice < 0) {
            f_linePrice = 0.00f;
        }

        LineItem item = new LineItem(clientSKU, i_quant, f_itemPrice, f_linePrice, desc, color, size);
        item.verifyInventory(clientID);

        return item;
    }


    synchronized public void dbsave() throws Exception {
            dbsaveInternal();
    }

    public boolean noVirtualOnlyOrders = true;
     public String orderReferencePrefix = "1";
    //for NEW orders only

    protected void dbsaveInternal() throws Exception {
        PreparedStatement stmt;
        String esql;
        boolean isBackorder = false;

        old_future_ship = is_future_ship;
        old_refnum = order_refnum;


        if (completed) {
            throw new Exception("order already done");
        } else {
            Connection cxn = ConnectionManager.getConnection();
             try
             {
                 verifyAddressLinesNotMissing();


            try {

                if (0 >= skuList.size()) {
                    throw new Exception("No valid line items in order - order could not be saved!");
                }

                //    fixSKUList(); //combine dupes and expand kits



                if (noduplicates) {
                    if (orderRefnumExists(cxn, order_refnum))
                        throw new DuplicateOrderException("Order reference already exists");
                }

                //must have a valid Client object
                Client client = Client.getClientForID(cxn, clientID);

                if (client == null)
                    throw new Exception("Client ID (" + clientID + ") is not valid");

                //get the order number so we can retrieve the generated identity ID after saving
                orderNum = ConnectionManager.getNextID("Order");

                //if the client did not provide a refnum, generate one based on the OWD ordernum
                if ("".equals(order_refnum) || "0".equals(order_refnum) || (null == order_refnum)) {

                        order_refnum = orderReferencePrefix + orderNum;

                }



                if (coupon_code != null) {
                    if (coupon_code.length() > 0 && processCouponDiscount) {
                        Coupon.useCoupon(cxn, coupon_code, clientID);
                    }
                }

                int tempIDIndex = 0;

                Vector kitList = new Vector();

                for (int i = 0; i < skuList.size(); i++) {
                    //log.debug("checking for kit");
                    LineItem item = (LineItem) skuList.elementAt(i);
                    Map reqItemMap = LineItem.getRequiredItemsForInventoryID(new Integer(item.inventory_fkey));
                    //log.debug("got kit items "+reqItemMap);
                    if (reqItemMap.size() > 0) {

                        //log.debug("got kit list "+reqItemMap.size());
                        //is a kit
                        tempIDIndex++;
                        item.tempID = tempIDIndex;
                        item.is_parent = new Integer(1);

                        Object[] keys = reqItemMap.keySet().toArray();
                        //log.debug("got keys "+keys);
                        for (int mapIndex = 0; mapIndex < keys.length; mapIndex++) {
                            //log.debug("map index "+mapIndex);
                            Inventory invRecord = Inventory.getInventoryForID("" + ((Integer) (keys[mapIndex])));

                            LineItem kititem = new LineItem(invRecord.inventory_num, ((int) (
                                    ((Integer) reqItemMap.get(((Integer) (keys[mapIndex])))).intValue() * item.quantity_request)),
                                    0.00f, 0.00f, invRecord.description, invRecord.item_color, invRecord.item_size);
                            kititem.setInventory(invRecord);
                            kititem.verifyInventory(clientID);
                            kititem.parent_line = item.tempID;
                            kitList.add(kititem);

                        }
                        //log.debug("kit done");

                    }

                }

                skuList.addAll(kitList);

                //todo lineitemupdate call
                //start new line item update section
                Map skuMap = OrderUtilities.updateLineItemsForAvailability(cxn, skuList, backorder_rule, true, FacilitiesManager.getFacilityForCode(getFacilityCode()).getId() );
                

                if (!containsPhysicalItem() && noVirtualOnlyOrders)
                    throw new Exception("Order cannot be saved - no physical items included in shipment");


                for (int i = 0; i < skuList.size(); i++) {
                    LineItem item = (LineItem) skuList.elementAt(i);

                    if (item.force_backorder_quantity && item.quantity_backordered > 0) {
                        isBackorder = true;
                        if (backorder_rule.equals(OrderXMLDoc.kRejectBackOrder)) {
                            //note: zero inventory thrown
                            throw new Exception("Insufficient inventory for SKU " + item.client_part_no);
                        }

                    } else {
                        log.debug("cking backorder");
                        if (item.quantity_backordered > 0) {
                            log.debug("is a backorder");
                            isBackorder = true;
                            //if rule applies, kick the order out
                            if (backorder_rule.equals(OrderXMLDoc.kRejectBackOrder)) {
                                //note: zero inventory thrown
                                throw new Exception("Insufficient inventory for SKU " + item.client_part_no);
                            }

                            if (backorder_rule.equals(OrderXMLDoc.kHoldBackOrder)) {

                        log.debug("holding backorder");
                                //note: zero inventory thrown
                                is_future_ship = 1;
                            }
                        }
                    }
                    if (item.quantity_actual == 0 && item.quantity_backordered == 0 && item.quantity_request == 0) {
                        Exception e = new Exception("Zero quantities problem for SKU: " + item.client_part_no + " from client: " + clientID + " for order Ref: " + order_refnum);
                        StringWriter s = new StringWriter();
                        e.printStackTrace(new PrintWriter(s, true));
                        Vector v = new Vector();
                        v.add("servicerequests@owd.com");
                        Mailer.postMailMessage("Zero Inventory problem", "sku:" + item.client_part_no + "|order_refnum:" + order_refnum + "\n" + s.toString(), v, "support@owd.com");
                        throw e;


                    }
                }
                //end new line item update section

                //start old line item update section
/*

                //check for backorder situation - update quantities by line
                for (int i = 0; i < skuList.size(); i++) {
                    LineItem item = (LineItem) skuList.elementAt(i);
                    if (item.force_backorder_quantity == true) {

                        int currentCount = getAvailableInventory(cxn, item.inventory_fkey);
                        if (currentCount >= item.quantity_request) {
                            item.quantity_actual = item.quantity_request;
                            if (item.quantity_backordered > 0) {
                                isBackorder = true;
                                if (backorder_rule.equals(OrderXMLDoc.kRejectBackOrder)) {
                                    //note: zero inventory thrown
                                    throw new Exception("Insufficient inventory for SKU " + item.client_part_no);
                                }
                            }

                            //log.debug("Force BO LI values: r=" + item.quantity_request + " a=" + item.quantity_actual + " b=" + item.quantity_backordered);
                        } else {
                            throw new Exception("Insufficient inventory for SKU " + item.client_part_no);
                        }
                    } else {
                        item.resetQuantities();
                        //check for "Zero quantities" problem before update quantities because of backorder
                        if (item.quantity_actual == 0 && item.quantity_backordered == 0 && item.quantity_request == 0) {
                            Exception e = new Exception("Zero quantities problem for SKU: " + item.client_part_no + " from client: " + clientID + " for order Ref: " + order_refnum);
                            StringWriter s = new StringWriter();
                            e.printStackTrace(new PrintWriter(s, true));
                            Vector v = new Vector();
                            v.add("owditadmin@owd.com");
                            v.add("owditadmin@owd.com");
                            Mailer.postMailMessage("Zero Inventory problem", "sku:" + item.client_part_no + "|order_refnum:" + order_refnum + "\n" + s.toString(), v, "support@owd.com");
                            throw e;


                        }
                        if (item.updateQuantities(getAvailableInventory(cxn, item.inventory_fkey), backorder_rule)) {
                            isBackorder = true;
                            //if rule applies, kick the order out
                            if (backorder_rule.equals(OrderXMLDoc.kRejectBackOrder)) {
                                //note: zero inventory thrown
                                throw new Exception("Insufficient inventory for SKU " + item.client_part_no);
                            }
                            if (backorder_rule.equals(OrderXMLDoc.kHoldBackOrder)) {
                                //note: zero inventory thrown
                                is_future_ship = 1;
                            }
                        }

                    }

                    if (item.quantity_actual > 0) {
                        //log.debug("quantity_actual > 0");
                        //isShippable = true;
                    } else {
                        //log.debug("quantity_actual <= 0");
                        if (item.quantity_actual == 0 && item.quantity_backordered == 0 && item.quantity_request == 0) {
                            Exception e = new Exception("Zero quantities problem for SKU: " + item.client_part_no + " from client: " + clientID + " for order Ref: " + order_refnum);
                            StringWriter s = new StringWriter();
                            e.printStackTrace();
                            e.printStackTrace(new PrintWriter(s, true));
                            Vector v = new Vector();
                            v.add("owditadmin@owd.com");
                            Mailer.postMailMessage("Zero Inventory problem", "sku:" + item.client_part_no + "|order_refnum:" + order_refnum + "\n" + s.toString(), v, "support@owd.com");
                            throw e;


                        }
                    }

                }
*/

                //end old line item update section

                //get balance for order with backorder
                recalculateBalance();

                //payments?
                //if backorder, correct quantities based on rule
                if (isBackorder) {
                    if (backorder_rule.equals(OrderXMLDoc.kPartialShip) || backorder_rule.equals(OrderXMLDoc.kIgnoreBackOrder)) {
                        //	if (total_product_cost == 0) //catch coupons, other non-paid items that may have inventory but shouldn't ship alone
                        //	{
                        //		for(int i = 0; i< skuList.size(); i++)//make sure these items are also backordered
                        //		{
                        //			((LineItem)skuList.elementAt(i)).backorderAll();
                        //		}
                        //	}
                    } else if (backorder_rule.equals(OrderXMLDoc.kBackOrderAll)) {
                        //set facility back to original to support closest release of backorders.
                        facilityCode = originalFacilityCode;
                        for (int i = 0; i < skuList.size(); i++) {
                            ((LineItem) skuList.elementAt(i)).backorderAll();
                        }
                    } else if (backorder_rule.equals(OrderXMLDoc.kHoldBackOrder)) {
//set facility back to original to support closest release of backorders.
                        facilityCode = originalFacilityCode;
                        log.debug("holding backorder 2");
                        for (int i = 0; i < skuList.size(); i++) {
                            ((LineItem) skuList.elementAt(i)).resetQuantities();
                        }
                        is_future_ship = 1;
                        hold_reason = hold_reason + ": Placed on hold per backorder rule";

                    }
                }

                //if the order is assumed to be paid for already, mark it as such
                if (payment_status.equals(OrderXMLDoc.kPaymentStatusClientManaged)) {
                    paid_date = OWDUtilities.getSQLDateTimeForToday();
                    recalculateBalance();
                    paid_amount = total_order_cost;
                    recalculateBalance();

                } else {
                    //log.debug("1:bct=" + bill_cc_type);
                    if (bill_cc_type.equals("CK")) {
                        if (order_balance > 0) {
                            is_future_ship = 1;
                        }
                        hold_reason = hold_reason + ": CK form of payment";

                    } else {
                        //log.debug("2:bct=" + bill_cc_type);
                        if (!bill_cc_type.equals("ECK")) {
                            try {
                                old_bill_cc_type = bill_cc_type;
                                bill_cc_type = CreditCard.vendorToString(CreditCard.recognizeVendor(CreditCard.parseDirtyLong(cc_num)));
                                cc_num = "" + CreditCard.parseDirtyLong(cc_num);
                                if (cc_num.length() > 16)
                                    cc_num = cc_num.substring(0, 16);

                            } catch (Exception ex) {
                                //bill_cc_type = "UNKN";
                                      ex.printStackTrace();
                            }
                        }

                    }
                    //    //log.debug("3:bct=" + bill_cc_type);

                }

                validateData();
                //hold order?

                //hold by setting future ship flag here

                if (bill_cc_type.equals("FREE")) {
                    is_future_ship = 0;
                }
                if (is_future_ship == 1) {
                    //  //log.debug("is future ship == 1");
                    for (int i = 0; i < skuList.size(); i++) {
                        ((LineItem) skuList.elementAt(i)).resetQuantities();
                    }
                } else {
                    //  //log.debug("is future ship == 0");

                }


                if ("".equals(customer_num) || null == customer_num) {
                    customer_num = orderNum;
                }

                if ("".equals(actual_order_date) || null == actual_order_date) {
                    actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                }

                if (is_auto_ship) {
                    order_refnum = "AUTOSHIP-" + order_refnum;
                }


                stmt = cxn.prepareStatement(OrderUtilities.kCreateOrderStatement);


                stmt.setInt(1, new Integer(clientID).intValue());
                stmt.setNull(2, Types.INTEGER);
                stmt.setString(3, created_by);
                stmt.setString(4, OWDUtilities.getSQLDateTimeForToday());
                stmt.setString(5, created_by);
                stmt.setString(6, OWDUtilities.getSQLDateTimeForToday());
                stmt.setString(7, "*" + orderNum + "*");
                stmt.setString(8, OrderUtilities.limitStr(82, po_num));
                stmt.setString(9, OrderUtilities.limitStr(30, order_type));
                stmt.setString(10, OrderUtilities.limitStr(20, ship_operator));
                stmt.setString(11, actual_order_date);
                stmt.setFloat(12, total_product_cost);
                stmt.setFloat(13, Math.abs(discount) * -1);
                stmt.setFloat(14, tax_pct);
                stmt.setFloat(15, total_tax_cost);
                stmt.setFloat(16, total_shipping_cost);
                stmt.setFloat(17, total_gift_wrap);
                stmt.setFloat(18, total_order_cost);
                stmt.setFloat(19, paid_amount);
                stmt.setString(20, paid_date);
                stmt.setFloat(21, order_balance);
                stmt.setString(22, OrderUtilities.limitStr(20, customer_num));
                stmt.setString(23, OrderUtilities.limitStr(20, orderNum));
                stmt.setString(24, OrderUtilities.limitStr(82, OWDUtilities.getLastNameFromWholeName(customerContact.getName())));
                stmt.setString(25, OrderUtilities.limitStr(82, OWDUtilities.getFirstNameFromWholeName(customerContact.getName())));
                stmt.setString(26, OrderUtilities.limitStr(82, customerAddress.address_one));
                stmt.setString(27, OrderUtilities.limitStr(82, customerAddress.address_two));
                stmt.setString(28, OrderUtilities.limitStr(82, customerAddress.city));
                stmt.setString(29, OrderUtilities.limitStr(82, customerAddress.state));
                stmt.setString(30, OrderUtilities.limitStr(13, customerAddress.zip));
                stmt.setString(31, OrderUtilities.limitStr(32, customerAddress.country));
                stmt.setString(32, OrderUtilities.limitStr(82, customerAddress.company_name));
                stmt.setString(33, "");
                stmt.setString(34, OrderUtilities.limitStr(30, customerContact.getPhone()));
                stmt.setString(35, OrderUtilities.limitStr(30, customerContact.getFax()));
                stmt.setString(36, OrderUtilities.limitStr(82, customerContact.getEmail()));
                stmt.setInt(37, prt_pick_reqd);
                stmt.setInt(38, prt_pack_reqd);
                stmt.setInt(39, prt_invoice_reqd);
                stmt.setInt(40, 0);
                stmt.setInt(41, 0);
                stmt.setNull(42, Types.VARCHAR);
                stmt.setInt(43, is_future_ship);
                stmt.setInt(44, 0);
                stmt.setNull(45, Types.VARCHAR);
                stmt.setInt(46, new Integer(is_gift).intValue());
                stmt.setString(47, OrderUtilities.limitStr(253, gift_message));
                stmt.setString(48, OrderUtilities.limitStr(4, bill_cc_type));
                stmt.setNull(49, Types.DATE);
                stmt.setString(50, cc_num);
                stmt.setInt(51, cc_exp_mon);
                stmt.setInt(52, cc_exp_year);
                stmt.setString(53, payment_status);
                stmt.setString(54, orderNum);
                stmt.setString(55, OrderUtilities.limitStr(82, order_refnum));
                stmt.setString(56, OrderUtilities.limitStr(50, coupon_code));
                stmt.setFloat(57, OWDUtilities.roundFloat(discount_pct));
                stmt.setString(58, group_name);
                stmt.setString(59, packInstructions);
                stmt.setString(60, facilityCode);
                stmt.setString(61, facilityPolicy);
                stmt.setInt(62, forceFedexTaxBillingToConsignee);
                stmt.setBoolean(63,business_order);

                int savedRows = stmt.executeUpdate();
                //if(savedRows < 1)
                //	throw new Exception("Couldn't save order record");
                stmt.close();

                orderID = ConnectionManager.getOrderKeyID(cxn, orderNum);

             /*   PreparedStatement locstmt = HibernateSession.getPreparedStatement("sp_updateOrderFacility " + orderID + ";");
                locstmt.executeUpdate();
                HibUtils.commit(HibernateSession.currentSession());
                locstmt.close();
*/
                //add customer
                saveCustomer(cxn);
                saveSecondaryOrderInfo(cxn);
                saveContactID(cxn);
                if(lineExemptions.size()>0){
                    saveLineItemExemptions(cxn);
                }

                if(!("".equals(template)))
                {
                    saveTemplate(cxn);
                }
                saveCustomValues(cxn);
                //savec ontact ID if present

                //add shipping info
                shipInfo.order_fkey = orderID;
                shipInfo.ship_info_id = "0";
                shipInfo.dbsave(cxn);

                Map idMap = new TreeMap();

                if (0 >= skuList.size()) {
                    throw new Exception("No valid line items in order - order could not be saved!");
                }
                //add line items
                for (int i = 0; i < skuList.size(); i++) {

                    LineItem item = ((LineItem) skuList.elementAt(i));
                    //((LineItem)skuList.elementAt(i)).updateQuantities(avail,backorder_rule);
                    item.order_fkey = orderID;
                    item.line_item_id = "0";


                    item.dbsave(cxn);
                    item.saveCustomValues(cxn);
                    if (item.is_parent.intValue() == 1) {
                        idMap.put(new Integer(item.tempID), new Integer(item.line_item_id));
                    }

                }
                //log.debug("got id map after saving items:"+idMap);

                for (int i = 0; i < skuList.size(); i++) {

                    LineItem item = ((LineItem) skuList.elementAt(i));
                    //log.debug("checking item "+item.client_part_no+" for parent");
                    if (item.parent_line != null)
                    {
                        //log.debug("found parent for tempID="+item.parent_line);
                        item.parent_line = (Integer) idMap.get(item.parent_line);
                        item.dbupdate(cxn);
                    }

                }


                if (0 >= skuList.size()) {
                    throw new Exception("No valid line items in order - order could not be saved!");
                }

                if (is_future_ship == 1)
                {
                    Event.addOrderEvent(new Integer(orderID), Event.kEventTypeComment, "Order placed on hold: " + hold_reason, null);

                    EventFeeds.reportOrderHeld(new Integer(orderID), Integer.parseInt(clientID),
                            EventFeeds.kApiSourceType,"Order placed on hold: " + hold_reason);
                }


                if (is_future_ship == 0 && order_balance <= 0) {
                    postOrder(cxn);

                    if (isBackorder) {
                        createBackorder(cxn);
                    }
                } else {


                }

                /***************************
                 COMMIT
                 ***************************/
                if (testing)
                {
                    cxn.rollback();
                }
                else {

                    //adding subscription handling - Dec. 9, 2002
                    //    //log.debug("Order...create_auto_ship...:" + create_auto_ship);
                    if (create_auto_ship) {
                        try {
                            setAutoShip(cxn);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    cxn.commit();

                }

                completed = true;
            } catch (Throwable th) {
                Exception ex;

                             log.debug("::"+skuList);
                if (th instanceof Exception) {
                    ex = (Exception) th;
                } else {
                    ex = new Exception(th.toString());
                }
                //Mailer.sendMail("Save order caught error",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","do_not_reply@owd.com");
                ex.printStackTrace();
                last_error = last_error+" "+ex.getMessage();
                is_void = 0;
                order_refnum = null;

                is_future_ship = 0;
                orderID = "0";
                is_backorder = 0;
                completed = false;
                post_date = null;

                deleteInsertedLineItems();
                try {
                    cxn.rollback();
                } catch (Exception exm) {

                    exm.printStackTrace();
                }
                throw ex;
            } finally {

                ConnectionManager.freeConnection(cxn);

            }

        }catch(Exception xxxox){
                       xxxox.printStackTrace();
                 last_error=xxxox.getMessage()+(last_error.trim().length()>0?"; ":"");
       } finally {

                ConnectionManager.freeConnection(cxn);

            }  }
    }

    public boolean containsPhysicalItem() throws Exception {
        log.debug("Checking Physical Items");
       boolean goodItems = false;
        for (int i = 0; i < skuList.size(); i++) {
            LineItem item = (LineItem) skuList.elementAt(i);
            Inventory invRecord = item.getInventory();
            log.debug("Checking "+item.client_part_no);
            log.debug("auto_inv="+invRecord.is_auto_inventory);
          //  log.debug("kit items="+LineItem.getRequiredItemsForInventoryID(Integer.parseInt(item.inventory_fkey)));
            log.debug("kit items="+LineItem.getRequiredItemsForInventoryID(Integer.parseInt(item.inventory_fkey)).size());
            if (invRecord.is_auto_inventory == 0 || (LineItem.getRequiredItemsForInventoryID(Integer.parseInt(item.inventory_fkey)).size()>0)
                 //   && (item.quantity_backordered + item.quantity_actual) > 0
                    ) {
                goodItems = true;
            }
        }
        return goodItems;
    }

    private void verifyAddressLinesNotMissing() throws Exception {
        if (getShippingAddress().address_one.trim().length() < 2) {
            if(getShippingAddress().address_two.trim().length()>0){
                getShippingAddress().address_one = getShippingAddress().address_one + " " + getShippingAddress().address_two.trim();
                getShippingAddress().address_two = "";
            }else {
                is_future_ship = 1;
                hold_reason = hold_reason + ":" + ("No valid shipping address in order - address line 1 requires at least two characters to be valid");

            }
        }

        if (getBillingAddress().address_one.trim().length() < 2 &&
                getShippingAddress().address_one.trim().length() >= 2
                ) {
            setBillingAddress(getShippingAddress());
        }

        if (getBillingContact().getName().trim().length() < 2 &&
                getBillingAddress().company_name.trim().length() < 2
                && (
                getShippingAddress().company_name.trim().length() >= 2
                        || getShippingContact().getName().trim().length() >= 2)
                ) {
            setBillingContact(getShippingContact());
        }
    }


    public void getFunds() throws Exception {
        getFunds(false);
    }

    public void getFunds(boolean testing) throws Exception {
        Connection cxn = null;

        try {

            cxn = ConnectionManager.getConnection();
            if(cxn==null){ throw new Exception("Unable to get database connection - please try again later.");}
            if (bill_cc_type.equals("ECK")) {
                payViaEcheck(cxn, testing);
            } else {
                payViaCC(cxn, testing);
            }
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
            if(cxn != null) {cxn.rollback();}
            throw ex;
        } finally {
            ConnectionManager.freeConnection(cxn);
        }

    }

    public void postToWarehouse() throws Exception {
        Connection cxn = null;
        boolean isBackorder = false;
        try {

            cxn = ConnectionManager.getConnection();

            if (is_future_ship == 0 && order_balance <= 0) {

                //check for backorder situation
                for (int i = 0; i < skuList.size(); i++) {
                    LineItem item = (LineItem) skuList.elementAt(i);

                    if (item.quantity_backordered > 0) {
                        isBackorder = true;

                    }
                }

                postOrder(cxn);

                if (isBackorder) {
                    createBackorder(cxn);
                }

            }

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
            ConnectionManager.freeConnection(cxn);
        }

    }

    boolean isOnlyItemID(String invID) {
        for (int i = 0; i < skuList.size(); i++) {
            if (!(((LineItem) (skuList.elementAt(i))).inventory_fkey.equals(invID))) {
                return false;
            }
        }
        return true;
    }

    public boolean hasItemID(String invID) {
        for (int i = 0; i < skuList.size(); i++) {
            if (((LineItem) (skuList.elementAt(i))).inventory_fkey.equals(invID)) {
                return true;
            }
        }
        return false;
    }
    public void printToQueue(Connection cxn) throws Exception {
        printToQueue(cxn,null);
    }
    public void printToQueue(Connection cxn, String printId) throws Exception {
        Statement stmt = null;
        String esql = "";

        if ((prt_pick_reqd + prt_pack_reqd + prt_invoice_reqd) > 0) {

            if (!testing) {
                try {

                        esql = "INSERT INTO owd_print_queue (order_id, client_id, printgroup_id)"
                                + " VALUES("
                                + orderID + ", "
                                + clientID + ", "
                                + printId
                                + " )  ";


                    stmt = cxn.createStatement();

                    stmt.executeUpdate(esql);



                } catch (Throwable th) {
                    Exception ex;

                    if (th instanceof Exception) {
                        ex = (Exception) th;
                    } else {
                        ex = new Exception(th.toString());
                    }

                    ex.printStackTrace();
                    throw ex;
                } finally {
                    try {
                        stmt.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }

    }

    private void payViaCC(Connection cxn, boolean testing) throws Exception {
        recalculateBalance();
        String esql;
        Statement stmt = null;
        last_payment_error = "";
        ////////log.debug(":::checking payment balance");
        if (order_balance > 0 && (!("0".equals(orderID)))) {
////////log.debug(":::trying payment");
            FinancialTransaction ft = new FinancialTransaction("0", orderID, "0", "", "", "", "",
                    order_balance, "", FinancialTransaction.TRANS_NEW,
                    FinancialTransaction.TRANS_CC + "", 0, 0, "-1",
                    OWDUtilities.getLastNameFromWholeName(customerContact.getName()),
                    OWDUtilities.getFirstNameFromWholeName(customerContact.getName()),
                    customerAddress.address_one, customerAddress.address_two,
                    customerAddress.zip, customerAddress.company_name,
                    customerAddress.city, customerAddress.country,
                    customerAddress.state, customerContact.getEmail(), customerContact.getPhone(),
                    orderNum, "" + total_shipping_cost, "" + total_tax_cost, "", "", "", "", "",
                    OWDUtilities.getExpDateStr(cc_exp_mon, cc_exp_year), cc_num, "", "", "", "", "", "", 0, 0, OWDUtilities.getSQLDateTimeForToday());
            ft.cvvCode = cc_cvv_code;
            try {

                Client client = Client.getClientForID(cxn, clientID);

                if(Client.isChargeOnShip(Integer.parseInt(clientID)))
                {
                    ft.amount = 1.00f;
                    ft.authorizeCC(client, testing);
                }   else
                {
                    ft.chargeCC(client, testing);
                }
                last_payment_error = ft.error_reponse;


                ft.dbsave(cxn);

                /***************************
                 COMMIT
                 ***************************/
                cxn.commit();
            } catch (Exception ex) {
                cxn.rollback();
                ex.printStackTrace();
                last_payment_error = last_payment_error + ";Processor unavailable; try again or use CK payment";
                throw ex;

            }
            try {

                if (ft.getStatus().equals(ft.TRANS_OK)) {
                    payment_status = OrderXMLDoc.kPaymentStatusPaid;
                    paid_date = OWDUtilities.getSQLDateTimeForToday();
                    paid_amount = order_balance;
                    avs_response = ft.proc_avs_code;
                    cvv_response = ft.proc_cvv_code;

                    recalculateBalance();


                    esql = "update owd_order set order_balance = " + order_balance + ", "
                            + "paid_date = " + "\'" + paid_date + "\'" + ", "
                            + "payment_status = " + "\'" + payment_status + "\'" + ", "
                            + " paid_amount = " + paid_amount + "  where order_id = " + orderID;
                    OWDUtilities.debugApp(esql);
                    stmt = cxn.createStatement();


                    int rowsUpdated = stmt.executeUpdate(esql);

                    /***************************
                     COMMIT
                     ***************************/
                    cxn.commit();
                }
            } catch (Exception ex) {
                cxn.rollback();
                throw ex;

            } finally {

                try {
                    stmt.close();
                } catch (Exception ex) {
                }

            }


        }
    }

    private void payViaEcheck(Connection cxn, boolean testing) throws Exception {
        recalculateBalance();
        String esql;
        Statement stmt = null;
        last_payment_error = "";
        ////////log.debug(":::checking payment balance");
        if (order_balance > 0 && (!("0".equals(orderID)))) {
////////log.debug(":::trying payment");
            FinancialTransaction ft = new FinancialTransaction("0", orderID, "0", "", "", "", "",
                    order_balance, "", FinancialTransaction.TRANS_NEW,
                    FinancialTransaction.TRANS_CK + "", 0, 0, "-1",
                    OWDUtilities.getLastNameFromWholeName(customerContact.getName()),
                    OWDUtilities.getFirstNameFromWholeName(customerContact.getName()),
                    customerAddress.address_one, customerAddress.address_two,
                    customerAddress.zip, customerAddress.company_name,
                    customerAddress.city, customerAddress.country,
                    customerAddress.state, customerContact.getEmail(), customerContact.getPhone(),
                    orderNum, "" + total_shipping_cost, "" + total_tax_cost, "", "", "", "", "",
                    "", "", "", ck_acct, "", ck_bank, ck_number, "", 0, 0, OWDUtilities.getSQLDateTimeForToday());
            ft.cvvCode = cc_cvv_code;
            try {


                ft.chargeEcheck(Client.getClientForID(cxn, clientID), testing);
                last_payment_error = ft.error_reponse;


                ft.dbsave(cxn);

                /***************************
                 COMMIT
                 ***************************/
                cxn.commit();
            } catch (Exception ex) {
                cxn.rollback();
                ex.printStackTrace();
                last_payment_error = last_payment_error + ";AuthorizeNet unavailable; try again or use CK payment";
                throw ex;

            }
            try {

                if (ft.getStatus().equals(ft.TRANS_OK)) {
                    payment_status = OrderXMLDoc.kPaymentStatusPaid;
                    paid_date = OWDUtilities.getSQLDateTimeForToday();
                    paid_amount = order_balance;

                    recalculateBalance();


                    esql = "update owd_order set order_balance = " + order_balance + ", "
                            + "paid_date = " + "\'" + paid_date + "\'" + ", "
                            + "payment_status = " + "\'" + payment_status + "\'" + ", "
                            + " paid_amount = " + paid_amount + "  where order_id = " + orderID;
                    OWDUtilities.debugApp(esql);
                    stmt = cxn.createStatement();


                    int rowsUpdated = stmt.executeUpdate(esql);

                    /***************************
                     COMMIT
                     ***************************/
                    cxn.commit();
                }
            } catch (Exception ex) {
                cxn.rollback();
                throw ex;

            } finally {

                try {
                    stmt.close();
                } catch (Exception ex) {
                }

            }


        }
    }

    public void createBackorder(Connection cxn) throws Exception {
        boolean isBackorder = false;
        boolean isFullBackorder = true;

        if (!isNew()) {

            //check for backorder situation
            for (int i = 0; i < skuList.size(); i++) {
                if (((LineItem) skuList.elementAt(i)).quantity_backordered >= 1) {
                    isBackorder = true;

                }
                if (((LineItem) skuList.elementAt(i)).quantity_actual >= 1) {
                    isFullBackorder = false;
                }
            }
            if (isBackorder && isFullBackorder && !(backorder_rule.equals(OrderXMLDoc.kIgnoreBackOrder))) {
                //skuList = LineItem.getItemsForOrder(cxn,orderID);

                for (int i = 0; i < skuList.size(); i++) {
                    ((LineItem) skuList.elementAt(i)).quantity_request = ((LineItem) skuList.elementAt(i)).quantity_backordered;
                    ((LineItem) skuList.elementAt(i)).quantity_actual = 0;
                    ((LineItem) skuList.elementAt(i)).quantity_backordered = 0;
                    ((LineItem) skuList.elementAt(i)).dbupdate(cxn);
                }
                Statement stmt = null;
                is_backorder = 1;
                try {
                    String esql = "update owd_order set is_backorder = 1 where order_id =  " + orderID;

                    stmt = cxn.createStatement();

                    int rowUpdated = stmt.executeUpdate(esql);

                    stmt.close();


                } catch (Exception ex) {
                    throw ex;

                } finally {

                    try {
                        stmt.close();
                    } catch (Exception ex) {
                    }

                }
            } else if (isBackorder) {

                if (!(backorder_rule.equals(OrderXMLDoc.kIgnoreBackOrder))) {
                    Statement stmt = null;

                    try {

                        String newOrderNum = ConnectionManager.getNextID("Order");
                        String esql = "exec create_backorder_DOM " + orderID + ",\'" + orderNum + "\',\'" + newOrderNum + "\', \'"+ originalFacilityCode +"\'";

                        stmt = cxn.createStatement();

                        //log.debug(esql);

                        boolean hasResultSet = stmt.execute(esql);

                        stmt.close();

                        backorder_order_num = newOrderNum;


                    } catch (Exception ex) {
                        throw ex;

                    } finally {

                        try {
                            stmt.close();
                        } catch (Exception ex) {
                        }

                    }
                }
            }
        } else {

            throw new Exception("can't backorder unsaved Order");
        }

    }

    public int postDateHoursDelay = 0;
    public Date holdUntilDate = null;
    //Calendar.getInstance().getTime();

    public void postOrder(Connection cxn) throws Exception {
        PreparedStatement stmt = null;


        if (getLineCount() > 0)
        {
            try {


                String esql;
                if(holdUntilDate!=null)
                {
                    esql  = "update owd_order set post_date = ? , is_future_ship = 0, is_backorder=0 where order_id = ?";
                    stmt = cxn.prepareStatement(esql);
                    stmt.setDate(1,new java.sql.Date(holdUntilDate.getTime()));
                    stmt.setInt(2,new Integer(orderID));
                }   else
                {
                    esql  = "update owd_order set post_date = dateadd(hour,?,getdate()) , is_future_ship = 0, is_backorder=0 where order_id = ?";
                   stmt = cxn.prepareStatement(esql);
                    stmt.setInt(1,postDateHoursDelay);
                    stmt.setInt(2,new Integer(orderID));
                }



                // OWDUtilities.debugApp(esql);

                int rowsUpdated = stmt.executeUpdate();


                if (rowsUpdated < 1)
                    throw new Exception("Order not posted; could not update post date");

                is_future_ship = 0;
                is_backorder=0;

                post_date = holdUntilDate==null?OWDUtilities.getSQLDateTimeForToday():OWDUtilities.getSQLDateString(holdUntilDate);

            } catch (Exception ex) {
                throw ex;

            } finally {

                try {
                    stmt.close();
                } catch (Exception ex) {
                }

            }

            if(printSlip)
            {
                printToQueue(cxn);
            }
        }

    }

    public void holdNewOrder() {
        is_future_ship=1;

    }

    public String getClientOrderReference() {
        return order_refnum;
    }

    public void setOnHold() throws Exception {
        Connection cxn = null;
        Statement stmt = null;
        try {

            cxn = ConnectionManager.getConnection();
            String esql = "update owd_order set is_future_ship = 1  where order_id = " + orderID;

            stmt = cxn.createStatement();

            stmt.executeUpdate(esql);

            stmt.close();

            log.debug(orderID);
            if(new Integer(orderID)>0)
            {
                skuList = LineItem.getItemsForOrder(cxn, orderID);
            }
            for (int i = 0; i < skuList.size(); i++) {
                log.debug("saving item");
                ((LineItem) skuList.elementAt(i)).resetQuantities();
                ((LineItem) skuList.elementAt(i)).dbupdate(cxn);
            }

            /***************************
             COMMIT
             ***************************/
            cxn.commit();
            is_future_ship = 1;

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

    public void resetOrderRef() throws Exception {
        Connection cxn = null;
        Statement stmt = null;
        try {

            cxn = ConnectionManager.getConnection();
            String esql = "update owd_order set order_refnum=order_refnum+\'v\'  where order_id = " + orderID;

            stmt = cxn.createStatement();

            stmt.executeUpdate(esql);

            stmt.close();

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


            String esql = "exec void_order " + orderID;

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

    public void addInsertItemIfAvailable(String sku, int quantity) throws Exception {
        Inventory i = Inventory.dbloadByPart(sku, clientID);
        if (OrderUtilities.getAvailableInventory("" + i.inventory_id,FacilitiesManager.getFacilityForCode(getFacilityCode()).getId() ) > (quantity + 1)) {
            LineItem item = getLineItem(sku, quantity+"", ""+0.00f, ""+0.00f, "", "", "");
            item.setInventory(i);
            item.insertedItem = true;
            skuList.add(item);
        }
    }

    @Override
    public void addInsertItemIfInventoryAvailable (String sku, int quantity) throws Exception {

        Inventory i = Inventory.dbloadByPart(sku, clientID);

        if (i.qty_on_hand > (quantity + 1)) {

            LineItem item = getLineItem(sku, quantity+"", ""+0.00f, ""+0.00f, "", "", "");
            item.setInventory(i);
            item.insertedItem = true;
            skuList.add(item);

        }
    }




    protected void saveContactID(Connection cxn) throws Exception {


        try {
            if (contactID == null) contactID = "";
            if (contactID.length() > 3) {
                WebResource server = new WebResource("http://service.owd.com/forms/do/recordCallcenterOrders", WebResource.kPOSTMethod);

                server.addParameter("uqhandle", contactID);
                server.addParameter("client", clientID);
                server.addParameter("order_refnum", order_refnum);
                server.addParameter("orderid", orderID);
                server.addParameter("total", "" + OWDUtilities.roundFloat(total_product_cost));
                server.addParameter("submit", "Submit");

                server.getResource();
            }

        } catch (Exception ex) {
                  ex.printStackTrace();
            throw ex;

        } finally {



        }

    }



    public void saveCustomer(Connection cxn) throws Exception {
        PreparedStatement stmt = null;

        try {

            stmt = cxn.prepareStatement(OrderUtilities.kInsertCustomerStatement);

            stmt.setString(1, OrderUtilities.limitStr(253, orderNum));
            stmt.setString(2, "");
            stmt.setString(3, OrderUtilities.limitStr(82, OWDUtilities.getLastNameFromWholeName(customerContact.getName())));
            stmt.setString(4, OrderUtilities.limitStr(82, OWDUtilities.getFirstNameFromWholeName(customerContact.getName())));
            stmt.setString(5, OrderUtilities.limitStr(82, customerAddress.address_one));
            stmt.setString(6, OrderUtilities.limitStr(82, customerAddress.address_two));
            stmt.setString(7, OrderUtilities.limitStr(82, customerAddress.city));
            stmt.setString(8, OrderUtilities.limitStr(32, customerAddress.state));
            stmt.setString(9, OrderUtilities.limitStr(13, customerAddress.zip));
            stmt.setString(10, OrderUtilities.limitStr(32, customerAddress.country));
            stmt.setString(11, OrderUtilities.limitStr(82, customerAddress.company_name));
            stmt.setString(12, "");
            stmt.setString(13, OrderUtilities.limitStr(32, customerContact.getPhone()));
            stmt.setString(14, OrderUtilities.limitStr(32, customerContact.getFax()));
            stmt.setString(15, OrderUtilities.limitStr(82, customerContact.getEmail()));
            stmt.setString(16, OrderUtilities.limitStr(82, OWDUtilities.getLastNameFromWholeName(shipInfo.shipContact.getName())));
            stmt.setString(17, OrderUtilities.limitStr(82, OWDUtilities.getFirstNameFromWholeName(shipInfo.shipContact.getName())));
            stmt.setString(18, OrderUtilities.limitStr(82, shipInfo.shipAddress.address_one));
            stmt.setString(19, OrderUtilities.limitStr(82, shipInfo.shipAddress.address_two));
            stmt.setString(20, OrderUtilities.limitStr(82, shipInfo.shipAddress.city));
            stmt.setString(21, OrderUtilities.limitStr(32, shipInfo.shipAddress.state));
            stmt.setString(22, OrderUtilities.limitStr(13, shipInfo.shipAddress.zip));
            stmt.setString(23, OrderUtilities.limitStr(32, shipInfo.shipAddress.country));
            stmt.setString(24, OrderUtilities.limitStr(82, shipInfo.shipAddress.company_name));
            stmt.setString(25, "");
            stmt.setString(26, OrderUtilities.limitStr(32, shipInfo.shipContact.getPhone()));
            stmt.setString(27, OrderUtilities.limitStr(32, shipInfo.shipContact.getFax()));
            stmt.setString(28, OrderUtilities.limitStr(82, shipInfo.shipContact.getEmail()));


            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated < 1)
                throw new Exception("Order not posted; could not insert into customer table");

        } catch (Exception ex) {
            throw ex;

        } finally {

            try {
                stmt.close();
            } catch (Exception ex) {
            }

        }

    }
    public void saveLineItemExemptions(Connection cxn) throws Exception{
        PreparedStatement stmt = null;

        try{
            for(lineItemExemptions lie:lineExemptions){
                stmt = cxn.prepareStatement("insert into owd_line_item_exemptions (order_fkey,inventory_num,vendor_sku,exemptionCode,exemptionValue,qty,merchant_line_number) VALUES (?,?,?,?,?,?,?)");

                stmt.setInt(1, Integer.parseInt(orderID));
                stmt.setString(2, lie.getInventory_num());
                stmt.setString(3, lie.getVendor_sku());
                stmt.setString(4, lie.getExemptionCode());
                stmt.setString(5, lie.getExemptionValue());
                stmt.setInt(6,Integer.parseInt(lie.getQty()));
                if(lie.getMerchant_line_number().length()>0){
                    stmt.setInt(7,Integer.parseInt(lie.getMerchant_line_number()));
                }else{
                    stmt.setInt(7,0);
                }

                stmt.executeUpdate();


            }




        }catch (Exception ex) {
            throw ex;

        } finally {

            try {
                stmt.close();
            } catch (Exception ex) {
            }

        }
    }
    public void saveCustomValues(Connection cxn) throws Exception {
        PreparedStatement stmt = null;

        try {

            for (String key : tagMap.keySet()) {
                stmt = cxn.prepareStatement("insert into owd_tags (type,external_id,name,value) VALUES (?,?,?,?)");

                stmt.setString(1, "ORDER");
                stmt.setInt(2, Integer.parseInt(orderID));
                stmt.setString(3, OrderUtilities.limitStr(254, key));
                stmt.setString(4, OrderUtilities.limitStr(640000, tagMap.get(key)));

                stmt.executeUpdate();

            }
        } catch (Exception ex) {
            throw ex;

        } finally {

            try {
                stmt.close();
            } catch (Exception ex) {
            }

        }
    }


    public void saveTemplate(Connection cxn) throws Exception {
           PreparedStatement stmt = null;

           try {

               stmt = cxn.prepareStatement("insert into owd_order_template (order_fkey,code,refdatamap) VALUES (?,?,?)");

               stmt.setInt(1, new Integer(orderID).intValue());
               stmt.setString(2, OrderUtilities.limitStr(50, template).toLowerCase());
               stmt.setString(3, OrderUtilities.limitStr(2000, templatedata));


               int rowsUpdated = stmt.executeUpdate();
               if (rowsUpdated < 1)
                   throw new Exception("Order not posted; could not insert template information");

           } catch (Exception ex) {
               throw ex;

           } finally {

               try {
                   stmt.close();
               } catch (Exception ex) {
               }

           }

       }

    @Override
    public float getEstimatedTotalLineItemWeight(){
        float totalWeight = 0.0f;
        try {
            for (LineItem lineItem : (Vector<LineItem>) this.skuList) {
                System.out.println(lineItem.getInventory().weight_lbs);
                System.out.println(lineItem.quantity_actual);
                totalWeight += lineItem.getInventory().weight_lbs * lineItem.quantity_request;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return totalWeight;
    }
    public void setLTLHold(){
        try {
            getShippingInfo().setShipOptions("LTL", "Prepaid", "");
            is_future_ship = 1;
            addNote("Placed on hold due to LTL ship method");
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isInternationalShipping()
    {
        return getShippingAddress().isInternational();
    }

    private List importNotes = new ArrayList();

    public List<String> getImportNotes() {
        return importNotes;
    }
    public void addNote(String note) {
        importNotes.add(note);
    }

    public void saveSecondaryOrderInfo(Connection cxn) throws Exception {
        PreparedStatement stmt = null;

        try {

            stmt = cxn.prepareStatement(OrderUtilities.kCreateSecondShipInfoStatement);

            stmt.setInt(1, new Integer(orderID).intValue());
            stmt.setString(2, OrderUtilities.limitStr(50, shippercontact));
            stmt.setString(3, OrderUtilities.limitStr(50, shippercompany));
            stmt.setString(4, OrderUtilities.limitStr(50, shipperaddress_one));
            stmt.setString(5, OrderUtilities.limitStr(50, shipperaddress_two));
            stmt.setString(6, OrderUtilities.limitStr(50, shippercity));
            stmt.setString(7, OrderUtilities.limitStr(50, shipperstate));
            stmt.setString(8, OrderUtilities.limitStr(50, shipperzip));
            stmt.setString(9, OrderUtilities.limitStr(50, shipperphone));
            stmt.setString(10, OrderUtilities.limitStr(50, shipperbilling_ref));
            stmt.setString(11, bestway);

            stmt.setString(12, OrderUtilities.limitStr(50, taxcontact));
            stmt.setString(13, OrderUtilities.limitStr(50, taxcompany));
            stmt.setString(14, OrderUtilities.limitStr(50, taxaddress_one));
            stmt.setString(15, OrderUtilities.limitStr(50, taxaddress_two));
            stmt.setString(16, OrderUtilities.limitStr(50, taxcity));
            stmt.setString(17, OrderUtilities.limitStr(50, taxstate));
            stmt.setString(18, OrderUtilities.limitStr(50, taxzip));
            stmt.setString(19, OrderUtilities.limitStr(50, taxphone));
            stmt.setString(20, OrderUtilities.limitStr(50, taxaccount));
            stmt.setInt(21, importerOfRecord);
            stmt.setString(22, OrderUtilities.limitStr(50, companyOverride));
            stmt.setString(23, OrderUtilities.limitStr(50, nameOverride));
            stmt.setString(24, OrderUtilities.limitStr(50, phoneOverride));
            stmt.setString(25, OrderUtilities.limitStr(50, address1Override));
            stmt.setString(26, OrderUtilities.limitStr(50, address2Override));
            stmt.setString(27, OrderUtilities.limitStr(50, cityOverride));
            stmt.setString(28, OrderUtilities.limitStr(50, stateOverride));
            stmt.setString(29, OrderUtilities.limitStr(50, zipOverride));
            stmt.setString(30, OrderUtilities.limitStr(50, externalShippingKey));


            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated < 1)
                throw new Exception("Order not posted; could not insert secondary order information");

        } catch (Exception ex) {
            throw ex;

        } finally {

            try {
                stmt.close();
            } catch (Exception ex) {
            }

        }

    }

    @Override
    public void updateOrderThirdPartyAccountInfo(String account) {
        PreparedStatement stmt = null;
        try {
            Connection cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement(OrderUtilities.kupdateThirdPartyAccountInfoStatement);

            stmt.setString(1, OrderUtilities.limitStr(50, account));
            stmt.setInt(2, new Integer(orderID).intValue());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated < 1)
                throw new Exception("Order not posted; could update third party billing account");
            cxn.commit();

        } catch (Exception ex) {

        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public void updateOrderThirdPartyConatacInfo(String contact, String company, String address1, String address2,
                                                 String city, String state, String zip, String phone) {

        PreparedStatement stmt = null;
        try {
            Connection cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement(OrderUtilities.kupdateThirdPartyContactInfoStatement);

            stmt.setString(1, OrderUtilities.limitStr(50, contact));
            stmt.setString(2, OrderUtilities.limitStr(50, company));
            stmt.setString(3, OrderUtilities.limitStr(50, address1));
            stmt.setString(4, OrderUtilities.limitStr(50, address2));
            stmt.setString(5, OrderUtilities.limitStr(50, city));
            stmt.setString(6, OrderUtilities.limitStr(50, state));
            stmt.setString(7, OrderUtilities.limitStr(50, zip));
            stmt.setString(8, OrderUtilities.limitStr(50, phone));
            stmt.setInt(9, new Integer(orderID).intValue());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated < 1) {
                throw new Exception("Order not posted; could update third party billing contact information");
            }
            cxn.commit();

        } catch (Exception ex) {

        } finally {
            try {
                stmt.close();
            } catch (Exception ex) {
            }
        }
    }

    public boolean orderRefnumExists(String refnum) throws Exception {
        boolean result = false;
        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();
            result = orderRefnumExists(cxn, refnum);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }

        return result;
    }

    private boolean orderRefnumExists(Connection cxn, String refnum) {

        Statement stmt = null;
        ResultSet rs = null;
        boolean orderExists = false;
       // log.debug("cking "+refnum);
        if (refnum == null) {
            refnum = "";
            return false;
        }

        if (refnum.equals("")) {
            return false;
        }

        try {

            String esql = "select ISNULL(count(order_id),0) as orders from  owd_order where order_refnum = \'" + refnum + "\' and client_fkey=" + clientID;
         //   log.debug(""+esql);////////////log.debug("ck orderrefexists");
            ////////////log.debug("ck orderrefexists");
            stmt = cxn.createStatement();
            rs = stmt.executeQuery(esql);
            ////////////log.debug("ck orderrefexists");
            if (rs != null) {
                ////////////log.debug("ck orderrefexists");
                ////////////log.debug("ck orderrefexists");
                if (rs.next()) {    ////////////log.debug("ck orderrefexists");
                    orderExists = (0 < rs.getInt(1));////////////log.debug("ck orderrefexists");
                }
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }

        }
        return orderExists;

    }

    private void setAutoShip(Connection cxn) throws Exception {
        //log.debug("setting Auto Ship...");

        Calendar now = Calendar.getInstance();

        if ("D".equals(interval_unit)) {
            now.add(Calendar.DATE, interval_num);
        } else if ("W".equals(interval_unit)) {
            now.add(Calendar.DATE, 7 * interval_num);
        } else if ("M".equals(interval_unit)) {
            now.add(Calendar.MONTH, interval_num);
        } else if ("Y".equals(interval_unit)) {
            now.add(Calendar.YEAR, interval_num);
        }

        long next_time = now.getTime().getTime();


        String statement = "insert into owd_order_auto values(?,?,?,?,?,?,?);";
        //Connection cxn = null;
        PreparedStatement stmt = null;
        try {
            //cxn = ConnectionManager.getConnection();
            stmt = cxn.prepareStatement(statement);
            stmt.setString(1, orderID);
            stmt.setInt(2, interval_num);
            stmt.setString(3, interval_unit);
            stmt.setInt(4, 1);
            stmt.setInt(5, 1);
            stmt.setString(6, "AUTOSHIP-" + orderNum);
            stmt.setLong(7, next_time);
            int result = stmt.executeUpdate();
            if (result <= 0) {
                throw new Exception("subscription not created");
            }
            stmt.close();

            //log.debug("Subscription created...state: " + result);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        /*
        finally
        {
            try{cxn.close();}catch(Exception e){e.printStackTrace(); throw e;}
        }
        */
    }

    public String getSKUStartsWith(String starts) {

        for (int i = 0; i < skuList.size(); i++) {
            if ((((LineItem) skuList.elementAt(i)).client_part_no).startsWith(starts)) {
                return (((LineItem) skuList.elementAt(i)).client_part_no);

            }
        }


        return null;

    }

    public boolean hasSKUWithSubstring(String subsku) {

        for (int i = 0; i < skuList.size(); i++) {
            if ((((LineItem) skuList.elementAt(i)).client_part_no).indexOf(subsku) >= 0) {
                return true;

            }
        }


        return false;

    }

    public boolean containsSKU(String sku) {

        for (int i = 0; i < skuList.size(); i++) {
            if (sku.equalsIgnoreCase(((LineItem) skuList.elementAt(i)).client_part_no)) {
                return true;

            }
        }


        return false;

    }


    public long getTotalPhysicalUnitQuantity() throws Exception {
        int quantity = 0;

        ////log.debug("sku count option 2");
        for (int i = 0; i < skuList.size(); i++) {
            Inventory invRecord = ((LineItem) skuList.elementAt(i)).getInventory();

            Map<Integer,Integer> kitItemMap = LineItem.getRequiredItemsForInventoryID(invRecord.inventory_id);

            if (((kitItemMap.keySet().size()>0) || invRecord.is_auto_inventory == 0) && (!(invRecord.inventory_num.startsWith("KIT-") ||
                    invRecord.inventory_num.startsWith("KITITEM")))) {
                if(kitItemMap.keySet().size()>0)
                {
                    for(Integer key:kitItemMap.keySet())
                    {
                        quantity += ((LineItem) skuList.elementAt(i)).quantity_request*kitItemMap.get(key);
                    }
                }   else
                {
                    quantity += ((LineItem) skuList.elementAt(i)).quantity_request;
                }
            }

        }
        return quantity;
    }

    public long getTotalUnitQuantity() {

        int quantity = 0;

        ////log.debug("sku count option 2");
        for (int i = 0; i < skuList.size(); i++) {
            quantity += ((LineItem) skuList.elementAt(i)).quantity_request;


        }


        return quantity;

    }

    public long getQuantityForSKU(String sku) {

        int quantity = 0;

        ////log.debug("sku count option 2");
        for (int i = 0; i < skuList.size(); i++) {
            if (sku.equals(((LineItem) skuList.elementAt(i)).client_part_no)) {
                quantity += ((LineItem) skuList.elementAt(i)).quantity_request;

            }
        }


        return quantity;

    }

    /**
     *
     * @param sku
     * @param quantity
     * @param itemPrice
     * @param linePrice
     * @param description
     * @param color
     * @param size
     * @throws Exception
     */
    public void addLineItemWithInventory(String sku, int quantity, float itemPrice, float linePrice, String description, String color, String size) throws Exception {

        Inventory i = Inventory.dbloadByPart(sku, this.getClientID());
        LineItem item = getLineItem(sku, "" + quantity, "" + itemPrice, "" + linePrice, description, color, size);
        item.setInventory(i);
        item.insertedItem = true;
        skuList.add(item);
    }

    /**
     * @param contactType@return
     */
    public String getContactEmail(String contactType) {
        try {
            if ("Billing".equalsIgnoreCase(contactType)) {
                return getBillingContact().getEmail();
            } else if ("Shipping".equalsIgnoreCase(contactType)) {
                return getShippingContact().getEmail();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * @param contactType@return
     */
    public String getContactName(String contactType) {
        try {
            if ("Billing".equalsIgnoreCase(contactType)) {
                return getBillingContact().getName();
            } else if ("Shipping".equalsIgnoreCase(contactType)) {
                return getShippingContact().getName();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * @param contactType@return
     */
    public String getContactPhone(String contactType) {
        try {
            if ("Billing".equalsIgnoreCase(contactType)) {
                return getBillingContact().getPhone();
            } else if ("Shipping".equalsIgnoreCase(contactType)) {
                return getShippingContact().getPhone();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * @param addressType
     * @return
     */
    public String getAddressCity(String addressType) {
        try {
            if ("Billing".equalsIgnoreCase(addressType)) {
                return getBillingAddress().getCity();
            } else if ("Shipping".equalsIgnoreCase(addressType)) {
                return getShippingAddress().getCity();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * @param addressType
     * @return
     */
    public String getAddressCountry(String addressType) {
        try {
            if ("Billing".equalsIgnoreCase(addressType)) {
                return getBillingAddress().getCountry();
            } else if ("Shipping".equalsIgnoreCase(addressType)) {
                return getShippingAddress().getCountry();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressOne(String addressType) {

        try {
            if ("Billing".equalsIgnoreCase(addressType)) {
                return getBillingAddress().getAddress_one();
            } else if ("Shipping".equalsIgnoreCase(addressType)) {
                return getShippingAddress().getAddress_one();
            }
        } catch (Exception e) {
        }
        return "";
    }
    @Override
    public void updateShipCompanyOverride(String name){
        companyOverride = name;
    }
    @Override
    public void updateShipNameOverride(String name){ nameOverride = name;
    }
    @Override
    public void updateShipPhoneOverride(String name){
        phoneOverride = name;
    }
    @Override
    public void updateShipAddress1Override(String name){
        address1Override = name;
    }
    @Override
    public void updateShipAddress2Override(String name){
        address2Override = name;
    }
    @Override
    public void updateShipCityOverride(String name){
        cityOverride = name;
    }
    @Override
    public void updateShipStateOverride(String name){
        stateOverride = name;
    }
    @Override
    public void updateShipZipOverride(String name){
        zipOverride = name;
    }
    @Override
    public void setExternalShippingKey(String key) { externalShippingKey = key;}
    @Override
    public String getShippingMethodName() {
        return getShippingInfo().carr_service;
    }

    @Override
    public void setShippingMethodName(String newMethod) throws Exception {
         getShippingInfo().setShipOptions(newMethod,"Prepaid","");
    }

    @Override
    public void setThirdPartyBilling(String account) throws Exception {

        getShippingInfo().setShipOptions(getShippingInfo().carr_service, "Third Party Billing", account);


    }

    @Override
    public void setThirdPartyBillingContact(String contact, String company, String address1, String address2, String city, String state, String zip, String phone) {
        shippercontact = contact;
        shippercompany = company;
        shipperaddress_one = address1;
        shipperaddress_two = address2;
        shippercity = city;
        shipperstate = state;
        shipperzip = zip;
        shipperphone = phone;

    }

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressTwo(String addressType) {

        try {
            if ("Billing".equalsIgnoreCase(addressType)) {
                return getBillingAddress().getAddress_two();
            } else if ("Shipping".equalsIgnoreCase(addressType)) {
                return getShippingAddress().getAddress_two();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressState(String addressType) {
        try {
            if ("Billing".equalsIgnoreCase(addressType)) {
                return getBillingAddress().getState();
            } else if ("Shipping".equalsIgnoreCase(addressType)) {
                return getShippingAddress().getState();
            }
        } catch (Exception e) {
        }
        return "";    }

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressZip(String addressType) {
        try {
            if ("Billing".equalsIgnoreCase(addressType)) {
                return getBillingAddress().getZip();
            } else if ("Shipping".equalsIgnoreCase(addressType)) {
                return getShippingAddress().getZip();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @param addressType
     * @return
     */
    public String getAddressCompanyName(String addressType) {
        try {
            if ("Billing".equalsIgnoreCase(addressType)) {
                return getBillingAddress().getCompany_name();
            } else if ("Shipping".equalsIgnoreCase(addressType)) {
                return getShippingAddress().getCompany_name();
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     *
     * @return
     */
    public String getGroupName() {
        return this.group_name;
    }

    public float getDecimalPoundsWeight() throws Exception {
        float pkgweight = 0.00f;

        for (int i = 0; i < skuList.size(); i++) {

            if (((float) ((LineItem) skuList.get(i)).quantity_request) > 0) {

                pkgweight += OWDUtilities.roundFloat(((float) ((LineItem) skuList.get(i)).quantity_request) * Inventory.getKittedWeight(((LineItem) skuList.get(i)).inventory_fkey), 2);
            }


        }

        return OWDUtilities.roundFloat(pkgweight,2);
    }

    /**
     * Returns count by named field within inventory object connected to a line item. It will check the field
     * with case independent contains method. The equals attribute when true will add if the field contains
     * the value and will add if it doesn't contain when false.
     *
     * @param field
     * @param value
     * @param containsValue
     * @return
     */
    public int getCountByLineItemInventoryField(String field, String value, boolean containsValue) {

        int count = 0;

        try {
            Class<?> c = Inventory.class;
            Field invField = c.getField(field);

            for (LineItem lineItem:(Vector<LineItem>)this.skuList) {
                String invValue = (String)invField.get(lineItem.getInventory());

                if (invValue.toUpperCase().contains(value.toUpperCase())) {
                    if (containsValue) {
                        count += lineItem.quantity_request;
                    }
                } else {
                    if (!containsValue) {
                        count += lineItem.quantity_request;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error - getCountByLineItemInventoryField: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Implement OrderRuleAdapter method for drools
     * @param required
     */
    public void setSignatureRequired(boolean required) {
        if (required) {
            this.getShippingInfo().ss_proof_delivery = "1";
        } else {
            this.getShippingInfo().ss_proof_delivery = "0";
        }
    }

    @Override
    public void setInsuredValue(float value) {
        this.getShippingInfo().ss_declared_value = "1";
        this.getShippingInfo().declared_value = "" + OWDUtilities.roundFloat(value,2);

    }


    public int getRequestedUnitsofSkusWithInventoryTagValue(String tagName, String tagValue) {
        long unitCount = 0;

       for(LineItem line:(Vector<LineItem>) skuList)
       {
           long invKey = Long.parseLong(line.inventory_fkey);


           try {
               PreparedStatement ps = HibernateSession.getPreparedStatement("select count(distinct(id)) from owd_tags where type='INVENTORY' and name=? and value=?\n" +
                       "and external_id = ?");
               ps.setString(1,tagName);
               ps.setString(2,tagValue);
                 ps.setLong(3,invKey);
               ResultSet rs =   ps.executeQuery();
               if(rs.next())
               {
                   if(rs.getLong(1)>0)
                   {
                       unitCount = unitCount+line.quantity_request;
                   }
               }
               rs.close();
               ps.close();
           }catch (Exception ex)
           {

               ex.printStackTrace();
           }

       }




        return (int)unitCount;
    }


    /**
     * Implement OrderRuleAdapter method for drools
     * @return
     */
    public float getTotalOrderCost() {
        return this.total_order_cost;
    }



    /**
     *
     * @param operator
     * @param value
     */
    public int getItemByWeightCount(String operator, float value) {

        int count = 0;
        try {
            for (LineItem lineItem:(Vector<LineItem>)this.skuList) {

                if (lineItem.getInventory() != null) {
                    boolean testTrue = false;

                    if (">".equals(operator)) {
                        testTrue = lineItem.getInventory().weight_lbs > value;
                    } else if ("<".equals(operator)) {
                        testTrue = lineItem.getInventory().weight_lbs < value;
                    } else if ("=".equals(operator)) {
                        testTrue = lineItem.getInventory().weight_lbs == value;
                    } else if ("<=".equals(operator)) {
                        testTrue = lineItem.getInventory().weight_lbs <= value;
                    } else if (">=".equals(operator)) {
                        testTrue = lineItem.getInventory().weight_lbs >= value;
                    } else if ("!=".equals(operator)) {
                        testTrue = lineItem.getInventory().weight_lbs != value;
                    }
                    if (testTrue) {
                        count++;
                    }
                }
            }
            return count;
        } catch (Exception e) {
            System.err.println("Error - addItemByWeight: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public boolean isTemplateValid() throws Exception{
        boolean valid = false;
        String sql ="select id from owd_client_template_list where client_fkey = :clientId and template = :template";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId",clientID);
        q.setParameter("template",template);

        if(q.list().size()>0){
            valid = true;
        }

        return valid;
    }

    public void setTemplate(String templateName) {
        template = templateName;
    }

    public void updateShipMethodAfterTime(String time, String newMethod){
       try{
           SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
           Date passedIn  = parser.parse(time);
           Date now = parser.parse(parser.format(Calendar.getInstance().getTime()));
           System.out.println(passedIn.getTime());
           System.out.println(now.getTime());

           if(now.after(passedIn)){
               System.out.println("over over");
               setShippingMethodName(newMethod);
               addNote("Updated shipping to "+newMethod+" because time is after "+time);

           }
       }catch(Exception e){
           e.printStackTrace();
       }
    }
    public void updateShipMethodAfterTimeShift(String time, String newMethod,int hourShift){
        try{
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
            Date passedIn  = parser.parse(time);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.HOUR,hourShift);
            Date now = parser.parse(parser.format(c.getTime()));
            System.out.println(passedIn.getTime());
            System.out.println(now.getTime());

            if(now.after(passedIn)){
                System.out.println("over over");
                setShippingMethodName(newMethod);
                addNote("Updated shipping to "+newMethod+" because time is after "+time);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

   @Override
    public void setGroupName(String groupName){
        this.group_name = groupName;

    }

    @Override
    public void setSmartPostReturnFlag()throws Exception{
        SmartPostReturn.markOrderForSmartPostShipmentViaOrder(this);
    }
    @Override
    public void setMailInnovationsReturnFlag()throws Exception{
        ReturnUtilities.markOrderForMailInnovationsShipmentViaOrder(this);
    }

    @Override
    public String getOrderType(){
        return order_type;
    }

    @Override
    public void setOrderType(String orderType){
        this.order_type = orderType;
    }

    @Override
    public long getPhysicalUnitsCountOnOrder(){
        try {
            return getTotalPhysicalUnitQuantity();
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    @Override
    public boolean onlyContainsTheseSKUs(List<String> skus) {
        for (int i = 0; i < skuList.size(); i++) {
            if (!skus.contains(((LineItem) skuList.elementAt(i)).client_part_no)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setBackorderRule(String rule) {
        backorder_rule = rule;
    }
    @Override
    public void setBusinessOrder(boolean businessOrder){
     business_order = businessOrder;
    }

    public boolean getBusinessOrder(){
        return business_order;
    }
    /**
     * Sean Chen created on 10/25/2019 if a customer received one certain free item before, then return true
     * @param client_id
     * @param fullName
     * @param addr1
     * @param city
     * @param lineitem
     * @return
     * @throws Exception
     */
    @Override
    public boolean isReceivedAnItemBefore(String client_id, String fullName, String addr1, String city,String lineitem){
        try {
            String firstName = OWDUtilities.getFirstNameFromWholeName(fullName);
            String lastName = OWDUtilities.getLastNameFromWholeName(fullName);
            String query =("exec sp_checkPeopleReceiveItemBefore :client_id, :firstName,:lastName, :addr1, :city, :lineitem");
            Query q = HibernateSession.currentSession().createSQLQuery(query);

            q.setParameter("client_id", client_id);
            q.setParameter("firstName", firstName);
            q.setParameter("lastName",lastName);
            q.setParameter("addr1",addr1);
            q.setParameter("city", city);
            q.setParameter("lineitem", lineitem);

            if (q.list().size() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Sean Chen created on 01/29/2019 to check if a customer ever purchased at lease once
     * @param client_id
     * @param fullName
     * @param addr1
     * @param city
     */
    @Override
    public boolean isPurchasedBefore(String client_id, String fullName, String addr1, String city)  {

        try {
            Session sess = HibernateSession.currentSession();

            String firstName = OWDUtilities.getFirstNameFromWholeName(fullName);
            String lastName = OWDUtilities.getLastNameFromWholeName(fullName);

            WInvRequest wq = (WInvRequest) sess.load(WInvRequest.class, Integer.valueOf(client_id));

            // invoice location
            Criteria crit = sess.createCriteria(OwdOrderShipInfo.class);
            crit.setMaxResults(1);
            System.out.println("1");

            crit.add(Restrictions.eq("shipFirstName", firstName));
            System.out.println("2");

            crit.add(Restrictions.eq("shipLastName", lastName));
            System.out.println("3");

            crit.add(Restrictions.eq("shipAddressOne", addr1));
            System.out.println("4");

            crit.add(Restrictions.eq("shipCity", city));
            System.out.println("5");

            List<String> orderHistory = crit.list();
            System.out.println(orderHistory.size());

            if (crit.list().size() > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
