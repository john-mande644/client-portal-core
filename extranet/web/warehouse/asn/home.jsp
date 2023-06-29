<%@ page import="com.owd.hibernate.HibernateSession,
                 com.owd.web.warehouse.asn.ASNHome,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 java.util.Calendar"%>
 <%

     DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
     Calendar today = Calendar.getInstance();
     String todayDateString = df.format(today.getTime());
     today.add(Calendar.DATE,-7);
     String sevenDayDateString = df.format(today.getTime());
     today.add(Calendar.DATE,-23);
     String thirtyDayDateString = df.format(today.getTime());

       String cid = ASNHome.getSessionString(request, ASNHome.kExtranetClientID);
String clientLimit = " and client_fkey="+cid;
     if("0".equals(cid))
     {
        clientLimit = "";
     }


 %>

<HTML>
<jsp:include page="asnheader.jsp" flush="true"/>
<P>&nbsp;<BR><B>ASN Status Summary for <%= todayDateString%></B>
<UL>
<LI>Pending ASNs:  <%=   HibernateSession.currentSession().createQuery("select count(*) from Asn where status=0"+clientLimit).iterate().next().toString() %>
<LI>Received Today:    <%=   HibernateSession.currentSession().createQuery("select count(*) from Asn where  (status=2 or status=1) and last_receive_date is not null and last_receive_date>=\'"+todayDateString+"\'"+clientLimit).iterate().next().toString() %>
<LI>Received This Week:   <%=   HibernateSession.currentSession().createQuery("select count(*) from Asn where  (status=2 or status=1) and last_receive_date is not null and  last_receive_date>=\'"+sevenDayDateString+"\'"+clientLimit).iterate().next().toString() %>
<LI>Pending ASNs Over 30 Days Old: <%=   HibernateSession.currentSession().createQuery("select count(*) from Asn where status=0 and created_date<=\'"+thirtyDayDateString+"\'"+clientLimit).iterate().next().toString() %>
</UL>
<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;
    <script src="//rum-static.pingdom.net/pa-5b369fc56a549f00160000bc.js" async></script>
    </BODY>
</HTML>
