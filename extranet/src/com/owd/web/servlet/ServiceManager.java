package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;


public class ServiceManager extends ExtranetManager {
private final static Logger log =  LogManager.getLogger();


    public static final String kParamSvcMgrAction = "svcmgr";

    public static final String kParamSvcMgrSubAction = "sub";

    public static final String kParamSvcQuery = "qryname";

    public static final String kParamSvcExploreAction = "explore";

    public static final String kParamSvcCreateAction = "new";

    public static final String kParamSvcEditAction = "edit";

    public static final String kParamSvcFindAction = "find";

    public static final String kParamSvcSaveAction = "save";

    public static final String kParamSvcDeleteAction = "delete";

    public static final String kParamSvcFindAdvancedAction = "findadv";


    public static final String kParamSvcQuickFind = "qf";

    public static final String kparamSvcID = "svcid";


    public static final String kCurrentFinder = "currSvcFinder";

    public static final String kCurrentService = "currSvcStatus";


    public String[] getManagerCreators(HttpServletRequest req) {

        String[] urls = {""};


        return urls;

    }


    public String[] getManagerActions(HttpServletRequest req) {

        String[] urls = {"", ""};


        urls[0] = "<A class=\"command\" HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamSvcMgrAction + "=" + kParamSvcExploreAction + "\" >Search</A>";

        urls[1] = "<A class=\"command\" HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamSvcMgrAction + "=" + kParamSvcCreateAction + "\" >Add New Service</A>";


        return urls;

    }


    public String[] getManagerQueries(HttpServletRequest req) {

        String[] urls = {""};


        return urls;

    }


    public String[] getManagerReports(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<B>N/A</B>";


        return urls;

    }


    public String getCurrentAction(HttpServletRequest req) {

        String currAction = ExtranetServlet.getStringParam(req, kParamSvcMgrAction, kParamSvcExploreAction);

        if (currAction.equals(kParamSvcExploreAction)) {

            return "Searching Billed Services";

        } else if (currAction.equals(kParamSvcCreateAction)) {

            return "Creating Billed Service";

        } else if (currAction.equals(kParamSvcEditAction)) {


            return "Editing Billed Service";

        } else if (currAction.equals(kParamSvcFindAction)) {

            return "Billed Service Search Results";

        }


        return "";


    }


    public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        try {
            req.getRequestDispatcher("extranettop.jsp").include(req, resp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String currAction = ExtranetServlet.getStringParam(req, kParamSvcMgrAction, kParamSvcExploreAction);





        //do real work here



        if (currAction.equals(kParamSvcExploreAction)) {

            try {

                req.setAttribute(kCurrentFinder, ServiceFinder.parseRequest(req, resp));


            } catch (Exception ex) {

                ex.printStackTrace();

                com.owd.core.Mailer.postMailMessage("Explore Services error", ex.getMessage() + "\n" + com.owd.core.OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "owditadmin@owd.com");

                req.setAttribute("formerror", ex.getMessage());


            }

            try {
                req.getRequestDispatcher("findservice.jsp").include(req, resp);
            } catch (Exception ex2) {

                ex2.printStackTrace();

                com.owd.core.Mailer.postMailMessage("Explore Services redirect error", ex2.getMessage() + "\n" + com.owd.core.OWDUtilities.getStackTraceAsString(ex2), "owditadmin@owd.com", "owditadmin@owd.com");

            }


        } else if (currAction.equals(kParamSvcCreateAction)) {

            try {

                req.setAttribute(kCurrentService, new com.owd.core.business.Service());


            } catch (Exception ex) {

                ex.printStackTrace();

                com.owd.core.Mailer.postMailMessage("Create Service error", ex.getMessage() + "\n" + com.owd.core.OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "owditadmin@owd.com");

                req.setAttribute("formerror", ex.getMessage());


            }

            try {
                req.getRequestDispatcher("editservice.jsp").include(req, resp);
            } catch (Exception ex2) {

                ex2.printStackTrace();

                com.owd.core.Mailer.postMailMessage("Create Service redirect error", ex2.getMessage() + "\n" + com.owd.core.OWDUtilities.getStackTraceAsString(ex2), "owditadmin@owd.com", "owditadmin@owd.com");

            }


        } else if (currAction.equals(kParamSvcEditAction)) {

            try {

                req.setAttribute(kCurrentService,

                        Service.getServiceForID(ExtranetServlet.getIntegerParam(req, kparamSvcID, 0)));


            } catch (Exception ex) {

                ex.printStackTrace();

                com.owd.core.Mailer.postMailMessage("Edit Service error", ex.getMessage() + "\n" + com.owd.core.OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "owditadmin@owd.com");

                req.setAttribute("formerror", ex.getMessage());


            }

            try {
                req.getRequestDispatcher("editservice.jsp").include(req, resp);
            } catch (Exception ex2) {

                ex2.printStackTrace();

                com.owd.core.Mailer.postMailMessage("Edit Service redirect error", ex2.getMessage() + "\n" + com.owd.core.OWDUtilities.getStackTraceAsString(ex2), "owditadmin@owd.com", "owditadmin@owd.com");

            }


        } else if (currAction.equals(kParamSvcSaveAction)) {


            Service item = null;

            Connection cxn = null;


            try {

                cxn = com.owd.core.managers.ConnectionManager.getConnection();

                ////log.debug("in coupon save");

                if (ExtranetServlet.getStringParam(req, Service.kdbServicePKName, "0").equals("0")) {

                    //create new and save

                    item = new Service();

                } else {

                    //update current and save

                    item = com.owd.core.business.Service.getServiceForID(cxn, ExtranetServlet.getStringParam(req, Service.kdbServicePKName, "0"));

                }

                req.setAttribute(kCurrentService, item);

                ////log.debug("got item");

                item.service_name = ExtranetServlet.getStringParam(req, Service.kdbServiceName, "");

                item.description = ExtranetServlet.getStringParam(req, Service.kdbServiceDesc, "");

                item.service_charge = new Float(ExtranetServlet.getStringParam(req, Service.kdbServiceCharge, "0.00")).floatValue();

                item.is_timed = new Integer(ExtranetServlet.getStringParam(req, Service.kdbServiceIsTimed, "0")).intValue();

                if (item.is_timed == 0) {

                    item.is_timed = 0;

                    item.time_unit = 0;

                }

                if (item.is_timed == 1) {

                    item.is_timed = 1;

                    item.time_unit = 0;

                }

                if (item.is_timed == 2) {

                    item.is_timed = 1;

                    item.time_unit = 1;

                }


                item.dbupdate(cxn);

                ////log.debug("saved");

                cxn.commit();

                ////log.debug("committed");

                req.setAttribute("formerror", "Service saved!");


            } catch (Exception ex) {

                try {
                    cxn.rollback();
                } catch (Exception e) {
                }

                ex.printStackTrace();

                com.owd.core.Mailer.postMailMessage("Save service error", ex.getMessage() + "\n" + com.owd.core.OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "owditadmin@owd.com");

                req.setAttribute("formerror", "Error saving service: " + ex.getMessage());


            } finally {

                try {
                    cxn.close();
                } catch (Exception e) {
                }

            }


            try {
                req.getRequestDispatcher("editservice.jsp").include(req, resp);
            } catch (Exception ex2) {

                ex2.printStackTrace();

                com.owd.core.Mailer.postMailMessage("Save Service redirect error", ex2.getMessage() + "\n" + com.owd.core.OWDUtilities.getStackTraceAsString(ex2), "owditadmin@owd.com", "owditadmin@owd.com");

            }


        } else {

            req.setAttribute("formerror", "Request not recognized");

            try {
                req.getRequestDispatcher("findservice.jsp").include(req, resp);
            } catch (Exception ex2) {

                ex2.printStackTrace();


                com.owd.core.Mailer.postMailMessage("Services catch error", ex2.getMessage() + "\n" + com.owd.core.OWDUtilities.getStackTraceAsString(ex2), "owditadmin@owd.com", "owditadmin@owd.com");

            }

        }


    }


    public void showTable(HttpServletRequest req, HttpServletResponse resp, String[] criteria, String description) throws IOException {


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
