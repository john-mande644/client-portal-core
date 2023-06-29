<html>
<%@ page import="com.owd.core.OWDUtilities,
				 com.owd.core.business.Client,
				 com.owd.core.business.order.Inventory,
				 com.owd.core.business.order.LineItem,
				 com.owd.core.business.order.Order,
				 com.owd.core.dbUtilities,
				 com.owd.web.servlet.ExtranetServlet,
				 com.owd.web.servlet.WebTable,
				 java.text.DecimalFormat,
				 java.text.NumberFormat,
				 java.util.ArrayList,
				 java.util.List,
				 java.util.Locale,
				 java.util.Vector"
%>
<%@ page import="com.owd.core.business.order.OrderUtilities" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%
/*
	String pn;
	Map m = request.getParameterMap(); 

	if(m.containsKey("tsc")&&m.containsKey("itemref"))
	{
		System.out.println("tsc+itemref\n");
		try
		{
			request.getRequestDispatcher("edititems.jsp?"+URLEncoder.encode("itemref")+"="+URLEncoder.encode((String)m.get("tsp"))+"&"+URLEncoder.encode("tsc")+"="+URLEncoder.encode((String)m.get("tsc"))).forward(request, response);
		}
		catch(Exception e)
		{
			System.out.println("Redirect error: "+e);
			e.printStackTrace();
		}
	}
*/


	Order currorder = null;
	WebTable table = null;

	String client_id = (String)request.getSession(true).getAttribute("client_id");
	if(client_id == null || "".equals(client_id) || "0".equals(client_id))
	{
		try
		{
			request.getRequestDispatcher("orderentry.jsp").forward(request, response);
		}
		catch(Exception e)
		{

		}
	}
	Client client = (Client) request.getSession(true).getAttribute("client");
	currorder = (Order) request.getSession(true).getAttribute("currorder");
    com.owd.core.business.client.ClientPolicy policy = null;
      policy = client.getClientPolicy();


      if("1".equals(request.getSession(true).getAttribute("gomanual")))
      {
         policy.setManualEntryMode(true);

      }                                  else
      {
          policy.setManualEntryMode(false);
      }


	DecimalFormat dform = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
	Vector skus = currorder.skuList;
    List  crits = new ArrayList();
	crits.add("client_fkey = \""+client_id+"\"");
	crits.add("is_active=1");

			String[] tableColumnNames = {"","Reference",
		"Available","Each",
		"Title",
		"Description"};
		String[] tableLinkStarts = {"editactionitems"+"="+"additem"+"&"+"itemref"+"=",
		"","",
		"","",""};
		String[] tableLinkEnds = {"Add</A>",
		"","",
		"","",""};
		int[] tableLinkMids = {1,
		0,0,
		0,0,0};
		String[] tableColumnDefs = {"inventory_num","inventory_num",
		"ISNULL(case when is_auto_inventory=1 then 99999 else o.qty_on_hand end,0)","price",
		"description",
		"long_desc"};

	String tableFromStmt = "from owd_inventory i left outer join owd_inventory_oh o on (inventory_id = inventory_fkey)";

	int[] tableRenders = {WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderString,WebTable.kRenderMoney,WebTable.kRenderString, WebTable.kRenderString};

        table = new WebTable();
		table.setSQLDefs(tableColumnNames,tableColumnDefs,tableLinkStarts,tableLinkMids,tableLinkEnds,tableRenders, tableFromStmt);




			//check for find criteria

		table.setPageNum(ExtranetServlet.getIntegerParam(request,WebTable.kTableShowPage,1));
		table.setOrderCol(ExtranetServlet.getIntegerParam(request,WebTable.kTableSortColumn,2));

    String editactionitems = (String)request.getParameter("editactionitems");
    if (editactionitems==null) editactionitems="";


	if(editactionitems.indexOf("Update") >= 0)
	{

		if(policy.allowUnitPriceEditing(currorder))
		{
			for(int i=(skus.size()-1);i>= 0;i--)
		{
			float newprice = 0;

			LineItem item = (LineItem) skus.elementAt(i);

			try{
				newprice= new Float((String)request.getParameter("item_"+i+"_price")).floatValue();
			}catch(Exception ex){newprice=-1.00f;}

			if (newprice >= 0)
			{

				item.sku_price =  newprice;
				item.total_price = OWDUtilities.roundFloat(newprice*item.quantity_request);

			}


		}
		}
		for(int i=(skus.size()-1);i>= 0;i--)
		{
			int newcount = 0;

			LineItem item = (LineItem) skus.elementAt(i);

			try{
				newcount= new Integer((String)request.getParameter("item_"+i+"_count")).intValue();
			}catch(Exception ex){newcount=0;}

			if (newcount < 0) newcount = 0;

			if (newcount==0)
			{
				skus.removeElementAt(i);
			}else
			{
				item.resetQuantity(newcount);
			}


		}

		String key = "%";


	}else if ("find_items_word".equals((String)request.getParameter("editactionitems")))
	{
	
		String key = ts((String)request.getParameter("findItemWord"));


		crits = new ArrayList();
		crits.add("(long_desc like \"%"+key+"%\" or description like \"%"+key+"%\")");
		crits.add("client_fkey = \""+client_id+"\"");

			table.setDescription("Items containing \""+key+"\" in description...");


			
	} else if ("find_items_sku".equals((String)request.getParameter("editactionitems")))
	{
	
		String key = ts((String)request.getParameter("findItemSKU"));


		crits = new ArrayList();
		crits.add("inventory_num like \"%"+key+"%\"");
		crits.add("client_fkey = \""+client_id+"\"");


			table.setDescription("Items containing \""+key+"\" in SKU...");

	} else if ("additem".equals((String)request.getParameter("editactionitems")))
	{
		System.out.println("adding item...");
		String partno = ExtranetServlet.getStringParam(request,"itemref","");
		System.out.println("partno: "+partno+"\n");
		Inventory item = (Inventory)Inventory.dbloadByPart(partno,client_id);
		String invid = (new Long(item.inventory_id)).toString();

		if(currorder.hasItemID(invid))
		{
			System.out.println("Same SKU...\n");
			int index = currorder.findLineItemForSKU(partno);
			LineItem li = (LineItem)currorder.skuList.get(index);
			int curr_qty = (int)li.quantity_request+1;
			li.resetQuantity(curr_qty);
		}
		else
		{			
			if(currorder.bill_cc_type.equalsIgnoreCase("FREE"))
			{
				//item.price=0;
			}
            currorder.addLineItem(partno,1,item.price,item.price,item.description,item.item_color,item.item_size);
		}
	
		String key = "%";


	}
	else 	if(editactionitems.indexOf("Save") >= 0)

	{
		//System.out.println("current order discount: "+currorder.discount);
                if(policy.allowUnitPriceEditing(currorder))
		{
			for(int i=(skus.size()-1);i>= 0;i--)
		{
			float newprice = 0;

			LineItem item = (LineItem) skus.elementAt(i);

			try{
				newprice= new Float((String)request.getParameter("item_"+i+"_price")).floatValue();
			}catch(Exception ex){newprice=-1.00f;}

			if (newprice >= 0)
			{

				item.sku_price =  newprice;
				item.total_price = OWDUtilities.roundFloat(newprice*item.quantity_request);

			}


		}
		}
//modified Nov 27
        for(int i=(skus.size()-1);i>= 0;i--)
		{
			int newcount = 0;

			LineItem item = (LineItem) skus.elementAt(i);

			try{
				newcount= new Integer((String)request.getParameter("item_"+i+"_count")).intValue();
			}catch(Exception ex){newcount=0;}

			if (newcount < 0) newcount = 0;

			if (newcount==0)
			{
				skus.removeElementAt(i);
			}else
			{
				item.resetQuantity(newcount);
			}


		}
			  currorder.discount=0;
			  float pkg_weight = 0;
			  for(int i=0; i<currorder.skuList.size();i++)
			  {
				LineItem item = (LineItem) skus.elementAt(i);
				int newcount = (int)item.quantity_request;

				
		Inventory inv = Inventory.dbloadByPart(item.client_part_no,client_id);
			pkg_weight += Inventory.getWeight(item.inventory_fkey)*item.quantity_request;
	    	System.out.println("total weight: "+pkg_weight);
			}
			
		float box_wgt = 0.5f;
		
		pkg_weight += box_wgt;	
			
		   boolean is_domestic = currorder.getShippingInfo().getDestinationType()==1?true:false;


		System.out.println("edititems.jsp - current order discount before submission: "+currorder.discount+"\n");

		
		session.setAttribute("currorder", currorder);

                  policy.updateLineItemsAfterItemChange(currorder);
		try
		{
			request.getRequestDispatcher("orderentry.jsp").forward(request, response);
		}
		catch(Exception e)
		{
		
		}
	
	
	}
	else
	{
		String key = "%";


	}
    if(crits == null)
    {
        crits = new ArrayList();
    }
    crits.add("is_active=1");
	if(crits!=null)
				{
					for(int i=0;i<crits.size();i++)
					{
						table.addCriterium((String)crits.get(i));
					}
				}



                  policy.updateLineItemsAfterItemChange(currorder);
	skus = currorder.skuList;
%>
<head>
<title>Order Entry - Editing Line Items</title>
 <link REL="stylesheet" HREF="/webapps/owd.css" TYPE="text/css">
</head>
<body bgcolor=#FFFFFF>
<CENTER><fontx face="Geneva, Verdana, Helvetica" size=-1><B>Order Entry - Editing Line Items</B></font></CENTER>
<FORM METHOD=POST ACTION=edititems.jsp>
<TABLE border=0 cellpadding=0 cellspacing=0 width=100%>
<TR><TD COLSPAN=5><HR></TD></TR>
<TR><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Units&nbsp;</B></TD><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Reference&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;(Available)&nbsp;Description&nbsp;</B></TD><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Each&nbsp;</B></TD><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Total&nbsp;</B></TD></TR>

<TR><TD COLSPAN=5><HR></TD></TR>
<%
	//Vector skus = currorder.skuList;
	//
	//
	for(int i=0;i<skus.size();i++)
	{
		LineItem item = (LineItem) skus.elementAt(i);
%>
<TR><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<INPUT TYPE=TEXT SIZE=5 NAME=item_<%= i %>_count VALUE=<%= item.quantity_request %>></B></TD><TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= item.client_part_no %></B></TD>
<TD ALIGN=LEFT WIDTH=100%>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;(<%= (OrderUtilities.getAvailableInventory(item.inventory_fkey, FacilitiesManager.getFacilityForCode(currorder.getFacilityCode()).getId())>=item.quantity_request?"YES":"BACKORDER") %>)&nbsp;<%= item.description %></B></TD>
<TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= (policy.allowUnitPriceEditing(currorder)?"<INPUT TYPE=TEXT SIZE=5 NAME=item_"+i+"_price VALUE=\""+item.sku_price+"\">":dform.format(item.sku_price))%></B></TD>
<TD ALIGN=LEFT>
<fontx face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= dform.format(item.total_price) %></B></TD>
</TR>
<% } %>
<TR><TD COLSPAN=5><HR></TD></TR>
<TR><TD COLSPAN=3 ALIGN=LEFT><INPUT TYPE=SUBMIT NAME=editactionitems VALUE="Update Item Counts"></TD>
<TD COLSPAN=2 ALIGN=RIGHT><INPUT TYPE=SUBMIT NAME=editactionitems VALUE="Save Items and Continue"></TD></TR>
                                                                               </FORM>
</TABLE>
<HR><TABLE BORDER=0 width=100%><TR><TD ALIGN=LEFT VALIGN=TOP NOWRAP>
<FORM METHOD=POST ACTION=edititems.jsp?editactionitems=find_items_word>By word in description:<INPUT TYPE=TEXT NAME=findItemWord><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE=Find...></FORM>
</TD><TD ALIGN=RIGHT VALIGN=TOP NOWRAP><FORM METHOD=POST ACTION=edititems.jsp?editactionitems=find_items_sku>By reference (SKU) number:<INPUT TYPE=TEXT NAME=findItemSKU><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE=Find...></FORM>
</TD></TR></TABLE>
<%
	if (table != null)
	{
%>
		<%=table.getTable(request,response)%>
<%
	}
%>
<HR>


</body>
</html>
<%!

public  String ts(String str)
 {
 
 	if (str == null) return "";
 	
 	return str.trim();
 }
 
	public String getHTMLSelect(String[] values, String[] names, String defaultValue, String selectName)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<SELECT NAME=\""+selectName+"\">");
		
		for (int i=0;i<values.length;i++)
		{
			String value = values[i];
			if (value.equals(defaultValue))
			{
				sb.append("<OPTION VALUE=\""+value+"\" SELECTED>"+names[i]);
			}else
			{
				sb.append("<OPTION VALUE=\""+value+"\">"+names[i]);
			}
		}
		
		sb.append("</SELECT>");
		return sb.toString();
	
	}
	
				


%>


