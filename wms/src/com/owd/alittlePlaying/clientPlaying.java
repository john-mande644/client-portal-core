package com.owd.alittlePlaying;

import com.owd.dc.manifest.Manifestxml.GSSPackage;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdClientBillTypes;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by danny on 12/22/2015.
 */
public class clientPlaying {
    public static void main(String args[]){
        try{
            OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, 489);


           /* for(OwdClientBillTypes btye:client.getBillTypes()){
                System.out.println(btye.getGroupName()+":"+btye.getId());
            }*/
            Criteria criteria = HibernateSession.currentSession().createCriteria(OwdClientBillTypes.class);
            criteria.add(Restrictions.eq("owdClient",client));
            criteria.add(Restrictions.ne("groupName",""));
            List<OwdClientBillTypes> btype = criteria.list();
            for(OwdClientBillTypes bt:btype){

                System.out.println(bt.getGroupName());
                System.out.println(bt.getCode());
            }
           /* System.out.println(client.getUspsAddedMarginPct());
            if(null == client.getUspsAddedMarginPct()){
                System.out.println("lala");
            }
            GSSPackage pkg = new GSSPackage();
            pkg.setUSPSAddToPercent(client.getUspsAddedMarginPct());
            System.out.println(pkg.getUSPSAddToPercent());
            long ll = 150l;*/



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
