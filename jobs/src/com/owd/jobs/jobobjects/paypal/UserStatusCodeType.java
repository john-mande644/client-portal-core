package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserStatusCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="UserStatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="Suspended"/>
 *     &lt;enumeration value="Confirmed"/>
 *     &lt;enumeration value="Unconfirmed"/>
 *     &lt;enumeration value="Ghost"/>
 *     &lt;enumeration value="InMaintenance"/>
 *     &lt;enumeration value="Deleted"/>
 *     &lt;enumeration value="CreditCardVerify"/>
 *     &lt;enumeration value="AccountOnHold"/>
 *     &lt;enumeration value="Merged"/>
 *     &lt;enumeration value="RegistrationCodeMailOut"/>
 *     &lt;enumeration value="TermPending"/>
 *     &lt;enumeration value="UnconfirmedHalfOptIn"/>
 *     &lt;enumeration value="CreditCardVerifyHalfOptIn"/>
 *     &lt;enumeration value="UnconfirmedPassport"/>
 *     &lt;enumeration value="CreditCardVerifyPassport"/>
 *     &lt;enumeration value="UnconfirmedExpress"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "UserStatusCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum UserStatusCodeType
{

    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("Suspended")
    SUSPENDED("Suspended"),
    @XmlEnumValue("Confirmed")
    CONFIRMED("Confirmed"),
    @XmlEnumValue("Unconfirmed")
    UNCONFIRMED("Unconfirmed"),
    @XmlEnumValue("Ghost")
    GHOST("Ghost"),
    @XmlEnumValue("InMaintenance")
    IN_MAINTENANCE("InMaintenance"),
    @XmlEnumValue("Deleted")
    DELETED("Deleted"),
    @XmlEnumValue("CreditCardVerify")
    CREDIT_CARD_VERIFY("CreditCardVerify"),
    @XmlEnumValue("AccountOnHold")
    ACCOUNT_ON_HOLD("AccountOnHold"),
    @XmlEnumValue("Merged")
    MERGED("Merged"),
    @XmlEnumValue("RegistrationCodeMailOut")
    REGISTRATION_CODE_MAIL_OUT("RegistrationCodeMailOut"),
    @XmlEnumValue("TermPending")
    TERM_PENDING("TermPending"),
    @XmlEnumValue("UnconfirmedHalfOptIn")
    UNCONFIRMED_HALF_OPT_IN("UnconfirmedHalfOptIn"),
    @XmlEnumValue("CreditCardVerifyHalfOptIn")
    CREDIT_CARD_VERIFY_HALF_OPT_IN("CreditCardVerifyHalfOptIn"),
    @XmlEnumValue("UnconfirmedPassport")
    UNCONFIRMED_PASSPORT("UnconfirmedPassport"),
    @XmlEnumValue("CreditCardVerifyPassport")
    CREDIT_CARD_VERIFY_PASSPORT("CreditCardVerifyPassport"),
    @XmlEnumValue("UnconfirmedExpress")
    UNCONFIRMED_EXPRESS("UnconfirmedExpress"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    UserStatusCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static UserStatusCodeType fromValue(String v)
    {
        for (UserStatusCodeType c : UserStatusCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
