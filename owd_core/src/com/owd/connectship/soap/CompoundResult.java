
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Response from a compound operation. Contains multiple responses from the individual operations.
 * 
 * <p>Java class for CompoundResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompoundResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="closeOutResponse" type="{urn:connectship-com:ampcore}CloseOutResponse"/>
 *         &lt;element name="createGroupResponse" type="{urn:connectship-com:ampcore}CreateGroupResponse"/>
 *         &lt;element name="customOperationResponse" type="{urn:connectship-com:ampcore}CustomOperationResponse"/>
 *         &lt;element name="deleteShipFilesResponse" type="{urn:connectship-com:ampcore}DeleteShipFilesResponse"/>
 *         &lt;element name="deleteTransmitItemsResponse" type="{urn:connectship-com:ampcore}DeleteTransmitItemsResponse"/>
 *         &lt;element name="getGroupResponse" type="{urn:connectship-com:ampcore}GetGroupResponse"/>
 *         &lt;element name="listCarriersResponse" type="{urn:connectship-com:ampcore}ListCarriersResponse"/>
 *         &lt;element name="listCloseOutItemsResponse" type="{urn:connectship-com:ampcore}ListCloseOutItemsResponse"/>
 *         &lt;element name="listCountriesResponse" type="{urn:connectship-com:ampcore}ListCountriesResponse"/>
 *         &lt;element name="listCurrenciesResponse" type="{urn:connectship-com:ampcore}ListCurrenciesResponse"/>
 *         &lt;element name="listDocumentsResponse" type="{urn:connectship-com:ampcore}ListDocumentsResponse"/>
 *         &lt;element name="listDocumentFormatsResponse" type="{urn:connectship-com:ampcore}ListDocumentFormatsResponse"/>
 *         &lt;element name="listGroupingsResponse" type="{urn:connectship-com:ampcore}ListGroupingsResponse"/>
 *         &lt;element name="listGroupsResponse" type="{urn:connectship-com:ampcore}ListGroupsResponse"/>
 *         &lt;element name="listIncotermsResponse" type="{urn:connectship-com:ampcore}ListIncotermsResponse"/>
 *         &lt;element name="listLocalPortsResponse" type="{urn:connectship-com:ampcore}ListLocalPortsResponse"/>
 *         &lt;element name="listPackagingTypesResponse" type="{urn:connectship-com:ampcore}ListPackagingTypesResponse"/>
 *         &lt;element name="listPaymentTypesResponse" type="{urn:connectship-com:ampcore}ListPaymentTypesResponse"/>
 *         &lt;element name="listPrinterDevicesResponse" type="{urn:connectship-com:ampcore}ListPrinterDevicesResponse"/>
 *         &lt;element name="listServicesResponse" type="{urn:connectship-com:ampcore}ListServicesResponse"/>
 *         &lt;element name="listShipFilesResponse" type="{urn:connectship-com:ampcore}ListShipFilesResponse"/>
 *         &lt;element name="listShippersResponse" type="{urn:connectship-com:ampcore}ListShippersResponse"/>
 *         &lt;element name="listStocksResponse" type="{urn:connectship-com:ampcore}ListStocksResponse"/>
 *         &lt;element name="listTransmitItemsResponse" type="{urn:connectship-com:ampcore}ListTransmitItemsResponse"/>
 *         &lt;element name="listUnitsResponse" type="{urn:connectship-com:ampcore}ListUnitsResponse"/>
 *         &lt;element name="listWindowsPrintersResponse" type="{urn:connectship-com:ampcore}ListWindowsPrintersResponse"/>
 *         &lt;element name="modifyContainerResponse" type="{urn:connectship-com:ampcore}ModifyContainerResponse"/>
 *         &lt;element name="modifyGroupResponse" type="{urn:connectship-com:ampcore}ModifyGroupResponse"/>
 *         &lt;element name="modifyPackagesResponse" type="{urn:connectship-com:ampcore}ModifyPackagesResponse"/>
 *         &lt;element name="printResponse" type="{urn:connectship-com:ampcore}PrintResponse"/>
 *         &lt;element name="printXmlResponse" type="{urn:connectship-com:ampcore}PrintXmlResponse"/>
 *         &lt;element name="rateResponse" type="{urn:connectship-com:ampcore}RateResponse"/>
 *         &lt;element name="reprocessResponse" type="{urn:connectship-com:ampcore}ReprocessResponse"/>
 *         &lt;element name="searchResponse" type="{urn:connectship-com:ampcore}SearchResponse"/>
 *         &lt;element name="shipResponse" type="{urn:connectship-com:ampcore}ShipResponse"/>
 *         &lt;element name="transmitResponse" type="{urn:connectship-com:ampcore}TransmitResponse"/>
 *         &lt;element name="validateAddressResponse" type="{urn:connectship-com:ampcore}ValidateAddressResponse"/>
 *         &lt;element name="voidPackagesResponse" type="{urn:connectship-com:ampcore}VoidPackagesResponse"/>
 *       &lt;/choice>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}asyncCorrelationData"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompoundResult", propOrder = {
    "closeOutResponseOrCreateGroupResponseOrCustomOperationResponse"
})
public class CompoundResult {

    @XmlElements({
        @XmlElement(name = "transmitResponse", type = TransmitResponse.class),
        @XmlElement(name = "modifyPackagesResponse", type = ModifyPackagesResponse.class),
        @XmlElement(name = "customOperationResponse", type = CustomOperationResponse.class),
        @XmlElement(name = "listStocksResponse", type = ListStocksResponse.class),
        @XmlElement(name = "listCarriersResponse", type = ListCarriersResponse.class),
        @XmlElement(name = "listPaymentTypesResponse", type = ListPaymentTypesResponse.class),
        @XmlElement(name = "voidPackagesResponse", type = VoidPackagesResponse.class),
        @XmlElement(name = "modifyGroupResponse", type = ModifyGroupResponse.class),
        @XmlElement(name = "listCloseOutItemsResponse", type = ListCloseOutItemsResponse.class),
        @XmlElement(name = "createGroupResponse", type = CreateGroupResponse.class),
        @XmlElement(name = "reprocessResponse", type = ReprocessResponse.class),
        @XmlElement(name = "listGroupingsResponse", type = ListGroupingsResponse.class),
        @XmlElement(name = "listServicesResponse", type = ListServicesResponse.class),
        @XmlElement(name = "deleteTransmitItemsResponse", type = DeleteTransmitItemsResponse.class),
        @XmlElement(name = "getGroupResponse", type = GetGroupResponse.class),
        @XmlElement(name = "rateResponse", type = RateResponse.class),
        @XmlElement(name = "printResponse", type = PrintResponse.class),
        @XmlElement(name = "printXmlResponse", type = PrintXmlResponse.class),
        @XmlElement(name = "closeOutResponse", type = CloseOutResponse.class),
        @XmlElement(name = "listPackagingTypesResponse", type = ListPackagingTypesResponse.class),
        @XmlElement(name = "shipResponse", type = ShipResponse.class),
        @XmlElement(name = "searchResponse", type = SearchResponse.class),
        @XmlElement(name = "listTransmitItemsResponse", type = ListTransmitItemsResponse.class),
        @XmlElement(name = "validateAddressResponse", type = ValidateAddressResponse.class),
        @XmlElement(name = "listLocalPortsResponse", type = ListLocalPortsResponse.class),
        @XmlElement(name = "listIncotermsResponse", type = ListIncotermsResponse.class),
        @XmlElement(name = "deleteShipFilesResponse", type = DeleteShipFilesResponse.class),
        @XmlElement(name = "listGroupsResponse", type = ListGroupsResponse.class),
        @XmlElement(name = "modifyContainerResponse", type = ModifyContainerResponse.class),
        @XmlElement(name = "listUnitsResponse", type = ListUnitsResponse.class),
        @XmlElement(name = "listDocumentsResponse", type = ListDocumentsResponse.class),
        @XmlElement(name = "listCountriesResponse", type = ListCountriesResponse.class),
        @XmlElement(name = "listPrinterDevicesResponse", type = ListPrinterDevicesResponse.class),
        @XmlElement(name = "listWindowsPrintersResponse", type = ListWindowsPrintersResponse.class),
        @XmlElement(name = "listShipFilesResponse", type = ListShipFilesResponse.class),
        @XmlElement(name = "listDocumentFormatsResponse", type = ListDocumentFormatsResponse.class),
        @XmlElement(name = "listShippersResponse", type = ListShippersResponse.class),
        @XmlElement(name = "listCurrenciesResponse", type = ListCurrenciesResponse.class)
    })
    protected List<Object> closeOutResponseOrCreateGroupResponseOrCustomOperationResponse;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String asyncCorrelationData;

    /**
     * Gets the value of the closeOutResponseOrCreateGroupResponseOrCustomOperationResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the closeOutResponseOrCreateGroupResponseOrCustomOperationResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCloseOutResponseOrCreateGroupResponseOrCustomOperationResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransmitResponse }
     * {@link ModifyPackagesResponse }
     * {@link CustomOperationResponse }
     * {@link ListStocksResponse }
     * {@link ListCarriersResponse }
     * {@link ListPaymentTypesResponse }
     * {@link VoidPackagesResponse }
     * {@link ModifyGroupResponse }
     * {@link ListCloseOutItemsResponse }
     * {@link CreateGroupResponse }
     * {@link ReprocessResponse }
     * {@link ListGroupingsResponse }
     * {@link ListServicesResponse }
     * {@link DeleteTransmitItemsResponse }
     * {@link GetGroupResponse }
     * {@link RateResponse }
     * {@link PrintResponse }
     * {@link PrintXmlResponse }
     * {@link CloseOutResponse }
     * {@link ListPackagingTypesResponse }
     * {@link ShipResponse }
     * {@link SearchResponse }
     * {@link ListTransmitItemsResponse }
     * {@link ValidateAddressResponse }
     * {@link ListLocalPortsResponse }
     * {@link ListIncotermsResponse }
     * {@link DeleteShipFilesResponse }
     * {@link ListGroupsResponse }
     * {@link ModifyContainerResponse }
     * {@link ListUnitsResponse }
     * {@link ListDocumentsResponse }
     * {@link ListCountriesResponse }
     * {@link ListPrinterDevicesResponse }
     * {@link ListWindowsPrintersResponse }
     * {@link ListShipFilesResponse }
     * {@link ListDocumentFormatsResponse }
     * {@link ListShippersResponse }
     * {@link ListCurrenciesResponse }
     * 
     * 
     */
    public List<Object> getCloseOutResponseOrCreateGroupResponseOrCustomOperationResponse() {
        if (closeOutResponseOrCreateGroupResponseOrCustomOperationResponse == null) {
            closeOutResponseOrCreateGroupResponseOrCustomOperationResponse = new ArrayList<Object>();
        }
        return this.closeOutResponseOrCreateGroupResponseOrCustomOperationResponse;
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

}
