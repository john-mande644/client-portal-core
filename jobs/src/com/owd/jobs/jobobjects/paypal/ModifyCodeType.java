package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModifyCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ModifyCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Dropped"/>
 *     &lt;enumeration value="Modify"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ModifyCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ModifyCodeType
{

    @XmlEnumValue("Dropped")
    DROPPED("Dropped"),
    @XmlEnumValue("Modify")
    MODIFY("Modify"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    ModifyCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static ModifyCodeType fromValue(String v)
    {
        for (ModifyCodeType c : ModifyCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
