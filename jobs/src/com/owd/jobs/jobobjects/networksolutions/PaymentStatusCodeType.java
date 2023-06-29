
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentStatusCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentStatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Authorized"/>
 *     &lt;enumeration value="Chargeable"/>
 *     &lt;enumeration value="Chargeback"/>
 *     &lt;enumeration value="Charged"/>
 *     &lt;enumeration value="Failure"/>
 *     &lt;enumeration value="Processing"/>
 *     &lt;enumeration value="Purged"/>
 *     &lt;enumeration value="Refunded"/>
 *     &lt;enumeration value="Unavailable"/>
 *     &lt;enumeration value="Voided"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PaymentStatusCodeType")
@XmlEnum
public enum PaymentStatusCodeType {

    @XmlEnumValue("Authorized")
    AUTHORIZED("Authorized"),
    @XmlEnumValue("Chargeable")
    CHARGEABLE("Chargeable"),
    @XmlEnumValue("Chargeback")
    CHARGEBACK("Chargeback"),
    @XmlEnumValue("Charged")
    CHARGED("Charged"),
    @XmlEnumValue("Failure")
    FAILURE("Failure"),
    @XmlEnumValue("Processing")
    PROCESSING("Processing"),
    @XmlEnumValue("Purged")
    PURGED("Purged"),
    @XmlEnumValue("Refunded")
    REFUNDED("Refunded"),
    @XmlEnumValue("Unavailable")
    UNAVAILABLE("Unavailable"),
    @XmlEnumValue("Voided")
    VOIDED("Voided"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    PaymentStatusCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PaymentStatusCodeType fromValue(String v) {
        for (PaymentStatusCodeType c: PaymentStatusCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
