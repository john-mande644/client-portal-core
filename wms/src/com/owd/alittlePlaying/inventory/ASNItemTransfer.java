package com.owd.alittlePlaying.inventory;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.generated.AsnItem;
import org.hibernate.Query;

import java.util.List;

public class ASNItemTransfer {

    public static void main(String[] args){
        swapItems(136269);
    }



    private static void swapItems(Integer asnId){
        System.out.println("Doing: "+asnId);

        try{
            Asn asn = (Asn) HibernateSession.currentSession().load(Asn.class,asnId);

            for(AsnItem item : asn.getAsnItems()){
                System.out.println("Doing Item: "+ item.getInventoryNum());
                String sql = "select newFkey, newSku from clientTransferData where inventory_fkey = :fkey";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                q.setParameter("fkey",item.getInventoryFkey());
                List l = q.list();
                Object o = l.get(0);
                Object[] data = (Object[]) o;
                item.setInventoryNum(data[1].toString());
                item.setInventoryFkey(Integer.parseInt(data[0].toString()));

                HibernateSession.currentSession().saveOrUpdate(item);

            }
            HibUtils.commit(HibernateSession.currentSession());


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
