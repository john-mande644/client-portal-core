package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MobileRecipientCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="MobileRecipientCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="PhoneNumber"/>
 *     &lt;enumeration value="EmailAddress"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "MobileRecipientCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum MobileRecipientCodeType
{

    @XmlEnumValue("PhoneNumber")
    PHONE_NUMBER("PhoneNumber"),
    @XmlEnumValue("EmailAddress")
    EMAIL_ADDRESS("EmailAddress");
    private final String value;

    MobileRecipientCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static MobileRecipientCodeType fromValue(String v)
    {
        for (MobileRecipientCodeType c : MobileRecipientCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
