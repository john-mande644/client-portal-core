
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DeleteWarehouseResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeleteWarehouseResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Warehouse" type="{urn:networksolutions:apis}WarehouseType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeleteWarehouseResponseType", propOrder = {
    "warehouse"
})
public class DeleteWarehouseResponseType
    extends BaseResponseType
{

    @XmlElement(name = "Warehouse")
    protected WarehouseType warehouse;

    /**
     * Gets the value of the warehouse property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseType }
     *     
     */
    public WarehouseType getWarehouse() {
        return warehouse;
    }

    /**
     * Sets the value of the warehouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseType }
     *     
     */
    public void setWarehouse(WarehouseType value) {
        this.warehouse = value;
    }

}
