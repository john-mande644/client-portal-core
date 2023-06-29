package com.owd.alittlePlaying.LyftProjectThings;

import com.owd.dc.inventory.beans.pallettagBean;
import com.owd.dc.packing.beans.Lyft.*;
import com.owd.dc.packing.beans.Lyft.ObjectFactory;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
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
import javax.print.attribute.standard.MediaSizeName;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by danny on 6/15/2017.
 */
public class letterUtils {




    public static void main(String[] args){
        try{
            String sql = "SELECT\n" +
                    "    dbo.owd_order_ship_info.order_fkey,\n" +
                    "    dbo.owd_order_ship_info.ship_first_name,\n" +
                    "    dbo.owd_order_ship_info.ship_last_name,\n" +
                    "    dbo.owd_order_ship_info.ship_address_one,\n" +
                    "   isnull(dbo.owd_order_ship_info.ship_address_two,''),\n" +
                    "    dbo.owd_order_ship_info.ship_city,\n" +
                    "    dbo.owd_order_ship_info.ship_state,\n" +
                    "    dbo.owd_order_ship_info.ship_zip,\n" +
                    "    dbo.zzLyftDuplicates.needReturned,\n" +
                    "    LetterSent\n" +
                    "FROM\n" +
                    "    dbo.zzLyftDuplicates\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order_ship_info\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.zzLyftDuplicates.orderFkey = dbo.owd_order_ship_info.order_fkey) where LetterSent = 6 and printed  is null ;";


            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l  = q.list();



            for(Object row:l){

                Object[] data = (Object[]) row;
                lookupBean lookup = new lookupBean();

                lookup.setName(data[1].toString() + " " +data[2].toString());
                lookup.setAddress1(data[3].toString());
                lookup.setAddress2(data[4].toString());
                lookup.setCity(data[5].toString());
                lookup.setState(data[6].toString());
                lookup.setZip(data[7].toString());
                lookup.setOrderId(data[0].toString());
                lookup.setUnits("1");

                printPalletTag(lookup);

            }










        }catch (Exception e){


        }



    }


    public static void printPalletTag(lookupBean lookup) throws Exception{






        XStream x = lookupBean.getXStream();
        System.out.println(x.toXML(lookup));
        Map params = new HashMap();
        Document document = loadXMLFrom(x.toXML(lookup)) ;
        params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
        params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
        params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
        params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
        params.put(JRParameter.REPORT_LOCALE, Locale.US);
        //System.out.println(System.getProperties());
        File l = new File("blah.txt");
        System.out.println(l.getAbsolutePath());
        System.out.println("1");
        JasperPrint print = JasperFillManager.fillReport("C:\\Users\\danny\\JaspersoftWorkspaceV2\\MyReports\\LyftLetter.jasper", params);
      //  print.setName(lookup.getOrderId()+".pdf");
       // JasperExportManager.exportReportToPdfFile(print, "d:/lyft/" + lookup.getOrderId()+".pdf");

        System.out.println("2");
        JRExporter exporter = new JRPrintServiceExporter();
        System.out.println("4");
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        System.out.println("5");
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService theprinter = printerStuff("DC1South");
       // PrintService theprinter = printerStuff("AdminSamsung");
        job.setPrintService(theprinter);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(new Copies(1));
        printRequestAttributeSet.add(MediaSizeName.NA_LETTER);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, theprinter);
        // exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, theprinter.getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        // Export the PDF file
        exporter.exportReport();
      /*   Ftp ftp = new Ftp("192.168.10.5","label","labels");
         ftp.connect();

        ftp.setDir("pallettag");
        ftp.setBinary();
        StringBuffer sb = new StringBuffer();
        sb.append("Inventory Labels\r\n\r\n");
        sb.append(preLabelName+palletLabel+"\r\n");
        sb.append(prePrinter+palletPrinter+"\r\n");
        sb.append(preNumberLabels+numLabels+"\r\n");
        sb.append(preBarcode+inv.getInventoryId()+"\r\n");
        sb.append(prePartnum+inv.getInventoryNum()+"\r\n");
        sb.append(preDescription+inv.getDescription()+"\r\n");
        sb.append(preLocation+inv.getFrontLocation()+"\r\n");
        sb.append(preColor+inv.getItemColor()+"\r\n");
        sb.append(preSize+inv.getItemSize()+"\r\n");
        sb.append(preClient+inv.getOwdClient().getCompanyName()+"\r\n");
        sb.append(";");
        InputStream is = new StringBufferInputStream(sb.toString());

        System.out.println("doing pallet tag");
        ftp.upload(is,"zzz"+Calendar.getInstance().getTimeInMillis()+".txt");
        ftp.disconnect();*/
    }


    public static org.w3c.dom.Document loadXMLFrom(String xml)
            throws org.xml.sax.SAXException, java.io.IOException {
        return loadXMLFrom(new java.io.ByteArrayInputStream(xml.getBytes()));
    }


    public static PrintService printerStuff(String printer) throws Exception{
        try{
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /* Scan found services to see if anyone suits our needs */
            for(int i = 0; i < services.length;i++){
                System.out.println(services[i].getName());
                if(services[i].getName().toUpperCase().equals(printer.toUpperCase())){
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
