package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 7, 2008
 * Time: 1:47:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionCreateRequest implements APIRequestHandler{
private final static Logger log =  LogManager.getLogger();

    
    public static final String kRootTag = "OWD_SUBSCRIPTION_CREATE_REQUEST";

     public static final String shipInterval = "shipInterval";
    public static final String calendarUnit = "calendarUnit";
    public static final String requirePayment = "requirePayment";
    public static final String status = "status";
    public static final String nextShipmentDate = "nextShipmentDate";

    public static final String billName = "billName";
    public static final String billAddressOne = "billAddressOne";
    public static final String billAddressTwo = "billAddressTwo";
    public static final String billCity = "billCity";
    public static final String billState = "billState";
    public static final String billZip = "billZip";
    public static final String billCountry = "billCountry";
    public static final String ccNum = "ccNum";
    public static final String ccExpMon = "ccExpMon";
    public static final String ccExpYear = "ccExpYear";
    public static final String shipName = "shipName";
    public static final String shipAddressOne = "shipAddressOne";
    public static final String shipAddressTwo = "shipAddressTwo";
    public static final String shipCity = "shipCity";
    public static final String shipState = "shipState";
    public static final String shipZip = "shipZip";
    public static final String shipCountry = "shipCountry";
    public static final String billPhone = "billPhone";
    public static final String shipPhone = "shipPhone";
    public static final String shipMethod = "shipMethod";
    public static final String salesTax = "salesTax";
    public static final String shipCost = "shipCost";
    public static final String created = "created";
    public static final String billEmail = "billEmail";
    public static final String shipEmail = "shipEmail";

    public static final String cancelDate = "cancelDate";
    public static final String orderSource = "orderSource";


    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception

    {

        try {


            SubscriptionStatusResponse response = new SubscriptionStatusResponse(api_version);

            //add limits (AND logic for all)
          

            return response;
        } catch (Exception ex) {
            //ex.printStackTrace();
            throw ex;

        }
    }
}
