
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Request to search for processed packages.
 * 
 * <p>Java class for SearchRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="carrier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shipper" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="shipFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filters" type="{urn:connectship-com:ampcore}DataDictionary"/>
 *         &lt;element name="returnFields" type="{urn:connectship-com:ampcore}DataDictionary" minOccurs="0"/>
 *         &lt;element name="searchCloseOutMode" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="searchVoided" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}preProcess"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}postProcess"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}locale"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}contextControl"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}asyncCorrelationData"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchRequest", propOrder = {
    "carrier",
    "shipper",
    "shipFile",
    "filters",
    "returnFields",
    "searchCloseOutMode",
    "searchVoided"
})
public class SearchRequest {

    @XmlElement(required = true)
    protected String carrier;
    protected String shipper;
    protected String shipFile;
    @XmlElement(required = true)
    protected DataDictionary filters;
    protected DataDictionary returnFields;
    protected String searchCloseOutMode;
    protected String searchVoided;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String preProcess;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String postProcess;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String locale;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected ContextControlSetting contextControl;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String asyncCorrelationData;

    /**
     * Gets the value of the carrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     * Sets the value of the carrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrier(String value) {
        this.carrier = value;
    }

    /**
     * Gets the value of the shipper property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipper() {
        return shipper;
    }

    /**
     * Sets the value of the shipper property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipper(String value) {
        this.shipper = value;
    }

    /**
     * Gets the value of the shipFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipFile() {
        return shipFile;
    }

    /**
     * Sets the value of the shipFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipFile(String value) {
        this.shipFile = value;
    }

    /**
     * Gets the value of the filters property.
     * 
     * @return
     *     possible object is
     *     {@link DataDictionary }
     *     
     */
    public DataDictionary getFilters() {
        return filters;
    }

    /**
     * Sets the value of the filters property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDictionary }
     *     
     */
    public void setFilters(DataDictionary value) {
        this.filters = value;
    }

    /**
     * Gets the value of the returnFields property.
     * 
     * @return
     *     possible object is
     *     {@link DataDictionary }
     *     
     */
    public DataDictionary getReturnFields() {
        return returnFields;
    }

    /**
     * Sets the value of the returnFields property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDictionary }
     *     
     */
    public void setReturnFields(DataDictionary value) {
        this.returnFields = value;
    }

    /**
     * Gets the value of the searchCloseOutMode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchCloseOutMode() {
        return searchCloseOutMode;
    }

    /**
     * Sets the value of the searchCloseOutMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchCloseOutMode(String value) {
        this.searchCloseOutMode = value;
    }

    /**
     * Gets the value of the searchVoided property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchVoided() {
        return searchVoided;
    }

    /**
     * Sets the value of the searchVoided property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchVoided(String value) {
        this.searchVoided = value;
    }

    /**
     * Gets the value of the preProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreProcess() {
        return preProcess;
    }

    /**
     * Sets the value of the preProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreProcess(String value) {
        this.preProcess = value;
    }

    /**
     * Gets the value of the postProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostProcess() {
        return postProcess;
    }

    /**
     * Sets the value of the postProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostProcess(String value) {
        this.postProcess = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the contextControl property.
     * 
     * @return
     *     possible object is
     *     {@link ContextControlSetting }
     *     
     */
    public ContextControlSetting getContextControl() {
        return contextControl;
    }

    /**
     * Sets the value of the contextControl property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContextControlSetting }
     *     
     */
    public void setContextControl(ContextControlSetting value) {
        this.contextControl = value;
    }

    /**
     * Gets the value of the asyncCorrelationData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsyncCorrelationData() {
        return asyncCorrelationData;
    }

    /**
     * Sets the value of the asyncCorrelationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsyncCorrelationData(String value) {
        this.asyncCorrelationData = value;
    }

}
