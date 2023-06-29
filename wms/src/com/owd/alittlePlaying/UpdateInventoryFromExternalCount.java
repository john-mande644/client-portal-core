package com.owd.alittlePlaying;

import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventoryHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by danny on 9/7/2016.
 */
public class UpdateInventoryFromExternalCount {





    public static void main(String[] args){


        Map<String, Integer> m = new TreeMap<String, Integer>();
        m.put("P476718",-3);
        m.put("P477380",-3);
        m.put("P481787",-2);
        m.put("P470047",-1);
        m.put("P470082",-1);
        m.put("P470513",-1);
        m.put("P470669",-1);
        m.put("P471038",-1);
        m.put("P471204",-1);
        m.put("P471730",-1);
        m.put("P471966",-1);
        m.put("P472431",-1);
        m.put("P472492",-1);
        m.put("P472764",-1);
        m.put("P472781",-1);
        m.put("P477392",-1);
        m.put("P477685",-1);
        m.put("P478698",-1);
        m.put("P478745",-1);
        m.put("P479443",-1);
        m.put("P481610",-1);
        m.put("P481747",-1);
        m.put("P481823",-1);
        m.put("P486422",-1);
        m.put("P486521",-1);
        m.put("P488760",-1);
        m.put("P488766",-1);
        m.put("P488842",-1);
        m.put("P470049",1);
        m.put("P470077",1);
        m.put("P470177",1);
        m.put("P470275",1);
        m.put("P470285",1);
        m.put("P470408",1);
        m.put("P470755",1);
        m.put("P470795",1);
        m.put("P470814",1);
        m.put("P470902",1);
        m.put("P470927",1);
        m.put("P470952",1);
        m.put("P470976",1);
        m.put("P471234",1);
        m.put("P471246",1);
        m.put("P471347",1);
        m.put("P471388",1);
        m.put("P471613",1);
        m.put("P471731",1);
        m.put("P471746",1);
        m.put("P471775",1);
        m.put("P471861",1);
        m.put("P471874",1);
        m.put("P471975",1);
        m.put("P472003",1);
        m.put("P472063",1);
        m.put("P472212",1);
        m.put("P472216",1);
        m.put("P472248",1);
        m.put("P472249",1);
        m.put("P472302",1);
        m.put("P472461",1);
        m.put("P472484",1);
        m.put("P472490",1);
        m.put("P472510",1);
        m.put("P472512",1);
        m.put("P472513",1);
        m.put("P472514",1);
        m.put("P472532",1);
        m.put("P472540",1);
        m.put("P472735",1);
        m.put("P477286",1);
        m.put("P477296",1);
        m.put("P477337",1);
        m.put("P477368",1);
        m.put("P477378",1);
        m.put("P477415",1);
        m.put("P477443",1);
        m.put("P477446",1);
        m.put("P477485",1);
        m.put("P477486",1);
        m.put("P477528",1);
        m.put("P477530",1);
        m.put("P477649",1);
        m.put("P478586",1);
        m.put("P478594",1);
        m.put("P478774",1);
        m.put("P478799",1);
        m.put("P478837",1);
        m.put("P478933",1);
        m.put("P481605",1);
        m.put("P481743",1);
        m.put("P486301",1);
        m.put("P486345",1);
        m.put("P486497",1);
        m.put("P486612",1);
        m.put("P488871",1);
        m.put("P470743",1);
       // m.put("P471481",1);
       // m.put("P472335",1);
        m.put("P471235",2);
        m.put("P471280",2);
        m.put("P471281",2);
        m.put("P472531",2);
        m.put("P477261",2);
        m.put("P477336",2);
        m.put("P477407",2);
        m.put("P477546",2);
        m.put("P470712",2);
        m.put("P471947",3);
        m.put("P477312",3);
        m.put("P477456",3);
        m.put("P477571",3);


        wipeOutTheStuff(m,"489");
    }

    public static void wipeOutTheStuff(Map<String,Integer> items,String clientId){
        try{
            List<String> errors = new ArrayList<String>();

            for(String sku:items.keySet()){
                OwdInventory inv = InventoryManager.getOwdInventoryForClientAndSku(clientId,sku);

                if((inv.getOwdInventoryOh().getQtyOnHand()+items.get(sku))>=0){
                    System.out.println("Doing: "+sku);

                    OwdInventoryHistory ohh = new OwdInventoryHistory();
                    ohh.setInventoryFkey(inv.getInventoryId());
                    ohh.setQtyChange(items.get(sku));
                    ohh.setNote("custom.OutsideCountDone");
                    ohh.setFacility(FacilitiesManager.getFacilityForCode("DC6"));
                    HibernateSession.currentSession().save(ohh);

                }    else{
                    errors.add(sku);

                }


            }
          ///  HibUtils.commit(HibernateSession.currentSession());



            for(String s: errors){
                System.out.println(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
