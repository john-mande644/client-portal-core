package com.owd.core.managers;

import com.owd.core.WebResource;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.IntravexEbill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.Security;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Mar 17, 2004
 * Time: 1:15:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class IntravexEbillManager {
private final static Logger log =  LogManager.getLogger();

    public static Iterator getBillingItemsForInvoiceNumber(String theInvoice) throws Exception {
        Session sess = HibernateSession.currentSession();

        try {

            return (sess.createQuery("from IntravexEbill billing  left join fetch billing.owdClient  where billing.invoiceNumber =  ? and parent_ups_key is null order by billing.serviceType, billing.owdClient.companyName")
                    .setString(1,theInvoice).list().iterator());

        } catch (Exception e) {
            throw e;
        } finally {
            // HibernateSession.closeSession();
        }
    }

    public static IntravexEbill getBillingItemForItemID(String theID) throws Exception {
        Session sess = HibernateSession.currentSession();

        try {

            return ((IntravexEbill) sess.load(IntravexEbill.class, new Integer(theID)));

        } catch (Exception e) {
            throw e;
        } finally {
            // HibernateSession.closeSession();
        }
    }

    public static Iterator getUnresolvedBillingItemsForInvoice(String invoiceNumber) throws Exception {
        Session sess = HibernateSession.currentSession();

        try {

            return (sess.createQuery("from IntravexEbill as ups left join fetch ups.owdClient where ups.needsReview = 1 and ups.invoiceNumber = ? order by ups.owdClient.companyName").setString(0,invoiceNumber)).list().iterator();


        } catch (Exception e) {
            throw e;
        } finally {
            // HibernateSession.closeSession();
        }
    }

  /*  public static Iterator getInvoiceClientTypeItems(String invoiceNumber, String tcode, String clientID) throws Exception {
        Session sess = HibernateSession.currentSession();

        try {

            if ("0".equals(clientID) || clientID == null) {
                return (sess.createQuery("from UpsEbill as ups left join fetch ups.owdClient where ups.needsReview = 1 and " +
                        "ups.invoiceNumber = ? and ups.transactionCode =? " +
                        "and parent_ups_key is null  order by ups.owdClient.companyName").setString(0,invoiceNumber)).setString(1,tcode).list().iterator();
            } else {
                return (sess.createQuery("from UpsEbill as ups left join fetch ups.owdClient " +
                        "where ups.invoiceNumber =? " +
                        "and ups.transactionCode =? " +
                        "and ups.owdClient.clientId =? and parent_ups_key is null order by ups.id")).setString(0,invoiceNumber)
                .setString(1,tcode)
                .setInteger(2,new Integer(clientID)).list().iterator();
            }

        } catch (Exception e) {
            throw e;
        } finally {
            // HibernateSession.closeSession();
        }
    }

*/
    public static Iterator getSubItemsForMultishipItem(Integer parentId) throws Exception {
        Session sess = HibernateSession.currentSession();

        try {

            return (sess.createQuery("from IntravexEbill as ups left join fetch ups.owdOrderTrack where ups.parentUpsKey = ? order by ups.owdOrderTrack.lineIndex").setInteger(0,parentId).list().iterator());
        } catch (Exception e) {
            throw e;
        } finally {
            // HibernateSession.closeSession();
        }
    }

    public static void main(String[] args) {

        try {
            Session sess = HibernateSession.currentSession();

           /* Iterator it = (sess.find("from UpsEbill as ups where ups.transactionCode in ('MAN','WWS','') and len(ups.trackingNumber)>6 and ups.netCharges > 0 and ups.upsStatus not in ('DELIVERED','BILLING INFORMATION VOIDED') and ups.billDate>='2004-4-1'")).iterator();
            //log.debug("Got rows 2 ...");
            while (it.hasNext()) {
                UpsEbill item = (UpsEbill) it.next();
                String upsText;
                //log.debug("Running " + item.getTrackingNumber());

                upsText = UPSEbillManager.getUPSPackageTrackingInfo(item.getTrackingNumber());
                if (upsText.length() > 0) {
                    item.setUpsText(upsText);
                    item.setUpsStatus(UPSEbillManager.getUPSReportedPackageStatus(upsText));
                }
                //log.debug("Saving " + item.getUpsStatus());
                sess.saveOrUpdate(item);
                sess.flush();
                HibUtils.commit(sess);
            }
            */
            testUPSTrack(args);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // HibernateSession.closeSession();
        }

    }

    public static void testUPSTrack(String[] args) {
        try {


            //log.debug(getUPSReportedPackageStatus("1ZE587150327058538"));


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {


        }
    }

    public static String getUPSReportedPackageStatus(String trackResponseString) {
        String response = "ERROR";
        try {

            String testRequest=
                              "<?xml version=\"1.0\"?>\n" +
                              "<AccessRequest xml:lang=\"en-US\">\n" +
                              "   <AccessLicenseNumber>CC15D99533A01C04</AccessLicenseNumber>\n" +
                              "   <UserId>upsowd</UserId>\n" +
                              "   <Password>owd7172</Password>\n" +
                              "</AccessRequest>\n" +
                              "<?xml version=\"1.0\"?>\n" +
                              "<TrackRequest xml:lang=\"en-US\">\n" +
                              "  <Request>\n" +
                              "    <TransactionReference>\n" +
                              "      <CustomerContext>Example 1</CustomerContext>\n" +
                              "      <XpciVersion>1.0001</XpciVersion>\n" +
                              "    </TransactionReference>\n" +
                              "    <RequestAction>Track</RequestAction>\n" +
//                    "    <RequestOption>activity</RequestOption>\n" +
                              "  </Request>\n" +
                              "  <TrackingNumber>"+trackResponseString+"</TrackingNumber>\n" +
                              "</TrackRequest>";

                      WebResource server = new WebResource("https://www.ups.com/ups.app/xml/Track",WebResource.kPOSTMethod);
                      //HttpURLConnection cxn = getConnection("https://wwwcie.ups.com/ups.app/xml/Track");

                     server.setContent(testRequest);
                      server.contentType="text/xml";


                      //log.debug("\nResponse:");
                      // //log.debug("\nResponse:");
                    //  request.marshal(writer);

                      BufferedReader rdr = server.getResource();

                      String line = "";
                      StringBuffer buf = new StringBuffer();

                      while ((line = rdr.readLine()) != null) {
                          buf.append(line);
                      }
                      //log.debug(buf);
                          /*
                            <Status>
          <StatusType>
            <Code>M</Code>
            <Description>PICKUP MANIFEST RECEIVED</Description>
          </StatusType>
          <StatusCode>
            <Code>MP</Code>
          </StatusCode>
        </Status>
                           */
                      String data = buf.toString().substring(buf.toString().indexOf("<Status>"));
                      data = data.substring(data.indexOf("<Description>")+13);
                     response = data.substring(0,data.indexOf("</Description>"));

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return response;

    }


    public static String getUPSPackageTrackingInfo(String trackingNumber) {
        String response = "";
        try {

           String testRequest=
                              "<?xml version=\"1.0\"?>\n" +
                              "<AccessRequest xml:lang=\"en-US\">\n" +
                              "   <AccessLicenseNumber>CC15D99533A01C04</AccessLicenseNumber>\n" +
                              "   <UserId>upsowd</UserId>\n" +
                              "   <Password>owd7172</Password>\n" +
                              "</AccessRequest>\n" +
                              "<?xml version=\"1.0\"?>\n" +
                              "<TrackRequest xml:lang=\"en-US\">\n" +
                              "  <Request>\n" +
                              "    <TransactionReference>\n" +
                              "      <CustomerContext>Example 1</CustomerContext>\n" +
                              "      <XpciVersion>1.0001</XpciVersion>\n" +
                              "    </TransactionReference>\n" +
                              "    <RequestAction>Track</RequestAction>\n" +
//                    "    <RequestOption>activity</RequestOption>\n" +
                              "  </Request>\n" +
                              "  <TrackingNumber>"+trackingNumber+"</TrackingNumber>\n" +
                              "</TrackRequest>";

                      WebResource server = new WebResource("https://www.ups.com/ups.app/xml/Track",WebResource.kPOSTMethod);
                      //HttpURLConnection cxn = getConnection("https://wwwcie.ups.com/ups.app/xml/Track");

                     server.setContent(testRequest);
                      server.contentType="text/xml";


                      //log.debug("\nResponse:");
                      // //log.debug("\nResponse:");
                    //  request.marshal(writer);

                      BufferedReader rdr = server.getResource();

                      String line = "";
                      StringBuffer buf = new StringBuffer();

                      while ((line = rdr.readLine()) != null) {
                          buf.append(line);
                      }

                     response = buf.toString();

        } catch (Exception ex) {

            ex.printStackTrace();
        }


        return response;

    }


    protected static HttpURLConnection getConnection(String aLink) throws IOException {
        ////log.debug("aLink: "+aLink);
        if (aLink.startsWith("https")) {
            System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
//            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        }
        java.net.URL url = new java.net.URL(aLink);
        HttpURLConnection cxn = null;
        try {
            cxn = (HttpURLConnection) url.openConnection();
            cxn.setRequestMethod("POST");
            cxn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            cxn.setDoOutput(true);
            cxn.connect();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return cxn;
    }


}
