<%@ page import="com.owd.core.business.order.LineItem"%>
<%@ page import="com.owd.core.business.order.Order"%>
<%@ page import="com.owd.web.carts.autumnleaves.AutumnLeavesCart" %>
<%@ page import="com.owd.web.carts.autumnleaves.AutumnLeavesServlet" %>
<%@ page import="java.util.List" %>
<%
    System.out.println("key="+AutumnLeavesServlet.kCartKey);
    AutumnLeavesCart cart = (AutumnLeavesCart) request.getSession(true).getAttribute(AutumnLeavesServlet.kCartKey);
    Order order = cart.getOrder();
    order.recalculateBalance();


   // String redirectURL = request.getServletPath();




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

             if(request.getParameter("pid")!=null && "edit".equals(request.getParameter("action")))
      		{


				//get part num
				String partNum = (String)(request.getParameter("pid"));
				String quantity = (String)(request.getParameter("qty"));

                  int qty=0;

                try
                {
                    qty = new Integer(quantity).intValue();
                } catch (Exception ex)
                {
                    qty=0;
                }

                if(cart.containsSKU(partNum))
                {
                    if(qty>0)
                    {
                        boolean hasFreeItem = false;
                      for(LineItem item:(List<LineItem>)cart.getOrder().skuList)
                   {
                       if(item.total_price == 0.00f)
                       {
                           hasFreeItem = true;
                       }
                   }
                      if(hasFreeItem)
                      {
                          request.setAttribute(AutumnLeavesServlet.kCartError,"Only one unit of a free item may be included in your order");
                      }   else
                      {
                      cart.resetQuantity(partNum,qty);
                      }
                    }   else
                    {
                       cart.resetQuantity(partNum,0);
                    }

                }


		}
              }catch(Exception ex){request.setAttribute(AutumnLeavesServlet.kCartError,ex.getMessage());}
        try
        {

            boolean hasFreeItem = false;

             if(request.getParameter("PRODUCT_ID")!=null)
      		{
                if (cart.old_order != null) cart.old_order = null;


                  for(LineItem item:(List<LineItem>)cart.getOrder().skuList)
                   {
                       if(item.total_price == 0.00f)
                       {
                           hasFreeItem = true;
                       }
                   }

                //get part num
				String partNum = (String)(request.getParameter("PRODUCT_ID"));
				String quantity = (String)(request.getParameter("Quantity"));

                  int qty=0;

                try
                {
                    qty = new Integer(quantity);
                } catch (Exception ex)
                {
                    qty=1;
                }


                if(cart.getQuantityForSKU(partNum)==0)
                {
                   cart.addItem(partNum,qty);
                }




        }
              }catch(Exception ex){request.setAttribute(AutumnLeavesServlet.kCartError,ex.getMessage());}


     order.recalculateBalance();

%><jsp:include page="bitehead.jsp"></jsp:include>
<div id="cart">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<% if (!("".equals(request.getAttribute(AutumnLeavesServlet.kCartError))))
{
%>
<TR><td colspan=5><BR><font face="Arial, Geneva, sans-serif" color="red" size="+1"><%= request.getAttribute(AutumnLeavesServlet.kCartError)%><P> </font></TD></TR>
<%
}
%>
<TR><TD COLSPAN="5" align=left><FORM ACTION="http://www.autumnleavesllc.com/On-Line-Ordering-Flaxseed-oil.php" METHOD="GET">
<INPUT TYPE=SUBMIT NAME="catalog" VALUE="<-Back to Catalog">
  </FORM>
<FORM ACTION="/carts/autumnleaves/autumnleaves.jsp" METHOD="POST"></TD></TR>
<tr VALIGN="BOTTOM">
  <TH ></TH>
  <TH  ><font size="-1" face="Arial, Geneva, sans-serif"><B>Product</B></TH>
  <TH ALIGN="CENTER" ><font size="-1" face="Arial, Geneva, sans-serif">Quantity</TH>
  <TH ALIGN="CENTER" ><font size="-1" face="Arial, Geneva, sans-serif">Price Each</TH>
  <TH ALIGN="RIGHT" ><font size="-1" face="Arial, Geneva, sans-serif">Total</TH>
</tr>
<tr>
    <td colspan="5"><BR></td>
</tr>
<%
	java.text.DecimalFormat decform = new java.text.DecimalFormat("#,###,##0.00");
	if(cart.getLineCount() > 0)
	{

	for(int i=0;i<order.skuList.size();i++)
	{
		LineItem item = (LineItem)order.skuList.elementAt(i);
%>
<!--SoftCart.cart.product.Loopbegin-->

<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="LEFT"><font size="-1" face="Arial, Geneva, sans-serif"><%=item.description%></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Arial, Geneva, sans-serif"><INPUT TYPE=TEXT SIZE=3 NAME="<%=item.client_part_no%>" VALUE="<%=item.quantity_request%>"></td>
  <td ALIGN="CENTER" NOWRAP><font size="-1" face="Arial, Geneva, sans-serif">$ <%=decform.format(item.sku_price)%></td>
  <td ALIGN="RIGHT" NOWRAP><font size="-1" face="Arial, Geneva, sans-serif">$ <%=decform.format(item.total_price)%></td>
</tr>

<%
	}
%>
<tr>
  <td colspan=5 align=right>
<HR color="#000000">
</td>
</tr>
<tr>
  <td COLSPAN=5 ALIGN="RIGHT" NOWRAP><font size="-1" face="Arial, Geneva, sans-serif"><B>Product Subtotal&nbsp;&nbsp;&nbsp;&nbsp;$ <%=decform.format(order.total_product_cost)%></B></td>
</tr>
    <tr><td colspan=5 align=right>
<BR>
</tr><tr>
  <td colspan=5 align=right>
  <INPUT TYPE=HIDDEN NAME=do VALUE=3><INPUT TYPE=SUBMIT NAME="Update" VALUE="Update Subtotal"><BR><font size="-1" face="Arial, Geneva, sans-serif">Click here to change item quantities.<BR>Use zero to remove an item.</FORM></td>
</tr>
<%
	}else{
%>
<tr VALIGN="TOP">
  <td ALIGN="LEFT"><BR></td>
  <td ALIGN="CENTER" NOWRAP colspan="4"><font size="-1" face="Arial, Geneva, sans-serif"><B>Cart is empty</B></td>
</tr>
<% 	} %>
<tr>
  <td colspan=5 align=right>
<HR color="#000000"></td>
</tr>
<tr>
  <td colspan=2 align=left> <br><P>&nbsp;</P>
      All orders ship within one business day.<P>
      
      Interested in bulk (unpackaged) quantities? <a href="http://www.autumnleavesllc.com/contact-us.php">Contact us for wholesale pricing!</A>
      <FORM ACTION="http://www.autumnleavesllc.com/On-Line-Ordering-Flaxseed-oil.php" METHOD="GET">
<INPUT TYPE=SUBMIT NAME="catalog" VALUE="<-Back to Catalog">
  </FORM>
</td>
  <td colspan=3 align=right><FORM ACTION="/carts/autumnleaves/autumnleaves.jsp" METHOD="POST">
  <INPUT TYPE=HIDDEN NAME=cd VALUE=447><INPUT TYPE=HIDDEN NAME=do VALUE=4><INPUT TYPE=SUBMIT NAME="Checkout" VALUE="Checkout->">
  </FORM>
</td>
</tr>

</TABLE>
    </div>
<jsp:include page="bitefoot.jsp"/>

 