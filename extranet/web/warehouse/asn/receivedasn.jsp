<%@ page import="com.owd.core.OWDUtilities,
                 com.owd.core.business.Client,
                 com.owd.core.business.asn.ASNManager,
                 com.owd.hibernate.*,
                 com.owd.web.warehouse.asn.ASNHome,
                 org.hibernate.LockMode,
                 org.hibernate.Session,
                 java.net.URLEncoder,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 java.util.*"%>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%@ page import="com.owd.hibernate.generated.*" %>

<%
	 String error = (String) request.getAttribute("errormessage");


     Asn asn = (Asn) request.getSession(true).getAttribute("asn.currentasn");

     DateFormat df = new SimpleDateFormat("MM/dd/yyyy",Locale.US);

     StringBuffer fsb = new StringBuffer();


         fsb.append(asn.getFacilityCode());




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
 </B></td><TD align=right>
 <B><A HREF="./download/<%= (asn.getId()+"").replaceAll("#","")+".csv" %>?<%=ASNHome.kParamAdminAction%>=download-asn" >Download (Excel)</A>
 </B></td></tr></table>
 <% } else
     {
         %><table><tr> <TD align=center>
 <B><A HREF="./edit?<%=ASNHome.kParamAdminAction%>=clone-asn&asn_id=<%= asn.getId()%>" >Clone This ASN</A>
 </B></td><TD align=center>
 <B><A HREF="./download/<%=(asn.getId()+"").replaceAll("#","")+".csv"%>?<%=ASNHome.kParamAdminAction%>=download-asn" >Download (Excel)</A>
 </B></td></tr></table>
         <%
     }%>

<HR><center><B>Viewing Received ASN for <%= Client.getClientForID(asn.getClientFkey()+"").company_name%></B></center>
<P><center><B>Status:&nbsp;<%= ASNManager.getAsnStatus(asn.getStatus()+"")%></B>&nbsp;
<% if ("2".equals(asn.getStatus()+"")) { %><A HREF="./edit?<%=ASNHome.kParamAdminAction%>=complete-asn&asn_id=<%= asn.getId()%>" >[Set Status to Received Complete]</A></center><% } %>
<HR>
<P><FORM METHOD=POST ACTION="./edit">
<TABLE border=0 width=100%><TR><TD width=50%>

<table border=0 cellspacing=2 width="100%">


<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Blind ASN?:</B></TD><TD ALIGN=LEFT width="100%">
<%
     if(ASNHome.getSessionFlag(request,ASNHome.kExtranetInternalFlag) && asn.getId()!=null)
     {
 %>
    <INPUT TYPE=CHECKBOX NAME="hasBlind" value=1  <%= asn.getHasBlind().intValue()==1?"CHECKED":"" %>>Blind ASN</TD></TR>
<% } else
     {
         %><%= asn.getHasBlind()==1?"Blind":"No" %><INPUT TYPE=HIDDEN NAME=hasBlind VALUE="<%= asn.getHasBlind().intValue()==1?"1":"0" %>"></TD></TR>
            <%
     }%>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Shipper Name:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="shipperName" value="<%= asn.getShipperName() %>" size=40 MAXLENGTH=255></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP VALIGN=TOP bgcolor="99ccff"><B>Expected Date:</B></TD><TD ALIGN=LEFT width="100%" >
<%= df.format(asn.getExpectDate())%></TD></TR>
<TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>AutoRelease:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=CHECKBOX NAME="isAutoRelease" value=1  <%= asn.getIsAutorelease()==1?"CHECKED":"" %>>Release for shipping as received</TD></TR>
 <TR><TD ALIGN=RIGHT NOWRAP bgcolor="99ccff"><B>Carrier:</B></TD><TD ALIGN=LEFT width="100%" ><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="carrier" value="<%= asn.getCarrier() %>" size=40 MAXLENGTH=255>
 </TD></TR>

 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Carton/Package Count:</B></TD><TD ALIGN=LEFT width="100%" ><%= asn.getCartonCount() %></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Pallet Count:</B></TD><TD ALIGN=LEFT width="100%" ><%= asn.getPalletCount() %></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Facility:</B></TD><TD ALIGN=LEFT width="100%" ><%= fsb.toString() %></TD></TR>





</table>
</TD><TD width=50%>
<table>
<!-- <TR><TD NOWRAP ALIGN=CENTER NOWRAP><B>Optional ASN Fields</B></TD><TD bgcolor="99ffcc"><HR><font size=-1><B>Please fill out the following fields to the best of your ability. While they are not required, they do help us process your deliveries more quickly and provide more meaningful reports for you.</B></font></font><HR></TD></TR>
    -->
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Client ASN Reference:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="clientRef" size=40  value="<%= asn.getClientRef() %>" MAXLENGTH=255><% if (asn.getId() != null){%>&nbsp;(OWD&nbsp;Ref:<%=asn.getId()%>)<% } %>
 <!--<BR><FONT size=-1>A reference number or name you would like to use for reporting purposes. </font>--></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Client PO Number:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=TEXT onkeypress="return handleEnter(this, event)" name="clientPo" size=40  value="<%= asn.getClientPo() %>" MAXLENGTH=255>
 <!-- <BR><FONT size=-1>A number or name that will appear on packing slips, invoices or other paperwork from your supplier and accompany the shipment. </font>
 --></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Instructions/Notes:</B></TD><TD ALIGN=LEFT width="100%" ><TEXTAREA ROWS=6 cols=40 NAME="notes"><%= asn.getNotes() %></TEXTAREA>
 <!--<BR><FONT size=-1>Instructions or comments for OWD personnel to review when receiving your shipment. Please note that any special services requested may result in additional charges for extended receiving time. </font>
 --> </TD></TR>
<% if (asn.getId() != null){%><TR><TD ALIGN=RIGHT VALIGN=TOP NOWRAP bgcolor="99ccff"><B>Created:</B></TD>
<TD ALIGN=LEFT width="100%" ><%=asn.getCreatedDate()%>&nbsp;by&nbsp;<%= asn.getCreatedBy() %></TD></TR><% } %>




</table>
</TD></TR>
<TR><TD colspan=2></TD></TR>
<TR><TD COLSPAN=2 align=center nowrap>
<INPUT TYPE=HIDDEN NAME=act VALUE=rcvasn-save>
        <% if(asn.getId()!=null){
        %>
    <input type="hidden" name="loadedAsnId" value="<%=asn.getId()%>"/>
        <%
    }%>
<INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Save Changes to ASN Fields Above">
</FORM></TD></TR>

</table>

<table border=0 cellspacing=0 width="100%"><TR  bgcolor="99ccff"><TD><HR></TD><TD NOWRAP ALIGN=CENTER width=1% ><B>Receives</B></TD><TD><HR></TD></TR>
</table>

<table border=0 cellspacing=1 width="100%">
<TR><TH ALIGN=LEFT>Date</TH><TH ALIGN=LEFT>By</TH><TH ALIGN=LEFT>Minutes</TH><TH ALIGN=LEFT>Cartons/Pallets</TH><TH ALIGN=LEFT>Notes</TH><TH ALIGN=LEFT>Status</TH><TH></TH><TH></TH></TR>
        <%
            
            List mapList = new ArrayList();
            
            Session sess = HibernateSession.currentSession();
            sess.lock(asn,LockMode.READ);
            Iterator it = asn.getReceives().iterator();
            int indexNum = 0;
            while(it.hasNext())
            {
                System.out.println("got a receive");
               Receive item = (Receive) it.next();
                boolean isPending = (item.getCreatedBy().equals("PENDING"));
              sess.lock(item,LockMode.READ);
                String releasedStatusString = "Released";
                String releaseFormString = "<FORM METHOD=POST ACTION=\"./edit\"><INPUT TYPE=HIDDEN NAME=rcvid VALUE=\""+ item.getId().intValue() +"\"><INPUT TYPE=HIDDEN NAME=act VALUE=\"post-receive\"><INPUT TYPE=SUBMIT NAME=act VALUE=\"Release\"></form>";
                String unreleaseFormString = "<FORM METHOD=POST ACTION=\"./edit\"><INPUT TYPE=HIDDEN NAME=rcvid VALUE=\""+ item.getId().intValue() +"\"><INPUT TYPE=HIDDEN NAME=act VALUE=\"unpost-receive\"><INPUT TYPE=SUBMIT NAME=act VALUE=\"Unrelease\"></form>";
                String deleteFormString = "<FORM METHOD=POST ACTION=\"./edit\"><INPUT TYPE=HIDDEN NAME=rcvid VALUE=\""+ item.getId().intValue() +"\"><INPUT TYPE=HIDDEN NAME=act VALUE=\"delete-receive\"><INPUT TYPE=SUBMIT NAME=act VALUE=\"Delete\"></form>";
                String detailLinkString = "<a href=\"/webapps/v2/asn/display?receiveId=" + item.getId().intValue() + "\" target=\"_blank\">Details</a>";
                if(!(ASNHome.getSessionFlag(request,ASNHome.kExtranetInternalFlag)))
                {
                   deleteFormString = "Contact&nbsp;AM&nbsp;to&nbsp;delete";
                }
        %>
 <tr>
 <TD ALIGN=LEFT NOWRAP ><B><%= ++indexNum %>.</B>&nbsp;&nbsp;<%= isPending?"In Progress":""+item.getEndTimestamp() %></TD>
  <TD ALIGN=LEFT NOWRAP ><%= isPending?"":item.getReceiveBy() %></TD>
  <TD ALIGN=LEFT NOWRAP ><%= isPending?"":""+item.getBilledMinutes() %></TD>
  <TD align=left nowrap><%= item.getCartonCount()%>/<%= item.getPalletCount()%>
  <TD ALIGN=LEFT  >Packing Slip?&nbsp;<%= isPending?"":(item.getIsPackSlipFound()==1?"Yes:  Matches? ":"No")%><% if (item.getIsPackSlipFound()==1){%><%=isPending?"":(item.getIsPackSlipMatch()==1?"Yes":"No")%><%}%><%= isPending?"":(item.getNotes().length()>0?"<BR>"+item.getNotes():"") %></TD>
  <TD ALIGN=LEFT NOWRAP >
<%= isPending?"":(item.getIsPosted()==1?releasedStatusString:releaseFormString) %>
&nbsp;&nbsp;<%= isPending?"":(item.getIsPosted()==1?unreleaseFormString:deleteFormString) %>
</TD>
<td>
    <%= detailLinkString %>
</td>
<td>
<%
        StringBuffer sb = new StringBuffer();
 Collection scans = item.getScanDocs();
  if (scans != null) {
                Iterator it2 = scans.iterator();
                while (it2.hasNext()) {
                    ScanReceive sr = (ScanReceive) it2.next();

                    String typeicon = "text.gif";
                    if (sr.getName().toUpperCase().endsWith(".PDF")) {
                        typeicon = "acrobat.gif";
                    }
                    sb.append("<A HREF=\"" + ("edit?act=get-scan&auth=" + URLEncoder.encode(OWDUtilities.encryptData(item.getId()
                            + "/" + sr.getName() + "/" + item.getClientFkey()), "UTF-8") + "\"><IMG SRC=\"" +
                             "/webapps/images/" + typeicon + " \" border=\"0\">&nbsp;" + sr.getName() + "</A>" +
                            (it2.hasNext() ? "<BR>" : "")));


                }
      %><%=sb.toString()%><%
            }

%>

</td></TR>
  <%

      if(!isPending)
      {
    Map itemMap = new TreeMap();
    Iterator itm = item.getReceiveItems().iterator();
    while(itm.hasNext())
    {
        ReceiveItem ri = (ReceiveItem) itm.next();
        itemMap.put(new Integer(ri.getInventoryFkey()),ri);

    }
        mapList.add(itemMap);
      }
   }

          %>


</table>
 <table border=0 cellspacing=0 width="100%"><TR  bgcolor="99ccff"><TD><HR></TD><TD NOWRAP ALIGN=CENTER width=1% ><B>ASN Items</B></TD><TD><HR></TD></TR>
 </table>

<table border=0 cellspacing=1 width="100%">
<TR><TH ALIGN=LEFT>SKU</TH><TH ALIGN=LEFT>Title</TH><TH ALIGN=LEFT>Expected</TH>
<% for (int i=0;i<mapList.size();i++)
{
    %><TH ALIGN=LEFT>Receive&nbsp;#<%= (i+1) %></TH><%
}
%><TH ALIGN=LEFT>Difference</TH></TR>
        <%

            Iterator it2 = asn.getAsnItems().iterator();
            while(it2.hasNext())
            {
                  System.out.println("got a asnitem");
               AsnItem item = (AsnItem) it2.next();
             // sess.lock(item,LockMode.READ);
        %>
 <tr>
 <TD ALIGN=LEFT NOWRAP ><B><%= item.getInventoryNum() %></B></TD>
  <TD ALIGN=LEFT NOWRAP ><%= item.getTitle()==null?"":item.getTitle() %></TD>
  <TD ALIGN=LEFT NOWRAP ><%= item.getExpected() %></TD>
  <% for (int i=0;i<mapList.size();i++)
{
      int receivedAmt = 0;
      int damagedAmount = 0;
      ReceiveItem currItem = ((ReceiveItem)((Map)mapList.get(i)).get(new Integer(item.getInventoryFkey())));
      if (currItem != null)
      {
          receivedAmt = currItem.getQtyReceive();
          damagedAmount = currItem.getQtyDamage();
      }
    %><TD ALIGN=LEFT><%= receivedAmt %>&nbsp;(Unusable:<%= damagedAmount %>)</TD><%
}
%>

  <TD ALIGN=LEFT NOWRAP ><%= item.getReceived()-item.getExpected() %></TD> </TR>
  <%
            }
                 HibernateSession.closeSession();%>


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
