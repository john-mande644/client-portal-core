package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FaultDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="FaultDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Severity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DetailedMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "errorCode",
        "severity",
        "detailedMessage"
})
public class FaultDetailsType
{

    @XmlElement(name = "ErrorCode", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String errorCode;
    @XmlElement(name = "Severity", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String severity;
    @XmlElement(name = "DetailedMessage", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String detailedMessage;

    /**
     * Gets the value of the errorCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getErrorCode()
    {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setErrorCode(String value)
    {
        this.errorCode = value;
    }

    /**
     * Gets the value of the severity property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getSeverity()
    {
        return severity;
    }

    /**
     * Sets the value of the severity property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSeverity(String value)
    {
        this.severity = value;
    }

    /**
     * Gets the value of the detailedMessage property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDetailedMessage()
    {
        return detailedMessage;
    }

    /**
     * Sets the value of the detailedMessage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDetailedMessage(String value)
    {
        this.detailedMessage = value;
    }

}
