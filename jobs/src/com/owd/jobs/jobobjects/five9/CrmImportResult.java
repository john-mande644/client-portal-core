
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for crmImportResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="crmImportResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}basicImportResult">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="crmRecordsDeleted" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="crmRecordsInserted" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="crmRecordsUpdated" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "crmImportResult", propOrder = {
    "crmRecordsDeleted",
    "crmRecordsInserted",
    "crmRecordsUpdated"
})
public class CrmImportResult
    extends BasicImportResult
{

    protected long crmRecordsDeleted;
    protected long crmRecordsInserted;
    protected long crmRecordsUpdated;

    /**
     * Gets the value of the crmRecordsDeleted property.
     * 
     */
    public long getCrmRecordsDeleted() {
        return crmRecordsDeleted;
    }

    /**
     * Sets the value of the crmRecordsDeleted property.
     * 
     */
    public void setCrmRecordsDeleted(long value) {
        this.crmRecordsDeleted = value;
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

}
