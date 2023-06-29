package com.owd.core.ftp;

/**
 * When there are any FTP command file occur in the FTP bean,<br>
 * This exception is throwed.
 */

public class FtpException extends Exception {
    public FtpException(String message) {
        super(message);
    }
}
