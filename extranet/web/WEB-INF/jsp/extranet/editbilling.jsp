<%
  	com.owd.core.business.order.OrderStatus order = (com.owd.core.business.order.OrderStatus)request.getAttribute(com.owd.web.servlet.OrdersManager.kCurrentOrder);
	java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");
	com.owd.core.business.Contact billc = order.billContact;
	com.owd.core.business.Address billa = order.billAddress;
%>

<P>

<fontx COLOR=RED><B><%= (String)request.getAttribute("formerror")==null?"":(String)request.getAttribute("formerror") %></B></fontx>
<FORM METHOD=POST ACTION="<%= com.owd.web.servlet.ExtranetServlet.kExtranetURLPath%>?<%=com.owd.web.servlet.OrdersManager.kParamOrderMgrAction%>=<%=com.owd.web.servlet.OrdersManager.kParamOrderEditBillingAction%>&<%=com.owd.web.servlet.OrdersManager.kparamOrderID%>=<%= order.order_id%>"><INPUT TYPE=HIDDEN NAME="editbillingsave" VALUE="1">

							<table cellpadding=1 cellspacing=0 border=0 bgcolor=#bbbbbb width=100% align="center">
								<tr>
									<td>
										<table cellpadding=2 cellspacing=0 border=0 width=100% bgcolor=666666>
											<tr>
												<td colspan=2 bgcolor=f0f0f0>
													<h3 class="formtitle">Edit Customer Information for Order <%= order.orderReference %> (<%= order.OWDorderReference %>)</h3>
												</td>
											</tr>
											<tr>
												<td colspan=2 bgcolor=f0f0f0>
													Use this form to edit the fields of this issue. You can optionally add a comment at the same time explaining your changes.
												</td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label">
																Name: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<INPUT SIZE=50 TYPE=TEXT NAME=billc.name VALUE="<%= billc.name %>"></td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label">
																Company Name: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<INPUT SIZE=50 TYPE=TEXT NAME=billa.company_name VALUE="<%= billa.company_name %>">
												</td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label"> Address Line 1: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<INPUT SIZE=50 TYPE=TEXT NAME=billa.address_one VALUE="<%= billa.address_one %>"></td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label"> Address Line 2: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
												<INPUT SIZE=50 TYPE=TEXT NAME=billa.address_two VALUE="<%= billa.address_two %>"></td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label"> City: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<INPUT SIZE=50 TYPE=TEXT NAME=billa.city VALUE="<%= billa.city %>">
												</td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label"> State: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<INPUT SIZE=50 TYPE=TEXT NAME=billa.state VALUE="<%= billa.state %>">
												</td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label"> Postal&nbsp;Code: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<INPUT SIZE=50 TYPE=TEXT NAME=billa.zip VALUE="<%= billa.zip %>">
												</td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label"> Country: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<%= com.owd.core.business.order.OrderUtilities.getCountryList().getHTMLSelect(billa.country, "billa.country") %>
												</td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label"> Email: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<INPUT SIZE=50 TYPE=TEXT NAME=billc.email VALUE="<%= billc.email %>"></td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label"> Phone: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<INPUT SIZE=50 TYPE=TEXT NAME=billc.phone VALUE="<%= billc.phone %>"></td>
											</tr>

											<tr>
												<td colspan=2 bgcolor=ffffff>
													&nbsp;
												</td>
											</tr>
											<tr>
												<td colspan=2 bgcolor=f0f0f0>
													<b>Comment</b>: (an optional comment describing the context of your changes)
												</td>
											</tr>
											<tr>
												<td align="right" valign="top" bgcolor="fffff0">
													<span class="label"> Comment: </span>
												</td>
												<td bgcolor="ffffff" nowrap>
													<textarea name="comment" cols="50" rows="4" wrap="virtual">
													</textarea>
												</td>
											</tr>
                                             <tr>
												<td colspan=2 bgcolor=ffffff>
													&nbsp;
												</td>
											</tr>
											
											<tr>
												<td bgcolor=ffffff valign=top colspan=2 align=center>
													<INPUT TYPE=SUBMIT NAME=submit VALUE="Save Changes">
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
</form>

