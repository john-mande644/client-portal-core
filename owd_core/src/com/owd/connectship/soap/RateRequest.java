
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Request to rate a list of packages with one or more services.
 * 
 * <p>Java class for RateRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RateRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="services" type="{urn:connectship-com:ampcore}ServiceList"/>
 *         &lt;element name="defaults" type="{urn:connectship-com:ampcore}DataDictionary"/>
 *         &lt;element name="packages" type="{urn:connectship-com:ampcore}DataDictionaryList"/>
 *         &lt;element name="sortType" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}preProcess"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}postProcess"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}locale"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}asyncCorrelationData"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RateRequest", propOrder = {
    "services",
    "defaults",
    "packages",
    "sortType"
})
public class RateRequest {

    @XmlElement(required = true)
    protected ServiceList services;
    @XmlElement(required = true)
    protected DataDictionary defaults;
    @XmlElement(required = true)
    protected DataDictionaryList packages;
    protected String sortType;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String preProcess;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String postProcess;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String locale;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String asyncCorrelationData;

    /**
     * Gets the value of the services property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceList }
     *     
     */
    public ServiceList getServices() {
        return services;
    }

    /**
     * Sets the value of the services property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceList }
     *     
     */
    public void setServices(ServiceList value) {
        this.services = value;
    }

    /**
     * Gets the value of the defaults property.
     * 
     * @return
     *     possible object is
     *     {@link DataDictionary }
     *     
     */
    public DataDictionary getDefaults() {
        return defaults;
    }

    /**
     * Sets the value of the defaults property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDictionary }
     *     
     */
    public void setDefaults(DataDictionary value) {
        this.defaults = value;
    }

    /**
     * Gets the value of the packages property.
     * 
     * @return
     *     possible object is
     *     {@link DataDictionaryList }
     *     
     */
    public DataDictionaryList getPackages() {
        return packages;
    }

    /**
     * Sets the value of the packages property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDictionaryList }
     *     
     */
    public void setPackages(DataDictionaryList value) {
        this.packages = value;
    }

    /**
     * Gets the value of the sortType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSortType() {
        return sortType;
    }

    /**
     * Sets the value of the sortType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSortType(String value) {
        this.sortType = value;
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
