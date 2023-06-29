package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for QuantityType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="QuantityType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>double">
 *       &lt;attribute name="unit" type="{http://www.w3.org/2001/XMLSchema}token" />
private final static Logger log =  LogManager.getLogger();
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuantityType", namespace = "urn:ebay:apis:CoreComponentTypes", propOrder = {
        "value"
})
public class QuantityType
{

    @XmlValue
    protected double value;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String unit;

    /**
     * Gets the value of the value property.
     */
    public double getValue()
    {
        return value;
    }

    /**
     * Sets the value of the value property.
     */
    public void setValue(double value)
    {
        this.value = value;
    }

    /**
     * Gets the value of the unit property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getUnit()
    {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUnit(String value)
    {
        this.unit = value;
    }

}
