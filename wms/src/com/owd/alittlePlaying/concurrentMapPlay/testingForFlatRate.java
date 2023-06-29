package com.owd.alittlePlaying.concurrentMapPlay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by danny on 8/24/2018.
 */
public class testingForFlatRate {
    protected final static Logger log = LogManager.getLogger();

    public static void main(String[] args){

        initilizae3andDoLookup();




    }

    private static void initilizae3andDoLookup(){

        log.debug(FlatRateShippingTest.getMapSize());
        log.debug(FlatRateShippingTest.getShipMethodToUse("55","","DC6",1,2,1));
        log.debug(FlatRateShippingTest.getMapSize());
        log.debug(FlatRateShippingTest.getShipMethodToUse("56","","DC6",1,2,1));
        log.debug(FlatRateShippingTest.getMapSize());
        log.debug(FlatRateShippingTest.getShipMethodToUse("56","","DC6",1,2,2));
        log.debug(FlatRateShippingTest.getShipMethodToUse("56","","DC6",3,2,1));




    }
}
