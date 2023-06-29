package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetBalanceRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetBalanceRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="ReturnAllCurrencies" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetBalanceRequestType", propOrder = {
        "returnAllCurrencies"
})
public class GetBalanceRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "ReturnAllCurrencies")
    protected String returnAllCurrencies;

    /**
     * Gets the value of the returnAllCurrencies property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getReturnAllCurrencies()
    {
        return returnAllCurrencies;
    }

    /**
     * Sets the value of the returnAllCurrencies property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReturnAllCurrencies(String value)
    {
        this.returnAllCurrencies = value;
    }

}
