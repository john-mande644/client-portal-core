<%@ page import="com.owd.hibernate.generated.Asn,
                 com.owd.hibernate.HibernateSession,
                 com.owd.web.warehouse.asn.ASNHome,
                 org.hibernate.Criteria,
                 org.hibernate.Session,
                 org.hibernate.criterion.Expression,
                 java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>	<div id="mainArea">
<%

    //if internal user and all clients id is current, return all - else return just for active client id
   // request.setAttribute("asnlist",asnlist);
    Session sess = HibernateSession.currentSession();

    //if internal user and all clients id is current, return all - else return just for active client id

              //  List asnlist = sess.find("from Asn "+((ASNHome.getSessionFlag(request,ASNHome.kExtranetInternalFlag) && ASNHome.getSessionString(request,ASNHome.kExtranetClientID).equals("0"))?"":(" where client_fkey="+ASNHome.getSessionString(request,ASNHome.kExtranetClientID))));
              //  System.out.println("found asn list of:"+asnlist.size());

                Criteria crit = sess.createCriteria(Asn.class);
                crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

             //   crit.add(Expression.eq("status", request.getParameter("asnstatus")));
                if(((ASNHome.getSessionFlag(request,ASNHome.kExtranetInternalFlag) && ASNHome.getSessionString(request,ASNHome.kExtranetClientID).equals("0"))))
                {
                }else
                {
                    crit.add(Expression.eq("clientFkey",new Integer((String)(ASNHome.getSessionString(request,ASNHome.kExtranetClientID)))));
                }

                request.getSession(true).setAttribute(ASNHome.kASNSearchStatus,"3");
                request.getSession(true).setAttribute(ASNHome.kASNSearchCompare,"1");

                request.getSession(true).setAttribute(ASNHome.kASNSearchString,"");

                request.getSession(true).setAttribute(ASNHome.kASNSearchType,"");

                if(null != request.getParameter(ASNHome.kASNSearchStatus))
                {
                   request.getSession(true).setAttribute(ASNHome.kASNSearchStatus,request.getParameter(ASNHome.kASNSearchStatus));
                }
                if(null != request.getParameter(ASNHome.kASNSearchCompare))
                {
                   request.getSession(true).setAttribute(ASNHome.kASNSearchCompare,request.getParameter(ASNHome.kASNSearchCompare));
                }
                if(null != request.getParameter(ASNHome.kASNSearchString))
                {
                   request.getSession(true).setAttribute(ASNHome.kASNSearchString,request.getParameter(ASNHome.kASNSearchString));
                }
                if(null != request.getParameter(ASNHome.kASNSearchType))
                {
                   request.getSession(true).setAttribute(ASNHome.kASNSearchType,request.getParameter(ASNHome.kASNSearchType));
                }

                int statusSrch = new Integer((String)request.getSession(true).getAttribute(ASNHome.kASNSearchStatus)).intValue();
                int compareSrch = new Integer((String)request.getSession(true).getAttribute(ASNHome.kASNSearchCompare)).intValue();
                String stringSrch = ((String)request.getSession(true).getAttribute(ASNHome.kASNSearchString));
                String typeSrch = (String)request.getSession(true).getAttribute(ASNHome.kASNSearchType);

                 if(!("-1".equals((String)request.getSession(true).getAttribute(ASNHome.kASNSearchStatus))))
                 {
                     if(statusSrch==3)
                     {
                         List openStatusList = new ArrayList();
                         openStatusList.add(new Integer(0));
                         openStatusList.add(new Integer(2));
                        crit.add(Expression.in("status", openStatusList));

                     }   else
                     {
                     crit.add(Expression.eq("status", new Integer(statusSrch)));
                     }
                 }

                if(stringSrch.trim().length()>0 && typeSrch.trim().length()>1)
                {
                    if(compareSrch == 0)//is
                    {
                        if(typeSrch.indexOf("asnItems.")>= 0)
                        {
                            crit.createCriteria("asnItems").add(Expression.eq(typeSrch.substring(typeSrch.indexOf("asnitems.")+10), stringSrch));
                        }   else
                        {
                             if(typeSrch.equals("id"))
                             {
                              crit.add(Expression.eq(typeSrch,new Integer(stringSrch)));

                             }   else
                             {
                            crit.add(Expression.eq(typeSrch,stringSrch));
                             }
                        }

                    }   else
                    {  //contains
                        if(typeSrch.indexOf("asnItems.")>= 0)
                        {
                            crit.createCriteria("asnItems").add(Expression.like(typeSrch.substring(typeSrch.indexOf("asnitems.")+10), "%"+stringSrch+"%"));
                        }   else
                        {
                            crit.add(Expression.like(typeSrch,"%"+stringSrch+"%"));
                        }
                    }
                }


                List asnlist = null;

                System.out.println("trying list");
                asnlist = crit.list();

    System.out.println("found asn list of:"+asnlist.size());
    request.setAttribute("asnlist",asnlist);
    ASNHome.setSearchSessionAttributes(request);

%>
<HTML>
<jsp:include page="asnheader.jsp" flush="true"/>
<FORM METHOD=POST ACTION="./edit"><INPUT TYPE=HIDDEN NAME=act VALUE="asn-search">
Status:<SELECT NAME="<%= ASNHome.kASNSearchStatus %>">
<OPTION VALUE="-1" <%= "-1".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchStatus))?"SELECTED":"" %>>All
<OPTION VALUE="0" <%= "0".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchStatus))?"SELECTED":"" %>>Unreceived
<OPTION VALUE="1" <%= "1".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchStatus))?"SELECTED":"" %>>Received
<OPTION VALUE="2" <%= "2".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchStatus))?"SELECTED":"" %>>Partial Receipt
<OPTION VALUE="3" <%= "3".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchStatus))?"SELECTED":"" %>>Pending
</SELECT>&nbsp;
Field:<SELECT NAME="<%= ASNHome.kASNSearchType %>"><OPTION VALUE="" <%= "".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchType))?"SELECTED":"" %>>Select a field to search
<OPTION VALUE="asnItems.inventoryNum" <%= "asnItems.inventoryNum".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchType))?"SELECTED":"" %>>Item SKU in ASN
 <OPTION VALUE="asnItems.title" <%= "asnItems.title".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchType))?"SELECTED":"" %>>Item Title in ASN
<OPTION VALUE="shipperName" <%= "shipperName".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchType))?"SELECTED":"" %>>Shipper/Vendor Name
<OPTION VALUE="clientRef" <%= "clientRef".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchType))?"SELECTED":"" %>>Client ASN Reference
<OPTION VALUE="clientPo" <%= "clientPo".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchType))?"SELECTED":"" %>>Client PO Number
<OPTION VALUE="id" <%= "id".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchType))?"SELECTED":"" %>>OWD ASN Reference
</SELECT>
<SELECT NAME="<%= ASNHome.kASNSearchCompare %>">
<OPTION VALUE="0" <%= "0".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchCompare))?"SELECTED":"" %>>is
<OPTION VALUE="1" <%= "1".equals(request.getSession(true).getAttribute(ASNHome.kASNSearchCompare))?"SELECTED":"" %>>contains

</SELECT>
<INPUT TYPE=TEXT NAME="<%= ASNHome.kASNSearchString%>" VALUE="<%= request.getSession(true).getAttribute(ASNHome.kASNSearchString)%>">
<INPUT TYPE=SUBMIT NAME=submit VALUE="Find">
</FORM><HR>
  <div class="bugList"><display:table name="asnlist" sort="list" class="bugs"  pagesize="50" decorator="com.owd.displaytag.ASNTableDecorator">
          <display:column property="links" title="Links"  />
 <display:column property="status" sortable="true"   title="Status"/>
     <display:column property="clientRef" title="Client Ref" sortable="true"  />
     <display:column property="id" title="OWD Ref" sortable="true"  />

<% if(ASNHome.getSessionFlag(request,ASNHome.kExtranetInternalFlag) && ASNHome.getSessionString(request,ASNHome.kExtranetClientID).equals("0"))
   {
       %>
   <display:column property="client" sortable="true"   title="Client"/>
    <display:column property="palletCount" sortable="true"   title="Pallets"/>
<display:column property="cartonCount" sortable="true" title="Cartons"/>
<display:column property="carrier" sortable="true" title="Carrier"/>
    <% } %>

        <display:column property="shipperName" sortable="true" title="Shipper"/>

 <display:column property="expectDate" sortable="true"   title="Expected" decorator="com.owd.displaytag.StandardDateDecorator" />
 <display:column property="releaseType" sortable="true"   title="Stock Release"/>
  <display:column property="lastReceiveDate" sortable="true"   title="Last Received"  decorator="com.owd.displaytag.StandardDateDecorator" />
  <display:column property="unpostedReceiptCount" sortable="true"   title="Pending Receipts"   />
<display:column property="itemCount" title="SKUs"/>

</display:table></div>


<%
        HibernateSession.closeSession();
%>

 	</div >
</HTML>
