package com.owd.core;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 28, 2003
 * Time: 3:04:37 PM
 * To change this template use Options | File Templates.
 */
public class InternalTechnicalException extends Exception {
    public InternalTechnicalException() {
        super();
    }

    public InternalTechnicalException(String message) {
        super(message);
    }

    public InternalTechnicalException(String message, Throwable th) {
        super(message, th);
    }
}
