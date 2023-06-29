package com.owd.alittlePlaying.orderthings.factoryPlaying;

import com.owd.core.business.order.OrderFactory;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 * Created by danny on 9/8/2017.
 */
public class testing {

    public static void main(String[] args){
        try{

           /* OwdOrder o = OrderFactory.getOwdOrderFromClientReference("W85779","390");
            System.out.println(o.getOrderNum());

           int days =  new Period(LocalDate.now(), LocalDate.fromDateFields(o.getShippedDate()), PeriodType.days()).getDays();

            System.out.println(days);
            if(days <-1 && days >-10){
                System.out.println(true);
            }else{
                System.out.println(false);
            }*/
           /* LocalDate date = new LocalDate();
            System.out.println(date.getDayOfWeek());
            System.out.println(date.withDayOfWeek(DateTimeConstants.THURSDAY));
            if(date.withDayOfWeek(DateTimeConstants.THURSDAY).isBefore(date)||date.withDayOfWeek(DateTimeConstants.THURSDAY).equals(date)){
                System.out.println(date.withDayOfWeek(DateTimeConstants.THURSDAY).plusDays(7));
            }else{
                System.out.println(date.withDayOfWeek(DateTimeConstants.THURSDAY));
            }*/

            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,14703641);
            System.out.println(order.getOrderStatus());

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
