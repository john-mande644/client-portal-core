package com.owd.onestop.test

import java.text.ParseException
import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 3/6/12
 * Time: 10:24 PM
 * To change this template use File | Settings | File Templates.
 */

public class DateToXsdDatetimeFormatter {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    public DateToXsdDatetimeFormatter () {}

   /* public DateToXsdDatetimeFormatter (TimeZone timeZone)  {
        simpleDateFormat.setTimeZone(timeZone);
    }
*/
    /**
    *  Parse a xml date string in the format produced by this class only.
    *  This method cannot parse all valid xml date string formats -
    *  so don't try to use it as part of a general xml parser
    */
    public static synchronized Date parse(String xmlDateTime) throws ParseException {
        if ( xmlDateTime.length() != 25 )  {
            throw new ParseException("Date not in expected xml datetime format", 0);
        }

        StringBuilder sb = new StringBuilder(xmlDateTime);
        sb.deleteCharAt(22);
        return simpleDateFormat.parse(sb.toString());
    }

    public static synchronized String format(Date xmlDateTime) throws IllegalFormatException {
        String s =  simpleDateFormat.format(xmlDateTime);
        StringBuilder sb = new StringBuilder(s);
        sb.insert(22, ':');
        return sb.toString();
    }

   /* public synchronized void setTimeZone(String timezone)  {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
    }*/
}