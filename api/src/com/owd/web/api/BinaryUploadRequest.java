package com.owd.web.api;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 2/7/14
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.managers.ScanManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.Receive;
import org.apache.commons.codec.binary.Base64;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 9/26/11
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class BinaryUploadRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();

    public static final String kRootTag = "OWD_BINARY_UPLOAD_REQUEST";
    /*
    <OWD_API_REQUEST api_version='1.0' client_authorization='WtchocYfUf+MLPZtqPlOBwI=' client_id='55'
    UPLOAD_TYPE = '' ID = '' testing='FALSE'>
  <OWD_BINARY_UPLOAD_REQUEST>
      <ORDER>BPC-GU-MLBUNIV</ORDER>
      <URL>http://www.client.com/script.action</URL>
      <TYPE>(PDF|ORDER|ALL)</TYPE>
  </OWD_TEST_ORDER_RELEASE_REQUEST>
</OWD_BINARY_UPLOAD_REQUEST>
     */
    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {
            int isTest = 0;

            log.debug("BinaryUploadRequest.handle");

            String uploadType = XPathAPI.eval(root, "@upload_type").toString();
            String dataType = XPathAPI.eval(root, "@data_type").toString();
            String identifier = XPathAPI.eval(root, "@id").toString();
            String data = XPathAPI.eval(root, "@data").toString();
            String sqlDateTimeForToday = OWDUtilities.getSQLDateTimeForToday();

            uploadType = root.getAttribute("upload_type");

            log.debug("uploadType: " + uploadType);

            if (uploadType == null || uploadType.trim().length() < 1) {
                throw new APIContentException("Required Upload Type not found");
            }

            if (dataType == null || dataType.trim().length() < 1) {
                throw new APIContentException("Required Data Type not found");
            }

            if (identifier == null || identifier.trim().length() < 1) {
                throw new APIContentException("Required Identifier not found");
            }

            if (data == null || data.trim().length() < 1) {
                throw new APIContentException("Required Data not found");
            }
            boolean success = false;

            if ("RECEIVE_SCAN".equals(uploadType) && "PDF".equals(dataType)) {
                success = saveReceiveScanPDF(identifier, sqlDateTimeForToday, data);
            }
            HibUtils.commit(HibernateSession.currentSession());

            BinaryUploadResponse response = new BinaryUploadResponse(api_version);
            response.setSuccess(success);

            HibUtils.commit(HibernateSession.currentSession());

            return response;

        } catch (Exception ex) {
            System.err.println("BinaryUploadRequest.handle: " + ex.getLocalizedMessage());
            ex.printStackTrace();
            HibUtils.rollback(HibernateSession.currentSession());

            throw ex;
        }
    }

    /**
     *
     * @param receiveId
     * @param datetime
     * @param data
     * @return
     */
    private boolean saveReceiveScanPDF(String receiveId, String datetime, String data) {

        boolean success = false;

        try {
            datetime = datetime.replace(":", "-");
            datetime = datetime.replace(" ", "-");
            String fileName = receiveId + "_" + datetime + ".pdf";

            log.debug("receiveId: " + receiveId);
            log.debug("fileName: " + fileName);

            Receive receive = (Receive)HibernateSession.currentSession().load((Receive.class), new Integer(receiveId));

            byte[] decodedBytes = Base64.decodeBase64(data);

            ScanManager.addScanToReceive(receive, decodedBytes, fileName, "");

            success = true;

        } catch (Exception e)  {
            System.err.println("Error - saveReceiveScanPDF: " + e.getLocalizedMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return success;
    }
}
