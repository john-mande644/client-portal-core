package com.owd.jobs.jobobjects

import com.owd.core.OWDUtilities
import ipworks.Ftp
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Created by stewartbuskirk1 on 10/9/15.
 */
class FtpActiveConnector extends FtpConnector {
    private final static Logger log =  LogManager.getLogger();



    public FtpActiveConnector(String ahost, String alogin, String apass, String aRemotePath) {
        super(ahost,alogin,apass,aRemotePath);
        ftp.setPassive(false);
    }
}
