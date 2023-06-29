package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FeedbackRatingStarCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="FeedbackRatingStarCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="Yellow"/>
 *     &lt;enumeration value="Turquoise"/>
 *     &lt;enumeration value="Purple"/>
 *     &lt;enumeration value="Red"/>
 *     &lt;enumeration value="Green"/>
 *     &lt;enumeration value="YellowShooting"/>
 *     &lt;enumeration value="TurquoiseShooting"/>
 *     &lt;enumeration value="PurpleShooting"/>
 *     &lt;enumeration value="RedShooting"/>
 *     &lt;enumeration value="Blue"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "FeedbackRatingStarCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum FeedbackRatingStarCodeType
{

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("Yellow")
    YELLOW("Yellow"),
    @XmlEnumValue("Turquoise")
    TURQUOISE("Turquoise"),
    @XmlEnumValue("Purple")
    PURPLE("Purple"),
    @XmlEnumValue("Red")
    RED("Red"),
    @XmlEnumValue("Green")
    GREEN("Green"),
    @XmlEnumValue("YellowShooting")
    YELLOW_SHOOTING("YellowShooting"),
    @XmlEnumValue("TurquoiseShooting")
    TURQUOISE_SHOOTING("TurquoiseShooting"),
    @XmlEnumValue("PurpleShooting")
    PURPLE_SHOOTING("PurpleShooting"),
    @XmlEnumValue("RedShooting")
    RED_SHOOTING("RedShooting"),
    @XmlEnumValue("Blue")
    BLUE("Blue"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    FeedbackRatingStarCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static FeedbackRatingStarCodeType fromValue(String v)
    {
        for (FeedbackRatingStarCodeType c : FeedbackRatingStarCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
