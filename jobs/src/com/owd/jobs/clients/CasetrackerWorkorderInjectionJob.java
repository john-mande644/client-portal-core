package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.OWDStatefulJob;
import com.owd.LogableException;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 4/6/15.
 */
public class CasetrackerWorkorderInjectionJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();



    public void internalExecute() {

        try{


            ResultSet rs = HibernateSession.getResultSet("select * from case_queue " +
                    "order by id asc");

            List<Map<String,Object>> ediList = new ArrayList<Map<String,Object>>();

            while(rs.next()) {
               Map<String,Object>  ediMap = new HashMap<String,Object>();
                ediMap.put("id",rs.getInt("id"));
                ediMap.put("subject",rs.getString("subject"));
                ediMap.put("body",rs.getString("body"));
                ediMap.put("brand",rs.getString("brand"));
                ediMap.put("facility",rs.getString("facility"));
                ediMap.put("clientid",rs.getInt("clientid"));
                ediList.add(ediMap);

            }
            rs.close();

             for(Map<String, Object> emap:ediList) {
                 int id = (Integer) emap.get("id");
                 try {


                     CasetrackerAPI.injectWorkOrder((String) emap.get("subject"), (String) emap.get("body"), (String) emap.get("brand"), (String) emap.get("facility"),(Integer) emap.get("clientid"));

                     PreparedStatement ps = HibernateSession.getPreparedStatement("delete from case_queue where id=?");

                     ps.setInt(1, id);
                     ps.executeUpdate();
                     HibUtils.commit(HibernateSession.currentSession());

                 } catch (Exception ex) {
                     ex.printStackTrace();
                     LogableException le = new LogableException(ex, "Error creating  work order", "TS:"+Calendar.getInstance().getTimeInMillis(), "489", "casetracker", LogableException.errorTypes.INTERNAL);

                 }


             }
            log.debug("done");

    }catch(Exception ex)
    {
        ex.printStackTrace();
        LogableException le = new LogableException(ex, "Error creating  work order","TS:"+Calendar.getInstance().getTimeInMillis(), "489", "casetracker", LogableException.errorTypes.INTERNAL);

    }finally
        {
        }

    }
    public static void main(String[] args) throws Exception{



        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,8917376);

        String body = "Symphony Order Ref: "+order.getOrderRefnum()+" OWD Ref: "+order.getOrderNum()+" Brand: Fiji Water\n\n\n - Palletize order \n\n" +
                " - Fetch LTL shipping quote\n\n" +
                " - Forward quote to these 3 email addresses:\n\n --- steve@symphonycommerce.com\n" +
                " --- maburger@fijiwater.com\n" +
                " --- viet@symphonycommerce.com\n\n" +
                " - Fiji will either approve shipment or cancel and ship from another location\n";

        PreparedStatement psedi = HibernateSession.getPreparedStatement("insert into case_queue (subject,body,brand,facility) VALUES (?,?,?,?);");
        psedi.setString(1, "Billable LTL Quote Request for Symphony/Fiji Waters - Symphony Order "+order.getOrderRefnum());
        psedi.setString(2,body);
        psedi.setString(3,order.getGroupName());
        psedi.setString(4,order.getFacilityCode());
        psedi.executeUpdate();
        psedi.close();
        HibUtils.commit(HibernateSession.currentSession());

        run();


    }
}
