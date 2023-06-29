package com.owd.alittlePlaying;

import com.owd.core.ExcelUtils;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by danny on 10/29/2016.
 */
public class udpateSLA {


    static String pattern = "yyyy-MM-dd";
    static SimpleDateFormat format = new SimpleDateFormat(pattern);

    public static void main(String args[]){
        try {
            Date start = format.parse("2016-09-11");

            System.out.println(start);
            System.out.println(addDays(start,1));
            System.out.println(format.format(start));


            int i = 1;
            while(i<100){
                System.out.println(format.format(start));
                updateSLAForRange(start);
                start = addDays(start,1);
                i++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
       // cal.roll(Calendar.DATE,true);
        cal.add(Calendar.DATE, days); //minus number would decrement the days



        return cal.getTime();
    }



    public static void updateSLAForRange(Date start) throws Exception{

    String sql = "update owd_order set is_shipping = 0 WHERE is_void = 0 and sla is null and actual_order_date > :start and actual_order_date < :end and order_status <> 'On Hold' and order_status <> 'Backorder (Active)'";

    Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("start",format.format(start));
        q.setParameter("end",format.format(addDays(start,1)));
        int i = q.executeUpdate();

        System.out.println(i);
        if(i>0){
            HibUtils.commit(HibernateSession.currentSession());
        }


    }
}
