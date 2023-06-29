package com.owd.alittlePlaying;

import com.owd.connectship.soap.Result;
import com.owd.core.dbUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.exolab.castor.types.Date;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.engine.spi.SessionImplementor;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 2/3/2017.
 */
public class fixingWeirdAdjustmentErrors {




    public static void main(String[] args){

        String sql = "select * from owd_inventory_history where note = 'custom.OutsideCountDone' and inventory_fkey != 377860";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

            List results = q.list();
            System.out.println(results.size());
            OwdReceive rcv = new OwdReceive();
            rcv.setCarrier("UPS Ground");
            rcv.setCreatedBy("OUTSIDE COUNT");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:MM:ss");
            rcv.setCreatedDate(format.parse("2017-02-01 21:23:32"));
            rcv.setDriver("");
            rcv.setIsVoid(false);
            rcv.setNotes("outside count");
            rcv.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, Integer.valueOf(489)));
            rcv.setReceiveDate(format.parse("2017-02-01 21:23:32"));
            rcv.setReceiveUser("danny");
            rcv.setRefNum("WIS Count");
            rcv.setTimeIn(format.parse("2017-02-01 21:23:32"));
            rcv.setTimeOut(format.parse("2017-02-01 21:23:32"));
            rcv.setType("Adjustment");
            rcv.setFacilityCode("DC6");
            HibernateSession.currentSession().save(rcv);




            for (Object row : results) {
                Map info = (Map) row;
                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,Integer.parseInt(info.get("inventory_fkey").toString()));
                System.out.println(inv.getInventoryNum());
                OwdReceiveItem ri = new OwdReceiveItem();
                OwdInventoryHistory history = (OwdInventoryHistory) HibernateSession.currentSession().load(OwdInventoryHistory.class,Integer.parseInt(info.get("id").toString()));

                ri.setCreatedBy("danny");
                ri.setCreatedDate(format.parse("2017-02-01 21:23:32"));
                ri.setDescription(inv.getDescription());
                ri.setInventoryNum(inv.getInventoryNum());
                ri.setItemStatus("New");
                ri.setOwdInventory(inv);
                ri.setIsVoid(0);
                ri.setQuantity(Integer.parseInt(info.get("qty_change").toString()));
                ri.setOwdReceive(rcv);
                HibernateSession.currentSession().save(ri);

                history.setReceiveItemFkey(ri.getReceiveItemId());
                HibernateSession.currentSession().save(history);





            }
            rcv.setTransactionNum("OWDADJ-"+ dbUtilities.getNextID(((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), "Receive"));

            HibernateSession.currentSession().save(rcv);
            HibUtils.commit(HibernateSession.currentSession());


        }catch(Exception e){
e.printStackTrace();
        }

    }
}
