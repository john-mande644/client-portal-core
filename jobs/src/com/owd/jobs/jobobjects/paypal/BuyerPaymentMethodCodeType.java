package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BuyerPaymentMethodCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="BuyerPaymentMethodCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="MOCC"/>
 *     &lt;enumeration value="AmEx"/>
 *     &lt;enumeration value="PaymentSeeDescription"/>
 *     &lt;enumeration value="CCAccepted"/>
 *     &lt;enumeration value="PersonalCheck"/>
 *     &lt;enumeration value="COD"/>
 *     &lt;enumeration value="VisaMC"/>
 *     &lt;enumeration value="Other"/>
 *     &lt;enumeration value="PayPal"/>
 *     &lt;enumeration value="Discover"/>
 *     &lt;enumeration value="CashOnPickup"/>
 *     &lt;enumeration value="MoneyXferAccepted"/>
 *     &lt;enumeration value="MoneyXferAcceptedinCheckout"/>
 *     &lt;enumeration value="OtherOnlinePayments"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "BuyerPaymentMethodCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum BuyerPaymentMethodCodeType
{

    @XmlEnumValue("None")
    NONE("None"),
    MOCC("MOCC"),
    @XmlEnumValue("AmEx")
    AM_EX("AmEx"),
    @XmlEnumValue("PaymentSeeDescription")
    PAYMENT_SEE_DESCRIPTION("PaymentSeeDescription"),
    @XmlEnumValue("CCAccepted")
    CC_ACCEPTED("CCAccepted"),
    @XmlEnumValue("PersonalCheck")
    PERSONAL_CHECK("PersonalCheck"),
    COD("COD"),
    @XmlEnumValue("VisaMC")
    VISA_MC("VisaMC"),
    @XmlEnumValue("Other")
    OTHER("Other"),
    @XmlEnumValue("PayPal")
    PAY_PAL("PayPal"),
    @XmlEnumValue("Discover")
    DISCOVER("Discover"),
    @XmlEnumValue("CashOnPickup")
    CASH_ON_PICKUP("CashOnPickup"),
    @XmlEnumValue("MoneyXferAccepted")
    MONEY_XFER_ACCEPTED("MoneyXferAccepted"),
    @XmlEnumValue("MoneyXferAcceptedinCheckout")
    MONEY_XFER_ACCEPTEDIN_CHECKOUT("MoneyXferAcceptedinCheckout"),
    @XmlEnumValue("OtherOnlinePayments")
    OTHER_ONLINE_PAYMENTS("OtherOnlinePayments"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    BuyerPaymentMethodCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static BuyerPaymentMethodCodeType fromValue(String v)
    {
        for (BuyerPaymentMethodCodeType c : BuyerPaymentMethodCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
