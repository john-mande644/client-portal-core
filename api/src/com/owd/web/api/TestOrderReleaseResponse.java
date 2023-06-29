package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Address;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 28, 2010
 * Time: 11:01:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestOrderReleaseResponse extends APIResponse
{
private final static Logger log =  LogManager.getLogger();


    String pdfData=null;
    boolean released = false;

    public String getPdfData() {
        return pdfData;
    }

    public void setPdfData(String pdfData) {
        this.pdfData = pdfData;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public TestOrderReleaseResponse(double api_v)
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



        StringBuffer responseBuffer = new StringBuffer();

        responseBuffer.append("<OWD_TEST_ORDER_RELEASE_RESPONSE>");
        responseBuffer.append("<RELEASED>"+(isReleased()?1:0)+"</RELEASED>");
        responseBuffer.append("<PDFDATA>"+ getPdfData()+"</PDFDATA>");
        responseBuffer.append("</OWD_TEST_ORDER_RELEASE_RESPONSE>");
       // log.debug(""+responseBuffer);
        return responseBuffer.toString();


    }

}
