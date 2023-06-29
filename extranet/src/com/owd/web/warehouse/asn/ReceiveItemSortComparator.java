package com.owd.web.warehouse.asn;

import com.owd.hibernate.generated.AsnItem;
import com.owd.hibernate.generated.ReceiveItem;

import java.util.Comparator;

/**
 * Created by danny on 9/12/2017.
 */
public class ReceiveItemSortComparator implements Comparator<ReceiveItem> {



    public int compare(ReceiveItem o1, ReceiveItem o2){

        try {
            System.out.println("Comparing: " + o1.getInventoryFkey() + " " + o2.getInventoryFkey());

            if (o1.getQtyReceive() == 0) {
                o1.setQtyPackslip(2);
            }else {
                if (o1.getQtyReceive() + o1.getAsnItem().getReceived() == o1.getAsnItem().getExpected()) {
                    o1.setQtyPackslip(3);
                } else {
                    o1.setQtyPackslip(1);
                }
            }


            if (o2.getQtyReceive() == 0) {
                o2.setQtyPackslip(2);
            }else {
                if (o2.getQtyReceive() + o2.getAsnItem().getReceived() == o2.getAsnItem().getExpected()) {
                    o2.setQtyPackslip(3);
                } else {
                    o2.setQtyPackslip(1);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

       int i = o1.getQtyPackslip().compareTo(o2.getQtyPackslip());

        if(i!=0){

            return i;
        }

        o1.setQtyPackslip(Math.abs(o1.getAsnItem().getExpected()-(o1.getQtyReceive() + o1.getAsnItem().getReceived()))*-1);
        o2.setQtyPackslip(Math.abs(o2.getAsnItem().getExpected()-(o2.getQtyReceive() + o2.getAsnItem().getReceived()))*-1);
        i =o1.getQtyPackslip().compareTo(o2.getQtyPackslip());

        if(i!=0){
            return i;

        }
        return o1.getAsnItem().getInventoryNum().compareTo(o2.getAsnItem().getInventoryNum());
    }
}
