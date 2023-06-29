<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <jsp:include page="/rfapps/includes/androidResponsive.jsp"/>



</head>
<body bgcolor=#ffffff >
<div class="body">
<div class="small-8 columns">
<div class="row">
<H3>OWD Warehouse RF Home</H3>
<HR><center>
<c:if test='${outcome != null}'>
<b class="outcome"><c:out value='${outcome}'/></b>
</c:if>
<H3><c:out value='${loginName}'/> in <c:out value='${owd_current_location}'/></H3>

    <table class="menu"><tr><td>
<html:link action="pick" styleClass="button"><bean:message key="button.pickorder"/></html:link>  </td><td>
<html:link action="find" styleClass="button"><bean:message key="button.finditem"/></html:link>  </td></tr>
 <tr><td>
<html:link action="loc-menu" styleClass="button"><bean:message key="button.locations"/></html:link>
 </td><td>
<html:link action="inv-menu" styleClass="button"><bean:message key="button.inventory"/></html:link>
     </td></tr><tr><td>
<html:link action="recv-menu" styleClass="button"><bean:message key="button.receiving"/></html:link>
    </td><td>
            <a href="/wms/locationManagement/orderMenu.action" class="button">Tote Utility</a>
        </td></tr>

            <tr><td><a href="http://warehouse.owd.com/tcid/${tc_id}/warehouse/${owd_current_location}/batchPicking/pick" class="button">Batch Picking</a></td><td>
<a href="/wms/miscMenu/start.action" class="button">MISC</a>
 <!--   <html:link action="batchPack" styleClass="button">PACK</html:link><P class="button"> -->
    </td></tr></table>
</center>
</div>
</div>
<P><HR class="foot"><br>
<table class="fullWidthTable"><tr>
<td><html:link action="logout" styleClass="button"><bean:message key="link.logout"/></html:link></td>
<td align="right"><html:link action="buttonsetup" styleClass="button">Setup</html:link>  </td>
</tr>
    </table>
    <ul class="accordion" data-accordion>
      <li class="accordion-navigation">
      <a href="#panel1a">Other Suff</a>
      <div id="panel1a" class="content">
<HR class="foot">
<A HREF="http://danny.owd.com/wms/" class="button">Testing Version</A>
<A HREF="http://it.owd.com/wms/" class="button">Production Version</A>


    <a href="http://whatsmy.browsersize.com/" class="button">size</a>
        <br>
        <a href="http://bjango.com/articles/min-device-pixel-ratio/" class="button">Ratio</a>
   </a>

<P>  <p>
 <%= request.getServerName() %>
    <UL>
    <LI>Served From Server:   <b><%= request.getServerName() %></b></LI>
    <LI>Server Port Number:   <b><%= request.getServerPort() %></b></LI>
    <LI>IP Address:           <b><%= request.getRemoteAddr() %></b>  </LI>
    <LI>Session ID:    <b><%= session.getId() %></b></LI>
    <LI>Session Created:  <%= new java.util.Date(session.getCreationTime())%></LI>
    <LI>Last Accessed:    <%= new java.util.Date(session.getLastAccessedTime())%></LI>
    <LI>Session will go inactive in  <b><%= session.getMaxInactiveInterval() %> seconds</b></LI>
</UL>
      </div>
      </li>
    </ul>
</div>

    </body>
</html>
