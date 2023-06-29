package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetPalDetailsResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="GetPalDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Pal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Locale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPalDetailsResponseType", propOrder = {
        "pal",
        "locale"
})
public class GetPalDetailsResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "Pal", required = true)
    protected String pal;
    @XmlElement(name = "Locale", required = true)
    protected String locale;

    /**
     * Gets the value of the pal property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPal()
    {
        return pal;
    }

    /**
     * Sets the value of the pal property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPal(String value)
    {
        this.pal = value;
    }

    /**
     * Gets the value of the locale property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getLocale()
    {
        return locale;
    }

    /**
     * Sets the value of the locale property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLocale(String value)
    {
        this.locale = value;
    }

}
