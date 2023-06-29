package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MerchandizingPrefCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="MerchandizingPrefCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="OptIn"/>
 *     &lt;enumeration value="OptOut"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "MerchandizingPrefCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum MerchandizingPrefCodeType
{

    @XmlEnumValue("OptIn")
    OPT_IN("OptIn"),
    @XmlEnumValue("OptOut")
    OPT_OUT("OptOut"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    MerchandizingPrefCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static MerchandizingPrefCodeType fromValue(String v)
    {
        for (MerchandizingPrefCodeType c : MerchandizingPrefCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
