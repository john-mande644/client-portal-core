package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateAccessPermissionsRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="UpdateAccessPermissionsRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractRequestType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PayerID" type="{urn:ebay:apis:eBLBaseComponents}UserIDType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateAccessPermissionsRequestType", propOrder = {
        "payerID"
})
public class UpdateAccessPermissionsRequestType
        extends AbstractRequestType
{

    @XmlElement(name = "PayerID", required = true)
    protected String payerID;

    /**
     * Gets the value of the payerID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPayerID()
    {
        return payerID;
    }

    /**
     * Sets the value of the payerID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPayerID(String value)
    {
        this.payerID = value;
    }

}
