
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addToListFtp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addToListFtp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="listName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateSettings" type="{http://service.admin.ws.five9.com/v2/}listUpdateSettings" minOccurs="0"/>
 *         &lt;element name="ftpSettings" type="{http://service.admin.ws.five9.com/v2/}ftpImportSettings" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addToListFtp", propOrder = {
    "listName",
    "updateSettings",
    "ftpSettings"
})
public class AddToListFtp {

    protected String listName;
    protected ListUpdateSettings updateSettings;
    protected FtpImportSettings ftpSettings;

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
     * Gets the value of the updateSettings property.
     * 
     * @return
     *     possible object is
     *     {@link ListUpdateSettings }
     *     
     */
    public ListUpdateSettings getUpdateSettings() {
        return updateSettings;
    }

    /**
     * Sets the value of the updateSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListUpdateSettings }
     *     
     */
    public void setUpdateSettings(ListUpdateSettings value) {
        this.updateSettings = value;
    }

    /**
     * Gets the value of the ftpSettings property.
     * 
     * @return
     *     possible object is
     *     {@link FtpImportSettings }
     *     
     */
    public FtpImportSettings getFtpSettings() {
        return ftpSettings;
    }

    /**
     * Sets the value of the ftpSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link FtpImportSettings }
     *     
     */
    public void setFtpSettings(FtpImportSettings value) {
        this.ftpSettings = value;
    }

}
