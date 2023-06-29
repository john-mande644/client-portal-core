
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AttributeDisplayCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AttributeDisplayCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="CheckBox"/>
 *     &lt;enumeration value="CheckBoxList"/>
 *     &lt;enumeration value="Text"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AttributeDisplayCodeType")
@XmlEnum
public enum AttributeDisplayCodeType {

    @XmlEnumValue("CheckBox")
    CHECK_BOX("CheckBox"),
    @XmlEnumValue("CheckBoxList")
    CHECK_BOX_LIST("CheckBoxList"),
    @XmlEnumValue("Text")
    TEXT("Text"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    AttributeDisplayCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AttributeDisplayCodeType fromValue(String v) {
        for (AttributeDisplayCodeType c: AttributeDisplayCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
