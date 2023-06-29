package com.owd.alittlePlaying.LyftProjectThings;

import com.thoughtworks.xstream.XStream;

/**
 * Created by danny on 6/15/2017.
 */
public class lookupBean {

    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String units;


    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    private String orderId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public static XStream getXStream(){
        XStream x = new XStream();
        x.alias("lookup",lookupBean.class);

        return x;
    }
}
