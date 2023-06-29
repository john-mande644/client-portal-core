
package com.owd.dc.manifest.ExternalApis.PurolatorUtils.Livingston;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="SHIPMENT" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SHIPMENT_HEADER">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="CCINo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="AltShpmntNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ETADate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="ETAHour" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="InvoiceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="Port" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="InvQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="InvUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BOL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DirectShipDt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Broker" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="ModeTransport" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="UnitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="NetWeight" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="GrossWeight" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="PayTerms1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="PayTerms2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Royalty" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="CanadianContent" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="InvTotal" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="RefPO1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="RefPO2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="PlaceDirShpmt" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="CntryExport" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DeptRuling" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="CargoCtl" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="ContainerNo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                             &lt;element name="CartonCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="PARS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="SHIPMENT_ADDRESS">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="VENDOR_ADDRESS">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="VendName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="VendAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="VendAddress2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="VendAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="VendCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="VendState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="VendCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="VendZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="VendAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="EXPORTER_ADDRESS">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ExpName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ExpAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ExpAddress2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ExpAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="ExpCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ExpState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ExpCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ExpZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ExpAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="PURCHASER_ADDRESS">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="PurchName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="PurchAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="PurchAddress2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="PurchAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="PurchCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="PurchState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="PurchCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="PurchZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="PurchAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="CONSIGNEE_ADDRESS">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ConsName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ConsAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ConsAddress2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="ConsAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="ConsCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ConsState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ConsCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ConsZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ConsAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="SHIPMENT_COMMENT">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="SHIPMENT_DETAIL">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ORDER" maxOccurs="unbounded">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="PartNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="PartName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="UnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="Qty" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                                       &lt;element name="Uom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="ContainerNo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="DiscountDesc" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="DiscountAmt" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="ExtendPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                       &lt;element name="RefPONo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="CountryOrigin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                       &lt;element name="QtyQualifier" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="HSNumber" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="TUom" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                       &lt;element name="TQty" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
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
    "shipment"
})
@XmlRootElement(name = "SHIPMENTS")
public class SHIPMENTS {

    @XmlElement(name = "SHIPMENT", required = true)
    protected List<SHIPMENTS.SHIPMENT> shipment;

    /**
     * Gets the value of the shipment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shipment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSHIPMENT().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SHIPMENTS.SHIPMENT }
     * 
     * 
     */
    public List<SHIPMENTS.SHIPMENT> getSHIPMENT() {
        if (shipment == null) {
            shipment = new ArrayList<SHIPMENTS.SHIPMENT>();
        }
        return this.shipment;
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
     *         &lt;element name="SHIPMENT_HEADER">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="CCINo" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="AltShpmntNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ETADate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="ETAHour" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="InvoiceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="Port" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="InvQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="InvUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="BOL" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="DirectShipDt" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Broker" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="ModeTransport" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="UnitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="NetWeight" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="GrossWeight" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="PayTerms1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="PayTerms2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Royalty" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="CanadianContent" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="InvTotal" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="RefPO1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="RefPO2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="PlaceDirShpmt" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="CntryExport" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="DeptRuling" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="CargoCtl" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="ContainerNo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                   &lt;element name="CartonCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="PARS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SHIPMENT_ADDRESS">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="VENDOR_ADDRESS">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="VendName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VendAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VendAddress2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VendAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="VendCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VendState" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VendCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VendZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="VendAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="EXPORTER_ADDRESS">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ExpName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ExpAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ExpAddress2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ExpAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="ExpCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ExpState" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ExpCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ExpZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ExpAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="PURCHASER_ADDRESS">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="PurchName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="PurchAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="PurchAddress2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="PurchAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="PurchCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="PurchState" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="PurchCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="PurchZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="PurchAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="CONSIGNEE_ADDRESS">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ConsName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ConsAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ConsAddress2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="ConsAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="ConsCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ConsState" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ConsCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ConsZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ConsAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SHIPMENT_COMMENT">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="SHIPMENT_DETAIL">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ORDER" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="PartNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="PartName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="UnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="Qty" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                             &lt;element name="Uom" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="ContainerNo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="DiscountDesc" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="DiscountAmt" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="ExtendPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                             &lt;element name="RefPONo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="CountryOrigin" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="QtyQualifier" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="HSNumber" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="TUom" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                             &lt;element name="TQty" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
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
        "shipmentheader",
        "shipmentaddress",
        "shipmentcomment",
        "shipmentdetail"
    })
    public static class SHIPMENT {

        @XmlElement(name = "SHIPMENT_HEADER", required = true)
        protected SHIPMENTS.SHIPMENT.SHIPMENTHEADER shipmentheader;
        @XmlElement(name = "SHIPMENT_ADDRESS", required = true)
        protected SHIPMENTS.SHIPMENT.SHIPMENTADDRESS shipmentaddress;
        @XmlElement(name = "SHIPMENT_COMMENT", required = true)
        protected SHIPMENTS.SHIPMENT.SHIPMENTCOMMENT shipmentcomment;
        @XmlElement(name = "SHIPMENT_DETAIL", required = true)
        protected SHIPMENTS.SHIPMENT.SHIPMENTDETAIL shipmentdetail;

        /**
         * Gets the value of the shipmentheader property.
         * 
         * @return
         *     possible object is
         *     {@link SHIPMENTS.SHIPMENT.SHIPMENTHEADER }
         *     
         */
        public SHIPMENTS.SHIPMENT.SHIPMENTHEADER getSHIPMENTHEADER() {
            return shipmentheader;
        }

        /**
         * Sets the value of the shipmentheader property.
         * 
         * @param value
         *     allowed object is
         *     {@link SHIPMENTS.SHIPMENT.SHIPMENTHEADER }
         *     
         */
        public void setSHIPMENTHEADER(SHIPMENTS.SHIPMENT.SHIPMENTHEADER value) {
            this.shipmentheader = value;
        }

        /**
         * Gets the value of the shipmentaddress property.
         * 
         * @return
         *     possible object is
         *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS }
         *     
         */
        public SHIPMENTS.SHIPMENT.SHIPMENTADDRESS getSHIPMENTADDRESS() {
            return shipmentaddress;
        }

        /**
         * Sets the value of the shipmentaddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS }
         *     
         */
        public void setSHIPMENTADDRESS(SHIPMENTS.SHIPMENT.SHIPMENTADDRESS value) {
            this.shipmentaddress = value;
        }

        /**
         * Gets the value of the shipmentcomment property.
         * 
         * @return
         *     possible object is
         *     {@link SHIPMENTS.SHIPMENT.SHIPMENTCOMMENT }
         *     
         */
        public SHIPMENTS.SHIPMENT.SHIPMENTCOMMENT getSHIPMENTCOMMENT() {
            return shipmentcomment;
        }

        /**
         * Sets the value of the shipmentcomment property.
         * 
         * @param value
         *     allowed object is
         *     {@link SHIPMENTS.SHIPMENT.SHIPMENTCOMMENT }
         *     
         */
        public void setSHIPMENTCOMMENT(SHIPMENTS.SHIPMENT.SHIPMENTCOMMENT value) {
            this.shipmentcomment = value;
        }

        /**
         * Gets the value of the shipmentdetail property.
         * 
         * @return
         *     possible object is
         *     {@link SHIPMENTS.SHIPMENT.SHIPMENTDETAIL }
         *     
         */
        public SHIPMENTS.SHIPMENT.SHIPMENTDETAIL getSHIPMENTDETAIL() {
            return shipmentdetail;
        }

        /**
         * Sets the value of the shipmentdetail property.
         * 
         * @param value
         *     allowed object is
         *     {@link SHIPMENTS.SHIPMENT.SHIPMENTDETAIL }
         *     
         */
        public void setSHIPMENTDETAIL(SHIPMENTS.SHIPMENT.SHIPMENTDETAIL value) {
            this.shipmentdetail = value;
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
         *         &lt;element name="VENDOR_ADDRESS">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="VendName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="VendAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="VendAddress2" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="VendAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="VendCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="VendState" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="VendCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="VendZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="VendAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="EXPORTER_ADDRESS">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ExpName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ExpAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ExpAddress2" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ExpAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="ExpCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ExpState" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ExpCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ExpZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ExpAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="PURCHASER_ADDRESS">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="PurchName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="PurchAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="PurchAddress2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="PurchAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="PurchCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="PurchState" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="PurchCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="PurchZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="PurchAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="CONSIGNEE_ADDRESS">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ConsName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ConsAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ConsAddress2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="ConsAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="ConsCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ConsState" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ConsCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ConsZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ConsAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                 &lt;/sequence>
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
            "vendoraddress",
            "exporteraddress",
            "purchaseraddress",
            "consigneeaddress"
        })
        public static class SHIPMENTADDRESS {

            @XmlElement(name = "VENDOR_ADDRESS", required = true)
            protected SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.VENDORADDRESS vendoraddress;
            @XmlElement(name = "EXPORTER_ADDRESS", required = true)
            protected SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.EXPORTERADDRESS exporteraddress;
            @XmlElement(name = "PURCHASER_ADDRESS", required = true)
            protected SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.PURCHASERADDRESS purchaseraddress;
            @XmlElement(name = "CONSIGNEE_ADDRESS", required = true)
            protected SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.CONSIGNEEADDRESS consigneeaddress;

            /**
             * Gets the value of the vendoraddress property.
             * 
             * @return
             *     possible object is
             *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.VENDORADDRESS }
             *     
             */
            public SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.VENDORADDRESS getVENDORADDRESS() {
                return vendoraddress;
            }

            /**
             * Sets the value of the vendoraddress property.
             * 
             * @param value
             *     allowed object is
             *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.VENDORADDRESS }
             *     
             */
            public void setVENDORADDRESS(SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.VENDORADDRESS value) {
                this.vendoraddress = value;
            }

            /**
             * Gets the value of the exporteraddress property.
             * 
             * @return
             *     possible object is
             *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.EXPORTERADDRESS }
             *     
             */
            public SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.EXPORTERADDRESS getEXPORTERADDRESS() {
                return exporteraddress;
            }

            /**
             * Sets the value of the exporteraddress property.
             * 
             * @param value
             *     allowed object is
             *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.EXPORTERADDRESS }
             *     
             */
            public void setEXPORTERADDRESS(SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.EXPORTERADDRESS value) {
                this.exporteraddress = value;
            }

            /**
             * Gets the value of the purchaseraddress property.
             * 
             * @return
             *     possible object is
             *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.PURCHASERADDRESS }
             *     
             */
            public SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.PURCHASERADDRESS getPURCHASERADDRESS() {
                return purchaseraddress;
            }

            /**
             * Sets the value of the purchaseraddress property.
             * 
             * @param value
             *     allowed object is
             *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.PURCHASERADDRESS }
             *     
             */
            public void setPURCHASERADDRESS(SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.PURCHASERADDRESS value) {
                this.purchaseraddress = value;
            }

            /**
             * Gets the value of the consigneeaddress property.
             * 
             * @return
             *     possible object is
             *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.CONSIGNEEADDRESS }
             *     
             */
            public SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.CONSIGNEEADDRESS getCONSIGNEEADDRESS() {
                return consigneeaddress;
            }

            /**
             * Sets the value of the consigneeaddress property.
             * 
             * @param value
             *     allowed object is
             *     {@link SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.CONSIGNEEADDRESS }
             *     
             */
            public void setCONSIGNEEADDRESS(SHIPMENTS.SHIPMENT.SHIPMENTADDRESS.CONSIGNEEADDRESS value) {
                this.consigneeaddress = value;
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
             *         &lt;element name="ConsName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ConsAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ConsAddress2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="ConsAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="ConsCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ConsState" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ConsCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ConsZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ConsAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
                "consName",
                "consAddress1",
                "consAddress2",
                "consAddress3",
                "consCity",
                "consState",
                "consCntry",
                "consZip",
                "consAddress8"
            })
            public static class CONSIGNEEADDRESS {

                @XmlElement(name = "ConsName", required = true)
                protected String consName;
                @XmlElement(name = "ConsAddress1", required = true)
                protected String consAddress1;
                @XmlElement(name = "ConsAddress2", required = true)
                protected Object consAddress2;
                @XmlElement(name = "ConsAddress3", required = true)
                protected Object consAddress3;
                @XmlElement(name = "ConsCity", required = true)
                protected String consCity;
                @XmlElement(name = "ConsState", required = true)
                protected String consState;
                @XmlElement(name = "ConsCntry", required = true)
                protected String consCntry;
                @XmlElement(name = "ConsZip", required = true)
                protected String consZip;
                @XmlElement(name = "ConsAddress8", required = true)
                protected Object consAddress8;

                /**
                 * Gets the value of the consName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getConsName() {
                    return consName;
                }

                /**
                 * Sets the value of the consName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setConsName(String value) {
                    this.consName = value;
                }

                /**
                 * Gets the value of the consAddress1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getConsAddress1() {
                    return consAddress1;
                }

                /**
                 * Sets the value of the consAddress1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setConsAddress1(String value) {
                    this.consAddress1 = value;
                }

                /**
                 * Gets the value of the consAddress2 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getConsAddress2() {
                    return consAddress2;
                }

                /**
                 * Sets the value of the consAddress2 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setConsAddress2(Object value) {
                    this.consAddress2 = value;
                }

                /**
                 * Gets the value of the consAddress3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getConsAddress3() {
                    return consAddress3;
                }

                /**
                 * Sets the value of the consAddress3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setConsAddress3(Object value) {
                    this.consAddress3 = value;
                }

                /**
                 * Gets the value of the consCity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getConsCity() {
                    return consCity;
                }

                /**
                 * Sets the value of the consCity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setConsCity(String value) {
                    this.consCity = value;
                }

                /**
                 * Gets the value of the consState property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getConsState() {
                    return consState;
                }

                /**
                 * Sets the value of the consState property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setConsState(String value) {
                    this.consState = value;
                }

                /**
                 * Gets the value of the consCntry property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getConsCntry() {
                    return consCntry;
                }

                /**
                 * Sets the value of the consCntry property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setConsCntry(String value) {
                    this.consCntry = value;
                }

                /**
                 * Gets the value of the consZip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getConsZip() {
                    return consZip;
                }

                /**
                 * Sets the value of the consZip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setConsZip(String value) {
                    this.consZip = value;
                }

                /**
                 * Gets the value of the consAddress8 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getConsAddress8() {
                    return consAddress8;
                }

                /**
                 * Sets the value of the consAddress8 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setConsAddress8(Object value) {
                    this.consAddress8 = value;
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
             *         &lt;element name="ExpName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ExpAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ExpAddress2" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ExpAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="ExpCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ExpState" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ExpCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ExpZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ExpAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
                "expName",
                "expAddress1",
                "expAddress2",
                "expAddress3",
                "expCity",
                "expState",
                "expCntry",
                "expZip",
                "expAddress8"
            })
            public static class EXPORTERADDRESS {

                @XmlElement(name = "ExpName", required = true)
                protected String expName;
                @XmlElement(name = "ExpAddress1", required = true)
                protected String expAddress1;
                @XmlElement(name = "ExpAddress2", required = true)
                protected String expAddress2;
                @XmlElement(name = "ExpAddress3", required = true)
                protected Object expAddress3;
                @XmlElement(name = "ExpCity", required = true)
                protected String expCity;
                @XmlElement(name = "ExpState", required = true)
                protected String expState;
                @XmlElement(name = "ExpCntry", required = true)
                protected String expCntry;
                @XmlElement(name = "ExpZip", required = true)
                protected String expZip;
                @XmlElement(name = "ExpAddress8", required = true)
                protected Object expAddress8;

                /**
                 * Gets the value of the expName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExpName() {
                    return expName;
                }

                /**
                 * Sets the value of the expName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExpName(String value) {
                    this.expName = value;
                }

                /**
                 * Gets the value of the expAddress1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExpAddress1() {
                    return expAddress1;
                }

                /**
                 * Sets the value of the expAddress1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExpAddress1(String value) {
                    this.expAddress1 = value;
                }

                /**
                 * Gets the value of the expAddress2 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExpAddress2() {
                    return expAddress2;
                }

                /**
                 * Sets the value of the expAddress2 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExpAddress2(String value) {
                    this.expAddress2 = value;
                }

                /**
                 * Gets the value of the expAddress3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getExpAddress3() {
                    return expAddress3;
                }

                /**
                 * Sets the value of the expAddress3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setExpAddress3(Object value) {
                    this.expAddress3 = value;
                }

                /**
                 * Gets the value of the expCity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExpCity() {
                    return expCity;
                }

                /**
                 * Sets the value of the expCity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExpCity(String value) {
                    this.expCity = value;
                }

                /**
                 * Gets the value of the expState property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExpState() {
                    return expState;
                }

                /**
                 * Sets the value of the expState property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExpState(String value) {
                    this.expState = value;
                }

                /**
                 * Gets the value of the expCntry property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExpCntry() {
                    return expCntry;
                }

                /**
                 * Sets the value of the expCntry property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExpCntry(String value) {
                    this.expCntry = value;
                }

                /**
                 * Gets the value of the expZip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getExpZip() {
                    return expZip;
                }

                /**
                 * Sets the value of the expZip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setExpZip(String value) {
                    this.expZip = value;
                }

                /**
                 * Gets the value of the expAddress8 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getExpAddress8() {
                    return expAddress8;
                }

                /**
                 * Sets the value of the expAddress8 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setExpAddress8(Object value) {
                    this.expAddress8 = value;
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
             *         &lt;element name="PurchName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="PurchAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="PurchAddress2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="PurchAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="PurchCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="PurchState" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="PurchCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="PurchZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="PurchAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
                "purchName",
                "purchAddress1",
                "purchAddress2",
                "purchAddress3",
                "purchCity",
                "purchState",
                "purchCntry",
                "purchZip",
                "purchAddress8"
            })
            public static class PURCHASERADDRESS {

                @XmlElement(name = "PurchName", required = true)
                protected String purchName;
                @XmlElement(name = "PurchAddress1", required = true)
                protected String purchAddress1;
                @XmlElement(name = "PurchAddress2", required = true)
                protected Object purchAddress2;
                @XmlElement(name = "PurchAddress3", required = true)
                protected Object purchAddress3;
                @XmlElement(name = "PurchCity", required = true)
                protected String purchCity;
                @XmlElement(name = "PurchState", required = true)
                protected String purchState;
                @XmlElement(name = "PurchCntry", required = true)
                protected String purchCntry;
                @XmlElement(name = "PurchZip", required = true)
                protected String purchZip;
                @XmlElement(name = "PurchAddress8", required = true)
                protected Object purchAddress8;

                /**
                 * Gets the value of the purchName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPurchName() {
                    return purchName;
                }

                /**
                 * Sets the value of the purchName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPurchName(String value) {
                    this.purchName = value;
                }

                /**
                 * Gets the value of the purchAddress1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPurchAddress1() {
                    return purchAddress1;
                }

                /**
                 * Sets the value of the purchAddress1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPurchAddress1(String value) {
                    this.purchAddress1 = value;
                }

                /**
                 * Gets the value of the purchAddress2 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getPurchAddress2() {
                    return purchAddress2;
                }

                /**
                 * Sets the value of the purchAddress2 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setPurchAddress2(Object value) {
                    this.purchAddress2 = value;
                }

                /**
                 * Gets the value of the purchAddress3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getPurchAddress3() {
                    return purchAddress3;
                }

                /**
                 * Sets the value of the purchAddress3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setPurchAddress3(Object value) {
                    this.purchAddress3 = value;
                }

                /**
                 * Gets the value of the purchCity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPurchCity() {
                    return purchCity;
                }

                /**
                 * Sets the value of the purchCity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPurchCity(String value) {
                    this.purchCity = value;
                }

                /**
                 * Gets the value of the purchState property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPurchState() {
                    return purchState;
                }

                /**
                 * Sets the value of the purchState property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPurchState(String value) {
                    this.purchState = value;
                }

                /**
                 * Gets the value of the purchCntry property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPurchCntry() {
                    return purchCntry;
                }

                /**
                 * Sets the value of the purchCntry property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPurchCntry(String value) {
                    this.purchCntry = value;
                }

                /**
                 * Gets the value of the purchZip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPurchZip() {
                    return purchZip;
                }

                /**
                 * Sets the value of the purchZip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPurchZip(String value) {
                    this.purchZip = value;
                }

                /**
                 * Gets the value of the purchAddress8 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getPurchAddress8() {
                    return purchAddress8;
                }

                /**
                 * Sets the value of the purchAddress8 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setPurchAddress8(Object value) {
                    this.purchAddress8 = value;
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
             *         &lt;element name="VendName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="VendAddress1" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="VendAddress2" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="VendAddress3" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="VendCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="VendState" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="VendCntry" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="VendZip" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="VendAddress8" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
                "vendName",
                "vendAddress1",
                "vendAddress2",
                "vendAddress3",
                "vendCity",
                "vendState",
                "vendCntry",
                "vendZip",
                "vendAddress8"
            })
            public static class VENDORADDRESS {

                @XmlElement(name = "VendName", required = true)
                protected String vendName;
                @XmlElement(name = "VendAddress1", required = true)
                protected String vendAddress1;
                @XmlElement(name = "VendAddress2", required = true)
                protected String vendAddress2;
                @XmlElement(name = "VendAddress3", required = true)
                protected Object vendAddress3;
                @XmlElement(name = "VendCity", required = true)
                protected String vendCity;
                @XmlElement(name = "VendState", required = true)
                protected String vendState;
                @XmlElement(name = "VendCntry", required = true)
                protected String vendCntry;
                @XmlElement(name = "VendZip", required = true)
                protected String vendZip;
                @XmlElement(name = "VendAddress8", required = true)
                protected Object vendAddress8;

                /**
                 * Gets the value of the vendName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendName() {
                    return vendName;
                }

                /**
                 * Sets the value of the vendName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendName(String value) {
                    this.vendName = value;
                }

                /**
                 * Gets the value of the vendAddress1 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendAddress1() {
                    return vendAddress1;
                }

                /**
                 * Sets the value of the vendAddress1 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendAddress1(String value) {
                    this.vendAddress1 = value;
                }

                /**
                 * Gets the value of the vendAddress2 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendAddress2() {
                    return vendAddress2;
                }

                /**
                 * Sets the value of the vendAddress2 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendAddress2(String value) {
                    this.vendAddress2 = value;
                }

                /**
                 * Gets the value of the vendAddress3 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getVendAddress3() {
                    return vendAddress3;
                }

                /**
                 * Sets the value of the vendAddress3 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setVendAddress3(Object value) {
                    this.vendAddress3 = value;
                }

                /**
                 * Gets the value of the vendCity property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendCity() {
                    return vendCity;
                }

                /**
                 * Sets the value of the vendCity property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendCity(String value) {
                    this.vendCity = value;
                }

                /**
                 * Gets the value of the vendState property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendState() {
                    return vendState;
                }

                /**
                 * Sets the value of the vendState property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendState(String value) {
                    this.vendState = value;
                }

                /**
                 * Gets the value of the vendCntry property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendCntry() {
                    return vendCntry;
                }

                /**
                 * Sets the value of the vendCntry property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendCntry(String value) {
                    this.vendCntry = value;
                }

                /**
                 * Gets the value of the vendZip property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getVendZip() {
                    return vendZip;
                }

                /**
                 * Sets the value of the vendZip property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setVendZip(String value) {
                    this.vendZip = value;
                }

                /**
                 * Gets the value of the vendAddress8 property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getVendAddress8() {
                    return vendAddress8;
                }

                /**
                 * Sets the value of the vendAddress8 property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setVendAddress8(Object value) {
                    this.vendAddress8 = value;
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
         *         &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "text"
        })
        public static class SHIPMENTCOMMENT {

            @XmlElement(name = "Text", required = true)
            protected String text;

            /**
             * Gets the value of the text property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getText() {
                return text;
            }

            /**
             * Sets the value of the text property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setText(String value) {
                this.text = value;
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
         *         &lt;element name="ORDER" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="PartNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="PartName" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="UnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="Qty" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *                   &lt;element name="Uom" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="ContainerNo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="DiscountDesc" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="DiscountAmt" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="ExtendPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *                   &lt;element name="RefPONo" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="CountryOrigin" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="QtyQualifier" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="HSNumber" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="TUom" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                   &lt;element name="TQty" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *                 &lt;/sequence>
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
            "order"
        })
        public static class SHIPMENTDETAIL {

            @XmlElement(name = "ORDER", required = true)
            protected List<SHIPMENTS.SHIPMENT.SHIPMENTDETAIL.ORDER> order;

            /**
             * Gets the value of the order property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the order property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getORDER().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link SHIPMENTS.SHIPMENT.SHIPMENTDETAIL.ORDER }
             * 
             * 
             */
            public List<SHIPMENTS.SHIPMENT.SHIPMENTDETAIL.ORDER> getORDER() {
                if (order == null) {
                    order = new ArrayList<SHIPMENTS.SHIPMENT.SHIPMENTDETAIL.ORDER>();
                }
                return this.order;
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
             *         &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="PartNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="PartName" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="UnitPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="Qty" type="{http://www.w3.org/2001/XMLSchema}int"/>
             *         &lt;element name="Uom" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="ContainerNo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="DiscountDesc" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="DiscountAmt" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="ExtendPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
             *         &lt;element name="RefPONo" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="CountryOrigin" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="QtyQualifier" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="HSNumber" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="TUom" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
             *         &lt;element name="TQty" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
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
                "currencyCode",
                "partNumber",
                "partName",
                "unitPrice",
                "qty",
                "uom",
                "containerNo",
                "discountDesc",
                "discountAmt",
                "extendPrice",
                "refPONo",
                "countryOrigin",
                "qtyQualifier",
                "hsNumber",
                "tUom",
                "tQty"
            })
            public static class ORDER {

                @XmlElement(name = "CurrencyCode", required = true)
                protected Object currencyCode;
                @XmlElement(name = "PartNumber", required = true)
                protected String partNumber;
                @XmlElement(name = "PartName", required = true)
                protected String partName;
                @XmlElement(name = "UnitPrice", required = true)
                protected BigDecimal unitPrice;
                @XmlElement(name = "Qty")
                protected int qty;
                @XmlElement(name = "Uom", required = true)
                protected String uom;
                @XmlElement(name = "ContainerNo", required = true)
                protected Object containerNo;
                @XmlElement(name = "DiscountDesc", required = true)
                protected Object discountDesc;
                @XmlElement(name = "DiscountAmt", required = true)
                protected Object discountAmt;
                @XmlElement(name = "ExtendPrice", required = true)
                protected BigDecimal extendPrice;
                @XmlElement(name = "RefPONo", required = true)
                protected String refPONo;
                @XmlElement(name = "CountryOrigin", required = true)
                protected String countryOrigin;
                @XmlElement(name = "QtyQualifier", required = true)
                protected Object qtyQualifier;
                @XmlElement(name = "HSNumber", required = true)
                protected Object hsNumber;
                @XmlElement(name = "TUom", required = true)
                protected Object tUom;
                @XmlElement(name = "TQty", required = true)
                protected Object tQty;

                /**
                 * Gets the value of the currencyCode property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getCurrencyCode() {
                    return currencyCode;
                }

                /**
                 * Sets the value of the currencyCode property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setCurrencyCode(Object value) {
                    this.currencyCode = value;
                }

                /**
                 * Gets the value of the partNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPartNumber() {
                    return partNumber;
                }

                /**
                 * Sets the value of the partNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPartNumber(String value) {
                    this.partNumber = value;
                }

                /**
                 * Gets the value of the partName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPartName() {
                    return partName;
                }

                /**
                 * Sets the value of the partName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPartName(String value) {
                    this.partName = value;
                }

                /**
                 * Gets the value of the unitPrice property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getUnitPrice() {
                    return unitPrice;
                }

                /**
                 * Sets the value of the unitPrice property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setUnitPrice(BigDecimal value) {
                    this.unitPrice = value;
                }

                /**
                 * Gets the value of the qty property.
                 * 
                 */
                public int getQty() {
                    return qty;
                }

                /**
                 * Sets the value of the qty property.
                 * 
                 */
                public void setQty(int value) {
                    this.qty = value;
                }

                /**
                 * Gets the value of the uom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getUom() {
                    return uom;
                }

                /**
                 * Sets the value of the uom property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setUom(String value) {
                    this.uom = value;
                }

                /**
                 * Gets the value of the containerNo property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getContainerNo() {
                    return containerNo;
                }

                /**
                 * Sets the value of the containerNo property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setContainerNo(Object value) {
                    this.containerNo = value;
                }

                /**
                 * Gets the value of the discountDesc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getDiscountDesc() {
                    return discountDesc;
                }

                /**
                 * Sets the value of the discountDesc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setDiscountDesc(Object value) {
                    this.discountDesc = value;
                }

                /**
                 * Gets the value of the discountAmt property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getDiscountAmt() {
                    return discountAmt;
                }

                /**
                 * Sets the value of the discountAmt property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setDiscountAmt(Object value) {
                    this.discountAmt = value;
                }

                /**
                 * Gets the value of the extendPrice property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getExtendPrice() {
                    return extendPrice;
                }

                /**
                 * Sets the value of the extendPrice property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setExtendPrice(BigDecimal value) {
                    this.extendPrice = value;
                }

                /**
                 * Gets the value of the refPONo property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRefPONo() {
                    return refPONo;
                }

                /**
                 * Sets the value of the refPONo property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRefPONo(String value) {
                    this.refPONo = value;
                }

                /**
                 * Gets the value of the countryOrigin property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCountryOrigin() {
                    return countryOrigin;
                }

                /**
                 * Sets the value of the countryOrigin property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCountryOrigin(String value) {
                    this.countryOrigin = value;
                }

                /**
                 * Gets the value of the qtyQualifier property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getQtyQualifier() {
                    return qtyQualifier;
                }

                /**
                 * Sets the value of the qtyQualifier property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setQtyQualifier(Object value) {
                    this.qtyQualifier = value;
                }

                /**
                 * Gets the value of the hsNumber property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getHSNumber() {
                    return hsNumber;
                }

                /**
                 * Sets the value of the hsNumber property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setHSNumber(Object value) {
                    this.hsNumber = value;
                }

                /**
                 * Gets the value of the tUom property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getTUom() {
                    return tUom;
                }

                /**
                 * Sets the value of the tUom property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setTUom(Object value) {
                    this.tUom = value;
                }

                /**
                 * Gets the value of the tQty property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Object }
                 *     
                 */
                public Object getTQty() {
                    return tQty;
                }

                /**
                 * Sets the value of the tQty property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Object }
                 *     
                 */
                public void setTQty(Object value) {
                    this.tQty = value;
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
         *         &lt;element name="CCINo" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="AltShpmntNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ETADate" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="ETAHour" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="InvoiceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="Port" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="InvQty" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="InvUnit" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="BOL" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="DirectShipDt" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Broker" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="ModeTransport" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="UnitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="NetWeight" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="GrossWeight" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="PayTerms1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="PayTerms2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="Currency" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="Royalty" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="CanadianContent" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="InvTotal" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="RefPO1" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="RefPO2" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="PlaceDirShpmt" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="CntryExport" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="DeptRuling" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="CargoCtl" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="ContainerNo" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
         *         &lt;element name="CartonCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="PARS" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "cciNo",
            "altShpmntNo",
            "etaDate",
            "etaHour",
            "invoiceNumber",
            "port",
            "invQty",
            "invUnit",
            "bol",
            "directShipDt",
            "broker",
            "modeTransport",
            "unitOfMeasure",
            "netWeight",
            "grossWeight",
            "payTerms1",
            "payTerms2",
            "currency",
            "royalty",
            "canadianContent",
            "invTotal",
            "refPO1",
            "refPO2",
            "placeDirShpmt",
            "cntryExport",
            "deptRuling",
            "cargoCtl",
            "containerNo",
            "cartonCount",
            "pars"
        })
        public static class SHIPMENTHEADER {

            @XmlElement(name = "CCINo")
            protected int cciNo;
            @XmlElement(name = "AltShpmntNo", required = true)
            protected String altShpmntNo;
            @XmlElement(name = "ETADate", required = true)
            protected Object etaDate;
            @XmlElement(name = "ETAHour", required = true)
            protected Object etaHour;
            @XmlElement(name = "InvoiceNumber")
            protected int invoiceNumber;
            @XmlElement(name = "Port")
            protected int port;
            @XmlElement(name = "InvQty")
            protected int invQty;
            @XmlElement(name = "InvUnit", required = true)
            protected String invUnit;
            @XmlElement(name = "BOL", required = true)
            protected String bol;
            @XmlElement(name = "DirectShipDt", required = true)
            protected String directShipDt;
            @XmlElement(name = "Broker", required = true)
            protected Object broker;
            @XmlElement(name = "ModeTransport", required = true)
            protected Object modeTransport;
            @XmlElement(name = "UnitOfMeasure", required = true)
            protected String unitOfMeasure;
            @XmlElement(name = "NetWeight", required = true)
            protected BigDecimal netWeight;
            @XmlElement(name = "GrossWeight", required = true)
            protected BigDecimal grossWeight;
            @XmlElement(name = "PayTerms1", required = true)
            protected String payTerms1;
            @XmlElement(name = "PayTerms2", required = true)
            protected Object payTerms2;
            @XmlElement(name = "Currency", required = true)
            protected String currency;
            @XmlElement(name = "Royalty", required = true)
            protected Object royalty;
            @XmlElement(name = "CanadianContent", required = true)
            protected Object canadianContent;
            @XmlElement(name = "InvTotal", required = true)
            protected BigDecimal invTotal;
            @XmlElement(name = "RefPO1", required = true)
            protected String refPO1;
            @XmlElement(name = "RefPO2", required = true)
            protected Object refPO2;
            @XmlElement(name = "PlaceDirShpmt", required = true)
            protected Object placeDirShpmt;
            @XmlElement(name = "CntryExport", required = true)
            protected String cntryExport;
            @XmlElement(name = "DeptRuling", required = true)
            protected Object deptRuling;
            @XmlElement(name = "CargoCtl", required = true)
            protected Object cargoCtl;
            @XmlElement(name = "ContainerNo", required = true)
            protected Object containerNo;
            @XmlElement(name = "CartonCount")
            protected int cartonCount;
            @XmlElement(name = "PARS", required = true)
            protected String pars;

            /**
             * Gets the value of the cciNo property.
             * 
             */
            public int getCCINo() {
                return cciNo;
            }

            /**
             * Sets the value of the cciNo property.
             * 
             */
            public void setCCINo(int value) {
                this.cciNo = value;
            }

            /**
             * Gets the value of the altShpmntNo property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAltShpmntNo() {
                return altShpmntNo;
            }

            /**
             * Sets the value of the altShpmntNo property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAltShpmntNo(String value) {
                this.altShpmntNo = value;
            }

            /**
             * Gets the value of the etaDate property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getETADate() {
                return etaDate;
            }

            /**
             * Sets the value of the etaDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setETADate(Object value) {
                this.etaDate = value;
            }

            /**
             * Gets the value of the etaHour property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getETAHour() {
                return etaHour;
            }

            /**
             * Sets the value of the etaHour property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setETAHour(Object value) {
                this.etaHour = value;
            }

            /**
             * Gets the value of the invoiceNumber property.
             * 
             */
            public int getInvoiceNumber() {
                return invoiceNumber;
            }

            /**
             * Sets the value of the invoiceNumber property.
             * 
             */
            public void setInvoiceNumber(int value) {
                this.invoiceNumber = value;
            }

            /**
             * Gets the value of the port property.
             * 
             */
            public int getPort() {
                return port;
            }

            /**
             * Sets the value of the port property.
             * 
             */
            public void setPort(int value) {
                this.port = value;
            }

            /**
             * Gets the value of the invQty property.
             * 
             */
            public int getInvQty() {
                return invQty;
            }

            /**
             * Sets the value of the invQty property.
             * 
             */
            public void setInvQty(int value) {
                this.invQty = value;
            }

            /**
             * Gets the value of the invUnit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getInvUnit() {
                return invUnit;
            }

            /**
             * Sets the value of the invUnit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setInvUnit(String value) {
                this.invUnit = value;
            }

            /**
             * Gets the value of the bol property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBOL() {
                return bol;
            }

            /**
             * Sets the value of the bol property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBOL(String value) {
                this.bol = value;
            }

            /**
             * Gets the value of the directShipDt property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDirectShipDt() {
                return directShipDt;
            }

            /**
             * Sets the value of the directShipDt property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDirectShipDt(String value) {
                this.directShipDt = value;
            }

            /**
             * Gets the value of the broker property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getBroker() {
                return broker;
            }

            /**
             * Sets the value of the broker property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setBroker(Object value) {
                this.broker = value;
            }

            /**
             * Gets the value of the modeTransport property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getModeTransport() {
                return modeTransport;
            }

            /**
             * Sets the value of the modeTransport property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setModeTransport(Object value) {
                this.modeTransport = value;
            }

            /**
             * Gets the value of the unitOfMeasure property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnitOfMeasure() {
                return unitOfMeasure;
            }

            /**
             * Sets the value of the unitOfMeasure property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnitOfMeasure(String value) {
                this.unitOfMeasure = value;
            }

            /**
             * Gets the value of the netWeight property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNetWeight() {
                return netWeight;
            }

            /**
             * Sets the value of the netWeight property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNetWeight(BigDecimal value) {
                this.netWeight = value;
            }

            /**
             * Gets the value of the grossWeight property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getGrossWeight() {
                return grossWeight;
            }

            /**
             * Sets the value of the grossWeight property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setGrossWeight(BigDecimal value) {
                this.grossWeight = value;
            }

            /**
             * Gets the value of the payTerms1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPayTerms1() {
                return payTerms1;
            }

            /**
             * Sets the value of the payTerms1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPayTerms1(String value) {
                this.payTerms1 = value;
            }

            /**
             * Gets the value of the payTerms2 property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getPayTerms2() {
                return payTerms2;
            }

            /**
             * Sets the value of the payTerms2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setPayTerms2(Object value) {
                this.payTerms2 = value;
            }

            /**
             * Gets the value of the currency property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCurrency() {
                return currency;
            }

            /**
             * Sets the value of the currency property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCurrency(String value) {
                this.currency = value;
            }

            /**
             * Gets the value of the royalty property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getRoyalty() {
                return royalty;
            }

            /**
             * Sets the value of the royalty property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setRoyalty(Object value) {
                this.royalty = value;
            }

            /**
             * Gets the value of the canadianContent property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getCanadianContent() {
                return canadianContent;
            }

            /**
             * Sets the value of the canadianContent property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setCanadianContent(Object value) {
                this.canadianContent = value;
            }

            /**
             * Gets the value of the invTotal property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getInvTotal() {
                return invTotal;
            }

            /**
             * Sets the value of the invTotal property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setInvTotal(BigDecimal value) {
                this.invTotal = value;
            }

            /**
             * Gets the value of the refPO1 property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRefPO1() {
                return refPO1;
            }

            /**
             * Sets the value of the refPO1 property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRefPO1(String value) {
                this.refPO1 = value;
            }

            /**
             * Gets the value of the refPO2 property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getRefPO2() {
                return refPO2;
            }

            /**
             * Sets the value of the refPO2 property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setRefPO2(Object value) {
                this.refPO2 = value;
            }

            /**
             * Gets the value of the placeDirShpmt property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getPlaceDirShpmt() {
                return placeDirShpmt;
            }

            /**
             * Sets the value of the placeDirShpmt property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setPlaceDirShpmt(Object value) {
                this.placeDirShpmt = value;
            }

            /**
             * Gets the value of the cntryExport property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCntryExport() {
                return cntryExport;
            }

            /**
             * Sets the value of the cntryExport property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCntryExport(String value) {
                this.cntryExport = value;
            }

            /**
             * Gets the value of the deptRuling property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getDeptRuling() {
                return deptRuling;
            }

            /**
             * Sets the value of the deptRuling property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setDeptRuling(Object value) {
                this.deptRuling = value;
            }

            /**
             * Gets the value of the cargoCtl property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getCargoCtl() {
                return cargoCtl;
            }

            /**
             * Sets the value of the cargoCtl property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setCargoCtl(Object value) {
                this.cargoCtl = value;
            }

            /**
             * Gets the value of the containerNo property.
             * 
             * @return
             *     possible object is
             *     {@link Object }
             *     
             */
            public Object getContainerNo() {
                return containerNo;
            }

            /**
             * Sets the value of the containerNo property.
             * 
             * @param value
             *     allowed object is
             *     {@link Object }
             *     
             */
            public void setContainerNo(Object value) {
                this.containerNo = value;
            }

            /**
             * Gets the value of the cartonCount property.
             * 
             */
            public int getCartonCount() {
                return cartonCount;
            }

            /**
             * Sets the value of the cartonCount property.
             * 
             */
            public void setCartonCount(int value) {
                this.cartonCount = value;
            }

            /**
             * Gets the value of the pars property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPARS() {
                return pars;
            }

            /**
             * Sets the value of the pars property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPARS(String value) {
                this.pars = value;
            }

        }

    }

}
