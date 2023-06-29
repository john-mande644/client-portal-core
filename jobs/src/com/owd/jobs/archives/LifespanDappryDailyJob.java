package com.owd.jobs.archives;

import com.owd.core.Mailer;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import org.hibernate.Query;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by danny on 1/30/2017.
 */
public class LifespanDappryDailyJob extends OWDStatefulJob{

    public static void main(String[] args){
        run();

    }

    public void internalExecute(){
        List<String> l = new ArrayList<>();
        l.add("EE");
        l.add("TPI");
        l.add("EV40");
        l.add("O3");
        l.add("XECUTEP");
        l.add("G90");
        l.add("HX");
        l.add("CRJB");
        l.add("F5");
        l.add("NikeM");
        l.add("NikeL");
        l.add("NikeXL");
        l.add("NikeXXL");
        l.add("RTSM");
        l.add("RTSL");
        l.add("RTSXL");
        l.add("RTS2XL");
        l.add("MGRUN-M");
        l.add("MGRUN-L");
        l.add("MGRUN-XL");
        l.add("MGRUN-XXL");
        l.add("NikeTankS");
        l.add("NikeTankM");
        l.add("NikeTankL");
        l.add("WBBELLA-S");
        l.add("WBBELLA-M");
        l.add("WBBELLA-L");
        l.add("CAMOHAT");
        l.add("WB");

        String sql = "SELECT\n" +
                "    dbo.owd_inventory.inventory_num,\n" +
                "    dbo.owd_inventory_oh.qty_on_hand\n" +
                "FROM\n" +
                "    dbo.owd_inventory\n" +
                "INNER JOIN\n" +
                "    dbo.owd_inventory_oh\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_oh.inventory_fkey)\n" +
                "WHERE\n" +
                "    dbo.owd_inventory.client_fkey = 320\n" +
                "AND dbo.owd_inventory.inventory_num IN (:skuList) ;";
try {


    Query q = HibernateSession.currentSession().createSQLQuery(sql);
    q.setParameterList("skuList",l);
    List results = q.list();
    System.out.println(results.size());
    StringBuilder sb = new StringBuilder();

    for(Object row : results){
        Object[] data = (Object[]) row;
        sb.append(data[0]);
        sb.append(",");
        sb.append(data[1]);
        sb.append("\r\n");

    }
    System.out.println(sb.toString());
    DateFormat df = new SimpleDateFormat("yyyyMMdd");

    Calendar aCalendar = Calendar.getInstance();
    Mailer.sendMailWithAttachment("Dappry","Attached is the inventory list","techsupport@dappry.com",sb.toString().getBytes(),"inventory_"+df.format(aCalendar.getTime())+".csv","text/csv");
    Mailer.sendMailWithAttachment("Dappry","Attached is the inventory list","owditadmin@owd.com",sb.toString().getBytes(),"inventory_"+df.format(aCalendar.getTime())+".csv","text/csv");

}catch (Exception e){
    e.printStackTrace();

}



    }
}
