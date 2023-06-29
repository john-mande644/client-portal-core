package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Custom Securiy Header.
 * <p/>
 * <p/>
 * <p>Java class for CustomSecurityHeaderType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="CustomSecurityHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="eBayAuthToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HardExpirationWarning" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Credentials" type="{urn:ebay:apis:eBLBaseComponents}UserIdPasswordType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomSecurityHeaderType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "eBayAuthToken",
        "hardExpirationWarning",
        "credentials"
})
public class CustomSecurityHeaderType
{

    @XmlElement(namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String eBayAuthToken;
    @XmlElement(name = "HardExpirationWarning", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String hardExpirationWarning;
    @XmlElement(name = "Credentials", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected UserIdPasswordType credentials;

    /**
     * Gets the value of the eBayAuthToken property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getEBayAuthToken()
    {
        return eBayAuthToken;
    }

    /**
     * Sets the value of the eBayAuthToken property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEBayAuthToken(String value)
    {
        this.eBayAuthToken = value;
    }

    /**
     * Gets the value of the hardExpirationWarning property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getHardExpirationWarning()
    {
        return hardExpirationWarning;
    }

    /**
     * Sets the value of the hardExpirationWarning property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHardExpirationWarning(String value)
    {
        this.hardExpirationWarning = value;
    }

    /**
     * Gets the value of the credentials property.
     *
     * @return possible object is
     *         {@link UserIdPasswordType }
     */
    public UserIdPasswordType getCredentials()
    {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     *
     * @param value allowed object is
     *              {@link UserIdPasswordType }
     */
    public void setCredentials(UserIdPasswordType value)
    {
        this.credentials = value;
    }

}
