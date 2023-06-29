package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.user.UserFactory;
import com.owd.hibernate.generated.OwdUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;


public class ReportsManager extends ExtranetManager {
private final static Logger log =  LogManager.getLogger();


    public static final String kParamReportMgrAction = "Reportmgr";


    public static final String kParamReportExploreAction = "explore";


    public static final String kParamReportDailyAction = "daily";

    public static final String kParamReportWeeklyAction = "weekly";

    public static final String kParamReportMonthlyAction = "monthly";


    public static final String kParamReportEditAction = "edit";

    public static final String kParamReportFindAction = "find";

    public static final String kParamReportSaveAction = "save";


    public static final String kParamReportQuickFind = "qf";


    public static final String kparamReportID = "oid";

    public static final String kCurrentFinder = "currReportFinder";

    public static final String kCurrentReport = "currReportStatus";


    public String getManagerMenus(HttpServletRequest req) {

        StringBuffer sb = new StringBuffer();

        sb.append("<UL><LI><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamReportMgrAction + "=" + kParamReportDailyAction + "\" >");

        sb.append("Daily Reports");

        sb.append("<LI><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamReportMgrAction + "=" + kParamReportWeeklyAction + "\" >");

        sb.append("Weekly Reports</UL> ");

        sb.append("<LI><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamReportMgrAction + "=" + kParamReportMonthlyAction + "\" >");

        sb.append("Monthly Reports</UL> ");

        return sb.toString();

    }


    public String getCurrentAction(HttpServletRequest req) {

        String currAction = ExtranetServlet.getStringParam(req, kParamReportMgrAction, kParamReportExploreAction);

        if (currAction.equals(kParamReportExploreAction)) {

            return "Please choose a report type at the left to view available reports";

        } else if (currAction.equals(kParamReportDailyAction)) {

            return "Daily Reports";

        } else if (currAction.equals(kParamReportWeeklyAction)) {

            return "Weekly Reports";

        } else if (currAction.equals(kParamReportMonthlyAction)) {

            return "Monthly Reports";

        }


        return "";


    }


    public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        try {
            req.getRequestDispatcher("extranettop.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String currAction = ExtranetServlet.getStringParam(req, kParamReportMgrAction, kParamReportExploreAction);



        /*

        if(currAction.equals(kParamReportExploreAction) | currAction.equals(kParamReportFindAction))

        {

            out.write("<FORM METHOD=POST ACTION="+ExtranetServlet.kExtranetURLPath+

                    "><INPUT TYPE=HIDDEN NAME=\""+kParamReportMgrAction+"\" VALUE=\""+

                    kParamReportFindAction+"\">");

            out.write("Find Receives:&nbsp;<INPUT TYPE=\"text\" NAME=\""+kParamReportQuickFind+"\" SIZE=\"30\"  onChange=\"this.form.submit()\">");

            out.write("<BR>");

            out.write("");

            out.write("<A HREF=\""+ExtranetServlet.kExtranetURLPath+"?"+kParamReportMgrAction+"="+kParamReportFindAdvancedAction+"\" >Advanced</A>&nbsp;");

            out.write("</FORM>");

        }

        */



        //do real work here



        req.setAttribute(kCurrentFinder, new ReportFinder());

        if (currAction.equals(kParamReportExploreAction)) {

            try {


                req.getRequestDispatcher("findReport.jsp").include(req, resp);

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (currAction.equals(kParamReportDailyAction)) {

            try {

                req.setAttribute(kCurrentFinder, ReportFinder.parseRequest(req, resp));

                req.getRequestDispatcher("findReport.jsp").include(req, resp);

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (currAction.equals(kParamReportWeeklyAction)) {

            try {

                req.setAttribute(kCurrentFinder, ReportFinder.parseRequest(req, resp));

                req.getRequestDispatcher("findReport.jsp").include(req, resp);

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (currAction.equals(kParamReportMonthlyAction)) {

            try {

                req.setAttribute(kCurrentFinder, ReportFinder.parseRequest(req, resp));

                req.getRequestDispatcher("findReport.jsp").include(req, resp);

            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (currAction.equals(kParamReportEditAction)) {

            Connection cxn = null;

            Report item = null;


            try {


                cxn = com.owd.core.managers.ConnectionManager.getConnection();


                item = Report.getReportForIDs(cxn, ExtranetServlet.getStringParam(req, Report.kdbReportPKName, "0"), ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));


                cxn.rollback();


            } catch (Exception ex) {

                ex.printStackTrace();

                try {
                    cxn.rollback();
                } catch (Exception e) {
                }

            } finally {

                try {
                    cxn.close();
                } catch (Exception ex) {
                }

            }

            try {


                req.setAttribute(kCurrentReport, item);


                req.getRequestDispatcher("editReport.jsp").include(req, resp);


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (currAction.equals(kParamReportSaveAction)) {

            Connection cxn = null;

            Report item = null;


            try {


                cxn = com.owd.core.managers.ConnectionManager.getConnection();


                item = Report.getReportForIDs(cxn, ExtranetServlet.getStringParam(req, Report.kdbReportPKName, "0"), ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

                item.is_active = ExtranetServlet.getIntegerParam(req, Report.kdbIsActive, 0);

                item.deliver_to = ExtranetServlet.getStringParam(req, Report.kdbDeliverTo, "");

                item.dbupdate(cxn);


                cxn.commit();


                resp.sendRedirect(resp.encodeRedirectURL(ExtranetServlet.kExtranetURLPath + "?" + kParamReportMgrAction + "=" + kParamReportExploreAction));


            } catch (Exception ex) {

                ex.printStackTrace();

                try {
                    cxn.rollback();
                } catch (Exception e) {
                }

            } finally {

                try {
                    cxn.close();
                } catch (Exception ex) {
                }

            }


        }


    }


    public String[] getManagerActions(HttpServletRequest req) {

        String[] urls = {"", "", ""};
        try {
            String user = req.getUserPrincipal().getName();
            OwdUser userObj = UserFactory.getOwdUserFromLogin(user);

            urls[0] = new StringBuffer().append("<A HREF=\"http://172.16.2.254/adhocreportbuilder/login.aspx?test=").append(OWDUtilities.getLogiXMLAuthString(userObj.getName(), userObj.getPassword(), "" + userObj.getClientFkey())).append("\" >Ad Hoc Reports</A>").toString();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return urls;

    }


    public void getManagerHeader(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        PrintWriter out = resp.getWriter();

        out.write("<TD VALIGN=\"TOP\">");

    }

    public void getManagerFooter(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        PrintWriter out = resp.getWriter();

        out.write("</TD></TR>");

        out.write("</TABLE>");

    }

}
