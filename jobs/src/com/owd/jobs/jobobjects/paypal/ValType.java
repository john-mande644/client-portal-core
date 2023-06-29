package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for ValType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ValType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ValueLiteral" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ValueID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "valueLiteral"
})
public class ValType
{

    @XmlElement(name = "ValueLiteral", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected String valueLiteral;
    @XmlAttribute(name = "ValueID")
    protected String valueID;

    /**
     * Gets the value of the valueLiteral property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getValueLiteral()
    {
        return valueLiteral;
    }

    /**
     * Sets the value of the valueLiteral property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValueLiteral(String value)
    {
        this.valueLiteral = value;
    }

    /**
     * Gets the value of the valueID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getValueID()
    {
        return valueID;
    }

    /**
     * Sets the value of the valueID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValueID(String value)
    {
        this.valueID = value;
    }

}
