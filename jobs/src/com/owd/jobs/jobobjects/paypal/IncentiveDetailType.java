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
 * <p>Java class for IncentiveDetailType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="IncentiveDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="RedemptionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisplayCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProgramId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IncentiveType" type="{urn:ebay:apis:eBLBaseComponents}IncentiveTypeCodeType" minOccurs="0"/>
 *         &lt;element name="IncentiveDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AppliedTo" type="{urn:ebay:apis:eBLBaseComponents}IncentiveAppliedToType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IncentiveDetailType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "redemptionCode",
        "displayCode",
        "programId",
        "incentiveType",
        "incentiveDescription",
        "appliedTo",
        "status",
        "errorCode"
})
public class IncentiveDetailType
{

    @XmlElement(name = "RedemptionCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String redemptionCode;
    @XmlElement(name = "DisplayCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String displayCode;
    @XmlElement(name = "ProgramId", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String programId;
    @XmlElement(name = "IncentiveType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected IncentiveTypeCodeType incentiveType;
    @XmlElement(name = "IncentiveDescription", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String incentiveDescription;
    @XmlElement(name = "AppliedTo", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<IncentiveAppliedToType> appliedTo;
    @XmlElement(name = "Status", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String status;
    @XmlElement(name = "ErrorCode", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String errorCode;

    /**
     * Gets the value of the redemptionCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getRedemptionCode()
    {
        return redemptionCode;
    }

    /**
     * Sets the value of the redemptionCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRedemptionCode(String value)
    {
        this.redemptionCode = value;
    }

    /**
     * Gets the value of the displayCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getDisplayCode()
    {
        return displayCode;
    }

    /**
     * Sets the value of the displayCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDisplayCode(String value)
    {
        this.displayCode = value;
    }

    /**
     * Gets the value of the programId property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getProgramId()
    {
        return programId;
    }

    /**
     * Sets the value of the programId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProgramId(String value)
    {
        this.programId = value;
    }

    /**
     * Gets the value of the incentiveType property.
     *
     * @return possible object is
     *         {@link IncentiveTypeCodeType }
     */
    public IncentiveTypeCodeType getIncentiveType()
    {
        return incentiveType;
    }

    /**
     * Sets the value of the incentiveType property.
     *
     * @param value allowed object is
     *              {@link IncentiveTypeCodeType }
     */
    public void setIncentiveType(IncentiveTypeCodeType value)
    {
        this.incentiveType = value;
    }

    /**
     * Gets the value of the incentiveDescription property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getIncentiveDescription()
    {
        return incentiveDescription;
    }

    /**
     * Sets the value of the incentiveDescription property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIncentiveDescription(String value)
    {
        this.incentiveDescription = value;
    }

    /**
     * Gets the value of the appliedTo property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the appliedTo property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAppliedTo().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link IncentiveAppliedToType }
     */
    public List<IncentiveAppliedToType> getAppliedTo()
    {
        if (appliedTo == null)
        {
            appliedTo = new ArrayList<IncentiveAppliedToType>();
        }
        return this.appliedTo;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStatus(String value)
    {
        this.status = value;
    }

    /**
     * Gets the value of the errorCode property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getErrorCode()
    {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setErrorCode(String value)
    {
        this.errorCode = value;
    }

}
