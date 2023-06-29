package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PaginationResultType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="PaginationResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="TotalNumberOfPages" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="TotalNumberOfEntries" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaginationResultType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "totalNumberOfPages",
        "totalNumberOfEntries"
})
public class PaginationResultType
{

    @XmlElement(name = "TotalNumberOfPages", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer totalNumberOfPages;
    @XmlElement(name = "TotalNumberOfEntries", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected Integer totalNumberOfEntries;

    /**
     * Gets the value of the totalNumberOfPages property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getTotalNumberOfPages()
    {
        return totalNumberOfPages;
    }

    /**
     * Sets the value of the totalNumberOfPages property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setTotalNumberOfPages(Integer value)
    {
        this.totalNumberOfPages = value;
    }

    /**
     * Gets the value of the totalNumberOfEntries property.
     *
     * @return possible object is
     *         {@link Integer }
     */
    public Integer getTotalNumberOfEntries()
    {
        return totalNumberOfEntries;
    }

    /**
     * Sets the value of the totalNumberOfEntries property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setTotalNumberOfEntries(Integer value)
    {
        this.totalNumberOfEntries = value;
    }

}
