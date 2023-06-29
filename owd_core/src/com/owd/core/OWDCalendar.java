/*
 * OWDCalendar.java
 * 
 * Created on March 3, 2003, 3:29 PM
 */

package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author liheng
 */
public class OWDCalendar extends GregorianCalendar {
private final static Logger log =  LogManager.getLogger();

    /**
     * Creates a new instance of OWDCalendar
     */
    public OWDCalendar() {
        super();
    }

    public void roll(int field, int roll_amount) {
        if (field == Calendar.DATE) {
            int day = get(Calendar.DAY_OF_MONTH);
            if (roll_amount < 0) {
                if ((day + roll_amount) >= getActualMinimum(Calendar.DATE)) {
                    super.roll(Calendar.DATE, roll_amount);

                } else {
                    super.roll(Calendar.MONTH, -1);
                    int adjust_amount = roll_amount + getActualMaximum(Calendar.DAY_OF_MONTH);
                    roll(Calendar.DAY_OF_MONTH, adjust_amount);
                }
            } else {
                if (roll_amount <= (getActualMaximum(Calendar.DATE) - day)) {
                    super.roll(Calendar.DATE, roll_amount);

                } else {
                    int adjusted_amount = roll_amount - getActualMaximum(Calendar.DATE);
                    super.roll(Calendar.MONTH, 1);
                    roll(Calendar.DATE, adjusted_amount);
                }
            }
        } else
            super.roll(field, roll_amount);

    }
    
    /*
    public static void main(String[] args)
    {
        OWDCalendar today = new OWDCalendar(); 
        //log.debug("What's the date? "+(today.get(Calendar.MONTH)+1)+"-"+today.get(Calendar.DATE));
        today.roll(Calendar.DATE, -34);
        //log.debug("What was the date for four  days ago? "+(today.get(Calendar.MONTH)+1)+"-"+today.get(Calendar.DAY_OF_MONTH));
        today = new OWDCalendar();
        today.roll(Calendar.DATE, 64);
        //log.debug("What was the date after 34 days? "+(today.get(Calendar.MONTH)+1)+"-"+today.get(Calendar.DAY_OF_MONTH));
    }
     */
}
