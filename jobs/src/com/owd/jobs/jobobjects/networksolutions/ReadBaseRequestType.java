
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ReadBaseRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadBaseRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="DetailSize" type="{urn:networksolutions:apis}SizeCodeType" minOccurs="0"/>
 *         &lt;element name="PageRequest" type="{urn:networksolutions:apis}PaginationType" minOccurs="0"/>
 *         &lt;element name="FilterList" type="{urn:networksolutions:apis}FilterType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SortList" type="{urn:networksolutions:apis}SortType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadBaseRequestType", propOrder = {
    "detailSize",
    "pageRequest",
    "filterList",
    "sortList"
})
@XmlSeeAlso({
    ReadOrderRequestType.class,
    ReadAttributeRequestType.class,
    ReadPaymentRequestType.class,
    ReadWarehouseRequestType.class,
    ReadCategoryRequestType.class,
    ReadGiftCertificateRequestType.class,
    ReadProductRequestType.class,
    ReadCustomerRequestType.class,
    ReadManufacturerRequestType.class,
    ReadPriceLevelRequestType.class,
    ReadOrderStatusRequestType.class
})
public class ReadBaseRequestType
    extends BaseRequestType
{

    @XmlElement(name = "DetailSize")
    protected SizeCodeType detailSize;
    @XmlElement(name = "PageRequest")
    protected PaginationType pageRequest;
    @XmlElement(name = "FilterList")
    protected List<FilterType> filterList;
    @XmlElement(name = "SortList")
    protected List<SortType> sortList;

    /**
     * Gets the value of the detailSize property.
     * 
     * @return
     *     possible object is
     *     {@link SizeCodeType }
     *     
     */
    public SizeCodeType getDetailSize() {
        return detailSize;
    }

    /**
     * Sets the value of the detailSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link SizeCodeType }
     *     
     */
    public void setDetailSize(SizeCodeType value) {
        this.detailSize = value;
    }

    /**
     * Gets the value of the pageRequest property.
     * 
     * @return
     *     possible object is
     *     {@link PaginationType }
     *     
     */
    public PaginationType getPageRequest() {
        return pageRequest;
    }

    /**
     * Sets the value of the pageRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaginationType }
     *     
     */
    public void setPageRequest(PaginationType value) {
        this.pageRequest = value;
    }

    /**
     * Gets the value of the filterList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filterList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilterList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FilterType }
     * 
     * 
     */
    public List<FilterType> getFilterList() {
        if (filterList == null) {
            filterList = new ArrayList<FilterType>();
        }
        return this.filterList;
    }

    /**
     * Gets the value of the sortList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SortType }
     * 
     * 
     */
    public List<SortType> getSortList() {
        if (sortList == null) {
            sortList = new ArrayList<SortType>();
        }
        return this.sortList;
    }

}
