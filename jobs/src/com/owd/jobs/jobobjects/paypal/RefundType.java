package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RefundType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="RefundType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Other"/>
 *     &lt;enumeration value="Full"/>
 *     &lt;enumeration value="Partial"/>
 *     &lt;enumeration value="ExternalDispute"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "RefundType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum RefundType
{

    @XmlEnumValue("Other")
    OTHER("Other"),
    @XmlEnumValue("Full")
    FULL("Full"),
    @XmlEnumValue("Partial")
    PARTIAL("Partial"),
    @XmlEnumValue("ExternalDispute")
    EXTERNAL_DISPUTE("ExternalDispute");
    private final String value;

    RefundType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static RefundType fromValue(String v)
    {
        for (RefundType c : RefundType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
