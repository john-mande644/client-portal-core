package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentTransactionStatusCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PaymentTransactionStatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Pending"/>
 *     &lt;enumeration value="Processing"/>
 *     &lt;enumeration value="Success"/>
 *     &lt;enumeration value="Denied"/>
 *     &lt;enumeration value="Reversed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PaymentTransactionStatusCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PaymentTransactionStatusCodeType
{

    @XmlEnumValue("Pending")
    PENDING("Pending"),
    @XmlEnumValue("Processing")
    PROCESSING("Processing"),
    @XmlEnumValue("Success")
    SUCCESS("Success"),
    @XmlEnumValue("Denied")
    DENIED("Denied"),
    @XmlEnumValue("Reversed")
    REVERSED("Reversed");
    private final String value;

    PaymentTransactionStatusCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PaymentTransactionStatusCodeType fromValue(String v)
    {
        for (PaymentTransactionStatusCodeType c : PaymentTransactionStatusCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
