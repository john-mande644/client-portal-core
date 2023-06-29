package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManagePendingTransactionStatusRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ManagePendingTransactionStatusRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="TransactionID" type="{urn:ebay:apis:eBLBaseComponents}TransactionId"/>
 *         &lt;element name="Action" type="{urn:ebay:apis:eBLBaseComponents}FMFPendingTransactionActionType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManagePendingTransactionStatusRequestType", propOrder = {
        "transactionID",
        "action"
})
public class ManagePendingTransactionStatusRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "TransactionID", required = true)
    protected String transactionID;
    @XmlElement(name = "Action", required = true)
    protected FMFPendingTransactionActionType action;

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
     * Gets the value of the action property.
     *
     * @return possible object is
     *         {@link FMFPendingTransactionActionType }
     */
    public FMFPendingTransactionActionType getAction()
    {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value allowed object is
     *              {@link FMFPendingTransactionActionType }
     */
    public void setAction(FMFPendingTransactionActionType value)
    {
        this.action = value;
    }

}
