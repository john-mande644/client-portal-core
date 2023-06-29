package com.owd.web.warehouse.asn;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateAdHocSession;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.Receive;
import com.owd.hibernate.generated.ReceiveItem;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

/**
 * Created by danny on 9/12/2017.
 */
public class ASNUtilites {


    public static void main(String[] args){

        try{
            Receive r = (Receive) HibernateSession.currentSession().load(Receive.class, 95439);
            System.out.println(r.getAsn().getClientPo());


            Collection<ReceiveItem> riCol = r.getReceiveItems();

            for(ReceiveItem ri: riCol){
                System.out.println(ri.getAsnItem().getInventoryNum());
            }



            System.out.println("?????????????????????????????????????");


            TreeSet<ReceiveItem> opi = new TreeSet<ReceiveItem>(new ReceiveItemSortComparator());
            System.out.println(riCol.size());
            opi.addAll(riCol);
            System.out.println(opi.size());

            for(ReceiveItem ri : opi){
                System.out.println(ri.getAsnItem().getInventoryNum());

            }







        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
