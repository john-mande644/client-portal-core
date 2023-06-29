package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CompleteCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="CompleteCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NotComplete"/>
 *     &lt;enumeration value="Complete"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "CompleteCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum CompleteCodeType
{

    @XmlEnumValue("NotComplete")
    NOT_COMPLETE("NotComplete"),
    @XmlEnumValue("Complete")
    COMPLETE("Complete");
    private final String value;

    CompleteCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static CompleteCodeType fromValue(String v)
    {
        for (CompleteCodeType c : CompleteCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
