
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for basicImportSettings complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="basicImportSettings">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="allowDataCleanup" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="fieldsMapping" type="{http://service.admin.ws.five9.com/v2/}fieldEntry" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reportEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="separator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="skipHeaderLine" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basicImportSettings", propOrder = {
    "allowDataCleanup",
    "fieldsMapping",
    "reportEmail",
    "separator",
    "skipHeaderLine"
})
@XmlSeeAlso({
    DispositionsUpdateSettings.class,
    CrmDeleteSettings.class,
    ListDeleteSettings.class,
    ListUpdateSettings.class,
    CrmUpdateSettings.class
})
public class BasicImportSettings {

    protected Boolean allowDataCleanup;
    @XmlElement(nillable = true)
    protected List<FieldEntry> fieldsMapping;
    protected String reportEmail;
    protected String separator;
    protected boolean skipHeaderLine;

    /**
     * Gets the value of the allowDataCleanup property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowDataCleanup() {
        return allowDataCleanup;
    }

    /**
     * Sets the value of the allowDataCleanup property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowDataCleanup(Boolean value) {
        this.allowDataCleanup = value;
    }

    /**
     * Gets the value of the fieldsMapping property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fieldsMapping property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldsMapping().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FieldEntry }
     * 
     * 
     */
    public List<FieldEntry> getFieldsMapping() {
        if (fieldsMapping == null) {
            fieldsMapping = new ArrayList<FieldEntry>();
        }
        return this.fieldsMapping;
    }

    /**
     * Gets the value of the reportEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportEmail() {
        return reportEmail;
    }

    /**
     * Sets the value of the reportEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportEmail(String value) {
        this.reportEmail = value;
    }

    /**
     * Gets the value of the separator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Sets the value of the separator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeparator(String value) {
        this.separator = value;
    }

    /**
     * Gets the value of the skipHeaderLine property.
     * 
     */
    public boolean isSkipHeaderLine() {
        return skipHeaderLine;
    }

    /**
     * Sets the value of the skipHeaderLine property.
     * 
     */
    public void setSkipHeaderLine(boolean value) {
        this.skipHeaderLine = value;
    }

}
