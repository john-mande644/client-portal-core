package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 1/18/13
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class RatedPackage {
private final static Logger log =  LogManager.getLogger();

    public static final String SVC_TOTAL_COST="cost_ttl";
    public static final String SVC_TOTAL_DISCOUNT="discount_ttl";
    public static final String SVC_TOTAL_SPECIAL="special_ttl";
    public static final String SVC_TOTAL_FREIGHT="freight_ttl";
    public static final String SVC_TOTAL_RES_FEE="residential_fee";
    public static final String SVC_TOTAL_PROOF_FEE="proof_fee";
    public static final String SVC_TOTAL_FUEL_FEE="fuel_fee";
    public static final String SVC_NAME="name";
    public static final String SVC_CODE="code";
    public static final String SVC_CARRIER="carrier";
    public static final String CARRIER_FEDEX="fedex";
    public static final String CARRIER_UPS="ups";
    public static final String CARRIER_USPS="usps";
    public static final String CARRIER_LTL="ltl";
    public static final String CARRIER_OTHER="other";




    List<Map<String,String>>  ratedServices = new ArrayList<Map<String,String>>();

}
