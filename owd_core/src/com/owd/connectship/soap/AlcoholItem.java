
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Elements that represent an alcoholic beverage item in a shipment.
 * 
 * <p>Java class for AlcoholItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlcoholItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;all>
 *         &lt;element name="alcoholPackaging" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="alcoholQuantity" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="alcoholType" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="alcoholVolume" type="{urn:connectship-com:ampcore}Volume" minOccurs="0"/>
 *         &lt;element name="userData1" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData3" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData4" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData5" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlcoholItem", propOrder = {

})
public class AlcoholItem {

    protected String alcoholPackaging;
    protected Integer alcoholQuantity;
    protected String alcoholType;
    protected Volume alcoholVolume;
    protected Object userData1;
    protected Object userData2;
    protected Object userData3;
    protected Object userData4;
    protected Object userData5;

    /**
     * Gets the value of the alcoholPackaging property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlcoholPackaging() {
        return alcoholPackaging;
    }

    /**
     * Sets the value of the alcoholPackaging property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlcoholPackaging(String value) {
        this.alcoholPackaging = value;
    }

    /**
     * Gets the value of the alcoholQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAlcoholQuantity() {
        return alcoholQuantity;
    }

    /**
     * Sets the value of the alcoholQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAlcoholQuantity(Integer value) {
        this.alcoholQuantity = value;
    }

    /**
     * Gets the value of the alcoholType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlcoholType() {
        return alcoholType;
    }

    /**
     * Sets the value of the alcoholType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlcoholType(String value) {
        this.alcoholType = value;
    }

    /**
     * Gets the value of the alcoholVolume property.
     * 
     * @return
     *     possible object is
     *     {@link Volume }
     *     
     */
    public Volume getAlcoholVolume() {
        return alcoholVolume;
    }

    /**
     * Sets the value of the alcoholVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link Volume }
     *     
     */
    public void setAlcoholVolume(Volume value) {
        this.alcoholVolume = value;
    }

    /**
     * Gets the value of the userData1 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData1() {
        return userData1;
    }

    /**
     * Sets the value of the userData1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData1(Object value) {
        this.userData1 = value;
    }

    /**
     * Gets the value of the userData2 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData2() {
        return userData2;
    }

    /**
     * Sets the value of the userData2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData2(Object value) {
        this.userData2 = value;
    }

    /**
     * Gets the value of the userData3 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData3() {
        return userData3;
    }

    /**
     * Sets the value of the userData3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData3(Object value) {
        this.userData3 = value;
    }

    /**
     * Gets the value of the userData4 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData4() {
        return userData4;
    }

    /**
     * Sets the value of the userData4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData4(Object value) {
        this.userData4 = value;
    }

    /**
     * Gets the value of the userData5 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData5() {
        return userData5;
    }

    /**
     * Sets the value of the userData5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData5(Object value) {
        this.userData5 = value;
    }

}
