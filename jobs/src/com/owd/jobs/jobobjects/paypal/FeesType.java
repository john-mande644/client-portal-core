package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Following are the current set of eBay fee types
 * AuctionLengthFee
 * BoldFee
 * BuyItNowFee
 * CategoryFeaturedFee
 * FeaturedFee
 * FeaturedGalleryFee
 * FixedPriceDurationFee
 * GalleryFee
 * GiftIconFee
 * HighLightFee
 * InsertionFee
 * ListingDesignerFee
 * ListingFee
 * PhotoDisplayFee
 * PhotoFee
 * ReserveFee
 * SchedulingFee
 * ThirtyDaysAucFee
 * Instances of this type could hold one or more supported types of fee.
 * <p/>
 * <p/>
 * <p>Java class for FeesType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="FeesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="Fee" type="{urn:ebay:apis:eBLBaseComponents}FeeType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeesType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "fee"
})
public class FeesType
{

    @XmlElement(name = "Fee", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected List<FeeType> fee;

    /**
     * Gets the value of the fee property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fee property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFee().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link FeeType }
     */
    public List<FeeType> getFee()
    {
        if (fee == null)
        {
            fee = new ArrayList<FeeType>();
        }
        return this.fee;
    }

}
