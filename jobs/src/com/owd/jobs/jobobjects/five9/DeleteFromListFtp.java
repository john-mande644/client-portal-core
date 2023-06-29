
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for deleteFromListFtp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="deleteFromListFtp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="listName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deleteSettings" type="{http://service.admin.ws.five9.com/v2/}listDeleteSettings" minOccurs="0"/>
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
@XmlType(name = "deleteFromListFtp", propOrder = {
    "listName",
    "deleteSettings",
    "ftpSettings"
})
public class DeleteFromListFtp {

    protected String listName;
    protected ListDeleteSettings deleteSettings;
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
     * Gets the value of the deleteSettings property.
     * 
     * @return
     *     possible object is
     *     {@link ListDeleteSettings }
     *     
     */
    public ListDeleteSettings getDeleteSettings() {
        return deleteSettings;
    }

    /**
     * Sets the value of the deleteSettings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListDeleteSettings }
     *     
     */
    public void setDeleteSettings(ListDeleteSettings value) {
        this.deleteSettings = value;
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
