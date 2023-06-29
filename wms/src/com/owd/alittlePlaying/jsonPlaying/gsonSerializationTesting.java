package com.owd.alittlePlaying.jsonPlaying;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.dc.inventory.beans.skuBean;

import java.util.ArrayList;

/**
 * Created by danny on 7/16/2019.
 */
public class gsonSerializationTesting {


    public static void main(String[] args){


      /*  GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        skuBean  s = new skuBean();
        s.setInventoryId("12345");
        s.setClientName("Testing");
        String jsonString = gson.toJson(s);
        System.out.println(jsonString);
        skuBean ss = gson.fromJson(jsonString,skuBean.class);

        System.out.println(ss.getInventoryId());*/
        testBatch();
    }


    public static void testBatch(){
        GsonBuilder builder = new GsonBuilder();
       // builder.setPrettyPrinting();
        Gson gson = builder.create();

        BatchPackData theData = new BatchPackData();
        theData.status = "Success";
        theData.errerMessage = "";
        BatchStatus status = new BatchStatus();
        status.setId("234");
        status.setTotal("100");
        status.setCreated("50");
        status.setPrinted(1);
        theData.setBatchStatus(status);
        String s = gson.toJson(theData);
        System.out.println(s);

        BatchPackData d = gson.fromJson(s,BatchPackData.class);
        d.setLabels(new ArrayList<batchLabel>());

        batchLabel l = new batchLabel();
        l.setType("zpl");
        l.setEncoding("Base64");
        l.setData("flkdsajf;lkja;djklfjak;kldjsaj");
        d.getLabels().add(l);
         s = gson.toJson(d);
        System.out.println(s);



    }
}
