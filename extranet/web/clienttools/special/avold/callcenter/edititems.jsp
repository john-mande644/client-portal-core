<html>
<%@ page import="
com.owd.core.business.order.Inventory,
com.owd.core.business.order.LineItem,
com.owd.core.business.order.Order,
com.owd.core.dbUtilities,
com.owd.extranet.servlet.ExtranetServlet,
com.owd.extranet.servlet.WebTable,
java.sql.Connection,
java.text.DecimalFormat"
%>
<%@ page import="java.text.NumberFormat"%><%@ page import="java.util.Locale"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.owd.core.business.order.OrderUtilities" %>
<%@ page import="com.owd.core.managers.FacilitiesManager" %>
<%

	Order currorder = null;
	WebTable table = null;
	try{
		currorder = (Order) request.getSession(true).getAttribute("currorder");
	}catch (Exception ex)
	{
		currorder = new Order("117");
		request.getSession(true).setAttribute("currorder",currorder);
	}
	if(null==currorder)
	{
		currorder = new Order("117");
		request.getSession(true).setAttribute("currorder",currorder);
	}else{
	
	}
	DecimalFormat dform = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
	Vector skus = currorder.skuList;
	if("update_items".equals((String)request.getParameter("editaction")))
	{
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
			}else{
				item.resetQuantity(newcount);
				
			}
		}
	}else if("find_items_word".equals((String)request.getParameter("editaction")))
	{
	
		String key = ts((String)request.getParameter("findItemWord"));
		
		String[] tableColumnNames = {"","Reference",
		"Available","Each",
		"Title",
		"Description"};
		String[] tableLinkStarts = {"editaction"+"="+"additem"+"&"+"itemref"+"=",
		"","",
		"","",""};
		String[] tableLinkEnds = {"Add</A>",
		"","",
		"","",""};
		int[] tableLinkMids = {1,
		0,0,
		0,0,0};
		String[] tableColumnDefs = {"inventory_num","inventory_num",
		"o.qty_on_hand","CONVERT(varchar,price,1)",
		"description",
		"long_desc"};
	
		String tableFromStmt = "from owd_inventory i left outer join owd_inventory_oh o on (inventory_id = inventory_fkey) ";
		String[] crits = {"(long_desc like \"%"+key+"%\" OR description like \"%"+key+"%\")", "client_fkey = 117", "is_active = 1"};
		
		table = new WebTable();
		
		table.setSQLDefs(tableColumnNames,tableColumnDefs,tableLinkStarts,tableLinkMids,tableLinkEnds,tableFromStmt);
		
		if(crits!=null)
		{
			for(int i=0;i<crits.length;i++)
			{
				table.addCriterium(crits[i]);
			}
		}
		table.setDescription("Items containing \""+key+"\" in description...");
			//check for find criteria
        System.out.println("Items containing \""+key+"\" in description...");
		table.setPageNum(ExtranetServlet.getIntegerParam(request,WebTable.kTableShowPage,1));
		table.setOrderCol(ExtranetServlet.getIntegerParam(request,WebTable.kTableSortColumn,-1));
		
		
		
		
	} else if ("find_items_sku".equals((String)request.getParameter("editaction")))
	{
	
			String key = ts((String)request.getParameter("findItemSKU"));
		
		String[] tableColumnNames = {"","Reference",
		"Available","Each",
		"Title",
		"Description"};
		String[] tableLinkStarts = {"editaction"+"="+"additem"+"&"+"itemref"+"=",
		"","",
		"","",""};
		String[] tableLinkEnds = {"Add</A>",
		"","",
		"","",""};
		int[] tableLinkMids = {1,
		0,0,
		0,0,0};
		String[] tableColumnDefs = {"inventory_num","inventory_num",
		"o.qty_on_hand","CONVERT(varchar,price,1)",
		"description",
		"long_desc"};
		String tableFromStmt = "from owd_inventory i left outer join owd_inventory_oh o on (inventory_id = inventory_fkey) ";
		String[] crits = {"inventory_num like \"%"+key+"%\"", "client_fkey = 117"};
		
		table = new WebTable();
		
		table.setSQLDefs(tableColumnNames,tableColumnDefs,tableLinkStarts,tableLinkMids,tableLinkEnds,tableFromStmt);
		
		if(crits!=null)
		{
			for(int i=0;i<crits.length;i++)
			{
				table.addCriterium(crits[i]);
			}
		}
		table.setDescription("Items containing \""+key+"\" in description...");
			//check for find criteria
			
		table.setPageNum(ExtranetServlet.getIntegerParam(request,WebTable.kTableShowPage,1));
		table.setOrderCol(ExtranetServlet.getIntegerParam(request,WebTable.kTableSortColumn,-1));
	} else if ("additem".equals((String)request.getParameter("editaction")))
	{
		System.out.println("adding item...");
		Inventory item = null;
		Connection cxn = null;
		try{
			cxn = com.owd.core.managers.ConnectionManager.getConnection();
			String partno = ExtranetServlet.getStringParam(request,"itemref","");
			item = Inventory.dbloadByPart(cxn,partno,"117");
			cxn.close();
			currorder.addLineItem(partno,1,item.price,item.price,item.description,item.item_color,item.item_size);
			skus = currorder.skuList;
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}finally{
			try{cxn.close();}catch(Exception ex){}
		}
		
	}
%>
<head>
<title>Call Center Order Creation - Editing Line Items</title>
</head>
<body bgcolor=#FFFFFF>
<CENTER><font face="Geneva, Verdana, Helvetica" size=-1><B>Call Center Order Creation - Editing Line Items</B></font></CENTER>
<HR>
Current Order Items
<FORM METHOD=POST ACTION=edititems.jsp?editaction=update_items>
<TABLE border=0 cellpadding=0 cellspacing=0 width=100%>
<TR><TD COLSPAN=5><HR></TD></TR>
<TR><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Units&nbsp;</B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Reference&nbsp;</B></TD><TD ALIGN=LEFT WIDTH=100%>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;(Available)&nbsp;Description&nbsp;</B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Each&nbsp;</B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;Total&nbsp;</B></TD></TR>

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
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<INPUT TYPE=TEXT SIZE=5 NAME=item_<%= i %>_count VALUE=<%= item.quantity_request %>></B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= item.client_part_no %></B></TD><TD ALIGN=LEFT WIDTH=100%>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;(<%= (OrderUtilities.getAvailableInventory(item.inventory_fkey, FacilitiesManager.getFacilityForCode(currorder.getFacilityCode()).getId())>=item.quantity_request?"YES":"BACKORDER") %>)&nbsp;<%= item.description %></B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= dform.format(item.sku_price) %></B></TD><TD ALIGN=LEFT>
<font face="Geneva, Verdana, Helvetica" size=-2><B>&nbsp;<%= dform.format(item.total_price) %></B></TD></TR>
<% } %>
<TR><TD COLSPAN=5 ALIGN=RIGHT>&nbsp;<BR><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE="Update Item Counts"></FORM></TD></TR>
<TR><TD COLSPAN=5><HR></TD></TR>
</TABLE>
<HR>

Find Items

<FORM METHOD=POST ACTION=edititems.jsp?editaction=find_items_word>By word in description:<INPUT TYPE=TEXT NAME=findItemWord><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE=Find...></FORM>
<FORM METHOD=POST ACTION=edititems.jsp?editaction=find_items_sku>By reference (SKU) number:<INPUT TYPE=TEXT NAME=findItemSKU><INPUT TYPE=SUBMIT NAME=SUBMIT VALUE=Find...></FORM>

<HR>
<%
	if (table != null)
	{
		%><%= table.getTable(request,response) %><%
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


