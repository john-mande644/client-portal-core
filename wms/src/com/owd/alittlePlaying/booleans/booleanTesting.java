package com.owd.alittlePlaying.booleans;

import junit.framework.TestCase;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by danny on 4/17/2018.
 */
public class booleanTesting extends TestCase{

    List<Boolean> threeBooleans, fourBooleans;


    protected void setUp(){
        threeBooleans = new ArrayList<Boolean>(Arrays.asList(true,false, true));

        fourBooleans = new ArrayList<>(Arrays.asList(false,false,false,true));


    }


    public void testThree(){

        assertTrue(xOutOfYTrue.xNumberTrue(threeBooleans,1));
        assertTrue(xOutOfYTrue.xNumberTrue(threeBooleans,2));
        assertFalse(xOutOfYTrue.xNumberTrue(threeBooleans,3));

    }

    public void testFour(){
        assertTrue(xOutOfYTrue.xNumberTrue(fourBooleans,1));
        assertFalse("Failed boolean", xOutOfYTrue.xNumberTrue(fourBooleans, 2));
        assertFalse("Failed boolean",xOutOfYTrue.xNumberTrue(fourBooleans,3));
        assertTrue(xOutOfYTrue.xNumberTrue(Arrays.asList(false,true,true),1));
    }





}
