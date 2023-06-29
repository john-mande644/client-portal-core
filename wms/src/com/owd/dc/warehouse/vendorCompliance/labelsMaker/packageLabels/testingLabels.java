package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

/**
 * Created by danny on 10/21/2016.
 */
public class testingLabels {

    public static void main(String[] args){
        try{

            vendorCompliancePackageLabelBase label;


                String s = "com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels.WalmartS2SPackageLabel";
                Class cls = Class.forName(s);



            label = ( vendorCompliancePackageLabelBase) cls.newInstance();
            label.loadFromOrderId("17152510");
            System.out.println(label.getLabelZPL(label.labelData.get(0)));

            System.out.println(label.getXml());


        }catch(Exception e){

            e.printStackTrace();
        }


    }
}
