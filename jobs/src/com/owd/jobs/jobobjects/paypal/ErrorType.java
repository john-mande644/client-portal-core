package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ErrorType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ErrorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ShortMessage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LongMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ErrorCode" type="{http://www.w3.org/2001/XMLSchema}token"/>
 *         &lt;element name="SeverityCode" type="{urn:ebay:apis:eBLBaseComponents}SeverityCodeType"/>
 *         &lt;element name="ErrorParameters" type="{urn:ebay:apis:eBLBaseComponents}ErrorParameterType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "shortMessage",
        "longMessage",
        "errorCode",
        "severityCode",
        "errorParameters"
})
public class ErrorType
{

    @XmlElement(name = "ShortMessage", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String shortMessage;
    @XmlElement(name = "LongMessage", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String longMessage;
    @XmlElement(name = "ErrorCode", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String errorCode;
    @XmlElement(name = "SeverityCode", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected SeverityCodeType severityCode;
    @XmlElement(name = "ErrorParameters", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected List<ErrorParameterType> errorParameters;

    /**
     * Gets the value of the shortMessage property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getShortMessage()
    {
        return shortMessage;
    }

    /**
     * Sets the value of the shortMessage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setShortMessage(String value)
    {
        this.shortMessage = value;
    }

    /**
     * Gets the value of the longMessage property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLongMessage()
    {
        return longMessage;
    }

    /**
     * Sets the value of the longMessage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLongMessage(String value)
    {
        this.longMessage = value;
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

    /**
     * Gets the value of the severityCode property.
     *
     * @return possible object is
     *         {@link SeverityCodeType }
     */
    public SeverityCodeType getSeverityCode()
    {
        return severityCode;
    }

    /**
     * Sets the value of the severityCode property.
     *
     * @param value allowed object is
     *              {@link SeverityCodeType }
     */
    public void setSeverityCode(SeverityCodeType value)
    {
        this.severityCode = value;
    }

    /**
     * Gets the value of the errorParameters property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errorParameters property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrorParameters().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorParameterType }
     */
    public List<ErrorParameterType> getErrorParameters()
    {
        if (errorParameters == null)
        {
            errorParameters = new ArrayList<ErrorParameterType>();
        }
        return this.errorParameters;
    }

}
