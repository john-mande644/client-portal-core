package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for GetBoardingDetailsResponseDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetBoardingDetailsResponseDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Status" type="{urn:ebay:apis:eBLBaseComponents}BoardingStatusType"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LastUpdated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProgramName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProgramCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CampaignID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UserWithdrawalLimit" type="{urn:ebay:apis:eBLBaseComponents}UserWithdrawalLimitTypeType" minOccurs="0"/>
 *         &lt;element name="PartnerCustom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccountOwner" type="{urn:ebay:apis:eBLBaseComponents}PayerInfoType" minOccurs="0"/>
 *         &lt;element name="Credentials" type="{urn:ebay:apis:eBLBaseComponents}APICredentialsType" minOccurs="0"/>
 *         &lt;element name="ConfigureAPIs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EmailVerificationStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VettingStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BankAccountVerificationStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetBoardingDetailsResponseDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "status",
        "startDate",
        "lastUpdated",
        "reason",
        "programName",
        "programCode",
        "campaignID",
        "userWithdrawalLimit",
        "partnerCustom",
        "accountOwner",
        "credentials",
        "configureAPIs",
        "emailVerificationStatus",
        "vettingStatus",
        "bankAccountVerificationStatus"
})
public class GetBoardingDetailsResponseDetailsType
{

    @XmlElement(name = "Status", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected BoardingStatusType status;
    @XmlElement(name = "StartDate", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "LastUpdated", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected XMLGregorianCalendar lastUpdated;
    @XmlElement(name = "Reason", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String reason;
    @XmlElement(name = "ProgramName", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String programName;
    @XmlElement(name = "ProgramCode", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String programCode;
    @XmlElement(name = "CampaignID", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String campaignID;
    @XmlElement(name = "UserWithdrawalLimit", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected UserWithdrawalLimitTypeType userWithdrawalLimit;
    @XmlElement(name = "PartnerCustom", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String partnerCustom;
    @XmlElement(name = "AccountOwner", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PayerInfoType accountOwner;
    @XmlElement(name = "Credentials", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected APICredentialsType credentials;
    @XmlElement(name = "ConfigureAPIs", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String configureAPIs;
    @XmlElement(name = "EmailVerificationStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String emailVerificationStatus;
    @XmlElement(name = "VettingStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String vettingStatus;
    @XmlElement(name = "BankAccountVerificationStatus", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String bankAccountVerificationStatus;

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     *         {@link BoardingStatusType }
     */
    public BoardingStatusType getStatus()
    {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link BoardingStatusType }
     */
    public void setStatus(BoardingStatusType value)
    {
        this.status = value;
    }

    /**
     * Gets the value of the startDate property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getStartDate()
    {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setStartDate(XMLGregorianCalendar value)
    {
        this.startDate = value;
    }

    /**
     * Gets the value of the lastUpdated property.
     *
     * @return possible object is
     *         {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getLastUpdated()
    {
        return lastUpdated;
    }

    /**
     * Sets the value of the lastUpdated property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setLastUpdated(XMLGregorianCalendar value)
    {
        this.lastUpdated = value;
    }

    /**
     * Gets the value of the reason property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReason()
    {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReason(String value)
    {
        this.reason = value;
    }

    /**
     * Gets the value of the programName property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProgramName()
    {
        return programName;
    }

    /**
     * Sets the value of the programName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProgramName(String value)
    {
        this.programName = value;
    }

    /**
     * Gets the value of the programCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProgramCode()
    {
        return programCode;
    }

    /**
     * Sets the value of the programCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProgramCode(String value)
    {
        this.programCode = value;
    }

    /**
     * Gets the value of the campaignID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getCampaignID()
    {
        return campaignID;
    }

    /**
     * Sets the value of the campaignID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCampaignID(String value)
    {
        this.campaignID = value;
    }

    /**
     * Gets the value of the userWithdrawalLimit property.
     *
     * @return possible object is
     *         {@link UserWithdrawalLimitTypeType }
     */
    public UserWithdrawalLimitTypeType getUserWithdrawalLimit()
    {
        return userWithdrawalLimit;
    }

    /**
     * Sets the value of the userWithdrawalLimit property.
     *
     * @param value allowed object is
     *              {@link UserWithdrawalLimitTypeType }
     */
    public void setUserWithdrawalLimit(UserWithdrawalLimitTypeType value)
    {
        this.userWithdrawalLimit = value;
    }

    /**
     * Gets the value of the partnerCustom property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPartnerCustom()
    {
        return partnerCustom;
    }

    /**
     * Sets the value of the partnerCustom property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPartnerCustom(String value)
    {
        this.partnerCustom = value;
    }

    /**
     * Gets the value of the accountOwner property.
     *
     * @return possible object is
     *         {@link PayerInfoType }
     */
    public PayerInfoType getAccountOwner()
    {
        return accountOwner;
    }

    /**
     * Sets the value of the accountOwner property.
     *
     * @param value allowed object is
     *              {@link PayerInfoType }
     */
    public void setAccountOwner(PayerInfoType value)
    {
        this.accountOwner = value;
    }

    /**
     * Gets the value of the credentials property.
     *
     * @return possible object is
     *         {@link APICredentialsType }
     */
    public APICredentialsType getCredentials()
    {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     *
     * @param value allowed object is
     *              {@link APICredentialsType }
     */
    public void setCredentials(APICredentialsType value)
    {
        this.credentials = value;
    }

    /**
     * Gets the value of the configureAPIs property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getConfigureAPIs()
    {
        return configureAPIs;
    }

    /**
     * Sets the value of the configureAPIs property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setConfigureAPIs(String value)
    {
        this.configureAPIs = value;
    }

    /**
     * Gets the value of the emailVerificationStatus property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getEmailVerificationStatus()
    {
        return emailVerificationStatus;
    }

    /**
     * Sets the value of the emailVerificationStatus property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEmailVerificationStatus(String value)
    {
        this.emailVerificationStatus = value;
    }

    /**
     * Gets the value of the vettingStatus property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getVettingStatus()
    {
        return vettingStatus;
    }

    /**
     * Sets the value of the vettingStatus property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVettingStatus(String value)
    {
        this.vettingStatus = value;
    }

    /**
     * Gets the value of the bankAccountVerificationStatus property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getBankAccountVerificationStatus()
    {
        return bankAccountVerificationStatus;
    }

    /**
     * Sets the value of the bankAccountVerificationStatus property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBankAccountVerificationStatus(String value)
    {
        this.bankAccountVerificationStatus = value;
    }

}
