package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoCaptureRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoCaptureRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="AuthorizationID" type="{urn:ebay:apis:eBLBaseComponents}AuthorizationId"/>
 *         &lt;element name="Amount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="CompleteType" type="{urn:ebay:apis:eBLBaseComponents}CompleteCodeType"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}EnhancedData" minOccurs="0"/>
 *         &lt;element name="Descriptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoCaptureRequestType", propOrder = {
        "authorizationID",
        "amount",
        "completeType",
        "note",
        "invoiceID",
        "enhancedData",
        "descriptor"
})
public class DoCaptureRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "AuthorizationID", required = true)
    protected String authorizationID;
    @XmlElement(name = "Amount", required = true)
    protected BasicAmountType amount;
    @XmlElement(name = "CompleteType", required = true)
    protected CompleteCodeType completeType;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "InvoiceID")
    protected String invoiceID;
    @XmlElement(name = "EnhancedData", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected EnhancedDataType enhancedData;
    @XmlElement(name = "Descriptor")
    protected String descriptor;

    /**
     * Gets the value of the authorizationID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAuthorizationID()
    {
        return authorizationID;
    }

    /**
     * Sets the value of the authorizationID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAuthorizationID(String value)
    {
        this.authorizationID = value;
    }

    /**
     * Gets the value of the amount property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getAmount()
    {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setAmount(BasicAmountType value)
    {
        this.amount = value;
    }

    /**
     * Gets the value of the completeType property.
     *
     * @return possible object is
     *         {@link CompleteCodeType }
     */
    public CompleteCodeType getCompleteType()
    {
        return completeType;
    }

    /**
     * Sets the value of the completeType property.
     *
     * @param value allowed object is
     *              {@link CompleteCodeType }
     */
    public void setCompleteType(CompleteCodeType value)
    {
        this.completeType = value;
    }

    /**
     * Gets the value of the note property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getNote()
    {
        return note;
    }

    /**
     * Sets the value of the note property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNote(String value)
    {
        this.note = value;
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

    /**
     * Contains enhanced data like airline itinerary information.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;b xmlns="http://www.w3.org/2001/XMLSchema"&gt;Not Required&lt;/b&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     *
     * @return possible object is
     *         {@link EnhancedDataType }
     */
    public EnhancedDataType getEnhancedData()
    {
        return enhancedData;
    }

    /**
     * Contains enhanced data like airline itinerary information.
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;b xmlns="http://www.w3.org/2001/XMLSchema"&gt;Not Required&lt;/b&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     *
     * @param value allowed object is
     *              {@link EnhancedDataType }
     */
    public void setEnhancedData(EnhancedDataType value)
    {
        this.enhancedData = value;
    }

    /**
     * Gets the value of the descriptor property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDescriptor()
    {
        return descriptor;
    }

    /**
     * Sets the value of the descriptor property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescriptor(String value)
    {
        this.descriptor = value;
    }

}
