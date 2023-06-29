
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCallLogReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCallLogReport">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="time" type="{http://service.admin.ws.five9.com/v2/}reportTimeCriteria" minOccurs="0"/>
 *         &lt;element name="criteria" type="{http://service.admin.ws.five9.com/v2/}callLogReportCriteria" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCallLogReport", propOrder = {
    "time",
    "criteria"
})
public class GetCallLogReport {

    protected ReportTimeCriteria time;
    protected CallLogReportCriteria criteria;

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

    /**
     * Gets the value of the criteria property.
     * 
     * @return
     *     possible object is
     *     {@link CallLogReportCriteria }
     *     
     */
    public CallLogReportCriteria getCriteria() {
        return criteria;
    }

    /**
     * Sets the value of the criteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallLogReportCriteria }
     *     
     */
    public void setCriteria(CallLogReportCriteria value) {
        this.criteria = value;
    }

}
