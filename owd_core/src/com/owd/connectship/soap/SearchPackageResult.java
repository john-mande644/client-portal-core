
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents the package-level result of a search operation.
 * 
 * <p>Java class for SearchPackageResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchPackageResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="resultData" type="{urn:connectship-com:ampcore}DataDictionary"/>
 *         &lt;element name="voided" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="closeOutMode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchPackageResult", propOrder = {
    "resultData",
    "voided",
    "closeOutMode"
})
public class SearchPackageResult {

    @XmlElement(required = true)
    protected DataDictionary resultData;
    protected boolean voided;
    @XmlElement(defaultValue = "0")
    protected Integer closeOutMode;

    /**
     * Gets the value of the resultData property.
     * 
     * @return
     *     possible object is
     *     {@link DataDictionary }
     *     
     */
    public DataDictionary getResultData() {
        return resultData;
    }

    /**
     * Sets the value of the resultData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDictionary }
     *     
     */
    public void setResultData(DataDictionary value) {
        this.resultData = value;
    }

    /**
     * Gets the value of the voided property.
     * 
     */
    public boolean isVoided() {
        return voided;
    }

    /**
     * Sets the value of the voided property.
     * 
     */
    public void setVoided(boolean value) {
        this.voided = value;
    }

    /**
     * Gets the value of the closeOutMode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCloseOutMode() {
        return closeOutMode;
    }

    /**
     * Sets the value of the closeOutMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCloseOutMode(Integer value) {
        this.closeOutMode = value;
    }

}
