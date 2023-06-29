
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProductCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Regular"/>
 *     &lt;enumeration value="GiftCertificate"/>
 *     &lt;enumeration value="Electronic"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProductCodeType")
@XmlEnum
public enum ProductCodeType {

    @XmlEnumValue("Regular")
    REGULAR("Regular"),
    @XmlEnumValue("GiftCertificate")
    GIFT_CERTIFICATE("GiftCertificate"),
    @XmlEnumValue("Electronic")
    ELECTRONIC("Electronic"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    ProductCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProductCodeType fromValue(String v) {
        for (ProductCodeType c: ProductCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
