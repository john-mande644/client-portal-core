package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DetailLevelCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="DetailLevelCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="ReturnAll"/>
 *     &lt;enumeration value="ItemReturnDescription"/>
 *     &lt;enumeration value="ItemReturnAttributes"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "DetailLevelCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum DetailLevelCodeType
{

    @XmlEnumValue("ReturnAll")
    RETURN_ALL("ReturnAll"),
    @XmlEnumValue("ItemReturnDescription")
    ITEM_RETURN_DESCRIPTION("ItemReturnDescription"),
    @XmlEnumValue("ItemReturnAttributes")
    ITEM_RETURN_ATTRIBUTES("ItemReturnAttributes");
    private final String value;

    DetailLevelCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static DetailLevelCodeType fromValue(String v)
    {
        for (DetailLevelCodeType c : DetailLevelCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
