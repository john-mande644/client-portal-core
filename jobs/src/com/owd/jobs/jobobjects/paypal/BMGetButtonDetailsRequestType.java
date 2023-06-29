package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BMGetButtonDetailsRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="BMGetButtonDetailsRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="HostedButtonID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BMGetButtonDetailsRequestType", propOrder = {
        "hostedButtonID"
})
public class BMGetButtonDetailsRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "HostedButtonID", required = true)
    protected String hostedButtonID;

    /**
     * Gets the value of the hostedButtonID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getHostedButtonID()
    {
        return hostedButtonID;
    }

    /**
     * Sets the value of the hostedButtonID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHostedButtonID(String value)
    {
        this.hostedButtonID = value;
    }

}
