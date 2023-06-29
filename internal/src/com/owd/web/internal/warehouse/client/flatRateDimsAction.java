package com.owd.web.internal.warehouse.client;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdShipServiceDims;
import com.owd.hibernate.generated.OwdShipServiceDimsMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class flatRateDimsAction extends ActionSupport {
    private final static Logger log =  LogManager.getLogger();
    private int clientId;
    private int serviceLevelId;
    private String methodCode;
    private String clientName;
    private List<flatRateDimBean> flatRateDimBeanList;
    private List<flatRateMethodDimListBean> flatRateMethodDimMethodList;
    private Map<Integer, String> serviceListMap;
    private Map<String, String> shipMethodMap;
    private String thresholdUpdate;
    private int serviceMethodId;


    public String loadClient(){

        log.debug(clientId);
        if(clientId>0){
            try {
                log.debug("loading list");
                flatRateDimBeanList = loadFlatRateList(clientId);

            }catch (Exception e){

                addActionError("Error loading page data: "+ e.getMessage());
            }
        }else{
            addActionError("Invalid client selected. Please go back to Client Setup page and click link again");
            return"error";
        }

        return "success";
    }

    public String saveDims(){
        try {
        for(flatRateDimBean b:flatRateDimBeanList){
            System.out.print(b.getShipServiceId());
            System.out.print(" ");
            System.out.println(b.getDim());
            if(b.getDimId()>0){
                updateFlatDim(b);
            }else{
                createFlatDim(b);
            }
        }
        HibUtils.commit(HibernateSession.currentSession());


            flatRateDimBeanList=  loadFlatRateList(clientId);
        }catch (Exception e){

            addActionError("Error saving page data: "+ e.getMessage());
        }
        return "success";
    }

    public String loadClientMethod(){
        if(clientId==0) {
            addActionError("Please specify client");
            return "error";
        }
        try {
            //Load data
            updateMethodMaps();

        }catch (Exception e){
            e.printStackTrace();
            addActionError("Error loading page data: "+ e.getMessage());
        }


        return "success";
    }

    public String saveNewMethodDims(){

        try{
            try{
                Integer.parseInt(thresholdUpdate);
            }catch (Exception e){
                e.printStackTrace();
                updateMethodMaps();
                throw new Exception("Threshold needs to be a number");
            }
            System.out.println(thresholdUpdate);
            System.out.println(serviceLevelId);
            System.out.println(methodCode);

            if(checkForDuplicate(clientId,serviceLevelId,methodCode)){
                updateMethodMaps();
                addActionError("Duplicate entry, use a different method");
                return "error";
            }

            OwdShipServiceDimsMethods m = new OwdShipServiceDimsMethods();
            m.setClientFkey(clientId);
            m.setMethodCode(methodCode);
            m.setShipServiceId(serviceLevelId);
            m.setThreshold(Integer.parseInt(thresholdUpdate));
            HibernateSession.currentSession().saveOrUpdate(m);

            HibUtils.commit(HibernateSession.currentSession());



            updateMethodMaps();
        }catch (Exception e){

            e.printStackTrace();
            addActionError("Error loading page data: "+ e.getMessage());
        }


        return "success";
    }

    public String updateMethodDims(){

        try {
            System.out.println(serviceMethodId);

        updateMethodMaps();
        OwdShipServiceDimsMethods m = (OwdShipServiceDimsMethods) HibernateSession.currentSession().load(OwdShipServiceDimsMethods.class,serviceMethodId);
        m.setThreshold(Integer.parseInt(thresholdUpdate));
        HibernateSession.currentSession().saveOrUpdate(m);
        HibUtils.commit(HibernateSession.currentSession());



            updateMethodMaps();
        }catch (Exception e){
            e.printStackTrace();
            addActionError("Error loading page data: "+ e.getMessage());
        }
        return "success";
    }

    public String removeMethodDims(){
        try {
            updateMethodMaps();
            OwdShipServiceDimsMethods m = (OwdShipServiceDimsMethods) HibernateSession.currentSession().load(OwdShipServiceDimsMethods.class,serviceMethodId);
            HibernateSession.currentSession().delete(m);
            HibUtils.commit(HibernateSession.currentSession());
            updateMethodMaps();

        }catch (Exception e){
            e.printStackTrace();
            addActionError("Error removing data: "+ e.getMessage());
        }
        return "success";
    }

    private boolean checkForDuplicate(Integer clientId, int serviceId, String method ) throws Exception{
        boolean duplicate = false;
        String sql = "select id from owd_ship_service_dims_methods where client_fkey = :clientId and ship_service_id = :serviceId " +
                "and method_code = :method";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId",clientId);
        q.setParameter("serviceId",serviceId);
        q.setParameter("method",method);
        List l = q.list();
        if(l.size()>0) duplicate = true;


        return duplicate;
    }

    private void updateMethodMaps() throws Exception{
        flatRateMethodDimMethodList = loadFlatRateMethodList(clientId);
        System.out.println("Loaded flatRate");
        serviceListMap = loadShipLevelList();
        System.out.println(serviceListMap.size());
        shipMethodMap = loadShipMethodList();
        System.out.println(shipMethodMap.size());
    }

    private void updateFlatDim(flatRateDimBean frdb) throws Exception{
        OwdShipServiceDims dim = (OwdShipServiceDims) HibernateSession.currentSession().load(OwdShipServiceDims.class,frdb.getDimId());
        dim.setDim(frdb.getDim());
        HibernateSession.currentSession().saveOrUpdate(dim);
    }

    private void createFlatDim(flatRateDimBean frdb) throws Exception{
        OwdShipServiceDims dim = new OwdShipServiceDims();
        dim.setDim(frdb.getDim());
        dim.setClientFkey(frdb.getClientFkey());
        dim.setShipServiceId(frdb.getShipServiceId());
        HibernateSession.currentSession().saveOrUpdate(dim);
    }

    public Map<Integer, String> loadShipLevelList() throws Exception{
        String sql = "select id, level_name from owd_ship_service_levels order by display_index";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);

        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        Map<Integer, String> shipLevel = new TreeMap<>();
        List results = q.list();
        if(results.size()>0) {
            for (Object row : results) {
                Map data = (Map) row;
                shipLevel.put(Integer.parseInt(data.get("id").toString()), data.get("level_name").toString());
            }
        }
            return shipLevel;
    }

    public Map<String, String> loadShipMethodList() throws Exception{
        String sql = "select method_code, method_name from owd_ship_methods where method_code in\n" +
                " ('CONNECTSHIP_UPS.UPS.3DA','TANDATA_USPS.USPS.SPCL','CONNECTSHIP_GLOBAL.APC.PRIDDPDC'," +
                "'CONNECTSHIP_GLOBAL.APC.PRIDDUDC','TANDATA_FEDEXFSMS.FEDEX.2DA','TANDATA_FEDEXFSMS.FEDEX.FO','TANDATA_FEDEXFSMS.FEDEX.FHD','TANDATA_FEDEXFSMS.FEDEX.IPRI','CONNECTSHIP_UPS.UPS.EXPPLS','CONNECTSHIP_UPS.UPS.NDA','TANDATA_FEDEXFSMS.FEDEX.SP_PS','CONNECTSHIP_GLOBAL.APC.PRIDDUDC','CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP','CONNECTSHIP_UPS.UPS.SPPS','CONNECTSHIP_UPS.UPS.SPSTD','CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_GND','CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_GND','CONNECTSHIP_DHLGLOBALMAIL.DHL.PS_EXP','CONNECTSHIP_UPS.UPS.2AM','CONNECTSHIP_DHL.DHL.WPX','CONNECTSHIP_UPS.UPS.NDS','TANDATA_USPS.USPS.I_EXP_DMND','CONNECTSHIP_UPS.UPS.EPD','CONNECTSHIP_UPS.UPS.STD','TANDATA_USPS.USPS.I_PRIORITY','BWTI_UPS.UPS.MIE','TANDATA_FEDEXFSMS.FEDEX.PRI','CONNECTSHIP_UPS.UPS.2DA','TANDATA_USPS.USPS.PS_NONPRESORT','TANDATA_FEDEXFSMS.FEDEX.STD','CONNECTSHIP_UPS.UPS.EXPSVR','CONNECTSHIP_DHLGLOBALMAIL.DHL.PP_EXP_MAX','CONNECTSHIP_UPS.UPS.EXP','TANDATA_FEDEXFSMS.FEDEX.EXP','CONNECTSHIP_UPS.UPS.NAM','ONTRAC.GROUND','TANDATA_USPS.USPS.I_FIRST','CONNECTSHIP_UPS.UPS.GND','TANDATA_FEDEXFSMS.FEDEX.GND','TANDATA_FEDEXFSMS.FEDEX.IECO','TANDATA_USPS.USPS.FIRST','TANDATA_USPS.USPS.PRIORITY','TANDATA_USPS.USPS.EXPR',CONNECTSHIP_DHLGLOBALMAIL.DHL.PC_PRI)\n";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);

        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        Map<String, String> shipMethods = new TreeMap<>();
        List results = q.list();
        if(results.size()>0) {
            for (Object row : results) {
                Map data = (Map) row;
                shipMethods.put(data.get("method_code").toString(), data.get("method_name").toString());
            }
        }

        return shipMethods;
    }


    public List<flatRateDimBean> loadFlatRateList(Integer clientId) throws Exception{
        OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,clientId);
        clientName = client.getCompanyName();
        List<flatRateDimBean> flatRateDimBeans = new ArrayList<>();
        //Check and load if client already has these set.
        String sql = "SELECT \n" +
                "    dbo.owd_ship_service_dims.id, \n" +
                "    dbo.owd_ship_service_dims.client_fkey, \n" +
                "    dbo.owd_ship_service_dims.ship_service_id, \n" +
                "    dbo.owd_ship_service_dims.dim, \n" +
                "    dbo.owd_ship_service_levels.level_name, \n" +
                "    dbo.owd_ship_service_levels.level_code, \n" +
                "    dbo.owd_ship_service_levels.id as level_id\n" +
                "FROM \n" +
                "    dbo.owd_ship_service_levels \n" +
                "LEFT OUTER JOIN \n" +
                "    dbo.owd_ship_service_dims \n" +
                "ON \n" +
                "    ( \n" +
                "        dbo.owd_ship_service_levels.id = dbo.owd_ship_service_dims.ship_service_id) \n" +
                "WHERE \n" +
                "    dbo.owd_ship_service_dims.client_fkey = :clientId and owd_ship_service_levels.ignore_on_dim_page = 0\n" +
                "    order by display_index;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId",clientId);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List results = q.list();
        if(results.size()>0){
            for(Object row: results){
                Map data = (Map)row;
                flatRateDimBean frdb = new flatRateDimBean();
                frdb.setDimId(Integer.parseInt(data.get("id").toString()));
                frdb.setClientFkey(Integer.parseInt(data.get("client_fkey").toString()));
                frdb.setShipServiceId(Integer.parseInt(data.get("ship_service_id").toString()));
                frdb.setDim(Integer.parseInt(data.get("dim").toString()));
                frdb.setLevelName(data.get("level_name").toString());
                frdb.setLevelCode(data.get("level_code").toString());
                frdb.setLevelId(Integer.parseInt(data.get("level_id").toString()));
                flatRateDimBeans.add(frdb);
            }
            return flatRateDimBeans;
        }

        //No results so we need to just load empty values with methods.
        sql = "SELECT \n" +
                "   \n" +
                "    dbo.owd_ship_service_levels.level_name, \n" +
                "    dbo.owd_ship_service_levels.level_code, \n" +
                "    dbo.owd_ship_service_levels.id as level_id\n" +
                "FROM \n" +
                "    dbo.owd_ship_service_levels \n" +
                "\n" +
                "WHERE \n" +
                "     owd_ship_service_levels.ignore_on_dim_page = 0\n" +
                "    order by display_index;";
        q = HibernateSession.currentSession().createSQLQuery(sql);



        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
         results = q.list();
        if(results.size()>0){
            for(Object row: results){
                Map data = (Map)row;
                flatRateDimBean frdb = new flatRateDimBean();
                frdb.setDimId(0);
                frdb.setClientFkey(clientId);
                frdb.setShipServiceId(Integer.parseInt(data.get("level_id").toString()));
                frdb.setDim(0);
                frdb.setLevelName(data.get("level_name").toString());
                frdb.setLevelCode(data.get("level_code").toString());
                frdb.setLevelId(Integer.parseInt(data.get("level_id").toString()));
                flatRateDimBeans.add(frdb);
            }
            return flatRateDimBeans;
        }




      return flatRateDimBeans;
    }

    public List<flatRateMethodDimListBean> loadFlatRateMethodList(Integer clientId) throws Exception {
        OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, clientId);
        clientName = client.getCompanyName();
        List<flatRateMethodDimListBean> flatRateMethodDimListBeans = new ArrayList<>();
        //Check and load if client already has these set.
        String sql = "SELECT \n" +
                "    dbo.owd_ship_service_dims_methods.id, \n" +
                "    dbo.owd_ship_service_dims_methods.threshold, \n" +
                "    dbo.owd_ship_service_dims_methods.ship_service_id, \n" +
                "    dbo.owd_ship_service_dims_methods.method_code, \n" +
                "    dbo.owd_ship_service_levels.level_name, \n" +
                "    dbo.owd_ship_methods.method_name \n" +
                "FROM \n" +
                "    dbo.owd_ship_service_dims_methods \n" +
                "INNER JOIN \n" +
                "    dbo.owd_ship_service_levels \n" +
                "ON \n" +
                "    ( \n" +
                "        dbo.owd_ship_service_dims_methods.ship_service_id = dbo.owd_ship_service_levels.id) \n" +
                "INNER JOIN \n" +
                "    dbo.owd_ship_methods \n" +
                "ON \n" +
                "    ( \n" +
                "        dbo.owd_ship_service_dims_methods.method_code = dbo.owd_ship_methods.method_code) \n" +
                "WHERE \n" +
                "    dbo.owd_ship_service_dims_methods.client_fkey = :clientId " +
                "order by ship_service_id, method_name;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId", clientId);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List results = q.list();
        if (results.size() > 0) {
            for (Object row : results) {
                Map data = (Map) row;
                flatRateMethodDimListBean frdb = new flatRateMethodDimListBean();
                frdb.setId(Integer.parseInt(data.get("id").toString()));

                frdb.setFlatRateMethod(data.get("level_name").toString());
                frdb.setShipMethod(data.get("method_name").toString());
                frdb.setThreshold(Integer.parseInt(data.get("threshold").toString()));
                flatRateMethodDimListBeans.add(frdb);
            }
            return flatRateMethodDimListBeans;
        }

        return flatRateMethodDimListBeans;
    }




    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public List<flatRateDimBean> getFlatRateDimBeanList() {
        return flatRateDimBeanList;
    }

    public void setFlatRateDimBeanList(List<flatRateDimBean> flatRateDimBeanList) {
        this.flatRateDimBeanList = flatRateDimBeanList;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<flatRateMethodDimListBean> getFlatRateMethodDimMethodList() {
        return flatRateMethodDimMethodList;
    }

    public void setFlatRateMethodDimMethodList(List<flatRateMethodDimListBean> flatRateMethodDimMethodList) {
        this.flatRateMethodDimMethodList = flatRateMethodDimMethodList;
    }

    public Map<Integer, String> getServiceListMap() {
        return serviceListMap;
    }

    public void setServiceListMap(Map<Integer, String> serviceListMap) {
        this.serviceListMap = serviceListMap;
    }

    public Map<String, String> getShipMethodMap() {
        return shipMethodMap;
    }

    public void setShipMethodMap(Map<String, String> shipMethodMap) {
        this.shipMethodMap = shipMethodMap;
    }

    public int getServiceLevelId() {
        return serviceLevelId;
    }

    public void setServiceLevelId(int serviceLevelId) {
        this.serviceLevelId = serviceLevelId;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public String getThresholdUpdate() {
        return thresholdUpdate;
    }

    public void setThresholdUpdate(String thresholdUpdate) {
        this.thresholdUpdate = thresholdUpdate;
    }

    public int getServiceMethodId() {
        return serviceMethodId;
    }

    public void setServiceMethodId(int serviceMethodId) {
        this.serviceMethodId = serviceMethodId;
    }
}
