package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SellerPaymentMethodCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="SellerPaymentMethodCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Other"/>
 *     &lt;enumeration value="Amex"/>
 *     &lt;enumeration value="Visa"/>
 *     &lt;enumeration value="Mastercard"/>
 *     &lt;enumeration value="Discover"/>
 *     &lt;enumeration value="JCB"/>
 *     &lt;enumeration value="Diners"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "SellerPaymentMethodCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum SellerPaymentMethodCodeType
{

    @XmlEnumValue("Other")
    OTHER("Other"),
    @XmlEnumValue("Amex")
    AMEX("Amex"),
    @XmlEnumValue("Visa")
    VISA("Visa"),
    @XmlEnumValue("Mastercard")
    MASTERCARD("Mastercard"),
    @XmlEnumValue("Discover")
    DISCOVER("Discover"),
    JCB("JCB"),
    @XmlEnumValue("Diners")
    DINERS("Diners"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    SellerPaymentMethodCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static SellerPaymentMethodCodeType fromValue(String v)
    {
        for (SellerPaymentMethodCodeType c : SellerPaymentMethodCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
