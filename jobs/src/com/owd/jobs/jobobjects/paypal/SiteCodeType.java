package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SiteCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="SiteCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="US"/>
 *     &lt;enumeration value="Canada"/>
 *     &lt;enumeration value="UK"/>
 *     &lt;enumeration value="Australia"/>
 *     &lt;enumeration value="Austria"/>
 *     &lt;enumeration value="Belgium_French"/>
 *     &lt;enumeration value="France"/>
 *     &lt;enumeration value="Germany"/>
 *     &lt;enumeration value="Italy"/>
 *     &lt;enumeration value="Belgium_Dutch"/>
 *     &lt;enumeration value="Netherlands"/>
 *     &lt;enumeration value="Spain"/>
 *     &lt;enumeration value="Switzerland"/>
 *     &lt;enumeration value="Taiwan"/>
 *     &lt;enumeration value="eBayMotors"/>
 *     &lt;enumeration value="HongKong"/>
 *     &lt;enumeration value="Singapore"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "SiteCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum SiteCodeType
{

    US("US"),
    @XmlEnumValue("Canada")
    CANADA("Canada"),
    UK("UK"),
    @XmlEnumValue("Australia")
    AUSTRALIA("Australia"),
    @XmlEnumValue("Austria")
    AUSTRIA("Austria"),
    @XmlEnumValue("Belgium_French")
    BELGIUM_FRENCH("Belgium_French"),
    @XmlEnumValue("France")
    FRANCE("France"),
    @XmlEnumValue("Germany")
    GERMANY("Germany"),
    @XmlEnumValue("Italy")
    ITALY("Italy"),
    @XmlEnumValue("Belgium_Dutch")
    BELGIUM_DUTCH("Belgium_Dutch"),
    @XmlEnumValue("Netherlands")
    NETHERLANDS("Netherlands"),
    @XmlEnumValue("Spain")
    SPAIN("Spain"),
    @XmlEnumValue("Switzerland")
    SWITZERLAND("Switzerland"),
    @XmlEnumValue("Taiwan")
    TAIWAN("Taiwan"),
    @XmlEnumValue("eBayMotors")
    E_BAY_MOTORS("eBayMotors"),
    @XmlEnumValue("HongKong")
    HONG_KONG("HongKong"),
    @XmlEnumValue("Singapore")
    SINGAPORE("Singapore"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    SiteCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static SiteCodeType fromValue(String v)
    {
        for (SiteCodeType c : SiteCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
