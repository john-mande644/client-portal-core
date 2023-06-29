
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for FilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Field" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Operator" type="{urn:networksolutions:apis}OperatorCodeType" minOccurs="0"/>
 *         &lt;element name="ValueList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SubFilterList" type="{urn:networksolutions:apis}FilterType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="OrClause" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FilterType", propOrder = {
    "field",
    "operator",
    "valueList",
    "subFilterList"
})
public class FilterType {

    @XmlElement(name = "Field")
    protected String field;
    @XmlElement(name = "Operator")
    protected OperatorCodeType operator;
    @XmlElement(name = "ValueList")
    protected List<String> valueList;
    @XmlElement(name = "SubFilterList")
    protected List<FilterType> subFilterList;
    @XmlAttribute(name = "OrClause")
    protected Boolean orClause;

    /**
     * Gets the value of the field property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the value of the field property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField(String value) {
        this.field = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link OperatorCodeType }
     *     
     */
    public OperatorCodeType getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatorCodeType }
     *     
     */
    public void setOperator(OperatorCodeType value) {
        this.operator = value;
    }

    /**
     * Gets the value of the valueList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getValueList() {
        if (valueList == null) {
            valueList = new ArrayList<String>();
        }
        return this.valueList;
    }

    /**
     * Gets the value of the subFilterList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subFilterList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubFilterList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FilterType }
     * 
     * 
     */
    public List<FilterType> getSubFilterList() {
        if (subFilterList == null) {
            subFilterList = new ArrayList<FilterType>();
        }
        return this.subFilterList;
    }

    /**
     * Gets the value of the orClause property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOrClause() {
        return orClause;
    }

    /**
     * Sets the value of the orClause property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOrClause(Boolean value) {
        this.orClause = value;
    }

}
