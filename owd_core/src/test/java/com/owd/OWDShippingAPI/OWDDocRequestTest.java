package com.owd.OWDShippingAPI;

import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by danny on 11/18/2019.
 */
public class OWDDocRequestTest {


    @Test
    public void getAvailableLabelsForBarcodeXMLTest(){
        OWDDocRequest docs = new OWDDocRequest();
        try {
            List<LABELDATA> labels = docs.getAvailableLabelsForBarcodeXML("p13779734*28309975*b1");
            assertEquals(1,labels.size());
            System.out.println(labels.get(0).getValue());


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Test
    public void getAvailableLabelsForBarcodeXMLTestNoLabels(){
        OWDDocRequest docs = new OWDDocRequest();
        try {
            List<LABELDATA> labels = docs.getAvailableLabelsForBarcodeXML(" p13785072*28313789*b1");
            assertEquals(0,labels.size());



        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
