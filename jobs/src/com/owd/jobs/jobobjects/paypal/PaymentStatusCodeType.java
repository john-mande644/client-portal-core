package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentStatusCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PaymentStatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Completed"/>
 *     &lt;enumeration value="Failed"/>
 *     &lt;enumeration value="Pending"/>
 *     &lt;enumeration value="Denied"/>
 *     &lt;enumeration value="Refunded"/>
 *     &lt;enumeration value="Reversed"/>
 *     &lt;enumeration value="Canceled-Reversal"/>
 *     &lt;enumeration value="Processed"/>
 *     &lt;enumeration value="Partially-Refunded"/>
 *     &lt;enumeration value="Voided"/>
 *     &lt;enumeration value="Expired"/>
 *     &lt;enumeration value="In-Progress"/>
 *     &lt;enumeration value="Created"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PaymentStatusCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PaymentStatusCodeType
{

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Completed")
    COMPLETED("Completed"),
    @XmlEnumValue("Failed")
    FAILED("Failed"),
    @XmlEnumValue("Pending")
    PENDING("Pending"),
    @XmlEnumValue("Denied")
    DENIED("Denied"),
    @XmlEnumValue("Refunded")
    REFUNDED("Refunded"),
    @XmlEnumValue("Reversed")
    REVERSED("Reversed"),
    @XmlEnumValue("Canceled-Reversal")
    CANCELED_REVERSAL("Canceled-Reversal"),
    @XmlEnumValue("Processed")
    PROCESSED("Processed"),
    @XmlEnumValue("Partially-Refunded")
    PARTIALLY_REFUNDED("Partially-Refunded"),
    @XmlEnumValue("Voided")
    VOIDED("Voided"),
    @XmlEnumValue("Expired")
    EXPIRED("Expired"),
    @XmlEnumValue("In-Progress")
    IN_PROGRESS("In-Progress"),
    @XmlEnumValue("Created")
    CREATED("Created");
    private final String value;

    PaymentStatusCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PaymentStatusCodeType fromValue(String v)
    {
        for (PaymentStatusCodeType c : PaymentStatusCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
