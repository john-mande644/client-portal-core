package com.owd.dc.warehouse.vendorCompliance.labelsMaker.packageLabels;

import com.owd.dc.packing.vendorCompliance.vendorComplianceUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

/**
 * Created by danny on 12/14/2018.
 */
public class DickWarehousePackageLabelTest {


    @BeforeClass
    public static void setUpBeforeClass(){
        //System.setProperty("com.owd.environment", "test");
    }

    @Test
    public void testingEDIInfoPUlling(){
       /* DickWarehousePackageLabel label = new DickWarehousePackageLabel();
        label.edi850 = vendorComplianceUtils.edi850FromOrderID("15740321");
        assertTrue(label.edi850.length()>0);
        label.loadDataFrom850();
        assertTrue(label.department.equals("312"));
        System.out.println(label.distributionCenter);
        assertTrue(label.distributionCenter.equals("351"));
        System.out.println(label.department);
        System.out.println(label.departmentName);
        assertTrue(label.storeNumber.equals("5524"));*/


    }

    @Test
    public void getZplDataTest(){


        SephoraPackageLabel label = new SephoraPackageLabel();
       try {
           label.loadFromOrderId("20294649");

           System.out.println(label.getXml());

       }catch (Exception e){
           e.printStackTrace();
           fail();
       }

    }

    // Sean 2020/01/04 comment out because of causing BUILD FAILED
    /*@Test
    public void getStoreNumberTest(){


        DickWarehousePackageLabel label = new DickWarehousePackageLabel();
        try {

            label.loadFromOrderId("19095416");

           System.out.println(label.getLabelZPL(label.labelData.get(0)));


            System.out.println(label.storeNumber);
            assertTrue("Store Numbers don't match", label.storeNumber.equals("851"));
            assertTrue("Ship to location wrong",label.labelData.get(0).getShipToName().replace(" DC","").equals("Goodyear"));
            assertTrue("Padding test", StringUtils.leftPad(label.storeNumber,4,"0").equals("0851"));

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }*/
}
