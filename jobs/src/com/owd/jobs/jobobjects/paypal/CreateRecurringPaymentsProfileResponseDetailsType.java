package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateRecurringPaymentsProfileResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CreateRecurringPaymentsProfileResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ProfileID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProfileStatus" type="{urn:ebay:apis:eBLBaseComponents}RecurringPaymentsProfileStatusType" minOccurs="0"/>
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DCCProcessorResponse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DCCReturnCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateRecurringPaymentsProfileResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "profileID",
        "profileStatus",
        "transactionID",
        "dccProcessorResponse",
        "dccReturnCode"
})
public class CreateRecurringPaymentsProfileResponseDetailsType
{

    @XmlElement(name = "ProfileID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String profileID;
    @XmlElement(name = "ProfileStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected RecurringPaymentsProfileStatusType profileStatus;
    @XmlElement(name = "TransactionID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String transactionID;
    @XmlElement(name = "DCCProcessorResponse", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String dccProcessorResponse;
    @XmlElement(name = "DCCReturnCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String dccReturnCode;

    /**
     * Gets the value of the profileID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProfileID()
    {
        return profileID;
    }

    /**
     * Sets the value of the profileID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProfileID(String value)
    {
        this.profileID = value;
    }

    /**
     * Gets the value of the profileStatus property.
     *
     * @return possible object is
     *         {@link RecurringPaymentsProfileStatusType }
     */
    public RecurringPaymentsProfileStatusType getProfileStatus()
    {
        return profileStatus;
    }

    /**
     * Sets the value of the profileStatus property.
     *
     * @param value allowed object is
     *              {@link RecurringPaymentsProfileStatusType }
     */
    public void setProfileStatus(RecurringPaymentsProfileStatusType value)
    {
        this.profileStatus = value;
    }

    /**
     * Gets the value of the transactionID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTransactionID()
    {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTransactionID(String value)
    {
        this.transactionID = value;
    }

    /**
     * Gets the value of the dccProcessorResponse property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDCCProcessorResponse()
    {
        return dccProcessorResponse;
    }

    /**
     * Sets the value of the dccProcessorResponse property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDCCProcessorResponse(String value)
    {
        this.dccProcessorResponse = value;
    }

    /**
     * Gets the value of the dccReturnCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDCCReturnCode()
    {
        return dccReturnCode;
    }

    /**
     * Sets the value of the dccReturnCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDCCReturnCode(String value)
    {
        this.dccReturnCode = value;
    }

}