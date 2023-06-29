package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FMFPendingTransactionActionType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="FMFPendingTransactionActionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Accept"/>
 *     &lt;enumeration value="Deny"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "FMFPendingTransactionActionType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum FMFPendingTransactionActionType
{

    @XmlEnumValue("Accept")
    ACCEPT("Accept"),
    @XmlEnumValue("Deny")
    DENY("Deny");
    private final String value;

    FMFPendingTransactionActionType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static FMFPendingTransactionActionType fromValue(String v)
    {
        for (FMFPendingTransactionActionType c : FMFPendingTransactionActionType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
