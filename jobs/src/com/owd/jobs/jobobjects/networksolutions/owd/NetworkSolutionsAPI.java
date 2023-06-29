package com.owd.jobs.jobobjects.networksolutions.owd;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.jobs.jobobjects.networksolutions.*;
import com.sun.xml.bind.api.JAXBRIContext;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Jul 20, 2010
 * Time: 12:16:10 PM
 * To change this template use File | Settings | File Templates.
 * <p/>
 * <p/>
 * <p/>
 * Order Status List
 * Current token expires 5/2011
 * <p/>
 * name:Order Received value:1
 * name:Payment Received value:2
 * name:Partially Shipped value:3
 * name:Shipped value:4
 * name:Canceled value:5
 * name:Declined value:6
 */
public class NetworkSolutionsAPI


{
private final static Logger log =  LogManager.getLogger();

    private String application = "";//AV
    private String certificate = "";
    private String userKey = "";
    private int clientID = 0;

    static Map<String,NetSolEcomServiceSoap> apiMap = new HashMap<String,NetSolEcomServiceSoap>();


    public NetworkSolutionsAPI(String application, String certificate, String userKey, int clientID)
    {
        this.application = application;
        this.certificate = certificate;
        this.userKey = userKey;
        this.clientID = clientID;
    }

    public static String getUserToken(String application, String certificate) throws Exception
    {

        NetSolEcomService service = new NetSolEcomService();
        NetSolEcomServiceSoap soap = service.getNetSolEcomServiceSoap12();
        WSBindingProvider bp = (WSBindingProvider) soap;

        SecurityCredentialType sec = new SecurityCredentialType();
        sec.setApplication(application);
        sec.setCertificate(certificate);
        sec.setUserToken("");
        ObjectFactory fact = new ObjectFactory();

        bp.setOutboundHeaders(
                Headers.create((JAXBRIContext) (JAXBContext.newInstance("com.owd.jobs.jobobjects.networksolutions")),
                        fact.createSecurityCredential(sec))
        );

        GetUserTokenRequestType req = new GetUserTokenRequestType();
        UserTokenType ut = new UserTokenType();
        ut.setUserKey("Kg89Skt3MBs5q6Z4");
        req.setUserToken(ut);
        GetUserTokenResponseType resp = soap.getUserToken(req);
        log.debug("");
        log.debug("id:" + resp.getRequestId());
        log.debug("store:" + resp.getStoreUrl());

        log.debug("status:" + resp.getStatus());
        log.debug("errors:" + resp.getErrorList().size());

        for (ErrorType os : resp.getErrorList())
        {
            log.debug("error:" + os.getMessage());
        }
        //user key: t4ZMw5s7D9Xdf8AK
        //url: https://ecomapi.networksolutions.com/Authorize.aspx?userkey=t4ZMw5s7D9Xdf8AK
        log.debug("resp:" + resp.getUserToken().getUserToken());
        log.debug("resp:" + resp.getUserToken().getExpiration());
        return resp.getUserToken().getUserToken();
    }

    public static String getLoginURL(String application, String certificate) throws Exception
    {

        NetSolEcomService service = new NetSolEcomService();
        NetSolEcomServiceSoap soap = service.getNetSolEcomServiceSoap();
        WSBindingProvider bp = (WSBindingProvider) soap;

        SecurityCredentialType sec = new SecurityCredentialType();
        sec.setApplication(application);
        sec.setCertificate(certificate);
        //  sec.setUserToken();
        ObjectFactory fact = new ObjectFactory();

        bp.setOutboundHeaders(
                Headers.create((JAXBRIContext) (JAXBContext.newInstance("com.owd.jobs.jobobjects.networksolutions")),
                        fact.createSecurityCredential(sec))
        );

        GetUserKeyResponseType resp = soap.getUserKey(new GetUserKeyRequestType());


        //user key: t4ZMw5s7D9Xdf8AK
        //url: https://ecomapi.networksolutions.com/Authorize.aspx?userkey=t4ZMw5s7D9Xdf8AK
       log.debug("resp:" + resp.getUserKey().getLoginUrl());
        log.debug("resp:" + resp.getUserKey().getUserKey());
        return resp.getUserKey().getLoginUrl();
    }

    public static void main(String[] args) throws Exception
    {

        //https://ecomapi.networksolutions.com/Authorize.aspx?userkey=a5R2MtGr7x4JBn69
         // log.debug("token="+NetworkSolutionsAPI.getLoginURL("internal app","1b706ccce4934c958ce16ba58a278539"));
          //Kg89Skt3MBs5q6Z4

           log.debug("token="+NetworkSolutionsAPI.getUserToken("internal app","1b706ccce4934c958ce16ba58a278539"));
      //  NetworkSolutionsAPI api = new NetworkSolutionsAPI("internal app", "1b706ccce4934c958ce16ba58a278539", "i3TJq2o7X8Nnd9KZf4e6B5HyRm7t4FCz", 461);
      //  api.markOrderAsShipped("","");
        //  api.showOrderStatusList();
        //  Long id = api.getOrderIdFromOrderNumber("2003");
        //d9CTc54NqFy87DmMf63KgBa2z7R6Etb3
    }

    String getApiMapKeyForInstance()
    {
        return application+certificate+userKey;
    }
    synchronized NetSolEcomServiceSoap getAPI() throws Exception {

        if(!(apiMap.containsKey(getApiMapKeyForInstance()))){
            NetSolEcomService service = new NetSolEcomService();
            NetSolEcomServiceSoap soap = service.getNetSolEcomServiceSoap12();


            setSecurityCredentials(soap);

            apiMap.put(getApiMapKeyForInstance(),soap);
        }

        return apiMap.get(getApiMapKeyForInstance());
    }


    public void showOrderStatusList() throws Exception
    {
        NetSolEcomServiceSoap soap = getAPI();



        ReadOrderStatusRequestType rsr = new ReadOrderStatusRequestType();


        rsr.setDetailSize(SizeCodeType.SMALL);
        PaginationType pt = new PaginationType();
        pt.setPage(1);
        pt.setSize(999);
        rsr.setPageRequest(pt);
        rsr.setRequestId("ReadOrderStatus");
        //   rsr.setVersion(new BigDecimal("1.9"));
        FilterType filter = new FilterType();
        filter.setField("OrderStatusId");
        filter.setOperator(OperatorCodeType.GREATER_EQUAL);
        filter.getValueList().add("1");

        rsr.getFilterList().add(filter);


        ReadOrderStatusResponseType uort = soap.readOrderStatus(rsr);
        log.debug("uort=" + uort);
        log.debug("statuses:" + uort.getOrderStatusList().size());
        log.debug("id:" + uort.getRequestId());
        log.debug("store:" + uort.getStoreUrl());

        log.debug("status:" + uort.getStatus());
        log.debug("errors:" + uort.getErrorList().size());
        for (ErrorType os : uort.getErrorList())
        {
            log.debug("error:" + os.getMessage());
        }

        for (OrderStatusType os : uort.getOrderStatusList())
        {
            log.debug("name:" + os.getName() + " value:" + os.getOrderStatusId());
        }

    }

    public Long getOrderIdFromOrderNumber(String orderNumber) throws Exception
    {

        try
        {
            NetSolEcomServiceSoap soap = getAPI();


            ReadOrderRequestType rsr = new ReadOrderRequestType();


        rsr.setDetailSize(SizeCodeType.SMALL);
        PaginationType pt = new PaginationType();
        pt.setPage(1);
        pt.setSize(999);
        rsr.setPageRequest(pt);
        rsr.setRequestId("ReadOrder");
        //   rsr.setVersion(new BigDecimal("1.9"));
        FilterType filter = new FilterType();
        filter.setField("OrderNumber");
        filter.setOperator(OperatorCodeType.EQUAL);
        filter.getValueList().add(orderNumber);

        rsr.getFilterList().add(filter);


        ReadOrderResponseType uort = soap.readOrder(rsr);
        log.debug("uort=" + uort);
        log.debug("orders:" + uort.getOrderList().size());
        log.debug("id:" + uort.getRequestId());
        log.debug("store:" + uort.getStoreUrl());

        log.debug("status:" + uort.getStatus());
        log.debug("errors:" + uort.getErrorList().size());
        for (ErrorType os : uort.getErrorList())
        {
            log.debug("error:" + os.getMessage());
        }

        for (OrderType os : uort.getOrderList())
        {
            log.debug("id:" + os.getOrderId() + " for:" + os.getOrderNumber());
        }
        return uort.getOrderList().get(0).getOrderId();
        }catch(Exception ex)
        {
            return 0L;
        }
    }

    public List<OrderType> getImportOrderList() throws Exception
    {
        NetSolEcomServiceSoap soap = getAPI();


        ReadOrderRequestType rsr = new ReadOrderRequestType();


        rsr.setDetailSize(SizeCodeType.LARGE);
        PaginationType pt = new PaginationType();
        pt.setPage(1);
        pt.setSize(99);
        rsr.setPageRequest(pt);
        rsr.setRequestId("ReadOrderList");
        //   rsr.setVersion(new BigDecimal("1.9"));
        FilterType filter = new FilterType();
        filter.setField("Status.OrderStatusId");
        filter.setOperator(OperatorCodeType.EQUAL);
        filter.getValueList().add("2");  //Payment Received

        rsr.getFilterList().add(filter);


        ReadOrderResponseType uort = soap.readOrder(rsr);
        log.debug("uort=" + uort);
        log.debug("orders:" + uort.getOrderList().size());

        log.debug("id:" + uort.getRequestId());
        log.debug("store:" + uort.getStoreUrl());

        log.debug("status:" + uort.getStatus());
        log.debug("errors:" + uort.getErrorList().size());
        for (ErrorType os : uort.getErrorList())
        {
            log.debug("error:" + os.getMessage());
        }

        if (uort.getOrderList() == null || uort.getOrderList().size() < 1)
        {
            throw new Exception("No orders found");
        }
        for (OrderType os : uort.getOrderList())
        {
            log.debug("country:" + os.getCustomer().getBillingAddress().getCountry().name() + ":" + os.getCustomer().getBillingAddress().getCountry().value());

        }
        return uort.getOrderList();
    }

    public boolean markOrderAsShipped(String netsolOrderNumber, String tracking) throws Exception
    {
        boolean ok = false;

        log.debug("marking order " + netsolOrderNumber + " shipped");
        NetSolEcomServiceSoap soap = getAPI();


        Long orderId = getOrderIdFromOrderNumber(netsolOrderNumber);
        if(orderId>0)
        {
        UpdateOrderRequestType uor = new UpdateOrderRequestType();

        uor.setRequestId("UpdateOrderRequest");

        OrderType ot = new OrderType();
        uor.setOrder(ot);
        ot.setOrderId(orderId);
        OrderStatusType ost = new OrderStatusType();
        ost.setOrderStatusId(4L);    //Shipped
        ost.setEmailCustomer(true);
        ot.setStatus(ost);
        ot.setShipping(new ShippingType());
        PackageType pt = new PackageType();
        pt.setTrackingNumber(tracking);
        ot.getShipping().getPackageList().add(pt);

        UpdateOrderResponseType resp1 = soap.updateOrder(uor);
        log.debug("errors:" + resp1.getErrorList().size());
        if (resp1.getErrorList().size() > 0)
        {
            ok = false;
        } else
        {
            ok = true;
        }
        for (ErrorType os : resp1.getErrorList())
        {
            log.debug("error:" + os.getMessage());
        }
        }else
        {
            ok = true;
        }

        return ok;


    }

    private void setSecurityCredentials(NetSolEcomServiceSoap soap)
            throws JAXBException
    {
        SecurityCredentialType sec = new SecurityCredentialType();
        sec.setApplication(application);
        sec.setCertificate(certificate);
        sec.setUserToken(userKey);

        ((WSBindingProvider) soap).setOutboundHeaders(
                Headers.create((JAXBRIContext) (JAXBContext.newInstance("com.owd.jobs.jobobjects.networksolutions")),
                        new ObjectFactory().createSecurityCredential(sec))
        );
    }
}
