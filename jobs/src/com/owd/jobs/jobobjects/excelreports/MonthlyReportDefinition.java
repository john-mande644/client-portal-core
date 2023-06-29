package com.owd.jobs.jobobjects.excelreports;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/11/16.
 */
public class MonthlyReportDefinition extends ReportDefinition {
    private final static Logger log =  LogManager.getLogger();

    public DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public DateFormat dfperiod = new SimpleDateFormat("MMMM yyyy");
    public Date firstDatePastPeriod;
    public Date firstDateOfPreviousMonth;
    public Date lastDateOfPreviousMonth;

    public MonthlyReportDefinition() {

        super();
        Calendar aCalendar = Calendar.getInstance();
        //  aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);
        firstDatePastPeriod = aCalendar.getTime();

        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);
        firstDateOfPreviousMonth = aCalendar.getTime();
        aCalendar.add(Calendar.MONTH, 1);
        aCalendar.set(Calendar.DATE, -1);
        lastDateOfPreviousMonth = aCalendar.getTime();

        log.debug("First Date Previous Month"+dfperiod.format(firstDateOfPreviousMonth)) ;
        log.debug("Last Date Previous Month"+dfperiod.format(lastDateOfPreviousMonth)) ;
        log.debug("First Date Next Period"+dfperiod.format(firstDatePastPeriod)) ;

        periodTitle = dfperiod.format(firstDateOfPreviousMonth);
        subject = "Financial Activity Report "+periodTitle ;
    }

    public MonthlyReportDefinition(Date firstDatePastPeriod, Date firstDateOfPreviousMonth) {

        super();
        this.firstDatePastPeriod = firstDatePastPeriod;
        this.firstDateOfPreviousMonth = firstDateOfPreviousMonth;
    }
}

