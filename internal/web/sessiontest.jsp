<%@ page import="com.owd.core.web.SessionEventListener,
                 java.util.Collection,
                 java.util.Iterator,
                 java.util.Date,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 java.util.Enumeration" %>
<%
    DateFormat fd = new SimpleDateFormat("yyyy-mm-dd hh:mm");

    Collection sessions = SessionEventListener.getActiveSessions();

    Iterator it = sessions.iterator();
    while (it.hasNext()) {
        HttpSession sess = (HttpSession) it.next();
%><B>Got Session: <%= sess.getId()%></B>
<UL>
    <%
        Enumeration enumm = sess.getAttributeNames();

        while (enumm.hasMoreElements()) {
            String aname = (String) enumm.nextElement();
            System.out.println(aname);
    %><LI>[ATT]:<%= aname %> -- <%= sess.getAttribute(aname)%><%
        }

        %>
    <LI>Last Access Time:<%=fd.format(new Date(sess.getLastAccessedTime()))%>
    <LI>Created Time:<%=fd.format(new Date(sess.getCreationTime()))%>
</UL><%
    }


%>
DONE