package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


public class FormMailServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    /**
     * Responds to the "POST" query from  the original form supplied by the goGet()
     * <p/>
     * method.
     */

    public void doPost(HttpServletRequest req, HttpServletResponse res)

            throws IOException, ServletException {

        StringBuffer sb = new StringBuffer();

        sb.append("\nSubmitted Information follows:\n\n");

        String ParamString;

        Enumeration ParamNames = req.getParameterNames();

        while (ParamNames.hasMoreElements()) {

            ParamString = (String) ParamNames.nextElement();

            if (!ParamString.equals("SUBMIT")) {

                sb.append(ParamString + " : " + req.getParameterValues(ParamString)[0] + "\n");


            }

        }

        //com.owd.core.Mailer.postMailMessage("Fulfillment Pricing Request", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");
        // com.owd.core.Mailer.postMailMessage("Fulfillment Pricing Request", sb.toString(), "owditadmin@owd.com", "webmaster@owd.com");

        res.sendRedirect(res.encodeRedirectURL("http://www.owd.com/test/owdthankyou.html"));

        return;

    }


    /**
     * Simple info about the servlet.
     */

    public String getServletInfo() {

        return "A simple form-to-mail servlet";

    }

}

