
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.1-02/02/2007 03:56 AM(vivekp)-FCS
 * Generated source version: 2.1
 * 
 */
@WebService(name = "NetSolEcomServiceSoap", targetNamespace = "urn:networksolutions:apis")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NetSolEcomServiceSoap {


    /**
     * 
     * @param createWarehouseRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.CreateWarehouseResponseType
     */
    @WebMethod(operationName = "CreateWarehouse", action = "http://networksolutions.com/CreateWarehouse")
    @WebResult(name = "CreateWarehouseResponse", targetNamespace = "urn:networksolutions:apis", partName = "CreateWarehouseResult")
    public CreateWarehouseResponseType createWarehouse(
        @WebParam(name = "CreateWarehouseRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateWarehouseRequest")
        CreateWarehouseRequestType createWarehouseRequest);

    /**
     * 
     * @param readWarehouseRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadWarehouseResponseType
     */
    @WebMethod(operationName = "ReadWarehouse", action = "http://networksolutions.com/ReadWarehouse")
    @WebResult(name = "ReadWarehouseResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadWarehouseResult")
    public ReadWarehouseResponseType readWarehouse(
        @WebParam(name = "ReadWarehouseRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadWarehouseRequest")
        ReadWarehouseRequestType readWarehouseRequest);

    /**
     * 
     * @param updateWarehouseRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateWarehouseResponseType
     */
    @WebMethod(operationName = "UpdateWarehouse", action = "http://networksolutions.com/UpdateWarehouse")
    @WebResult(name = "UpdateWarehouseResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateWarehouseResult")
    public UpdateWarehouseResponseType updateWarehouse(
        @WebParam(name = "UpdateWarehouseRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateWarehouseRequest")
        UpdateWarehouseRequestType updateWarehouseRequest);

    /**
     * 
     * @param createWarehouseManager
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.DeleteWarehouseResponseType
     */
    @WebMethod(operationName = "DeleteWarehouse", action = "http://networksolutions.com/DeleteWarehouse")
    @WebResult(name = "DeleteWarehouseResponse", targetNamespace = "urn:networksolutions:apis", partName = "DeleteWarehouseResult")
    public DeleteWarehouseResponseType deleteWarehouse(
        @WebParam(name = "DeleteWarehouseRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateWarehouseManager")
        DeleteWarehouseRequestType createWarehouseManager);

    /**
     * 
     * @param createManufacturerRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.CreateManufacturerResponseType
     */
    @WebMethod(operationName = "CreateManufacturer", action = "http://networksolutions.com/CreateManufacturer")
    @WebResult(name = "CreateManufacturerResponse", targetNamespace = "urn:networksolutions:apis", partName = "CreateManufacturerResult")
    public CreateManufacturerResponseType createManufacturer(
        @WebParam(name = "CreateManufacturerRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateManufacturerRequest")
        CreateManufacturerRequestType createManufacturerRequest);

    /**
     * 
     * @param readManufacturerRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadManufacturerResponseType
     */
    @WebMethod(operationName = "ReadManufacturer", action = "http://networksolutions.com/ReadManufacturer")
    @WebResult(name = "ReadManufacturerResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadManufacturerResult")
    public ReadManufacturerResponseType readManufacturer(
        @WebParam(name = "ReadManufacturerRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadManufacturerRequest")
        ReadManufacturerRequestType readManufacturerRequest);

    /**
     * 
     * @param updateManufacturerRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateManufacturerResponseType
     */
    @WebMethod(operationName = "UpdateManufacturer", action = "http://networksolutions.com/UpdateManufacturer")
    @WebResult(name = "UpdateManufacturerResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateManufacturerResult")
    public UpdateManufacturerResponseType updateManufacturer(
        @WebParam(name = "UpdateManufacturerRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateManufacturerRequest")
        UpdateManufacturerRequestType updateManufacturerRequest);

    /**
     * 
     * @param createManufacturerManager
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.DeleteManufacturerResponseType
     */
    @WebMethod(operationName = "DeleteManufacturer", action = "http://networksolutions.com/DeleteManufacturer")
    @WebResult(name = "DeleteManufacturerResponse", targetNamespace = "urn:networksolutions:apis", partName = "DeleteManufacturerResult")
    public DeleteManufacturerResponseType deleteManufacturer(
        @WebParam(name = "DeleteManufacturerRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateManufacturerManager")
        DeleteManufacturerRequestType createManufacturerManager);

    /**
     * 
     * @param createCategoryRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.CreateCategoryResponseType
     */
    @WebMethod(operationName = "CreateCategory", action = "http://networksolutions.com/CreateCategory")
    @WebResult(name = "CreateCategoryResponse", targetNamespace = "urn:networksolutions:apis", partName = "CreateCategoryResult")
    public CreateCategoryResponseType createCategory(
        @WebParam(name = "CreateCategoryRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateCategoryRequest")
        CreateCategoryRequestType createCategoryRequest);

    /**
     * 
     * @param readCategoryRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadCategoryResponseType
     */
    @WebMethod(operationName = "ReadCategory", action = "http://networksolutions.com/ReadCategory")
    @WebResult(name = "ReadCategoryResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadCategoryResult")
    public ReadCategoryResponseType readCategory(
        @WebParam(name = "ReadCategoryRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadCategoryRequest")
        ReadCategoryRequestType readCategoryRequest);

    /**
     * 
     * @param updateCategoryRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateCategoryResponseType
     */
    @WebMethod(operationName = "UpdateCategory", action = "http://networksolutions.com/UpdateCategory")
    @WebResult(name = "UpdateCategoryResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateCategoryResult")
    public UpdateCategoryResponseType updateCategory(
        @WebParam(name = "UpdateCategoryRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateCategoryRequest")
        UpdateCategoryRequestType updateCategoryRequest);

    /**
     * 
     * @param createCategoryManager
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.DeleteCategoryResponseType
     */
    @WebMethod(operationName = "DeleteCategory", action = "http://networksolutions.com/DeleteCategory")
    @WebResult(name = "DeleteCategoryResponse", targetNamespace = "urn:networksolutions:apis", partName = "DeleteCategoryResult")
    public DeleteCategoryResponseType deleteCategory(
        @WebParam(name = "DeleteCategoryRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateCategoryManager")
        DeleteCategoryRequestType createCategoryManager);

    /**
     * 
     * @param createProductRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.CreateProductResponseType
     */
    @WebMethod(operationName = "CreateProduct", action = "http://networksolutions.com/CreateProduct")
    @WebResult(name = "CreateProductResponse", targetNamespace = "urn:networksolutions:apis", partName = "CreateProductResult")
    public CreateProductResponseType createProduct(
        @WebParam(name = "CreateProductRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateProductRequest")
        CreateProductRequestType createProductRequest);

    /**
     * 
     * @param readProductRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadProductResponseType
     */
    @WebMethod(operationName = "ReadProduct", action = "http://networksolutions.com/ReadProduct")
    @WebResult(name = "ReadProductResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadProductResult")
    public ReadProductResponseType readProduct(
        @WebParam(name = "ReadProductRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadProductRequest")
        ReadProductRequestType readProductRequest);

    /**
     * 
     * @param updateProductRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateProductResponseType
     */
    @WebMethod(operationName = "UpdateProduct", action = "http://networksolutions.com/UpdateProduct")
    @WebResult(name = "UpdateProductResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateProductResult")
    public UpdateProductResponseType updateProduct(
        @WebParam(name = "UpdateProductRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateProductRequest")
        UpdateProductRequestType updateProductRequest);

    /**
     * 
     * @param createProductManager
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.DeleteProductResponseType
     */
    @WebMethod(operationName = "DeleteProduct", action = "http://networksolutions.com/DeleteProduct")
    @WebResult(name = "DeleteProductResponse", targetNamespace = "urn:networksolutions:apis", partName = "DeleteProductResult")
    public DeleteProductResponseType deleteProduct(
        @WebParam(name = "DeleteProductRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateProductManager")
        DeleteProductRequestType createProductManager);

    /**
     * 
     * @param createCustomerRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.CreateCustomerResponseType
     */
    @WebMethod(operationName = "CreateCustomer", action = "http://networksolutions.com/CreateCustomer")
    @WebResult(name = "CreateCustomerResponse", targetNamespace = "urn:networksolutions:apis", partName = "CreateCustomerResult")
    public CreateCustomerResponseType createCustomer(
        @WebParam(name = "CreateCustomerRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateCustomerRequest")
        CreateCustomerRequestType createCustomerRequest);

    /**
     * 
     * @param readCustomerRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadCustomerResponseType
     */
    @WebMethod(operationName = "ReadCustomer", action = "http://networksolutions.com/ReadCustomer")
    @WebResult(name = "ReadCustomerResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadCustomerResult")
    public ReadCustomerResponseType readCustomer(
        @WebParam(name = "ReadCustomerRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadCustomerRequest")
        ReadCustomerRequestType readCustomerRequest);

    /**
     * 
     * @param updateCustomerRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateCustomerResponseType
     */
    @WebMethod(operationName = "UpdateCustomer", action = "http://networksolutions.com/UpdateCustomer")
    @WebResult(name = "UpdateCustomerResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateCustomerResult")
    public UpdateCustomerResponseType updateCustomer(
        @WebParam(name = "UpdateCustomerRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateCustomerRequest")
        UpdateCustomerRequestType updateCustomerRequest);

    /**
     * 
     * @param createCustomerManager
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.DeleteCustomerResponseType
     */
    @WebMethod(operationName = "DeleteCustomer", action = "http://networksolutions.com/DeleteCustomer")
    @WebResult(name = "DeleteCustomerResponse", targetNamespace = "urn:networksolutions:apis", partName = "DeleteCustomerResult")
    public DeleteCustomerResponseType deleteCustomer(
        @WebParam(name = "DeleteCustomerRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateCustomerManager")
        DeleteCustomerRequestType createCustomerManager);

    /**
     * 
     * @param readOrderRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadOrderResponseType
     */
    @WebMethod(operationName = "ReadOrder", action = "http://networksolutions.com/ReadOrder")
    @WebResult(name = "ReadOrderResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadOrderResult")
    public ReadOrderResponseType readOrder(
        @WebParam(name = "ReadOrderRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadOrderRequest")
        ReadOrderRequestType readOrderRequest);

    /**
     * 
     * @param updateOrderRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateOrderResponseType
     */
    @WebMethod(operationName = "UpdateOrder", action = "http://networksolutions.com/UpdateOrder")
    @WebResult(name = "UpdateOrderResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateOrderResult")
    public UpdateOrderResponseType updateOrder(
        @WebParam(name = "UpdateOrderRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateOrderRequest")
        UpdateOrderRequestType updateOrderRequest);

    /**
     * 
     * @param createOrderStatusRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.CreateOrderStatusResponseType
     */
    @WebMethod(operationName = "CreateOrderStatus", action = "http://networksolutions.com/CreateOrderStatus")
    @WebResult(name = "CreateOrderStatusResponse", targetNamespace = "urn:networksolutions:apis", partName = "CreateOrderStatusResult")
    public CreateOrderStatusResponseType createOrderStatus(
        @WebParam(name = "CreateOrderStatusRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateOrderStatusRequest")
        CreateOrderStatusRequestType createOrderStatusRequest);

    /**
     * 
     * @param readOrderStatusRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadOrderStatusResponseType
     */
    @WebMethod(operationName = "ReadOrderStatus", action = "http://networksolutions.com/ReadOrderStatus")
    @WebResult(name = "ReadOrderStatusResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadOrderStatusResult")
    public ReadOrderStatusResponseType readOrderStatus(
        @WebParam(name = "ReadOrderStatusRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadOrderStatusRequest")
        ReadOrderStatusRequestType readOrderStatusRequest);

    /**
     * 
     * @param updateOrderStatusRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateOrderStatusResponseType
     */
    @WebMethod(operationName = "UpdateOrderStatus", action = "http://networksolutions.com/UpdateOrderStatus")
    @WebResult(name = "UpdateOrderStatusResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateOrderStatusResult")
    public UpdateOrderStatusResponseType updateOrderStatus(
        @WebParam(name = "UpdateOrderStatusRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateOrderStatusRequest")
        UpdateOrderStatusRequestType updateOrderStatusRequest);

    /**
     * 
     * @param createOrderStatusManager
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.DeleteOrderStatusResponseType
     */
    @WebMethod(operationName = "DeleteOrderStatus", action = "http://networksolutions.com/DeleteOrderStatus")
    @WebResult(name = "DeleteOrderStatusResponse", targetNamespace = "urn:networksolutions:apis", partName = "DeleteOrderStatusResult")
    public DeleteOrderStatusResponseType deleteOrderStatus(
        @WebParam(name = "DeleteOrderStatusRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateOrderStatusManager")
        DeleteOrderStatusRequestType createOrderStatusManager);


    /**
     * 
     * @param createPriceLevelRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.CreatePriceLevelResponseType
     */
    @WebMethod(operationName = "CreatePriceLevel", action = "http://networksolutions.com/CreatePriceLevel")
    @WebResult(name = "CreatePriceLevelResponse", targetNamespace = "urn:networksolutions:apis", partName = "CreatePriceLevelResult")
    public CreatePriceLevelResponseType createPriceLevel(
        @WebParam(name = "CreatePriceLevelRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreatePriceLevelRequest")
        CreatePriceLevelRequestType createPriceLevelRequest);

    /**
     * 
     * @param readPriceLevelRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadPriceLevelResponseType
     */
    @WebMethod(operationName = "ReadPriceLevel", action = "http://networksolutions.com/ReadPriceLevel")
    @WebResult(name = "ReadPriceLevelResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadPriceLevelResult")
    public ReadPriceLevelResponseType readPriceLevel(
        @WebParam(name = "ReadPriceLevelRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadPriceLevelRequest")
        ReadPriceLevelRequestType readPriceLevelRequest);

    /**
     * 
     * @param updatePriceLevelRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdatePriceLevelResponseType
     */
    @WebMethod(operationName = "UpdatePriceLevel", action = "http://networksolutions.com/UpdatePriceLevel")
    @WebResult(name = "UpdatePriceLevelResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdatePriceLevelResult")
    public UpdatePriceLevelResponseType updatePriceLevel(
        @WebParam(name = "UpdatePriceLevelRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdatePriceLevelRequest")
        UpdatePriceLevelRequestType updatePriceLevelRequest);

    /**
     * 
     * @param createPriceLevelManager
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.DeletePriceLevelResponseType
     */
    @WebMethod(operationName = "DeletePriceLevel", action = "http://networksolutions.com/DeletePriceLevel")
    @WebResult(name = "DeletePriceLevelResponse", targetNamespace = "urn:networksolutions:apis", partName = "DeletePriceLevelResult")
    public DeletePriceLevelResponseType deletePriceLevel(
        @WebParam(name = "DeletePriceLevelRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreatePriceLevelManager")
        DeletePriceLevelRequestType createPriceLevelManager);

    /**
     * 
     * @param createAttributeRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.CreateAttributeResponseType
     */
    @WebMethod(operationName = "CreateAttribute", action = "http://networksolutions.com/CreateAttribute")
    @WebResult(name = "CreateAttributeResponse", targetNamespace = "urn:networksolutions:apis", partName = "CreateAttributeResult")
    public CreateAttributeResponseType createAttribute(
        @WebParam(name = "CreateAttributeRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateAttributeRequest")
        CreateAttributeRequestType createAttributeRequest);

    /**
     * 
     * @param readAttributeRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadAttributeResponseType
     */
    @WebMethod(operationName = "ReadAttribute", action = "http://networksolutions.com/ReadAttribute")
    @WebResult(name = "ReadAttributeResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadAttributeResult")
    public ReadAttributeResponseType readAttribute(
        @WebParam(name = "ReadAttributeRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadAttributeRequest")
        ReadAttributeRequestType readAttributeRequest);

    /**
     * 
     * @param updateAttributeRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateAttributeResponseType
     */
    @WebMethod(operationName = "UpdateAttribute", action = "http://networksolutions.com/UpdateAttribute")
    @WebResult(name = "UpdateAttributeResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateAttributeResult")
    public UpdateAttributeResponseType updateAttribute(
        @WebParam(name = "UpdateAttributeRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateAttributeRequest")
        UpdateAttributeRequestType updateAttributeRequest);

    /**
     * 
     * @param createAttributeManager
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.DeleteAttributeResponseType
     */
    @WebMethod(operationName = "DeleteAttribute", action = "http://networksolutions.com/DeleteAttribute")
    @WebResult(name = "DeleteAttributeResponse", targetNamespace = "urn:networksolutions:apis", partName = "DeleteAttributeResult")
    public DeleteAttributeResponseType deleteAttribute(
        @WebParam(name = "DeleteAttributeRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateAttributeManager")
        DeleteAttributeRequestType createAttributeManager);

    /**
     * 
     * @param updateInventoryRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateInventoryResponseType
     */
    @WebMethod(operationName = "UpdateInventory", action = "http://networksolutions.com/UpdateInventory")
    @WebResult(name = "UpdateInventoryResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateInventoryResult")
    public UpdateInventoryResponseType updateInventory(
        @WebParam(name = "UpdateInventoryRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateInventoryRequest")
        UpdateInventoryRequestType updateInventoryRequest);

    /**
     * 
     * @param performMultipleRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.PerformMultipleResponseType
     */
    @WebMethod(operationName = "PerformMultiple", action = "http://networksolutions.com/PerformMultiple")
    @WebResult(name = "PerformMultipleResponse", targetNamespace = "urn:networksolutions:apis", partName = "PerformMultipleResult")
    public PerformMultipleResponseType performMultiple(
        @WebParam(name = "PerformMultipleRequest", targetNamespace = "urn:networksolutions:apis", partName = "PerformMultipleRequest")
        PerformMultipleRequestType performMultipleRequest);

    /**
     * 
     * @param readSiteSettingRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadSiteSettingResponseType
     */
    @WebMethod(operationName = "ReadSiteSetting", action = "http://networksolutions.com/ReadSiteSetting")
    @WebResult(name = "ReadSiteSettingResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadSiteSettingResult")
    public ReadSiteSettingResponseType readSiteSetting(
        @WebParam(name = "ReadSiteSettingRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadSiteSettingRequest")
        ReadSiteSettingRequestType readSiteSettingRequest);

    /**
     * 
     * @param getUserTokenRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.GetUserTokenResponseType
     */
    @WebMethod(operationName = "GetUserToken", action = "http://networksolutions.com/GetUserToken")
    @WebResult(name = "GetUserTokenResponse", targetNamespace = "urn:networksolutions:apis", partName = "GetUserTokenResult")
    public GetUserTokenResponseType getUserToken(
        @WebParam(name = "GetUserTokenRequest", targetNamespace = "urn:networksolutions:apis", partName = "GetUserTokenRequest")
        GetUserTokenRequestType getUserTokenRequest);

    /**
     * 
     * @param getUserKeyRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.GetUserKeyResponseType
     */
    @WebMethod(operationName = "GetUserKey", action = "http://networksolutions.com/GetUserKey")
    @WebResult(name = "GetUserKeyResponse", targetNamespace = "urn:networksolutions:apis", partName = "GetUserKeyResult")
    public GetUserKeyResponseType getUserKey(
        @WebParam(name = "GetUserKeyRequest", targetNamespace = "urn:networksolutions:apis", partName = "GetUserKeyRequest")
        GetUserKeyRequestType getUserKeyRequest);

    /**
     * 
     * @param createGiftCertificateRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.CreateGiftCertificateResponseType
     */
    @WebMethod(operationName = "CreateGiftCertificate", action = "http://networksolutions.com/CreateGiftCertificate")
    @WebResult(name = "CreateGiftCertificateResponse", targetNamespace = "urn:networksolutions:apis", partName = "CreateGiftCertificateResult")
    public CreateGiftCertificateResponseType createGiftCertificate(
        @WebParam(name = "CreateGiftCertificateRequest", targetNamespace = "urn:networksolutions:apis", partName = "CreateGiftCertificateRequest")
        CreateGiftCertificateRequestType createGiftCertificateRequest);

    /**
     * 
     * @param readGiftCertificateRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.ReadGiftCertificateResponseType
     */
    @WebMethod(operationName = "ReadGiftCertificate", action = "http://networksolutions.com/ReadGiftCertificate")
    @WebResult(name = "ReadGiftCertificateResponse", targetNamespace = "urn:networksolutions:apis", partName = "ReadGiftCertificateResult")
    public ReadGiftCertificateResponseType readGiftCertificate(
        @WebParam(name = "ReadGiftCertificateRequest", targetNamespace = "urn:networksolutions:apis", partName = "ReadGiftCertificateRequest")
        ReadGiftCertificateRequestType readGiftCertificateRequest);

    /**
     * 
     * @param updateGiftCertificateRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.UpdateGiftCertificateResponseType
     */
    @WebMethod(operationName = "UpdateGiftCertificate", action = "http://networksolutions.com/UpdateGiftCertificate")
    @WebResult(name = "UpdateGiftCertificateResponse", targetNamespace = "urn:networksolutions:apis", partName = "UpdateGiftCertificateResult")
    public UpdateGiftCertificateResponseType updateGiftCertificate(
        @WebParam(name = "UpdateGiftCertificateRequest", targetNamespace = "urn:networksolutions:apis", partName = "UpdateGiftCertificateRequest")
        UpdateGiftCertificateRequestType updateGiftCertificateRequest);

    /**
     * 
     * @param deleteGiftCertificateRequest
     * @return
     *     returns com.owd.jobs.jobobjects.networksolutions.DeleteGiftCertificateResponseType
     */
    @WebMethod(operationName = "DeleteGiftCertificate", action = "http://networksolutions.com/DeleteGiftCertificate")
    @WebResult(name = "DeleteGiftCertificateResponse", targetNamespace = "urn:networksolutions:apis", partName = "DeleteGiftCertificateResult")
    public DeleteGiftCertificateResponseType deleteGiftCertificate(
        @WebParam(name = "DeleteGiftCertificateRequest", targetNamespace = "urn:networksolutions:apis", partName = "DeleteGiftCertificateRequest")
        DeleteGiftCertificateRequestType deleteGiftCertificateRequest);

}
