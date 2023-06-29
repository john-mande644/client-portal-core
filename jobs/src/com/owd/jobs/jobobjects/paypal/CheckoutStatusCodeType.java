package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CheckoutStatusCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="CheckoutStatusCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="CheckoutComplete"/>
 *     &lt;enumeration value="CheckoutIncomplete"/>
 *     &lt;enumeration value="BuyerRequestsTotal"/>
 *     &lt;enumeration value="SellerResponded"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "CheckoutStatusCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum CheckoutStatusCodeType
{

    @XmlEnumValue("CheckoutComplete")
    CHECKOUT_COMPLETE("CheckoutComplete"),
    @XmlEnumValue("CheckoutIncomplete")
    CHECKOUT_INCOMPLETE("CheckoutIncomplete"),
    @XmlEnumValue("BuyerRequestsTotal")
    BUYER_REQUESTS_TOTAL("BuyerRequestsTotal"),
    @XmlEnumValue("SellerResponded")
    SELLER_RESPONDED("SellerResponded"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    CheckoutStatusCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static CheckoutStatusCodeType fromValue(String v)
    {
        for (CheckoutStatusCodeType c : CheckoutStatusCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
