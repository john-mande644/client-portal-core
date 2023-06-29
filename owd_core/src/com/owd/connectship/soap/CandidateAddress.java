
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents a candidate address from a validation operation.
 * 
 * <p>Java class for CandidateAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CandidateAddress">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="address" type="{urn:connectship-com:ampcore}NameAddress"/>
 *         &lt;element name="residentialCommercialProvided" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CandidateAddress", propOrder = {
    "address",
    "residentialCommercialProvided"
})
public class CandidateAddress {

    @XmlElement(required = true)
    protected NameAddress address;
    protected boolean residentialCommercialProvided;

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setAddress(NameAddress value) {
        this.address = value;
    }

    /**
     * Gets the value of the residentialCommercialProvided property.
     * 
     */
    public boolean isResidentialCommercialProvided() {
        return residentialCommercialProvided;
    }

    /**
     * Sets the value of the residentialCommercialProvided property.
     * 
     */
    public void setResidentialCommercialProvided(boolean value) {
        this.residentialCommercialProvided = value;
    }

}
