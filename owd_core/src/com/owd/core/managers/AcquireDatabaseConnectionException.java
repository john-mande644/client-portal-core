/*
 * AcquireDatabaseConnectionException.java
 *
 * Created on September 22, 2003, 7:39 PM
 */

package com.owd.core.managers;

/**
 * @author liheng
 */
public class AcquireDatabaseConnectionException extends Exception {

    /**
     * Creates a new instance of AcquireDatabaseConnectionException
     */
    public AcquireDatabaseConnectionException() {
        super();
    }

    public AcquireDatabaseConnectionException(String message) {
        super(message);
    }

    public AcquireDatabaseConnectionException(String message, Throwable th) {
        super(message + " " + th.getMessage());
    }

}
