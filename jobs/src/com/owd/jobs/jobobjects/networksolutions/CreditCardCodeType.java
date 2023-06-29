
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditCardCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CreditCardCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="AmericanExpress"/>
 *     &lt;enumeration value="Diners"/>
 *     &lt;enumeration value="Discover"/>
 *     &lt;enumeration value="JCB"/>
 *     &lt;enumeration value="MasterCard"/>
 *     &lt;enumeration value="Visa"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CreditCardCodeType")
@XmlEnum
public enum CreditCardCodeType {

    @XmlEnumValue("AmericanExpress")
    AMERICAN_EXPRESS("AmericanExpress"),
    @XmlEnumValue("Diners")
    DINERS("Diners"),
    @XmlEnumValue("Discover")
    DISCOVER("Discover"),
    JCB("JCB"),
    @XmlEnumValue("MasterCard")
    MASTER_CARD("MasterCard"),
    @XmlEnumValue("Visa")
    VISA("Visa"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    CreditCardCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CreditCardCodeType fromValue(String v) {
        for (CreditCardCodeType c: CreditCardCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
