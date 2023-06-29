
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
 * <p>Java class for PerformMultipleRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PerformMultipleRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="UpdateInventoryRequestList" type="{urn:networksolutions:apis}UpdateInventoryRequestType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PerformMultipleRequestType", propOrder = {
    "updateInventoryRequestList"
})
public class PerformMultipleRequestType
    extends BaseRequestType
{

    @XmlElement(name = "UpdateInventoryRequestList")
    protected List<UpdateInventoryRequestType> updateInventoryRequestList;

    /**
     * Gets the value of the updateInventoryRequestList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateInventoryRequestList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateInventoryRequestList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateInventoryRequestType }
     * 
     * 
     */
    public List<UpdateInventoryRequestType> getUpdateInventoryRequestList() {
        if (updateInventoryRequestList == null) {
            updateInventoryRequestList = new ArrayList<UpdateInventoryRequestType>();
        }
        return this.updateInventoryRequestList;
    }

}
