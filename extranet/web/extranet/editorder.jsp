<%@ page import="com.owd.core.OWDUtilities,com.owd.core.business.order.Event" %>
<%@ page import="com.owd.core.business.order.Package" %>
<%@ page import="com.owd.core.dbUtilities" %>
<%@ page import="com.owd.core.payment.FinancialTransaction" %>
<%@ page import="com.owd.extranet.servlet.ExtranetManager" %>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ page import="com.owd.hibernate.generated.OwdOrder" %>
<%@ page import="com.owd.hibernate.generated.OwdOrderShipHold" %>
<%@ page import="com.owd.hibernate.generated.ScanOrder" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.owd.core.business.order.OrderUtilities" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page import="static com.owd.core.business.order.Package.*" %>
<%@ page import="org.hibernate.engine.spi.SessionImplementor" %>
<%@ page import="com.owd.core.business.order.billing.shippingCharges" %>
<%@ page import="java.util.*" %>
<%@ page import="com.owd.core.business.order.beans.lotData" %>
<%@ page import="com.owd.core.managers.LotManager" %>
<%@ page import="com.owd.core.TagUtilities" %>
<%@ page import="com.owd.hibernate.generated.OwdLineItemExemptions" %>
<%@ page import="com.owd.core.business.order.beans.suppliesBean" %>

<%
    com.owd.core.business.order.OrderStatus order = (com.owd.core.business.order.OrderStatus) request.getAttribute(com.owd.extranet.servlet.OrdersManager.kCurrentOrder);
    java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
    java.text.DecimalFormat plainformatter = new java.text.DecimalFormat("#,###,##0.00");
    com.owd.core.business.Contact billc = order.billContact;
    com.owd.core.business.Address billa = order.billAddress;
    com.owd.core.business.Contact shipc = order.shipping.shipContact;
    com.owd.core.business.Address shipa = order.shipping.shipAddress;
    String carrier = order.shipping.carr_service;
    boolean cannotShipAll = false;
    boolean canPartialShip = false;
    String backorder = order.backorderNum.trim();
    boolean isInternalUser = ExtranetServlet.getSessionFlag(request, ExtranetServlet.kExtranetInternalFlag);

    if (!("".equals(backorder))) {
        backorder = "<A HREF=\"" + request.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction + "=" + com.owd.extranet.servlet.OrdersManager.kParamOrderEditAction + "&" + com.owd.extranet.servlet.OrdersManager.kparamOrderID + "=" + order.backorder.order_id + "\">" + order.backorderNum.trim() + "</A>";
    }
%>

<HR>
<fontx COLOR=RED><B><%= (null == request.getAttribute("formerror") ? "" : request.getAttribute("formerror") + "<BR>") %>
</B>
    <TABLE width=100% border=0 cellpadding=5>
        <FORM METHOD=POST
              ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
            <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
                   value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
            <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
                   value="<%= ExtranetManager.kOrdersMgrName %>"/>

            <TR>
                <TD width=5% VALIGN=CENTER><%
                    if (!order.is_void && (isInternalUser || order.is_unpicked) && !order.is_posted && !order.is_shipped) {
                %>
                    <INPUT TYPE=SUBMIT NAME=submit VALUE="Void This Order">
                    <INPUT TYPE=HIDDEN NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
                           VALUE="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderVoidAction%>">
                    <%
                        }
                    %>
                </TD>
        </FORM>
        <FORM METHOD=POST
              ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
            <TD VALIGN=CENTER>
                <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
                       value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
                <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
                       value="<%= ExtranetManager.kOrdersMgrName %>"/>
                <%
                    if (!order.is_void && !order.is_posted && !order.is_shipped) {
                        if (order.is_on_hold) {
                %>
                <INPUT TYPE=HIDDEN NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
                       VALUE="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderRemoveHoldAction%>"><INPUT
                    TYPE=SUBMIT NAME=submit VALUE="Remove Hold - Backorder All">
                <%
                } else {
                %>
                <INPUT TYPE=HIDDEN NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
                       VALUE="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderSetHoldAction%>"><INPUT TYPE=SUBMIT
                                                                                                           NAME=submit
                                                                                                           VALUE="Set On Hold">
                <%
                        }
                    }
                %>
            </TD>
        </FORM>
        <%
            if (!order.is_void && order.is_posted && !order.is_shipped && (!(order.isShipping))) {
        %>
        <FORM METHOD=POST
              ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
            <TD VALIGN=CENTER><INPUT TYPE=HIDDEN NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
                                     VALUE="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderUnpostAction%>"><INPUT
                    TYPE=SUBMIT NAME=submit VALUE="Unpost Order - Set On Hold" <% if (!order.is_unpicked){%>
                    onclick="return confirm('This order has been picked - unposting will incur an additional change order fee. Are you sure you want to unpost this order?')"<%}%>>
            </TD>
        </FORM>
        <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
               value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
        <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
               value="<%= ExtranetManager.kOrdersMgrName %>"/>
        <%
            }
        %>
        <TD align="right" valign="center">
            <FORM METHOD=POST
                  ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
                <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
                       value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
                <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
                       value="<%= ExtranetManager.kOrdersMgrName %>"/>
                <INPUT TYPE=HIDDEN NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
                       VALUE="clone-new"><INPUT TYPE=SUBMIT NAME=submit VALUE="Reorder">
            </FORM>
        </TD>
        </TR></TABLE>
        <%
  if(isInternalUser)
{
%>
    <TABLE>
        <TR>
            <TD align=right>
                <A HREF="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>&<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=editbilling">EDIT
                    GUARANTEE CREDITS</A>
            </TD>
        </TR>
    </TABLE>
        <%
}
%>
    <TABLE width=100% border=0>
        <TR>
            <TD>
                <%
                    OwdOrder owdOrder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(order.order_id));
                    String isOnHold = "";             
                    if (null!=owdOrder.getHoldinfo()){
                        if (owdOrder.getHoldinfo().getIsOnWhHold() == 1){
                            isOnHold=" (on DC Hold)";
                        }
                    }
                %>
                <fontx size="-1"><B>Status : </B><%= order.getStatusString() + " " + isOnHold %>
            </TD>
            <TD align=right>
                <fontx size=-1><B>Order Reference : </B><%= order.orderReference %>
            </TD>
        </TR>
        <TD>
            <fontx size=-1><B>Backorder Reference
                : </B><%= ("".equals(order.backorderNum.trim())?"No backorders":backorder) %>
        </TD>
        <TD align=right>
            <fontx size=-1><B>OWD Reference : </B><%= order.OWDorderReference %>
        </TD>
        </TR>

        <TR bgcolor="#000000">
            <TH style="color:#ffffff;">
                <fontx size=-1>Bill
                    To <% if((!order.is_posted) || (order.is_posted && (isInternalUser || order.is_unpicked) && !order.is_shipped && (!(order.isShipping)))){%><A
                            style="color:#ffffff;"
                            HREF="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderEditBillingAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id %>">
                        <fontx color="#F3F3E1">[edit]
                    </A>
                <%}%></TH>
            <TH style="color:#ffffff;">
                <fontx size=-1 color="#ffffff">Ship
                    To <% if((!order.is_posted || (order.is_posted && (isInternalUser || order.is_unpicked) && !order.is_shipped && (!(order.isShipping))))){%><A
                            style="color:#ffffff;"
                            HREF="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderEditShippingAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id %>">
                        <fontx color="#F3F3E1">[edit]
                    </A>
                <%}%></TH>
        </TR>
        <TR>
            <TD VALIGN=TOP ALIGN=LEFT WIDTH=50%>
                <fontx size=-2><%=billc.name+"<BR>"+(billa.company_name.length()>1?billa.company_name+"<BR>":"")+(billa.address_one.length()>0?billa.address_one+"<BR>":"")+(billa.address_two.length()>0?billa.address_two+"<BR>":"") %>
                        <%= billa.city+"&nbsp;"+billa.state+"&nbsp;"+billa.zip+"<BR>"+billa.country %>
                    <P>
                        Phone: <%= billc.phone %><BR>
                        Fax: <%= billc.fax %><BR>
                        Email: <%= (billc.email.length()>1?"<A HREF=\"mailto:"+billc.email+"\">"+billc.email+"</A>":"") %>
            </TD>
            <TD VALIGN=TOP ALIGN=LEFT WIDTH=50%>
                <fontx size=-2>
                        <%= shipc.name+"<BR>"+(shipa.company_name.length()>1?shipa.company_name+"<BR>":"")+(shipa.address_one.length()>0?shipa.address_one+"<BR>":"")+(shipa.address_two.length()>0?shipa.address_two+"<BR>":"") %>
                        <%= shipa.city+"&nbsp;"+shipa.state+"&nbsp;"+shipa.zip+"<BR>"+shipa.country %><P>
                    Phone: <%= shipc.phone %><BR>
                    Fax: <%= shipc.fax %><BR>
                    Email: <%= (shipc.email.length()>1?"<A HREF=\"mailto:"+shipc.email+"\">"+shipc.email+"</A>":"") %><BR>
                    Ship Via: <B><%= carrier %></B><BR>
                     <% if(!order.getIsUS()) {%>
                     International Tax and Duty:&nbsp;<B><%= order.dduDDP %></B>
                     <%  }%>
                </B>
            </TD>
        </TR>
    </TABLE>
    <HR>
    <TABLE>
        <TR>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size="-2"><B>Created:</B>
            </TD>
            <TD width="100%">
                <fontx size=-2><%= order.orderDate %> <B>via</B>
                <%=order.order_type%>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size="-2"><B>Type:</B>
            </TD>
            <TD width="100%">
                <fontx size=-2><%= (order.business_order ? "Business" : "Consumer") %>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size=-2><B><%= (order.client_id.equals("117") ? "Affiliate" : "PO Number") %>:</B>
            </TD>
            <TD width="100%">
                <fontx size=-2>
                <%= order.po_num %>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size=-2><B>Coupon/Discount Code:</B>
            </TD>
            <TD width="100%">
                <fontx size=-2>
                <%= order.coupon %>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size=-2><B>Comments:</B>
            </TD>
            <TD width="100%">
                <fontx size=-2>
                <%= order.shipping.comments %>
            </TD>
        </TR>
        <TR>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size=-2><B>Warehouse Notes:</B>
            </TD>
            <TD width="100%">
                <fontx size=-2>
                <%= order.shipping.whse_notes %>
            </TD>
        </TR>

        <%
            StringBuffer sb = new StringBuffer();
            OwdOrder oorder = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(order.order_id));

            System.out.println("check scans");
            Collection scans = oorder.getScanDocs();
            if (scans != null) {
                System.out.println("got scans " + scans.size());
                Iterator it2 = scans.iterator();
                if (it2.hasNext()) {
                    ScanOrder sr = (ScanOrder
                            ) it2.next();
                    if(sr.getType().equals("RETURN_LABEL")){
                        sr = (ScanOrder) it2.next();
                    }

                    System.out.println("Scan=" + sr);
                    String typeicon = "text.gif";
                    if (sr.getName().toUpperCase().endsWith(".PDF")) {
                        typeicon = "acrobat.gif";
                    }
                    System.out.println("Name:" + sr.getName());
                    sb.append("<A HREF=\"" +
                                    ("" + request.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction + "=get-scan&" + com.owd.extranet.servlet.OrdersManager.kparamOrderID + "=" + order.order_id + "&auth=" +
                                            URLEncoder.encode(OWDUtilities.encryptData("" + oorder.getOrderId() + "/" + sr.getName() + "/" + oorder.getClientFkey()), "UTF-8") +
                                            "\"><IMG SRC=\"/webapps/images/" + typeicon +
                                            "\" border=\"0\"></A>")
                    );

        %>
        <TR>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size=-2><B>Packing Slip:
                </B>
            </TD>
            <td ALIGN="LEFT" WIDTH="100%">
                <%=sb.toString()%>
            </TD>
        </TR>
        <%
                }

            }
            if (order.is_shipped && (order.shipping.shipAddress.isInternational() || order.shipping.shipAddress.isAPO())) {
                StringBuffer csb = new StringBuffer();

                csb.append("<A HREF=\"" +
                        ("" + request.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction + "=get-comminvoice&" + com.owd.extranet.servlet.OrdersManager.kparamOrderID + "=" + order.order_id + "&auth=" +
                                URLEncoder.encode(OWDUtilities.encryptData("" + oorder.getOrderId() + "/" + oorder.getClientFkey() + "/" + oorder.getOrderNum()), "UTF-8") +
                                "\"><IMG SRC=\"/webapps/images/acrobat.gif\" border=\"0\"></A>"));
        %>
        <TR>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size=-2><B>Commercial Invoice:</B>
            </TD>
            <td ALIGN="LEFT" WIDTH="100%">
                <%=csb.toString()%>
            </TD>
        </TR>
        <%
            }
            if (FacilitiesManager.isClientMultiFacility(oorder.getClient().getClientId()) && (!(order.is_posted || order.is_void))) {
        %>
        <TR>
            <TD ALIGN=RIGHT NOWRAP><B>Facility:</B></TD>
            <TD width="100%">
                <FORM METHOD=POST
                      ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
                    <INPUT TYPE="HIDDEN" name="act" value="cngMgr"/>
                    <INPUT TYPE="HIDDEN" name="mgr" value="Orders"/>
                    <INPUT TYPE=HIDDEN NAME="ordermgr" VALUE="setLocation">
                    <SELECT NAME="location"
                            onChange="this.form.submit();"><%= FacilitiesManager.getHTMLSelectOptions(order.getLocation()) %>
                    </SELECT></FORM>
            </TD>
        </TR>
        <% } else {%>
        <TR>
            <TD ALIGN=RIGHT NOWRAP><B>Facility:</B></TD>
            <TD width="100%"><%= order.getLocation() + " - " + FacilitiesManager.getFacilityForCode(order.getLocation()).getMetroArea() %>
            </TD>
        </TR>

        <% } %>
        <% if(order.group_name.length()>0){
           %>
        <TR>
            <TD ALIGN=RIGHT NOWRAP><B>Group Name:</B></TD>
            <TD width="100%"> <%=order.group_name%></td>
        </TR>

       <% }%>
        <% if(order.order_location.length()>0){
        %>
        <TR>
            <TD ALIGN=RIGHT NOWRAP><B>Order Location:</B></TD>
            <TD width="100%"> <%=order.order_location%></td>
        </TR>

        <% }%>

        <% if(order.getTagMap().containsKey(TagUtilities.kEDIAmazonARN)){
        %>
        <TR>
            <TD ALIGN=RIGHT NOWRAP><B>Amazon ARN:</B></TD>
            <TD width="100%"> <%=order.getTagMap().get(TagUtilities.kEDIAmazonARN)%></td>
        </TR>
        <%}%>

        <% if(order.getTagMap().containsKey(TagUtilities.kEDIZapposDN)){
        %>
        <TR>
            <TD ALIGN=RIGHT NOWRAP><B>Zappos DN:</B></TD>
            <TD width="100%"> <%=order.getTagMap().get(TagUtilities.kEDIZapposDN)%></td>
        </TR>
        <%}%>


        <% if(order.getTagMap().containsKey(TagUtilities.kEDIDicksASIDN)){
        %>
        <TR>
            <TD ALIGN=RIGHT NOWRAP><B>Dick&#39;s ASIDN:</B></TD>
            <TD width="100%"> <%=order.getTagMap().get(TagUtilities.kEDIDicksASIDN)%></td>
        </TR>
        <%}%>

        <!-- =================== Sean Created on 4/5/2019 Excel Download ========================== -->
        <%
            StringBuffer esb = new StringBuffer();
            esb.append("<A HREF=\"" +
                    ("" + request.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" 
                        + com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction + "=downloadExcel&" 
                        + com.owd.extranet.servlet.OrdersManager.kparamOrderID + "=" + order.order_id 
                        + "&auth=" + URLEncoder.encode(OWDUtilities.encryptData("" + oorder.getOrderId() + "/" + oorder.getClientFkey() + "/" + oorder.getOrderNum()), "UTF-8") 
                        + "\"><IMG SRC=\"/webapps/img/ico_file_excel.png\" border=\"0\">Download</A>"));
        %>
        <TR>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size=-2><B>Excel File: 
                </B>
            </TD>
            <TD ALIGN="LEFT" WIDTH="100%">
                <%=esb.toString()%>
            </TD>
        </TR>
        <%
         if(order.is_shipped){
             Map<String,String> tags = TagUtilities.getTagMap("ORDER",Integer.parseInt(order.order_id));
             if(tags.containsKey("COM.OWD.RETURN.LABEL")){

             %>
        <tr>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size=-2><B>Return Label:
                </B>
            </TD>
            <td>
                <a href="<%=tags.get("COM.OWD.RETURN.LABEL")%>" target="_blank">View Label</a>
            </td>
        </tr>

        <%
             }else{
                 if(oorder.getClient().isSmartPostReturnExtranet()){

                     %>
        <tr>
            <TD ALIGN=RIGHT NOWRAP>
                <fontx size=-2><B>Return Label:
                </B>
            </TD>
            <td>
                <a href="/webapps/v2/returnLabel/create?orderId=<%=order.order_id%>" target="_blank">Create Label</a>
            </td>
        </tr>
        <%
                 }

             }


         }
        %>
    <!-- ================================================================================ -->
    </TABLE>
    <BR>




    <% List<lotData> lots = LotManager.getShippedLotsFromOrderIdGrouped(order.order_id);
    if(lots.size()>0){
    %>
    <hr>

    <table class="info">
        <tr bgcolor="#000000"><th align="left" style="color:#ffffff"><fontx color="#ffffff" size=-2>Lot Value</fontx></th><th align="left" style="color:#ffffff"><fontx color="#ffffff" size=-2>Qty</fontx></th></tr>
        <% for(lotData lot:lots){
            %>
        <tr>
            <td><%=lot.getLotValue()%></td>
            <td><%=lot.getLotQty()%></td>
        </tr>
        <% }%>
    </table>
    <hr>
    <%
    }
    %>


    <% if(isInternalUser){
        Map<String,String> charges = order.getBillingCharges();
        if(charges.size()>0){
    %>

   <button onClick="$('.pricingBreakdown').toggle();">Show Pricing Breakdown</button>
   <div class="pricingBreakdown" style="display:none">
        <% for(String s : charges.keySet()){ %>


             <%= s +" : " + charges.get(s) %>
             </br>




       <% }%>
       <hr>
   </div>


    <%}
     }%>

    </BR>
    <P>

            <% if(order.items.size() < 1) {
%>
        <B>No items found for this order</B>
        <%
}else{ 
%>
    <TABLE width=100% border=0 cellspacing=0 cellpadding=1>
        <tr nowrap>
            <td nowrap>
                <fontx size=-2>
                        <% if(!order.is_void && !order.is_posted && !order.is_shipped && (!(order.isShipping)))
{
    %>
                    <HR>
                    &nbsp;<BR>

                    <FORM METHOD=POST
                          ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
                        <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
                               value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
                        <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
                               value="<%= ExtranetManager.kOrdersMgrName %>"/>
                        <INPUT TYPE=HIDDEN NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
                               VALUE="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderAddItemAction%>">
                        <TABLE BORDER="0">
                            <TR>
                                <TD align="right" NOWRAP>
                                    <fontx size=-2>Add&nbsp;New&nbsp;SKU:
                                </TD>
                                <TD><INPUT TYPE=TEXT name=newsku></TD>
                                <TD align="right">
                                    <fontx size=-2>Quantity:
                                </TD>
                                <TD><INPUT TYPE=TEXT name=newqty size="8"></TD>
                                <TD width="100%"></TD>
                            </TR>
                            <TR>
                                <TD align="right" valign="top">
                                    <fontx size=-2>Description:
                                </TD>
                                <TD valign="top"><INPUT TYPE=TEXT name=newdesc><BR>
                                    <fontx size=-2>Optional - leave blank to use default description
                                </TD>
                                <TD NOWRAP align="right" valign="top">
                                    <fontx size=-2>Price&nbsp;Each:
                                </TD>
                                <TD valign="top"><INPUT TYPE=TEXT name=newprice size="8"></TD>
                                <TD align="left" valign="top"><INPUT TYPE=SUBMIT NAME=addItem VALUE="Add Item To Order">
                                </TD>
                            </TR>
                        </TABLE>
                    </FORM>
                <% }
                %></td>
        </tr>
    </table>
    <FORM METHOD=POST
          ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderShipAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
        <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
               value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
        <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
               value="<%= ExtranetManager.kOrdersMgrName %>"/>
        <TABLE width=100% border=0 cellspacing=0 cellpadding=3>
            <TR bgcolor="#000000">
                <TH width=1%></TH>
                <TH style="color:#ffffff;" align=left colspan=2>
                    <fontx size=-2 color="#ffffff" align=left>SKU&nbsp;
                </TH>
                <TH style="color:#ffffff;" align=left width=50%>
                    <fontx size=-2 color="#ffffff">Description&nbsp;
                </TH>
                <TH style="color:#ffffff;" align=left>
                    <fontx size=-2 color="#ffffff" align=left>Color&nbsp;
                </TH>
                <TH style="color:#ffffff;" align=left>
                    <fontx size=-2 color="#ffffff" align=left>Size&nbsp;
                </TH>
                <TH style="color:#ffffff;" align=right COLSPAN=2>
                    <fontx size=-2 color="#ffffff" align=right>&nbsp;Count
                </TH>
                <TH style="color:#ffffff;" align=right>
                    <fontx size=-2 color="#ffffff" align=right>&nbsp;BO
                </TH>
                <TH style="color:#ffffff;" align=right>
                    <fontx size=-2 color="#ffffff" align=right>&nbsp;Cost
                </TH>
                <TH style="color:#ffffff;" align=right>
                    <fontx size=-2 color="#ffffff" align=right>&nbsp;Line&nbsp;Total&nbsp;
                </TH>

                <TH align=right NOWRAP></TH>
            </TR>
                <%

    for(int i=0; i<order.items.size(); i++)
	{
		com.owd.core.business.order.LineItem item = (com.owd.core.business.order.LineItem) order.items.elementAt(i);
    if(item.parent_line==null)item.parent_line=new Integer(0);
    if(item.is_parent==null)item.is_parent=new Integer(0);
%>
            <TR>
                <TD><% if (item.parent_line.intValue() == 0) {
                    String stockLevel = "";

                    if (!order.is_void && !order.is_posted) {
                        int available = 0;
                        try
                        {
                            available = OrderUtilities.getAvailableInventory(item.inventory_fkey, FacilitiesManager.getFacilityForCode(order.getLocation()).getId());

                        System.out.println("got avail " + available + " " + item.client_part_no);
                        if (item.is_parent.intValue() == 1) {
                            System.out.println("getting kit quant");
                            available = OrderUtilities.getAvailableKitInventory(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), item.inventory_fkey, FacilitiesManager.getFacilityForCode(order.getLocation()).getId());
                            System.out.println("got kit avail " + available);
                        }
                        }catch(Exception exa)
                        {
                            exa.printStackTrace();
                        }

                        if (available >= item.quantity_request) {
                            stockLevel = "<fontx color=green><B>On Hand (" + available + ")&nbsp;&nbsp;</B>";
                            if (!order.is_shipped) {%><INPUT TYPE=CHECKBOX NAME="ship_item_<%= item.line_item_id %>"
                                                             VALUE=1><%
                            }
                            canPartialShip = true;
                        } else {
                            stockLevel = "<fontx color=red><B>On Hand (" + available + ")&nbsp;&nbsp;</B>";
                            cannotShipAll = true;
                        }


                    }

                %>
                </TD>
                <TD align=left height=1% colspan=2 NOWRAP>
                    <fontx size=-2 align=left>
                    <%= item.is_parent.intValue() == 1 ? "<B>" : ""%><%= item.client_part_no %><%= item.is_parent.intValue() == 1 ? "</B>" : ""%>
                </TD>
                <TD align=left>
                    <fontx size=-2 align=left>
                    <%= item.description %>
                </TD>
                <TD align=left NOWRAP>
                    <fontx size=-2 align=left>
                    <%= ("null".equalsIgnoreCase(item.color) ? "" : item.color) %>
                </TD>
                <TD align=left NOWRAP>
                    <fontx size=-2 align=left>
                    <%= ("null".equalsIgnoreCase(item.size) ? "" : item.size) %>
                </TD>
                <TD align=right NOWRAP>
                    <fontx size=-2>
                    <%= stockLevel %>
                </TD>
                <TD align=right>
                    <fontx size=-2>
                    <%= (item.quantity_backordered + item.quantity_request) %>
                </TD>
                <TD align=right NOWRAP>
                    <fontx size=-2>
                    <%= item.quantity_backordered %>
                </TD>
                <TD align=right NOWRAP>
                    <fontx size=-2>
                    <%= formatter.format(item.sku_price) %>
                </TD>
                <TD align=right NOWRAP>
                    <fontx size=-2>
                    <%= formatter.format(item.total_price) %>
                </TD>
                <TD><% if (!order.is_void && !order.is_posted && !order.is_shipped && (!(order.isShipping))) {%>&nbsp;|&nbsp;<A
                        HREF="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?ordermgr=removeItem&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>&lineid=<%= item.line_item_id%>"
                        onclick="return confirm('Are you sure you want to remove this item? This action cannot be undone.')"><IMG
                        border="0" src="<%=request.getContextPath()+"/images/test/Delete.gif"%>" alt="remove item"/></A><%
                    }
                %></TD>
            </TR>
                <%
     if(item.is_parent.intValue()==1)
     {
       for(int is=0; is<order.items.size(); is++)
	{
		com.owd.core.business.order.LineItem subitem = (com.owd.core.business.order.LineItem) order.items.elementAt(is);
         if(subitem.parent_line==null)subitem.parent_line=new Integer(0);
    if(subitem.is_parent==null)subitem.is_parent=new Integer(0);
      String  subStockLevel = "";

if(!order.is_void && !order.is_posted)
{
	int subavailable = OrderUtilities.getAvailableInventory(subitem.inventory_fkey,FacilitiesManager.getFacilityForCode(order.getLocation()).getId());
	if(subavailable>=subitem.quantity_request)
	{
       subStockLevel = "<fontx color=green><B>On Hand ("+subavailable+")&nbsp;&nbsp;</B>";

	}else
	{
		subStockLevel = "<fontx color=red><B>On Hand ("+subavailable+")&nbsp;&nbsp;</B>";
		//cannotShipAll=true;
	}

}
        System.out.println(""+subitem.parent_line.intValue()+"?=?"+item.line_item_id);
    if((""+subitem.parent_line.intValue()).equals(item.line_item_id))
    {
       %>
            <tr>
                <TD>&nbsp;</TD>
                <td width=18 align=left><img src="/webapps/images/purchase.gif"></td>
                <TD align=left NOWRAP>
                    <fontx size=-2 align=left>
                    <%= subitem.client_part_no %>
                </TD>
                <TD align=left>
                    <fontx size=-2 align=left>
                    <%=  subitem.description %>
                </TD>
                <TD align=left NOWRAP>
                    <fontx size=-2 align=left>
                    <%= ("null".equalsIgnoreCase(subitem.color) ? "" : subitem.color) %>
                </TD>
                <TD align=left NOWRAP>
                    <fontx size=-2 align=left>
                    <%= ("null".equalsIgnoreCase(subitem.size) ? "" : subitem.size) %>
                </TD>
                <TD align=right NOWRAP>
                    <fontx size=-2>
                    <%= subStockLevel %>
                </TD>
                <TD align=right>
                    <fontx size=-2>
                    <%= (subitem.quantity_backordered + subitem.quantity_request) %>
                </TD>
                <TD align=right NOWRAP>
                    <fontx size=-2>
                    <%= subitem.quantity_backordered %>
                </TD>
                <TD align=right NOWRAP></TD>
                <TD align=right NOWRAP></TD>
                <TD align=right NOWRAP></TD>
            </TR>
                <%
                    }
                }
     }
    }
    }
%>
            <TR>
                <TD colspan=11>
                    <HR>
                </TD>
            </TR>
            <TR>
                <TD colspan=9 rowspan=2 valign=TOP>
                    <fontx size=-2>&nbsp;
                            <% if(!(order.is_posted) && !order.is_void && canPartialShip)
	{
		
	%>
                        <INPUT TYPE=SUBMIT NAME=shipOrder VALUE="Ship Checked Items Only">
                            <%
		
		if(!cannotShipAll)
		{
%>
                        &nbsp;<INPUT TYPE=SUBMIT NAME=shipOrder VALUE="Ship Entire Order">
                            <%
		}

	}
%>
    </FORM>
    <FORM METHOD=POST
          ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
        <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
               value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
        <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
               value="<%= ExtranetManager.kOrdersMgrName %>"/>
        <INPUT TYPE=HIDDEN NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
               VALUE="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderUpdateTotalAction%>">

        </TD>
        <TD align=right>
            <fontx size=-1><B>Subtotal&nbsp;:&nbsp;
        </TD>
        <TD align=right>
            <fontx size=-1><B><%= formatter.format(order.order_sub_total) %>
            </B>
        </TD>
        </TR>
        <TR>

            <TD align=right>
                <fontx size=-1><B>Discount&nbsp;:&nbsp;
            </TD>
            <TD align=right><% if (!order.is_posted && !order.is_void) {%><INPUT TYPE="TEXT" size=8 NAME="discountValue"
                                                                                 VALUE="<%= plainformatter.format(Math.abs(order.discount)) %>"><%} else {%>
                <fontx size=-1>
                <%= formatter.format(order.discount * -1.00) %><%}%></TD>
        </TR>
        <TR>
            <TD colspan="9" rowspan="4" valign="center" align="left" border=1></TD>
            <TD align=right>
                <fontx size=-1><B>Tax&nbsp;:&nbsp;
            </TD>
            <TD align=right><% if (!order.is_posted && !order.is_void) {%><INPUT TYPE="TEXT" size=8 NAME="taxValue"
                                                                                 VALUE="<%= plainformatter.format(order.tax_amount) %>"><%} else {%>
                <fontx size=-1>
                <%= formatter.format(order.tax_amount) %><%}%></TD>
        </TR>
        <TR>
            <TD align=right>
                <fontx size=-1><B>S/H&nbsp;:&nbsp;
            </TD>
            <TD align=right><% if (!order.is_posted && !order.is_void) {%><INPUT TYPE="TEXT" size=8 NAME="shipValue"
                                                                                 VALUE="<%= plainformatter.format(order.ship_handling_fee) %>"><%} else {%>
                <fontx size=-1>
                <%= formatter.format(order.ship_handling_fee) %><%}%></TD>
        </TR>
        <% if (!order.is_posted && !order.is_void) {%>
        <TR>
            <TD align=right colspan="2"><INPUT TYPE="SUBMIT" NAME="SUBMIT" VALUE="UPDATE TOTAL"></TD>
        </TR>
        <%}%>
        <TR>
            <TD align=right>
                <fontx size=-1><B>Total&nbsp;:&nbsp;
            </TD>
            <TD align=right>
                <fontx size=-1><B><%= formatter.format(order.order_total) %>
                </B>
            </TD>
        </TR>
        </TABLE></FORM>
        <%
}
%>
    <!--Line Item Exemption block-->
    <%if(order.lineItemExemptions.size()>0){
    %>
    <button onClick="$('.exemptionBreakdown').toggle();" type="button">Show Line Item Exemptions</button><br/>
    <div class="exemptionBreakdown" style="display:none;  padding-bottom:10px">
        <table border="0">
            <TR bgcolor="#000000">
            <TH style="color:#ffffff">SKU</TH>
                <TH style="color:#ffffff">Exemption Type</TH>
                <TH style="color:#ffffff">Reason</TH>
                <TH style="color:#ffffff">Count</TH>
            </TR>
            <%
            for(OwdLineItemExemptions lie: order.lineItemExemptions){
                %>
                <tr>
                    <td><%=lie.getInventoryNum()%></td>
                    <td><%=lie.getExemptionCode()%></td>
                    <td><%=lie.getExemptionValue()%></td>
                    <td><%=lie.getQty()%></td>
                </tr>

           <% }
            %>
        </table>
        </div>
    <% } %>

    <%if(order.serialItemMap.size()>0){

    %>
    <button onClick="$('.serialBreakdown').toggle();" type="button">Show Serial# Breakdown</button>
    <div class="serialBreakdown" style="display:none; width:50%">
        <table>
            <tr>
                <th>sku</th>
                <th>Serial</th>
            </tr>

    <%
        System.out.println(order.serialItemMap);
        for(int i=0; i<order.items.size(); i++) {
            com.owd.core.business.order.LineItem item = (com.owd.core.business.order.LineItem) order.items.elementAt(i);
            System.out.println(order.serialItemMap.keySet());
            System.out.println(item.line_item_id);
            System.out.println(order.serialItemMap.containsKey(new Integer(item.line_item_id)));
            if(order.serialItemMap.containsKey(new Integer(item.line_item_id))){
                System.out.println("contains");
                %>
            <tr>
                <td><%=item.getInventory().inventory_num%>:</td>
                <td></td>
            </tr>
                <%
                    List ll = (List) order.serialItemMap.get(new Integer(item.line_item_id));
                    for(Object l:ll){
                        %>
                <tr><td></td><td><%=l.toString()%></td></tr>
                <%
                    }
                             %>

            <%
            }

        }

        %>

        </table>
        </div>
    <%
    }

    %>


    <P>
        <fontx size=-1 color="#000000"><B>Order Processing Notes</B>
                <%
	
	if(order.comments.size() < 1) {
%>
            <HR>
            <fontx color="#000000" size=-2><B>No comments found for this order</B>
                    <%
}else{
%>
                <TABLE width=100% cellspacing=0 cellpadding=2>

                    <%

                        Iterator iter = order.comments.iterator();
                        while (iter.hasNext()) {
                            Event item = (Event) iter.next();
                            String when;
                            String what = "Unknown Type (" + item.event_type + ")";
                            String msg = "";
                            if (item.message != null) msg = item.message;
                            switch (item.event_type) {
                                case (Event.kEventTypeGeneral):
                                    what = "Miscellaneous";

                                    break;
                                case (Event.kEventTypeComment):
                                    what = "Note";

                                    break;
                                case (Event.kEventTypeEmailSent):
                                    what = "Email Sent";

                                    break;
                                case (Event.kEventTypeHandling):
                                    what = "Order Processing";

                                    break;
                                case (Event.kEventTypeEdit):
                                    what = "Order Edit";

                                    break;
                            }


                            when = item.event_stamp.substring(0, item.event_stamp.indexOf(" "));
                            when = when + "&nbsp;" + item.event_stamp.substring(item.event_stamp.indexOf(" ") + 1, item.event_stamp.indexOf("."));
                            if (item.creator.equalsIgnoreCase("NULL")
                                    || item.creator.equalsIgnoreCase("dbo")
                                    || item.creator.equalsIgnoreCase("guest")
                                    || item.creator.equalsIgnoreCase("system")
                                    || item.creator.equalsIgnoreCase("sa")) {
                                when = when + "<BR><B>(" + what + "&nbsp;by&nbsp;System)";
                            } else {
                                when = when + "<BR><B>(" + what + "&nbsp;by&nbsp;" + item.creator + ")";
                            }


                    %>
                    <TR>
                        <TD colspan=3>
                            <HR>
                        </TD>
                    </TR>
                    <TR>
                        <TD VALIGN="TOP">
                            <fontx size=-2><%= when %></B>
                        </TD>
                        <TD>&nbsp;&nbsp;</TD>
                        <TD VALIGN="TOP">
                            <fontx size=-2>
                            <%= msg %>
                        </TD>
                    </TR>

                    <%
                        }
                    %>
                </TABLE>
                    <%
    }


%>
                <FORM METHOD=POST
                      ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
                    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
                           value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
                    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
                           value="<%= ExtranetManager.kOrdersMgrName %>"/>
                    <INPUT TYPE=HIDDEN NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
                           VALUE="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderAddCommentAction%>">
                    <TEXTAREA ROWS=5 COLS=50 NAME="newCommentText"></TEXTAREA>
                    <BR><INPUT TYPE=SUBMIT NAME="Add New Comment" VALUE="Add New Comment">
                </FORM>
                <HR>
                <fontx size=-1 color="#000000"><B>Credit Card Transactions</B><BR>
                        <% if(order.transactions.size() < 1) {
%>
                    <fontx color="#000000" size=-2><B>No transactions found for this order</B>
                            <%
}else{
%>
                        <TABLE width=100%>
                            <TR bgcolor="#000000">
                                <TH style="color:#ffffff;">
                                    <fontx size=-1 color="#ffffff">Type
                                </TH>
                                <TH style="color:#ffffff;">
                                    <fontx size=-1 color="#ffffff">Status
                                </TH>
                                <TH style="color:#ffffff;">
                                    <fontx size=-1 style="color:#ffffff;">Message
                                </TH>
                                <TH style="color:#ffffff;">
                                    <fontx size=-1 style="color:#ffffff;">Amount
                                </TH>
                            </TR>
                            <%
                                Iterator iter = order.transactions.iterator();
                                while (iter.hasNext()) {

                                    FinancialTransaction item = (FinancialTransaction) iter.next();
                                    if (0 == item.is_void) {
                            %>
                            <TR>
                                <TD align=center>
                                    <fontx size=-2><B><%
                                        String strType = "Unknown";
                                        if ("0".equals(item.fop)) {
                                            //CC FOP
                                            int cctype = new Integer(item.type).intValue();
                                            switch (cctype) {
                                                case (FinancialTransaction.transTypeAuthOnlyRequest):
                                                    strType = "CC Authorization";
                                                    break;
                                                case (FinancialTransaction.transTypeCaptureOutsideAuthRequest):
                                                    strType = "CC Capture";
                                                    break;
                                                case (FinancialTransaction.transTypeAuthCaptureRequest):
                                                    strType = "CC Capture";
                                                    break;
                                                case (FinancialTransaction.transTypeVoidTransactionRequest):
                                                    strType = "CC Void Transaction";
                                                    break;
                                                case (FinancialTransaction.transTypeCreditRequest):
                                                    strType = "Credit CC";
                                                    break;
                                            }

                                        } else {
                                            //CK or PO FOP
                                            if (item.type.equals(FinancialTransaction.transTypeCheckTransactionRequest + "")) {
                                                strType = "E-Check";
                                            } else {
                                                strType = "Check/PO";
                                            }
                                        }

                                        if (item.is_future_charge == 1 && item.charge_status == 1) {
                                            strType = strType + " (Future)";
                                        }

                                    %><%= strType %>
                                    </B>
                                </TD>
                                <TD align=center>
                                    <fontx size=-2><B><%= item.status %>
                                    </B>
                                </TD>
                                <TD align=center>
                                    <fontx size=-2>
                                    <%= item.error_reponse %>
                                </TD>
                                <TD align=right>
                                    <fontx size=-2>
                                    <%= formatter.format(item.amount) %>
                                </TD>
                            </TR>
                            <% }
                            }
                            %>
                        </TABLE>
                            <%
    }

    com.owd.core.business.Client client = com.owd.core.business.Client.getClientForID(order.client_id);
    System.out.println("cking packages");
    if (client == null) {
        System.out.println("client null");
    }
    if (client.pp_proc == null) {
        System.out.println("pp_proc null");
    }

//if(client.pp_proc != null||client.check_proc!=null)
//{
//if(client.pp_proc.length()>7 || client.check_proc.length()>4)
    //{
    
    if ((client.pp_proc != null && client.pp_proc.length() > 7) || (null != client.check_proc && client.check_proc.length() > 4)) {
        System.out.println("cking packages 2");
%>
                        <HR>
                        <FORM METHOD=POST
                              ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
                            <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
                                   value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
                            <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
                                   value="<%= ExtranetManager.kOrdersMgrName %>"/>
                            <INPUT TYPE=HIDDEN NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
                                   VALUE="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderDoCCAction%>">
                            <INPUT TYPE=SUBMIT NAME=submit VALUE="New Payment Transaction">

                        </FORM>
                            <% }


     %>

                        <P>
                        <HR>
                        <fontx size=-1 color="#000000"><B>Packages/Shipments</B><BR><% if (order.is_shipped) {%>
                            <FORM METHOD=POST
                                  ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
                                <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>"
                                       value="<%= ExtranetServlet.kParamChangeMgrAction %>"/>
                                <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>"
                                       value="<%= ExtranetManager.kOrdersMgrName %>"/>
                                <INPUT TYPE=HIDDEN
                                       NAME="<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>"
                                       VALUE="calltag-getinfo">
                                <INPUT TYPE=SUBMIT NAME=submit VALUE="Send UPS Calltag">
                            </FORM>
                                <% } %>
                                <% if(order.packages.size() < 1) {
%>
                            <fontx color="#000000" size=-2><B>No packages/shipments found for this order</B>
                                    <%
}else{
%>
                                <TABLE width=100%>
                                    <TR bgcolor="#000000">
                                        <TH></TH>
                                        <TH style="color:#ffffff;">
                                            <fontx size=-1 color="#ffffff">Shipped
                                        </TH>
                                        <TH style="color:#ffffff;">
                                            <fontx size=-1 color="#ffffff">Carrier
                                        </TH>
                                        <TH style="color:#ffffff;">
                                            <fontx size=-1 color="#ffffff">Tracking
                                        </TH>
                                        <TH style="color:#ffffff;">
                                            <fontx size=-1 color="#ffffff">Weight
                                        </TH>
                                        <TH style="color:#ffffff;">
                                        	<fontx size=-1 color="#ffffff">Dim Weight
                                        </TH>
                                        <TH style="color:#ffffff;">
                                            <fontx size=-1 color="#ffffff">Rate
                                        </TH>
                                    </TR>
                                    <%
                                        //String carrier = order.shipping.carr_service;
                                        for (int i = 0; i < order.packages.size(); i++) {


                                            com.owd.core.business.order.Package item = (com.owd.core.business.order.Package) order.packages.elementAt(i);
                                            Double insuranceValue = getInsuranceValueForPackage(Integer.parseInt(item.order_track_id));
                                    %>
                                    <TR VALIGN=CENTER>
                                        <TD VALIGN=CENTER><%
                                            if (ExtranetServlet.getSessionFlag(request, ExtranetServlet.kExtranetInternalFlag) && ("0".equals(item.is_void) && (!(order.isShipping)) && (4 <= TimeUnit.MILLISECONDS.toHours(Calendar.getInstance().getTime().getTime() - OWDUtilities.getDateForSQLDateString(item.createdOn).getTime()))||carrier.equals("LTL"))) {
                                        %>
                                            <fontx SIZE=-2><A
                                                    HREF="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kparamShipmentID%>=<%= item.order_track_id%>&<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.extranet.servlet.OrdersManager.kParamOrderVoidPackageAction%>&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>"
                                                    onclick="return confirm('Are you sure you want to void this shipment? This action cannot be undone.')">Void
                                                Shipment</A>
                                            <%
                                                } else {

                                                    %>
                                              <I><%= ("0".equals(item.is_void) ? "" : "Voided") %>
                                                </I><%
                                                }
                                            %></TD>
                                        <TD VALIGN=CENTER>
                                            <fontx size=-2>
                                            <%= item.createdOn.substring(0, item.createdOn.indexOf(" ")) %>
                                        </TD>
                                        <TD VALIGN=CENTER>
                                            <fontx size=-2>
                                                <%
                                                    if(item.shipMethod.length()>1){
                                                    %>

                                                    <% if(isInternalUser){ %>
                                                    <%= item.shipMethod %>

                                                    <% }else{  %>
                                                     <% if(order.shipping.carr_service_ref_num.contains("FLATRATE")){ %>
                                                        <%= item.carrierName %>
                                                    <% }else{ %>
                                                        <%= item.shipMethod %>

                                                    <%}%>


                                                    <%} %>
                                                  <%  }else{ %>

                                                    <%= carrier %>
                                                <%
                                                    }
                                                %>

                                        </TD>
                                        <TD VALIGN=CENTER>
                                            <fontx size=-2>
                                            <%= (carrier.indexOf("UPS") == 0 ? "<A HREF=\"http://wwwapps.ups.com/etracking/tracking.cgi?HTMLVersion=4.0&TypeOfInquiryNumber=T&tracknums_displayed=1&InquiryNumber1=" + item.tracking_no + "\">" + item.tracking_no + "</A>" : ((carrier.indexOf("USPS") >= 0) && (item.tracking_no.indexOf("NONE") == (-1)) ? "<A HREF=\"https://tools.usps.com/go/TrackConfirmAction.action?tLabels=" + item.tracking_no + "\">" + item.tracking_no + "</A>" : item.tracking_no)) %>
                                        </TD>
                                        <TD VALIGN=CENTER>
                                            <fontx size=-2><%= item.weight %> lbs
                                        </TD>
                                        <TD VALIGN=CENTER>
                                        	<fontx size=-2><%= item.dimWeight %> lbs
                                        </TD>
                                        <TD VALIGN=CENTER>
                                            <fontx size=-2>
                                            <%= formatter.format(item.total_billed) %><%= insuranceValue > 0.00 ? ("<BR>(Insured for: " + formatter.format(insuranceValue) + ")") : "" %>
                                        </TD>
                                    </TR>


                                    <%
}
%>

                                </TABLE>

                                <!-- begin shipping billing invfo-->

                                    <%
                                        if(isInternalUser){

                                        %>
                                    <%if(order.supplies.size()>0){
    %>
                                <button onClick="$('.suppliesBreakdown').toggle();" type="button">Show Supplies Used</button>
                                <div class="suppliesBreakdown" style="display:none; padding-bottom:10px">
                                    <table border="0">
                                        <TR bgcolor="#000000">
                                            <TH style="color:#ffffff">SKU</TH>
                                            <TH style="color:#ffffff">Description</TH>
                                            <TH style="color:#ffffff">Type</TH>
                                            <TH style="color:#ffffff">Count</TH>
                                        </TR>
                                        <%
                                            for(suppliesBean supply: order.supplies){
                                        %>
                                        <tr>
                                            <td><%=supply.getSku()%></td>
                                            <td><%=supply.getDescription()%></td>
                                            <td><%=supply.getType()%></td>
                                            <td><%=supply.getQuantity()%></td>
                                        </tr>

                                        <% }
                                        %>
                                    </table>
                                </div>
                                    <% } %>



                                        <%    if(order.getShippingCharges().size()>0){
                                                %>


<button onClick="$('.shippingBreakdown').toggle();">Show Shipping Breakdown</button>
                                        <div class="shippingBreakdown" style="display:none">
                                            <table>
                                                <tr>
                                                    <th>
                                                        Recorded Date
                                                    </th>
                                                    <th>
                                                        Amount
                                                    </th>
                                                    <th>
                                                        Activity
                                                    </th>
                                                    <th>
                                                        Transaction ID
                                                    </th>
                                                    <th>
                                                        Tracking
                                                    </th>
                                                </tr>
                                                <%
                                                    for(shippingCharges charges : order.getShippingCharges()){
                                                %>
                                                <tr>
                                                    <td>
                                                        <%=charges.getRecordedDate()%>
                                                    </td>
                                                    <td>
                                                        <%=charges.getAmount()%>
                                                    </td>
                                                    <td>
                                                        <%=charges.getActivity()%>
                                                    </td>
                                                    <td>
                                                        <%=charges.getTransactionId()%>
                                                    </td>
                                                    <td>
                                                        <%=charges.getTracking()%>
                                                    </td>
                                                </tr>
                                                <%
                                                    }
                                                %>
                                            </table>
                                            <hr>
                                        </div>


                                    <%

                                            }
                                        }
                                        }
                                    %>



                                <P>
                                    <fontx size=-1 color="#000000"><B>Order Processing Events</B>
                                            <%
	
	if(order.events.size() < 1) {
%>
                                        <HR>
                                        <fontx color="#000000" size=-2><B>No events found for this order</B>
                                                <%
}else{
%>
                                            <TABLE WIDTH=100% cellspacing=0 cellpadding=2>

                                                <%

                                                    Iterator iter = order.events.iterator();
                                                    while (iter.hasNext()) {
                                                        Event item = (Event) iter.next();
                                                        String when;
                                                        String what = "Unknown Type (" + item.event_type + ")";
                                                        String msg = "";
                                                        StringBuffer msgPrint = new StringBuffer();
                                                        if (item.message != null) msg = item.message;

                                                        switch (item.event_type) {
                                                            case (Event.kEventTypeGeneral):
                                                                what = "Miscellaneous";

                                                                break;
                                                            case (Event.kEventTypeComment):
                                                                what = "Note";

                                                                break;
                                                            case (Event.kEventTypeEmailSent):
                                                                what = "Email&nbsp;Sent";
                                                                msg = "<fontx FACE=\"Courier\" ><PRE>" + msg + "</PRE>";

                                                                break;
                                                            case (Event.kEventTypeHandling):
                                                                what = "Order&nbsp;Processing";

                                                                break;
                                                            case (Event.kEventTypeEdit):
                                                                what = "Order&nbsp;Edit";

                                                                break;
                                                        }


                                                        when = item.event_stamp.substring(0, item.event_stamp.indexOf(" "));
                                                        when = when + "&nbsp;" + item.event_stamp.substring(item.event_stamp.indexOf(" ") + 1, item.event_stamp.indexOf("."));
                                                        if (item.creator.equalsIgnoreCase("NULL")
                                                                || item.creator.equalsIgnoreCase("dbo")
                                                                || item.creator.equalsIgnoreCase("guest")
                                                                || item.creator.equalsIgnoreCase("system")
                                                                || item.creator.equalsIgnoreCase("sa")) {
                                                            when = when + "<BR><B>(" + what + "&nbsp;by&nbsp;System)";
                                                        } else {
                                                            when = when + "<BR><B>(" + what + "&nbsp;by&nbsp;" + item.creator + ")";
                                                        }


                                                        int lineChars = 0;
                                                        boolean lastCharWasReturn = false;
                                                        for (int i = 0; i < msg.length(); i++) {

                                                            int theChar = msg.charAt(i);

                                                            if ((theChar == 32 || theChar == 9) && (lastCharWasReturn || lineChars == 0)) {
                                                                //skip it
                                                            } else {
                                                                if (theChar == 10 || theChar == 13) {
                                                                    if (lastCharWasReturn)
                                                                        msgPrint.append("&nbsp;");
                                                                    msgPrint.append("<BR>");
                                                                    lineChars = 0;
                                                                    lastCharWasReturn = true;

                                                                } else if (lineChars > 80) {
                                                                    msgPrint.append("<BR>");
                                                                    if ((theChar == 32 || theChar == 9)) {
                                                                        lineChars = 0;
                                                                    } else {
                                                                        msgPrint.append(msg.substring(i, i + 1));
                                                                        lineChars = 1;
                                                                    }
                                                                    lastCharWasReturn = true;

                                                                } else {
                                                                    msgPrint.append(msg.substring(i, i + 1));
                                                                    lineChars++;
                                                                    lastCharWasReturn = false;
                                                                }
                                                            }

                                                %>

                                                <%

                                                    }
                                                %>
                                                <TR>
                                                    <TD colspan=3>
                                                        <HR>
                                                    </TD>
                                                </TR>
                                                <TR>
                                                    <TD VALIGN="TOP">
                                                        <fontx size=-2><%= when %></B>
                                                    </TD>
                                                    <TD>&nbsp;&nbsp;</TD>
                                                    <TD VALIGN="TOP">
                                                        <fontx size=-2>
                                                        <%= msgPrint %>
                                                    </TD>
                                                </TR>

                                                <%
                                                    }
                                                %>
                                            </TABLE>
<%
    }

    HibernateSession.closeSession();
%>