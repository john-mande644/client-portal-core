package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentTransactionClassCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PaymentTransactionClassCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="All"/>
 *     &lt;enumeration value="Sent"/>
 *     &lt;enumeration value="Received"/>
 *     &lt;enumeration value="MassPay"/>
 *     &lt;enumeration value="MoneyRequest"/>
 *     &lt;enumeration value="FundsAdded"/>
 *     &lt;enumeration value="FundsWithdrawn"/>
 *     &lt;enumeration value="PayPalDebitCard"/>
 *     &lt;enumeration value="Referral"/>
 *     &lt;enumeration value="Fee"/>
 *     &lt;enumeration value="Subscription"/>
 *     &lt;enumeration value="Dividend"/>
 *     &lt;enumeration value="Billpay"/>
 *     &lt;enumeration value="Refund"/>
 *     &lt;enumeration value="CurrencyConversions"/>
 *     &lt;enumeration value="BalanceTransfer"/>
 *     &lt;enumeration value="Reversal"/>
 *     &lt;enumeration value="Shipping"/>
 *     &lt;enumeration value="BalanceAffecting"/>
 *     &lt;enumeration value="ECheck"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PaymentTransactionClassCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PaymentTransactionClassCodeType
{

    @XmlEnumValue("All")
    ALL("All"),
    @XmlEnumValue("Sent")
    SENT("Sent"),
    @XmlEnumValue("Received")
    RECEIVED("Received"),
    @XmlEnumValue("MassPay")
    MASS_PAY("MassPay"),
    @XmlEnumValue("MoneyRequest")
    MONEY_REQUEST("MoneyRequest"),
    @XmlEnumValue("FundsAdded")
    FUNDS_ADDED("FundsAdded"),
    @XmlEnumValue("FundsWithdrawn")
    FUNDS_WITHDRAWN("FundsWithdrawn"),
    @XmlEnumValue("PayPalDebitCard")
    PAY_PAL_DEBIT_CARD("PayPalDebitCard"),
    @XmlEnumValue("Referral")
    REFERRAL("Referral"),
    @XmlEnumValue("Fee")
    FEE("Fee"),
    @XmlEnumValue("Subscription")
    SUBSCRIPTION("Subscription"),
    @XmlEnumValue("Dividend")
    DIVIDEND("Dividend"),
    @XmlEnumValue("Billpay")
    BILLPAY("Billpay"),
    @XmlEnumValue("Refund")
    REFUND("Refund"),
    @XmlEnumValue("CurrencyConversions")
    CURRENCY_CONVERSIONS("CurrencyConversions"),
    @XmlEnumValue("BalanceTransfer")
    BALANCE_TRANSFER("BalanceTransfer"),
    @XmlEnumValue("Reversal")
    REVERSAL("Reversal"),
    @XmlEnumValue("Shipping")
    SHIPPING("Shipping"),
    @XmlEnumValue("BalanceAffecting")
    BALANCE_AFFECTING("BalanceAffecting"),
    @XmlEnumValue("ECheck")
    E_CHECK("ECheck");
    private final String value;

    PaymentTransactionClassCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PaymentTransactionClassCodeType fromValue(String v)
    {
        for (PaymentTransactionClassCodeType c : PaymentTransactionClassCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
