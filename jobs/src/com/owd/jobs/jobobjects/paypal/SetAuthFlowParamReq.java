package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}SetAuthFlowParamRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "setAuthFlowParamRequest"
})
@XmlRootElement(name = "SetAuthFlowParamReq")
public class SetAuthFlowParamReq
{

    @XmlElement(name = "SetAuthFlowParamRequest", required = true)
    protected SetAuthFlowParamRequestType setAuthFlowParamRequest;

    /**
     * Gets the value of the setAuthFlowParamRequest property.
     *
     * @return possible object is
     *         {@link SetAuthFlowParamRequestType }
     */
    public SetAuthFlowParamRequestType getSetAuthFlowParamRequest()
    {
        return setAuthFlowParamRequest;
    }

    /**
     * Sets the value of the setAuthFlowParamRequest property.
     *
     * @param value allowed object is
     *              {@link SetAuthFlowParamRequestType }
     */
    public void setSetAuthFlowParamRequest(SetAuthFlowParamRequestType value)
    {
        this.setAuthFlowParamRequest = value;
    }

}
