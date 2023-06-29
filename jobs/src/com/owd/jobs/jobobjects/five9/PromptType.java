
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for promptType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="promptType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="TTSGenerated"/>
 *     &lt;enumeration value="PreRecorded"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "promptType")
@XmlEnum
public enum PromptType {

    @XmlEnumValue("TTSGenerated")
    TTS_GENERATED("TTSGenerated"),
    @XmlEnumValue("PreRecorded")
    PRE_RECORDED("PreRecorded");
    private final String value;

    PromptType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PromptType fromValue(String v) {
        for (PromptType c: PromptType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
