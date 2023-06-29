package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OptionTrackingDetailsType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="OptionTrackingDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="OptionNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptionQty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptionSelect" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptionQtyDelta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptionAlert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OptionCost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OptionTrackingDetailsType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "optionNumber",
        "optionQty",
        "optionSelect",
        "optionQtyDelta",
        "optionAlert",
        "optionCost"
})
public class OptionTrackingDetailsType
{

    @XmlElement(name = "OptionNumber", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String optionNumber;
    @XmlElement(name = "OptionQty", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String optionQty;
    @XmlElement(name = "OptionSelect", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String optionSelect;
    @XmlElement(name = "OptionQtyDelta", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String optionQtyDelta;
    @XmlElement(name = "OptionAlert", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String optionAlert;
    @XmlElement(name = "OptionCost", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String optionCost;

    /**
     * Gets the value of the optionNumber property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOptionNumber()
    {
        return optionNumber;
    }

    /**
     * Sets the value of the optionNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOptionNumber(String value)
    {
        this.optionNumber = value;
    }

    /**
     * Gets the value of the optionQty property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOptionQty()
    {
        return optionQty;
    }

    /**
     * Sets the value of the optionQty property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOptionQty(String value)
    {
        this.optionQty = value;
    }

    /**
     * Gets the value of the optionSelect property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOptionSelect()
    {
        return optionSelect;
    }

    /**
     * Sets the value of the optionSelect property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOptionSelect(String value)
    {
        this.optionSelect = value;
    }

    /**
     * Gets the value of the optionQtyDelta property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOptionQtyDelta()
    {
        return optionQtyDelta;
    }

    /**
     * Sets the value of the optionQtyDelta property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOptionQtyDelta(String value)
    {
        this.optionQtyDelta = value;
    }

    /**
     * Gets the value of the optionAlert property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOptionAlert()
    {
        return optionAlert;
    }

    /**
     * Sets the value of the optionAlert property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOptionAlert(String value)
    {
        this.optionAlert = value;
    }

    /**
     * Gets the value of the optionCost property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getOptionCost()
    {
        return optionCost;
    }

    /**
     * Sets the value of the optionCost property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOptionCost(String value)
    {
        this.optionCost = value;
    }

}
