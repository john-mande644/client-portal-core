package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Feb 3, 2009
 * Time: 8:50:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class PackSlipReleaseJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    private Map<String, Map<String, Integer>> runtimes = new TreeMap<String, Map<String, Integer>>();
    private String deleteInvalidOrders = "delete from owd_print_queue3 where print_queue_id in (select print_queue_id from owd_print_queue3 q\n" +
            " join owd_order o on o.order_id=q.order_id and order_status<>'At Warehouse')";

    private String deleteOlderDuplicatePrintJobs = "delete from owd_print_queue3 where print_queue_id in (select print_queue_id from owd_print_queue3 join (\n" +
            " select order_id as oid,max(created) as mc from owd_print_queue3 q \n" +
            " group by order_id\n" +
            "having count(*)>1) as dupes on dupes.oid=order_id and created<>mc)";
    private String deleteMovedSql = "delete from owd_print_queue3 where sentToQueue=1";                          
    private String updateSentToQueueRedsSql = "update owd_print_queue3 set sentToQueue = 1 from owd_print_queue3 p, owd_order o where p.order_id=o.order_id and sentToQueue=0 and getdate()>=post_date\n" +
            "                       and isVerified=1  AND fileCreated = 1 AND fileDownloaded = 1 and (printer_name  like '%@%')";

    private String moveToPrinterQueueRedsSql = "insert into owd_print_queue_sl (order_id, client_id, created, printer_name,pdf_binary) SELECT     owd_print_queue3.order_id, owd_print_queue3.client_id, owd_print_queue3.created, owd_print_queue3.printer_name, owd_print_queue3.pdf_binary\n" +
            "                      FROM        owd_print_queue3\n" +
            "                     WHERE     (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.sentToQueue = 1) \n" +
            "                      ORDER BY client_id, owd_print_queue3.created";
    private String updateSentToQueueGroupSql = "update owd_print_queue3 set sentToQueue = 1 from owd_print_queue3 p, owd_order o where p.order_id=o.order_id and sentToQueue=0 and getdate()>=post_date\n" +
            "                      and isVerified=1 AND fileCreated = 1 AND fileDownloaded = 1 and printgroup_id is not null";

    private String moveToPrintQueueGroupSql = "insert into owd_print_queue_sl (order_id, client_id, created, printer_name,pdf_binary) SELECT     owd_print_queue3.order_id, owd_print_queue3.client_id, owd_print_queue3.created, printer_name, owd_print_queue3.pdf_binary\n" +
            "                     FROM        owd_print_queue3\n" +
            "                      WHERE     (owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND (owd_print_queue3.sentToQueue = 1) \n" +
            "                      ORDER BY client_id, printgroup_id, owd_print_queue3.created";

    private String updateSentToQueueSql = "update owd_print_queue3 set sentToQueue=1 from owd_print_queue3 p, owd_order o, owd_order_ship_info oi  where p.order_id=o.order_id and p.order_id = oi.order_fkey and sentToQueue=0 and getdate()>=post_date\n" +
            "            and isVerified=1 AND fileCreated = 1 AND fileDownloaded = 1 and dbo.releasePackSlipToday(sla,getDate()) = 1 and client_id not in (select value from app_data where \n" +
            " project = 'wms' and description = 'packSlips' and variable = 'noAuto') AND carr_service NOT IN\n" +
            "    (\n" +
            "        SELECT\n" +
            "            value\n" +
            "        FROM\n" +
            "            app_data\n" +
            "        WHERE\n" +
            "            project = 'global'\n" +
            "        AND description = 'shipMethod'\n" +
            "        AND VARIABLE = 'reds'\n" +
            "    )";

    private String moveToPrintQueueSql = "insert into  owd_print_queue_sl (order_id, client_id, created, printer_name, pdf_binary) SELECT    owd_print_queue3.order_id, owd_print_queue3.client_id, owd_print_queue3.created, dbo.getSlaPrinterName(order_id,getDate()), owd_print_queue3.pdf_binary\n" +
            "            FROM          owd_print_queue3 INNER JOIN\n" +
            "                                  w_order_attributes ON owd_print_queue3.order_id = w_order_attributes.order_fkey\n" +
            "            WHERE     ( owd_print_queue3.isVerified = 1) AND (owd_print_queue3.fileCreated = 1) AND (owd_print_queue3.fileDownloaded = 1) AND ( owd_print_queue3.sentToQueue = 1) \n" +
            "            ORDER BY  owd_print_queue3.client_id, w_order_attributes.fingerprint";

    public static void main(String[] args) {
        run();
        //log.debug(runAll(Calendar.getInstance().getTime()));
    }

    private void loadTimeMap(Session sess) {
        String sql = "select value, display from app_data where \n" +
                "project = 'wms' and \n" +
                "description = 'packSlip' and \n" +
                "variable = 'AutoRunDay'";
                                                                                           
        try {
            Query q = sess.createSQLQuery(sql);
            List l = q.list();
            for (Object row : l.toArray()) {
                Object[] data = (Object[]) row;
                Map<String, Integer> m = new TreeMap<String, Integer>();
                String[] times = data[1].toString().split("-");
                m.put("startTime", Integer.parseInt(times[0]));
                m.put("endTime", Integer.parseInt(times[1]));
                m.put("timeOne", Integer.parseInt(times[2]));
                m.put("timeTwo", Integer.parseInt(times[3]));
                runtimes.put(data[0].toString(), m);


            }


        } catch (Exception e) {
            e.printStackTrace();
            //todo insert default values
        }


    }

    private boolean runDayHour(Date date) {
        boolean b = false;
        try {
            String today = getDay(date);
            log.debug("Today  " + today);
            if (runtimes.containsKey(today)) {
                log.debug("Got a Good day");
                int startTime = runtimes.get(today).get("startTime");
                int endtime = runtimes.get(today).get("endTime");
                log.debug("Start time " + startTime);
                log.debug("Stop time: " + endtime);
                int hour = Integer.valueOf(getHour(date));
                if (hour >= startTime && hour <= endtime) b = true;


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    private boolean runAll(Date date) {
        boolean ru = false;


        try {
            String today = getDay(date);

            int minutes = Integer.parseInt(getMinutes(date));
            int timeOne = runtimes.get(today).get("timeOne");
            int timeTwo = runtimes.get(today).get("timeTwo");


            if ((minutes >= (timeOne - 2) && minutes <= (timeOne + 2)) || (minutes >= (timeTwo - 2) && minutes <= (timeTwo + 2))) {
                log.debug("minutes good");
                ru = true;

            }

        } catch (Exception e) {

        }
        return ru;
    }

    public void internalExecute() {
        try {
            log.debug("We have the updated packslip release!!!");
            Date date = Calendar.getInstance().getTime();

            Session sess = HibernateSession.currentSession();


            //next two deletes added to clear out print requests invalided through unposting and/or reposting
              Query   q = sess.createSQLQuery(deleteInvalidOrders);
                    q.executeUpdate();

                    HibUtils.commit(sess);
                  q = sess.createSQLQuery(deleteOlderDuplicatePrintJobs);
                    q.executeUpdate();

                    HibUtils.commit(sess);

           /* loadTimeMap(sess);
            for (Map m : runtimes.values()) {
                log.debug(m);
            }
           //check for day and hour
            if (runDayHour(date)) {
                 releaseReds(sess);
               //check for minutes for the rest
                if (runAll(date)) {
                     releaseGroups(sess);

                     releasePackSlips(sess);
                }


            }else{
                log.debug("Not the time to Release!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!XXXXXX");
            }*/
        } catch (Exception e) {
            log.debug("Error releasing slips");
            e.printStackTrace();
            try {
              //  Mailer.sendMail("Error fingerprint", e.getMessage(), "dnickels@owd.com", "dnickels@owd.com");
            } catch (Exception ex) {

            } finally {
                try {
                    HibernateSession.closeSession();

                } catch (Exception se) {

                }
            }

        }
    }

    private void releasePackSlips(Session sess) {
        log.debug("releaseing all packing slips");
        try {
            int rows = 0;
            Query q = sess.createSQLQuery(updateSentToQueueSql);

            rows = q.executeUpdate();

            if (rows > 0) {
                log.debug("It updated " + rows);
                q = sess.createSQLQuery(moveToPrintQueueSql);

                rows = q.executeUpdate();
                if (rows > 0) {
                    log.debug("moved " + rows);

                    q = sess.createSQLQuery(deleteMovedSql);
                    q.executeUpdate();

                    HibUtils.commit(sess);

                } else {
                    HibUtils.rollback(sess);
                }

            } else {
                log.debug("Nothing to release for packing Slips");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releaseGroups(Session sess) {
        log.debug("Releaseing slips with groups id's");
        try {
            int rows = 0;
            Query q2 = sess.createSQLQuery(updateSentToQueueGroupSql);


            rows = q2.executeUpdate();

            if (rows > 0) {
                log.debug("moving groups");
                q2 = sess.createSQLQuery(moveToPrintQueueGroupSql);

                rows = 0;
                rows = q2.executeUpdate();

                if (rows > 0) {
                    q2 = sess.createSQLQuery(deleteMovedSql);
                    q2.executeUpdate();

                    HibUtils.commit(sess);
                }


            } else {
                log.debug("Nothing to print for Groups!!!!!!!!!!!!!!!!!!");
            }
        } catch (Exception e) {
            log.debug("error doing group rlease");
            e.printStackTrace();
        }


    }

    private void releaseReds(Session sess) {
        log.debug("Releasing Reds");
        int rows = 0;
        try {


            Query q2 = sess.createSQLQuery(updateSentToQueueRedsSql);


            rows = q2.executeUpdate();

            if (rows > 0) {
                log.debug("moving RedsXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                q2 = sess.createSQLQuery(moveToPrinterQueueRedsSql);


                rows = 0;
                rows = q2.executeUpdate();

                if (rows > 0) {
                    q2 = sess.createSQLQuery(deleteMovedSql);
                    q2.executeUpdate();

                    HibUtils.commit(sess);
                }


            } else {
                log.debug("Nothing to print for Reds");
            }

        } catch (Exception e) {
            log.debug("Error doing emails and such ");
            e.printStackTrace();
        }
    }

    static String getDay(Date date) throws Exception {
        try {
            SimpleDateFormat df = new SimpleDateFormat("EEEEE");
            return df.format(date);

        } catch (Exception e) {
            throw new Exception("Unparseable Date");

        }
    }

    static String getMinutes(Date date) throws Exception {
        try {
            SimpleDateFormat df = new SimpleDateFormat("m");
            return df.format(date);

        } catch (Exception e) {
            throw new Exception("Unparseable Date");

        }
    }

    static String getHour(Date date) throws Exception {
        try {
            SimpleDateFormat df = new SimpleDateFormat("H");
            return df.format(date);

        } catch (Exception e) {
            throw new Exception("Unparseable Date");

        }
    }
}
