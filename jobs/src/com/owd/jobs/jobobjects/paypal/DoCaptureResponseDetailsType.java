package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoCaptureResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoCaptureResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}AuthorizationID"/>
 *         &lt;element name="PaymentInfo" type="{urn:ebay:apis:eBLBaseComponents}PaymentInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoCaptureResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "authorizationID",
        "paymentInfo"
})
public class DoCaptureResponseDetailsType
{

    @XmlElement(name = "AuthorizationID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String authorizationID;
    @XmlElement(name = "PaymentInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PaymentInfoType paymentInfo;

    /**
     * The authorization identification number you specified in the request.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * Character length and limits: 19 single-byte characters maximum
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAuthorizationID()
    {
        return authorizationID;
    }

    /**
     * The authorization identification number you specified in the request.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * Character length and limits: 19 single-byte characters maximum
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAuthorizationID(String value)
    {
        this.authorizationID = value;
    }

    /**
     * Gets the value of the paymentInfo property.
     *
     * @return possible object is
     *         {@link PaymentInfoType }
     */
    public PaymentInfoType getPaymentInfo()
    {
        return paymentInfo;
    }

    /**
     * Sets the value of the paymentInfo property.
     *
     * @param value allowed object is
     *              {@link PaymentInfoType }
     */
    public void setPaymentInfo(PaymentInfoType value)
    {
        this.paymentInfo = value;
    }

}
