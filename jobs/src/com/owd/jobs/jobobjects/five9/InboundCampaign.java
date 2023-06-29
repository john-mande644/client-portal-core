
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for inboundCampaign complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="inboundCampaign">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}generalCampaign">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="defaultIvrSchedule" type="{http://service.admin.ws.five9.com/v2/}ivrScriptSchedule" minOccurs="0"/>
 *         &lt;element name="maxNumOfLines" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inboundCampaign", propOrder = {
    "defaultIvrSchedule",
    "maxNumOfLines"
})
public class InboundCampaign
    extends GeneralCampaign
{

    protected IvrScriptSchedule defaultIvrSchedule;
    protected Integer maxNumOfLines;

    /**
     * Gets the value of the defaultIvrSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link IvrScriptSchedule }
     *     
     */
    public IvrScriptSchedule getDefaultIvrSchedule() {
        return defaultIvrSchedule;
    }

    /**
     * Sets the value of the defaultIvrSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvrScriptSchedule }
     *     
     */
    public void setDefaultIvrSchedule(IvrScriptSchedule value) {
        this.defaultIvrSchedule = value;
    }

    /**
     * Gets the value of the maxNumOfLines property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMaxNumOfLines() {
        return maxNumOfLines;
    }

    /**
     * Sets the value of the maxNumOfLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxNumOfLines(Integer value) {
        this.maxNumOfLines = value;
    }

}
