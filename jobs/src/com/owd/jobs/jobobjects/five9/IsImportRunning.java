
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for isImportRunning complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="isImportRunning">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="identifier" type="{http://service.admin.ws.five9.com/v2/}importIdentifier" minOccurs="0"/>
 *         &lt;element name="waitTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "isImportRunning", propOrder = {
    "identifier",
    "waitTime"
})
public class IsImportRunning {

    protected ImportIdentifier identifier;
    protected Long waitTime;

    /**
     * Gets the value of the identifier property.
     * 
     * @return
     *     possible object is
     *     {@link ImportIdentifier }
     *     
     */
    public ImportIdentifier getIdentifier() {
        return identifier;
    }

    /**
     * Sets the value of the identifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportIdentifier }
     *     
     */
    public void setIdentifier(ImportIdentifier value) {
        this.identifier = value;
    }

    /**
     * Gets the value of the waitTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getWaitTime() {
        return waitTime;
    }

    /**
     * Sets the value of the waitTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setWaitTime(Long value) {
        this.waitTime = value;
    }

}
