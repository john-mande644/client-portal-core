package com.owd.core;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 27, 2003
 * Time: 4:23:53 PM
 * To change this template use Options | File Templates.
 */
public class AuthenticationException extends Exception {
    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);

    }

    public AuthenticationException(String message, Throwable th) {
        super(message, th);

    }
}

