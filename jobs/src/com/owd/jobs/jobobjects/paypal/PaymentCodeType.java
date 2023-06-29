package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PaymentCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="echeck"/>
 *     &lt;enumeration value="instant"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PaymentCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PaymentCodeType
{

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("echeck")
    ECHECK("echeck"),
    @XmlEnumValue("instant")
    INSTANT("instant");
    private final String value;

    PaymentCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PaymentCodeType fromValue(String v)
    {
        for (PaymentCodeType c : PaymentCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
