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
 *         &lt;element ref="{urn:ebay:api:PayPalAPI}UpdateAccessPermissionsRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "updateAccessPermissionsRequest"
})
@XmlRootElement(name = "UpdateAccessPermissionsReq")
public class UpdateAccessPermissionsReq
{

    @XmlElement(name = "UpdateAccessPermissionsRequest", required = true)
    protected UpdateAccessPermissionsRequestType updateAccessPermissionsRequest;

    /**
     * Gets the value of the updateAccessPermissionsRequest property.
     *
     * @return possible object is
     *         {@link UpdateAccessPermissionsRequestType }
     */
    public UpdateAccessPermissionsRequestType getUpdateAccessPermissionsRequest()
    {
        return updateAccessPermissionsRequest;
    }

    /**
     * Sets the value of the updateAccessPermissionsRequest property.
     *
     * @param value allowed object is
     *              {@link UpdateAccessPermissionsRequestType }
     */
    public void setUpdateAccessPermissionsRequest(UpdateAccessPermissionsRequestType value)
    {
        this.updateAccessPermissionsRequest = value;
    }

}
