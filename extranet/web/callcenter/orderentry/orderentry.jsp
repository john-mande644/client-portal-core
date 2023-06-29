<html>
<%@ page import="com.owd.core.OWDUtilities,
				 com.owd.core.business.Address,
				 com.owd.core.business.Client,
				 com.owd.core.business.Contact,
				 com.owd.core.business.client.ShippingOption,
				 com.owd.core.business.client.UnableToRateShipmentException,
				 com.owd.core.business.order.Coupon,
				 com.owd.core.business.order.LineItem,
				 com.owd.core.business.order.Order,
				 com.owd.core.dbUtilities,
				 com.owd.core.xml.OrderXMLDoc,
				 java.text.DateFormat,
				 java.text.DecimalFormat,
				 java.text.NumberFormat,
				 java.text.SimpleDateFormat,
				 java.util.*"
%>
<%@ page import="com.owd.core.business.order.OrderUtilities" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%
	String client_id = "";
	String client_name = "";
	boolean allowShipPriceEditing = false;
	String user_client_id = "";
	Order currorder=null;
	List shipOptions = null;
	List customFields = null;
	Client client = null;
	String trackEvent = null;


     DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    
   
    //get logged-in user assigned client id
	user_client_id = Client.getClientIDForUser(request.getUserPrincipal().getName());
    System.out.println("got client id for user:"+user_client_id);
	if(user_client_id==null )    {	%>
		<jsp:forward page="select_client.jsp" />
<%}
	//get session-stored client id and set to user client id if missing
	client_id=(String)request.getSession(true).getAttribute("client_id");
	if(client_id == null) client_id = user_client_id;
	if(client_id.equals("")) client_id = user_client_id;

	//check authorization and reset if necessary
	if(user_client_id.equals(client_id) && new Integer(user_client_id).intValue() > 0)
	{
		//we're good - client login
		System.out.println("got client login");
		client = Client.getClientForUser(request.getUserPrincipal().getName());
		System.out.println("got client "+client);

	}else if(user_client_id.equals("0"))
	{
		//internal user
        System.out.println("internal user");
		//is this a request to reset the client?
		String curr_id = (String) request.getParameter("selected_client");
		if(curr_id == null) curr_id = "0";

		if(new Integer(curr_id).intValue() > 0)
		{
			//we have a selected client
			client_id = curr_id;
			client = Client.getClientForID(client_id);

		}else
		{
			//no requested client
			if(client_id.equals("0"))
			{
				//need to select a client
				%>
		<jsp:forward page="select_client.jsp" />
<%
			}else{
				//should be an existing client
				if(new Integer(client_id).intValue() > 0)
				{
					client = Client.getClientForID(client_id);
				}      else
				{
					%>
		<jsp:forward page="select_client.jsp" />
<%
				}

			}
		}

	}else
	{
	%>
		<jsp:forward page="select_client.jsp" />
<%
	}

	if(null==client)
	{
%>
		<jsp:forward page="select_client.jsp" />
<%
	}
	else
	{

		client_id = client.client_id;
		request.getSession(true).setAttribute("client", client);
		request.getSession(true).setAttribute("client_id", client.client_id);
		client_name = client.company_name;
		request.getSession(true).setAttribute("client_name", client.company_name);
		//client_url = client.url_string;
		request.getSession(true).setAttribute("client_url", client.url_string);


   String editaction = (String)request.getParameter("editaction");
	if(editaction==null) editaction= "";

	if("Y".equals((String)request.getParameter("logout")) && !"Y".equals((String)request.getAttribute("stoplogout")))

	{
		System.out.println("logging out...\n");
		request.getSession(true).setAttribute("client", null);
		request.getSession(true).setAttribute("client_id", null);
		request.getSession(true).setAttribute("client_name", null);
		request.getSession(true).setAttribute("client_url", null);
		request.getSession(true).setAttribute("error",null);
		request.getSession(true).setAttribute("warning",null);
		request.getSession(true).setAttribute("currorder",null);
		request.getSession(true).setAttribute("shipoptions",null);
		request.getSession(true).setAttribute("customFields",null);
		request.getSession(true).setAttribute("contactID","");
		request.getSession(true).setAttribute("allowShipPriceEditing",null);
		request.setAttribute("stoplogout","Y");
	 	request.getRequestDispatcher("orderentry.jsp").forward(request,response);
  }
else
{
	com.owd.core.business.client.ClientPolicy policy = null;
	policy = client.getClientPolicy();



	String errorMessage = "";
	String warning = "";
	errorMessage = (String)request.getSession(true).getAttribute("error");
	warning = (String)request.getSession(true).getAttribute("warning");

	request.getSession(true).setAttribute("error",null);
	request.getSession(true).setAttribute("warning",null);
		currorder = (Order) request.getSession(true).getAttribute("currorder");
		shipOptions = (List) request.getSession(true).getAttribute("shipoptions");
		customFields = (List) request.getSession(true).getAttribute("customFields");

        String contact = request.getParameter("CALL_ID");
        if(contact == null) contact = "";
        if(contact.length()>0)
        {
           // currorder.contactID = contact;
		request.getSession(true).setAttribute("contactID",contact);
        }

    if(null==currorder || (editaction).indexOf("Start New Order")>=0 || (!(currorder.clientID.equals(policy.getClient().client_id))))
	{

        currorder = policy.createInitializedOrder();
        currorder.contactID = (String) request.getSession(true).getAttribute("contactID");
        currorder.order_type = "OWD Order Entry";

        request.getSession(true).setAttribute("currorder",currorder);
		customFields = policy.getCustomOrderFields(currorder);
		request.getSession(true).setAttribute("customFields",customFields);
		request.getSession(true).setAttribute("shipoptions",null);
	}



    allowShipPriceEditing = policy.allowShipPriceEditing(currorder);

		String allowShipPriceEditingStr = request.getParameter("allowShipPriceEditing");

		if(editaction.indexOf("Ship Cost")>0)
		{
			if(allowShipPriceEditingStr==null)
		{

			 allowShipPriceEditingStr =  (String) request.getSession(true).getAttribute("allowShipPriceEditing");

			if(allowShipPriceEditingStr==null)
			{

				 allowShipPriceEditingStr = "0";
			}
		}

			if(currorder != null) currorder.total_shipping_cost = 0.00f;

		}else
		{
			 allowShipPriceEditingStr =  (String) request.getSession(true).getAttribute("allowShipPriceEditing");
			if(allowShipPriceEditingStr==null)
			{
				 allowShipPriceEditingStr = "0";
			}
		}
		request.getSession(true).setAttribute("allowShipPriceEditing",allowShipPriceEditingStr);

		allowShipPriceEditing = allowShipPriceEditing && "1".equals((String) request.getSession(true).getAttribute("allowShipPriceEditing"));


	Address billingAddress = currorder.getBillingAddress();
	Address shippingAddress = currorder.getShippingAddress();
	Contact	billContact = currorder.getBillingContact();
	Contact	shipContact = currorder.getShippingContact();

	DecimalFormat dform = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
	Vector skus = currorder.skuList;
	//
	//
	currorder.total_product_cost = (float)0.000;

	int units = 0;


	for(int i=0;i<skus.size();i++)
	{
		LineItem item = (LineItem) skus.elementAt(i);
		if ((item.quantity_backordered+item.quantity_request) > 0)
		{
			currorder.total_product_cost += item.total_price;

			int qty = (int) item.quantity_request+(int)item.quantity_backordered;


			units += qty;
		}
	}
                 currorder.tax_pct = (float)0.000;
                if(!(policy.allowSalesTaxEditing(currorder)))
				{
					currorder.total_tax_cost = (float)0.000;
					currorder.total_tax_cost = policy.getSalesTaxValue(currorder) ;
				}else
				{
					if(null!= request.getParameter("total_tax_cost"))
					{
					String tax = (String)request.getParameter("total_tax_cost");
					if(tax.trim().equals("")) tax = "xx";
                    System.out.println("setting tax to "+tax);

                    System.out.println("curr tax pct= "+currorder.tax_pct);
                    try
					{
						currorder.total_tax_cost = new Float(tax).floatValue();

					}catch(NumberFormatException ex)
					{
						currorder.total_tax_cost = (float)0.000;
					}
					}


				}
	if (units < 1 && !allowShipPriceEditing) currorder.total_shipping_cost=(float)0;

String coupMessage = "";
String disctype = "";
currorder.discount = 0;
boolean shippingDiscountApplies = false;

    System.out.println("couponcode:"+currorder.coupon_code+":");
if(currorder.coupon_code != null)
{
	if(currorder.coupon_code.length() > 0)
	{
		try
		{
		com.owd.core.business.order.Coupon coupon = Coupon.dbloadByCoupon(currorder.coupon_code,currorder.clientID);

            System.out.println("got coupon");
		currorder.discount += Coupon.getOrderDiscount(currorder.total_product_cost,currorder.total_shipping_cost,currorder.coupon_code,currorder.clientID,currorder.skuList,currorder.getShippingAddress());
            System.out.println("got discount:"+currorder.discount);
		currorder.recalculateBalance();

		if(currorder.discount <= 0.00f)
		{
			switch(coupon.coupon_type)
			{
			case Coupon.kCouponTypeAmount:

			disctype = "(credit&nbsp;for&nbsp;order) ";

			break;
			case Coupon.kCouponTypePercent:

			disctype = "("+(coupon.disc_pct*100)+"%&nbsp;discount&nbsp;on&nbsp;product&nbsp;subtotal) ";

			break;
			case Coupon.kCouponTypeShipAmt:

			disctype = "(credit&nbsp;toward&nbsp;shipping) ";
            shippingDiscountApplies = true;

			break;
			case Coupon.kCouponTypeShipPct:

			disctype = "("+(coupon.ship_pct*100)+"%&nbsp;discount&nbsp;on&nbsp;standard&nbsp;shipping) ";
              shippingDiscountApplies = true;
			break;

			default:
			disctype = "Discount";

			}
            
            if(allowShipPriceEditing && shippingDiscountApplies)
            {
                throw new Exception("Shipping discount coupons cannot be applied to orders that allow manual editing of the shipping charge");
            }
		}
		}catch(Exception excp)
		{
            excp.printStackTrace();
			coupMessage = excp.getMessage();
		}
	}else
	{
					//currorder.discount = 0;
	}
}


	currorder.recalculateBalance();

	if(editaction.equals("Update") || editaction.indexOf("Ship Cost")>0)
	{
		if(allowShipPriceEditing)
						{
							try{
								currorder.total_shipping_cost = new Float(request.getParameter("total_shipping_cost")).floatValue();
							}catch(Exception exp){}
						}

		if(policy.allowSalesTaxEditing(currorder))
						{

							String tax = (String)request.getParameter("total_tax_cost");
							if(tax == null) tax = "xx";
							if(tax.trim().equals("")) tax = "xx";

					try
					{
						currorder.total_tax_cost = new Float(tax).floatValue();

					}catch(NumberFormatException ex)
					{
						//throw new Exception("Sales tax value invalid - please re-enter and try again.");
					}
						}

           currorder.recalculateBalance();

	}

    if("FREE".equalsIgnoreCase(currorder.bill_cc_type)||"Custom".equalsIgnoreCase(currorder.bill_cc_type))
			{
				warning = "WARNING: This order has been marked as FREE - no charge will be made to the customer and the order will ship ASAP. If this is not correct, return to the customer information page and choose the proper form of payment.";
			} else if("CK".equalsIgnoreCase(currorder.bill_cc_type) && currorder.holdUntilDate!=null)
			{
                warning = "WARNING: This order has been marked to release immediately and not ship until "+df.format(currorder.holdUntilDate);
  }

    if("SUBMIT".equalsIgnoreCase((String)request.getParameter("SUBMIT")))
	{
        try{
        errorMessage = "";
		System.out.println("handling submit action");
		if(units<1 )
		{
			if(units < 1)
			{
				errorMessage="No items in order : order not saved<BR>";
			}
			else
			{
				errorMessage="Shipping choice not made - edit customer info to set shipping. : order not saved<BR>";
			}
		}
		else
		{
			currorder.backorder_rule = (String)request.getParameter("backorder_rule");
			if (null == currorder.backorder_rule || "null".equals(currorder.backorder_rule))
			{
				currorder.backorder_rule = OrderXMLDoc.kBackOrderAll;
			}

			System.out.println("orderentry.jsp - Payment type: "+ currorder.bill_cc_type);

			currorder.is_future_ship = 0;

			if("FREE".equalsIgnoreCase(currorder.bill_cc_type)||"Custom".equalsIgnoreCase(currorder.bill_cc_type))
			{
				currorder.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

				warning = "WARNING: This order has been marked as FREE - no charge will be made to the customer and the order will ship ASAP. If this is not correct, return to the customer information page and choose the proper form of payment.";
			} else if("CK".equalsIgnoreCase(currorder.bill_cc_type) && currorder.holdUntilDate==null)
			{
				currorder.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;
				currorder.is_future_ship = 1;
			}else if("CK".equalsIgnoreCase(currorder.bill_cc_type) && currorder.holdUntilDate!=null)
			{
				currorder.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                warning = "WARNING: This order has been marked to release immediately and not ship until "+df.format(currorder.holdUntilDate);


            }
			else
			{

				currorder.payment_status = OrderXMLDoc.kPaymentStatusWaitingForPayment;

				int this_year = Calendar.getInstance().get(Calendar.YEAR);
				int this_month = Calendar.getInstance().get(Calendar.MONTH);
			 if(currorder.bill_cc_type.equals("CC")){
					if(currorder.cc_num.length()<=0)
				{
					errorMessage = "Credit Card Number Missing!";
				}
				else
				{
			if(currorder.cc_exp_year<this_year)
					{
						errorMessage = "Credit Card Expired!";
					}
					else if(currorder.cc_exp_year==this_year&&currorder.cc_exp_mon<=this_month)
					{
						errorMessage = "Credit Card Expired!";
					}
				}
			}
                if(currorder.bill_cc_type.equals("ECK")){
                    if(currorder.ck_acct.length()<=0) errorMessage="Checking Account Number Missing!";
                    if(currorder.ck_bank.length()<=0) errorMessage="Check Routing Number is Missing!";
                    if(currorder.ck_number.length()<=0) errorMessage="Check Number is Missing!";
                }
            }
            currorder.ship_call_date = OWDUtilities.getSQLDateForToday();
			currorder.created_by = request.getUserPrincipal().getName();



			String orderNum = "";

			if(currorder!=null)
			{
				currorder.is_future_ship = 0;



			  String sr = (String)request.getParameter("shipmethod");
                if(null == sr){
                    throw new Exception("Ship Method cannot be null, please select a valid ship method");

                }

              System.out.println("final shipping rate: "+sr);
              //currorder.total_shipping_cost = 0;
			try
			{
				if(sr.equals("LTL")){
					currorder.getShippingInfo().setShipOptions("LTL", "Prepaid", "");
				}else{
					currorder.getShippingInfo().setShipOptions(policy.getShippingMethod(currorder,sr,shipOptions).replaceAll("\\ \\(Book\\)","").replaceAll("\\ \\(US\\)","").replaceAll("[()]", ""), "Prepaid", "");

				}
			 }catch(Exception ex)
				{
					ex.printStackTrace();
                    throw new Exception(ex.getMessage());
				}

			   if(!currorder.bill_cc_type.equalsIgnoreCase("FREE") && !allowShipPriceEditing)
			   {
                   try
				   {
			  	currorder.total_shipping_cost =  policy.getShippingCost(currorder,sr,shipOptions);
				   }catch(UnableToRateShipmentException utrex)
				   {
					   currorder.total_shipping_cost = -1;
				   }
			   }else
			   {
				  if(allowShipPriceEditing)
				  {
					  try{
						  if(request.getParameter("total_shipping_cost") != null)
					  			currorder.total_shipping_cost = new Float(request.getParameter("total_shipping_cost")).floatValue();
					  }catch(Exception exp){}
				  }


			   }
				if(currorder.bill_cc_type.equalsIgnoreCase("FREE") && !allowShipPriceEditing)
                {
					currorder.total_shipping_cost  = 0.000f;
                }

                if(currorder.bill_cc_type.equalsIgnoreCase("CK") && currorder.holdUntilDate==null)
                {
                    currorder.is_future_ship = 1;
                }
				if(currorder.total_shipping_cost < 0.00f)
				{
				//	currorder.is_future_ship = 1;
                  //  currorder.total_shipping_cost = 0;
					currorder.getShippingInfo().comments = "Customer requested shipment type ("+sr+") requiring manual rating. Order placed on hold for manual followup and release.";

				}
				currorder.recalculateBalance();




				if("FREE".equalsIgnoreCase(currorder.bill_cc_type) ||
                        ("CK".equals(currorder.bill_cc_type) && currorder.holdUntilDate!=null))
			{
				currorder.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
				currorder.paid_amount = currorder.total_order_cost;
				currorder.is_paid = 1;
				currorder.paid_date = OWDUtilities.getSQLDateNoTimeForToday();
			}



currorder.discount = 0;
 shippingDiscountApplies = false;


if(currorder.coupon_code != null)
{
	if(currorder.coupon_code.length() > 0)
	{
		try
		{
		com.owd.core.business.order.Coupon coupon = Coupon.dbloadByCoupon(currorder.coupon_code,currorder.clientID);


		currorder.discount += Coupon.getOrderDiscount(currorder.total_product_cost,currorder.total_shipping_cost,currorder.coupon_code,currorder.clientID,currorder.skuList,currorder.getShippingAddress());

		currorder.recalculateBalance();

		if(currorder.discount <= 0.00f)
		{
			switch(coupon.coupon_type)
			{
			case Coupon.kCouponTypeAmount:


			break;
			case Coupon.kCouponTypePercent:


			break;
			case Coupon.kCouponTypeShipAmt:

            shippingDiscountApplies = true;

			break;
			case Coupon.kCouponTypeShipPct:

              shippingDiscountApplies = true;
			break;

			default:

			}

            if(allowShipPriceEditing && shippingDiscountApplies)
            {
                throw new Exception("Shipping discount coupons cannot be applied to orders that allow manual editing of the shipping charge");
            }
		}
		}catch(Exception excp)
		{
			coupMessage = excp.getMessage();
		}
	}else
	{
					//currorder.discount = 0;
	}
}


	currorder.recalculateBalance();
      currorder.tax_pct = (float)0.000;
                if(!(policy.allowSalesTaxEditing(currorder)))
				{
					currorder.total_tax_cost = (float)0.000;
					currorder.total_tax_cost = policy.getSalesTaxValue(currorder) ;
				}else
				{
					if(null!= request.getParameter("total_tax_cost"))
					{
					String tax = (String)request.getParameter("total_tax_cost");
					if(tax.trim().equals("")) tax = "xx";
                    System.out.println("setting tax to "+tax);
					try
					{
						currorder.total_tax_cost = new Float(tax).floatValue();

					}catch(NumberFormatException ex)
					{
						currorder.total_tax_cost = (float)0.000;
					}
					}


				}

	currorder.recalculateBalance();
				policy.handleCustomOrderFields(currorder,customFields);

				if(errorMessage.length()==0)
				{
					System.out.println("before saving - order_refnum: "+currorder.order_refnum);
					System.out.println(currorder.template);
					policy.saveNewOrder(currorder,false);
					//currorder.saveNewOrder(OrderUtilities.kHoldForPayment,true);
					System.out.println("after saving - order_refnum: "+currorder.order_refnum);

				}
				else
				{
					session.setAttribute("error", errorMessage);

					%>
					<jsp:forward page="editcustomer.jsp" />
					<%
				}
			orderNum = currorder.order_refnum;
			if(orderNum==null||"".equals(orderNum))
			{
			//order not saved
				errorMessage="Order not completed: "+currorder.last_error;

				  if(!allowShipPriceEditing)
			   {

			  	currorder.total_shipping_cost =  0.00f;

			   }


			}else{
			//order OK
				policy.sendNotificationMessage(currorder,null,null);
				errorMessage = "Order Complete! OWD Reference is: "+orderNum;
                trackEvent = "SalePhone";
                            if(currorder.bill_cc_type.equals("CK"))
                            {
                                trackEvent = "SalePhoneCheck";
                            }

                if(currorder.is_future_ship == 1)
				{

					errorMessage=errorMessage+"<BR>Order placed on hold: "+currorder.last_error;
				}

	%>
			<jsp:forward page="order_entry_response.jsp" />

	<%
			}


		}

		}


	} catch (Exception exc){
                exc.printStackTrace();
         errorMessage= exc.getMessage();
        }
        }

       // ExtranetServlet.authenticateUser(request);
        request.setAttribute("clientselector",Client.getClientForID(client_id).company_name);
        request.setAttribute("areatitle","Order Entry");
        String error = (String) request.getAttribute("errormessage");

    %>

<jsp:include page="/extranettop.jsp" />


<CENTER><B><A HREF="orderstatus.jsp">Order Status</A>&nbsp;&nbsp;&nbsp;
<A HREF="orderentry.jsp">Current Order</A>&nbsp;&nbsp;&nbsp;<% if (user_client_id.equals("0")){ %>
<A HREF="orderentry.jsp?logout=Y">Change Client</A><% } %></B> </CENTER>
<HR>
<% if (client.getClientPolicy().getClass().getName().equals("com.owd.core.business.client.DefaultClientPolicy"))
{

%>
<B><font color=green>Please note: Your order entry screen has not been customized for your business rules. You may place orders using this interface, but you should be aware that:<BR>
<OL>
     <LI>Shipping costs will only be accurate if we have weights listed for all your SKUs.
     <LI>Any special rules or steps taken with your orders submitted via other means will not be taken into account
</OL>
<BR>
If you have any questions or concerns, please contact your Account Manager or send your questions directly to casetracker@owd.com via email.

<HR>
 <%
}
        %>
<TABLE border=0 cellpadding=0 cellspacing=2 width=100%>
<TR>
<TD ALIGN=CENTER valign=top width=50% bgcolor="#000000"><font  color="#FFFFFF" size=-1><B>Bill To:</B></font></TD>
<TD ALIGN=CENTER valign=top width=50% bgcolor="#000000"><font  color="#FFFFFF" size=-1><B>Ship To:</B></font></TD>
</TR>
<TD ALIGN=LEFT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>&nbsp;<BR><%= billContact.name %><BR><%= billingAddress.company_name %><BR><%= billingAddress.address_one %><BR><%= billingAddress.address_two %><BR><%= billingAddress.city %>,<%= billingAddress.state %> <%= billingAddress.zip %><BR><%= billingAddress.country %><P><%= billContact.phone %><BR><%= billContact.email %>
    <% if(currorder.bill_cc_type.equals("ECK"))
    {
    %>
     <P><B>Payment Info:</B><BR><%= currorder.bill_cc_type %> - Rout: <%= currorder.ck_bank %> - Acct: <%= currorder.ck_acct %> - Check Num: <%= currorder.ck_number%></TD>
    <%  } else {%>
    <P><B>Payment Info:</B><BR><%= currorder.bill_cc_type %> - <%= currorder.cc_num %> - Exp <%= currorder.cc_exp_mon %>/<%= currorder.cc_exp_year %></TD>
    <% }%>
<TD ALIGN=LEFT valign=top><fontx face="Geneva, Verdana, Helvetica" size=-1>&nbsp;<BR><%= shipContact.name %><BR><%= shippingAddress.company_name %><BR><%= shippingAddress.address_one %><BR><%= shippingAddress.address_two %><BR><%= shippingAddress.city %>,<%= shippingAddress.state %> <%= shippingAddress.zip %><BR><%= shippingAddress.country %><P><%= shipContact.phone %><BR><%= shipContact.email %></TD>
</TR>
<% if (!currorder.completed)
{
%>
    <TR><TD COLSPAN=2><HR></TD></TR>
<TR><TD ALIGN=LEFT NOWRAP><B>Ship&nbsp;From:&nbsp;</B><%= FacilitiesManager.getFacilityDisplayLabel(FacilitiesManager.getFacilityForCode(currorder.getFacilityCode())) %></TD>
    <TD ALIGN=RIGHT><FORM METHOD=POST ACTION=editcustomer.jsp><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Edit Customer Info"></FORM></TD></TR>
<% } %>
</TABLE>
<TABLE border=0 cellpadding=0 cellspacing=3 width=100%>
<TR><TD COLSPAN=5><HR></TD></TR>
<TR><TD ALIGN=LEFT>
<% if (!currorder.completed)
{
%>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Units&nbsp;</B></TD><TD ALIGN=LEFT>
<% } else{%>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Requested-Backordered&nbsp;</B></TD><TD ALIGN=LEFT>
<% }%>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Reference&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</B></TD><TD ALIGN=LEFT width=100% >
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;(Available)&nbsp;Description&nbsp;</B></TD><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Each&nbsp;</B></TD><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Total&nbsp;</B></TD></TR>

<TR><TD COLSPAN=5><HR></TD></TR>
<%
	//Vector skus = currorder.skuList;
	//
	int backorderCount = 0;

	for(int i=0;i<skus.size();i++)
	{
		long boAmount = 0;
		LineItem item = (LineItem) skus.elementAt(i);
		boolean isBack = false;

			if(currorder.completed)
			{
				boAmount = item.quantity_backordered;
			}else{
				boAmount = item.quantity_request- OrderUtilities.getAvailableInventory(item.inventory_fkey, FacilitiesManager.getFacilityForCode(currorder.getFacilityCode()).getId());
			}
			if(boAmount > 0)
			{
				backorderCount++;isBack=true;
			}

%>
<TR><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= item.quantity_request+item.quantity_backordered %>
<%
if(currorder.completed)
{
%>
- <%= item.quantity_backordered %>
<% } %>
</B></TD><TD ALIGN=LEFT NOWRAP>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= item.client_part_no %></B></TD><TD ALIGN=LEFT NOWRAP>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;(<%= (isBack?"BACKORDER":"YES") %><%= (isBack?(" "+boAmount):"") %>)&nbsp;<%= item.description %></B></TD><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= dform.format(item.sku_price) %></B></TD><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= dform.format(item.total_price) %></B></TD></TR>
<% } %>
<% if (!currorder.completed)
{
%>
<TR><TD COLSPAN=5 ALIGN=RIGHT><FORM METHOD=POST ACTION=edititems.jsp><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Edit Items"></FORM></TD></TR>
<% } %>
<TR><TD COLSPAN=5><HR></TD></TR>

<TR>
<TD ALIGN=RIGHT colspan=4>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>Product&nbsp;Total:&nbsp;</B></TD>
<TD><fontx face="Geneva, Verdana, Helvetica" size=-2>
 <%= dform.format(currorder.total_product_cost) %></TD>
 </TR>
<TR><TD COLSPAN=2 VALIGN=CENTER>&nbsp;<%= coupMessage==null?"":coupMessage.length()>0?"<B><FONT COLOR=RED>"+coupMessage+"</FONT></B>":"" %>
</TD>
<TD ALIGN=RIGHT colspan=2><fontx face="Geneva, Verdana, Helvetica" size=-2><B>Coupon Discount<%= disctype %>:&nbsp;</B><% if(shippingDiscountApplies ){%><BR>(See ship options below for discount amount)</TD><TD><fontx face="Geneva, Verdana, Helvetica" size=-2>N/A</TD><%}else{%></TD><TD><fontx face="Geneva, Verdana, Helvetica" size=-2><%= dform.format(currorder.discount) %></TD><% }%>
</TR>
<TR><TD ALIGN=RIGHT colspan=4><fontx face="Geneva, Verdana, Helvetica" size=-2><B>Sales Tax:&nbsp;</B></TD>
<TD colspan=1><fontx face="Geneva, Verdana, Helvetica" size=-2><FORM METHOD=POST ACTION="orderentry.jsp"><%= (policy.allowSalesTaxEditing(currorder)?"<INPUT TYPE=TEXT SIZE=6 NAME=total_tax_cost VALUE=\""+currorder.total_tax_cost+"\">":dform.format(currorder.total_tax_cost))%></TD></TR>
<TR><TD ALIGN=RIGHT colspan=4><fontx face="Geneva, Verdana, Helvetica" size=-2><B>Shipping/Handling Charge:&nbsp;</B></TD>
<TD colspan=1><fontx face="Geneva, Verdana, Helvetica" size=-2>
<%= (allowShipPriceEditing?"<INPUT TYPE=TEXT SIZE=6 NAME=total_shipping_cost VALUE=\""+currorder.total_shipping_cost+"\">":dform.format(currorder.total_shipping_cost))%></TD></TR>
<TR><TD COLSPAN=5 align=right><% if (allowShipPriceEditing  || policy.allowSalesTaxEditing(currorder)) {
	%><input type=submit name=editaction value="Update"><%}%></TD></TR>
<TR><TD COLSPAN=5 align=right><% if (allowShipPriceEditing  && policy.allowShipPriceEditing(currorder)) {
	%><input type=hidden name="allowShipPriceEditing" VALUE="0"><input type=submit name=editaction value="Auto-calculate Ship Cost"><%}else
if(!allowShipPriceEditing && policy.allowShipPriceEditing(currorder)) {%><input type=hidden name="allowShipPriceEditing" VALUE="1"><input type=submit name=editaction value="Override Ship Cost">
<% } %></TD></TR>

<% if (allowShipPriceEditing  && policy.allowShipPriceEditing(currorder)) { %>
 <TR>
<TD ALIGN=RIGHT colspan=4>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>Current&nbsp;Order&nbsp;Total:&nbsp;</B></TD>
<TD><fontx face="Geneva, Verdana, Helvetica" size=-2>
 <%= dform.format(currorder.total_order_cost) %></TD>
 </TR>
 <% } %>

<%
	java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");

	if(currorder.skuList.size()>0 && currorder.getShippingAddress().address_one.length()>0)
	{
%>
	<TR><TD width=100% colspan=5><table border=0 width=100%><TR><TD ALIGN=LEFT >
	<fontx face="Geneva, Verdana, Helvetica" size=-1><B>Shipping&nbsp;Options</B>
	</TD><TD ALIGN=RIGHT width=100%>
	<fontx face="Geneva, Verdana, Helvetica" size=-2>(Order Total includes discounts and sales tax if they are being automatically included)
	</TD></TR> </table></td></tr>
<TR>
	<TD ALIGN=LEFT colspan=2 >
<FONTx  SIZE=-2><b>Method</b>
	</TD>
	<TD ALIGN=LEFT COLSPAN=2>
	<FONTx  SIZE=-2><b><%= allowShipPriceEditing?"Estimated Actual Ship Rate":"" %></b>
	</TD>
    <% if(!allowShipPriceEditing){%>
	<TD ALIGN=LEFT >
	<FONTx  SIZE=-2><b>Order&nbsp;Total</b>
	</TD>
    <% } else {%>  <TD ALIGN=LEFT ></TD> <% } %>
</TR>
<%

		try
		{

			shipOptions = policy.getShipOptions(currorder,0.00f);

            System.out.println("GOT SHIP OPTIONS: "+shipOptions.size());
		}catch(Exception ex)
		{ }


        if(shipOptions == null) shipOptions = new ArrayList();
		request.getSession(true).setAttribute("shipoptions",shipOptions);

		for (int i=0;i<shipOptions.size();i++)
		{

   %>

<%

            float oldDiscount = currorder.discount;
            
	if(((ShippingOption) shipOptions.get(i)).customerCost>=0)
	{
        try
        {
		currorder.discount = Coupon.getOrderDiscount(currorder.total_product_cost,((ShippingOption) shipOptions.get(i)).customerCost,currorder.coupon_code,currorder.clientID,currorder.skuList,currorder.getShippingAddress());
        }catch(Exception ex){}


%>
<TR>
<TD ALIGN=LEFT NOWRAP width=100% COLSPAN=2><INPUT TYPE=RADIO NAME=shipmethod VALUE="<%=  ((ShippingOption) shipOptions.get(i)).code %>" <%= ((ShippingOption) shipOptions.get(i)).code.equals( (String)request.getParameter("shipmethod"))?"CHECKED":"" %>>
<FONTx  SIZE=-2><B><%= ((ShippingOption) shipOptions.get(i)).name%></B><BR><%= ((ShippingOption) shipOptions.get(i)).desc%></TD>
<TD ALIGN=LEFT COLSPAN=2><!--<FONTx  SIZE=-2><%=formatter.format(((ShippingOption) shipOptions.get(i)).customerCost)%>--></TD>
    <% if(!allowShipPriceEditing){%>
<TD align=left><FONTx  SIZE=-2><b></b>&nbsp;<%= currorder.bill_cc_type.equalsIgnoreCase("FREE")?"$0.00":formatter.format(((((ShippingOption) shipOptions.get(i)).customerCost+currorder.total_product_cost)*(1.0000f+currorder.tax_pct))+currorder.discount) %></TD></TR>
<% }
	}else
	{
%>
<TR><TD ALIGN=LEFT COLSPAN=4><INPUT TYPE=RADIO NAME=shipmethod VALUE="<%=  ((ShippingOption) shipOptions.get(i)).code %>">
<FONTx  SIZE=-2><B><%= ((ShippingOption) shipOptions.get(i)).name%></B><BR><%= ((ShippingOption) shipOptions.get(i)).desc%>
</TD>
    <% if(!allowShipPriceEditing){%>
<TD align=left><FONTx  SIZE=-2>To&nbsp;be&nbsp;determined</TD></TR>
<% } else {%>
<TD align=left><FONTx  SIZE=-2></TD></TR><%          }
	}
	} %>

	
    <TR>
    	<TD ALIGN=LEFT NOWRAP width=100% COLSPAN=2><INPUT TYPE=RADIO NAME=shipmethod VALUE="LTL" >
    	<FONTX  SIZE=-2><B>LTL</B><BR></TD>
    	<TD ALIGN=LEFT COLSPAN=2><FONTx  SIZE=-2></TD>

    	<TD align=left><FONTx  SIZE=-2><b></b>TBD</TD>
    </TR>




	<%}  else {
        %><TR><TD ALIGN=LEFT COLSPAN=5><BR />
	<fontx face="Geneva, Verdana, Helvetica" size=-1>Shipping Options not available - please set the shipping address and add at least one SKU to see options.
	</TD></TR>
    <TR>
<TD ALIGN=RIGHT colspan=4>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>Current&nbsp;Order&nbsp;Total:&nbsp;</B></TD>
<TD><fontx face="Geneva, Verdana, Helvetica" size=-2>
 <%= dform.format(currorder.total_order_cost) %></TD>
 </TR>
        <%
    }
%>


</TABLE>
<TABLE border=0 cellpadding=0 cellspacing=0 width=100%>

<TR>
<TD COLSPAN=5 ALIGN=RIGHT>&nbsp;<BR><fontx face="Geneva, Verdana, Helvetica" size=-1 COLOR=red>
    <p><B><%= ((currorder.bill_cc_type.equals("FLEX") && currorder.total_product_cost>0.00f )?policy.getFlexPayStatement(currorder):"") %></B>
	<p style="color:red;"><b><%= errorMessage==null?"":errorMessage %></b><p>
	<p style="color:red;"><b><%= warning==null?"":warning %> </b><p>
<% if(backorderCount >0 && currorder.completed)
	{
%>
<BR>Backorder Created
<%
	}
%>
<% if (!currorder.completed)
{
%>
<P><B>PLEASE REPEAT ORDER INFORMATION TO CUSTOMER BEFORE SUBMITTING! </B></font>
<%
	if(backorderCount >0)
	{
		if (null==currorder.backorder_rule)
		{
			currorder.backorder_rule=OrderXMLDoc.kBackOrderAll;
		}
%>
<P><B>Backorder Type:&nbsp;</B><SELECT NAME="backorder_rule">
<OPTION VALUE="<%= OrderXMLDoc.kPartialShip %>" <%= (currorder.backorder_rule.equals(OrderXMLDoc.kPartialShip)?"SELECTED":"") %>>Ship In Stock items Now
<OPTION VALUE="<%= OrderXMLDoc.kBackOrderAll %>" <%= (currorder.backorder_rule.equals(OrderXMLDoc.kBackOrderAll)?"SELECTED":"") %>>Backorder Entire Order
</SELECT><P>
<%
	}

%>

<%
if(currorder.skuList.size()>0&&currorder.getShippingInfo().carr_service.length()>0)
{
%>
<BR><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="SUBMIT"></FORM>
<%
}
%>

<%
}
%><P><FORM METHOD=POST ACTION=orderentry.jsp><INPUT TYPE=SUBMIT NAME=editaction VALUE="Start New Order / Reset Order"></FORM></TD></TR>
</TABLE>

</FONT>

<!-- Begin Footer -->
<HR ALIGN="center" SIZE="5" NOSHADE/>
<fontx SIZE="1em">
    &nbsp;&nbsp;Copyright 2002-2018,
    <A HREF="http://www.owd.com/">
        One World Direct</A>. All Rights Reserved.&nbsp;&nbsp;

    </BODY>
</HTML>

<% }} %><%!

public  String ts(String str)
 {

 	if (str == null) return "";

 	return str.trim();
 }


%>


