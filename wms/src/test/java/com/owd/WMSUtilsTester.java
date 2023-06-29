package com.owd;

import junit.framework.TestCase;

/**
 * Created by danny on 4/15/2016.
 */
public class WMSUtilsTester  extends TestCase{






    public void testgetOrderNumberFromPackage(){

        assertEquals("123456", WMSUtils.getOrderNumberFromString("123456R1"));
        assertEquals("5717780",WMSUtils.getOrderNumberFromString("p4354*5717780R1*b1"));
        assertEquals("1234567",WMSUtils.getOrderNumberFromString("1234567"));



    }
}
