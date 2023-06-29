
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for campaignFilterCriterion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="campaignFilterCriterion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="compareOperator" type="{http://service.admin.ws.five9.com/v2/}compareOperatorType" minOccurs="0"/>
 *         &lt;element name="leftValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rightValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "campaignFilterCriterion", propOrder = {
    "compareOperator",
    "leftValue",
    "rightValue"
})
public class CampaignFilterCriterion {

    protected CompareOperatorType compareOperator;
    protected String leftValue;
    protected String rightValue;

    /**
     * Gets the value of the compareOperator property.
     * 
     * @return
     *     possible object is
     *     {@link CompareOperatorType }
     *     
     */
    public CompareOperatorType getCompareOperator() {
        return compareOperator;
    }

    /**
     * Sets the value of the compareOperator property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompareOperatorType }
     *     
     */
    public void setCompareOperator(CompareOperatorType value) {
        this.compareOperator = value;
    }

    /**
     * Gets the value of the leftValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeftValue() {
        return leftValue;
    }

    /**
     * Sets the value of the leftValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeftValue(String value) {
        this.leftValue = value;
    }

    /**
     * Gets the value of the rightValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRightValue() {
        return rightValue;
    }

    /**
     * Sets the value of the rightValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRightValue(String value) {
        this.rightValue = value;
    }

}
