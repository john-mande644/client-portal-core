<%@ page import="com.owd.core.OWDUtilities"%>
<%@ page import="com.owd.core.business.PackageRate"%>
<%@ page import="com.owd.core.business.order.Inventory"%>
<%@ page import="com.owd.core.business.order.LineItem"%>
<%@ page import="com.owd.core.business.order.OrderStatus"%>
<%@ page import="com.owd.core.csXml.OrderRater"%>
<%@ page import="com.owd.extranet.servlet.ExtranetManager"%>
<%@ page import="com.owd.extranet.servlet.ExtranetServlet"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%

    java.text.DateFormat dformat = new java.text.SimpleDateFormat("MM/dd/yyyy", Locale.US);

     Date holdUntilDate = Calendar.getInstance().getTime();

    if(request.getParameter("holdUntilDate")!= null)
    {
        try

        {
            holdUntilDate = dformat.parse(request.getParameter("holdUntilDate"));
        }   catch(Exception exd)
        {

        }
    }

    OrderStatus order = (com.owd.core.business.order.OrderStatus)request.getSession(true).getAttribute(com.owd.extranet.servlet.OrdersManager.kCurrentOrder);
    java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
    com.owd.core.business.Contact shipc = order.shipping.shipContact;
    com.owd.core.business.Address shipa = order.shipping.shipAddress;
    com.owd.core.business.order.ShippingInfo shipping = order.shipping;

    Calendar now = Calendar.getInstance();
    while(now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
    {
        now.add(Calendar.DATE, 1);
    }

    String wgterror = "";

    String reqwgtStr = request.getParameter("reqwgt");
    float reqwgt = (float) 0.00;
    if(reqwgtStr == null) reqwgt = (float)0.00;
    try{
        reqwgt = new Float(reqwgtStr).floatValue();
    }catch(Exception fltex)
    {
        wgterror = "Weight must be a number";
        reqwgt = (float)0.00;
    }
    System.out.println("ratecxv");System.out.flush();
    OrderRater rater = null;
    try
    {
    System.out.println("ratecxeee");System.out.flush();
    rater = new OrderRater(order);
    System.out.println("ratecveee");System.out.flush();



    if(reqwgt > (float)0.00)
        rater.rate(reqwgt,order.getLocation());
    else
        rater.rate(order.getLocation());


    }catch(Throwable ex)
    {
        System.out.println("ratecveeexxx");System.out.flush();
        ex.printStackTrace();
    }
    System.out.println("ratecx");

 %>         

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
<P>
<fontx  size=-1>
 
<B>Compare Shipping Methods<HR><A HREF="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=edit&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>"><-Go back to order editing page</A></B>
<% if(null != request.getAttribute("formerror")){%>(<BR><fontx COLOR=RED><B><%= (String)request.getAttribute("formerror") %></B>
<% }%><P>
<TABLE WIDTH=100% border=0 cellspacing=0 >
<TR><TD WIDTH=20% VALIGN=TOP>
<fontx  SIZE=-2><B>Shipping to: </B><BR><%= shipc.name+"<BR>"+shipa.company_name+"<BR>"+shipa.address_one+"<BR>"+shipa.address_two %><BR><%= shipa.city %>,<%= shipa.state %> <%= shipa.zip %><BR><%= shipa.country %>
</TD><TD WIDTH=80% VALIGN=TOP>
<TABLE width=100% border=0 cellspacing=0 >
<TR bgcolor="#649CCC"><TH ALIGN=LEFT ><fontx  COLOR=WHITE SIZE=-1>SKU</TH><TH ALIGN=LEFT ><fontx  COLOR=WHITE SIZE=-1>Description</TH><TH ALIGN=LEFT ><fontx  COLOR=WHITE SIZE=-1>Count</TH>
    <TH ALIGN=LEFT  align=CENTER ><fontx  COLOR=WHITE SIZE=-1><B>&nbsp;Weight</B></TH></TR>
    <%
String bgcolor = "#E4ECF4";
int itemCount = order.items.size();
boolean hasZeroWeights = false; 
float pkgwgt = (float)0.00;
System.out.println("ratex");


		for(int i=0;i<itemCount;i++)
		{
		
		if(((com.owd.core.business.order.LineItem)order.items.get(i)).quantity_actual > 0)
			{
			float unitwgt = Inventory.getWeight(((LineItem)order.items.get(i)).inventory_fkey);
			float itmwgt = OWDUtilities.roundFloat(((float)((LineItem)order.items.get(i)).quantity_actual)*unitwgt,2);
			pkgwgt+=itmwgt;
			if(itmwgt == (float) 0.0)
			{
				hasZeroWeights = true;
			}
			
			
			if(bgcolor.equals("#E4ECF4")) bgcolor = "#FFFFFF";
		else bgcolor = "#E4ECF4";
%>
<TR bgcolor=<%= bgcolor %>><TD><%= ((LineItem)order.items.get(i)).client_part_no %></TD><TD align=LEFT><%= ((com.owd.core.business.order.LineItem)order.items.get(i)).description %></TD><TD align=CENTER><fontx  SIZE=-2><%= ((com.owd.core.business.order.LineItem)order.items.get(i)).quantity_request %></TD><TD align=RIGHT><fontx  SIZE=-2><%= (itmwgt==0.0?"<fontx COLOR=RED>"+itmwgt+"":""+itmwgt) %> lbs</TD></TR>
<%
			
		}
		}
%>
<TR><TD colspan=4><HR>
<%
	if(hasZeroWeights)
	{
	%>
	One or more items have no listed weight - this is likely to produce inaccurate rate predictions. You may wish to add weights to the inventory records for these items before proceeding.
	<%
	}
%></TD></TR> 
<TR><TD COLSPAN=4 ALIGN=RIGHT><fontx  SIZE=-2>&nbsp;<BR><B>Predicted&nbsp;Package&nbsp;Weight:&nbsp;</B><%= pkgwgt %>&nbsp;lbs<BR>&nbsp;</TD></TR>
</TABLE>
</TD></TR></TABLE>

<TABLE width=100% border=0 cellspacing=0 ><TR><TD ALIGN=LEFT><fontx  SIZE=-1><B>Predicted Rates for shipment<BR>on/after <%= dformat.format(now.getTime()) %> at <%= (reqwgt>0?reqwgt:pkgwgt) %> lbs</B></TD><TD ALIGN=RIGHT><fontx  SIZE=-2><FORM name="custform" METHOD=POST ACTION="<%= request.getContextPath()+ExtranetServlet.kExtranetURLPath%>?<%=com.owd.extranet.servlet.OrdersManager.kParamOrderMgrAction%>=ordershipmethod&<%=com.owd.extranet.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>">
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamAdminAction%>" value="<%= ExtranetServlet.kParamChangeMgrAction %>" />
    <INPUT TYPE="HIDDEN" name="<%= ExtranetServlet.kParamChangeMgrTo%>" value="<%= ExtranetManager.kOrdersMgrName %>" />
    <INPUT TYPE=submit name="submit" VALUE="Use This Weight">&nbsp;<INPUT TYPE=TEXT SIZE=4 NAME=reqwgt VALUE="<%= reqwgt %>">&nbsp;lbs</TD></TR></TABLE>
<TABLE width=100% border=0 cellspacing=0 >
<TR bgcolor="#649CCC"><TH ALIGN=CENTER WIDTH=1%><fontx  COLOR=WHITE SIZE=-1>&nbsp;Method&nbsp;</TH><TH ALIGN=LEFT WIDTH=20%><fontx  COLOR=WHITE SIZE=-1>Info</TH><TH ALIGN=LEFT WIDTH=100% align=CENTER ><fontx  COLOR=WHITE SIZE=-1><B>Service</B></TH></TR>
<%

if((reqwgt>0?reqwgt:pkgwgt) > (float)0.0)
{
	System.out.println("rate1start");
	System.out.println("rater:"+rater);
	System.out.println("raterResp:"+rater.theResponse);
	
    PackageRate rs1 = new PackageRate();
    rs1.setErrorCode(0);
    rs1.setMethodName("LTL");
    rs1.setMethodCode("TANDATA_LTL.LTL.LTL");
    rs1.setFinalRate(0.00);

    rater.theResponse.add(rs1);
    PackageRate rs8 = new PackageRate();
    rs8.setErrorCode(0);
    rs8.setMethodName("Economy");
    rs8.setMethodCode("COM_OWD_FLATRATE_ECONOMY");
    rs8.setFinalRate(0.00);
    rater.theResponse.add(rs8);

    PackageRate rs2 = new PackageRate();
    rs2.setErrorCode(0);
    rs2.setMethodName("Ground");
    rs2.setMethodCode("COM_OWD_FLATRATE_GROUND");
    rs2.setFinalRate(0.00);
    rater.theResponse.add(rs2);

    PackageRate rs9 = new PackageRate();
    rs9.setErrorCode(0);
    rs9.setMethodName("Standard Priority");
    rs9.setMethodCode("COM_OWD_FLATRATE_STANDARD_GROUND");
    rs9.setFinalRate(0.00);
    rater.theResponse.add(rs9);

    PackageRate rs4 = new PackageRate();
    rs4.setErrorCode(0);
    rs4.setMethodName("2 Day");
    rs4.setMethodCode("COM_OWD_FLATRATE_2DA");
    rs4.setFinalRate(0.00);
    rater.theResponse.add(rs4);

    PackageRate rs5 = new PackageRate();
    rs5.setErrorCode(0);
    rs5.setMethodName("Overnight");
    rs5.setMethodCode("COM_OWD_FLATRATE_NDA");
    rs5.setFinalRate(0.00);
    rater.theResponse.add(rs5);

    PackageRate rs10 = new PackageRate();
    rs10.setErrorCode(0);
    rs10.setMethodName("International Economy");
    rs10.setMethodCode("COM_OWD_FLATRATE_INTL_ECONOMY");
    rs10.setFinalRate(0.00);
    rater.theResponse.add(rs10);

    PackageRate rs6 = new PackageRate();
    rs6.setErrorCode(0);
    rs6.setMethodName("International Standard");
    rs6.setMethodCode("COM_OWD_FLATRATE_INTL_STND");
    rs6.setFinalRate(0.00);
    rater.theResponse.add(rs6);

    PackageRate rs7 = new PackageRate();
    rs7.setErrorCode(0);
    rs7.setMethodName("International Expedited");
    rs7.setMethodCode("COM_OWD_FLATRATE_INTL_EXPD");
    rs7.setFinalRate(0.00);
    rater.theResponse.add(rs7);

    PackageRate rs11 = new PackageRate();
    rs11.setErrorCode(0);
    rs11.setMethodName("International Priority DDP");
    rs11.setMethodCode("COM_OWD_FLATRATE_INTL_PRIDDP");
    rs11.setFinalRate(0.00);
    rater.theResponse.add(rs11);

    PackageRate rs12 = new PackageRate();
    rs12.setErrorCode(0);
    rs12.setMethodName("International Priority DDU");
    rs12.setMethodCode("COM_OWD_FLATRATE_INTL_PRIDDU");
    rs12.setFinalRate(0.00);
    rater.theResponse.add(rs12);

    PackageRate rs3 = new PackageRate();
    rs3.setErrorCode(0);
    rs3.setMethodName("Purolator");
    rs3.setMethodCode("EASYPOST.PUROLATOR");
    rs3.setFinalRate(0.00);
    rater.theResponse.add(rs3);

    PackageRate rs13 = new PackageRate();
    rs13.setErrorCode(0);
    rs13.setMethodName("Passport Priority DDP");
    rs13.setMethodCode("CONNECTSHIP_GLOBAL.APC");
    rs13.setFinalRate(0.00);
    rater.theResponse.add(rs13);

    PackageRate rs14 = new PackageRate();
    rs14.setErrorCode(0);
    rs14.setMethodName("Passport Priority DDU");
    rs14.setMethodCode("CONNECTSHIP_GLOBAL.APC");
    rs14.setFinalRate(0.00);
    rater.theResponse.add(rs14);


	//TreeMap discountMap = com.owd.core.business.Client.getClientForID(order.client_id).getMethodMap();
	//System.out.println("rate3:"+discountMap);
	String tkey = "";
	try{
	tkey = rater.getNewServiceCode(shipping.carr_service_ref_num);
	}catch(Exception ext)
	{
		tkey = shipping.carr_service_ref_num;
	}
	System.out.println("rate4:"+tkey);
	bgcolor = "#E4ECF4";
	System.out.println("l");

		System.out.println("3");

    for (PackageRate currShipment:rater.theResponse)
	{

    try{
        System.out.println("4");

		if(bgcolor.equals("#E4ECF4")) bgcolor = "#FFFFFF";
		else bgcolor = "#E4ECF4";
	

%>
<!-- err:<%= currShipment.getErrorCode() %> desc:<%= currShipment.getErrorMessage() %> -->
<%
      System.out.println("5");
	if(currShipment.getErrorCode()==0 || ( currShipment.getErrorCode()==1001 && currShipment.getMethodCode().indexOf("AIRBORNE")>=0))
		{
		 System.out.println("6");
		float shiprate = new Float(currShipment.getFinalRate()).floatValue();
	//	float discount = ((Float)discountMap.get(currShipment.getSHIPMENTSERVICE().getSCS())).floatValue();
		String rateStr;
		if(currShipment.getMethodCode().indexOf("AIRBORNE")>=0)
		{
			rateStr = "N/A";
		}else
		{    System.out.println("7");
			rateStr = formatter.format(OWDUtilities.roundFloat(((float)1.0)*shiprate,2));
		}
		
   %>

<TR bgcolor=<%= bgcolor %>><TD WIDTH=1% ALIGN=CENTER><INPUT TYPE=RADIO NAME=shipmethod VALUE="<%= currShipment.getMethodCode() %>" <%= (tkey.equals(currShipment.getMethodCode())?"CHECKED":"") %>></TD><TD WIDTH=20% ><fontx  SIZE=-2></TD><TD align=LEFT><fontx  SIZE=-2><%= (currShipment.getMethodName().indexOf("(")>=0?currShipment.getMethodName().substring(0,currShipment.getMethodName().indexOf("(")):currShipment.getMethodName())%> </TD></TR>
<%
		}else
		{
%>

<TR bgcolor=<%= bgcolor %>><TD WIDTH=1 ALIGN=CENTER><INPUT TYPE=RADIO NAME=shipmethod VALUE="<%= currShipment.getMethodCode() %>" <%= (tkey.equals(currShipment.getMethodCode())?"CHECKED":"") %>></TD><TD WIDTH=20%><fontx  SIZE=-2><%= currShipment.getErrorMessage() %></TD><TD align=LEFT><fontx  SIZE=-2><%= (currShipment.getMethodName().indexOf("(")>=0?currShipment.getMethodName().substring(0,currShipment.getMethodName().indexOf("(")):currShipment.getMethodName())%></TD></TR>
<%
		}
	}catch(Exception e){

        }   }
}else
{ //no weight available
%>
<TR bgcolor=<%= bgcolor %>><TD WIDTH=1% ALIGN=CENTER><INPUT TYPE=RADIO NAME=shipmethod VALUE="CURRENT" CHECKED></TD><TD WIDTH=20% ><fontx  SIZE=-2>N/A</TD><TD align=LEFT><fontx  SIZE=-2><%= shipping.carr_service %></TD></TR>
<%
}
%>


<TR><TD colspan=3><HR><fontx  SIZE=-2>
Service options and predicted rates are based solely on the destination and predicted weight of the shipment. Actual billed rates may vary from those presented. Predicted rates should be used for comparative purposes only and do not represent a promise to deliver at the displayed cost. Some packages may not qualify for some services due to contents, size or requested optional services such as insurance. You are responsible for choosing an appropriate service - if you have any questions, please contact your OWD Account Manager before proceeding.<BR>&nbsp;&nbsp;</TD></TR> 
<tr><td colspan="3"><input name="holdUntilDate" value="<%= holdUntilDate==null?"":dformat.format(holdUntilDate) %>" size="11" onfocus="this.blur()" readonly><a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.custform.holdUntilDate);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>&nbsp;<a style="color: #666;text-decoration: none;font-size: 11px;" name="cleardate" href="javascript:void(0)" onclick="document.custform.holdUntilDate.value='<%= dformat.format(Calendar.getInstance().getTime()) %>';return false;">CLEAR&nbsp;DATE</a>
<br><fontx size="-1">If you change the date above to a day in the future, this order will be released immediately but will not print for shipping until the indicated date. This date indicator will be cleared if the order is ever unposted or placed on hold after being released to ship.</td></tr>
    <TR><TD  colspan=3><INPUT TYPE=SUBMIT NAME=submit VALUE="Complete Shipment Using Selected Method"></FORM></TR>
</TABLE>
 <iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js" src="/webapps/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
     <LAYER name="gToday:outlook:agenda.js" src="/webapps/popcal/npopeng.htm">     </LAYER>
     </iframe>