package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Element;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 9/26/11
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class TestOrderReleaseRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();

      public static final String kRootTag = "OWD_TEST_ORDER_RELEASE_REQUEST";
      /*
      <OWD_API_REQUEST api_version='1.0' client_authorization='abc123' client_id='123' testing='FALSE'>
    <OWD_TEST_ORDER_RELEASE_REQUEST>
        <ORDER>BPC-GU-MLBUNIV</ORDER>
        <URL>http://www.client.com/script.action</URL>
        <TYPE>(PDF|ORDER|ALL)</TYPE>
    </OWD_TEST_ORDER_RELEASE_REQUEST>
</OWD_API_REQUEST>
       */

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {
        XPath xPath = XPathFactory.newInstance().newXPath();

        try {
            TestOrderReleaseResponse response = new TestOrderReleaseResponse(api_version);
             int isTest = 0;

            ResultSet rs = HibernateSession.getResultSet("select count(*) from owd_client_test_accounts where client_fkey="+client.client_id);
            while(rs.next())
            {
                isTest = rs.getInt(1);
            }
            rs.close();
            HibernateSession.closeSession();
            if(isTest!=1 && (!("test".equalsIgnoreCase(System.getProperty("com.owd.environment")))))
            {
               // throw new APIContentException("This call can only be used with test accounts!");
            }
            String oref = xPath.evaluate( "./ORDER",root);
            if (oref == null) {
                throw new APIContentException("Required ORDER element not found");
            }
            oref = oref.trim();

            if (oref.length() < 1) {
                throw new APIContentException("Required ORDER element empty - must have a minimum length of one character");
            }
            List orderIDList = ConnectionManager.getOrderKeyForClientID(oref, client.client_id, "FALSE");

            log.debug("got first list size=" + orderIDList.size() + " for " + oref + " and " + client.client_id);
            if (orderIDList.size() < 1)
            {

                String orderID = ConnectionManager.getOrderKey(oref, client.client_id);
                log.debug("got order ID " + orderID + " for " + oref + " and " + client.client_id);

                if (orderID != null)
                {
                    orderIDList.add(orderID);
                } else
                {
                    throw new APIContentException("Order ID not recognized");
                }
            }
            log.debug("got orderlist size=" + orderIDList.size());
            if (orderIDList.size() > 1)
            {
                throw new APIContentException("Multiple orders returned by search; an unambiguous order reference must be used instead");
            }
            String orderID = (String) orderIDList.get(0);


            if (orderID == null)
            {
                orderID = ConnectionManager.getOrderKey(oref, client.client_id);
            }


            if (orderID == null) throw new APIContentException("Order ID not recognized");

            String url = xPath.evaluate( "./URL",root);
            if(url==null)  throw new APIContentException("URL element is required");

            log.debug(url);

            String checktype = xPath.evaluate( "./TYPE",root);
            if (!("PDF".equals(checktype) || "ALL".equals(checktype) || "ORDER".equals(checktype))) {
                throw new APIContentException("TYPE value " + checktype + " must be PDF or ORDER or ALL");
            }
            boolean ordercheck = (!("PDF".equals(checktype)));
            boolean pdfcheck = (!("ORDER".equals(checktype)));
            byte[] pdfdata = null;
            try
            {

                pdfdata = RemoteOrderReleaseAPI.getOrderReleaseApprovalAndPackingSlip(url, Integer.parseInt(orderID), pdfcheck);
                if(pdfdata == null && pdfcheck)
                {
                    throw new Exception("PDF slip requested, but no valid data was returned");
                }
            }catch(OrderInvalidatedException ex)
            {
                throw new APIContentException(ex.getMessage());
            }catch(Exception ex)
            {
                throw new APIContentException(ex.getMessage());
            }


            if(pdfdata!=null) {
                response.setPdfData(Base64.encodeBase64String(pdfdata));
            }

            response.setReleased(true);

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            HibUtils.rollback(HibernateSession.currentSession());

            throw ex;

        } finally {
            HibUtils.rollback(HibernateSession.currentSession());

        }

}

}
