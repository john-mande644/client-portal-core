package com.owd.web.internal.warehouse;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.ScanManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.generated.Receive;
import com.owd.hibernate.HibernateSession;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 11, 2006
 * Time: 3:20:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScanDownloader extends HttpServlet {
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
         String rcvid = req.getParameter("rcvid");

    String filename = req.getParameter("filename");
    String cid = req.getParameter("cid");

      if(req.getParameter("auth") != null)
      {

          String[] result = OWDUtilities.decryptData(req.getParameter("auth")).split("/");
     if(result.length == 3)
     {

        rcvid=result[0];
         filename=result[1];
         cid=result[2];
     }
      }
        Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class,new Integer(rcvid));

        returnReportDataToBrowser(resp, ScanManager.getScanFromReceive(rcv,filename), filename);
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
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + attName.trim().replace(' ', '_') + "\"");

            //log.debug("datalen=" + data.length);
            resp.setContentLength(data.length);
            resp.getOutputStream().write(data);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("There was a problem running your report. Please check your parameters.");
        } finally {


        }

    }

    public static void main (String[] args)
    {
        try
        {
            String file = "42977_2012-07-26_15:45:02.pdf";
            Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class,42977);
            log.debug(ScanManager.getScanFromReceive(rcv,file));
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());


        }catch(Exception ex)
        {
            ex.printStackTrace();


        }   finally
        {
            // HibernateSession.closeSession();
        }


    }
}
