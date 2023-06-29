package com.owd.core.business.client;

import com.owd.core.MailAddressValidator;
import com.owd.core.Mailer;
import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.internet.AddressException;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 8/17/11
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class EzUltrasoundClientPolicy extends DefaultClientPolicy {
private final static Logger log =  LogManager.getLogger();


    @Override
     public void sendNotificationMessage(Order order, String subject, String message) {
         this.sendCustomerEmailConfirmation(order);

    }

    @Override
    public void sendCustomerEmailConfirmation(Order order) {
        if((order.containsSKU("US1000") || order.containsSKU("ZZA1000") || order.containsSKU("PainShield") || order.containsSKU("UCPro")))
        {
            String subject = "EZUltrasound Order #"+order.order_refnum+" Terms of Sale Agreement";

                 String emailText = "\n" +
                         "Dear "+order.getBillingContact().getName()+",\n" +
                         "\n" +
                         "Below are the Terms of Sale for your new device.\n" +
                         "\n" +
                         "Accepting your purchase will constitute agreement with these terms.\n" +
                         "\n" +
                         "If you do not agree with the Terms of Sale please immediately call 1-888-772-4047 to receive a full refund.\n" +
                         "\n" +
                         "Sincerely,\n" +
                         "\n" +
                         "The EZUltrasound Team\n" +
                         "\n" +
                         "--------------------------------------------------------\n" +
                         "Terms of Sale:\n" +
                         "\n" +
                         "1. Buyer has read and agrees to the Shipping, Return, and Privacy policies. You can review these policies at the following web links:\n\n"+
                         "Shipping: http://www.ezultrasound.com/shipping-policy.aspx\n" +
                         "Returns: http://www.ezultrasound.com/return-policy.aspx\n" +
                         "Privacy: http://www.ezultrasound.com/privacy-policy.aspx\n" +
                         "\n" +
                         "2. Buyer certifies that ultrasound machine will be used only as specified by the manufacturer.\n" +
                         "\n" +
                         "3. Buyer or customer certifies that he/she has read EZUltrasound's FAQ's page on proper ultrasound machine operation and usage at http://www.ezultrasound.com/faqs.aspx.\n" +
                         "\n" +
                         "4. Buyer indemnifies & holds harmless Med Health Supplies, LLC dba EZUltrasound, its managers, owners & affiliates from any possible injury/ harm from use of any products sold by or through EZUltrasound.\n" +
                         "\n" +
                         "5. Buyer certifies machine is intended to be used only on buyer and buyer is not 18 years old or younger, has a pacemaker, poor circulation or is pregnant. ";

            try
            {

                  String addr = order.getBillingContact().getEmail();

                try {
                    MailAddressValidator.validate(addr);
                } catch (AddressException ea) {
                    addr = null;
                }

                if (addr != null)
                {
                    Mailer.sendMail(subject,emailText,order.getBillingContact().getEmail(),"sales@EZUltrasound.com");
                }
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }
}
