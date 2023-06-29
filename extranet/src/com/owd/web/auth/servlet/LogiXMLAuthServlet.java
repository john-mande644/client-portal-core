package com.owd.web.auth.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.web.auth.LogiXMLAuthUtilities;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 24, 2003
 * Time: 10:07:17 AM
 * To change this template use Options | File Templates.
 */
public class LogiXMLAuthServlet extends HttpServlet {
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
            String data = req.getParameter("logindata");
            log.debug("In LogiXML Auth Servlet");
            if (data == null) data = "";
            log.debug("In LogiXML Auth Servlet: data=" + data);
            String response = LogiXMLAuthUtilities.getLogiXMLResponseFromLogiXMLAuthString(data);
            resp.getWriter().print(response);
            resp.getWriter().flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


}
