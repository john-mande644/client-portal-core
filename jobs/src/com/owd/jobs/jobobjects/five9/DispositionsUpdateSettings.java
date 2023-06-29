
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dispositionsUpdateSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dispositionsUpdateSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}basicImportSettings">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="commonDispositionValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dispositionColumnNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dispositionsUpdateMode" type="{http://service.admin.ws.five9.com/v2/}dispositionsUpdateMode" minOccurs="0"/>
 *         &lt;element name="updateToCommonDisposition" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="warnIfNoCrmMatchFound" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dispositionsUpdateSettings", propOrder = {
    "commonDispositionValue",
    "dispositionColumnNumber",
    "dispositionsUpdateMode",
    "updateToCommonDisposition",
    "warnIfNoCrmMatchFound"
})
public class DispositionsUpdateSettings
    extends BasicImportSettings
{

    protected String commonDispositionValue;
    protected int dispositionColumnNumber;
    protected DispositionsUpdateMode dispositionsUpdateMode;
    protected boolean updateToCommonDisposition;
    protected boolean warnIfNoCrmMatchFound;

    /**
     * Gets the value of the commonDispositionValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommonDispositionValue() {
        return commonDispositionValue;
    }

    /**
     * Sets the value of the commonDispositionValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommonDispositionValue(String value) {
        this.commonDispositionValue = value;
    }

    /**
     * Gets the value of the dispositionColumnNumber property.
     * 
     */
    public int getDispositionColumnNumber() {
        return dispositionColumnNumber;
    }

    /**
     * Sets the value of the dispositionColumnNumber property.
     * 
     */
    public void setDispositionColumnNumber(int value) {
        this.dispositionColumnNumber = value;
    }

    /**
     * Gets the value of the dispositionsUpdateMode property.
     * 
     * @return
     *     possible object is
     *     {@link DispositionsUpdateMode }
     *     
     */
    public DispositionsUpdateMode getDispositionsUpdateMode() {
        return dispositionsUpdateMode;
    }

    /**
     * Sets the value of the dispositionsUpdateMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DispositionsUpdateMode }
     *     
     */
    public void setDispositionsUpdateMode(DispositionsUpdateMode value) {
        this.dispositionsUpdateMode = value;
    }

    /**
     * Gets the value of the updateToCommonDisposition property.
     * 
     */
    public boolean isUpdateToCommonDisposition() {
        return updateToCommonDisposition;
    }

    /**
     * Sets the value of the updateToCommonDisposition property.
     * 
     */
    public void setUpdateToCommonDisposition(boolean value) {
        this.updateToCommonDisposition = value;
    }

    /**
     * Gets the value of the warnIfNoCrmMatchFound property.
     * 
     */
    public boolean isWarnIfNoCrmMatchFound() {
        return warnIfNoCrmMatchFound;
    }

    /**
     * Sets the value of the warnIfNoCrmMatchFound property.
     * 
     */
    public void setWarnIfNoCrmMatchFound(boolean value) {
        this.warnIfNoCrmMatchFound = value;
    }

}
