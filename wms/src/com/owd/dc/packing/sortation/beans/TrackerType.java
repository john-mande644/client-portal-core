
package com.owd.dc.packing.sortation.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for trackerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="trackerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tracking_no" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ship_Method" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lane" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tracking_fkey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trackerType", propOrder = {
    "trackingNo",
    "shipMethod",
    "lane",
    "weight",
    "trackingFkey"
})
public class TrackerType {

    @XmlElement(name = "tracking_no", required = true)
    protected String trackingNo;
    @XmlElement(name = "ship_Method", required = true)
    protected String shipMethod;
    @XmlElement(required = true)
    protected String lane;
    @XmlElement(required = true)
    protected String weight;
    @XmlElement(name = "tracking_fkey", required = true)
    protected String trackingFkey;

    /**
     * Gets the value of the trackingNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingNo() {
        return trackingNo;
    }

    /**
     * Sets the value of the trackingNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingNo(String value) {
        this.trackingNo = value;
    }

    /**
     * Gets the value of the shipMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipMethod() {
        return shipMethod;
    }

    /**
     * Sets the value of the shipMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipMethod(String value) {
        this.shipMethod = value;
    }

    /**
     * Gets the value of the lane property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLane() {
        return lane;
    }

    /**
     * Sets the value of the lane property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLane(String value) {
        this.lane = value;
    }

    /**
     * Gets the value of the weight property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeight() {
        return weight;
    }

    /**
     * Sets the value of the weight property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeight(String value) {
        this.weight = value;
    }

    /**
     * Gets the value of the trackingFkey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingFkey() {
        return trackingFkey;
    }

    /**
     * Sets the value of the trackingFkey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingFkey(String value) {
        this.trackingFkey = value;
    }

}
