package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 16, 2005
 * Time: 10:37:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class MailAddressValidator {
private final static Logger log =  LogManager.getLogger();


    /**
     * Attempts to validate whether an email address is valid.
     * It doesn't actually try to send an email to the address
     * but it can spot badly constructed
     * addresses and missing domains. For instance, "bill",
     * "bill@", "@gulesider.no", * "bill@@gulesider.no",
     * "@bill@gulesider.no", "bill;@gulesider.no" would all be
     * deemed invalid. However, "blah@gulesider.no"
     * would be okay.
     */


    private static final String DELIMITER = "@";


    public static void validate(String address) throws Exception {


        try {
            InternetAddress internetAddress =
                    new InternetAddress(address);
        } catch (AddressException e) {
            throw new Exception(e.getMessage());
        } catch (NullPointerException e) {
            throw new Exception("Address is null!");
        }


        // This bit is to exclude local email addresses (i.e.
        // "bill") which we don't want either.
        StringTokenizer st =
                new StringTokenizer(address, DELIMITER);


        if (st.countTokens() != 2) {
            throw new Exception("Missing domain");
        }


    }

    public static boolean isValid(String address) throws Exception {


        try {
            validate(address);

            return true;
        } catch (Exception e) {
            return false;
        }


    }


}
