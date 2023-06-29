
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Request to return a list of countries.
 * 
 * <p>Java class for ListCountriesRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListCountriesRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
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
@XmlType(name = "ListCountriesRequest")
public class ListCountriesRequest {

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
