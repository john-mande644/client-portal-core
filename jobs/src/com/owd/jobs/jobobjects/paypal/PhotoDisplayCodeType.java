package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PhotoDisplayCodeType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="PhotoDisplayCodeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="None"/>
 *     &lt;enumeration value="SlideShow"/>
 *     &lt;enumeration value="SuperSize"/>
 *     &lt;enumeration value="PicturePack"/>
 *     &lt;enumeration value="CustomCode"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "PhotoDisplayCodeType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum PhotoDisplayCodeType
{

    @XmlEnumValue("None")
    NONE("None"),
    @XmlEnumValue("SlideShow")
    SLIDE_SHOW("SlideShow"),
    @XmlEnumValue("SuperSize")
    SUPER_SIZE("SuperSize"),
    @XmlEnumValue("PicturePack")
    PICTURE_PACK("PicturePack"),
    @XmlEnumValue("CustomCode")
    CUSTOM_CODE("CustomCode");
    private final String value;

    PhotoDisplayCodeType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static PhotoDisplayCodeType fromValue(String v)
    {
        for (PhotoDisplayCodeType c : PhotoDisplayCodeType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
