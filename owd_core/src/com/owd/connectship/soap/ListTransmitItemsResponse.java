
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;


/**
 * Response from the list transmit items operation.
 * 
 * <p>Java class for ListTransmitItemsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListTransmitItemsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="result" type="{urn:connectship-com:ampcore}ListTransmitItemsResult"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{urn:connectship-com:ampcore}asyncCorrelationData"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListTransmitItemsResponse", propOrder = {
    "result"
})
public class ListTransmitItemsResponse {

    @XmlElement(required = true)
    protected ListTransmitItemsResult result;
    @XmlAttribute(namespace = "urn:connectship-com:ampcore")
    protected String asyncCorrelationData;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link ListTransmitItemsResult }
     *     
     */
    public ListTransmitItemsResult getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListTransmitItemsResult }
     *     
     */
    public void setResult(ListTransmitItemsResult value) {
        this.result = value;
    }

    /**
     * Gets the value of the asyncCorrelationData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsyncCorrelationData() {
        return asyncCorrelationData;
    }

    /**
     * Sets the value of the asyncCorrelationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsyncCorrelationData(String value) {
        this.asyncCorrelationData = value;
    }

}
