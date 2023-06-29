
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for crmCriteriaGroupingType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="crmCriteriaGroupingType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="All"/>
 *     &lt;enumeration value="Any"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "crmCriteriaGroupingType")
@XmlEnum
public enum CrmCriteriaGroupingType {

    @XmlEnumValue("All")
    ALL("All"),
    @XmlEnumValue("Any")
    ANY("Any"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    CrmCriteriaGroupingType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CrmCriteriaGroupingType fromValue(String v) {
        for (CrmCriteriaGroupingType c: CrmCriteriaGroupingType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
