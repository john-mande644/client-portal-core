// CreditCard.java, also contains class LCR (LegalCardRange)

/**

 * Validates Credit Card numbers, and determines

 * which credit card company they belong to.

 *

 * 1. if a credit card number is valid,

 * 2. which credit card vendor handles that number.

 *

 * It validates the prefix and the checkdigit. It does

 * *not* contact the credit card company to ensure that number

 * has actually been issued and that the account is in good

 * standing.

 *

 * It will also tell you which of the following credit card

 * companies issued the card: Amex, Diners Club, Carte Blanche,

 * Discover, enRoute, JCB, MasterCard or Visa.

 *

 * @author  copyright (c) 1999-2000 Roedy Green of Canadian Mind Products

 * Shareware that may be copied and used freely for any purpose

 * but military.

 *

 * If you make more than casual use, please sent a registration fee of

 * $10 either US or Canadian to:

 * CREDITCARD REGISTRATIONS

 * Roedy Green

 * Canadian Mind Products

 * #208 - 525 Ninth Street

 * New Westminster, BC Canada V3M 5T9

 * tel: (604) 777-1804

 * mailto:roedy@mindprod.com

 * http://mindprod.com CHECK HERE FOR LATEST VERSION!!

 *

 * Futures:

 * - provide a set of icons to use to represent the various

 *   credit card companies.

 * - provide a smart keyable object that generates prompts etc.

 *

 * version 1.0 1999 August 17

 *             - posted to comp.lang.java.programmer

 * version 1.1 1999 August 17

 *             - add vendorToString, rename isValid

 *             - implement patterns for 13-16 digit numbers for toPrettyString

 * version 1.2 1999 August 17

 *             - separate enumerations for too few and too many digits.

 * version 1.3 1999 August 19

 *             - ignore dashes in numbers

 */


package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class CreditCard {
private final static Logger log =  LogManager.getLogger();


    public static final boolean debugging = false;

    /**
     * Credit card vendors supported
     */


    public static final int NOT_ENOUGH_DIGITS = -3;

    public static final int TOO_MANY_DIGITS = -2;

    public static final int UNKNOWN_VENDOR = -1;


    public static final int AMEX = 1;

    public static final int DINERS = 2; // includes Carte Blanche

    public static final int DISCOVER = 3;

    public static final int ENROUTE = 4;

    public static final int JCB = 5;

    public static final int MASTERCARD = 6;

    public static final int VISA = 7;


    /**
     * used by vendorToString to describe the enumerations
     */

    private static final String[] vendors =

            {

                "UNKN",

                "UNKN",

                "UNKN",

                "UNKN",

                "AMEX",

                "DCCB",

                "DISC",

                "ENRO",

                "JCB ",

                "MAST",

                "VISA"};

    private static List ccCardTypeList = new ArrayList();

    {

        ccCardTypeList.add("AMEX");

        ccCardTypeList.add("DCCB");

        ccCardTypeList.add("DISC");

        ccCardTypeList.add("ENRO");

        ccCardTypeList.add("JCB ");

        ccCardTypeList.add("MAST");

        ccCardTypeList.add("VISA");
    }

    /**
     * Determine the credit card company.
     * <p/>
     * Does NOT validate checkdigit.
     *
     * @param creditCardNumber number on card.
     * @return credit card vendor enumeration constant.
     */

    public static int recognizeVendor(long creditCardNumber) {


        int i = findMatchingRange(creditCardNumber);

        if (i < 0) {

            return i;

        } else {

            return ranges[i].vendor;

        }

    } // end recognize


    /**
     * Determine if the credit card number is valid,
     * <p/>
     * i.e. has good prefix and checkdigit.
     * <p/>
     * Does _not_ ask the credit card company
     * <p/>
     * if this card has been issued or is in good standing.
     *
     * @param creditCardNumber number on card.
     * @return true if card number is good.
     */

    public static boolean isValid(long creditCardNumber) {

        int i = findMatchingRange(creditCardNumber);

        if (i < 0) {

            return false;

        } else {

            // we have a match

            if (ranges[i].hasCheckDigit) {

                // there is a checkdigit to be validated

                /*

                Manual method MOD 10 checkdigit

                706-511-227



                7   0   6   5   1   1   2   2   7

                  * 2     * 2     * 2     * 2

                ---------------------------------

                7 + 0 + 6 +1+0+ 1 + 2 + 2 + 4 = 23



                23 MOD 10 = 3



                10 - 3 = 7 -- the check digit



                Note digits of multiplication results

                must be added before sum.





                Computer Method MOD 10 checkdigit

                706-511-227



                7   0   6   5   1   1   2   2   7

                    Z       Z       Z       Z

                ---------------------------------

                7 + 0 + 6 + 1 + 1 + 2 + 2 + 4 + 7 = 30



                30 MOD 10 had better = 0



                */

                long number = creditCardNumber;

                int checksum = 0;

                for (int place = 0; place < 16; place++) {

                    int digit = (int) (number % 10);

                    number /= 10;

                    if ((place & 1) == 0) {

                        // even position, just add digit

                        checksum += digit;

                    } else { // odd position, must double and add

                        checksum += z(digit);

                    }

                    if (number == 0) {

                        break;

                    }

                } // end for

                // good checksum should be 0 mod 10

                return (checksum % 10) == 0;


            } else {

                return true; // no checksum needed

            }

        } // end if have match

    } // end isValid


    /**
     * Convert a creditCardNumber as long to a formatted String.
     * <p/>
     * Currently it breaks 16-digit numbers into groups of 4.
     *
     * @param creditCardNumber number on card.
     * @return String representation of the credit card number.
     */

    public static String toPrettyString(long creditCardNumber) {

        String plain = Long.toString(creditCardNumber);

        int i = findMatchingRange(creditCardNumber);

        int length = plain.length();


        switch (length) {

            case 12:

                // 12 pattern 3-3-3-3

                return plain.substring(0, 3)

                        + ' ' + plain.substring(3, 6)

                        + ' ' + plain.substring(6, 9)

                        + ' ' + plain.substring(9, 12);


            case 13:

                // 13 pattern 4-3-3-3

                return plain.substring(0, 4)

                        + ' ' + plain.substring(4, 7)

                        + ' ' + plain.substring(7, 10)

                        + ' ' + plain.substring(10, 13);


            case 14:

                // 14 pattern 2-4-4-4

                return plain.substring(0, 2)

                        + ' ' + plain.substring(2, 6)

                        + ' ' + plain.substring(6, 10)

                        + ' ' + plain.substring(10, 14);


            case 15:

                // 15 pattern 3-4-4-4

                return plain.substring(0, 3)

                        + ' ' + plain.substring(3, 7)

                        + ' ' + plain.substring(7, 11)

                        + ' ' + plain.substring(11, 15);


            case 16:

                // 16 pattern 4-4-4-4

                return plain.substring(0, 4)

                        + ' ' + plain.substring(4, 8)

                        + ' ' + plain.substring(8, 12)

                        + ' ' + plain.substring(12, 16);


            case 17:

                // 17 pattern 1-4-4-4-4

                return plain.substring(0, 1)

                        + ' ' + plain.substring(1, 5)

                        + ' ' + plain.substring(5, 9)

                        + ' ' + plain.substring(9, 13)

                        + ' ' + plain.substring(13, 17);


            default:

                // 0..11, 18+ digits long

                // plain

                return plain;

        } // end switch


    } // end toPrettyString


    /**
     * Converts a vendor index enumeration to the equivalent words.
     * <p/>
     * It will trigger an ArrayIndexOutOfBoundsException
     * <p/>
     * if you feed it an illegal value.
     *
     * @param vendorEnum e.g. AMEX, UNKNOWN_VENDOR, TOO_MANY_DIGITS
     * @return equivalent string in words, e.g. "Amex" "Error: unknown vendor".
     */

    public static String vendorToString(int vendorEnum) {

        return vendors[vendorEnum - NOT_ENOUGH_DIGITS];

    } // end vendorToString


    /**
     * Used to speed up findMatchingRange by caching the last hit.
     */

    private static int cachedLastFind = 0;


    /**
     * Finds a matching range in the ranges array
     * <p/>
     * for a given creditCardNumber.
     *
     * @param creditCardNumber number on card.
     * @return index of matching range,
     *         <p/>
     *         or NOT_ENOUGH_DIGITS or UNKNOWN_VENDOR on
     *         <p/>
     *         failure.
     */

    protected static int findMatchingRange(long creditCardNumber) {


        if (creditCardNumber < 1000000000000L) {

            return NOT_ENOUGH_DIGITS;

        }

        if (creditCardNumber > 9999999999999999L) {

            return TOO_MANY_DIGITS;

        }

        // check the cached index first, where we last found a number.

        if (ranges[cachedLastFind].low <= creditCardNumber

                && creditCardNumber <= ranges[cachedLastFind].high) {

            return cachedLastFind;

        }

        for (int i = 0; i < ranges.length; i++) {

            if (ranges[i].low <= creditCardNumber

                    && creditCardNumber <= ranges[i].high) {

                // we have a match

                cachedLastFind = i;

                return i;

            }

        } // end for

        return UNKNOWN_VENDOR;


    } // end findMatchingRange


    /**
     * used in computing checksums, doubles and adds resulting digits.
     *
     * @param digit the digit to be doubled, and digit summed.
     * @return // 0->0 1->2 2->4 3->6 4->8 5->1 6->3 7->5 8->7 9->9
     */

    private static int z(int digit) {

        if (digit == 0)
            return 0;

        else
            return (digit * 2 - 1) % 9 + 1;

    }


    /**
     * convert a String to a long.  The routine is very forgiving.
     * <p/>
     * It ignores invalid chars, lead trail,
     * <p/>
     * embedded spaces, decimal points etc, AND minus signs.
     *
     * @param numstr the String containing the number
     *               <p/>
     *               to be converted to long.
     * @return long value of the string found,
     *         <p/>
     *         ignoring junk characters. May be negative.
     * @throws java.lang.NumberFormatException
     *          if the number is too big to fit in a long.
     * @see Misc.parseDirtyLong It is similar, but treats
     *      <p/>
     *      dash as a minus sign.
     */

    public static long parseDirtyLong(String numStr) {

        numStr = numStr.trim();

        // strip commas, spaces, + etc, AND -

        StringBuffer b = new StringBuffer(numStr.length());

        for (int i = 0; i < numStr.length(); i++) {

            char c = numStr.charAt(i);

            if ('0' <= c && c <= '9') {

                b.append(c);

            }

        } // end for

        numStr = b.toString();

        if (numStr.length() == 0) {

            return 0;

        }

        return Long.parseLong(numStr);

    } // end parseDirtyLong



    /*

     From http://www.icverify.com/

     Vendor        Prefix len      checkdigit

     MASTERCARD    51-55  16       mod 10

     VISA          4      13, 16   mod 10

     AMEX          34,37  15       mod 10

     Diners Club/

     Carte Blanche

                  300-305 14

                  36      14

                  38      14       mod 10

     Discover     6011    16       mod 10

     enRoute      2014    15

                  2149    15       any

     JCB          3       16       mod 10

     JCB          2131    15

                  1800    15       mod 10  */



    private static LCR[] ranges =

            {// careful, no lead zeros allowed

                //                    low               high  len  vendor      mod-10?

                new LCR(4000000000000L, 4999999999999L, 13, VISA, true),

                new LCR(30000000000000L, 30599999999999L, 14, DINERS, true),

                new LCR(36000000000000L, 36999999999999L, 14, DINERS, true),

                new LCR(38000000000000L, 38999999999999L, 14, DINERS, true),

                new LCR(180000000000000L, 180099999999999L, 15, JCB, true),

                new LCR(201400000000000L, 201499999999999L, 15, ENROUTE, false),

                new LCR(213100000000000L, 213199999999999L, 15, JCB, true),

                new LCR(214900000000000L, 214999999999999L, 15, ENROUTE, false),

                new LCR(340000000000000L, 349999999999999L, 15, AMEX, true),

                new LCR(370000000000000L, 379999999999999L, 15, AMEX, true),

                new LCR(3000000000000000L, 3999999999999999L, 16, JCB, true),

                new LCR(4000000000000000L, 4999999999999999L, 16, VISA, true),

                new LCR(5100000000000000L, 5599999999999999L, 16, MASTERCARD, true),

                new LCR(6011000000000000L, 6011999999999999L, 16, DISCOVER, true)

            }; // end table initialisation


    /**
     * test harness
     */

    public static void main(String[] args) {


        if (true) {


            //////////log.debug(recognizeVendor(0)); // -2

            //////////log.debug(recognizeVendor(6011222233334444L)); // 3

            //////////log.debug(recognizeVendor(6010222233334444L)); // -1

            //////////log.debug(recognizeVendor(   4000000000000L)); // 7

            //////////log.debug(recognizeVendor(   4999999999999L)); // 7



            //////////log.debug(isValid(0));                  // false
                                            //log.debug(isValid(5581588024607582L));
          ////log.debug(isValid(6011222233334444L));  // true

            //////////log.debug(isValid(6010222233334444L));  // false

            //////////log.debug(isValid(   4000000000000L));  // false

            //////////log.debug(isValid(   4000000000006L));  // true

            //////////log.debug(isValid(   4000000000009L));  // false

            //////////log.debug(isValid(   4999999999999L));  // false



            //////////log.debug(parseDirtyLong("123,444 999=z/99"));



            //////////log.debug(toPrettyString(0));

            //////////log.debug(toPrettyString(6011222233334444L));

            //////////log.debug(toPrettyString(6010222233334444L));

            //////////log.debug(toPrettyString(   4000000000000L));

            //////////log.debug(toPrettyString(   4000000000006L));

            //////////log.debug(toPrettyString(   4000000000009L));

            //////////log.debug(toPrettyString(3123456789012341L));

            //////////log.debug(toPrettyString(    999999999990L));

            //////////log.debug(toPrettyString(   4000000000006L));

            //////////log.debug(toPrettyString(  30000000000004L));

            //////////log.debug(toPrettyString( 180000000000002L));

            //////////log.debug(toPrettyString(3000000000000004L));

            //////////log.debug(toPrettyString(3000000000000005L));

            //////////log.debug(toPrettyString(13000000000000005L));





            //////////log.debug(vendorToString(VISA));

            //////////log.debug(vendorToString(UNKNOWN_VENDOR));

        } // end if debugging

    }  // end main


} // end class CreditCard


/**
 * Describes a single Legal Card Range
 */

class LCR {


    /**
     * public constructor
     */

    public LCR(long low,

               long high,

               int length,

               int vendor,

               boolean hasCheckDigit) {

        this.low = low;

        this.high = high;

        this.length = length;

        this.vendor = vendor;

        this.hasCheckDigit = hasCheckDigit;

    } // end public constructor


    /**
     * low and high bounds on range covered by this vendor
     */

    public long low, high;


    /**
     * how many digits in this type of number.
     */

    public int length;


    /**
     * enumeration credit card service
     */

    public int vendor;


    /**
     * does this range have a MOD-10 checkdigit?
     */

    public boolean hasCheckDigit;


} // end class LCR











