
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for renameDisposition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="renameDisposition">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="dispositionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dispositionNewName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "renameDisposition", propOrder = {
    "dispositionName",
    "dispositionNewName"
})
public class RenameDisposition {

    protected String dispositionName;
    protected String dispositionNewName;

    /**
     * Gets the value of the dispositionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispositionName() {
        return dispositionName;
    }

    /**
     * Sets the value of the dispositionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispositionName(String value) {
        this.dispositionName = value;
    }

    /**
     * Gets the value of the dispositionNewName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispositionNewName() {
        return dispositionNewName;
    }

    /**
     * Sets the value of the dispositionNewName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispositionNewName(String value) {
        this.dispositionNewName = value;
    }

}
