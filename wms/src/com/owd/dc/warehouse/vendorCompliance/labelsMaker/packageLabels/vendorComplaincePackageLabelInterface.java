package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

/**
 * Created by danny on 10/20/2016.
 */
public interface vendorComplaincePackageLabelInterface {



 public void loadFromOrderId(String orderId) throws Exception;


    public String getXml () throws Exception;

    public String getLabelZPL(packageLabelDataBean data) throws Exception;



}
