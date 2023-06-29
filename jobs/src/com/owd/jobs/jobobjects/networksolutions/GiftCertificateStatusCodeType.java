
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GiftCertificateStatusCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="GiftCertificateStatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Active"/>
 *     &lt;enumeration value="Discharged"/>
 *     &lt;enumeration value="Expired"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "GiftCertificateStatusCodeType")
@XmlEnum
public enum GiftCertificateStatusCodeType {

    @XmlEnumValue("Active")
    ACTIVE("Active"),
    @XmlEnumValue("Discharged")
    DISCHARGED("Discharged"),
    @XmlEnumValue("Expired")
    EXPIRED("Expired"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    GiftCertificateStatusCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static GiftCertificateStatusCodeType fromValue(String v) {
        for (GiftCertificateStatusCodeType c: GiftCertificateStatusCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
