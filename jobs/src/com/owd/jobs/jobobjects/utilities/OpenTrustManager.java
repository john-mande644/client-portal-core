package com.owd.jobs.jobobjects.utilities;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Oct 23, 2010
 * Time: 2:30:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenTrustManager implements X509TrustManager
{
private final static Logger log =  LogManager.getLogger();

             public java.security.cert.X509Certificate[] getAcceptedIssuers() {
             return null;
            }
            public void checkClientTrusted(
             java.security.cert.X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(
             java.security.cert.X509Certificate[] certs, String authType) {
            }


}
