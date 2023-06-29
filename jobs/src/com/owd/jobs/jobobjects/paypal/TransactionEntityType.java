package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionEntityType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="TransactionEntityType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Auth"/>
 *     &lt;enumeration value="Reauth"/>
 *     &lt;enumeration value="Order"/>
 *     &lt;enumeration value="Payment"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "TransactionEntityType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum TransactionEntityType
{

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Auth")
    AUTH("Auth"),
    @XmlEnumValue("Reauth")
    REAUTH("Reauth"),
    @XmlEnumValue("Order")
    ORDER("Order"),
    @XmlEnumValue("Payment")
    PAYMENT("Payment");
    private final String value;

    TransactionEntityType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static TransactionEntityType fromValue(String v)
    {
        for (TransactionEntityType c : TransactionEntityType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
