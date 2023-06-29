package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * AttributeSet.
 * <p/>
 * <p/>
 * <p>Java class for AttributeSetType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="AttributeSetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Attribute" type="{urn:ebay:apis:eBLBaseComponents}AttributeType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="AttributeSetID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributeSetType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "attribute"
})
public class AttributeSetType
{

    @XmlElement(name = "Attribute", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected List<AttributeType> attribute;
    @XmlAttribute(name = "AttributeSetID")
    protected String attributeSetID;

    /**
     * Gets the value of the attribute property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attribute property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttribute().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeType }
     */
    public List<AttributeType> getAttribute()
    {
        if (attribute == null)
        {
            attribute = new ArrayList<AttributeType>();
        }
        return this.attribute;
    }

    /**
     * Gets the value of the attributeSetID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getAttributeSetID()
    {
        return attributeSetID;
    }

    /**
     * Sets the value of the attributeSetID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAttributeSetID(String value)
    {
        this.attributeSetID = value;
    }

}
