package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SubscribeTextType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="SubscribeTextType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="BUYNOW"/>
 *     &lt;enumeration value="SUBSCRIBE"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "SubscribeTextType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum SubscribeTextType
{

    BUYNOW,
    SUBSCRIBE;

    public String value()
    {
        return name();
    }

    public static SubscribeTextType fromValue(String v)
    {
        return valueOf(v);
    }

}
