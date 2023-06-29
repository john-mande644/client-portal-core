package com.owd.extranet.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



public class HomeManager extends ExtranetManager

{
private final static Logger log =  LogManager.getLogger();



	public static final String kParamHomeMgrAction = "hmgr";

	public static final String kParamHomeMgrSubAction = "sub";



	public static final String kParamHomeSummaryAction = "stat";

	public static final String kParamHomeProfileAction = "pro";

	public static final String kParamHomePrefsAction = "prf";

	public static final String kParamHomeReportsAction = "rpt";



	public String getManagerMenus(HttpServletRequest req)

	{

		StringBuffer sb = new StringBuffer();

		sb.append("<UL><LI><A HREF=\""+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+kParamHomeMgrAction+"="+kParamHomeSummaryAction+"\" >");

		sb.append("Account Status</A>");

		sb.append("<LI><A HREF=\""+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+kParamHomeMgrAction+"="+kParamHomeProfileAction+"\" >");

		sb.append("Edit Client Profile</A>");

		sb.append("<LI><A HREF=\""+ req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+kParamHomeMgrAction+"="+kParamHomePrefsAction+"\" >");

		sb.append("Extranet Prefs</A>");

		sb.append("<LI><A HREF=\""+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?"+kParamHomeMgrAction+"="+kParamHomeReportsAction+"\" >");

		sb.append("Edit Reports</A></UL>");

		return sb.toString();

	}



	

	public String getCurrentAction(HttpServletRequest req)

	{

		String currAction = ExtranetServlet.getStringParam(req,kParamHomeMgrAction,kParamHomeSummaryAction);

		if (currAction.equals(kParamHomeProfileAction))

		{

			return "Editing Client Profile";

		}else if (currAction.equals(kParamHomePrefsAction))

		{

			return "Editing Extranet Preferences";

		}else if(currAction.equals(kParamHomeReportsAction))

		{

		

			return "Editing Report Definitions";

		}

		return "Account Status";

		

	}



	

	public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException

	{



		PrintWriter out = resp.getWriter();

		out.write("<!-- content name -->");

		out.write("<FONTx SIZE=\"-1\"><B>"+getCurrentAction(req)+"</B></FONT>");

		out.write("</TD>");

		out.write("<TD NOWRAP WIDTH=\"100%\" ALIGN=RIGHT><FONTx SIZE=\"-1\">");

		out.write("");

		out.write("</FONT>");

		out.write("</TD>");

		out.write("</TR>");

		out.write("<TR><TD WIDTH=100% COLSPAN=2>");

		out.write("<!-- main content area -->");

		

		//do real work here



		String currAction = ExtranetServlet.getStringParam(req,kParamHomeMgrAction,kParamHomeSummaryAction);

		 if( currAction.equals(kParamHomeProfileAction))

		{

		out.write("<TABLE bgcolor=#00CC00 cellspacing=4><TR bgcolor=#00CC00><TD>");

		out.write("title");

		out.write("</TD></TR><TR><TD>");

		out.write("content");

		out.write("</TD></TR></TABLE");

	

		}else if( currAction.equals(kParamHomePrefsAction))

		{

			

			

		}else if( currAction.equals(kParamHomeReportsAction))

		{



		

		}else

		{ //summary

			

			//today's activities

			

			//status

			

			//recent history

			

			

		

		}

		

		

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
