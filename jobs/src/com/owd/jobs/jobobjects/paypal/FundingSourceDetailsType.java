package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FundingSourceDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="FundingSourceDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="AllowPushFunding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserSelectedFundingSource" type="{urn:ebay:apis:eBLBaseComponents}UserSelectedFundingSourceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FundingSourceDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "allowPushFunding",
        "userSelectedFundingSource"
})
public class FundingSourceDetailsType
{

    @XmlElement(name = "AllowPushFunding", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String allowPushFunding;
    @XmlElement(name = "UserSelectedFundingSource", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected UserSelectedFundingSourceType userSelectedFundingSource;

    /**
     * Gets the value of the allowPushFunding property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAllowPushFunding()
    {
        return allowPushFunding;
    }

    /**
     * Sets the value of the allowPushFunding property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAllowPushFunding(String value)
    {
        this.allowPushFunding = value;
    }

    /**
     * Gets the value of the userSelectedFundingSource property.
     *
     * @return possible object is
     *         {@link UserSelectedFundingSourceType }
     */
    public UserSelectedFundingSourceType getUserSelectedFundingSource()
    {
        return userSelectedFundingSource;
    }

    /**
     * Sets the value of the userSelectedFundingSource property.
     *
     * @param value allowed object is
     *              {@link UserSelectedFundingSourceType }
     */
    public void setUserSelectedFundingSource(UserSelectedFundingSourceType value)
    {
        this.userSelectedFundingSource = value;
    }

}
