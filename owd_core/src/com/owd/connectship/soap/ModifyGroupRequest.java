
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Request to modify a group.
 * 
 * <p>Java class for ModifyGroupRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ModifyGroupRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="carrier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grouping" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="groupIdentifier" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="groupData" type="{urn:connectship-com:ampcore}DataDictionary" minOccurs="0"/>
 *         &lt;element name="status" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
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
@XmlType(name = "ModifyGroupRequest", propOrder = {
    "carrier",
    "grouping",
    "groupIdentifier",
    "groupData",
    "status"
})
public class ModifyGroupRequest {

    protected String carrier;
    @XmlElement(required = true)
    protected String grouping;
    @XmlElement(required = true)
    protected String groupIdentifier;
    protected DataDictionary groupData;
    protected String status;
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
     * Gets the value of the grouping property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrouping() {
        return grouping;
    }

    /**
     * Sets the value of the grouping property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrouping(String value) {
        this.grouping = value;
    }

    /**
     * Gets the value of the groupIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupIdentifier() {
        return groupIdentifier;
    }

    /**
     * Sets the value of the groupIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupIdentifier(String value) {
        this.groupIdentifier = value;
    }

    /**
     * Gets the value of the groupData property.
     * 
     * @return
     *     possible object is
     *     {@link DataDictionary }
     *     
     */
    public DataDictionary getGroupData() {
        return groupData;
    }

    /**
     * Sets the value of the groupData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDictionary }
     *     
     */
    public void setGroupData(DataDictionary value) {
        this.groupData = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
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
