
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for CreateCustomerRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateCustomerRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Customer" type="{urn:networksolutions:apis}CustomerType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="emailCustomer" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateCustomerRequestType", propOrder = {
    "customer"
})
public class CreateCustomerRequestType
    extends BaseRequestType
{

    @XmlElement(name = "Customer")
    protected CustomerType customer;
    @XmlAttribute
    protected Boolean emailCustomer;

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerType }
     *     
     */
    public CustomerType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerType }
     *     
     */
    public void setCustomer(CustomerType value) {
        this.customer = value;
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

}
