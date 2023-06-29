package com.owd.alittlePlaying.asn;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.generated.AsnItem;
import com.owd.hibernate.generated.Receive;
import com.owd.hibernate.generated.ReceiveItem;

import java.util.*;

/**
 * Created by danny on 7/6/2017.
 */
public class displaytesting {




    public static void main(String[] args){

        viewInfo(91040);
    }





    public static void viewInfo(Integer id){

        try {
            Asn a = (Asn) HibernateSession.currentSession().load(Asn.class, id);
            List mapList = new ArrayList();

            Iterator it = a.getReceives().iterator();

            while (it.hasNext()) {
                Receive item = (Receive) it.next();


                Map itemMap = new TreeMap();
                Iterator itm = item.getReceiveItems().iterator();
                while (itm.hasNext()) {
                    ReceiveItem ri = (ReceiveItem) itm.next();
                    itemMap.put(new Integer(ri.getInventoryFkey()), ri);

                }
                mapList.add(itemMap);
            }


            Iterator it2 = a.getAsnItems().iterator();
            while(it2.hasNext()) {
               // System.out.println("got a asnitem");
                AsnItem item = (AsnItem) it2.next();

System.out.println(item.getInventoryNum() + " : " +item.getExpected() + " : " + item.getReceived());



                for (int i=0;i<mapList.size();i++) {
                    int receivedAmt = 0;
                    int damagedAmount = 0;
                    ReceiveItem currItem = ((ReceiveItem) ((Map) mapList.get(i)).get(new Integer(item.getInventoryFkey())));
                    if (currItem != null) {
                        receivedAmt = currItem.getQtyReceive();
                        damagedAmount = currItem.getQtyDamage();
                    }
                    System.out.println(receivedAmt + " : " + damagedAmount + " : " +(item.getReceived()-item.getExpected()));
                }


            }










        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
