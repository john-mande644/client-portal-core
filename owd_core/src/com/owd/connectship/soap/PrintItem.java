
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Identifies which specific source item (package, bundle, group, container, ship file) was used to produce a document.
 * 
 * <p>Java class for PrintItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrintItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;choice>
 *         &lt;element name="msn" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bundleId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="containerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shipFileSymbol" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrintItem", propOrder = {
    "msn",
    "bundleId",
    "groupId",
    "containerCode",
    "shipFileSymbol"
})
public class PrintItem {

    protected Integer msn;
    protected Integer bundleId;
    protected Integer groupId;
    protected String containerCode;
    protected String shipFileSymbol;

    /**
     * Gets the value of the msn property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMsn() {
        return msn;
    }

    /**
     * Sets the value of the msn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMsn(Integer value) {
        this.msn = value;
    }

    /**
     * Gets the value of the bundleId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBundleId() {
        return bundleId;
    }

    /**
     * Sets the value of the bundleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBundleId(Integer value) {
        this.bundleId = value;
    }

    /**
     * Gets the value of the groupId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGroupId(Integer value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the containerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContainerCode() {
        return containerCode;
    }

    /**
     * Sets the value of the containerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContainerCode(String value) {
        this.containerCode = value;
    }

    /**
     * Gets the value of the shipFileSymbol property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipFileSymbol() {
        return shipFileSymbol;
    }

    /**
     * Sets the value of the shipFileSymbol property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipFileSymbol(String value) {
        this.shipFileSymbol = value;
    }

}
