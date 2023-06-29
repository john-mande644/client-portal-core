


<%@ page import="com.owd.core.business.order.Inventory,com.owd.hibernate.HibernateSession,com.owd.web.servlet.ExtranetServlet, com.owd.web.servlet.InventoryManager,java.sql.ResultSet,java.util.List" %>
<%

      System.out.println("in edititem.jsp 1");
  	Inventory item = (Inventory) request.getAttribute(InventoryManager.kCurrentItem);
     System.out.println("in edititem.jsp 2");

    if(item==null)    System.out.println("item null in edititem.jsp");
	java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
                  System.out.println("in edititem.jsp 3");

    Integer listindex = (Integer) request.getSession(true).getAttribute("inventorylistindex");

    if(listindex == null) listindex = new Integer(0);



%>
<table width=100% cellpadding=0 cellspacing=0 border=0 bgcolor=ffffff>
<tr ><td valign=top bgcolor=f0f0f0 nowrap>
    <table cellpadding=3 cellspacing=0 border=0 width=100% >
          <tr><td align=center valign=top>
        <table cellpadding=1 cellspacing=0 border=0 bgcolor=#000000 >
																<tr>
																	<td >
																		<table cellpadding=2 cellspacing=0 border=0 bgcolor=f0f0f0 width=100%>
																			<tr>
																				<td bgcolor=dddddd align=center nowrap>
                                                                                <div align=center>
																					<b><a href="<%=ExtranetServlet.kExtranetURLPath%>?<%=InventoryManager.kParamInvMgrAction%>=<%=InventoryManager.kParamInvExploreAction%>" /><font color=blue>Return to search</font></a></b>
                                                                                    </div>
																				</td>
																			</tr>
																			<tr>
																				<td nowrap> <div align=center valign=center>
                                                                                <% int index=listindex.intValue();
                                                                                    int listSize = ((List)request.getSession(true).getAttribute("inventoryfinderresults")).size();
                                                                                    %>
																					<font size=1> Item <b><%= index+1 %></b> of <b><%= request.getSession(true).getAttribute("orderfinderresults")==null?"1":listSize+"" %></b> item(s)
																						<br>

																						<a href="<%=ExtranetServlet.kExtranetURLPath%>?<%=InventoryManager.kParamInvMgrAction%>=<%=InventoryManager.kParamInvEditAction%>&listindex=<%= index-1%>"> &lt;&lt; Previous</a> || <a href="<%=ExtranetServlet.kExtranetURLPath%>?<%=InventoryManager.kParamInvMgrAction%>=<%=InventoryManager.kParamInvEditAction%>&listindex=<%= index+1 %>">Next &gt;&gt;</a>
																					</div>
																			</td>
																		</tr>
																	</table>
																</td>
															</tr>
														</table>

              </td></tr>
    <tr><td bgcolor=dddddd ><b>Available Actions</b>
    </td></tr>
    </table>
<img src="/webapps/images/bluepixel.gif" width=100% height=1 border=0 valign=top >
<table cellpadding=3 cellspacing=0 border=0 bgcolor=f0f0f0>








   <tr><td>
        <img src="/webapps/images/icons/bullet_creme.gif" height=8 width=8 border=0 align=absmiddle>
        <b><a href="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.InventoryManager.kparamInvID%>=<%= item.inventory_id%>&<%=com.owd.web.servlet.InventoryManager.kParamInvMgrAction%>=<%=com.owd.web.servlet.InventoryManager.kParamInvDeleteAction%>" >Edit</a></b> this item
    </td></tr>



</table>

</td><td valign=top width=100% >

<%
    String status = "";
   if(item.qty_on_hand>0 && item.is_active==1)
   {
       status = "In&nbsp;Stock";
   }
    else
   {
       if(item.is_active == 0)
       {
         status = "Inactive";  
       } else
       {
           status = "Out&nbsp;Of&nbsp;Stock";
       }
   }
    
%>
<B><%= (String)request.getAttribute("formerror")==null?"":(String)request.getAttribute("formerror")+"<BR>" %></B>
<table cellpadding=5 bgcolor=ffffff width=100%>
<tr><td valign="top">
  <table cellpadding=1 cellspacing=0 border=0 bgcolor=bbbbbb width=100% >
							<tr>
								<td>
									<table cellpadding=4 cellspacing=0 border=0 width=100% bgcolor=ffffff>
										<tr>
											<td bgcolor=f0f0f0 width=99%  ><table width=100% cellpadding=0 cellspacing=0>
													<tr><td bgcolor=f0f0f0  valign=center><h3 class="formtitle"><%= item.inventory_num %>&nbsp;:&nbsp;<%= item.description %>
</h3> <font size=1> Created: <font color=336699><%= item.created_date %> </font><B>by <%=item.created_by%></B> </font>
                                                        </td>
                                                        <td NOWRAP bgcolor=f0f0f0 valign=center width=1%>
                                                <h2 ><center>&nbsp;&nbsp;<%= status %>&nbsp;(<%= item.qty_on_hand%>)&nbsp;&nbsp;</center></h2></td>
												</tr>
											</table>
									</tr>


								</table>
							</td>
						</tr>
					</table>




&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% >
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=left>
			&nbsp;<font color=ffffff><b>General Information:</b></font>&nbsp;
	</td>
	<td align=right width=99%>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0  width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0  width=100%>
<tr>
	<td bgcolor=ffffff valign=top>
    <table cellpadding=1>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;SKU:&nbsp;</B></TD><TD width=100%><%= item.inventory_num %></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;Title:&nbsp;</B></TD><TD width=100%><%= item.description%></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;Color:&nbsp;</B></TD><TD width=100%><%= item.item_color %></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;Size:&nbsp;</B></TD><TD width=100%><%= item.item_size%></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;Weight&nbsp;(lbs):&nbsp;</B></TD><TD width=100%><%= item.weight_lbs%></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;Keyword:&nbsp;</B></TD><TD width=100%><%= item.keyword %></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;Supplier:&nbsp;</B></TD><TD width=100%><%= item.mfr_part_num%></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;Unit&nbsp;Price:&nbsp;</B></TD><TD width=100%><%= item.price%></TD></TR>



</table></td></tr>

</table>
</td>
</tr>
</table>
&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% >
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=left>
			&nbsp;<font color=ffffff><b>Export Information:</b></font>&nbsp;
	</td>
	<td align=right width=99%>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0  width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0  width=100%>
<tr>
	<td bgcolor=ffffff valign=top>
    <table cellpadding=1>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;Harmonized&nbsp;Code:&nbsp;</B></TD><TD width=100%><%= item.harm_code %></TD></TR>
<TR><TD ALIGN=RIGHT NOWRAP bgcolor=bbee88><B>&nbsp;Documents&nbsp;Only?&nbsp;</B></TD><TD width=100%><%= item.is_documents==1?"Yes":"No" %></TD></TR>
</table></td></tr>

</table>
</td>
</tr>
</table>
&nbsp;<P>
<table cellpadding=2 cellspacing=0 border=0 width=100% >
<tr>
	<td bgcolor=00aa00 width=1% nowrap align=left>
			&nbsp;<font color=ffffff><b>Receives</b></font>&nbsp;
	</td>
    <td align=right width=5></td>
    <td bgcolor=00aa00 width=1% nowrap align=left>
			&nbsp;<A HREF=""><font color=ffffff><b>Returns</b></font></A>&nbsp;
	</td>   <td align=right width=5></td>
    <td bgcolor=00aa00 width=1% nowrap align=left>
			&nbsp;<A HREF=""><font color=ffffff><b>Sales</b></font></A>&nbsp;
	</td>
	<td align=right width=99%>
</td>
</tr>
</table>
<table cellpadding=1 cellspacing=0 border=0  width=100% bgcolor=bbbbbb align=center><tr><td>
<table cellpadding=2 cellspacing=0 border=0  width=100%>
<tr height=100%>
	<td bgcolor=ffffff valign=top width=50%>
<%
        System.out.println("starting history retrieval");
        ResultSet rs = HibernateSession.getResultSet("select "
            + "TransactionType as 'Transaction Type',max(TransactionDate) as 'Transaction Date'"
            + ",sum(QuantityChange) as 'Quantity Change'"
            + "from vw_item_history "
            + "where inventory_num = '"+item.inventory_num+"' and client_fkey="+item.client_fkey+" "
            + "  group by transactiontype");
          System.out.println("done with history retrieval");
    while(rs.next())
    {
        %><%= rs.getString(1) %>:<%= rs.getString(2)%>:<%= rs.getString(3)%><BR><%
    }

            rs.close();
            HibernateSession.closeSession();


%>
    </td>
</tr>
</table>
</td>
</tr>
</table>
&nbsp;<P>
<%


    %>
		</TD></TR>
		</FORM></TABLE>
