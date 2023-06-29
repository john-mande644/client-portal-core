package com.owd.jobs.jobobjects.transfirst;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.LineItem;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdInventory;

import java.text.SimpleDateFormat;
import java.util.*;

public class HighTimesUtilities {
private final static Logger log =  LogManager.getLogger();
    public static List<Integer> BESUBMonthArray = Arrays.asList(8, 11, 2, 5);
    public static List<Integer> MMJSUBMonthArray = Arrays.asList(10, 1, 4, 7);
    /**
     * Logic for HT subscriber orders...
     * <p/>
     * Remove items from order matching SUB pattern
     * <p/>
     * Separate three subscription types based on SKU
     * For each subscription type:
     * - calculate next shipment date and SKU for that sub type
     * - if SKU does not exist, create it according to pattern   (reuse with subscription order maker)
     * - Create order with SKUs/quantities/post date for that sub type
     * - Create subscription order with CC info for 2nd ship date  and subsequent
     * <p/>
     * <p/>
     * <p/>
     * So I'll set the MM anniversary to the 10th, say, of the first month of each quarter starting November 2011, and
     * Best Of to the 10th of the first month of each quarter starting in September?
     * HT 3rd of each month
     * HTSUB, BESUB, and MMJSUB
     */

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static List<String> SubscriptionSkusList = Arrays.asList("HTSUB", "BESUB", "MMJSUB");

    public static void printNextAndSecondDatesForDayAndSub(Calendar cal) throws Exception {
        log.debug("Today:" + sdf.format(cal.getTime()));
        log.debug("Next HT Issue: " + sdf.format(getNextAnniversaryDateAfter(cal.getTime(), "HTSUB")) + " SKU:" + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "HTSUB"), "HTSUB"));
        log.debug("Next Best Of Issue: " + sdf.format(getNextAnniversaryDateAfter(cal.getTime(), "BESUB")) + " SKU:" + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "BESUB"), "BESUB"));
        log.debug("Next MM Issue: " + sdf.format(getNextAnniversaryDateAfter(cal.getTime(), "MMJSUB")) + " SKU:" + getSkuForAnniversaryDate(getNextAnniversaryDateAfter(cal.getTime(), "MMJSUB"), "MMJSUB"));

        log.debug("2nd HT Issue: " + sdf.format(getSecondAnniversaryDateAfter(cal.getTime(), "HTSUB")) + " SKU:" + getSkuForAnniversaryDate(getSecondAnniversaryDateAfter(cal.getTime(), "HTSUB"), "HTSUB"));
        log.debug("2nd Best Of Issue: " + sdf.format(getSecondAnniversaryDateAfter(cal.getTime(), "BESUB")) + " SKU:" + getSkuForAnniversaryDate(getSecondAnniversaryDateAfter(cal.getTime(), "BESUB"), "BESUB"));
        log.debug("2nd MM Issue: " + sdf.format(getSecondAnniversaryDateAfter(cal.getTime(), "MMJSUB")) + " SKU:" + getSkuForAnniversaryDate(getSecondAnniversaryDateAfter(cal.getTime(), "MMJSUB"), "MMJSUB"));
    }

    public static Date getSecondAnniversaryDateAfter(Date startDate, String Sku) throws Exception {
        Date firstAnnDate = getNextAnniversaryDateAfter(startDate, Sku);
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstAnnDate);
        cal.add(Calendar.DATE, 1);
        return getNextAnniversaryDateAfter(cal.getTime(), Sku);
    }

    public static Date getNextAnniversaryDateAfter(Date startDate, String Sku) throws Exception {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(startDate);
        if (Sku.equals("HTSUB")) {
            if (cal.get(Calendar.DAY_OF_MONTH) < 3) {
                cal.set(Calendar.DAY_OF_MONTH, 3);
            } else {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 3);
            }
        } else if (Sku.equals("BESUB")) {

            //quarterly, starts september 2011 (month 8), so months 8, 11, 2, 5, and back to 8


            if (BESUBMonthArray.contains(cal.get(Calendar.MONTH)) && cal.get(Calendar.DAY_OF_MONTH) < 10) {
                cal.set(Calendar.DAY_OF_MONTH, 10);
            } else if (BESUBMonthArray.contains(cal.get(Calendar.MONTH))) {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 10);
            }

            while (!(BESUBMonthArray.contains(cal.get(Calendar.MONTH)))) {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 10);
            }
        } else if (Sku.equals("MMJSUB")) {
            //quarterly, starts november 2011 (month 10), so months 10, 1, 4, 7, 10, 1, etc.
            if (MMJSUBMonthArray.contains(cal.get(Calendar.MONTH)) && cal.get(Calendar.DAY_OF_MONTH) < 12) {
                cal.set(Calendar.DAY_OF_MONTH, 12);
            } else if (MMJSUBMonthArray.contains(cal.get(Calendar.MONTH))) {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 12);
            }

            while (!(MMJSUBMonthArray.contains(cal.get(Calendar.MONTH)))) {
                cal.add(Calendar.MONTH, 1);
                cal.set(Calendar.DAY_OF_MONTH, 12);
            }


        } else {
            throw new Exception("Subscription SKU " + Sku + " not valid");
        }
        return cal.getTime();
    }

    public static String getSkuForAnniversaryDate(Date annDate, String subSku) throws Exception {
        String newSku = null;

        Calendar htcal = Calendar.getInstance();
        htcal.set(Calendar.MONTH, 8);
        htcal.set(Calendar.YEAR, 2011);
        htcal.set(Calendar.DAY_OF_MONTH, 3);

        Calendar becal = Calendar.getInstance();
        becal.set(Calendar.MONTH, 8);
        becal.set(Calendar.YEAR, 2011);
        becal.set(Calendar.DAY_OF_MONTH, 10);

        Calendar mmjcal = Calendar.getInstance();
        mmjcal.set(Calendar.MONTH, 7);
        mmjcal.set(Calendar.YEAR, 2011);
        mmjcal.set(Calendar.DAY_OF_MONTH, 12);

        int beNum = (62 + (getMonthsDifference(becal.getTime(), annDate)) / 3);
        int htNum = (429 + getMonthsDifference(htcal.getTime(), annDate));
        int mmNum = (7 + (getMonthsDifference(becal.getTime(), annDate)) / 3);
        if (subSku.equals("HTSUB")) {
            //count months
            newSku = "" + htNum;

        } else if (subSku.equals("BESUB")) {

            //quarterly, starts september 2011 (month 8), so months 8, 11, 2, 5, and back to 8

            newSku = "BE" + beNum;


        } else if (subSku.equals("MMJSUB")) {
            //quarterly, starts november 2011 (month 10), so months 10, 1, 4, 7, 10, 1, etc.
            newSku = "MM#" + mmNum;


        } else {
            throw new Exception("Subscription SKU " + subSku + " not valid");
        }
        if (newSku == null) {
            throw new Exception("Future SKU for " + subSku + " and date " + sdf.format(annDate) + " not valid");
        }

        if (!(LineItem.SKUExists("463", newSku))) {
            OwdInventory item = InventoryManager.getInitializedOwdInventory(463);
            item.setInventoryNum(newSku);
            if (subSku.equals("HTSUB")) {
                //count months
                item.setDescription("HIGH TIMES #" + htNum);
            } else if (subSku.equals("BESUB")) {

                //quarterly, starts september 2011 (month 8), so months 8, 11, 2, 5, and back to 8

                item.setDescription("Best of HIGH TIMES #" + beNum);
            } else if (subSku.equals("MMJSUB")) {
                //quarterly, starts november 2011 (month 10), so months 10, 1, 4, 7, 10, 1, etc.
                item.setDescription("Medical Marijuana #" + mmNum);
            }

            item.setModifiedBy("Order Importer");
            item.setNotes("");
            item.setOwdClient((OwdClient) HibernateSession.currentSession().load(OwdClient.class, 463));
            item.setWeightLbs(0.45f);
            HibernateSession.currentSession().save(item);
            HibernateSession.currentSession().save(item.getOwdInventoryOh());
            HibernateSession.currentSession().save(item.getPackingInstructions());
            HibernateSession.currentSession().flush();
            //commit?
            HibUtils.commit(HibernateSession.currentSession());
        }
        return newSku;
    }

    public static final int getMonthsDifference(Date date1, Date date2) {
        int m1 = date1.getYear() * 12 + date1.getMonth();
        int m2 = date2.getYear() * 12 + date2.getMonth();
        return m2 - m1 + 1;
    }
}
