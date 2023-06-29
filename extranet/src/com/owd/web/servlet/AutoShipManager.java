package com.owd.web.servlet;


public class AutoShipManager extends ExtranetManager {


    public static final String kParamAutoShipMgrAction = "autoshipmgr";

    public static final String kParamAutoShipMgrSubAction = "autoshipmgrsub";


    public static final String kParamAutoShipExploreAction = "explore";


    public static final String kParamAutoShipCreateAction = "new";

    public static final String kParamAutoShipEditAction = "edit";

    public static final String kParamAutoShipFindAction = "find";

    public static final String kParamAutoShipSaveAction = "save";

    public static final String kParamAutoShipEditItemAction = "edititem";


    public static final String kParamAutoShipVoidAction = "autoshipvoid";


    public static final String kParamAutoShipQuickFind = "qf";


    public static final String kparamAutoShipID = "oid";

    public static final String kCurrentFinder = "currAutoShipFinder";

    public static final String kCurrentAutoShip = "currAutoShipStatus";


    public static final int kAddItem = 1;
    public static final int kDeleteItem = 2;
		

    /*
	public String getManagerMenus(HttpServletRequest req)

	{

		StringBuffer sb = new StringBuffer();

		sb.append("<UL><LI><A class=\"command\" HREF=\""+ExtranetServlet.kExtranetURLPath+"?"+kParamAutoShipMgrAction+"="+kParamAutoShipExploreAction+"\" >");

		sb.append("Explore Subscriptions");

		//sb.append("<LI><A HREF=\""+ExtranetServlet.kExtranetURLPath+"?"+kParamAutoShipMgrAction+"="+kParamAutoShipExploreAction+"\" >");

		//sb.append("New Subscription</UL> ");

		return sb.toString();

	}



	

	public String getCurrentAction(HttpServletRequest req)

	{

		String currAction = ExtranetServlet.getStringParam(req,kParamAutoShipMgrAction,kParamAutoShipExploreAction);

		if (currAction.equals(kParamAutoShipExploreAction))

		{

			return "Searching Subscriptions...";

		}else if (currAction.equals(kParamAutoShipCreateAction))

		{

			return "Creating New AutoShip Subscription";

		}else if(currAction.equals(kParamAutoShipEditAction))

		{

			return "Editing Subscription";

		}else if(currAction.equals(kParamAutoShipFindAction))

		{

			return "Subscription Search Results";

		}

		

		return "";

		

	}



	

	public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException

	{


		
		try
		{
		req.getRequestDispatcher("extranettop.jsp").include(req,resp);
		}catch(Exception ex){ex.printStackTrace();}
		

		//do real work here
		String currAction = ExtranetServlet.getStringParam(req,kParamAutoShipMgrAction,kParamAutoShipExploreAction);

		if(currAction.equals(kParamAutoShipExploreAction))

		{

			try

			{

				req.setAttribute(kCurrentFinder,AutoShipFinder.parseRequest(req,resp));

				

			}catch(Exception ex)

			{

				ex.printStackTrace();

				com.owd.core.Mailer.postMailMessage("Explore AutoShip error",ex.getMessage()+"\n"+com.owd.core.OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","owditadmin@owd.com");

				req.setAttribute("formerror",ex.getMessage());

				

				

			}

			try{req.getRequestDispatcher("findsubscription.jsp").include(req,resp);}catch(Exception ex2)

			{

				ex2.printStackTrace();

				com.owd.core.Mailer.postMailMessage("Explore Autoship redirect error",ex2.getMessage()+"\n"+com.owd.core.OWDUtilities.getStackTraceAsString(ex2),"owditadmin@owd.com","owditadmin@owd.com");

			}

		

		} else if( currAction.equals(kParamAutoShipEditAction))

		{
          /*
			try

			{
				OwdOrderAuto item = SubscriptionManager.getSubscriptionForSubscriptionID(ctxt,ExtranetServlet.getIntegerParam(req,kparamAutoShipID,0)+"",
									ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID));
				if(item == null)
				{
					//create new subscription to edit
					item = (OwdOrderAuto)ctxt.createAndRegisterNewObject("OwdOrderAuto");
				}
				req.getSession(true).setAttribute(kCurrentAutoShip,item);


			}catch(Exception ex)

			{

				ex.printStackTrace();

				com.owd.core.Mailer.postMailMessage("Edit Subscriptions error",ex.getMessage()+"\n"+com.owd.core.OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","owditadmin@owd.com");

				req.setAttribute("formerror",ex.getMessage());


			}

			try{req.getRequestDispatcher("editsubscription.jsp").include(req,resp);}catch(Exception ex2)

			{

				ex2.printStackTrace();

				com.owd.core.Mailer.postMailMessage("Edit Subscriptions redirect error",ex2.getMessage()+"\n"+com.owd.core.OWDUtilities.getStackTraceAsString(ex2),"owditadmin@owd.com","owditadmin@owd.com");

			}



		}else if( currAction.equals(kParamAutoShipEditItemAction))

		{

				OwdOrderAuto item = null;
				OwdOrderAutoItems line = null;

			try

			{

					//update current and save

				item = (OwdOrderAuto) req.getSession(true).getAttribute(kCurrentAutoShip);
				
				if(item == null)
				{
					//create new subscription to save
					throw new Exception("Could not edit autoship subscription item - the record could not be found");
				
				}
				
				int subaction = ExtranetServlet.getIntegerParam(req,"autoship_edit_item_action",0);
				switch(subaction)
				{
					case kAddItem:
						
					//	line = (OwdOrderAutoItems)ctxt.createAndRegisterNewObject("OwdOrderAutoItems");
						line.setSku(ExtranetServlet.getStringParam(req,"sku",""));
						line.setQuantity(new Integer(ExtranetServlet.getStringParam(req,"quantity","0")));
						line.setUnitPrice(new java.math.BigDecimal(ExtranetServlet.getStringParam(req,"unitprice","0.00")));
						line.setToOwdOrderAuto(item);
						break;
					case kDeleteItem:
						String delsku = ExtranetServlet.getStringParam(req,"delsku","0");
						List itemList = item.getOwdOrderAutoItemsArray();
						Iterator it = itemList.iterator();
						while (it.hasNext())
						{
							OwdOrderAutoItems testline = (OwdOrderAutoItems) it.next();
							if(testline.equals(delsku))
							{
								item.removeFromOwdOrderAutoItemsArray(testline);
								break;
							}
						}
						break;
				}


			}catch(Exception ex)

			{

				ex.printStackTrace();

				com.owd.core.Mailer.postMailMessage("Edit Subscription Item error",ex.getMessage()+"\n"+com.owd.core.OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","owditadmin@owd.com");

				req.setAttribute("formerror",ex.getMessage());


			}

			try{req.getRequestDispatcher("editsubscription.jsp").include(req,resp);}catch(Exception ex2)
			{

				ex2.printStackTrace();

				com.owd.core.Mailer.postMailMessage("Edit Subscription Item redirect error",ex2.getMessage()+"\n"+com.owd.core.OWDUtilities.getStackTraceAsString(ex2),"owditadmin@owd.com","owditadmin@owd.com");

			}

		

		}else if( currAction.equals(kParamAutoShipSaveAction))

		{
			OwdOrderAuto item = null;

			try

			{

					//update current and save

				item = (OwdOrderAuto) req.getSession(true).getAttribute(kCurrentAutoShip);
				
				if(item == null)
				{
					//create new subscription to save
					throw new Exception("Could not save autoship subscription - the record could not be found");
				
				}

				//update autoship record
				item.setShipInterval(new Integer(ExtranetServlet.getStringParam(req,"shipInterval","1")));
				item.setCalendarUnit(ExtranetServlet.getStringParam(req,"calendarUnit","M"));
				item.setRequirePayment(new Boolean(ExtranetServlet.getStringParam(req,"requirePayment","1")));
				item.setStatus(new Boolean(ExtranetServlet.getStringParam(req,"status","0")));
				item.setBillName(ExtranetServlet.getStringParam(req,"billname","M"));
				item.setBillAddressOne(ExtranetServlet.getStringParam(req,"billaddressone","M"));
				item.setBillAddressTwo(ExtranetServlet.getStringParam(req,"billaddresstwo","M"));
				item.setBillCity(ExtranetServlet.getStringParam(req,"billcity","M"));
				item.setBillState(ExtranetServlet.getStringParam(req,"billstate","M"));
				item.setBillZip(ExtranetServlet.getStringParam(req,"billzip","M"));
				item.setBillCountry(ExtranetServlet.getStringParam(req,"billcountry","M"));
				item.setShipName(ExtranetServlet.getStringParam(req,"shipname","M"));
				item.setShipAddressOne(ExtranetServlet.getStringParam(req,"shipaddressone","M"));
				item.setShipAddressTwo(ExtranetServlet.getStringParam(req,"shipaddresstwo","M"));
				item.setShipCity(ExtranetServlet.getStringParam(req,"shipcity","M"));
				item.setShipState(ExtranetServlet.getStringParam(req,"shipstate","M"));
				item.setShipZip(ExtranetServlet.getStringParam(req,"shipzip","M"));
				item.setShipCountry(ExtranetServlet.getStringParam(req,"shipcountry","M"));
				item.setShipMethod(ExtranetServlet.getStringParam(req,"shipmethod","M"));
				item.setBillPhone(ExtranetServlet.getStringParam(req,"billphone","M"));
				item.setShipPhone(ExtranetServlet.getStringParam(req,"shipphone","M"));
				item.setNextShipmentDate(new java.util.Date(ExtranetServlet.getStringParam(req,"nextshipment","1/1/1900")));
				item.setOrigOrderId(new Integer(ExtranetServlet.getStringParam(req,"origorderid","0")));
				item.setShipCost(new java.math.BigDecimal(ExtranetServlet.getStringParam(req,"shipcost","0.00")));
				item.setSalesTax(new java.math.BigDecimal(ExtranetServlet.getStringParam(req,"salestax","0.00")));
				item.setCcNum(ExtranetServlet.getStringParam(req,"ccnum","M"));
				item.setCcExpMon(new Integer(ExtranetServlet.getStringParam(req,"ccexpmon","0")));
				item.setCcExpYear(new Integer(ExtranetServlet.getStringParam(req,"ccexpyear","0")));
				item.setClientFkey(ExtranetServlet.getStringParam(req,"clientfkey","0"));
				
				
				//update line items
				//req.getParameter(
				
				
				//ctxt.commitChanges();

				req.setAttribute("formerror","Subscription saved!");

			}catch(Exception ex)

			{

				//try{ctxt.rollbackChanges();}catch(Exception e){}

				ex.printStackTrace();

				com.owd.core.Mailer.postMailMessage("Save subscription error",ex.getMessage()+"\n"+com.owd.core.OWDUtilities.getStackTraceAsString(ex),"owditadmin@owd.com","owditadmin@owd.com");

				req.setAttribute("formerror","Error saving subscription: "+ex.getMessage());

				

			}

			try{req.getRequestDispatcher("editsubscription.jsp").include(req,resp);}catch(Exception ex2)

			{

				ex2.printStackTrace();

				com.owd.core.Mailer.postMailMessage("Save Subscription redirect error",ex2.getMessage()+"\n"+com.owd.core.OWDUtilities.getStackTraceAsString(ex2),"owditadmin@owd.com","owditadmin@owd.com");

			}



		}else

		{

			req.setAttribute("formerror","Request not recognized");

			try{req.getRequestDispatcher("findsubscription.jsp").include(req,resp);}catch(Exception ex2)

			{

				ex2.printStackTrace();

			

				com.owd.core.Mailer.postMailMessage("Subscription catch error",ex2.getMessage()+"\n"+com.owd.core.OWDUtilities.getStackTraceAsString(ex2),"owditadmin@owd.com","owditadmin@owd.com");

			}

		}

		

				

		

	}



	public String[] getManagerActions(HttpServletRequest req)

	{

		String[] urls = {""};

		

		urls[0] = "<A class=\"command\" HREF=\""+ExtranetServlet.kExtranetURLPath+"?"+kParamAutoShipMgrAction+"="+kParamAutoShipExploreAction+"\" >Search</A>";

	//	urls[1] = "<A HREF=\""+ExtranetServlet.kExtranetURLPath+"?"+kParamCouponMgrAction+"="+kParamCouponCreateAction+"\" >Create New Coupon</A>";

		

		return urls;

	}

	



	public void getManagerHeader(HttpServletRequest req, HttpServletResponse resp)   throws IOException

	{

		

		PrintWriter out = resp.getWriter();	

		out.write("<TD VALIGN=\"TOP\">");

	}	

	public void getManagerFooter(HttpServletRequest req, HttpServletResponse resp)  throws IOException

	{

		

		PrintWriter out = resp.getWriter();	

		out.write("</TD></TR>");

		out.write("</TABLE>");

	}
*/

}
