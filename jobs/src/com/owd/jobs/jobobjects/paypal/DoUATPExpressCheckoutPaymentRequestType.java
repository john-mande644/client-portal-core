package com.owd.jobs.jobobjects.paypal;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DoUATPExpressCheckoutPaymentRequestType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="DoUATPExpressCheckoutPaymentRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ebay:api:PayPalAPI}DoExpressCheckoutPaymentRequestType">
private final static Logger log =  LogManager.getLogger();
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DoUATPExpressCheckoutPaymentRequestType")
public class DoUATPExpressCheckoutPaymentRequestType
        extends DoExpressCheckoutPaymentRequestType
{


}
