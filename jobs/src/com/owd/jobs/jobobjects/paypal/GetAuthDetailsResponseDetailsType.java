package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetAuthDetailsResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetAuthDetailsResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PayerID" type="{urn:ebay:apis:eBLBaseComponents}UserIDType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAuthDetailsResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "firstName",
        "lastName",
        "email",
        "payerID"
})
public class GetAuthDetailsResponseDetailsType
{

    @XmlElement(name = "FirstName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String firstName;
    @XmlElement(name = "LastName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String lastName;
    @XmlElement(name = "Email", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String email;
    @XmlElement(name = "PayerID", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String payerID;

    /**
     * Gets the value of the firstName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFirstName(String value)
    {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLastName(String value)
    {
        this.lastName = value;
    }

    /**
     * Gets the value of the email property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the value of the email property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEmail(String value)
    {
        this.email = value;
    }

    /**
     * Gets the value of the payerID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayerID()
    {
        return payerID;
    }

    /**
     * Sets the value of the payerID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayerID(String value)
    {
        this.payerID = value;
    }

}
