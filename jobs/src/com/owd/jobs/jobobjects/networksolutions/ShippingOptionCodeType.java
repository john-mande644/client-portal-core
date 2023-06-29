
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ShippingOptionCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ShippingOptionCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Shippable"/>
 *     &lt;enumeration value="Free"/>
 *     &lt;enumeration value="NonShippable"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ShippingOptionCodeType")
@XmlEnum
public enum ShippingOptionCodeType {

    @XmlEnumValue("Shippable")
    SHIPPABLE("Shippable"),
    @XmlEnumValue("Free")
    FREE("Free"),
    @XmlEnumValue("NonShippable")
    NON_SHIPPABLE("NonShippable"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    ShippingOptionCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ShippingOptionCodeType fromValue(String v) {
        for (ShippingOptionCodeType c: ShippingOptionCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
