package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for VendorHostedPictureType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="VendorHostedPictureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PictureURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="GalleryURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="GalleryType" type="{urn:ebay:apis:eBLBaseComponents}GalleryTypeCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendorHostedPictureType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "pictureURL",
        "galleryURL",
        "galleryType"
})
public class VendorHostedPictureType
{

    @XmlElement(name = "PictureURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    @XmlSchemaType(name = "anyURI")
    protected String pictureURL;
    @XmlElement(name = "GalleryURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    @XmlSchemaType(name = "anyURI")
    protected String galleryURL;
    @XmlElement(name = "GalleryType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected GalleryTypeCodeType galleryType;

    /**
     * Gets the value of the pictureURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getPictureURL()
    {
        return pictureURL;
    }

    /**
     * Sets the value of the pictureURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPictureURL(String value)
    {
        this.pictureURL = value;
    }

    /**
     * Gets the value of the galleryURL property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getGalleryURL()
    {
        return galleryURL;
    }

    /**
     * Sets the value of the galleryURL property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setGalleryURL(String value)
    {
        this.galleryURL = value;
    }

    /**
     * Gets the value of the galleryType property.
     *
     * @return possible object is
     *         {@link GalleryTypeCodeType }
     */
    public GalleryTypeCodeType getGalleryType()
    {
        return galleryType;
    }

    /**
     * Sets the value of the galleryType property.
     *
     * @param value allowed object is
     *              {@link GalleryTypeCodeType }
     */
    public void setGalleryType(GalleryTypeCodeType value)
    {
        this.galleryType = value;
    }

}
