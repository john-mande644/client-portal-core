package com.owd.dc.inventory;

import com.owd.dc.inventory.beans.pallettagBean;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.HibernateSession;
import com.thoughtworks.xstream.XStream;

import java.awt.print.PrinterJob;
import java.net.Socket;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import org.w3c.dom.Document;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: May 8, 2005
 * Time: 7:52:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class LabelUtilities {
    private static int packageSocket = 10030;
    private static int testSocket = 50001;
    private static String testIP = "127.0.0.1";
    private static int barcodeSocket = 10028;
    private static int upcSocket = 10029;
    private static int inventorySocket = 10031;
    private static String niceWatchServer = "192.168.10.5";
    //private static String smallPrinter = "\\\\receiving2\\dymo";
    private static String smallPrinter="\\\\receiving3\\dymo";
    private static String barcodeLabelname = "productbarcode.lbl";
    private static String upcLabelName="owdupc.lbl";
    private static String upc13LabelName="owdupc13.lbl";
    private static String eanLabelName="owdean.lbl";
    private static String smallLabelName="smallinventory.lbl";
    private static String barcodeEndString = "z4z4z4z4";
    private static String delm = "|";
    private static String inventorypath = "\\\\172.16.66.255\\shareddocs\\";
    private static String inventoryPath = "\\\\192.168.10.5\\labels\\inventory\\";
    private static String preLabelName = "LabelName=";
    private static String prePrinter = "Printer=";
    private static String preNumberLabels = "Numberlabels=";
    private static String preBarcode = "Barcode=";
    private static String prePartnum = "Partnum=";
    private static String preDescription = "Description=";
    private static String preLocation = "Location=";
    private static String preColor = "color=";
    private static String preSize = "size=";
    private static String preClient= "client=";
    private static String preSku= "Sku=";
    private static String endLabel = ";";
    private static String end = "\r\n";
    private static String inventoryOpening = "Inventory Labels\r\n\r\n";
    private static String inventoryLargeLabel = "inventory.lbl";
   // private static String largePrinter = "191510th";
   //  private static String largePrinter = "\\\\jay\\dymo";
    private static String inventoryEndString="zzzz";
   private static String palletPrinter = "\\\\receiving3\\receiving";
    private static String palletLabel="pallettag.lbl";
    public static void testlabel(String barcode) {
        try {
            Socket testsocket = new Socket(testIP, testSocket);
            PrintWriter sendlabel = new PrintWriter(testsocket.getOutputStream());
            sendlabel.print(barcode);
            sendlabel.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

       public static void barcodeLabel(String barcode, int num_labels, String printer, String server)throws Exception{
                             printLabel(barcode, num_labels,printer,server);
       }
   /* public static void barcodeLabel(String barcode, int num_labels, String printer) throws Exception {

        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, (Integer.decode(barcode)));
     if(inv.getUpcCode().length()==13){
             String upc = inv.getUpcCode().substring(0,12);
            Socket barcodesocket = new Socket(niceWatchServer, upcSocket);
        PrintWriter sendlabel = new PrintWriter(barcodesocket.getOutputStream());
        String labeltext = inv.getInventoryNum() + delm + num_labels + delm + upc13LabelName +
                delm + printer + delm + upc+ delm + barcodeEndString;
        sendlabel.print(labeltext);
        sendlabel.close();
    }else if(inv.getUpcCode().length()>10){
            String upc = inv.getUpcCode().substring(0,11);
            Socket barcodesocket = new Socket(niceWatchServer, upcSocket);
        PrintWriter sendlabel = new PrintWriter(barcodesocket.getOutputStream());
        String labeltext = inv.getInventoryNum() + delm + num_labels + delm + upcLabelName +
                delm + printer + delm + upc+ delm + barcodeEndString;
        sendlabel.print(labeltext);
        sendlabel.close();


        } else if(inv.getIsbnCode().length()>10){
                  String upc = inv.getIsbnCode().substring(0,12);
            Socket barcodesocket = new Socket(niceWatchServer, upcSocket);
        PrintWriter sendlabel = new PrintWriter(barcodesocket.getOutputStream());
        String labeltext = inv.getInventoryNum() + delm + num_labels + delm + eanLabelName +
                delm + printer + delm + upc+ delm + barcodeEndString;
        sendlabel.print(labeltext);
        sendlabel.close();

        }  else{
          String labeltext = inv.getInventoryId()+delm+inv.getInventoryNum()+delm+inv.getDescription()+delm+inv.getFrontLocation()+
                delm+inv.getItemColor()+delm+inv.getItemSize()+delm+num_labels+delm+smallLabelName+delm+printer+delm+inventoryEndString;
        Socket testsocket = new Socket(niceWatchServer, inventorySocket);
        PrintWriter sendlabel = new PrintWriter(testsocket.getOutputStream());
        sendlabel.print(labeltext);
        sendlabel.close();

        }


    }*/
     public static void printLabel(String id, int numLabels, String printer, String server) throws Exception {
         URL url = new URL("http://"+server+"/wms2/labelMaker/printInventory.action");

                 String data= "printer="+printer+"&id="+id+"&qty="+numLabels;
           System.out.println(data);
                 URLConnection conn = url.openConnection();
                 conn.setDoOutput(true);
                 OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                 wr.write(data);
                 wr.flush();
                 wr.close();
         BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                // Process line...
            }
            wr.close();
            rd.close();



     }

    public static void printLargeInventoryLabel(String id, int numLabels, String printer, String server) throws Exception {

           printLabel(id, numLabels, printer,server);






    }
   /* public static void printLargeInventoryLabel(String id, int numLabels,String printer) throws Exception {

            if (numLabels > 20){
                throw new Exception("Label Count is too High");
            }
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, (new Integer(id)));
        String labeltext = inv.getInventoryId()+delm+inv.getInventoryNum()+delm+inv.getDescription()+delm+inv.getFrontLocation()+
                delm+inv.getItemColor()+delm+inv.getItemSize()+delm+numLabels+delm+inventoryLargeLabel+delm+printer+delm+inventoryEndString;
        Socket testsocket = new Socket(niceWatchServer, inventorySocket);
        PrintWriter sendlabel = new PrintWriter(testsocket.getOutputStream());
        sendlabel.print(labeltext);
        sendlabel.close();
    }*/
    
    public static void printLocationLabel_Large(int id, int numLabels,String printer) throws Exception {
            
            if (numLabels > 20){
                throw new Exception("Label Count is too High");
            }
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, (new Integer(id)));
        String labeltext = inv.getInventoryId()+delm+inv.getInventoryNum()+delm+inv.getDescription()+delm+inv.getFrontLocation()+
                delm+inv.getItemColor()+delm+inv.getItemSize()+delm+numLabels+delm+smallLabelName+delm+printer+delm+inventoryEndString;
        Socket testsocket = new Socket(niceWatchServer, inventorySocket);
        PrintWriter sendlabel = new PrintWriter(testsocket.getOutputStream());
        sendlabel.print(labeltext);
        sendlabel.close();
      /*  if (inv.getClientItemKey().equals("127")) {

            FileWriter writer = new FileWriter(inventoryPath + "hello.txt");
            writer.write(inventoryOpening);
            writer.write(preLabelName + inventoryLargeLabel + end);
            writer.write(prePrinter + largePrinter + end);
            writer.write(preNumberLabels + numLabels + end);
            writer.write(preBarcode + inv.getInventoryId() + end);
            writer.write(prePartnum + inv.getInventoryId() + end);
            writer.write(preDescription + inv.getDescription() + end);
            writer.write(preLocation + inv.getFrontLocation() + end);
            writer.write(preColor + inv.getItemColor() + end);
            writer.write(preSize + inv.getItemSize() + end);
            writer.write(endLabel);
            writer.close();
        } else {
            FileWriter writer = new FileWriter(inventoryPath + "hello.txt");
            writer.write(inventoryOpening);
            writer.write(preLabelName + inventoryLargeLabel + end);
            writer.write(prePrinter + largePrinter + end);
            writer.write(preNumberLabels + numLabels + end);
            writer.write(preBarcode + inv.getInventoryId() + end);
            writer.write(prePartnum + inv.getInventoryNum() + end);
            writer.write(preDescription + inv.getDescription() + end);
            writer.write(preLocation + inv.getFrontLocation() + end);
            writer.write(preColor + inv.getItemColor() + end);
            writer.write(preSize + inv.getItemSize() + end);
            writer.write(endLabel);
            writer.close();
        }
       */

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
    public static void printPalletTag(OwdInventory inv, int numLabels,String printer) throws Exception{

        pallettagBean pallet = new pallettagBean();
        pallet.setClient(inv.getOwdClient().getCompanyName());
        pallet.setDescription(inv.getDescription());
        pallet.setInventoryId(inv.getInventoryId());
        pallet.setInventoryNum(inv.getInventoryNum());
        pallet.setQty(numLabels);
        XStream x = pallettagBean.getXStream();
        System.out.println(x.toXML(pallet));
        Map params = new HashMap();
        Document  document = loadXMLFrom(x.toXML(pallet)) ;
        params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, document);
        		params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
        		params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
        		params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
        		params.put(JRParameter.REPORT_LOCALE, Locale.US);
        //System.out.println(System.getProperties());
        File l = new File("blah.txt");
        System.out.println(l.getAbsolutePath());
        System.out.println("1");
        JasperPrint print = JasperFillManager.fillReport(System.getProperty("user.dir")+"/lib/pallettag.jasper",params);
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
        printRequestAttributeSet.add(new Copies(numLabels));
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
    public static void main(String args[]){
        try{
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(143770));
        printPalletTag(inv,1,"BASEMENT");
/*
            pallettagBean pallet = new pallettagBean();
            pallet.setClient(inv.getOwdClient().getCompanyName());
            pallet.setDescription(inv.getDescription());
            pallet.setInventoryId(inv.getInventoryId());
            pallet.setInventoryNum(inv.getInventoryNum());
            pallet.setQty(1);
            XStream x = pallettagBean.getXStream();
            System.out.println(x.toXML(pallet));*/
        //    printerStuff();
       //  printLargeInventoryLabel("36351",1,"zebra1.dc1.owd.com","danny.owd.com");
        //   printPackageBarcode("2165489994");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void printPackageBarcode(String barcode) throws Exception{

          Socket barcodesocket = new Socket(niceWatchServer, packageSocket);
        PrintWriter sendlabel = new PrintWriter(barcodesocket.getOutputStream());
        String labeltext = barcode + delm + barcodeEndString;
        sendlabel.print(labeltext);
        sendlabel.close();

    }
}


