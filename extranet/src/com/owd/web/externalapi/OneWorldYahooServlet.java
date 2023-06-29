package com.owd.web.externalapi;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.IANACountries;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.business.client.ClientFactory;
import com.owd.core.business.order.*;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.payment.FinancialTransaction;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.hibernate.engine.spi.SessionImplementor;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/* URL request model

    https://secure.owd.com/yahoo -> servlet path

        .../<clientid>/<clientauthstring>/type



    order request type = "neworder"

    inventory count type = "count"



    Examples:

              495:FEAwXtSGpEqzrqrXsOsbJQM=

               https://secure.owd.com/webapps/yahoo/495/FEAwXtSGpEqzrqrXsOsbJQM=/neworder.jsp
               
    https://secure.owd.com/webapps/yahoo/304/lyUVuBp5qFUzCx2SzqyPTQM/count.jsp

    https://secure.owd.com/webapps/yahoo/111/3gfdf4dgf32df32g4df=/count.jsp

    RT inventory format

All requests will be sent as POST requests with item information sent in <key>=<value> format as described below:
.catalog=<catalog name>
.code=<item code>
.id=<item id>
.quantity=<item quantity requested>
.price=<price of item>
Note the "." preceding each of the keys above. Also, each key-value pair will be separated by the ampersand (&) symbol. This list is followed by the item attribute list, if any.
When options are defined and selected for an item in the order, the key will take the name of the option (such as size or color) and the value is the option. For example:
Color=<Sea Green>
Size=<L>

The script should respond with the following header information:
Available: <item quantity available>
where <quantity> is the number of items available.
Use 0 (zero) to indicate non-availability. Use a negative number to indicate unknown availability.
Inventory-Message: <message>
where <message> is the text that a customer will see when they try to place an order for an item that isn't available.


*/

public class OneWorldYahooServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    public static final String kOrderCreationRequest = "neworder.jsp";

    public static final String kInventoryCountRequest = "count.jsp";

    //root node name

    public static final String kRootTag = "OrderList";

    //order node name

    public static final String kOrderNodeName = "Order";

    //order node attributes

    public static final String kOrderReference = "id";

    public static IANACountries countryCodes = new IANACountries();

    //shipto node name

    public static final String kAddressNodeName = "AddressInfo";

    public static final String kNameNodeName = "Name";

    public static final String kFirstNameNodeName = "First";

    public static final String kLastNameNodeName = "Last";

    public static final String kFullNameNodeName = "Full";

    public static final String kAddress1NodeName = "Address1";

    public static final String kAddress2NodeName = "Address2";

    public static final String kCityNodeName = "City";

    public static final String kStateNodeName = "State";

    public static final String kCountryNodeName = "Country";

    public static final String kZipNodeName = "Zip";

    public static final String kPhoneNodeName = "Phone";

    public static final String kEmailNodeName = "Email";

    //Address node attirubutes

    public static final String kAddressType = "type"; //"ship" or "bill"

    public static final String kAddressTypeValBilling = "bill";

    public static final String kAddressTypeValShipping = "ship";

    //shipping type node name

    public static final String kShippingNodeName = "Shipping";

    //comments node name

    public static final String kCommentNodeName = "Comments";

    //line items node name

    public static final String kItemsNodeName = "Item";

    public static final String kItemIDNodeName = "Id";

    public static final String kItemCodeNodeName = "Code";

    public static final String kItemQuantityNodeName = "Quantity";

    public static final String kItemUnitPriceNodeName = "Unit-Price";

    public static final String kItemDescriptionNodeName = "Description";

    public static final String kOptionsNodeName = "Option";

    //CC node name
    public static final String kCreditCardNodeName = "CreditCard";


    //CC node attributes
    public static final String kCardType = "type";
    public static final String kCardExpDate = "expiration";

    //CC Auth node name
    public static final String kCardEventsNodeName = "CardEvents";
    public static final String kCardAuthNodeName = "CardAuth";
    public static final String kCardEventNodeName = "CardEvent";

    public static final String kCardEventType = "type";
    //(Charge|Return|VoidCharge|VoidReturn|Capture|Refund|Void|ReAuth) #REQUIRED

    public static final String kCardEventAmount = "amount";

    public static final String kCardEventPaypalAuth = "paypal-auth";
    public static final String kCardEventPaypalTransId = "paypal-txid";

    //CC Auth node attributes

    public static final String kCardAuthAVSCode = "avs-response";
    public static final String kCardAuthAuthCode = "approval-number";
    public static final String kCardAuthCVVCode = "cvv-response";
    public static final String kCardAuthStatusCode = "auth-response";
    public static final String kCardAuthAuthAmount = "amount";

    public static final String kCardAuthPaypalAuth = "paypal-auth";
    public static final String kCardAuthPaypalPayer = "paypal-payer-status";
    public static final String kCardAuthPaypalAddress = "paypal-address-status";

    //totals node name

    public static final String kTotalsNodeName = "Total";

    public static final String kTotalsLineNodeName = "Line";

    //totals line node attributes

    public static final String kTotalsLineType = "type";

    public static final String kTotalGift = "GiftWrap";

    public static final String kTotalDiscount = "Discount";

    public static final String kTotalCoupon = "Coupon";

    public static final String kTotalSubtotal = "Subtotal";

    public static final String kTotalShipping = "Shipping";

    public static final String kTotalTax = "Tax";

    public static final String kTotalCredit = "Credit";

    public static final String kTotal = "Total";

    public static final String kTotalsLineName = "name";

    public static final String kTotalsLineNotes = "notes";


    //message node name
    public static final String kGiftMessageNodeName = "GiftWrapMessage";

    public static final String kBogusNodeName = "Bogus";

    //comments node name

    public static final String kWarningNodeName = "Warning";

    //warehouse notes node name

    public static final String kSuspectNodeName = "Suspect";

    public static Map yahooCCTransactionTypeMap = null;

    {
        yahooCCTransactionTypeMap = new TreeMap();
        //(Charge|Return|VoidCharge|VoidReturn|Capture|Refund|Void|ReAuth) #REQUIRED
        yahooCCTransactionTypeMap.put("Charge", "" + FinancialTransaction.transTypeAuthCaptureRequest);

        yahooCCTransactionTypeMap.put("Return", "-" + FinancialTransaction.transTypeCreditRequest);
        yahooCCTransactionTypeMap.put("VoidCharge", "-" + FinancialTransaction.transTypeCreditRequest);
        yahooCCTransactionTypeMap.put("VoidReturn", "" + FinancialTransaction.transTypeCaptureOnlyRequest);

        yahooCCTransactionTypeMap.put("Capture", "" + FinancialTransaction.transTypeCaptureOnlyRequest);
        yahooCCTransactionTypeMap.put("Refund", "-" + FinancialTransaction.transTypeCreditRequest);

        yahooCCTransactionTypeMap.put("Void", "-" + FinancialTransaction.transTypeVoidTransactionRequest);

        yahooCCTransactionTypeMap.put("ReAuth", "" + FinancialTransaction.transTypeAuthOnlyRequest);


    }

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


        try {

            doPost(req, resp);

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    //all requests should be POST

    public void doPost(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

        OwdClient clientInfo = null;

        String clientID = null;

        String clientAuth = null;

        String requestType = null;

        myServletInputStream myFilter = null;

        ////log.debug("in Yahoo post");


        try {

            //////log.debug("in API post "+ req.getContentLength());

            if (req.getContentLength() > 102400)

                throw new Exception("Requests cannot exceed 102400 characters in length");

            //find out what kind of request came in

            String path = req.getPathInfo();

            ////log.debug("got URL "+HttpUtils.getRequestURL(req));

            StringTokenizer pathElements = new StringTokenizer(path, "/");

            if (pathElements.hasMoreTokens())

                clientID = pathElements.nextToken();

            else

                throw new Exception("Client ID not found");

            if (pathElements.hasMoreTokens())

                clientAuth = pathElements.nextToken();

            else

                throw new Exception("Client Authorization not found");

            if (pathElements.hasMoreTokens())

                requestType = pathElements.nextToken();

            else

                throw new Exception("Request type not found");

            //////log.debug("got id, type, auth as: "+clientID+","+clientAuth+","+requestType);


            if (clientID == null || clientAuth == null)

                throw new Exception("Authorization not found");


            if (!(clientID.equals(OWDUtilities.decryptData(clientAuth))))

                throw new Exception("Authorization invalid");


            if (kOrderCreationRequest.equals(requestType)) {

                //order create

                clientInfo = ClientFactory.getClientFromClientID(new Integer(clientID));

                if (clientInfo == null)

                    throw new Exception("Client ID invalid");


                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                factory.setNamespaceAware(false);

                factory.setValidating(false);

                javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();


                myFilter = new myServletInputStream(req.getInputStream());

                org.w3c.dom.Document doc = builder.parse(myFilter);


                Element root = doc.getDocumentElement();

                root.normalize();

                createYahooOrder(clientInfo, root);

                resp.getWriter().write("Order OK");

                if (myFilter != null) {

                    Mailer.postMailMessage("Yahoo API Request Data", myFilter.data.toString(), "noop@owd.com", "noop@owd.com");

                }

            } else if (kInventoryCountRequest.equals(requestType)) {

                //inventory query


                Enumeration params = req.getParameterNames();

                while (params.hasMoreElements()) {

                    String name = (String) (params.nextElement());

                    String[] values = req.getParameterValues(name);

                    for (int i = 0; i < values.length; i++) {

                        ////log.debug("got Yahoo inv data:<"+name+">=<"+values[i]+">");

                    }

                }


                resp.getWriter().write("OK");

                int invLevel = getYahooInventory(clientID, req.getParameter(".catalog"),
                        req.getParameter(".code"),
                        req.getParameter(".id"),
                        req.getParameter(".quantity")
                        , req.getParameter(".price"));


                resp.setHeader("Available", "" + invLevel);

                if (invLevel <= 0) {


                    resp.setHeader("Inventory-Message", "" + getYahooMessage(clientID, req.getParameter(".code"),
                            req.getParameter(".id")));

                }

            } else {

                throw new Exception("Request type invalid");

            }


        } catch (Throwable ex) {

            ex.printStackTrace();

            if (myFilter != null) {

                Mailer.postMailMessage("Yahoo API Error Data", myFilter.data.toString() + "\n\n" + ex.getMessage(), "noop@owd.com", "noop@owd.com");

            }

            try {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
            } catch (Exception e) {
            }

        }


    }


    class myServletInputStream extends ServletInputStream {


        public StringBuffer data = new StringBuffer();

        private ServletInputStream in = null;


        public myServletInputStream(ServletInputStream ins) {

            in = ins;

        }


        public int read() throws IOException {


            int b = in.read();

            data.append(new Character((char) b));

            return b;

        }


        public int readLine(byte[] b, int off, int len) throws IOException {


            int r = in.readLine(b, off, len);

            data.append(b);

            return r;

        }


    }


    static public void main2(String[] args) {


        String[] payload = {"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE OrderList SYSTEM \"http://store.yahoo.com/doc/dtd/OrderList2.dtd\">\n" +
                "<OrderList StoreAccountName=\"razorama\">\n" +
                " <Order currency=\"USD\" id=\"razorama-135655\">\n" +
                "  <Time>Tue Jul 10 19:06:27 2007 GMT</Time>\n" +
                "  <NumericTime>1184094387</NumericTime>\n" +
                "  <Referer>&lt;a href=http://internal.owd.com:8080/intranet/cc/screenpop.do?agentfname=bquaschnick&amp;agentid=bquaschnick&amp;callId=1183986322.1890&amp;campaign=RAZORAMA&gt;http://internal.owd.com:8080/intranet/cc/screenpop.do?agentfname=bquaschnick&amp;agentid=bquaschnick&amp;callId=1183986322.1890&amp;campaign=RAZORAMA&lt;/a&gt;</Referer>\n" +
                "  <Entry-Point>&lt;a href=http://razorama.com/&gt;http://razorama.com/&lt;/a&gt;</Entry-Point>\n" +
                "  <AddressInfo type=\"ship\">\n" +
                "   <Name>\n" +
                "    <First>Scott</First>\n" +
                "    <Last>Tucker</Last>\n" +
                "    <Full>Scott Tucker</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>Belt Sports Complex</Address1>\n" +
                "   <Address2>210 N Belt Hiway</Address2>\n" +
                "   <City>St. Joseph</City>\n" +
                "   <State>MO</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>64506</Zip>\n" +
                "   <Phone>816-233-1054</Phone>\n" +
                "   <Email></Email>\n" +
                "  </AddressInfo>\n" +
                "  <AddressInfo type=\"bill\">\n" +
                "   <Name>\n" +
                "    <First>Scott</First>\n" +
                "    <Last>Tucker</Last>\n" +
                "    <Full>Scott Tucker</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>Belt Sports Complex</Address1>\n" +
                "   <Address2>210 N Belt Hiway</Address2>\n" +
                "   <City>St. Joseph</City>\n" +
                "   <State>MO</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>64506</Zip>\n" +
                "   <Phone>816-233-1054</Phone>\n" +
                "   <Email>inscho3@aol.com</Email>\n" +
                "  </AddressInfo>\n" +
                "  <IPAddress>66.231.0.194</IPAddress>\n" +
                "  <Shipping>UPS Ground</Shipping>\n" +
                "  <CreditCard expiration=\"04/2010\" type=\"Visa\">4339930005767647</CreditCard>\n" +
                "  <Comments>bonnie</Comments>\n" +
                "  <Item num=\"0\">\n" +
                "   <Id>razore100</Id>\n" +
                "   <Code>13111260-DS</Code>\n" +
                "   <Quantity>6</Quantity>\n" +
                "   <Unit-Price>89.95</Unit-Price>\n" +
                "   <Description>RED RAZOR E100 ELECTRIC SCOOTER</Description>\n" +
                "   <Url>http://www.razorama.com/razore100.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=52 height=70 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1955_551476&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Total>\n" +
                "   <Line type=\"Subtotal\" name=\"Subtotal\">539.70</Line>\n" +
                "   <Line type=\"Shipping\" name=\"Shipping\">123.00</Line>\n" +
                "   <Line type=\"Total\" name=\"Total\">662.70</Line>\n" +
                "  </Total>\n" +
                "  <Space-Id></Space-Id>\n" +
                "  <CardEvents>\n" +
                "   <CardAuth auth-response=\"A\" cvv-response=\"M\" amount=\"662.70\" avs-response=\"NYZ\" approval-number=\"088296\"/>\n" +
                "  </CardEvents>\n" +
                " </Order>\n" +
                " <Order currency=\"USD\" id=\"razorama-135656\">\n" +
                "  <Time>Tue Jul 10 21:21:23 2007 GMT</Time>\n" +
                "  <NumericTime>1184102483</NumericTime>\n" +
                "  <Referer>&lt;a href=http://www.google.com&gt;www.google.com&lt;/a&gt; search for &lt;a href=http://www.google.com/search?cd=1&amp;ct=result&amp;hl=en&amp;oi=spell&amp;q=seat%20belt%20for%20razor%20go-kart&amp;resnum=0&amp;rls=com.microsoft:en-us:IE-SearchBox&amp;rlz=1I7SUNA&amp;sa=X&amp;spell=1&gt;seat belt for razor go-kart&lt;/a&gt;</Referer>\n" +
                "  <Entry-Point>&lt;a href=http://www.razorama.com/ragrfogokapa.html&gt;http://www.razorama.com/ragrfogokapa.html&lt;/a&gt;</Entry-Point>\n" +
                "  <AddressInfo type=\"ship\">\n" +
                "   <Name>\n" +
                "    <First>Kristin</First>\n" +
                "    <Last>Kagen</Last>\n" +
                "    <Full>Kristin Kagen</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>9 Cobham Draw</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Pooler</City>\n" +
                "   <State>GA</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>31322</Zip>\n" +
                "   <Phone>912-450-0031</Phone>\n" +
                "   <Email></Email>\n" +
                "  </AddressInfo>\n" +
                "  <AddressInfo type=\"bill\">\n" +
                "   <Name>\n" +
                "    <First>Kristin</First>\n" +
                "    <Last>Kagen</Last>\n" +
                "    <Full>Kristin Kagen</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>9 Cobham Draw</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Pooler</City>\n" +
                "   <State>GA</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>31322</Zip>\n" +
                "   <Phone>912-450-0031</Phone>\n" +
                "   <Email>kwkagen@hargray.com</Email>\n" +
                "  </AddressInfo>\n" +
                "  <IPAddress>64.203.223.240</IPAddress>\n" +
                "  <Shipping>UPS Ground</Shipping>\n" +
                "  <CreditCard expiration=\"09/2009\" type=\"Visa\">4388576021246239</CreditCard>\n" +
                "  <Item num=\"0\">\n" +
                "   <Id>ground-force-seat-belt</Id>\n" +
                "   <Code>99034</Code>\n" +
                "   <Quantity>1</Quantity>\n" +
                "   <Unit-Price>6.95</Unit-Price>\n" +
                "   <Description>Razor Ground Force Seat Belt</Description>\n" +
                "   <Url>http://www.razorama.com/ground-force-seat-belt.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=70 height=55 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1955_183413&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Item num=\"1\">\n" +
                "   <Id>grfowh</Id>\n" +
                "   <Code>99049</Code>\n" +
                "   <Quantity>2</Quantity>\n" +
                "   <Unit-Price>9.95</Unit-Price>\n" +
                "   <Description>Razor Ground Force &lt;b&gt;FRONT&lt;/b&gt; Wheel</Description>\n" +
                "   <Url>http://www.razorama.com/grfowh.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=70 height=55 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1955_126698&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Item num=\"2\">\n" +
                "   <Id>razor-ground-force-rear-whee</Id>\n" +
                "   <Code>99066</Code>\n" +
                "   <Quantity>2</Quantity>\n" +
                "   <Unit-Price>15.95</Unit-Price>\n" +
                "   <Description>Razor Ground Force &lt;b&gt;REAR&lt;/b&gt; Wheel - without Bearings</Description>\n" +
                "   <Url>http://www.razorama.com/razor-ground-force-rear-whee.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=70 height=55 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1956_4811584&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Total>\n" +
                "   <Line type=\"Subtotal\" name=\"Subtotal\">58.75</Line>\n" +
                "   <Line type=\"Shipping\" name=\"Shipping\">11.01</Line>\n" +
                "   <Line type=\"Total\" name=\"Total\">69.76</Line>\n" +
                "  </Total>\n" +
                "  <Space-Id></Space-Id>\n" +
                "  <CardEvents>\n" +
                "   <CardAuth auth-response=\"A\" cvv-response=\"M\" amount=\"69.76\" avs-response=\"YYY\" approval-number=\"03694D\"/>\n" +
                "  </CardEvents>\n" +
                " </Order>\n" +
                " <Order currency=\"USD\" id=\"razorama-135657\">\n" +
                "  <Time>Tue Jul 10 21:22:32 2007 GMT</Time>\n" +
                "  <NumericTime>1184102552</NumericTime>\n" +
                "  <Referer>&lt;a href=http://www.google.com&gt;www.google.com&lt;/a&gt; search for &lt;a href=http://www.google.com/search?hl=en&amp;q=razorama&gt;razorama&lt;/a&gt;</Referer>\n" +
                "  <Entry-Point>&lt;a href=http://www.razorama.com/&gt;http://www.razorama.com/&lt;/a&gt;</Entry-Point>\n" +
                "  <AddressInfo type=\"ship\">\n" +
                "   <Name>\n" +
                "    <First>Trish</First>\n" +
                "    <Last>Smith</Last>\n" +
                "    <Full>Trish Smith</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>1821C Anderton Road</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Comox</City>\n" +
                "   <State>BC</State>\n" +
                "   <Country>CA Canada</Country>\n" +
                "   <Zip>V9M 4B1</Zip>\n" +
                "   <Phone>250-339-9593</Phone>\n" +
                "   <Email></Email>\n" +
                "  </AddressInfo>\n" +
                "  <AddressInfo type=\"bill\">\n" +
                "   <Name>\n" +
                "    <First>Trish</First>\n" +
                "    <Last>Smith</Last>\n" +
                "    <Full>Trish Smith</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>1821C Anderton Road</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Comox</City>\n" +
                "   <State>BC</State>\n" +
                "   <Country>CA Canada</Country>\n" +
                "   <Zip>V9M 4B1</Zip>\n" +
                "   <Phone>250-339-9593</Phone>\n" +
                "   <Email>ts9593@telus.net</Email>\n" +
                "  </AddressInfo>\n" +
                "  <IPAddress>64.180.204.54</IPAddress>\n" +
                "  <Shipping>UPS Standard to Canada</Shipping>\n" +
                "  <CreditCard expiration=\"06/2009\" type=\"MasterCard\">5191230096898763</CreditCard>\n" +
                "  <Item num=\"0\">\n" +
                "   <Id>blacrazgrip</Id>\n" +
                "   <Code>134320-BK</Code>\n" +
                "   <Quantity>1</Quantity>\n" +
                "   <Unit-Price>9.95</Unit-Price>\n" +
                "   <Description>Black Razor Scooter Handle Grips</Description>\n" +
                "   <Url>http://www.razorama.com/blacrazgrip.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=53 height=70 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1958_35094&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Item num=\"1\">\n" +
                "   <Id>a4promosc</Id>\n" +
                "   <Code>13018010-RD-DS</Code>\n" +
                "   <Quantity>1</Quantity>\n" +
                "   <Unit-Price>59.95</Unit-Price>\n" +
                "   <Description>RAZOR &lt;b&gt;PRO MODEL&lt;/b&gt; KICK SCOOTER</Description>\n" +
                "   <Url>http://www.razorama.com/a4promosc.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=53 height=70 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1957_3856120&gt;</Thumb>\n" +
                "   <Option name=\"Choose Color\">Red</Option>\n" +
                "   <OptionLists>\n" +
                "    <OptionList name=\"Choose Color\">\n" +
                "     <OptionValue>Clear/Black</OptionValue>\n" +
                "     <OptionValue>Red</OptionValue>\n" +
                "    </OptionList>\n" +
                "   </OptionLists>\n" +
                "  </Item>\n" +
                "  <Total>\n" +
                "   <Line type=\"Subtotal\" name=\"Subtotal\">69.90</Line>\n" +
                "   <Line type=\"Shipping\" name=\"Shipping\">27.54</Line>\n" +
                "   <Line type=\"Total\" name=\"Total\">97.44</Line>\n" +
                "  </Total>\n" +
                "  <Space-Id></Space-Id>\n" +
                "  <CardEvents>\n" +
                "   <CardAuth auth-response=\"A\" cvv-response=\"M\" amount=\"97.44\" avs-response=\"NNN\" approval-number=\"172233\"/>\n" +
                "  </CardEvents>\n" +
                " </Order>\n" +
                " <Order currency=\"USD\" id=\"razorama-135658\">\n" +
                "  <Time>Tue Jul 10 21:39:37 2007 GMT</Time>\n" +
                "  <NumericTime>1184103577</NumericTime>\n" +
                "  <AddressInfo type=\"ship\">\n" +
                "   <Name>\n" +
                "    <First>GISELLE</First>\n" +
                "    <Last>MARTINEZ</Last>\n" +
                "    <Full>GISELLE MARTINEZ</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>3515 E. IOWA</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>FRESNO</City>\n" +
                "   <State>CA</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>93702</Zip>\n" +
                "   <Phone>559-485-7446</Phone>\n" +
                "   <Email></Email>\n" +
                "  </AddressInfo>\n" +
                "  <AddressInfo type=\"bill\">\n" +
                "   <Name>\n" +
                "    <First>GISELLE</First>\n" +
                "    <Last>MARTINEZ</Last>\n" +
                "    <Full>GISELLE MARTINEZ</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>3515 E. IOWA</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>FRESNO</City>\n" +
                "   <State>CA</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>93702</Zip>\n" +
                "   <Phone>559-485-7446</Phone>\n" +
                "   <Email>HERNANDEZ1938@SBCGLOBAL.NET</Email>\n" +
                "  </AddressInfo>\n" +
                "  <IPAddress>71.128.249.81</IPAddress>\n" +
                "  <Shipping>UPS Next Day Air Saver</Shipping>\n" +
                "  <CreditCard expiration=\"06/2010\" type=\"Visa\">4185865851690623</CreditCard>\n" +
                "  <Item num=\"0\">\n" +
                "   <Id>razor-dirt-quad-inner-tubes</Id>\n" +
                "   <Code>3060078</Code>\n" +
                "   <Quantity>2</Quantity>\n" +
                "   <Unit-Price>15.95</Unit-Price>\n" +
                "   <Description>Razor Dirt Quad Inner Tubes &lt;br&gt;13X6.5-6</Description>\n" +
                "   <Url>http://www.razorama.com/razor-dirt-quad-inner-tubes.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=70 height=55 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1958_688122&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Total>\n" +
                "   <Line type=\"Subtotal\" name=\"Subtotal\">31.90</Line>\n" +
                "   <Line type=\"Shipping\" name=\"Shipping\">44.61</Line>\n" +
                "   <Line type=\"Tax\" name=\"Tax\">2.47</Line>\n" +
                "   <Line type=\"Total\" name=\"Total\">78.98</Line>\n" +
                "  </Total>\n" +
                "  <Space-Id></Space-Id>\n" +
                "  <CardEvents>\n" +
                "   <CardAuth auth-response=\"A\" cvv-response=\"M\" amount=\"78.98\" avs-response=\"YYY\" approval-number=\"08115A\"/>\n" +
                "  </CardEvents>\n" +
                " </Order>\n" +
                " <Order currency=\"USD\" id=\"razorama-135659\">\n" +
                "  <Time>Tue Jul 10 21:51:25 2007 GMT</Time>\n" +
                "  <NumericTime>1184104285</NumericTime>\n" +
                "  <AddressInfo type=\"ship\">\n" +
                "   <Name>\n" +
                "    <First>Kellie</First>\n" +
                "    <Last>Janes</Last>\n" +
                "    <Full>Kellie Janes</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>2913  Evergreen</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Salt Lake</City>\n" +
                "   <State>UT</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>84109</Zip>\n" +
                "   <Phone>801-484-2509</Phone>\n" +
                "   <Email></Email>\n" +
                "  </AddressInfo>\n" +
                "  <AddressInfo type=\"bill\">\n" +
                "   <Name>\n" +
                "    <First>Kellie</First>\n" +
                "    <Last>Janes</Last>\n" +
                "    <Full>Kellie Janes</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>2913  Evergreen</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Salt Lake</City>\n" +
                "   <State>UT</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>84109</Zip>\n" +
                "   <Phone>801-484-2509</Phone>\n" +
                "   <Email>canyonconch@yahoo.com</Email>\n" +
                "  </AddressInfo>\n" +
                "  <IPAddress>66.231.0.194</IPAddress>\n" +
                "  <Shipping>UPS Ground</Shipping>\n" +
                "  <CreditCard expiration=\"05/2008\" type=\"MasterCard\">5491237272058622</CreditCard>\n" +
                "  <Comments>Luann</Comments>\n" +
                "  <Item num=\"0\">\n" +
                "   <Id>12-5x2-25-pocket-mod-inner-tube-</Id>\n" +
                "   <Code>640045</Code>\n" +
                "   <Quantity>1</Quantity>\n" +
                "   <Unit-Price>9.95</Unit-Price>\n" +
                "   <Description>Razor Pocket Mod Innertube (12.5 x 2.25)</Description>\n" +
                "   <Url>http://www.razorama.com/12-5x2-25-pocket-mod-inner-tube-.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=70 height=55 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1957_3789140&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Total>\n" +
                "   <Line type=\"Subtotal\" name=\"Subtotal\">9.95</Line>\n" +
                "   <Line type=\"Shipping\" name=\"Shipping\">6.80</Line>\n" +
                "   <Line type=\"Tax\" name=\"Tax\">0.00</Line>\n" +
                "   <Line type=\"Total\" name=\"Total\">16.75</Line>\n" +
                "  </Total>\n" +
                "  <Space-Id></Space-Id>\n" +
                "  <CardEvents>\n" +
                "   <CardAuth auth-response=\"A\" cvv-response=\"M\" amount=\"22.99\" avs-response=\"NNN\" approval-number=\"010156\"/>\n" +
                "  </CardEvents>\n" +
                " </Order>\n" +
                " <Order currency=\"USD\" id=\"razorama-135660\">\n" +
                "  <Time>Tue Jul 10 23:28:33 2007 GMT</Time>\n" +
                "  <NumericTime>1184110113</NumericTime>\n" +
                "  <Referer>&lt;a href=http://internal.owd.com:8080/intranet/cc/screenpop.do?agentfname=afeltner&amp;agentid=afeltner&amp;callId=1184086185.3957&amp;campaign=RAZORAMA&gt;http://internal.owd.com:8080/intranet/cc/screenpop.do?agentfname=afeltner&amp;agentid=afeltner&amp;callId=1184086185.3957&amp;campaign=RAZORAMA&lt;/a&gt;</Referer>\n" +
                "  <Entry-Point>&lt;a href=http://razorama.com/&gt;http://razorama.com/&lt;/a&gt;</Entry-Point>\n" +
                "  <AddressInfo type=\"ship\">\n" +
                "   <Name>\n" +
                "    <First>Pauline</First>\n" +
                "    <Last>Toner</Last>\n" +
                "    <Full>Pauline Toner</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>82 Forest Green</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Statin Island</City>\n" +
                "   <State>NY</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>10312</Zip>\n" +
                "   <Phone>718-605-4287</Phone>\n" +
                "   <Email></Email>\n" +
                "  </AddressInfo>\n" +
                "  <AddressInfo type=\"bill\">\n" +
                "   <Name>\n" +
                "    <First>Pauline</First>\n" +
                "    <Last>Toner</Last>\n" +
                "    <Full>Pauline Toner</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>82 Forest Green</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Statin Island</City>\n" +
                "   <State>NY</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>10312</Zip>\n" +
                "   <Phone>718-605-4287</Phone>\n" +
                "   <Email>lilstuff211@aol.com</Email>\n" +
                "  </AddressInfo>\n" +
                "  <IPAddress>66.231.0.194</IPAddress>\n" +
                "  <Shipping>UPS Ground</Shipping>\n" +
                "  <CreditCard expiration=\"04/2008\" type=\"Visa\">4151270001692506</CreditCard>\n" +
                "  <Comments>ashley</Comments>\n" +
                "  <Item num=\"0\">\n" +
                "   <Id>dirt-rocket-mx500</Id>\n" +
                "   <Code>15128190-DS</Code>\n" +
                "   <Quantity>1</Quantity>\n" +
                "   <Unit-Price>249.95</Unit-Price>\n" +
                "   <Description>RAZOR MX500 DIRT ROCKET</Description>\n" +
                "   <Url>http://www.razorama.com/dirt-rocket-mx500.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=70 height=47 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1953_211052&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Total>\n" +
                "   <Line type=\"Subtotal\" name=\"Subtotal\">249.95</Line>\n" +
                "   <Line type=\"Shipping\" name=\"Shipping\">121.54</Line>\n" +
                "   <Line type=\"Total\" name=\"Total\">371.49</Line>\n" +
                "  </Total>\n" +
                "  <Space-Id></Space-Id>\n" +
                "  <CardEvents>\n" +
                "   <CardAuth auth-response=\"A\" cvv-response=\"M\" amount=\"371.49\" avs-response=\"YYY\" approval-number=\"087941\"/>\n" +
                "  </CardEvents>\n" +
                " </Order>\n" +
                " <Order currency=\"USD\" id=\"razorama-135661\">\n" +
                "  <Time>Tue Jul 10 23:32:52 2007 GMT</Time>\n" +
                "  <NumericTime>1184110372</NumericTime>\n" +
                "  <AddressInfo type=\"ship\">\n" +
                "   <Name>\n" +
                "    <First>Dawn</First>\n" +
                "    <Last>Young</Last>\n" +
                "    <Full>Dawn Young</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>505 Showalter Road</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Yorktown</City>\n" +
                "   <State>VA</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>23692</Zip>\n" +
                "   <Phone>(757) 898-3002</Phone>\n" +
                "   <Email></Email>\n" +
                "  </AddressInfo>\n" +
                "  <AddressInfo type=\"bill\">\n" +
                "   <Name>\n" +
                "    <First>Dawn</First>\n" +
                "    <Last>Young</Last>\n" +
                "    <Full>Dawn Young</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>505 Showalter Road</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Yorktown</City>\n" +
                "   <State>VA</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>23692</Zip>\n" +
                "   <Phone>(757) 898-3002</Phone>\n" +
                "   <Email>dawnyoung1@cox.net</Email>\n" +
                "  </AddressInfo>\n" +
                "  <IPAddress>68.96.203.154</IPAddress>\n" +
                "  <Shipping>UPS Ground</Shipping>\n" +
                "  <CreditCard expiration=\"10/2010\" type=\"Visa\">4368024014943193</CreditCard>\n" +
                "  <Item num=\"0\">\n" +
                "   <Id>razor-mx500-control-module</Id>\n" +
                "   <Code>8190015</Code>\n" +
                "   <Quantity>1</Quantity>\n" +
                "   <Unit-Price>20.95</Unit-Price>\n" +
                "   <Description>Razor MX500 &amp; MX650 Control Module</Description>\n" +
                "   <Url>http://www.razorama.com/razor-mx500-control-module.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=70 height=55 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1955_374836&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Total>\n" +
                "   <Line type=\"Subtotal\" name=\"Subtotal\">20.95</Line>\n" +
                "   <Line type=\"Shipping\" name=\"Shipping\">14.64</Line>\n" +
                "   <Line type=\"Total\" name=\"Total\">35.59</Line>\n" +
                "  </Total>\n" +
                "  <Space-Id></Space-Id>\n" +
                "  <CardEvents>\n" +
                "   <CardAuth auth-response=\"A\" cvv-response=\"M\" amount=\"35.59\" avs-response=\"YYY\" approval-number=\"113624\"/>\n" +
                "  </CardEvents>\n" +
                " </Order>\n" +
                " <Order currency=\"USD\" id=\"razorama-135662\">\n" +
                "  <Time>Wed Jul 11 00:09:08 2007 GMT</Time>\n" +
                "  <NumericTime>1184112548</NumericTime>\n" +
                "  <Referer>&lt;a href=http://www.google.com&gt;www.google.com&lt;/a&gt; search for &lt;a href=http://www.google.com/search?btnG=Search&amp;hl=en&amp;q=razor%20go%20cart%20parts&gt;razor go cart parts&lt;/a&gt;</Referer>\n" +
                "  <Entry-Point>&lt;a href=http://www.razorama.com/ragrfogokapa.html&gt;http://www.razorama.com/ragrfogokapa.html&lt;/a&gt;</Entry-Point>\n" +
                "  <AddressInfo type=\"ship\">\n" +
                "   <Name>\n" +
                "    <First>Randy L. Davey</First>\n" +
                "    <Last>DDS</Last>\n" +
                "    <Full>Randy L. Davey DDS</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>6246 air Oaks Blvd</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Carmichael</City>\n" +
                "   <State>CA</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>95608</Zip>\n" +
                "   <Phone>916-972-8851</Phone>\n" +
                "   <Email></Email>\n" +
                "  </AddressInfo>\n" +
                "  <AddressInfo type=\"bill\">\n" +
                "   <Name>\n" +
                "    <First>Randy L. Davey</First>\n" +
                "    <Last>DDS</Last>\n" +
                "    <Full>Randy L. Davey DDS</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>6246 air Oaks Blvd</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Carmichael</City>\n" +
                "   <State>CA</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>95608</Zip>\n" +
                "   <Phone>916-972-8851</Phone>\n" +
                "   <Email>doctordavey@daveydental.com</Email>\n" +
                "  </AddressInfo>\n" +
                "  <IPAddress>67.125.10.186</IPAddress>\n" +
                "  <Shipping>UPS 3 Day Select</Shipping>\n" +
                "  <CreditCard expiration=\"06/2010\" type=\"MasterCard\">5424180840357484</CreditCard>\n" +
                "  <Item num=\"0\">\n" +
                "   <Id>groundforcebelt</Id>\n" +
                "   <Code>99004</Code>\n" +
                "   <Quantity>1</Quantity>\n" +
                "   <Unit-Price>11.95</Unit-Price>\n" +
                "   <Description>Razor Ground Force Belt</Description>\n" +
                "   <Url>http://www.razorama.com/groundforcebelt.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=70 height=55 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1955_185715&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Total>\n" +
                "   <Line type=\"Subtotal\" name=\"Subtotal\">11.95</Line>\n" +
                "   <Line type=\"Shipping\" name=\"Shipping\">23.84</Line>\n" +
                "   <Line type=\"Tax\" name=\"Tax\">0.93</Line>\n" +
                "   <Line type=\"Total\" name=\"Total\">36.72</Line>\n" +
                "  </Total>\n" +
                "  <Space-Id></Space-Id>\n" +
                "  <CardEvents>\n" +
                "   <CardAuth auth-response=\"A\" cvv-response=\"M\" amount=\"36.72\" avs-response=\"YYY\" approval-number=\"183052\"/>\n" +
                "  </CardEvents>\n" +
                " </Order>\n" +
                " <Order currency=\"USD\" id=\"razorama-135663\">\n" +
                "  <Time>Wed Jul 11 03:45:29 2007 GMT</Time>\n" +
                "  <NumericTime>1184125529</NumericTime>\n" +
                "  <AddressInfo type=\"ship\">\n" +
                "   <Name>\n" +
                "    <First>Kyle</First>\n" +
                "    <Last>Howes</Last>\n" +
                "    <Full>Kyle Howes</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>14444 Muirwood Circle</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Herriman</City>\n" +
                "   <State>UT</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>84096</Zip>\n" +
                "   <Phone>801-302-7884</Phone>\n" +
                "   <Email></Email>\n" +
                "  </AddressInfo>\n" +
                "  <AddressInfo type=\"bill\">\n" +
                "   <Name>\n" +
                "    <First>Kyle</First>\n" +
                "    <Last>Howes</Last>\n" +
                "    <Full>Kyle Howes</Full>\n" +
                "   </Name>\n" +
                "   <Company></Company>\n" +
                "   <Address1>14444 Muirwood Circle</Address1>\n" +
                "   <Address2></Address2>\n" +
                "   <City>Herriman</City>\n" +
                "   <State>UT</State>\n" +
                "   <Country>US United States</Country>\n" +
                "   <Zip>84096</Zip>\n" +
                "   <Phone>801-302-7884</Phone>\n" +
                "   <Email>Desert_Sky@comcast.net</Email>\n" +
                "  </AddressInfo>\n" +
                "  <IPAddress>76.27.14.29</IPAddress>\n" +
                "  <Shipping>UPS Ground</Shipping>\n" +
                "  <CreditCard expiration=\"03/2009\" type=\"MasterCard\">5466160141493955</CreditCard>\n" +
                "  <Item num=\"0\">\n" +
                "   <Id>12-5x2-25-pocket-mod-inner-tube-</Id>\n" +
                "   <Code>640045</Code>\n" +
                "   <Quantity>2</Quantity>\n" +
                "   <Unit-Price>9.95</Unit-Price>\n" +
                "   <Description>Razor Pocket Mod Innertube (12.5 x 2.25)</Description>\n" +
                "   <Url>http://www.razorama.com/12-5x2-25-pocket-mod-inner-tube-.html</Url>\n" +
                "   <Taxable>YES</Taxable>\n" +
                "   <Thumb>&lt;img border=0 width=70 height=55 src=http://us.st11.yimg.com/us.st.yimg.com/I/razorama_1957_3789140&gt;</Thumb>\n" +
                "  </Item>\n" +
                "  <Total>\n" +
                "   <Line type=\"Subtotal\" name=\"Subtotal\">19.90</Line>\n" +
                "   <Line type=\"Shipping\" name=\"Shipping\">13.90</Line>\n" +
                "   <Line type=\"Total\" name=\"Total\">33.80</Line>\n" +
                "  </Total>\n" +
                "  <Space-Id></Space-Id>\n" +
                "  <CardEvents>\n" +
                "   <CardAuth auth-response=\"A\" cvv-response=\"M\" amount=\"33.80\" avs-response=\"YYY\" approval-number=\"531878\"/>\n" +
                "  </CardEvents>\n" +
                " </Order>" +
                "</OrderList>"};


        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < payload.length; i++) {

            try {

                WebResource server = new WebResource("https://secure.owd.com/webapps/yahoo/304/GAXIIPFaTqR09FJp0XNtTwM=/neworder.jsp", "POST");

                log.debug("got server");

                server.setParameters(payload[i]);

                log.debug("set payload \r" + payload[i]);

                server.contentType = "text/xml";

                BufferedReader response = server.getResource();

                log.debug("got response:" + response);

                int line = 0;

                line = (int) response.read();

                while (line != -1) {

                    System.out.print(line);

                    if ((char) line == '>')

                        sb.append("&gt;\n");

                    else if ((char) line == '<')

                        sb.append("&lt;");

                    else

                        sb.append((char) line);


                    line = response.read();

                }

            } catch (Exception ex) {

                log.debug("RESP EX");

                ex.printStackTrace();

            }

        }

        log.debug(sb.toString());

    }


    public String getServletInfo() {

        return "One World Yahoo Server v";

    }


    public void createYahooOrder(OwdClient client, Element root) throws Exception {

        ////log.debug("in YahooOrderCreateRequest Handler2");

        boolean isAllVirtual = false;
        NodeList orderNodes = root.getElementsByTagName(kOrderNodeName);

        if (orderNodes == null) throw new Exception("No orders found in order");

        int orderCount = orderNodes.getLength();

        ////log.debug("yahoo ordercount="+orderCount);

        for (int i = 0; i < orderCount; i++) {


            Element orderElement = (Element) orderNodes.item(i);

            if (orderElement == null) throw new Exception("Order node " + i + " invalid");


            NodeList bogusNodes = orderElement.getElementsByTagName(kBogusNodeName);

            if (bogusNodes != null && bogusNodes.getLength() > 0) throw new Exception("Bogus flag found!");

            List transList = new ArrayList();
            StringBuffer importNotes = new StringBuffer();
            Order order = new Order(client.getClientId() + "");

            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();

            order.order_type = "Posted via Yahoo delivery";

            order.ship_call_date = OWDUtilities.getSQLDateForToday();

            order.backorder_rule = OrderXMLDoc.kPartialShip;

            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            order.is_paid = 1;

            String ref = orderElement.getAttribute(kOrderReference);

            if (ref == null) throw new Exception("Order ref not found");

            String gm = getSafeSubNodeValue(orderElement, kGiftMessageNodeName, "");

            if (gm != null) {
                order.gift_message = gm;
                if (order.gift_message.trim().length() > 0) {
                    order.is_gift = "1";
                }
            }


            order.order_refnum = orderElement.getAttribute(kOrderReference);

            ////log.debug("yahoo orderref="+order.order_refnum);

            NodeList addressNodes = orderElement.getElementsByTagName(kAddressNodeName);

            if (addressNodes != null) {

                int addrCount = addressNodes.getLength();

                if (addrCount == 2) {


                    for (int a = 0; a < addrCount; a++) {

                        Element addrElement = (Element) addressNodes.item(a);

                        if (addrElement == null) throw new Exception("Address node " + i + " invalid");


                        String addType = addrElement.getAttribute(kAddressType);

                        if (addType == null) throw new Exception("Address node " + i + " invalid");


                        if (kAddressTypeValBilling.equals(addType)) {

                            ////log.debug(getSafeSubNodeValue(addrElement,kCountryNodeName,""));

                            ////log.debug(getSafeSubNodeValue(addrElement,kCountryNodeName,"").substring(0,2));

                            Address billAddress = new Address("",

                                    getSafeSubNodeValue(addrElement, kAddress1NodeName, ""),

                                    getSafeSubNodeValue(addrElement, kAddress2NodeName, ""),

                                    getSafeSubNodeValue(addrElement, kCityNodeName, ""),

                                    getSafeSubNodeValue(addrElement, kStateNodeName, ""),

                                    getSafeSubNodeValue(addrElement, kZipNodeName, ""),

                                    countryCodes.getCountryForCode(getSafeSubNodeValue(addrElement, kCountryNodeName, "   ").substring(0, 2).trim()));


                            order.setBillingAddress(billAddress);


                            Contact billContact = new Contact(getSafeSubSubNodeValue(addrElement, kFullNameNodeName, kNameNodeName, ""),

                                    getSafeSubNodeValue(addrElement, kPhoneNodeName, ""),

                                    "",

                                    getSafeSubNodeValue(addrElement, kEmailNodeName, ""),

                                    "");

                            order.setBillingContact(billContact);

                        } else if (kAddressTypeValShipping.equals(addType)) {

                            ////log.debug(getSafeSubNodeValue(addrElement,kCountryNodeName,""));

                            ////log.debug(getSafeSubNodeValue(addrElement,kCountryNodeName,"").substring(0,2));

                            Address shipAddress = new Address("",

                                    getSafeSubNodeValue(addrElement, kAddress1NodeName, ""),

                                    getSafeSubNodeValue(addrElement, kAddress2NodeName, ""),

                                    getSafeSubNodeValue(addrElement, kCityNodeName, ""),

                                    getSafeSubNodeValue(addrElement, kStateNodeName, ""),

                                    getSafeSubNodeValue(addrElement, kZipNodeName, ""),

                                    countryCodes.getCountryForCode(getSafeSubNodeValue(addrElement, kCountryNodeName, "   ").substring(0, 2).trim()));


                            if (shipAddress.country.equals("USA") || shipAddress.country.equals("CANADA")) {

                                if (shipAddress.state.length() < 2) {

                                    throw new Exception("State value missing or invalid");

                                }

                            }


                            if (shipAddress.address_one.length() < 1 && shipAddress.address_two.length() < 1) {

                                throw new Exception("Street address information missing");


                            }

                            order.getShippingInfo().setShippingAddress(shipAddress);


                            Contact shipContact = new Contact(getSafeSubSubNodeValue(addrElement, kFullNameNodeName, kNameNodeName, ""),

                                    getSafeSubNodeValue(addrElement, kPhoneNodeName, ""),

                                    "",

                                    getSafeSubNodeValue(addrElement, kEmailNodeName, ""),

                                    "");


                            order.getShippingInfo().setShippingContact(shipContact);


                        } else {

                            ////log.debug("invalid address node");

                            throw new Exception("Address node " + i + " type invalid");

                        }


                    }

                } else {

                    throw new Exception("Not enough address nodes found");

                }

            } else {

                throw new Exception("No address nodes found");

            }

            ////log.debug("got address node");

            ////log.debug("got ship type");

            List yahooItems = new ArrayList();


            NodeList itemNodes = orderElement.getElementsByTagName(kItemsNodeName);

            if (itemNodes != null) {

                if (itemNodes.getLength() > 0) {

                    for (int r = 0; r < itemNodes.getLength(); r++) {

                        Element itemElement = (Element) itemNodes.item(r);
                        YahooItemData item = new YahooItemData();
                        yahooItems.add(item);

                        item.setYahooLineItemID(itemElement.getAttribute("num"));


                        if (item.getYahooLineItemID() == null || ("").equals(item.getYahooLineItemID()))

                            item.setYahooLineItemID("0");


                        if (client.getClientId() == 495) {
                            item.setPart(getSafeSubNodeValue(itemElement, kItemIDNodeName, ""));
                        } else {
                            item.setPart(getSafeSubNodeValue(itemElement, kItemCodeNodeName, ""));
                        }

                        item.setDesc(getSafeSubNodeValue(itemElement, kItemDescriptionNodeName, ""));

                        if (item.getDesc().toUpperCase().indexOf("<BR>") > 0) {

                            String desc2 = (item.getDesc() + " ").substring(0, item.getDesc().toUpperCase().indexOf("<BR>"));

                            item.setDesc(desc2 + (item.getDesc() + " ").substring(item.getDesc().toUpperCase().indexOf("<BR>") + 4));

                        }

                        item.setCost(OWDUtilities.floatFromString(getSafeSubNodeValue(itemElement, kItemUnitPriceNodeName, "")));

                        if (item.getCost() < (float) 0.00)

                            throw new Exception("Cost less than zero for SKU " + item.getPart());

                        item.setQty(OWDUtilities.intFromString(getSafeSubNodeValue(itemElement, kItemQuantityNodeName, "")));

                        if (item.getQty() < 1)

                            throw new Exception("Quantity requested less than one for SKU " + item.getPart());


                        if (client.getClientId() == 133) {


                            TeamDepotItemCustomization(item, itemElement);


                        }

                        if (client.getClientId() == 304) {


                            RazoramaItemCustomization(item, yahooItems);


                        }


                    }

                    if (yahooItems.size() > 0) {
                        Iterator it = yahooItems.iterator();
                        while (it.hasNext()) {
                            YahooItemData anItem = (YahooItemData) it.next();
                            if (client.getClientId() == 445 && anItem.getPart().contains("download")) {
                                anItem.setPart("download");
                            }
                            if (LineItem.SKUExists(client.getClientId() + "", anItem.getPart())) {


                                if (client.getClientId() == 311)//PuddleDancer
                                {

                                    PuddledancerItemCustomization(anItem, client, order);


                                } else {

                                    order.addLineItem(LineItem.getCleanSKU(anItem.getPart()), anItem.getQty(), anItem.getCost(), anItem.getCost() * anItem.getQty(), anItem.getDesc(), anItem.getColor(), anItem.getSize());
                                    if (order.skuList.size() > 0) {
                                        ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = anItem.getYahooLineItemID();
                                    }
                                }


                                if (anItem.isMailin()) {

                                    ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).long_desc = "Mailed in by Customer";

                                } else if (anItem.isPersonalized()) {

                                    ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).long_desc = "Personalized";

                                }

                            } else {

                                throw new Exception("No inventory found matching SKU " + anItem.getPart());

                            }


                        }
                    }


                } else {

                    throw new Exception("No valid line items found in order");

                }

            } else {

                throw new Exception("No valid line items found in order");

            }

            if (client.getClientId() == 311)//PuddleDancer
            {
                //lit insert
                //  order.addInsertItemIfAvailable("pdp-catalog-insert", 2);
                //backorder policy

                order.backorder_rule = OrderXMLDoc.kBackOrderAll;
            }
            if (client.getClientId() == 445)//cantarima
            {
                //backorder policy

                order.backorder_rule = OrderXMLDoc.kBackOrderAll;
            }


            String shipType = "";

            try {

                shipType = getSafeSubNodeValue(orderElement, kShippingNodeName, "");

                if (client.getClientId() == 311)//PuddleDancer
                {

                    float orderWeight = order.getDecimalPoundsWeight() + 0.2f;

                    if (shipType.indexOf("Ground") >= 0) {
                        shipType = "UPS Ground";
                    } else if (shipType.startsWith("Priority Mail")) {
                        shipType = "USPS Priority Mail";
                    } else if (shipType.indexOf("Global Priority") >= 0) {
                        shipType = "USPS Global Priority Mail";
                    } else if (shipType.indexOf("Parcel Post Canada") >= 0 || shipType.indexOf("USPS Int'l Letter-Post Air") >= 0) {
                        if (orderWeight >= 4.00f) {
                            shipType = "USPS Int'l Parcel Post Air";
                        } else {
                            shipType = "USPS Int'l Letter-Post Air";

                        }
                    }


                } else if (client.getClientId() == 304)//Razorama
                {


                    if (!(shipType.toUpperCase().contains("UPS"))) {
                        List<String> svcCodes = new ArrayList<String>();

                        OrderRater rater = new OrderRater(order);
                        rater.setCalculateKitWeights(true);

                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD.1ST.PRIORITY"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD.1ST.LETTER"))));

                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("UPS.GND"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("FDX.GND"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("FDX.HD"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("TANDATA_FEDEXFSMS.FEDEX.SP_PS"))));

                        try {
                            shipType = rater.selectBestWay(svcCodes);
                        } catch (Exception exx) {
                            if (order.getShippingAddress().isPOAPO() || order.getShippingAddress().isUS()) {
                                shipType = "FedEx SmartPost Parcel Select";
                            } else {
                                shipType = "USPS First-Class Mail International";
                            }
                        }


                    }else{

                        if (shipType.toUpperCase().contains("3 DAY SELECT")) {
                            shipType = "UPS 3 Day Select";
                        } else if (shipType.toUpperCase().contains("2ND DAY AIR")) {
                            shipType = "UPS 2nd Day Air";
                        } else if (shipType.toUpperCase().contains("NEXT DAY AIR SAVER")) {
                            shipType = "UPS Next Day Air Saver";
                        } else if (shipType.toUpperCase().contains("NEXT DAY AIR")) {
                            shipType = "UPS Next Day Air";
                        } else if (shipType.toUpperCase().contains("CANADA")) {
                            shipType = "UPS Standard to Canada";
                        } else if (shipType.toUpperCase().contains("EXPEDITED")) {
                            shipType = "UPS Worldwide Expedited";
                        } else if (shipType.toUpperCase().contains("WORLDWIDE SAVER")) {
                            shipType = "UPS Worldwide Saver";
                        } else
                        {
                            shipType = "UPS Ground";
                        }
                    }

                } else if (client.getClientId() == 445)//anticougar
                {
                    List<String> svcCodes = new ArrayList<String>();

                    OrderRater rater = new OrderRater(order);
                    rater.setCalculateKitWeights(true);
                    if (shipType.toUpperCase().contains("GROUND")) {
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD.1ST.PRIORITY"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("UPS.GND"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD.4TH.PARCEL"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD.1ST.LETTER"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD_USPS_I_FIRST"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD_USPS_I_PRIORITY"))));
                    } else if (shipType.toUpperCase().contains("AIR")) {
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD.1ST.PRIORITY"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("UPS.GND"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD_USPS_I_PRIORITY"))));
                    } else if (shipType.toUpperCase().contains("EXPRESS")) {
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("POS.EXP"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("UPS.2DA"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD_USPS_I_EXP_DMND"))));
                    }
                    try {
                        shipType = rater.selectBestWay(svcCodes);
                    } catch (Exception exx) {
                        if (order.getShippingAddress().isPOAPO() || order.getShippingAddress().isUS()) {
                            shipType = "USPS First-Class Mail";
                        } else {
                            shipType = "USPS First-Class Mail International";
                        }
                    }


                } else if (client.getClientId() == 474)//Gryndo
                {


                        if (shipType.toUpperCase().contains("3 DAY SELECT")) {
                            shipType = "UPS 3 Day Select";
                        } else if (shipType.toUpperCase().contains("2ND DAY AIR")) {
                            shipType = "UPS 2nd Day Air";
                        } else if (shipType.toUpperCase().contains("NEXT DAY AIR SAVER")) {
                            shipType = "UPS Next Day Air Saver";
                        } else if (shipType.toUpperCase().contains("NEXT DAY AIR")) {
                            shipType = "UPS Next Day Air";
                        } else if (shipType.toUpperCase().contains("CANADA")) {
                            shipType = "UPS Standard to Canada";
                        } else if (shipType.toUpperCase().contains("EXPEDITED")) {
                            shipType = "UPS Worldwide Expedited";
                        } else if (shipType.toUpperCase().contains("WORLDWIDE SAVER")) {
                            shipType = "UPS Worldwide Saver";
                        } else
                        {
                            shipType = "UPS Ground";
                        }




                } else if (client.getClientId() == 495)//GListen
                {


                    if (shipType.contains("Ground")) {
                        List<String> svcCodes = new ArrayList<String>();

                        OrderRater rater = new OrderRater(order);
                        rater.setCalculateKitWeights(true);

                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("UPS.GND"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD.1ST.PRIORITY"))));
                        svcCodes.add(OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef("OWD.1ST.LETTER"))));

                        try {
                            shipType = rater.selectBestWay(svcCodes);
                        } catch (Exception exx) {
                            shipType="USPS Priority Mail";
                        }
                    } else if (shipType.toUpperCase().contains("EXPRESS")) {
                        shipType = "USPS Express Mail";
                    } else
                    {
                        shipType="USPS Priority Mail";
                    }


                } else {

                    //throw new Exception("Shipping Methods not configured at OWD");

                }

                order.getShippingInfo().setShipOptions(shipType, "Prepaid", "");

            } catch (Exception ex) {

                throw new Exception("Shipping type / Carrier code... " + shipType + " not valid");


            }
            order.getShippingInfo().comments = order.getShippingInfo().comments + " " + getSafeSubNodeValue(orderElement, kCommentNodeName, "");

            if (order.getShippingInfo().comments.length() > 253)
                order.getShippingInfo().comments = order.getShippingInfo().comments.substring(0, 253);

            NodeList totalNodes = orderElement.getElementsByTagName(kTotalsNodeName);

            if (totalNodes != null) {

                if (totalNodes.getLength() > 0) {

                    for (int r = 0; r < totalNodes.getLength(); r++) {

                        Element totalElement = (Element) totalNodes.item(r);

                        NodeList lineNodes = totalElement.getElementsByTagName(kTotalsLineNodeName);

                        if (lineNodes != null) {

                            if (lineNodes.getLength() > 0) {

                                for (int l = 0; l < lineNodes.getLength(); l++) {

                                    Element lineElement = (Element) lineNodes.item(l);

                                    String lineType = lineElement.getAttribute(kTotalsLineType);


                                    float floater = (float) 0.0;

                                    try {

                                        floater = OWDUtilities.floatFromString(lineElement.getFirstChild().getNodeValue());

                                    } catch (Exception ex) {
                                    }


                                    if (kTotalGift.equals(lineType)) {

                                        order.total_gift_wrap = floater;

                                    } else if (kTotalSubtotal.equals(lineType)) {

                                        order.total_product_cost = floater;

                                    } else if (kTotalShipping.equals(lineType)) {

                                        order.total_shipping_cost = floater;

                                    } else if (kTotalTax.equals(lineType)) {

                                        order.total_tax_cost = floater;


                                    } else if (kTotal.equals(lineType)) {

                                        order.total_order_cost = floater;


                                    } else if (kTotalCoupon.equals(lineType)) {

                                        if (floater < 0)

                                            floater = floater * -1;


                                        order.discount = floater;

                                    } else if (kTotalDiscount.equals(lineType)) {

                                        order.discount = Math.abs(floater) * -1;

                                    }

                                }


                            } else {

                                //throw new Exception("No valid line items found in order");

                            }

                        } else {

                            //throw new Exception("No valid line items found in order");

                        }

                    }


                } else {

                    //throw new Exception("No valid line items found in order");

                }

            } else {

                //throw new Exception("No valid line items found in order");

            }

            //teamdepot special handling

            /*

                check for personalized items



                ??? what if *all* personalized items ???



                pull personalized items from order/leave original custom items as backordered

                ship order

                get backorder

                if backorder contains non-custom items, remove personalized items, otherwise void backorder

                (regular items taken care of)



                if custom items present, create new order for the items

                set ship to to contractor's address

                set shipping method to contractor

                set backorder status normally

                set print template to shipped inventory

                set backorder_from num to original order num

                ship order



                if send-in items,

                repeat as custom items

                set print template to teamdepot custom sendin template

                place order on hold





                create new order with personalized items

            */

            boolean dups = false;


           /* if (com.owd.api.client.client_id.equals("133")) {

                Vector personalizedItems = new Vector();

                Vector mailinItems = new Vector();

                //order.is_future_ship = 1;


                int origItems = order.skuList.size();

                for (int in = (origItems - 1); in >= 0; in--) {

                    if (((LineItem) order.skuList.elementAt(in)).long_desc.equals("Personalized")) {

                        personalizedItems.add(((LineItem) order.skuList.elementAt(in)));


                        order.skuList.remove(in);

                    } else if (((LineItem) order.skuList.elementAt(in)).long_desc.equals("Mailed in by Customer")) {

                        mailinItems.add(((LineItem) order.skuList.elementAt(in)));

                        //add item to inventory


                        order.skuList.remove(in);

                    }


                }

                //if p-items, create p-order

                if (personalizedItems.size() > 0) {

                    log.debug("in personalized order");
                    Order pOrder = new Order("133");

                    order.copyNewOrderVars(pOrder);

                    pOrder.skuList = personalizedItems;

                    // pOrder.noduplicates = false;

                    //set print template

                    pOrder.prt_pack_reqd = 0;

                    pOrder.prt_pick_reqd = 1;

                    pOrder.getShippingInfo().setShipOptions("TeamDepot Personalization", "Prepaid", "");

                    //set porder tag field

                    pOrder.po_num = "Personalized inventory item";

                    //pOrder.is_future_ship = 1;
                    log.debug("Saving personalized order " + pOrder.skuList.size());
                    String referenceP = pOrder.saveNewOrder(OrderUtilities.kHoldForPayment, false);

                    dups = true;

                    if (referenceP == null || pOrder.completed == false)

                        throw new Exception("Order Processing Error: " + pOrder.last_error);

                }

                //if m-items, create m-order

                if (mailinItems.size() > 0) {

                    log.debug("in mailin order");
                    Order mOrder = new Order("133");

                    order.copyNewOrderVars(mOrder);

                    mOrder.skuList = mailinItems;

                    //set print template

                    mOrder.prt_pack_reqd = 0;

                    mOrder.prt_pick_reqd = 1;

                    mOrder.getShippingInfo().setShipOptions("TeamDepot Personalization", "Prepaid", "");

                    mOrder.is_future_ship = 1;

                    //set porder tag field

                    mOrder.po_num = "Personalized mail-in customer item";

                    if (dups) mOrder.noduplicates = false;

                    String referenceM = mOrder.saveNewOrder(OrderUtilities.kHoldForPayment, false);


                    dups = true;

                    if (referenceM == null || mOrder.completed == false)

                        throw new Exception("Order Processing Error: " + mOrder.last_error);

                }


            }*/

            if (client.getClientId() == 304)//Razorama
            {
                //    order.postDateHoursDelay = com.owd.api.client.;

                order.backorder_rule = OrderXMLDoc.kBackOrderAll;
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;
                order.noVirtualOnlyOrders = false;

                order.recalculateBalance();
                if ((order.total_product_cost + (-1 * (Math.abs(order.discount)))) > 300.00) {
                    order.getShippingInfo().ss_declared_value = "1";
                    order.getShippingInfo().declared_value = "" + (order.total_product_cost + (-1 * (Math.abs(order.discount))));
                } else if ((order.total_product_cost + (-1 * (Math.abs(order.discount)))) > 100.00
                        && (order.containsSKU("15130661") ||
                        order.containsSKU("15130638") ||
                        order.containsSKU("13113640") ||
                        order.containsSKU("13116240"))) {


                    order.getShippingInfo().ss_declared_value = "1";
                    order.getShippingInfo().declared_value = "" + (order.total_product_cost + (-1 * (Math.abs(order.discount))));
                }

                if (!(order.getBillingAddress().address_one.equals(order.getShippingAddress().address_one)
                        && order.getBillingAddress().zip.equals(order.getShippingAddress().zip)) && order.total_order_cost >= 250.00) {
                    order.is_future_ship = 1;
                    order.po_num += ";fraud";
                    importNotes.append(" Held due to unmatched billing/shipping address and order total >= 250.00.");

                }

                if (order.getShippingAddress().isPOAPO()) {
                    //   order.is_future_ship = 1;
                    //   importNotes.append(" Held due to PO Box or APO shipping address.");
                    //     order.po_num+=";PO/APO";
                    //       Mailer.sendMail("Yahoo Order Import PO/APO Address Check","Order reference "+order.order_refnum+" was imported on hold due to a possible PO/APO address\n\n"
                    //                                ,"razorama@owd.com",com.owd.api.client.getAmEmail());

                }

                isAllVirtual = true;

                for (int k = 0; k < order.skuList.size(); k++) {
                    LineItem item = (LineItem) order.skuList.elementAt(k);
                    if (!(item.client_part_no.toUpperCase().indexOf("-DS") >= 0)) {
                        isAllVirtual = false;
                    } else {
                        order.po_num = "dropship";
                    }
                }

                if (isAllVirtual) {
                    order.is_future_ship = 1;
                    importNotes.append(" Held due to all dropship items.");

                    //  Mailer.sendMail("Yahoo All Dropship Order Received","Order reference "+order.order_refnum+" was imported on hold due to not having any non-dropship items\n\n"
                    //                      ,"razorama@owd.com",com.owd.api.client.getAmEmail());
                }

                if (order.getShippingAddress().country.equalsIgnoreCase("CANADA")
                        && (order.containsSKU("15130661") || order.containsSKU("15130661-DS")
                        || order.containsSKU("15130638") || order.containsSKU("151306380-DS")
                        || order.containsSKU("13301S-SL") || order.containsSKU("13113640-06")
                        || order.containsSKU("CL-13113640") || order.containsSKU("13116240-DS")
                        || order.containsSKU("13113640-DS") || order.containsSKU("CL-13116240")
                        || order.containsSKU("13116240") || order.containsSKU("13113640")
                        || order.containsSKU("13112730-DS") || order.containsSKU("13112430-DS")
                        || order.containsSKU("CL-13112430") || order.containsSKU("CL-13112430")
                        || order.containsSKU("13112430") || order.containsSKU("155001-RD-DS")
                        || order.containsSKU("CL-155001-RD") || order.containsSKU("155001-RD")
                        || order.containsSKU("15128050-DS") || order.containsSKU("CL-15128050")
                        || order.containsSKU("15128050") || order.containsSKU("15128190-DS")
                        || order.containsSKU("CL-15128190") || order.containsSKU("15165070-DS")
                        || order.containsSKU("15165070") || order.containsSKU("CL-25143060")
                        || order.containsSKU("25143060-DS") || order.containsSKU("25143060")
                        || order.containsSKU("25143511-DS") || order.containsSKU("25143511")
                        || order.containsSKU("300001-SL-DS") || order.containsSKU("CL-300001-SL")
                        || order.containsSKU("300001-SL") || order.containsSKU("15130412-DS")
                )) {
                    order.is_future_ship = 1;
                    importNotes.append(" Held due to Betty/Mint/e300/e300s match on Canadian destination");
                    order.po_num += ";Canada";
                }

                NodeList creditCardNodes = orderElement.getElementsByTagName(kCreditCardNodeName);
                String transCCExpiration = "";
                boolean isPaypal = false;

                if (creditCardNodes != null) {
                    Element ccElement = (Element) creditCardNodes.item(0);
                    if (ccElement != null) {
                        order.cc_num = getSafeNodeValue(ccElement, "");
                        if (order.cc_num.length() > 19) {
                            order.cc_num = "INVALID";
                        }
                        String expDate = ccElement.getAttribute(kCardExpDate);
                        isPaypal = "PAYPAL".equalsIgnoreCase(ccElement.getAttribute(kCardType));
                        if (expDate != null) {
                            if (expDate.indexOf("/") > 0) {
                                try {
                                    transCCExpiration = expDate.substring(0, 2) + expDate.substring(expDate.length() - 2, expDate.length());
                                    order.cc_exp_mon = new Integer(expDate.substring(0, expDate.indexOf("/"))).intValue();
                                    order.cc_exp_year = new Integer(expDate.substring(expDate.indexOf("/") + 1)).intValue();
                                } catch (Exception ex) {
                                }
                            }
                        }
                    }

                }


                NodeList cardEventsNodes = orderElement.getElementsByTagName(kCardEventsNodeName);

                if (cardEventsNodes != null) {
                    int cardEventCount = cardEventsNodes.getLength();
                    for (int a = 0; a < cardEventCount; a++) {
                        Element ccEventElement = (Element) cardEventsNodes.item(a);
                        if (ccEventElement != null) {
                            NodeList cardAuthNodes = ccEventElement.getElementsByTagName(kCardAuthNodeName);
                            if (cardAuthNodes != null) {
                                int cardAuthCount = cardAuthNodes.getLength();
                                //CC AUTHORIZATION NODES
                                log.debug("CARD AUTH SIZE=" + cardAuthCount);
                                for (int b = 0; b < cardAuthCount; b++) {
                                    Element ccAuthElement = (Element) cardAuthNodes.item(b);

                                    if (ccAuthElement != null) {


                                        if (isPaypal) {
                                            String status = ccAuthElement.getAttribute(kCardAuthPaypalAuth);
                                            if (status == null) status = "";
                                            FinancialTransaction trans = new FinancialTransaction("0",

                                                    "0",

                                                    "0",

                                                    OWDUtilities.getSQLDateForToday(),

                                                    "Yahoo Import",

                                                    "Yahoo Import",

                                                    OWDUtilities.getSQLDateForToday(),

                                                    new Float(ccAuthElement.getAttribute(kCardAuthAuthAmount)).floatValue(),

                                                    OWDUtilities.getSQLDateForToday(),

                                                    status.length() > 1 ? FinancialTransaction.TRANS_OK : FinancialTransaction.TRANS_DECLINE,

                                                    "" + FinancialTransaction.TRANS_PAYPAL,

                                                    1,

                                                    0,

                                                    "" + FinancialTransaction.transTypeAuthOnlyRequest,

                                                    OWDUtilities.getLastNameFromWholeName(order.getBillingContact().getName()),

                                                    OWDUtilities.getFirstNameFromWholeName(order.getBillingContact().getName()),

                                                    order.getBillingAddress().address_one,

                                                    order.getBillingAddress().address_two,

                                                    order.getBillingAddress().zip,

                                                    order.getBillingAddress().company_name,

                                                    order.getBillingAddress().city,

                                                    order.getBillingAddress().country,

                                                    order.getBillingAddress().state,

                                                    order.getBillingContact().email,

                                                    order.getBillingContact().phone,

                                                    "",

                                                    "0.00",

                                                    "0.00",

                                                    "",

                                                    ccAuthElement.getAttribute(kCardAuthPaypalAuth),

                                                    "XXX",

                                                    "",

                                                    "",

                                                    "0000",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    isPaypal ? "PAYPAL/YAHOO" : "YAHOO",

                                                    0,

                                                    0,

                                                    "",

                                                    "");
                                            transList.add(trans);
                                        } else {
                                            String avsCode = ccAuthElement.getAttribute(kCardAuthAVSCode);
                                            if (avsCode == null) avsCode = "";
                                            if (avsCode.startsWith("NN")) {
                                                order.is_future_ship = 1;
                                                importNotes.append(" Held due to bad AVS code [" + avsCode + "]");
                                                //       Mailer.sendMail("Yahoo Order Import Fraud Check","Order reference "+order.order_refnum+" was imported on hold due to a fraud check problem\n\n"
                                                //       +" Held due to bad AVS code ["+avsCode+"]","razorama@owd.com",com.owd.api.client.getAmEmail());
                                            }
                                            // yn = address yes, zip code no
                                            if (
                                                    avsCode.startsWith("YY")
                                                            &&
                                                            order.total_order_cost >= 250.00
                                                            &&
                                                            (order.getShippingInfo().carr_service.indexOf("UPS Next Day") >= 0
                                                                    || order.getShippingInfo().carr_service.indexOf("UPS 2nd Day") >= 0)
                                                            &&
                                                            (!(order.getBillingAddress().company_name.equals(order.getShippingAddress().company_name))
                                                                    || !(order.getBillingAddress().address_one.equals(order.getShippingAddress().address_one))
                                                                    || !(order.getBillingAddress().address_two.equals(order.getShippingAddress().address_two))
                                                                    || !(order.getBillingAddress().city.equals(order.getShippingAddress().city))
                                                                    || !(order.getBillingAddress().state.equals(order.getShippingAddress().state))
                                                                    || !(order.getBillingAddress().zip.equals(order.getShippingAddress().zip))
                                                                    || !(order.getBillingAddress().country.equals(order.getShippingAddress().country))
                                                                    || !(order.getBillingContact().getName().equals(order.getShippingContact().getName()))
                                                            )
                                                    ) {
                                                order.is_future_ship = 1;
                                                importNotes.append(" Held due to fraud check");
                                                order.po_num += ";fraud";
                                                //     Mailer.sendMail("Yahoo Order Import Fraud Check","Order reference "+order.order_refnum+" was imported on hold due to a fraud check problem\n\n"
                                                //     +" Held due to bad fraud check","razorama@owd.com",com.owd.api.client.getAmEmail());

                                            }

                                            if (
                                                    avsCode.startsWith("YY")
                                                            &&
                                                            order.total_order_cost >= 500.00
                                                            &&
                                                            (!(order.getBillingAddress().company_name.equals(order.getShippingAddress().company_name))
                                                                    || !(order.getBillingAddress().address_one.equals(order.getShippingAddress().address_one))
                                                                    || !(order.getBillingAddress().address_two.equals(order.getShippingAddress().address_two))
                                                                    || !(order.getBillingAddress().city.equals(order.getShippingAddress().city))
                                                                    || !(order.getBillingAddress().state.equals(order.getShippingAddress().state))
                                                                    || !(order.getBillingAddress().zip.equals(order.getShippingAddress().zip))
                                                                    || !(order.getBillingAddress().country.equals(order.getShippingAddress().country))
                                                                    || !(order.getBillingContact().getName().equals(order.getShippingContact().getName()))
                                                            )
                                                    ) {
                                                order.is_future_ship = 1;
                                                //todo casetracker
                                                importNotes.append(" Held due to fraud check");
                                                order.po_num += ";fraud";
                                                ///   Mailer.sendMail("Yahoo Order Import Fraud Check","Order reference "+order.order_refnum+" was imported on hold due to a fraud check problem\n\n"
                                                //    +" Held due to bad fraud check","razorama@owd.com",com.owd.api.client.getAmEmail());
                                            }
                                            if (
                                                    avsCode.startsWith("NY")
                                                            &&
                                                            order.total_order_cost >= 500.00
                                                            &&
                                                            ((order.getBillingAddress().company_name.equals(order.getShippingAddress().company_name))
                                                                    || (order.getBillingAddress().address_one.equals(order.getShippingAddress().address_one))
                                                                    || (order.getBillingAddress().address_two.equals(order.getShippingAddress().address_two))
                                                                    || (order.getBillingAddress().city.equals(order.getShippingAddress().city))
                                                                    || (order.getBillingAddress().state.equals(order.getShippingAddress().state))
                                                                    || (order.getBillingAddress().zip.equals(order.getShippingAddress().zip))
                                                                    || (order.getBillingAddress().country.equals(order.getShippingAddress().country))
                                                                    || (order.getBillingContact().getName().equals(order.getShippingContact().getName()))
                                                            )
                                                    ) {
                                                order.is_future_ship = 1;
                                                importNotes.append(" Held due to fraud check");
                                                order.po_num += ";fraud";
                                                //    Mailer.sendMail("Yahoo Order Import Fraud Check","Order reference "+order.order_refnum+" was imported on hold due to a fraud check problem\n\n"
                                                //    +" Held due to bad fraud check","razorama@owd.com",com.owd.api.client.getAmEmail());
                                            }

                                            String status = ccAuthElement.getAttribute(kCardAuthStatusCode);
                                            if (status == null) status = "";
                                            FinancialTransaction trans = new FinancialTransaction("0",

                                                    "0",

                                                    "0",

                                                    OWDUtilities.getSQLDateForToday(),

                                                    "Yahoo Import",

                                                    "Yahoo Import",

                                                    OWDUtilities.getSQLDateForToday(),

                                                    new Float(ccAuthElement.getAttribute(kCardAuthAuthAmount)).floatValue(),

                                                    OWDUtilities.getSQLDateForToday(),

                                                    status.equals("A") ? FinancialTransaction.TRANS_OK : FinancialTransaction.TRANS_DECLINE,

                                                    "" + FinancialTransaction.TRANS_CC,

                                                    1,

                                                    0,

                                                    "" + FinancialTransaction.transTypeAuthOnlyRequest,

                                                    OWDUtilities.getLastNameFromWholeName(order.getBillingContact().getName()),

                                                    OWDUtilities.getFirstNameFromWholeName(order.getBillingContact().getName()),

                                                    order.getBillingAddress().address_one,

                                                    order.getBillingAddress().address_two,

                                                    order.getBillingAddress().zip,

                                                    order.getBillingAddress().company_name,

                                                    order.getBillingAddress().city,

                                                    order.getBillingAddress().country,

                                                    order.getBillingAddress().state,

                                                    order.getBillingContact().email,

                                                    order.getBillingContact().phone,

                                                    "",

                                                    "0.00",

                                                    "0.00",

                                                    "",

                                                    ccAuthElement.getAttribute(kCardAuthAuthCode),

                                                    ccAuthElement.getAttribute(kCardAuthAVSCode),

                                                    "",

                                                    "",

                                                    transCCExpiration,

                                                    order.cc_num,

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    isPaypal ? "PAYPAL/YAHOO" : "YAHOO",

                                                    0,

                                                    0,

                                                    "",

                                                    ccAuthElement.getAttribute(kCardAuthCVVCode));
                                            transList.add(trans);
                                        }

                                    }
                                }
                            }

                            //CC EVENT NODES
                            NodeList cardEventNodes = ccEventElement.getElementsByTagName(kCardEventNodeName);
                            if (cardEventNodes != null) {
                                int cardEvtCount = cardEventNodes.getLength();
                                //CC AUTHORIZATION NODES
                                for (int b = 0; b < cardEvtCount; b++) {
                                    Element ccEvtElement = (Element) cardEventNodes.item(b);

                                    if (ccEvtElement != null) {
                                        int amountMultiplier = 1;

                                        //  String avsCode = ccEvtElement.getAttribute(kCardAuthAVSCode);

                                        String type = ccEvtElement.getAttribute(kCardEventType);
                                        type = (String) yahooCCTransactionTypeMap.get(type);
                                        if (type.startsWith("-")) {
                                            amountMultiplier = new Integer(type.substring(0, 1) + 1).intValue();
                                            type = type.substring(1);
                                        }
                                        if (isPaypal) {
                                            //paypal
                                            FinancialTransaction trans = new FinancialTransaction("0",

                                                    "0",

                                                    "0",

                                                    OWDUtilities.getSQLDateForToday(),

                                                    "Yahoo Import",

                                                    "Yahoo Import",

                                                    OWDUtilities.getSQLDateForToday(),

                                                    amountMultiplier * new Float(ccEvtElement.getAttribute(kCardEventAmount)).floatValue(),

                                                    OWDUtilities.getSQLDateForToday(),

                                                    FinancialTransaction.TRANS_OK,

                                                    "" + FinancialTransaction.TRANS_PAYPAL,

                                                    1,

                                                    0,

                                                    type,

                                                    OWDUtilities.getLastNameFromWholeName(order.getBillingContact().getName()),

                                                    OWDUtilities.getFirstNameFromWholeName(order.getBillingContact().getName()),

                                                    order.getBillingAddress().address_one,

                                                    order.getBillingAddress().address_two,

                                                    order.getBillingAddress().zip,

                                                    order.getBillingAddress().company_name,

                                                    order.getBillingAddress().city,

                                                    order.getBillingAddress().country,

                                                    order.getBillingAddress().state,

                                                    order.getBillingContact().email,

                                                    order.getBillingContact().phone,

                                                    "",

                                                    "0.00",

                                                    "0.00",

                                                    ccEvtElement.getAttribute(kCardEventPaypalTransId),

                                                    ccEvtElement.getAttribute(kCardEventPaypalAuth),

                                                    "XXX",

                                                    "",

                                                    "",

                                                    "0000",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    isPaypal ? "PAYPAL/YAHOO" : "YAHOO",

                                                    0,

                                                    0,

                                                    "",

                                                    "");
                                            transList.add(trans);
                                        } else {

                                            FinancialTransaction trans = new FinancialTransaction("0",

                                                    "0",

                                                    "0",

                                                    OWDUtilities.getSQLDateForToday(),

                                                    "Yahoo Import",

                                                    "Yahoo Import",

                                                    OWDUtilities.getSQLDateForToday(),

                                                    amountMultiplier * new Float(ccEvtElement.getAttribute(kCardEventAmount)).floatValue(),

                                                    OWDUtilities.getSQLDateForToday(),

                                                    FinancialTransaction.TRANS_OK,

                                                    "" + FinancialTransaction.TRANS_CC,

                                                    1,

                                                    0,

                                                    type,

                                                    OWDUtilities.getLastNameFromWholeName(order.getBillingContact().getName()),

                                                    OWDUtilities.getFirstNameFromWholeName(order.getBillingContact().getName()),

                                                    order.getBillingAddress().address_one,

                                                    order.getBillingAddress().address_two,

                                                    order.getBillingAddress().zip,

                                                    order.getBillingAddress().company_name,

                                                    order.getBillingAddress().city,

                                                    order.getBillingAddress().country,

                                                    order.getBillingAddress().state,

                                                    order.getBillingContact().email,

                                                    order.getBillingContact().phone,

                                                    "",

                                                    "0.00",

                                                    "0.00",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    transCCExpiration,

                                                    order.cc_num,

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    "",

                                                    isPaypal ? "PAYPAL/YAHOO" : "YAHOO",


                                                    0,

                                                    0,

                                                    "",

                                                    "");
                                            transList.add(trans);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }


            if (client.getClientId() == 474) {
                order.backorder_rule = OrderXMLDoc.kBackOrderAll;
                //  if(order.total_order_cost>=200.00)
                //  {
                NodeList cardEventsNodes = orderElement.getElementsByTagName(kCardEventsNodeName);

                if (cardEventsNodes != null) {
                    int cardEventCount = cardEventsNodes.getLength();
                    for (int a = 0; a < cardEventCount; a++) {
                        Element ccEventElement = (Element) cardEventsNodes.item(a);
                        if (ccEventElement != null) {
                            NodeList cardAuthNodes = ccEventElement.getElementsByTagName(kCardAuthNodeName);
                            if (cardAuthNodes != null) {
                                int cardAuthCount = cardAuthNodes.getLength();
                                //CC AUTHORIZATION NODES
                                log.debug("CARD AUTH SIZE=" + cardAuthCount);
                                for (int b = 0; b < cardAuthCount; b++) {
                                    Element ccAuthElement = (Element) cardAuthNodes.item(b);
                                    if (ccAuthElement != null) {

                                        String paypalAuth = ccAuthElement.getAttribute("paypal-auth");
                                        if (paypalAuth == null) {
                                            paypalAuth = "";
                                        }
                                        if (paypalAuth.length() < 5 && order.total_order_cost > 200.00) {


                                            String avsCode = ccAuthElement.getAttribute(kCardAuthAVSCode);
                                            if (avsCode == null) avsCode = "";
                                            if ((!(avsCode.startsWith("Y"))) && (!(avsCode.startsWith("YN")))
                                                    && (!(avsCode.endsWith("A"))) && (!(avsCode.endsWith("B")))
                                                    && (!("D".equalsIgnoreCase(avsCode)))
                                                    && (!("X".equalsIgnoreCase(avsCode)))
                                                    && (!("B".equalsIgnoreCase(avsCode)))
                                                    && (!("F".equalsIgnoreCase(avsCode)))
                                                    && (!("M".equalsIgnoreCase(avsCode)))
                                                    ) {
                                                order.is_future_ship = 1;
                                                importNotes.append(" Held due to bad AVS code [" + avsCode + "]");
                                                Mailer.sendMail("Yahoo Order Import Fraud Check", "Order reference " + order.order_refnum + " was imported on hold due to a fraud check problem\n\n"
                                                        + " Held due to bad AVS code [" + avsCode + "]", "melanie@razorama.com", client.getAmEmail());
                                            }
                                            String cvvCode = ccAuthElement.getAttribute(kCardAuthCVVCode);
                                            if (cvvCode == null) cvvCode = "";
                                            if ((!(cvvCode.startsWith("M"))) && (!(cvvCode.equals("")))) {
                                                order.is_future_ship = 1;
                                                importNotes.append(" Held due to bad CVV code [" + avsCode + "]");
                                                Mailer.sendMail("Yahoo Order Import Fraud Check", "Order reference " + order.order_refnum + " was imported on hold due to a fraud check problem\n\n"
                                                        + " Held due to bad CVV code [" + avsCode + "]", "melanie@razorama.com", client.getAmEmail());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // }


                if (!(Character.isDigit(order.getShippingAddress().address_one.trim().charAt(0)))) {
                    order.is_future_ship = 1;
                    importNotes.append(" Held due to shipping address not beginning with a numeric character");
                    Mailer.sendMail("Yahoo Order Import Fraud Check", "Order reference " + order.order_refnum + " was imported on hold due to a fraud check problem\n\n"
                            + "Held due to shipping address not beginning with a numeric character", "melanie@razorama.com", client.getAmEmail());
                }

                if (order.getShippingAddress().isCanada() && order.total_product_cost > 99.99) {
                    order.is_future_ship = 1;
                    importNotes.append(" Held due to Canadian orders rule");
                    Mailer.sendMail("Yahoo Order Import Canada Check", "Order reference " + order.order_refnum + " was imported on hold due to a Canadian shipping address and a subtotal over $100.00\n\n"
                            + "Held due to shipping address to Canada", "melanie@razorama.com", client.getAmEmail());

                }
            }

            if (client.getClientId() == 495) {
                order.backorder_rule = OrderXMLDoc.kBackOrderAll;
                //  if(order.total_order_cost>=200.00)
                //  {
                NodeList cardEventsNodes = orderElement.getElementsByTagName(kCardEventsNodeName);

                if (cardEventsNodes != null) {
                    int cardEventCount = cardEventsNodes.getLength();
                    for (int a = 0; a < cardEventCount; a++) {
                        Element ccEventElement = (Element) cardEventsNodes.item(a);
                        if (ccEventElement != null) {
                            NodeList cardAuthNodes = ccEventElement.getElementsByTagName(kCardAuthNodeName);
                            if (cardAuthNodes != null) {
                                int cardAuthCount = cardAuthNodes.getLength();
                                //CC AUTHORIZATION NODES
                                log.debug("CARD AUTH SIZE=" + cardAuthCount);
                                for (int b = 0; b < cardAuthCount; b++) {
                                    Element ccAuthElement = (Element) cardAuthNodes.item(b);
                                    if (ccAuthElement != null) {

                                        String paypalAuth = ccAuthElement.getAttribute("paypal-auth");
                                        if (paypalAuth == null) {
                                            paypalAuth = "";
                                        }
                                        if (paypalAuth.length() < 5 && order.total_order_cost > 200.00) {


                                            String avsCode = ccAuthElement.getAttribute(kCardAuthAVSCode);
                                            if (avsCode == null) avsCode = "";
                                            if ((!(avsCode.startsWith("Y"))) && (!(avsCode.startsWith("YN")))
                                                    && (!(avsCode.endsWith("A"))) && (!(avsCode.endsWith("B")))
                                                    && (!("D".equalsIgnoreCase(avsCode)))
                                                    && (!("X".equalsIgnoreCase(avsCode)))
                                                    && (!("B".equalsIgnoreCase(avsCode)))
                                                    && (!("F".equalsIgnoreCase(avsCode)))
                                                    && (!("M".equalsIgnoreCase(avsCode)))
                                                    ) {
                                                order.is_future_ship = 1;
                                                importNotes.append(" Held due to bad AVS code [" + avsCode + "]");
                                                Mailer.sendMail("Yahoo Order Import Fraud Check", "Order reference " + order.order_refnum + " was imported on hold due to a fraud check problem\n\n"
                                                        + " Held due to bad AVS code [" + avsCode + "]", "tommy@glisten.com", client.getAmEmail());
                                            }
                                            String cvvCode = ccAuthElement.getAttribute(kCardAuthCVVCode);
                                            if (cvvCode == null) cvvCode = "";
                                            if ((!(cvvCode.startsWith("M"))) && (!(cvvCode.equals("")))) {
                                                order.is_future_ship = 1;
                                                importNotes.append(" Held due to bad CVV code [" + avsCode + "]");
                                                Mailer.sendMail("Yahoo Order Import Fraud Check", "Order reference " + order.order_refnum + " was imported on hold due to a fraud check problem\n\n"
                                                        + " Held due to bad CVV code [" + avsCode + "]", "tommy@glisten.com", client.getAmEmail());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // }


                if (!(Character.isDigit(order.getShippingAddress().address_one.trim().charAt(0)))) {
                    order.is_future_ship = 1;
                    importNotes.append(" Held due to shipping address not beginning with a numeric character");
                    Mailer.sendMail("Yahoo Order Import Fraud Check", "Order reference " + order.order_refnum + " was imported on hold due to a fraud check problem\n\n"
                            + "Held due to shipping address not beginning with a numeric character", "melanie@razorama.com", client.getAmEmail());
                }


            }

            if (order.skuList.size() > 0) {

                if (dups) order.noduplicates = false;

                log.debug("in standard order");
                String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, false);


                if (reference == null || order.completed == false) {

                    if (client.getClientId() == 304) {
                        //razorama

                        //    Mailer.sendMail("Yahoo Order Import Error","Order reference "+order.order_refnum+" was not imported from Yahoo. The error was: "+order.last_error+"\n\n"
                        //                             ,"razorama@owd.com",com.owd.api.client.getAmEmail());
                    }
                    if (!order.last_error.contains("no physical items included in shipment")) {
                        throw new Exception("Order Processing Error: " + order.last_error);
                    }

                } else {

                    try {
                        Iterator it = transList.iterator();
                        while (it.hasNext()) {
                            FinancialTransaction trans = (FinancialTransaction) it.next();
                            trans.order_fkey = order.getID();
                            trans.dbsave( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());
                        }
                        HibUtils.commit(HibernateSession.currentSession());

                        if (importNotes.length() > 0) {

                            Event.addOrderEvent( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), new Integer(order.getID()).intValue(), Event.kEventTypeGeneral,
                                    "Yahoo Import Notes: " + importNotes.toString(), "Yahoo Order Importer");
                            HibUtils.commit(HibernateSession.currentSession());

                        }

                        if (order.is_backorder == 1 && reference != null && client.getClientId() == 445) {
                            Mailer.sendMail("Backorder imported at OWD from Yahoo store", "Order reference " + order.order_refnum + " was imported to OWD as an active backorder. Please check if any manual intervention is required.", "customerservice@imnotacougar.com", "donotreply@owd.com");

                        }
                        //razorama

                        if (isAllVirtual && client.getClientId() == 304 && order.is_future_ship == 1) {

                            order.voidOrder();
                        }

                        if(client.getClientId()==495)
                        {
                            GlistenSupport.sendOrderConfirmationEmail(Integer.decode(order.orderID),order.getBillingContact().email,getServletContext());
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        HibernateSession.closeSession();
                    }

                    /*  if (com.owd.api.client.client_id.equals("159"))
                                        try {
                                            String[] toAddresses = {"allison@ruoutside.com"};
                                            if (order.is_future_ship == 1) {
                                                String asubject = "RUOutside order placed on hold";
                                                String amessage = "OWD Order reference " + order.orderNum + " has been placed on hold. Please check the order on the OWD extranet for details.";

                                                Mailer.sendMail(asubject, amessage, toAddresses, "do_not_reply@owd.com");
                                            }
                                        } catch (Exception ex) {
                                        }
                    */
                }
            } else {
                throw new Exception("No shippable SKUs found in order creation request");
            }

            /*

            if(order.skuList.size()>0 && com.owd.api.client.client_id.equals("133"))

            {

                Connection cxn = null;

                try

                {

                cxn = com.owd.managers.ConnectionManager.getConnection();

                for(int i2=0;i2<order.skuList.size();i2++)

                {

                    Inventory item = Inventory.dbloadByPart(cxn,((LineItem)order.skuList.elementAt(i2)).client_part_no,"136");

                    item.addToAvailability(cxn,((int)((LineItem)order.skuList.elementAt(i2)).quantity_request)*-1);



                }

                cxn.commit();

                cxn.close();

                }catch(Exception ex)

                {

                    ex.printStackTrace();

                }finally

                {

                    try{cxn.close();}catch(Exception exc){}

                }

            }

            */

        }


    }


    private void PuddledancerItemCustomization(YahooItemData item, OwdClient client, Order order) throws Exception {

        boolean skuHandled = false;

        item.setPart(LineItem.getCleanSKU(item.getPart()));

        log.debug("got pd sku " + item.getPart());
        if (item.getPart().toUpperCase().startsWith("KIT-")) {
            log.debug("adding " + item.getQty() + " units to SKU " + item.getPart());
            Inventory iitem = Inventory.dbloadByPart(item.getPart(), "311");
            iitem.addToAvailabilityAtFacility(item.getQty(), FacilitiesManager.getLocationCodeForClient(client.getClientId()));

        }

        if (item.getPart().startsWith("kit-pdp-") && item.getPart().length() >= 13) {

            log.debug("possible auto-kit part " + item.getPart());
            String partNum = item.getPart().substring(4);
            String sku = partNum.substring(0, 8);
            String code = partNum.substring(8);

            log.debug("breakdown:" + item.getPart() + ":" + partNum + ":" + sku + ":" + code);

            if (LineItem.SKUExists(client.getClientId() + "", sku)) {

                //is it a multiple?
                if (code.startsWith("x")) {


                    try {
                        int multiple = new Integer(code.substring(1)).intValue();
                        order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                        ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                        order.addLineItem(sku, multiple * item.getQty(), 0.00f, 0.00f, "", "", "");
                        skuHandled = true;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else if (code.startsWith("-")) {
                    order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                    ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                    order.addLineItem(sku, item.getQty(), 0.00f, 0.00f, "", "", "");
                    skuHandled = true;
                } else {


                }
                //is it a straight discount or tracking SKU?

                //if none of the above, proceed with normal checks and insertion

            }
        }

        if (!(skuHandled)) {

            log.debug("Sku not handled, checking for regular kits for part " + item.getPart());

            if (item.getPart().equals("kit-pdp-acr-rcs")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0505", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0311", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0312", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0507", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0317", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-nep")) {
                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0303", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0302", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0316", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-nep50")) {
                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0303", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0302", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0316", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-nerp50")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0505", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0311", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0317", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-nerp")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0505", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0311", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0317", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-npghp")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0506", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0311", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0504", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-npghp50")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0506", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0311", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0504", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-npp")) {
                //NVC Parent Package: 1 NVC, 1 PFYH, 1 RCC, 1 SP

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0314", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0144", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0605", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-npp50")) {
                //NVC Parent Package: 1 NVC, 1 PFYH, 1 RCC, 1 SP

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0314", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0144", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0605", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-nsc")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0310", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0318", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0503", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");

            } else if (item.getPart().equals("kit-pdp-nsk") || item.getPart().equals("kit-pdp-nskspc") || item.getPart().equals("kit-pdp-nsksp")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0312", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0313", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0503", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");

            } else if (item.getPart().equals("kit-pdp-rcrp")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0311", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0317", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0507", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-gophs")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0503", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0504", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0310", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-rcrp50")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0311", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0317", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0507", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-nerp2")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0505", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0701", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0317", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-nerp250")) {

                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0505", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0701", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0317", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else if (item.getPart().equals("kit-pdp-all50")) {
                //kit-pdp-all50 | pdp-0144, pdp-0302, pdp-0303, pdp-0310, pdp-0311,
                // pdp-0312, pdp-0313, pdp-0314, pdp-0315, pdp-0316, pdp-0317, pdp-0318,
                //  pdp-0503, pdp-0504, pdp-0505, pdp-0506, pdp-0507, pdp-0605, pdp-0701
                order.addLineItem(item.getPart(), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), "", "");
                ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                order.addLineItem("pdp-0144", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0302", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0303", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0310", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0311", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0312", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0313", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0314", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0315", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0316", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0317", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0318", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0503", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0504", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0505", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0506", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0507", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0605", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
                order.addLineItem("pdp-0701", "" + (1 * item.getQty()), "0.00", "0.00", "", "", "");
            } else {
                log.debug("Adding normal line item");
                order.addLineItem(LineItem.getCleanSKU(item.getPart()), item.getQty(), item.getCost(), item.getCost() * item.getQty(), item.getDesc(), item.getColor(), item.getSize());
                if (order.skuList.size() > 0) {
                    ((LineItem) order.skuList.elementAt(order.skuList.size() - 1)).client_ref_num = item.getYahooLineItemID();
                }


            }
        }
    }

    private void RazoramaItemCustomization(YahooItemData item, List yahooItems) {

        if (item.getPart().indexOf("_") > 0) {
            //possible dual-SKU entry
            String[] test = item.getPart().split("_");
            item.setPart(test[0]);
            for (int i = 1; i < test.length; i++) {
                YahooItemData newItem = new YahooItemData();
                newItem.setPart(test[i]);
                newItem.setCost(0.00f);
                newItem.setDesc("");
                newItem.setQty(item.getQty());
                newItem.setYahooLineItemID(item.getYahooLineItemID());
                yahooItems.add(newItem);
            }


        } else if (item.getPart().indexOf("XX") > 0) {
            //possible dual-SKU entry
            String[] test = item.getPart().split("XX");
            item.setPart(test[0]);
            for (int i = 1; i < test.length; i++) {
                YahooItemData newItem = new YahooItemData();
                newItem.setPart(test[i]);
                newItem.setCost(0.00f);
                newItem.setDesc("");
                newItem.setQty(item.getQty());
                newItem.setYahooLineItemID(item.getYahooLineItemID());
                yahooItems.add(newItem);
            }


        }
    }

    private void TeamDepotItemCustomization(YahooItemData item, Element itemElement) {
        item.setColor("-");

        item.setSize("-");


        NodeList optionNodes = itemElement.getElementsByTagName(kOptionsNodeName);

        if (optionNodes != null) {

            if (optionNodes.getLength() > 0) {

                for (int r2 = 0; r2 < optionNodes.getLength(); r2++) {

                    //////log.debug("checking option node "+r2);

                    Element optionElement = (Element) optionNodes.item(r2);


                    String optionName = optionElement.getAttribute("name");

                    //////log.debug("checking option node "+r2+", name="+optionName);

                    String optionValue = getSafeNodeValue(optionElement, "");

                    //////log.debug("checking option node "+r2+", name="+optionName+", value="+optionValue);

                    if (optionName.equalsIgnoreCase("size")) {

                        item.setPart(item.getPart() + optionValue);

                    } else if (optionValue.equalsIgnoreCase("C") || optionValue.equalsIgnoreCase("A")) {

                        item.setColor(optionValue);

                        //personalized=true;

                    } else if (!(optionValue.equals("") || optionValue.equals("-"))) {

                        if (optionName.indexOf("Team") >= 0) {

                            item.setDesc(item.getDesc() + "/" + optionValue);

                            item.setMailin(true);

                        } else if (optionName.indexOf("Home/Road") >= 0) {

                            item.setDesc(item.getDesc() + "/" + optionValue);

                            item.setMailin(true);

                        } else if (!(optionValue.equalsIgnoreCase("None"))) {

                            if (optionName.indexOf("Name") >= 0) {

                                item.setSize(optionValue);

                                item.setPersonalized(true);

                            }

                        }

                    }

                }

            }

        }
    }


    String getSafeSubNodeValue(Element elementNode, String subNodeName, String defaultValue) {

        if (defaultValue == null) defaultValue = "";

        //////log.debug("getting node value name="+subNodeName);

        try {

            if (subNodeName == null || ("".equals(subNodeName))) throw new Exception("subnodename invalid");


            try {


                Node dataNode = elementNode.getElementsByTagName(subNodeName).item(0);


                StringBuffer response = new StringBuffer();

                Node child = dataNode.getChildNodes().item(0);

                do {

                    //////log.debug("got node "+child.getNodeName()+":"+child.getNodeType());

                    if (child.getNodeType() == 5 || child.getNodeType() == Node.TEXT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE) {

                        //////log.debug("printing node "+child.getNodeName()+":"+child.getNodeType());

                        if (child.getNodeName().toUpperCase().equals("LT")) {

                            response.append(" <");

                        } else if (child.getNodeName().toUpperCase().equals("GT")) {

                            response.append(">");

                        } else {

                            if (child.getNodeType() != 5)

                                response.append(child.getNodeValue().trim());

                        }

                    }

                    child = child.getNextSibling();

                } while (child != null);


                return response.toString();

            } catch (NullPointerException ex) {

                return defaultValue;

            }

        } catch (Throwable th) {

            th.printStackTrace();

            return defaultValue;

        }


    }


    String getSafeNodeValue(Element dataNode, String defaultValue) {

        if (defaultValue == null) defaultValue = "";

        //////log.debug("getting node value name="+subNodeName);

        try {

            StringBuffer response = new StringBuffer();

            Node child = dataNode.getChildNodes().item(0);

            do {

                //////log.debug("got node "+child.getNodeName()+":"+child.getNodeType());

                if (child.getNodeType() == 5 || child.getNodeType() == Node.TEXT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE) {

                    //////log.debug("printing node "+child.getNodeName()+":"+child.getNodeType());

                    if (child.getNodeName().toUpperCase().equals("LT")) {

                        response.append(" <");

                    } else if (child.getNodeName().toUpperCase().equals("GT")) {

                        response.append(">");

                    } else {

                        if (child.getNodeType() != 5)

                            response.append(child.getNodeValue().trim());

                    }

                }

                child = child.getNextSibling();

            } while (child != null);


            return response.toString();


        } catch (Throwable th) {

            th.printStackTrace();

            return defaultValue;

        }


    }

    String getSafeSubSubNodeValue(Element elementNode, String subSubNodeName, String subNodeName, String defaultValue) {

        if (defaultValue == null) defaultValue = "";

        //////log.debug("getting subnode value name="+subNodeName+":"+subSubNodeName);

        try {

            if (subNodeName == null || ("".equals(subNodeName))) throw new Exception();


            NodeList subs = elementNode.getElementsByTagName(subNodeName);

            if (subs == null) throw new Exception();

            return getSafeSubNodeValue((Element) subs.item(0), subSubNodeName, defaultValue);

        } catch (Throwable th) {

            th.printStackTrace();

            return defaultValue;

        }


    }


    static public String getYahooMessage(String clientID, String code, String id) {

        String message = "";


        if (clientID.equals("495")) //glisten
        {

            try {

                String sku = code;
                if("495".equals(clientID))
                {
                    sku= id;
                }

               message="Item is out of stock. Typically ships in 1-2 weeks";

            } catch (Exception ex) {

            }
        }


        return message;


    }


    static public int getYahooInventory(String clientID, String catalog, String code, String id, String quantity, String price) {


        PreparedStatement stmt = null;

        ResultSet rs = null;

        int count = -1;


        if (code == null) code = "";
        if (id == null) id = "";
        if (quantity == null) quantity = "0";
        if (catalog == null) catalog = "";
        if (price == null) price = "00";


        try {
            log.debug("YAHOOINVENTORYCHECK");
            if ("304".equals(clientID)) {


                Map<String, Integer> skus = new TreeMap<String, Integer>();

                String test = code;
                if (test.contains("XX")) {
                    skus.put(test.substring(0, test.indexOf("XX")), 0);
                    skus.put(test.substring(test.indexOf("XX") + 2), 0);
                } else {
                    skus.put(test, 0);
                }


            for(String sku:skus.keySet())
            {
                 WebResource omserver = new WebResource("https://api.omx.ordermotion.com/hdde/xml/yiir.asp?Info=Available&HTTPBizID=svSYjSNlfGxaZOLGPaEUUOaaqcQRKGUghhfoTIOUuBGjOjEMtyACrBQJlghHGLfvuXQgfJtPhJoIevvjBIwuCCcMcPhwOsDOjefPdXGmdDZIiZiYnaSlaQYygsfaPLAlPtIsYunIVPZTfERHWoiUEogleDUloKMiikKJajpfvRLfxtctwWygNfOpJTBSjqbWKhIqfqIbeiGVQviDfpEwhpCeMNDAHODEsPelWTeNFydnQLAregMgGVwwyQrcLWg",WebResource.kPOSTMethod);
            //todo
            omserver.addParameter(".catalog",catalog);
            omserver.addParameter(".code",sku);
            omserver.addParameter(".id",id);
            omserver.addParameter(".quantity",quantity);
            omserver.addParameter(".price",price);

                    BufferedReader response = omserver.getResource();

                    log.debug("got om response:" + response);


                    try {
                        skus.put(sku, Integer.parseInt(omserver.avail));


                    } catch (Exception ex) {
                        ex.printStackTrace();


                    }
                }


                for (String sku : skus.keySet()) {

                    String asnsql = "select ISNULL(expected-received,0) from asn \n" +
                            "join asn_items \n" +
                            "on asn.id=asn_fkey where client_fkey=304 and ( status=0 or status=2)\n" +
                            "and (expected-received)>0 and inventory_num='" + sku + "'";

                    ResultSet crs = HibernateSession.getResultSet(asnsql);
                    if (crs != null) {
                        if (crs.next()) {
                            log.debug("pending qty " + crs.getInt(1));
                            log.debug("curr qty " + skus.get(sku));
                            skus.put(sku, skus.get(sku) + crs.getInt(1));
                        }
                    }
                }

                HibernateSession.closeSession();

                if (skus.size() > 0) {
                    count = (Integer) skus.values().toArray()[0];
                }
                for (String sku : skus.keySet()) {
                    if (skus.get(sku) < count) {
                        count = skus.get(sku);
                    }
                }

                log.debug("razor return qty " + count);

                return count;

            } else if ("474".equals(clientID))  //Gryndo
            {


                Map<String, Integer> skus = new TreeMap<String, Integer>();

                String test = code;
                if (test.contains("XX")) {
                    skus.put(test.substring(0, test.indexOf("XX")), 0);
                    skus.put(test.substring(test.indexOf("XX") + 2), 0);
                } else {
                    skus.put(test, 0);
                }


                log.debug("skus=" + skus);
                for (String sku : skus.keySet()) {
                    String esql = "select  qty_on_hand  from owd_inventory_oh (NOLOCK) join owd_inventory (NOLOCK) on (inventory_id = inventory_fkey "

                            + " and inventory_num = ? and client_fkey = ?)";

                    log.debug(esql);


                    stmt = HibernateSession.getPreparedStatement(esql);
                    stmt.setString(1, sku);
                    stmt.setInt(2, Integer.parseInt(clientID));

                    boolean hasResultSet = stmt.execute();


                    if (hasResultSet) {


                        rs = stmt.getResultSet();


                        if (rs.next()) {

                            log.debug("got sku qty " + rs.getInt(1));
                            skus.put(sku, rs.getInt(1));

                        }

                        rs.close();

                    } else {
                        log.debug("Skus not found");
                    }
                }
                log.debug("skus=" + skus);

                for (String sku : skus.keySet()) {

                    String asnsql = "select ISNULL(expected-received,0) from asn \n" +
                            "join asn_items \n" +
                            "on asn.id=asn_fkey where client_fkey=474 and ( status=0 or status=2)\n" +
                            "and (expected-received)>0 and inventory_num='" + sku + "'";

                    ResultSet crs = HibernateSession.getResultSet(asnsql);
                    if (crs != null) {
                        if (crs.next()) {
                            log.debug("pending qty " + crs.getInt(1));
                            log.debug("curr qty " + skus.get(sku));
                            skus.put(sku, skus.get(sku) + crs.getInt(1));
                        }
                    }
                }

                HibernateSession.closeSession();

                if (skus.size() > 0) {
                    count = (Integer) skus.values().toArray()[0];
                }
                for (String sku : skus.keySet()) {
                    if (skus.get(sku) < count) {
                        count = skus.get(sku);
                    }
                }

                log.debug("gryndo return qty " + count);

                return count;

            } else {
                String sku = code;
                if("495".equals(clientID))
                {
                    sku= id;
                }


                String esql = "select  qty_on_hand  from owd_inventory_oh (NOLOCK) join owd_inventory (NOLOCK) on (inventory_id = inventory_fkey "

                        + " and inventory_num = ? and client_fkey = ?)";

                log.debug(esql);


                stmt = HibernateSession.getPreparedStatement(esql);
                stmt.setString(1, sku);
                stmt.setInt(2, Integer.parseInt(clientID));

                boolean hasResultSet = stmt.execute();


                if (hasResultSet) {


                    rs = stmt.getResultSet();


                    if (rs.next()) {

                        count = rs.getInt(1);

                    }

                    rs.close();

                }


            }
        } catch (Exception ex) {

            ex.printStackTrace();
            //  Mailer.postMailMessage("Bad Yahoo Inventory Count Request", "", "noop@owd.com", "noop@owd.com");


        } finally {

            try {
                HibUtils.rollback(HibernateSession.currentSession());
            } catch (Exception ex) {
            }

            try {
                HibernateSession.closeSession();
            } catch (Exception ex) {
            }


            return count;

        }

    }


    static public String getTeamDepotInventory(String sku) throws Exception {

        WebResource web = new WebResource("http://kohlpacking.nayan.org/ypost-inventory.htm?site=48", "POST");

        web.addParameter(".code", sku);

        web.addParameter(".item", sku);

        BufferedReader response = web.getResource();

        response.close();

        return web.avail;


    }


    boolean isCatalogOrder(Order order) {
        String catalogSKUInitCharacter = "9";
        boolean isCatalogOrder = true;

        for (int i = 0; i < order.skuList.size(); i++) {
            LineItem item = (LineItem) order.skuList.get(i);
            if (!(item.client_part_no.startsWith(catalogSKUInitCharacter))) {
                isCatalogOrder = false;
            }
        }
        if (order.skuList.size() < 1)
            isCatalogOrder = false;

        return isCatalogOrder;
    }


    static public void main(String[] args) {

        try {


               log.debug("count=" + getYahooInventory("495", "grryndo", "201-934", "glominerals-luxury-hand-creamx", "", ""));

            log.debug("495:" + OWDUtilities.encryptData("495"));

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public class UnableToRateShipmentException extends Exception {
    }

    public class YahooItemData {
        String yahooLineItemID = "0";
        String part = "";
        String desc = "";
        float cost = 0.00f;
        int qty = 0;
        String color = "";
        String size = "";

        public boolean isPersonalized() {
            return personalized;
        }

        public void setPersonalized(boolean personalized) {
            this.personalized = personalized;
        }

        public boolean isMailin() {
            return mailin;
        }

        public void setMailin(boolean mailin) {
            this.mailin = mailin;
        }

        boolean personalized = false;

        boolean mailin = false;

        public String getYahooLineItemID() {
            return yahooLineItemID;
        }

        public void setYahooLineItemID(String yahooLineItemID) {
            this.yahooLineItemID = yahooLineItemID;
        }

        public String getPart() {
            return part;
        }

        public void setPart(String part) {
            this.part = part;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public float getCost() {
            return cost;
        }

        public void setCost(float cost) {
            this.cost = cost;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}

