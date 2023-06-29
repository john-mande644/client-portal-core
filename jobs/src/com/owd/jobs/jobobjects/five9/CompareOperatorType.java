
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for compareOperatorType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="compareOperatorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Contains"/>
 *     &lt;enumeration value="DontContains"/>
 *     &lt;enumeration value="IsNull"/>
 *     &lt;enumeration value="IsNotNull"/>
 *     &lt;enumeration value="EndsWith"/>
 *     &lt;enumeration value="Equals"/>
 *     &lt;enumeration value="NotEqual"/>
 *     &lt;enumeration value="Greater"/>
 *     &lt;enumeration value="GreaterOrEqual"/>
 *     &lt;enumeration value="Less"/>
 *     &lt;enumeration value="LessOrEqual"/>
 *     &lt;enumeration value="Like"/>
 *     &lt;enumeration value="StartsWith"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "compareOperatorType")
@XmlEnum
public enum CompareOperatorType {

    @XmlEnumValue("Contains")
    CONTAINS("Contains"),
    @XmlEnumValue("DontContains")
    DONT_CONTAINS("DontContains"),
    @XmlEnumValue("IsNull")
    IS_NULL("IsNull"),
    @XmlEnumValue("IsNotNull")
    IS_NOT_NULL("IsNotNull"),
    @XmlEnumValue("EndsWith")
    ENDS_WITH("EndsWith"),
    @XmlEnumValue("Equals")
    EQUALS("Equals"),
    @XmlEnumValue("NotEqual")
    NOT_EQUAL("NotEqual"),
    @XmlEnumValue("Greater")
    GREATER("Greater"),
    @XmlEnumValue("GreaterOrEqual")
    GREATER_OR_EQUAL("GreaterOrEqual"),
    @XmlEnumValue("Less")
    LESS("Less"),
    @XmlEnumValue("LessOrEqual")
    LESS_OR_EQUAL("LessOrEqual"),
    @XmlEnumValue("Like")
    LIKE("Like"),
    @XmlEnumValue("StartsWith")
    STARTS_WITH("StartsWith");
    private final String value;

    CompareOperatorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CompareOperatorType fromValue(String v) {
        for (CompareOperatorType c: CompareOperatorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
