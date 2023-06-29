package com.owd.dc.manifest.api;

import com.owd.core.business.Client;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.HibernateSession;
import com.owd.connectship.xml.api.OWDShippingResponse.LISTRESPONSE;
import com.owd.connectship.xml.api.OWDShippingResponse.LISTVALUE;
import com.owd.connectship.xml.api.OWDShippingResponse.DATALIST;
import com.owd.connectship.xml.api.OWDShippingRequest.LISTREQUEST;
import com.owd.connectship.services.CSReferenceService;
import com.owd.connectship.services.ConnectShipCommunications;
import org.w3c.dom.Element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 7, 2008
 * Time: 8:52:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class ListRequest
{

     private static Map supportedLabelPrinters = new HashMap<String, String>();

    static {
        supportedLabelPrinters.put("Zebra.ZebraS600", "Zebra S600");
        supportedLabelPrinters.put("Zebra.Zebra2746e", "Zebra 2348 Plus");
        supportedLabelPrinters.put("Datamax.ProdigyMax","Datamax Prodigy Max");
        supportedLabelPrinters.put("Zebra.ZebraZ4Mplus","Zebra ZM400");
        
    }

    private static ConnectShipCommunications comm = new ConnectShipCommunications(ConnectShipCommunications.liveServerURL);

    public static LISTRESPONSE handle(LISTREQUEST listRequest, boolean testing, double api_version) throws Exception

    {

        LISTRESPONSE response = new LISTRESPONSE();

        response.setDATALIST(new DATALIST());
        response.getDATALIST().setLISTNAME(listRequest.getList_Name());

        Connection cxn = null;
        try {

           // authorize(listRequest.getLOGIN().getLOGINID(), listRequest.getLOGIN().getPASSWORD());
            if (listRequest.getList_Name().equals("LABELPRINTERTYPES")) {
                for (String code : (Set<String>) supportedLabelPrinters.keySet()) {
                    LISTVALUE currvalue = new LISTVALUE();
                    currvalue.setDISPLAYNAME((String) supportedLabelPrinters.get(code));
                    currvalue.setAPIVALUE(code);
                    response.getDATALIST().getLISTVALUE().add(currvalue);
                }
            } else if (listRequest.getList_Name().equals("LOCATIONS")) {
                cxn = ConnectionManager.getConnection();
                ResultSet rs = cxn.prepareStatement("select loc_name,loc_code from owd_facilities  (NOLOCK) where is_active = 1 order by loc_name asc").executeQuery();
                while (rs.next()) {
                    LISTVALUE currvalue = new LISTVALUE();
                    currvalue.setAPIVALUE(rs.getString(2));
                    currvalue.setDISPLAYNAME(rs.getString(1));
                    response.getDATALIST().getLISTVALUE().add(currvalue);
                }

            } else if (listRequest.getList_Name().equals("SHIPPERS")) {
                Map shipperMap = CSReferenceService.getShipperMap(comm);
                for (String code : (Set<String>) shipperMap.keySet()) {
                    LISTVALUE currvalue = new LISTVALUE();
                    currvalue.setAPIVALUE((String) shipperMap.get(code));
                    currvalue.setDISPLAYNAME(code);
                    response.getDATALIST().getLISTVALUE().add(currvalue);
                }

            } else if (listRequest.getList_Name().equals("LISTNAMES")) {


                LISTVALUE currvalue = new LISTVALUE();
                currvalue.setAPIVALUE("SHIPPERS");
                currvalue.setDISPLAYNAME("List of shipper codes to choose from. Shipper codes should be chosen based on shipment origin and/or client.");
                response.getDATALIST().getLISTVALUE().add(currvalue);
                LISTVALUE currvalue2 = new LISTVALUE();
                currvalue2.setAPIVALUE("LABELPRINTERTYPES");
                currvalue2.setDISPLAYNAME("List of label printer codes and types available for generating thermal labels. The chosen type must match the physical printer being used.");
                response.getDATALIST().getLISTVALUE().add(currvalue2);
                LISTVALUE currvalue3 = new LISTVALUE();
                currvalue3.setAPIVALUE("LOCATIONS");
                currvalue3.setDISPLAYNAME("List of location codes used to establish physical facility location.");
                response.getDATALIST().getLISTVALUE().add(currvalue3);


            } else {
                throw new ShipAPIContentException("Invalid list name " + listRequest.getList_Name() + " passed to OWDManifestService.getReferenceList. Send \"LISTNAMES\" as name of list to get information on available reference lists.");
            }

        } catch (Exception ex) {
         //   ex.printStackTrace();
            response.setDATALIST(new DATALIST());
            throw ex;
        } finally {
            ConnectionManager.freeConnection(cxn);
        }

        return response;


    }




}
