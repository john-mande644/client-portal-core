package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Specific physical attribute of an item.
 * <p/>
 * <p/>
 * <p>Java class for AttributeType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="AttributeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Value" type="{urn:ebay:apis:eBLBaseComponents}ValType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="AttributeID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "value"
})
public class AttributeType
{

    @XmlElement(name = "Value", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected List<ValType> value;
    @XmlAttribute(name = "AttributeID")
    protected String attributeID;

    /**
     * Gets the value of the value property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link ValType }
     */
    public List<ValType> getValue()
    {
        if (value == null)
        {
            value = new ArrayList<ValType>();
        }
        return this.value;
    }

    /**
     * Gets the value of the attributeID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAttributeID()
    {
        return attributeID;
    }

    /**
     * Sets the value of the attributeID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAttributeID(String value)
    {
        this.attributeID = value;
    }

}
