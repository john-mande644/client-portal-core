package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.PackageRate;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 28, 2010
 * Time: 11:01:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShippingRateResponse extends APIResponse
{
private final static Logger log =  LogManager.getLogger();


    Address ratedAddress = new Address();
    Map<String,List<PackageRate>> responseMap = new TreeMap<String,List<PackageRate>>();
    boolean addressCorrected = false;
    Map codeMap = null;
    boolean addressValid=false;
    double packageWeight=0.00;

    public double getPackageWeight() {
        return packageWeight;
    }

    public void setPackageWeight(double packageWeight) {
        this.packageWeight = packageWeight;
    }

    public boolean isAddressValid()
    {
        return addressValid;
    }

    public void setAddressValid(boolean addressValid)
    {
        this.addressValid = addressValid;
    }

    public void setShipMethodCodeMap(Map cmap)
    {
        codeMap = cmap;
    }

    public void setRatedAddress(Address ratedAddress)
    {
        this.ratedAddress = ratedAddress;
    }

    public void addResponse( String originCode,List<PackageRate> response)
    {
        this.responseMap.put(originCode,response);
    }

    public void setAddressCorrected(boolean addressCorrected)
    {
        this.addressCorrected = addressCorrected;
    }

    public ShippingRateResponse(double api_v)
    {
        super(api_v);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void setError(String errorType, String errorResponse)
    {
        super.setError(errorType, errorResponse);    //To change body of overridden methods use File | Settings | File Templates.
    }



    public String getXML()

    {


        if(addressCorrected && !addressValid)
        {
            if(ratedAddress.address_one.endsWith(ratedAddress.address_two.trim()))
            {
                ratedAddress.address_one = ratedAddress.address_one.substring(0,ratedAddress.address_one.lastIndexOf(ratedAddress.address_two));
            }
        }
        StringBuffer responseBuffer = new StringBuffer();

        responseBuffer.append("<OWD_SHIPPING_RATE_RESPONSE weight_lbs=\""+ OWDUtilities.roundDouble(getPackageWeight())+"\">");
        responseBuffer.append("<ADDRESS>");
        responseBuffer.append("<RESULT>"+(addressValid?"VALID":(addressCorrected?"CORRECTED":"UNCORRECTED"))+"</RESULT>");
        responseBuffer.append("<ADDRESS_1>"+ StringEscapeUtils.escapeXml(ratedAddress.address_one.trim()) + "</ADDRESS_1>");
        responseBuffer.append("<ADDRESS_2>"+StringEscapeUtils.escapeXml(ratedAddress.address_two)+"</ADDRESS_2>");
        responseBuffer.append("<CITY>"+StringEscapeUtils.escapeXml(ratedAddress.city)+"</CITY>");
        responseBuffer.append("<REGION>"+StringEscapeUtils.escapeXml(ratedAddress.state)+"</REGION>");
        responseBuffer.append("<POSTCODE>"+StringEscapeUtils.escapeXml(ratedAddress.zip)+"</POSTCODE>");
        responseBuffer.append("<COUNTRYCODE>"+ StringEscapeUtils.escapeXml(ratedAddress.country)+"</COUNTRYCODE>");
        responseBuffer.append("</ADDRESS>");


        for(String origin:responseMap.keySet())
        {
            if(api_vers>=2.0)
            {
                responseBuffer.append("<FACILITY>");
                responseBuffer.append("<FACILITY_CODE>"+origin+"</FACILITY_CODE>");
            }

            responseBuffer.append("<RATES>");
 for (PackageRate currShipment:responseMap.get(origin))
 {

        responseBuffer.append("<RATE>");
        responseBuffer.append("<CODE>"+StringEscapeUtils.escapeXml(""+codeMap.get(currShipment.getMethodCode()))+"</CODE>");
        responseBuffer.append("<NAME>"+StringEscapeUtils.escapeXml(currShipment.getMethodName())+"</NAME>");
     //todo correct to add smartpost
     if(ratedAddress.isPOAPONew() && (!(currShipment.getMethodName().toUpperCase().contains("USPS"))) && (!(currShipment.getMethodName().toUpperCase().contains("SMARTPOST"))))
     {
        responseBuffer.append("<ERROR>APO and PO Box addresses must ship USPS only</ERROR >");
     } else if(currShipment.getErrorCode()==0)
     {
        responseBuffer.append("<COST>"+StringEscapeUtils.escapeXml(""+currShipment.getFinalRate())+"</COST>");
     }else
     {
        responseBuffer.append("<ERROR>"+StringEscapeUtils.escapeXml(currShipment.getErrorMessage())+"</ERROR>");
     }

        responseBuffer.append("</RATE>");
        }

        responseBuffer.append("</RATES>");
            if(api_vers>=2.0)
            {
                responseBuffer.append("</FACILITY>");
            }
        }

        responseBuffer.append("</OWD_SHIPPING_RATE_RESPONSE>");
        log.debug(""+responseBuffer);
        return responseBuffer.toString();


    }


}
