
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dispositionsImportResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dispositionsImportResult">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}basicImportResult">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="dispRecordsUpdated" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dispositionsImportResult", propOrder = {
    "dispRecordsUpdated"
})
public class DispositionsImportResult
    extends BasicImportResult
{

    protected long dispRecordsUpdated;

    /**
     * Gets the value of the dispRecordsUpdated property.
     * 
     */
    public long getDispRecordsUpdated() {
        return dispRecordsUpdated;
    }

    /**
     * Sets the value of the dispRecordsUpdated property.
     * 
     */
    public void setDispRecordsUpdated(long value) {
        this.dispRecordsUpdated = value;
    }

}
