package com.owd.web.callcenter.admin;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.ConnectionManager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 12, 2003
 * Time: 9:06:45 AM
 * To change this template use Options | File Templates.
 */
public class CallCenterStatus {
private final static Logger log =  LogManager.getLogger();

  

    public static void setCreationTime(long creationTime) {
        CallCenterStatus.creationTime = creationTime;
    }

    protected static long creationTime = 0;
    public static Object lockObject = new Object();


    public static JFreeChart oldChart = null;

    static private CallCenterStatus oldStatus = null;

    public Date getReferenceDate() {
        return referenceDate;
    }

    public static long getCreationTime() {
        //	synchronized(oldStatus)
        //	{
        return creationTime;
        //	}
    }

    public static CallCenterStatus getOldStatus() {

        //	synchronized(oldStatus)
        //	{
        return oldStatus;
        //	}
    }


    Date referenceDate = new Date();
    static String getCurrentDateTimeSQL = "select getdate()";

    public static JFreeChart getOldChart() {
        return oldChart;
    }

    public static void setOldChart(JFreeChart oldChart2) {
        oldChart = oldChart2;
    }

    public static void setOldStatus(CallCenterStatus oldStatus) {
        CallCenterStatus.oldStatus = oldStatus;
    }


    public CallCenterStatus() {
        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;


        TimeSeries series = new TimeSeries("Calls Received");

        try {


            log.debug("CCS chart creating");


            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement("select  datepart(hour,contact_initiated),datepart(day,contact_initiated),datepart(month,contact_initiated),datepart(year,contact_initiated),count(*) as 'Calls' from cc_cl_contacts\n" +
                    "                    where contact_type like 'VOICE%' and datediff(hour,contact_initiated,getdate()) < 49  \n" +
                    "                    group by datepart(hour,contact_initiated),datepart(day,contact_initiated),datepart(month,contact_initiated),datepart(year,contact_initiated)");

            stmt.executeQuery();
            rs = stmt.getResultSet();

            while (rs.next()) {

                series.add(new Hour(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)), rs.getDouble(5));


            }
            TimeSeriesCollection xyDataset = new TimeSeriesCollection(series);

            JFreeChart chart = ChartFactory.createXYBarChart("48-Hour Call History", // Title
                    "Hourly Period", // X-Axis label
                    true, //x-axis is dates?
                    "Calls Received", // Y-Axis label
                    xyDataset, // Dataset
                    PlotOrientation.VERTICAL, //Plot orientation
                    false, // Show legend
                    true, //boolean - tooltips?
                    false      //boolean -   gen URLs?
            );

            chart.setAntiAlias(true);
            chart.setBackgroundPaint(Color.white);
            chart.getPlot().setBackgroundPaint(Color.white);


            //    XYPlot plot = (XYPlot) chart.getPlot(); XYAnnotation annotation = new XYTextAnnotation("Now is to the right ->", 599.0, 25.0); plot.addAnnotation(annotation);

            // DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
            // axis.setDateFormatOverride(new SimpleDateFormat("dd MMM hh"));
            DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
            axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM ha"));
            axis.setInverted(true);
            log.debug("CCS chart created");
            setOldChart(chart);


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }

            try {
                cxn.close();
            } catch (Exception e) {
            }
        }


    }


}
