package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoUATPAuthorizationResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoUATPAuthorizationResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:api:PayPalAPI}DoAuthorizationResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}UATPDetails"/>
 *         &lt;element name="AuthorizationCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="InvoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoUATPAuthorizationResponseType", propOrder = {
        "uatpDetails",
        "authorizationCode",
        "invoiceID"
})
public class DoUATPAuthorizationResponseType
        extends DoAuthorizationResponseType
{

    @XmlElement(name = "UATPDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected UATPDetailsType uatpDetails;
    @XmlElement(name = "AuthorizationCode", required = true)
    protected String authorizationCode;
    @XmlElement(name = "InvoiceID")
    protected String invoiceID;

    /**
     * Gets the value of the uatpDetails property.
     *
     * @return possible object is
     *         {@link UATPDetailsType }
     */
    public UATPDetailsType getUATPDetails()
    {
        return uatpDetails;
    }

    /**
     * Sets the value of the uatpDetails property.
     *
     * @param value allowed object is
     *              {@link UATPDetailsType }
     */
    public void setUATPDetails(UATPDetailsType value)
    {
        this.uatpDetails = value;
    }

    /**
     * Gets the value of the authorizationCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAuthorizationCode()
    {
        return authorizationCode;
    }

    /**
     * Sets the value of the authorizationCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAuthorizationCode(String value)
    {
        this.authorizationCode = value;
    }

    /**
     * Gets the value of the invoiceID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getInvoiceID()
    {
        return invoiceID;
    }

    /**
     * Sets the value of the invoiceID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvoiceID(String value)
    {
        this.invoiceID = value;
    }

}
