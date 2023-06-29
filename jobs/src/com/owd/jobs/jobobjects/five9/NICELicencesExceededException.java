
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NICELicencesExceededException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NICELicencesExceededException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="featureLicenseType" type="{http://service.admin.ws.five9.com/v2/}niceLicenseType" minOccurs="0"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalNumberOfLicenses" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NICELicencesExceededException", propOrder = {
    "featureLicenseType",
    "message",
    "totalNumberOfLicenses"
})
public class NICELicencesExceededException {

    protected NiceLicenseType featureLicenseType;
    protected String message;
    protected int totalNumberOfLicenses;

    /**
     * Gets the value of the featureLicenseType property.
     * 
     * @return
     *     possible object is
     *     {@link NiceLicenseType }
     *     
     */
    public NiceLicenseType getFeatureLicenseType() {
        return featureLicenseType;
    }

    /**
     * Sets the value of the featureLicenseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NiceLicenseType }
     *     
     */
    public void setFeatureLicenseType(NiceLicenseType value) {
        this.featureLicenseType = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the totalNumberOfLicenses property.
     * 
     */
    public int getTotalNumberOfLicenses() {
        return totalNumberOfLicenses;
    }

    /**
     * Sets the value of the totalNumberOfLicenses property.
     * 
     */
    public void setTotalNumberOfLicenses(int value) {
        this.totalNumberOfLicenses = value;
    }

}
