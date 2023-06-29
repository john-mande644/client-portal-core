<%@ page import="com.owd.hibernate.HibernateSession,
                 org.hibernate.Session,
                 java.util.List,
                 org.hibernate.Criteria,
                 org.hibernate.criterion.Expression,
                 com.owd.hibernate.generated.OwdUser,
                 com.owd.web.internal.user.UserHome,
                 com.owd.core.business.Client,
                 com.owd.core.business.client.ClientManager,
                 com.owd.hibernate.generated.OwdClient,
                 com.owd.core.business.client.ClientFactory,
                 com.owd.core.business.user.UserFactory" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%
    String user_client_id = Client.getClientIDForUser(request.getRemoteUser());
    boolean isInternal = false;
    String errormessage = "";
    if (user_client_id == null) user_client_id = "-1";
    if (user_client_id.equals("")) user_client_id = "-1";
    int clientIDInt = new Integer(user_client_id).intValue();

    if (clientIDInt == 0) {
        isInternal = true;
        //is internal
        if (request.getParameter("internalid") != null) {
            clientIDInt = new Integer(request.getParameter("internalid")).intValue();

        }
    }


    Session sess = HibernateSession.currentSession();


    Criteria crit = sess.createCriteria(OwdUser.class);
    crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

    //   crit.add(Restrictions.eq("status", request.getParameter("asnstatus")));


    request.getSession(true).setAttribute(UserHome.kUserSearchStatus, "-1");
    request.getSession(true).setAttribute(UserHome.kUserSearchCompare, "1");

    request.getSession(true).setAttribute(UserHome.kUserSearchString, "");

    request.getSession(true).setAttribute(UserHome.kUserSearchType, "");

    if (null != request.getParameter(UserHome.kUserSearchStatus)) {
        request.getSession(true).setAttribute(UserHome.kUserSearchStatus, request.getParameter(UserHome.kUserSearchStatus));
    }
    if (null != request.getParameter(UserHome.kUserSearchCompare)) {
        request.getSession(true).setAttribute(UserHome.kUserSearchCompare, request.getParameter(UserHome.kUserSearchCompare));
    }
    if (null != request.getParameter(UserHome.kUserSearchString)) {
        request.getSession(true).setAttribute(UserHome.kUserSearchString, request.getParameter(UserHome.kUserSearchString));
    }
    if (null != request.getParameter(UserHome.kUserSearchType)) {
        request.getSession(true).setAttribute(UserHome.kUserSearchType, request.getParameter(UserHome.kUserSearchType));
    }

    int statusSrch = new Integer((String) request.getSession(true).getAttribute(UserHome.kUserSearchStatus)).intValue();
    int compareSrch = new Integer((String) request.getSession(true).getAttribute(UserHome.kUserSearchCompare)).intValue();
    String stringSrch = ((String) request.getSession(true).getAttribute(UserHome.kUserSearchString));
    String typeSrch = (String) request.getSession(true).getAttribute(UserHome.kUserSearchType);

    if (!(statusSrch == -1)) {

        System.out.println("adding active criteria:isActive:" + statusSrch);
        crit.add(Restrictions.eq("isActive", new Boolean(statusSrch == 1)));
    }

    if (stringSrch.trim().length() > 0 && typeSrch.trim().length() > 1) {
        if (compareSrch == 0)//is
        {

            System.out.println("adding is criteria:" + typeSrch + ":" + stringSrch);
            crit.add(Restrictions.eq(typeSrch, stringSrch));


        } else {  //contains

            System.out.println("adding like criteria:" + typeSrch + ":" + stringSrch);
            crit.add(Restrictions.like(typeSrch, "%" + stringSrch + "%"));

        }
    }

    if (clientIDInt != 0) {
        crit.add(Restrictions.eq("clientFkey", new Integer(clientIDInt)));
    }


    List clientlist = null;

    System.out.println("trying list");
    clientlist = crit.list();

    System.out.println("found user list of:" + clientlist.size());
    request.setAttribute("userlist", clientlist);
    UserHome.setSearchSessionAttributes(request);

%>
<FORM METHOD=POST ACTION="./edit"><INPUT TYPE=HIDDEN NAME=act VALUE="client-search">

    <div class="FormBlock" style="padding-top: 5px;">
        Status:<SELECT NAME="<%= UserHome.kUserSearchStatus %>"><OPTION
            VALUE="-1" <%= "-1".equals(request.getSession(true).getAttribute(UserHome.kUserSearchStatus))?"SELECTED":"" %>>
        All
        <OPTION VALUE="1" <%= "1".equals(request.getSession(true).getAttribute(UserHome.kUserSearchStatus))?"SELECTED":"" %>>
            Active
        <OPTION VALUE="0" <%= "0".equals(request.getSession(true).getAttribute(UserHome.kUserSearchStatus))?"SELECTED":"" %>>
            Inactive
    </SELECT>&nbsp;
        Client:<SELECT NAME="internalid"
                       onChange="this.form.submit()"><%= UserHome.getClientSelector("" + clientIDInt, "All Clients", "0") %></SELECT>
        &nbsp;
        Field:<SELECT NAME="<%= UserHome.kUserSearchType %>">
        <OPTION VALUE="" <%= "".equals(request.getSession(true).getAttribute(UserHome.kUserSearchType))?"SELECTED":"" %>>
            Select a field to search
        <OPTION VALUE="name" <%= "name".equals(request.getSession(true).getAttribute(UserHome.kUserSearchType))?"SELECTED":"" %>>
            Name
        <OPTION VALUE="login" <%= "login".equals(request.getSession(true).getAttribute(UserHome.kUserSearchType))?"SELECTED":"" %>>
            Login
        <OPTION VALUE="email" <%= "email".equals(request.getSession(true).getAttribute(UserHome.kUserSearchType))?"SELECTED":"" %>>
            Email


    </SELECT>
        <SELECT NAME="<%= UserHome.kUserSearchCompare %>">
            <OPTION VALUE="1" <%= "1".equals(request.getSession(true).getAttribute(UserHome.kUserSearchCompare))?"SELECTED":"" %>>
                contains
            <OPTION VALUE="0" <%= "0".equals(request.getSession(true).getAttribute(UserHome.kUserSearchCompare))?"SELECTED":"" %>>
                is
        </SELECT>
        <INPUT TYPE=TEXT NAME="<%= UserHome.kUserSearchString%>"
               VALUE="<%= request.getSession(true).getAttribute(UserHome.kUserSearchString)%>">
        <INPUT TYPE=SUBMIT NAME=submit VALUE="Find">
    </div>
</FORM>
<HR>
<display:table cellspacing="0" name="userlist" sort="list" class="its" defaultsort="1" pagesize="10" export="true"
               decorator="com.owd.web.internal.displaytag.UserTableDecorator">
    <display:column property="client" title="Client" sortable="true" group="1"/>
    <display:column property="links" title="Login" sortable="true" sortProperty="login"/>
    <display:column property="name" title="Name" sortable="true"/>
    <display:column property="email" title="Email" autolink="true" sortable="true"/>
    <display:column property="isActive" title="Active?" sortable="true"/>

    <display:setProperty name="export.excel.label" value="Excel"/>
    <display:setProperty name="paging.banner.all_items_found" value=""/>
    <display:setProperty name="paging.banner.onepage" value=""/></display:table>


<%
    HibernateSession.closeSession();
%>

