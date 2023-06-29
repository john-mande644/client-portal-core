<%@ page import="com.owd.web.warehouse.asn.ASNHome,
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
<jsp:include page="autoheader.jsp" flush="true"/>
<P>&nbsp;<BR><B>Subscription Status Summary for <%= todayDateString%></B>
<UL>

</UL>
<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

    </BODY>
</HTML>
