<%@ page import="com.owd.core.business.order.OrderUtilities,
                 com.owd.hibernate.generated.OwdClient,
                 com.owd.web.internal.client.ClientHome,
                 com.owd.web.internal.navigation.UIUtilities" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%@ page import="com.owd.hibernate.generated.OwdFacility" %>
<%@ page import="java.util.*" %>
<%@ page import="com.owd.core.OWDUtilities" %>
<%@ page import="org.hibernate.criterion.Projections" %>
<%@ page import="com.owd.hibernate.generated.OwdClientBillTypes" %>
<%@ page import="org.hibernate.Criteria" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.owd.hibernate.HibernateSession" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="org.hibernate.criterion.Restrictions" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://ditchnet.org/jsp-tabs-taglib" prefix="tab" %>

<%
    try {
        OwdClient client = (OwdClient) request.getSession(true).getAttribute("client.currentclient");
        System.out.println("hello");

        pageContext.setAttribute("client", client);
        if (client.getPpProc() == null) {
            client.setPpProc("");
        }
        if (client.getPpLogin() == null) {
            client.setPpLogin("");
        }
        if (client.getPpPass() == null) {
            client.setPpPass("");
        }

        String saveBtnName = (client.getClientId() == null ? " Save New Client" : "Save Changes");
        pageContext.setAttribute("saveBtnName", saveBtnName);

        StringBuffer sb = new StringBuffer();

        System.out.println("test");
        Map<String,Map<String,String>> billOptions = new HashMap<String,Map<String,String>>();
        billOptions.put("PICK_FEE", new HashMap<String,String>());
        billOptions.get("PICK_FEE").put("1STPICKFREE","1st Pick Free");
        billOptions.put("HOURLY_RATE_RECEIVE", new HashMap<String,String>());
        billOptions.get("HOURLY_RATE_RECEIVE").put("EXACT","Actual Minutes");
        System.out.println("test1");
        List<String> fcodes = FacilitiesManager.getActiveFacilityCodes();
        Criteria criteria = HibernateSession.currentSession().createCriteria(OwdClientBillTypes.class);

        criteria.setProjection(Projections.distinct(Projections.property("code")));
        List<String> billTypes = criteria.list();
     //   Map<String, Map<String,BigDecimal>> billingMap = new TreeMap<String,Map<String,BigDecimal>>();
        StringBuffer billingStr = new StringBuffer();
            Map<String,OwdClientBillTypes> billingMap = new TreeMap<String,OwdClientBillTypes>();
            Map<String,Map<String,List<OwdClientBillTypes>>> brandBillingMap = new TreeMap<String,Map<String,List<OwdClientBillTypes>>>();
        System.out.println("test1");
        for(OwdClientBillTypes btype:client.getBillTypes()) {

            String group = "";

            try{

                group = btype.getGroupName();
                //System.out.println(group);
               System.out.println(btype.getId());


            }catch(Exception e){
                System.out.println("helloexception");
            }

            if(group == null || group.equals("")){
                System.out.println("hellozero");
                billingMap.put(btype.getLocationCode()+btype.getCode(),btype);
            }



        }
        System.out.println("Testing2");
        List<OwdClientBillTypes> groups = new ArrayList<OwdClientBillTypes>();
        if(client.getClientId() != null) {
            Criteria criteria2 = HibernateSession.currentSession().createCriteria(OwdClientBillTypes.class);
            criteria2.add(Restrictions.eq("owdClient", client));
            criteria2.add(Restrictions.ne("groupName", ""));
             groups = criteria2.list();
            System.out.println("Testing3");

        }
            for (OwdClientBillTypes btype : groups) {
                if (brandBillingMap.containsKey(btype.getLocationCode())) {
                    System.out.println("helloother");
                    //check if group is already there
                    if (brandBillingMap.get(btype.getLocationCode()).containsKey(btype.getGroupName())) {
                        System.out.println("10");
                        brandBillingMap.get(btype.getLocationCode()).get(btype.getGroupName()).add(btype);
                    } else {
                        System.out.println("11");
                        //no broup yet add
                        List<OwdClientBillTypes> l = new ArrayList<OwdClientBillTypes>();
                        brandBillingMap.get(btype.getLocationCode()).put(btype.getGroupName(), l);
                    }

                    System.out.println("12");
                } else {
                    //no facility yet
                    System.out.println("13");
                    Map<String, List<OwdClientBillTypes>> m = new TreeMap<String, List<OwdClientBillTypes>>();
                    List<OwdClientBillTypes> l = new ArrayList<OwdClientBillTypes>();
                    m.put(btype.getGroupName(), l);
                    brandBillingMap.put(btype.getLocationCode(), m);


                }

            }

        System.out.println("hello5");
        billingStr.append("<TR>");
        for(String loc:fcodes) {
          billingStr.append("<TH colspan=2>"+loc+"</TH>");
        }

          billingStr.append("</TR>");
            for(String code:billTypes)
            {
                billingStr.append("<TR>");
                for(String loc:fcodes) {
                if(billingMap.keySet().contains(loc+code)) {

                    billingStr.append("<TD>"+code.toUpperCase().replaceAll("_"," ")+"</TD><TD><INPUT name='"+loc+code+"' value='"+billingMap.get(loc+code).getRate()
                            +"'><select name='"+loc+code+"subtype'><option value='' "+("".equals(billingMap.get(loc+code).getSubtype())?"SELECTED":"")+">Standard");

                    if(billOptions.keySet().contains(code)) {
                        for(String type:billOptions.get(code).keySet()) {
                            billingStr.append("<option value='"+type+"' " + (type.equals(billingMap.get(loc + code).getSubtype()) ? "SELECTED" : "") + ">"+billOptions.get(code).get(type));
                        }
                    }
                } else {
                    billingStr.append("<TD>"+code.toUpperCase().replaceAll("_"," ")+"</TD><TD><INPUT name='"+loc+code+"' value='0.00'>" +
                            "<select name='"+loc+code+"subtype'><option value='' SELECTED>Standard");

                    if(billOptions.keySet().contains(code)) {
                        for(String type:billOptions.get(code).keySet()) {
                            billingStr.append("<option value='"+type+"' >"+billOptions.get(code).get(type));
                        }
                    }

                }
            }
                billingStr.append("</TR>");
          }
        StringBuffer sbb = new StringBuffer();

       // brandBillingMap = new TreeMap<String,Map<String,List<OwdClientBillTypes>>>();

        if(brandBillingMap.size()>0){
            for(String facility:brandBillingMap.keySet()){
                sbb.append("<div>"+facility+"<br/>");
                for(String brand:brandBillingMap.get(facility).keySet()){
                    sbb.append("&nbsp;&nbsp;&nbsp;"+brand+"<br/>");
                    for(OwdClientBillTypes btype:brandBillingMap.get(facility).get(brand)){
                        sbb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+btype.getCode()+":&nbsp;&nbsp;&nbsp;"+btype.getRate()+"<br/>");
                    }
                }
                sbb.append("</div>");


            }
        }
        System.out.println("hello8");
            pageContext.setAttribute("brandStr",sbb.toString());
        pageContext.setAttribute("billingStr", billingStr.toString());//sb.toString());


        //  int entries = OrderRater.getServiceCodes().size() / 2;
       // Iterator iter = OrderRater.getServiceCodes().iterator();
       // System.out.println("1");
      //  Map methods = new TreeMap();
      //  methods = ClientManager.getClientMethodMap(client);
       // System.out.println("2");
     //   int currEntry = 1;
      //  while (iter.hasNext()) {
       //     String key = (String) iter.next();
        //    if (currEntry == (1 + entries)) {

         //       sb.append("</TD><TD NOWRAP ALIGN=LEFT VALIGN=TOP>");
          //  }
         //   sb.append("<input type=\"CHECKBOX\" name=\"svc" + key + "\" VALUE=\"1\" " + (methods.containsKey(key) ? "CHECKED" : "")
           //         + ">&nbsp;<input type=\"TEXT\" size=\"6\" name=\"" + key + "pct\" VALUE=\""
             //       + (methods.containsKey(key) ? "" + (Double) methods.get(key) : "0.00") + "\">&nbsp;<font size=\"-2\">"
               //     + OrderRater.getRateableServicesMap().get(key) + "</font><br>");

           // currEntry++;
       // }
        pageContext.setAttribute("shipMethodBlock", "");//sb.toString());


        String originalShipCarrier = OrderUtilities.getServiceList().getHTMLSelect(client.getOriginalShipCarrier(), "originalShipCarrier");
        String partialShipCarrier = OrderUtilities.getServiceList().getHTMLSelect(client.getPartialShipCarrier(), "partialShipCarrier");

        pageContext.setAttribute("originalShipCarrier", originalShipCarrier);
        pageContext.setAttribute("partialShipCarrier", partialShipCarrier);
        UIUtilities.setRequestNavigationElements(UIUtilities.kClientAreaName, "Editing Client", request);



        List<OwdFacility> facilities = FacilitiesManager.getActiveFacilities();
        StringBuffer fsb = new StringBuffer();
        fsb.append(" <select name=\"defaultFacilityCode\">");
        for(OwdFacility f: facilities)
        {

                fsb.append("<OPTION VALUE=\""+f.getFacilityCode()+"\" "+(client.getDefaultFacilityCode().equals(f.getFacilityCode())?"SELECTED":"")+">"+f.getFacilityCode()+" - "+f.getCity()+", "+f.getState()+"");

        }
        fsb.append("<OPTION VALUE=\"ALL\" "+(client.getDefaultFacilityCode().equals("ALL")?"SELECTED":"")+">Multiple Facilities");
        fsb.append("</select>");

        pageContext.setAttribute("fsbstr", fsb.toString());

        String clientAuth = OWDUtilities.encryptData(""+client.getClientId());

        pageContext.setAttribute("clientauth", clientAuth);
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <tab:tabConfig />
    <title><%= request.getAttribute("lf_CURRMANAGER") %></title>
    <jsp:include page="/includes/owdtop.jsp"/>

<P>
<font color=red>
    <B><%= (request.getAttribute("errormessage") != null ? "<div class=\"AlertBad\">" + request.getAttribute("errormessage") + "</div>" : "") %></B>
</font>
<form method="POST" action="/internal/client/edit">
<input type="HIDDEN" name="<%=ClientHome.kParamAdminAction%>" value="create-save">
<input type="HIDDEN" name="client_id" value="<%= client.getClientId()==null?0:client.getClientId()%>">
<TABLE border=0 WIDTH=100%><TR><TD align=left width=100%>
<table border="0" width=100%>
<tr>
    <td colspan="2" align=CENTER>
        <hr>
        <input type="SUBMIT" name="" value="${saveBtnName}">
        <hr>
    </td>
</tr>
</table>
<tab:tabContainer id="client-edit-container">
<tab:tabPane id="clientmain" tabTitle="Main">
<table>
<tr>
    <td align="RIGHT">
        <font size="-2"><b></b></font>
    </td>
    <td align="LEFT">
        <INPUT TYPE=CHECKBOX NAME=isActive VALUE="1" <c:if test="${(client.isActive)}"> CHECKED</c:if>><font color="#000000"
                                                                                                     size="-1"><b>&nbsp;Active&nbsp;Client</b>
    </font>
    </td></tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b></b></font>
    </td>
    <td align="LEFT">
        <INPUT TYPE=CHECKBOX NAME=isShippingOnHold
               VALUE="1" <c:if test="${(client.isShippingOnHold == 1)}"> CHECKED</c:if>><font color="#000000"
                                                                                                size="-1"><b>&nbsp;Shipping&nbsp;On&nbsp;Hold</b>
    </font>
    </td></tr>

    <tr>
        <td align="RIGHT">
            <font size="-2"><b></b></font>
        </td>
        <td align="LEFT">
            <INPUT TYPE=CHECKBOX NAME=billWeekly
                   VALUE="1" <c:if test="${(client.billWeekly)}"> CHECKED</c:if>><font color="#000000"
                                                                                                  size="-1"><b>&nbsp;Bill Client Weekly</b>
        </font>
        </td></tr>
    <tr>
        <td align="RIGHT">
            <font size="-2"><b></b></font>
        </td>
        <td align="LEFT">
            <INPUT TYPE=CHECKBOX NAME=billCubicStorage
                   VALUE="1" <c:if test="${(client.billCubicStorage)}"> CHECKED</c:if>><font color="#000000"
                                                                                       size="-1"><b>&nbsp;Bill Cubic Storage Weekly</b>
        </font>
        </td></tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b></b></font>
    </td>
    <td align="LEFT">
        <INPUT TYPE=CHECKBOX NAME=chargeOnShip
               VALUE="1" <c:if test="${(client.chargeOnShip == 1)}"> CHECKED</c:if>><font color="#000000"
                                                                                                size="-1"><b>&nbsp;Charge&nbsp;On&nbsp;Ship</b>
    </font><br>
       <font color="red">DO NOT MODIFY THE CHARGE ON SHIP VALUE!</font>
    </td></tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b></b></font>
    </td>
    <td align="LEFT">
        <INPUT TYPE=text NAME=shipperSymbol
               VALUE="${fn:replace(client.shipperSymbol,' ','_')}"><font color="#000000"
                                                                                                size="-1"><b>&nbsp;Intacct&nbsp;Client&nbsp;Code</b>
    </font>
    </td></tr>
<tr><td></td><td>
    <font size="-2">Use the underscore (_) for spaces</font>
</td>
</tr>
      <tr>
    <td align="RIGHT">
        <font size="-2"><b>Default&nbsp;DC:&nbsp;</b></font>
    </td>
    <td align="LEFT">
${fsbstr}
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Client Official Name:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="companyName" size="32" value="${client.companyName}">
    </td>

</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Account&nbsp;Mgr&nbsp;Email&nbsp;Address:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="amEmail" size="32" value="${client.amEmail}">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">One email address only</font>
</td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Contact&nbsp;Center&nbsp;Group&nbsp;Name:&nbsp;</b></font>
    </td>
    <td align="LEFT">
         <select name="ccGroupName"><OPTION VALUE="24/7"
           <c:if test="${(client.ccGroupName eq '24/7')}"> SELECTED</c:if>
                >24/7 <OPTION VALUE="7-9"
                 <c:if test="${(client.ccGroupName eq '7-9')}"> SELECTED</c:if>
                >7-9 <OPTION VALUE="8-5"
                 <c:if test="${(client.ccGroupName eq '8-5')}"> SELECTED</c:if>
                >8-5 <OPTION VALUE="INTERNAL"
                 <c:if test="${(client.ccGroupName eq 'INTERNAL') || (client.ccGroupName eq '') }"> SELECTED</c:if>
                >Internal
        </select>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Private Carrier Discount Sharing Percentage:&nbsp;</b></font>
    </td>
    <td align="LEFT">

        <input type="TEXT" name="discountSharePct" size="10" value="<fmt:formatNumber maxFractionDigits='2'  value='${client.discountSharePct}'/>">
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b>USPS Priority/Express (domestic) per package add-on fee:&nbsp;</b></font>
    </td>
    <td align="LEFT">

        <input type="TEXT" name="uspsPriorityExpressPackFee" size="10" value="<fmt:formatNumber maxFractionDigits='2'  value='${client.uspsPriorityExpressPackFee}'/>">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">0.00 or more, the amount to add to the base ship cost for USPS Priority and Priority Express shipments (domestic). Entering 0.10 will add ten cents to each eleigible package. 0.10 is the default fee, anything less than that is considered a discount.</font>
</td>
</tr>
    <tr>
        <td align="RIGHT">
            <font size="-2"><b>USPS All Other Method per package add-on fee:&nbsp;</b></font>
        </td>
        <td align="LEFT">

            <input type="TEXT" name="uspsOtherPackFee" size="10" value="<fmt:formatNumber maxFractionDigits='2'  value='${client.uspsOtherPackFee}'/>">
        </td>
    </tr>
    <tr><td></td><td>
    <font size="-2">Enter amount to be applied to other USPS methods besides Priority and Express methods.</font>
    </td>
    </tr>
    <tr>
        <td align="RIGHT">
            <font size="-2"><b>Passport fee:&nbsp;</b></font>
        </td>
        <td align="LEFT">

            <input type="TEXT" name="passportFee" size="10" value="<fmt:formatNumber maxFractionDigits='2'  value='${client.passportFee}'/>">
        </td>
    </tr>

    <tr>
    <td align="RIGHT">
    <font size="-2"><b>Domestic Dim Factor:&nbsp;</b></font>
    </td>
    <td align="LEFT">

    <input type="TEXT" name="dimFactor" size="10" value="<fmt:formatNumber maxFractionDigits='2'  value='${client.dimFactor}'/>">
    </td>
    </tr>
    <tr><td></td><td>
    <font size="-2">(Depricated)Enter negotiated dim factor. If client does not have one enter 0.</font>
    </td>
    </tr>
    <tr>
    <td align="RIGHT">
    <font size="-2"><b>International Dim Factor:&nbsp;</b></font>
    </td>
    <td align="LEFT">

    <input type="TEXT" name="intlDimFactor" size="10" value="<fmt:formatNumber maxFractionDigits='2'  value='${client.intlDimFactor}'/>">
    </td>
    </tr>
    <tr><td></td><td>
    <font size="-2">(Depricated)Enter negotiated dim factor. If client does not have one enter 0.</font>
    </td>
    </tr>
    <tr>
        <td><b>Flatrate Dim Factor:</b></td><td> <a href="/internal/flatRateDims/loadClient.action?clientId=${client.clientId}" target="_blank">Set Flat Rate Dim factors</a>
</td></tr>

    <tr>
        <td align="RIGHT">
            <font size="-2"><b>API access credentials:&nbsp;</b></font>
        </td>
        <td align="LEFT">
        <P>
           <B>Client ID: ${client.clientId}</B><br>
           <B>Client Authorization Code: ${clientauth} </B>
        </td>
    </tr>
    <tr><td></td><td>
        <font size="-2">Values to give clients for access to OWD API</font>
    </td>
    </tr>
<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
<tr>
    <td colspan="2">
        Contact Information:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Address 1:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="addressOne" size="32" value="${client.addressOne}">
    </td>

</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Address 2:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="addressTwo" size="32" value="${client.addressTwo}">
    </td>

</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> City:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="city" size="32" value="${client.city}">
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> State:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="state" size="32" value="${client.state}">
    </td>

</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Zip:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="zip" size="32" value="${client.zip}">
    </td>

</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Country:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="country" size="32" value="${client.country}">
    </td>

</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Contact Name:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="contactName" size="32" value="${client.contactName}">
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Phone:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="primaryPhoneNum" size="32" value="${client.primaryPhoneNum}">
    </td>

</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Email:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="primaryEmailAddress" size="32" value="${client.primaryEmailAddress}">
    </td>

</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Fax:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="primaryFaxNum" size="32" value="${client.primaryFaxNum}">
    </td>

</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Web:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="urlString" size="32" value="${client.urlString}">
    </td>

</tr>
<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
</table>
</tab:tabPane>
<tab:tabPane id="clientreceiving" tabTitle="Receiving"><table>
<tr>
    <td colspan="2">
        Receiving Management Configuration:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Client&nbsp;Receive&nbsp;Notification&nbsp;Email:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="asnRcvEmail" size="32" value="${client.asnRcvEmail}">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">
        Multiple addresses separated by commas OK; maximum 255 characters total
</td>

</tr>
<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>

        </table>
</tab:tabPane>
<tab:tabPane id="clientinventory" tabTitle="Inventory"> <table>
<tr>
    <td colspan="2">
        Inventory Management Configuration:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b></b></font>
    </td>
    <td align="LEFT" valign="TOP">
        <INPUT TYPE=CHECKBOX NAME=lowInventoryAlert VALUE="1"
        <c:if test="${(client.lowInventoryAlert==1)}"> CHECKED</c:if>><font size="-2"><b>&nbsp;Send&nbsp;Low&nbsp;Inventory&nbsp;Alerts</b>
    </font>
    </td>
</tr>

<tr>
    <td align="RIGHT">
        <font size="-2"><b>Client&nbsp;Notification&nbsp;Email:&nbsp;</b></font>
    </td>
    <td align="LEFT" valign="TOP"><input type="TEXT" name="lowInventoryEmail" size="40"
                                         value="${client.lowInventoryEmail}">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">Multiple addresses separated by commas OK; maximum 255 characters total</font></td></tr>

<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>

  </table>
</tab:tabPane>
<tab:tabPane id="clientautoship" tabTitle="Autoship">  <table>
<tr>
    <td colspan="2">
        AutoShip/Continuity Order Configuration:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Client&nbsp;AutoShip&nbsp;Notification&nbsp;Email:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="autoShipEmail" size="32" value="${client.autoShipEmail}">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">
        Multiple addresses separated by commas OK; maximum 255 characters total
</td>
</tr>
<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>

</table>
</tab:tabPane>
<tab:tabPane id="clientshipping" tabTitle="Shipping">
<table>
<tr>
    <td colspan="2">
        Shipping Configuration:
        <hr>
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">
        By default, shipping labels show a return address of the OWD warehouse, with the client's official company name
        (above) as the addressee. Client who wish their shipping labels to show a different
        address can have that information entered here. This will cause the shipping labels to print the indicated
        address as the return address.
</td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Label Return Line 1</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="retAddr1" size="32" value="${client.retAddr1}">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">
        (Street Address)&nbsp;-&nbsp;Up to 30 characters&nbsp;
</td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Label Return Line 2</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="retAddr2" size="32" value="${client.retAddr2}">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">
        Format as: (City, State Zip)&nbsp;Up to 30 characters:&nbsp;
</td>
</tr>
<tr>
    <td align="RIGHT" valign="TOP">

    </td>
    <td align="LEFT" valign="TOP">
        <INPUT TYPE=CHECKBOX NAME="usedcFirstclass" VALUE="1"
        <c:if test="${(client.usedcFirstclass==1)}"> CHECKED</c:if>
                >&nbsp;<font size="-2"><b> Force&nbsp;Delivery&nbsp;Confirmation&nbsp;</b></font>
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">If checked, USPS First Class, Media Mail and Bound Printed Matter services will use Delivery
        Confirmation.<BR>This service adds appx. 0.19 per shipment to the USPS postage.</font>
</td>
<tr>
<tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b> Services Available:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <TABLE border=0><TR><TD NOWRAP ALIGN=LEFT VALIGN=TOP>
        ${shipMethodBlock}
        </td></tr></table>
    </td>
</tr>
<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
 <tr>
    <td colspan="2">
        Custom Shipping Accounts Configuration:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> FedEx Account Number:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="fedexAcct" value="${client.fedexAcct}">
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> UPS Account Number:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="upsAcct" value="${client.upsAcct}">
    </td>
</tr>
<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
</table>
</tab:tabPane>
<tab:tabPane id="clientshipinvoice" tabTitle="Shipping Invoices">
    <table>
<tr>
    <td colspan="2">
        Shipping Account Invoices Configuration:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b>Credit Limit:</b></font>
    </td>
    <td align="LEFT" valign="TOP">${(client.shipInvoiceConfig.creditLimit)}
    </td>
</tr>

<tr>
    <td align="RIGHT">
        <font size="-2"><b></b></font>
    </td>
    <td align="LEFT" valign="TOP">
        <INPUT TYPE=CHECKBOX NAME=notifyFlag VALUE="1"
        <c:if test="${(client.shipInvoiceConfig.notifyFlag==1)}"> CHECKED</c:if>><font size="-2"><b>&nbsp;Send&nbsp;Daily&nbsp;Account&nbsp;Status&nbsp;Alerts</b>
    </font>
    </td>
</tr>

<tr>
    <td align="RIGHT">
        <font size="-2"><b>Account&nbsp;Status&nbsp;Email(s):&nbsp;</b></font>
    </td>
    <td align="LEFT" valign="TOP"><input type="TEXT" name="notifyEmails" size="40"
                                         value="${client.shipInvoiceConfig.notifyEmails}">
    </td>
</tr>
 <tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
        <tr>
    <td align="RIGHT">
        <font size="-2"><b></b></font>
    </td>
    <td align="LEFT" valign="TOP">
        <INPUT TYPE=CHECKBOX NAME=invoiceFlag VALUE="1"
        <c:if test="${(client.shipInvoiceConfig.invoiceFlag==1)}"> CHECKED</c:if>><font size="-2"><b>&nbsp;Send&nbsp;Invoices</b>
    </font>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b>Invoice&nbsp;Email(s):&nbsp;</b></font>
    </td>
    <td align="LEFT" valign="TOP"><input type="TEXT" name="invoiceEmails" size="40"
                                         value="${client.shipInvoiceConfig.invoiceEmails}">
    </td>
</tr>
 <tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
        <tr>
    <td align="RIGHT">
        <font size="-2"><b>Send&nbsp;Invoice&nbsp;When:&nbsp;</b></font>
    </td>
    <td align="LEFT">

        <select name="invoiceTriggerType"><OPTION VALUE="WEEKLY"
           <c:if test="${(client.shipInvoiceConfig.invoiceTriggerType eq 'WEEKLY')}"> SELECTED</c:if>
                >Weekly (Saturday) <OPTION VALUE="AT_TRIGGER_LEVEL"
                <c:if test="${(client.shipInvoiceConfig.invoiceTriggerType eq 'AT_TRIGGER_LEVEL')}"> SELECTED</c:if>
                >When balance reaches trigger level (below)
        </select>
    </td>
</tr>
 <tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b>Trigger Level:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type=text name="invoiceTriggerLevel" size="15" value="${client.shipInvoiceConfig.invoiceTriggerLevel}" />
        <br><font size=-2>Enter a decimal dollar amount if using trigger level to send invoices (above)</font>
        </td>
</tr>
 <tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
  <tr>
    <td align="RIGHT">
        <font size="-2"><b>Invoice&nbsp;Amount&nbsp;Type:&nbsp;</b></font>
    </td>
    <td align="LEFT">

        <select name="invoiceAmountType"><OPTION VALUE="BALANCE"
           <c:if test="${(client.shipInvoiceConfig.invoiceAmountType eq 'BALANCE')}"> SELECTED</c:if>
                >Balance Due<OPTION VALUE="FIXED_AMOUNT"
                <c:if test="${(client.shipInvoiceConfig.invoiceAmountType eq 'FIXED_AMOUNT')}"> SELECTED</c:if>
                >Fixed Amount (set below) <OPTION VALUE="DIFFERENCE"
                <c:if test="${(client.shipInvoiceConfig.invoiceAmountType eq 'DIFFERENCE')}"> SELECTED</c:if>
                >Difference between current balance and invoice amount (set below)
        </select>
    </td>
</tr>
         <tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b>Invoice Amount:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type=text name="invoiceAmount" size="15" value="${client.shipInvoiceConfig.invoiceAmount}" />
        <br><font size=-2>Enter a decimal dollar amount to use when calculating invoice amount (above)</font>
        </td>
</tr>
 <tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
    <tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b>Invoice Addressee:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <textarea name="invoiceToAddress" rows="7" cols="50">${client.shipInvoiceConfig.invoiceToAddress}</textarea>
        <br><font size=-2>Enter the full name and address block to appear on the invoice (who is paying the invoice)</font>
    </td>
</tr>
 </table>

</tab:tabPane>
<tab:tabPane id="clientexternal" tabTitle="Integrations">
<table>
<tr>
    <td colspan="2">
        Yahoo Store:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Send&nbsp;Updates&nbsp;to&nbsp;Yahoo:&nbsp;</b></font>
    </td>
    <td align="LEFT" valign="TOP">
        <INPUT TYPE=CHECKBOX NAME=tellYahoo VALUE="1"
        <c:if test="${(client.tellYahoo==1)}"> CHECKED</c:if>
                ><font size="-2"><b>&nbsp;&nbsp;Yahoo&nbsp;Password:&nbsp;</b></font><input type="TEXT" name="yahooPass"
                                                                                            size="8"
                                                                                            value="${client.yahooPass}">
    </td>
</tr>
<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
    <tr>
    <td align="RIGHT" valign=top>
        <font size="-2"><b> Download&nbsp;Paypal&nbsp;Orders:&nbsp;</b></font>
    </td>
    <td align="LEFT" valign="TOP">
        <INPUT TYPE=CHECKBOX NAME=usePaypal VALUE="1"
        <c:if test="${(client.paypalData.usePaypal==1)}"> CHECKED</c:if>
                ><font size="-2"><p>
        <b>&nbsp;&nbsp;Paypal&nbsp;API&nbsp;Username:&nbsp;</b></font>
        <input type="TEXT" name="paypalApiUsername" value="${client.paypalData.paypalApiUsername}">
        <p>
                <b>&nbsp;&nbsp;Paypal&nbsp;API&nbsp;Password:&nbsp;</b></font>
                <input type="TEXT" name="paypalApiPassword" value="${client.paypalData.paypalApiPassword}">
        <p>
                <b>&nbsp;&nbsp;Paypal&nbsp;API&nbsp;Signature:&nbsp;</b></font>
                <input type="TEXT" name="paypalApiSignature" value="${client.paypalData.paypalSignature}">
          </td>
</tr>
  </table>
</tab:tabPane>
<tab:tabPane id="clientcustomeremails" tabTitle="Customer Emails">
<table><tr>
    <td colspan="2">
        Customer Emails Configuration:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">

    </td>
    <td align="LEFT">
        <INPUT TYPE=CHECKBOX NAME=shipEmail VALUE="1"
        <c:if test="${(client.shipEmail==1)}"> CHECKED</c:if>
                >&nbsp;<font size="-2"><b> Email Customers on Shipment&nbsp;</b></font>
    </td>
</tr>
<tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b> Return Email Address:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="shipEmailFrom" size="32" value="${client.shipEmailFrom}">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">Only one address allowed; if left blank, return address will be do-not-reply@owd.com, which is not
        read or responded to</font>
</td>
</tr>
<tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b> CC Emails To:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="shipEmailCc" size="60" value="${client.shipEmailCc}">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">Separate multiple addresses with a comma</font>
</td>
</tr>
<tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b> Bcc Emails To:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="shipEmailBcc" size="60" value="${client.shipEmailBcc}">
    </td>
</tr>
<tr><td></td><td>
    <font size="-2">Separate multiple addresses with a comma</font>
</td>
</tr>
<tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b> Custom Email Footer:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <textarea name="shipEmailFtr" rows="6" cols="50">${client.shipEmailFtr}</textarea>
    </td>
</tr>
<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
 </table>
</tab:tabPane>
<tab:tabPane id="clientbackorders" tabTitle="Backorders">
<table>
<tr>
    <td colspan="2">
        Automatic Backorder Shipping Configuration:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">

    </td>
    <td align="LEFT">
        <INPUT TYPE=CHECKBOX NAME=isBackShip VALUE="1"
 <c:if test="${(client.isBackship==1)}"> CHECKED</c:if>
                >&nbsp;<font size="-2"><b> Ship Fillable Backorders Automatically&nbsp;</b></font>
    </td>
</tr>
<tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b> When the backorder is the full, original order:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <font size="-2"><INPUT TYPE=RADIO NAME=originalShipType VALUE=0
        <c:if test="${(client.originalShipType==0)}"> CHECKED</c:if>
                >Use the original orders shipping method
            <br>
            <INPUT TYPE=RADIO NAME=originalShipType VALUE=1
           <c:if test="${(client.originalShipType==1)}"> CHECKED</c:if>
                    >Use this shipping method:
            ${originalShipCarrier}
        </td>
</tr>
<tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b> When the backorder is the final shipment of a partially fulfilled order:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <font size="-2"><INPUT TYPE=RADIO NAME=partialShipType VALUE=0
           <c:if test="${(client.partialShipType==0)}"> CHECKED</c:if>
                >Use the backorder's current shipping method
            <br>
            <INPUT TYPE=RADIO NAME=partialShipType VALUE=1
           <c:if test="${(client.partialShipType==1)}"> CHECKED</c:if>
                    >Use this shipping method:
            ${partialShipCarrier}
        </td>
</tr>
<tr>
    <td colspan="2">
        <p>
            <br>

        <p>
    </td>
</tr>
</table>


</tab:tabPane>
<tab:tabPane id="clientccs" tabTitle="Credit Cards">
<table>
<tr>
    <td colspan="2">
        Credit Card Processing Configuration:
        <hr>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Processor:&nbsp;</b></font>
    </td>
    <td align="LEFT">

        <select name="ppProc"><OPTION VALUE=""<c:if test="${(client.ppProc eq '')}"> SELECTED</c:if>>None
            <OPTION VALUE="com.owd.core.payment.AuthorizeNetProcessor"<c:if test="${(client.ppProc eq 'com.owd.core.payment.AuthorizeNetProcessor')}"> SELECTED</c:if>>AuthorizeNet <3.1
            <OPTION VALUE="com.owd.core.payment.AuthorizeNet31Processor"<c:if test="${(client.ppProc eq 'com.owd.core.payment.AuthorizeNet31Processor')}"> SELECTED</c:if>>AuthorizeNet 3.1+
            <OPTION VALUE="com.owd.core.payment.VerisignProcessor"<c:if test="${(client.ppProc eq 'com.owd.core.payment.VerisignProcessor')}"> SELECTED</c:if>>Verisign
            <OPTION VALUE="com.owd.core.payment.LinkPointProcessor"<c:if test="${(client.ppProc eq 'com.owd.core.payment.LinkPointProcessor')}"> SELECTED</c:if>>LinkPoint/YourPay
            <OPTION VALUE="com.owd.core.payment.FirstDataProcessor"<c:if test="${(client.ppProc eq 'com.owd.core.payment.FirstDataProcessor')}"> SELECTED</c:if>>First Data E4 Global Gateway
            <OPTION VALUE="com.owd.core.payment.USAePayProcessor"<c:if test="${(client.ppProc eq 'com.owd.core.payment.USAePayProcessor')}"> SELECTED</c:if>>USAePay
            <OPTION VALUE="com.owd.core.payment.IBillProcessor"<c:if test="${(client.ppProc eq 'com.owd.core.payment.IBillProcessor')}"> SELECTED</c:if>>IBill
            <OPTION VALUE="com.owd.core.payment.TransFirstProcessor"<c:if test="${(client.ppProc eq 'com.owd.core.payment.TransFirstProcessor')}"> SELECTED</c:if>>TransFirst
            <OPTION VALUE="com.owd.core.payment.SkipjackProcessor"<c:if test="${(client.ppProc eq 'com.owd.core.payment.SkipjackProcessor')}"> SELECTED</c:if>>SkipJack </select>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Login:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="ppLogin" value="${client.ppLogin}">
    </td>
</tr>
<tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b> &nbsp;
            <br>
            Password:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="ppPass" value="${client.ppPass}">

        <p>
        <hr>
        <b>Card Processor Notes:</b>
        <hr>
        <ol>
            <li>
                <b>AuthorizeNet</b>
                <ul>
                    <li>
                        No special setup required. Login and password is same as logging into AuthorizeNet web pages to
                        manage account.
                </ul>
            <li>
                <b>Verisign</b>
                <ul>
                    <li>
                        Verisign's account management web page requests three entries - login, password and "Partner".
                        When entering a VeriSign account, the OWD login is the Verisign login, but the password needs to
                        be constructed by appending the character "|" (shift-\) to the web page password and then
                        appending the Partner value.
                        <br>
                        For example, a password of "dog" and Partner "Hello" would mean entering the text "dog|Hello" in
                        the password field above.
                    </ul>
            <li>
                <b>LinkPoint</b>
                <ul>
                    <li>
                        LinkPoint has a simple username and password scheme like AuthorizeNet, but requires the use of a
                        special authorization file per client that must be installed by IT before use. This is only
                        required when the account is set up for the first time or if the login needs to change - the
                        password can be changed at any time safely.
                </ul>
            <li>
                <b>USA ePay</b>
                <ul>
                    <li>
                        USAePay has a single authorization code or "hash". This should be obtained by the client or IT
                        from the "Sources" setup on the USAEPay site. The code value goes into the login field - the
                        password field may be left blank.
                </ul>
            <li>
                <b>iBill</b>
            <ul>
                <li>
                    iBill has a login and password. The login is the client's iBill account with the relevant
                    "subaccount" appended to the account number. This will end up as a single value with no spaces, all
                    numbers. The password is their password as assigned when setting up their gateway account with
                    iBill.
            </ul>
               <li>
                <b>SkipJack</b>
            <ul>
                <li>
                    SkipJack has a client HTML key to be put in the password field. The login field may be left empty and will be ignored.
            </ul>
        </ol>
    </td>
</tr>
</table>

 </tab:tabPane>
<tab:tabPane id="echeck" tabTitle="Echeck Info">
 <table>
<tr>
    <td colspan="2">
       Electronic Check Configuration:
        <hr>
    </td>
</tr>
     <tr>
         <td>
             Do E-Check??
         </td>
         <td>
             <INPUT TYPE=CHECKBOX NAME=doEcheck VALUE="1" <c:if test="${(client.doEcheck==1)}"> CHECKED</c:if>>
         </td>
     </tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Processor:&nbsp;</b></font>
    </td>
    <td align="LEFT">

        <select name="checkProc"><OPTION VALUE=""
           <c:if test="${(client.checkProc eq '')}"> SELECTED</c:if>
                >None <OPTION VALUE="com.owd.core.payment.TransFirstProcessor"
                <c:if test="${(client.checkProc eq 'com.owd.core.payment.TransFirstProcessor')}"> SELECTED</c:if>
                >TransFirst</select>
    </td>
</tr>
<tr>
    <td align="RIGHT">
        <font size="-2"><b> Login:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="checkLogin" value="${client.checkLogin}">
    </td>
</tr>
<tr>
    <td align="RIGHT" valign="TOP">
        <font size="-2"><b> &nbsp;
            <br>
            Password:&nbsp;</b></font>
    </td>
    <td align="LEFT">
        <input type="TEXT" name="checkPass" value="${client.checkPass}">

    </td>
</tr>
</table>


    </tab:tabPane>

<tab:tabPane id="instructions" tabTitle="Special Instructions">
 <table>
<tr>
    <td colspan="2">
       Special Instructions By Activity:
        <hr>
    </td>
</tr>

<tr>
    <td align="RIGHT">
        <font size="-2"><b> Packing:&nbsp;</b></font>
    </td>
    <td align="LEFT">
         <textarea name="packingInstructions" rows="6" cols="50">${client.packingInstructions.instructions}</textarea>
    </td>
</tr>
</table>


    </tab:tabPane>

        <tab:tabPane id="billing" tabTitle="Billing">
        <table>
            <tr>
                <td colspan="2">
                    Autobilling Configuration (changes reflected only for events from the data of change forward)
                    <hr>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <BR>
                   <TABLE>
                       ${billingStr}
                   </TABLE>
                </td>
            </tr>
        </table>
        ${brandStr}

        </tab:tabPane>

    <tab:tabPane id="Bulky Sku Billing" tabTitle="Bulky Sku Billing">
        <table>
            <tr>
                <td colspan="2">
                    Use the following setting to override the default setting to set bulky sku flag.<br>
                    Cubic Feet is decimal feet.   Weight is decimal pounds.<br><br>
                    Default Cubic Feet being set will over ride default lengths. 
                    <hr>
                </td>
            </tr>


            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pick Cubic Feet Default</b></font>
                </td>
                           
                
                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPickCubicFeetDefault" size="20"
                                                     value="${client.bulkyPickCubicFeetDefault}">
                </td>
            </tr>
            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pick Long Default</b></font>
                </td>


                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPickLongDefault" size="20"
                                                     value="${client.bulkyPickLongDefault}">
                </td>
            </tr>
            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pick Median Default</b></font>
                </td>


                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPickMedianDefault" size="20"
                                                     value="${client.bulkyPickMedianDefault}">
                </td>
            </tr>
            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pick Short Default</b></font>
                </td>


                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPickShortDefault" size="20"
                                                     value="${client.bulkyPickShortDefault}">
                </td>
            </tr>

            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pick Weight Default</b></font>
                </td>
                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPickWeightDefault" size="20"
                                                     value="${client.bulkyPickWeightDefault}">
                </td>
            </tr>



            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pack Cubic Feet Default</b></font>
                </td>
                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPackCubicFeetDefault" size="20"
                                                     value="${client.bulkyPackCubicFeetDefault}">
                </td>
            </tr>
            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pack Long Default</b></font>
                </td>


                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPackLongDefault" size="20"
                                                     value="${client.bulkyPackLongDefault}">
                </td>
            </tr>
            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pack Median Default</b></font>
                </td>


                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPackMedianDefault" size="20"
                                                     value="${client.bulkyPackMedianDefault}">
                </td>
            </tr>
            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pack Short Default</b></font>
                </td>


                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPackShortDefault" size="20"
                                                     value="${client.bulkyPackShortDefault}">
                </td>
            </tr>

            <tr>
                <td align="RIGHT">
                    <font size="-2"><b>Bulky Pack Weight Default</b></font>
                </td>
                <td align="LEFT" valign="TOP"><input type="TEXT" name="bulkyPackWeightDefault" size="20"
                                                     value="${client.bulkyPackWeightDefault}">
                </td>
            </tr>
        </table>

    </tab:tabPane>

</tab:tabContainer>
</td></tr></table>
</form>


<jsp:include page="/includes/owdbottom.jsp"/>
</BODY>
</HTML>

<% } catch (Exception ex) {
    ex.printStackTrace();
}%>
