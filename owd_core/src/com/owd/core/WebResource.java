package com.owd.core;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WebResource implements HostnameVerifier {
private final static Logger log =  LogManager.getLogger();

    public static final String kGETMethod = "GET";
    public static final String kPOSTMethod = "POST";

    public String avail = "x";
    public long contentLength = 0;
    public Map<String,String> headers = new TreeMap<String,String>();


    public void addHeader(String key, String value)
    {
        headers.put(key,value);
    }
    public WebResource(String aurl, String amethod) throws Exception {
        if (aurl == null || amethod == null)
            throw new Exception("Null values not allowed in WebResource constructor");

        url = aurl;
        setMethod(amethod);
    }

    public WebResource(String aurl, String amethod, String username, String password) throws Exception {
        if (aurl == null || amethod == null)
            throw new Exception("Null values not allowed in WebResource constructor");

        url = aurl;
        setMethod(amethod);
        user = username;
        pass = password;
    }

    public WebResource(String aurl, String amethod, String cookie) throws Exception {
        if (aurl == null || amethod == null)
            throw new Exception("Null values not allowed in WebResource constructor");

        url = aurl;
        setMethod(amethod);
        cookieStr = cookie;
    }

    public void readParametersFromURL() {
        if (url.indexOf("?") > 0) {
            String params = url.substring(url.indexOf("?") + 1);
            url = url.substring(0, url.indexOf("?"));

            while (params.indexOf("&") > 0) {
                String param = params.substring(0, params.indexOf("&"));
                addParameter(param.substring(0, param.indexOf("=")), param.substring(param.indexOf("=" + 1)));
                //log.debug("Adding param :" + param.substring(0, param.indexOf("=")) + ":,:" + param.substring(param.indexOf("=" + 1)));
                params = params.substring(params.indexOf("&") + 1);
            }
            if (params.indexOf("=") > 0) {
                addParameter(params.substring(0, params.indexOf("=")), params.substring(params.indexOf("=" + 1)));
                //log.debug("Adding param :" + params.substring(0, params.indexOf("=")) + ":,:" + params.substring(params.indexOf("=" + 1)));

            }
            //log.debug("Final url :" + url);


        }

    }

    public long getContentLength() {
        return contentLength;
    }

    public void addParameter(String name, String value) {
        if (value == null) value = "";
        if (name != null) {
            if (name.length() > 0) {
                if (buffer.length() > 0)
                    buffer.append("&");

                buffer.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value));
            }
        }
    }

    public void clearParameters() {
        buffer = new StringBuffer();
    }

   

    public void setCookie(String cookieStr) {
        this.cookieStr = cookieStr;
    }

    public boolean verify(String hostname, SSLSession session)
    {
        return true;
    }
    public void setParameters(String is) {
        clearParameters();
        buffer.append(is);                         
    }

    public void setContent(String is) {
        clearParameters();
        buffer.append(is);
    }

    public BufferedReader getResource(String referrer) throws Exception {
        referer = referrer;
        return getResource(false);
    }

    public BufferedReader getResource() throws Exception {
        return getResource(false);

    }

    public BufferedReader getResource(String referrer, boolean forceCURL) throws Exception {
        referer = referrer;
        return getResource(forceCURL);
    }

    public BufferedReader getResource(boolean forceCURL) throws Exception {
        return new BufferedReader(new InputStreamReader(getResourceAsInputStream(forceCURL),"UTF-8"));
    }

     public InputStream getResourceAsInputStream(boolean forceCURL) throws Exception {
        InputStream in = null;
        java.net.URL newurl = null;
         HttpURLConnection c = null;

        try {
            try {
                try {
                    if (kGETMethod.equals(method)) {
                        newurl = new java.net.URL(url + (buffer.length()>0?"?" + buffer.toString():""));
                    } else {
                        newurl = new java.net.URL(url);
                    }

                    log.debug(newurl.toString());
                    if(url.toUpperCase().startsWith("HTTPS"))
                    {
                        c =   (HttpsURLConnection) newurl.openConnection();
                        ((HttpsURLConnection)c).setHostnameVerifier(this);
                    }else
                    {
                    c = (HttpURLConnection) newurl.openConnection();
                    }
                    log.debug("readto="+c.getReadTimeout());
                    log.debug("connectto="+c.getConnectTimeout());
                    c.setReadTimeout(120000);
                    c.setConnectTimeout(120000);
                    c.setInstanceFollowRedirects(true);
                    c.setRequestMethod(method);
                    if (user != null && pass != null) {
                        c.setRequestProperty("Authorization", "Basic " + Base64.encodeBase64String((user + ":" + pass).getBytes()));
                    }

                    if (cookieStr != null) {
                        log.debug("setting cookie to "+cookieStr);
                        c.setRequestProperty("Cookie", cookieStr);
                    }

                    if (referer != null) {
                        c.setRequestProperty("Referer", referer);
                    }

                    c.setRequestProperty("Host", newurl.getHost());

                    if (contentType != null) {
                        c.setRequestProperty("Content-Type", contentType);
                    }

                    for(String key:headers.keySet())
                    {
                        c.setRequestProperty(key,headers.get(key));
                    }
                    if (kPOSTMethod.equals(method)) {
                        c.setDoOutput(true);
                       // PrintWriter pr = new PrintWriter(c.getOutputStream());
                        c.getOutputStream().write(buffer.toString().getBytes("UTF-8"));
                       // log.debug("PAYLOAD:"+buffer.toString());
                       // pr.close();
                        c.getOutputStream().close();
                    }
                    //fix for socket block on read issues GAAAAH!
                    /*
                    URL turl = c.getURL();
                    Socket socket = null;
                    if(url.startsWith("https"))
                    {
                        socket = new Socket(InetAddress.getByName(turl.getHost()),
                                            turl.getPort()==-1?443:turl.getPort());
                        socket.setSoTimeout(30000);
                    }
                    */

                    if (forceCURL) {
                     //ignored now in favor of always using Java HttpsURLConnection
                        //    throw new java.net.MalformedURLException();
                    }

                    try{
                    in = c.getInputStream();
                    }catch(IOException ioex)
                    {
                        in = c.getErrorStream();
                    }
                    returnHeaders = c.getHeaderFields();
                    for(String header:returnHeaders.keySet())
                    {
                        log.debug(header+":"+returnHeaders.get(header));
                    }
                    cookieStr = c.getHeaderField("set-cookie");
                    avail = c.getHeaderField("Available");
                    try {
                        contentLength = new Long(c.getHeaderField("Content-Length")).longValue();
                    } catch (Exception nex) {
                        contentLength = 0;
                    }

                    log.debug("data:"+c.getContentLength());
           } catch (java.net.MalformedURLException ex) {
                  /*       Runtime rt = Runtime.getRuntime();
                    //log.debug("/usr/bin/curl -m 10 -k -u "+user+":"+pass+" "+url+"?"+((buffer.toString().length() > 0)?buffer.toString():""));
                    //Process proc = rt.exec("curl -m 10 -2 -u "+user+":"+pass+" "+url);
                    String curlRequest = "/usr/bin/curl -m 10 -k -u " + user + ":" + pass + " " + url;
                    if (buffer.toString().length() > 0) {
                        curlRequest = curlRequest + "?" + buffer.toString();
                    }
                    Process proc = rt.exec(curlRequest);
                    in = proc.getInputStream();*/
                    ex.printStackTrace();
                    throw ex;
                }
            } catch (IOException ex) {
                  if(c != null)
                {
try{
                      c.disconnect();
}catch(Exception exxxx){}
                }
                ex.printStackTrace();
            }finally
            {

            }


            return in;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public Map<String, List<String>> returnHeaders = null;

    public void setMethod(String amethod) throws Exception {
        for (int i = 0; i < supportedMethods.length; i++) {
            if (supportedMethods[i].equals(amethod)) {
                method = amethod;
                return;
            }
        }
        throw new Exception(amethod + " is not a supported WebResource method");

    }

    public String getCookie() {
        return cookieStr;
    }

    private static final String[] supportedMethods = {kGETMethod, kPOSTMethod};
    private String user = null;
    private String pass = null;
    private String method = kGETMethod;
    public StringBuffer buffer = new StringBuffer();
    public String url = null;
    private String cookieStr = null;
    private String referer = null;
    public String contentType = null;


}
