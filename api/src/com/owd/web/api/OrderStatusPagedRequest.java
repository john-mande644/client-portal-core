package com.owd.web.api;

import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderStatusPagedRequest extends OrderStatusRequest {

    private final static Logger log =  LogManager.getLogger();
    //root node name

    public static final String kRootTag = "OWD_ORDER_STATUS_PAGED_REQUEST";

    //root node attributes

    public static final String kPageIndex = "pageIndex";
    public static final String kPageSize= "pageSize";

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception  {

        int clientId = Integer.parseInt(client.client_id);

        String summary = root.getAttribute(kSummary);
        if (summary == null) summary = "FALSE";
        String orderRef = root.getAttribute(kOrderRef);
        if (orderRef == null) orderRef = "";
        String pageSizeStr = root.getAttribute(kPageSize);
        String pageIndexStr = root.getAttribute(kPageIndex);
        int pageSize = 0;
        int pageIndex = 0;
        String createDate = root.getAttribute(kCreatedDate);
        String shipDate = root.getAttribute(kShippedDate);
        String prefixSearch = root.getAttribute(kPrefixSearch);
        log.debug("cd="+createDate);

        if (pageSizeStr != null ) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        if (pageIndexStr != null ) {
            pageIndex = Integer.parseInt(pageIndexStr);
        }

        if(createDate==null || createDate.length()==0) { createDate="19000101"; } else {
            try  {
                Calendar cal = getCalendarFromAPIDateString(createDate);
                if(!(cal.get(Calendar.YEAR)>=2000))
                {
                    throw new Exception("");
                }
            }  catch(Exception ex)
            {
                throw new APIContentException("createdDate attribute value '"+createDate+ "' is not a valid date - should be in the format YYYYMMDD");
            }
        }

        if (shipDate==null || shipDate.length()==0) { shipDate="19000101"; } else {
            try  {
                Calendar cal = getCalendarFromAPIDateString(shipDate);
                if(!(cal.get(Calendar.YEAR)>=2000))
                {
                    throw new Exception("");
                }
            }  catch(Exception ex)  {
                throw new APIContentException("shippedDate attribute value '"+shipDate+ "' is not a valid date - should be in the format YYYYMMDD");
            }
        }

        int count = ConnectionManager.getOrdersCount(clientId,shipDate, createDate,orderRef,prefixSearch);

        try
        {
            APIResponse response = null;

            log.debug("cd="+createDate);
            if(summary.equals("TRUE"))
            {
                response = new OrderSummaryResponse(api_version);

                List orderIDs = ConnectionManager.getPagedOrderKeysForClientID(clientId,"19000101","19000101",orderRef,"FALSE", pageIndex, pageSize);

                if(orderIDs.size() < 1 && api_version<2.0)   {

                    orderIDs = new ArrayList();
                    String orderID = ConnectionManager.getOrderKey(orderRef,client.client_id);
                    if (orderID != null)
                        orderIDs.add(orderID);
                }

                if(orderIDs.size() < 1)
                    throwNoOrdersReturnedError("Order ID not recognized ",
                            pageSize, pageIndex, count);

                OrderStatus order = new OrderStatus((String) orderIDs.get(0));
                ((OrderSummaryResponse)response).buildFromOrderStatus(order);

            } else if (orderRef.trim().length()>0) {
                response = new OrderStatusPagedResponse(api_version, count, pageSize, pageIndex);
                if("345".equals(client.client_id) )
                {
                    ((OrderStatusResponse)response).showOrderDetails = false;
                }
                log.debug("orderid=::"+root.getAttribute(kOrderRef)+"::");

                List orderIDs = ConnectionManager.getPagedOrderKeysForClientID(clientId,"19000101","19000101",orderRef,prefixSearch, pageIndex, pageSize);

                if(orderIDs.size() < 1 && api_version<2.0) {
                    orderIDs = new ArrayList();
                    String orderID = ConnectionManager.getOrderKey(root.getAttribute(kOrderRef),client.client_id);
                    if (orderID != null)
                        orderIDs.add(orderID);
                }

                if(orderIDs.size() < 1)
                    throwNoOrdersReturnedError("Order ID not recognized ",
                            pageSize, pageIndex, count);

                if(orderIDs.size() == 1) {
                    OrderStatus order = new OrderStatus((String) orderIDs.get(0));
                    ((OrderStatusResponse)response).buildFromOrderStatus(order);

                } else {
                    ((OrderStatusResponse)response).buildFromOrderIDs(orderIDs);
                }
            } else  if(!("19000101".equals(createDate))) {
                log.debug("matched cd of "+createDate);
                response = new OrderStatusPagedResponse(api_version, count, pageSize, pageIndex);

                try {

                    createDate = OWDUtilities.getSQLDateString(dateFormat.parse(createDate));
                } catch(Exception ex) {
                    throw new APIContentException("Created Date invalid or unrecognized");
                }

                List orderIDs = ConnectionManager.getPagedOrderKeysForClientID(clientId,"19000101",createDate,null,null, pageIndex, pageSize);

                if(orderIDs.size() < 1)
                    throwNoOrdersReturnedError("Created Date "+createDate,
                            pageSize, pageIndex, count);

                if(orderIDs.size() == 1)  {
                    OrderStatus order = new OrderStatus((String) orderIDs.get(0));
                    ((OrderStatusResponse)response).buildFromOrderStatus(order);
                } else {
                    ((OrderStatusResponse)response).buildFromOrderIDs(orderIDs);
                }

            } else   if(!("19000101".equals(shipDate)))
            {
                response = new OrderStatusPagedResponse(api_version, count, pageSize, pageIndex);

                try  {

                    shipDate = OWDUtilities.getSQLDateString(dateFormat.parse(shipDate));
                } catch(Exception ex) {
                    throw new APIContentException("Shipped Date invalid or unrecognized");
                }

                List orderIDs = ConnectionManager.getPagedOrderKeysForClientID(clientId,shipDate,"19000101",null,null, pageIndex, pageSize);

                if(orderIDs.size() < 1)
                    throwNoOrdersReturnedError("Ship Date "+shipDate,
                            pageSize, pageIndex, count);

                if(orderIDs.size() == 1) {
                    OrderStatus order = new OrderStatus((String) orderIDs.get(0));
                    ((OrderStatusResponse)response).buildFromOrderStatus(order);
                } else {
                    ((OrderStatusResponse)response).buildFromOrderIDs(orderIDs);
                }

            } else {
                throw new APIContentException("No valid parameters provided for order search");
            }

            return response;
        } catch(Exception ex) {
            throw ex;
        }
    }

    private String throwNoOrdersReturnedError(String noOrdersErrorPrefix, int pageSize, int pageIndex, int count) throws Exception{
        String pageOutOfRangePostifx = "";

        if(count > 0) {
            pageOutOfRangePostifx = " because the page is out of range (totalCount " + count + ")";
        }

        String errorMessage = noOrdersErrorPrefix + ", pageSize " + pageSize +
                ", pageIndex " + pageIndex + " - returned no orders" + pageOutOfRangePostifx;

        throw new APIContentException(errorMessage);
    }
}

