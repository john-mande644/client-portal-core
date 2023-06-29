package com.owd.jobs.archives;

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
import ipworks.Http;

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
public class GymnasticBodiesEBayImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    static final Map<String, String> methodMap;

    static final Pattern skuFind = Pattern.compile("(?<=\\(SKU: {0,1})[[a-zA-Z_0-9\\.]- /|,]*(?=\\))");

    static final String goodStatusString = "CompleteCheckoutCompleteNoPaymentFailureNonePayPal";


    public static final String clientID = "411";
    
    // static boolean singleDVD = false;
      public static void main(String[] args) {

       run();

     //   log.debug(getSkuFromEbay("270402577024"));
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
            account.setDeveloper("81cd5f0b-e52f-400c-b1d1-4a23da062486");
            account.setApplication("OneWorld-917f-429a-a054-4fff4277d407");
            account.setCertificate("83c2e71e-4a14-477a-9f18-df55426a769d");

// set ApiAccount and token in ApiCredential
            ApiCredential credential = new ApiCredential();
            credential.setApiAccount(account);
            credential.seteBayToken("AgAAAA**AQAAAA**aAAAAA**ALflSg**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wAmIOlDZmHoA+dj6x9nY+seQ**tbAAAA**AAMAAA**hdcsRmAi+ro/gZeX6h1VcQIcAT1PNWtC3rqPCnIPj5Dk3xvGUfIDqGNE+1azZH/SIf6UNjWcgAzd74S8l3hyPNpD1RpwtruUPZNTayY+3KU3Xhqzym0GewtgC7Ufd0cCVfARxCwFEhC+07i4ZFfe4SuYjZmdH8kraTM7yVUGsIZ9hlMtifVkasWtr4IGFJWXqUNE0wsGyOh1jUytQZZpr3s4KlptxefMn3bY7QgB0NqGjwmE2i2oNKv1ivNw3kc/LF9tUMwbr4Fjywrb/e2U7O0qe4eaZRdYO9hHbP8X+aWO+RKhlKMr4JZdRdkPDABflz4i8X2V/8tsZ92uSnqPz4NGmue3vf0B7WKUcbBnmKc2zXD5NABBZGU7B+7Ph2Dv60emV2fqQIDoM+eoV37Frd64D1zG0EudUwgJQKqLoU4GcG1los77QDuU0DK29iIWz8aBTUZxkE0FmgUXHyd4mz5awkLgFeT2XRJ7VM71BdfICtzvqM7ZBoAhzKf7pP0UF9iFhBm07QYXf67VC7ytdZkV3IjXjtLb5OJhB2TuBKXc1IAOtqKP4RbfBACl8OYAwRqUOBS2KtVji0mi4b++fhDj02OdXmFnF39wvVN08UB3EEkcslTKYL5lG8FXoSAhDYOCA+g7iB/qB6CzVpMsUb4qBEyyiC7TgY+K1iukBhvV7oZbw9EcqJKwEjfVkdpnfVjzIt2Zpw3iLME1Q+mVxNxb/cE54/AAzkF1NkrRdg6pSkaQAimjA+G48/itvBlM");
// add ApiCredential to ApiContext
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
                twoDaysAgo.add(Calendar.DATE, -29);
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
                            if (trans.getStatus().getCompleteStatus().toString().equals("Complete")) {
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

            getLast2DaysTransactions();

        } catch (Exception ex) {

            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            Mailer.postMailMessage("East Coast EBay import error import error", sb.toString(), "casetracker@owd.com", "noop@owd.com");
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
                Vector osVec = OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderClientReference,"Combined-"+trans.getOrderID().toString(), clientID, false);


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
            order.backorder_rule = OrderXMLDoc.kBackOrderAll;


            log.debug("before loadorder");
            //load order bean from buffered reader
            o = loadOrder(trans);
            // Check for blank shipping and copy billing over
            if (null == o.getShippingFirstName() || o.getShippingFirstName().length() < 1) {
                log.debug("Copying Bill Address to ship Address");
                o.copyBillToShip();
            }


            o.loadOwdOrder(order);


            order.recalculateBalance();
            order.paid_amount = order.total_order_cost;
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.is_paid = 1;
            order.order_type = "EBay Listing - Combined Order";



             log.debug("got pay status:"+payStatus);
            if("CompleteNoPaymentFailurePayPal".equals(payStatus))
            {

            order.is_future_ship = 0;
            order.po_num = "EBay payment confirmed";
            }else{

            order.is_future_ship = 1;
            order.po_num = "Paypal Unverified";
              }
             if("USPS First-Class Mail International".equals(o.getShippingMethod()))
        {
            //calculate insurance
            float subtotal = order.total_product_cost;
            if(subtotal<130.00)
            {

            }
        }
            policy.setSignatureRequired(order);
            policy.saveNewOrder(order,false);

            String reference = order.order_refnum;

            log.debug(reference);
            if (reference == null) {
                if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1) {
                    throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                }
            }


        } catch (Exception
                ex) {
            ex.printStackTrace();

            log.debug(o.getOrderRef() + ", " + ex.getMessage());

        } finally {


        }
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

                 String payStatus = trans.getStatus().getCompleteStatus().toString()
                             +trans.getStatus().getCheckoutStatus()
                             +trans.getStatus().getEBayPaymentStatus()
                             +trans.getStatus().getPaymentHoldStatus()
                             +trans.getStatus().getPaymentMethodUsed();
            log.debug("got pay status:"+payStatus);


        //add new
        if (OrderUtilities.orderRefnumExists(itemReference, clientID)) {
            Vector osVec = OrderStatus.getOrderStatusByKey(OrderStatus.kByOrderClientReference,itemReference, clientID, false);
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
            order.backorder_rule = OrderXMLDoc.kBackOrderAll;

            //load order bean from buffered reader
            log.debug("loading order");
            o = loadOrder(trans, skus);
            // Check for blank shipping and copy billing over
            if (null == o.getShippingFirstName() || o.getShippingFirstName().length() < 1) {
                o.copyBillToShip();
            }

            log.debug("loading owd order");
            o.loadOwdOrder(order);


            order.recalculateBalance();
            order.paid_amount = order.total_order_cost;
            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
            order.is_paid = 1;
            order.order_type = "EBay Listing";


            if(goodStatusString.equals(payStatus))
            {

            order.is_future_ship = 0;
            order.po_num = "EBay payment confirmed";
            }    else
            {
            order.is_future_ship = 1;
            order.po_num = "Paypal Unverified";

            }
            ClientPolicy policy = Client.getClientForID(clientID).getClientPolicy();
              policy.setSignatureRequired(order);
            policy.saveNewOrder(order,false);
           // Client.getClientForID(clientID).getClientPolicy().saveNewOrder(order,false);
            String reference = order.order_refnum;


            log.debug("Client Order Ref: "+reference);
            if (reference == null) {
                if ((order.last_payment_error + " " + order.last_error).indexOf("reference already exists") < 1) {
                    throw new Exception("The order could not be completed, most likely because " + order.last_payment_error + " " + order.last_error + ".");
                }
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
            o.setTax(trans.getShippingDetails().getSalesTax().getSalesTaxAmount() + "");
        } catch (NullPointerException npex) {
            //presume no tax
        }

        double sh = trans.getShippingServiceSelected().getShippingServiceCost().getValue();

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


            lb.setPrice(leadSKU ? trans.getConvertedTransactionPrice() + "" : "0.00");

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
            o.setTax(trans.getShippingDetails().getSalesTax().getSalesTaxAmount() + "");
        } catch (NullPointerException npex) {
            //presume no tax
        }

        double sh = trans.getShippingServiceSelected().getShippingServiceCost().getValue();

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


                lb.setPrice(leadSKU ? tt.getTransactionPrice() + "" : "0.00");

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
            String orderDownloadUrl = "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=NV&appid=OneWorld-917f-429a-a054-4fff4277d407&siteid=0&version=515&ItemID=" + s + "&IncludeSelector=Description";
            h.setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());
            log.debug("trying to get SKUs for " + s);
            h.get(orderDownloadUrl);
            byte[] b = h.getTransferredData();

            String xdata = new String(b);
                         log.debug(xdata);
                     String data = xdata.replaceAll("\\<.*?>","");
                         log.debug(data);

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
            String orderDownloadUrl = "http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=NV&appid=OneWorld-917f-429a-a054-4fff4277d407&siteid=0&version=515&ItemID=" + s + "&IncludeSelector=Description";
            h.setRuntimeLicense(OWDUtilities.getIPWorksRuntimeLicense());
            log.debug("trying to get SKUs for " + s);
            h.get(orderDownloadUrl);
            byte[] b = h.getTransferredData();

            String data = new String(b);
          //  log.debug(data);
            sku = getSKUsFromText(data);

        } catch (Exception e) {
              e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return sku;
    }

    private static Map<String, String> getSKUsFromText(String xdata) {
        Map<String, String> sku = new HashMap<String, String>();
                     log.debug(xdata);
                 String data = xdata.replaceAll("\\<.*?>","");
                     log.debug(data);

        Matcher match = skuFind.matcher(data);
        match.find();
        String found = match.group().trim();
               log.debug("found="+found);

        String title = data.substring(data.indexOf("Item.Title=") + 11);
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
https://api.ebay.com/wsapi
             */


            ApiAccount account = new ApiAccount();
                      account.setDeveloper("81cd5f0b-e52f-400c-b1d1-4a23da062486");
                      account.setApplication("OneWorld-917f-429a-a054-4fff4277d407");
                      account.setCertificate("83c2e71e-4a14-477a-9f18-df55426a769d");

// set ApiAccount and token in ApiCredential
                      ApiCredential credential = new ApiCredential();
                      credential.setApiAccount(account);
            credential.seteBayToken("AgAAAA**AQAAAA**aAAAAA**ALflSg**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wAmIOlDZmHoA+dj6x9nY+seQ**tbAAAA**AAMAAA**hdcsRmAi+ro/gZeX6h1VcQIcAT1PNWtC3rqPCnIPj5Dk3xvGUfIDqGNE+1azZH/SIf6UNjWcgAzd74S8l3hyPNpD1RpwtruUPZNTayY+3KU3Xhqzym0GewtgC7Ufd0cCVfARxCwFEhC+07i4ZFfe4SuYjZmdH8kraTM7yVUGsIZ9hlMtifVkasWtr4IGFJWXqUNE0wsGyOh1jUytQZZpr3s4KlptxefMn3bY7QgB0NqGjwmE2i2oNKv1ivNw3kc/LF9tUMwbr4Fjywrb/e2U7O0qe4eaZRdYO9hHbP8X+aWO+RKhlKMr4JZdRdkPDABflz4i8X2V/8tsZ92uSnqPz4NGmue3vf0B7WKUcbBnmKc2zXD5NABBZGU7B+7Ph2Dv60emV2fqQIDoM+eoV37Frd64D1zG0EudUwgJQKqLoU4GcG1los77QDuU0DK29iIWz8aBTUZxkE0FmgUXHyd4mz5awkLgFeT2XRJ7VM71BdfICtzvqM7ZBoAhzKf7pP0UF9iFhBm07QYXf67VC7ytdZkV3IjXjtLb5OJhB2TuBKXc1IAOtqKP4RbfBACl8OYAwRqUOBS2KtVji0mi4b++fhDj02OdXmFnF39wvVN08UB3EEkcslTKYL5lG8FXoSAhDYOCA+g7iB/qB6CzVpMsUb4qBEyyiC7TgY+K1iukBhvV7oZbw9EcqJKwEjfVkdpnfVjzIt2Zpw3iLME1Q+mVxNxb/cE54/AAzkF1NkrRdg6pSkaQAimjA+G48/itvBlM");
// add ApiCredential to ApiContext
                       ApiContext context = new ApiContext();
            context.setApiCredential(credential);
            ApiLogging logging = new ApiLogging();
         //   logging.setEnableLogging(false);
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
                twoDaysAgo.add(Calendar.DATE, -29);


            //    twoDaysAgo.add(Calendar.DATE, -7);
                Calendar today = Calendar.getInstance();
             //   today.add(Calendar.DATE, -10);

                TimeFilter tf = new TimeFilter(twoDaysAgo, today);

                List<String> orderIDs = new ArrayList<String>();

                try {

                    final TransactionType[] transList = api.getSellerTransactions(tf);
                    if (api.getHasMoreTransactions()) {
                        pageCount = api.getPaginationResult().getTotalNumberOfPages();
                    }
                    if(transList==null)
                    {
                        throw new Exception("No transactions found");
                    }
                    for (TransactionType trans : transList) {
                        try {
                            if (trans.getStatus().getCompleteStatus().toString().equals("Complete")) {
                                if (trans.getContainingOrder() != null) {
                                    log.debug("ORDER ID:" + trans.getContainingOrder().getOrderID() + " (" + trans.getContainingOrder().getOrderStatus() + ")");
                                    if (!(orderIDs.contains(trans.getContainingOrder().getOrderID()))) {
                                        orderIDs.add(trans.getContainingOrder().getOrderID());
                                    }
                                } else {
                                    log.debug(trans.getTransactionID() );
                                    List skus = getSkuFromEbay(trans.getItem().getItemID());
                                    log.debug("got SKUs:"+skus);
                                    if (skus.size() > 0) {
                                        log.debug("processing order");
                                        processOrder(trans, skus);
                                    }
                                }
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

                    oidArray.setOrderID((String[])orderIDs.toArray());
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


     //   releasePaidOrders();
    }



    private void postTrackingInformation()
    {


            ApiAccount account = new ApiAccount();
                      account.setDeveloper("81cd5f0b-e52f-400c-b1d1-4a23da062486");
                      account.setApplication("OneWorld-917f-429a-a054-4fff4277d407");
                      account.setCertificate("83c2e71e-4a14-477a-9f18-df55426a769d");

// set ApiAccount and token in ApiCredential
                      ApiCredential credential = new ApiCredential();
                      credential.setApiAccount(account);
        credential.seteBayToken("AgAAAA**AQAAAA**aAAAAA**ALflSg**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wAmIOlDZmHoA+dj6x9nY+seQ**tbAAAA**AAMAAA**hdcsRmAi+ro/gZeX6h1VcQIcAT1PNWtC3rqPCnIPj5Dk3xvGUfIDqGNE+1azZH/SIf6UNjWcgAzd74S8l3hyPNpD1RpwtruUPZNTayY+3KU3Xhqzym0GewtgC7Ufd0cCVfARxCwFEhC+07i4ZFfe4SuYjZmdH8kraTM7yVUGsIZ9hlMtifVkasWtr4IGFJWXqUNE0wsGyOh1jUytQZZpr3s4KlptxefMn3bY7QgB0NqGjwmE2i2oNKv1ivNw3kc/LF9tUMwbr4Fjywrb/e2U7O0qe4eaZRdYO9hHbP8X+aWO+RKhlKMr4JZdRdkPDABflz4i8X2V/8tsZ92uSnqPz4NGmue3vf0B7WKUcbBnmKc2zXD5NABBZGU7B+7Ph2Dv60emV2fqQIDoM+eoV37Frd64D1zG0EudUwgJQKqLoU4GcG1los77QDuU0DK29iIWz8aBTUZxkE0FmgUXHyd4mz5awkLgFeT2XRJ7VM71BdfICtzvqM7ZBoAhzKf7pP0UF9iFhBm07QYXf67VC7ytdZkV3IjXjtLb5OJhB2TuBKXc1IAOtqKP4RbfBACl8OYAwRqUOBS2KtVji0mi4b++fhDj02OdXmFnF39wvVN08UB3EEkcslTKYL5lG8FXoSAhDYOCA+g7iB/qB6CzVpMsUb4qBEyyiC7TgY+K1iukBhvV7oZbw9EcqJKwEjfVkdpnfVjzIt2Zpw3iLME1Q+mVxNxb/cE54/AAzkF1NkrRdg6pSkaQAimjA+G48/itvBlM");
// add ApiCredential to ApiContext
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

// turn on logging
            int currentPage = 1;
            int pageCount = 1;

// create soap api request and response objects
            while (pageCount >= currentPage) {
                CompleteSaleCall api = new CompleteSaleCall(context);

               // api.setItemID();
              //  api.setTransactionID();
                api.setShipped(true);
                ShipmentType shipdata = new ShipmentType();
              //  shipdata.getShipmentTrackingNumber();
              //  shipdata.setShippingCarrierUsed(ShippingCarrierCodeType.USPS);
                api.setShipment(shipdata);

    }
    }
}
