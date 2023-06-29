package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MerchantPullPaymentCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="MerchantPullPaymentCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Any"/>
 *     &lt;enumeration value="InstantOnly"/>
 *     &lt;enumeration value="EcheckOnly"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "MerchantPullPaymentCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum MerchantPullPaymentCodeType
{

    @XmlEnumValue("Any")
    ANY("Any"),
    @XmlEnumValue("InstantOnly")
    INSTANT_ONLY("InstantOnly"),
    @XmlEnumValue("EcheckOnly")
    ECHECK_ONLY("EcheckOnly");
    private final String value;

    MerchantPullPaymentCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static MerchantPullPaymentCodeType fromValue(String v)
    {
        for (MerchantPullPaymentCodeType c : MerchantPullPaymentCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
