
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReadSiteSettingResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadSiteSettingResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="SiteSetting" type="{urn:networksolutions:apis}SiteSettingType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadSiteSettingResponseType", propOrder = {
    "siteSetting"
})
public class ReadSiteSettingResponseType
    extends BaseResponseType
{

    @XmlElement(name = "SiteSetting")
    protected SiteSettingType siteSetting;

    /**
     * Gets the value of the siteSetting property.
     * 
     * @return
     *     possible object is
     *     {@link SiteSettingType }
     *     
     */
    public SiteSettingType getSiteSetting() {
        return siteSetting;
    }

    /**
     * Sets the value of the siteSetting property.
     * 
     * @param value
     *     allowed object is
     *     {@link SiteSettingType }
     *     
     */
    public void setSiteSetting(SiteSettingType value) {
        this.siteSetting = value;
    }

}
