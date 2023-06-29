package com.owd.jobs.jobobjects.billing.FlatRateShipping;

import com.owd.LogableException;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by danny on 2/15/2019.
 * Utilities to Modify Flat Rate Shipping records to account for added rules
 */
public class FlatRateShippingBillingUtilities {
    private final static Logger log =  LogManager.getLogger();
    private static double IntravexFees = 0.26;


    /**
     * This function looks for records in the intravexebills table that have duties and taxes and were shipped under OWD_FLAT_RATE shipping methods
     *
     * @param date Billed Date that we are looking up the records for
     * @return A list of Map<String,String> containing the following fields from the table(order_fkey,tracking,dutyAndTaxes,importDate)
     * @throws Exception
     */
    public static List getDutyAndTaxesRecordsForDate(String date) throws Exception{


        List records = new ArrayList();

        String sql = "execute getFlatRateDutyAndTaxesForBilledDate :billedDate ";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        q.setParameter("billedDate",date);

        records = q.list();




        return records;
    }




    public static void findAndUpdateDutyAndTaxesForFlatRateShipmentsForDate(String date){

        try{
            List l = getDutyAndTaxesRecordsForDate(date);

            for(Object data : l){
                Map record = (Map) data;
                log.debug("Doing Duty and Taxes update for :"+ record.get("order_fkey"));
                updateOwdBillShippingTransDutyAndTaxes(record,date);
            }


        }catch (Exception e){
            e.printStackTrace();
          //  new LogableException(e.getMessage(),"","55","Billing Updates", LogableException.errorTypes.INTERNAL);
        }




    }


    /**
     * Update the owdbill_shipping_trans record with the duty and Taxes info
     * @param record Map containing order_fkey, tracking, dutyAndTaxes, importDate
     * @param date the Date the record was created
     */
    public static void updateOwdBillShippingTransDutyAndTaxes(Map record,String date){
        try {
            String sql = "update owdbill_shipping_trans set amount = :amount where convert(date,created_date) = :billedDate and order_fkey = :orderFkey and tracking = :tracking and activity_type_fkey = 1 and activity_desc like '%Manual Airbill' ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("amount", Double.parseDouble(record.get("dutyAndTaxes").toString())+IntravexFees);
            q.setParameter("billedDate",date);
            q.setParameter("orderFkey",record.get("order_fkey"));
            q.setParameter("tracking",record.get("tracking"));

            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }else{
                throw new Exception("Unable to update duty and taxes for: " + record.get("order_fkey"));
            }



        }catch (Exception e){
            e.printStackTrace();
            //  new LogableException(e.getMessage(),"","55","Billing Updates", LogableException.errorTypes.INTERNAL);

        }


    }

    public static void main(String[] args){
        findAndUpdateDutyAndTaxesForFlatRateShipmentsForDate("2019-02-09");
    }
}
