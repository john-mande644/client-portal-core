package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoUATPAuthorizationRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoUATPAuthorizationRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}UATPDetails"/>
 *         &lt;element name="TransactionEntity" type="{urn:ebay:apis:eBLBaseComponents}TransactionEntityType" minOccurs="0"/>
 *         &lt;element name="Amount" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType"/>
 *         &lt;element name="InvoiceID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoUATPAuthorizationRequestType", propOrder = {
        "uatpDetails",
        "transactionEntity",
        "amount",
        "invoiceID"
})
public class DoUATPAuthorizationRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "UATPDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected UATPDetailsType uatpDetails;
    @XmlElement(name = "TransactionEntity")
    protected TransactionEntityType transactionEntity;
    @XmlElement(name = "Amount", required = true)
    protected BasicAmountType amount;
    @XmlElement(name = "InvoiceID")
    protected String invoiceID;

    /**
     * UATP card details
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
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;b xmlns="http://www.w3.org/2001/XMLSchema"&gt;Required&lt;/b&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     *
     * @return possible object is
     *         {@link UATPDetailsType }
     */
    public UATPDetailsType getUATPDetails()
    {
        return uatpDetails;
    }

    /**
     * UATP card details
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
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;b xmlns="http://www.w3.org/2001/XMLSchema"&gt;Required&lt;/b&gt;
     * </pre>
     * <p/>
     * <pre>
     * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br xmlns="http://www.w3.org/2001/XMLSchema"/&gt;
     * </pre>
     *
     * @param value allowed object is
     *              {@link UATPDetailsType }
     */
    public void setUATPDetails(UATPDetailsType value)
    {
        this.uatpDetails = value;
    }

    /**
     * Gets the value of the transactionEntity property.
     *
     * @return possible object is
     *         {@link TransactionEntityType }
     */
    public TransactionEntityType getTransactionEntity()
    {
        return transactionEntity;
    }

    /**
     * Sets the value of the transactionEntity property.
     *
     * @param value allowed object is
     *              {@link TransactionEntityType }
     */
    public void setTransactionEntity(TransactionEntityType value)
    {
        this.transactionEntity = value;
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
