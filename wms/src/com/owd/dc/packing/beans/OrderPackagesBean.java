package com.owd.dc.packing.beans;

import com.owd.dc.packing.ResultBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Nov 11, 2009
 * Time: 10:32:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderPackagesBean {
    private String OrderNum;
     private ResultBean results;
    private List<PackageBoxBean> packages;

    public String getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(String orderNum) {
        OrderNum = orderNum;
    }

    public ResultBean getResults() {
        if(null == results){
            results = new ResultBean();
        }
        return results;
    }

    public void setResults(ResultBean results) {
        this.results = results;
    }

    public List<PackageBoxBean> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageBoxBean> packages) {
        this.packages = packages;
    }

   
}

