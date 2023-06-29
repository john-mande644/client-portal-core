package com.owd.dc.manifest.ExternalApis.OWDEasyPost;

import com.easypost.EasyPost;
import com.easypost.model.Address;
import com.easypost.model.Order;
import com.easypost.model.Parcel;
import com.easypost.model.Rate;
import com.owd.core.business.order.OrderFactory;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 6/9/2017.
 */
public class SingleLyftShipper implements Runnable {

    public static void main(String[] args){

        SingleLyftShipper sls = new SingleLyftShipper(12339682,1);
        sls.run();


    }

    private Integer orderId;
    private Integer units;

    SingleLyftShipper(Integer id, Integer unit){orderId = id; units = unit;}

    public void run(){


            try{
                System.out.println("Start");

                EasyPost.apiKey = EasyPostAccountsAndUsers.xLIVEEasypostKey;

                Map<String, Object> fromAddressMap = new HashMap<String, Object>();
                fromAddressMap.put("name", "Shipping Department");
                fromAddressMap.put("street1", "3325 Manitou Court");
                fromAddressMap.put("company","Lyft, Inc.");
                fromAddressMap.put("city", "Mira Loma");
                fromAddressMap.put("state", "CA");
                fromAddressMap.put("zip", "91752");
                fromAddressMap.put("phone", "415-456-7890");

                OwdOrder oorder = OrderFactory.getOwdOrderFromOrderID(orderId, 529, true);
                OwdOrderShipInfo shipInfo = oorder.getShipinfo();
                Map<String, Object> toAddressMap = new HashMap<String, Object>();
                toAddressMap.put("name", shipInfo.getShipFirstName()+ " " + shipInfo.getShipLastName());
                toAddressMap.put("company",shipInfo.getShipCountry());
                toAddressMap.put("street1", shipInfo.getShipAddressOne());
                toAddressMap.put("street2", shipInfo.getShipAddressTwo());
                toAddressMap.put("city", shipInfo.getShipCity());
                toAddressMap.put("state", shipInfo.getShipState());
                toAddressMap.put("zip", shipInfo.getShipZip());
                toAddressMap.put("phone", shipInfo.getShipPhoneNum());
                toAddressMap.put("county", shipInfo.getShipCountry());

                System.out.println("Setting addresses");
                Address fromAddress = Address.create(fromAddressMap);
                Address toAddress = Address.create(toAddressMap);


                Map<String, Object> optionsMap = new HashMap<String, Object>();

                optionsMap.put("label_format", "ZPL");
                optionsMap.put("label_size", "4x6");
                optionsMap.put("invoice_number", "REF# "+ oorder.getOrderNum());

                Map<String, Object> parcelMap = new HashMap<String, Object>();
                parcelMap.put("weight", 96);
                parcelMap.put("height", 6);
                parcelMap.put("width", 14);
                parcelMap.put("length", 14);
                System.out.println("Creating parcels1");
                Parcel p1 = Parcel.create(parcelMap);





                System.out.println("Creating parcels");
                List<Map<String, Object>> shipments = new ArrayList<Map<String, Object>>();




                /////////////////////////////////////////////


                Map<String, Object> shipmentMap = new HashMap<String, Object>();

                shipmentMap.put("parcel", p1);

                shipmentMap.put("options",optionsMap);
                // shipmentMap.put("is_return",true);

                System.out.println("Creating shipments1");




                shipments.add(shipmentMap);
                // shipments.setShipments(ship);








                Map<String, Object> orderMap = new HashMap<String, Object>();
                // orderMap.put("reference","testOrder");
                orderMap.put("to_address", toAddress);
                orderMap.put("from_address",fromAddress);
                orderMap.put("shipments",shipments);
                orderMap.put("is_return",true);

                System.out.println("Creating order");
                Order order = Order.create(orderMap);
                System.out.println("Created order");

                Map<String,Object> carriers = new HashMap<String, Object>();
                carriers.put("carrier","USPS");
                carriers.put("service","Priority");


                order.buy(carriers);
               System.out.println(order.prettyPrint());

                    //no error
                    String labelUrl = order.getShipments().get(0).getPostageLabel().getLabelUrl();
                    String shipmentId = order.getShipments().get(0).getId();
                    Float rate = order.getShipments().get(0).getSelectedRate().getRate();
                    String trackerId = order.getShipments().get(0).getTracker().getId();
                    String sql = "update zzLyftDuplicates set actualRate = :rate, labelString = :label, shipmentID = :shipmentID, trackerId = :trackerId where orderFkey = :orderId";
                    Query q = HibernateSession.currentSession().createSQLQuery(sql);
                    q.setParameter("rate",rate);
                    q.setParameter("orderId",orderId);
                    q.setParameter("label",labelUrl);
                    q.setParameter("shipmentID",shipmentId);
                    q.setParameter("trackerId",trackerId);
                    int i = q.executeUpdate();
                    if(i>0){
                        HibUtils.commit(HibernateSession.currentSession());
                    }else{
                        //bad.add(orderId);
                    }




               // System.out.println(order.prettyPrint());

               /* order.getShipments().get(0).getUspsZone();
                List<Rate> theRates = order.getRates();
                for(Rate r:theRates){
                    r.prettyPrint();
                    if(r.getService().equals("Priority")){
                        System.out.println(r.getRate());
                        String sql = "update zzLyftDuplicates set singleRate = :rate, zone = :zone where orderFkey = :orderId";
                        Query q = HibernateSession.currentSession().createSQLQuery(sql);
                        q.setParameter("rate",r.getRate());
                        q.setParameter("orderId",orderId);
                        q.setParameter("zone",order.getShipments().get(0).getUspsZone());
                        int i = q.executeUpdate();
                        if(i>0){
                            HibUtils.commit(HibernateSession.currentSession());
                        }else{
                            //bad.add(orderId);
                        }
                    }

                }*/




                // System.out.println(order.prettyPrint());







            }catch (Exception e){
                e.printStackTrace();
            }
















    }
}
