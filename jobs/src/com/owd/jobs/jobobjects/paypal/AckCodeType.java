package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AckCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="AckCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Success"/>
 *     &lt;enumeration value="Failure"/>
 *     &lt;enumeration value="Warning"/>
 *     &lt;enumeration value="SuccessWithWarning"/>
 *     &lt;enumeration value="FailureWithWarning"/>
 *     &lt;enumeration value="PartialSuccess"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "AckCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum AckCodeType
{

    @XmlEnumValue("Success")
    SUCCESS("Success"),
    @XmlEnumValue("Failure")
    FAILURE("Failure"),
    @XmlEnumValue("Warning")
    WARNING("Warning"),
    @XmlEnumValue("SuccessWithWarning")
    SUCCESS_WITH_WARNING("SuccessWithWarning"),
    @XmlEnumValue("FailureWithWarning")
    FAILURE_WITH_WARNING("FailureWithWarning"),
    @XmlEnumValue("PartialSuccess")
    PARTIAL_SUCCESS("PartialSuccess"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    AckCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static AckCodeType fromValue(String v)
    {
        for (AckCodeType c : AckCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
