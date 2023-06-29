
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * Request to perform multiple operations in a sequence.
 * 
 * <p>Java class for CompoundOperation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompoundOperation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="closeOutRequest" type="{urn:connectship-com:ampcore}CloseOutRequest"/>
 *         &lt;element name="createGroupRequest" type="{urn:connectship-com:ampcore}CreateGroupRequest"/>
 *         &lt;element name="customOperationRequest" type="{urn:connectship-com:ampcore}CustomOperationRequest"/>
 *         &lt;element name="deleteShipFilesRequest" type="{urn:connectship-com:ampcore}DeleteShipFilesRequest"/>
 *         &lt;element name="deleteTransmitItemsRequest" type="{urn:connectship-com:ampcore}DeleteTransmitItemsRequest"/>
 *         &lt;element name="getGroupRequest" type="{urn:connectship-com:ampcore}GetGroupRequest"/>
 *         &lt;element name="listCarriersRequest" type="{urn:connectship-com:ampcore}ListCarriersRequest"/>
 *         &lt;element name="listCloseOutItemsRequest" type="{urn:connectship-com:ampcore}ListCloseOutItemsRequest"/>
 *         &lt;element name="listCountriesRequest" type="{urn:connectship-com:ampcore}ListCountriesRequest"/>
 *         &lt;element name="listCurrenciesRequest" type="{urn:connectship-com:ampcore}ListCurrenciesRequest"/>
 *         &lt;element name="listDocumentsRequest" type="{urn:connectship-com:ampcore}ListDocumentsRequest"/>
 *         &lt;element name="listDocumentFormatsRequest" type="{urn:connectship-com:ampcore}ListDocumentFormatsRequest"/>
 *         &lt;element name="listGroupingsRequest" type="{urn:connectship-com:ampcore}ListGroupingsRequest"/>
 *         &lt;element name="listGroupsRequest" type="{urn:connectship-com:ampcore}ListGroupsRequest"/>
 *         &lt;element name="listIncotermsRequest" type="{urn:connectship-com:ampcore}ListIncotermsRequest"/>
 *         &lt;element name="listLocalPortsRequest" type="{urn:connectship-com:ampcore}ListLocalPortsRequest"/>
 *         &lt;element name="listPackagingTypesRequest" type="{urn:connectship-com:ampcore}ListPackagingTypesRequest"/>
 *         &lt;element name="listPaymentTypesRequest" type="{urn:connectship-com:ampcore}ListPaymentTypesRequest"/>
 *         &lt;element name="listPrinterDevicesRequest" type="{urn:connectship-com:ampcore}ListPrinterDevicesRequest"/>
 *         &lt;element name="listServicesRequest" type="{urn:connectship-com:ampcore}ListServicesRequest"/>
 *         &lt;element name="listShipFilesRequest" type="{urn:connectship-com:ampcore}ListShipFilesRequest"/>
 *         &lt;element name="listShippersRequest" type="{urn:connectship-com:ampcore}ListShippersRequest"/>
 *         &lt;element name="listStocksRequest" type="{urn:connectship-com:ampcore}ListStocksRequest"/>
 *         &lt;element name="listTransmitItemsRequest" type="{urn:connectship-com:ampcore}ListTransmitItemsRequest"/>
 *         &lt;element name="listUnitsRequest" type="{urn:connectship-com:ampcore}ListUnitsRequest"/>
 *         &lt;element name="listWindowsPrintersRequest" type="{urn:connectship-com:ampcore}ListWindowsPrintersRequest"/>
 *         &lt;element name="modifyContainerRequest" type="{urn:connectship-com:ampcore}ModifyContainerRequest"/>
 *         &lt;element name="modifyGroupRequest" type="{urn:connectship-com:ampcore}ModifyGroupRequest"/>
 *         &lt;element name="modifyPackagesRequest" type="{urn:connectship-com:ampcore}ModifyPackagesRequest"/>
 *         &lt;element name="printRequest" type="{urn:connectship-com:ampcore}PrintRequest"/>
 *         &lt;element name="printXmlRequest" type="{urn:connectship-com:ampcore}PrintXmlRequest"/>
 *         &lt;element name="rateRequest" type="{urn:connectship-com:ampcore}RateRequest"/>
 *         &lt;element name="reprocessRequest" type="{urn:connectship-com:ampcore}ReprocessRequest"/>
 *         &lt;element name="searchRequest" type="{urn:connectship-com:ampcore}SearchRequest"/>
 *         &lt;element name="shipRequest" type="{urn:connectship-com:ampcore}ShipRequest"/>
 *         &lt;element name="transmitRequest" type="{urn:connectship-com:ampcore}TransmitRequest"/>
 *         &lt;element name="validateAddressRequest" type="{urn:connectship-com:ampcore}ValidateAddressRequest"/>
 *         &lt;element name="voidPackagesRequest" type="{urn:connectship-com:ampcore}VoidPackagesRequest"/>
 *       &lt;/choice>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}preProcess"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}postProcess"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}locale"/>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}asyncCorrelationData"/>
 *       &lt;attribute name="stopOnFailure" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompoundOperation", propOrder = {
    "closeOutRequestOrCreateGroupRequestOrCustomOperationRequest"
})
public class CompoundOperation {

    @XmlElements({
        @XmlElement(name = "searchRequest", type = SearchRequest.class),
        @XmlElement(name = "shipRequest", type = ShipRequest.class),
        @XmlElement(name = "voidPackagesRequest", type = VoidPackagesRequest.class),
        @XmlElement(name = "closeOutRequest", type = CloseOutRequest.class),
        @XmlElement(name = "customOperationRequest", type = CustomOperationRequest.class),
        @XmlElement(name = "listCurrenciesRequest", type = ListCurrenciesRequest.class),
        @XmlElement(name = "listGroupsRequest", type = ListGroupsRequest.class),
        @XmlElement(name = "listDocumentsRequest", type = ListDocumentsRequest.class),
        @XmlElement(name = "listPackagingTypesRequest", type = ListPackagingTypesRequest.class),
        @XmlElement(name = "validateAddressRequest", type = ValidateAddressRequest.class),
        @XmlElement(name = "listDocumentFormatsRequest", type = ListDocumentFormatsRequest.class),
        @XmlElement(name = "listCloseOutItemsRequest", type = ListCloseOutItemsRequest.class),
        @XmlElement(name = "getGroupRequest", type = GetGroupRequest.class),
        @XmlElement(name = "deleteShipFilesRequest", type = DeleteShipFilesRequest.class),
        @XmlElement(name = "deleteTransmitItemsRequest", type = DeleteTransmitItemsRequest.class),
        @XmlElement(name = "modifyPackagesRequest", type = ModifyPackagesRequest.class),
        @XmlElement(name = "listShipFilesRequest", type = ListShipFilesRequest.class),
        @XmlElement(name = "modifyGroupRequest", type = ModifyGroupRequest.class),
        @XmlElement(name = "reprocessRequest", type = ReprocessRequest.class),
        @XmlElement(name = "listPrinterDevicesRequest", type = ListPrinterDevicesRequest.class),
        @XmlElement(name = "printXmlRequest", type = PrintXmlRequest.class),
        @XmlElement(name = "modifyContainerRequest", type = ModifyContainerRequest.class),
        @XmlElement(name = "listPaymentTypesRequest", type = ListPaymentTypesRequest.class),
        @XmlElement(name = "listIncotermsRequest", type = ListIncotermsRequest.class),
        @XmlElement(name = "listLocalPortsRequest", type = ListLocalPortsRequest.class),
        @XmlElement(name = "listStocksRequest", type = ListStocksRequest.class),
        @XmlElement(name = "transmitRequest", type = TransmitRequest.class),
        @XmlElement(name = "rateRequest", type = RateRequest.class),
        @XmlElement(name = "listUnitsRequest", type = ListUnitsRequest.class),
        @XmlElement(name = "printRequest", type = PrintRequest.class),
        @XmlElement(name = "listServicesRequest", type = ListServicesRequest.class),
        @XmlElement(name = "listShippersRequest", type = ListShippersRequest.class),
        @XmlElement(name = "listWindowsPrintersRequest", type = ListWindowsPrintersRequest.class),
        @XmlElement(name = "createGroupRequest", type = CreateGroupRequest.class),
        @XmlElement(name = "listGroupingsRequest", type = ListGroupingsRequest.class),
        @XmlElement(name = "listTransmitItemsRequest", type = ListTransmitItemsRequest.class),
        @XmlElement(name = "listCarriersRequest", type = ListCarriersRequest.class),
        @XmlElement(name = "listCountriesRequest", type = ListCountriesRequest.class)
    })
    protected List<Object> closeOutRequestOrCreateGroupRequestOrCustomOperationRequest;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String preProcess;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String postProcess;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String locale;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String asyncCorrelationData;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected Boolean stopOnFailure;

    /**
     * Gets the value of the closeOutRequestOrCreateGroupRequestOrCustomOperationRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the closeOutRequestOrCreateGroupRequestOrCustomOperationRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCloseOutRequestOrCreateGroupRequestOrCustomOperationRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchRequest }
     * {@link ShipRequest }
     * {@link VoidPackagesRequest }
     * {@link CloseOutRequest }
     * {@link CustomOperationRequest }
     * {@link ListCurrenciesRequest }
     * {@link ListGroupsRequest }
     * {@link ListDocumentsRequest }
     * {@link ListPackagingTypesRequest }
     * {@link ValidateAddressRequest }
     * {@link ListDocumentFormatsRequest }
     * {@link ListCloseOutItemsRequest }
     * {@link GetGroupRequest }
     * {@link DeleteShipFilesRequest }
     * {@link DeleteTransmitItemsRequest }
     * {@link ModifyPackagesRequest }
     * {@link ListShipFilesRequest }
     * {@link ModifyGroupRequest }
     * {@link ReprocessRequest }
     * {@link ListPrinterDevicesRequest }
     * {@link PrintXmlRequest }
     * {@link ModifyContainerRequest }
     * {@link ListPaymentTypesRequest }
     * {@link ListIncotermsRequest }
     * {@link ListLocalPortsRequest }
     * {@link ListStocksRequest }
     * {@link TransmitRequest }
     * {@link RateRequest }
     * {@link ListUnitsRequest }
     * {@link PrintRequest }
     * {@link ListServicesRequest }
     * {@link ListShippersRequest }
     * {@link ListWindowsPrintersRequest }
     * {@link CreateGroupRequest }
     * {@link ListGroupingsRequest }
     * {@link ListTransmitItemsRequest }
     * {@link ListCarriersRequest }
     * {@link ListCountriesRequest }
     * 
     * 
     */
    public List<Object> getCloseOutRequestOrCreateGroupRequestOrCustomOperationRequest() {
        if (closeOutRequestOrCreateGroupRequestOrCustomOperationRequest == null) {
            closeOutRequestOrCreateGroupRequestOrCustomOperationRequest = new ArrayList<Object>();
        }
        return this.closeOutRequestOrCreateGroupRequestOrCustomOperationRequest;
    }

    /**
     * Gets the value of the preProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreProcess() {
        return preProcess;
    }

    /**
     * Sets the value of the preProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreProcess(String value) {
        this.preProcess = value;
    }

    /**
     * Gets the value of the postProcess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostProcess() {
        return postProcess;
    }

    /**
     * Sets the value of the postProcess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostProcess(String value) {
        this.postProcess = value;
    }

    /**
     * Gets the value of the locale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocale(String value) {
        this.locale = value;
    }

    /**
     * Gets the value of the asyncCorrelationData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsyncCorrelationData() {
        return asyncCorrelationData;
    }

    /**
     * Sets the value of the asyncCorrelationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsyncCorrelationData(String value) {
        this.asyncCorrelationData = value;
    }

    /**
     * Gets the value of the stopOnFailure property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStopOnFailure() {
        return stopOnFailure;
    }

    /**
     * Sets the value of the stopOnFailure property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStopOnFailure(Boolean value) {
        this.stopOnFailure = value;
    }

}
