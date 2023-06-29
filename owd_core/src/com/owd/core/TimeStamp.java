package com.owd.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


/**
 * This is class TimeStamp - it has a print method which will keep track of the last
 * <p/>
 * time it was called and print a message along with the elapsed time since the last call.
 *
 * @author Joy Kyriakopulos (joyk@fnal.gov)
 * @version $Id: TimeStamp.java,v 1.6 2008/01/08 20:20:12 stewart Exp $
 */


public class TimeStamp {
private final static Logger log =  LogManager.getLogger();


    private long pastTime;

    private boolean silent = false;


    public TimeStamp() {

        pastTime = System.currentTimeMillis();

    }


    public TimeStamp(String str) {

        this();

        log.debug(str + " - begun at: " + new Date(pastTime));

    }


    public void print(String str) {

        if (!silent) {

            long now = System.currentTimeMillis();

            log.debug(str + " - elapsed time: " + (now-pastTime) + " milliseconds.");

            pastTime = now;

        }

    }


    public void setSilent() {

        silent = true;

    }

    /**
     * A static method for getting a single, global instance of TimeStamp.
     */

    public static TimeStamp sharedInstance() {

        if (sharedInstance == null) sharedInstance = new TimeStamp("Shared Timestamp");

        return sharedInstance;

    }

    private static TimeStamp sharedInstance;

}
