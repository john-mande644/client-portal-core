package com.owd.extranet.servlet;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ExtranetManager

{
private final static Logger log =  LogManager.getLogger();



	public static final String kInvMgrName = "Inventory";

	public static final String kHomeMgrName = "Home";


	public static final String kOrdersMgrName = "Orders";

	public static final String kUsersMgrName = "Users";


	public static final String kClientsMgrName = "Clients";

	public static final String kCouponsMgrName = "Coupons";

	public static final String kSuppliersMgrName = "Suppliers";


	

	public static final String[] kManagers = {kOrdersMgrName,kInvMgrName,kCouponsMgrName,kSuppliersMgrName};

	public static final String[] kAdminManagers = {};



	public static String getManagerSelector(HttpServletRequest req)

	{

		StringBuffer selector = new StringBuffer();

			selector.append("<INPUT TYPE=HIDDEN NAME=\""+com.owd.extranet.servlet.ExtranetServlet.kParamAdminAction+"\" VALUE=\""+ExtranetServlet.kParamChangeMgrAction+"\">");

			selector.append("<SELECT NAME=\""+ExtranetServlet.kParamChangeMgrTo+"\" onChange=\"this.form.submit()\">");

			String currManager = ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetCurrMgr);

			

			for(int i=0;i<kManagers.length;i++)

			{

				selector.append("<OPTION VALUE=\""+kManagers[i]+"\"");

				if (kManagers[i].equals(currManager))

					selector.append(" SELECTED>"+kManagers[i]);

				else

					selector.append(" >"+kManagers[i]);

			}

			

			if(ExtranetServlet.getSessionFlag(req,ExtranetServlet.kExtranetAdminFlag) && ExtranetServlet.getSessionFlag(req,ExtranetServlet.kExtranetInternalFlag))

			{

				for(int i=0;i<kAdminManagers.length;i++)

				{

					selector.append("<OPTION VALUE=\""+kAdminManagers[i]+"\"");

					if (kAdminManagers[i].equals(currManager))

						selector.append(" SELECTED>"+kAdminManagers[i]);

					else

						selector.append(" >"+kAdminManagers[i]);

				}

			}

			

			selector.append("</SELECT>");

			

			return selector.toString();

	}

	

	public String[] getManagerCreators(HttpServletRequest req)

	{

		String[] urls = {""};

		

		urls[0] = "<A HREF="+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?xx=fg>testing2</A>";

		

		return urls;

	}



	public String[] getManagerActions(HttpServletRequest req)

	{

		String[] urls = {""};

		

		urls[0] = "<A HREF="+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?xx=fg>testing2</A>";

		

		return urls;

	}



	public String[] getManagerQueries(HttpServletRequest req)

	{

		String[] urls = {""};

		

		urls[0] = "<A HREF="+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?xx=fg>testing2</A>";

		

		return urls;

	}



	public String[] getManagerReports(HttpServletRequest req)

	{

		String[] urls = {""};

		

		urls[0] = "<A HREF="+req.getContextPath()+ExtranetServlet.kExtranetURLPath+"?xx=fg>testing2</A>";

		

		return urls;	

	}

	

	public String getCurrentAction(HttpServletRequest req)

	{

		return "";

	}

		

	public void getContent(HttpServletRequest req, HttpServletResponse resp) throws IOException

	{

		//getManagerNavigator(req,resp);

		//getManagerHeader(req,resp);

		getManagerContent(req,resp);

		//getManagerFooter(req,resp);

	

	}

	

	public void download(HttpServletRequest req, HttpServletResponse resp) 

	{



			resp.setContentType("x-application/binary");

			

			try{

			

			

			

			}catch(Exception ex)

			{

			

			}finally{

			

			}

		

		

	

	}

	public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException

	{



		PrintWriter out = resp.getWriter();

		out.write("<!-- content name -->");

		out.write("<FONTx SIZE=\"-1\"><B>"+getCurrentAction(req)+"</B></FONT>");

		out.write("</TD>");

		out.write("<TD NOWRAP WIDTH=\"100%\" ALIGN=RIGHT><FONTx SIZE=\"-1\">");

		out.write("");

		out.write("<FORM ACTION=\"/changemanager.jsp\" METHOD=\"POST\">");

		out.write("Find Orders:&nbsp;<INPUT TYPE=\"text\" NAME=\"crit\" SIZE=\"30\">");

		out.write("<INPUT TYPE=\"SUBMIT\" NAME=\"act\" VALUE=\"Go\"><BR>");

		out.write("");

		out.write("<A HREF=\"/dd\" >Advanced</A>&nbsp;");

		out.write("</FORM>");

		out.write("</FONT>");

		out.write("</TD>");

		out.write("</TR>");

		out.write("<TR><TD WIDTH=100% COLSPAN=2>");

		out.write("<!-- main content area -->");

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
