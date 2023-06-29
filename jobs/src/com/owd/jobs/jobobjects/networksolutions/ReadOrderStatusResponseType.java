
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
 * <p>Java class for ReadOrderStatusResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadOrderStatusResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}ReadBaseResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="OrderStatusList" type="{urn:networksolutions:apis}OrderStatusType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadOrderStatusResponseType", propOrder = {
    "orderStatusList"
})
public class ReadOrderStatusResponseType
    extends ReadBaseResponseType
{

    @XmlElement(name = "OrderStatusList")
    protected List<OrderStatusType> orderStatusList;

    /**
     * Gets the value of the orderStatusList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderStatusList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderStatusList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderStatusType }
     * 
     * 
     */
    public List<OrderStatusType> getOrderStatusList() {
        if (orderStatusList == null) {
            orderStatusList = new ArrayList<OrderStatusType>();
        }
        return this.orderStatusList;
    }

}
