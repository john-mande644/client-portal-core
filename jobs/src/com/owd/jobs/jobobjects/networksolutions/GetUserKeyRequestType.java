
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetUserKeyRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetUserKeyRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="UserKey" type="{urn:networksolutions:apis}UserKeyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetUserKeyRequestType", propOrder = {
    "userKey"
})
public class GetUserKeyRequestType
    extends BaseRequestType
{

    @XmlElement(name = "UserKey")
    protected UserKeyType userKey;

    /**
     * Gets the value of the userKey property.
     * 
     * @return
     *     possible object is
     *     {@link UserKeyType }
     *     
     */
    public UserKeyType getUserKey() {
        return userKey;
    }

    /**
     * Sets the value of the userKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserKeyType }
     *     
     */
    public void setUserKey(UserKeyType value) {
        this.userKey = value;
    }

}
