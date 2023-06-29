package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VATStatusCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="VATStatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NoVATTax"/>
 *     &lt;enumeration value="VATTax"/>
 *     &lt;enumeration value="VATExempt"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "VATStatusCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum VATStatusCodeType
{

    @XmlEnumValue("NoVATTax")
    NO_VAT_TAX("NoVATTax"),
    @XmlEnumValue("VATTax")
    VAT_TAX("VATTax"),
    @XmlEnumValue("VATExempt")
    VAT_EXEMPT("VATExempt"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    VATStatusCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static VATStatusCodeType fromValue(String v)
    {
        for (VATStatusCodeType c : VATStatusCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
