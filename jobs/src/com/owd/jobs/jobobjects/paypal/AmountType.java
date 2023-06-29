package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for AmountType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="AmountType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>double">
 *       &lt;attribute name="currencyID" use="required" type="{urn:ebay:apis:eBLBaseComponents}CurrencyCodeType" />
private final static Logger log =  LogManager.getLogger();
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountType", namespace = "urn:ebay:apis:CoreComponentTypes", propOrder = {
        "value"
})
public class AmountType
{

    @XmlValue
    protected double value;
    @XmlAttribute(required = true)
    protected CurrencyCodeType currencyID;

    /**
     * Gets the value of the value property.
     */
    public double getValue()
    {
        return value;
    }

    /**
     * Sets the value of the value property.
     */
    public void setValue(double value)
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
