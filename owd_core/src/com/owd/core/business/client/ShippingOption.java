package com.owd.core.business.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 29, 2003
 * Time: 11:13:56 AM
 * To change this template use Options | File Templates.
 */
public class ShippingOption implements Serializable {
private final static Logger log =  LogManager.getLogger();
    public float customerCost = 0.00f;
    public float ratedCost = 0.00f;
    public String name = "";
    public String desc = "";
    public String code = "";

    public String toString() {
        return "ShippingOption{" +
                "customerCost=" + customerCost +
                ", ratedCost=" + ratedCost +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
    
}
