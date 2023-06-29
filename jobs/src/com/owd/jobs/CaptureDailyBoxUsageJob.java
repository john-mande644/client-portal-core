package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.LogableException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class CaptureDailyBoxUsageJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) {
        run();
    }

    public void internalExecute() {

        try {
            Calendar currCal = Calendar.getInstance();

            //loops to ensure that past transactions captured. inventory adjust method returns error if transaction ref value already exists
            for (int i=1; i<=2; i++)
            {
                currCal.add(Calendar.DATE,-1);

                PreparedStatement ps = HibernateSession.getPreparedStatement("select a.inventory_fkey,a.client_fkey,a.qty+isnull(b.qty,0),a.sdate,facility_code  from \n" +
                        "                    (\n" +
                        "                    select b.inventory_fkey,case when b.client_fkey=0 THEN 99999 ELSE \n" +
                        "                                            case when b.client_fkey is null then\n" +
                        "                                            case \n" +
                        "                                            when o2.facility_code = 'DC1' then 55\n" +
                        "                                            when o2.facility_code = 'DC6' then 488\n" +
                        "                                            when o2.facility_code = 'DC7' then 534\n" +
                        "                                            end\n" +
                        "                                            else\n" +
                        "                                            b.client_fkey\n" +
                        "                                            end END as client_fkey,\n" +
                        "                    case when qty_on_hand-(count(*))>=0 then -1*(count(*)) else -1*qty_on_hand end as qty ,\n" +
                        "                                                           convert(varchar,convert(datetime,convert(varchar,max(ship_time),101)),21) as sdate ,\n" +
                        "                                                           o2.facility_code\n" +
                        "                                       from owd_boxtypes b join owd_inventory_oh oh on oh.inventory_fkey=b.inventory_fkey\n" +
                        "                                       join package p \n" +
                        "                                                           join package_order po join owd_order o2 on o2.order_id=po.owd_order_fkey on po.id=package_order_fkey  \n" +
                        "                                                           on p.owd_boxtypes_fkey=b.id and b.inventory_fkey is not null\n" +
                        "                                                           where po.is_void=0 and ship_time>='2007-11-16' and datediff(day,ship_time,getdate())>0 \n" +
                        "                                       and convert(datetime,convert(varchar,ship_time,101))=?\n" +
                        "                                                          group by  convert(varchar,convert(datetime,convert(varchar,ship_time,101)),21), \n" +
                        "                                       name, b.inventory_fkey,b.client_fkey,qty_on_hand,o2.facility_code)\n" +
                        "                                       a \n" +
                        "                                       left outer join \n" +
                        "                                       (select inventory_id as bkey,shipped_on as sdate,\n" +
                        "                                       sum(quantity_actual) as qty from owd_order o join owd_line_item on order_id=order_fkey group by inventory_id,shipped_on)\n" +
                        "                                       b\n" +
                        "                                       on b.bkey=a.inventory_fkey and a.sdate=b.sdate");


                Date now = currCal.getTime();
                log.debug("Trying for date "+OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE,-1*i));
                ps.setString(1, OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE,-1*i));
                log.debug(OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE,-1*i));
                      ResultSet rs = ps.executeQuery();
                List<BoxData> boxData = new ArrayList<BoxData>();

                    while(rs.next()) {

                         boxData.add(new BoxData(rs.getInt(1),rs.getInt(2),rs.getLong(3),rs.getString(4),rs.getString(5)));
                    }
                rs.close();
                       for(BoxData box:boxData) {
                           log.debug("Trying for BOXADJ-"+OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE,-1*i,true)+"-"+box.clientId+"-"+box.inventoryId+"::"+box.qty);

                        try
                        {
                        InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(box.inventoryId,box.clientId,(int)box.qty,
                                OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE,-1*i,true),
                                "BOXADJ-"+OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE,-1*i,true)+"-"+box.clientId+"-"+box.inventoryId,
                                box.facilityCode,HibernateSession.currentSession());
                            HibUtils.commit(HibernateSession.currentSession());
                        }catch(Exception ex)
                        {
                            if(ex.getMessage().contains("already exists")) {
                                log.debug(ex.getMessage() + " for " + "BOXADJ-" + OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.DATE, -1 * i, true) + "-" + box.clientId + "-" + box.inventoryId);
                            }
                            else {
                                HibernateSession.closeSession();
                                ex.printStackTrace();
                            }
                            HibUtils.rollback(HibernateSession.currentSession());
                        }
                    }
                rs.close();
                HibernateSession.closeSession();
            }
                } catch (Exception ex) {
                    try {
                        HibUtils.rollback(HibernateSession.currentSession());
                    } catch (Exception exx) {
                    }
                    ex.printStackTrace();
            Exception xx = new LogableException(ex, "Failed to collect used boxes",
                    "TS:"+ Calendar.getInstance().getTimeInMillis(), "55", "OWD", LogableException.errorTypes.INTERNAL);

        } finally {
                    HibernateSession.closeSession();
                }


    }

    public class BoxData {
        int inventoryId;
        int clientId;
        long qty;
        String shipDate;
        String facilityCode;

        public BoxData(int iid, int cid, long aqty, String adate, String fcode)
        {
            inventoryId = iid;
            clientId = cid;
            qty = aqty;
            shipDate = adate;
            facilityCode = fcode;
        }
    }
}
