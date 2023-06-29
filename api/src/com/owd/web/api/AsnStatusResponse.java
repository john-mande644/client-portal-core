package com.owd.web.api;

import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import org.apache.commons.lang.StringEscapeUtils;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 7/5/11
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsnStatusResponse extends APIResponse

{
private final static Logger log =  LogManager.getLogger();

    static Map<Integer,String> statusMap = new TreeMap<Integer,String>() ;
//XML root name

    public static final String kTagAsnStatusResponse = "OWD_ASN_STATUS_RESPONSE";

    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    private List<Asn> asnList = new ArrayList<Asn>();

    static {
        //static init
        statusMap.put(0,"UNRECEIVED") ;
        statusMap.put(1,"COMPLETE") ;
        statusMap.put(2,"PARTIALRECEIPT") ;
        statusMap.put(3,"CANCELLED") ;
    }
    public AsnStatusResponse(double api_v)

    {

        super(api_v);

    }

    public void setAsnList(List<Asn> items)

    {
        if (items != null) {
            asnList = items;

        }


    }

    public String getXML()

    {

        StringBuffer responseBuffer = new StringBuffer();

        responseBuffer.append("<" + kTagAsnStatusResponse + ">");
        responseBuffer.append("<COUNT>" + asnList.size() + "</COUNT>");
        for (Asn item : asnList) {
            responseBuffer.append(getXMLFromItem(item));
        }
        responseBuffer.append("</" + kTagAsnStatusResponse + ">");
        return responseBuffer.toString();


    }


    protected String getXMLFromItem(Asn item) {

          boolean completedReceivesAvailable = false;
        boolean hasPendingReceive = false;
         int pendingReceiveCount = 0;
          if (item.getReceives().size() > 0) {
              completedReceivesAvailable = true;
            for (Receive receive : item.getReceives()) {

               if("PENDING".equalsIgnoreCase(receive.getCreatedBy()))
               {
                   hasPendingReceive = true;
                   pendingReceiveCount++;
               }
            }
          }

        if(hasPendingReceive && completedReceivesAvailable)
        {
            if(item.getReceives().size()<=pendingReceiveCount)
            {
               completedReceivesAvailable = false;
            }
        }


        StringBuffer sb = new StringBuffer();
        sb.append("<OWD_ASN_STATUS id=\"" + item.getId()
                + "\" created=\"" + df.format(item.getCreatedDate())
                + "\" createdBy=\"" + item.getCreatedBy()
                + "\" receive_count=\"" + item.getReceiveCount()
                + "\" receive_active=\"" + (hasPendingReceive?"1":"0")
                  + "\" >");

        sb.append(API.buildTextElement("STATUS", "" + statusMap.get(item.getStatus())));
        sb.append(API.buildTextElement("REFERENCE", "" + item.getClientRef()));
        sb.append(API.buildTextElement("PO_REFERENCE", item.getClientPo()));
        sb.append(API.buildTextElement("NOTES", "" + item.getNotes()));
        sb.append(API.buildTextElement("SHIPPER", "" + item.getShipperName()));
        sb.append(API.buildTextElement("EXPECTED_DATE", df.format(item.getExpectDate())));
        sb.append(API.buildTextElement("CARRIER", item.getCarrier()));
        sb.append(API.buildTextElement("AUTORELEASE", item.getIsAutorelease()+""));
        sb.append(API.buildTextElement("CARTONS", item.getCartonCount()+""));
        sb.append(API.buildTextElement("PALLETS", "" + item.getPalletCount()));
        sb.append(API.buildTextElement("GROUPNAME", "" + item.getGroupName()==null?"":item.getGroupName()));
        sb.append(API.buildTextElement("FACILITY_CODE", "" + item.getFacilityCode()==null?"DC1":item.getFacilityCode()));


        //items
        if (item.getAsnItems().size() > 0) {
            sb.append("<ASNITEMS>");
            for (AsnItem asnItem : item.getAsnItems()) {
                sb.append("<ASNITEM "
                             + " qtyReceived=\"" + asnItem.getReceived()
                             + "\" total_remaining=\"" + (asnItem.getExpected()-asnItem.getReceived())
                            + "\" >");

                sb.append(API.buildTextElement("ASNITEM_SKU", asnItem.getInventoryNum()));
                sb.append(API.buildTextElement("ASNITEM_EXPECTED_QTY", asnItem.getExpected() + ""));
                sb.append(API.buildTextElement("ASNITEM_DESCRIPTION", asnItem.getTitle() + ""));
                log.debug("ESCAPED:"+API.buildTextElement("ASNITEM_DESCRIPTION", asnItem.getTitle() + ""));
                sb.append("</ASNITEM>");
            }
            sb.append("</ASNITEMS>");
        }

        if (item.getReceiveCount() > 0) {
            sb.append("<RECEIVES>");
            for (Receive receive : item.getReceives()) {
                if(!("PENDING".equalsIgnoreCase(receive.getCreatedBy())))
                {
                sb.append("<RECEIVE id=\"" + receive.getId()
                        + "\" created=\"" + df.format(receive.getCreatedOn())
                        + "\" createdBy=\"" + receive.getCreatedBy()
                        + "\" receiveMinutes=\"" + receive.getBilledMinutes()
                        + "\" receivedCartons=\"" + receive.getCartonCount()
                        + "\" released=\"" + receive.getIsPosted()
                        + "\" receivedPallets=\"" + receive.getPalletCount()
                        + (api_vers>=2.0?"\" facility_code=\"" + receive.getFacilityCode():"")
                        + "\" notes=\"" +  StringEscapeUtils.escapeXml(receive.getNotes())
                        + (receive.getIsPosted() == 1 ? "\" releaseDate=\"" + df.format(receive.getPostDate()) : "")
                          + "\" >");


                if (receive.getReceiveItems().size() > 0) {
                    sb.append("<RECEIVE_ITEMS>");
                    for (ReceiveItem receiveItem : receive.getReceiveItems()) {
                        sb.append("<RECEIVE_ITEM "
                             + " sku=\"" + receiveItem.getAsnItem().getInventoryNum()
                             + "\" countMethod=\"" + ("SEE RECEIVE".equalsIgnoreCase(receiveItem.getCountMethod())?receive.getCountMethod():receiveItem.getCountMethod())
                             + "\" quantity_received=\"" + receiveItem.getQtyReceive()
                             + "\" quantity_unusable=\"" + receiveItem.getQtyDamage()
                          + "\" />");   //end RECEIVE_ITEM
                    }
                    sb.append("</RECEIVE_ITEMS>");
                }
                if (receive.getScanDocs().size() > 0) {
                    sb.append("<RECEIVE_SCAN_URLS>");
                    for (ScanReceive scanItem : receive.getScanDocs()) {
                        sb.append("<RECEIVE_SCAN_URL>");
                        try
                        {
                            log.debug("ID:"+scanItem.getId());
                            log.debug("scanId:"+scanItem.getId());
                            log.debug("Class:"+scanItem.getClass().getName());

                            log.debug("encrypting "+receive.getClientFkey()+":"+scanItem.getId()+":goobledygookIsAPerfectlyGoodSaltValue");

                            URI uri = new URI(
                                    "https",
                                    "secure.owd.com",
                                    "/webapps/api/scans.jsp",
                                    "auth="+OWDUtilities.encryptData(receive.getClientFkey() + ":" + scanItem.getId() + ":goobledygookIsAPerfectlyGoodSaltValue"),
                                    null);

                            sb.append(StringEscapeUtils.escapeXml(uri.toASCIIString()));
                         //   sb.append(new sun.misc.BASE64Encoder().encode(ScanManager.getScanFromReceive(receive,scanItem.getName())));
                        }catch(Exception ex)
                        {
                            log.debug("Unable to get scandata!");
                        }
                        sb.append("</RECEIVE_SCAN_URL>");
                    }
                    sb.append("</RECEIVE_SCAN_URLS>");
                }
                sb.append("</RECEIVE>");  //end RECEIVE
                }
            }
            sb.append("</RECEIVES>");
        }

        sb.append("</OWD_ASN_STATUS>");

        log.debug(sb);
        return sb.toString();
    }


}

