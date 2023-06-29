package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.displaytag.decorator.TableDecorator;
import com.owd.web.internal.it.StatusAction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 3, 2008
 * Time: 1:37:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ITStatusTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    static DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

    public String getCaseId()
    {
        StatusAction.Case job = (StatusAction.Case) getCurrentRowObject();
        if (job == null) return "";
        if ("".equals(job)) return "";
        return "<A target=\"_currentcase\" HREF=\"http://casetracker.owd.com/default.asp?pgx=EV&ixBug="+job.getCaseId()+"\" >"+job.getCaseId()+"</A>";
    }

    public String getScheduled()
    {
        StatusAction.Case job = (StatusAction.Case) getCurrentRowObject();
        if (job == null) return "";
        if ("".equals(job.getCaseId())) return "";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        if(job.getDue().before(cal.getTime()))
        {
        return "<font color=\"red\">"+df.format(job.getDue())+"</font>";
        }else
        {
            return df.format(job.getDue());
        }
    }

   
    
}
