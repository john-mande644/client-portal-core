package com.owd.core.business.order;

import junit.framework.TestCase;

/**
 * Created by danny on 10/15/2018.
 */
public class PackageTest  extends TestCase {


    public void testPackageSSCC(){

        String SSCC = Package.getSSCCCodeForPackage("16803278");
        assertTrue("".equals(SSCC));

        SSCC = Package.getSSCCCodeForPackage("14033831");
        assertTrue("008590390040373566".equals(SSCC));
    }
}
