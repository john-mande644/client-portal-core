package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Thes are filters that could result in accept/deny/pending action.
 * <p/>
 * <p/>
 * <p>Java class for FMFDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="FMFDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="AcceptFilters" type="{urn:ebay:apis:eBLBaseComponents}RiskFilterListType" minOccurs="0"/>
 *         &lt;element name="PendingFilters" type="{urn:ebay:apis:eBLBaseComponents}RiskFilterListType" minOccurs="0"/>
 *         &lt;element name="DenyFilters" type="{urn:ebay:apis:eBLBaseComponents}RiskFilterListType" minOccurs="0"/>
 *         &lt;element name="ReportFilters" type="{urn:ebay:apis:eBLBaseComponents}RiskFilterListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FMFDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "acceptFilters",
        "pendingFilters",
        "denyFilters",
        "reportFilters"
})
public class FMFDetailsType
{

    @XmlElement(name = "AcceptFilters", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected RiskFilterListType acceptFilters;
    @XmlElement(name = "PendingFilters", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected RiskFilterListType pendingFilters;
    @XmlElement(name = "DenyFilters", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected RiskFilterListType denyFilters;
    @XmlElement(name = "ReportFilters", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected RiskFilterListType reportFilters;

    /**
     * Gets the value of the acceptFilters property.
     *
     * @return possible object is
     *         {@link RiskFilterListType }
     */
    public RiskFilterListType getAcceptFilters()
    {
        return acceptFilters;
    }

    /**
     * Sets the value of the acceptFilters property.
     *
     * @param value allowed object is
     *              {@link RiskFilterListType }
     */
    public void setAcceptFilters(RiskFilterListType value)
    {
        this.acceptFilters = value;
    }

    /**
     * Gets the value of the pendingFilters property.
     *
     * @return possible object is
     *         {@link RiskFilterListType }
     */
    public RiskFilterListType getPendingFilters()
    {
        return pendingFilters;
    }

    /**
     * Sets the value of the pendingFilters property.
     *
     * @param value allowed object is
     *              {@link RiskFilterListType }
     */
    public void setPendingFilters(RiskFilterListType value)
    {
        this.pendingFilters = value;
    }

    /**
     * Gets the value of the denyFilters property.
     *
     * @return possible object is
     *         {@link RiskFilterListType }
     */
    public RiskFilterListType getDenyFilters()
    {
        return denyFilters;
    }

    /**
     * Sets the value of the denyFilters property.
     *
     * @param value allowed object is
     *              {@link RiskFilterListType }
     */
    public void setDenyFilters(RiskFilterListType value)
    {
        this.denyFilters = value;
    }

    /**
     * Gets the value of the reportFilters property.
     *
     * @return possible object is
     *         {@link RiskFilterListType }
     */
    public RiskFilterListType getReportFilters()
    {
        return reportFilters;
    }

    /**
     * Sets the value of the reportFilters property.
     *
     * @param value allowed object is
     *              {@link RiskFilterListType }
     */
    public void setReportFilters(RiskFilterListType value)
    {
        this.reportFilters = value;
    }

}
