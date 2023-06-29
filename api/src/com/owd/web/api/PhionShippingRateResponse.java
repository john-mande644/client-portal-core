package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Address;
import com.owd.core.business.PackageRate;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 28, 2010
 * Time: 11:01:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhionShippingRateResponse extends APIResponse
{
private final static Logger log =  LogManager.getLogger();


    Address ratedAddress = new Address();
    List<PackageRate> response = null;
    boolean addressCorrected = false;
    Map codeMap = null;
    boolean addressValid=false;
    boolean hasFreeMethod=false;

    public boolean isHasFreeMethod() {
        return hasFreeMethod;
    }

    public void setHasFreeMethod(boolean hasFreeMethod) {
        this.hasFreeMethod = hasFreeMethod;
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

    public void setResponse(List<PackageRate> response)
    {
        this.response = response;
    }

    public void setAddressCorrected(boolean addressCorrected)
    {
        this.addressCorrected = addressCorrected;
    }

    public PhionShippingRateResponse(double api_v)
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

        responseBuffer.append("<PHION_SHIPPING_RATE_RESPONSE>");
        responseBuffer.append("<ADDRESS>");
        responseBuffer.append("<RESULT>"+(addressValid?"VALID":(addressCorrected?"CORRECTED":"UNCORRECTED"))+"</RESULT>");
        responseBuffer.append("<ADDRESS_1>"+ratedAddress.address_one.trim()+"</ADDRESS_1>");
        responseBuffer.append("<ADDRESS_2>"+ratedAddress.address_two+"</ADDRESS_2>");
        responseBuffer.append("<CITY>"+ratedAddress.city+"</CITY>");
        responseBuffer.append("<REGION>"+ratedAddress.state+"</REGION>");
        responseBuffer.append("<POSTCODE>"+ratedAddress.zip+"</POSTCODE>");
        responseBuffer.append("<COUNTRYCODE>"+ ratedAddress.country+"</COUNTRYCODE>");
        responseBuffer.append("</ADDRESS>");
        int optionCount = 0;
        StringBuffer options = new StringBuffer();

        if(isHasFreeMethod())
        {
            optionCount++;
            options.append("<RATE>");
            options.append("<CODE>22</CODE>");
            options.append("<NAME>Free Standard Shipping (Free)</NAME>");
            options.append("<COST>0.00</COST>");
            options.append("</RATE>");

        }
 for (PackageRate currShipment:response)
 {

     if(ratedAddress.isPOAPONew() && (!(currShipment.getMethodName().contains("USPS"))))
     {
        //responseBuffer.append("<ERROR>APO and PO Box addresses must ship USPS only</ERROR >");
     } else if(currShipment.getErrorCode()==0)
     {
        optionCount++;
         options.append("<RATE>");
         options.append("<CODE>"+codeMap.get(currShipment.getMethodCode())+"</CODE>");
         options.append("<NAME>"+currShipment.getMethodName().replaceAll(" \\(Addressee\\)","")+" ($"+currShipment.getFinalRate()+")</NAME>");
         options.append("<COST>"+(currShipment.getFinalRate())+"</COST>");
         options.append("</RATE>");
     }

        }

        responseBuffer.append("<RATES count=\""+optionCount+"\" >");
        responseBuffer.append(options.toString());
        responseBuffer.append("</RATES>");

        responseBuffer.append("</PHION_SHIPPING_RATE_RESPONSE>");
       // log.debug(""+responseBuffer);
        return responseBuffer.toString();


    }

}
