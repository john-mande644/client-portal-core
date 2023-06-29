package com.owd.web.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ScanManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 11, 2006
 * Time: 3:20:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderScanDownloader extends HttpServlet {
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

     try
     {
         String orderid = req.getParameter("orderid");

    String filename = req.getParameter("filename");
    String cid = req.getParameter("cid");

      if(req.getParameter("auth") != null)
      {

          String[] result = OWDUtilities.decryptData(req.getParameter("auth")).split("/");
     if(result.length == 3)
     {

        orderid=result[0];
         filename=result[1];
         cid=result[2];
     }
      }
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,new Integer(orderid));

        returnReportDataToBrowser(resp, ScanManager.getScanFromOwdOrder(order,filename), filename);
     }catch(Exception ex)
     {
        ex.printStackTrace();
         //todo redirect

     }   finally
     {
         HibernateSession.closeSession();
     }
    }




     public static void returnReportDataToBrowser(HttpServletResponse resp, byte[] data, String attName) throws Exception {


        try {

            resp.reset();
            if(attName.toUpperCase().endsWith(".PDF"))
            {
                resp.setContentType("application/pdf");
            }   else
            {
            resp.setContentType("application/octet-stream");
            }
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + attName.trim().replace(' ', '_').replace(':','-') + "\"");

            log.debug("datalen=" + data.length);
            resp.setContentLength(data.length);
            resp.getOutputStream().write(data);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("There was a problem running your report. Please check your parameters.");
        } finally {


        }

    }
}
