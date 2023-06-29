
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperatorCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OperatorCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Between"/>
 *     &lt;enumeration value="Equal"/>
 *     &lt;enumeration value="GreaterEqual"/>
 *     &lt;enumeration value="GreaterThan"/>
 *     &lt;enumeration value="In"/>
 *     &lt;enumeration value="IsNull"/>
 *     &lt;enumeration value="LessEqual"/>
 *     &lt;enumeration value="LessThan"/>
 *     &lt;enumeration value="Like"/>
 *     &lt;enumeration value="NotEqual"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperatorCodeType")
@XmlEnum
public enum OperatorCodeType {

    @XmlEnumValue("Between")
    BETWEEN("Between"),
    @XmlEnumValue("Equal")
    EQUAL("Equal"),
    @XmlEnumValue("GreaterEqual")
    GREATER_EQUAL("GreaterEqual"),
    @XmlEnumValue("GreaterThan")
    GREATER_THAN("GreaterThan"),
    @XmlEnumValue("In")
    IN("In"),
    @XmlEnumValue("IsNull")
    IS_NULL("IsNull"),
    @XmlEnumValue("LessEqual")
    LESS_EQUAL("LessEqual"),
    @XmlEnumValue("LessThan")
    LESS_THAN("LessThan"),
    @XmlEnumValue("Like")
    LIKE("Like"),
    @XmlEnumValue("NotEqual")
    NOT_EQUAL("NotEqual"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    OperatorCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OperatorCodeType fromValue(String v) {
        for (OperatorCodeType c: OperatorCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
