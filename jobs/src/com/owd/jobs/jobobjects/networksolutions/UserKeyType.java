
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserKeyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="LoginUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SuccessUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FailureUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserKeyType", propOrder = {
    "loginUrl",
    "userKey",
    "successUrl",
    "failureUrl"
})
public class UserKeyType {

    @XmlElement(name = "LoginUrl")
    protected String loginUrl;
    @XmlElement(name = "UserKey")
    protected String userKey;
    @XmlElement(name = "SuccessUrl")
    protected String successUrl;
    @XmlElement(name = "FailureUrl")
    protected String failureUrl;

    /**
     * Gets the value of the loginUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoginUrl() {
        return loginUrl;
    }

    /**
     * Sets the value of the loginUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoginUrl(String value) {
        this.loginUrl = value;
    }

    /**
     * Gets the value of the userKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     * Sets the value of the userKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserKey(String value) {
        this.userKey = value;
    }

    /**
     * Gets the value of the successUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuccessUrl() {
        return successUrl;
    }

    /**
     * Sets the value of the successUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuccessUrl(String value) {
        this.successUrl = value;
    }

    /**
     * Gets the value of the failureUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFailureUrl() {
        return failureUrl;
    }

    /**
     * Sets the value of the failureUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFailureUrl(String value) {
        this.failureUrl = value;
    }

}
