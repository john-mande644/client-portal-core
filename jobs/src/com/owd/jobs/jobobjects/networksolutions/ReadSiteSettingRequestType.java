
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReadSiteSettingRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadSiteSettingRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="DetailSize" type="{urn:networksolutions:apis}SizeCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadSiteSettingRequestType", propOrder = {
    "detailSize"
})
public class ReadSiteSettingRequestType
    extends BaseRequestType
{

    @XmlElement(name = "DetailSize")
    protected SizeCodeType detailSize;

    /**
     * Gets the value of the detailSize property.
     * 
     * @return
     *     possible object is
     *     {@link SizeCodeType }
     *     
     */
    public SizeCodeType getDetailSize() {
        return detailSize;
    }

    /**
     * Sets the value of the detailSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link SizeCodeType }
     *     
     */
    public void setDetailSize(SizeCodeType value) {
        this.detailSize = value;
    }

}
