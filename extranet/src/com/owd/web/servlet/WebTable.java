package com.owd.web.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.ConnectionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;


public class WebTable {
private final static Logger log =  LogManager.getLogger();

    public final static int kRenderString = 0;
    public final static int kRenderMoney = 1;

    String tableDelimiter = ",";

    String[] columns = null;
    String[] columnDefs = null;
    String[] linkstart = null;
    String[] linkend = null;
    int[] renderTypes = null;
    int[] linkmid = null;
    String fromStmt = null;
    Vector criterias = new Vector();
    String description = null;
    String groupBy = null;
    int orderCol = 1;
    String orderDir = "ASC";
    int pageNum = 1;
    int pageSize = 30;
    static java.text.DecimalFormat currencyFormatter = new java.text.DecimalFormat("$#,##0.00;($#,##0.00)");


    public WebTable() {

    }

    public WebTable(String delim) {

        super();
        tableDelimiter = delim;
    }


    public void setPageNum(int p) {
        pageNum = p;
    }

    public void setOrderCol(int p) {
        orderCol = p;
    }

    public void setSQLDefs(String[] cols, String[] colDefs, String[] links, int[] linkcols, String[] linkers, int[] renderers, String from) {
        columns = cols;
        columnDefs = colDefs;
        fromStmt = from;

        linkstart = links;
        linkmid = linkcols;
        linkend = linkers;
        renderTypes = renderers;

    }

    public void setSQLDefs(String[] cols, String[] colDefs, String[] links, int[] linkcols, String[] linkers, String from) {
        columns = cols;
        columnDefs = colDefs;
        fromStmt = from;

        linkstart = links;
        linkmid = linkcols;
        linkend = linkers;
        renderTypes = new int[columns.length];
        for (int i = 0; i < renderTypes.length; i++) {
            renderTypes[i] = kRenderString;
        }

    }

    public void addCriterium(String crit) {
        criterias.addElement(crit);
    }

    public void setOrderColumn(int index) {
        orderCol = index;
    }

    public void setDescription(String desc) {
        description = desc;
    }

    public void setGroupBy(String gpby) {
        groupBy = gpby;
    }

    public String getTable(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        StringBuffer sql = new StringBuffer();
        StringBuffer table = new StringBuffer();
        String emailMessage = null;

        StringBuffer params = new StringBuffer();
        int p = 0;
        log.debug("xx");
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
            p++;
            String name = (String) e.nextElement();

            if (name != null & (name.equals(kTableSortColumn) || name.equals(kTableShowPage) || name.equals("tdest") || kTableGotoPage.equals(name))) {
            } else {
                String[] vals = req.getParameterValues(name);
                for (int k = 0; k < vals.length; k++) {
                    params.append((p == 0 ? "" : "&") + URLEncoder.encode(name,"UTF-8") + "=" + URLEncoder.encode(vals[k],"UTF-8"));
                }
            }
        }
//////log.debug("xx1");
        StringBuffer message = null;
        String email = ExtranetServlet.getStringParam(req, "tdest", "x");
        if (!email.equals("x")) {
            if ((email.indexOf(".") > 0) && (email.indexOf("@") > 0))
                message = new StringBuffer();
            else
                emailMessage = "Email address not valid";
        }
		
//////log.debug("xx2 cols="+columns.length);
        sql.append("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            //////log.debug("yy"+" "+i);
            sql.append((i == 0 ? " " : ", ") + columnDefs[i] + " AS \"" + (columns[i].length() > 0 ? columns[i] : " ") + "\" ");
        }
//////log.debug("xx3");
        sql.append(" " + fromStmt);

        for (int i = 0; i < criterias.size(); i++) {
            sql.append((i == 0 ? " WHERE " : " AND ") + ((String) criterias.elementAt(i)) + " ");
        }
//////log.debug("xx4");
        if (groupBy != null && groupBy.length() > 0)
            sql.append(" GROUP BY " + groupBy + "	");
        sql.append(" ORDER BY " + columnDefs[((orderCol < 0) ? (-1 * orderCol) - 1 : orderCol - 1)] + ((orderCol < 0) ? " DESC" : " ASC"));
//////log.debug("xx5");
		
			
//////log.debug("xx cols="+columns.length);
        //table.append("<B>"+description+"</B><P>");
        table.append("<table border=0 cellspacing=0 cellpadding=2 width=100% name=results>");
        table.append("<TR>");
        for (int i = 0; i < columns.length; i++) {
//////log.debug("yy"+" "+i);
            table.append("<td bgcolor=\"#649CCC\"><fontx face=\"Geneva,Verdana,Helvetica\" color=\"#FFFFFF\" size=\"-2\"><b>");

            if (i > 0) table.append("<A HREF=\"" + req.getRequestURI() + "?" + params.toString() + (p > 0 ? "&" : "") + kTableShowPage + "=" + (pageNum) + "&" + kTableSortColumn + "=" + (i + 1) + "\"><fontx color=yellow><</fontx></A>");
            table.append(columns[i]);
            if (i > 0) table.append("<A HREF=\"" + req.getRequestURI() + "?" + params.toString() + (p > 0 ? "&" : "") + kTableShowPage + "=" + (pageNum) + "&" + kTableSortColumn + "=" + (-1 * (1 + i)) + "\"><fontx color=yellow>></fontx></A>");


            table.append("&nbsp;&nbsp;&nbsp;&nbsp;</b></TD>");
            //////log.debug("zz"+" "+i);
            if (message != null) {
                message.append((i == 0 ? "" : ",") + "\"" + columns[i] + "\"");
            }
        }
        if (message != null) {
            message.append("\r\n");
        }
        table.append("</TR>");
//	////log.debug("xx");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int lastCol = columns.length;
        String bg = "#E4ECF4";

        int rownum = 1;
        int pagecnt = 0;
        int maxpage = 1;
        try {


            conn = com.owd.core.managers.ConnectionManager.getConnection();
            ConnectionManager.setQuotedIdentifier(conn, false);
            stmt = conn.createStatement();

            log.debug("sql="+sql.toString());
            rs = stmt.executeQuery(sql.toString());
      		
//////log.debug("xx");
            while (rs.next()) {
                if ((rownum <= (pageNum * pageSize)) && (rownum > ((pageNum - 1) * pageSize))) {
                    if (bg.equals("#E4ECF4"))
                        bg = "#FFFFFF";
                    else
                        bg = "#E4ECF4";
                    table.append("<TR>");
                    for (int i = 1; i <= columns.length; i++) {//(i==lastCol?" WIDTH=100%":"")+

                        table.append("<td bgcolor=" + bg + " " + "><fontx face=\"Geneva,Verdana,Helvetica\" size=\"-2\" >"
                                + (linkmid[i - 1] != 0 ? "<A HREF=\"" + req.getRequestURI() + "?" + linkstart[i - 1]
                                + URLEncoder.encode(rs.getString(linkmid[i - 1]),"UTF-8") + "\">" : ""));

                        if (i != 1) {
                            switch (renderTypes[i - 1]) {
                                case kRenderMoney:
                                    try {
                                        table.append(currencyFormatter.format(rs.getFloat(i)));
                                    } catch (Exception ex) {
                                        //log.debug("can't format "+rs.getString(i));
                                        table.append(rs.getString(i));
                                    }
                                    break;
                                default:
                                    table.append(rs.getString(i));

                            }
                        }

                        table.append(linkend[i - 1] + "&nbsp;</TD>");
                    }
                    table.append("</TR>");

                }
                if (message != null) {
                    for (int i = 1; i <= columns.length; i++) {
                        message.append((i == 1 ? "" : tableDelimiter) + "\" " + rs.getString(i) + "\"");
                    }

                    message.append("\r\n");

                }
                rownum++;
                pagecnt++;
                if (pagecnt > pageSize) {
                    pagecnt = 1;
                    maxpage++;
                }
            }
            if (message != null) {
                try {
                    com.owd.core.Mailer.sendMailWithAttachment("One World search results", "Attached is a comma-delimited file containing the data you requested\r\n\r\n(" + description + ")", email, message.toString().getBytes(), "results.csv", "application/octet-stream");
                    emailMessage = "Data sent to " + email;
                } catch (Exception ex) {
                    emailMessage = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            table.append("<TD>Server error</TD>");
        } finally {

            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                conn.close();
            } catch (Exception e) {

            }

        }

        StringBuffer tablenav = new StringBuffer();
////log.debug("xx");
        table.append("</TABLE>");

        tablenav.append("");

        tablenav.append("<TABLE width=100%><TR><TD width=50% align=left valign=bottom><fontx face=\"Geneva,Verdana,Helvetica\" size=\"-2\" ><B>");
        if (pageNum > 1)
            tablenav.append("<B><A HREF=\"" + req.getRequestURI() + "?" + params.toString() + (p > 0 ? "&" : "") + kTableShowPage + "=" + (pageNum - 1) + "&" + kTableSortColumn + "=" + orderCol + "\">Previous&nbsp;Page</A></B>");

        tablenav.append("&nbsp;&nbsp;Page&nbsp;" + pageNum + "&nbsp;of&nbsp;" + maxpage + "&nbsp;&nbsp;");
        if (pageNum < maxpage)
            tablenav.append("<B><A HREF=\"" + req.getRequestURI() + "?" + params.toString() + (p > 0 ? "&" : "") + kTableShowPage + "=" + (pageNum + 1) + "&" + kTableSortColumn + "=" + orderCol + "\">Next&nbsp;Page</A></B>");
        tablenav.append("</TD><TD width=50%  align=right valign=bottom><fontx face=\"Geneva,Verdana,Helvetica\" size=\"-2\" ><B>");
        tablenav.append("<B><FORM METHOD=POST ACTION=\"" + req.getRequestURI() + "\">");

        for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {

            String name = (String) e.nextElement();

            if (name != null && (name.equals(kTableSortColumn) || name.equals(kTableShowPage) || name.equals("tdest") || kTableGotoPage.equals(name))) {
            } else {
                String[] vals = req.getParameterValues(name);
                for (int k = 0; k < vals.length; k++) {
                    tablenav.append("<INPUT TYPE=HIDDEN NAME=\"" + name + "\" VALUE=\"" + vals[k] + "\">");
                }
            }
        }

        tablenav.append("<INPUT TYPE=HIDDEN NAME=\"" + kTableShowPage + "\" VALUE=\"" + (pageNum >= 1 ? pageNum : (pageNum - 1)) + "\">");
        tablenav.append("<INPUT TYPE=HIDDEN NAME=\"" + kTableSortColumn + "\" VALUE=\"" + orderCol + "\">");
        tablenav.append("<B>Email&nbsp;Results&nbsp;To:&nbsp;<INPUT TYPE=TEXT NAME=tdest SIZE=15><INPUT TYPE=SUBMIT NAME=xxx VALUE=Send></TD></TR></TABLE>");


        //tablenav = new StringBuffer();

        table.append("<TABLE width=100%><TR><TD width=100% align=left valign=bottom><fontx face=\"Geneva,Verdana,Helvetica\" size=\"-2\" ><B>");
        if (pageNum > 1)
            table.append("<B><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + params.toString() + (p > 0 ? "&" : "") + kTableShowPage + "=" + (pageNum - 1) + "&" + kTableSortColumn + "=" + orderCol + "\">Previous&nbsp;Page</A></B>");

        table.append("&nbsp;&nbsp;Page&nbsp;" + pageNum + "&nbsp;of&nbsp;" + maxpage + "&nbsp;&nbsp;");
        if (pageNum < maxpage)
            table.append("<B><A HREF=\"" + req.getRequestURI() + "?" + params.toString() + (p > 0 ? "&" : "") + kTableShowPage + "=" + (pageNum + 1) + "&" + kTableSortColumn + "=" + orderCol + "\">Next&nbsp;Page</A></B>");
        table.append("&nbsp;&nbsp;<input type=\"submit\" name=\"searchorders\" value=\"Go to Page No.\"><input type=\"text\" value=\"\" name=\"" + kTableGotoPage + "\" size=\"5\">");

        if (rownum < 2) return "";

        return tablenav.toString() + table.toString() + "</TD></TR></FORM></TABLE>";
    }


    public static final String kTableShowPage = "tsp";
    public static final String kTableSortColumn = "tsc";

    public static final String kTableGotoPage = "goto";


}
