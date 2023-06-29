
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for crmUpdateSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="crmUpdateSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}basicImportSettings">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="crmAddMode" type="{http://service.admin.ws.five9.com/v2/}crmAddMode" minOccurs="0"/>
 *         &lt;element name="crmUpdateMode" type="{http://service.admin.ws.five9.com/v2/}crmUpdateMode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "crmUpdateSettings", propOrder = {
    "crmAddMode",
    "crmUpdateMode"
})
public class CrmUpdateSettings
    extends BasicImportSettings
{

    protected CrmAddMode crmAddMode;
    protected CrmUpdateMode crmUpdateMode;

    /**
     * Gets the value of the crmAddMode property.
     * 
     * @return
     *     possible object is
     *     {@link CrmAddMode }
     *     
     */
    public CrmAddMode getCrmAddMode() {
        return crmAddMode;
    }

    /**
     * Sets the value of the crmAddMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrmAddMode }
     *     
     */
    public void setCrmAddMode(CrmAddMode value) {
        this.crmAddMode = value;
    }

    /**
     * Gets the value of the crmUpdateMode property.
     * 
     * @return
     *     possible object is
     *     {@link CrmUpdateMode }
     *     
     */
    public CrmUpdateMode getCrmUpdateMode() {
        return crmUpdateMode;
    }

    /**
     * Sets the value of the crmUpdateMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CrmUpdateMode }
     *     
     */
    public void setCrmUpdateMode(CrmUpdateMode value) {
        this.crmUpdateMode = value;
    }

}
