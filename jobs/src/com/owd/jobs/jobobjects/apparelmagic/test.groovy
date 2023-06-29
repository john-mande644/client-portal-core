package com.owd.jobs.jobobjects.apparelmagic

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import groovy.json.JsonOutput

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.SignatureException


class test {

    public static void main(String[] args) {


        //filter for retrieving pick_tickets by order_id
        Map filterMap = new HashMap()
        filterMap.put('field','pick_tickets.order_id')
        filterMap.put('value','1003')

        println getPHPRequestString("xxx","yyy",filterMap)

        //no filter - just minimum params to authorize call - can pass null or empty map for second parameter
        println getPHPRequestString("xxx","yyy",null)

        //Use return value from getPHPRequestString as raw query parameters value (after the "?" in URL) or as POST/PUT data payload.

    }


    public static String getPHPRequestString(String publicKey, String privateKey, Map filterMap)
    {

        Map<String,Object> m = [time: ''+((int) (System.currentTimeMillis() / 1000L)), api_key: publicKey]

        if(filterMap?.keySet()?.size()>0) {
            m.put('parameters', filterMap)//builder.toString());
        }

        String hash = calculateRFC2104HMAC(URLEncoder.encode(JsonOutput.toJson(m).toString()), privateKey)
        m.put('hash',hash)
        //  println m
        return PhpSimulator.httpBuildQuery(m,'UTF-8')

        // strBuilder.append(",{\"hash\":\""+calculateRFC2104HMAC(jsonRequest,key)+"\"}}")
    }


/**
 * Computes RFC 2104-compliant HMAC signature.
 * * @param data
 * The data to be signed.
 * @param key
 * The signing key.
 * @return
 * The Base64-encoded RFC 2104-compliant HMAC signature.
 * @throws
 * java.security.SignatureException when signature generation fails
 */
    public static String calculateRFC2104HMAC(String data, String key)
            throws SignatureException
    {
        String result;
        try {

// get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");

// get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

// compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());
            //   println rawHmac
// base64-encode the hmac
            result = org.codehaus.groovy.runtime.EncodingGroovyMethods.encodeHex(rawHmac);

        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}
