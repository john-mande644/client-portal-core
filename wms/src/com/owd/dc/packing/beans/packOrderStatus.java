package com.owd.dc.packing.beans;

import com.thoughtworks.xstream.XStream;

import java.util.List;

/**
 * Created by danny on 1/7/2016.
 */
public class packOrderStatus {

    private String error;
    private String status;
    private Boolean isShipping;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getShipping() {
        return isShipping;
    }

    public void setShipping(Boolean shipping) {
        isShipping = shipping;
    }

    private List<PackageBoxBean> packages;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PackageBoxBean> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageBoxBean> packages) {
        this.packages = packages;
    }

    public XStream getXStream(){
        XStream x = new XStream();
         x.alias("packOrderStatus",packOrderStatus.class);
        x.alias("package",PackageBoxBean.class);

        return x;
    }
}
