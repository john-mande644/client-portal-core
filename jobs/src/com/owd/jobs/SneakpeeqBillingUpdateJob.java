package com.owd.jobs;

import com.owd.jobs.jobobjects.billing.AllInclusive.AllInclusiveUtilities;
import com.owd.jobs.jobobjects.billing.BulkySkuBilling.BulkySkuBillingUtilities;
import com.owd.jobs.jobobjects.billing.specialRules.processSpecialRules;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/1/12
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class SneakpeeqBillingUpdateJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    static         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws Exception {

        run();

      //  storageChargesNeedToBeCalculated();
       /*
        Date chargingDate = getLastDayOfPreviousMonth();
        PreparedStatement ps = HibernateSession.getPreparedStatement(updateStorageSql);
        ps.setDate(1, new java.sql.Date(chargingDate.getTime()));
        ps.executeUpdate();
        HibUtils.commit(HibernateSession.currentSession());
        ps.close();

        HibernateSession.closeSession();*/
    }


    static private String updatePickPackSql =
            "insert  into sp_bill_pickpack select * from vw_sp_bill_pickpack2 \n" +
            "where shipped_on>(select max(shipped_on) from sp_bill_pickpack) \n" +
            "and shipped_on<=convert(datetime,convert(varchar,dateadd(day,-1,getdate()),101)) and oid not in (select oid from sp_bill_pickpack);";

    static private String updateReceivingSql =
            "insert  into sp_bill_receiving select * from vw_sp_bill_receiving2 \n" +
            "where received>(select max(received) from sp_bill_receiving) \n" +
            "and received<=convert(datetime,convert(varchar,dateadd(day,-1,getdate()),101));";

    static private String updateReturnsSql =
            "insert  into sp_bill_returns select * from vw_sp_bill_returns2 \n" +
            "where returned>(select max(returned) from sp_bill_returns) \n" +
            "and returned<=convert(datetime,convert(varchar,dateadd(day,-1,getdate()),101));";

    static private String updateShippingSql =
            "insert  into sp_bill_shipping select * from vw_sp_bill_shipping \n" +
            "where recorded_date>(select max(recorded_date) from sp_bill_shipping)  \n" +
            "and recorded_date<=convert(datetime,convert(varchar,dateadd(day,-1,getdate()),101));";

    static private String updateStorageSql =
            "insert  into sp_bill_storage (default_facility_code,\n" +
                    "inventory_num,\n" +
                    "description,\n" +
                    "mfr_part_num,\n" +
                    "group_name,\n" +
                    "items,\n" +
                    "location,\n" +
                    "[Cubic Feet Used],\n" +
                    "Cost,\n" +
                    "[Client ID],\n" +
                    "recorded_date)\n" +
                    "\n" +
                    "select default_facility_code,\n" +
                    "inventory_num,\n" +
                    "description,\n" +
                    "mfr_part_num,\n" +
                    "group_name,\n" +
                    "items,\n" +
                    "location,\n" +
                    "[Cubic Feet Used],\n" +
                    "Cost,\n" +
                    "[Client ID],? as recorded_date from vw_sp_bill_storage2 s";

    public void internalExecute() {

        try {
            PreparedStatement ps = HibernateSession.getPreparedStatement(updatePickPackSql);
            ps.executeUpdate();
            ps.close();
            HibUtils.commit(HibernateSession.currentSession());

            ps = HibernateSession.getPreparedStatement(updateReceivingSql);
            ps.executeUpdate();
            ps.close();
            HibUtils.commit(HibernateSession.currentSession());

            ps = HibernateSession.getPreparedStatement(updateReturnsSql);
            ps.executeUpdate();
            ps.close();
            HibUtils.commit(HibernateSession.currentSession());

            ps = HibernateSession.getPreparedStatement(updateShippingSql);
            ps.executeUpdate();
            ps.close();
            HibUtils.commit(HibernateSession.currentSession());

            if(storageChargesNeedToBeCalculated())
            {
                //first day of month, get storage charges
                Date chargingDate = getLastDayOfPreviousMonth();
                ps = HibernateSession.getPreparedStatement(updateStorageSql);
                ps.setDate(1,new java.sql.Date(chargingDate.getTime()));
                ps.executeUpdate();
                ps.close();

            }
            HibUtils.commit(HibernateSession.currentSession());
            //process all the special rules sales is making.

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE,-1);
            processSpecialRules.processRules(df.format(c.getTime()));

            BulkySkuBillingUtilities.processDailyBulkySkus(df.format(c.getTime()));
            AllInclusiveUtilities.updateAllInclusivePricingForDate(df.format(c.getTime()));

            HibernateSession.closeSession();


        } catch (Exception ex) {
            ex.printStackTrace();

            Mailer.postMailMessage("[URGENT] Daily Billing Job update failed with exception", ex.getMessage(), "casetracker@owd.com", "donotreply@owd.com");

        }
    }


    static Date getLastDayOfPreviousMonth()
    {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.set(Calendar.HOUR,0);
        aCalendar.set(Calendar.MINUTE,0);
        aCalendar.set(Calendar.MILLISECOND,0);
        aCalendar.set(Calendar.SECOND,0);
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);
        return aCalendar.getTime();
    }
    static boolean storageChargesNeedToBeCalculated()
    {
        boolean chargesNeeded = false;

        try{
        ResultSet rs = HibernateSession.getResultSet("select max(recorded_date) from sp_bill_storage");

            if(rs.next())
            {
                Date maxDate = rs.getDate(1);

                Date lastDateOfPreviousMonth = getLastDayOfPreviousMonth();

                log.debug("lastdayofmonth="+df.format(lastDateOfPreviousMonth));
                log.debug("maxDate="+df.format(maxDate));
                if(lastDateOfPreviousMonth.getTime()>maxDate.getTime())
                {
                    chargesNeeded = true;
                }
           /*     if(!(df.format(lastDateOfPreviousMonth).equals(df.format(maxDate))))
                {
                    chargesNeeded = true;
                }*/
            }

            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            Mailer.postMailMessage("Symphony Billing Job storage charge eligibility calculation failed with exception", ex.getMessage(), "servicerequests@owd.com", "donotreply@owd.com");
        }
        log.debug("Storage charges needed="+chargesNeeded);
        return chargesNeeded;

    }
}
