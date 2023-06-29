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
 * AID for Airlines
 * <p/>
 * <p/>
 * <p>Java class for AirlineItineraryType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="AirlineItineraryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PassengerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TravelAgencyName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TravelAgencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TicketNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssuingCarrierCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TotalFare" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="TotalTaxes" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="TotalFee" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="RestrictedTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClearingSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClearingCount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FlightDetails" type="{urn:ebay:apis:eBLBaseComponents}FlightDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AirlineItineraryType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "passengerName",
        "issueDate",
        "travelAgencyName",
        "travelAgencyCode",
        "ticketNumber",
        "issuingCarrierCode",
        "customerCode",
        "totalFare",
        "totalTaxes",
        "totalFee",
        "restrictedTicket",
        "clearingSequence",
        "clearingCount",
        "flightDetails"
})
public class AirlineItineraryType
{

    @XmlElement(name = "PassengerName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String passengerName;
    @XmlElement(name = "IssueDate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String issueDate;
    @XmlElement(name = "TravelAgencyName", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String travelAgencyName;
    @XmlElement(name = "TravelAgencyCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String travelAgencyCode;
    @XmlElement(name = "TicketNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String ticketNumber;
    @XmlElement(name = "IssuingCarrierCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String issuingCarrierCode;
    @XmlElement(name = "CustomerCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String customerCode;
    @XmlElement(name = "TotalFare", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType totalFare;
    @XmlElement(name = "TotalTaxes", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType totalTaxes;
    @XmlElement(name = "TotalFee", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType totalFee;
    @XmlElement(name = "RestrictedTicket", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String restrictedTicket;
    @XmlElement(name = "ClearingSequence", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String clearingSequence;
    @XmlElement(name = "ClearingCount", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String clearingCount;
    @XmlElement(name = "FlightDetails", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<FlightDetailsType> flightDetails;

    /**
     * Gets the value of the passengerName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPassengerName()
    {
        return passengerName;
    }

    /**
     * Sets the value of the passengerName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPassengerName(String value)
    {
        this.passengerName = value;
    }

    /**
     * Gets the value of the issueDate property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getIssueDate()
    {
        return issueDate;
    }

    /**
     * Sets the value of the issueDate property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIssueDate(String value)
    {
        this.issueDate = value;
    }

    /**
     * Gets the value of the travelAgencyName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTravelAgencyName()
    {
        return travelAgencyName;
    }

    /**
     * Sets the value of the travelAgencyName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTravelAgencyName(String value)
    {
        this.travelAgencyName = value;
    }

    /**
     * Gets the value of the travelAgencyCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTravelAgencyCode()
    {
        return travelAgencyCode;
    }

    /**
     * Sets the value of the travelAgencyCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTravelAgencyCode(String value)
    {
        this.travelAgencyCode = value;
    }

    /**
     * Gets the value of the ticketNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTicketNumber()
    {
        return ticketNumber;
    }

    /**
     * Sets the value of the ticketNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTicketNumber(String value)
    {
        this.ticketNumber = value;
    }

    /**
     * Gets the value of the issuingCarrierCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getIssuingCarrierCode()
    {
        return issuingCarrierCode;
    }

    /**
     * Sets the value of the issuingCarrierCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIssuingCarrierCode(String value)
    {
        this.issuingCarrierCode = value;
    }

    /**
     * Gets the value of the customerCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCustomerCode()
    {
        return customerCode;
    }

    /**
     * Sets the value of the customerCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustomerCode(String value)
    {
        this.customerCode = value;
    }

    /**
     * Gets the value of the totalFare property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTotalFare()
    {
        return totalFare;
    }

    /**
     * Sets the value of the totalFare property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTotalFare(BasicAmountType value)
    {
        this.totalFare = value;
    }

    /**
     * Gets the value of the totalTaxes property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTotalTaxes()
    {
        return totalTaxes;
    }

    /**
     * Sets the value of the totalTaxes property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTotalTaxes(BasicAmountType value)
    {
        this.totalTaxes = value;
    }

    /**
     * Gets the value of the totalFee property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTotalFee()
    {
        return totalFee;
    }

    /**
     * Sets the value of the totalFee property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTotalFee(BasicAmountType value)
    {
        this.totalFee = value;
    }

    /**
     * Gets the value of the restrictedTicket property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getRestrictedTicket()
    {
        return restrictedTicket;
    }

    /**
     * Sets the value of the restrictedTicket property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRestrictedTicket(String value)
    {
        this.restrictedTicket = value;
    }

    /**
     * Gets the value of the clearingSequence property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getClearingSequence()
    {
        return clearingSequence;
    }

    /**
     * Sets the value of the clearingSequence property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setClearingSequence(String value)
    {
        this.clearingSequence = value;
    }

    /**
     * Gets the value of the clearingCount property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getClearingCount()
    {
        return clearingCount;
    }

    /**
     * Sets the value of the clearingCount property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setClearingCount(String value)
    {
        this.clearingCount = value;
    }

    /**
     * Gets the value of the flightDetails property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flightDetails property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlightDetails().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link FlightDetailsType }
     */
    public List<FlightDetailsType> getFlightDetails()
    {
        if (flightDetails == null)
        {
            flightDetails = new ArrayList<FlightDetailsType>();
        }
        return this.flightDetails;
    }

}
