package com.owd.core.business.order.zoneUtilities;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by danny on 10/24/2018.
 */
public class ZoneLookupTest {


    @Test
    public void testLookupViaORderId(){
        Integer i = ZoneLookup.lookupZoneFromOrderId(16661736);
        System.out.println(i);
        assertTrue(i==8);
        i = ZoneLookup.lookupZoneFromOrderId(16669232);
        System.out.println(i);
    }


}
