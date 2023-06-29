package com.owd.web.internal.it;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibernateFogbugzSession;
import com.owd.hibernate.HibUtils;

import java.util.*;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 2, 2008
 * Time: 2:02:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatusAction extends ActionSupport {
private final static Logger log =  LogManager.getLogger();

     List<Case> cases = new ArrayList<Case>();
     List<Case> futurecases = new ArrayList<Case>();
     List<Case> clientcases = new ArrayList<Case>();
     List<Case> closedcases = new ArrayList<Case>();
    List<WorkingOn> currentCases = new ArrayList<WorkingOn>();

    public List<Case> getClosedcases() {
        return closedcases;
    }

    public void setClosedcases(List<Case> closedcases) {
        this.closedcases = closedcases;
    }

    public List<Case> getClientcases() {
        return clientcases;
    }

    public void setClientcases(List<Case> clientcases) {
        this.clientcases = clientcases;
    }

    public List<Case> getFuturecases() {
        return futurecases;
    }

    public void setFuturecases(List<Case> futurecases) {
        this.futurecases = futurecases;
    }

    public List<WorkingOn> getCurrentCases() {
        return currentCases;
    }

    public void setCurrentCases(List<WorkingOn> currentCases) {
        this.currentCases = currentCases;
    }

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    public String status()
        {

            try
            {
          ResultSet rs = HibernateFogbugzSession.getResultSet("select ixBug,'('+CASE WHEN CHARINDEX(' ',sFullName,1)>=1 THEN UPPER(LEFT(sFullName,1))+substring(UPPER(sFullName),CHARINDEX(' ',sFullName)+1,1) ELSE sFullName END+') '+sTitle,ISNULL(dtDue,getdate()) from bug b join person p on p.ixPerson=ixPersonAssignedTo\n" +
                  " where fOpen=1 and p.sEmail in ('owditadmin@owd.com','dnickels@owd.com','rhoisington@owd.com','bfulkerson@owd.com')\n" +
                  " and b.ixCategory<>5 and b.ixProject<>212 and b.ixPriority>0 and convert(datetime,convert(varchar,ISNULL(dtDue,getdate()),101))=convert(datetime,convert(varchar,getdate(),101))");
            while(rs.next())
            {
                cases.add(new Case(rs.getString(1),rs.getString(2),rs.getDate(3)));
            }

                HibUtils.rollback(HibernateFogbugzSession.currentSession());


            rs = HibernateFogbugzSession.getResultSet("select '('+CASE WHEN CHARINDEX(' ',sFullName,1)>=1 THEN UPPER(LEFT(sFullName,1))+substring(UPPER(sFullName),CHARINDEX(' ',sFullName)+1,1) ELSE sFullName END+') '+p.sFullname,ISNULL(t.ixBug,0),ISNULL(sTitle,''),dtStart from person p \n" +
                    "                    left outer join timeinterval t on t.ixBug=p.ixBugWorkingOn and t.dtEnd IS NULL left outer join bug b \n" +
                    "                    on p.ixBugWorkingOn=b.ixBug\n" +
                    "                     where p.sEmail in ('owditadmin@owd.com','dnickels@owd.com','rhoisington@owd.com','bfulkerson@owd.com')");
            while(rs.next())
            {
                WorkingOn wo = new WorkingOn();
                wo.setPerson(rs.getString(1));
                wo.setAssignment(rs.getString(2).equals("0")?"Not focused on a specific case":"<A target=\"_casedetail\" HREF=\"http://casetracker.owd.com/default.asp?pgx=EV&ixBug="+rs.getString(2)+"\" >("+rs.getString(2)+") "+rs.getString(3)+"</A>");
                wo.setStarted(subtractFiveHoursFromDate(rs.getTimestamp(4)));
                currentCases.add(wo);
            }

            HibUtils.rollback(HibernateFogbugzSession.currentSession());

                rs = HibernateFogbugzSession.getResultSet("select ixBug,'('+CASE WHEN CHARINDEX(' ',sFullName,1)>=1 THEN UPPER(LEFT(sFullName,1))+substring(UPPER(sFullName),CHARINDEX(' ',sFullName)+1,1) ELSE sFullName END+') '+sTitle,ISNULL(dtDue,getdate()) from bug b join person p on p.ixPerson=ixPersonAssignedTo\n" +
                                  " where fOpen=1 and p.sEmail in ('owditadmin@owd.com','dnickels@owd.com','rhoisington@owd.com','bfulkerson@owd.com')\n" +
                                  " and b.ixCategory<>5 and b.ixProject<>212 and b.ixPriority>0 and convert(datetime,convert(varchar,ISNULL(dtDue,getdate()),101))<>convert(datetime,convert(varchar,getdate(),101)) order by ISNULL(dtDue,getdate()) asc");
                         while(rs.next())
                {
                     futurecases.add(new Case(rs.getString(1),rs.getString(2),rs.getDate(3)));
                }

                HibUtils.rollback(HibernateFogbugzSession.currentSession());

                rs = HibernateFogbugzSession.getResultSet("select ixBug,'('+CASE WHEN CHARINDEX(' ',sFullName,1)>=1 THEN UPPER(LEFT(sFullName,1))+substring(UPPER(sFullName),CHARINDEX(' ',sFullName)+1,1) ELSE sFullName END+') '+sTitle,ISNULL(dtDue,getdate()),g.sname as client from bug b \n" +
                        " join project pro join [groups] g on g.ixgroup=pro.ixgroup and g.ixGroup>1\n" +
                        " on b.ixProject=pro.ixProject \n" +
                        " join person p on p.ixPerson=ixPersonAssignedTo\n" +
                        "                                   where fOpen=1 and b.ixCategory<>5 and b.ixProject<>212  and b.ixPriority>0 and p.sEmail in ('owditadmin@owd.com','dnickels@owd.com','rhoisington@owd.com','bfulkerson@owd.com')\n" +
                        "                                   order by client,ISNULL(dtDue,getdate()) asc");
                         while(rs.next())
                {
                     clientcases.add(new Case(rs.getString(1),rs.getString(2), subtractFiveHoursFromDate(rs.getTimestamp(3)),rs.getString(4)));
                }


                HibUtils.rollback(HibernateFogbugzSession.currentSession());


                rs = HibernateFogbugzSession.getResultSet("select ixBug,'('+CASE WHEN CHARINDEX(' ',sFullName,1)>=1 THEN \n" +
                        " UPPER(LEFT(sFullName,1))+substring(UPPER(sFullName),CHARINDEX(' ',sFullName)+1,1) ELSE sFullName END+') '+sTitle,convert(datetime,convert(varchar,ISNULL(dtResolved,getdate()),101)),g.sname as client from bug b \n" +
                        "                         join project pro join [groups] g on g.ixgroup=pro.ixgroup \n" +
                        "                         on b.ixProject=pro.ixProject \n" +
                        "                         join person p on p.ixPerson=ixPersonResolvedBy\n" +
                        " where \n" +
                        " ixStatus<>1 and ixstatus<>15 and b.ixProject<>212 and datediff(day,dtResolved,getdate())<8 and b.ixCategory<>5 and b.ixPriority>0 and p.sEmail in ('owditadmin@owd.com','dnickels@owd.com','rhoisington@owd.com','bfulkerson@owd.com')\n" +
                        " order by ISNULL(dtResolved,getdate()) desc, client asc");

                while(rs.next())
                              {
                                   closedcases.add(new Case(rs.getString(1),rs.getString(2), rs.getDate(3),rs.getString(4)));
                              }


                              HibUtils.rollback(HibernateFogbugzSession.currentSession());




            HibernateFogbugzSession.closeSession();

             }catch(Exception ex)
            {
                ex.printStackTrace();
            }
          return "status";
        }

    Date subtractFiveHoursFromDate(Date theDate)
    {
        if (theDate==null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(theDate);
        cal.add(Calendar.HOUR, -5);
        return cal.getTime();

    }
    public class WorkingOn
    {
        String person;
        String assignment;
        Date started;

        public Date getStarted() {
            return started;
        }

        public void setStarted(Date started) {
            this.started = started;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }

        public String getAssignment() {
            return assignment;
        }

        public void setAssignment(String assignment) {
            this.assignment = assignment;
        }

        public String toString() {
            return "WorkingOn{" +
                    "person='" + person + '\'' +
                    ", assignment='" + assignment + '\'' +
                    ", started=" + started +
                    '}';
        }
    }
    public class Case
    {
        String caseId;
        String title;
        Date due;
        String client;

        public Case(String caseId, String title, Date due, String client) {
            this.caseId = caseId;
            this.title = title;
            this.due = due;
            this.client = client;
        }

        public Case(String caseId, String title,  Date due) {
            this.caseId = caseId;
            this.title = title;
            this.due = due;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

         
        public Date getDue() {
            return due;
        }

        public void setDue(Date due) {
            this.due = due;
        }

        public String toString() {
            return "Case{" +
                    "caseId='" + caseId + '\'' +
                    ", title='" + title + '\'' +
                    ", due=" + due +
                    ", client='" + client + '\'' +
                    '}';
        }
    }
}
