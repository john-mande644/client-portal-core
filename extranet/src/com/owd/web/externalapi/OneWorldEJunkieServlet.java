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
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/* URL request model



    https://secure.owd.com/ejunkie -> servlet path

        .../<clientid>/<clientauthstring>/type



    order request type = "neworder"




    Examples:


               https://secure.owd.com/webapps/ejunkie/440/Q8zSynE1mzZHBLUY5JrmzQM=/neworder.jsp
               
   

Common Order Data

country:
payer_business_name:
first_name: 	Stewart
last_name: 	Buskirk
payer_email: 	owditadmin@owd.com
payer_phone:
payer_street:
payer_city:
payer_state:
payer_zip:
payer_country_code:
address_name:
address_business_name:
address_phone:
address_street:
address_city:
address_state:
address_zip:
address_country_code:
address_country:
payment_date: 	13:39:30 Jan 26, 2010 MST
custom:
mc_currency: 	USD
business: 	013984bf98dbbbabafc229c7a931c775
mc_gross: 	0
mc_shipping: 	0
tax: 	0
txn_type: 	ejgift
payment_type: 	Instant
invoice: 	25bad20jpza0tz9sjcs6q6wc0soook488oc0sc0
buyer_ip: 	96.2.236.130
card_last_four:
card_type:
mailing_list_status: 	false
charset: 	utf-8
item_name1: 	Sar Shield Cell Phone Protection
item_number1: 	1000
mc_gross_1: 	0
quantity1: 	1
num_cart_items: 	1
txn_id: 	jg-kwv9w6d36520339
payment_status: 	Completed
pending_reason:
item_name: 	Sar Shield Cell Phone Protection
item_number: 	1000
quantity: 	1
option_name1:
option_selection1:
option_name2:
option_selection2:
option_name3:
option_selection3:
mc_handling: 	0
handshake: 	920b5a978ad7326834324b06f1a384db
discount_codes: 	For Cart Item Total: owdtest
from_name: 	Safe Cell Phone Inc
from_email: 	bmwalsh426@yahoo.com
client_shipping_method_id: 	0

    

*/


public class OneWorldEJunkieServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    public static final String kOrderCreationRequest = "neworder.jsp";


    public static IANACountries countryCodes = new IANACountries();




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

                createEJunkieOrder(clientInfo, req);

                resp.getWriter().write("Order OK");

                if (myFilter != null) {

                    Mailer.postMailMessage("EJunkie API Request Data", myFilter.data.toString(), "owditadmin@owd.com", "noop@owd.com");

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

    public String getServletInfo() {

        return "One World Yahoo Server v";

    }


    public void createEJunkieOrder(OwdClient client, HttpServletRequest req) throws Exception {

        ////log.debug("in YahooOrderCreateRequest Handler2");

          try
          {
        Map<String,String> params = new TreeMap<String,String>();

              StringBuffer paramStr = new StringBuffer();

        Enumeration<String> paramKeys = req.getParameterNames();
              while (paramKeys.hasMoreElements()) {
                      String name = (String)paramKeys.nextElement();
                      String value = req.getParameter(name);
                      params.put(name,value);
                      paramStr.append(name+":"+value+"\r\n");
                  }

            
            Mailer.sendMail("EJunkie Order Posting Receipt",paramStr.toString(),"owditadmin@owd.com","donotreply@owd.com");

            Order order = new Order(client.getClientId()+"");

            order.actual_order_date = OWDUtilities.getSQLDateTimeForToday();

            order.order_type = "Posted via EJunkie delivery";

            order.ship_call_date = OWDUtilities.getSQLDateForToday();

            order.backorder_rule = OrderXMLDoc.kPartialShip;

            order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;

            order.is_paid = 1;



            order.order_refnum = (String) params.get("txn_id");

            ////log.debug("yahoo orderref="+order.order_refnum);


                            Address billAddress = new Address(""+params.get("payer_business_name"),

                                    ""+params.get("address_street"),

                                    "",

                                    ""+params.get("address_city"),

                                    ""+params.get("address_state"),

                                    ""+params.get("address_zip"),

                                    countryCodes.getCountryForCode((""+params.get("address_country_code")).substring(0, 2).trim()));


                            order.setBillingAddress(billAddress);


                            Contact billContact = new Contact(

                                   ""+params.get("first_name")+" "+params.get("last_name"),

                                    ""+params.get("payer_phone"),

                                    "",

                                    ""+params.get("payer_email"),

                                    "");

                            order.setBillingContact(billContact);

              Address shipAddress = null;
              if("".equals(params.get("address_street")))
              {
                 shipAddress = new Address(billAddress.company_name,

                                    billAddress.address_one,

                                    "",

                                    billAddress.city,

                                    billAddress.state,

                                    billAddress.zip,

                                    billAddress.country);
              }    else
              {
                             shipAddress = new Address(""+params.get("address_business_name"),

                                    ""+params.get("address_street"),

                                    "",

                                    ""+params.get("address_city"),

                                    ""+params.get("address_state"),

                                    ""+params.get("address_zip"),

                                    countryCodes.getCountryForCode((""+params.get("address_country_code")).substring(0, 2).trim()));
              }
                            if (shipAddress.country.equals("USA") || shipAddress.country.equals("CANADA")) {

                                if (shipAddress.state.length() < 2) {

                                    throw new Exception("State value missing or invalid");

                                }

                            }


                            if (shipAddress.address_one.length() < 1 && shipAddress.address_two.length() < 1) {

                                throw new Exception("Street address information missing");


                            }

                            order.getShippingInfo().setShippingAddress(shipAddress);
                              Contact shipContact =  null;

                        if("".equals(params.get("address_street")))
              {
                    shipContact = new Contact(



                                   billContact.getName(),

                                    billContact.phone,

                                    "",

                                    billContact.email,

                                    "");
              }else
                        {
                             shipContact = new Contact(



                                   ""+params.get("first_name")+" "+params.get("last_name"),

                                    ""+params.get("address_phone"),

                                    "",

                                    ""+params.get("payer_email"),

                                    "");
                        }
                            order.getShippingInfo().setShippingContact(shipContact);





              if(order.getShippingAddress().isInternational())
              {
                order.getShippingInfo().setShipOptions("USPS First-Class Mail International","Prepaid","");
              }   else
              {
                order.getShippingInfo().setShipOptions("USPS First-Class Mail", "Prepaid", "");
              }


                                        order.total_shipping_cost = Float.parseFloat(""+params.get("mc_shipping"));

                                        order.total_tax_cost = Float.parseFloat(""+params.get("tax"));


                order.backorder_rule = OrderXMLDoc.kBackOrderAll;
                order.payment_status = OrderXMLDoc.kPaymentStatusClientManaged;
                order.is_paid = 1;

            int itemsInCart = Integer.parseInt(""+params.get("num_cart_items"));
              for (int i=1;i<=itemsInCart;i++)
              {
                  order.addLineItem((String)params.get("item_number"+i),
                          (String)params.get("quantity"+i),
                          ""+OWDUtilities.roundFloat((Float.parseFloat((String)params.get("mc_gross_"+i))/Float.parseFloat((String)params.get("quantity"+i)))),
                  (String)params.get("mc_gross_"+i),
                      (String)params.get("item_name"+i)
                      ,"","");
              }
              order.recalculateBalance();

              float cartTotal = OWDUtilities.roundFloat(Float.parseFloat(""+params.get("mc_gross")));
              float discount =  OWDUtilities.roundFloat(order.total_order_cost-cartTotal);

              if(discount != 0.00f)
              {
                    order.discount = Math.abs(discount) * -1;
              }

                String reference = order.saveNewOrder(OrderUtilities.kHoldForPayment, false);


                if (reference == null || order.completed == false) {

                  //    Mailer.sendMail("Yahoo Order Import Error","Order reference "+order.order_refnum+" was not imported from Yahoo. The error was: "+order.last_error+"\n\n"
                   //                             ,"razorama@owd.com",com.owd.api.client.getAmEmail());

                    throw new Exception("Order Processing Error: " + order.last_error);

                } else {


                }









                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        HibernateSession.closeSession();
                    }


    }



    static public void main(String[] args) {

        try {

            WebResource omserver = new WebResource("https://api.omx.ordermotion.com/hdde/xml/yiir.asp?Info=Available&HTTPBizID=svSYjSNlfGxaZOLGPaEUUOaaqcQRKGUghhfoTIOUuBGjOjEMtyACrBQJlghHGLfvuXQgfJtPhJoIevvjBIwuCCcMcPhwOsDOjefPdXGmdDZIiZiYnaSlaQYygsfaPLAlPtIsYunIVPZTfERHWoiUEogleDUloKMiikKJajpfvRLfxtctwWygNfOpJTBSjqbWKhIqfqIbeiGVQviDfpEwhpCeMNDAHODEsPelWTeNFydnQLAregMgGVwwyQrcLWg",WebResource.kPOSTMethod);
            //todo
            omserver.addParameter(".catalog","razorama");
            omserver.addParameter(".code","W13110050152");
            omserver.addParameter(".id","blacrazgrip");
            omserver.addParameter(".quantity","1");
            omserver.addParameter(".price","26.95");

            BufferedReader response = omserver.getResource();

             log.debug("got response:" + response);

                int line = 0;
                StringBuffer sb = new StringBuffer();

            for(String key:omserver.returnHeaders.keySet())
            {
                for(String value:omserver.returnHeaders.get(key))
                {
                    log.debug(key+":"+value);
                }
            }
            log.debug("avail="+omserver.avail);
                line = (int) response.read();
            log.debug("got line:"+line);
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
            log.debug(sb.toString());

            /*
.catalog=<catalog name>
.code=<item code>
.id=<item id>
.quantity=<item quantity requested>
.price=<price of item>


             */

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }



    public class ItemData {
        String part = "";
        String desc = "";
        float cost = 0.00f;
        int qty = 0;



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

    }
}
