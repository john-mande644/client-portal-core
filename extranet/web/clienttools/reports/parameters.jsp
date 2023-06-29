     <%@ page import="com.owd.core.business.Client,
     				 com.owd.extranet.servlet.ExtranetServlet,
     				 com.owd.web.reports.ReportDefinition,
     				 com.owd.web.reports.ReportParameter,
     				 com.owd.web.reports.ReportsManager,
     				 java.util.Iterator,
     				 java.util.List,
     				 java.util.Map"%>
     <%@ taglib uri="http://displaytag.sf.net" prefix="display" %><%


                          String internal_client_id = null;

                          String user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
     	if(user_client_id == null) user_client_id = "-1";
     	if(user_client_id.equals("")) user_client_id = "-1";

                          if(!(request.getParameter("internal_client_id")==null))
                          {
                            if("0".equals(user_client_id))
                            {
                                internal_client_id=request.getParameter("internal_client_id");
                            }
                          }
        String errorNote = (String) request.getAttribute("errorNote");

        ReportDefinition rdef = ReportsManager.getReportDefForId(request.getParameter("id"),user_client_id);

     	%>
     <%

         ExtranetServlet.authenticateUser(request);
         request.setAttribute("clientselector","<FORM METHOD=POST ACTION=\""+ request.getContextPath() + ExtranetServlet.kExtranetURLPath +"\">"+ ExtranetServlet.getClientSelector(request) +"</FORM>");
         request.setAttribute("areatitle","Custom Reports");
     %>
     <jsp:include page="/extranettop.jsp" />

     <script language="JavaScript">
     // The following script is used to hide the calendar whenever you click the document.
     document.onmousedown=function(e){
     var n=!e?self.event.srcElement.name:e.target.name;
     if (document.layers) {
     with (gfPop) var l=pageX, t=pageY, r=l+clip.width,b=t+clip.height;
     if (n!="popcal"&&(e.pageX>r||e.pageX<l||e.pageY>b||e.pageY<t)) gfPop.fHideCal();
     return routeEvent(e); // must use return here.
     } else if (n!="popcal") gfPop.fHideCal();
            }
     if (document.layers) document.captureEvents(Event.MOUSEDOWN);
     </script>

     <B>Client Report Downloads</B><BR>
     <A HREF="/webapps/clienttools/reports/index.jsp">Back to Report Listing Page</A>
     <FORM method="POST" name="dateform"  action="reportServlet">
     <P>
<% if (user_client_id.equals("0"))
{
    %>
     Choose a client:&nbsp;<SELECT NAME="internal_client_id"><%= com.owd.web.servlet.ExtranetServlet.getClientSelector(request,"internal_client_id",internal_client_id==null?user_client_id:internal_client_id) %></SELECT>

    <% }else{

    Client client = Client.getClientForUser(request.getUserPrincipal().getName());
    %>

   For: <B><%= client.company_name %></B>

    <%
}   
if(errorNote != null)
    {

    %><P><B><font color=red><%= errorNote %></font></b><%
    }%>
<HR>
Parameters for report: <B><%= rdef.getName() %></B>
<P>

     <TABLE border=0 cellpadding=3>

     <%
         for(int i=0;i<rdef.getParameters().size();i++)
         {
             ReportParameter param = (ReportParameter) rdef.getParameters().get(i);
             switch(param.getParamDataType())
             {
                 case (ReportParameter.kParamTypeDate):

                %><TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP><%= param.getDisplayName() %></TD><TD ALIGN=LEFT VALIGN=TOP><input name="<%= param.getFormValueName() %>" value="<%= request.getParameter(param.getFormValueName())==null?param.getCurrentValue():request.getParameter(param.getFormValueName()) %>" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.<%= param.getFormValueName() %>);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
                 <BR><%= param.getDescription() %></TD></TR><%
                 break;
                     case (ReportParameter.kParamTypeText):

                %><TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP><%= param.getDisplayName() %></TD><TD ALIGN=LEFT VALIGN=TOP><input name="<%= param.getFormValueName() %>" value="<%= request.getParameter(param.getFormValueName())==null?param.getCurrentValue():request.getParameter(param.getFormValueName()) %>" type=text>
                 <BR><%= param.getDescription() %></TD></TR><%
                 break;

                      case (ReportParameter.kParamTypeOptions):

                %><TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP><%= param.getDisplayName() %></TD><TD ALIGN=LEFT VALIGN=TOP>
<select name="<%= param.getFormValueName() %>">
                     <%
                     Map mapper = param.getOptionMap();
                     for (int im=0;im<mapper.size();im++){
            %>
<OPTION value="<%= mapper.keySet().toArray()[im] %>" <%= request.getParameter(param.getFormValueName())==mapper.keySet().toArray()[im]?"SELECTED":""%>><%= mapper.get(mapper.keySet().toArray()[im]) %>

    <%} %></SELECT>
                 <BR><%= param.getDescription() %></TD></TR><%
                 break;

                     case (ReportParameter.kParamTypeCheckbox):

                %><TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP></TD><TD ALIGN=LEFT VALIGN=TOP>
<INPUT TYPE=CHECKBOX name="<%= param.getFormValueName() %>" value="<%= param.getCurrentValue() %>" <%= request.getParameter(param.getFormValueName())==null?"":"CHECKED"%>>&nbsp;<%= param.getDisplayName() %>
<BR><%= param.getDescription() %></TD></TR><%
                 break;

             }

         }
     %>



     <TR><TD COLSPAN=2></TD></TR>
      </TABLE>
      <INPUT TYPE=HIDDEN NAME="action" VALUE="run">
      <INPUT TYPE=HIDDEN NAME="id" VALUE="<%= request.getParameter("id")%>" >
      <INPUT TYPE=SUBMIT NAME="Download" Value="Get Report"><BR>
      Enter parameter values and click to run report. When the report is displayed, scroll to bottom of report page for links to save the report data on your computer.
      <HR>
      <% if (request.getAttribute("reportrs") != null)
      {
           %>
            <display:table name="requestScope.reportrs.rows" sort="list" class="its"  pagesize="10" export="true" requestURI="reportServlet/report.csv">
          <%
              Iterator itcols = ((List) request.getAttribute("reportrs-column-list")).iterator();
              while(itcols.hasNext())
              {   String colName = (String) itcols.next();
                  %><display:column property="<%= colName%>" title="<%= colName %>" sortable="true"/><%
              }
              
              
              
          %>
 <display:setProperty name="export.excel.filename" value="report.xls" />
<display:setProperty name="export.excel.include_header" value="true" />
<display:setProperty name="export.csv.include_header" value="true" />
</display:table>
           
          <%
      }
          %>
     <!--  PopCalendar(tag name and id must match) Tags should sit at the page bottom -->
     <iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js" src="/webapps/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
     <LAYER name="gToday:outlook:agenda.js" src="/webapps/popcal/npopeng.htm">     </LAYER>
     </iframe>
     </FORM><!-- Begin Footer -->
     <HR ALIGN="center" SIZE="5" NOSHADE/>
     <fontx SIZE="1em">
         &nbsp;&nbsp;Copyright 2002-2018,
         <A HREF="http://www.owd.com/">
             One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

         </BODY>
         </HTML>
