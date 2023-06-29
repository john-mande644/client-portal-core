
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addRecordToList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addRecordToList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="listName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listUpdateSettings" type="{http://service.admin.ws.five9.com/v2/}listUpdateSettings" minOccurs="0"/>
 *         &lt;element name="record" type="{http://service.admin.ws.five9.com/v2/}recordData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addRecordToList", propOrder = {
    "listName",
    "listUpdateSettings",
    "record"
})
public class AddRecordToList {

    protected String listName;
    protected ListUpdateSettings listUpdateSettings;
    protected RecordData record;

    /**
     * Gets the value of the listName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getListName() {
        return listName;
    }

    /**
     * Sets the value of the listName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setListName(String value) {
        this.listName = value;
    }

    /**
     * Gets the value of the listUpdateSettings property.
     * 
     * @return
     *     possible object is
     *     {@link ListUpdateSettings }
     *     
     */
    public ListUpdateSettings getListUpdateSettings() {
        return listUpdateSettings;
    }

    /**
     * Sets the value of the listUpdateSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListUpdateSettings }
     *     
     */
    public void setListUpdateSettings(ListUpdateSettings value) {
        this.listUpdateSettings = value;
    }

    /**
     * Gets the value of the record property.
     * 
     * @return
     *     possible object is
     *     {@link RecordData }
     *     
     */
    public RecordData getRecord() {
        return record;
    }

    /**
     * Sets the value of the record property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordData }
     *     
     */
    public void setRecord(RecordData value) {
        this.record = value;
    }

}
