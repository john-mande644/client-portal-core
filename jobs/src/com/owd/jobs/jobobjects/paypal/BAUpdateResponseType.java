package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BAUpdateResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BAUpdateResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}BAUpdateResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BAUpdateResponseType", propOrder = {
        "baUpdateResponseDetails"
})
public class BAUpdateResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "BAUpdateResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BAUpdateResponseDetailsType baUpdateResponseDetails;

    /**
     * Gets the value of the baUpdateResponseDetails property.
     *
     * @return possible object is
     *         {@link BAUpdateResponseDetailsType }
     */
    public BAUpdateResponseDetailsType getBAUpdateResponseDetails()
    {
        return baUpdateResponseDetails;
    }

    /**
     * Sets the value of the baUpdateResponseDetails property.
     *
     * @param value allowed object is
     *              {@link BAUpdateResponseDetailsType }
     */
    public void setBAUpdateResponseDetails(BAUpdateResponseDetailsType value)
    {
        this.baUpdateResponseDetails = value;
    }

}
