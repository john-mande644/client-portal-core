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
 * <p>Java class for MassPayRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="MassPayRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="EmailSubject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReceiverType" type="{urn:ebay:apis:eBLBaseComponents}ReceiverInfoCodeType" minOccurs="0"/>
 *         &lt;element name="ButtonSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MassPayItem" type="{urn:ebay:api:PayPalAPI}MassPayRequestItemType" maxOccurs="250"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MassPayRequestType", propOrder = {
        "emailSubject",
        "receiverType",
        "buttonSource",
        "massPayItem"
})
public class MassPayRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "EmailSubject")
    protected String emailSubject;
    @XmlElement(name = "ReceiverType")
    protected ReceiverInfoCodeType receiverType;
    @XmlElement(name = "ButtonSource")
    protected String buttonSource;
    @XmlElement(name = "MassPayItem", required = true)
    protected List<MassPayRequestItemType> massPayItem;

    /**
     * Gets the value of the emailSubject property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getEmailSubject()
    {
        return emailSubject;
    }

    /**
     * Sets the value of the emailSubject property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEmailSubject(String value)
    {
        this.emailSubject = value;
    }

    /**
     * Gets the value of the receiverType property.
     *
     * @return possible object is
     *         {@link ReceiverInfoCodeType }
     */
    public ReceiverInfoCodeType getReceiverType()
    {
        return receiverType;
    }

    /**
     * Sets the value of the receiverType property.
     *
     * @param value allowed object is
     *              {@link ReceiverInfoCodeType }
     */
    public void setReceiverType(ReceiverInfoCodeType value)
    {
        this.receiverType = value;
    }

    /**
     * Gets the value of the buttonSource property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getButtonSource()
    {
        return buttonSource;
    }

    /**
     * Sets the value of the buttonSource property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setButtonSource(String value)
    {
        this.buttonSource = value;
    }

    /**
     * Gets the value of the massPayItem property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the massPayItem property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMassPayItem().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link MassPayRequestItemType }
     */
    public List<MassPayRequestItemType> getMassPayItem()
    {
        if (massPayItem == null)
        {
            massPayItem = new ArrayList<MassPayRequestItemType>();
        }
        return this.massPayItem;
    }

}
