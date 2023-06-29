
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteFromList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteFromList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="listName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listDeleteSettings" type="{http://service.admin.ws.five9.com/v2/}listDeleteSettings" minOccurs="0"/>
 *         &lt;element name="importData" type="{http://service.admin.ws.five9.com/v2/}importData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteFromList", propOrder = {
    "listName",
    "listDeleteSettings",
    "importData"
})
public class DeleteFromList {

    protected String listName;
    protected ListDeleteSettings listDeleteSettings;
    protected ImportData importData;

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
     * Gets the value of the listDeleteSettings property.
     * 
     * @return
     *     possible object is
     *     {@link ListDeleteSettings }
     *     
     */
    public ListDeleteSettings getListDeleteSettings() {
        return listDeleteSettings;
    }

    /**
     * Sets the value of the listDeleteSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListDeleteSettings }
     *     
     */
    public void setListDeleteSettings(ListDeleteSettings value) {
        this.listDeleteSettings = value;
    }

    /**
     * Gets the value of the importData property.
     * 
     * @return
     *     possible object is
     *     {@link ImportData }
     *     
     */
    public ImportData getImportData() {
        return importData;
    }

    /**
     * Sets the value of the importData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImportData }
     *     
     */
    public void setImportData(ImportData value) {
        this.importData = value;
    }

}
