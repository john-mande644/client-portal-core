
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContextControlSetting.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContextControlSetting">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="clear"/>
 *     &lt;enumeration value="preserve"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ContextControlSetting")
@XmlEnum
public enum ContextControlSetting {


    /**
     * Clear the context for this operation.
     * 
     */
    @XmlEnumValue("clear")
    CLEAR("clear"),

    /**
     * Preserve the context for this operation.
     * 
     */
    @XmlEnumValue("preserve")
    PRESERVE("preserve");
    private final String value;

    ContextControlSetting(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContextControlSetting fromValue(String v) {
        for (ContextControlSetting c: ContextControlSetting.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
