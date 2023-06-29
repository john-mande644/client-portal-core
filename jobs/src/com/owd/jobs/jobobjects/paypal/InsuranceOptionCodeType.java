package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InsuranceOptionCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="InsuranceOptionCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Optional"/>
 *     &lt;enumeration value="Required"/>
 *     &lt;enumeration value="NotOffered"/>
 *     &lt;enumeration value="IncludedInShippingHandling"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "InsuranceOptionCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum InsuranceOptionCodeType
{

    @XmlEnumValue("Optional")
    OPTIONAL("Optional"),
    @XmlEnumValue("Required")
    REQUIRED("Required"),
    @XmlEnumValue("NotOffered")
    NOT_OFFERED("NotOffered"),
    @XmlEnumValue("IncludedInShippingHandling")
    INCLUDED_IN_SHIPPING_HANDLING("IncludedInShippingHandling"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    InsuranceOptionCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static InsuranceOptionCodeType fromValue(String v)
    {
        for (InsuranceOptionCodeType c : InsuranceOptionCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
