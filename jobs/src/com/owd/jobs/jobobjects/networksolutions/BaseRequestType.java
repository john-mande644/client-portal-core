
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import java.math.BigDecimal;


/**
 * <p>Java class for BaseRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="RequestId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseRequestType", propOrder = {
    "requestId",
    "version"
})
@XmlSeeAlso({
    UpdateCategoryRequestType.class,
    UpdateManufacturerRequestType.class,
    ReadSiteSettingRequestType.class,
    UpdateWarehouseRequestType.class,
    DeleteProductRequestType.class,
    DeleteOrderStatusRequestType.class,
    DeleteCustomerRequestType.class,
    UpdateOrderRequestType.class,
    GetUserTokenRequestType.class,
    CreateWarehouseRequestType.class,
    UpdateAttributeRequestType.class,
    GetUserKeyRequestType.class,
    PerformMultipleRequestType.class,
    UpdateInventoryRequestType.class,
    CreateCustomerRequestType.class,
    CreateAttributeRequestType.class,
    CreatePriceLevelRequestType.class,
    DeletePriceLevelRequestType.class,
    UpdateGiftCertificateRequestType.class,
    DeleteAttributeRequestType.class,
    CreateProductRequestType.class,
    DeleteGiftCertificateRequestType.class,
    UpdatePriceLevelRequestType.class,
    CreateManufacturerRequestType.class,
    DeleteManufacturerRequestType.class,
    DeleteWarehouseRequestType.class,
    UpdateProductRequestType.class,
    CreateGiftCertificateRequestType.class,
    UpdateCustomerRequestType.class,
    CreateOrderStatusRequestType.class,
    UpdateOrderStatusRequestType.class,
    CreateCategoryRequestType.class,
    DeleteCategoryRequestType.class,
    ReadBaseRequestType.class
})
public class BaseRequestType {

    @XmlElement(name = "RequestId")
    protected String requestId;
    @XmlElement(name = "Version")
    protected BigDecimal version;

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestId(String value) {
        this.requestId = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }

}
