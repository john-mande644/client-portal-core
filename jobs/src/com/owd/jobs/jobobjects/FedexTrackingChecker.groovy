package com.owd.jobs.jobobjects

import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import groovy.json.JsonOutput
import groovyx.net.http.RESTClient
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.client.HttpClient
import org.apache.http.conn.ClientConnectionManager
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.scheme.SchemeRegistry
import org.apache.http.conn.ssl.AllowAllHostnameVerifier
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.ssl.TrustStrategy
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager
import org.apache.http.protocol.HttpContext

import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * Created by stewartbuskirk1 on 11/23/15.
 */
@groovy.util.logging.Log4j2
class FedexTrackingChecker {

    public static void main(String[] args) {

        Map<Integer, String> trackMap = new HashMap<Integer, String>()
                             /*
                             select carr_service, order_refnum,order_id,order_track_id,tracking_no,group_name,o.client_fkey, '' as trackstatus
into temp_fedexmissed
                              */
        ResultSet rs = HibernateSession.getResultSet("select order_track_id,tracking_no from temp_upsmissed where trackstatus=''")

        while (rs.next()) {

                trackMap.put(rs.getInt(1), rs.getString(2))

        }
        log.debug("got "+trackMap.keySet().size()+" map entries!")
        rs.close()
        PreparedStatement ps = HibernateSession.getPreparedStatement("update temp_upsmissed set trackstatus=? where order_track_id=?")
        for (Integer key : trackMap.keySet()) {
            String status = getTrackStatus(trackMap.get(key))
            ps.setString(1,status)
            ps.setInt(2,key)
            ps.executeUpdate()
            HibUtils.commit(HibernateSession.currentSession())
        }
    }

    public static String getTrackStatus(String trackStr) {
        log.debug 'checking '+trackStr
        String status = 'UNKNOWN'
        RESTClient endpoint = new RESTClient('https://api.easypost.com/')
        endpoint.ignoreSSLIssues()
        endpoint.setClient(getTestHttpClient())
        endpoint.client.addRequestInterceptor(
                new HttpRequestInterceptor() {
                    void process(HttpRequest httpRequest, HttpContext httpContext) {
                        httpRequest.addHeader('Authorization', 'Basic ' + (('6yxRjQdTFLfXyhawqR8AVg' + ':').bytes.encodeBase64().toString()))
                    }
                });

        endpoint.post(
                path: 'v2/trackers',
                body: ['tracker[tracking_code]': trackStr, 'tracker[carrier]': 'UPS']
        )

                { resp, reader ->
                    status = reader.status
                }

        return status

    }

    public static HttpClient getTestHttpClient() {
        try {
            SSLSocketFactory sf = new SSLSocketFactory(new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }, new AllowAllHostnameVerifier());
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("https", 443, sf));
            ClientConnectionManager ccm = new ThreadSafeClientConnManager(registry);
            return new DefaultHttpClient(ccm);
        } catch (Exception e) {
            e.printStackTrace()
            return new DefaultHttpClient();
        }
    }
}
