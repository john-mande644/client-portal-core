package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExtranetManager {
private final static Logger log =  LogManager.getLogger();


    public static final String kInvMgrName = "Inventory";

    public static final String kHomeMgrName = "Home";

    public static final String kReceiveMgrName = "Receiving";

    public static final String kOrdersMgrName = "Orders";

    public static final String kUsersMgrName = "Users";

    public static final String kSchedulesMgrName = "Schedules";

    public static final String kClientsMgrName = "Clients";

    public static final String kReportsMgrName = "Reports";

    public static final String kCouponsMgrName = "Coupons";

    public static final String kSuppliersMgrName = "Suppliers";

    public static final String kServicesMgrName = "Services";

    public static final String kAutoShipMgrName = "AutoShip";


    public static final String[] kManagers = {kOrdersMgrName, kInvMgrName, kCouponsMgrName, kSuppliersMgrName};

    public static final String[] kAdminManagers = {kReceiveMgrName, kUsersMgrName, kClientsMgrName, kServicesMgrName, kReportsMgrName, kAutoShipMgrName};


    public static String getManagerSelector(HttpServletRequest req) {

        StringBuffer selector = new StringBuffer();

        selector.append("<INPUT TYPE=HIDDEN NAME=\"" + ExtranetServlet.kParamAdminAction + "\" VALUE=\"" + ExtranetServlet.kParamChangeMgrAction + "\">");

        selector.append("<SELECT NAME=\"" + ExtranetServlet.kParamChangeMgrTo + "\" onChange=\"this.form.submit()\">");

        String currManager = ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetCurrMgr);


        for (int i = 0; i < kManagers.length; i++) {

            selector.append("<OPTION VALUE=\"" + kManagers[i] + "\"");

            if (kManagers[i].equals(currManager))

                selector.append(" SELECTED>" + kManagers[i]);

            else

                selector.append(" >" + kManagers[i]);

        }


        if (ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetAdminFlag) && ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetInternalFlag)) {

            for (int i = 0; i < kAdminManagers.length; i++) {

                selector.append("<OPTION VALUE=\"" + kAdminManagers[i] + "\"");

                if (kAdminManagers[i].equals(currManager))

                    selector.append(" SELECTED>" + kAdminManagers[i]);

                else

                    selector.append(" >" + kAdminManagers[i]);

            }

        }


        selector.append("</SELECT>");


        return selector.toString();

    }

    public static List getManagerList(HttpServletRequest req) {

        List selector = new ArrayList();
        String currManager = ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetCurrMgr);


        for (int i = 0; i < kManagers.length; i++) {
            StringBuffer link = new StringBuffer();

            if (kManagers[i].equals(currManager))
                link.append("<td bgcolor=\"#3C5490\" nowrap><center>&nbsp;<A class=\"currmgr\" ");
            else
                link.append("<td bgcolor=\"#cfcfcf\" nowrap><center>&nbsp;<A class=\"newmgr\" ");

            link.append(" HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + ExtranetServlet.kParamAdminAction + "=" + ExtranetServlet.kParamChangeMgrAction +
                    "&" + ExtranetServlet.kParamChangeMgrTo + "=" + kManagers[i] + "\">" + kManagers[i] + "</A>&nbsp;</center>  </td>");
            selector.add(link.toString());
        }


        if (ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetAdminFlag) && ExtranetServlet.getSessionFlag(req, ExtranetServlet.kExtranetInternalFlag)) {

            for (int i = 0; i < kAdminManagers.length; i++) {

                StringBuffer link = new StringBuffer();

                if (kAdminManagers[i].equals(currManager))
                    link.append("<td bgcolor=\"#3C5490\" nowrap><center>&nbsp;<A class=\"currmgr\" ");
                else
                    link.append("<td bgcolor=\"#efefef\" nowrap><center>&nbsp;<A class=\"newmgr\" ");

                link.append(" HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + ExtranetServlet.kParamAdminAction + "=" + ExtranetServlet.kParamChangeMgrAction +
                        "&" + ExtranetServlet.kParamChangeMgrTo + "=" + kAdminManagers[i] + "\">" + kAdminManagers[i] + "</A>&nbsp;</center>  </td>");
                selector.add(link.toString());

            }

        }


        return selector;

    }


    public String[] getManagerCreators(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<A HREF=" + ExtranetServlet.kExtranetURLPath + "?xx=fg>testing2</A>";


        return urls;

    }


    public String[] getManagerActions(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<A HREF=" + ExtranetServlet.kExtranetURLPath + "?xx=fg>testing2</A>";


        return urls;

    }


    public String[] getManagerQueries(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<A HREF=" + ExtranetServlet.kExtranetURLPath + "?xx=fg>testing2</A>";


        return urls;

    }


    public String[] getManagerReports(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<A HREF=" + ExtranetServlet.kExtranetURLPath + "?xx=fg>testing2</A>";


        return urls;

    }

    public void download(HttpServletRequest req, HttpServletResponse resp) {


        resp.setContentType("x-application/binary");


        try {


        } catch (Exception ex) {


        } finally {


        }


    }

    public String getCurrentAction(HttpServletRequest req) {

        return "";

    }

    public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }

    public void getContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //getManagerNavigator(req,resp);

        //getManagerHeader(req,resp);

        getManagerContent(req, resp);

        //getManagerFooter(req,resp);


    }

}
