package com.owd.jobs.jobobjects.excelreports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stewartbuskirk1 on 2/11/16.
 */
public class WeeklyReportDefinition extends ReportDefinition {

    public DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public DateFormat dfperiod = new SimpleDateFormat("yyyy-MM-dd");
    public Date firstDatePastPeriod;
    public Date firstDateOfPeriod;

    public WeeklyReportDefinition() {

        super();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR ,0) ;
        today.set(Calendar.HOUR_OF_DAY ,0)  ;
        today.set(Calendar.MINUTE ,0)  ;
        today.set(Calendar.SECOND ,0)    ;
        today.set(Calendar.MILLISECOND ,0)    ;

        while(today.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY)
        {
            today.add(Calendar.DATE, -1);
        }
        today.add(Calendar.DATE, 1);
        firstDatePastPeriod = today.getTime();
        today.add(Calendar.DATE, -1);
        while(today.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
        {
            today.add(Calendar.DATE, -1);
        }
        firstDateOfPeriod = today.getTime();



        periodTitle = "Week starting "+dfperiod.format(firstDateOfPeriod);
        subject = "Financial Activity Report "+periodTitle ;
    }

    public WeeklyReportDefinition(Date firstDatePastPeriod, Date firstDateOfPreviousMonth) {

        super();
        this.firstDatePastPeriod = firstDatePastPeriod;
        this.firstDateOfPeriod = firstDateOfPreviousMonth;
    }
}

