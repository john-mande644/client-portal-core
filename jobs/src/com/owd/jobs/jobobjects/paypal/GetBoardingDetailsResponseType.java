package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetBoardingDetailsResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetBoardingDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}GetBoardingDetailsResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetBoardingDetailsResponseType", propOrder = {
        "getBoardingDetailsResponseDetails"
})
public class GetBoardingDetailsResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "GetBoardingDetailsResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected GetBoardingDetailsResponseDetailsType getBoardingDetailsResponseDetails;

    /**
     * Gets the value of the getBoardingDetailsResponseDetails property.
     *
     * @return possible object is
     *         {@link GetBoardingDetailsResponseDetailsType }
     */
    public GetBoardingDetailsResponseDetailsType getGetBoardingDetailsResponseDetails()
    {
        return getBoardingDetailsResponseDetails;
    }

    /**
     * Sets the value of the getBoardingDetailsResponseDetails property.
     *
     * @param value allowed object is
     *              {@link GetBoardingDetailsResponseDetailsType }
     */
    public void setGetBoardingDetailsResponseDetails(GetBoardingDetailsResponseDetailsType value)
    {
        this.getBoardingDetailsResponseDetails = value;
    }

}
