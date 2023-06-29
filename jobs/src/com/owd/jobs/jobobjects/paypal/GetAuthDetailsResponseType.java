package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetAuthDetailsResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetAuthDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}GetAuthDetailsResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAuthDetailsResponseType", propOrder = {
        "getAuthDetailsResponseDetails"
})
public class GetAuthDetailsResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "GetAuthDetailsResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected GetAuthDetailsResponseDetailsType getAuthDetailsResponseDetails;

    /**
     * Gets the value of the getAuthDetailsResponseDetails property.
     *
     * @return possible object is
     *         {@link GetAuthDetailsResponseDetailsType }
     */
    public GetAuthDetailsResponseDetailsType getGetAuthDetailsResponseDetails()
    {
        return getAuthDetailsResponseDetails;
    }

    /**
     * Sets the value of the getAuthDetailsResponseDetails property.
     *
     * @param value allowed object is
     *              {@link GetAuthDetailsResponseDetailsType }
     */
    public void setGetAuthDetailsResponseDetails(GetAuthDetailsResponseDetailsType value)
    {
        this.getAuthDetailsResponseDetails = value;
    }

}
