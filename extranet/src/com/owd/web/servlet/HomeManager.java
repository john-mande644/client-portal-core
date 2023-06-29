package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class HomeManager extends ExtranetManager {
private final static Logger log =  LogManager.getLogger();


    public static final String kParamHomeMgrAction = "hmgr";

    public static final String kParamHomeMgrSubAction = "sub";


    public static final String kParamHomeSummaryAction = "stat";

    public static final String kParamHomeProfileAction = "pro";

    public static final String kParamHomePrefsAction = "prf";

    public static final String kParamHomeReportsAction = "rpt";


    public String getManagerMenus(HttpServletRequest req) {

        StringBuffer sb = new StringBuffer();

        sb.append("<UL><LI><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamHomeMgrAction + "=" + kParamHomeSummaryAction + "\" >");

        sb.append("Account Status</A>");

        sb.append("<LI><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamHomeMgrAction + "=" + kParamHomeProfileAction + "\" >");

        sb.append("Edit Client Profile</A>");

        sb.append("<LI><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamHomeMgrAction + "=" + kParamHomePrefsAction + "\" >");

        sb.append("Extranet Prefs</A>");

        sb.append("<LI><A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamHomeMgrAction + "=" + kParamHomeReportsAction + "\" >");

        sb.append("Edit Reports</A></UL>");

        return sb.toString();

    }


    public String getCurrentAction(HttpServletRequest req) {

        String currAction = ExtranetServlet.getStringParam(req, kParamHomeMgrAction, kParamHomeSummaryAction);

        if (currAction.equals(kParamHomeProfileAction)) {

            return "Editing Client Profile";

        } else if (currAction.equals(kParamHomePrefsAction)) {

            return "Editing Extranet Preferences";

        } else if (currAction.equals(kParamHomeReportsAction)) {


            return "Editing Report Definitions";

        }

        return "Account Status";


    }


    public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            req.getRequestDispatcher("extranettop.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //do real work here



        String currAction = ExtranetServlet.getStringParam(req, kParamHomeMgrAction, kParamHomeSummaryAction);

        if (currAction.equals(kParamHomeProfileAction)) {


        } else if (currAction.equals(kParamHomePrefsAction)) {


        } else if (currAction.equals(kParamHomeReportsAction)) {


        } else { //summary



            //today's activities



            //status



            //recent history


        }


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
