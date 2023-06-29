
package com.owd.jobs.jobobjects.commercehub.invoicing;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="partnerID">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="roleType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="hubInvoice" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="participatingParty" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="roleType" default="merchant">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="merchant"/>
 *                                 &lt;enumeration value="vendor"/>
 *                                 &lt;enumeration value="shipper"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                           &lt;attribute name="participationCode" default="To:">
 *                             &lt;simpleType>
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                 &lt;enumeration value="Cc:"/>
 *                                 &lt;enumeration value="To:"/>
 *                                 &lt;enumeration value="From:"/>
 *                               &lt;/restriction>
 *                             &lt;/simpleType>
 *                           &lt;/attribute>
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="partnerTrxID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="partnerTrxDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="poNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="trxShipping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="trxHandling" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="trxTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="trxCredits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="trxBalanceDue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="trxData" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{}creditBreakout" maxOccurs="unbounded" minOccurs="0"/>
 *                             &lt;element ref="{}discountBreakout" maxOccurs="unbounded"/>
 *                             &lt;element ref="{}miscChargeBreakout" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="hubAction" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="action">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;enumeration value="v_invoice"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="merchantLineNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="trxQty" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="trxUnitCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="CARBformaldehydeComplianceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="invoiceDetailLink">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="invoiceDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="packageDetailLink" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                                     &lt;attribute name="packageDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="invoiceDetail" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="remitTo">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="personPlaceID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="invoiceDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="packageDetail" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="shipDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="serviceLevel1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="trackingNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="packageDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="personPlace" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="partnerPersonPlaceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                           &lt;attribute name="personPlaceID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="messageCount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "partnerID",
    "hubInvoice",
    "messageCount"
})
@XmlRootElement(name = "InvoiceMessageBatch")
public class InvoiceMessageBatch {

    @XmlElement(required = true)
    protected InvoiceMessageBatch.PartnerID partnerID;
    @XmlElement(required = true)
    protected List<InvoiceMessageBatch.HubInvoice> hubInvoice;
    @XmlElement(required = true)
    protected String messageCount;

    /**
     * Gets the value of the partnerID property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceMessageBatch.PartnerID }
     *     
     */
    public InvoiceMessageBatch.PartnerID getPartnerID() {
        return partnerID;
    }

    /**
     * Sets the value of the partnerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceMessageBatch.PartnerID }
     *     
     */
    public void setPartnerID(InvoiceMessageBatch.PartnerID value) {
        this.partnerID = value;
    }

    /**
     * Gets the value of the hubInvoice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hubInvoice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHubInvoice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InvoiceMessageBatch.HubInvoice }
     * 
     * 
     */
    public List<InvoiceMessageBatch.HubInvoice> getHubInvoice() {
        if (hubInvoice == null) {
            hubInvoice = new ArrayList<InvoiceMessageBatch.HubInvoice>();
        }
        return this.hubInvoice;
    }

    /**
     * Gets the value of the messageCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageCount() {
        return messageCount;
    }

    /**
     * Sets the value of the messageCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageCount(String value) {
        this.messageCount = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="participatingParty" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="roleType" default="merchant">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="merchant"/>
     *                       &lt;enumeration value="vendor"/>
     *                       &lt;enumeration value="shipper"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *                 &lt;attribute name="participationCode" default="To:">
     *                   &lt;simpleType>
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                       &lt;enumeration value="Cc:"/>
     *                       &lt;enumeration value="To:"/>
     *                       &lt;enumeration value="From:"/>
     *                     &lt;/restriction>
     *                   &lt;/simpleType>
     *                 &lt;/attribute>
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="partnerTrxID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="partnerTrxDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="poNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="trxShipping" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="trxHandling" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="trxTax" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="trxCredits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="trxBalanceDue" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="trxData" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{}creditBreakout" maxOccurs="unbounded" minOccurs="0"/>
     *                   &lt;element ref="{}discountBreakout" maxOccurs="unbounded"/>
     *                   &lt;element ref="{}miscChargeBreakout" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="hubAction" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="action">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;enumeration value="v_invoice"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="merchantLineNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="trxQty" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="trxUnitCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="CARBformaldehydeComplianceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="invoiceDetailLink">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="invoiceDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="packageDetailLink" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                           &lt;attribute name="packageDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="invoiceDetail" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="remitTo">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="personPlaceID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="invoiceDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="packageDetail" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="shipDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="serviceLevel1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="trackingNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="packageDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="personPlace" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="partnerPersonPlaceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *                 &lt;attribute name="personPlaceID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "participatingParty",
        "partnerTrxID",
        "partnerTrxDate",
        "poNumber",
        "trxShipping",
        "trxHandling",
        "trxTax",
        "trxCredits",
        "trxBalanceDue",
        "trxData",
        "hubAction",
        "invoiceDetail",
        "packageDetail",
        "personPlace"
    })
    public static class HubInvoice {

        @XmlElement(required = true)
        protected List<InvoiceMessageBatch.HubInvoice.ParticipatingParty> participatingParty;
        @XmlElement(required = true)
        protected String partnerTrxID;
        @XmlElement(required = true)
        protected String partnerTrxDate;
        @XmlElement(required = true)
        protected String poNumber;
        protected String trxShipping;
        protected String trxHandling;
        protected String trxTax;
        protected String trxCredits;
        @XmlElement(required = true)
        protected String trxBalanceDue;
        protected InvoiceMessageBatch.HubInvoice.TrxData trxData;
        @XmlElement(required = true)
        protected List<InvoiceMessageBatch.HubInvoice.HubAction> hubAction;
        @XmlElement(required = true)
        protected List<InvoiceMessageBatch.HubInvoice.InvoiceDetail> invoiceDetail;
        @XmlElement(required = true)
        protected List<InvoiceMessageBatch.HubInvoice.PackageDetail> packageDetail;
        @XmlElement(required = true)
        protected List<InvoiceMessageBatch.HubInvoice.PersonPlace> personPlace;

        /**
         * Gets the value of the participatingParty property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the participatingParty property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParticipatingParty().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InvoiceMessageBatch.HubInvoice.ParticipatingParty }
         * 
         * 
         */
        public List<InvoiceMessageBatch.HubInvoice.ParticipatingParty> getParticipatingParty() {
            if (participatingParty == null) {
                participatingParty = new ArrayList<InvoiceMessageBatch.HubInvoice.ParticipatingParty>();
            }
            return this.participatingParty;
        }

        /**
         * Gets the value of the partnerTrxID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPartnerTrxID() {
            return partnerTrxID;
        }

        /**
         * Sets the value of the partnerTrxID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPartnerTrxID(String value) {
            this.partnerTrxID = value;
        }

        /**
         * Gets the value of the partnerTrxDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPartnerTrxDate() {
            return partnerTrxDate;
        }

        /**
         * Sets the value of the partnerTrxDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPartnerTrxDate(String value) {
            this.partnerTrxDate = value;
        }

        /**
         * Gets the value of the poNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPoNumber() {
            return poNumber;
        }

        /**
         * Sets the value of the poNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPoNumber(String value) {
            this.poNumber = value;
        }

        /**
         * Gets the value of the trxShipping property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTrxShipping() {
            return trxShipping;
        }

        /**
         * Sets the value of the trxShipping property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTrxShipping(String value) {
            this.trxShipping = value;
        }

        /**
         * Gets the value of the trxHandling property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTrxHandling() {
            return trxHandling;
        }

        /**
         * Sets the value of the trxHandling property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTrxHandling(String value) {
            this.trxHandling = value;
        }

        /**
         * Gets the value of the trxTax property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTrxTax() {
            return trxTax;
        }

        /**
         * Sets the value of the trxTax property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTrxTax(String value) {
            this.trxTax = value;
        }

        /**
         * Gets the value of the trxCredits property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTrxCredits() {
            return trxCredits;
        }

        /**
         * Sets the value of the trxCredits property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTrxCredits(String value) {
            this.trxCredits = value;
        }

        /**
         * Gets the value of the trxBalanceDue property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTrxBalanceDue() {
            return trxBalanceDue;
        }

        /**
         * Sets the value of the trxBalanceDue property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTrxBalanceDue(String value) {
            this.trxBalanceDue = value;
        }

        /**
         * Gets the value of the trxData property.
         * 
         * @return
         *     possible object is
         *     {@link InvoiceMessageBatch.HubInvoice.TrxData }
         *     
         */
        public InvoiceMessageBatch.HubInvoice.TrxData getTrxData() {
            return trxData;
        }

        /**
         * Sets the value of the trxData property.
         * 
         * @param value
         *     allowed object is
         *     {@link InvoiceMessageBatch.HubInvoice.TrxData }
         *     
         */
        public void setTrxData(InvoiceMessageBatch.HubInvoice.TrxData value) {
            this.trxData = value;
        }

        /**
         * Gets the value of the hubAction property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hubAction property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHubAction().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InvoiceMessageBatch.HubInvoice.HubAction }
         * 
         * 
         */
        public List<InvoiceMessageBatch.HubInvoice.HubAction> getHubAction() {
            if (hubAction == null) {
                hubAction = new ArrayList<InvoiceMessageBatch.HubInvoice.HubAction>();
            }
            return this.hubAction;
        }

        /**
         * Gets the value of the invoiceDetail property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the invoiceDetail property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInvoiceDetail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InvoiceMessageBatch.HubInvoice.InvoiceDetail }
         * 
         * 
         */
        public List<InvoiceMessageBatch.HubInvoice.InvoiceDetail> getInvoiceDetail() {
            if (invoiceDetail == null) {
                invoiceDetail = new ArrayList<InvoiceMessageBatch.HubInvoice.InvoiceDetail>();
            }
            return this.invoiceDetail;
        }

        /**
         * Gets the value of the packageDetail property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the packageDetail property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPackageDetail().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InvoiceMessageBatch.HubInvoice.PackageDetail }
         * 
         * 
         */
        public List<InvoiceMessageBatch.HubInvoice.PackageDetail> getPackageDetail() {
            if (packageDetail == null) {
                packageDetail = new ArrayList<InvoiceMessageBatch.HubInvoice.PackageDetail>();
            }
            return this.packageDetail;
        }

        /**
         * Gets the value of the personPlace property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the personPlace property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPersonPlace().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InvoiceMessageBatch.HubInvoice.PersonPlace }
         * 
         * 
         */
        public List<InvoiceMessageBatch.HubInvoice.PersonPlace> getPersonPlace() {
            if (personPlace == null) {
                personPlace = new ArrayList<InvoiceMessageBatch.HubInvoice.PersonPlace>();
            }
            return this.personPlace;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="action">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;enumeration value="v_invoice"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="merchantLineNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="trxQty" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="trxUnitCost" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="CARBformaldehydeComplianceCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="invoiceDetailLink">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="invoiceDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="packageDetailLink" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *                 &lt;attribute name="packageDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "action",
            "merchantLineNumber",
            "trxQty",
            "trxUnitCost",
            "carBformaldehydeComplianceCode",
            "invoiceDetailLink",
            "packageDetailLink"
        })
        public static class HubAction {

            @XmlElement(required = true, defaultValue = "v_invoice")
            protected String action;
            @XmlElement(required = true)
            protected String merchantLineNumber;
            @XmlElement(required = true)
            protected String trxQty;
            @XmlElement(required = true)
            protected String trxUnitCost;
            @XmlElement(name = "CARBformaldehydeComplianceCode")
            protected String carBformaldehydeComplianceCode;
            @XmlElement(required = true)
            protected InvoiceMessageBatch.HubInvoice.HubAction.InvoiceDetailLink invoiceDetailLink;
            @XmlElement(required = true)
            protected List<InvoiceMessageBatch.HubInvoice.HubAction.PackageDetailLink> packageDetailLink;

            /**
             * Gets the value of the action property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAction() {
                return action;
            }

            /**
             * Sets the value of the action property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAction(String value) {
                this.action = value;
            }

            /**
             * Gets the value of the merchantLineNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMerchantLineNumber() {
                return merchantLineNumber;
            }

            /**
             * Sets the value of the merchantLineNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMerchantLineNumber(String value) {
                this.merchantLineNumber = value;
            }

            /**
             * Gets the value of the trxQty property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTrxQty() {
                return trxQty;
            }

            /**
             * Sets the value of the trxQty property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTrxQty(String value) {
                this.trxQty = value;
            }

            /**
             * Gets the value of the trxUnitCost property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTrxUnitCost() {
                return trxUnitCost;
            }

            /**
             * Sets the value of the trxUnitCost property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTrxUnitCost(String value) {
                this.trxUnitCost = value;
            }

            /**
             * Gets the value of the carBformaldehydeComplianceCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCARBformaldehydeComplianceCode() {
                return carBformaldehydeComplianceCode;
            }

            /**
             * Sets the value of the carBformaldehydeComplianceCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCARBformaldehydeComplianceCode(String value) {
                this.carBformaldehydeComplianceCode = value;
            }

            /**
             * Gets the value of the invoiceDetailLink property.
             * 
             * @return
             *     possible object is
             *     {@link InvoiceMessageBatch.HubInvoice.HubAction.InvoiceDetailLink }
             *     
             */
            public InvoiceMessageBatch.HubInvoice.HubAction.InvoiceDetailLink getInvoiceDetailLink() {
                return invoiceDetailLink;
            }

            /**
             * Sets the value of the invoiceDetailLink property.
             * 
             * @param value
             *     allowed object is
             *     {@link InvoiceMessageBatch.HubInvoice.HubAction.InvoiceDetailLink }
             *     
             */
            public void setInvoiceDetailLink(InvoiceMessageBatch.HubInvoice.HubAction.InvoiceDetailLink value) {
                this.invoiceDetailLink = value;
            }

            /**
             * Gets the value of the packageDetailLink property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the packageDetailLink property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPackageDetailLink().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link InvoiceMessageBatch.HubInvoice.HubAction.PackageDetailLink }
             * 
             * 
             */
            public List<InvoiceMessageBatch.HubInvoice.HubAction.PackageDetailLink> getPackageDetailLink() {
                if (packageDetailLink == null) {
                    packageDetailLink = new ArrayList<InvoiceMessageBatch.HubInvoice.HubAction.PackageDetailLink>();
                }
                return this.packageDetailLink;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="invoiceDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "content"
            })
            public static class InvoiceDetailLink {

                @XmlValue
                protected String content;
                @XmlAttribute(name = "invoiceDetailID", required = true)
                @XmlIDREF
                @XmlSchemaType(name = "IDREF")
                protected Object invoiceDetailID;

                /**
                 * Gets the value of the content property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getContent() {
                    return content;
                }

                /**
                 * Sets the value of the content property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setContent(String value) {
                    this.content = value;
                }

                /**
                 * Gets the value of the invoiceDetailID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getInvoiceDetailID() {
                    return invoiceDetailID;
                }

                /**
                 * Sets the value of the invoiceDetailID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setInvoiceDetailID(Object value) {
                    this.invoiceDetailID = value;
                }

            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
             *       &lt;attribute name="packageDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class PackageDetailLink {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "packageDetailID", required = true)
                @XmlIDREF
                @XmlSchemaType(name = "IDREF")
                protected Object packageDetailID;

                /**
                 * Gets the value of the value property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the packageDetailID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getPackageDetailID() {
                    return packageDetailID;
                }

                /**
                 * Sets the value of the packageDetailID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setPackageDetailID(Object value) {
                    this.packageDetailID = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="remitTo">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="personPlaceID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="invoiceDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "remitTo"
        })
        public static class InvoiceDetail {

            @XmlElement(required = true)
            protected InvoiceMessageBatch.HubInvoice.InvoiceDetail.RemitTo remitTo;
            @XmlAttribute(name = "invoiceDetailID", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlID
            @XmlSchemaType(name = "ID")
            protected String invoiceDetailID;

            /**
             * Gets the value of the remitTo property.
             * 
             * @return
             *     possible object is
             *     {@link InvoiceMessageBatch.HubInvoice.InvoiceDetail.RemitTo }
             *     
             */
            public InvoiceMessageBatch.HubInvoice.InvoiceDetail.RemitTo getRemitTo() {
                return remitTo;
            }

            /**
             * Sets the value of the remitTo property.
             * 
             * @param value
             *     allowed object is
             *     {@link InvoiceMessageBatch.HubInvoice.InvoiceDetail.RemitTo }
             *     
             */
            public void setRemitTo(InvoiceMessageBatch.HubInvoice.InvoiceDetail.RemitTo value) {
                this.remitTo = value;
            }

            /**
             * Gets the value of the invoiceDetailID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvoiceDetailID() {
                return invoiceDetailID;
            }

            /**
             * Sets the value of the invoiceDetailID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvoiceDetailID(String value) {
                this.invoiceDetailID = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="personPlaceID" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class RemitTo {

                @XmlAttribute(name = "personPlaceID", required = true)
                @XmlIDREF
                @XmlSchemaType(name = "IDREF")
                protected Object personPlaceID;

                /**
                 * Gets the value of the personPlaceID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getPersonPlaceID() {
                    return personPlaceID;
                }

                /**
                 * Sets the value of the personPlaceID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setPersonPlaceID(Object value) {
                    this.personPlaceID = value;
                }

            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="shipDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="serviceLevel1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="trackingNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="packageDetailID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "shipDate",
            "serviceLevel1",
            "trackingNumber"
        })
        public static class PackageDetail {

            @XmlElement(required = true)
            protected String shipDate;
            @XmlElement(required = true)
            protected String serviceLevel1;
            @XmlElement(required = true)
            protected String trackingNumber;
            @XmlAttribute(name = "packageDetailID", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlID
            @XmlSchemaType(name = "ID")
            protected String packageDetailID;

            /**
             * Gets the value of the shipDate property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getShipDate() {
                return shipDate;
            }

            /**
             * Sets the value of the shipDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setShipDate(String value) {
                this.shipDate = value;
            }

            /**
             * Gets the value of the serviceLevel1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getServiceLevel1() {
                return serviceLevel1;
            }

            /**
             * Sets the value of the serviceLevel1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setServiceLevel1(String value) {
                this.serviceLevel1 = value;
            }

            /**
             * Gets the value of the trackingNumber property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTrackingNumber() {
                return trackingNumber;
            }

            /**
             * Sets the value of the trackingNumber property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTrackingNumber(String value) {
                this.trackingNumber = value;
            }

            /**
             * Gets the value of the packageDetailID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPackageDetailID() {
                return packageDetailID;
            }

            /**
             * Sets the value of the packageDetailID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPackageDetailID(String value) {
                this.packageDetailID = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="roleType" default="merchant">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="merchant"/>
         *             &lt;enumeration value="vendor"/>
         *             &lt;enumeration value="shipper"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *       &lt;attribute name="participationCode" default="To:">
         *         &lt;simpleType>
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *             &lt;enumeration value="Cc:"/>
         *             &lt;enumeration value="To:"/>
         *             &lt;enumeration value="From:"/>
         *           &lt;/restriction>
         *         &lt;/simpleType>
         *       &lt;/attribute>
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class ParticipatingParty {

            @XmlValue
            protected String value;
            @XmlAttribute(name = "name", required = true)
            protected String name;
            @XmlAttribute(name = "roleType")
            protected String roleType;
            @XmlAttribute(name = "participationCode")
            protected String participationCode;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

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
             * Gets the value of the roleType property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRoleType() {
                if (roleType == null) {
                    return "merchant";
                } else {
                    return roleType;
                }
            }

            /**
             * Sets the value of the roleType property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRoleType(String value) {
                this.roleType = value;
            }

            /**
             * Gets the value of the participationCode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getParticipationCode() {
                if (participationCode == null) {
                    return "To:";
                } else {
                    return participationCode;
                }
            }

            /**
             * Sets the value of the participationCode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setParticipationCode(String value) {
                this.participationCode = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="partnerPersonPlaceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *       &lt;attribute name="personPlaceID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "partnerPersonPlaceId"
        })
        public static class PersonPlace {

            @XmlElement(required = true)
            protected String partnerPersonPlaceId;
            @XmlAttribute(name = "personPlaceID", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlID
            @XmlSchemaType(name = "ID")
            protected String personPlaceID;

            /**
             * Gets the value of the partnerPersonPlaceId property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPartnerPersonPlaceId() {
                return partnerPersonPlaceId;
            }

            /**
             * Sets the value of the partnerPersonPlaceId property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPartnerPersonPlaceId(String value) {
                this.partnerPersonPlaceId = value;
            }

            /**
             * Gets the value of the personPlaceID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPersonPlaceID() {
                return personPlaceID;
            }

            /**
             * Sets the value of the personPlaceID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPersonPlaceID(String value) {
                this.personPlaceID = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element ref="{}creditBreakout" maxOccurs="unbounded" minOccurs="0"/>
         *         &lt;element ref="{}discountBreakout" maxOccurs="unbounded"/>
         *         &lt;element ref="{}miscChargeBreakout" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "creditBreakout",
            "discountBreakout",
            "miscChargeBreakout"
        })
        public static class TrxData {

            protected List<CreditBreakout> creditBreakout;
            @XmlElement(required = true)
            protected List<DiscountBreakout> discountBreakout;
            protected List<MiscChargeBreakout> miscChargeBreakout;

            /**
             * Breakout element for credits.  Content of the element represents a sub-component of the trxCredits element.   This element also carries attributes that can be used to characterize the credit sub-component.
             * 
             * The collection of breakout elements should sum to match the amount in the trxCredits element that is a direct child of hubInvoice.Gets the value of the creditBreakout property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the creditBreakout property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCreditBreakout().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link CreditBreakout }
             * 
             * 
             */
            public List<CreditBreakout> getCreditBreakout() {
                if (creditBreakout == null) {
                    creditBreakout = new ArrayList<CreditBreakout>();
                }
                return this.creditBreakout;
            }

            /**
             * Use to provide payment terms and details regarding payment related discount opportunities.  The attributes of this element stipulate the discount calculation and qualifying factors that are in effect.Gets the value of the discountBreakout property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the discountBreakout property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDiscountBreakout().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link DiscountBreakout }
             * 
             * 
             */
            public List<DiscountBreakout> getDiscountBreakout() {
                if (discountBreakout == null) {
                    discountBreakout = new ArrayList<DiscountBreakout>();
                }
                return this.discountBreakout;
            }

            /**
             * Breakout element for miscellaneous charges.   Content of the element represents a sub-component of the amount in the trxMiscCharges element.  This element also carries attributes that can be used to describe the miscellaneous charge sub-component.
             * 
             * The collection of breakout elements should sum to match the amount in the trxMiscCharges element that is a direct child of hubInvoice.Gets the value of the miscChargeBreakout property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the miscChargeBreakout property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getMiscChargeBreakout().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link MiscChargeBreakout }
             * 
             * 
             */
            public List<MiscChargeBreakout> getMiscChargeBreakout() {
                if (miscChargeBreakout == null) {
                    miscChargeBreakout = new ArrayList<MiscChargeBreakout>();
                }
                return this.miscChargeBreakout;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="roleType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class PartnerID {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "roleType", required = true)
        protected String roleType;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

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
         * Gets the value of the roleType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRoleType() {
            return roleType;
        }

        /**
         * Sets the value of the roleType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRoleType(String value) {
            this.roleType = value;
        }

    }

}
