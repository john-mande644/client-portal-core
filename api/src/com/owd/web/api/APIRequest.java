package com.owd.web.api;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.owd.core.business.Client;
import com.owd.core.OWDUtilities;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class APIRequest

{
private final static Logger log =  LogManager.getLogger();

    public final static int maxInProcess = 10;

  //  static ArrayBlockingQueue processQueue = new ArrayBlockingQueue(maxInProcess,true);

 static ConcurrentMap<String, ArrayBlockingQueue> throttleMap = new ConcurrentHashMap<String, ArrayBlockingQueue>();
 //   static ConcurrentMap<String, AtomicInteger> processMap = new ConcurrentHashMap<String, AtomicInteger>();

//XML root name

	public static final String kTagAPIRequest 	= "OWD_API_REQUEST";

	

//XML attribute names

	public static final String kAttAPIVersion 	= "api_version";

	public static final String kAttClientID 		= "client_id";

	public static final String kAttClientAuth 		= "client_authorization";

	public static final String kAttRedirectURL 	= "redirect_url";

	public static final String kAttTesting 		= "testing";




	

	public void init()

	{



	}

    public static APIResponse handle(Document doc)

    {
       return handle(doc,null);
    }

    @Trace
	public static APIResponse handle(Document doc,  HttpServletRequest req)

	{
        if(req==null)
        {
            req = (HttpServletRequest) new MockRequest();
        }
		String api_version="1.0";

		String client_id="0";

		String client_authorization="";

		String testing="FALSE";

		String redirect_url=""	;

		double api_vers = 1.0;

		

		Client clientInfo = null;


        if(req==null)
        {
            req = (HttpServletRequest) OneWorldAPIServlet.webRequest.get();
        }

		APIResponse apiResponse =  new APIResponse(api_vers);

		//handle request

		int clientID = 0;
        String startTime = OWDUtilities.getSQLDateTimeForToday();

        String request = "";
        String APIError = null;
        try

		{

			Element root = doc.getDocumentElement();

			root.normalize();

			if(root.getTagName().equals(kTagAPIRequest))

			{

				api_version = root.getAttribute(kAttAPIVersion).trim();

				

				try

				{

					api_vers = new Double(api_version).doubleValue();

				}

				catch(NumberFormatException nfex)

				{

					throw new APIContentException("api_version is not a recognizable number");

				}

				

				client_id = root.getAttribute(kAttClientID).trim();


              	try

				{

					 clientID= Integer.parseInt(client_id);

				}

				catch(NumberFormatException nfex)

				{
                    client_id = "0";
                    throw new APIContentException("Client ID invalid");
					

				}
                client_authorization = root.getAttribute(kAttClientAuth).trim();

				redirect_url = root.getAttribute(kAttRedirectURL).trim();

				testing = root.getAttribute(kAttTesting).trim();

				

				if (!testing.equals("") && !testing.equalsIgnoreCase("TRUE") && !testing.equalsIgnoreCase("FALSE"))

					throw new APIContentException("OWD_API_REQUEST testing attribute must be TRUE, FALSE or not included in request");

				try{

				//////log.debug("trying to match id "+client_id+" to auth "+client_authorization);

				if(!(client_id.equals(OWDUtilities.decryptData(client_authorization))) || "Bqgui96fh9aICqRXNaeOKwM=".equals(client_authorization))
				{

					throw new APIContentException("Authorization invalid");
				}

				}catch(Exception ex)

				{

					throw new APIContentException("Authorization invalid");

				}

					

				clientInfo = Client.getClientForID(client_id);

				if(clientInfo == null)

					throw new APIContentException("Client ID invalid");

					

				if("0".equals(clientInfo.is_active) || "false".equalsIgnoreCase(clientInfo.is_active))
				{
					throw new APIContentException("You are not authorized to access this service");
				}
				

				

				NodeList nodes = root.getChildNodes();

				if(nodes == null)

					throw new APIFormatException("Request type element not found");

				

				APIRequestHandler handler = null;

                waitForQueue(client_id);

                for(int  i=0;i<nodes.getLength();i++)

				{

					if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE)

					{

						String reqName = nodes.item(0).getNodeName();
                        request = reqName;
                        log.debug("reqname="+reqName);
						if(reqName.equals(OrderCreateRequest.kRootTag))

						{

							handler = new OrderCreateRequest();
							log.debug("API Order Create Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "OrderCreateRequest");

						}else if(reqName.equals(AsnCreateRequest.kRootTag))

						{

							handler = new AsnCreateRequest();
							log.debug("API ASN Create Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "AsnCreateRequest");


                        }else if(reqName.equals(OrderCancelRequest.kRootTag))

						{

							handler = new OrderCancelRequest();
							log.debug("API Order Cancel Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "OrderCancelRequest");


                        }else if(reqName.equals(OrderHoldRequest.kRootTag))

						{

							handler = new OrderHoldRequest();
							log.debug("API Order Hold Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "OrderHoldRequest");


                        } else if(reqName.equals(OrderUpdateRequest.kRootTag))

                        {

                            handler = new OrderUpdateRequest();
                            log.debug("API Order Update Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "OrderUpdateRequest");


                        }   else if(reqName.equals(FacilityListRequest.kRootTag))

                        {

                            handler = new FacilityListRequest();
                            log.debug("API Facility List Request for ID "+clientInfo.company_name);

                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "FacilityListRequest");


                        }
                        else if(reqName.equals(InventoryCreateRequest.kRootTag))

						{

							handler = new InventoryCreateRequest();
							log.debug("API Inventory Create Request for ID "+clientInfo.company_name);

                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "InventoryCreateRequest");


                        }else if(reqName.equals(InventoryUpdateRequest.kRootTag))

						{

							handler = new InventoryUpdateRequest();
							log.debug("API Inventory Update Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "InventoryUpdateRequest");


                        }else if(reqName.equals(InvoiceDataRequest.kRootTag))

                        {

                            handler = new InvoiceDataRequest();
                            log.debug("API Invoice Data Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "InvoiceDataRequest");


                        }else if(reqName.equals(InventoryStatusRequest.kRootTag))

						{

							handler = new InventoryStatusRequest();
							log.debug("API Inventory Status Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "InventoryStatusRequest");


                        }else if(reqName.equals(InventoryReportRequest.kRootTag))

						{

							handler = new InventoryReportRequest();
							log.debug("API Inventory Report Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "InventoryReportRequest");


                        }else if(reqName.equals(AsnStatusRequest.kRootTag))

						{

							handler = new AsnStatusRequest();
							log.debug("API Asn Status Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "AsnStatusRequest");


                        }else if(reqName.equals(AsnUpdateRequest.kRootTag))

						{

							handler = new AsnUpdateRequest();
							log.debug("API Asn Update Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "AsnUpdateRequest");


                        }else if(reqName.equals(AsnDeleteRequest.kRootTag))

						{

							handler = new AsnDeleteRequest();
							log.debug("API Asn Delete Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "AsnDeleteRequest");


                        }else if(reqName.equals(PhionShippingRateRequest.kRootTag))

                        {

                            handler = new PhionShippingRateRequest();
                            log.debug("API Phion Shipping Rate Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "PhionShippingRateRequest");


                        }else if(reqName.equals(AsnReceiveReleaseRequest.kRootTag))

						{

							handler = new AsnReceiveReleaseRequest();
							log.debug("API Asn Receive Release Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "AsnReceiveReleaseRequest");

                        }else if(reqName.equals(TestInventorySetCountRequest.kRootTag))

						{

							handler = new TestInventorySetCountRequest();
							log.debug("API Test Inventory Set Count Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "TestInventorySetCountRequest");


                        }else if(reqName.equals(TestOrderReleaseRequest.kRootTag))

                        {

                            handler = new TestOrderReleaseRequest();
                            log.debug("API Test Order Release Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "TestOrderReleaseRequest");


                        }else if(reqName.equals(BinaryUploadRequest.kRootTag))

                        {

                            handler = new BinaryUploadRequest();
                            log.debug("API Upload for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "BinaryUploadRequest");


                        }else if(reqName.equals(OrderReleaseRequest.kRootTag))

						{

							handler = new OrderReleaseRequest();
							log.debug("API Order Release Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "OrderReleaseRequest");


                        }else if(reqName.equals(OrderStatusRequest.kRootTag))

						{

							handler = new OrderStatusRequest();
							log.debug("API Status Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "OrderStatusRequest");

                        }else if(reqName.equals(InventoryCountRequest.kRootTag))

						{

							handler = new InventoryCountRequest();
							log.debug("API Count Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "InventoryCountRequest");

                        }else if(reqName.equals(ReportRequest.kRootTag))

						{

							handler = new ReportRequest();
							log.debug("API Report Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "ReportRequest");

                        } else if(reqName.equals(SubscriptionStatusRequest.kRootTag))

						{

							handler = new SubscriptionStatusRequest();
							log.debug("API Sub Status Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "SubscriptionStatusRequest");

                        } else if(reqName.equals(SubscriptionUpdateRequest.kRootTag))

						{
                            log.debug("API Sub Update Request for ID "+clientInfo.company_name);
							handler = new SubscriptionUpdateRequest();
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "SubscriptionUpdateRequest");


                        }else if(reqName.equals(SubscriptionCreateRequest.kRootTag))

						{

							handler = new SubscriptionCreateRequest();
							log.debug("API Sub Create Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "SubscriptionCreateRequest");

                        }  else if(reqName.equals(ShippingRateRequest.kRootTag))

						{

							handler = new ShippingRateRequest();
							log.debug("API Shipping Rate Request for ID "+clientInfo.company_name);
                            req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "ShippingRateRequest");

                        }else if(reqName.equals(OrderStatusPagedRequest.kRootTag))

						{

							handler = new OrderStatusPagedRequest();
							log.debug("API Status Request for ID "+clientInfo.company_name);
							req.setAttribute("com.newrelic.agent.TRANSACTION_NAME", "OrderStatusRequest");

						}

						

						if(handler != null)

						{

							apiResponse = handler.handle(clientInfo,(Element)nodes.item(i),(testing.equals("TRUE")),api_vers);

							i = nodes.getLength();

						}

					}

				}

				

				if(handler == null)

					throw new APIContentException("Request type element not recognized");

							

			}else

				throw new APIFormatException("Not an "+kTagAPIRequest+" document");

				

		}catch(APIFormatException e)

		{//////log.debug("in APIRequest ex");

			apiResponse.setError(APIResponse.kErrFormat,e.getMessage());

		//	Mailer.postMailMessage("API Format Error Report",e.getMessage()+"\n\n"+OWDUtilities.getStackTraceAsString(e),"owditadmin@owd.com","support@owd.com");
            APIError = e.getMessage();


		}catch(APIContentException e)

		{//////log.debug("in APIRequest ex");

			apiResponse.setError(APIResponse.kErrContent,e.getMessage());

			//Mailer.postMailMessage("API Content Error Report",e.getMessage()+"\n\n"+OWDUtilities.getStackTraceAsString(e),"owditadmin@owd.com","support@owd.com");
              APIError = e.getMessage();


		}catch(APIRuleException e)

		{//////log.debug("in APIRequest ex");

			apiResponse.setError(APIResponse.kErrRule,e.getMessage());

			Mailer.postMailMessage("API Rule Error Report", e.getMessage() + "\n\n" + OWDUtilities.getStackTraceAsString(e), "casetracker@owd.com", "support@owd.com");
               APIError = e.getMessage();


		}catch(APIProcessException e)

		{//////log.debug("in APIRequest ex");
             e.printStackTrace();
			apiResponse.setError(APIResponse.kErrProcess,e.getMessage());

			//Mailer.postMailMessage("API Process Error Report",e.getMessage()+"\n\n"+OWDUtilities.getStackTraceAsString(e),"owditadmin@owd.com","support@owd.com");
              APIError = e.getMessage();


		}catch(Exception e)

		{//////log.debug("in APIRequest ex");
            e.printStackTrace();
			apiResponse.setError(APIResponse.kErrProcess,e.getMessage());
                APIError = e.getMessage();
			//Mailer.postMailMessage("API General Error Report",e.getMessage()+"\n\n"+OWDUtilities.getStackTraceAsString(e),"owditadmin@owd.com","support@owd.com");

		}finally
        {
            //just in case
            throttleMap.putIfAbsent(client_id+"",new ArrayBlockingQueue(maxInProcess,true));

            boolean removed = throttleMap.get(client_id).remove(Thread.currentThread());

        }



        return apiResponse;

		

	}

    @Trace
    private static void waitForQueue(String client_id) throws APIProcessException {
        throttleMap.putIfAbsent(client_id,new ArrayBlockingQueue(maxInProcess,true));
        for(String key:throttleMap.keySet())
        {
            log.debug("THROTTLE:"+key+":"+throttleMap.get(key).size());
        }
        boolean inQueue = true;
        try
        {
            inQueue = throttleMap.get(client_id).offer(Thread.currentThread(),30, TimeUnit.SECONDS);
        }catch(Exception ex)
        {
            //ignore, go ahead and process. Should only happen on thread interrupt
            ex.printStackTrace();
        }
        if(!inQueue)
        {
            log.debug("THROTTLE: Limit Reached : "+client_id+" : "+throttleMap.get(client_id).size());
            throw new APIProcessException("Throttling limit exceeded; please try again later");
        }

        log.debug("THROTTLE: Permitted Request : "+client_id+" : "+throttleMap.get(client_id).size());
    }


}
