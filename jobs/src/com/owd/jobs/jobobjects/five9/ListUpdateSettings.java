
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for listUpdateSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="listUpdateSettings">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.admin.ws.five9.com/v2/}basicImportSettings">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="callNowColumnNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="callNowMode" type="{http://service.admin.ws.five9.com/v2/}callNowMode" minOccurs="0"/>
 *         &lt;element name="callTime" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="callTimeColumnNumber" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cleanListBeforeUpdate" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="crmAddMode" type="{http://service.admin.ws.five9.com/v2/}crmAddMode" minOccurs="0"/>
 *         &lt;element name="crmUpdateMode" type="{http://service.admin.ws.five9.com/v2/}crmUpdateMode" minOccurs="0"/>
 *         &lt;element name="listAddMode" type="{http://service.admin.ws.five9.com/v2/}listAddMode" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listUpdateSettings", propOrder = {
    "callNowColumnNumber",
    "callNowMode",
    "callTime",
    "callTimeColumnNumber",
    "cleanListBeforeUpdate",
    "crmAddMode",
    "crmUpdateMode",
    "listAddMode"
})
public class ListUpdateSettings
    extends BasicImportSettings
{

    protected Integer callNowColumnNumber;
    protected CallNowMode callNowMode;
    protected Long callTime;
    protected Integer callTimeColumnNumber;
    protected boolean cleanListBeforeUpdate;
    protected CrmAddMode crmAddMode;
    protected CrmUpdateMode crmUpdateMode;
    protected ListAddMode listAddMode;

    /**
     * Gets the value of the callNowColumnNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCallNowColumnNumber() {
        return callNowColumnNumber;
    }

    /**
     * Sets the value of the callNowColumnNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCallNowColumnNumber(Integer value) {
        this.callNowColumnNumber = value;
    }

    /**
     * Gets the value of the callNowMode property.
     * 
     * @return
     *     possible object is
     *     {@link CallNowMode }
     *     
     */
    public CallNowMode getCallNowMode() {
        return callNowMode;
    }

    /**
     * Sets the value of the callNowMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallNowMode }
     *     
     */
    public void setCallNowMode(CallNowMode value) {
        this.callNowMode = value;
    }

    /**
     * Gets the value of the callTime property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCallTime() {
        return callTime;
    }

    /**
     * Sets the value of the callTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCallTime(Long value) {
        this.callTime = value;
    }

    /**
     * Gets the value of the callTimeColumnNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCallTimeColumnNumber() {
        return callTimeColumnNumber;
    }

    /**
     * Sets the value of the callTimeColumnNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCallTimeColumnNumber(Integer value) {
        this.callTimeColumnNumber = value;
    }

    /**
     * Gets the value of the cleanListBeforeUpdate property.
     * 
     */
    public boolean isCleanListBeforeUpdate() {
        return cleanListBeforeUpdate;
    }

    /**
     * Sets the value of the cleanListBeforeUpdate property.
     * 
     */
    public void setCleanListBeforeUpdate(boolean value) {
        this.cleanListBeforeUpdate = value;
    }

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

    /**
     * Gets the value of the listAddMode property.
     * 
     * @return
     *     possible object is
     *     {@link ListAddMode }
     *     
     */
    public ListAddMode getListAddMode() {
        return listAddMode;
    }

    /**
     * Sets the value of the listAddMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListAddMode }
     *     
     */
    public void setListAddMode(ListAddMode value) {
        this.listAddMode = value;
    }

}
