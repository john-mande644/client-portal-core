package com.owd.web.api;

import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdReceive;
import com.owd.hibernate.generated.OwdReceiveItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 7/5/11
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryReportResponse extends APIResponse

{
private final static Logger log =  LogManager.getLogger();

    static Map<Integer,String> statusMap = new TreeMap<Integer,String>() ;
//XML root name

    public static final String kTagInventoryReportResponse = "OWD_INVENTORY_REPORT_RESPONSE";

    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    private List<OwdReceive> owdReceiveList = new ArrayList<OwdReceive>();
    Map<String,String> orderRefMap = new HashMap<String,String>();
    Map<String,AsnData> asnInfoMap = new HashMap<String,AsnData>();

    public List<OwdReceive> getOwdReceiveList() {
        return owdReceiveList;
    }

    static {
        //static init
        statusMap.put(0,"UNRECEIVED") ;
        statusMap.put(1,"COMPLETE") ;
        statusMap.put(2,"PARTIALRECEIPT") ;
        statusMap.put(3,"CANCELLED") ;
    }
    public InventoryReportResponse(double api_v)

    {

        super(api_v);

    }

    public void setOwdReceiveList(List<OwdReceive> items)

    {
        if (items != null) {
            owdReceiveList = items;

        }


    }

    public String getXML()

    {

        StringBuilder responseBuffer = new StringBuilder();

        responseBuffer.append("<" + kTagInventoryReportResponse + ">");
        for (OwdReceive item : owdReceiveList) {
            responseBuffer.append(getXMLFromItem(item));
        }
        responseBuffer.append("</" + kTagInventoryReportResponse + ">");
        return responseBuffer.toString();


    }


    protected String getXMLFromItem(OwdReceive item) {

        StringBuilder sb = new StringBuilder();

        boolean validToReport = false;

        for(OwdReceiveItem testitem:item.getOwdReceiveItems())
        {
           if(testitem.getQuantity() != 0)
           {
               //ok to report, at least one item is good
               validToReport = true;

           }
        }
        if(validToReport)
        {
        if("ADJUSTMENT".equalsIgnoreCase(item.getType()) )
        {
           sb.append("<ADJUSTMENT_EVENT id=\"" + item.getReceiveId()
                + "\" created=\"" + df.format(item.getCreatedDate())
                  + "\" >");

        sb.append(API.buildTextElement("NOTES", "" + item.getNotes()));
            if(api_vers>=2.0){sb.append(API.buildTextElement("FACILITY_CODE",item.getFacilityCode()));}

        }   else
        if ("RETURN".equalsIgnoreCase(item.getType()))
        {
            System.out.println("getting return data for "+item.getRefNum());
           sb.append("<RETURN_EVENT id=\"" + item.getReceiveId()
                + "\" created=\"" + df.format(item.getCreatedDate())
                  + "\" >");

        sb.append(API.buildTextElement("OWD_ORDER_REF", "" + item.getRefNum()));
            sb.append(API.buildTextElement("CLIENT_ORDER_REF", "" + orderRefMap.get(item.getRefNum())==null?"":orderRefMap.get(item.getRefNum())));
            sb.append(API.buildTextElement("RETURN_REF", "" + item.getBlNum()));
            sb.append(API.buildTextElement("POSTAGE_DUE", "" + item.getDriver()));
        sb.append(API.buildTextElement("NOTES", "" + item.getNotes()));
            if(api_vers>=2.0){sb.append(API.buildTextElement("FACILITY_CODE",item.getFacilityCode()));}
        }   else
        if("RECEIVE".equalsIgnoreCase(item.getType()))
        {


                String rcvId = item.getTransactionNum();
                if(rcvId.contains("-")) {
                    rcvId = rcvId.substring(rcvId.indexOf("-") + 1);
                }


           sb.append("<RECEIVE_EVENT id=\"" + item.getReceiveId()
                + "\" created=\"" + df.format(item.getCreatedDate())
                  + "\" >");
            sb.append(API.buildTextElement("ASN_REFERENCE", "" + (asnInfoMap.get(rcvId).asnRef.equals("")?item.getRefNum():asnInfoMap.get(rcvId).asnRef)));
            sb.append(API.buildTextElement("PO_REFERENCE", "" + asnInfoMap.get(rcvId).poRef));
            sb.append(API.buildTextElement("OWD_ASN_ID", "" + asnInfoMap.get(rcvId).asnId));
            sb.append(API.buildTextElement("SHIPPER", "" + item.getShipperRef()));
            sb.append(API.buildTextElement("NOTES", "" + item.getNotes()));
            if(api_vers>=2.0){sb.append(API.buildTextElement("FACILITY_CODE",item.getFacilityCode()));}


        }   else
        {
            sb.append("<ADJUSTMENT_EVENT id=\"" + item.getReceiveId()
                + "\" created=\"" + df.format(item.getCreatedDate())
                  + "\" >");
            sb.append(API.buildTextElement("NOTES", "" + item.getNotes()));
            if(api_vers>=2.0){sb.append(API.buildTextElement("FACILITY_CODE",item.getFacilityCode()));}



        }
        if (item.getOwdReceiveItems().size() > 0) {

            StringBuilder sbitem = new StringBuilder();
            boolean hasValidItems = false;
                 sbitem.append("<ITEM_MOVEMENTS>");
                 for (OwdReceiveItem receiveItem : item.getOwdReceiveItems()) {

                     if(receiveItem.getQuantity()!=0 && (!("RECEIVE".equalsIgnoreCase(item.getType()) && receiveItem.getQuantity()>0 && ("DAMAGED".equalsIgnoreCase(receiveItem.getItemStatus())))))
                     {
                         hasValidItems = true;
                     sbitem.append("<ITEM_MOVEMENT id=\"" + receiveItem.getReceiveItemId()
                             + "\" quantity_change=\"" + receiveItem.getQuantity()
                                  + "\" sku=\"" + StringEscapeUtils.escapeXml(receiveItem.getInventoryNum())
                             + "\" title=\"" + StringEscapeUtils.escapeXml(receiveItem.getDescription())
                             + "\" notes=\"" + StringEscapeUtils.escapeXml(receiveItem.getReturnReason()==null?"":receiveItem.getReturnReason().replaceAll("null", ""))
                                 + "\" />");
                     }
                 }
                 sbitem.append("</ITEM_MOVEMENTS>");
                if(hasValidItems)
                {
                    sb.append(sbitem.toString());
                }
             }

         if("ADJUSTMENT".equalsIgnoreCase(item.getType()) )
        {
           sb.append("</ADJUSTMENT_EVENT>");

        }   else
        if ("RETURN".equalsIgnoreCase(item.getType()))
        {
           sb.append("</RETURN_EVENT>");
        }   else
        if("RECEIVE".equalsIgnoreCase(item.getType()))
        {
           sb.append("</RECEIVE_EVENT>");

        }   else
        {
            sb.append("</ADJUSTMENT_EVENT>");


        }
        }
        return sb.toString();
    }



}
