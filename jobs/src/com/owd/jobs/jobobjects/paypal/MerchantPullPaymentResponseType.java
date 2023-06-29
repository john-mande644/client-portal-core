package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * MerchantPullPaymentResponseType
 * Response data from the merchant pull.
 * <p/>
 * <p/>
 * <p>Java class for MerchantPullPaymentResponseType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="MerchantPullPaymentResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
private final static Logger log =  LogManager.getLogger();
 *       &lt;sequence>
 *         &lt;element name="PayerInfo" type="{urn:ebay:apis:eBLBaseComponents}PayerInfoType"/>
 *         &lt;element name="PaymentInfo" type="{urn:ebay:apis:eBLBaseComponents}PaymentInfoType"/>
 *         &lt;element name="MerchantPullInfo" type="{urn:ebay:apis:eBLBaseComponents}MerchantPullInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MerchantPullPaymentResponseType", namespace = "urn:ebay:apis:eBLBaseComponents", propOrder = {
        "payerInfo",
        "paymentInfo",
        "merchantPullInfo"
})
public class MerchantPullPaymentResponseType
{

    @XmlElement(name = "PayerInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PayerInfoType payerInfo;
    @XmlElement(name = "PaymentInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected PaymentInfoType paymentInfo;
    @XmlElement(name = "MerchantPullInfo", namespace = "urn:ebay:apis:eBLBaseComponents", required = true)
    protected MerchantPullInfoType merchantPullInfo;

    /**
     * Gets the value of the payerInfo property.
     *
     * @return possible object is
     *         {@link PayerInfoType }
     */
    public PayerInfoType getPayerInfo()
    {
        return payerInfo;
    }

    /**
     * Sets the value of the payerInfo property.
     *
     * @param value allowed object is
     *              {@link PayerInfoType }
     */
    public void setPayerInfo(PayerInfoType value)
    {
        this.payerInfo = value;
    }

    /**
     * Gets the value of the paymentInfo property.
     *
     * @return possible object is
     *         {@link PaymentInfoType }
     */
    public PaymentInfoType getPaymentInfo()
    {
        return paymentInfo;
    }

    /**
     * Sets the value of the paymentInfo property.
     *
     * @param value allowed object is
     *              {@link PaymentInfoType }
     */
    public void setPaymentInfo(PaymentInfoType value)
    {
        this.paymentInfo = value;
    }

    /**
     * Gets the value of the merchantPullInfo property.
     *
     * @return possible object is
     *         {@link MerchantPullInfoType }
     */
    public MerchantPullInfoType getMerchantPullInfo()
    {
        return merchantPullInfo;
    }

    /**
     * Sets the value of the merchantPullInfo property.
     *
     * @param value allowed object is
     *              {@link MerchantPullInfoType }
     */
    public void setMerchantPullInfo(MerchantPullInfoType value)
    {
        this.merchantPullInfo = value;
    }

}
