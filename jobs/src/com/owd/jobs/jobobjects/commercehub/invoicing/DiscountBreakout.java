
package com.owd.jobs.jobobjects.commercehub.invoicing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="discTypeCode">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="01"/>
 *             &lt;enumeration value="09"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="discDateCode">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="2"/>
 *             &lt;enumeration value="3"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="discPercent" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="discDaysDue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="netDaysDue" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "discountBreakout")
public class DiscountBreakout {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "discTypeCode")
    protected String discTypeCode;
    @XmlAttribute(name = "discDateCode")
    protected String discDateCode;
    @XmlAttribute(name = "discPercent")
    protected String discPercent;
    @XmlAttribute(name = "discDaysDue")
    protected String discDaysDue;
    @XmlAttribute(name = "netDaysDue")
    protected String netDaysDue;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the discTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscTypeCode() {
        return discTypeCode;
    }

    /**
     * Sets the value of the discTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscTypeCode(String value) {
        this.discTypeCode = value;
    }

    /**
     * Gets the value of the discDateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscDateCode() {
        return discDateCode;
    }

    /**
     * Sets the value of the discDateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscDateCode(String value) {
        this.discDateCode = value;
    }

    /**
     * Gets the value of the discPercent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscPercent() {
        return discPercent;
    }

    /**
     * Sets the value of the discPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscPercent(String value) {
        this.discPercent = value;
    }

    /**
     * Gets the value of the discDaysDue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscDaysDue() {
        return discDaysDue;
    }

    /**
     * Sets the value of the discDaysDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscDaysDue(String value) {
        this.discDaysDue = value;
    }

    /**
     * Gets the value of the netDaysDue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetDaysDue() {
        return netDaysDue;
    }

    /**
     * Sets the value of the netDaysDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetDaysDue(String value) {
        this.netDaysDue = value;
    }

}
