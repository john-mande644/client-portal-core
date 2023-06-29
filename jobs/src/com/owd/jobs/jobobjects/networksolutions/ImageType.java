
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PathUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AlternateText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageType", propOrder = {
    "pathUrl",
    "alternateText"
})
public class ImageType {

    @XmlElement(name = "PathUrl")
    protected String pathUrl;
    @XmlElement(name = "AlternateText")
    protected String alternateText;

    /**
     * Gets the value of the pathUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathUrl() {
        return pathUrl;
    }

    /**
     * Sets the value of the pathUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathUrl(String value) {
        this.pathUrl = value;
    }

    /**
     * Gets the value of the alternateText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternateText() {
        return alternateText;
    }

    /**
     * Sets the value of the alternateText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternateText(String value) {
        this.alternateText = value;
    }

}
