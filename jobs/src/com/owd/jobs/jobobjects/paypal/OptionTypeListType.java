package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OptionTypeListType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="OptionTypeListType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="NoOptionType"/>
 *     &lt;enumeration value="FULL"/>
 *     &lt;enumeration value="EMI"/>
 *     &lt;enumeration value="VARIABLE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "OptionTypeListType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum OptionTypeListType
{

    @XmlEnumValue("NoOptionType")
    NO_OPTION_TYPE("NoOptionType"),
    FULL("FULL"),
    EMI("EMI"),
    VARIABLE("VARIABLE");
    private final String value;

    OptionTypeListType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static OptionTypeListType fromValue(String v)
    {
        for (OptionTypeListType c : OptionTypeListType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
