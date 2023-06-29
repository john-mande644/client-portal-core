package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GeneralPaymentMethodCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="GeneralPaymentMethodCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Other"/>
 *     &lt;enumeration value="Echeck"/>
 *     &lt;enumeration value="ACH"/>
 *     &lt;enumeration value="Creditcard"/>
 *     &lt;enumeration value="PayPalBalance"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "GeneralPaymentMethodCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum GeneralPaymentMethodCodeType
{

    @XmlEnumValue("Other")
    OTHER("Other"),
    @XmlEnumValue("Echeck")
    ECHECK("Echeck"),
    ACH("ACH"),
    @XmlEnumValue("Creditcard")
    CREDITCARD("Creditcard"),
    @XmlEnumValue("PayPalBalance")
    PAY_PAL_BALANCE("PayPalBalance"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    GeneralPaymentMethodCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static GeneralPaymentMethodCodeType fromValue(String v)
    {
        for (GeneralPaymentMethodCodeType c : GeneralPaymentMethodCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
