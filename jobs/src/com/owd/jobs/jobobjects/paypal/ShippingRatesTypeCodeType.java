package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingRatesTypeCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ShippingRatesTypeCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Flat"/>
 *     &lt;enumeration value="Calculated"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ShippingRatesTypeCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ShippingRatesTypeCodeType
{

    @XmlEnumValue("Flat")
    FLAT("Flat"),
    @XmlEnumValue("Calculated")
    CALCULATED("Calculated"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    ShippingRatesTypeCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static ShippingRatesTypeCodeType fromValue(String v)
    {
        for (ShippingRatesTypeCodeType c : ShippingRatesTypeCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
