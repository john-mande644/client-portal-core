package com.owd.jobs.jobobjects.OneOffUtilities

import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdOrder
import com.owd.hibernate.generated.OwdOrderShipInfo
import com.owd.jobs.jobobjects.woocommerce.WooCommerceWordPressAPI
import groovy.json.JsonOutput
import groovy.json.internal.LazyMap
import groovyx.net.http.RESTClient

/**
 * Created by danny on 12/16/2016.
 */
class WeareHandsomeCheckShipmentTrackingAndUpdate {

   static  List<Integer> nonComplete = new ArrayList<>();
    public static void main(String[] args){

        List<Integer> l = new ArrayList<>();
        l.add(11525840);
        l.add(11525844);
        l.add(11525847);
        l.add(11525848);
        l.add(11525849);
        l.add(11525850);
        l.add(11525851);
        l.add(11476861);
        l.add(11476862);
        l.add(11476863);
        l.add(11476864);
        l.add(11476867);
        l.add(11476873);
        l.add(11476876);
        l.add(11476881);
        l.add(11476883);
        l.add(11476884);
        l.add(11476885);
        l.add(11476886);
        l.add(11476887);
        l.add(11476891);
        l.add(11476892);
        l.add(11476893);
        l.add(11476894);
        l.add(11476895);
        l.add(11476896);
        l.add(11476897);
        l.add(11476899);
        l.add(11476900);
        l.add(11476903);
        l.add(11476904);
        l.add(11476906);
        l.add(11476907);
        l.add(11476909);
        l.add(11476913);
        l.add(11476914);
        l.add(11476916);
        l.add(11476917);
        l.add(11476919);
        l.add(11476920);
        l.add(11476921);
        l.add(11525843);
        l.add(11536244);
        l.add(11536245);
        l.add(11536246);
        l.add(11536247);
        l.add(11536378);
        l.add(11536379);
        l.add(11536380);
        l.add(11536382);
        l.add(11536393);
        l.add(11536395);
        l.add(11536396);
        l.add(11536397);
        l.add(11536398);
        l.add(11536400);
        l.add(11536401);
        l.add(11536403);
        l.add(11536404);
        l.add(11536405);
        l.add(11536406);
        l.add(11536407);
        l.add(11536408);
        l.add(11536409);
        l.add(11536411);
        l.add(11536412);
        l.add(11536413);
        l.add(11536427);
        l.add(11536428);
        l.add(11536429);
        l.add(11536430);
        l.add(11536431);
        l.add(11536416);
        l.add(11540500);
        l.add(11540501);

        for(Integer i:l){
            checkForandUpdateTracking(i)

        }
        println nonComplete




    }

    public static void checkForandUpdateTracking(Integer orderId){



        try{
            WooCommerceWordPressAPI api = new WooCommerceWordPressAPI(600, "https://wearehandsome.com/", "ck_fdd87e9d3eb125fdc21f1f35e7921f83529bcf71", "cs_b52ddcce0ddcab0a921c323418dc170eb6f7b229","1",true);
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
            String status = (getOrderStatus(order.getPoNum(),api))
            if(status.equals("completed")){
                println "This is complete check tracking";
                boolean hasTracking = checkShipmentTracking(order.getPoNum(),api)
                if(!hasTracking){
                    println "in no tracking"
                    if(order.trackingNums.length()>0){

                        OwdOrderShipInfo info = order.shipinfo

                        api.markOrderTracking(Integer.parseInt(order.getPoNum()),order.trackingNums,info.carrService)

                    }
                }

            }else{
                nonComplete.add(orderId)
            }


        }catch (Exception e){
            e.printStackTrace()
        }
    }



    public static boolean checkShipmentTracking(String orderId, WooCommerceWordPressAPI api) throws  Exception{
        boolean tracking = false
        RESTClient endpoint = api.getEndpoint()
        endpoint.get(
                path: 'wp-json/wc/v'+1+'/orders/'+orderId+'/shipment-trackings'   ,

                requestContentType: 'application/json',
                contentType: 'application/json'  ,
                //query: [status: 'processing', order:'desc']
        )

                {resp, reader ->

                    println JsonOutput.prettyPrint(JsonOutput.toJson(reader))

                    if (reader == null || reader.empty) {
                    return tracking

                    }




                   // assert reader instanceof LazyMap
                   // println(reader.size())
                    tracking = true





                }


        return tracking
    }

    public static String getOrderStatus(String orderId, WooCommerceWordPressAPI api) throws  Exception{

        String status = new String()
        RESTClient endpoint = api.getEndpoint()
        endpoint.get(
                path: 'wp-json/wc/v'+1+'/orders/'+orderId   ,

                requestContentType: 'application/json',
                contentType: 'application/json'  ,
                query: [status: 'processing', order:'desc']
        )

                {resp, reader ->

                    println JsonOutput.prettyPrint(JsonOutput.toJson(reader))




                        assert reader instanceof LazyMap
                        String s = reader.shipping_lines[0].method_title
                        println(s)
                        status = reader.status

                        Float f = Float.parseFloat(reader.shipping_lines[0].'total');
                        println f;


                    }



        return status


    }

}
