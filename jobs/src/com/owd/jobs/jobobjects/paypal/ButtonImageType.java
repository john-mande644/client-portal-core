package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ButtonImageType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ButtonImageType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="REG"/>
 *     &lt;enumeration value="SML"/>
 *     &lt;enumeration value="CC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ButtonImageType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ButtonImageType
{

    REG,
    SML,
    CC;

    public String value()
    {
        return name();
    }

    public static ButtonImageType fromValue(String v)
    {
        return valueOf(v);
    }

}
