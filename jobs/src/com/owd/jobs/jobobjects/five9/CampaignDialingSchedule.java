
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignDialingSchedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="campaignDialingSchedule">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="dialASAPSortOrder" type="{http://service.admin.ws.five9.com/v2/}dialSortOrder" minOccurs="0"/>
 *         &lt;element name="dialASAPTimeout" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="dialASAPTimeoutPeriod" type="{http://service.admin.ws.five9.com/v2/}timePeriod" minOccurs="0"/>
 *         &lt;element name="dialingOrder" type="{http://service.admin.ws.five9.com/v2/}campaignDialingOrder" minOccurs="0"/>
 *         &lt;element name="dialingSchedules" type="{http://service.admin.ws.five9.com/v2/}campaignNumberSchedule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="includeNumbers" type="{http://service.admin.ws.five9.com/v2/}campaignDialNumber" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campaignDialingSchedule", propOrder = {
    "dialASAPSortOrder",
    "dialASAPTimeout",
    "dialASAPTimeoutPeriod",
    "dialingOrder",
    "dialingSchedules",
    "includeNumbers"
})
public class CampaignDialingSchedule {

    protected DialSortOrder dialASAPSortOrder;
    protected Integer dialASAPTimeout;
    protected TimePeriod dialASAPTimeoutPeriod;
    protected CampaignDialingOrder dialingOrder;
    @XmlElement(nillable = true)
    protected List<CampaignNumberSchedule> dialingSchedules;
    @XmlElement(nillable = true)
    protected List<CampaignDialNumber> includeNumbers;

    /**
     * Gets the value of the dialASAPSortOrder property.
     * 
     * @return
     *     possible object is
     *     {@link DialSortOrder }
     *     
     */
    public DialSortOrder getDialASAPSortOrder() {
        return dialASAPSortOrder;
    }

    /**
     * Sets the value of the dialASAPSortOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link DialSortOrder }
     *     
     */
    public void setDialASAPSortOrder(DialSortOrder value) {
        this.dialASAPSortOrder = value;
    }

    /**
     * Gets the value of the dialASAPTimeout property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDialASAPTimeout() {
        return dialASAPTimeout;
    }

    /**
     * Sets the value of the dialASAPTimeout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDialASAPTimeout(Integer value) {
        this.dialASAPTimeout = value;
    }

    /**
     * Gets the value of the dialASAPTimeoutPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link TimePeriod }
     *     
     */
    public TimePeriod getDialASAPTimeoutPeriod() {
        return dialASAPTimeoutPeriod;
    }

    /**
     * Sets the value of the dialASAPTimeoutPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimePeriod }
     *     
     */
    public void setDialASAPTimeoutPeriod(TimePeriod value) {
        this.dialASAPTimeoutPeriod = value;
    }

    /**
     * Gets the value of the dialingOrder property.
     * 
     * @return
     *     possible object is
     *     {@link CampaignDialingOrder }
     *     
     */
    public CampaignDialingOrder getDialingOrder() {
        return dialingOrder;
    }

    /**
     * Sets the value of the dialingOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link CampaignDialingOrder }
     *     
     */
    public void setDialingOrder(CampaignDialingOrder value) {
        this.dialingOrder = value;
    }

    /**
     * Gets the value of the dialingSchedules property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dialingSchedules property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDialingSchedules().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampaignNumberSchedule }
     * 
     * 
     */
    public List<CampaignNumberSchedule> getDialingSchedules() {
        if (dialingSchedules == null) {
            dialingSchedules = new ArrayList<CampaignNumberSchedule>();
        }
        return this.dialingSchedules;
    }

    /**
     * Gets the value of the includeNumbers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the includeNumbers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncludeNumbers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CampaignDialNumber }
     * 
     * 
     */
    public List<CampaignDialNumber> getIncludeNumbers() {
        if (includeNumbers == null) {
            includeNumbers = new ArrayList<CampaignDialNumber>();
        }
        return this.includeNumbers;
    }

}
