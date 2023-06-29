
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for crmDeleteSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="crmDeleteSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}basicImportSettings">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="crmDeleteMode" type="{http://service.admin.ws.five9.com/v2/}crmDeleteMode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "crmDeleteSettings", propOrder = {
    "crmDeleteMode"
})
public class CrmDeleteSettings
    extends BasicImportSettings
{

    protected CrmDeleteMode crmDeleteMode;

    /**
     * Gets the value of the crmDeleteMode property.
     * 
     * @return
     *     possible object is
     *     {@link CrmDeleteMode }
     *     
     */
    public CrmDeleteMode getCrmDeleteMode() {
        return crmDeleteMode;
    }

    /**
     * Sets the value of the crmDeleteMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrmDeleteMode }
     *     
     */
    public void setCrmDeleteMode(CrmDeleteMode value) {
        this.crmDeleteMode = value;
    }

}
