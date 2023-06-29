package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SolutionTypeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="SolutionTypeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Mark"/>
 *     &lt;enumeration value="Sole"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "SolutionTypeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum SolutionTypeType
{

    @XmlEnumValue("Mark")
    MARK("Mark"),
    @XmlEnumValue("Sole")
    SOLE("Sole");
    private final String value;

    SolutionTypeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static SolutionTypeType fromValue(String v)
    {
        for (SolutionTypeType c : SolutionTypeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
