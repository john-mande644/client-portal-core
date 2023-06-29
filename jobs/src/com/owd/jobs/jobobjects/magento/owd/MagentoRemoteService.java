package com.owd.jobs.jobobjects.magento.owd;

import com.owd.LogableException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.payment.FinancialTransaction;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.jobs.jobobjects.magento.MagentoImportOrderCustomizer;
import com.owd.jobs.jobobjects.magento.MagentoSubscriptionCreator;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import com.owd.jobs.jobobjects.magento.MagentoService;
import com.owd.jobs.jobobjects.magento.MagentoServiceLocator;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import org.hibernate.engine.spi.SessionImplementor;
import org.lorecraft.phparser.SerializedPhpParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 15, 2009
 * Time: 1:24:13 PM
 * To change this template use File | Settings | File Templates.
 * <p/>
 * <p/>
 * increment_id is order id
 * created_at:2009-05-12 12:16:48
 */
public class MagentoRemoteService {
private final static Logger log =  LogManager.getLogger();


    private String magentoURL = "";//AV
    private String magentoLogin = "";
    private String magentoPassword = "";
    private int clientID = 0;
    private MagentoSubscriptionCreator autoShipManager = null;
    private MagentoImportOrderCustomizer orderCustomizer = null;
    private Integer storeId = null;
    private String storeFilterString = "";
    private OwdClient owdClient = null;
    private final MagentoCustomerAPI magentoCustomerAPI = new MagentoCustomerAPI(this);
    private String currentSession = "";
    private MagentoService currentService = null;
    private List<String> fraudPhoneList = new ArrayList<>();
    private List<String> fraudEmailList = new ArrayList<>();

    private List<String> skipItemList;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public synchronized OwdClient getOwdClient() throws Exception {
        if (owdClient == null) {
            owdClient = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, clientID);
            if (owdClient == null) {
                throw new Exception("No OWD client found for ID " + clientID);
            }

        }
        return owdClient;
    }

    public String getStoreFilterString() {
        return storeFilterString;
    }

    public void setStoreFilterString(String storeFilterString) {
        this.storeFilterString = storeFilterString==null?"":storeFilterString;
    }

    public MagentoCustomerAPI getCustomerApi() {
        return magentoCustomerAPI;
    }

    public MagentoImportOrderCustomizer getOrderCustomizer() {
        return orderCustomizer;
    }

    public void setOrderCustomizer(MagentoImportOrderCustomizer _orderCustomizer) {
        this.orderCustomizer = _orderCustomizer;
    }

    public MagentoSubscriptionCreator getAutoShipManager() {
        return autoShipManager;
    }

    public void setAutoShipManager(MagentoSubscriptionCreator autoShipManager) {
        this.autoShipManager = autoShipManager;
    }

    public int getClientID() {
        return clientID;
    }

    public String getMagentoURL() {
        return magentoURL;
    }

    public MagentoRemoteService(String magentoURL, String login, String password, int clientID) {
        this.magentoURL = magentoURL;
        this.magentoLogin = login;
        this.magentoPassword = password;
        this.clientID = clientID;
    }

    public void testAllowedCarriers() throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

        List<String> argList = new ArrayList<String>();
        argList.add("112323831 ");


        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_shipment.getCarriers", argList);
        if (obj instanceof java.util.HashMap[]) {
            for (HashMap keym : ((HashMap[]) obj)) {

                log.debug("" + keym);
            }
        } else if (obj instanceof java.util.HashMap) {

            log.debug("Hash:" + ((HashMap) obj));

        } else {
            log.debug("Obj:" + obj);
        }
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);
    }

    public void getProductAttributeSetList() throws Exception {
        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

        //  HashMap<String, Object> dets = new HashMap<String, Object>();

        // HashMap<String, Object> arg = new HashMap<String, Object>();
        //  arg.put("sku", dets);
        // dets.put("=", sku);
        // List<HashMap> argList = new ArrayList<HashMap>();
        // argList.add(arg);

        List<String> argList = new ArrayList<String>();

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "product_attribute_set.list", argList);

        /*

      {set_id=4, name=Default}
{set_id=26, name=Shirts}
        */
        if (obj instanceof java.util.HashMap[]) {
            for (HashMap keym : ((HashMap[]) obj)) {

                log.debug("" + keym);
            }
        } else if (obj instanceof java.util.HashMap) {

            log.debug("Hash:" + ((HashMap) obj));

        } else {
            log.debug("Obj:" + obj);
        }


        argList.add("" + 4);
        obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "product_attribute.list", argList);

        /*

      {set_id=4, name=Default}
{set_id=26, name=Shirts}
        */
        if (obj instanceof java.util.HashMap[]) {
            for (HashMap keym : ((HashMap[]) obj)) {

                log.debug("" + keym);
            }
        } else if (obj instanceof java.util.HashMap) {

            log.debug("Hash:" + ((HashMap) obj));

        } else if (obj instanceof Object[]) {

            log.debug("List:" + ((Object[]) obj).length);//+ ((List) obj));
            for (Object keym : ((Object[]) obj)) {

                log.debug("" + keym);
            }
        } else {
            log.debug("Obj:" + obj);
        }


        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);


    }

    public void getInventoryDetails(String sku) throws Exception {
        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

        HashMap<String, Object> dets = new HashMap<String, Object>();

        HashMap<String, Object> arg = new HashMap<String, Object>();
        arg.put("sku", dets);
        dets.put("=", sku);
        List<HashMap> argList = new ArrayList<HashMap>();
        argList.add(arg);

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "product.info", argList);

        /*


Hash:{visibility=4, manufacturer=null, created_at=2009-10-28 19:51:50, meta_description=, tax_class_id=2, tier_price=[[Ljava.lang.Object;@784a7df6, small_image_label=40" Black Crystal Cluster Necklace, special_price=null, websites=[Ljava.lang.String;@20968fda, options_container=container1, price=195.0000, news_from_date=null, custom_layout_update=, special_to_date=null, type_id=simple, news_to_date=null, meta_title=, promotion=0, required_options=0, category_ids=[Ljava.lang.String;@69fe571f, sku=40-5540N, weight=0.0000, custom_design_from=null, has_options=true, updated_at=2009-12-18 08:21:54, thumbnail_label=40" Black Crystal Cluster Necklace, categories=[Ljava.lang.String;@4386d484, url_key=colette-cluster-necklace, short_name=Colette Necklace, custom_design=, short_description=Mirror-smooth black Swarovski crystals contrast with the brushed texture of 14k gold vermeil beads in designs that are playful but graceful. The 40" necklace with spring-ring clasp can be worn dramatically long or doubled in cascading layers.
, url_path=colette-cluster-necklace.html, name=Colette Cluster Necklace, enable_googlecheckout=0, type=simple, product_id=40, status=1, gift_message_available=2, cost=null, page_layout=two_columns_left, minimal_price=null, color=null, featured=0, set=4, image_label=40" Black Crystal Cluster Necklace, description=Mirror-smooth black Swarovski crystals contrast with the brushed texture of 14k gold vermeil beads in designs that are playful but graceful. The 40" necklace with spring-ring clasp can be worn dramatically long or doubled in cascading layers.
, special_from_date=null, meta_keyword=, old_id=null, custom_design_to=null}
        */
        if (obj instanceof java.util.HashMap[]) {
            for (HashMap keym : ((HashMap[]) obj)) {

                log.debug("" + keym);
            }
        } else if (obj instanceof java.util.HashMap) {

            log.debug("Hash:" + ((HashMap) obj));

        } else {
            log.debug("Obj:" + obj);
        }

        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);


    }

    public List<String> getAllSKUsIncludingString(String searchTerm) throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);
        List<String> skus = new ArrayList<String>();

        HashMap<String, Object> dets = new HashMap<String, Object>();

        HashMap<String, Object> arg = new HashMap<String, Object>();
        arg.put("sku", dets);
        dets.put("like", "%" + searchTerm + "%");
        List<HashMap> argList = new ArrayList<HashMap>();
        argList.add(arg);

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "product.list", argList);

        if (obj instanceof java.util.HashMap[]) {
            for (HashMap keym : ((HashMap[]) obj)) {

                log.debug("" + keym);
                skus.add((String) keym.get("sku"));
            }
        }

        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);


        return skus;
    }
    public List<String> getAllSKUs() throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);
        List<String> skus = new ArrayList<String>();

        /*HashMap<String, Object> dets = new HashMap<String, Object>();

        HashMap<String, Object> arg = new HashMap<String, Object>();
        arg.put("sku", dets);
        dets.put("like", "%" + searchTerm + "%");
        List<HashMap> argList = new ArrayList<HashMap>();
        argList.add(arg);*/

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "product.list", null);

        if (obj instanceof java.util.HashMap[]) {
            for (HashMap keym : ((HashMap[]) obj)) {

                log.debug("" + keym);
                skus.add((String) keym.get("sku"));
            }
        }

        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);


        return skus;
    }
    public static void printMagentoResponseObject(Object obj) {
        if (obj instanceof java.util.HashMap[]) {
            for (HashMap keym : ((HashMap[]) obj)) {

                log.debug("" + keym);
            }
        } else if (obj instanceof java.util.HashMap) {

            log.debug("Hash:" + ((HashMap) obj));

        } else if (obj instanceof Object[][]) {
            log.debug("Obj:" + obj);
            log.debug("" + obj.getClass().getCanonicalName());
            if (((Object[][]) obj).length > 0) {

                log.debug("" + ((Object[][]) obj)[0].getClass().getCanonicalName());
            }
        } else if (obj instanceof Object[]){
            System.out.println("Array");
            for(Object data: (Object []) obj){
                log.debug(data.getClass().getCanonicalName());
                log.debug("Obj" + data);
            }

        }else {
            log.debug("" + obj.getClass().getCanonicalName());
            log.debug("" + obj);
        }
    }

    public HashMap<String, String> getAllOrdersWithOrderField(String orderField) throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);
        List<String> orders = new ArrayList<String>();


        HashMap<String, Object> dets = new HashMap<String, Object>();

        HashMap<String, Object> arg = new HashMap<String, Object>();
        //  arg.put("status", "pending");
        arg.put("created_at", dets);
        //  dets.put("status", "processing");
        dets.put("from", OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, -365));
        dets.put("to", OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, 2));
        //  arg.put("sku","1");
        List<HashMap> argList = new ArrayList<HashMap>();
        argList.add(arg);
        log.debug(arg);
        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order.list", argList);
        if (obj instanceof java.util.HashMap[]) {
            //      log.debug("got Magento order list size=" + ((HashMap[]) obj).length);
            for (HashMap keym : ((HashMap[]) obj)) {
                orders.add((String) keym.get("increment_id"));
            }
        }


        HashMap<String, String> oips = new HashMap<String, String>();

        for (String oid : orders) {
            MagentoOrder mo = getMagentoOrder(service, sessid, oid);
            if (mo.order.get(orderField) != null && !(mo.order.get(orderField).equals(""))) {
                oips.put(oid, mo.order.get(orderField));
            }
        }


        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);
        return oips;
    }

    public MagentoOrder getMagentoOrder(String orderid) throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

        return getMagentoOrder(service, sessid, orderid);

    }


    /*

// Create new customer
$newCustomer = array(
 'firstname'  => 'First',
 'lastname'   => 'Last',
 'email'      => 'test@example.com',
 'password'   => 'password',
 'store_id'   => 0,
 'website_id' => 0
);

$newCustomerId = $proxy->call($sessionId, 'customer.create', array($newCustomer));

//Create new customer address
$newCustomerAddress = array(
 'firstname'  => 'First',
 'lastname'   => 'Last',
 'country_id' => 'USA',
 'region_id'  => '43',
 'region'     => 'New York',
 'city'       => 'New York',
 'street'     => array('Bla bla','bla bla'),
 'telephone'  => '5555-555',
 'postcode'   => 10021,

 'is_default_billing'  => true,
 'is_default_shipping' => true
);

$newAddressId = $proxy->call($sessionId, 'customer_address.create', array($newCustomerId, $newCustomerAddress));

var_dump($proxy->call($sessionId, 'customer_address.list', $newCustomerId));

//Update customer address
$proxy->call($sessionId, 'customer_address.update', array($newAddressId, array('firstname'=>'Changed Firstname')));

    */
    private HashMap<String, HashMap<String, String>> flatRegionMaps = null;

    public HashMap<String, HashMap<String, String>> getFlattenedUSCARegionCodeToIDMaps() throws Exception {

        if (flatRegionMaps == null) {
            flatRegionMaps = new HashMap<String, HashMap<String, String>>();

            MagentoService service = new MagentoServiceLocator(magentoURL);
            String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);
            log.debug("" + sessid);
            List<String> argList = new ArrayList<String>();
            //  argList.add(mo.order.get("increment_id"));
            argList.add("US");
            HashMap<String, String> USMap = new HashMap<String, String>();
            flatRegionMaps.put("US", USMap);
            //  argList.add("Check Invoice by OWD");
            //  argList.add("false");
            //  argList.add("false");


//$newInvoiceId = $proxy->call($sessionId, 'sales_order_invoice.create', array($notInvoicedOrderId, array(), 'Invoice Created', true, true));
            Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "region.list", argList);
            if (obj instanceof java.util.HashMap[]) {
                log.debug("got Magento region list size=" + ((HashMap[]) obj).length);
                for (HashMap keym : ((HashMap[]) obj)) {
                    // log.debug(keym);

                    USMap.put(((String) keym.get("code")).toUpperCase(), (String) keym.get("region_id"));
                    USMap.put(((String) keym.get("name")).toUpperCase(), (String) keym.get("region_id"));
                }
            }
            argList = new ArrayList<String>();
            //  argList.add(mo.order.get("increment_id"));
            argList.add("CA");
            HashMap<String, String> CAMap = new HashMap<String, String>();
            flatRegionMaps.put("CA", CAMap);
            //  argList.add("Check Invoice by OWD");
            //  argList.add("false");
            //  argList.add("false");


//$newInvoiceId = $proxy->call($sessionId, 'sales_order_invoice.create', array($notInvoicedOrderId, array(), 'Invoice Created', true, true));
            obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "region.list", argList);
            if (obj instanceof java.util.HashMap[]) {
                log.debug("got Magento region list size=" + ((HashMap[]) obj).length);
                for (HashMap keym : ((HashMap[]) obj)) {
                    log.debug(keym);

                    CAMap.put(((String) keym.get("code")).toUpperCase(), (String) keym.get("region_id"));
                    CAMap.put(((String) keym.get("name")).toUpperCase(), (String) keym.get("region_id"));

                }
            }

            service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);
        }

        return flatRegionMaps;
    }


    public String getMagentoLogin() {
        return magentoLogin;
    }

    public String getMagentoPassword() {
        return magentoPassword;
    }

    public List<HashMap<String, String>> getAllInventoryInfo() throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

        List<String> argList = new ArrayList<String>();
        // argList.add(magentoOrderID);
        // argList.add(null);//sendMagentoEmail?"true":"false");
        // argList.add(null);//includeCommentInEmail?"true":"false");

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "product.list", argList);
        if (obj instanceof HashMap[]) {
            log.debug("is array");
            List<HashMap<String, String>> newobj = new ArrayList<HashMap<String, String>>();

            for (HashMap<String, String> map : (HashMap<String, String>[]) obj) {
                newobj.add(map);
            }
            obj = newobj;
        }
        /*

       HashMap[]

{type=simple, set=4, category_ids=[Ljava.lang.String;@3611c755, product_id=93, name=Signature WSJ Leather Travel Portfolio, sku=30-402MX}
{type=configurable, set=26, category_ids=[Ljava.lang.String;@4128d1f4, product_id=94, name=Men's Short Sleeve Cycling Jersey, sku=10-110}
{type=simple, set=26, category_ids=[[Ljava.lang.Object;@487011c, product_id=95, name=Men's Short Sleeve Cycling Jersey-medium, sku=10-110SSM}
{type=simple, set=26, category_ids=[[Ljava.lang.Object;@50b20090, product_id=96, name=Men's Short Sleeve Cycling Jersey-large, sku=10-110SSL}
{type=simple, set=26, category_ids=[[Ljava.lang.Object;@55cc446d, product_id=97, name=Men's Short Sleeve Cycling Jersey-XL, sku=10-110SSX}
{type=configurable, set=26, category_ids=[Ljava.lang.String;@588980b7, product_id=98, name=Men's Long Sleeve Cycling Jersey, sku=10-120}
         */

        log.debug("obj:" + obj);
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);
        return (List<HashMap<String, String>>) obj;


    }

    public boolean cancelinvoice(String magentoOrderID) throws Exception {
        boolean ok = false;

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

        List<String> argList = new ArrayList<String>();
        argList.add(magentoOrderID);
        // argList.add(null);//sendMagentoEmail?"true":"false");
        // argList.add(null);//includeCommentInEmail?"true":"false");

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_invoice.cancel", argList);
        log.debug("New shipment ID:" + obj);
        String shipmentID = obj.toString();

        log.debug(shipmentID);
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);


        return ok;


    }

    public boolean testOrderShippedWithItems(String magentoOrderID, String tracking, String shipMethod, Vector items) throws Exception {

        boolean ok = false;
        try {
            log.debug("marking order " + magentoOrderID + " shipped");
            MagentoService service = new MagentoServiceLocator(magentoURL);
            String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

            Map itemsShipped = new TreeMap();
            for (LineItem item : (Vector<LineItem>) items) {
                try {
                    int itemid = Integer.parseInt(item.client_ref_num);
                    if (itemid > 0) {
                        itemsShipped.put(itemid, item.quantity_actual);//""+Integer.parseInt(item.client_ref_num),""+item.quantity_actual);
                    }
                } catch (Exception ex) {

                }
            }
            log.debug("itemsshipped=" + itemsShipped);
            List<Object> argList = new ArrayList<Object>();
            argList.add(magentoOrderID);
            argList.add(itemsShipped.keySet().size() > 0 ? itemsShipped : null);
            argList.add(shipMethod);
            // argList.add(null);//sendMagentoEmail?"true":"false");
            // argList.add(null);//includeCommentInEmail?"true":"false");


            Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_shipment.create", argList);
            log.debug("New shipment ID:" + obj);
            String shipmentID = obj.toString();

            List<String> argList2 = new ArrayList<String>();
            argList2.add("" + shipmentID);
            String carrier = "usps";
            if (tracking.startsWith("1Z")) {
                carrier = "ups";
            }
            argList2.add(carrier);
            argList2.add(carrier.toUpperCase());
            if ((tracking.equals("NONE") || tracking.equals(""))) {

                tracking = "Not Applicable";
            }
            argList2.add(tracking);


//$newInvoiceId = $proxy->call($sessionId, 'sales_order_invoice.create', array($notInvoicedOrderId, array(), 'Invoice Created', true, true));
            obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_shipment.addTrack", argList2);
            log.debug("track sent:" + obj);


            argList2 = new ArrayList<String>();
            argList2.add("" + shipmentID);
            argList2.add("");
            argList2.add("true");//sendMagentoEmail?"true":"false");
            argList2.add("true");//includeCommentInEmail?"true":"false");

            obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_shipment.addComment", argList2);

            ok = true;


            MagentoRemoteService.setMagentoOrderStatus(service, sessid, magentoOrderID, "complete");

            service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);

        } catch (Exception exx) {
            if (exx.getMessage().toUpperCase().indexOf("NOT EXISTS") < 0
                    &&
                    exx.getMessage().toUpperCase().indexOf("CANNOT DO SHIPMENT") < 0) {
                throw exx;
            }
            return true;
        }
        return ok;


    }

    public boolean testOrderShipped(String magentoOrderID, String tracking, String comment, boolean sendMagentoEmail, boolean includeCommentInEmail) throws Exception {
        boolean ok = false;

        log.debug("marking order " + magentoOrderID + " shipped");
        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

        List<String> argList = new ArrayList<String>();
        argList.add(magentoOrderID);
        argList.add(null);
        argList.add(comment);
        // argList.add(null);//sendMagentoEmail?"true":"false");
        // argList.add(null);//includeCommentInEmail?"true":"false");

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_shipment.create", argList);
        log.debug("New shipment ID:" + obj);
        String shipmentID = obj.toString();

        argList = new ArrayList<String>();
        argList.add("" + shipmentID);
        String carrier = "usps";
        if (tracking.startsWith("1Z")) {
            carrier = "ups";
        }
        argList.add(carrier);
        argList.add("UPS");
        argList.add(tracking);

        if (!(tracking.equals("NONE"))) {
//$newInvoiceId = $proxy->call($sessionId, 'sales_order_invoice.create', array($notInvoicedOrderId, array(), 'Invoice Created', true, true));
            obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_shipment.addTrack", argList);
            log.debug("track sent:" + obj);
        }


        argList = new ArrayList<String>();
        argList.add("" + shipmentID);
        argList.add("");
        argList.add("true");//sendMagentoEmail?"true":"false");
        argList.add("true");//includeCommentInEmail?"true":"false");

        obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_shipment.addComment", argList);

        ok = true;
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);


        return ok;


    }

    public boolean markOrderAsShipped(String magentoOrderID, String tracking, String comment, boolean sendMagentoEmail, boolean includeCommentInEmail) throws Exception {
        boolean ok = false;

        log.debug("marking order " + magentoOrderID + " shipped");
        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

        List<String> argList = new ArrayList<String>();
        argList.add(magentoOrderID);
        argList.add(null);
        argList.add(comment);
        argList.add(sendMagentoEmail ? "true" : "false");
        argList.add(includeCommentInEmail ? "true" : "false");

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_shipment.create", argList);
        log.debug("New shipment ID:" + obj);
        String shipmentID = obj.toString();

        argList = new ArrayList<String>();
        argList.add("" + shipmentID);
        String carrier = "usps";
        if (tracking.startsWith("1Z")) {
            carrier = "ups";
        }
        argList.add(carrier);
        argList.add("Tracking Number");
        if ((tracking.equals("NONE"))) {

            tracking = "Not Applicable";
        }
        argList.add(tracking);

        if (!(tracking.equals("NONE"))) {
//$newInvoiceId = $proxy->call($sessionId, 'sales_order_invoice.create', array($notInvoicedOrderId, array(), 'Invoice Created', true, true));
            obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_shipment.addTrack", argList);
            log.debug("track sent:" + obj);
        }
        ok = true;
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);


        return ok;


    }

    private HashMap<String, HashMap<String, String>> regionMaps = null;

    public synchronized HashMap<String, HashMap<String, String>> getUSCARegionMap() throws Exception {

        if (regionMaps == null) {
            regionMaps = new HashMap<String, HashMap<String, String>>();

            MagentoService service = new MagentoServiceLocator(magentoURL);
            String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

            List<String> argList = new ArrayList<String>();
            //  argList.add(mo.order.get("increment_id"));
            argList.add("US");
            HashMap<String, String> USMap = new HashMap<String, String>();
            regionMaps.put("US", USMap);
            //  argList.add("Check Invoice by OWD");
            //  argList.add("false");
            //  argList.add("false");


//$newInvoiceId = $proxy->call($sessionId, 'sales_order_invoice.create', array($notInvoicedOrderId, array(), 'Invoice Created', true, true));
            Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "region.list", argList);
            if (obj instanceof java.util.HashMap[]) {
                log.debug("got Magento region list size=" + ((HashMap[]) obj).length);
                for (HashMap keym : ((HashMap[]) obj)) {
                    log.debug(keym);

                    USMap.put((String) keym.get("region_id"), (String) keym.get("code"));
                }
            }
            argList = new ArrayList<String>();
            //  argList.add(mo.order.get("increment_id"));
            argList.add("CA");
            HashMap<String, String> CAMap = new HashMap<String, String>();
            regionMaps.put("CA", CAMap);
            //  argList.add("Check Invoice by OWD");
            //  argList.add("false");
            //  argList.add("false");


//$newInvoiceId = $proxy->call($sessionId, 'sales_order_invoice.create', array($notInvoicedOrderId, array(), 'Invoice Created', true, true));
            obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "region.list", argList);
            if (obj instanceof java.util.HashMap[]) {
                log.debug("got Magento region list size=" + ((HashMap[]) obj).length);
                for (HashMap keym : ((HashMap[]) obj)) {
                    log.debug(keym);
                    CAMap.put((String) keym.get("region_id"), (String) keym.get("code"));

                }
            }

            service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);
        }

        return regionMaps;
    }


    public static void main(String[] args) throws Exception {
try {

    MagentoRemoteService service = new MagentoRemoteService("https://basicwear.com/index.php/api/index/index", "owd", "owd7172", 471);//438);




   MagentoService mservice = new MagentoServiceLocator(service.magentoURL);
    String sessid = mservice.getMage_Api_Model_Server_HandlerPort().login(service.magentoLogin, service.magentoPassword);
    service.getMagentoLogin();
    service.setMagentoOrdeCancel(mservice,sessid,"3100015453");
    //  service.sendInventoryDiscrepancyReport("owditadmin@owd.com");


    // argList.add(magentoOrderID);
    // argList.add(null);//sendMagentoEmail?"true":"false");
    // argList.add(null);//includeCommentInEmail?"true":"false");

  //  List<String> argList = new ArrayList<String>();
    // argList.add(magentoOrderID);
    // argList.add(null);//sendMagentoEmail?"true":"false");
    // argList.add(null);//includeCommentInEmail?"true":"false");

   // Object obj = mservice.getMage_Api_Model_Server_HandlerPort().call("", "product.list", argList);
     /*   List<HashMap<String, String>>  items = service.getAllInventoryInfo();
        for(Map<String,String> item:items){
            System.out.println(item);
        }*/
    //item_id=1136,  base_price_incl_tax=31.6000, tax_before_discount=null, row_invoiced=31.6000, base_original_price=29.9500
    // base_price=109.9000, tax_percent=0.0000

    //base_price_incl_tax=31.6000 (each)
}catch (Exception e){
    e.printStackTrace();
    System.out.println(e.getMessage());
}

    }

    public void updateCouponCodesForLastYear() throws Exception {
        HashMap<String, String> mapper = getAllOrdersWithOrderField("coupon_code");
        PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_order set coupon=? where order_refnum=? and client_fkey=" + clientID);

        for (String ref : mapper.keySet()) {
            ps.setString(1, mapper.get(ref).toUpperCase());
            ps.setString(2, ref);
            ps.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
        }
        HibernateSession.closeSession();


    }

    private boolean pendingOrders = false;
    private boolean pendingOrdersInvoiced = false;
    private boolean processingOrders = true;
    private boolean processingOrdersInvoiced = false;
    private boolean pendingCheckOrdersInvoiced = true;
    private boolean allowFreeItems = false;

    public boolean isAllowFreeItems() {
        return allowFreeItems;
    }

    public void setAllowFreeItems(boolean allowFreeItems) {
        this.allowFreeItems = allowFreeItems;
    }

    public boolean isPendingOrdersInvoiced() {
        return pendingOrdersInvoiced;
    }

    public void setPendingOrdersInvoiced(boolean pendingOrdersInvoiced) {
        this.pendingOrdersInvoiced = pendingOrdersInvoiced;
    }

    public boolean isProcessingOrdersInvoiced() {
        return processingOrdersInvoiced;
    }

    public void setProcessingOrdersInvoiced(boolean processingOrdersInvoiced) {
        this.processingOrdersInvoiced = processingOrdersInvoiced;
    }

    public boolean isPendingCheckOrdersInvoiced() {
        return pendingCheckOrdersInvoiced;
    }

    public void setPendingCheckOrdersInvoiced(boolean pendingCheckOrdersInvoiced) {
        this.pendingCheckOrdersInvoiced = pendingCheckOrdersInvoiced;
    }

    public boolean isPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(boolean pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public boolean isProcessingOrders() {
        return processingOrders;
    }

    public void setProcessingOrders(boolean processingOrders) {
        this.processingOrders = processingOrders;
    }

    public void importMagentoOrders(double minFirstNumericOrderId, int numberOfDaysToLookBack) throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);

        if (numberOfDaysToLookBack > 0) {
            numberOfDaysToLookBack *= -1;
        }

        if (isProcessingOrders()) {
            List<String> orders = getProcessingOrderList(service, sessid, numberOfDaysToLookBack);

            for (String orderid : orders) {

                MagentoOrder mo = getMagentoOrder(service, sessid, orderid);
                log.debug(mo.order.get("store_name") + ":" + mo.order.get("store_id"));
                boolean idInRange = (minFirstNumericOrderId <
                        Double.parseDouble(
                                mo.order.get("increment_id").contains("-") ?
                                        mo.order.get("increment_id").substring(0, mo.order.get("increment_id").indexOf("-")) :
                                        mo.order.get("increment_id")));

                log.debug("isinrange for " + mo.order.get("increment_id") + "=" + idInRange);
                log.debug("order ref exists:" + OrderUtilities.clientOrderReferenceExists(mo.order.get("increment_id"), getClientID() + "", false));
                if (idInRange
                        && (getStoreId() == null || ("" + getStoreId()).equals(mo.order.get("store_id")))
                        && (getStoreFilterString().length() < 1 || mo.order.get("store_name").contains(getStoreFilterString()))
                        && !((OrderUtilities.clientOrderReferenceExists(mo.order.get("increment_id"), getClientID() + "", false)))) {
                    try {
                        processMagentoOrder(mo);
                        if (isProcessingOrdersInvoiced()) {
                            invoiceMagentoOrder(service, sessid, mo);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        try {
                            if (ex.getMessage().contains("fraud")) {
                                setMagentoOrdeCancel(service, sessid,mo.order.get("increment_id"));
                                throw new LogableException(ex,ex.getMessage(),mo.order.get("increment_id"),""+clientID,
                                        this.getClass().getName(),
                                        LogableException.errorTypes.ORDER_IMPORT);
                            }
                        } catch(Exception e){

                            }
                    }
                } else {
                    log.debug("SKIPPING " + mo.order.get("increment_id"));
                }
            }
        }


        if (isPendingCheckOrdersInvoiced()) {
            List<String> orders = getPendingOrderList(service, sessid, numberOfDaysToLookBack);

            for (String orderid : orders) {
                MagentoOrder mo = getMagentoOrder(service, sessid, orderid);

                if (mo.isCheckOrder()) {
                    if (minFirstNumericOrderId < Double.parseDouble(mo.order.get("increment_id")) && !OrderUtilities.clientOrderReferenceExists(mo.order.get("increment_id"), getClientID() + "", false)) {
                        try {
                            processMagentoOrder(mo);
                            invoiceMagentoOrder(service, sessid, mo);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

            }
        }

        if (isPendingOrders()) {
            List<String> orders = getPendingOrderList(service, sessid, numberOfDaysToLookBack);
            for (String orderid : orders) {
                log.debug("Loading order "+orderid);

                if (minFirstNumericOrderId < Double.parseDouble(orderid) && !OrderUtilities.clientOrderReferenceExists(orderid, getClientID() + "", false)) {

                    MagentoOrder mo = getMagentoOrder(service, sessid, orderid);
                    log.debug(mo.order.get("store_name") + ":" + mo.order.get("store_id"));
                    boolean idInRange = (minFirstNumericOrderId <
                            Double.parseDouble(
                                    mo.order.get("increment_id").contains("-") ?
                                            mo.order.get("increment_id").substring(0, mo.order.get("increment_id").indexOf("-")) :
                                            mo.order.get("increment_id")));

                    log.debug("isinrange for " + mo.order.get("increment_id") + "=" + idInRange);
                    log.debug("order ref exists:" + OrderUtilities.clientOrderReferenceExists(mo.order.get("increment_id"), getClientID() + "", false));
                    if (idInRange
                            && (getStoreId() == null || ("" + getStoreId()).equals(mo.order.get("store_id")))
                            && (getStoreFilterString().length() < 1 || mo.order.get("store_name").contains(getStoreFilterString()))
                            && !((OrderUtilities.clientOrderReferenceExists(mo.order.get("increment_id"), getClientID() + "", false)))) {

                        try {
                            processMagentoOrder(mo);
                            if (isPendingOrdersInvoiced()) {
                                invoiceMagentoOrder(service, sessid, mo);
                                setMagentoOrderStatus(service, sessid, mo, "processing");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            try {
                                if (ex.getMessage().contains("fraud")) {
                                    setMagentoOrdeCancel(service, sessid,mo.order.get("increment_id"));
                                    throw new LogableException(ex,ex.getMessage(),mo.order.get("increment_id"),""+clientID,
                                            this.getClass().getName(),
                                            LogableException.errorTypes.ORDER_IMPORT);
                                }
                            } catch(Exception e){

                            }
                        }
                    }
                }


            }
        }
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);
    }

    private String getRegionValue(String regionID, String regionName, String countryCode) {
        String region = regionName;
        if (regionMaps != null) {
            if (regionMaps.get(countryCode) != null) {
                if (regionMaps.get(countryCode).get(regionID) != null) {
                    region = regionMaps.get(countryCode).get(regionID);
                }
            }
        }
        return region;
    }

    public void processMagentoOrder(MagentoOrder mo) throws Exception {


        if (autoShipManager != null) {
            autoShipManager.clearSubscriptionMap();
        }
        log.debug("" + mo);
        Client client = Client.getClientForID(getClientID() + "");

        ClientPolicy policy = client.getClientPolicy();

        log.debug("Policy:" + policy.getClass().getName());
        Order order = policy.createInitializedOrder();


        order.getBillingContact().setName(mo.billing.get("firstname") + " " + mo.billing.get("lastname"));
        order.getBillingContact().phone = mo.billing.get("telephone");
        order.getBillingContact().email = mo.order.get("customer_email");
        order.getShippingContact().setName(mo.shipping.get("firstname") + " " + mo.shipping.get("lastname"));
        order.getShippingContact().phone = mo.shipping.get("telephone");
        order.getBillingAddress().company_name = mo.billing.get("company") + "";

        order.getShippingAddress().company_name = mo.shipping.get("company") + "";
        if (order.getBillingContact().getName().endsWith("null")) {
            order.getBillingContact().setName(order.getBillingContact().getName().replaceAll(" null", ""));
        }
        if (order.getShippingContact().getName().endsWith("null")) {
            order.getShippingContact().setName(order.getShippingContact().getName().replaceAll(" null", ""));
        }
        if (order.getBillingAddress().company_name.endsWith("null")) {
            order.getBillingAddress().company_name = order.getBillingAddress().company_name.replaceAll("null", "");
        }
        if (order.getShippingAddress().company_name.endsWith("null")) {
            order.getShippingAddress().company_name = order.getShippingAddress().company_name.replaceAll("null", "");
        }

        //  if (order.clientID.equals("438")) {


        for (HashMap<String, String> item : mo.aitoc) {

            log.debug("aitoc " + item.get("label") + ":" + item.get("value"));
            if ("po_number".equals(item.get("label"))) {
                if (item.get("value").trim().length() > 0) {
                    order.po_num = item.get("value").trim();
                }
            }

            if ("Gift Message".equals(item.get("label"))) {
                if (item.get("value").trim().length() > 0) {
                    order.is_gift = "1";
                    order.gift_message = item.get("value");
                }
            }

        }


        String couponUsed = mo.order.get("coupon_code");
        if (couponUsed != null) {
            order.processCouponDiscount = false;
            order.coupon_code = couponUsed.toUpperCase();
            log.debug("COUPON:" + couponUsed);
        }


        //  }
        if (mo.shipping.keySet().size() == 0) {
            mo.shipping = mo.billing;
        }

        String test = mo.shipping.get("street");
        String[] splitter = test.split("\\n");
        log.debug("splitter size = " + splitter.length);

        order.getShippingAddress().address_one = splitter[0];
        if (splitter.length > 1) {
            order.getShippingAddress().address_two = splitter[1];
        }

        order.getShippingAddress().city = mo.shipping.get("city") + "";
        order.getShippingAddress().state = getRegionValue(mo.shipping.get("region_id"), mo.shipping.get("region"), mo.shipping.get("country_id"));
        if (order.getShippingAddress().state == null) {
            order.getShippingAddress().state = "";
        }
        order.getShippingAddress().zip = mo.shipping.get("postcode");
        order.getShippingAddress().country = CountryNames.getCountryName(mo.shipping.get("country_id"));

        splitter = null;
        test = null;
        try {
            test = mo.billing.get("street");
            splitter = test.split("\\n");
            log.debug("splitter size = " + splitter.length);

            order.getBillingAddress().address_one = splitter[0];
            if (splitter.length > 1) {
                order.getBillingAddress().address_two = splitter[1];
            }
            order.getBillingAddress().city = mo.billing.get("city");
            order.getBillingAddress().state = getRegionValue(mo.billing.get("region_id"), mo.billing.get("region"), mo.billing.get("country_id"));
            if (order.getBillingAddress().state == null) {
                order.getBillingAddress().state = "";
            }
            order.getBillingAddress().zip = mo.billing.get("postcode");

            order.getBillingAddress().country = CountryNames.getCountryName(mo.billing.get("country_id"));
        } catch (Exception eb) {
            order.setBillingAddress(order.getShippingAddress());
        }

        if (order.getBillingAddress().state.contains("Armed Forces")) {
            if (order.getBillingAddress().state.contains("Pacific")) {
                order.getBillingAddress().state = "AP";
            } else if (order.getBillingAddress().state.contains("Europe")) {
                order.getBillingAddress().state = "AE";
            } else {
                order.getBillingAddress().state = "AA";
            }
        }
        if (order.getShippingAddress().state.contains("Armed Forces")) {
            if (order.getShippingAddress().state.contains("Pacific")) {
                order.getShippingAddress().state = "AP";
            } else if (order.getShippingAddress().state.contains("Europe")) {
                order.getShippingAddress().state = "AE";
            } else {
                order.getShippingAddress().state = "AA";
            }
        }


        order.order_refnum = mo.order.get("increment_id");
        if(fraudEmailList.size()>0){

            if(fraudEmailList.contains(order.getBillingContact().email)||fraudEmailList.contains(order.getShippingContact().getEmail())){
                throw new Exception("Email fraud Canceled order "+ mo.order.get("increment_id"));
            }
        }

        if(fraudPhoneList.size()>0){

            if(fraudPhoneList.contains(order.getBillingContact().getPhone().replaceAll("[\\(\\)-\\.]", ""))||fraudPhoneList.contains(order.getShippingContact().getPhone().replaceAll("[\\(\\)-\\.]",""))){

                throw new Exception("Phone fraud Canceled order "+ mo.order.get("increment_id"));
            }
        }
        skipItemList = new ArrayList<String>();

        for (HashMap item : mo.items) {
            log.debug("processing item " + item.get("sku") + " type:" + item.get("product_type") + " price:" + item.get("price"));

            if ((!(skipItemList.contains(item.get("item_id")))) && (!(item.get("product_type").equals("virtual"))) && (!(item.get("product_type").equals("downloadable"))) && (allowFreeItems || (Float.parseFloat("" + item.get("price")) != 0.00f))) {
                boolean itemIsGiftWrapped = false;
                log.debug("in item");

                String originalSKU = item.get("product_type").equals("configurable") ? (getConfigurableItemRealSku(mo.items, "" + item.get("sku"), "" + item.get("item_id"))).trim() : ((String) item.get("sku")).trim();

                if (originalSKU.equals("03-30D")) {
                    originalSKU = "O3-30D";
                }

                if (originalSKU.contains("MMJ#")) {
                    originalSKU = originalSKU.replaceAll("MMJ#", "MM#");
                }
                if (originalSKU.equals("PRF30D---")) {
                    originalSKU = "PRF30D";
                }
                if (originalSKU.equals("PRF90D---")) {
                    originalSKU = "PRF90D";
                }
                if (originalSKU.equals("PRF00D---")) {
                    originalSKU = "PRF30D";
                }
                if (originalSKU.contains("-+-")) {
                    originalSKU = originalSKU.substring(0, originalSKU.indexOf("-+-"));
                }
                if (originalSKU.startsWith("EES12")) {
                    originalSKU = "EES12";
                }
                if (originalSKU.equals("yoga-purple")) {
                    originalSKU = "DS-yoga-purple";
                }
                if (originalSKU.equals("BO69")) {
                    originalSKU = "BE69";
                }

                String realSKU = policy.translateRemoteSkuToOwdSku("magento", originalSKU);
                String longdesc = "";
                Map imap = (Map) item.get("options");
                if (imap == null) {
                    imap = new HashMap();
                }
                if (order.clientID.equals("-1")) {  //current noop - left for code example for options


                    /*   log.debug("is personalized:" + wsjPersonalizedSKUs.contains(realSKU));
                    if (imap.containsKey("options") && wsjPersonalizedSKUs.contains(realSKU)) {
                        longdesc = "PERSONALIZED:" + OWDUtilities.OToS((Map) imap.get("options"));
                    } else {
                        if (imap.containsKey("options") && wsjDropshipSKUs.contains(realSKU)) {
                            longdesc = "DROPSHIP:";
                        }
                    }*/
                    // log.debug("longdesc:" + longdesc);
                    // policy.addLineItemToOrder(order, realSKU, "", itemIsGiftWrapped?""+OWDUtilities.roundFloat(Float.parseFloat((String)item.get("price"))-6.95f):(String) item.get("price"), ((String) item.get("qty_ordered")).substring(0, ((String) item.get("qty_ordered")).indexOf(".")), longdesc);
                    // ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = (String) item.get("item_id");

                } else {

                    if (autoShipManager != null) {
                        if (autoShipManager.isAutoshipSKU(originalSKU)) {
                            realSKU = autoShipManager.getTrueSKUFromAutoshipSKU(originalSKU);
                        }
                    }
                    /*     if(((byte)realSKU.trim().toCharArray()[realSKU.length()-1]) == -96)
                    {
                        realSKU = realSKU.substring(0,realSKU.length()-1);
                    }
                    if(((byte)originalSKU.trim().toCharArray()[originalSKU.length()-1]) == -96)
                    {
                        realSKU = originalSKU.substring(0,originalSKU.length()-1);
                    }*/
                    log.debug("adding [" + realSKU + "] to order");
//                    log.debug("adding [" + ((byte)realSKU.trim().toCharArray()[realSKU.length()-1]) + "] to order");


                    log.debug("map has options:" + imap.containsKey("options"));

                 //   if (imap.containsKey("options") && getOrderCustomizer() != null) {
                //        getOrderCustomizer().handleLineItemWithOptions(this, order, mo, policy, realSKU, item);
                 //   } else
                    if (item.get("product_type").equals("bundle")) {

                        String[] realSkus = realSKU.trim().split("-");
                        int sku_ix = 0;

                        policy.addLineItemToOrder(order, realSkus[sku_ix], sku_ix == 0 ? (String) item.get("name") : "", sku_ix == 0 ? (String) item.get("price") : "0.00", ((String) item.get("qty_ordered")).substring(0, ((String) item.get("qty_ordered")).indexOf(".")));


                    } else {

                        if(clientID==528) //PMConcepts/amatalife.com, for custom subscription plugin
                        {
                            double originalPrice = Double.parseDouble((String) item.get("price"));

                            double taxPct= Double.parseDouble(""+item.get("tax_percent"));
                            if(taxPct>0.00){

                            double fullbase=Double.parseDouble(""+item.get("base_price_incl_tax"));

                             originalPrice = OWDUtilities.roundDouble(((100.00 * fullbase) / (100.0000 + taxPct)),2);


                            order.total_tax_cost =  order.total_tax_cost+OWDUtilities.roundFloat(new Float((fullbase-originalPrice)));

                            //item_id=1136,  base_price_incl_tax=31.6000, tax_before_discount=null, row_invoiced=31.6000, base_original_price=29.9500
                            // base_price=109.9000, tax_percent=0.0000

                            //base_price_incl_tax=31.6000 (each)

                            }

                            double unitPrice = OWDUtilities.roundDouble(originalPrice,2);
                            if (unitPrice>0.00 && unitPrice == OWDUtilities.roundDouble(Double.parseDouble((String) item.get("row_invoiced"))) && 1<Integer.parseInt(((String) item.get("qty_ordered")).substring(0, ((String) item.get("qty_ordered")).indexOf("."))))
                            {
                                 unitPrice = OWDUtilities.roundDouble(originalPrice/Double.parseDouble(((String) item.get("qty_ordered")).substring(0, ((String) item.get("qty_ordered")).indexOf("."))),2);
                            }



                            policy.addLineItemToOrder(order, realSKU.trim(), (String) item.get("name"), ""+unitPrice, ((String) item.get("qty_ordered")).substring(0, ((String) item.get("qty_ordered")).indexOf(".")));

                        } else{
                        policy.addLineItemToOrder(order, realSKU.trim(), (String) item.get("name"), (String) item.get("price"), ((String) item.get("qty_ordered")).substring(0, ((String) item.get("qty_ordered")).indexOf(".")));
                        }
                    }

                    if (autoShipManager != null) {
                        autoShipManager.addItemToList(mo, order, originalSKU, OWDUtilities.roundFloat(Float.parseFloat((String) item.get("price"))), Integer.parseInt(((String) item.get("qty_ordered")).substring(0, ((String) item.get("qty_ordered")).indexOf("."))));
                    }
                }
                /* if (order.clientID.equals("438")) {
                    if (itemIsGiftWrapped) {
                        policy.addLineItemToOrder(order, "GIFTWRAP", "Gift Wrap For Item " + realSKU, "6.95", ((String) item.get("qty_ordered")).substring(0, ((String) item.get("qty_ordered")).indexOf(".")));
                        ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).insertedItem = true;
                    }
                }*/


            }
        }

        if (order.containsPhysicalItem() || !order.noVirtualOnlyOrders) {
            order.total_tax_cost = Float.parseFloat(mo.order.get("tax_amount"));
            order.total_shipping_cost = Float.parseFloat(mo.order.get("shipping_amount"));
            try {
                order.discount = Math.abs(Float.parseFloat(mo.order.get("discount_amount")));
            } catch (Exception ex) {
                order.discount = 0.00f;
            }
            String shipMethod = null;
            log.debug("Line Count:" + order.getLineCount() + ":" + order.getTotalUnitQuantity());
            if (order.getTotalUnitQuantity() > 0) {
                try {
                    log.debug("old ship method: " + mo.order.get("shipping_description"));
                    shipMethod = translateShipMethod(policy, mo.order.get("shipping_description"), order);
                    log.debug("New ship method: " + shipMethod);
                    order.getShippingInfo().setShipOptions(shipMethod, "Prepaid", "");

                } catch (Exception ex) {
                    order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
                    order.is_future_ship = 1;
                    CasetrackerAPI.createNewCaseForClient(client.company_name + " store order " + order.order_refnum + " held - correct and release",
                            "Unable to determine ship method for method '" + mo.order.get("shipping_description") + "'; check and verify address and release to ship. Also check and correct shipping address for any subscriptions created as a result of this order. Ship method defaulted to Priority Mail.", clientID + "", null, CasetrackerAPI.getLoginToken());
                    Mailer.sendMail(client.company_name + " order " + order.order_refnum + " received on hold", "Unable to determine ship method for method '" + mo.order.get("shipping_description") + "'; check and verify import setup\n\n" + order.getShippingAddress().toString(), "servicerequests@owd.com", "do-not-reply@owd.com");
                    order.hold_reason = "Unable to determine ship method for method '" + mo.order.get("shipping_description") + "'; check and verify";
                }
                //  shipMethod="Picked Up";

            } else {
                order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");

            }
            order.recalculateBalance();
            order.order_type = "Magento";

            order.bill_cc_type = "CL";
            order.forcePayment = false;
            order.is_paid = 1;

            order.paid_amount = order.total_order_cost;

            if (mo.isCheckOrder()) {
                order.bill_cc_type = "CK";
                order.is_future_ship = 1;
                order.order_type = "Magento Mail Order";
            }

            if (mo.order.get("remote_ip") != null) {
                if (mo.order.get("remote_ip").startsWith("96.2.236")) {
                    order.order_type = order.order_type + " (OWDCC)";
                }

                if (mo.order.get("remote_ip").startsWith("172.16")) {
                    order.order_type = order.order_type + " (OWDCC)";
                }

                if (mo.order.get("remote_ip").startsWith("192.168")) {
                    order.order_type = order.order_type + " (OWDCC)";
                }


            }
            try {
                if (mo.payment != null && mo.payment.containsKey("po_number") && (!(mo.payment.get("po_number").contains("null")))) {
                    order.po_num = mo.payment.get("po_number");

                }
            } catch (NullPointerException nex) {

            }
            if (getOwdClient().getChargeOnShip() == 1 || autoShipManager != null) {
                if (mo.payment.get("cc_number_enc").equals("null") || "".equals(mo.payment.get("cc_number_enc"))) {

                } else {

                    order.cc_num = mo.payment.get("cc_number_enc");
                    order.cc_exp_mon = Integer.parseInt(mo.payment.get("cc_exp_month"));
                    order.cc_exp_year = Integer.parseInt(mo.payment.get("cc_exp_year"));
                }

            }


            order.gift_message = "";
            order.is_gift = "0";
            String gift_message = mo.order.get("gift_message");
            if (gift_message != null) {
                if (gift_message.trim().length() > 0) {
                    order.gift_message = gift_message;
                    order.is_gift = "1";
                }
            }

            policy.setSignatureRequired(order);

            if (orderCustomizer != null) {
                orderCustomizer.customizeOrder(this, order, mo);
            }

            String fraudCheck = "";
            if (getOwdClient().getChargeOnShip() == 1 && getOwdClient().getPpProc().contains("TransFirst")) {
                if (order.total_order_cost >= 250.00) {
                    fraudCheck = "(Order value >= $250.00)";
                    order.is_future_ship = 1;

                }
                if (order.getShippingAddress().getAddress_one().toUpperCase().contains("2116 CAMILLE")) {
                    fraudCheck = "(known fraudulent shipto address)";
                    order.is_future_ship = 1;

                }
                if (order.total_order_cost >= 200.00 && !(order.getBillingAddress().address_one.equalsIgnoreCase(order.getShippingAddress().address_one))) {
                    fraudCheck = fraudCheck + "(Different billto/shipto address and value >= $200.00)";
                    order.is_future_ship = 1;
                }
            }
            policy.saveNewOrder(order, false);
            String orderNum = order.orderID;
            log.debug("order id=" + order.orderID);
            if (orderNum == null || "".equals(orderNum) || "0".equals(orderNum)) {
                log.debug("Bad order!!");
                if (autoShipManager != null) {
                    //  autoShipManager.createSubscription(order);
                }
            } else {

                if (OWDUtilities.isValidEmailAddress(order.getBillingContact().email == null ? "" : order.getBillingContact().email)) {
                    policy.sendCustomerEmailConfirmation(order);
                }
                policy.sendNotificationMessage(order, null, null);

                if (getOwdClient().getChargeOnShip() == 1) {
                    saveWSJMagentoAuth(order);

                }
                if (autoShipManager != null) {
                    autoShipManager.createSubscription(order);
                }
                if (fraudCheck.length() > 0) {
                    heldForFraudCheck(order, fraudCheck);
                }
                if(order.is_backorder==1 && order.clientID.equals("471")){
                    if(order.getBillingContact().email.length()>0){
                        sendBackorderEmail(order.getBillingContact().email);
                    }
                }

                if(order.is_backorder==1 && order.clientID.equals("528"))
                {
                    sendPMConceptsBackorderEmail(order);
                }
            }
        }
    }

    public static void sendBackorderEmail( String email)
    {
        if(OWDUtilities.isValidEmailAddress(email))
        {

            String subject = "Backorder Notification";
            String emailBody =
                    "Dear Valued Customer, \n" +
                            "\n" +
                            "Thank you for your order and for choosing Gildan� USA. \n" +
                            "\n" +
                            "Unfortunately, one or more of the item(s) you ordered are now out-of-stock. We expect these items to arrive shortly, and we will ship your entire order as soon as they do. \n" +
                            "\n" +
                            "If you have any questions or you�d like to make a change to your order, please contact us by emailing customerservice@gildanonline.com or calling 1-866-210-5946 and we�d be happy to assist you. Phone representatives are available between the hours of 7 a.m. and 9 p.m. CST, Monday through Friday. \n" +
                            "\n" +
                            "We appreciate your business and apologize for any inconvenience you might have experienced. \n" +
                            "\n" +
                            "Kind Regards, \n" +
                            "\n" +
                            "Gildan� USA\n" +
                            "Customer Service ";

            try{

                Mailer.sendMail(subject,emailBody,email,"customerservice@gildanonline.com");
            }   catch(Exception ex)
            {
                ex.printStackTrace();
            }

        }

    }

    public static void sendPMConceptsBackorderEmail(Order order)
    {
        if(OWDUtilities.isValidEmailAddress(order.getBillingContact().email))
        {

            String subject = "Update on your order "+order.order_refnum;
            String emailBody =
                    "\n" +
                            "Thank you for your order.  Unfortunately, we are temporarily out of stock on " +
                            "the item(s) listed below.\n" +
                            "\n" +
                            "We will make every effort to fulfill your order as quickly as possible.\n\n" +
                            "Should you have any questions about your order, please contact us at " +
                            "customerservice@amatalife.com, or call 1-800-760-9090.\n" +
                            "\n" +
                            "Thank you,\n" +
                            "\n" +
                            "Amata Life by Dr. Christiane Northrup\n\nOut of stock items:\n\n";

            try{
            for (LineItem line:(Vector<LineItem>)order.skuList)
            {
                OwdInventory i = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,Integer.parseInt(line.inventory_fkey));
                if((line.quantity_backordered>0) || (line.quantity_actual<1 && (line.quantity_request>(OrderUtilities.getAvailableInventory(""+i.getInventoryId(), FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId())))) )
                {
                emailBody = emailBody+line.client_part_no+"\n";
                }
            }



                Mailer.sendMail(subject,emailBody,order.getBillingContact().email,"customerservice@amatalife.com");
            }   catch(Exception ex)
            {
                ex.printStackTrace();
            }

        }

    }
    public static String translateShipMethod(ClientPolicy policy, String moShip, Order order) {
        moShip = moShip.toUpperCase();
        moShip = moShip.toUpperCase().replaceAll("SELECT SHIPPING METHOD - ", "");
        moShip = moShip.toUpperCase().replaceAll("SHIPPING METHOD - ", "");


        String moShipTranslated = null;
        log.debug("moship=" + moShip);


        if (moShip.contains("UPS") || moShip.contains("UNITED PARCEL SERVICE")) {
            //ups
            if (moShip.indexOf("NEXT DAY") >= 0) {
                moShipTranslated = "UPS Next Day Air Saver";
            } else if (moShip.indexOf("GROUND") >= 0 && order.getShippingAddress().country.toUpperCase().trim().equals("CANADA")) {
                moShipTranslated = "UPS Standard Canada";
            } else if (moShip.trim().equals("GROUND") && order.clientID.equals("407")) {
                List<String> alternateShipMethodList = new ArrayList<String>();
                alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
                moShipTranslated = RateShopper.rateShop(order, alternateShipMethodList);
            } else if (moShip.indexOf("GROUND") >= 0) {
                moShipTranslated = "UPS Ground";
            } else if (moShip.indexOf("2 DAY") >= 0) {
                moShipTranslated = "UPS 2nd Day Air";
            } else if (moShip.indexOf("2ND DAY") >= 0) {
                moShipTranslated = "UPS 2nd Day Air";
            } else if (moShip.indexOf("3 DAY") >= 0) {
                moShipTranslated = "UPS 3 Day Select";
            } else if (moShip.indexOf("CANADA STANDARD") >= 0 || moShip.indexOf("STANDARD CANADA") >= 0) {
                moShipTranslated = "UPS Standard Canada";
            } else if (moShip.indexOf("WORLDWIDE EXPRESS SAVER") >= 0) {
                moShipTranslated = "UPS Worldwide Express Saver";
            } else if (moShip.indexOf("WORLDWIDE EXPEDITED") >= 0) {
                moShipTranslated = "UPS Worldwide Expedited";
            }
        } else if (moShip.startsWith("FDX") || moShip.startsWith("FEDEX") || moShip.startsWith("FEDERAL")) {
            if (moShip.indexOf("HOME") >= 0) {
                moShipTranslated = "FedEx Home Delivery";
            } else if (moShip.indexOf("GROUND") >= 0) {
                moShipTranslated = "FedEx Ground";
            } else if (moShip.indexOf("2 DAY") >= 0) {
                moShipTranslated = "FedEx 2Day";
            } else if (moShip.indexOf("EXPRESS SAVER") >= 0) {
                moShipTranslated = "FedEx Express Saver";
            }  else if (moShip.indexOf("INTERNATIONAL ECONOMY") >= 0) {
                moShipTranslated = "FedEx International Economy";
            }else if (moShip.indexOf("INTERNATIONAL PRIORITY") >= 0) {
                moShipTranslated = "FedEx International Priority";
            }else  if (moShip.indexOf("OVERNIGHT") >= 0) {
                moShipTranslated = "FedEx Standard Overnight";
            }
        } else {
            //usps


            if ((moShip.indexOf("EXPRESS MAIL") >= 0 ||moShip.contains("PRIORITY MAIL EXPRESS")) && order.getShippingAddress().isInternational()) {
                moShipTranslated = "USPS Priority Mail Express International";
            } else if (moShip.indexOf("EXPRESS MAIL") >= 0||moShip.contains("PRIORITY MAIL EXPRESS")) {
                moShipTranslated = "USPS Priority Mail Express";
            } else if (moShip.indexOf("OVERNIGHT") >= 0 && order.clientID.equals("407")) {
                List<String> alternateShipMethodList = new ArrayList<String>();
                alternateShipMethodList.add("FDX.PRI");
                alternateShipMethodList.add("FDX.STD");
                alternateShipMethodList.add("POS.EXP");
                moShipTranslated = RateShopper.rateShop(order, alternateShipMethodList);

            } else if (moShip.indexOf("CLASS MAIL INTERNATIONAL") >= 0 || moShip.indexOf("CLASS PACKAGE INTERNATIONAL") >= 0) {
                moShipTranslated = "USPS First-Class Mail International";
            } else if (moShip.indexOf("FIRST-CLASS") >= 0 || moShip.indexOf("FIRST CLASS") >= 0) {
                moShipTranslated = "USPS First-Class Mail";
            } else if (moShip.indexOf("PRIORITY MAIL INTERNATIONAL") >= 0) {
                moShipTranslated = "USPS Priority Mail International";
            } else if (moShip.indexOf("PARCEL POST") >= 0) {
                moShipTranslated = "USPS Parcel Select Nonpresort";
            } else if (moShip.indexOf("PRIORITY") >= 0 && order.getShippingAddress().isInternational()) {
                moShipTranslated = "USPS Priority Mail International";
            } else if (moShip.indexOf("PRIORITY") >= 0) {
                moShipTranslated = "USPS Priority Mail";
            } else if (moShip.indexOf("LIBRARY") >= 0) {
                moShipTranslated = "USPS Priority Mail";
            } else if (moShip.indexOf("MEDIA MAIL") >= 0) {
                moShipTranslated = "USPS Media Mail Single-Piece";
            } else if (moShip.indexOf("INTERNATIONAL") >= 0 && order.clientID.equals("407")) {
                List<String> alternateShipMethodList = new ArrayList<String>();
                alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.STD");
                moShipTranslated = RateShopper.rateShop(order, alternateShipMethodList);

            }  else if ((moShip.indexOf("FREE") >= 0 || moShip.indexOf("STANDARD") >= 0) && order.clientID.equals("550")) {
                List<String> alternateShipMethodList = new ArrayList<String>();

                alternateShipMethodList.add("TANDATA_USPS.USPS.PS_NONPRESORT");
                alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_STD");
                moShipTranslated = RateShopper.rateShop(order, alternateShipMethodList);

            } else if ((moShip.indexOf("FLAT RATE") >= 0) && order.clientID.equals("592")) {
                List<String> alternateShipMethodList = new ArrayList<String>();

                //alternateShipMethodList.add("TANDATA_USPS.USPS.PS_NONPRESORT");
                alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
                // alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                // alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_STD");
                alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.GND");
                alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                alternateShipMethodList.add("CONNECTSHIP_UPS.UPS.STD");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.IECO");
                moShipTranslated = RateShopper.rateShop(order, alternateShipMethodList);

            }
            else if ((moShip.contains("PREMIUM") || moShip.contains("EXPEDITED")) && order.clientID.equals("550")) {
                List<String> alternateShipMethodList = new ArrayList<String>();
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
                moShipTranslated = RateShopper.rateShop(order, alternateShipMethodList);

            } else if (moShip.indexOf("3 DAY") >= 0 && order.clientID.equals("407")) {
                List<String> alternateShipMethodList = new ArrayList<String>();
                alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                alternateShipMethodList.add("FDX.ECO");
                moShipTranslated = RateShopper.rateShop(order, alternateShipMethodList);

            } else if ((moShip.indexOf("FLAT RATE")>=0 || moShip.indexOf("STANDARD") >= 0) && order.clientID.equals("407")) {
                List<String> alternateShipMethodList = new ArrayList<String>();
                alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_STD");

                moShipTranslated = RateShopper.rateShop(order, alternateShipMethodList);

            } else if (moShip.indexOf("GROUND") >= 0) {
                moShipTranslated = "FedEx Ground";
            }
        }

        log.debug("moshipTranslated=" + moShipTranslated);
        if (moShipTranslated == null)    //ie, couldn't find it in the above list, like "Standard"
        {
            log.debug("getting real ship method through clientpolicy object " + policy.getClass().getCanonicalName() + " from " + moShip);
            moShipTranslated = policy.getOWDShipMethodForExternalShipMethodName("magento", moShip, order);
            if (moShipTranslated.contains("No valid rate")) {
                if (moShip.toUpperCase().contains("STANDARD") || moShip.toUpperCase().contains("FREE")  || moShip.toUpperCase().contains("FLAT RATE") || (order.clientID.equals("476") && moShip.toUpperCase().contains("FLAT RATE"))) {

                    List<String> alternateShipMethodList = new ArrayList<String>();
                    if (!(order.clientID.equals("471"))) //Gildan fedex only
                    {
                        alternateShipMethodList.add("TANDATA_USPS.USPS.FIRST");
                        alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
                        alternateShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
                        alternateShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
                    }
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");/*
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
                    alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_STD");*/
                    moShipTranslated = RateShopper.rateShop(order, alternateShipMethodList);


                }
            }
        }
        if (moShipTranslated == null) {
            moShipTranslated = moShip;
        }

        return moShipTranslated;
    }


    private String getConfigurableItemRealSku(List<HashMap<String, String>> items, String configSku, String configItemId) {
        int testint = Integer.parseInt(configItemId) + 1;
        for (HashMap item : items) {
            if ((item.get("product_type").equals("simple")) && Integer.parseInt("" + item.get("item_id")) == testint
                //      && ("" + item.get("sku")).indexOf(configSku) >= 0
                    ) {
                skipItemList.add((String) item.get("item_id"));
                return "" + item.get("sku");
            }


        }

        return configSku;

    }


    public MagentoOrder getMagentoOrder(MagentoService service, String sessid, String orderid) throws Exception {
        HashMap<String, Object> dets = new HashMap<String, Object>();
        MagentoOrder mo = new MagentoOrder();

        HashMap<String, Object> arg = new HashMap<String, Object>();
        arg.put("increment_id", dets);
        dets.put("=", orderid);
        List<HashMap> argList = new ArrayList<HashMap>();
        argList.add(arg);

        log.debug("logged in");

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order.info", argList);

        if (obj instanceof HashMap) {
            // log.debug("got Hash size=" + ((HashMap) obj).size());
            for (Object key : ((HashMap) obj).keySet()) {
               //  log.debug("Map key:" + key + ":" + ((HashMap) obj).get(key));
                if (((HashMap) obj).get(key) instanceof String) {
                    mo.order.put((String) key, (String) ((HashMap) obj).get(key));
                }
                Object itemsObj = ((HashMap) obj).get(key);
                if (itemsObj instanceof HashMap) {
                    if ("payment".equals(key)) {
                        //  log.debug("payment");
                        mo.payment = ((HashMap<String, String>) itemsObj);
                        log.debug("REF:"+orderid+" cc:"+mo.payment.get("cc_number_enc")+" exp:"+mo.payment.get("cc_exp_month")+"/"+mo.payment.get("cc_exp_year"));
                    } else if ("billing_address".equals(key)) {
                        //  log.debug("billing");
                        mo.billing = ((HashMap) itemsObj);
                    } else if ("shipping_address".equals(key)) {
                        //  log.debug("shipping");
                        mo.shipping = ((HashMap) itemsObj);
                    } else if ("aitoc_order_custom_data".equals(key)) {
                        log.debug("aitoc");
                        for (Object keym : ((HashMap) itemsObj).values()) {
                            mo.aitoc.add((HashMap) keym);
                        }
                    }
                    for (Object key2 : ((HashMap) itemsObj).keySet()) {
                        //  log.debug("***key:" + key2 + ":" + ((HashMap) itemsObj).get(key2));
                    }
                } else if (itemsObj instanceof HashMap[]) {
                    // log.debug("got Hash[] array size=" + ((HashMap[]) itemsObj).length);
                    for (HashMap keym : ((HashMap[]) itemsObj)) {
                      //  log.debug("keym="+keym);
                        if ("items".equals(key)) {
                          //  log.debug((String) keym.get("product_options"));
                            mo.items.add(keym);
                            Map optionsMap = new HashMap();
                            try {
                                optionsMap = buildOptionsMapFromMagentoString((String) keym.get("product_options"));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                optionsMap = new HashMap();
                            }
                            if (optionsMap.keySet().size() >= 2) {
                                keym.put("options", optionsMap);
                                keym.remove("product_options");
                            }
                        } else if ("status_history".equals(key)) {
                            //    log.debug("status1");
                            mo.status.add(keym);
                        } else if ("aitoc_order_custom_data".equals(key)) {
                            mo.aitoc.add(keym);// = ((HashMap) itemsObj);

                        }
                        for (Object key2 : ((HashMap) keym).keySet()) {

                            //  log.debug("***[]Item key:" + key2 + ":" + ((HashMap) keym).get(key2));
                        }
                    }
                }


            }
        } else {
           // log.debug("" + obj.getClass().toString());
           // log.debug(obj);
        }
       // log.debug("" + mo);
        for (String key : mo.order.keySet()) {
            if (key.contains("cust")) {
               // log.debug("Cust Key: " + key + " Value: " + mo.order.get(key));
            }
        }
        return mo;
    }

    public List<String> getProcessingOrderList(MagentoService service, String sessid, int numberOfDaysToLookBack) throws Exception {
        List<String> orders = new ArrayList<String>();

        HashMap<String, Object> dets = new HashMap<String, Object>();

        HashMap<String, Object> arg = new HashMap<String, Object>();
        arg.put("status", "processing");
        arg.put("created_at", dets);
        //  dets.put("status", "processing");

        dets.put("from", OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, numberOfDaysToLookBack > 0 ? -1 * numberOfDaysToLookBack : numberOfDaysToLookBack));

        dets.put("to", OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, 2));
        //  arg.put("sku","1");
        List<HashMap> argList = new ArrayList<HashMap>();
        argList.add(arg);

        log.debug(arg);
        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order.list", argList);
        if (obj instanceof java.util.HashMap[]) {
            log.debug("got Magento order list size=" + ((HashMap[]) obj).length);
            for (HashMap keym : ((HashMap[]) obj)) {
                log.debug("getting order " + keym.get("increment_id"));
                orders.add((String) keym.get("increment_id"));
            }
        }

        return orders;
    }


    public static void invoiceMagentoOrder(MagentoService service, String sessid, MagentoOrder mo) throws Exception {
        //  HashMap<String, Object> dets = new HashMap<String, Object>();

        //   HashMap<String, Object> arg = new HashMap<String, Object>();
        //   arg.put("status", dets);
        //  dets.put("=", "pending");
        //  arg.put("sku","1");
        Map itemsShipped = new TreeMap();
        log.debug(mo.items);
        for (HashMap item : (List<HashMap<String, String>>) mo.items) {
            try {
                String itemid = ((String) item.get("sku"));
                //   if (itemid > 0) {
                itemsShipped.put(itemid, ((String) item.get("qty_ordered")));//""+Integer.parseInt(item.client_ref_num),""+item.quantity_actual);
                //   }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        log.debug("Invoicing" + mo.order.get("increment_id"));
        log.debug("Invoicing : " + itemsShipped);
        List<Object> argList = new ArrayList<Object>();
        argList.add(mo.order.get("increment_id"));
        argList.add(itemsShipped);
        argList.add("Invoice by OWD");
        argList.add("false");
        argList.add("false");

        log.debug("Invoice "+mo.order.get("increment_id"));
//$newInvoiceId = $proxy->call($sessionId, 'sales_order_invoice.create', array($notInvoicedOrderId, array(), 'Invoice Created', true, true));
        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_invoice.create", argList);
        if (obj instanceof java.util.HashMap[]) {
            log.debug("got Magento order list size=" + ((HashMap[]) obj).length);
            for (HashMap keym : ((HashMap[]) obj)) {
                log.debug("invoice create:" + keym);
            }
        } else if (obj instanceof java.util.HashMap) {

            log.debug("InvoiceMap:" + (HashMap) obj);

        } else {
            log.debug("Invoice ID:" + obj);
        }
        argList = new ArrayList<Object>();
        argList.add(obj + "");
        //   argList.add("");
        //   argList.add("Invoice by OWD");
        //  argList.add("false");
        //   argList.add("false");


//$newInvoiceId = $proxy->call($sessionId, 'sales_order_invoice.create', array($notInvoicedOrderId, array(), 'Invoice Created', true, true));
        /*  obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order_invoice.capture", argList);
                if (obj instanceof java.util.HashMap[]) {
                    log.debug("got Magento order list size=" + ((HashMap[]) obj).length);
                    for (HashMap keym : ((HashMap[]) obj)) {
                        //  log.debug("invoice create:" + keym);
                    }
                } else if (obj instanceof java.util.HashMap) {

                    log.debug((HashMap) obj);

                } else {
                    log.debug("" + obj);
                }
        */


    }

    public HashMap<String, Integer> getMagentoInventoryLevelsBySku(List<String> skus) throws Exception {

        HashMap<String, Integer> response = new HashMap<String, Integer>();
        List<Object> argList = new ArrayList<Object>();
        argList.add(skus);
        //  argList.add(mo.order.get("increment_id"));
        //  argList.add(newstatus);
        Object obj = null;
        if(null == currentService){
            currentService = new MagentoServiceLocator(magentoURL);
        }

        if(currentSession.length()==0){
            currentSession = currentService.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);
        }

        try {
            obj = currentService.getMage_Api_Model_Server_HandlerPort().call(currentSession, "product_stock.list", argList);

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        printMagentoResponseObject(obj);
        if (obj instanceof java.util.HashMap[]) {
            for (HashMap keym : ((HashMap[]) obj)) {

                log.debug("" + keym);
                response.put((String) keym.get("sku"), (int) Math.floor(new Double((String) keym.get("qty"))));
            }
        }

        return response;

    }

    public void setMagentoInventoryPrice(HashMap<String, Float> stockLevels, boolean ignoreIfZero) throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);


        for (String sku : stockLevels.keySet()) {
            float qty = stockLevels.get(sku);

            if (qty < 0.00) {
                qty = 0.0000f;
            }
            if (qty > 0.00 || (!(ignoreIfZero))) {
                List<Object> argList = new ArrayList<Object>();
                //  argList.add(mo.order.get("increment_id"));
                //  argList.add(newstatus);
                argList.add(sku);
                HashMap<String, String> level = new HashMap<String, String>();
                level.put("price", "" + qty);
                // level.put("is_in_stock", "" + (qty == 0 ? (setOutOfStockIfZero ? 0 : 1) : 1));
                argList.add(level);
                Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "catalog_product.update", argList);
                printMagentoResponseObject(obj);
                log.debug("" + obj);
            }
        }

    }

    public void setMagentoInventoryWeight(HashMap<String, Float> stockLevels, boolean ignoreIfZero) throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);


        for (String sku : stockLevels.keySet()) {
            float qty = stockLevels.get(sku);

            if (qty < 0.00) {
                qty = 0.0000f;
            }
            if (qty > 0.00 || (!(ignoreIfZero))) {
                List<Object> argList = new ArrayList<Object>();
                //  argList.add(mo.order.get("increment_id"));
                //  argList.add(newstatus);
                argList.add(sku);
                HashMap<String, String> level = new HashMap<String, String>();
                level.put("weight", "" + qty);
                // level.put("is_in_stock", "" + (qty == 0 ? (setOutOfStockIfZero ? 0 : 1) : 1));
                argList.add(level);
                Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "catalog_product.update", argList);
                printMagentoResponseObject(obj);
                log.debug("" + obj);
            }
        }

    }

    public void setMagentoInventoryLevel(HashMap<String, Integer> stockLevels, boolean setOutOfStockIfZero) throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);


        for (String sku : stockLevels.keySet()) {
            int qty = stockLevels.get(sku);

            List<Object> argList = new ArrayList<Object>();
            //  argList.add(mo.order.get("increment_id"));
            //  argList.add(newstatus);
            log.debug("setting magento inventory level of " + qty + " for sku " + sku);
            argList.add(sku);
            HashMap<String, String> level = new HashMap<String, String>();
            level.put("qty", "" + qty);
            level.put("is_in_stock", "" + (qty == 0 ? (setOutOfStockIfZero ? 0 : 1) : 1));
            argList.add(level);
            Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "product_stock.update", argList);
           // printMagentoResponseObject(obj);
            //log.debug("" + obj);
        }


    }
    public void setMagentoInventoryLevelMultiCall(HashMap<String, Integer> stockLevels, boolean setOutOfStockIfZero) throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoURL);
        if(currentSession.length()==0){
            currentSession = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);
        }

        //Split into groups of x
        List<HashMap<String,Integer>> groupListOfSkus = new ArrayList<HashMap<String,Integer>>();

        int tracker = 0;
        HashMap<String,Integer> group = new HashMap<String,Integer>();
        for (String sku : stockLevels.keySet()) {
            group.put(sku, stockLevels.get(sku));
            tracker++;
            if (tracker == 100) {
                groupListOfSkus.add(group);
                group = new HashMap<String, Integer>();
                tracker = 0;
            }

        }
        System.out.println(stockLevels.size());
        System.out.println(groupListOfSkus.size());

        //Loop through each group of x and multicall update
        for(HashMap<String,Integer> skuGroup: groupListOfSkus) {
            List<Object> calls = new ArrayList<Object>();

            for (String sku : skuGroup.keySet()) {
                int qty = skuGroup.get(sku);
                List<Object> argList = new ArrayList<Object>();
                argList.add(sku);
                HashMap<String, String> level = new HashMap<String, String>();
                level.put("qty", "" + qty);
                level.put("is_in_stock", "" + (qty == 0 ? (setOutOfStockIfZero ? 0 : 1) : 1));
                argList.add(level);

                List<Object> outerCall = new ArrayList<Object>();
                outerCall.add("product_stock.update");
                outerCall.add(argList);
                calls.add(outerCall);
            }
            System.out.println(calls);

            Object obj;
            try {
                obj = service.getMage_Api_Model_Server_HandlerPort().multiCall(currentSession, calls.toArray(), "");
            } catch (Exception e) {
                if (e.getMessage().contains("Session expired")) {
                    currentSession = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);
                    obj = service.getMage_Api_Model_Server_HandlerPort().multiCall(currentSession, calls.toArray(), "");

                } else {
                    throw new Exception(e.getMessage());
                }
            }
         //   printMagentoResponseObject(obj);
           // log.debug("" + obj);
        }


       /* for() {
            int qty = stockLevels.get(sku);

            List<Object> argList = new ArrayList<Object>();
            //  argList.add(mo.order.get("increment_id"));
            //  argList.add(newstatus);
            log.debug("setting magento inventory level of " + qty + " for sku " + sku);
            argList.add(sku);
            HashMap<String, String> level = new HashMap<String, String>();
            level.put("qty", "" + qty);
            level.put("is_in_stock", "" + (qty == 0 ? (setOutOfStockIfZero ? 0 : 1) : 1));
            argList.add(level);
            Object obj;
            try {
                obj = service.getMage_Api_Model_Server_HandlerPort().call(currentSession, "product_stock.update", argList);
            }catch (Exception e){
                if(e.getMessage().contains("Session expired")){
                    currentSession = service.getMage_Api_Model_Server_HandlerPort().login(magentoLogin, magentoPassword);
                    obj = service.getMage_Api_Model_Server_HandlerPort().call(currentSession, "product_stock.update", argList);

                }else{
                    throw new Exception(e.getMessage());
                }
        }*/




    }
    public static void setMagentoOrdeCancel(MagentoService service, String sessid, String incrementId) throws Exception{


        try {
            Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order.cancel", incrementId);

            log.debug("" + obj);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void setMagentoOrderStatus(MagentoService service, String sessid, MagentoOrder mo, String newstatus) throws Exception {
        List<String> argList = new ArrayList<String>();
        argList.add(mo.order.get("increment_id"));
        argList.add(newstatus);


        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order.addComment", argList);

        log.debug("" + obj);


    }

    public static void setMagentoOrderStatus(MagentoService service, String sessid, String magentoID, String newstatus) throws Exception {
        List<Object> argList = new ArrayList<Object>();
        argList.add(magentoID);
        argList.add(newstatus);
        argList.add("");
        argList.add(false);


        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order.addComment", argList);

        log.debug("" + obj);


    }

    public List<String> getPendingOrderList(MagentoService service, String sessid, int numberOfDaysToLookBack) throws Exception {
        List<String> orders = new ArrayList<String>();


        HashMap<String, Object> dets = new HashMap<String, Object>();

        HashMap<String, Object> arg = new HashMap<String, Object>();
        arg.put("status", "pending");
        arg.put("created_at", dets);
        //  dets.put("status", "processing");
        dets.put("from", OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, numberOfDaysToLookBack > 0 ? -1 * numberOfDaysToLookBack : numberOfDaysToLookBack));
        dets.put("to", OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, 2));
        //  arg.put("sku","1");
        List<HashMap> argList = new ArrayList<HashMap>();
        argList.add(arg);
        log.debug(arg);
        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "sales_order.list", argList);
        if (obj instanceof java.util.HashMap[]) {
            //      log.debug("got Magento order list size=" + ((HashMap[]) obj).length);
            for (HashMap keym : ((HashMap[]) obj)) {
                orders.add((String) keym.get("increment_id"));
            }
        }

        return orders;
    }


    public class MagentoOrder {
        public List<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
        public HashMap<String, String> shipping = new HashMap<String, String>();
        public HashMap<String, String> billing = new HashMap<String, String>();
        public HashMap<String, String> payment = new HashMap<String, String>();
        public List<HashMap<String, String>> aitoc = new ArrayList<HashMap<String, String>>();
        public List<HashMap<String, String>> status = new ArrayList<HashMap<String, String>>();

        public Map<String, String> order = new HashMap<String, String>();

        @Override
        public String toString() {
            for (HashMap item : items) {
                log.debug("ITEM: " + item);
            }
            for (HashMap item : status) {
                log.debug("STATUS: " + status);
            }
            log.debug("BILLING: " + billing);
            log.debug("SHIPPING: " + shipping);
            log.debug("PAYMENT: " + payment);
            log.debug("ORDER: " + order);
            log.debug("AITOC: " + aitoc);
            return "MAGENTO ORDER";
        }

        public boolean isCheckOrder() {
            if (payment == null || payment.keySet().size() == 0 || "checkmo".equals(payment.get("method"))) {
                return true;

            } else {
                return false;
            }

        }

        public void printOptions() throws Exception {
            for (HashMap item : items) {
                log.debug("ITEM: " + item);
                log.debug("ITEM OPTIONS: " + item.get("options"));
                // // String itemopts = (String)item.get("product_options");
                //   log.debug(""+item.get("product_options").getClass());
                // Map optionsMap = new HashMap();
                //  buildOptionsMapFromMagentoString(itemopts,optionsMap);
                //
                //
                log.debug("" + item.get("options"));
                log.debug("" + OWDUtilities.OToS((Map) item.get("options")));
                log.debug("" + OWDUtilities.SToO(OWDUtilities.OToS((Map) item.get("options"))));
            }

        }
    }


    public static Map buildOptionsMapFromMagentoString(String instr) throws Exception {

        log.debug("Magento option string = " + instr);
        if (instr == null) {
            return new TreeMap();
        }
        try{
            return (Map) (new SerializedPhpParser(instr).parse());
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return new TreeMap();
        }
        /*  log.debug(instr);
       int optionsIndex = instr.indexOf("a:");
       if (optionsIndex == 0) {
           instr = instr.substring(instr.indexOf(":") + 1);
           int arraycnt = Integer.parseInt(instr.substring(0, instr.indexOf(":")));
           instr = instr.substring(instr.indexOf("{") + 1);
           for (int i = 0; i < arraycnt; i++) {
               log.debug("i=" + i + "::" + instr);
               String keyName = "";
               if (instr.startsWith("i:")) {
                   keyName = instr.substring(instr.indexOf(":") + 1, instr.indexOf(";"));
               } else if (instr.startsWith("b:")) {
                   keyName = instr.substring(instr.indexOf(":") + 1, instr.indexOf(";"));
               } else {
                   int idxx1 = (instr.indexOf("\"") + 1);
                   int idxx2 = (instr.indexOf(";") - 2);
                   keyName = instr.substring(instr.indexOf("\"") + 1, instr.indexOf(";") - 1);
               }
               instr = instr.substring(instr.indexOf(";") + 1);
               if (instr.startsWith("s")) {
                   log.debug("s-instr=" + i + "::" + instr);
                   int idx1 = (instr.indexOf("\"") + 1);
                   int idx2 = (instr.indexOf(";") - 2);
                   if (idx2 >= idx1) {
                       list.put(keyName, instr.substring(instr.indexOf("\"") + 1, instr.indexOf(";") - 1));
                   } else {
                       list.put(keyName, "");
                   }
                   instr = instr.substring(instr.indexOf(";") + 1);
               } else if (instr.startsWith("i")) {
                   list.put(keyName, instr.substring(instr.indexOf(":") + 1, instr.indexOf(";")));
                   instr = instr.substring(instr.indexOf(";") + 1);

               } else if (instr.startsWith("a")) {
                   Map alist = new HashMap();
                   instr = buildOptionsMapFromMagentoString(instr, alist);

                   list.put(keyName, alist);
               }

           }
           instr = instr.substring(instr.indexOf("}") + 1);
         //  log.debug("aystr:" + instr);

       } else {
           throw new Exception("cannot parse magento string to array (" + instr + ")");
       }


       return instr;*/

    }


    public static void saveWSJMagentoAuth(Order order) throws Exception {
        FinancialTransaction trans = new FinancialTransaction("0",

                "0",

                "0",

                OWDUtilities.getSQLDateForToday(),

                "magento",

                "magento",

                OWDUtilities.getSQLDateForToday(),

                1.00f,

                OWDUtilities.getSQLDateForToday(),

                FinancialTransaction.TRANS_OK,

                "" + FinancialTransaction.TRANS_CC,

                1,

                0,

                "" + FinancialTransaction.transTypeAuthOnlyRequest,

                OWDUtilities.getLastNameFromWholeName(order.getBillingContact().getName()),

                OWDUtilities.getFirstNameFromWholeName(order.getBillingContact().getName()),

                order.getBillingAddress().address_one,

                order.getBillingAddress().address_two,

                order.getBillingAddress().zip,

                order.getBillingAddress().company_name,

                order.getBillingAddress().city,

                order.getBillingAddress().country,

                order.getBillingAddress().state,

                order.getBillingContact().email,

                order.getBillingContact().phone,

                "",

                "0.00",

                "0.00",

                "",

                "",

                "",

                "",

                "",

                OWDUtilities.getExpDateStr(order.cc_exp_mon, order.cc_exp_year),

                order.cc_num,

                "",

                "",

                "",

                "",

                "",

                "MAGENTO",

                0,

                0,

                "",

                "");

        trans.order_fkey = order.getID();
        trans.dbsave(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

        HibUtils.commit(HibernateSession.currentSession());
    }


    public void updateInventoryPrices() throws Exception {


        HashMap<String, Float> updateMap = new HashMap<String, Float>();

        List<HashMap<String, String>> storeSkus = this.getAllInventoryInfo();

        HashMap<String, Float> owdPriceMap = new HashMap<String, Float>();


        ResultSet rs = HibernateSession.getResultSet("select inventory_num,price from owd_inventory (NOLOCK) where client_fkey=" + this.clientID);

        while (rs.next()) {

            owdPriceMap.put(rs.getString(1), rs.getFloat(2));

        }

        for (HashMap<String, String> storeSkuMap : storeSkus) {
            log.debug(storeSkuMap);
            String sku = storeSkuMap.get("sku");
            log.debug("checking SKU " + sku);
            if (owdPriceMap.containsKey(sku)) {
                log.debug("getting qty for SKU " + sku);
                updateMap.put(sku, owdPriceMap.get(sku));
            } else {
                log.debug("no owd entry found for sku " + sku);
            }

        }


        log.debug("updateMap:" + updateMap);

        this.setMagentoInventoryPrice(updateMap, false);
    }

    public void updateInventoryLevels() throws Exception {


        HashMap<String, Integer> updateMap = new HashMap<String, Integer>();
        HashMap<String, Float> weightUpdateMap = new HashMap<String, Float>();

        List<HashMap<String, String>> storeSkus = this.getAllInventoryInfo();

        HashMap<String, Integer> owdStockMap = new HashMap<String, Integer>();
        HashMap<String, Float> owdWeightMap = new HashMap<String, Float>();


        ResultSet rs = HibernateSession.getResultSet("select inventory_num,case when is_auto_inventory=1 then 99999 else qty_on_hand end as qty_on_hand,is_active, weight_lbs from owd_inventory (NOLOCK) join owd_inventory_oh (NOLOCK) on inventory_id=inventory_fkey and client_fkey=" + this.clientID);

        while (rs.next()) {

            owdStockMap.put(rs.getString(1), (1 == rs.getInt(3) ? rs.getInt(2) : 0));
            owdWeightMap.put(rs.getString(1), rs.getFloat(4));

        }

        for (HashMap<String, String> storeSkuMap : storeSkus) {
            log.debug(storeSkuMap);
            String sku = storeSkuMap.get("sku");
            log.debug("checking SKU " + sku);
            if (owdStockMap.containsKey(sku)) {
                log.debug("getting qty for SKU " + sku);
                updateMap.put(sku, owdStockMap.get(sku));
                weightUpdateMap.put(sku, owdWeightMap.get(sku));
            } else {
                log.debug("no owd entry found for sku " + sku);
            }

        }


        log.debug("updateMap:" + updateMap);

        this.setMagentoInventoryLevel(updateMap, false);
        this.setMagentoInventoryWeight(weightUpdateMap, true);
    }

    public void sendInventoryDiscrepancyReport(String emailAddress) throws Exception {


        HashMap<String, HashMap> remoteSkuMap = new HashMap<String, HashMap>();

        List<HashMap<String, String>> storeSkus = this.getAllInventoryInfo();
        List<String> remoteList = new ArrayList<String>();
        List<String> atStoreNotOwdList = new ArrayList<String>();
        List<String> atOwdNotStoreList = new ArrayList<String>();

        for (HashMap<String, String> storeSkuMap : storeSkus) {
          //  log.debug(storeSkuMap);
          //  log.debug(Arrays.asList(storeSkuMap.get("website_ids"))+"");
            try {
             //   getProductAttributeSetList();
            //    getInventoryDetails(storeSkuMap.get("sku"));
            }catch(Exception ex){

            }
            remoteList.add(storeSkuMap.get("sku"));
            remoteSkuMap.put(storeSkuMap.get("sku"), storeSkuMap);
        }

        Map<String,  String> localMap = new HashMap<String,String>();

        ResultSet rs = HibernateSession.getResultSet("select inventory_num, description, item_color,item_size,qty_on_hand from owd_inventory (NOLOCK) " +
                "join owd_inventory_oh h on h.inventory_fkey=inventory_id where is_active=1 and qty_on_hand>=0 and client_fkey=" + this.clientID);

        while (rs.next()) {

            localMap.put(rs.getString(1),rs.getString(2).replaceAll("\n","").replaceAll("\r","")+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));

        }
        List<String> matchedSkus = new ArrayList<String>();
        for (String localSku : localMap.keySet()) {

            if (!remoteList.contains(localSku)) {
                atOwdNotStoreList.add(localSku);
            }else{
                matchedSkus.add(localSku);
            }

        }
        for (String remoteSku : remoteList) {

            if (!localMap.keySet().contains(remoteSku)) {
                atStoreNotOwdList.add(remoteSku);
            }

        }

        StringBuilder str = new StringBuilder();

        str.append("At OWD Not Store (in stock):\n\n");
        for (String sku : atOwdNotStoreList) {

            str.append(sku + "\t"+localMap.get(sku)+"\n");

        }
        str.append("\n\n");
        str.append("At Store Not OWD:\n\n");
        for (String sku : atStoreNotOwdList) {
            log.debug(remoteSkuMap.get(sku));
            if(remoteSkuMap.get(sku).get("type").equals("simple")) {
                str.append(sku + "\t" + (""+remoteSkuMap.get(sku).get("name")).replaceAll("\n","").replaceAll("\r","") + "\n");
            }

        }

        log.debug(str);
        Mailer.sendMailWithAttachment("magento sku info","magento sku info",emailAddress,str.toString().getBytes(),"skus.csv","text/csv");
    }

    public void sendAdvancedInventoryDiscrepancyReport(String emailAddress) throws Exception {


        HashMap<String, HashMap> remoteSkuMap = new HashMap<String, HashMap>();

        List<HashMap<String, String>> storeSkus = this.getAllInventoryInfo();
        List<String> remoteList = new ArrayList<String>();
        List<String> atStoreNotOwdList = new ArrayList<String>();
        List<String> atOwdNotStoreList = new ArrayList<String>();

        for (HashMap<String, String> storeSkuMap : storeSkus) {
            //  log.debug(storeSkuMap);
            //  log.debug(Arrays.asList(storeSkuMap.get("website_ids"))+"");
            try {
                //   getProductAttributeSetList();
                //    getInventoryDetails(storeSkuMap.get("sku"));
            }catch(Exception ex){

            }
            remoteList.add(storeSkuMap.get("sku"));
            remoteSkuMap.put(storeSkuMap.get("sku"), storeSkuMap);
        }

        Map<String,  String> localMap = new HashMap<String,String>();
        Map<String, Integer> localOHMap = new HashMap<String,Integer>();
        ResultSet rs = HibernateSession.getResultSet("select inventory_num, description, item_color,item_size,qty_on_hand from owd_inventory (NOLOCK) " +
                "join owd_inventory_oh h on h.inventory_fkey=inventory_id where is_active=1 and client_fkey=" + this.clientID);

        while (rs.next()) {

            localMap.put(rs.getString(1),rs.getString(2).replaceAll("\n","").replaceAll("\r","")+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5));
            localOHMap.put(rs.getString(1),rs.getInt(5));
        }
        List<String> matchedSkus = new ArrayList<String>();
        for (String localSku : localMap.keySet()) {

            if (!remoteList.contains(localSku)) {
                atOwdNotStoreList.add(localSku);
            }else{
                matchedSkus.add(localSku);
            }

        }
        for (String remoteSku : remoteList) {

            if (!localMap.keySet().contains(remoteSku)) {
                atStoreNotOwdList.add(remoteSku);
            }

        }

        StringBuilder str = new StringBuilder();

        str.append("At OWD Not Store :\n\n");
        for (String sku : atOwdNotStoreList) {

            str.append(sku + "\t"+localMap.get(sku)+"\n");

        }
        str.append("\n\n");
        str.append("At Store Not OWD:\n\n");
        for (String sku : atStoreNotOwdList) {
            log.debug(remoteSkuMap.get(sku));
            if(remoteSkuMap.get(sku).get("type").equals("simple")) {
                str.append(sku + "\t" + (""+remoteSkuMap.get(sku).get("name")).replaceAll("\n","").replaceAll("\r","") + "\n");
            }

        }
        str.append("\n\n");
        str.append("Inventory issues");
        for (String sku : matchedSkus) {
            HashMap<String,Integer> remoteInventory = getMagentoInventoryLevelsBySku(Arrays.asList(sku));
            System.out.println(localOHMap.get(sku));
            System.out.println(remoteInventory.get(sku));
            if(localOHMap.get(sku).equals(remoteInventory.get(sku))){
                System.out.println("Matched");

            }else{
              str.append(sku+"\tOWD: \t"+localOHMap.get(sku)+"\t"+"MAG: \t"+remoteInventory.get(sku)+"\n");
            }


        }


        log.debug(str);
        Mailer.sendMailWithAttachment("magento sku info","magento sku info",emailAddress,str.toString().getBytes(),"skus.csv","text/csv");
    }

    public static void heldForFraudCheck(Order order, String reason) throws Exception {
        Event.addOrderEvent(Integer.parseInt(order.orderID), Event.kEventTypeHandling, "Order held for fraud check (" + reason + ")", "Order Importer");

        CasetrackerAPI.createNewCaseForClient("Fraud Check Request (OWD Ref:" + order.orderNum + ")",
                "Obtain positive cardholder voice confirmation of order value before releasing order to ship.", order.clientID, null, CasetrackerAPI.getLoginToken());
    }

    public List<String> getFraudEmailList() {
        return fraudEmailList;
    }

    public void setFraudEmailList(List<String> fraudEmailList) {
        this.fraudEmailList = fraudEmailList;
    }

    public List<String> getFraudPhoneList() {
        return fraudPhoneList;
    }

    public void setFraudPhoneList(List<String> fraudPhoneList) {
        this.fraudPhoneList = fraudPhoneList;
    }
}
