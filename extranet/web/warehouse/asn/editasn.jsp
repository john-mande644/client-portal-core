<%@ page import="com.owd.core.business.Client,
                 com.owd.hibernate.generated.Asn,
                 com.owd.hibernate.generated.AsnItem,
                 com.owd.web.warehouse.asn.ASNHome,
                 org.apache.commons.lang.StringEscapeUtils,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 java.util.Iterator"%>
<%@ page import="java.util.Locale" %>
<%@ page import="com.owd.hibernate.generated.OwdFacility" %>
<%@ page import="java.util.List" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>

<%
    String error = (String) request.getAttribute("errormessage");

    Asn asn = (Asn) request.getSession(true).getAttribute("asn.currentasn");

    StringBuffer fsb = new StringBuffer();

    String currentLocation =  (String) request.getSession(true).getAttribute("owd_default_location");

    boolean isMultiFacility = FacilitiesManager.isClientMultiFacility(asn.getClientFkey());

    List<OwdFacility> facilities = FacilitiesManager.getActiveFacilities();

    if("ALL".equals(asn.getFacilityCode()))
    {
        asn.setFacilityCode(currentLocation);
    }
    if(isMultiFacility)
    {
        fsb.append("<SELECT NAME=\"facilityCode\">");


        for(OwdFacility f: facilities)
        {

             if(f.getIsPublic()==1)
             {
            fsb.append("<OPTION VALUE=\""+f.getFacilityCode()+"\" "+( f.getFacilityCode().equals(asn.getFacilityCode())?"SELECTED":"")+">"+( f.getFacilityCode()+" - "+f.getCity()+", "+f.getState()));
              }
        }
fsb.append("</SELECT>");

    }




            DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);


%>
<HTML>
<HEAD></HEAD><script type="text/javascript">

/***********************************************
* Disable "Enter" key in Form script- By Nurul Fadilah(nurul@REMOVETHISvolmedia.com)
* This notice must stay intact for use
* Visit http://www.dynamicdrive.com/ for full source code
***********************************************/

function handleEnter (field, event) {
		var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
		if (keyCode == 13) {
			var i;
			for (i = 0; i < field.form.elements.length; i++)
				if (field == field.form.elements[i])
					break;
			i = (i + 1) % field.form.elements.length;
			field.form.elements[i].focus();
			return false;
		}
		else
		return true;
	}

</script>
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
<jsp:include page="asnheader.jsp" flush="true" />
<%
    if(ASNHome.getSessionFlag(request,ASNHome.kExtranetInternalFlag) && asn.getId()!=null)
    {
%>

 <table><tr><td align=left><B><A HREF="./edit?<%=ASNHome.kParamAdminAction%>=display-receive-printable&asn_id=<%=asn.getId()%>" target="#newwasasdasdindisplay">Printable Receiving Sheet</A></B></TD>
 <TD align=center>
 <B><A HREF="./edit?<%=ASNHome.kParamAdminAction%>=clone-asn&blind=1&asn_id=<%= asn.getId()%>" >Clone Blind ASN</A>
 </B></td><TD align=center>
 <B><A HREF="./edit?<%=ASNHome.kParamAdminAction%>=clone-asn&asn_id=<%= asn.getId()%>" >Clone Normal ASN</A>
 </B></td><TD align=right>
 <B><A HREF="./edit?<%=ASNHome.kParamAdminAction%>=create-receive&asn_id=<%= asn.getId()%>" >Enter New Receive</A>
 </B></td></tr></table>
 <% } else if(asn.getId()!=null)
     {
         %><table><tr> <TD align=center>
 <B><A HREF="./edit?<%=ASNHome.kParamAdminAction%>=clone-asn&asn_id=<%= asn.getId()%>" >Clone This ASN</A>
 </B></td></tr></table>
         <%
     } else if (request.getParameter("act").equals("clone-asn")){
         %>
         <table><tr> <TD align=LEFT>
 <B><A HREF="./edit?<%=ASNHome.kParamAdminAction%>=clear-sku&asn_id=<%= asn.getId()%>" >Clear All Sku's</A>
 </B></td></tr></table>
         <%
     }
             %>
<% if(asn.getId()!=null && asn.getReceiveCount()<1)
{
      %> <TABLE width=100%><TR><td width="50%"> </td><TD ALIGN=MIDDLE VALIGN=TOP NOWRAP bgcolor="ffffff">
<FORM ACTION="./edit" METHOD=POST><INPUT TYPE=HIDDEN NAME="act" VALUE="delete-asn"><INPUT TYPE=HIDDEN NAME="page-asn" VALUE="<%= asn.getId()%>"><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Delete this ASN"></FORM>
</TD></TR></TABLE>
      <%
}
    %>
<P><center><B><%= (asn.getId()==null?"Create New ASN ":"Editing Unreceived ASN ") %>for <%= Client.getClientForID(asn.getClientFkey()+"").company_name%></B></center>
<HR>
<FORM NAME="dateform" ACTION="./edit" METHOD=POST > <CENTER><INPUT TYPE=submit NAME="submit" VALUE="Save ASN" ></CENTER>
<%= (asn.getId()==null?"<INPUT TYPE=HIDDEN NAME=\""+ASNHome.kParamAdminAction+"\" VALUE=\"create-save\">":"<INPUT TYPE=HIDDEN NAME=\""+ASNHome.kParamAdminAction+"\" VALUE=\"edit-save\">")%>

    <% if(asn.getId()!=null){
        %>
        <input type="hidden" name="loadedAsnId" value="<%=asn.getId()%>"/>
    <%
    }%>
<TABLE border=0 width=100%><TR><TD >

<table border=0 cellspacing=2 width="100%">


<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Blind ASN?:</B></TD><TD ALIGN=LEFT width="100%"><%= asn.getHasBlind().intValue()==1?"Blind":"No" %></TD></TR>

<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Shipper Name:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="shipperName" value="<%= StringEscapeUtils.escapeHtml(asn.getShipperName()) %>" size=40 MAXLENGTH=255><!--<BR><FONT size=-1>The name that will appear on the shipping label or waybill as the person or company sending the shipment to OWD.</font>--></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP VALIGN=TOP bgcolor="99ccff"><B>Expected Date:</B></TD><TD ALIGN=LEFT width="100%" >
<input name="expect_date"  value="<%= df.format(asn.getExpectDate())%>" size="11" onfocus="this.blur()" readonly>
<a href="javascript:void(0)" onclick="gfPop.fPopCalendar(document.dateform.expect_date);return false;" HIDEFOCUS name="popcal" ><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
<!--<BR><FONT size=-1>Click on the icon to choose the date that this shipment is most likely to arrive at OWD.</font>--></TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>AutoRelease:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=CHECKBOX NAME="isAutoRelease" value=1  <%= asn.getIsAutorelease().intValue()==1?"CHECKED":"" %>>Release for shipping as received
<BR><FONT size=-1 color=red><b>Check the AutoRelease to make your inventory immediately available for orders as it is received and counted. Uncheck the box above if you want items in this shipment to not be added to available inventory until after review by you or your Account Manager following receipt.</b></font></TD></TR>
 <TR><TD ALIGN=RIGHT NOWRAP bgcolor="99ccff"><B>Carrier:</B></TD><TD ALIGN=LEFT width="100%" ><SELECT NAME="carrier">
 <OPTION VALUE="Unknown" <%= "Unknown".equals(asn.getCarrier())?"SELECTED":"" %>>Unknown
 <OPTION VALUE="UPS" <%= "UPS".equals(asn.getCarrier())?"SELECTED":"" %>>UPS
 <OPTION VALUE="USPS" <%= "USPS".equals(asn.getCarrier())?"SELECTED":"" %>>USPS
 <OPTION VALUE="FedEx" <%= "FedEx".equals(asn.getCarrier())?"SELECTED":"" %>>FedEx
 <OPTION VALUE="LTL Truck" <%= "LTL Truck".equals(asn.getCarrier())?"SELECTED":"" %>>LTL Truck
     <% if(ASNHome.getSessionString(request,ASNHome.kExtranetClientID).equals("160") || ASNHome.getSessionString(request,ASNHome.kExtranetClientID).equals("364"))
     {%>
 <OPTION VALUE="Kitting" <%= "Kitting".equals(asn.getCarrier())?"SELECTED":"" %>>Kitting at OWD
     <%}%>
 </SELECT>
 </TD></TR>

 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Carton/Package Count:</B></TD><TD ALIGN=LEFT width="100%" ><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="cartonCount" value="<%= asn.getCartonCount() %>" size=10 MAXLENGTH=255></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Pallet Count:</B></TD><TD ALIGN=LEFT width="100%" ><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="palletCount" value="<%= asn.getPalletCount() %>" size=10 MAXLENGTH=255></TD></TR>

 <%
    if(ASNHome.getSessionFlag(request,ASNHome.kExtranetInternalFlag))
    {
 %>
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Has OWD Labels?:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=CHECKBOX NAME="owdLabels" value=1  <%= asn.getOwdLabels().intValue()==1?"CHECKED":"" %>></TD></TR>
 <%
    }
 %>
<% if(isMultiFacility){%>    <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Facility:</B></TD><TD ALIGN=LEFT width="100%" ><%= fsb.toString() %></TD></TR> <% } %>




</table>
</TD><TD>
<table>
<!-- <TR><TD NOWRAP ALIGN=CENTER NOWRAP><B>Optional ASN Fields</B></TD><TD bgcolor="99ffcc"><HR><font size=-1><B>Please fill out the following fields to the best of your ability. While they are not required, they do help us process your deliveries more quickly and provide more meaningful reports for you.</B></font></font><HR></TD></TR>
    -->
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Client ASN Reference:</B></TD><TD ALIGN=LEFT width="100%">
<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="clientRef" size=40  value="<%= StringEscapeUtils.escapeHtml(asn.getClientRef()) %>" MAXLENGTH=255><% if (asn.getId() != null){%>&nbsp;(OWD&nbsp;Ref:<%=asn.getId()%>)<% } %>
 <!--<BR><FONT size=-1>A reference number or name you would like to use for reporting purposes. </font>--></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Client PO Number:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="clientPo" size=40  value="<%= StringEscapeUtils.escapeHtml(asn.getClientPo()) %>" MAXLENGTH=255>
 <!-- <BR><FONT size=-1>A number or name that will appear on packing slips, invoices or other paperwork from your supplier and accompany the shipment. </font>
 --></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Instructions/Notes:</B></TD><TD ALIGN=LEFT width="100%" ><TEXTAREA ROWS=6 cols=40 NAME="notes"><%= StringEscapeUtils.escapeHtml(asn.getNotes()) %></TEXTAREA>
 <!--<BR><FONT size=-1>Instructions or comments for OWD personnel to review when receiving your shipment. Please note that any special services requested may result in additional charges for extended receiving time. </font>
 --></TD></TR>
<% if (asn.getId() != null){%><TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Created:</B></TD>
<TD ALIGN=LEFT width="100%" ><%=asn.getCreatedDate()%>&nbsp;by&nbsp;<%= asn.getCreatedBy() %></TD></TR><% } %>

</table>
</TD></TR>


</table>

 <table width=100%>
  <TR>
<TD ALIGN=LEFT  VALIGN=CENTER><INPUT TYPE=HIDDEN NAME="subaction" VALUE="add-item">Add SKU:<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" NAME="addsku" VALUE="">&nbsp;Units:<INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" NAME="qtysku">&nbsp;<INPUT TYPE=SUBMIT NAME="subactionSubmit" VALUE="Add SKU to ASN" >
</TD><TD ALIGN=RIGHT NOWRAP   VALIGN=CENTER><INPUT TYPE=HIDDEN NAME="subaction2" VALUE="import-items"><TEXTAREA NAME="importskus" ></TEXTAREA>&nbsp;<INPUT TYPE=SUBMIT NAME="subaction2Submit" VALUE="Import SKUs to ASN" ><BR>
</TD></TR>

<table border=0 cellspacing=0 width="100%"><TR  bgcolor="99ccff"><TD><HR></TD><TD NOWRAP ALIGN=CENTER width=1% ><B>Current ASN Items</B></TD><TD><HR></TD></TR>
</table>

<table border=0 cellspacing=1 width="100%">
<TR><TH ALIGN=LEFT>SKU</TH><TH ALIGN=LEFT>Title</TH><TH ALIGN=LEFT>Description</TH><TH ALIGN=LEFT>Units</TH><TH ALIGN=LEFT></TH></TR>
        <%
            Iterator it = asn.getAsnItems().iterator();
            while(it.hasNext())
            {
               AsnItem item = (AsnItem) it.next();

        %>
 <tr>
 <TD ALIGN=LEFT NOWRAP ><B><%= item.getInventoryNum() %></B></TD>
  <TD ALIGN=LEFT NOWRAP ><%= item.getTitle()==null?"":StringEscapeUtils.escapeHtml(item.getTitle()) %></TD>
 <TD ALIGN=LEFT NOWRAP><%= item.getDescription()==null?"":StringEscapeUtils.escapeHtml(item.getDescription())%></TD>
  <TD ALIGN=LEFT NOWRAP COLSPAN=2><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" SIZE=6 NAME="<%= item.getInventoryNum()+"_qty"%>" VALUE="<%= item.getExpected() %>"></TD></TR>
 </TR>
 <% } %>


</table>
</form>



     <!--  PopCalendar(tag name and id must match) Tags should sit at the page bottom -->
     <iframe width=152 height=163 name="gToday:outlook:agenda.js" id="gToday:outlook:agenda.js" src="/webapps/popcal/outlook/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
     <LAYER name="gToday:outlook:agenda.js" src="/webapps/popcal/npopeng.htm">     </LAYER>
     </iframe>

<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;
    <script src="//rum-static.pingdom.net/pa-5b369fc56a549f00160000bc.js" async></script>
    </BODY>
</HTML>
