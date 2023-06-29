import com.owd.core.MailAddressValidator;
import com.owd.core.Mailer;
import com.owd.core.OWDConstants;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibernateSession;
import jet.bean.JREngine;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import org.hibernate.engine.spi.SessionImplementor;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: Mar 9, 2010
 * Time: 3:24:14 PM
 * To change this template use File | Settings | File Templates.
 */

public class PrintServer {


    public static final String kPrintPropClientID = "CLIENTID";
    public static final String kPrintPropTemplateType = "TEMPLATE";
    public static final String kPrintPropPrinterName = "PRINTER";
    public static final String kPrintPropOrderRef = "ORDERREF";


    public static void main(String[] args) throws Exception {

       // buildPrinterNameMap();

        //    printPackingSlip("7733775", 117, "DDS", "samsung");
        //   printPackingSlip("7733775", 117, "DDS", "stewart@owd.com");
        //    printPackingSlip("7733775", 117, "Pack", "samsung");
        //     printPackingSlip("7733775", 117, "Pack", "stewart@owd.com");
        int totalPrintJobs = 0;
        while (totalPrintJobs < 1001) {
            Connection cxn = null;
            ResultSet rs = null;

          //  Set keys = getPrinterMap().keySet();

            /*
                System.out.println("Print Service Name:" + keys.toArray()[i]);
                String printerName = (String) keys.toArray()[i];*/
                //  printerMap.put(printServices[i].getName(),printServices[i]);

                try {

                    cxn = ConnectionManager.getConnection();

                    String sql = "select top 100 order_num,c.client_id, ISNULL(code,case when o.prt_pick_reqd=1 then 'Pick'\n" +
                            "                             else case when o.prt_pack_reqd=1 then 'Pack' else 'Invoice' end end), printer_name, print_queue_id, c.am_email, pdf_binary\n" +
                            "                             from owd_print_queue3 q (UPDLOCK) join owd_order o (NOLOCK) join owd_client c (NOLOCK) on c.client_id=o.client_fkey left outer join owd_order_template t on o.order_id=t.order_fkey and code<>''\n" +
                            "                             on o.order_id=q.order_id \n" +
                            "                             where isVerified = 1 and fileCreated = 0  order by q.sla asc";
                    // System.out.println(sql);
                    Map<Integer, Properties> orders = new TreeMap<Integer, Properties>();
                    Map<Integer, byte[]> pdfs = new TreeMap<Integer, byte[]>();
                    System.out.println("checking");

                    rs = cxn.createStatement().executeQuery(sql);
                    System.out.println("check complete");
                    while (rs.next()) {
                        if(rs.getString(4)!= null){
                        System.out.println("in rs next");
                        Properties props = new Properties();
                        System.out.println("in rs next 1");
                        props.setProperty(kPrintPropClientID, rs.getString(2));
                        props.setProperty(kPrintPropTemplateType, rs.getString(3));
                        props.setProperty(kPrintPropPrinterName, rs.getString(4));
                        props.setProperty(kPrintPropOrderRef, rs.getString(1));
                        props.setProperty("AMEMAIL", rs.getString(6));
                        orders.put(new Integer(rs.getInt(5)), props);
                        if (rs.getBytes(7) != null) {
                            if (rs.getBytes(7).length > 3) {
                                pdfs.put(rs.getInt(5), rs.getBytes(7));
                            }
                        }
                        }
                    }

                    rs.close();
                    //cxn.rollback();
                    System.out.println("Got " + orders.size() + " orders for this round");
                    if(orders.size()==0){
                        System.out.println("Nothing left going to take a nap");
                        Thread.sleep(15000);
                    }
                    Iterator it = orders.keySet().iterator();
                    boolean timeOK = true;
                    while (it.hasNext() && timeOK) {
                        Integer printID = (Integer) it.next();

                        long startTime = Calendar.getInstance().getTimeInMillis();

                        Properties props = (Properties) orders.get(printID);
                        System.out.println("doing " + (String) props.getProperty(kPrintPropOrderRef));

                        boolean ok = false;

                        try {
                            if (pdfs.containsKey(printID)) {
                              //  sendToEmail("orderslips@owd.com", pdfs.get(printID), (String) props.getProperty(kPrintPropOrderRef));
                                String printer = props.getProperty(kPrintPropPrinterName);
                                //write to temp file
                                if (MailAddressValidator.isValid(printer)) {
                                    sendToEmail(printer, pdfs.get(printID), (String) props.getProperty(kPrintPropOrderRef));
                                } else {
                                    //PDFPrintLibClass.printFoxitPDF(pdfs.get(printID), props.getProperty(kPrintPropPrinterName));
                                    attachPDFtoOrder.savePDFtoOrderByteArray(printID, pdfs.get(printID));
                                }


                                ok = true;
                            }
                        } catch (Exception ex) {

                            Mailer.sendMail("Packing slip pdf printing error", "The packing slip PDF for order number " + props.getProperty(kPrintPropOrderRef) + " could not be printed.\n\n" + ex.getMessage()
                                    + "\n\n",
                                    "servicerequests@owd.com", "printserver@owd.com");
                        }
                        if (!ok) {
                            try {
                                ok = printPackingSlip((String) props.getProperty(kPrintPropOrderRef),
                                        Integer.parseInt((String) props.getProperty(kPrintPropClientID)),
                                        (String) props.getProperty(kPrintPropTemplateType),
                                        (String) props.getProperty(kPrintPropPrinterName));
                            } catch (MissingTemplateException ex) {
                                ok = true;
                                Mailer.sendMail("Packing slip printing error", "The packing slip for order number " + props.getProperty(kPrintPropOrderRef) + " could not be printed.\n\n" + ex.getMessage()
                                        + "\n\nAfter verifying that the template is in place, you can reprint the packing slip at http://internal.owd.com:8080/internal/do/startreprint.",
                                        "servicerequests@owd.com", "printserver@owd.com");
                                Mailer.sendMail("Packing slip printing error", "The packing slip for order number " + props.getProperty(kPrintPropOrderRef) + " could not be printed.\n\n" + ex.getMessage()
                                        + "\n\nAfter verifying that the template is in place, you can reprint the packing slip at http://internal.owd.com:8080/internal/do/startreprint.",
                                        "" + props.getProperty("AMEMAIL"), "printserver@owd.com");
                            }
                        }
                        if (ok) {
                             updateFileCreated(cxn, (String) props.getProperty(kPrintPropOrderRef));
                           // deletePrintQueueRequest(cxn, (String) props.getProperty(kPrintPropOrderRef));
                        }

                        System.out.println("done " + (String) props.getProperty(kPrintPropOrderRef));


                        long endTime = Calendar.getInstance().getTimeInMillis();

                        if ((endTime - startTime) > 30000) {
                          //  i = keys.size();
                            timeOK = false;

                        }
                        totalPrintJobs++;
                        if (totalPrintJobs > 1000) {
                            timeOK = false;
                        }
                    }

                    cxn.close();


                } catch (Exception ex) {
                    ex.printStackTrace();
                    Mailer.sendMail("Packing slip pdf printing error",  ex.getMessage()
                            + "\n\n",
                            "dnickels@owd.com", "printserver@owd.com");
                } finally {
                    try {
                        rs.close();
                    } catch (Exception ex) {
                    }

                    try {
                        cxn.close();
                    } catch (Exception ex) {
                    }

                }


            System.out.println("loop end " + Calendar.getInstance().getTime());

            /*if (totalPrintJobs <= 200) {
                System.out.println("Pause: 1 sec");
                Thread.sleep(1000);
                System.out.println("Getting QC 1");
                int queueCount = QueueCount.getQueueCount("");
                if (queueCount < 0 || queueCount > 10) {
                    System.out.println("Pause: 9 secs");
                    Thread.sleep(9000);
                }
                System.out.println("Getting QC 2");
                queueCount = QueueCount.getQueueCount("");
                while (queueCount > 10) {
                    System.out.println("Pause: 5 secs");
                    Thread.sleep(5000);
                    System.out.println("Getting QC 3");
                    queueCount = QueueCount.getQueueCount("");
                }
            }*/
            System.out.println("End Main Loop: ttlPrintJobs=" + totalPrintJobs);

        }
        System.exit(0);
    }

    private static void buildPrinterNameMap() {
        printerMap = new TreeMap();
        PrintService printServices[] = PrinterJob.lookupPrintServices();

        for (int i = 0; i < printServices.length; i++) {
            System.out.println("Print Service:" + printServices[i].getName());
            printerMap.put(printServices[i].getName(), printServices[i]);

        }
        printerMap.put("%@%", null);

    }
    static void updateFileCreated(Connection cxn, String orderNum) throws Exception{
        cxn.createStatement().executeUpdate("update owd_print_queue3 set fileCreated = 1,fileDownloaded = 0 where order_id in " +
                "(select order_id from owd_order (NOLOCK) where order_num=" + orderNum + ")");
        cxn.commit();
    }
    static void deletePrintQueueRequest(Connection cxn, String orderNum) throws SQLException {
        cxn.createStatement().executeUpdate("delete from owd_print_queue_sl  where order_id in " +
                "(select order_id from owd_order (NOLOCK) where order_num=" + orderNum + ")");
        cxn.commit();

    }

    public static String getRootPath() {
        return rootPath;
    }

    public static void setRootPath(String rootPath) {
        PrintServer.rootPath = rootPath;
    }

    static String rootPath = null;

    private static JREngine engineBean = null;


    public static Map getPrinterMap() {
        return printerMap;
    }

    public static void setPrinterMap(Map printerMap) {
        PrintServer.printerMap = printerMap;
    }

    public static Map printerMap = new TreeMap();

    static private Map<Integer, Map<String, JasperReport>> clientPackingSlipMap = new TreeMap<Integer, Map<String, JasperReport>>();

    public static synchronized JasperReport getCompiledPackingSlipJasperReport(int clientId, String templateKey) throws Exception {
        JasperReport template = null;
        if (clientPackingSlipMap.get(clientId) != null) {
            template = clientPackingSlipMap.get(clientId).get(templateKey);
        }
        if (template == null) {
            System.out.println("" + clientId + "-" + templateKey + ".jrxml");
            template = JasperCompileManager.compileReport(PrintServer.class.getClassLoader().getResourceAsStream(clientId + "-" + templateKey.toLowerCase() + ".jrxml"));
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
            throw new MissingTemplateException("Packing slip template key " + templateKey + " for client ID " + clientId + "could not be found");
        }
        return template;

    }

    private static JREngine getEngineBean() {
        if (engineBean == null) {
            engineBean = new JREngine();
            if (getRootPath() == null) {
                setRootPath(System.getProperty("user.dir") +

                        System.getProperty("file.separator"));
            }


            //   System.out.println("EngineBean root path:"+getRootPath());
            engineBean.setUID("OWD");
            engineBean.setKey("ROUD4IH1DTJCLWQNFDZ1ASTM1D4IGKWMAZOX24OXX4BVRLC");
            engineBean.setReportHome(getRootPath() + "JReport");

            //  System.out.println("ReportHome:"+getRootPath() + "JReport");

            engineBean.setCatName(getRootPath() + "JReport"
                    + System.getProperty("file.separator") + "packingslips"
                    + System.getProperty("file.separator") + "packingslips.cat");

            //   System.out.println("Catalog:"+getRootPath() + "JReport"
            //         + System.getProperty("file.separator") + "packingslips"
            //         + System.getProperty("file.separator") + "packingslips.cat");

            getEngineBean().setShowInfoLevel(JREngine.vOff);
            //     getEngineBean().setLogFile("printserver.log");
            //   getEngineBean().setResultFile("resultfile.rst");
            System.out.println("Status after creating bean: " + getEngineBean().getStatus());


            // engineBean.getUniverse().ge


        }


        return engineBean;
    }

    public static boolean printJReportPackingSlip(String orderNum, int clientID, String templateName, String printerName) throws Exception {

        System.out.println("1:" + getEngineBean().getPrintStatus());

        String filePath = getRootPath() + "JReport"
                + System.getProperty("file.separator") + "packingslips"
                + System.getProperty("file.separator") + clientID + "-" + templateName + ".cls";

        File file = new File("" + filePath);
        if (!file.exists()) {
            throw new MissingTemplateException("Packing slip template name " + templateName + " for client ID " + clientID + "could not be found");
        }

        getEngineBean().setReportName(filePath);
        System.out.println("load1=" + engineBean.getLoadReport());
        //       System.out.println("status1="+engineBean.getStatus());

        System.out.println(filePath);
        System.out.println("2:" + getEngineBean().getPrintStatus());
        getEngineBean().setParamValues("orderNum=" + orderNum);
        //  System.out.println("3:"+getEngineBean().getPrintStatus());
        getEngineBean().runReport(false);
        System.out.println("4:" + getEngineBean().getPrintStatus());

        System.out.println("load=" + engineBean.getLoadReport());
        System.out.println("status=" + engineBean.getStatus());
        System.out.println("" + printerName);
        if (MailAddressValidator.isValid(printerName)) {
            System.out.println("Printing to " + printerName);
            getEngineBean().configMailServer(OWDConstants.SMTPServer,
                    "printserver@owd.com",
                    25);

            getEngineBean().exportToMail(printerName,
                    "orderslips@owd.com".equals(printerName) ? "" : "orderslips@owd.com",
                    "",
                    "Order PDF",
                    "Order_Num=" + orderNum + "\r\n",
                    false,
                    JREngine.PDFFILEMAIL);

            System.out.println("ok:" + getEngineBean().getPrintStatus());
            System.out.println("Errors:" + getEngineBean().getErrors());
            return true;

        } else {

           // System.out.println("sending email copy");
           /* getEngineBean().configMailServer(OWDConstants.SMTPServer,
                    "noop@owd.com",
                    25);

            getEngineBean().exportToMail("orderslips@owd.com",
                    "",
                    "",
                    "Order PDF",
                    "Order_Num=" + orderNum + "\r\n",
                    false,
                    JREngine.PDFFILEMAIL);*/
          //  System.out.println("email copy sent");
          /*  OWDPrinterJob printJob = (OWDPrinterJob) OWDPrinterJob.getPrinterJob();
            printJob.setPrintService((PrintService) printerMap.get(printerName));
            printJob.setJobName(orderNum);*/
           // getEngineBean().runReport();
            getEngineBean().exportToPdf("c:\\tmp\\" + orderNum + ".pdf");

             attachPDFtoOrder.savePDFtoOrderFile(orderNum, "c:\\tmp\\" + orderNum + ".pdf");
            File thePDF = new File("c:\\tmp\\"+orderNum+".pdf");
            System.out.println("Going to delete the file");
            //todo fix the delete
            thePDF.delete();
           /* getEngineBean().printReport(printJob, null, false, false, false, false, orderNum, printerName);*/
           // String status = getEngineBean().getPrintStatus();

              return true;
            /*if ("OK".equalsIgnoreCase(status) ) {
                System.out.println("ok:" + getEngineBean().getPrintStatus());
                return true;
            } else {
                System.out.println("err:" + status);
                // printJob.getPrintingException().printStackTrace();
                throw new Exception(status);
            }*/
        }


    }

    public static boolean printJasperReportPackingSlip(String orderNum, int clientID, String template, String printer) throws Exception {

        boolean ok = false;

        try {

            Map parameterMap = new HashMap();
            parameterMap.put("order_id", orderNum);

            JasperPrint print = JasperFillManager.fillReport(getCompiledPackingSlipJasperReport(clientID, template),
                    parameterMap,  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

            sendToEmail("orderslips@owd.com", print, orderNum);

            if (MailAddressValidator.isValid(printer)) {
                sendToEmail(printer, print, orderNum);
            } else {
                //sendToPrinter(printer, print);
                System.out.println("we have jasper");
             attachPDFtoOrder.savePDFtoOrderViaOrderNumByteArray(orderNum, JasperExportManager.exportReportToPdf(print));


            }

            ok = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            //todo redirect

        } finally {
            HibernateSession.closeSession();
        }

        return ok;


    }

    public static boolean printPackingSlip(String orderNum, int clientID, String template, String printer) throws Exception {

        if ("Pick".equals(template) || "Pack".equals(template) || "Invoice".equals(template)) {
            return printJReportPackingSlip(orderNum, clientID, template, printer);
        } else {
            return printJasperReportPackingSlip(orderNum, clientID, template, printer);
        }

    }

    public static void sendToPrinter(String printer, JasperPrint jasperPrint) throws JRException {

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
        printRequestAttributeSet.add(new Copies(1));

        PrinterName printerName = new PrinterName(printer, null); //gets printer

        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        printServiceAttributeSet.add(printerName);

        JRPrintServiceExporter exporter = new JRPrintServiceExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }

    public static void sendToEmail(String printer, byte[] pdfData, String orderNum) throws Exception {
        Mailer.sendMailWithAttachment("Order PDF",
                "Order_Num=" + orderNum + "\r\n", printer, null, pdfData, orderNum + ".pdf", "application/octet-stream");
        System.out.println("Emailed:");
    }

    public static void sendToEmail(String printer, JasperPrint jasperPrint, String orderNum) throws Exception {


        JRPdfExporter pdfmaker = new JRPdfExporter();

        ByteArrayOutputStream pdfBytes = new ByteArrayOutputStream();

        pdfmaker.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        pdfmaker.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        pdfmaker.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        pdfmaker.setParameter(JRPrintServiceExporterParameter.OUTPUT_STREAM, pdfBytes);

        pdfmaker.exportReport();

        Mailer.sendMailWithAttachment("Order PDF",
                "Order_Num=" + orderNum + "\r\n", printer, null, pdfBytes.toByteArray(), orderNum + ".pdf", "application/octet-stream");
        System.out.println("Emailed:");
    }


}
