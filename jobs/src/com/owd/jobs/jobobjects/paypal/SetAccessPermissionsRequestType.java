package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SetAccessPermissionsRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SetAccessPermissionsRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}SetAccessPermissionsRequestDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetAccessPermissionsRequestType", propOrder = {
        "setAccessPermissionsRequestDetails"
})
public class SetAccessPermissionsRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "SetAccessPermissionsRequestDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected SetAccessPermissionsRequestDetailsType setAccessPermissionsRequestDetails;

    /**
     * Gets the value of the setAccessPermissionsRequestDetails property.
     *
     * @return possible object is
     *         {@link SetAccessPermissionsRequestDetailsType }
     */
    public SetAccessPermissionsRequestDetailsType getSetAccessPermissionsRequestDetails()
    {
        return setAccessPermissionsRequestDetails;
    }

    /**
     * Sets the value of the setAccessPermissionsRequestDetails property.
     *
     * @param value allowed object is
     *              {@link SetAccessPermissionsRequestDetailsType }
     */
    public void setSetAccessPermissionsRequestDetails(SetAccessPermissionsRequestDetailsType value)
    {
        this.setAccessPermissionsRequestDetails = value;
    }

}
