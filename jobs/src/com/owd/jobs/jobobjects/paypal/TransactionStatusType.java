package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for TransactionStatusType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="TransactionStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="eBayPaymentStatus" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="IncompleteState" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LastTimeModified" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PaymentMethodUsed" type="{urn:ebay:apis:eBLBaseComponents}BuyerPaymentMethodCodeType" minOccurs="0"/>
 *         &lt;element name="StatusIs" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionStatusType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "eBayPaymentStatus",
        "incompleteState",
        "lastTimeModified",
        "paymentMethodUsed",
        "statusIs"
})
public class TransactionStatusType
{

    @XmlElement(namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer eBayPaymentStatus;
    @XmlElement(name = "IncompleteState", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer incompleteState;
    @XmlElement(name = "LastTimeModified", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar lastTimeModified;
    @XmlElement(name = "PaymentMethodUsed", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BuyerPaymentMethodCodeType paymentMethodUsed;
    @XmlElement(name = "StatusIs", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer statusIs;

    /**
     * Gets the value of the eBayPaymentStatus property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getEBayPaymentStatus()
    {
        return eBayPaymentStatus;
    }

    /**
     * Sets the value of the eBayPaymentStatus property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setEBayPaymentStatus(Integer value)
    {
        this.eBayPaymentStatus = value;
    }

    /**
     * Gets the value of the incompleteState property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getIncompleteState()
    {
        return incompleteState;
    }

    /**
     * Sets the value of the incompleteState property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setIncompleteState(Integer value)
    {
        this.incompleteState = value;
    }

    /**
     * Gets the value of the lastTimeModified property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getLastTimeModified()
    {
        return lastTimeModified;
    }

    /**
     * Sets the value of the lastTimeModified property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setLastTimeModified(XMLGregorianCalendar value)
    {
        this.lastTimeModified = value;
    }

    /**
     * Gets the value of the paymentMethodUsed property.
     *
     * @return possible object is
     *         {@link BuyerPaymentMethodCodeType }
     */
    public BuyerPaymentMethodCodeType getPaymentMethodUsed()
    {
        return paymentMethodUsed;
    }

    /**
     * Sets the value of the paymentMethodUsed property.
     *
     * @param value allowed object is
     *              {@link BuyerPaymentMethodCodeType }
     */
    public void setPaymentMethodUsed(BuyerPaymentMethodCodeType value)
    {
        this.paymentMethodUsed = value;
    }

    /**
     * Gets the value of the statusIs property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getStatusIs()
    {
        return statusIs;
    }

    /**
     * Sets the value of the statusIs property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setStatusIs(Integer value)
    {
        this.statusIs = value;
    }

}
