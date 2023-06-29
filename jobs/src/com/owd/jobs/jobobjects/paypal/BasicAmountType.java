package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * On requests, you must set the currencyID attribute to one of the three-character currency codes for any of the supported PayPal currencies.
 * <p/>
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br/&gt;
 * </pre>
 * <p/>
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;br/&gt;
 * </pre>
 * <p/>
 * Limitations: Must not exceed $10,000 USD in any currency. No currency symbol. Decimal separator must be a period (.), and the thousands separator must be a comma (,).
 * <p/>
 * <p>Java class for BasicAmountType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BasicAmountType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="currencyID" use="required" type="{urn:ebay:apis:eBLBaseComponents}CurrencyCodeType" />
private final static Logger log =  LogManager.getLogger();
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BasicAmountType", namespace = "urn:ebay:apis:CoreComponentTypes", propOrder = {
        "value"
})
public class BasicAmountType
{

    @XmlValue
    protected String value;
    @XmlAttribute(required = true)
    protected CurrencyCodeType currencyID;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * Gets the value of the currencyID property.
     *
     * @return possible object is
     *         {@link CurrencyCodeType }
     */
    public CurrencyCodeType getCurrencyID()
    {
        return currencyID;
    }

    /**
     * Sets the value of the currencyID property.
     *
     * @param value allowed object is
     *              {@link CurrencyCodeType }
     */
    public void setCurrencyID(CurrencyCodeType value)
    {
        this.currencyID = value;
    }

}
