package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for SiteHostedPictureType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="SiteHostedPictureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PictureURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" maxOccurs="6" minOccurs="0"/>
 *         &lt;element name="PhotoDisplay" type="{urn:ebay:apis:eBLBaseComponents}PhotoDisplayCodeType" minOccurs="0"/>
 *         &lt;element name="GalleryType" type="{urn:ebay:apis:eBLBaseComponents}GalleryTypeCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiteHostedPictureType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "pictureURL",
        "photoDisplay",
        "galleryType"
})
public class SiteHostedPictureType
{

    @XmlElement(name = "PictureURL", namespace = "urn:ebay:apis:eBLBaseComponents")
    @XmlSchemaType(name = "anyURI")
    protected List<String> pictureURL;
    @XmlElement(name = "PhotoDisplay", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected PhotoDisplayCodeType photoDisplay;
    @XmlElement(name = "GalleryType", namespace = "urn:ebay:apis:eBLBaseComponents")
    protected GalleryTypeCodeType galleryType;

    /**
     * Gets the value of the pictureURL property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pictureURL property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPictureURL().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getPictureURL()
    {
        if (pictureURL == null)
        {
            pictureURL = new ArrayList<String>();
        }
        return this.pictureURL;
    }

    /**
     * Gets the value of the photoDisplay property.
     *
     * @return possible object is
     *         {@link PhotoDisplayCodeType }
     */
    public PhotoDisplayCodeType getPhotoDisplay()
    {
        return photoDisplay;
    }

    /**
     * Sets the value of the photoDisplay property.
     *
     * @param value allowed object is
     *              {@link PhotoDisplayCodeType }
     */
    public void setPhotoDisplay(PhotoDisplayCodeType value)
    {
        this.photoDisplay = value;
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
