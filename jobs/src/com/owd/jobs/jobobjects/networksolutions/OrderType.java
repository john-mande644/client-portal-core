
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for OrderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="AdminNotes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Archived" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SalesRep" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AffiliateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReferringURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Customer" type="{urn:networksolutions:apis}CustomerType" minOccurs="0"/>
 *         &lt;element name="Invoice" type="{urn:networksolutions:apis}InvoiceType" minOccurs="0"/>
 *         &lt;element name="Payment" type="{urn:networksolutions:apis}PaymentType" minOccurs="0"/>
 *         &lt;element name="Shipping" type="{urn:networksolutions:apis}ShippingType" minOccurs="0"/>
 *         &lt;element name="Status" type="{urn:networksolutions:apis}OrderStatusType" minOccurs="0"/>
 *         &lt;element name="QuestionList" type="{urn:networksolutions:apis}QuestionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="OrderId" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="OrderNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderType", propOrder = {
    "adminNotes",
    "archived",
    "createDate",
    "notes",
    "salesRep",
    "affiliateCode",
    "referringURL",
    "customer",
    "invoice",
    "payment",
    "shipping",
    "status",
    "questionList"
})
public class OrderType {

    @XmlElement(name = "AdminNotes")
    protected String adminNotes;
    @XmlElement(name = "Archived")
    protected Boolean archived;
    @XmlElement(name = "CreateDate")
    protected XMLGregorianCalendar createDate;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "SalesRep")
    protected String salesRep;
    @XmlElement(name = "AffiliateCode")
    protected String affiliateCode;
    @XmlElement(name = "ReferringURL")
    protected String referringURL;
    @XmlElement(name = "Customer")
    protected CustomerType customer;
    @XmlElement(name = "Invoice")
    protected InvoiceType invoice;
    @XmlElement(name = "Payment")
    protected PaymentType payment;
    @XmlElement(name = "Shipping")
    protected ShippingType shipping;
    @XmlElement(name = "Status")
    protected OrderStatusType status;
    @XmlElement(name = "QuestionList")
    protected List<QuestionType> questionList;
    @XmlAttribute(name = "OrderId")
    protected Long orderId;
    @XmlAttribute(name = "OrderNumber")
    protected String orderNumber;

    /**
     * Gets the value of the adminNotes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdminNotes() {
        return adminNotes;
    }

    /**
     * Sets the value of the adminNotes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdminNotes(String value) {
        this.adminNotes = value;
    }

    /**
     * Gets the value of the archived property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isArchived() {
        return archived;
    }

    /**
     * Sets the value of the archived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setArchived(Boolean value) {
        this.archived = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDate(XMLGregorianCalendar value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the salesRep property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesRep() {
        return salesRep;
    }

    /**
     * Sets the value of the salesRep property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesRep(String value) {
        this.salesRep = value;
    }

    /**
     * Gets the value of the affiliateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAffiliateCode() {
        return affiliateCode;
    }

    /**
     * Sets the value of the affiliateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAffiliateCode(String value) {
        this.affiliateCode = value;
    }

    /**
     * Gets the value of the referringURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferringURL() {
        return referringURL;
    }

    /**
     * Sets the value of the referringURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferringURL(String value) {
        this.referringURL = value;
    }

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
     * Gets the value of the invoice property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceType }
     *     
     */
    public InvoiceType getInvoice() {
        return invoice;
    }

    /**
     * Sets the value of the invoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceType }
     *     
     */
    public void setInvoice(InvoiceType value) {
        this.invoice = value;
    }

    /**
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentType }
     *     
     */
    public PaymentType getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentType }
     *     
     */
    public void setPayment(PaymentType value) {
        this.payment = value;
    }

    /**
     * Gets the value of the shipping property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingType }
     *     
     */
    public ShippingType getShipping() {
        return shipping;
    }

    /**
     * Sets the value of the shipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingType }
     *     
     */
    public void setShipping(ShippingType value) {
        this.shipping = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OrderStatusType }
     *     
     */
    public OrderStatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderStatusType }
     *     
     */
    public void setStatus(OrderStatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the questionList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the questionList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuestionList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QuestionType }
     * 
     * 
     */
    public List<QuestionType> getQuestionList() {
        if (questionList == null) {
            questionList = new ArrayList<QuestionType>();
        }
        return this.questionList;
    }

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setOrderId(Long value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the orderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }

}
