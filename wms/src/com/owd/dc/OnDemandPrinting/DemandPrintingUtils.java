package com.owd.dc.OnDemandPrinting;

import com.owd.core.TimeStamp;
import com.owd.dc.OnDemandPrinting.BollAndBranch.giftMessage;
import com.owd.dc.OnDemandPrinting.BollAndBranch.*;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.thoughtworks.xstream.XStream;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import org.hibernate.Query;
import org.w3c.dom.Document;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import java.awt.print.PrinterJob;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 7/10/14
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class DemandPrintingUtils {
    public static void main(String[] args){
        try{
            giftMessage message = new giftMessage();
            message.setMessage("Hope this reaches you in good health and wishes");
           // printBollBranchGiftCard(message, "BOLLTEST");
            OnDemandInfoBean ib = checkForOnDemandSkusViaOrderNum("13949068");
            System.out.println(ib.isOnDemand());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static OnDemandInfoBean checkForOnDemandSkusViaOrderNum(String orderNum) throws Exception{
        System.out.println("checking via order Num");

        TimeStamp ts1 = new TimeStamp("Start");

        String sql = "execute sp_getOrderIdFromOrderNum :orderNum";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderNum", orderNum);

        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(q.list().get(0).toString()));

           System.out.println(order.getClientFkey());

        ts1.print("end");
         return checkForOnDemandSkus(order);
    }
    public static OnDemandInfoBean checkForOnDemandSkus(OwdOrder order) throws Exception{
        OnDemandInfoBean ib = new OnDemandInfoBean();
        ib.setOnDemand(false);
                  //Add individual rules here. If we get true return it right away.
        if(order.getClientFkey()==489||order.getClientFkey()==55){
            System.out.println("we are going to test boll now");
            ib =BollBanchUtils.isOnDemandOrder(order);


        }

        return ib;
    }
    public static void printBollBranchGiftCard(giftMessage message, String printer) throws Exception{


        XStream x = giftMessage.getXStream();
        System.out.println(x.toXML(message));
        Map params = new HashMap();
        Document document = loadXMLFrom(x.toXML(message)) ;
        params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
        params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
        params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
        params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
        params.put(JRParameter.REPORT_LOCALE, Locale.US);
        //System.out.println(System.getProperties());
        System.out.println("1");
        JasperPrint print = JasperFillManager.fillReport(System.getProperty("user.dir") + "/lib/BollBranch.jasper", params);
        System.out.println("2");
        JRExporter exporter = new JRPrintServiceExporter();
        System.out.println("3");
        // exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, System.getProperty("user.dir")+"/lib/testing.pdf");
        System.out.println("4");
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        System.out.println("5");
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService theprinter = printerStuff(printer);
        job.setPrintService(theprinter);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(1));
        printRequestAttributeSet.add(new MediaPrintableArea(0f,0f,6f,8.5f, MediaPrintableArea.INCH));
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, theprinter);
        // exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, theprinter.getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        // Export the PDF file
        exporter.exportReport();

    }
    public static PrintService printerStuff(String printer) throws Exception{
        try{
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /* Scan found services to see if anyone suits our needs */
            for(int i = 0; i < services.length;i++){
                System.out.println(services[i].getName());
                if(services[i].getName().toUpperCase().equals(printer)){
            /*If the service is named as what we are querying we select it */
                    System.out.println("Found it");
                    return services[i];

                }
            }
            throw new Exception("Printer not found");
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Printer not found");
        }
    }
    public static org.w3c.dom.Document loadXMLFrom(String xml)
            throws org.xml.sax.SAXException, java.io.IOException {
        return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes()));
    }

    public static org.w3c.dom.Document loadXMLFrom(java.io.InputStream is)
            throws org.xml.sax.SAXException, java.io.IOException {
        javax.xml.parsers.DocumentBuilderFactory factory =
                javax.xml.parsers.DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        javax.xml.parsers.DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        }
        catch (javax.xml.parsers.ParserConfigurationException ex) {
        }
        org.w3c.dom.Document doc = builder.parse(is);
        is.close();
        return doc;
    }
}
