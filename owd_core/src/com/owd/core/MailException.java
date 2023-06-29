package com.owd.core;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 27, 2003
 * Time: 5:39:39 PM
 * To change this template use Options | File Templates.
 */
public class MailException extends Exception {
    public MailException() {
        super();
    }

    public MailException(String message) {
        super(message);

    }

    public MailException(String message, Throwable th) {
        super(message, th);
    }
}
