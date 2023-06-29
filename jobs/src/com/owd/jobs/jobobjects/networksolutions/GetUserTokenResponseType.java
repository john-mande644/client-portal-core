
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetUserTokenResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetUserTokenResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}BaseResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="UserToken" type="{urn:networksolutions:apis}UserTokenType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetUserTokenResponseType", propOrder = {
    "userToken"
})
public class GetUserTokenResponseType
    extends BaseResponseType
{

    @XmlElement(name = "UserToken")
    protected UserTokenType userToken;

    /**
     * Gets the value of the userToken property.
     * 
     * @return
     *     possible object is
     *     {@link UserTokenType }
     *     
     */
    public UserTokenType getUserToken() {
        return userToken;
    }

    /**
     * Sets the value of the userToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserTokenType }
     *     
     */
    public void setUserToken(UserTokenType value) {
        this.userToken = value;
    }

}
