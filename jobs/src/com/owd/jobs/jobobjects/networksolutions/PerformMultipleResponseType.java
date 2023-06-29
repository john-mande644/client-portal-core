
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for PerformMultipleResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PerformMultipleResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="UpdateInventoryResponse" type="{urn:networksolutions:apis}UpdateInventoryResponseType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerformMultipleResponseType", propOrder = {
    "updateInventoryResponse"
})
public class PerformMultipleResponseType
    extends BaseResponseType
{

    @XmlElement(name = "UpdateInventoryResponse")
    protected List<UpdateInventoryResponseType> updateInventoryResponse;

    /**
     * Gets the value of the updateInventoryResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateInventoryResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateInventoryResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateInventoryResponseType }
     * 
     * 
     */
    public List<UpdateInventoryResponseType> getUpdateInventoryResponse() {
        if (updateInventoryResponse == null) {
            updateInventoryResponse = new ArrayList<UpdateInventoryResponseType>();
        }
        return this.updateInventoryResponse;
    }

}
