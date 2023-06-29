package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import com.owd.dc.packing.vendorCompliance.vendorComplianceUtils;
import org.junit.BeforeClass;
import org.junit.Test;



/**
 * Created by danny on 12/14/2018.
 */
public class BassProWarehousePackageLabeltest {


    @BeforeClass
    public static void setUpBeforeClass(){
        //System.setProperty("com.owd.environment", "test");
    }

    @Test
    public void testingEDIInfoPUlling(){
      /*  BassProWarehousePackageLabel label = new BassProWarehousePackageLabel();
        label.edi850 = vendorComplianceUtils.edi850FromOrderID("18680392");
        org.junit.Assert.assertTrue(label.edi850.length() > 0);
        label.loadDataFrom850();
       // assertTrue(label.department.equals("312"));
        System.out.println(label.distributionCenter);
        org.junit.Assert.assertTrue(label.distributionCenter.equals("915S"));*/
       // System.out.println(label.department);
       // System.out.println(label.departmentName);
       // assertTrue(label.storeNumber.equals("5524"));


    }

    @Test
    public void getZplDataTest(){


     /*   DickWarehousePackageLabel label = new DickWarehousePackageLabel();
       try {
           label.loadFromOrderId("15740321");

           System.out.println(label.getXml());

       }catch (Exception e){
           e.printStackTrace();
           fail();
       }
*/

    }
}
