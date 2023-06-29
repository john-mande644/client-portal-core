
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for fieldEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="fieldEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="columnNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fieldName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fieldEntry", propOrder = {
    "columnNumber",
    "fieldName",
    "key"
})
public class FieldEntry {

    protected int columnNumber;
    protected String fieldName;
    protected boolean key;

    /**
     * Gets the value of the columnNumber property.
     * 
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * Sets the value of the columnNumber property.
     * 
     */
    public void setColumnNumber(int value) {
        this.columnNumber = value;
    }

    /**
     * Gets the value of the fieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the value of the fieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Gets the value of the key property.
     * 
     */
    public boolean isKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     */
    public void setKey(boolean value) {
        this.key = value;
    }

}
