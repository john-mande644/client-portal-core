<%@ page import="com.owd.core.business.Client,
                 com.owd.hibernate.generated.Receive,
                 com.owd.hibernate.generated.ReceiveItem,
                 com.owd.web.warehouse.asn.ASNEditServlet,
                 com.owd.web.warehouse.asn.ASNHome,
                 java.text.DateFormat,
                 java.text.SimpleDateFormat,
                 java.util.HashMap,
                 java.util.Iterator,
                 java.util.Map,
                 com.owd.core.managers.LotManager"%>
<%@ page import="com.owd.hibernate.generated.OwdLotReceiveItem" %>
<%@ page import="java.util.TreeSet" %>
<%@ page import="com.owd.web.warehouse.asn.ReceiveItemSortComparator" %>

<%
	 String error = (String) request.getAttribute("errormessage");


     Receive rcv = (Receive) request.getSession(true).getAttribute("asn.currentrcv");
     boolean isPending = ("PENDING".equals(rcv.getCreatedBy()));
     
     DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mmaa");
        Map receivedqty = new HashMap();
            try{
                System.out.println("in received try");
                if (request.getAttribute("receivedqty")==null){
                    receivedqty.put("cartonCount", "0");
                    receivedqty.put("palletCount", "0");

                    Iterator it = rcv.getReceiveItems().iterator();

            while(it.hasNext())
            {
               ReceiveItem item = (ReceiveItem) it.next();
               Map qty = new HashMap();
                 try{
                    if((item.getQtyReceive()>0 || item.getQtyDamage()>0) && item.getAsnItem().getInventoryNum()!=null){
                         qty.put("rqty", ""+item.getQtyReceive());
                         qty.put("dqty", ""+item.getQtyDamage());
                        receivedqty.put(item.getAsnItem().getInventoryNum(),qty);
                     }else {
                    }
            } catch (Exception e){

          }
            }


                }else{
                 receivedqty = (Map) request.getAttribute("receivedqty");
                }
            } catch(Exception e){
                        }
 %>
<HTML>
<HEAD>
    <script   src="https://code.jquery.com/jquery-1.12.3.min.js"   integrity="sha256-aaODHAgvwQW1bFOGXMeX+pC4PZIPsvn2h1sArYOhgXQ="   crossorigin="anonymous"></script>
</HEAD>
<jsp:include page="asnheader.jsp" flush="true" />
<body onKeyPress = "return show_key(event.which)">
<P><center><B><font size=+1><%= isPending?"Verifying Pending HH ":"Creating New "%>Receive for <%= Client.getClientForID(rcv.getClientFkey()+"").company_name%></font></B></center>
<P>&nbsp;<P>

<HR>&nbsp;<P>
<SCRIPT LANGUAGE="JavaScript"><!--
function match() {

if(document.dateform.packSlipFound.checked)
{
document.dateform.packSlipMatch.disabled=false;
}

else
{
document.dateform.packSlipMatch.checked=false;
document.dateform.packSlipMatch.disabled=true;
}
}
function  checkLotToItemValue(idListofLots,idToTotal){
    var total = 0;
    idListofLots.forEach(function(entry){
        console.log(entry);
        console.log( $('#'.concat(entry)).val());
        total = total + +$('#'.concat(entry)).val();
        console.log(total);
    });

    if(total != +$('#'.concat(idToTotal)).val()){
        alert('Please make sure values add up');
    };


}
function show_key ( the_key )
{
    if ( ! the_key )
    {
        the_key = event.keyCode;
    }

    if (the_key==13)
{
	var y =confirm("Do you want to Submit??");
    if (y)
	{
    document.dateform.submit.click() ;
  }
  return false;
}
 return true ;
}
//-->
</SCRIPT>

<FORM NAME="dateform" ACTION="./edit" METHOD=POST><INPUT TYPE=HIDDEN NAME="<%= ASNHome.kParamAdminAction %>" VALUE="receive-save">
<INPUT TYPE=HIDDEN NAME="pendingid" VALUE=<%= request.getParameter("pendingrcvid")%>>
<CENTER><INPUT TYPE=SUBMIT NAME="submit" VALUE="Save This Receive"></CENTER>
<TABLE border=0 width=100%><TR><TD>

<table border=0 cellspacing=2 width="100%">



<TR><TD ALIGN=RIGHT VALIGN=CENTER NOWRAP bgcolor="99ccff"><B>Received By:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=TEXT name="receiveBy" value="<%= rcv.getReceiveBy() %>" size=40 MAXLENGTH=255><!--<BR><FONT size=-1>The name that will appear on the shipping label or waybill as the person or company sending the shipment to OWD.</font>--></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP VALIGN=CENTER bgcolor="99ccff"><B>Start Time:</B></TD><TD ALIGN=LEFT width="100%" >
<input name="startTimestamp"  value="<%= rcv.getStartTimestamp()==null?"":df.format(rcv.getStartTimestamp()) %>" size="20" onfocus="this.blur()" readonly>
<a href="javascript:void(0)" onclick="if(self.gfPop)gfPop.fPopCalendar(document.dateform.startTimestamp);return false;" HIDEFOCUS><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>
<TR><TD ALIGN=RIGHT NOWRAP VALIGN=CENTER bgcolor="99ccff"><B>End Time:</B></TD><TD ALIGN=LEFT width="100%" >
<input name="endTimestamp"  value="<%= rcv.getEndTimestamp()==null?"":df.format(rcv.getEndTimestamp()) %>" size="20" onfocus="this.blur()" readonly>
<a href="javascript:void(0)" onclick="if(self.gfPop)gfPop.fPopCalendar(document.dateform.endTimestamp);return false;" HIDEFOCUS><img name="popcali" align="absbottom" src="/webapps/images/calbtn.gif" width="34" height="22" border="0" alt=""></A>


<TR><TD ALIGN=RIGHT VALIGN=CENTER NOWRAP bgcolor="99ccff"><B>Packing Slip?:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=CHECKBOX onclick="match()" NAME="packSlipFound" value=1  <%= rcv.getIsPackSlipFound()==1?"CHECKED":"" %>>Was a shipper's packing list found?
<br><Input type=checkbox name="packSlipMatch" value=1 <%= rcv.getIsPackSlipMatch()==1?"Checked":""%><%= rcv.getIsPackSlipFound()==1?"":"Disabled"%>> Match?
<TR><TD ALIGN=RIGHT VALIGN=CENTER NOWRAP bgcolor="99ccff"><B>Compliant Receipt?:</B></TD><TD ALIGN=LEFT width="100%"><INPUT TYPE=CHECKBOX NAME="asnFound" value=1  <%= rcv.getIsAsnFound()==1?"CHECKED":"" %>>Was the shipment compliant?

<BR><FONT size=-1>A compliant shipment includes a correct and easily located ASN identifier/reference and no mixed-SKU cartons.</font></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=CENTER NOWRAP bgcolor="99ccff"><B>Received Carton/Package Count:</B></TD><TD ALIGN=LEFT width="100%" ><INPUT TYPE=TEXT name="cartonCount" value="<%= request.getAttribute("receivedqty")==null?rcv.getCartonCount():Integer.parseInt(receivedqty.get("cartonCount").toString()) %>" size=10 MAXLENGTH=255></TD></TR>
 <TR><TD ALIGN=RIGHT VALIGN=CENTER NOWRAP bgcolor="99ccff"><B>Received Pallet Count:</B></TD><TD ALIGN=LEFT width="100%" ><INPUT TYPE=TEXT name="palletCount" value="<%= rcv.getPalletCount() %>" size=10 MAXLENGTH=255></TD></TR>

</table>
</TD><TD>
<table>
<!-- <TR><TD NOWRAP ALIGN=CENTER NOWRAP><B>Optional ASN Fields</B></TD><TD bgcolor="99ffcc"><HR><font size=-1><B>Please fill out the following fields to the best of your ability. While they are not required, they do help us process your deliveries more quickly and provide more meaningful reports for you.</B></font></font><HR></TD></TR>
    -->
    <TR><TD ALIGN=RIGHT VALIGN=CENTER NOWRAP bgcolor="99ccff"><B>Count Method:</B></TD><TD ALIGN=LEFT width="100%" ><INPUT TYPE=TEXT name="count_method" value="<%= rcv.getCountMethod()==null?"":("".equals(rcv.getCountMethod())?"":rcv.getCountMethod()) %>" size=30 MAXLENGTH=50><br>Count Method is REQUIRED</TD></TR>

 <TR><TD ALIGN=RIGHT VALIGN=CENTER NOWRAP bgcolor="99ccff"><B>Notes:</B></TD><TD ALIGN=LEFT width="100%" ><TEXTAREA ROWS=10 cols=40 NAME="notes"><%= rcv.getNotes() %></TEXTAREA>
 <BR><INPUT TYPE=CHECKBOX VALUE=1 NAME=notifyAM>&nbsp;Check this box to notify AM to review this receive. The assigned AM will be sent an immediate email with your notes as written above.
<!--<BR><FONT size=-1>Instructions or comments for OWD personnel to review when receiving your shipment. Please note that any special services requested may result in additional charges for extended receiving time. </font>
 --></TD></TR>
</table>
</TD></TR></table>

<table border=0 cellspacing=0 width="100%"><TR  bgcolor="99ccff"><TD><HR></TD><TD NOWRAP ALIGN=CENTER width=1% ><B>Items</B></TD><TD><HR></TD></TR>
</table>

<table border=0 cellspacing=1 width="100%">
<TR><TH ALIGN=LEFT COLSPAN=2>SKU&nbsp;-&nbsp;Title</TH><TH ALIGN=LEFT>Expected/Remaining</TH><TH ALIGN=LEFT>Received</TH><TH ALIGN=LEFT>Damaged</TH><TH align=left>Weight</TH></th></TR>
        <%
           // Iterator it = rcv.getReceiveItems().iterator();

            TreeSet<ReceiveItem> opi = new TreeSet<ReceiveItem>(new ReceiveItemSortComparator());
            System.out.println();
            opi.addAll(rcv.getReceiveItems());
            System.out.println(opi.size());

            for(ReceiveItem item:opi)
            {

               Map qty = new HashMap();
                 try{
                    if(receivedqty.get(item.getAsnItem().getInventoryNum())==null){

                         qty.put("rqty", "0");
                         qty.put("dqty", "0");
                     }else {
                         qty = (Map) receivedqty.get(item.getAsnItem().getInventoryNum());
                    }
            } catch (Exception e){

          }

        %>
 <tr>
 <TD ALIGN=LEFT NOWRAP COLSPAN=2><B><%= item.getAsnItem().getInventoryNum() %></B>&nbsp;-&nbsp;<%= item.getAsnItem().getTitle()==null?"":item.getAsnItem().getTitle() %><br>
 Counted By:<INPUT TYPE=TEXT NAME="<%= item.getAsnItem().getInventoryNum()+"_count_method"%>" VALUE="<%= item.getCountMethod()==null?"See Receive":("".equals(item.getCountMethod())?"See Receive":item.getCountMethod()) %>">
     &nbsp;Notes:<INPUT TYPE=TEXT MAXLENGTH=50 SIZE=30 NAME="<%= item.getAsnItem().getInventoryNum()+"_notes"%>" VALUE="<%= item.getNotes()%>">
 </TD>
 <TD ALIGN=LEFT NOWRAP><b><%= item.getAsnItem().getExpected() %>/<%= item.getAsnItem().getExpected()-item.getAsnItem().getReceived() %></b></TD>
  <TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT SIZE=6 NAME="<%= item.getAsnItem().getInventoryNum()+"_rcv_qty"%>" VALUE="<%= qty.get("rqty")==null?item.getQtyReceive():Integer.parseInt(qty.get("rqty").toString()) %>" id="<%= item.getAsnItem().getInventoryFkey()+"_rcv_qty"%>"></TD>
  <TD ALIGN=LEFT NOWRAP><INPUT TYPE=TEXT SIZE=6 NAME="<%= item.getAsnItem().getInventoryNum()+"_dmg_qty"%>" VALUE="<%= qty.get("dqty")==null?item.getQtyDamage():Integer.parseInt(qty.get("dqty").toString()) %>"></TD>
  <TD ALIGN=LEFT NOWRAP><% if(true){%><INPUT TYPE=TEXT SIZE=6 NAME="<%= item.getAsnItem().getInventoryNum()+"_weight"%>" VALUE="<%=qty.get("weight")==null?ASNEditServlet.returnNeedToWeigh(item.getInventoryFkey()):qty.get("weight")%>"><%}%> </td>
 </TR>
     <%
         try{
         if(LotManager.isInventoryIdLotControlled(item.getInventoryFkey())) {

             %>
      <script>var <%= "qtylist"+item.getAsnItem().getInventoryFkey()%> =[]; </script>

    <%

           if(item.getLots().size()>0){

             for(OwdLotReceiveItem rItem:item.getLots()){
                 %>

           <tr>
               <td colspan="5">
                   <table id="<%= item.getAsnItem().getInventoryFkey()%>">
                       <tr>
                           <td>
                            Lot Code: <input type="text" name="<%= "lots_"+rItem.getId()+"_lotvalue"%>" value="<%= rItem.getLotValue()%>">
                           </td>
                           <td>
                               Qty: <input type="text" size="6" name="<%="lots_"+rItem.getId()+"_qty"%>" value="<%= rItem.getQuantityChange()%>" id="<%= item.getAsnItem().getInventoryFkey()+"_lots_"+rItem.getId()+"_qty"%>">
                           </td>
                           <td>
                               DMG: <input type="text" size="6" name="<%="lots_"+rItem.getId()+"_dmg"%>" value="<%= rItem.getQuantityDamage()%>">
                           </td>
                       </tr>
                   </table>
               </td>

           </tr>
             <script>
                 <%= "qtylist"+item.getAsnItem().getInventoryFkey()%>.push('<%= item.getAsnItem().getInventoryFkey()+"_lots_"+rItem.getId()+"_qty"%>');
                 $("#<%= item.getAsnItem().getInventoryFkey()+"_lots_"+rItem.getId()+"_qty"%>").change(function(){
                          checkLotToItemValue(<%= "qtylist"+item.getAsnItem().getInventoryFkey()%>,'<%= item.getAsnItem().getInventoryFkey()+"_rcv_qty"%>');
                 });
             </script>
    <%
             }

        } else{
                 //There are no predefined lots add one default
                 //use hiddend field to keep track of new lots
              %>
    <tr>
        <input type="hidden" name="<%= "newLot_"+item.getInventoryFkey()+"_total"%>" value="1" id="<%= "newlot_"+item.getInventoryFkey()+"total"%>"/>
        <td colspan="5">
            <table>
                <tr>
                    <td>
                        Lot Code: <input type="text" name="<%= "new_lot_1_"+item.getInventoryFkey()+"_lotValue"%>" >
                    </td>
                    <td>
                        Qty: <input type="text" size="6" name="<%="new_lot_1_"+item.getInventoryFkey()+"_qty"%>" >
                    </td>
                    <td>
                        DMG: <input type="text" size="6" name="<%="new_lot_1_"+item.getInventoryFkey()+"_dmg"%>" >
                    </td>
                </tr>
            </table>
        </td>

    </tr>

    <%

             }%>
    <script>
        $("#<%= item.getAsnItem().getInventoryFkey()+"_rcv_qty"%>").change(function(){
           alert('Please make sure lot qty\'s match this change');

        });
    </script>
    <%

         }
     }  catch(Exception ex){
             ex.printStackTrace();
         }
     %>
 <% } %>


</table>
</FORM>


  <iframe width=188 height=166 name="gToday:datetime:agenda.js:gfPop:plugins_12.js" id="gToday:datetime:agenda.js:gfPop:plugins_12.js" src="/webapps/popcal/DateTime/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; left:-500px; top:0px;">
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
