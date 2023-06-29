package com.owd.intranet.adjustments.beans;

import com.owd.intranet.util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 2:01:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class lotCountItemBean {
private final static Logger log =  LogManager.getLogger();
private String lotValue;
private int lotQty;
    private int newQty;

    public int getNewQty() {
        return newQty;
    }

    public void setNewQty(int newQty) {
        this.newQty = newQty;
    }

    public String getLotValue() {
        return lotValue;
    }

    public void setLotValue(String lotValue) {
        this.lotValue = lotValue;
    }

    public int getLotQty() {
        return lotQty;
    }

    public void setLotQty(int lotQty) {
        this.lotQty = lotQty;
    }

    @Override
    public String toString() {
        return "lotCountItemBean{" +
                "lotValue='" + lotValue + '\'' +
                ", lotQty=" + lotQty +
                ", newQty=" + newQty +
                '}';
    }
}
