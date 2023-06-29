//revised on June 12, 2002
package com.owd.web.api;

import com.owd.hibernate.generated.ScanReceive;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.ScanManager;
import com.owd.hibernate.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;

public class ScanRetrievalServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    public void init(ServletConfig config)
            throws ServletException {
        ////log.debug("in API init");
        super.init(config);

    }

    public void destroy() {
        ////log.debug("in API destroy");
        super.destroy();

    }

    //GET requests not supported
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        doGet(req,resp);

        //all requests should be POST
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        try {
            req.setCharacterEncoding("UTF-8");

        } catch (Exception exxx) {
            exxx.printStackTrace();
        }
        //    log.debug("in API post2");

        try {
            resp.setCharacterEncoding("UTF-8");

            String encryptValue = req.getParameter("auth");
            log.debug("auth="+encryptValue);
            String decryptValue = OWDUtilities.decryptData(encryptValue);
            log.debug("decrypt="+decryptValue);
            String[] authValues = decryptValue.split(":");
            log.debug("auth value count="+authValues.length);
            if (authValues.length != 3) {
                throw new Exception("Incorrect link provided");
            }
            String cid = authValues[0];
            String scanId = authValues[1];

            ScanReceive sr = (ScanReceive) HibernateSession.currentSession().load(ScanReceive.class, Integer.parseInt(scanId));
            if (sr.getReceive().getClientFkey() != Integer.parseInt(cid)) {
                throw new Exception("Authorization not found");
            }
            if (sr == null) {
                throw new Exception("Scan not found");
            } else {

                byte[] scanData = ScanManager.getScanFromReceive(sr.getReceive(), sr.getName());

                resp.setContentType("application/pdf");

                resp.setContentLength(scanData.length);

                resp.setHeader("Content-Disposition",
                        "attachment;filename=" + sr.getName() + ".pdf");

                OutputStream os = resp.getOutputStream();

                os.write(scanData);

                os.flush();
                os.close();

            }

        } catch (Throwable ex) {
            ex.printStackTrace();
            Mailer.postMailMessage(" Scan Response Write  Failure:" + ex.getMessage(), OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "apimonitor@owd.com");

        }
    }

    public String getServletInfo() {
        return "One World Scan Retrieval Server v" + API.getVersionString();
    }


    public static void main(String[] args) {
        try {
            log.debug(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("TANDATA_FEDEXFSMS.FEDEX.SP_PS"))));

        } catch (Exception ex) {
            log.debug("");
            ex.printStackTrace();
        }

    }


}
