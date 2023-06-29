package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ModifiedFieldType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ModifiedFieldType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Field" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModifyType" type="{urn:ebay:apis:eBLBaseComponents}ModifyCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ModifiedFieldType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "field",
        "modifyType"
})
public class ModifiedFieldType
{

    @XmlElement(name = "Field", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected String field;
    @XmlElement(name = "ModifyType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected ModifyCodeType modifyType;

    /**
     * Gets the value of the field property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getField()
    {
        return field;
    }

    /**
     * Sets the value of the field property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setField(String value)
    {
        this.field = value;
    }

    /**
     * Gets the value of the modifyType property.
     *
     * @return possible object is
     *         {@link ModifyCodeType }
     */
    public ModifyCodeType getModifyType()
    {
        return modifyType;
    }

    /**
     * Sets the value of the modifyType property.
     *
     * @param value allowed object is
     *              {@link ModifyCodeType }
     */
    public void setModifyType(ModifyCodeType value)
    {
        this.modifyType = value;
    }

}
