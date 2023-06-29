package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.*;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.order.*;
import com.owd.core.business.order.clients.PhionUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.batchimporters.OWDUploadOrdersData_1;
import com.owd.jobs.jobobjects.batchimporters.PhionOmxImportData;
import com.owd.jobs.jobobjects.utilities.RateShopper;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 27, 2005
 * Time: 10:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhionOrdersImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    private static String[] ordersBulkLoadColumns = {"OrderID","CustomerID","CustomerFirstName","CustomerLastName","CustomerPhone","CustomerEmail","BillingStreet1","BillingStreet2","BillingCity","BillingState","BillingZip","BillingCountry","PaymentType","PaymentCostModifier","PaymentConfirmationMessage","ShippingSameAsBilling","ShippingName","ShippingFirstName","ShippingLastName","ShippingStreet1","ShippingStreet2","ShippingCity","ShippingState","ShippingZip","ShippingCountry","TotalShippingWeight","OrderStatus","OrderDateTime","OrderNotes","OrderTax","OrderShippingCost","OrderSubTotal","QuantityDiscounts","OrderTotal","AdminShippingTracking","AdminSalesRep","AdminNotes","dPerOrderHandlingFee","DiscountCode","DiscountAmount","DiscountCookie","QuantityDiscounts","Notes","CustomerCompanyName","CreditCard","CreditCardExtra","Keycode","Return","blankfield","ProductID","MfgName","Desc1","Price","Weight","Qty","MfgPart","PartNo"};      //46

    static List<String> holdNames = new ArrayList<String>();

    {

        holdNames.add("HPC-Grocery Invoices");
        holdNames.add("Potential Dynamix");
        holdNames.add("Biovorur");
     //   holdNames.add("Select Nutrition");
      //  holdNames.add("Palko Distributing Co");
        holdNames.add("Tree Of Life");
        holdNames.add("Lotus Light Enterprises");
     //   holdNames.add("Nutri-Books");
        holdNames.add("PRINCE OCHI");
        holdNames.add("UK LIMITED");
        holdNames.add("YUEN MI MING");
        holdNames.add("AMERIMARK");
        holdNames.add("Earth Fare");
        holdNames.add("Whole Foods");
      //  holdNames.add("Down to Earth");
        holdNames.add("Sunflower Market");
      //  holdNames.add("Wr Group");
        holdNames.add("Vitamin Shoppe");
        holdNames.add("accra");
        holdNames.add("World of Wellness");
        holdNames.add("Fruitful Yield");
        holdNames.add("Sprouts Farmers Market");
        holdNames.add("DrugStore");
        holdNames.add("drugstore.com");
        holdNames.add("Lucky Vitamin");
        holdNames.add("Palko Distributing Co.");
        holdNames.add("Palko Distributing");
        holdNames.add("Avocado Ninja");

    }

    public static void main(String[] args) throws Exception {

        //  internalExecute("2005-4-7");
        run();

    }

    public void internalExecute() {

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", OWDConstants.SMTPServer);

            URLName url = new URLName("pop3", "secure.emailsrvr.com", -1, "INBOX", "phionorders@owd.com", "money1");


            javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props, null);
            mailSession.setDebug(false);
            Store popStore = mailSession.getStore(url);

            popStore.connect();
            Folder inbox = popStore.getDefaultFolder();
            if (inbox == null)
                throw new MessagingException("No default folder");

            inbox = inbox.getFolder("INBOX");
            if (inbox == null)
                throw new MessagingException("Invalid folder");

            inbox.open(Folder.READ_WRITE);

            javax.mail.Message[] messages = {};
            int count = inbox.getMessageCount();
            log.debug("avpi got " + count + " messages...");
            if (count > 0)
                messages = inbox.getMessages();

            for (int i = 0; i < messages.length; i++) {
                int newOrders = 0;

                try {
                    String subject = messages[i].getSubject();
                    log.debug("got message: " + subject);


                    javax.mail.Multipart mp = null;
                    int parts = 1;
                    try {
                        javax.mail.internet.MimeMessage message = (javax.mail.internet.MimeMessage) messages[i];
                        //log.debug("got javax.mail.Message content=" + message.getContentType());
                        mp = (javax.mail.Multipart) (((MimeMessage) message).getContent());
                        //log.debug("got message content");
                        parts = mp.getCount();
                        //log.debug("got message partcount=" + parts);
                        //log.debug("got MimeMessage, partcount=" + parts + ", content-type=" + mp.getContentType() + ", subject=" + subject);
                    } catch (ClassCastException ecc1) {
                        //////log.debug("avpi imp got null mp");


                    }

                    InputStream in = null;
                    String partName = "";
                    for (int j = 0; j < parts; j++) {
                        //log.debug("checking part " + j);
                        if (mp != null) {
                            MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
                            //////log.debug("got message bodypart");


                            if (part.getContentType().indexOf("binhex") >= 0) {
                                in = new BinHex4InputStream(part.getInputStream());
                                //////log.debug(((com.owd.BinHex4InputStream)in).getHeader());
                            } else {
                                in = part.getInputStream();
                            }
                            partName = part.getFileName();
                            if (partName == null) partName = "";
                            //////log.debug("got inputstream");
                            //log.debug("got part " + j + " , encoding=" + part.getEncoding() + ", content-type=" + part.getContentType() + " , description=" + part.getDescription() + " , disposition=" + part.getDisposition() + ", size=" + part.getSize() + ", name=" + part.getFileName());
                        } else {
                            in = messages[i].getInputStream();

                        }

                        try {
                            log.debug("partname:"+partName);
                            if (partName.toUpperCase().endsWith(".CSV")) {
                                StringBuffer sbx = new StringBuffer();

                                List results = processDataFile(new com.owd.core.CSVReader(new BufferedReader(new InputStreamReader(in)), true));

                                if (results.size() > 0) {
                                    newOrders = 1;
                                }
                                Iterator it = results.iterator();
                                while (it.hasNext()) {
                                    sbx.append("\r\n" + it.next());
                                }
                                Vector emailsx = new Vector();
                                log.debug(sbx);
                                emailsx.add("frank@ph-ion.com");
                                emailsx.add("brittany@ph-ion.com");
                                log.debug(sbx.toString());
                               // Mailer.postMailMessage("PhIon Order Import (" + partName + ")", sbx.toString(), emailsx, "phionimport@owd.com");
                            } else {
                                //log.debug("Got non-CSV part");
                            }
                        } catch (Exception mimepartex) {
                            mimepartex.printStackTrace();
                        }
                    } //for each MIME part

                } catch (ClassCastException ecc) {
                    //messages[i].setFlag(Flags.Flag.DELETED,true);
                    ecc.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException: " + ecc.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                    //////////log.debug("CUTImporter stamper="+stamper);
                    ecc.printStackTrace();
                    //      Mailer.postMailMessage("AVPIImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");

                } catch (Exception ex) {

                    ex.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
                    //////////log.debug("CUTImporter stamper="+stamper);
                    ex.printStackTrace();
                    Vector emails = new Vector();
                    emails.add("owditadmin@owd.com");
                    //     Mailer.postMailMessage("AVPIImporter import error", sb.toString(), emails, "owditadmin@owd.com");
                }
                if (newOrders == 0) {
                    messages[i].setFlag(Flags.Flag.DELETED, true);
                }
            }    //for each message


            inbox.close(true);


        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            //  Mailer.postMailMessage("AVPIImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");
        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            //   Mailer.postMailMessage("AVPIImporter import error", sb.toString(), "owditadmin@owd.com", "owditadmin@owd.com");
        }

    }

    private List processDataFile(DelimitedReader data) throws Exception {

        List resultsList = new ArrayList();


        PhionOmxImportData dataHandler = new PhionOmxImportData();
        dataHandler.init(data);

        //log.debug("toprocess " + dataHandler.getOrderCount());

        for (int orderIndex = 0; orderIndex < dataHandler.getOrderCount(); orderIndex++) {
            try {
                List resultL = processOrder(dataHandler, "179", OrderXMLDoc.kBackOrderAll, orderIndex);
                //log.debug(resultL);
                if (resultL.toString().indexOf(",") > 0 || resultL.toString().toUpperCase().indexOf("BACKORDER") >= 0 ||
                        resultL.toString().toUpperCase().indexOf("ON HOLD") >= 0) {
                    //log.debug("adding to result list");
                    resultsList.add(resultL);
                }
                //log.debug("processed " + (orderIndex + 1));
                //record success
            } catch (Exception ex) {
                //record error
                //ex.printStackTrace();
                //log.debug("Uncaught error at row " + orderIndex + ":" + ex.getMessage());
            }


        }
        return resultsList;
    }



    private List processOrder(PhionOmxImportData dataHandler, String clientID, String backorderRule, int orderIndex) {
        //returns list of two elements - client Order ID and response
        List response = new ArrayList();
        //add new

        if(!(OrderUtilities.orderRefnumExists(dataHandler.getOrderReference(orderIndex),clientID))) {
            response.add(dataHandler.getOrderReference(orderIndex));

            try {
                Order order = new Order(clientID);

                order.ship_operator = "OrderMotion";
                order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.backorder_rule = OrderXMLDoc.kBackOrderAll;
                dataHandler.loadOrder(order, orderIndex);
                //log.debug("after load order");

                order.paid_amount = order.total_order_cost;
                order.is_paid = 1;

                order.is_future_ship = 0;


                if (order.containsSKU("CATW") || order.containsSKU("CAT6") || order.containsSKU("CATR") || order.containsSKU("CATALOG-2007")) {
                    int index = order.findLineItemForSKU("CATW");
                    if (index >= 0) {
                        if (((LineItem) order.skuList.elementAt(index)).quantity_request > OrderUtilities.getAvailableInventory(Inventory.dbloadByPart("CATW", "179").inventory_id + "", FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId())) {
                            order.deleteLineItemForSKU("CATW");
                        }
                    }
                    index = order.findLineItemForSKU("CAT6");
                    if (index >= 0) {
                        if (((LineItem) order.skuList.elementAt(index)).quantity_request > OrderUtilities.getAvailableInventory(Inventory.dbloadByPart("CAT6", "179").inventory_id + "", FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId())) {
                            order.deleteLineItemForSKU("CAT6");
                        }
                    }
                    index = order.findLineItemForSKU("CATR");
                    if (index >= 0) {
                        if (((LineItem) order.skuList.elementAt(index)).quantity_request > OrderUtilities.getAvailableInventory(Inventory.dbloadByPart("CATR", "179").inventory_id + "", FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId())) {
                            order.deleteLineItemForSKU("CATR");
                        }
                    }
                    index = order.findLineItemForSKU("CATALOG-2007");
                    if (index >= 0) {
                        if (((LineItem) order.skuList.elementAt(index)).quantity_request > OrderUtilities.getAvailableInventory(Inventory.dbloadByPart("CATALOG-2007", "179").inventory_id + "", FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId())) {
                            order.deleteLineItemForSKU("CATALOG-2007");
                        }
                    }

                } else {
                    if (order.skuList.size() > 0 && OrderUtilities.getAvailableInventory(Inventory.dbloadByPart("CATALOG-2007", "179").inventory_id + "", FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId()) > 1) {
                        order.addLineItem("CATALOG-2007", "1", "0.00", "0.00", "", "", "");
                    }
                    if (order.skuList.size() > 0 && OrderUtilities.getAvailableInventory(Inventory.dbloadByPart("SUPR", "179").inventory_id + "", FacilitiesManager.getFacilityForCode(order.getFacilityCode()).getId()) > 1) {
                        order.addLineItem("SUPR", "1", "0.00", "0.00", "", "", "");
                    }
                }


                //  order.addInsertItemIfAvailable("PHP3",1);   USPS FIRST-CLASS MAIL INTL


/*
             if (OrderUtilities.getAvailableInventory(Inventory.dbloadByPart("php15", "179").inventory_id + "") > 2 )
                                {
                                    PhionUtilities.getInstance().addItem(order, "php15", "1", "0.00", "0.00", "", "", "");
                                }*/

                determineShipMethod(order.getShippingInfo().carr_service, order);

            /**/
                try {
                    putAddresseeOnHold(order);
                } catch (Exception ex) {
                    log.debug("unable to put phion order with bad name on hold");
                    ex.printStackTrace();
                }


                order.recalculateBalance();
                log.debug("total:" + order.total_order_cost + "::paid:" + order.paid_amount);
                String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, true);
                log.debug("reference=" + reference);
                if (reference == null) {
                    if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1
                            &&
                            (order.last_payment_error + " " + order.last_error).indexOf("no physical items") < 1) {
                        log.debug("reporting error on import");
                        throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                    } else {
                        //duplicate
                    }
                } else {
                    log.debug("got valid order import");


                    if (order.is_future_ship == 1) {
                        response.add("[HELD ORDER] " + order.hold_reason);
                    }
                    if (order.is_backorder == 1) {
                        StringBuffer sb = new StringBuffer();


                        response.add("[BACKORDER] " + sb);
                    }
                }

            } catch (Exception
                    ex) {


                ex.printStackTrace();


                response.add("[NOT IMPORTED] " + ex.getMessage());
            } finally {


            }
        }
        return response;
    }



    private static void determineShipMethod(String shipmethod, Order order) throws Exception {
        OrderRater rater = new OrderRater(order);
        rater.setCalculateKitWeights(true);
        List domStandardMethods;
        List domExpeditedMethods;
        List domFreeMethods;
        List intlStandardMethods;
        List intlExpeditedMethods;

        domStandardMethods = Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY", "CONNECTSHIP_UPS.UPS.GND","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD");
        domExpeditedMethods = Arrays.asList("CONNECTSHIP_UPS.UPS.2DA", "TANDATA_USPS.USPS.EXPR","FDX.2DA");
        domFreeMethods = Arrays.asList("TANDATA_USPS.USPS.PRIORITY", "CONNECTSHIP_UPS.UPS.GND","TANDATA_FEDEXFSMS.FEDEX.GND","TANDATA_FEDEXFSMS.FEDEX.FHD");
        intlStandardMethods = Arrays.asList("CONNECTSHIP_UPS.UPS.STD", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY","UPS.STDCAMX");
        intlExpeditedMethods = Arrays.asList("TANDATA_USPS.USPS.I_EXP_DMND");

        List<String> currMethods = null;

        /*  if (order.getShippingAddress().isInternational())
    {
        order.getShippingInfo().setShipOptions("USPS First-Class Mail International", "Prepaid", "");
        if (shipmethod.contains("EXPRESS"))
        {
            currMethods = intlExpeditedMethods;
        } else
        {
            currMethods = intlStandardMethods;
        }
    } else
    {
        order.getShippingInfo().setShipOptions("USPS First-Class Mail", "Prepaid", "");
        if (shipmethod.contains("GROUND"))
        {
            order.getShippingInfo().setShipOptions("UPS Ground", "Prepaid", "");
            currMethods = new ArrayList();
        }


        if (shipmethod.contains("PRIORITY"))
        {
            order.getShippingInfo().setShipOptions("USPS Priority Mail", "Prepaid", "");
            currMethods = new ArrayList();
        } else if (shipmethod.contains("FREE"))
        {
            currMethods = domFreeMethods;
        } else if (shipmethod.contains("NEXT DAY"))
        {
            currMethods = domNextDayMethods;
        }else if (shipmethod.contains("2 DAY") || shipmethod.contains("EXPRESS"))
        {
            currMethods = domExpeditedMethods;
        } else
        {
            currMethods = domStandardMethods;
        }
    }*/
        String moShipTranslated = null;
        shipmethod = shipmethod.toUpperCase();
        log.debug("shipmethod:"+shipmethod);

        if (shipmethod.contains("UPS ") || shipmethod.contains("UNITED PARCEL SERVICE")) {
            //ups
            if (shipmethod.indexOf("NEXT DAY") >= 0) {
                moShipTranslated = "UPS Next Day Air Saver";
            } else if ((shipmethod.indexOf("GROUND") >= 0) && (shipmethod.indexOf("FREE")>=0)) {
                moShipTranslated = "UPS Ground";
            } else if (shipmethod.indexOf("GROUND") >= 0) {
                moShipTranslated = "UPS Ground";
            } else if (shipmethod.indexOf("STANDARD") >= 0) {
                moShipTranslated = "UPS Standard Canada";
            } else if (shipmethod.indexOf("2ND DAY") >= 0 || shipmethod.contains("2 DAY")) {
                moShipTranslated = "UPS 2nd Day Air";
            } else if (shipmethod.indexOf("3 DAY") >= 0) {
                moShipTranslated = "UPS 3 Day Select";
            } else if (shipmethod.indexOf("WORLDWIDE EXPRESS SAVER") >= 0) {
                moShipTranslated = "UPS Worldwide Express Saver";
            } else if (shipmethod.indexOf("WORLDWIDE EXPRESS EXPEDITED") >= 0) {
                moShipTranslated = "UPS Worldwide Expedited";
            }
        }

        if (shipmethod.startsWith("FDX") || shipmethod.startsWith("FEDEX")) {
            //          USPS GLOBAL EXPRESS - WEIGHT
            //todo
        } else {
            //usps
            if ((shipmethod.indexOf("GLOBAL EXPRESS") >= 0 || shipmethod.indexOf("EXPRESS MAIL") >= 0) && order.getShippingAddress().isInternational()) {
                moShipTranslated = "USPS Priority Mail Express International";
            } else if (shipmethod.indexOf("EXPRESS MAIL") >= 0) {
                moShipTranslated = "USPS Priority Mail Express";              //
            } else if (shipmethod.indexOf("CLASS MAIL INT") >= 0 || shipmethod.contains("INTERNATIONAL AIRMAIL POST") || shipmethod.contains("INTERNATIONAL AIRMAIL PARCEL")) {
                currMethods = intlStandardMethods;
                //moShipTranslated = "USPS First-Class Mail International";
            } else if (shipmethod.indexOf("FIRST-CLASS") >= 0 || shipmethod.indexOf("FIRST CLASS") >= 0) {
                moShipTranslated = "USPS First-Class Mail";
            } else if (shipmethod.indexOf("PRIORITY MAIL INT") >= 0 || (order.getShippingAddress().isInternational() && shipmethod.contains("STANDARD"))) {
                moShipTranslated = "USPS Priority Mail International";
            } else if (shipmethod.indexOf("PRIORITY MAIL") >= 0) {
                moShipTranslated = "USPS Priority Mail";
            } else if (shipmethod.indexOf("MEDIA MAIL") >= 0) {
                moShipTranslated = "USPS Media Mail Single-Piece";
            } else if (shipmethod.indexOf("PARCEL POST") >= 0) {
                moShipTranslated = "USPS Parcel Select Nonpresort";
            } else if (shipmethod.indexOf("LIBRARY") >= 0) {
                moShipTranslated = "USPS Priority Mail";
            } else if (shipmethod.indexOf("WEB STANDARD") >= 0 && order.getShippingAddress().isInternational()) {
                currMethods = intlStandardMethods;
            }  else if (shipmethod.indexOf("WEB INTL STANDARD") >= 0 && order.getShippingAddress().isInternational()) {
                currMethods = intlStandardMethods;
            } else if (shipmethod.indexOf("WEB STANDARD") >= 0 && order.getShippingAddress().isUS()) {
                currMethods = domStandardMethods;
            } else if (shipmethod.indexOf("WEB RUSH") >= 0 && order.getShippingAddress().isUS()) {
                currMethods = domExpeditedMethods;
            }   else if (shipmethod.indexOf("FREE") >= 0 && order.getShippingAddress().isUS()) {
                currMethods = domStandardMethods;
            }    else if (shipmethod.indexOf("FREE") >= 0 && !(order.getShippingAddress().isUS())) {
                currMethods = intlStandardMethods;
            }
        }

        log.debug("shipmethod:"+shipmethod);
        log.debug("moship:"+moShipTranslated);
        log.debug("currmeth:"+currMethods);
        log.debug("shipaddr:"+order.getShippingAddress());

        if (moShipTranslated != null) {
            currMethods = new ArrayList<String>();
            currMethods.add(moShipTranslated);
        }
            if (currMethods.size() > 0) {
                if (currMethods.size() == 1) {
                    order.getShippingInfo().setShipOptions((String) currMethods.get(0), "Prepaid", "");
                } else {
                    order.getShippingInfo().setShipOptions(RateShopper.rateShop(order, currMethods)
                    , "Prepaid", "");
                }
            }

    }

    private static void putAddresseeOnHold(Order order)
            throws Exception {
        String holdName = null;
        for (String testname : holdNames) {
            String cappedtestName = testname.toUpperCase();

            if (holdName == null) {
                if (order.getBillingContact().getName().toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getBillingAddress().company_name.toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getShippingContact().getName().toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getShippingAddress().company_name.toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getShippingAddress().city.toUpperCase().indexOf(cappedtestName) >= 0
                        || order.getShippingAddress().state.toUpperCase().indexOf(cappedtestName) >= 0
                        ) {
                    holdName = testname;
                }
            }
        }
        if (holdName != null) {
            order.is_future_ship = 1;
            Mailer.sendMail("Phion " + holdName + " order received on hold", "Phion order " + order.order_refnum + " was received and placed on hold per the \"" + holdName + "\" rule", "lroberts@owd.com", "do-not-reply@owd.com");
        }
    }

    private static void addPackInstructionItem(String sku, Order order, int qty) throws Exception {
        if (sku.toUpperCase().equals("COMPPK-PW")) order.addInsertItemIfAvailable("INSTR-COMPSYS", qty);
        if (sku.toUpperCase().equals("PHMIRPK-PW")) order.addInsertItemIfAvailable("Inst200", qty);
        if (sku.toUpperCase().equals("ALKPK-PW")) order.addInsertItemIfAvailable("INSTR-ALKZERSYS", qty);
        if (sku.toUpperCase().equals("PHMAINPK-PW")) order.addInsertItemIfAvailable("INSTR-MAINTSYS", qty);
        if (sku.toUpperCase().equals("BASPK-PW")) order.addInsertItemIfAvailable("INSTR-BASICSYS", qty);
        if (sku.toUpperCase().equals("DETOXPK")) order.addInsertItemIfAvailable("Inst500", qty);
        if (sku.toUpperCase().equals("WGHTLOSSPK")) order.addInsertItemIfAvailable("Inst700", qty);
        if (sku.toUpperCase().equals("COMPPK-CP")) order.addInsertItemIfAvailable("INSTR-COMPSYS", qty);
        if (sku.toUpperCase().equals("PHMIRPK-CP")) order.addInsertItemIfAvailable("Inst200", qty);
        if (sku.toUpperCase().equals("ALKPK-CP")) order.addInsertItemIfAvailable("INSTR-ALKZERSYS", qty);
        if (sku.toUpperCase().equals("PHMAINPK-CP")) order.addInsertItemIfAvailable("INSTR-MAINTSYS", qty);
        if (sku.toUpperCase().equals("BASPK-CP")) order.addInsertItemIfAvailable("INSTR-BASICSYS", qty);
        if (sku.toUpperCase().equals("ALKZP")) order.addInsertItemIfAvailable("PIALKZ", qty);
        if (sku.toUpperCase().equals("ALKZC")) order.addInsertItemIfAvailable("PIALKZ", qty);
        if (sku.toUpperCase().equals("BASCC")) order.addInsertItemIfAvailable("PIBASC", qty);
        if (sku.toUpperCase().equals("BASCP")) order.addInsertItemIfAvailable("PIBASC", qty);
        if (sku.toUpperCase().equals("COLNP")) order.addInsertItemIfAvailable("PICOLN", qty);
        if (sku.toUpperCase().equals("COLNC")) order.addInsertItemIfAvailable("PICOLN", qty);
        if (sku.toUpperCase().equals("COMPP")) order.addInsertItemIfAvailable("PICOMP", qty);
        if (sku.toUpperCase().equals("COMPC")) order.addInsertItemIfAvailable("PICOMP", qty);
        if (sku.toUpperCase().equals("DETXP")) order.addInsertItemIfAvailable("PIDETX", qty);
        if (sku.toUpperCase().equals("MAINP ")) order.addInsertItemIfAvailable("PIMAIN", qty);
        if (sku.toUpperCase().equals("WTLSP")) order.addInsertItemIfAvailable("PIWTLS", qty);
        if (sku.toUpperCase().equals("STARP")) order.addInsertItemIfAvailable("PISTAR", qty);
        if (sku.toUpperCase().equals("DETXC")) order.addInsertItemIfAvailable("PIDETX", qty);
        if (sku.toUpperCase().equals("MAINC ")) order.addInsertItemIfAvailable("PIMAIN", qty);
        if (sku.toUpperCase().equals("WTLSC")) order.addInsertItemIfAvailable("PIWTLS", qty);
        if (sku.toUpperCase().equals("STARC")) order.addInsertItemIfAvailable("PISTAR", qty);
    }


}
