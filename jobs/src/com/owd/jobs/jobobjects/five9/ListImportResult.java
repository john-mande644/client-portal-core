
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listImportResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listImportResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}basicImportResult">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="callNowQueued" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="crmRecordsInserted" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="crmRecordsUpdated" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="listName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listRecordsDeleted" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="listRecordsInserted" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listImportResult", propOrder = {
    "callNowQueued",
    "crmRecordsInserted",
    "crmRecordsUpdated",
    "listName",
    "listRecordsDeleted",
    "listRecordsInserted"
})
public class ListImportResult
    extends BasicImportResult
{

    protected long callNowQueued;
    protected long crmRecordsInserted;
    protected long crmRecordsUpdated;
    protected String listName;
    protected long listRecordsDeleted;
    protected long listRecordsInserted;

    /**
     * Gets the value of the callNowQueued property.
     * 
     */
    public long getCallNowQueued() {
        return callNowQueued;
    }

    /**
     * Sets the value of the callNowQueued property.
     * 
     */
    public void setCallNowQueued(long value) {
        this.callNowQueued = value;
    }

    /**
     * Gets the value of the crmRecordsInserted property.
     * 
     */
    public long getCrmRecordsInserted() {
        return crmRecordsInserted;
    }

    /**
     * Sets the value of the crmRecordsInserted property.
     * 
     */
    public void setCrmRecordsInserted(long value) {
        this.crmRecordsInserted = value;
    }

    /**
     * Gets the value of the crmRecordsUpdated property.
     * 
     */
    public long getCrmRecordsUpdated() {
        return crmRecordsUpdated;
    }

    /**
     * Sets the value of the crmRecordsUpdated property.
     * 
     */
    public void setCrmRecordsUpdated(long value) {
        this.crmRecordsUpdated = value;
    }

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
     * Gets the value of the listRecordsDeleted property.
     * 
     */
    public long getListRecordsDeleted() {
        return listRecordsDeleted;
    }

    /**
     * Sets the value of the listRecordsDeleted property.
     * 
     */
    public void setListRecordsDeleted(long value) {
        this.listRecordsDeleted = value;
    }

    /**
     * Gets the value of the listRecordsInserted property.
     * 
     */
    public long getListRecordsInserted() {
        return listRecordsInserted;
    }

    /**
     * Sets the value of the listRecordsInserted property.
     * 
     */
    public void setListRecordsInserted(long value) {
        this.listRecordsInserted = value;
    }

}
