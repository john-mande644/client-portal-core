<%@ page import="com.owd.hibernate.HibernateSession,
                 com.owd.hibernate.generated.OwdOrderAuto,
                 com.owd.web.autoship.AutoShipHome,
                 org.hibernate.Criteria,
                 org.hibernate.Session,
                 org.hibernate.criterion.Expression,
                 java.util.List"%><%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%

    //if internal user and all clients id is current, return all - else return just for active client id
   // request.setAttribute("asnlist",asnlist);
    Session sess = HibernateSession.currentSession();

    //if internal user and all clients id is current, return all - else return just for active client id

              //  List autoshipl = sess.find("from OwdOrderAuto "+((AutoShipHome.getSessionFlag(request,AutoShipHome.kExtranetInternalFlag) && AutoShipHome.getSessionString(request,AutoShipHome.kExtranetClientID).equals("0"))?"":(" where client_fkey="+AutoShipHome.getSessionString(request,AutoShipHome.kExtranetClientID))));
             //   System.out.println("found autoship list of:"+autoshipl.size());

                Criteria crit = sess.createCriteria(OwdOrderAuto.class);
                crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

             //   crit.add(Expression.eq("status", request.getParameter("asnstatus")));
                if(((AutoShipHome.getSessionFlag(request,AutoShipHome.kExtranetInternalFlag) && AutoShipHome.getSessionString(request,AutoShipHome.kExtranetClientID).equals("0"))))
                {
                }else
                {
                    crit.add(Expression.eq("clientFkey",new Integer((String)(AutoShipHome.getSessionString(request,AutoShipHome.kExtranetClientID)))));
                }

                request.getSession(true).setAttribute(AutoShipHome.kAutoSearchStatus,"-1");
                request.getSession(true).setAttribute(AutoShipHome.kAutoSearchCompare,"0");

                request.getSession(true).setAttribute(AutoShipHome.kAutoSearchString,"");

                request.getSession(true).setAttribute(AutoShipHome.kAutoSearchType,"");

                if(null != request.getParameter(AutoShipHome.kAutoSearchStatus))
                {
                   request.getSession(true).setAttribute(AutoShipHome.kAutoSearchStatus,request.getParameter(AutoShipHome.kAutoSearchStatus));
                }
                if(null != request.getParameter(AutoShipHome.kAutoSearchCompare))
                {
                   request.getSession(true).setAttribute(AutoShipHome.kAutoSearchCompare,request.getParameter(AutoShipHome.kAutoSearchCompare));
                }
                if(null != request.getParameter(AutoShipHome.kAutoSearchString))
                {
                   request.getSession(true).setAttribute(AutoShipHome.kAutoSearchString,request.getParameter(AutoShipHome.kAutoSearchString));
                }
                if(null != request.getParameter(AutoShipHome.kAutoSearchType))
                {
                   request.getSession(true).setAttribute(AutoShipHome.kAutoSearchType,request.getParameter(AutoShipHome.kAutoSearchType));
                }

                int statusSrch = new Integer((String)request.getSession(true).getAttribute(AutoShipHome.kAutoSearchStatus)).intValue();
                int compareSrch = new Integer((String)request.getSession(true).getAttribute(AutoShipHome.kAutoSearchCompare)).intValue();
                String stringSrch = ((String)request.getSession(true).getAttribute(AutoShipHome.kAutoSearchString));
                String typeSrch = (String)request.getSession(true).getAttribute(AutoShipHome.kAutoSearchType);

                 if(!("-1".equals((String)request.getSession(true).getAttribute(AutoShipHome.kAutoSearchStatus))))
                 {
                     crit.add(Expression.eq("status", new Integer(statusSrch)));
                 }else  if(stringSrch==null || ("".equals(stringSrch)))
                 {

                     crit.add(Expression.eq("status", 0));
                     request.getSession(true).setAttribute(AutoShipHome.kAutoSearchStatus,"0");    //default to active

                 }

                if(stringSrch.trim().length()>0 && typeSrch.trim().length()>1)
                {
                    if(compareSrch == 1)//is
                    {
                        if(typeSrch.indexOf("owdOrderAutoItems.")>= 0)
                        {
                            crit.createCriteria("owdOrderAutoItems").add(Expression.eq(typeSrch.substring(typeSrch.indexOf("owdOrderAutoItems.")+18), stringSrch));
                        } else if(typeSrch.indexOf("owdOrderAutoHistories.")>= 0)
                        {
                            crit.createCriteria("owdOrderAutoHistories").add(Expression.eq(typeSrch.substring(typeSrch.indexOf("owdOrderAutoHistories.")+22), stringSrch));
                        } else if(typeSrch.indexOf("sourceOrders.")>= 0)
                        {
                            crit.createCriteria("sourceOrders").add(Expression.eq(typeSrch.substring(typeSrch.indexOf("sourceOrders.")+13), stringSrch));
                        }  else
                        {
                            crit.add(Expression.eq(typeSrch, stringSrch));
                        }

                    }   else
                    {  //contains
                         if(typeSrch.indexOf("owdOrderAutoItems.")>= 0)
                        {
                            crit.createCriteria("owdOrderAutoItems").add(Expression.like(typeSrch.substring(typeSrch.indexOf("owdOrderAutoItems.")+18), "%"+stringSrch+"%"));
                        } else if(typeSrch.indexOf("owdOrderAutoHistories.")>= 0)
                        {
                            crit.createCriteria("owdOrderAutoHistories").add(Expression.like(typeSrch.substring(typeSrch.indexOf("owdOrderAutoHistories.")+22), "%"+stringSrch+"%"));
                        } else if(typeSrch.indexOf("sourceOrders.")>= 0)
                        {
                            crit.createCriteria("sourceOrders").add(Expression.like(typeSrch.substring(typeSrch.indexOf("sourceOrders.")+13), "%"+stringSrch+"%"));
                        }  else
                        {
                            crit.add(Expression.like(typeSrch,"%"+stringSrch+"%"));
                        }
                    }
                }


                List autoshiplist = null;

                System.out.println("trying list");
                autoshiplist = crit.list();

    System.out.println("found asn list of:"+autoshiplist.size());
    request.setAttribute("autoshiplist",autoshiplist);
    AutoShipHome.setSearchSessionAttributes(request);

%>
<HTML>
<jsp:include page="autoheader.jsp" flush="true"/>
<FORM METHOD=POST ACTION="./edit"><INPUT TYPE=HIDDEN NAME=act VALUE="auto-search">
Status:<SELECT NAME="<%= AutoShipHome.kAutoSearchStatus %>"><OPTION VALUE="-1" <%= "-1".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchStatus))?"SELECTED":"" %>>All
<OPTION VALUE="0" <%= "0".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchStatus))?"SELECTED":"" %>>Active
<OPTION VALUE="1" <%= "1".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchStatus))?"SELECTED":"" %>>Suspended
<OPTION VALUE="2" <%= "2".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchStatus))?"SELECTED":"" %>>Cancelled
<OPTION VALUE="3" <%= "3".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchStatus))?"SELECTED":""%>>Complete
</SELECT>&nbsp;
Field:<SELECT NAME="<%= AutoShipHome.kAutoSearchType %>">
        <OPTION VALUE="billName" <%= "billName".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchType))?"SELECTED":"" %>>Customer Name
<OPTION VALUE="owdOrderAutoItems.sku" <%= "owdOrderAutoItems.sku".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchType))?"SELECTED":"" %>>Item SKU
<OPTION VALUE="billZip" <%= "billZip".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchType))?"SELECTED":"" %>>Customer Zip Code
    <OPTION VALUE="billPhone" <%= "billPhone".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchType))?"SELECTED":"" %>>Customer Phone Number

    <OPTION VALUE="sourceOrders.orderNum" <%= "sourceOrders.orderNum".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchType))?"SELECTED":"" %>>Source Order OWD Ref
    <OPTION VALUE="sourceOrders.orderRefnum" <%= "sourceOrders.orderRefnum".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchType))?"SELECTED":"" %>>Source Order Client Ref
    <OPTION VALUE="owdOrderAutoHistories.message" <%= "owdOrderAutoHistories.message".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchType))?"SELECTED":"" %>>Autoship Comments

</SELECT>
<SELECT NAME="<%= AutoShipHome.kAutoSearchCompare %>">
    <OPTION VALUE="0" <%= "0".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchCompare))?"SELECTED":"" %>>contains
    <OPTION VALUE="1" <%= "1".equals(request.getSession(true).getAttribute(AutoShipHome.kAutoSearchCompare))?"SELECTED":"" %>>is
</SELECT>
<INPUT TYPE=TEXT NAME="<%= AutoShipHome.kAutoSearchString%>" VALUE="<%= request.getSession(true).getAttribute(AutoShipHome.kAutoSearchString)%>">
<INPUT TYPE=SUBMIT NAME=submit VALUE="Find">
</FORM><HR>
  <display:table name="autoshiplist" sort="list" class="its"  pagesize="50" decorator="com.owd.displaytag.AutoShipTableDecorator">
          <display:column property="links" title="Links"  />

<% if(AutoShipHome.getSessionFlag(request,AutoShipHome.kExtranetInternalFlag) && AutoShipHome.getSessionString(request,AutoShipHome.kExtranetClientID).equals("0"))
   {
       %>
   <display:column property="client" sortable="true"   title="Client"/>
    <% } %>
<display:column property="subscriptionStatus" title="Status"/>
<display:column property="billName" title="Customer"/>
<display:column property="nextShipmentDate" title="Next Shipment"/>
<display:column property="itemCount" title="SKUs"/>
      <display:column property="address" title="Ship Address"/>
      <display:column property="city" title="Ship City/State"/>

</display:table>


<%
        HibernateSession.closeSession();
%>
<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

    </BODY>
</HTML>
