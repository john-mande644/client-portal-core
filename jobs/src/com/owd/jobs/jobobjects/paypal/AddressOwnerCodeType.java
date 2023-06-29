package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddressOwnerCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="AddressOwnerCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="PayPal"/>
 *     &lt;enumeration value="eBay"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "AddressOwnerCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum AddressOwnerCodeType
{

    @XmlEnumValue("PayPal")
    PAY_PAL("PayPal"),
    @XmlEnumValue("eBay")
    E_BAY("eBay"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    AddressOwnerCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static AddressOwnerCodeType fromValue(String v)
    {
        for (AddressOwnerCodeType c : AddressOwnerCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
