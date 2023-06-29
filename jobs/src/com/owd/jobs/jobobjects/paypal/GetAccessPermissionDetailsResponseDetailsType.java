package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for GetAccessPermissionDetailsResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetAccessPermissionDetailsResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccessPermissionName" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AccessPermissionStatus" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PayerID" type="{urn:ebay:apis:eBLBaseComponents}UserIDType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAccessPermissionDetailsResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "firstName",
        "lastName",
        "email",
        "accessPermissionName",
        "accessPermissionStatus",
        "payerID"
})
public class GetAccessPermissionDetailsResponseDetailsType
{

    @XmlElement(name = "FirstName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String firstName;
    @XmlElement(name = "LastName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String lastName;
    @XmlElement(name = "Email", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String email;
    @XmlElement(name = "AccessPermissionName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> accessPermissionName;
    @XmlElement(name = "AccessPermissionStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<String> accessPermissionStatus;
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
     * Gets the value of the accessPermissionName property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accessPermissionName property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccessPermissionName().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getAccessPermissionName()
    {
        if (accessPermissionName == null)
        {
            accessPermissionName = new ArrayList<String>();
        }
        return this.accessPermissionName;
    }

    /**
     * Gets the value of the accessPermissionStatus property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accessPermissionStatus property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccessPermissionStatus().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getAccessPermissionStatus()
    {
        if (accessPermissionStatus == null)
        {
            accessPermissionStatus = new ArrayList<String>();
        }
        return this.accessPermissionStatus;
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
