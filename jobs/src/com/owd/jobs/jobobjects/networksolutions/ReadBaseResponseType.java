
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for ReadBaseResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadBaseResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PageResponse" type="{urn:networksolutions:apis}PaginationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadBaseResponseType", propOrder = {
    "pageResponse"
})
@XmlSeeAlso({
    ReadProductResponseType.class,
    ReadCustomerResponseType.class,
    ReadOrderResponseType.class,
    ReadAttributeResponseType.class,
    ReadPriceLevelResponseType.class,
    ReadCategoryResponseType.class,
    ReadGiftCertificateResponseType.class,
    ReadManufacturerResponseType.class,
    ReadPaymentResponseType.class,
    ReadWarehouseResponseType.class,
    ReadOrderStatusResponseType.class
})
public class ReadBaseResponseType
    extends BaseResponseType
{

    @XmlElement(name = "PageResponse")
    protected PaginationType pageResponse;

    /**
     * Gets the value of the pageResponse property.
     * 
     * @return
     *     possible object is
     *     {@link PaginationType }
     *     
     */
    public PaginationType getPageResponse() {
        return pageResponse;
    }

    /**
     * Sets the value of the pageResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaginationType }
     *     
     */
    public void setPageResponse(PaginationType value) {
        this.pageResponse = value;
    }

}
