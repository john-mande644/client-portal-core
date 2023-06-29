package com.owd.callcenter.apps;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owd.callcenter.forms.ajaxForm;
/*import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.extensions.When;
import com.google.gdata.client.calendar.CalendarService;*/

import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 4, 2006
 * Time: 11:46:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class addSalesCallEventAction extends Action {

    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
           /* CalendarService myService = new CalendarService("Sales");
            myService.setUserCredentials("sales@owd.com","salesowdcom");
            URL postUrl =
  new URL("http://www.google.com/calendar/feeds/sales@owd.com/private/full");
CalendarEventEntry myEntry = new CalendarEventEntry();

myEntry.setTitle(new PlainTextConstruct("Tennis with Beth"));
myEntry.setContent(new PlainTextConstruct("Meet for a quick lesson."));

DateTime startTime = DateTime.parseDateTime("2008-04-17T15:00:00-08:00");
DateTime endTime = DateTime.parseDateTime("2008-04-17T17:00:00-08:00");
When eventTimes = new When();

eventTimes.setStartTime(startTime);
eventTimes.setEndTime(endTime);
myEntry.addTime(eventTimes);



// Send the request and receive the response:
CalendarEventEntry insertedEntry = myService.insert(postUrl, myEntry);*/

            return (mapping.findForward("success"));
        } catch (Exception e) {
            return mapping.findForward("error");

        }
    }

    static public void main(String[] args) throws Exception
    {
          /*CalendarService myService = new CalendarService("Sales");
            myService.setUserCredentials("sales@owd.com","salesowdcom");
            URL postUrl =
  new URL("http://www.google.com/calendar/feeds/sales@owd.com/private/full");
CalendarEventEntry myEntry = new CalendarEventEntry();

myEntry.setTitle(new PlainTextConstruct("Tennis with Beth"));
myEntry.setContent(new PlainTextConstruct("Meet for a quick lesson."));

DateTime startTime = DateTime.parseDateTime("2008-04-17T15:00:00-08:00");
DateTime endTime = DateTime.parseDateTime("2008-04-17T17:00:00-08:00");
When eventTimes = new When();

eventTimes.setStartTime(startTime);
eventTimes.setEndTime(endTime);
myEntry.addTime(eventTimes);

// Send the request and receive the response:
CalendarEventEntry insertedEntry = myService.insert(postUrl, myEntry);*/
    }
}
