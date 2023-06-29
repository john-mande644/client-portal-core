package com.owd.jobs.jobobjects

import com.owd.core.OWDUtilities
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdOrder
import groovy.util.slurpersupport.GPathResult
import groovy.xml.XmlUtil
import groovyx.net.http.RESTClient
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory

import java.security.KeyStore
import java.security.MessageDigest

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 6/4/13
 * Time: 9:46 AM
 * Posts OWD_ORDER_STATUS XML from OWD API to designated endpoint with a query param of "sig" of value md5 hash of xml body with secret value appended
 *
 */
class OrderShipmentPingback {
    static List<String> customSslKeystoreDomains;

    static
    {
        customSslKeystoreDomains = new ArrayList<String>()

        customSslKeystoreDomains.add("tonx.org")
      //  customSslKeystoreDomains.add("bluebottlecoffee.com")
    }

    public static boolean sendOrderStatusPingback(OwdOrder order, String url, String secret) {

        String content = getOrderStatusXml(order)

        println content


        RESTClient endpoint = new RESTClient(url)

        try {   //for unrecognized ssl certs

            boolean useCustomKeystore = false

            for (String domain : customSslKeystoreDomains) {
                if (url.contains(domain)) {
                    useCustomKeystore = false
                }

            }
            if (useCustomKeystore) {
                def keyStore = KeyStore.getInstance(KeyStore.defaultType)

                getClass().getResource("/truststore.jks").withInputStream {
                    keyStore.load(it, "owd7172".toCharArray())
                }

                endpoint.client.connectionManager.schemeRegistry.register(
                        new Scheme("https", new SSLSocketFactory(keyStore), 443))
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        def resp = endpoint.post(
                query: [sig: generateMD5(content + secret)],
                requestContentType: groovyx.net.http.ContentType.XML,
                contentType: groovyx.net.http.ContentType.TEXT,   //because otherwise it reads the JSON response as in indicated gzip encoding, then complains it's not in gzip format and dies.
                body: content
        )

        if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}
        return true
    }

    public static void main(String[] args) throws Exception {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, 11077035) ;

       // def gotMessage = OrderShipmentPingback.sendOrderStatusPingback(order, "https://bluebottlecoffee.com/api/owd_order_postbacks.json", "wr0sjh4vlikbsrzn") ;

        println(getOrderStatusXml(order))
        //  sendOrderStatusPingback(order, "https://xxk8m1jheq0u.runscope.net", "wr0sjh4vlikbsrzn")
    }

    public static String getOrderStatusXml(OwdOrder order) {

        // setMetaClass(DefaultGroovyMethods.getMetaClass(getClass()))

        RESTClient endpoint = new RESTClient("https://secure.owd.com")


        def resp = endpoint.post(
                path: '/api/api.jsp',
                requestContentType: groovyx.net.http.ContentType.XML,
                body: {//mkp.xmlDeclaration()
                    mkp.xmlDeclaration()
                    OWD_API_REQUEST(api_version: '1.9', client_authorization: OWDUtilities.encryptData("" + order.getClientFkey()), client_id: order.getClientFkey(), testing: 'FALSE') {
                        OWD_ORDER_STATUS_REQUEST(clientOrderId: order.getOrderRefnum()) {
                        }
                    }
                }
        )


        if (!(resp.status == 200 || resp.status == 201)) { throw new Exception("Bad response status: "+resp.status);}
        assert (resp.data instanceof GPathResult)
        return XmlUtil.serialize(resp.data.OWD_ORDER_STATUS_RESPONSE[0])

    }

    def static generateMD5(String s) {
        MessageDigest digest = MessageDigest.getInstance("MD5")
        digest.update(s.bytes);
        new BigInteger(1, digest.digest()).toString(16).padLeft(32, '0')
    }
}
