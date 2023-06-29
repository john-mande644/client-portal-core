package com.owd.alittlePlaying.inventory;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by danny on 9/6/2018.
 */
public class orginCountryPlaying {
    protected final static Logger log = LogManager.getLogger();



    public static void main(String[] args){

        try{
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, 392592);
            if(null == inv.getOwdClientInv()){
                log.debug("null");
            }
          /*  log.debug(inv.getOwdInventoryOh().getQtyOnHand());
            log.debug(inv.getOwdClientInv().getOriginCountry());
            inv.getOwdClientInv().setOriginCountry("CHINA");
            HibernateSession.currentSession().saveOrUpdate(inv.getOwdClientInv());
            HibUtils.commit(HibernateSession.currentSession());*/

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
