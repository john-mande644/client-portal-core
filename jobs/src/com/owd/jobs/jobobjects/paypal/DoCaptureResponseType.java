package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoCaptureResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoCaptureResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:apis:eBLBaseComponents}AbstractResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element ref="{urn:ebay:apis:eBLBaseComponents}DoCaptureResponseDetails"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoCaptureResponseType", propOrder = {
        "doCaptureResponseDetails"
})
public class DoCaptureResponseType
        extends AbstractResponseType
{

    @XmlElement(name = "DoCaptureResponseDetails", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected DoCaptureResponseDetailsType doCaptureResponseDetails;

    /**
     * Gets the value of the doCaptureResponseDetails property.
     *
     * @return possible object is
     *         {@link DoCaptureResponseDetailsType }
     */
    public DoCaptureResponseDetailsType getDoCaptureResponseDetails()
    {
        return doCaptureResponseDetails;
    }

    /**
     * Sets the value of the doCaptureResponseDetails property.
     *
     * @param value allowed object is
     *              {@link DoCaptureResponseDetailsType }
     */
    public void setDoCaptureResponseDetails(DoCaptureResponseDetailsType value)
    {
        this.doCaptureResponseDetails = value;
    }

}
