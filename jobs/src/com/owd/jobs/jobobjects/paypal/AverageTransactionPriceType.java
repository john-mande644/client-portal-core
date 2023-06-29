package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AverageTransactionPriceType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="AverageTransactionPriceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="AverageTransactionPrice-Not-Applicable"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range1"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range2"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range3"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range4"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range5"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range6"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range7"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range8"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range9"/>
 *     &lt;enumeration value="AverageTransactionPrice-Range10"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "AverageTransactionPriceType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum AverageTransactionPriceType
{

    @XmlEnumValue("AverageTransactionPrice-Not-Applicable")
    AVERAGE_TRANSACTION_PRICE_NOT_APPLICABLE("AverageTransactionPrice-Not-Applicable"),
    @XmlEnumValue("AverageTransactionPrice-Range1")
    AVERAGE_TRANSACTION_PRICE_RANGE_1("AverageTransactionPrice-Range1"),
    @XmlEnumValue("AverageTransactionPrice-Range2")
    AVERAGE_TRANSACTION_PRICE_RANGE_2("AverageTransactionPrice-Range2"),
    @XmlEnumValue("AverageTransactionPrice-Range3")
    AVERAGE_TRANSACTION_PRICE_RANGE_3("AverageTransactionPrice-Range3"),
    @XmlEnumValue("AverageTransactionPrice-Range4")
    AVERAGE_TRANSACTION_PRICE_RANGE_4("AverageTransactionPrice-Range4"),
    @XmlEnumValue("AverageTransactionPrice-Range5")
    AVERAGE_TRANSACTION_PRICE_RANGE_5("AverageTransactionPrice-Range5"),
    @XmlEnumValue("AverageTransactionPrice-Range6")
    AVERAGE_TRANSACTION_PRICE_RANGE_6("AverageTransactionPrice-Range6"),
    @XmlEnumValue("AverageTransactionPrice-Range7")
    AVERAGE_TRANSACTION_PRICE_RANGE_7("AverageTransactionPrice-Range7"),
    @XmlEnumValue("AverageTransactionPrice-Range8")
    AVERAGE_TRANSACTION_PRICE_RANGE_8("AverageTransactionPrice-Range8"),
    @XmlEnumValue("AverageTransactionPrice-Range9")
    AVERAGE_TRANSACTION_PRICE_RANGE_9("AverageTransactionPrice-Range9"),
    @XmlEnumValue("AverageTransactionPrice-Range10")
    AVERAGE_TRANSACTION_PRICE_RANGE_10("AverageTransactionPrice-Range10");
    private final String value;

    AverageTransactionPriceType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static AverageTransactionPriceType fromValue(String v)
    {
        for (AverageTransactionPriceType c : AverageTransactionPriceType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
