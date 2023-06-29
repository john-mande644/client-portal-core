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
 * <p>Java class for ListOfAttributeSetType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ListOfAttributeSetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="AttributeSet" type="{urn:ebay:apis:eBLBaseComponents}AttributeSetType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfAttributeSetType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "attributeSet"
})
public class ListOfAttributeSetType
{

    @XmlElement(name = "AttributeSet", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected List<AttributeSetType> attributeSet;

    /**
     * Gets the value of the attributeSet property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeSet property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeSet().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeSetType }
     */
    public List<AttributeSetType> getAttributeSet()
    {
        if (attributeSet == null)
        {
            attributeSet = new ArrayList<AttributeSetType>();
        }
        return this.attributeSet;
    }

}
