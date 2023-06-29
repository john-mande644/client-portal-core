
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for BaseResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="RequestId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{urn:networksolutions:apis}StatusCodeType"/>
 *         &lt;element name="TimeStamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="StoreUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorList" type="{urn:networksolutions:apis}ErrorType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "BaseResponseType", propOrder = {
    "requestId",
    "status",
    "timeStamp",
    "storeUrl",
    "errorList",
    "version"
})
@XmlSeeAlso({
    UpdateCategoryResponseType.class,
    ReadSiteSettingResponseType.class,
    UpdateManufacturerResponseType.class,
    CreatePriceLevelResponseType.class,
    CreateProductResponseType.class,
    DeleteProductResponseType.class,
    CreateManufacturerResponseType.class,
    UpdateProductResponseType.class,
    CreateAttributeResponseType.class,
    GetUserTokenResponseType.class,
    UpdateAttributeResponseType.class,
    UpdateOrderStatusResponseType.class,
    DeleteManufacturerResponseType.class,
    DeleteGiftCertificateResponseType.class,
    CreateCustomerResponseType.class,
    UpdateGiftCertificateResponseType.class,
    DeleteOrderStatusResponseType.class,
    DeleteWarehouseResponseType.class,
    UpdatePriceLevelResponseType.class,
    UpdateOrderResponseType.class,
    DeleteCategoryResponseType.class,
    CreateCategoryResponseType.class,
    DeleteCustomerResponseType.class,
    UpdateWarehouseResponseType.class,
    UpdateCustomerResponseType.class,
    DeleteAttributeResponseType.class,
    DeletePriceLevelResponseType.class,
    GetUserKeyResponseType.class,
    PerformMultipleResponseType.class,
    CreateGiftCertificateResponseType.class,
    CreateOrderStatusResponseType.class,
    UpdateInventoryResponseType.class,
    CreateWarehouseResponseType.class,
    ReadBaseResponseType.class
})
public class BaseResponseType {

    @XmlElement(name = "RequestId")
    protected String requestId;
    @XmlElement(name = "Status", required = true)
    protected StatusCodeType status;
    @XmlElement(name = "TimeStamp", required = true)
    protected XMLGregorianCalendar timeStamp;
    @XmlElement(name = "StoreUrl")
    protected String storeUrl;
    @XmlElement(name = "ErrorList")
    protected List<ErrorType> errorList;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusCodeType }
     *     
     */
    public StatusCodeType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusCodeType }
     *     
     */
    public void setStatus(StatusCodeType value) {
        this.status = value;
    }

    /**
     * Gets the value of the timeStamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the storeUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreUrl() {
        return storeUrl;
    }

    /**
     * Sets the value of the storeUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreUrl(String value) {
        this.storeUrl = value;
    }

    /**
     * Gets the value of the errorList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorType }
     * 
     * 
     */
    public List<ErrorType> getErrorList() {
        if (errorList == null) {
            errorList = new ArrayList<ErrorType>();
        }
        return this.errorList;
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
