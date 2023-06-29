package com.owd.web.api;

import com.owd.hibernate.generated.OwdFacility;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 7/5/11
 * Time: 2:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class FacilityListResponse extends APIResponse

{
private final static Logger log =  LogManager.getLogger();

//XML root name

    public static final String kTagFacilityListResponse = "OWD_FACILITY_LIST_RESPONSE";

    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    private List<OwdFacility> facilityList = new ArrayList<OwdFacility>();


    public FacilityListResponse(double api_v)

    {

        super(api_v);

    }

    public void setFacilityList(List<OwdFacility> items)

    {
        if (items != null) {
            facilityList = items;

        }


    }

    public String getXML()

    {

        StringBuffer responseBuffer = new StringBuffer();

        responseBuffer.append("<" + kTagFacilityListResponse + ">");
        for (OwdFacility item : facilityList) {
            responseBuffer.append(getXMLFromItem(item));
        }
        responseBuffer.append("</" + kTagFacilityListResponse + ">");
        return responseBuffer.toString();


    }


    protected String getXMLFromItem(OwdFacility item) {



        StringBuffer sb = new StringBuffer();
        sb.append("<FACILITY>");

        sb.append(API.buildTextElement("CODE", "" + item.getFacilityCode()));
        sb.append(API.buildTextElement("METRO", "" + item.getMetroArea()));
        sb.append(API.buildTextElement("STREET", item.getAddress()));
        sb.append(API.buildTextElement("CITY", "" + item.getCity()));
        sb.append(API.buildTextElement("STATE", "" + item.getState()));
        sb.append(API.buildTextElement("POSTAL_CODE", item.getZip()));


        sb.append("</FACILITY>");

        log.debug(sb);
        return sb.toString();
    }


}

