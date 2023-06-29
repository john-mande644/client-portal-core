package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoReferenceTransactionRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoReferenceTransactionRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}DoReferenceTransactionRequestDetails"/>
 *         &lt;element name="ReturnFMFDetails" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoReferenceTransactionRequestType", propOrder = {
        "doReferenceTransactionRequestDetails",
        "returnFMFDetails"
})
public class DoReferenceTransactionRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "DoReferenceTransactionRequestDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected DoReferenceTransactionRequestDetailsType doReferenceTransactionRequestDetails;
    @XmlElement(name = "ReturnFMFDetails")
    protected Integer returnFMFDetails;

    /**
     * Gets the value of the doReferenceTransactionRequestDetails property.
     *
     * @return possible object is
     *         {@link DoReferenceTransactionRequestDetailsType }
     */
    public DoReferenceTransactionRequestDetailsType getDoReferenceTransactionRequestDetails()
    {
        return doReferenceTransactionRequestDetails;
    }

    /**
     * Sets the value of the doReferenceTransactionRequestDetails property.
     *
     * @param value allowed object is
     *              {@link DoReferenceTransactionRequestDetailsType }
     */
    public void setDoReferenceTransactionRequestDetails(DoReferenceTransactionRequestDetailsType value)
    {
        this.doReferenceTransactionRequestDetails = value;
    }

    /**
     * Gets the value of the returnFMFDetails property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getReturnFMFDetails()
    {
        return returnFMFDetails;
    }

    /**
     * Sets the value of the returnFMFDetails property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setReturnFMFDetails(Integer value)
    {
        this.returnFMFDetails = value;
    }

}
