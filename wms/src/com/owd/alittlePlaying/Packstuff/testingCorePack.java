package com.owd.alittlePlaying.Packstuff;

/**
 * Created by danny on 11/6/2017.
 */
public class testingCorePack {


    public static void main(String[] args){
        try{

            com.owd.core.business.order.Package pack = com.owd.core.business.order.Package.getPackagesForOrderId(13680488).get(0);
            String tracking = pack.tracking_no;
            String scacCode = pack.SCAC;
            System.out.println(tracking);
            System.out.println(scacCode);
            if(tracking.contains(":")){
                tracking = tracking.substring(tracking.indexOf(":")+1);
            }
            System.out.println(tracking);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
