
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaymentMethodCodeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="PaymentMethodCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="AuthorizeNet"/>
 *     &lt;enumeration value="Cybersource"/>
 *     &lt;enumeration value="Google"/>
 *     &lt;enumeration value="LinkPoint"/>
 *     &lt;enumeration value="NetworkSolutions"/>
 *     &lt;enumeration value="NetworkMerchantsInc"/>
 *     &lt;enumeration value="NotRequired"/>
 *     &lt;enumeration value="OfflineAccount"/>
 *     &lt;enumeration value="OfflineCheck"/>
 *     &lt;enumeration value="OfflineCashOnDelivery"/>
 *     &lt;enumeration value="OfflineCreditCard"/>
 *     &lt;enumeration value="OfflineMoneyOrder"/>
 *     &lt;enumeration value="OfflineStorePayment"/>
 *     &lt;enumeration value="PayPalDirect"/>
 *     &lt;enumeration value="PayPalExpress"/>
 *     &lt;enumeration value="PayPalPayFlowPro"/>
 *     &lt;enumeration value="PaymentTech"/>
 *     &lt;enumeration value="PlugNPay"/>
 *     &lt;enumeration value="QBMS"/>
 *     &lt;enumeration value="USAePay"/>
 *     &lt;enumeration value="PayGov"/>
 *     &lt;enumeration value="eCheckIt"/>
 *     &lt;enumeration value="Custom"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "PaymentMethodCodeType")
@XmlEnum
public enum PaymentMethodCodeType {

    @XmlEnumValue("AuthorizeNet")
    AUTHORIZE_NET("AuthorizeNet"),
    @XmlEnumValue("Cybersource")
    CYBERSOURCE("Cybersource"),
    @XmlEnumValue("Google")
    GOOGLE("Google"),
    @XmlEnumValue("LinkPoint")
    LINK_POINT("LinkPoint"),
    @XmlEnumValue("NetworkSolutions")
    NETWORK_SOLUTIONS("NetworkSolutions"),
    @XmlEnumValue("NetworkMerchantsInc")
    NETWORK_MERCHANTS_INC("NetworkMerchantsInc"),
    @XmlEnumValue("NotRequired")
    NOT_REQUIRED("NotRequired"),
    @XmlEnumValue("OfflineAccount")
    OFFLINE_ACCOUNT("OfflineAccount"),
    @XmlEnumValue("OfflineCheck")
    OFFLINE_CHECK("OfflineCheck"),
    @XmlEnumValue("OfflineCashOnDelivery")
    OFFLINE_CASH_ON_DELIVERY("OfflineCashOnDelivery"),
    @XmlEnumValue("OfflineCreditCard")
    OFFLINE_CREDIT_CARD("OfflineCreditCard"),
    @XmlEnumValue("OfflineMoneyOrder")
    OFFLINE_MONEY_ORDER("OfflineMoneyOrder"),
    @XmlEnumValue("OfflineStorePayment")
    OFFLINE_STORE_PAYMENT("OfflineStorePayment"),
    @XmlEnumValue("PayPalDirect")
    PAY_PAL_DIRECT("PayPalDirect"),
    @XmlEnumValue("PayPalExpress")
    PAY_PAL_EXPRESS("PayPalExpress"),
    @XmlEnumValue("PayPalPayFlowPro")
    PAY_PAL_PAY_FLOW_PRO("PayPalPayFlowPro"),
    @XmlEnumValue("PaymentTech")
    PAYMENT_TECH("PaymentTech"),
    @XmlEnumValue("PlugNPay")
    PLUG_N_PAY("PlugNPay"),
    QBMS("QBMS"),
    @XmlEnumValue("USAePay")
    US_AE_PAY("USAePay"),
    @XmlEnumValue("PayGov")
    PAY_GOV("PayGov"),
    @XmlEnumValue("eCheckIt")
    E_CHECK_IT("eCheckIt"),
    @XmlEnumValue("Custom")
    CUSTOM("Custom");
    private final String value;

    PaymentMethodCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PaymentMethodCodeType fromValue(String v) {
        for (PaymentMethodCodeType c: PaymentMethodCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
