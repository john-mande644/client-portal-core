
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DiscountTypeCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DiscountTypeCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Order"/>
 *     &lt;enumeration value="Product"/>
 *     &lt;enumeration value="Quantity"/>
 *     &lt;enumeration value="Shipping"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DiscountTypeCodeType")
@XmlEnum
public enum DiscountTypeCodeType {

    @XmlEnumValue("Order")
    ORDER("Order"),
    @XmlEnumValue("Product")
    PRODUCT("Product"),
    @XmlEnumValue("Quantity")
    QUANTITY("Quantity"),
    @XmlEnumValue("Shipping")
    SHIPPING("Shipping"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    DiscountTypeCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DiscountTypeCodeType fromValue(String v) {
        for (DiscountTypeCodeType c: DiscountTypeCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
