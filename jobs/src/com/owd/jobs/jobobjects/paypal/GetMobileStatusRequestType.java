package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetMobileStatusRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetMobileStatusRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}GetMobileStatusRequestDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetMobileStatusRequestType", propOrder = {
        "getMobileStatusRequestDetails"
})
public class GetMobileStatusRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "GetMobileStatusRequestDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected GetMobileStatusRequestDetailsType getMobileStatusRequestDetails;

    /**
     * Gets the value of the getMobileStatusRequestDetails property.
     *
     * @return possible object is
     *         {@link GetMobileStatusRequestDetailsType }
     */
    public GetMobileStatusRequestDetailsType getGetMobileStatusRequestDetails()
    {
        return getMobileStatusRequestDetails;
    }

    /**
     * Sets the value of the getMobileStatusRequestDetails property.
     *
     * @param value allowed object is
     *              {@link GetMobileStatusRequestDetailsType }
     */
    public void setGetMobileStatusRequestDetails(GetMobileStatusRequestDetailsType value)
    {
        this.getMobileStatusRequestDetails = value;
    }

}
