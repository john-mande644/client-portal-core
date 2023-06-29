package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManageRecurringPaymentsProfileStatusRequestDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ManageRecurringPaymentsProfileStatusRequestDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ProfileID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Action" type="{urn:ebay:apis:eBLBaseComponents}StatusChangeActionType"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManageRecurringPaymentsProfileStatusRequestDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "profileID",
        "action",
        "note"
})
public class ManageRecurringPaymentsProfileStatusRequestDetailsType
{

    @XmlElement(name = "ProfileID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String profileID;
    @XmlElement(name = "Action", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected StatusChangeActionType action;
    @XmlElement(name = "Note", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String note;

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
     * Gets the value of the action property.
     *
     * @return possible object is
     *         {@link StatusChangeActionType }
     */
    public StatusChangeActionType getAction()
    {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value allowed object is
     *              {@link StatusChangeActionType }
     */
    public void setAction(StatusChangeActionType value)
    {
        this.action = value;
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

}
