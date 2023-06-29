package com.owd.jobs.jobobjects.billing.specialRules;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 10/9/2017.
 */
public class BypassAllInsertRule extends specialRulesBase{
    private final static Logger log =  LogManager.getLogger();
    public BypassAllInsertRule(){

        setSql("SELECT\n"+
                "    dbo.sp_bill_pickpack.id\n"+
                "FROM\n"+
                "    dbo.sp_bill_pickpack\n"+
                "INNER JOIN\n"+
                "    dbo.owd_line_item\n"+
                "ON\n"+
                "    (\n"+
                "        dbo.sp_bill_pickpack.oid = dbo.owd_line_item.order_fkey)\n"+
                "INNER JOIN\n"+
                "    dbo.owd_inventory\n"+
                "ON\n"+
                "    (\n"+
                "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id)\n"+
                "WHERE\n"+
                "    \n"+
                " dbo.sp_bill_pickpack.[Client ID]  = :clientId\n"+
                "AND dbo.sp_bill_pickpack.shipped_on > :effectiveDate and owd_inventory.is_insert = 1 and [Additional Picks] > 0 \n"+
                "group by oid, id\n"+
                "having dbo.udf_getShippingUnitCount(dbo.sp_bill_pickpack.oid) = SUM(dbo.owd_line_item.quantity_actual) ;");
    }


    public void processIds(List<String> ids,pricingBean prices) throws Exception{
        log.debug("Processing "+ids.size()+" for "+ prices.getRuleName());
        for(String id:ids){
            //update the id to the pricing bean info.
            String allInsertLookup = "SELECT\n" +
                    "    SUM(CASE when owd_inventory.is_insert = 1 then dbo.owd_line_item.quantity_actual else 0 END) as IsInsert,\n" +
                    "     SUM(CASE when owd_inventory.is_insert = 0 then dbo.owd_line_item.quantity_actual else 0 END) as NoInsert\n" +
                    "FROM\n" +
                    "    dbo.owd_line_item\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_inventory\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_line_item.inventory_id = dbo.owd_inventory.inventory_id)\n" +
                    "INNER JOIN\n" +
                    "    dbo.sp_bill_pickpack\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_line_item.order_fkey = dbo.sp_bill_pickpack.oid)\n" +
                    "WHERE\n" +
                    "    dbo.sp_bill_pickpack.id = :id ;";

            Query q = HibernateSession.currentSession().createSQLQuery(allInsertLookup);
            q.setParameter("id",id);

            List l = q.list();
            Object[] data = (Object[]) l.get(0);
            int inserts = Integer.parseInt(data[0].toString());
            int noInserts = Integer.parseInt(data[1].toString());

            log.debug(inserts + ", " + noInserts);
            if(noInserts == 0){
                updateBillingRecord(id);
            }else{
                log.debug("Mixed: "+ id);
            }




        }

    }


    private boolean updateBillingRecord(String id){
        boolean success = false;

        try{
            String sql = "update sp_bill_pickpack set [Additional Picks] = 0, [Additional Pick Fees] = 0, [Inserts] = [Additional Picks], \n" +
                    "[Insert Fees] = [Additional Picks] *dbo.udf_getapirategroup([Client ID],'INSERT_FEE', facility_code,group_name) where id = :id\n";
               Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("id",id);
            int i = q.executeUpdate();
            if (i==1){
                HibUtils.commit(HibernateSession.currentSession());
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        return success;
    }



}
