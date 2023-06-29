
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CarrierCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CarrierCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Customer"/>
 *     &lt;enumeration value="FedEx"/>
 *     &lt;enumeration value="NotRequired"/>
 *     &lt;enumeration value="UPS"/>
 *     &lt;enumeration value="USPS"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CarrierCodeType")
@XmlEnum
public enum CarrierCodeType {

    @XmlEnumValue("Customer")
    CUSTOMER("Customer"),
    @XmlEnumValue("FedEx")
    FED_EX("FedEx"),
    @XmlEnumValue("NotRequired")
    NOT_REQUIRED("NotRequired"),
    UPS("UPS"),
    USPS("USPS"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    CarrierCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CarrierCodeType fromValue(String v) {
        for (CarrierCodeType c: CarrierCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
