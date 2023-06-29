<%@ page import="com.owd.core.managers.ManifestingManager" %>
<%@ page import="com.owd.core.ConnectshipManifest" %>
<%@ page import="com.owd.core.ConnectshipTransmissionFile" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: danny
  Date: 11/26/2018
  Time: 3:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String location = request.getParameter("location");
    Map<String, List<ConnectshipTransmissionFile>> untransmittedManifests = new HashMap<String, List<ConnectshipTransmissionFile>>();
    Map<String, List<ConnectshipManifest>> openManifests = new HashMap<String, List<ConnectshipManifest>>();
     openManifests = ManifestingManager.getConnectshipOpenManifestsForToday(location) ;
    untransmittedManifests =  ManifestingManager.getConnectshipUntransmittedForToday(location) ;
%>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Info about manifests for <%= location%>:</h1>


<div class="latemilestones" style="display:block;">
  <h2>Manifests to Close:</h2>
  <hr>
  <ul>
    <%

      if(openManifests.keySet().size()>0)
      { %>

    <%
      for(String carrier:openManifests.keySet()){
        for(ConnectshipManifest mani:openManifests.get(carrier)){
    %> <li><%= mani.getCarrierName()+" : "+mani.getDateLabel() %></li>
    <%
        }

      }
      }
    %>
  </ul>
</div>
<%

   %>
<div class="latemilestones" style="display:block;">
  <h2>Manifests to Transmit:</h2>
  <hr>
  <ul>

    <%
      if(untransmittedManifests.keySet().size()>0)
      {
      for(String carrier2:untransmittedManifests.keySet()){
        for(ConnectshipTransmissionFile mani:untransmittedManifests.get(carrier2)){
    %> <li><%= carrier2+" : "+mani.getName() %></li>   <%
      }

    }
    }

  %>
  </ul>
</div>

</body>
</html>
