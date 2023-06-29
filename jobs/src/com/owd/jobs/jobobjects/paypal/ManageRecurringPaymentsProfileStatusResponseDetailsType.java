package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManageRecurringPaymentsProfileStatusResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ManageRecurringPaymentsProfileStatusResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ProfileID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManageRecurringPaymentsProfileStatusResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "profileID"
})
public class ManageRecurringPaymentsProfileStatusResponseDetailsType
{

    @XmlElement(name = "ProfileID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String profileID;

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

}
