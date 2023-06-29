
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for customReportCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="customReportCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="reportObjects" type="{http://service.admin.ws.five9.com/v2/}reportObjectList" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="time" type="{http://service.admin.ws.five9.com/v2/}reportTimeCriteria" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "customReportCriteria", propOrder = {
    "reportObjects",
    "time"
})
public class CustomReportCriteria {

    @XmlElement(nillable = true)
    protected List<ReportObjectList> reportObjects;
    protected ReportTimeCriteria time;

    /**
     * Gets the value of the reportObjects property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reportObjects property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReportObjects().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReportObjectList }
     * 
     * 
     */
    public List<ReportObjectList> getReportObjects() {
        if (reportObjects == null) {
            reportObjects = new ArrayList<ReportObjectList>();
        }
        return this.reportObjects;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link ReportTimeCriteria }
     *     
     */
    public ReportTimeCriteria getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportTimeCriteria }
     *     
     */
    public void setTime(ReportTimeCriteria value) {
        this.time = value;
    }

}
