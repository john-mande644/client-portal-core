
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * List of items (packages, bundles, groups, containers, ship files) used as source data for the document print operation.
 * 
 * <p>Java class for PrintItemList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrintItemList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;choice>
 *         &lt;element name="msn" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *         &lt;element name="bundleId" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *         &lt;element name="groupId" type="{http://www.w3.org/2001/XMLSchema}int" maxOccurs="unbounded"/>
 *         &lt;element name="containerCode" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="shipFileSymbol" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrintItemList", propOrder = {
    "msn",
    "bundleId",
    "groupId",
    "containerCode",
    "shipFileSymbol"
})
public class PrintItemList {

    @XmlElement(type = Integer.class)
    protected List<Integer> msn;
    @XmlElement(type = Integer.class)
    protected List<Integer> bundleId;
    @XmlElement(type = Integer.class)
    protected List<Integer> groupId;
    protected List<String> containerCode;
    protected List<String> shipFileSymbol;

    /**
     * Gets the value of the msn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the msn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMsn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getMsn() {
        if (msn == null) {
            msn = new ArrayList<Integer>();
        }
        return this.msn;
    }

    /**
     * Gets the value of the bundleId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bundleId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBundleId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getBundleId() {
        if (bundleId == null) {
            bundleId = new ArrayList<Integer>();
        }
        return this.bundleId;
    }

    /**
     * Gets the value of the groupId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getGroupId() {
        if (groupId == null) {
            groupId = new ArrayList<Integer>();
        }
        return this.groupId;
    }

    /**
     * Gets the value of the containerCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the containerCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContainerCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getContainerCode() {
        if (containerCode == null) {
            containerCode = new ArrayList<String>();
        }
        return this.containerCode;
    }

    /**
     * Gets the value of the shipFileSymbol property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipFileSymbol property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShipFileSymbol().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getShipFileSymbol() {
        if (shipFileSymbol == null) {
            shipFileSymbol = new ArrayList<String>();
        }
        return this.shipFileSymbol;
    }

}
