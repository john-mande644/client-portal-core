package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Details of leg information
 * <p/>
 * <p/>
 * <p>Java class for FlightDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="FlightDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ConjuctionTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExchangeTicket" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CouponNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ServiceClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TravelDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CarrierCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StopOverPermitted" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DepartureAirport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ArrivalAirport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FlightNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DepartureTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ArrivalTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FareBasisCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fare" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="Taxes" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="Fee" type="{urn:ebay:apis:CoreComponentTypes}BasicAmountType" minOccurs="0"/>
 *         &lt;element name="EndorsementOrRestrictions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "conjuctionTicket",
        "exchangeTicket",
        "couponNumber",
        "serviceClass",
        "travelDate",
        "carrierCode",
        "stopOverPermitted",
        "departureAirport",
        "arrivalAirport",
        "flightNumber",
        "departureTime",
        "arrivalTime",
        "fareBasisCode",
        "fare",
        "taxes",
        "fee",
        "endorsementOrRestrictions"
})
public class FlightDetailsType
{

    @XmlElement(name = "ConjuctionTicket", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String conjuctionTicket;
    @XmlElement(name = "ExchangeTicket", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String exchangeTicket;
    @XmlElement(name = "CouponNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String couponNumber;
    @XmlElement(name = "ServiceClass", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String serviceClass;
    @XmlElement(name = "TravelDate", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String travelDate;
    @XmlElement(name = "CarrierCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String carrierCode;
    @XmlElement(name = "StopOverPermitted", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String stopOverPermitted;
    @XmlElement(name = "DepartureAirport", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String departureAirport;
    @XmlElement(name = "ArrivalAirport", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String arrivalAirport;
    @XmlElement(name = "FlightNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String flightNumber;
    @XmlElement(name = "DepartureTime", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String departureTime;
    @XmlElement(name = "ArrivalTime", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String arrivalTime;
    @XmlElement(name = "FareBasisCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String fareBasisCode;
    @XmlElement(name = "Fare", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType fare;
    @XmlElement(name = "Taxes", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType taxes;
    @XmlElement(name = "Fee", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected BasicAmountType fee;
    @XmlElement(name = "EndorsementOrRestrictions", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String endorsementOrRestrictions;

    /**
     * Gets the value of the conjuctionTicket property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getConjuctionTicket()
    {
        return conjuctionTicket;
    }

    /**
     * Sets the value of the conjuctionTicket property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setConjuctionTicket(String value)
    {
        this.conjuctionTicket = value;
    }

    /**
     * Gets the value of the exchangeTicket property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getExchangeTicket()
    {
        return exchangeTicket;
    }

    /**
     * Sets the value of the exchangeTicket property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setExchangeTicket(String value)
    {
        this.exchangeTicket = value;
    }

    /**
     * Gets the value of the couponNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCouponNumber()
    {
        return couponNumber;
    }

    /**
     * Sets the value of the couponNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCouponNumber(String value)
    {
        this.couponNumber = value;
    }

    /**
     * Gets the value of the serviceClass property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getServiceClass()
    {
        return serviceClass;
    }

    /**
     * Sets the value of the serviceClass property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setServiceClass(String value)
    {
        this.serviceClass = value;
    }

    /**
     * Gets the value of the travelDate property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getTravelDate()
    {
        return travelDate;
    }

    /**
     * Sets the value of the travelDate property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTravelDate(String value)
    {
        this.travelDate = value;
    }

    /**
     * Gets the value of the carrierCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCarrierCode()
    {
        return carrierCode;
    }

    /**
     * Sets the value of the carrierCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCarrierCode(String value)
    {
        this.carrierCode = value;
    }

    /**
     * Gets the value of the stopOverPermitted property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStopOverPermitted()
    {
        return stopOverPermitted;
    }

    /**
     * Sets the value of the stopOverPermitted property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStopOverPermitted(String value)
    {
        this.stopOverPermitted = value;
    }

    /**
     * Gets the value of the departureAirport property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDepartureAirport()
    {
        return departureAirport;
    }

    /**
     * Sets the value of the departureAirport property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDepartureAirport(String value)
    {
        this.departureAirport = value;
    }

    /**
     * Gets the value of the arrivalAirport property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getArrivalAirport()
    {
        return arrivalAirport;
    }

    /**
     * Sets the value of the arrivalAirport property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setArrivalAirport(String value)
    {
        this.arrivalAirport = value;
    }

    /**
     * Gets the value of the flightNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getFlightNumber()
    {
        return flightNumber;
    }

    /**
     * Sets the value of the flightNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFlightNumber(String value)
    {
        this.flightNumber = value;
    }

    /**
     * Gets the value of the departureTime property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDepartureTime()
    {
        return departureTime;
    }

    /**
     * Sets the value of the departureTime property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDepartureTime(String value)
    {
        this.departureTime = value;
    }

    /**
     * Gets the value of the arrivalTime property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getArrivalTime()
    {
        return arrivalTime;
    }

    /**
     * Sets the value of the arrivalTime property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setArrivalTime(String value)
    {
        this.arrivalTime = value;
    }

    /**
     * Gets the value of the fareBasisCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getFareBasisCode()
    {
        return fareBasisCode;
    }

    /**
     * Sets the value of the fareBasisCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFareBasisCode(String value)
    {
        this.fareBasisCode = value;
    }

    /**
     * Gets the value of the fare property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getFare()
    {
        return fare;
    }

    /**
     * Sets the value of the fare property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setFare(BasicAmountType value)
    {
        this.fare = value;
    }

    /**
     * Gets the value of the taxes property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getTaxes()
    {
        return taxes;
    }

    /**
     * Sets the value of the taxes property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setTaxes(BasicAmountType value)
    {
        this.taxes = value;
    }

    /**
     * Gets the value of the fee property.
     *
     * @return possible object is
     *         {@link BasicAmountType }
     */
    public BasicAmountType getFee()
    {
        return fee;
    }

    /**
     * Sets the value of the fee property.
     *
     * @param value allowed object is
     *              {@link BasicAmountType }
     */
    public void setFee(BasicAmountType value)
    {
        this.fee = value;
    }

    /**
     * Gets the value of the endorsementOrRestrictions property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getEndorsementOrRestrictions()
    {
        return endorsementOrRestrictions;
    }

    /**
     * Sets the value of the endorsementOrRestrictions property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEndorsementOrRestrictions(String value)
    {
        this.endorsementOrRestrictions = value;
    }

}
