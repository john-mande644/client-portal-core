<%@ page import="com.owd.core.business.Service" %>
  
<%   
  com.owd.core.business.Service service = (com.owd.core.business.Service)request.getAttribute(com.owd.web.servlet.ServiceManager.kCurrentService);
  

%><fontx COLOR=RED><B><%= (String)request.getAttribute("formerror") %></B></fontx><BR>
  <TABLE border=0 width=100% cellpadding=5>
<FORM METHOD=POST ACTION=<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath %>?<%= com.owd.web.servlet.ServiceManager.kParamSvcMgrAction %>=<%=com.owd.web.servlet.ServiceManager.kParamSvcSaveAction%>>
<TR><TD COLSPAN=2 width="100%">
<HR><INPUT TYPE=HIDDEN NAME="<%= Service.kdbServicePKName %>" VALUE="<%= service.service_id %>">
<INPUT TYPE=SUBMIT NAME="<%= com.owd.web.servlet.ServiceManager.kParamSvcMgrSubAction %>" VALUE="<%= (service.isNew()?"Save New Service":"Save Changes") %>">
<HR>
</TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Short Name:&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<INPUT TYPE=TEXT NAME="<%= Service.kdbServiceName %>" VALUE="<%= service.service_name %>" MAXLENGTH=50 SIZE=50><BR>
<fontx SIZE=-2>Enter a short name for this service to be used in service listings and reports.
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Description:&nbsp;</B></TD><TD ALIGN=LEFT><TEXTAREA ROWS=10 COLS=40 Name="<%= Service.kdbServiceDesc %>">
<%= service.description %></TEXTAREA><BR>
<fontx SIZE=-2>Enter a long description of this service. This text will be used when instructions or detail information is displayed. It can be overridden on a per-client basis if special instructions are needed.
</TD>

</TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP><fontx size="-1"><b>
Default&nbsp;Billing&nbsp;Charges:&nbsp;</B></TD><TD ALIGN=LEFT><B>$</B><INPUT TYPE=TEXT NAME="<%= service.kdbServiceCharge %>" VALUE="<%= service.service_charge %>" MAXLENGTH=15 SIZE=12>&nbsp;
<SELECT NAME="<%= service.kdbServiceIsTimed %>" >
<OPTION VALUE="0" <%= (0==(service.is_timed)?"SELECTED":"") %>>Per event
<OPTION VALUE="1" <%= ((1==(service.is_timed) && 0==(service.time_unit))?"SELECTED":"") %>>Per minute
<OPTION VALUE="2" <%= ((1==(service.is_timed) && 1==(service.time_unit))?"SELECTED":"") %>>Per hour
</SELECT><BR>
<fontx SIZE=-2>Enter the default charges (in dollars) for this service, and how it is billed. Per-event services are flat fees. Selecting per-hour or per-minute options will both bill on that basis and alter the prompts presented to users entering services performed to request the appropriate time taken. All these values can be overriden on a per-client basis if necessary.
</TD>

</TR>



</FORM></TABLE>

