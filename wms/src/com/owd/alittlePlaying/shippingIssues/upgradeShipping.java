package com.owd.alittlePlaying.shippingIssues;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 12/9/2016.
 */
public class upgradeShipping {







    public static List<String> problems = new ArrayList<String>();

    public static void main(String[] args){
        List<Integer> l = new ArrayList<Integer>();



        for(int i : l){
            updateShipping(i);
        }

        System.out.println(problems);

    }




    public static void updateShipping(int orderId){
        try{
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);

            OwdOrderShipInfo si = order.getShipinfo();
            if(si.getCarrService().equals("UPS Next Day Air Saver")){
                si.setCarrService("FedEx Standard Overnight");
                si.setCarrServiceRefNum("FDX.STD");
                si.setCarrFreightTerms("Prepaid");
                si.setCarrFreightTermsRefNum("SHIPPER");
                si.setThirdPartyRefnum("");
                HibernateSession.currentSession().save(si);
                HibUtils.commit(HibernateSession.currentSession());
            }


        }catch (Exception  e){
            e.printStackTrace();
            problems.add(orderId+"");
        }





    }
}
