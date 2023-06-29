package com.owd.web.reports.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.web.reports.ReportDefinition;
import com.owd.web.reports.ReportsManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 3, 2004
 * Time: 9:14:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClientReportsServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    public void init(ServletConfig config)

            throws ServletException {

        super.init(config);


    }


    public void destroy() {

        super.destroy();


    }



    //GET requests not supported

    public void doGet(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

        doPost(req, resp);


    }



    //all requests should be POST

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

        try {
            String action = req.getParameter("action");
            if (action == null) action = "";

            if (action.equals("start")) {
                req.getRequestDispatcher("parameters.jsp").forward(req, resp);

            } else if (action.equals("run")) {
                try {
                    String user_client_id = Client.getClientIDForUser(req.getUserPrincipal().getName());
                    if (user_client_id == null) user_client_id = "-1";
                    if (user_client_id.equals("")) user_client_id = "-1";

                    log.debug("servlet run got cid:" + user_client_id + " for user " + req.getUserPrincipal().getName());

                    ReportDefinition rdef = ReportsManager.getReportDefForId(req.getParameter("id"), user_client_id);
                    rdef.returnReportDataToBrowser(req,new Integer(user_client_id).intValue());
                    req.getRequestDispatcher("/clienttools/reports/parameters.jsp").forward(req, resp);
                   // rdef.returnReportDataToBrowser(ReportDefinition.kReportDataCSVType, req, resp, new Integer(user_client_id).intValue());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    try {
                        req.setAttribute("errorNote", ex.getMessage());
                        req.getRequestDispatcher("/clienttools/reports/parameters.jsp").forward(req, resp);
                    } catch (Exception exc) {
                    }
                }
//  resp.sendRedirect("index.jsp");

            } else {
                resp.sendRedirect("/clienttools/reports/index.jsp");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                req.setAttribute("errorNote", ex.getMessage());
                req.getRequestDispatcher("/clienttools/reports/index.jsp").forward(req, resp);
            } catch (Exception exc) {
            }
        }


    }


    public String getServletInfo() {

        return "One World Client Reports Servlet";

    }





//public void


}
