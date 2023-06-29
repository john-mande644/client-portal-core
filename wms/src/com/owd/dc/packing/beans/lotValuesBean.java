package com.owd.dc.packing.beans;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;
import java.util.SortedMap;

/**
 * Created by danny on 5/23/2016.
 */
public class lotValuesBean {

    private String inventoryId;
    private String inventoryNum;
    private String facility;

    private List<String> lotValues;
    private SortedMap<String,Integer> lotValueAndQty;


    public SortedMap<String,Integer> getLotValueAndQty() {
        return lotValueAndQty;
    }

    public void setLotValueAndQty(SortedMap<String, Integer> lotValueAndQty) {
        this.lotValueAndQty = lotValueAndQty;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public List<String> getLotValues() {
        return lotValues;
    }

    public void setLotValues(List<String> lotValues) {
        this.lotValues = lotValues;
    }

    public XStream getXStream(){
        XStream x = new XStream();
        x.alias("Lots",lotValuesBean.class);


        return x;
    }

    public String getXML(){
     return getXStream().toXML(this);

    }
}
