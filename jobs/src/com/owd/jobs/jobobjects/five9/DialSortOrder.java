
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dialSortOrder.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="dialSortOrder">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="LIFO"/>
 *     &lt;enumeration value="FIFO"/>
 *     &lt;enumeration value="ContactFields"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "dialSortOrder")
@XmlEnum
public enum DialSortOrder {

    LIFO("LIFO"),
    FIFO("FIFO"),
    @XmlEnumValue("ContactFields")
    CONTACT_FIELDS("ContactFields");
    private final String value;

    DialSortOrder(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DialSortOrder fromValue(String v) {
        for (DialSortOrder c: DialSortOrder.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
