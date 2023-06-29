package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EscrowCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="EscrowCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ByBuyer"/>
 *     &lt;enumeration value="BySeller"/>
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "EscrowCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum EscrowCodeType
{

    @XmlEnumValue("ByBuyer")
    BY_BUYER("ByBuyer"),
    @XmlEnumValue("BySeller")
    BY_SELLER("BySeller"),
    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    EscrowCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static EscrowCodeType fromValue(String v)
    {
        for (EscrowCodeType c : EscrowCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
