
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents the result of a void operation.
 * 
 * <p>Java class for VoidResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VoidResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resultData" type="{urn:connectship-com:ampcore}VoidPackageResultList"/>
 *         &lt;element name="additionalVoidedPackages" type="{urn:connectship-com:ampcore}IntegerList" minOccurs="0"/>
 *         &lt;element name="modifiedPackages" type="{urn:connectship-com:ampcore}IntegerList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoidResult", propOrder = {
    "code",
    "message",
    "resultData",
    "additionalVoidedPackages",
    "modifiedPackages"
})
public class VoidResult {

    protected int code;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(required = true)
    protected VoidPackageResultList resultData;
    protected IntegerList additionalVoidedPackages;
    protected IntegerList modifiedPackages;

    /**
     * Gets the value of the code property.
     * 
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     */
    public void setCode(int value) {
        this.code = value;
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
     * Gets the value of the resultData property.
     * 
     * @return
     *     possible object is
     *     {@link VoidPackageResultList }
     *     
     */
    public VoidPackageResultList getResultData() {
        return resultData;
    }

    /**
     * Sets the value of the resultData property.
     * 
     * @param value
     *     allowed object is
     *     {@link VoidPackageResultList }
     *     
     */
    public void setResultData(VoidPackageResultList value) {
        this.resultData = value;
    }

    /**
     * Gets the value of the additionalVoidedPackages property.
     * 
     * @return
     *     possible object is
     *     {@link IntegerList }
     *     
     */
    public IntegerList getAdditionalVoidedPackages() {
        return additionalVoidedPackages;
    }

    /**
     * Sets the value of the additionalVoidedPackages property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegerList }
     *     
     */
    public void setAdditionalVoidedPackages(IntegerList value) {
        this.additionalVoidedPackages = value;
    }

    /**
     * Gets the value of the modifiedPackages property.
     * 
     * @return
     *     possible object is
     *     {@link IntegerList }
     *     
     */
    public IntegerList getModifiedPackages() {
        return modifiedPackages;
    }

    /**
     * Sets the value of the modifiedPackages property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegerList }
     *     
     */
    public void setModifiedPackages(IntegerList value) {
        this.modifiedPackages = value;
    }

}
