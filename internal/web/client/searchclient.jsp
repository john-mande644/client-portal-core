<%@ page import="com.owd.hibernate.HibernateSession,
                 org.hibernate.Session,
                 java.util.List,
                 org.hibernate.Criteria,
                 org.hibernate.criterion.Expression,
                 com.owd.hibernate.generated.OwdClient,
                 com.owd.web.internal.navigation.*,
                 com.owd.web.internal.client.ClientHome" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%

    //if internal user and all clients id is current, return all - else return just for active client id
    // request.setAttribute("asnlist",asnlist);
    Session sess = HibernateSession.currentSession();

    //if internal user and all clients id is current, return all - else return just for active client id

    //  List autoshipl = sess.find("from OwdOrderAuto "+((ClientHome.getSessionFlag(request,ClientHome.kExtranetInternalFlag) && ClientHome.getSessionString(request,ClientHome.kExtranetClientID).equals("0"))?"":(" where client_fkey="+ClientHome.getSessionString(request,ClientHome.kExtranetClientID))));
    //   System.out.println("found autoship list of:"+autoshipl.size());

    Criteria crit = sess.createCriteria(OwdClient.class);
    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

    //   crit.add(Restrictions.eq("status", request.getParameter("asnstatus")));


    request.getSession(true).setAttribute(ClientHome.kClientSearchStatus, "-1");
    request.getSession(true).setAttribute(ClientHome.kClientSearchCompare, "1");

    request.getSession(true).setAttribute(ClientHome.kClientSearchString, "");

    System.out.println("req sess");
    request.getSession(true).setAttribute(ClientHome.kClientSearchType, "");
    System.out.println("req sess");
    if (null != request.getParameter(ClientHome.kClientSearchStatus)) {
        request.getSession(true).setAttribute(ClientHome.kClientSearchStatus, request.getParameter(ClientHome.kClientSearchStatus));
    }
    if (null != request.getParameter(ClientHome.kClientSearchCompare)) {
        request.getSession(true).setAttribute(ClientHome.kClientSearchCompare, request.getParameter(ClientHome.kClientSearchCompare));
    }
    if (null != request.getParameter(ClientHome.kClientSearchString)) {
        request.getSession(true).setAttribute(ClientHome.kClientSearchString, request.getParameter(ClientHome.kClientSearchString));
    }
    if (null != request.getParameter(ClientHome.kClientSearchType)) {
        request.getSession(true).setAttribute(ClientHome.kClientSearchType, request.getParameter(ClientHome.kClientSearchType));
    }

    int statusSrch = new Integer((String) request.getSession(true).getAttribute(ClientHome.kClientSearchStatus)).intValue();
    int compareSrch = new Integer((String) request.getSession(true).getAttribute(ClientHome.kClientSearchCompare)).intValue();
    String stringSrch = ((String) request.getSession(true).getAttribute(ClientHome.kClientSearchString));
    String typeSrch = (String) request.getSession(true).getAttribute(ClientHome.kClientSearchType);

    if (!(statusSrch == -1)) {
        crit.add(Restrictions.eq("isActive", new Boolean(statusSrch == 1)));
    }

    if (stringSrch.trim().length() > 0 && typeSrch.trim().length() > 1) {
        if (compareSrch == 0)//is
        {

            crit.add(Restrictions.eq(typeSrch, stringSrch));


        } else {  //contains

            crit.add(Restrictions.like(typeSrch, "%" + stringSrch + "%"));

        }
    }


    List clientlist = null;

    System.out.println("trying list");
    clientlist = crit.list();

    System.out.println("found client list of:" + clientlist.size());
    request.setAttribute("clientlist", clientlist);
    ClientHome.setSearchSessionAttributes(request);

%>
<%
    UIUtilities.setRequestNavigationElements(UIUtilities.kClientAreaName, "Search Clients", request);

%>
<html>
<head>
    <title><%= request.getAttribute("lf_CURRMANAGER") %></title>
    <jsp:include page="/includes/owdtop.jsp"/>

<P>

<FORM METHOD=POST ACTION="./edit"><INPUT TYPE=HIDDEN NAME=act VALUE="client-search">
    Status:<SELECT NAME="<%= ClientHome.kClientSearchStatus %>"><OPTION
        VALUE="-1" <%= "-1".equals(request.getSession(true).getAttribute(ClientHome.kClientSearchStatus))?"SELECTED":"" %>>
    All
    <OPTION VALUE="1" <%= "1".equals(request.getSession(true).getAttribute(ClientHome.kClientSearchStatus))?"SELECTED":"" %>>
        Active
    <OPTION VALUE="0" <%= "0".equals(request.getSession(true).getAttribute(ClientHome.kClientSearchStatus))?"SELECTED":"" %>>
        Inactive
</SELECT>&nbsp;
    Field:<SELECT NAME="<%= ClientHome.kClientSearchType %>">
    <OPTION VALUE="" <%= "".equals(request.getSession(true).getAttribute(ClientHome.kClientSearchType))?"SELECTED":"" %>>
        Select a field to search
    <OPTION VALUE="companyName" <%= "companyName".equals(request.getSession(true).getAttribute(ClientHome.kClientSearchType))?"SELECTED":"" %>>
        Client Name
    <OPTION VALUE="AmEmail" <%= "AmEmail".equals(request.getSession(true).getAttribute(ClientHome.kClientSearchType))?"SELECTED":"" %>>
        AM Email


</SELECT>
    <SELECT NAME="<%= ClientHome.kClientSearchCompare %>">
        <OPTION VALUE="1" <%= "1".equals(request.getSession(true).getAttribute(ClientHome.kClientSearchCompare))?"SELECTED":"" %>>
            contains
        <OPTION VALUE="0" <%= "0".equals(request.getSession(true).getAttribute(ClientHome.kClientSearchCompare))?"SELECTED":"" %>>
            is
    </SELECT>
    <INPUT TYPE=TEXT NAME="<%= ClientHome.kClientSearchString%>"
           VALUE="<%= request.getSession(true).getAttribute(ClientHome.kClientSearchString)%>">
    <INPUT TYPE=SUBMIT NAME=submit VALUE="Find">
</FORM>
<%

    System.out.println("starting table");
      
    %>
<HR>

    <display:table cellspacing="0" name="clientlist" sort="list" class="its" pagesize="10" export="true"
                   decorator="com.owd.web.internal.displaytag.ClientTableDecorator">
        <display:column property="links" title="Links" media=""/>

        <display:column property="companyName" title="Client" sortable="true"/>
        <display:column property="clientId" title="ID" sortable="true"/>
        <display:column property="createdDate" title="Created" sortable="true"/>
        <display:column property="isActive" title="Active?" sortable="true"/>
        <display:column property="amEmail" title="AM" sortable="true"/>
        <display:column property="shippingBalance" title="Shipping Balance" sortable="true" decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator"/>
        <display:column property="billingBalance" title="Account Balance" sortable="true" decorator="com.owd.web.internal.displaytag.StandardMoneyDecorator"/>

        <display:setProperty name="paging.banner.all_items_found" value=""/>
        <display:setProperty name="paging.banner.onepage" value=""/>
    </display:table>


    <%

    System.out.println("closing session");
        HibernateSession.closeSession();
    %>

    <jsp:include page="/includes/owdbottom.jsp"/>
</BODY>
</HTML>
