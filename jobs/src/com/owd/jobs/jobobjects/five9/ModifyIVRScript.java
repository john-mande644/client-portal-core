
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for modifyIVRScript complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="modifyIVRScript">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="scriptDef" type="{http://service.admin.ws.five9.com/v2/}ivrScriptDef" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyIVRScript", propOrder = {
    "scriptDef"
})
public class ModifyIVRScript {

    protected IvrScriptDef scriptDef;

    /**
     * Gets the value of the scriptDef property.
     * 
     * @return
     *     possible object is
     *     {@link IvrScriptDef }
     *     
     */
    public IvrScriptDef getScriptDef() {
        return scriptDef;
    }

    /**
     * Sets the value of the scriptDef property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvrScriptDef }
     *     
     */
    public void setScriptDef(IvrScriptDef value) {
        this.scriptDef = value;
    }

}
