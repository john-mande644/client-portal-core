
package com.owd.jobs.jobobjects.networksolutions;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ReadGiftCertificateResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReadGiftCertificateResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:networksolutions:apis}ReadBaseResponseType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="GiftCertificateList" type="{urn:networksolutions:apis}GiftCertificateType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadGiftCertificateResponseType", propOrder = {
    "giftCertificateList"
})
public class ReadGiftCertificateResponseType
    extends ReadBaseResponseType
{

    @XmlElement(name = "GiftCertificateList")
    protected List<GiftCertificateType> giftCertificateList;

    /**
     * Gets the value of the giftCertificateList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the giftCertificateList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGiftCertificateList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GiftCertificateType }
     * 
     * 
     */
    public List<GiftCertificateType> getGiftCertificateList() {
        if (giftCertificateList == null) {
            giftCertificateList = new ArrayList<GiftCertificateType>();
        }
        return this.giftCertificateList;
    }

}
