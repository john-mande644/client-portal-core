package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PercentageRevenueFromOnlineSalesType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PercentageRevenueFromOnlineSalesType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="PercentageRevenueFromOnlineSales-Not-Applicable"/>
 *     &lt;enumeration value="PercentageRevenueFromOnlineSales-Range1"/>
 *     &lt;enumeration value="PercentageRevenueFromOnlineSales-Range2"/>
 *     &lt;enumeration value="PercentageRevenueFromOnlineSales-Range3"/>
 *     &lt;enumeration value="PercentageRevenueFromOnlineSales-Range4"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PercentageRevenueFromOnlineSalesType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PercentageRevenueFromOnlineSalesType
{

    @XmlEnumValue("PercentageRevenueFromOnlineSales-Not-Applicable")
    PERCENTAGE_REVENUE_FROM_ONLINE_SALES_NOT_APPLICABLE("PercentageRevenueFromOnlineSales-Not-Applicable"),
    @XmlEnumValue("PercentageRevenueFromOnlineSales-Range1")
    PERCENTAGE_REVENUE_FROM_ONLINE_SALES_RANGE_1("PercentageRevenueFromOnlineSales-Range1"),
    @XmlEnumValue("PercentageRevenueFromOnlineSales-Range2")
    PERCENTAGE_REVENUE_FROM_ONLINE_SALES_RANGE_2("PercentageRevenueFromOnlineSales-Range2"),
    @XmlEnumValue("PercentageRevenueFromOnlineSales-Range3")
    PERCENTAGE_REVENUE_FROM_ONLINE_SALES_RANGE_3("PercentageRevenueFromOnlineSales-Range3"),
    @XmlEnumValue("PercentageRevenueFromOnlineSales-Range4")
    PERCENTAGE_REVENUE_FROM_ONLINE_SALES_RANGE_4("PercentageRevenueFromOnlineSales-Range4");
    private final String value;

    PercentageRevenueFromOnlineSalesType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PercentageRevenueFromOnlineSalesType fromValue(String v)
    {
        for (PercentageRevenueFromOnlineSalesType c : PercentageRevenueFromOnlineSalesType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
