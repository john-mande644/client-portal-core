package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contains information about a Charity listing.in case of revision - all data can be min occur = 0
 * <p/>
 * <p/>
 * <p>Java class for CharityType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CharityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="CharityName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CharityNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="DonationPercent" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CharityType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "charityName",
        "charityNumber",
        "donationPercent"
})
public class CharityType
{

    @XmlElement(name = "CharityName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String charityName;
    @XmlElement(name = "CharityNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer charityNumber;
    @XmlElement(name = "DonationPercent", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Float donationPercent;

    /**
     * Gets the value of the charityName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCharityName()
    {
        return charityName;
    }

    /**
     * Sets the value of the charityName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCharityName(String value)
    {
        this.charityName = value;
    }

    /**
     * Gets the value of the charityNumber property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getCharityNumber()
    {
        return charityNumber;
    }

    /**
     * Sets the value of the charityNumber property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setCharityNumber(Integer value)
    {
        this.charityNumber = value;
    }

    /**
     * Gets the value of the donationPercent property.
     *
     * @return possible object is
     *         {@link Float }
     */
    public Float getDonationPercent()
    {
        return donationPercent;
    }

    /**
     * Sets the value of the donationPercent property.
     *
     * @param value allowed object is
     *              {@link Float }
     */
    public void setDonationPercent(Float value)
    {
        this.donationPercent = value;
    }

}
