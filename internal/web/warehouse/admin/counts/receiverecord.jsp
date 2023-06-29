<%@ page import="java.util.List,
                 com.owd.hibernate.HibernateSession,
                 com.owd.hibernate.generated.OwdReceive,
                 java.util.Iterator"%>
<%@ page import="com.owd.hibernate.generated.OwdReceiveItem" %>
<%@ page import="java.util.Collection" %>
<%--
 Created by IntelliJ IDEA.
 User: stewartbuskirk
 Date: Feb 27, 2006
 Time: 8:44:42 AM
 To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%


    List rcvList = HibernateSession.currentSession().createQuery("from OwdReceive rcv where rcv.receiveId="+request.getParameter("rcvid")).list();
    if(rcvList.size()==1)
    {
        OwdReceive rcv = (OwdReceive) rcvList.get(0);

        Collection<OwdReceiveItem> itemList = rcv.getOwdReceiveItems();

            request.setAttribute("itemlist", itemList);
    }
%>
<HTML>
<HEAD>
    <TITLE><%= request.getAttribute("lf_CURRMANAGER") %></TITLE>
    <jsp:include page="/includes/owdtop.jsp"/>

<div id="pagecommands" style="padding-top: 8px;">
    <h3>

    </h3>
</div>
<style>
    span.pagelinks {
        border: none;
    }
</style>
<font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
</font>
<%

    try {

%>

<TABLE border=0><TR><TD width="33%" valign="top">
    Count Request Status
    <HR>
     <display:table id="table1" cellspacing="0" name="itemlist" sort="list" class="its" pagesize="10"
                      export="false" >





          <display:column property="inventoryNum" title="InventoryNum" sortable="true"/>
          <display:column property="createdBy" title="CreatedBy" sortable="true"/>
          <display:column property="description" title="Description" sortable="true"/>
          <display:column property="quantity" title="Quantity" sortable="true"/>

           <display:setProperty name="basic.empty.showtable" value="true"/>
           <display:setProperty name="paging.banner.placement" value="bottom"/>
           <display:setProperty name="paging.banner.page.selected" value=""/>
           <display:setProperty name="paging.banner.all_items_found" value=""/>
           <display:setProperty name="paging.banner.no_items_found" value=""/>
           <display:setProperty name="paging.banner.one_item_found" value=""/>
       </display:table>


 </TD></TR></TABLE>


 <P>
     <jsp:include page="/includes/owdbottom.jsp"/>
 </BODY></HTML>

 <% } catch (Exception ex) {
     ex.printStackTrace();
 } finally {
     HibernateSession.closeSession();
 }%>
