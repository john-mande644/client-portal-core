
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DisplayCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DisplayCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="CheckBox"/>
 *     &lt;enumeration value="CheckBoxList"/>
 *     &lt;enumeration value="DropDown"/>
 *     &lt;enumeration value="Radio"/>
 *     &lt;enumeration value="Text"/>
 *     &lt;enumeration value="TextList"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DisplayCodeType")
@XmlEnum
public enum DisplayCodeType {

    @XmlEnumValue("CheckBox")
    CHECK_BOX("CheckBox"),
    @XmlEnumValue("CheckBoxList")
    CHECK_BOX_LIST("CheckBoxList"),
    @XmlEnumValue("DropDown")
    DROP_DOWN("DropDown"),
    @XmlEnumValue("Radio")
    RADIO("Radio"),
    @XmlEnumValue("Text")
    TEXT("Text"),
    @XmlEnumValue("TextList")
    TEXT_LIST("TextList"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    DisplayCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DisplayCodeType fromValue(String v) {
        for (DisplayCodeType c: DisplayCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
