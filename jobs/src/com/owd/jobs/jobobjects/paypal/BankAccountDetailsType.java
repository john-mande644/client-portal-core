package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * BankAccountDetailsType
 * <p/>
 * <p/>
 * <p>Java class for BankAccountDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BankAccountDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Type" type="{urn:ebay:apis:eBLBaseComponents}BankAccountTypeType"/>
 *         &lt;element name="RoutingNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AccountNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAccountDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "name",
        "type",
        "routingNumber",
        "accountNumber"
})
public class BankAccountDetailsType
{

    @XmlElement(name = "Name", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String name;
    @XmlElement(name = "Type", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BankAccountTypeType type;
    @XmlElement(name = "RoutingNumber", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String routingNumber;
    @XmlElement(name = "AccountNumber", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String accountNumber;

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value)
    {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is
     *         {@link BankAccountTypeType }
     */
    public BankAccountTypeType getType()
    {
        return type;
    }

    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is
     *              {@link BankAccountTypeType }
     */
    public void setType(BankAccountTypeType value)
    {
        this.type = value;
    }

    /**
     * Gets the value of the routingNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getRoutingNumber()
    {
        return routingNumber;
    }

    /**
     * Sets the value of the routingNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRoutingNumber(String value)
    {
        this.routingNumber = value;
    }

    /**
     * Gets the value of the accountNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAccountNumber()
    {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAccountNumber(String value)
    {
        this.accountNumber = value;
    }

}
