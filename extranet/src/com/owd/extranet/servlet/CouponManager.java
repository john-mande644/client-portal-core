package com.owd.extranet.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Coupon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;





public class CouponManager extends ExtranetManager

{
private final static Logger log =  LogManager.getLogger();



	public static final String kParamCouponMgrAction = "couponmgr";

	public static final String kParamCouponMgrSubAction = "cpnmgrsub";

	

	public static final String kParamCouponExploreAction = "explore";

	

	public static final String kParamCouponCreateAction = "new";

	public static final String kParamCouponEditAction = "edit";

	public static final String kParamCouponFindAction = "find";

	public static final String kParamCouponSaveAction = "save";

	

	public static final String kParamCouponVoidAction = "cpnvoid";

	

	public static final String kParamCouponQuickFind = "qf";

	

	public static final String kparamCouponID = "oid";

	public static final String kCurrentFinder = "currCouponFinder";

	public static final String kCurrentCoupon = "currCouponStatus";





	public String getManagerMenus(HttpServletRequest req)

	{

		StringBuffer sb = new StringBuffer();

		sb.append("<UL><LI><A HREF=\""+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+kParamCouponMgrAction+"="+kParamCouponExploreAction+"\" >");

		sb.append("Explore Coupons");

		sb.append("<LI><A HREF=\""+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+kParamCouponMgrAction+"="+kParamCouponExploreAction+"\" >");

		sb.append("New Coupon</UL> ");

		

		return sb.toString();

	}



	

	public String getCurrentAction(HttpServletRequest req)

	{

		String currAction = ExtranetServlet.getStringParam(req,kParamCouponMgrAction,kParamCouponExploreAction);

		if (currAction.equals(kParamCouponExploreAction))

		{

			return "Searching Coupons...";

		}else if (currAction.equals(kParamCouponCreateAction))

		{

			return "Creating New Coupon";

		}else if(currAction.equals(kParamCouponEditAction))

		{

			return "Editing Coupon";

		}else if(currAction.equals(kParamCouponFindAction))

		{

			return "Coupons Search Results";

		}

		

		return "";

		

	}



	

	public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException

	{





		String currAction=ExtranetServlet.getStringParam(req,kParamCouponMgrAction,kParamCouponExploreAction);

		


		

		//do real work here



		////log.debug("cpnmgr action "+currAction);

		if(currAction.equals(kParamCouponExploreAction))

		{

			try

			{

				req.setAttribute(kCurrentFinder, CouponFinder.parseRequest(req,resp));

				

			}catch(Exception ex)

			{

				ex.printStackTrace();

				Mailer.postMailMessage("Explore Coupons error",ex.getMessage()+"\n"+ OWDUtilities.getStackTraceAsString(ex),"noop@owd.com","noop@owd.com");

				req.setAttribute("formerror",ex.getMessage());

				

				

			}

			try{req.getRequestDispatcher("findcoupon.jsp").include(req,resp);}catch(Exception ex2)

			{

				ex2.printStackTrace();

				Mailer.postMailMessage("Explore Coupons redirect error",ex2.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex2),"noop@owd.com","noop@owd.com");

			}

		

		}else if( currAction.equals(kParamCouponCreateAction))

		{

			try

			{

				req.setAttribute(kCurrentCoupon,new com.owd.core.business.order.Coupon(ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID)));



			}catch(Exception ex)

			{

				ex.printStackTrace();

				Mailer.postMailMessage("Create Coupons error",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"noop@owd.com","noop@owd.com");

				req.setAttribute("formerror",ex.getMessage());

				

				

			}

			try{req.getRequestDispatcher("editcoupon.jsp").include(req,resp);}catch(Exception ex2)

			{

				ex2.printStackTrace();

				Mailer.postMailMessage("Create Coupons redirect error",ex2.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex2),"noop@owd.com","noop@owd.com");

			}

		

		}else if( currAction.equals(kParamCouponEditAction))

		{

			try

			{

				req.setAttribute(kCurrentCoupon,

									Coupon.getCouponForID(ExtranetServlet.getIntegerParam(req,kparamCouponID,0)+"",

									ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID)));

								

				

			}catch(Exception ex)

			{

				ex.printStackTrace();

				Mailer.postMailMessage("Edit Coupons error",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"noop@owd.com","noop@owd.com");

				req.setAttribute("formerror",ex.getMessage());

				

				

			}

			try{req.getRequestDispatcher("editcoupon.jsp").include(req,resp);}catch(Exception ex2)

			{

				ex2.printStackTrace();

				Mailer.postMailMessage("Edit Coupons redirect error",ex2.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex2),"noop@owd.com","noop@owd.com");

			}




        }else if( currAction.equals(kParamCouponSaveAction))

		{



			Coupon item = null;

			Connection cxn = null;

		

			try

			{

				cxn = com.owd.core.managers.ConnectionManager.getConnection();

				////log.debug("in coupon save");

				if(ExtranetServlet.getStringParam(req,Coupon.kdbCouponPKName,"0").equals("0"))

        			{

        				//create new and save

        					item = new Coupon(ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID));

				}else

				{

					//update current and save

						item = com.owd.core.business.order.Coupon.getCouponForID(cxn, ExtranetServlet.getStringParam(req,Coupon.kdbCouponPKName,"0"),

							ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID));

				}

				req.setAttribute(kCurrentCoupon,item);

				////log.debug("got item");

				item.coupon_name = ExtranetServlet.getStringParam(req,Coupon.kdbCouponName,"");

				item.coupon_user = ExtranetServlet.getStringParam(req,Coupon.kdbCouponUser,"");

				item.coupon_type = new Integer(ExtranetServlet.getStringParam(req,Coupon.kdbCouponType,"0")).intValue();

				item.disc_amt = new Double(ExtranetServlet.getStringParam(req,Coupon.kdbCouponDiscAmt,"0.0000")).doubleValue();

				item.disc_pct = new Double(ExtranetServlet.getStringParam(req,Coupon.kdbCouponDiscPct,"0.0000")).doubleValue();

				item.ship_amt = new Double(ExtranetServlet.getStringParam(req,Coupon.kdbCouponShipAmt,"0.0000")).doubleValue();

				item.ship_pct = new Double(ExtranetServlet.getStringParam(req,Coupon.kdbCouponShipPct,"0.0000")).doubleValue();

				item.use_limit = new Integer(ExtranetServlet.getStringParam(req,Coupon.kdbCouponUseLimit,"0")).intValue();

				item.setExpiresDate(

					new Integer(ExtranetServlet.getStringParam(req,Coupon.kExpiresDayValue,"31")).intValue(),

					new Integer(ExtranetServlet.getStringParam(req,Coupon.kExpiresMonthValue,"12")).intValue(),

					new Integer(ExtranetServlet.getStringParam(req,Coupon.kExpiresYearValue,"2011")).intValue()

					);

				item.coupon_code = ExtranetServlet.getStringParam(req,Coupon.kdbCouponCode,"");

				item.sku_match_flag = new Integer(ExtranetServlet.getStringParam(req,Coupon.kdbCouponSKUMatch,"0")).intValue();
				
				item.sku_match_type = new Integer(ExtranetServlet.getStringParam(req,Coupon.kdbCouponSKUMatchType,"0")).intValue();
				
				item.code_match_type = new Integer(ExtranetServlet.getStringParam(req,Coupon.kdbCouponMatchType,"0")).intValue();
				
				item.sku_value = ExtranetServlet.getStringParam(req,Coupon.kdbCouponSKU,"");


				item.min_subtotal = new Float(ExtranetServlet.getStringParam(req,Coupon.kdbCouponMinSubtotal,"0.00")).floatValue();

				item.us_good = new Integer(ExtranetServlet.getStringParam(req,Coupon.kdbCouponUSGood,"0")).intValue();
				
				item.canada_good = new Integer(ExtranetServlet.getStringParam(req,Coupon.kdbCouponCanadaGood,"0")).intValue();
				
				item.intl_good = new Integer(ExtranetServlet.getStringParam(req,Coupon.kdbCouponIntlGood,"0")).intValue();
				

				item.dbupdate(cxn);

				////log.debug("saved");

				cxn.commit();

				////log.debug("committed");

				req.setAttribute("formerror","Coupon saved!");

   					

			}catch(Exception ex)

			{

				try{cxn.rollback();}catch(Exception e){}

				ex.printStackTrace();

				Mailer.postMailMessage("Save coupon error",ex.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex),"noop@owd.com","noop@owd.com");

				req.setAttribute("formerror","Error saving coupon: "+ex.getMessage());

				

			}finally

			{

				try{cxn.close();}catch(Exception e){}

			}

			

			try{req.getRequestDispatcher("editcoupon.jsp").include(req,resp);}catch(Exception ex2)

			{

				ex2.printStackTrace();

				Mailer.postMailMessage("Save Coupon redirect error",ex2.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex2),"noop@owd.com","noop@owd.com");

			}



		}else

		{

			req.setAttribute("formerror","Request not recognized");

			try{req.getRequestDispatcher("findcoupon.jsp").include(req,resp);}catch(Exception ex2)

			{

				ex2.printStackTrace();

			

				Mailer.postMailMessage("Coupons catch error",ex2.getMessage()+"\n"+OWDUtilities.getStackTraceAsString(ex2),"noop@owd.com","noop@owd.com");

			}

		}

		

				

		

	}



	public String[] getManagerActions(HttpServletRequest req)

	{

		String[] urls = {"",""};

		

		urls[0] = "<A HREF=\""+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+ExtranetServlet.kParamAdminAction+"="+ExtranetServlet.kParamChangeMgrAction +"&"+ ExtranetServlet.kParamChangeMgrTo+"="+ ExtranetManager.kCouponsMgrName+"&"+kParamCouponMgrAction+"="+kParamCouponExploreAction+"\" >Search</A>";

		urls[1] = "<A HREF=\""+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+ExtranetServlet.kParamAdminAction+"="+ExtranetServlet.kParamChangeMgrAction +"&"+ ExtranetServlet.kParamChangeMgrTo+"="+ ExtranetManager.kCouponsMgrName+"&"+kParamCouponMgrAction+"="+kParamCouponCreateAction+"\" >Create New Coupon</A>";

		

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

}
