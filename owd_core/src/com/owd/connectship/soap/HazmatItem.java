
package com.owd.connectship.soap;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Elements that represent a hazardous material item in a shipment.
 * 
 * <p>Java class for HazmatItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HazmatItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;all>
 *         &lt;element name="hazmat500kgExemption" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmatAccessible" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmatCargo" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmatCaCategory" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="hazmatClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatEmergencyContact" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatEmergencyPhone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatExceptedQuantity" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmatExNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatInfectiousResponsibleParty" type="{urn:connectship-com:ampcore}NameAddress" minOccurs="0"/>
 *         &lt;element name="hazmatLabel" type="{urn:connectship-com:ampcore}enumList" minOccurs="0"/>
 *         &lt;element name="hazmatLimitedQuantity" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmatPacking" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatPackingGroup" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="hazmatPackingInstruction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatPercentage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatQuantity" type="{urn:connectship-com:ampcore}HazmatQuantity" minOccurs="0"/>
 *         &lt;element name="hazmatRadioactiveActivity" type="{urn:connectship-com:ampcore}HazmatQuantity" minOccurs="0"/>
 *         &lt;element name="hazmatRadioactiveChemicalForm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatRadioactiveCsi" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="hazmatRadioactiveException" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="hazmatRadioactiveName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatRadioactivePackaging" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatRadioactivePhysicalForm" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="hazmatRadioactiveSurfaceReading" type="{urn:connectship-com:ampcore}HazmatQuantity" minOccurs="0"/>
 *         &lt;element name="hazmatRadioactiveTransportIndex" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="hazmatReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatRegulation" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="hazmatRegulationSet" type="{urn:connectship-com:ampcore}enumItem" minOccurs="0"/>
 *         &lt;element name="hazmatReportableQuantity" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="hazmatSpecialProvisions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatSubsidiaryRiskClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hazmatTechnicalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userData1" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData2" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData3" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData4" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *         &lt;element name="userData5" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HazmatItem", propOrder = {

})
public class HazmatItem {

    @XmlElement(name = "hazmat500kgExemption")
    protected Boolean hazmat500KgExemption;
    protected Boolean hazmatAccessible;
    protected Boolean hazmatCargo;
    protected String hazmatCaCategory;
    protected String hazmatClass;
    protected String hazmatDescription;
    protected String hazmatEmergencyContact;
    protected String hazmatEmergencyPhone;
    protected Boolean hazmatExceptedQuantity;
    protected String hazmatExNumber;
    protected String hazmatId;
    protected NameAddress hazmatInfectiousResponsibleParty;
    @XmlList
    protected List<String> hazmatLabel;
    protected Boolean hazmatLimitedQuantity;
    protected String hazmatPacking;
    protected String hazmatPackingGroup;
    protected String hazmatPackingInstruction;
    protected String hazmatPercentage;
    protected HazmatQuantity hazmatQuantity;
    protected HazmatQuantity hazmatRadioactiveActivity;
    protected String hazmatRadioactiveChemicalForm;
    protected BigDecimal hazmatRadioactiveCsi;
    protected String hazmatRadioactiveException;
    protected String hazmatRadioactiveName;
    protected String hazmatRadioactivePackaging;
    protected String hazmatRadioactivePhysicalForm;
    protected HazmatQuantity hazmatRadioactiveSurfaceReading;
    protected BigDecimal hazmatRadioactiveTransportIndex;
    protected String hazmatReference;
    protected String hazmatRegulation;
    protected String hazmatRegulationSet;
    protected Boolean hazmatReportableQuantity;
    protected String hazmatSpecialProvisions;
    protected String hazmatSubsidiaryRiskClass;
    protected String hazmatTechnicalName;
    protected Object userData1;
    protected Object userData2;
    protected Object userData3;
    protected Object userData4;
    protected Object userData5;

    /**
     * Gets the value of the hazmat500KgExemption property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazmat500KgExemption() {
        return hazmat500KgExemption;
    }

    /**
     * Sets the value of the hazmat500KgExemption property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHazmat500KgExemption(Boolean value) {
        this.hazmat500KgExemption = value;
    }

    /**
     * Gets the value of the hazmatAccessible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazmatAccessible() {
        return hazmatAccessible;
    }

    /**
     * Sets the value of the hazmatAccessible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHazmatAccessible(Boolean value) {
        this.hazmatAccessible = value;
    }

    /**
     * Gets the value of the hazmatCargo property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazmatCargo() {
        return hazmatCargo;
    }

    /**
     * Sets the value of the hazmatCargo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHazmatCargo(Boolean value) {
        this.hazmatCargo = value;
    }

    /**
     * Gets the value of the hazmatCaCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatCaCategory() {
        return hazmatCaCategory;
    }

    /**
     * Sets the value of the hazmatCaCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatCaCategory(String value) {
        this.hazmatCaCategory = value;
    }

    /**
     * Gets the value of the hazmatClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatClass() {
        return hazmatClass;
    }

    /**
     * Sets the value of the hazmatClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatClass(String value) {
        this.hazmatClass = value;
    }

    /**
     * Gets the value of the hazmatDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatDescription() {
        return hazmatDescription;
    }

    /**
     * Sets the value of the hazmatDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatDescription(String value) {
        this.hazmatDescription = value;
    }

    /**
     * Gets the value of the hazmatEmergencyContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatEmergencyContact() {
        return hazmatEmergencyContact;
    }

    /**
     * Sets the value of the hazmatEmergencyContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatEmergencyContact(String value) {
        this.hazmatEmergencyContact = value;
    }

    /**
     * Gets the value of the hazmatEmergencyPhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatEmergencyPhone() {
        return hazmatEmergencyPhone;
    }

    /**
     * Sets the value of the hazmatEmergencyPhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatEmergencyPhone(String value) {
        this.hazmatEmergencyPhone = value;
    }

    /**
     * Gets the value of the hazmatExceptedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazmatExceptedQuantity() {
        return hazmatExceptedQuantity;
    }

    /**
     * Sets the value of the hazmatExceptedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHazmatExceptedQuantity(Boolean value) {
        this.hazmatExceptedQuantity = value;
    }

    /**
     * Gets the value of the hazmatExNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatExNumber() {
        return hazmatExNumber;
    }

    /**
     * Sets the value of the hazmatExNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatExNumber(String value) {
        this.hazmatExNumber = value;
    }

    /**
     * Gets the value of the hazmatId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatId() {
        return hazmatId;
    }

    /**
     * Sets the value of the hazmatId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatId(String value) {
        this.hazmatId = value;
    }

    /**
     * Gets the value of the hazmatInfectiousResponsibleParty property.
     * 
     * @return
     *     possible object is
     *     {@link NameAddress }
     *     
     */
    public NameAddress getHazmatInfectiousResponsibleParty() {
        return hazmatInfectiousResponsibleParty;
    }

    /**
     * Sets the value of the hazmatInfectiousResponsibleParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAddress }
     *     
     */
    public void setHazmatInfectiousResponsibleParty(NameAddress value) {
        this.hazmatInfectiousResponsibleParty = value;
    }

    /**
     * Gets the value of the hazmatLabel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hazmatLabel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHazmatLabel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getHazmatLabel() {
        if (hazmatLabel == null) {
            hazmatLabel = new ArrayList<String>();
        }
        return this.hazmatLabel;
    }

    /**
     * Gets the value of the hazmatLimitedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazmatLimitedQuantity() {
        return hazmatLimitedQuantity;
    }

    /**
     * Sets the value of the hazmatLimitedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHazmatLimitedQuantity(Boolean value) {
        this.hazmatLimitedQuantity = value;
    }

    /**
     * Gets the value of the hazmatPacking property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatPacking() {
        return hazmatPacking;
    }

    /**
     * Sets the value of the hazmatPacking property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatPacking(String value) {
        this.hazmatPacking = value;
    }

    /**
     * Gets the value of the hazmatPackingGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatPackingGroup() {
        return hazmatPackingGroup;
    }

    /**
     * Sets the value of the hazmatPackingGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatPackingGroup(String value) {
        this.hazmatPackingGroup = value;
    }

    /**
     * Gets the value of the hazmatPackingInstruction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatPackingInstruction() {
        return hazmatPackingInstruction;
    }

    /**
     * Sets the value of the hazmatPackingInstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatPackingInstruction(String value) {
        this.hazmatPackingInstruction = value;
    }

    /**
     * Gets the value of the hazmatPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatPercentage() {
        return hazmatPercentage;
    }

    /**
     * Sets the value of the hazmatPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatPercentage(String value) {
        this.hazmatPercentage = value;
    }

    /**
     * Gets the value of the hazmatQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link HazmatQuantity }
     *     
     */
    public HazmatQuantity getHazmatQuantity() {
        return hazmatQuantity;
    }

    /**
     * Sets the value of the hazmatQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link HazmatQuantity }
     *     
     */
    public void setHazmatQuantity(HazmatQuantity value) {
        this.hazmatQuantity = value;
    }

    /**
     * Gets the value of the hazmatRadioactiveActivity property.
     * 
     * @return
     *     possible object is
     *     {@link HazmatQuantity }
     *     
     */
    public HazmatQuantity getHazmatRadioactiveActivity() {
        return hazmatRadioactiveActivity;
    }

    /**
     * Sets the value of the hazmatRadioactiveActivity property.
     * 
     * @param value
     *     allowed object is
     *     {@link HazmatQuantity }
     *     
     */
    public void setHazmatRadioactiveActivity(HazmatQuantity value) {
        this.hazmatRadioactiveActivity = value;
    }

    /**
     * Gets the value of the hazmatRadioactiveChemicalForm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatRadioactiveChemicalForm() {
        return hazmatRadioactiveChemicalForm;
    }

    /**
     * Sets the value of the hazmatRadioactiveChemicalForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatRadioactiveChemicalForm(String value) {
        this.hazmatRadioactiveChemicalForm = value;
    }

    /**
     * Gets the value of the hazmatRadioactiveCsi property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHazmatRadioactiveCsi() {
        return hazmatRadioactiveCsi;
    }

    /**
     * Sets the value of the hazmatRadioactiveCsi property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHazmatRadioactiveCsi(BigDecimal value) {
        this.hazmatRadioactiveCsi = value;
    }

    /**
     * Gets the value of the hazmatRadioactiveException property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatRadioactiveException() {
        return hazmatRadioactiveException;
    }

    /**
     * Sets the value of the hazmatRadioactiveException property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatRadioactiveException(String value) {
        this.hazmatRadioactiveException = value;
    }

    /**
     * Gets the value of the hazmatRadioactiveName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatRadioactiveName() {
        return hazmatRadioactiveName;
    }

    /**
     * Sets the value of the hazmatRadioactiveName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatRadioactiveName(String value) {
        this.hazmatRadioactiveName = value;
    }

    /**
     * Gets the value of the hazmatRadioactivePackaging property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatRadioactivePackaging() {
        return hazmatRadioactivePackaging;
    }

    /**
     * Sets the value of the hazmatRadioactivePackaging property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatRadioactivePackaging(String value) {
        this.hazmatRadioactivePackaging = value;
    }

    /**
     * Gets the value of the hazmatRadioactivePhysicalForm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatRadioactivePhysicalForm() {
        return hazmatRadioactivePhysicalForm;
    }

    /**
     * Sets the value of the hazmatRadioactivePhysicalForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatRadioactivePhysicalForm(String value) {
        this.hazmatRadioactivePhysicalForm = value;
    }

    /**
     * Gets the value of the hazmatRadioactiveSurfaceReading property.
     * 
     * @return
     *     possible object is
     *     {@link HazmatQuantity }
     *     
     */
    public HazmatQuantity getHazmatRadioactiveSurfaceReading() {
        return hazmatRadioactiveSurfaceReading;
    }

    /**
     * Sets the value of the hazmatRadioactiveSurfaceReading property.
     * 
     * @param value
     *     allowed object is
     *     {@link HazmatQuantity }
     *     
     */
    public void setHazmatRadioactiveSurfaceReading(HazmatQuantity value) {
        this.hazmatRadioactiveSurfaceReading = value;
    }

    /**
     * Gets the value of the hazmatRadioactiveTransportIndex property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHazmatRadioactiveTransportIndex() {
        return hazmatRadioactiveTransportIndex;
    }

    /**
     * Sets the value of the hazmatRadioactiveTransportIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHazmatRadioactiveTransportIndex(BigDecimal value) {
        this.hazmatRadioactiveTransportIndex = value;
    }

    /**
     * Gets the value of the hazmatReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatReference() {
        return hazmatReference;
    }

    /**
     * Sets the value of the hazmatReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatReference(String value) {
        this.hazmatReference = value;
    }

    /**
     * Gets the value of the hazmatRegulation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatRegulation() {
        return hazmatRegulation;
    }

    /**
     * Sets the value of the hazmatRegulation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatRegulation(String value) {
        this.hazmatRegulation = value;
    }

    /**
     * Gets the value of the hazmatRegulationSet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatRegulationSet() {
        return hazmatRegulationSet;
    }

    /**
     * Sets the value of the hazmatRegulationSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatRegulationSet(String value) {
        this.hazmatRegulationSet = value;
    }

    /**
     * Gets the value of the hazmatReportableQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHazmatReportableQuantity() {
        return hazmatReportableQuantity;
    }

    /**
     * Sets the value of the hazmatReportableQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHazmatReportableQuantity(Boolean value) {
        this.hazmatReportableQuantity = value;
    }

    /**
     * Gets the value of the hazmatSpecialProvisions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatSpecialProvisions() {
        return hazmatSpecialProvisions;
    }

    /**
     * Sets the value of the hazmatSpecialProvisions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatSpecialProvisions(String value) {
        this.hazmatSpecialProvisions = value;
    }

    /**
     * Gets the value of the hazmatSubsidiaryRiskClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatSubsidiaryRiskClass() {
        return hazmatSubsidiaryRiskClass;
    }

    /**
     * Sets the value of the hazmatSubsidiaryRiskClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatSubsidiaryRiskClass(String value) {
        this.hazmatSubsidiaryRiskClass = value;
    }

    /**
     * Gets the value of the hazmatTechnicalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHazmatTechnicalName() {
        return hazmatTechnicalName;
    }

    /**
     * Sets the value of the hazmatTechnicalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHazmatTechnicalName(String value) {
        this.hazmatTechnicalName = value;
    }

    /**
     * Gets the value of the userData1 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData1() {
        return userData1;
    }

    /**
     * Sets the value of the userData1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData1(Object value) {
        this.userData1 = value;
    }

    /**
     * Gets the value of the userData2 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData2() {
        return userData2;
    }

    /**
     * Sets the value of the userData2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData2(Object value) {
        this.userData2 = value;
    }

    /**
     * Gets the value of the userData3 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData3() {
        return userData3;
    }

    /**
     * Sets the value of the userData3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData3(Object value) {
        this.userData3 = value;
    }

    /**
     * Gets the value of the userData4 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData4() {
        return userData4;
    }

    /**
     * Sets the value of the userData4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData4(Object value) {
        this.userData4 = value;
    }

    /**
     * Gets the value of the userData5 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUserData5() {
        return userData5;
    }

    /**
     * Sets the value of the userData5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUserData5(Object value) {
        this.userData5 = value;
    }

}
