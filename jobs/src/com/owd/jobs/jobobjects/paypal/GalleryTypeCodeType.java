package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GalleryTypeCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="GalleryTypeCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Featured"/>
 *     &lt;enumeration value="Gallery"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "GalleryTypeCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum GalleryTypeCodeType
{

    @XmlEnumValue("Featured")
    FEATURED("Featured"),
    @XmlEnumValue("Gallery")
    GALLERY("Gallery"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    GalleryTypeCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static GalleryTypeCodeType fromValue(String v)
    {
        for (GalleryTypeCodeType c : GalleryTypeCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
