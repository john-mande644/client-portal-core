package com.owd.core.managers;

import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Event;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import net.sf.jasperreports.engine.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by stewartbuskirk1 on 7/31/15.
 */
public class JasperReportPrintManager {
private final static Logger log =  LogManager.getLogger();

    private static JasperReport comminvoiceReport = null;

    public static synchronized JasperReport getCompiledCommInvoiceJasperReport() throws Exception {
        if (comminvoiceReport == null) {
            String path2 = "/com/owd/jaspertemplates/comminvoice.jrxml";

            comminvoiceReport = JasperCompileManager.compileReport(JasperReportPrintManager.class.getResourceAsStream(path2));

        }
        return comminvoiceReport;

    }
    private static JasperReport returnTemplateReport = null;

    public static synchronized JasperReport getCompiledReturnTemplateReport() throws Exception {
        if (returnTemplateReport == null) {
            String path2 = "/com/owd/jaspertemplates/returntemplate.jrxml";

            returnTemplateReport = JasperCompileManager.compileReport(JasperReportPrintManager.class.getResourceAsStream(path2));

        }
        return returnTemplateReport;

    }
    public static synchronized JasperReport getCompiledMIReturnTemplateReport() throws Exception {
        if (returnTemplateReport == null) {
            String path2 = "/com/owd/jaspertemplates/MIreturntemplate.jrxml";

            returnTemplateReport = JasperCompileManager.compileReport(JasperReportPrintManager.class.getResourceAsStream(path2));

        }
        return returnTemplateReport;

    }

    static private Map<Integer, Map<String, JasperReport>> clientPackingSlipMap = new TreeMap<Integer, Map<String, JasperReport>>();

    public static synchronized JasperReport getCompiledPackingSlipJasperReport(int clientId, String templateKey) throws Exception {
        JasperReport template = null;
        if(templateKey.length()==0){
            templateKey="Pack";
        }
        if (clientPackingSlipMap.get(clientId) != null) {
            template = clientPackingSlipMap.get(clientId).get(templateKey);
        }
        if (template == null) {
            String path2 = "/com/owd/jaspertemplates/"+clientId + "-" + templateKey + ".jrxml";
             template = JasperCompileManager.compileReport(JasperReportPrintManager.class.getResourceAsStream(path2));
            if (template != null) {

                Map<String, JasperReport> amap = null;
                if (clientPackingSlipMap.get(clientId) == null) {
                    amap = new TreeMap<String, JasperReport>();
                    clientPackingSlipMap.put(clientId, amap);
                }
                clientPackingSlipMap.get(clientId).put(templateKey, template);
            }

        } else {
            template = clientPackingSlipMap.get(clientId).get(templateKey);
        }
        if (template == null) {
            throw new Exception("Packing slip template key " + templateKey + " for com.owd.api.client ID " + clientId + "could not be found");
        }
        return template;

    }

    public static void getCommercialInvoice(HttpServletRequest req, HttpServletResponse response) throws ServletException, Exception {


        try {
            String orderid = req.getParameter("orderid");

            String cid = req.getParameter("cid");
            String ordernum = req.getParameter("ordernum");

            if (req.getParameter("auth") != null) {

                String[] result = OWDUtilities.decryptData(req.getParameter("auth")).split("/");
                if (result.length == 3) {

                    orderid = result[0];
                    cid = result[1];
                    ordernum = result[2];
                }
            }
            log.debug("getting order");
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, new Integer(orderid));
            log.debug("got order " + order);
            if (order != null) {
                Event.addOrderEvent(new Integer(orderid).intValue(), Event.kEventTypeHandling, "Commercial invoice PDF downloaded by " + req.getUserPrincipal().getName(), req.getUserPrincipal().getName());


                byte[] data = getCommInvoicePdf(ordernum);

                returnScanDataToBrowser(response, data, "CommInvoice-" + ordernum + ".pdf");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //todo redirect

        } finally {
            HibernateSession.closeSession();
        }


    }

    public static byte[] getReturnTemplatePdf(String ordernum, Image label) throws Exception {
        Map parameterMap = new HashMap();
        parameterMap.put("order_id", ordernum);
        parameterMap.put("labelImage",label);


        JasperPrint print = JasperFillManager.fillReport(getCompiledReturnTemplateReport(),
                parameterMap, ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

        return JasperExportManager.exportReportToPdf(print);
    }
    public static byte[] getMIReturnTemplatePdf(String ordernum, Image label) throws Exception {
        Map parameterMap = new HashMap();
        parameterMap.put("order_id", ordernum);
        parameterMap.put("labelImage",label);


        JasperPrint print = JasperFillManager.fillReport(getCompiledMIReturnTemplateReport(),
                parameterMap, ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

        return JasperExportManager.exportReportToPdf(print);
    }

    public static byte[] getCommInvoicePdf(String ordernum) throws Exception {
        Map parameterMap = new HashMap();
        parameterMap.put("ORDER_NUM", ordernum);


        JasperPrint print = JasperFillManager.fillReport(getCompiledCommInvoiceJasperReport(),
                parameterMap, ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

        return JasperExportManager.exportReportToPdf(print);
    }


    public static void main(String[] args) throws Exception {
                JasperReportPrintManager pm = new  JasperReportPrintManager();

        log.debug(pm.getClass().getResource(pm.getClass().getSimpleName() + ".class"));
        log.debug(Thread.currentThread());
        log.debug(Thread.currentThread().getContextClassLoader());
        String fullPath="";

        try{
            fullPath = JasperReportPrintManager.class.getResource("/com/owd/jaspertemplates/").getFile();
            log.debug("HBM PATH:"+fullPath);
        } catch (Exception e1) {
            try{
            e1.printStackTrace();
                fullPath = Thread.currentThread().getContextClassLoader().getResource("com/owd/jaspertemplates/").getFile();
                log.debug("HBM PATH2:"+fullPath);

        } catch (Exception e2) {
                try{
                    e1.printStackTrace();
                    fullPath = Thread.currentThread().getContextClassLoader().getResource("/com/owd/jaspertemplates/").getFile();
                    log.debug("HBM PATH3:"+fullPath);
                } catch (Exception e3) {
                     e3.printStackTrace();
                }

        }
        }

        log.debug("PATH:"+fullPath);
        byte[] data = getPackingSlip("29967366",657,"");

       // byte[] data = getCommInvoicePdf("16460400");

        log.debug("PDF size:"+data.length);
    }

    public static byte[] getPackingSlip(String owdOrderRef, int clientId, String templateName) throws Exception {


        try {

            String fullPath;

            try{
                fullPath = JasperReportPrintManager.class.getResource("/com/owd/jaspertemplates/").getFile();
                log.debug("HBM PATH:"+fullPath);
            } catch (Exception e1) {
                try{
                    e1.printStackTrace();
                    fullPath = Thread.currentThread().getContextClassLoader().getResource("com/owd/jaspertemplates/").getFile();
                    log.debug("HBM PATH2:"+fullPath);

                } catch (Exception e2) {
                    e1.printStackTrace();
                    fullPath = Thread.currentThread().getContextClassLoader().getResource("/com/owd/jaspertemplates/").getFile();
                    log.debug("HBM PATH3:"+fullPath);


                }
            }

            Map parameterMap = new HashMap();
            parameterMap.put("order_id", owdOrderRef);
            parameterMap.put("imageDir", fullPath);


            JasperPrint print = JasperFillManager.fillReport(getCompiledPackingSlipJasperReport(clientId, templateName),
                    parameterMap, ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

            byte[] data = JasperExportManager.exportReportToPdf(print);

            return data;

        } catch (Exception ex) {
            ex.printStackTrace();
            //todo redirect
            throw ex;
        } finally {
            HibernateSession.closeSession();
        }


    }

    public static void returnScanDataToBrowser(HttpServletResponse resp, byte[] data, String attName) throws Exception {


        try {

            resp.reset();
            if (attName.toUpperCase().endsWith(".PDF")) {
                resp.setContentType("application/pdf");
            } else {
                resp.setContentType("application/octet-stream");
            }
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + attName.trim().replace(' ', '_').replace(':', '-') + "\"");

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
