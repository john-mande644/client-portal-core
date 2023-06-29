package com.owd.dc.packing.vendorCompliance;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by danny on 12/14/2018.
 */
public class vendorComplianceUtilsTesting {


    @BeforeClass
    public static void setUpBeforeClass(){
        System.setProperty("com.owd.environment", "test");
    }

    @Test
    public void get850fromOrderIdTest(){

     /*   String s = vendorComplianceUtils.edi850FromOrderID("15740321");
        assertTrue(s.length()>0);
        System.out.println(s);
        System.out.println(s.length());
*/

    }

    @Test
    public void getVendorComplianceXmlResponseForOrderTesting(){
       /* String s = "<vendorComplianceInfo><name>DicksWarehouse</name><packageLabels><labels><label>XlhBflRBMDAwfkpTTl5MVDBeTU5XXk1URF5QT05eUE1OXkxIMCwwXkpNQV5QUjYsNn5TRDE1XkpVU15MUk5eQ0kwXlhaXlhBXk1NVF5QVzgxMl5MTDEyMTheTFMwXkJZNCwzLDIwOV5GVDk0LDExMTleQkNOLCxZLFksWSxVXkZEKDAwKSAwMDg1OTAzOTAwNDA0MzEzODleRlNeRk8xMiwxMzheR0I3OTcsMCw0XkZTXkZPOSw2MDFeR0I3OTYsMCw0XkZTXkZPNywzMzJeR0I3OTYsMCw0XkZTXkZPOCw4MjReR0I3OTcsMCw0XkZTXkZUNTcyLDY4OF5BME4sMjMsMzFeRkhcXkZEMzUxXkZTXkZUMjYsNTQ1XkEwTiwyMywyNF5GSFxeRkRVUEM6IE1peGVkXkZTXkZUMTYxLDQ5Ml5BME4sMzksMzheRkhcXkZER0VOQUNDRVNTLUZPT1RXRUFSICAgICAgVF5GU15GVDI0LDQ4Nl5BME4sMjMsMjReRkhcXkZERGVwdCBOYW1lOl5GU15GVDk1LDQ0Ml5BME4sMzksMzheRkhcXkZEMzEyXkZTXkZUMjQsNDM0XkEwTiwyMywyNF5GSFxeRkREZXB0I15GU15GVDI1LDM4Nl5BME4sMjMsMjReRkhcXkZEUE86IFRFU1RfOTc5MDAwMDkzXkZTXkZUNDI2LDE3N15BME4sMjMsMjReRkhcXkZEQ0FSUklFUjpVU1BTIFByaW9yaXR5IE1haWxeRlNeRlQ0NjQsMTE3XkEwTiwyMywyNF5GSFxeRkRQbGFpbmZpZWxkLCBJTiA0NjE2OF5GU15GVDQ2NCw0NV5BME4sMjMsMjReRkhcXkZEUGxhaW5maWVsZCBEQ15GU15GVDQ2NCw4Ml5BME4sMjMsMjReRkhcXkZENjU1IFNvdXRoIFBlcnJ5IFJvYWReRlNeRlQ3NiwxMjFeQTBOLDIzLDI0XkZIXF5GRENvbHVtYnVzLCBPSCA0MzIyOF5GU15GVDc4LDg2XkEwTiwyMywyNF5GSFxeRkQzMTUgUGhpbGxpcGkgUm9hZF5GU15GVDgxLDQ5XkEwTiwyMywyNF5GSFxeRkRPV0ReRlNeQlk0LDMsMTEwXkZUMzgsMzE1XkJDTiwsTixOXkZEPjs0MjA0NjE2OF5GU15GVDEyMCwxODJeQTBOLDIzLDI0XkZIXF5GRCg0MjApICA0NjE2OF5GU15GTzQwMCw3XkdCMCwzMjUsNV5GU15CWTQsMywxMDZeRlQ0Nyw3ODdeQkNOLCxZLFleRkQ+OzkxNTUyNF5GU15GTzQwMSw2MDZeR0IwLDIxMyw1XkZTXlBRMSwwLDEsWV5YWg==</label></labels></packageLabels></vendorComplianceInfo>";
        String ss = vendorComplianceUtils.getVendorComplianceXmlResponseForOrder("15740321");
        assertEquals(s,ss);*/

    }

}
