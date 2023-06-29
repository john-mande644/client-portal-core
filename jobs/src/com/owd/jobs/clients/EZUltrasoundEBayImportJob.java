package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.ebay.sdk.*;
import com.ebay.sdk.call.CompleteSaleCall;
import com.ebay.sdk.call.GetOrdersCall;
import com.ebay.sdk.call.GetSellerTransactionsCall;
import com.ebay.soap.eBLBaseComponents.*;
import com.owd.core.CountryNames;
import com.owd.core.DuplicateOrderException;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.Contact;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.utilities.lineItemBean;
import com.owd.jobs.jobobjects.utilities.orderInfoBean;
import ipworks.*;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Eric
 * Date: June 18, 2008
 * Time: 3:23:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class EZUltrasoundEBayImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();




    static final String ebLogin = "81cd5f0b-e52f-400c-b1d1-4a23da062486";
    static final String ebApp = "OneWorld-917f-429a-a054-4fff4277d407";
    static final String ebCert = "83c2e71e-4a14-477a-9f18-df55426a769d";

    static final String ebToken=    "AgAAAA**AQAAAA**aAAAAA**UWiCUg**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wGl4unCpCGpAydj6x9nY+seQ**tbAAAA**AAMAAA**w2BH60ex+/caHE8Vfe8Bmy/hNGHu7vxKAuk7JQmjEIR6O4Y+obvigeoxBAR5jzub+vYaUUWbCCtjfs5slHY0RUpMHewL4TKtikNoNswjV7tkelNz3+mqMSamIRtQ5sr2NAsL+IPGddlY1T5aYx/SKzVXctST/1L4wAbLXrIdiAQHBXxigzJniGFmWUvR4rJC83HxjnwOMWXWmleefD8A+NQXATNoQnQAWscYrqlzNmP5Q8Qw4Viuqgv252ztiyXhQGmQZwBoJ7o/yIabX+hdH1azPf1oqKy/vuy5oW7Pxwo11kqwYbK0YErDsB479Nvvi7CnPuyWQL8TXuHyE9Wj4f4JdmHqWWgurcYJ36rZ7ORVyCDTOZKLfI01rmUVsgRxTGIrfcSmHm8a8sVoDJppuZ+4LZ6Sx4iOFFo5lzO8sPKAMJ4shmwSZuxmGl1wru/mwTf5OKWNhfzxYYr70zei20P2faKPdJirlxrW8S4IleNQZWsQxfrdJVMLrgoEjgZQsxlbPVi5cRZ5B9kPXLUtV73KeiCsdBW8sXcIivE9mwBa+me1kbJOURCrjro/H+w3k+GD6w4PqRkd7qR5Y64uj17JxxcfXHhDjiAbzVonxiyWJp0Rn8w1wzlcw3zuKJhIV/rygnB1fKqoQ6vOaIHXariQY/1ZCUonmT5G0breLRuCa91BtEpIiuepMGZTT62jb0r736gdN0TAWCPGB+kDE9JChM2dy4CEdsSsjvrjcr+sNa0mwspubIhwXUAx5VPl";
     static final String getOrderItemTitle(String s)
    {
        return "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=NV&appid=OneWorld-917f-429a-a054-4fff4277d407&siteid=0&version=515&ItemID=" + s + "";
    }


    static final String getOrderDownloadUrl(String s)
    {
        return "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=NV&appid=OneWorld-917f-429a-a054-4fff4277d407&siteid=0&version=515&ItemID=" + s + "&IncludeSelector=Description";
    }
              static final String backRule = OrderXMLDoc.kBackOrderAll;
    public static final String clientID = "432";

    private static void lastly(){}

     static void finalOrderUpdate(Order order) throws Exception
     {
         if(!order.getShippingAddress().isInternational())
            {
                order.backorder_rule = backRule;
            }

           if ((order.containsSKU("US1000") || order.containsSKU("ZZA1000") || order.containsSKU("PainShield")) && order.getShippingAddress().isCanada()) {
            //cannot ship these to canada
         //   order.is_future_ship = 1;

        }

        if (!order.containsSKU("returnslip")) {
            try
            {
            order.addInsertItemIfAvailable("returnslip", 1);
            }catch(Exception exinsert)
            {
                //ignore
            }
        }
            if (order.containsSKU("ZZA1000")) {

                order.addLineItem("ChinaSample", ""+order.getQuantityForSKU("ZZA1000"),"0.00","0.00","","","");

        }
           if (order.containsSKU("US1000")) {

                order.addLineItem("Sombra4", ""+order.getQuantityForSKU("US1000"),"0.00","0.00","","","");

        }

        if (order.containsSKU("UCPro")) {

                order.addLineItem("Sombra4", ""+order.getQuantityForSKU("UCPro"),"0.00","0.00","","","");

        }
         if (order.containsSKU("UCPro") || order.containsSKU("US1000") || order.containsSKU("Sombra4") || order.containsSKU("Sombra8")) {

                order.addInsertItemIfAvailable("Sombra-Brochure",1);

        }

         if (order.containsSKU("UCPro") || order.containsSKU("US1000")) {

             order.addInsertItemIfAvailable("User-Reference-Guide", 1);

         }
     }


    public void connected(HttpConnectedEvent httpConnectedEvent) {
        log.debug("HTTP CONNECTED");
    }

    public void connectionStatus(HttpConnectionStatusEvent httpConnectionStatusEvent) {
        log.debug("HTTP STATUS");
    }

    public void disconnected(HttpDisconnectedEvent httpDisconnectedEvent) {
        log.debug("HTTP DISCONNECT "+httpDisconnectedEvent.statusCode+"/"+httpDisconnectedEvent.description);
    }

    public void endTransfer(HttpEndTransferEvent httpEndTransferEvent) {
        log.debug("HTTP END TRANSFER");
    }

    public void header(HttpHeaderEvent httpHeaderEvent) {
        log.debug("HTTP HEADER "+httpHeaderEvent.field+":"+httpHeaderEvent.value);
    }

    public void redirect(HttpRedirectEvent httpRedirectEvent) {
        log.debug("HTTP REDIRECT");
    }

    public void setCookie(HttpSetCookieEvent httpSetCookieEvent) {
        log.debug("HTTP SET-COOKIE");
    }

    public void startTransfer(HttpStartTransferEvent httpStartTransferEvent) {
        log.debug("HTTP START TRANSFER");
    }

    public void status(HttpStatusEvent httpStatusEvent) {
        log.debug("HTTP STATUS "+httpStatusEvent.statusCode+"/"+httpStatusEvent.description);
    }

    public void transfer(HttpTransferEvent httpTransferEvent) {
        log.debug("HTTP TRANSFER "+httpTransferEvent.bytesTransferred);
    }

    public void error(HttpErrorEvent httpErrorEvent) {
        log.debug("HTTP "+httpErrorEvent.description+":"+httpErrorEvent);
    }

    static final Map<String, String> methodMap;

    static final Pattern skuFind = Pattern.compile("(?<=\\(SKU: {0,1})[[a-zA-Z_0-9\\.]- /|, ]*(?=\\))");

    static final String goodStatusString = "COMPLETECHECKOUT_COMPLETENO_PAYMENT_FAILURENONEPAY_PAL";//"CompleteCheckoutCompleteNoPaymentFailureNonePayPal";

    // static boolean singleDVD = false;
    public static void main(String[] args)throws Exception  {

     //    methodMap.get(trans.getShippingServiceSelected().getShippingService().toString())
        run();
      //     postTrackingInformation(3590665,"380177992171-158492156025","USPS First-Class Mail International","EBay Listing");

         //  log.debug(getSkuAndTitleMapFromEbay("120762686045"));
            //      log.debug(getSKUsFromText("(SKU: US1000,China4)"));
     //    log.debug(methodMap.get("NotSelected"));
//           Order order = new Order("1");
//           order.setBillingContact(new Contact());
//           order.getBillingContact().setName("Roscoe Whitmountain");

         //  sendTermsEmail(order);
     }



    public static boolean postTrackingInformation(int orderId, String owdEbayOrderReference, String shipmethod, String orderType)
    {

       try
       {
             ApiAccount account = new ApiAccount();
            account.setDeveloper(ebLogin);
            account.setApplication(ebApp);
            account.setCertificate(ebCert);

// set ApiAccount and token in ApiCredential
            ApiCredential credential = new ApiCredential();
            credential.setApiAccount(account);
            credential.seteBayToken(ebToken);
            ApiContext context = new ApiContext();
            context.setApiCredential(credential);
            ApiLogging logging = new ApiLogging();
         //   logging.setEnableLogging(true);
            logging.setLogSOAPMessages(false);
            logging.setLogExceptions(true);
            context.setApiLogging(logging);

            //     ApiCall call = new ApiCall( context );

// set eBay server URL to call
            context.setApiServerUrl("https://api.ebay.com/wsapi");  // sandbox

// set timeout in milliseconds - 3 minutes
            context.setTimeout(180000);

// set wsdl version number
            context.setWSDLVersion("639");


                CompleteSaleCall api = new CompleteSaleCall(context);

               // api.setItemID();
              //  api.setTransactionID();
                api.setShipped(true);

        List<ShipmentTrackingDetailsType> packages = new ArrayList<ShipmentTrackingDetailsType>();

        List<com.owd.core.business.order.Package> packs = com.owd.core.business.order.Package.getPackagesForOrderId(orderId);
        for(com.owd.core.business.order.Package pack: packs)
        {
            log.debug("got pack "+pack);
             ShipmentTrackingDetailsType std = new ShipmentTrackingDetailsType();
             if(shipmethod.toUpperCase().contains("USPS"))
        {
            std.setShippingCarrierUsed(ShippingCarrierCodeType.USPS.toString());
        }else if (shipmethod.toUpperCase().contains("UPS"))
        {
            std.setShippingCarrierUsed(ShippingCarrierCodeType.UPS.toString());
        }else
        {
            std.setShippingCarrierUsed(ShippingCarrierCodeType.OTHER.toString());

        }
            std.setShipmentTrackingNumber(pack.tracking_no);
            packages.add(std);
        }




         ShipmentType shipdata = new ShipmentType();
        shipdata.setShippingServiceUsed(shipmethod);
        shipdata.setShippedTime(Calendar.getInstance());
           log.debug(""+packages.toArray());
           ShipmentTrackingDetailsType[] shipArray = new ShipmentTrackingDetailsType[packages.size()];

        shipdata.setShipmentTrackingDetails(packages.toArray(shipArray));

       api.setShipment(shipdata);
           log.debug("checking order type");
       if(orderType.toUpperCase().contains("EBAY LISTING"))
       {
           log.debug(" checking orderref "+owdEbayOrderReference);
            if(owdEbayOrderReference.toUpperCase().startsWith("COMBINED-"))
            {
                log.debug(" setting order id "+owdEbayOrderReference.substring(owdEbayOrderReference.indexOf("-")+1));
             api.setOrderID(owdEbayOrderReference.substring(owdEbayOrderReference.indexOf("-")+1));
             api.setItemID(owdEbayOrderReference.substring(owdEbayOrderReference.indexOf("-")+1));
            }else if(owdEbayOrderReference.contains("-"))
            {

             api.setTransactionID(owdEbayOrderReference.substring(owdEbayOrderReference.indexOf("-")+1));
                api.setItemID(owdEbayOrderReference.substring(0,owdEbayOrderReference.indexOf("-")));
                log.debug("set item id="+api.getItemID());
        }   else
        {
             api.setItemID(owdEbayOrderReference);
        }



            }

        api.completeSale();

    }catch(Exception ex)
    {
      ex.printStackTrace();
    }

      return true;
       }
      private static void getTransactionInfo() {
        try {

            // set devId, appId, certId in ApiAccount
            /*
            Key Set 1
DevID    b3282552-0dd6-4a86-9e76-c2eeaf0fbf21

AppID   OneWorld-ba62-4b2c-ab97-9bf9cbb06f14

CertID   01123f83-6737-4344-9373-92c5169b6a1e

Token for East Cost Trading (Andrew Boyce):
AgAAAA**AQAAAA**aAAAAA**wYl/SA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wJlYKoDZWCpwudj6x9nY+seQ**yHEAAA**AAMAAA**ZqT49bYYM8UTVyrHuQ7DcYbHASZLbHz4/+KjBW1rq/mdOjQ/LZcfUFWyr2sHI9+ORfAUdDBxkLD+pec3jfN5vnO18uNM/EJWAgZHSpAiAqZGZSw/x4jTeS70bdzAzatQREppTgy+pwu/RLspN3ccx28zPRgEu9Gq4IIsI4ntLDACU8qey968c/XOuP4M7hT6K0DpRmLNSDLaLD8xz3B9CcMwUBhdM2ckigktXbpiQFyZUOYg/evA2iYUIwjFNn/1v3PdiODtg28toJdH/eHKk5JwbCXIDzBMDoHoQabQPg0d7l6B6q2ajaz1IeHw4kX0o50TB0GW5bUAnLPa/GSI1tTg01YykCxJ9jBXjHcpDYKSdkz9XSQjR5r3b0dB7MLjpch7zYMbO9rqDdCEZVLMhE5LGUO2h3fGneWlvmHGoiofx3dGhwV+8F32rK6yKrYMgl47M+MkAsNA/lvekshoOHtgYRcqsYtZZ29SiRPZNIE5Wv0Z4fEtieugijH0KHN14uBiqrcFeXf8KoCibbzDzo6q5M4Ncv1qFw0qzkIF70K92ukonG411FY19RpKAco3ZVsxntThWzi9ISaVNpWVBTqqPlZgMB+P350IsLSsicbwlP2423Z54S9O8ligFn0DUlgaO7DTpNCUUu9s/LpLQW4OC8sLGq2DwM27AzFzRNy9U3Drqw7Ux7PdSrhVP+/C0XrQ+0I/7QeZ2ebgUOBlOPriTBNOYH/D/raEnLHuJRsZNXP/U/2CKzGxKKla2xU3

REST token:
%2FOYhGFbI3%2BI%3D**ARzzy%2BicZRvAll8EPTDQjV8hADU%3D

Tokens expire 1-1-2010

XML API Production Gateway URI
https://api.ebay.com/ws/api.dll
SOAP API Production Gateway URI
https://api.ebay.com/wsapi
             */


            ApiAccount account = new ApiAccount();
            account.setDeveloper(ebLogin);
            account.setApplication(ebApp);
            account.setCertificate(ebCert);

// set ApiAccount and token in ApiCredential
            ApiCredential credential = new ApiCredential();
            credential.setApiAccount(account);
            credential.seteBayToken(ebToken);// add ApiCredential to ApiContext
            ApiContext context = new ApiContext();
            context.setApiCredential(credential);
            ApiLogging logging = new ApiLogging();
       //     logging.setEnableLogging(true);
            logging.setLogSOAPMessages(false);
            logging.setLogExceptions(true);
            context.setApiLogging(logging);

            //     ApiCall call = new ApiCall( context );

// set eBay server URL to call
            context.setApiServerUrl("https://api.ebay.com/wsapi");  // sandbox

// set timeout in milliseconds - 3 minutes
            context.setTimeout(180000);

// set wsdl version number
            context.setWSDLVersion("639");

// turn on logging
            int currentPage = 1;
            int pageCount = 1;

// create soap api request and response objects
            while (pageCount >= currentPage) {
                GetSellerTransactionsCall api = new GetSellerTransactionsCall(context);

                DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[]{
                        DetailLevelCodeType.RETURN_ALL
                };
                api.setDetailLevel(detailLevels);
                api.setIncludeContainingOrder(true);

                PaginationType pt = new PaginationType();

                pt.setEntriesPerPage(100);
                pt.setPageNumber(currentPage);
                api.setPagination(pt);

                Calendar twoDaysAgo = Calendar.getInstance();
                twoDaysAgo.add(Calendar.DATE, -2);
                TimeFilter tf = new TimeFilter(twoDaysAgo, Calendar.getInstance());

                List<String> orderIDs = new ArrayList<String>();

                try {

                    final TransactionType[] transList = api.getSellerTransactions(tf);
                    if (api.getHasMoreTransactions()) {
                        pageCount = api.getPaginationResult().getTotalNumberOfPages();
                    }

                    for (TransactionType trans : transList) {
                        try {

                            log.debug(""+trans.getItem().getItemID());
                            log.debug("1:"+trans.getPaidTime());
                            log.debug("2:"+trans.getBuyerPaidStatus());
                            log.debug("3:"+trans.getStatus());
                             log.debug("4:"+trans.getStatus().getCompleteStatus());
                             log.debug("5:"+trans.getStatus().getCheckoutStatus());
                             log.debug("6:"+trans.getStatus().getEBayPaymentStatus());
                             log.debug("7:"+trans.getStatus().getPaymentHoldStatus());
                             log.debug("8:"+trans.getStatus().getPaymentMethodUsed());


                          /*


                          For item: 270329930821

                             no response while payment not complete


                           */
                            if (trans.getStatus().getCompleteStatus().toString().equalsIgnoreCase("Complete")) {
                                if (trans.getContainingOrder() != null) {
                                    log.debug("ORDER ID:" + trans.getContainingOrder().getOrderID() + " (" + trans.getContainingOrder().getOrderStatus() + ")");
                                    if (!(orderIDs.contains(trans.getContainingOrder().getOrderID()))) {
                                        orderIDs.add(trans.getContainingOrder().getOrderID());
                                    }
                                } else {
                                    log.debug("TransID:"+trans.getTransactionID()+ " (" + trans.getStatus().getCompleteStatus()
                                            + ")");
                                    log.debug("ItemID:"+trans.getItem().getItemID());

                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                } catch (ApiException ae) {
                    log.debug(ae);
                } catch (SdkSoapException sse) {
                    log.debug(sse);
                } catch (SdkException se) {
                    log.debug(se);
                }


                currentPage++;
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


     //   releasePaidOrders();
    }

    public void internalExecute() {


        try {
            log.debug("GETTING TRANSACTIONS");
            getLast2DaysTransactions();
            lastly();
        } catch (Exception ex) {

            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
          //  Mailer.postMailMessage("East Coast EBay import error import error", sb.toString(), "casetracker@owd.com", "dnickels@owd.com");
        } finally {

            HibernateSession.closeSession();
        }

    }


    private void processOrder(OrderType trans) throws Exception {
        //returns list of two elements - client Order ID and response


        ClientPolicy policy  = Client.getClientForID(clientID).getClientPolicy();

        log.debug("orderID="+"Combined-"+trans.getOrderID().toString());
               log.debug("1:"+trans.getPaidTime());

                             log.debug("5:"+trans.getCheckoutStatus().getStatus());
                             log.debug("6:"+trans.getCheckoutStatus().getEBayPaymentStatus());
                             log.debug("8:"+trans.getCheckoutStatus().getPaymentMethod());

            String payStatus = trans.getCheckoutStatus().getStatus().toString()
                                       +trans.getCheckoutStatus().getEBayPaymentStatus()
                                       +trans.getCheckoutStatus().getPaymentMethod();

        //add new
       // for (int i = 0; i < trans.getTransactionArray().getTransaction().length; i++) {
            if (OrderUtilities.orderRefnumExists("Combined-"+trans.getOrderID().toString(), clientID)) {
                Vector osVec = OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderClientReference,"Combined-"+trans.getOrderID().toString(), clientID,false);


                  if(osVec.size()>0)
            {
              for(int k=0;k<osVec.size();k++)
              {
                  OrderStatus os = (OrderStatus) osVec.elementAt(k);
            if(trans.getPaidTime()!=null && os.is_on_hold && !os.is_void)
            {
                for (Event comm:(List<Event>) os.events)
                {
                    if("Address Verification Check".equalsIgnoreCase(comm.creator))
                    {
                        throw new Exception("Order found paid but already released once");
                    }
                }
                os.clearHoldAndSetBackorder();
            }
              }
            }
                log.debug("duplicate order refnum "+trans.getOrderID().toString());
               // throw new DuplicateOrderException();
            }
       // }
        orderInfoBean o = new orderInfoBean();
        try {
            Order order = new Order(clientID);
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            order.backorder_rule = backRule;


            log.debug("before loadorder");
            //load order bean from buffered reader
            o = loadOrder(trans);
            // Check for blank shipping and copy billing over
            if (null == o.getShippingFirstName() || o.getShippingFirstName().length() < 1) {
                log.debug("Copying Bill Address to ship Address");
                o.copyBillToShip();
            }

            if("NotSelected".equalsIgnoreCase(o.getShippingMethod()))
              {

                  if(o.getShippingCountry().equals("USA"))
                  {
                      o.setShippingMethod("USPS First-Class Mail");
                  }else{
                      o.setShippingMethod("USPS First-Class Mail International");
                  }
              }
            o.loadOwdOrder(order);


            order.recalculateBalance();
            order.paid_amount = order.total_order_cost;
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.is_paid = 1;
            order.order_type = "EBay Listing - Combined Order";



             log.debug("got pay status:"+payStatus);
            if("CHECKOUT_COMPLETENO_PAYMENT_FAILURENONEPAY_PAL".equalsIgnoreCase(payStatus))
            {

            order.is_future_ship = 0;
            order.po_num = "EBay payment confirmed";
            }else{

            order.is_future_ship = 1;
            order.po_num = "Paypal Unverified";
              }


            if ((order.containsSKU("US1000") || order.containsSKU("ZZA1000")) && order.getShippingAddress().isCanada() && order.is_future_ship == 1) {
             //   order.po_num = "Canadian Blocked Order";
            }

             if("USPS First-Class Mail International".equalsIgnoreCase(o.getShippingMethod()))
        {
            //calculate insurance
            float subtotal = order.total_product_cost;
            if(subtotal<130.00)
            {

            }
        }
            finalOrderUpdate(order);
          policy.saveNewOrder(order,false);
            String reference = order.order_refnum;

            log.debug(reference);
            if (reference == null) {
                    throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
            }

            policy.sendCustomerEmailConfirmation(order);

               if ((order.containsSKU("US1000") || order.containsSKU("ZZA1000") || order.containsSKU("PainShield")) && order.getShippingAddress().isCanada() && order.is_future_ship == 1) {
           // Event.addOrderEvent(Integer.parseInt(order.orderID), Event.kEventTypeHandling, "Order held due to blocked product shipping to Canada", "ORDER RELEASE CHECK");

           // Mailer.sendMail("Canadian Order Placed On Hold (OWD Ref:" + order.orderNum + ")", "Order held at OWD due to blocked product shipping to Canada", "sales@ezultrasound.com", "donotreply@owd.com");



          //  CasetrackerAPI.createNewCaseForClient("Blocked EBay Canadian Order ("+order.orderNum+")",
          //          "Order held due to blocked product shipping to Canada - please notify customer and issue refund. Update client with status and order information.", order.clientID, null, CasetrackerAPI.getLoginToken("owditadmin@owd.com", "torque"));
        }

        } catch (Exception
                ex) {
            ex.printStackTrace();

            log.debug(o.getOrderRef() + ", " + ex.getMessage());

        } finally {


        }
    }

       public static String getClientSupportProjectID(Session
            sess, int clientID) throws Exception {
        log.debug("getting email");
        String sql = "select top 1 ISNULL(sEmail,'customerservice@owd.com') as 'address' from FogBUGZ.fogbugz.dbo.Mailbox m join FogBUGZ.fogbugz.dbo.Project p\n" +
                " join FOGBUGZ.fogbugz.dbo.ACL acl join FOGBUGZ.fogbugz.dbo.PermissionGroup pg on pg.ixpermissiongroup=acl.ixpermissiongroup\n" +
                " on acl.ixproject=p.ixproject\n" +
                " on p.ixProject=m.ixProject and convert(varchar,sNotes)=convert(varchar,"+clientID+");";
        ResultSet rs = HibernateSession.getResultSet(sess, sql);
        String address = "16"; //Inbox
        if (rs.next()) {
            address = rs.getString(1);
        }

        rs.close();
        return address;
    }
    private void processOrder(TransactionType trans, List<String> skus) throws Exception {
        //returns list of two elements - client Order ID and response
        //  boolean foundShipping = false;


        String transactionID = trans.getTransactionID();
        String itemReference = "";
         if(transactionID.length()>1)
        {

            itemReference = trans.getItem().getItemID()+"-"+transactionID;
        }   else
        {
            itemReference = trans.getItem().getItemID();
        }

        log.debug("itemReference="+itemReference);
          log.debug("1:"+trans.getPaidTime());

          log.debug("5:"+trans.getStatus().getCheckoutStatus());
                             log.debug("6:"+trans.getStatus().getEBayPaymentStatus());
                             log.debug("8:"+trans.getStatus().getPaymentHoldStatus());
                             log.debug("9:"+trans.getStatus().getPaymentMethodUsed());

                 String payStatus = trans.getStatus().getCompleteStatus().toString()
                             +trans.getStatus().getCheckoutStatus()
                             +trans.getStatus().getEBayPaymentStatus()
                             +trans.getStatus().getPaymentHoldStatus()
                             +trans.getStatus().getPaymentMethodUsed();
            log.debug("got pay status:"+payStatus);


        //add new
        if (OrderUtilities.orderRefnumExists(itemReference, clientID)) {
            Vector osVec = OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderClientReference,itemReference, clientID,false);
            if(osVec.size()>0)
            {
              for(int i=0;i<osVec.size();i++)
              {
                  OrderStatus os = (OrderStatus) osVec.elementAt(i);
            if(trans.getPaidTime()!=null && os.is_on_hold && !os.is_void)
            {
                 for (Event comm:(List<Event>) os.events)
                {
                    if("Address Verification Check".equalsIgnoreCase(comm.creator))
                    {
                        throw new Exception("Order found paid but already released once");
                    }
                }
                os.clearHoldAndSetBackorder();
            }
              }
            }
            throw new DuplicateOrderException();
        }
        orderInfoBean o;
        try {
            Order order = new Order(clientID);
            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();
            order.backorder_rule = backRule;

            //load order bean from buffered reader
            log.debug("loading order");
            o = loadOrder(trans, skus);
            // Check for blank shipping and copy billing over
            if (null == o.getShippingFirstName() || o.getShippingFirstName().length() < 1) {
                o.copyBillToShip();
            }

             if("NotSelected".equalsIgnoreCase(o.getShippingMethod()))
              {

                  if(o.getShippingCountry().equalsIgnoreCase("USA"))
                  {
                      o.setShippingMethod("USPS First-Class Mail");
                  }else{
                      o.setShippingMethod("USPS First-Class Mail International");
                  }
              }




            log.debug("loading owd order");
            o.loadOwdOrder(order);


            order.recalculateBalance();
            order.paid_amount = order.total_order_cost;
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.is_paid = 1;
            order.order_type = "EBay Listing";


            if(goodStatusString.equalsIgnoreCase(payStatus))
            {

            order.is_future_ship = 0;
            order.po_num = "EBay payment confirmed";
            }    else
            {
            order.is_future_ship = 1;
            order.po_num = "Unverified";

            }

            if ((order.containsSKU("US1000") || order.containsSKU("ZZA1000")) && order.getShippingAddress().isCanada() && order.is_future_ship == 1) {
             //   order.po_num = "Canadian Blocked Order";
            }

            finalOrderUpdate(order);
            Client.getClientForID(clientID).getClientPolicy().saveNewOrder(order,false);
            String reference = order.order_refnum;


            log.debug("Client Order Ref: "+reference);
            if (reference == null) {
                    throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");

            }

             Client.getClientForID(clientID).getClientPolicy().sendCustomerEmailConfirmation(order);

                if ((order.containsSKU("US1000") || order.containsSKU("ZZA1000") || order.containsSKU("PainShield")) && order.getShippingAddress().isCanada() && order.is_future_ship == 1) {
         //   Event.addOrderEvent(Integer.parseInt(order.orderID), Event.kEventTypeHandling, "Order held due to blocked product shipping to Canada", "ORDER RELEASE CHECK");

         //   Mailer.sendMail("Canadian Order Placed On Hold (OWD Ref:" + order.orderNum + ")", "Order held at OWD due to blocked product shipping to Canada", "sales@ezultrasound.com", "donotreply@owd.com");



          //  CasetrackerAPI.createNewCaseForClient("Canadian Order Placed On Hold (OWD Ref:" + order.orderNum + ")",
          //          "Order held due to blocked product shipping to Canada - please notify customer and issue refund. Update client with status and order information.", order.clientID, null,CasetrackerAPI.getLoginToken("owditadmin@owd.com", "torque"));
        }


        } catch (Exception
                ex) {
            ex.printStackTrace();

            //   log.debug("adding to response " + o.getOrderRef() + ", " + ex.getMessage());
            //   response.add(o.getOrderRef() + ", " + ex.getMessage());

        } finally {


        }
    }

    public static orderInfoBean loadOrder(TransactionType trans, List<String> skus) throws Exception {

        orderInfoBean o = new orderInfoBean();


        List<lineItemBean> lines = new ArrayList();

         String transactionID = trans.getTransactionID();
        String itemReference = "";
        if(transactionID.length()>1)
        {

            itemReference = trans.getItem().getItemID()+"-"+transactionID;
        }   else
        {
            itemReference = trans.getItem().getItemID();
        }

        o.setOrderRef(itemReference);

        try {
            o.setTax(trans.getShippingDetails().getSalesTax().getSalesTaxAmount().getValue() + "");
        } catch (NullPointerException npex) {
            //presume no tax
        }

        double sh = trans.getShippingServiceSelected().getShippingServiceCost().getValue();
          log.debug("GOT SHIPPING loadOrder(Ot) = "+sh);
        try {
            log.debug("Ship Details:" + trans.getShippingDetails());
            if (trans.getShippingDetails().getInsuranceOption().toString().equalsIgnoreCase("Required")

                    || trans.getShippingDetails().isInsuranceWanted()) {
                //  log.debug("Adding insurance of "+trans.getShippingServiceSelected().getShippingInsuranceCost()
                //  +" to current shipping cost "+sh);
                sh += trans.getShippingServiceSelected().getShippingInsuranceCost().getValue();
                o.setInsuranceRequested("TRUE");
            }
        } catch (NullPointerException npex) {
            //ignore
        }
        o.setShippingandHandling(sh + "");


        if(trans.getBuyer().getBuyerInfo().getShippingAddress().getName().trim().indexOf(" ")>0)
        {
        o.setBillingLastName(trans.getBuyer().getBuyerInfo().getShippingAddress().getName().trim().substring(trans.getBuyer().getBuyerInfo().getShippingAddress().getName().trim().indexOf(" ") + 1));


        o.setBillingFirstName(trans.getBuyer().getBuyerInfo().getShippingAddress().getName().trim().substring(0, trans.getBuyer().getBuyerInfo().getShippingAddress().getName().trim().indexOf(" ")));
        }else
        {
            o.setBillingLastName("");
            o.setBillingFirstName(trans.getBuyer().getBuyerInfo().getShippingAddress().getName().trim());

        }

        o.setBillingCompany(trans.getBuyer().getBuyerInfo().getShippingAddress().getCompanyName());

        o.setBillingAddress1(trans.getBuyer().getBuyerInfo().getShippingAddress().getStreet1());

        o.setBillingAddress2(trans.getBuyer().getBuyerInfo().getShippingAddress().getStreet2());

        o.setBillingCity(trans.getBuyer().getBuyerInfo().getShippingAddress().getCityName());

        o.setBillingState(trans.getBuyer().getBuyerInfo().getShippingAddress().getStateOrProvince());

        o.setBillingZip(trans.getBuyer().getBuyerInfo().getShippingAddress().getPostalCode());

        o.setBillingCountry(getCountry(trans.getBuyer().getBuyerInfo().getShippingAddress().getCountry() + ""));

        o.setBillingPhone1(trans.getBuyer().getBuyerInfo().getShippingAddress().getPhone());


        o.setBillingEmail(trans.getBuyer().getEmail());

          log.debug("Looking up " + trans.getShippingServiceSelected().getShippingService().toString());
        o.setShippingMethod(methodMap.get(trans.getShippingServiceSelected().getShippingService().toString()));

        boolean leadSKU = true;
        for (String sku : skus) {
            lineItemBean lb = new lineItemBean();
            lb.setInventory_num(sku.trim());
            lb.setQuanity(trans.getQuantityPurchased() + "");

            log.debug("LINEITEM PRICE (tt) = "+trans.getConvertedTransactionPrice().getValue());

            lb.setPrice(leadSKU ? trans.getConvertedTransactionPrice().getValue() + "" : "0.00");

            lb.setDesc("(" + trans.getItem().getItemID() + ") " + trans.getItem().getTitle());

            lines.add(lb);
            leadSKU = false;
        }

        o.setItems(lines);

        //   log.debug("here is what we have");
        //    o.printAll();
        return o;
    }

    public static orderInfoBean loadOrder(OrderType trans) throws Exception {
        String s;
        orderInfoBean o = new orderInfoBean();


        List<lineItemBean> lines = new ArrayList();

        String itemReference = "Combined-"+trans.getOrderID().toString();

        o.setOrderRef(itemReference);
        log.debug("loading multiitem order order ref "+itemReference);
        try {
            o.setTax(trans.getShippingDetails().getSalesTax().getSalesTaxAmount().getValue() + "");
        } catch (NullPointerException npex) {
            //presume no tax
        }

        double sh = trans.getShippingServiceSelected().getShippingServiceCost().getValue();
        log.debug("GOT SHIPPING loadOrder(Ot) = "+sh);
        try {
            // log.debug("Ship Details:"+trans.getShippingDetails());
            if (trans.getShippingDetails().getInsuranceOption().toString().equalsIgnoreCase("Required")

                    || trans.getShippingDetails().isInsuranceWanted()) {
                //  log.debug("Adding insurance of "+trans.getShippingServiceSelected().getShippingInsuranceCost()
                //  +" to current shipping cost "+sh);
                sh += trans.getShippingServiceSelected().getShippingInsuranceCost().getValue();
                o.setInsuranceRequested("TRUE");
            }
        } catch (NullPointerException npex) {
            //ignore
        }
        o.setShippingandHandling(sh + "");

        if(trans.getShippingAddress().getName().trim().indexOf(" ")>0)
               {
               o.setBillingLastName(trans.getShippingAddress().getName().trim().substring(trans.getShippingAddress().getName().trim().indexOf(" ") + 1));


               o.setBillingFirstName(trans.getShippingAddress().getName().trim().substring(0, trans.getShippingAddress().getName().trim().indexOf(" ")));
               }else
               {
                   o.setBillingLastName("");
                   o.setBillingFirstName(trans.getShippingAddress().getName().trim());

               }


        o.setBillingLastName(trans.getShippingAddress().getName().substring(trans.getShippingAddress().getName().indexOf(" ") + 1));

        o.setBillingFirstName(trans.getShippingAddress().getName().substring(0, trans.getShippingAddress().getName().indexOf(" ")));


        o.setBillingCompany(trans.getShippingAddress().getCompanyName());

        o.setBillingAddress1(trans.getShippingAddress().getStreet1());

        o.setBillingAddress2(trans.getShippingAddress().getStreet2());

        o.setBillingCity(trans.getShippingAddress().getCityName());

        o.setBillingState(trans.getShippingAddress().getStateOrProvince());

        o.setBillingZip(trans.getShippingAddress().getPostalCode());

        o.setBillingCountry(getCountry(trans.getShippingAddress().getCountry() + ""));

        o.setBillingPhone1(trans.getShippingAddress().getPhone());


        o.setBillingEmail(trans.getTransactionArray().getTransaction(0).getBuyer().getEmail());

        log.debug("Looking up " + trans.getShippingServiceSelected().getShippingService().toString());
        o.setShippingMethod(methodMap.get(trans.getShippingServiceSelected().getShippingService().toString()));



        for (int i = 0; i < trans.getTransactionArray().getTransaction().length; i++) {
            TransactionType tt = trans.getTransactionArray().getTransaction()[i];

            log.debug("multiitem order item: "+tt.getItem().getItemID());
            boolean leadSKU = true;
            Map<String, String> skus = getSkuAndTitleMapFromEbay(tt.getItem().getItemID());
            for (String sku : skus.keySet()) {
                lineItemBean lb = new lineItemBean();
                lb.setInventory_num(sku.trim());
                lb.setQuanity(tt.getQuantityPurchased() + "");

                log.debug("LINEITEM PRICE (tt) = "+tt.getTransactionPrice().getValue());

                lb.setPrice(leadSKU ? tt.getTransactionPrice().getValue() + "" : "0.00");

                lb.setDesc("(" + tt.getItem().getItemID() + ") " + skus.get(sku));

                lines.add(lb);
                leadSKU = false;
            }
        }

        o.setItems(lines);

        //   log.debug("here is what we have");
        //   o.printAll();
        return o;
    }

    public static String getCountry(String s) throws Exception {
        if (!CountryNames.exists(s)) {
            return "USA";
            //throw new Exception("Country name not recognized: " + s);
        }
        //   log.debug("converting " + s + " to " + CountryNames.getCountryName(s));
        return CountryNames.getCountryName(s);


    }


    static {
        methodMap = new TreeMap();
        methodMap.put("Delivery", "Picked Up");
        methodMap.put("NotSelected", "NotSelected");
        methodMap.put("ExpeditedInternational", "UPS Worldwide Expedited");
        methodMap.put("Freight", "LTL");
        methodMap.put("FreightShipping", "LTL");
        methodMap.put("FreightShippingInternational", "LTL");
        methodMap.put("LocalDelivery", "Picked Up");
        methodMap.put("Other", "Picked Up");
        methodMap.put("OtherInternational", "Picked Up");
        methodMap.put("Pickup", "Picked Up");
        methodMap.put("PromotionalShippingMethod", "USPS Priority Mail");
        methodMap.put("ShippingMethodExpress", "USPS Priority Mail");
        methodMap.put("ShippingMethodOvernight", "USPS Priority Mail Express");
        methodMap.put("ShippingMethodStandard", "USPS Priority Mail");
        methodMap.put("StandardInternational", "USPS Priority Mail International");
        methodMap.put("UPS2DayAirAM", "UPS 2nd Day Air");
        methodMap.put("UPS2ndDay", "UPS 2nd Day Air");
        methodMap.put("UPS3rdDay", "UPS 3 Day Select");
        methodMap.put("UPSGround", "UPS Ground");
        methodMap.put("UPSNextDay", "UPS Next Day Air Saver");
        methodMap.put("UPSNextDayAir", "UPS Next Day Air Saver");
        methodMap.put("UPSStandardToCanada", "UPS Standard Canada");
        methodMap.put("UPSWorldWideExpedited", "UPS Worldwide Expedited");
        methodMap.put("UPSWorldWideExpress", "UPS Worldwide Express");
        methodMap.put("UPSWorldWideExpressBox10kg", "UPS Worldwide Express");
        methodMap.put("UPSWorldWideExpressBox25kg", "UPS Worldwide Express");
        methodMap.put("UPSWorldWideExpressPlus", "UPS Worldwide Express Plus");
        methodMap.put("UPSWorldWideExpressPlusBox10kg", "UPS Worldwide Express Plus");
        methodMap.put("UPSWorldWideExpressPlusBox25kg", "UPS Worldwide Express Plus");
        methodMap.put("USPSAirmailLetter", "USPS First-Class Mail International");
        methodMap.put("USPSAirmailParcel", "USPS Priority Mail International");
        methodMap.put("USPSEconomyLetter", "USPS First-Class Mail International");
        methodMap.put("USPSEconomyParcel", "USPS Priority Mail International");
        methodMap.put("USPSExpressFlatRateEnvelope", "USPS Priority Mail Express");
        methodMap.put("USPSExpressMail", "USPS Priority Mail Express");
        methodMap.put("USPSExpressMailInternational", "USPS Priority Mail Express International");
        methodMap.put("USPSExpressMailInternationalFlatRateEnvelope", "USPS Priority Mail Express International");
        methodMap.put("USPSFirstClass", "USPS First-Class Mail");
        methodMap.put("USPSFirstClassLargeEnvelop", "USPS First-Class Mail");
        methodMap.put("USPSFirstClassLetter", "USPS First-Class Mail");
        methodMap.put("USPSFirstClassMailInternational", "USPS First-Class Mail International");
        methodMap.put("USPSFirstClassMailInternationalLargeEnvelope", "USPS First-Class Mail International");
        methodMap.put("USPSFirstClassMailInternationalLetter", "USPS First-Class Mail International");
        methodMap.put("USPSFirstClassMailInternationalParcel", "USPS First-Class Mail International");
        methodMap.put("USPSFirstClassParcel", "USPS First-Class Mail");
        methodMap.put("USPSGlobalExpress", "USPS Priority Mail Express International");
        methodMap.put("USPSGlobalExpressGuaranteed", "USPS Priority Mail Express International");
        methodMap.put("USPSGlobalPriority", "USPS Priority Mail International");
        methodMap.put("USPSGlobalPriorityLargeEnvelope", "USPS Priority Mail International");
        methodMap.put("USPSGlobalPrioritySmallEnvelope", "USPS Priority Mail International");
        methodMap.put("USPSGround", "USPS Parcel Select Nonpresort");
        methodMap.put("USPSMedia", "USPS Media Mail Single-Piece");
        methodMap.put("USPSParcel", "USPS Parcel Select Nonpresort");
        methodMap.put("USPSPriority", "USPS Priority Mail");
        methodMap.put("USPSPriorityFlatRateBox", "USPS Priority Mail");
        methodMap.put("USPSPriorityFlatRateEnvelope", "USPS Priority Mail");
        methodMap.put("USPSPriorityMailInternational", "USPS Priority Mail International");
        methodMap.put("USPSPriorityMailInternationalFlatRateBox", "USPS Priority Mail International");
        methodMap.put("USPSPriorityMailInternationalFlatRateEnvelope", "USPS Priority Mail International");
        methodMap.put("USPSPriorityMailInternationalLargeFlatRateBox", "USPS Priority Mail International");
        methodMap.put("USPSPriorityMailLargeFlatRateBox", "USPS Priority Mail");
        methodMap.put("FedEx2Day","FedEx 2Day");
        methodMap.put("FedExExpressSaver","FedEx Express Saver");
        methodMap.put("FedExGround","FedEx Ground");
        methodMap.put("FedExHomeDelivery","FedEx Home Delivery");
        methodMap.put("FedExInternationalEconomy","FedEx International Economy");
        methodMap.put("FedExInternationalFirst","FedEx International Priority");
        methodMap.put("FedExInternationalGround","FedEx Ground");
        methodMap.put("FedExInternationalPriority","FedEx International Priority");
        methodMap.put("FedExPriorityOvernight","FedEx Priority Overnight");
        methodMap.put("FedExStandardOvernight","FedEx Standard Overnight");
    }




    private static List<String> getSkuFromEbay(String s) {
        List<String> sku = new ArrayList<String>();
        try {
            Http h = new Http();
                      h.setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());
            log.debug("trying to get SKUs for " + s);
            h.get(getOrderDownloadUrl(s));
            byte[] b = h.getTransferredData();

            String xdata = new String(b);
                log.debug(xdata);
            String data = xdata.replaceAll("\\<.*?>","");
          //      log.debug(data);

            Matcher match = skuFind.matcher(data);
            match.find();
            String found = match.group().trim();
                   log.debug("["+found+"]");
            found = found.replaceAll("\\|",",");
            if (found.contains(",")) {
                String[] st = found.split(",");
                sku.addAll(Arrays.asList(st));

            } else {
                sku.add(found);
            }

        } catch (Exception e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return sku;
    }

    private static Map<String, String> getSkuAndTitleMapFromEbay(String s) {
        Map<String, String> sku = new HashMap<String, String>();
        try {
            Http h = new Http();
           //    h.addHttpEventListener(new EastCoastEBayImportJob());
            h.setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());
            log.debug("trying to get SKUs for " + s);
            h.get(getOrderDownloadUrl(s));
          //  log.debug("HTTP HEADERS "+h.getTransferredHeaders());
            byte[] b = h.getTransferredData();
            log.debug("Http returned size="+b.length);
         //   380152433129


            String data = new String(b);

                  h.get(getOrderItemTitle(s));
          //  log.debug("HTTP HEADERS "+h.getTransferredHeaders());
            byte[] b2 = h.getTransferredData();
            log.debug("Http returned size="+b2.length);
         //   380152433129


            String data2 = new String(b2);

          //  log.debug("***DATA>");
          //  log.debug(data);

          //  log.debug("<DATA***");
            sku = getSKUsFromText(data, data2);

        } catch (Exception e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return sku;
    }

    private static Map<String, String> getSKUsFromText(String xdata, String titleData) {
        Map<String, String> sku = new HashMap<String, String>();

                log.debug(xdata);
            String data = xdata.replaceAll("\\<.*?>","");
                log.debug(data);
        Matcher match = skuFind.matcher(data);
        match.find();
        String found = match.group().trim();
               log.debug("found="+found);

        String title = titleData.substring(titleData.indexOf("Item.Title=") + 11);
        title = title.substring(0, title.indexOf("&"));
        found = found.replaceAll("\\|",",");
        if (found.contains(",")) {
            String[] st = found.split(",");
            for (String aSt : st) {
                sku.put(aSt, title);
            }


        } else {
            sku.put(found, title);
        }

        return sku;
    }


    private void getLast2DaysTransactions() {
        try {

            // set devId, appId, certId in ApiAccount
            /*
            Key Set 1
DevID    b3282552-0dd6-4a86-9e76-c2eeaf0fbf21

AppID   OneWorld-ba62-4b2c-ab97-9bf9cbb06f14

CertID   01123f83-6737-4344-9373-92c5169b6a1e

Token for East Cost Trading (Andrew Boyce):
AgAAAA**AQAAAA**aAAAAA**wYl/SA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wJlYKoDZWCpwudj6x9nY+seQ**yHEAAA**AAMAAA**ZqT49bYYM8UTVyrHuQ7DcYbHASZLbHz4/+KjBW1rq/mdOjQ/LZcfUFWyr2sHI9+ORfAUdDBxkLD+pec3jfN5vnO18uNM/EJWAgZHSpAiAqZGZSw/x4jTeS70bdzAzatQREppTgy+pwu/RLspN3ccx28zPRgEu9Gq4IIsI4ntLDACU8qey968c/XOuP4M7hT6K0DpRmLNSDLaLD8xz3B9CcMwUBhdM2ckigktXbpiQFyZUOYg/evA2iYUIwjFNn/1v3PdiODtg28toJdH/eHKk5JwbCXIDzBMDoHoQabQPg0d7l6B6q2ajaz1IeHw4kX0o50TB0GW5bUAnLPa/GSI1tTg01YykCxJ9jBXjHcpDYKSdkz9XSQjR5r3b0dB7MLjpch7zYMbO9rqDdCEZVLMhE5LGUO2h3fGneWlvmHGoiofx3dGhwV+8F32rK6yKrYMgl47M+MkAsNA/lvekshoOHtgYRcqsYtZZ29SiRPZNIE5Wv0Z4fEtieugijH0KHN14uBiqrcFeXf8KoCibbzDzo6q5M4Ncv1qFw0qzkIF70K92ukonG411FY19RpKAco3ZVsxntThWzi9ISaVNpWVBTqqPlZgMB+P350IsLSsicbwlP2423Z54S9O8ligFn0DUlgaO7DTpNCUUu9s/LpLQW4OC8sLGq2DwM27AzFzRNy9U3Drqw7Ux7PdSrhVP+/C0XrQ+0I/7QeZ2ebgUOBlOPriTBNOYH/D/raEnLHuJRsZNXP/U/2CKzGxKKla2xU3

REST token:
%2FOYhGFbI3%2BI%3D**ARzzy%2BicZRvAll8EPTDQjV8hADU%3D

Tokens expire 1-1-2010

XML API Production Gateway URI
https://api.ebay.com/ws/api.dll
SOAP API Production Gateway URI
https://api.ebay.com/wsapi               b3282552-0dd6-4a86-9e76-c2eeaf0fbf21
             */

            log.debug("getting account");
            ApiAccount account = new ApiAccount();
            account.setDeveloper(ebLogin);
            account.setApplication(ebApp);
            account.setCertificate(ebCert);

// set ApiAccount and token in ApiCredential
            ApiCredential credential = new ApiCredential();
            credential.setApiAccount(account);
            credential.seteBayToken(ebToken);        log.debug("set credential");
            ApiContext context = new ApiContext();
            context.setApiCredential(credential);
            ApiLogging logging = new ApiLogging();
        //    logging.setEnableLogging(false);
            logging.setLogSOAPMessages(false);
            logging.setLogExceptions(false);
            context.setApiLogging(logging);

            //     ApiCall call = new ApiCall( context );

// set eBay server URL to call
            context.setApiServerUrl("https://api.ebay.com/wsapi");  // sandbox

// set timeout in milliseconds - 3 minutes
            context.setTimeout(180000);

// set wsdl version number
            context.setWSDLVersion("639");

// turn on logging
            int currentPage = 1;
            int pageCount = 1;
            log.debug("trying page "+currentPage);
// create soap api request and response objects
            while (pageCount >= currentPage) {
                GetSellerTransactionsCall api = new GetSellerTransactionsCall(context);

                DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[]{
                        DetailLevelCodeType.RETURN_ALL
                };
                api.setDetailLevel(detailLevels);
                api.setIncludeContainingOrder(true);

                PaginationType pt = new PaginationType();

                pt.setEntriesPerPage(100);
                pt.setPageNumber(currentPage);
                api.setPagination(pt);

                Calendar twoDaysAgo = Calendar.getInstance();
                twoDaysAgo.add(Calendar.DATE, -21);


            //    twoDaysAgo.add(Calendar.DATE, -7);
                Calendar today = Calendar.getInstance();
             //   today.add(Calendar.DATE, -10);

                TimeFilter tf = new TimeFilter(twoDaysAgo, today);

                List<String> orderIDs = new ArrayList<String>();
                log.debug("got order ids "+orderIDs.size());
                try {

                     TransactionType[] transList = api.getSellerTransactions(tf);
                    if(transList==null)
                    {
                       transList = new TransactionType[]{};
                    }
                    if (api.getHasMoreTransactions()) {
                        pageCount = api.getPaginationResult().getTotalNumberOfPages();
                    }

                    for (TransactionType trans : transList) {
                        try {
                            log.debug("got a trans");
                            log.debug("Transaction ID:"+trans.getTransactionID() );
                            log.debug("Transaction Status:"+trans.getStatus().getCompleteStatus().toString() );

                            if (trans.getStatus().getCompleteStatus().toString().equalsIgnoreCase("Complete")) {
                                if (trans.getContainingOrder() != null) {
                                    log.debug("ORDER ID:" + trans.getContainingOrder().getOrderID() + " (" + trans.getContainingOrder().getOrderStatus() + ")");
                                    if (!(orderIDs.contains(trans.getContainingOrder().getOrderID()))) {
                                        orderIDs.add(trans.getContainingOrder().getOrderID());
                                    }
                                } else {
                                    List skus = getSkuFromEbay(trans.getItem().getItemID());
                                    log.debug("got SKUs:"+skus);
                                    if (skus.size() > 0) {
                                        log.debug("processing order");
                                        processOrder(trans, skus);
                                    }
                                }
                            }else
                            {
                                log.debug("incomplete order, status="+trans.getStatus().getCompleteStatus().toString());
                            }
                        } catch (DuplicateOrderException ex) {
                            log.debug("dupe");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                } catch (ApiException ae) {
                    log.debug(ae);
                } catch (SdkSoapException sse) {
                    log.debug(sse);
                } catch (SdkException se) {
                    log.debug(se);
                }

                if (orderIDs.size() > 0) {
                    GetOrdersCall apio = new GetOrdersCall(context);

                    DetailLevelCodeType[] detailLevelso = new DetailLevelCodeType[]{
                            DetailLevelCodeType.RETURN_ALL,
                    };
                    OrderIDArrayType oidArray = new OrderIDArrayType();
                    String[] ordersArray = new String[0];
                    ordersArray = orderIDs.toArray(ordersArray);
                    oidArray.setOrderID(orderIDs.toArray(ordersArray));
                    apio.setOrderIDArray(oidArray);

                    apio.setDetailLevel(detailLevelso);

                    try {

                        final OrderType[] transList = apio.getOrders();

                        for (OrderType trans : transList) {
                            try {
                                processOrder(trans);
                            } catch (DuplicateOrderException ex) {
                                log.debug("dupe");      //ignore it
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    } catch (ApiException ae) {
                        log.debug(ae);
                    } catch (SdkSoapException sse) {
                        log.debug(sse);
                    } catch (SdkException se) {
                        log.debug(se);
                    }

                }
                currentPage++;
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


    public static void sendTermsEmail(Order order)
    {
        String subject = "EZUltrasound Order #"+order.order_refnum+" Terms of Sale Agreement";

        String emailText = "\n" +
                "Dear "+order.getBillingContact().getName()+",\n" +
                "\n" +
                "Below are the Terms of Sale for your new device.\n" +
                "\n" +
                "Accepting your purchase will constitute agreement with these terms.\n" +
                "\n" +
                "If you do not agree with the Terms of Sale please immediately call 1-888-772-4047 to receive a full refund.\n" +
                "\n" +
                "Sincerely,\n" +
                "\n" +
                "The EZUltrasound Team\n" +
                "\n" +
                "--------------------------------------------------------\n" +
                "Terms of Sale:\n" +
                "\n" +
                "1. Buyer has read and agrees to the Shipping, Return, and Privacy policies. You can review these policies at the following web links:\n\n"+
                "Shipping: http://www.ezultrasound.com/shipping-policy.aspx\n" +
                "Returns: http://www.ezultrasound.com/return-policy.aspx\n" +
                "Privacy: http://www.ezultrasound.com/privacy-policy.aspx\n" +
                "\n" +
                "2. Buyer certifies that ultrasound machine will be used only as specified by the manufacturer.\n" +
                "\n" +
                "4. Buyer or customer certifies that he/she has read EZUltrasound's FAQ's page on proper ultrasound machine operation and usage at http://www.ezultrasound.com/faqs.aspx.\n" +
                "\n" +
                "5. Buyer indemnifies & holds harmless Mountain Valley Enterprises, LLC dba EZUltrasound, its managers, owners & affiliates from any possible injury/ harm from use of any products sold by or through EZUltrasound.\n" +
                "\n" +
                "6. Buyer certifies machine is intended to be used only on buyer and buyer is not 18 years old or younger, has a pacemaker, poor circulation or is pregnant. ";


        log.debug("Subject: "+subject);
        log.debug("Body: "+emailText);
    }

 }
