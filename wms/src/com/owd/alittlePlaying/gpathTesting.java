package com.owd.alittlePlaying;

import com.owd.core.business.order.OrderStatus;
import groovy.lang.Closure;
import groovy.util.XmlSlurper;
import groovy.util.slurpersupport.GPathResult;

import java.util.Map;

/**
 * Created by danny on 7/13/2019.
 */
public class gpathTesting {


    public static void main(String[] args){

        try{
            OrderStatus order = new OrderStatus("" + 18538081);
            Map<String, String> tagmap = order.getTagMap();
            GPathResult hubXml = new XmlSlurper().parseText(tagmap.get("COMMERCEHUB_PO_XML"));


            GPathResult partnerXml = (GPathResult) hubXml.getProperty("participatingParty");



            System.out.println(partnerXml);
            //System.out.println(partnerXml.);




        }catch (Exception e){
            e.printStackTrace();
        }




    }
}
