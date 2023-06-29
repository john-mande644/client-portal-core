
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for OrderStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailCustomer" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EmailAdministrator" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Parent" type="{urn:networksolutions:apis}OrderStatusType" minOccurs="0"/>
 *         &lt;element name="NextStatusList" type="{urn:networksolutions:apis}OrderStatusType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="OrderStatusId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderStatusType", propOrder = {
    "name",
    "emailCustomer",
    "emailAdministrator",
    "parent",
    "nextStatusList"
})
public class OrderStatusType {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "EmailCustomer")
    protected Boolean emailCustomer;
    @XmlElement(name = "EmailAdministrator")
    protected Boolean emailAdministrator;
    @XmlElement(name = "Parent")
    protected OrderStatusType parent;
    @XmlElement(name = "NextStatusList")
    protected List<OrderStatusType> nextStatusList;
    @XmlAttribute(name = "OrderStatusId")
    protected Long orderStatusId;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the emailCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEmailCustomer() {
        return emailCustomer;
    }

    /**
     * Sets the value of the emailCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEmailCustomer(Boolean value) {
        this.emailCustomer = value;
    }

    /**
     * Gets the value of the emailAdministrator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEmailAdministrator() {
        return emailAdministrator;
    }

    /**
     * Sets the value of the emailAdministrator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEmailAdministrator(Boolean value) {
        this.emailAdministrator = value;
    }

    /**
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link OrderStatusType }
     *     
     */
    public OrderStatusType getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderStatusType }
     *     
     */
    public void setParent(OrderStatusType value) {
        this.parent = value;
    }

    /**
     * Gets the value of the nextStatusList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nextStatusList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNextStatusList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderStatusType }
     * 
     * 
     */
    public List<OrderStatusType> getNextStatusList() {
        if (nextStatusList == null) {
            nextStatusList = new ArrayList<OrderStatusType>();
        }
        return this.nextStatusList;
    }

    /**
     * Gets the value of the orderStatusId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOrderStatusId() {
        return orderStatusId;
    }

    /**
     * Sets the value of the orderStatusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOrderStatusId(Long value) {
        this.orderStatusId = value;
    }

}
