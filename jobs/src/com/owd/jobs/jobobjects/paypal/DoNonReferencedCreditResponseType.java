package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoNonReferencedCreditResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoNonReferencedCreditResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}DoNonReferencedCreditResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoNonReferencedCreditResponseType", propOrder = {
        "doNonReferencedCreditResponseDetails"
})
public class DoNonReferencedCreditResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "DoNonReferencedCreditResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected DoNonReferencedCreditResponseDetailsType doNonReferencedCreditResponseDetails;

    /**
     * Gets the value of the doNonReferencedCreditResponseDetails property.
     *
     * @return possible object is
     *         {@link DoNonReferencedCreditResponseDetailsType }
     */
    public DoNonReferencedCreditResponseDetailsType getDoNonReferencedCreditResponseDetails()
    {
        return doNonReferencedCreditResponseDetails;
    }

    /**
     * Sets the value of the doNonReferencedCreditResponseDetails property.
     *
     * @param value allowed object is
     *              {@link DoNonReferencedCreditResponseDetailsType }
     */
    public void setDoNonReferencedCreditResponseDetails(DoNonReferencedCreditResponseDetailsType value)
    {
        this.doNonReferencedCreditResponseDetails = value;
    }

}
