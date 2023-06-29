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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}SetAccessPermissionsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "setAccessPermissionsRequest"
})
@XmlRootElement(name = "SetAccessPermissionsReq")
public class SetAccessPermissionsReq
{

    @XmlElement(name = "SetAccessPermissionsRequest", required = true)
    protected SetAccessPermissionsRequestType setAccessPermissionsRequest;

    /**
     * Gets the value of the setAccessPermissionsRequest property.
     *
     * @return possible object is
     *         {@link SetAccessPermissionsRequestType }
     */
    public SetAccessPermissionsRequestType getSetAccessPermissionsRequest()
    {
        return setAccessPermissionsRequest;
    }

    /**
     * Sets the value of the setAccessPermissionsRequest property.
     *
     * @param value allowed object is
     *              {@link SetAccessPermissionsRequestType }
     */
    public void setSetAccessPermissionsRequest(SetAccessPermissionsRequestType value)
    {
        this.setAccessPermissionsRequest = value;
    }

}
