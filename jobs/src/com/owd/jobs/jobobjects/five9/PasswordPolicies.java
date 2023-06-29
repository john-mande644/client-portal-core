
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for passwordPolicies complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="passwordPolicies">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="entryValues" type="{http://service.admin.ws.five9.com/v2/}passwordPolicyEntryValue" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "passwordPolicies", propOrder = {
    "entryValues"
})
public class PasswordPolicies {

    @XmlElement(nillable = true)
    protected List<PasswordPolicyEntryValue> entryValues;

    /**
     * Gets the value of the entryValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the entryValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntryValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PasswordPolicyEntryValue }
     * 
     * 
     */
    public List<PasswordPolicyEntryValue> getEntryValues() {
        if (entryValues == null) {
            entryValues = new ArrayList<PasswordPolicyEntryValue>();
        }
        return this.entryValues;
    }

}
