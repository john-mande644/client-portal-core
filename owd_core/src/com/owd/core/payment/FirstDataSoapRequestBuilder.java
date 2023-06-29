package com.owd.core.payment;

import com.owd.core.WebResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/17/12
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class FirstDataSoapRequestBuilder {
private final static Logger log =  LogManager.getLogger();
// The following section sets the connection requirements.
protected static final String[] kFirstDataTransTypes = {"01", "02", "00", "13", "04", ""};
//    protected static final String[] kFirstDataTransTypes = {"AUTH_ONLY", "PRIOR_AUTH_CAPTURE", "AUTH_CAPTURE", "VOID", "CREDIT", "CHECK"};

    static String Host = "api.globalgatewaye4.firstdata.com";
    static String WebServicePath = "/transaction/v8";
    static String SoapAction = "api.globalgatewaye4.firstdata.com/vplug-in/transaction/rpc-enc/SendAndCommit";
    static String MethodName = "SendAndCommit";
    static String XmlNamespace = "api.globalgatewaye4.firstdata.com/vplug-in/transaction/rpc-enc/Request";

   static  public String sendRequest(Hashtable source) {
        String retval = "";


       BufferedReader in = null;
       try{


        try {

            Enumeration e = source.elements();

            StringBuffer outBuffer = new StringBuffer();

            // post header
          /*  outBuffer.append("POST " + WebServicePath + " HTTP/1.0\r\n");
            outBuffer.append("Host: " + Host + "\r\n");
            outBuffer.append("Content-Type:text/xml;charset=utf-8\r\n");
            outBuffer.append("Content-Length:" + String.valueOf(length) + "\r\n");
            outBuffer.append("SOAPAction:\"" + SoapAction + "\"" + "\r\n");
            outBuffer.append("\r\n");
*/
            // XML packet to process transactions.
            outBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + "\n");
            outBuffer.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:tns=\"http://secure2.e-xact.com/vplug-in/transaction/rpc-enc/\" xmlns:types=\"http://secure2.e-xact.com/vplug-in/transaction/rpc-enc/encodedTypes\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "\n");
            outBuffer.append("<soap:Body soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + "\n");
            outBuffer.append("<q1:" + MethodName + " xmlns:q1=\"" + XmlNamespace + "\">" + "\n");
            outBuffer.append("<SendAndCommitSource href=\"#id1\" />" + "\n");
            outBuffer.append("</q1:" + MethodName + ">" + "\n");
            outBuffer.append("<types:Transaction id=\"id1\" xsi:type=\"types:Transaction\">" + "\n");

            // Populate the fields in the xml packet.
            e = source.keys();
            while ( e.hasMoreElements() ) {
                String key = (String)e.nextElement();
                String value = (String)source.get(key);
                outBuffer.append("<" + key + " xsi:type=\"xsd:string\">" + value + "</" + key + ">\n");
            }

            outBuffer.append("</types:Transaction>" + "\n");
            outBuffer.append("</soap:Body>" + "\n");
            outBuffer.append("</soap:Envelope>");
            // end of XML packet

            // Print out the packet to stdout for your convenience.
            String sOutBuffer = outBuffer.toString();
            log.debug(sOutBuffer);

            WebResource server = new WebResource("https://"+Host+WebServicePath,WebResource.kPOSTMethod);//,(String)source.get("ExactID"),(String)source.get("Password"));

            server.setContent(sOutBuffer);
            BufferedReader reader = null;
            server.contentType="application/xml;charset=utf-8";
        //    server.addHeader("SOAPAction",""+SoapAction+"");
            server.addHeader("Content-Length",sOutBuffer.length()+"");
            server.addHeader("Host",Host);

            try {

                reader = server.getResource();

            } catch (Exception ex) {

                if (reader == null)

                    reader = server.getResource();

            }

            log.debug("done talking to Anet server ");

            StringBuffer sb = new StringBuffer();
            String inputLine = "";

            while ((inputLine = reader.readLine()) != null)
            {
                //log.debug(inputLine);
                sb.append(inputLine);
            }
            reader.close();
            // The StringBuffer result now contains the complete result from the
            // webservice in XML format.  You can parse this XML if you want to
            // get more complex results than a single value.

            return sb.toString();
        }
        catch (Exception ex) {
            log.debug(ex.getMessage());
            return ex.getMessage();
        }
       }finally
       {
           try{
           in.close();  }catch(Exception ex){}
       }
    }


    static  public String sendRequest2(Hashtable source) {
        String retval = "";


        SSLSocket socket = null;
        BufferedReader in = null;
        try{
            try {
                SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
                socket = (SSLSocket)factory.createSocket(Host, 443);
            }
            catch (Exception ex1) {
                return ("Error: "+ex1.getMessage());
            }

            try {

                // Sum up all the lengths of field values.
                int length = 2330;      // Original xml packet size without the variable data.

                // Sum up all the lengths of field values.
                length += (MethodName.length() * 2) + XmlNamespace.length();
                Enumeration e = source.elements();
                while ( e.hasMoreElements() ) {
                    String value = (String)e.nextElement();
                    length += value.length();
                }

                StringBuffer outBuffer = new StringBuffer();

                // post header

                // XML packet to process transactions.
                outBuffer.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + "\n");
                outBuffer.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:tns=\"http://secure2.e-xact.com/vplug-in/transaction/rpc-enc/\" xmlns:types=\"http://secure2.e-xact.com/vplug-in/transaction/rpc-enc/encodedTypes\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" + "\n");
                outBuffer.append("<soap:Body soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + "\n");
                outBuffer.append("<q1:" + MethodName + " xmlns:q1=\"" + XmlNamespace + "\">" + "\n");
                outBuffer.append("<SendAndCommitSource href=\"#id1\" />" + "\n");
                outBuffer.append("</q1:" + MethodName + ">" + "\n");
                outBuffer.append("<types:Transaction id=\"id1\" xsi:type=\"types:Transaction\">" + "\n");

                // Populate the fields in the xml packet.
                e = source.keys();
                while ( e.hasMoreElements() ) {
                    String key = (String)e.nextElement();
                    String value = (String)source.get(key);
                    outBuffer.append("<" + key + " xsi:type=\"xsd:string\">" + value + "</" + key + ">\n");
                }

                outBuffer.append("</types:Transaction>" + "\n");
                outBuffer.append("</soap:Body>" + "\n");
                outBuffer.append("</soap:Envelope>");
                // end of XML packet

                StringBuffer abuffer = new StringBuffer() ;
                abuffer.append("POST " + WebServicePath + " HTTP/1.0\r\n");
                abuffer.append("Host: " + Host + "\r\n");
                abuffer.append("Content-Type:text/xml;charset=utf-8\r\n");
                abuffer.append("Content-Length:" + outBuffer.toString().length() + "\r\n");
            //    abuffer.append("SOAPAction:\"" + SoapAction + "\"" + "\r\n");
                abuffer.append("\r\n");

                // Print out the packet to stdout for your convenience.
                String sOutBuffer = outBuffer.toString();
                log.debug(sOutBuffer);
                sOutBuffer = abuffer+sOutBuffer;

                log.debug("sockets");
                OutputStream os = socket.getOutputStream();
                boolean autoflush = true;

                PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);

                log.debug("sending");
                // send an HTTPS request to the web service
                out.println(sOutBuffer);
                log.debug("sent");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                log.debug("got is");

                StringBuffer sb = new StringBuffer();     // Result buffer.
                char[] cb = new char[1];
                int r = 0;
                while ( (r = in.read(cb, 0, 1) ) != -1 ) {
                    log.debug("reading");
                    sb.append(cb, 0, r);
                }
                // Close read buffer and close socket connnection.
                in.close();
                socket.close();

                // The StringBuffer result now contains the complete result from the
                // webservice in XML format.  You can parse this XML if you want to
                // get more complex results than a single value.

                return sb.toString();
            }
            catch (Exception ex) {
                log.debug(ex.getMessage());
                return ex.getMessage();
            }
        }finally
        {
            try{
                in.close();  }catch(Exception ex){}
            try{
                socket.close();   }catch(Exception ex){}
        }
    }
}
