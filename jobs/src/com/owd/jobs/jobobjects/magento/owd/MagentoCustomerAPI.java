package com.owd.jobs.jobobjects.magento.owd;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.jobs.jobobjects.magento.MagentoService;
import com.owd.jobs.jobobjects.magento.MagentoServiceLocator;

import java.util.*;

public class MagentoCustomerAPI {
private final static Logger log =  LogManager.getLogger();
    private final MagentoRemoteService magentoRemoteService;

    public MagentoCustomerAPI(MagentoRemoteService magentoRemoteService) {
        this.magentoRemoteService = magentoRemoteService;
    }

    public static void main (String[] args)  throws Exception
    {
        MagentoRemoteService service = new MagentoRemoteService("http://xendurance.com/magento/index.php/api", "OWD", "one7172world", 320);
         MagentoCustomerAPI custApi = new MagentoCustomerAPI(service);
        log.debug(custApi.getAllCustomerGroupsAsIdNameMap());


    }

    public Map<Integer, String> getAllCustomerGroupsAsIdNameMap() throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoRemoteService.getMagentoURL());
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoRemoteService.getMagentoLogin(), magentoRemoteService.getMagentoPassword());

        List<String> argList = new ArrayList<String>();

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "customer_group.list", argList);
        if (obj instanceof HashMap[]) {
            // log.debug("is array");
            Map<Integer, String> newobj = new TreeMap<Integer, String>();

            for (HashMap<String, String> map : (HashMap<String, String>[]) obj) {
                newobj.put(Integer.parseInt(map.get("customer_group_id")),map.get("customer_group_code"));
            }
            obj = newobj;
        }
        /*

      HashMap[]
obj:[{customer_group_id=0, customer_group_code=NOT LOGGED IN}, {customer_group_id=1, customer_group_code=General}, {customer_group_id=4, customer_group_code=PDS Exclusives}]

        */
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);
        return (Map<Integer, String>) obj;


    }

    public List<HashMap<String, String>> getAllCustomerGroupData() throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoRemoteService.getMagentoURL());
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoRemoteService.getMagentoLogin(), magentoRemoteService.getMagentoPassword());

        List<String> argList = new ArrayList<String>();

        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "customer_group.list", argList);
        if (obj instanceof HashMap[]) {
            // log.debug("is array");
            List<HashMap<String, String>> newobj = new ArrayList<HashMap<String, String>>();

            for (HashMap<String, String> map : (HashMap<String, String>[]) obj) {
                newobj.add(map);
            }
            obj = newobj;
        }
        /*

      HashMap[]
obj:[{customer_group_id=0, customer_group_code=NOT LOGGED IN}, {customer_group_id=1, customer_group_code=General}, {customer_group_id=4, customer_group_code=PDS Exclusives}]

        */
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);
        return (List<HashMap<String, String>>) obj;


    }

    public String getCustomerGroupIDForNamedGroup(String groupName) throws Exception {
        String gid = null;
        for (HashMap<String, String> group : getAllCustomerGroupData()) {
            for (String key : group.keySet()) {
                if (key.equalsIgnoreCase("customer_group_code")) {
                    if (group.get(key).equalsIgnoreCase(groupName)) {
                        gid = group.get("customer_group_id");
                        return gid;
                    }
                }
            }
        }
        throw new Exception("Customer Group " + groupName + " not found at " + magentoRemoteService.getMagentoURL());
    }

    public List<HashMap<String, String>> getCustomerDataListForGroupId(String groupId) throws Exception {

        return getCustomerDataListForSimpleCriteria("group_id", groupId);

    }

    public HashMap<String, String> getCustomerDataMapForEmail(String email) throws Exception {

        List<HashMap<String, String>> customerList = getCustomerDataListForSimpleCriteria("email", email);
        if (customerList.size() == 1) {
            return customerList.get(0);
        } else {
            return null;
        }

    }

    public List<HashMap<String, String>> getCustomerDataListForSimpleCriteria(String criteria, String value) throws Exception {
        List<HashMap<String, String>> customers = new ArrayList<HashMap<String, String>>();

        MagentoService service = new MagentoServiceLocator(magentoRemoteService.getMagentoURL());
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoRemoteService.getMagentoLogin(), magentoRemoteService.getMagentoPassword());

        HashMap<String, Object> dets = new HashMap<String, Object>();

        HashMap<String, Object> arg = new HashMap<String, Object>();
        arg.put(criteria, value);
        //  arg.put("created_at", dets);
        //  dets.put("status", "processing");

        // dets.put("from", OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, numberOfDaysToLookBack > 0 ? -1 * numberOfDaysToLookBack : numberOfDaysToLookBack));

        // dets.put("to", OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, 2));
        //  arg.put("sku","1");
        List<HashMap> argList = new ArrayList<HashMap>();
        argList.add(arg);
        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "customer.list", argList);
        if (obj instanceof HashMap[]) {
            log.debug("got Magento customer list size=" + ((HashMap[]) obj).length);
            for (HashMap keym : ((HashMap[]) obj)) {
                customers.add(keym);
            }
        }
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);

        return customers;

    }

    public String createMagentoCustomer(OwdOrderAuto sub, String groupId, String storeId, String websiteId) throws Exception {


        String customerId = null;

        HashMap<String, HashMap<String, String>> regionmap = magentoRemoteService.getFlattenedUSCARegionCodeToIDMaps();

        MagentoService service = new MagentoServiceLocator(magentoRemoteService.getMagentoURL());
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoRemoteService.getMagentoLogin(), magentoRemoteService.getMagentoPassword());
        service.getMage_Api_Model_Server_HandlerPort().startSession();
        log.debug("sessid:" + sessid);


        HashMap<String, String> usMap = regionmap.get("US");
        HashMap<String, String> caMap = regionmap.get("CA");
        String billCountryID = "";
        String billRegionID = "";
        String shipCountryID = "";
        String shipRegionID = "";
        if (sub.getBillCountry().equalsIgnoreCase("USA")) {
            billCountryID = "US";
            if (usMap.containsKey(sub.getBillState().toUpperCase())) {
                billRegionID = usMap.get(sub.getBillState().toUpperCase());
            }
        } else if (sub.getBillCountry().equalsIgnoreCase("CANADA")) {
            billCountryID = "CA";
            if (caMap.containsKey(sub.getBillState().toUpperCase())) {
                billRegionID = caMap.get(sub.getBillState().toUpperCase());
            }

        } else {
            throw new Exception("Bill Country " + sub.getBillCountry() + " and/or region " + sub.getBillState() + " do not match Magento required values!");
        }

        if (sub.getShipCountry().equalsIgnoreCase("USA")) {
            shipCountryID = "US";
            if (usMap.containsKey(sub.getShipState().toUpperCase())) {
                shipRegionID = usMap.get(sub.getShipState().toUpperCase());
            }
        } else if (sub.getShipCountry().equalsIgnoreCase("CANADA")) {
            shipCountryID = "CA";
            if (caMap.containsKey(sub.getShipState().toUpperCase())) {
                shipRegionID = caMap.get(sub.getShipState().toUpperCase());
            }

        } else {
            throw new Exception("Ship Country " + sub.getShipCountry() + " and/or region " + sub.getShipState() + " do not match Magento required values!");
        }


        HashMap<String, String> custInfo = new HashMap<String, String>();
        custInfo.put("firstname", OWDUtilities.getFirstNameFromWholeName(sub.getBillName()));
        custInfo.put("lastname", OWDUtilities.getLastNameFromWholeName(sub.getBillName()));
        custInfo.put("email", sub.getBillEmail());
        custInfo.put("password", "");
        custInfo.put("store_id", storeId);
        custInfo.put("website_id", websiteId);
        custInfo.put("group_id", groupId);

        HashMap<String, Object> billAddress = new HashMap<String, Object>();
        HashMap<String, Object> shipAddress = new HashMap<String, Object>();
        List<String> billStreetAddress = new ArrayList<String>();
        billStreetAddress.add(sub.getBillAddressOne());
        billStreetAddress.add(sub.getBillAddressTwo());
        List<String> shipStreetAddress = new ArrayList<String>();
        billAddress.put("firstname", OWDUtilities.getFirstNameFromWholeName(sub.getBillName()));
        billAddress.put("lastname", OWDUtilities.getLastNameFromWholeName(sub.getBillName()));
        billAddress.put("country_id", sub.getBillCountry().equalsIgnoreCase("USA") ? "US" : (sub.getBillCountry().equalsIgnoreCase("CANADA") ? "CA" : ""));
        billAddress.put("region_id", billRegionID);
        billAddress.put("region", sub.getBillCountry().equalsIgnoreCase("USA") ? magentoRemoteService.getUSCARegionMap().get("US").get(billRegionID) : magentoRemoteService.getUSCARegionMap().get("CA").get(billRegionID));
        billAddress.put("city", sub.getBillCity());
        billAddress.put("street", billStreetAddress);
        billAddress.put("telephone", sub.getBillPhone().length()<7?"800-339-5952":sub.getBillPhone());
        billAddress.put("postcode", sub.getBillZip());
        billAddress.put("is_default_billing", "1");
        billAddress.put("is_default_shipping", "0");

        shipStreetAddress.add(sub.getShipAddressOne());
        shipStreetAddress.add(sub.getShipAddressTwo());

        shipAddress.put("firstname", OWDUtilities.getFirstNameFromWholeName(sub.getShipName()));
        shipAddress.put("lastname", OWDUtilities.getLastNameFromWholeName(sub.getShipName()));
        shipAddress.put("country_id", sub.getShipCountry().equalsIgnoreCase("USA") ? "US" : (sub.getShipCountry().equalsIgnoreCase("CANADA") ? "CA" : ""));
        shipAddress.put("region_id", shipRegionID);
        shipAddress.put("region", sub.getShipCountry().equalsIgnoreCase("USA") ? magentoRemoteService.getUSCARegionMap().get("US").get(shipRegionID) : magentoRemoteService.getUSCARegionMap().get("CA").get(shipRegionID));
        shipAddress.put("city", sub.getShipCity());
        shipAddress.put("street", shipStreetAddress);
        shipAddress.put("telephone", sub.getBillPhone().length()<7?"800-339-5952":sub.getBillPhone());
        shipAddress.put("postcode", sub.getShipZip());
        shipAddress.put("is_default_billing", "0");
        shipAddress.put("is_default_shipping", "1");


        log.debug("sending custinfo: " + custInfo);
        sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoRemoteService.getMagentoLogin(), magentoRemoteService.getMagentoPassword());

        List<Object> argList = new ArrayList<Object>();
        argList.add(custInfo);
        Object custId = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "customer.create", argList);
        MagentoRemoteService.printMagentoResponseObject(custId);
        log.debug("" + custId);


        argList = new ArrayList<Object>();
        argList.add(custId);
        argList.add(billAddress);

        Object billAddressID = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "customer_address.create", argList);
        MagentoRemoteService.printMagentoResponseObject(billAddressID);
        log.debug("" + billAddressID);

        argList = new ArrayList<Object>();
        argList.add(custId);
        argList.add(shipAddress);

        Object shipAddressId = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "customer_address.create", argList);
        MagentoRemoteService.printMagentoResponseObject(shipAddressId);
        log.debug("" + shipAddressId);

        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);

        return customerId;
    }

    public String createMagentoCustomer(OwdOrder order) {
        String customerId = null;


        return customerId;

    }

    public void setMagentoCustomerGroupId(String customerId, String groupId) throws Exception {

        MagentoService service = new MagentoServiceLocator(magentoRemoteService.getMagentoURL());
        String sessid = service.getMage_Api_Model_Server_HandlerPort().login(magentoRemoteService.getMagentoLogin(), magentoRemoteService.getMagentoPassword());


        List<Object> argList = new ArrayList<Object>();
        //  argList.add(mo.order.get("increment_id"));
        //  argList.add(newstatus);
        argList.add(customerId);
        HashMap<String, String> level = new HashMap<String, String>();
        level.put("group_id", groupId);

        argList.add(level);
        Object obj = service.getMage_Api_Model_Server_HandlerPort().call(sessid, "customer.update", argList);
        MagentoRemoteService.printMagentoResponseObject(obj);
        log.debug("" + obj);
        service.getMage_Api_Model_Server_HandlerPort().endSession(sessid);


    }
}
