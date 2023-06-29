
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents the result of a close out operation.
 * 
 * <p>Java class for CloseOutResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CloseOutResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="resultData" type="{urn:connectship-com:ampcore}DataDictionary"/>
 *         &lt;element name="shipFile" type="{urn:connectship-com:ampcore}ShipFile" minOccurs="0"/>
 *         &lt;element name="transmitItemList" type="{urn:connectship-com:ampcore}TransmitItemList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CloseOutResult", propOrder = {
    "code",
    "message",
    "resultData",
    "shipFile",
    "transmitItemList"
})
public class CloseOutResult {

    protected int code;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(required = true)
    protected DataDictionary resultData;
    protected ShipFile shipFile;
    protected TransmitItemList transmitItemList;

    /**
     * Gets the value of the code property.
     * 
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     */
    public void setCode(int value) {
        this.code = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the resultData property.
     * 
     * @return
     *     possible object is
     *     {@link DataDictionary }
     *     
     */
    public DataDictionary getResultData() {
        return resultData;
    }

    /**
     * Sets the value of the resultData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataDictionary }
     *     
     */
    public void setResultData(DataDictionary value) {
        this.resultData = value;
    }

    /**
     * Gets the value of the shipFile property.
     * 
     * @return
     *     possible object is
     *     {@link ShipFile }
     *     
     */
    public ShipFile getShipFile() {
        return shipFile;
    }

    /**
     * Sets the value of the shipFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipFile }
     *     
     */
    public void setShipFile(ShipFile value) {
        this.shipFile = value;
    }

    /**
     * Gets the value of the transmitItemList property.
     * 
     * @return
     *     possible object is
     *     {@link TransmitItemList }
     *     
     */
    public TransmitItemList getTransmitItemList() {
        return transmitItemList;
    }

    /**
     * Sets the value of the transmitItemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransmitItemList }
     *     
     */
    public void setTransmitItemList(TransmitItemList value) {
        this.transmitItemList = value;
    }

}
