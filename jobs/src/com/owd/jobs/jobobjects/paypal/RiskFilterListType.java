package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Details of Risk Filter.
 * <p/>
 * <p/>
 * <p>Java class for RiskFilterListType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="RiskFilterListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Filters" type="{urn:ebay:apis:eBLBaseComponents}RiskFilterDetailsType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiskFilterListType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "filters"
})
public class RiskFilterListType
{

    @XmlElement(name = "Filters", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected List<RiskFilterDetailsType> filters;

    /**
     * Gets the value of the filters property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filters property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilters().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link RiskFilterDetailsType }
     */
    public List<RiskFilterDetailsType> getFilters()
    {
        if (filters == null)
        {
            filters = new ArrayList<RiskFilterDetailsType>();
        }
        return this.filters;
    }

}
