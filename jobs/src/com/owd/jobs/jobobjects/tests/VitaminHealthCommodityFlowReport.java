package com.owd.jobs.jobobjects.tests;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;

import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/31/12
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class VitaminHealthCommodityFlowReport {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args)  throws Exception
    {
          sendReport();
    }

    static public Map<Integer,Integer> sampleMap = new TreeMap<Integer, Integer>();
    static{
        sampleMap.put(1,1);
        sampleMap.put(40,2);
        sampleMap.put(80,3);
        sampleMap.put(100,5);
        sampleMap.put(200,10);
        sampleMap.put(400,20);
        sampleMap.put(800,40);
        sampleMap.put(1600,80);
        sampleMap.put(3200,160);
        sampleMap.put(6400,320);
        sampleMap.put(12800,0);

    }
    public static void sendReport()  throws Exception
    {
        StringBuffer sb = new StringBuffer();

        sb.append("Commodity Flow Report\r\nFor 12/16/2012 through 12/22/2012\r\n");
        ResultSet rs = HibernateSession.getResultSet("select count(*) from owd_order where client_fkey=146 and shipped_on>='2012-12-16' and shipped_on<='2012-12-22'");
         int shipments = 0;

        if(rs.next())
        {
           shipments = rs.getInt(1);
          sb.append("\r\nItem D:\t"+rs.getInt(1)+"\r\n");


        }
        rs.close();
        sb.append("\r\nItem E:\tSampling every " + getSampleSize(shipments) + "th shipment\r\n");
        sb.append("\r\nItem F:\r\n");
        sb.append("\r\nA\tB\tC\tD\tE\tF\tG\tH\tI\tJCity\tJState\tJZip\tK\tL\tMCity\tMCountry\tN\r\n");

        float totalValue=0.00f;
        int nextDay = 0;
        int twoday = 0;
        int threeday = 0;

        rs = HibernateSession.getResultSet("select order_refnum,datepart(month,o.shipped_on),datepart(day,o.shipped_on),\n" +
                "                order_sub_total,o.shipped_weight,'<code>','<type>','N','',case when ship_country<>'USA' then 'New York' else ship_city end," +
                "case when ship_country<>'USA' then 'NY' else ship_state end,case when ship_country<>'USA' then '' else  ship_zip end,'1','N',case when ship_country<>'USA' then ship_city else '' end," +
                "case when ship_country<>'USA' then ship_country else '' end,'',carr_service from owd_order o join owd_order_ship_info s \n" +
                "                on order_id=order_fkey where client_fkey=146 and o.shipped_on>='2012-12-16' and o.shipped_on<='2012-12-22'");
        int sample = getSampleSize(shipments);
        int counter=0;
        int sampled = 0;

        while(rs.next())
        {
            counter++;

          if(counter==sample)
          {
              counter=0;
          sb.append(rs.getString(1)+"\t");
            sb.append(rs.getString(2)+"\t");
            sb.append(rs.getString(3)+"\t");
            sb.append(rs.getString(4)+"\t");
            sb.append(rs.getString(5)+"\t");
            sb.append(rs.getString(6)+"\t");
            sb.append(rs.getString(7)+"\t");
            sb.append(rs.getString(8)+"\t");
            sb.append(rs.getString(9)+"\t");
            sb.append(rs.getString(10)+"\t");
            sb.append(rs.getString(11)+"\t");
            sb.append(rs.getString(12)+"\t");
            sb.append(rs.getString(13)+"\t");
            sb.append(rs.getString(14)+"\t");
            sb.append(rs.getString(15)+"\t");
            sb.append(rs.getString(16)+"\r\n");
            totalValue+=rs.getFloat(3);
            String svc = rs.getString(17).toUpperCase();

            if(svc.contains("NEXT DAY"))
            {
                nextDay++;
            }             else if (svc.contains("2 DAY") || svc.contains("EXPRESS") || svc.contains("3 DAY"))
            {
                twoday++;
            }
              sampled++;

          }
        }
        sb.append("\r\nItem G1:\t"+(nextDay+twoday+threeday>0?"YES":"NO"));
        sb.append("\r\nItem G2:\tOvernight="+nextDay+", 2-3 day="+twoday+", More than 3=0\r\n");
        sb.append("\r\nItem H:\t"+ OWDUtilities.roundFloat(totalValue,0)+"\r\n");

        log.debug(sb);
        log.debug("samples:"+sampled);
    }

    public static int getSampleSize(int shipments)
    {
        int sample = 0;

        for(int threshold:sampleMap.keySet())
        {
            log.debug(shipments+":"+threshold);
            if(shipments > threshold)
            {
                sample = sampleMap.get(threshold)  ;
                log.debug("s:"+sample);
            }
        }
        log.debug(sample);
        return sample;
    }
}
