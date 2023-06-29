
package com.owd.connectship.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Generic dictionary of items.
 * 
 * <p>Java class for Dictionary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dictionary">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:connectship-com:ampcore}CollectionBase">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="item" type="{urn:connectship-com:ampcore}DictionaryItem" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dictionary", propOrder = {
    "item"
})
public class Dictionary
    extends CollectionBase
{

    protected List<DictionaryItem> item;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DictionaryItem }
     * 
     * 
     */
    public List<DictionaryItem> getItem() {
        if (item == null) {
            item = new ArrayList<DictionaryItem>();
        }
        return this.item;
    }

}
