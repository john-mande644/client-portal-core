package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProductCategoryType.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;simpleType name="ProductCategoryType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
private final static Logger log =  LogManager.getLogger();
 *     &lt;enumeration value="Other"/>
 *     &lt;enumeration value="Airlines"/>
 *     &lt;enumeration value="Antiques"/>
 *     &lt;enumeration value="Art"/>
 *     &lt;enumeration value="Cameras_Photos"/>
 *     &lt;enumeration value="Cars_Boats_Vehicles_Parts"/>
 *     &lt;enumeration value="CellPhones_Telecom"/>
 *     &lt;enumeration value="Coins_PaperMoney"/>
 *     &lt;enumeration value="Collectibles"/>
 *     &lt;enumeration value="Computers_Networking"/>
 *     &lt;enumeration value="ConsumerElectronics"/>
 *     &lt;enumeration value="Jewelry_Watches"/>
 *     &lt;enumeration value="MusicalInstruments"/>
 *     &lt;enumeration value="RealEstate"/>
 *     &lt;enumeration value="SportsMemorabilia_Cards_FanShop"/>
 *     &lt;enumeration value="Stamps"/>
 *     &lt;enumeration value="Tickets"/>
 *     &lt;enumeration value="Travels"/>
 *     &lt;enumeration value="Gambling"/>
 *     &lt;enumeration value="Alcohol"/>
 *     &lt;enumeration value="Tobacco"/>
 *     &lt;enumeration value="MoneyTransfer"/>
 *     &lt;enumeration value="Software"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "ProductCategoryType", namespace = "urn:ebay:apis:eBLBaseComponents")
@XmlEnum
public enum ProductCategoryType
{

    @XmlEnumValue("Other")
    OTHER("Other"),
    @XmlEnumValue("Airlines")
    AIRLINES("Airlines"),
    @XmlEnumValue("Antiques")
    ANTIQUES("Antiques"),
    @XmlEnumValue("Art")
    ART("Art"),
    @XmlEnumValue("Cameras_Photos")
    CAMERAS_PHOTOS("Cameras_Photos"),
    @XmlEnumValue("Cars_Boats_Vehicles_Parts")
    CARS_BOATS_VEHICLES_PARTS("Cars_Boats_Vehicles_Parts"),
    @XmlEnumValue("CellPhones_Telecom")
    CELL_PHONES_TELECOM("CellPhones_Telecom"),
    @XmlEnumValue("Coins_PaperMoney")
    COINS_PAPER_MONEY("Coins_PaperMoney"),
    @XmlEnumValue("Collectibles")
    COLLECTIBLES("Collectibles"),
    @XmlEnumValue("Computers_Networking")
    COMPUTERS_NETWORKING("Computers_Networking"),
    @XmlEnumValue("ConsumerElectronics")
    CONSUMER_ELECTRONICS("ConsumerElectronics"),
    @XmlEnumValue("Jewelry_Watches")
    JEWELRY_WATCHES("Jewelry_Watches"),
    @XmlEnumValue("MusicalInstruments")
    MUSICAL_INSTRUMENTS("MusicalInstruments"),
    @XmlEnumValue("RealEstate")
    REAL_ESTATE("RealEstate"),
    @XmlEnumValue("SportsMemorabilia_Cards_FanShop")
    SPORTS_MEMORABILIA_CARDS_FAN_SHOP("SportsMemorabilia_Cards_FanShop"),
    @XmlEnumValue("Stamps")
    STAMPS("Stamps"),
    @XmlEnumValue("Tickets")
    TICKETS("Tickets"),
    @XmlEnumValue("Travels")
    TRAVELS("Travels"),
    @XmlEnumValue("Gambling")
    GAMBLING("Gambling"),
    @XmlEnumValue("Alcohol")
    ALCOHOL("Alcohol"),
    @XmlEnumValue("Tobacco")
    TOBACCO("Tobacco"),
    @XmlEnumValue("MoneyTransfer")
    MONEY_TRANSFER("MoneyTransfer"),
    @XmlEnumValue("Software")
    SOFTWARE("Software");
    private final String value;

    ProductCategoryType(String v)
    {
        value = v;
    }

    public String value()
    {
        return value;
    }

    public static ProductCategoryType fromValue(String v)
    {
        for (ProductCategoryType c : ProductCategoryType.values())
        {
            if (c.value.equals(v))
            {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
