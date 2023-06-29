package com.owd.jobs.jobobjects.utilities;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.net.ssl.HostnameVerifier;

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Oct 23, 2010
 * Time: 2:32:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenHostnameVerifier  implements HostnameVerifier
{
private final static Logger log =  LogManager.getLogger();
     public boolean verify(String urlHostname, javax.net.ssl.SSLSession _session) {
                                         return true;
         }

}
