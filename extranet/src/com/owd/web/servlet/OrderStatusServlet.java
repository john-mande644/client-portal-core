package com.owd.web.servlet;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class OrderStatusServlet extends HttpServlet {
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


    }



    //all requests should be POST

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {


    }


    public String getServletInfo() {

        return "One World Order Status API";

    }





    //public void


}
