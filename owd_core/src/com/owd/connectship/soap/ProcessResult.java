
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents the result of a rate or ship operation.
 * 
 * <p>Java class for ProcessResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resultData" type="{urn:connectship-com:ampcore}DataDictionary"/>
 *         &lt;element name="packageResults" type="{urn:connectship-com:ampcore}PackageResultList" minOccurs="0"/>
 *         &lt;element name="service" type="{urn:connectship-com:ampcore}Identity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessResult", propOrder = {
    "code",
    "message",
    "resultData",
    "packageResults",
    "service"
})
public class ProcessResult {

    protected int code;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(required = true)
    protected DataDictionary resultData;
    protected PackageResultList packageResults;
    protected Identity service;

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
     * Gets the value of the packageResults property.
     * 
     * @return
     *     possible object is
     *     {@link PackageResultList }
     *     
     */
    public PackageResultList getPackageResults() {
        return packageResults;
    }

    /**
     * Sets the value of the packageResults property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageResultList }
     *     
     */
    public void setPackageResults(PackageResultList value) {
        this.packageResults = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link Identity }
     *     
     */
    public Identity getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link Identity }
     *     
     */
    public void setService(Identity value) {
        this.service = value;
    }

}
