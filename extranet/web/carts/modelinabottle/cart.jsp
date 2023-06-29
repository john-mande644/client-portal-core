<%


	com.owd.web.carts.modelbottle.ModelBottleCart cart = (com.owd.web.carts.modelbottle.ModelBottleCart) request.getSession(true).getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartKey);
	if(cart==null) { cart = new com.owd.web.carts.modelbottle.ModelBottleCart("316"); request.getSession(true).setAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartKey,cart); }
	com.owd.core.business.order.Order order = cart.getOrder();
	order.recalculateBalance();

	String returnURL = "http://www.modelinabottle.com/display.php";
	String redirectURL = "";




	if(request.getHeader("Referer") != null)
	{
	if(request.getHeader("Referer").indexOf("secure.owd.com")>=0)
	{
		//internal
	}else
	{
		cart.setReturnURL(request.getHeader("Referer"));
	}
	}




		try
		{
            if(request.getParameter("NEW")!=null)
      		{
               String reset = (String)(request.getParameter("NEW")); 
                if("YES".equals(reset))
                {
                    cart.old_order = null;
                }
                
            }
            
      		if(request.getParameter("PRODUCT_ID")!=null)
      		{
                if (cart.old_order != null) cart.old_order = null;
                  
				//get part num
				String partNum = (String)(request.getParameter("PRODUCT_ID"));
				String quantity = (String)(request.getParameter("Quantity"));

                  int qty=0;

                try
                {
                    qty = new Integer(quantity).intValue();
                } catch (Exception ex)
                {
                    qty=1;
                }

				cart.addItem(partNum,qty);//,new Integer(quantity).intValue());

		}
      		}catch(Exception ex){request.setAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError,ex.getMessage());}


 	order.recalculateBalance();
 	if(redirectURL.indexOf("http://") == 0)
 	{
 		if(request.getParameter("COMMISSION") != null)
		{
			if(request.getParameter("COMMISSION").trim().length()>2)
				order.po_num = request.getParameter("COMMISSION");
		}
 		response.sendRedirect(redirectURL);
 	}
%>

<jsp:include page="top.jsp"/>
<!--<DISPLAY CART>-->
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<TR><TD width="5"> <BR> </TD><TD>
<table border="0" cellpadding="3" cellspacing="0" width="100%" >
<TR><td colspan=2 align=LEFT>
<% if (!("".equals(request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError)) || null==(request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError))))
{
%>
<TR><td colspan=4><font color="red"><%= request.getAttribute(com.owd.web.carts.modelbottle.ModelBottleServlet.kCartError)%><P>If you continue to encounter a problem, you may email us at shipping@modelinabottle.com for further assistance.<P> </font></TD></TR>
<%
}
%>

<tr VALIGN="BOTTOM" >
  <TH ALIGN="LEFT" NOWRAP class="xxx"><font size="-1" face="Arial, Geneva, sans-serif"><B>Product</B>&nbsp;</TH>
  <TH ALIGN="CENTER" NOWRAP class="xxx"><font size="-1" face="Arial, Geneva, sans-serif">Quantity&nbsp;</TH>
  <TH ALIGN="CENTER" NOWRAP class="xxx"><font size="-1" face="Arial, Geneva, sans-serif">Price&nbsp;Each&nbsp;</TH>
  <TH ALIGN="RIGHT" NOWRAP class="xxx"><font size="-1" face="Arial, Geneva, sans-serif">Line Total</TH>
</tr>
<tr>
  <td colspan=4 align=right>
<HR color="#000000">
</td>
</tr>
<%
	java.text.DecimalFormat decform = new java.text.DecimalFormat("#,###,##0.00");
	if(cart.getLineCount() > 0)
	{

	for(int i=0;i<order.skuList.size();i++)
	{
		com.owd.core.business.order.LineItem item = (com.owd.core.business.order.LineItem)order.skuList.elementAt(i);
%>
<!--SoftCart.cart.product.Loopbegin-->

<tr VALIGN="TOP"><FORM ACTION="/carts/modelinabottle/store" METHOD="POST">

  <td ALIGN="LEFT"><font size="-1" face="Arial, Geneva, sans-serif"><%=item.description%></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Arial, Geneva, sans-serif"><INPUT TYPE=TEXT SIZE=3 NAME="<%=item.client_part_no%>" VALUE="<%=item.quantity_request%>"></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Arial, Geneva, sans-serif">$ <%=decform.format(item.sku_price)%></td>
  <td ALIGN="RIGHT" NOWRAP><font size="-1" face="Arial, Geneva, sans-serif">$ <%=decform.format(item.total_price)%></td>
</tr>

<%
	}
%>
<tr>
  <td colspan=4 align=right><INPUT TYPE=HIDDEN NAME=do VALUE=3><INPUT TYPE=SUBMIT NAME="Update" VALUE="Update Subtotal"><BR><font size="-2" face="Arial, Geneva, sans-serif">Change item quantities, then click to update your subtotal.<BR>Enter a zero to remove an item.</FORM>
<HR color="#000000">
</td>
</tr>
<tr>
  <td COLSPAN=4 ALIGN="RIGHT" NOWRAP><font size="-1" face="Arial, Geneva, sans-serif"><P><B>Product Subtotal&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_product_cost)%></B></td>
</tr> <tr><td align=left valign=top><FORM ACTION="http://www.modelinabottle.com/" METHOD="GET">
<INPUT TYPE=SUBMIT NAME="catalog" VALUE="<-Back to Product Page">
  </FORM>
  </td><td colspan=2 align=right valign=top></td><td align=right><FORM ACTION="/carts/modelinabottle/store" METHOD="POST"><INPUT TYPE=HIDDEN NAME=do VALUE=4><INPUT TYPE=SUBMIT NAME="Checkout" VALUE="Checkout->"><BR><font size="-2" face="Arial, Geneva, sans-serif">Pay on-line with your<BR>credit/debit card </FORM></td>
</tr>
<%
	}else{
%>
<tr VALIGN="TOP">
  <td ALIGN="CENTER" NOWRAP colspan="4"><font size="-1" face="Arial, Geneva, sans-serif"><B>Cart is empty</B></td>
</tr>
<% 	} %>
<tr>
  <td colspan=4 align=right>
<HR color="#000000"></td>
</tr>
<tr>
  <td colspan=2 align=left>
</td>
<TD align=CENTER>

</TD>
  <td colspan=2 align=right>

</td>
</tr>

<TR><TD COLSPAN=4>



<!--
<p><font face="Arial, Geneva, sans-serif" size=-1>For customer service, contact us via email at
<a href="mailto://<%= cart.storeEmail %>"><%= cart.storeEmail %></A></font></p>-->
<p>

</TD></TR>

</TABLE>

</TD><TD width="100"> <BR> </TD></TR></TABLE>
<!-- END CART -->
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
var pageTracker = _gat._getTracker("UA-29586944-1");
pageTracker._setDomainName("none");
pageTracker._setAllowHash(false);
pageTracker._setAllowLinker(true);
pageTracker._trackPageview("/cart/cart");
</script>
<jsp:include page="bottom.jsp"/>
