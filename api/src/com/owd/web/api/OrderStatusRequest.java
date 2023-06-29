package com.owd.web.api;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.*;
import com.owd.core.business.order.*;
import com.owd.core.managers.*;
import com.owd.core.business.Client;
import com.owd.core.OWDUtilities;
import org.w3c.dom.Element;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;



public class OrderStatusRequest implements APIRequestHandler

{
private final static Logger log =  LogManager.getLogger();





	//root node name

	public static final String kRootTag = "OWD_ORDER_STATUS_REQUEST";



	//root node attributes

	public static final String kOrderRef = "clientOrderId";
    public static final String kClientOrderRef = "clientOrderRef";
	public static final String kPrefixSearch = "prefixSearch";
	public static final String kSummary = "summary"; //TRUE to activate
	public static final String kCreatedDate = "createdDate"; //YYYYMMDD to activate
	public static final String kShippedDate = "shippedDate"; //YYYYMMDD to activate
    public static final String kGroupName = "shippedDate";

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
    public static void main (String[] args) throws Exception
    {
        Calendar cal = getCalendarFromAPIDateString(null);
        log.debug(""+cal.get(Calendar.YEAR));
        log.debug(""+cal.get(Calendar.MONTH));
        log.debug(""+cal.get(Calendar.DATE));
    }

    @Trace
	public APIResponse handle(Client client, Element root,boolean testing, double api_version) throws Exception

	{

        int clientId = Integer.parseInt(client.client_id);

          String summary = root.getAttribute(kSummary);
		 if (summary == null) summary = "FALSE";

        String orderRef = root.getAttribute(kOrderRef);
		 if (orderRef == null) orderRef = "";



        String createDate = root.getAttribute(kCreatedDate);
        log.debug("cd="+createDate);
        if(createDate==null || createDate.length()==0) { createDate="19000101"; } else {
        try
        {
            Calendar cal = getCalendarFromAPIDateString(createDate);
            if(!(cal.get(Calendar.YEAR)>=2000))
            {
               throw new Exception("");
            }
        }   catch(Exception ex)
        {
             throw new APIContentException("createdDate attribute value '"+createDate+ "' is not a valid date - should be in the format YYYYMMDD");
        }
        }

        String shipDate = root.getAttribute(kShippedDate);
        if(shipDate==null || shipDate.length()==0) { shipDate="19000101"; }   else{
         try
        {
            Calendar cal = getCalendarFromAPIDateString(shipDate);
            if(!(cal.get(Calendar.YEAR)>=2000))
            {
               throw new Exception("");
            }
        }   catch(Exception ex)
        {
             throw new APIContentException("shippedDate attribute value '"+shipDate+ "' is not a valid date - should be in the format YYYYMMDD");
        }
        }
		try
		{

		 
		 APIResponse response = null;

            log.debug("cd="+createDate);
		 if(summary.equals("TRUE"))
		 {
		 	response = new OrderSummaryResponse(api_version);
		 	
		 	List orderIDs = ConnectionManager.getOrderKeyForClientID(orderRef,client.client_id,"FALSE");
		 	if(orderIDs.size() < 1 && api_version<2.0)

		{

			orderIDs = new ArrayList();
			String orderID = ConnectionManager.getOrderKey(orderRef,client.client_id);
			if (orderID != null)
				orderIDs.add(orderID);

		}
		
		if(orderIDs.size() < 1)
			throw new APIContentException("Order ID not recognized");

		
			OrderStatus order = new OrderStatus((String) orderIDs.get(0));
			((OrderSummaryResponse)response).buildFromOrderStatus(order);
		
		 }else if(orderRef.trim().length()>0)
		 {
		 response = new OrderStatusResponse(api_version);
             if("345".equals(client.client_id) )
             {
                 ((OrderStatusResponse)response).showOrderDetails = false;
             }
		  log.debug("orderid=::"+root.getAttribute(kOrderRef)+"::");

		List orderIDs = ConnectionManager.getOrderKeyForClientID(root.getAttribute(kOrderRef),client.client_id,root.getAttribute(kPrefixSearch));

		

		if(orderIDs.size() < 1 && api_version<2.0)

		{

			orderIDs = new ArrayList();
			String orderID = ConnectionManager.getOrderKey(root.getAttribute(kOrderRef),client.client_id);
			if (orderID != null)
				orderIDs.add(orderID);

		}

		

		if(orderIDs.size() < 1)
			throw new APIContentException("Order ID not recognized");

		
		if(orderIDs.size() == 1)
		{
			OrderStatus order = new OrderStatus((String) orderIDs.get(0));
			((OrderStatusResponse)response).buildFromOrderStatus(order);
		
		}else
		{
			((OrderStatusResponse)response).buildFromOrderIDs(orderIDs);
		}

		
		 
		}else   if(!("19000101".equals(createDate)))
         {
             log.debug("matched cd of "+createDate);
             response = new OrderStatusResponse(api_version);
                  //////log.debug("orderid=::"+root.getAttribute(kOrderRef)+"::");

             try
             {

             createDate = OWDUtilities.getSQLDateString(dateFormat.parse(createDate));
             }catch(Exception ex)
             {
                 throw new APIContentException("Created Date invalid or unrecognized");
             }

                List orderIDs = ConnectionManager.getOrderKeyForClientIDAndCreatedDate(client.client_id,createDate);

               //  log.debug("createddate os ids size="+orderIDs.size());

                if(orderIDs.size() < 1)
                    throw new APIContentException("Created Date "+createDate+" returned no orders");


                if(orderIDs.size() == 1)
                {
                    OrderStatus order = new OrderStatus((String) orderIDs.get(0));
                    ((OrderStatusResponse)response).buildFromOrderStatus(order);

                }else
                {
                    ((OrderStatusResponse)response).buildFromOrderIDs(orderIDs);
                }

         } else   if(!("19000101".equals(shipDate)))
         {
             response = new OrderStatusResponse(api_version);
                  //////log.debug("orderid=::"+root.getAttribute(kOrderRef)+"::");

             try
             {

             shipDate = OWDUtilities.getSQLDateString(dateFormat.parse(shipDate));
             }catch(Exception ex)
             {
                 throw new APIContentException("Shipped Date invalid or unrecognized");
             }

                List orderIDs = ConnectionManager.getOrderKeyForClientIDAndShippedDate(client.client_id,shipDate);

               //  log.debug("createddate os ids size="+orderIDs.size());

                if(orderIDs.size() < 1)
                    throw new APIContentException("Ship Date "+shipDate+" returned no orders");


                if(orderIDs.size() == 1)
                {
                    OrderStatus order = new OrderStatus((String) orderIDs.get(0));
                    ((OrderStatusResponse)response).buildFromOrderStatus(order);

                }else
                {
                    ((OrderStatusResponse)response).buildFromOrderIDs(orderIDs);
                }

         } else
         {
             throw new APIContentException("No valid parameters provided for order search");
         }






		return response;
	}catch(Exception ex)
	{
		//ex.printStackTrace();
		throw ex;
	
	}
	}

    protected static Calendar getCalendarFromAPIDateString(String createDate) throws Exception {
        if(createDate==null) { throw new Exception();  }
        if(createDate.length()!=8) { throw new Exception();  }
        Date dt = dateFormat.parse(createDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        return cal;
    }



}
