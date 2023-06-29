package com.owd.core.business;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ClientWeb {
private final static Logger log =  LogManager.getLogger();


    public static final String kClient_ClientID = "cid";

    public static final String kClientAction = "ca";


    public static void getHeader(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        PrintWriter out = resp.getWriter();

        out.write("<head> ");

        out.write("<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\"> ");


        out.write("<title>One World Direct - Virtual Warehouse</title>");

        out.write("</head>");


        out.write("<body bgcolor=\"#ffffff\">");


        out.write("<table width=\"100%\" border=0 cellspacing=0 cellpadding=2 bgcolor=\"#ffffff\">");


        out.write("<TR>");

        out.write("<TD align=left ><FONT face=\"Arial, Helvetica\" size=\"+2\"><B>One World Virtual Warehouse</B></FONT></TD>");

        out.write("<td height=25 align=right><font face=\"Geneva, Verdana, Helvetica\" size=\"-2\" color=\"#000000\">");

        out.write(" &nbsp;&nbsp; <span style=\"color:#000000\">");


        out.write("<A HREF=\"client_home.jsp\"><font color=\"#000000\"><B>Home</B></FONT></A>");

        out.write(" | ");

        out.write("<A HREF=\"orders.jsp\"><font color=\"#000000\"><B>Orders</B></FONT></A>");

        out.write(" | ");

        out.write("<A HREF=\"inventory.jsp\"><font color=\"#000000\"><B>Inventory</B></FONT></A>");

        out.write(" | ");

        out.write("<A HREF=\"receiving.jsp\"><font color=\"#000000\"><B>Receiving</B></FONT></A>");

        out.write(" | ");

        out.write("<A HREF=\"returns.jsp\"><font color=\"#000000\"><B>Returns</B></FONT></A>");

        out.write(" | ");

        out.write("<A HREF=\"reports.jsp\"><font color=\"#000000\"><B>Reporting</B></FONT></A>");

        out.write(" | ");

        out.write("<A HREF=\"login.jsp\"><font color=\"#000000\"><B>Login</B></FONT></A>");

        out.write("</TD>");


        out.write("</TR>");

        out.write("<TR><TD colspan=2>");

        out.write("<TABLE  width=\"100%\" cellpadding=\"1\" cellspacing=\"0\" border=\"0\" bgcolor=\"#000000\">");

        out.write("<TR>");

        out.write("<TD bgcolor=\"#CCCCFF\" align=left>");

        out.write("<FONT face=\"Arial, Helvetica\" size=\"+1\"><B><%= currentManager %></B>&nbsp;</FONT></TD>");

        out.write("<TD bgcolor=\"#CCCCFF\" align=right>");

        out.write("<FONT face=\"Arial, Helvetica\" size=\"+1\"><B><%= clientName %></B>&nbsp;</FONT></TD>");

        out.write("</TD></TR></TABLE>");

        out.write("</TR>");

        out.write("</TABLE>");


    }


    public static void getFooter(HttpServletRequest req, HttpServletResponse resp) {


    }


    public static void showClient(HttpServletRequest req, HttpServletResponse resp, Client client) throws IOException {

        PrintWriter out = resp.getWriter();

        out.write(client.company_name);

    }


}
