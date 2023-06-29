
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sayAs.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sayAs">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Default"/>
 *     &lt;enumeration value="Words"/>
 *     &lt;enumeration value="Acronym"/>
 *     &lt;enumeration value="Address"/>
 *     &lt;enumeration value="Cardinal"/>
 *     &lt;enumeration value="Currency"/>
 *     &lt;enumeration value="Date"/>
 *     &lt;enumeration value="Decimal"/>
 *     &lt;enumeration value="Digits"/>
 *     &lt;enumeration value="Duration"/>
 *     &lt;enumeration value="Fraction"/>
 *     &lt;enumeration value="Letters"/>
 *     &lt;enumeration value="Measure"/>
 *     &lt;enumeration value="Name"/>
 *     &lt;enumeration value="Net"/>
 *     &lt;enumeration value="Telephone"/>
 *     &lt;enumeration value="Ordinal"/>
 *     &lt;enumeration value="Spell"/>
 *     &lt;enumeration value="Time"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sayAs")
@XmlEnum
public enum SayAs {

    @XmlEnumValue("Default")
    DEFAULT("Default"),
    @XmlEnumValue("Words")
    WORDS("Words"),
    @XmlEnumValue("Acronym")
    ACRONYM("Acronym"),
    @XmlEnumValue("Address")
    ADDRESS("Address"),
    @XmlEnumValue("Cardinal")
    CARDINAL("Cardinal"),
    @XmlEnumValue("Currency")
    CURRENCY("Currency"),
    @XmlEnumValue("Date")
    DATE("Date"),
    @XmlEnumValue("Decimal")
    DECIMAL("Decimal"),
    @XmlEnumValue("Digits")
    DIGITS("Digits"),
    @XmlEnumValue("Duration")
    DURATION("Duration"),
    @XmlEnumValue("Fraction")
    FRACTION("Fraction"),
    @XmlEnumValue("Letters")
    LETTERS("Letters"),
    @XmlEnumValue("Measure")
    MEASURE("Measure"),
    @XmlEnumValue("Name")
    NAME("Name"),
    @XmlEnumValue("Net")
    NET("Net"),
    @XmlEnumValue("Telephone")
    TELEPHONE("Telephone"),
    @XmlEnumValue("Ordinal")
    ORDINAL("Ordinal"),
    @XmlEnumValue("Spell")
    SPELL("Spell"),
    @XmlEnumValue("Time")
    TIME("Time");
    private final String value;

    SayAs(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SayAs fromValue(String v) {
        for (SayAs c: SayAs.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
