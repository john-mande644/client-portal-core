<%@ page import="org.quartz.impl.DirectSchedulerFactory"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.quartz.Scheduler"%>
<%@ page import="java.util.List"%>
<%@ page import="org.quartz.JobDetail"%>
<%@ page import="org.quartz.JobExecutionContext"%>
<%@ page import="com.owd.jobs.jobobjects.utilities.SystemThreadList" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.quartz.Trigger" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ page import="com.owd.jobs.OWDStatefulJob" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<HEAD>
    </HEAD>
<body>
<%
     try
    {
%>
Schedulers:<%= DirectSchedulerFactory.getInstance().getAllSchedulers().size()%>
<%

    Iterator it =  DirectSchedulerFactory.getInstance().getAllSchedulers().iterator();
    while(it.hasNext())
    {
        Scheduler s = (Scheduler) it.next();

    List currentJobs = s.getCurrentlyExecutingJobs();
        %>Current Jobs:<table><th>Name</th><th>Class</th><th>Fired At</th><%
    for (int cj = 0; cj < currentJobs.toArray().length; cj++) {
        JobExecutionContext job = (JobExecutionContext) currentJobs.get(cj);

        Date lastFireDate = job.getTrigger().getPreviousFireTime();
        Date nextFireDate = job.getTrigger().getPreviousFireTime();

%><tr><td><b><%= job.getJobDetail().getFullName() %></b></td>
    <td><%= job.getJobDetail().getJobClass() %></td><td><br>&nbsp;&nbsp;<%= job.getFireTime()%></td></tr><%

         }
        String[] groupNames = s.getJobGroupNames();
    %></table><hr>Current Jobs:<BR><table><tr><th>Name</th><th>Class</th><th>Last Fire</th><th>Next Fire</th></tr><%
    for (int g = 0; g < groupNames.length; g++
            ) {
        String[] jobNames = s.getJobNames(groupNames[g]);
        for (int j = 0; j < jobNames.length; j++) {
            Trigger[] trigger = s.getTriggersOfJob(jobNames[j], groupNames[g]);

            JobDetail job = s.getJobDetail(jobNames[j], groupNames[g]);

%><tr><td><%= job.getFullName() %></td><td><%= job.getJobClass() %></td>
    <td><%= trigger[0].getPreviousFireTime()%></td><td><%= trigger[0].getNextFireTime()%></td>

    </tr><%

         }

     }
        %></table><%

    }
    %><hr><h2>Thread List</h2><%
        SystemThreadList stl = new SystemThreadList();
        Thread[] allThreads = stl.getAllThreads();

        StringBuffer sb = new StringBuffer(stl.toString());
        sb.append("\n");

        // Individual thread info
        Thread t = null;
        for (int i = 0; i < allThreads.length; i++) {
            t = allThreads[i];
             %><%= "Thread " + i + " = " + t.toString() %><BR><%
        }



%><hr><h2>Job Status in owd_jobs</h2><%
    ResultSet rs = HibernateSession.getResultSet("select name,datediff(minute,max(event_time),getdate()) 'Minutes Since Last Start' from owd_jobs\n" +
"where event_type='START'\n" +
"group by name\n" +
"order by datediff(minute,max(event_time),getdate()) desc");

        %><table><tr><th>Name</th><th>Minutes Since Last Start</th></tr><%
    while (rs.next())
    {
       %><tr><td><%= rs.getString(1) %></td><td><%= rs.getString(2)%></td></tr><%
        }
        %></table><%
    } catch (Exception ex) {
        ex.printStackTrace();
    }finally
    {
        HibernateSession.closeSession();
    }
%>
</body>
</html>