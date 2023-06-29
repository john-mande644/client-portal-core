package com.owd.web.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.core.business.order.OrderUtilities;import com.owd.core.business.order.Order;
import com.owd.core.business.order.LineItem;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.JasperExportManager;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 27, 2003
 * Time: 4:01:57 PM
 * To change this template use Options | File Templates.
 */

public class SDWorkOrderServlet extends HttpServlet {
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
        try {
            doPost(req, resp);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException {


        try {

            String kitsku = req.getParameter("sdkitsku");
            if(kitsku==null)
            {
                        req.getRequestDispatcher("sdkit.jsp").forward(req, response);
            }else{
                if(kitsku.trim().length()<1)
                {  req.getRequestDispatcher("sdkit.jsp").forward(req, response);
                }else {
                    if(LineItem.SDKitSKUExists("319",kitsku))
                    {
                       Map parameterMap = new HashMap();
            parameterMap.put("kitsku", kitsku);

            JasperPrint print = JasperFillManager.fillReport(getClass().getClassLoader().getResourceAsStream("KittingSheet.jasper"),
                    parameterMap,  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());


            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition",
                    "attachment; filename=SDKit-" +
                    kitsku + ".pdf");

                JasperExportManager.exportReportToPdfStream(print,
                        response.getOutputStream());

                    }   else
                    {
                        req.setAttribute("sdkit-error","Kit SKU "+kitsku+" not found!");
                        req.getRequestDispatcher("sdkit.jsp").forward(req, response);
                    }


                }
            }



            } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }


    }


}
