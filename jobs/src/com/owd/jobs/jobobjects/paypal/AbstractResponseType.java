package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * Base type definition of a response payload that can carry any
 * type of payload content with following optional elements:
 * - timestamp of response message,
 * - application level acknowledgement, and
 * - application-level errors and warnings.
 * <p/>
 * <p/>
 * <p>Java class for AbstractResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="AbstractResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Ack" type="{urn:ebay:apis:eBLBaseComponents}AckCodeType"/>
 *         &lt;element name="CorrelationID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Errors" type="{urn:ebay:apis:eBLBaseComponents}ErrorType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Build" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractResponseType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "timestamp",
        "ack",
        "correlationID",
        "errors",
        "version",
        "build",
        "any"
})
@XmlSeeAlso({
        BillOutstandingAmountResponseType.class,
        SetCustomerBillingAgreementResponseType.class,
        GetMobileStatusResponseType.class,
        DoReauthorizationResponseType.class,
        GetBalanceResponseType.class,
        EnterBoardingResponseType.class,
        SetMobileCheckoutResponseType.class,
        CreateBillingAgreementResponseType.class,
        SetExpressCheckoutResponseType.class,
        DoMobileCheckoutPaymentResponseType.class,
        UpdateRecurringPaymentsProfileResponseType.class,
        BillUserResponseType.class,
        SetAuthFlowParamResponseType.class,
        CreateMobilePaymentResponseType.class,
        GetExpressCheckoutDetailsResponseType.class,
        SetAccessPermissionsResponseType.class,
        RefundTransactionResponseType.class,
        BMGetButtonDetailsResponseType.class,
        MassPayResponseType.class,
        GetBillingAgreementCustomerDetailsResponseType.class,
        ManageRecurringPaymentsProfileStatusResponseType.class,
        DoCaptureResponseType.class,
        BMManageButtonStatusResponseType.class,
        BMGetInventoryResponseType.class,
        GetRecurringPaymentsProfileDetailsResponseType.class,
        UpdateAccessPermissionsResponseType.class,
        DoAuthorizationResponseType.class,
        GetAccessPermissionDetailsResponseType.class,
        BMUpdateButtonResponseType.class,
        DoDirectPaymentResponseType.class,
        DoExpressCheckoutPaymentResponseType.class,
        GetIncentiveEvaluationResponseType.class,
        BAUpdateResponseType.class,
        GetPalDetailsResponseType.class,
        ReverseTransactionResponseType.class,
        AddressVerifyResponseType.class,
        DoNonReferencedCreditResponseType.class,
        BMSetInventoryResponseType.class,
        CreateRecurringPaymentsProfileResponseType.class,
        GetBoardingDetailsResponseType.class,
        GetTransactionDetailsResponseType.class,
        BMCreateButtonResponseType.class,
        TransactionSearchResponseType.class,
        BMButtonSearchResponseType.class,
        ManagePendingTransactionStatusResponseType.class,
        GetAuthDetailsResponseType.class,
        DoReferenceTransactionResponseType.class,
        DoVoidResponseType.class
})
public abstract class AbstractResponseType
{

    @XmlElement(name = "Timestamp", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "Ack", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected AckCodeType ack;
    @XmlElement(name = "CorrelationID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String correlationID;
    @XmlElement(name = "Errors", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<ErrorType> errors;
    @XmlElement(name = "Version", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String version;
    @XmlElement(name = "Build", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String build;
    @XmlAnyElement(lax = true)
    protected Object any;

    /**
     * Gets the value of the timestamp property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getTimestamp()
    {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setTimestamp(XMLGregorianCalendar value)
    {
        this.timestamp = value;
    }

    /**
     * Gets the value of the ack property.
     *
     * @return possible object is
     *         {@link AckCodeType }
     */
    public AckCodeType getAck()
    {
        return ack;
    }

    /**
     * Sets the value of the ack property.
     *
     * @param value allowed object is
     *              {@link AckCodeType }
     */
    public void setAck(AckCodeType value)
    {
        this.ack = value;
    }

    /**
     * Gets the value of the correlationID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCorrelationID()
    {
        return correlationID;
    }

    /**
     * Sets the value of the correlationID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCorrelationID(String value)
    {
        this.correlationID = value;
    }

    /**
     * Gets the value of the errors property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errors property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrors().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorType }
     */
    public List<ErrorType> getErrors()
    {
        if (errors == null)
        {
            errors = new ArrayList<ErrorType>();
        }
        return this.errors;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVersion(String value)
    {
        this.version = value;
    }

    /**
     * Gets the value of the build property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBuild()
    {
        return build;
    }

    /**
     * Sets the value of the build property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBuild(String value)
    {
        this.build = value;
    }

    /**
     * Gets the value of the any property.
     *
     * @return possible object is
     *         {@link Element }
     *         {@link Object }
     */
    public Object getAny()
    {
        return any;
    }

    /**
     * Sets the value of the any property.
     *
     * @param value allowed object is
     *              {@link Element }
     *              {@link Object }
     */
    public void setAny(Object value)
    {
        this.any = value;
    }

}
