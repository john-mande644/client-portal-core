package com.owd.dc.manifest.api;

import com.owd.OWDShippingAPI.OWDDocRequest;
import com.owd.connectship.xml.api.OWDShippingResponse.DOCRESPONSE;
import com.owd.connectship.xml.api.OWDShippingResponse.INTLDOCDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.connectship.xml.api.OWDShippingRequest.DOCREQUEST;
import com.owd.core.TagUtilities;
import com.owd.dc.manifest.BluePackage.BluePackageApi;
import com.owd.dc.manifest.ExternalApis.OWDEasyPost.EasyShipmentUtils;
import com.owd.dc.manifest.ExternalApis.deliverr.DeliverrApi;
import com.owd.dc.manifest.OSMWorldwide.OSMUtils;
import com.owd.dc.manifest.ThirdPartyLabels;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ConnectShipShipment;
import com.owd.dc.manifest.api.internal.ConnectShipWebServices;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.dc.manifest.gssShipment;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderTrack;
import com.owd.hibernate.generated.OwdShipServiceFlatrateMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 7, 2008
 * Time: 8:52:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocRequest {
    protected final static Logger log = LogManager.getLogger();

    //  private static ConnectShipCommunications comm = new ConnectShipCommunications(ConnectShipCommunications.liveServerURL);

    public static DOCRESPONSE handle(DOCREQUEST docRequest, boolean testing, double api_version) throws Exception {
        //Check for OWDAPIShipment
        DOCRESPONSE response = new DOCRESPONSE();

        OWDDocRequest docs = new OWDDocRequest();
        List<LABELDATA> labels = docs.getAvailableLabelsForBarcodeXML(docRequest.getBARCODE());
        if(labels.size()>0){
            response.getLABELDATA().addAll(labels);
            return response;
        }


        boolean tandata = true;
        if (docRequest.getDOCTYPE() == null) throw new Exception("DOCTYPE value required");
        AMPConnectShipShipment css = new AMPConnectShipShipment();
        css.loadOrderFromOrderReference(docRequest.getBARCODE(), true,docRequest.getSHIPPER());

        if(css.getAssignedServiceCode().contains("BPD")){
            response.getLABELDATA().add(BluePackageApi.reprintLabelFromPackageBarcode(docRequest.getBARCODE()));
            return response;
        }

        Map<String, String> customValues = TagUtilities.getTagMap("ORDER", Integer.parseInt(css.getValueAsString("OWDORDERID")));
            // check it tage SHIPPING_LABEL exitst and equals DELIVERR_APO

        if(customValues.containsKey("SHIPPING_LABEL")) {
            log.debug("Found SHIPPING_LABEL tag");
            if (customValues.get("SHIPPING_LABEL").equals("DELIVERR_API")) {
                log.debug("SHIPPING_LABEL contained DELIVERR_API");
                response.getLABELDATA().add(ThirdPartyLabels.reprintLabelFromPackageBarcode(docRequest.getBARCODE()));
                return response;
            }
        }

        if(css.getAssignedServiceCode().contains("DLVR")){
            response.getLABELDATA().add(ThirdPartyLabels.reprintLabelFromPackageBarcode(docRequest.getBARCODE()));
            return response;
        }

        if(css.getAssignedServiceCode().contains("COM_OWD")){
           log.debug("FlatRate code, need to lookup actual method>");
            Criteria crit = HibernateSession.currentSession().createCriteria(OwdOrderTrack.class);
            crit.add(Restrictions.eq("orderFkey", Integer.parseInt(css.getValueAsString("OWDORDERID"))));
            crit.add(Restrictions.eq("isVoid",0));
          List<OwdOrderTrack> tracks = crit.list();

            if(tracks.size()>0){
                css.setAssignedServiceCode(tracks.get(0).getServiceCode());
            }

        }

        if(css.getValue(ShipConfig.EXTERNAL_PACKAGE).toString().length()>0){

            List<LABELDATA> lds = EasyShipmentUtils.reprintShipmentLable(css.getValue(ShipConfig.EXTERNAL_PACKAGE).toString(),css.getValue(ShipConfig.EXTERNAL_API_KEY).toString());
            for(LABELDATA lbls:lds){
                response.getLABELDATA().add(lbls);
            }
            return response;


        }
        if(css.getAssignedServiceCode().contains("OSM")){
            response.getLABELDATA().add(OSMUtils.reprintLabelFromPackageBarcode(docRequest.getBARCODE()));
            return response;
        }



        if (docRequest.getDOCTYPE().equals("LABEL") || docRequest.getDOCTYPE().equals("ALL")) {
           List<String> gssShipMethodList = new ArrayList<String>();
          gssShipMethodList.add("TANDATA_USPS.USPS.I_EXP_DMND");
        gssShipMethodList.add("TANDATA_USPS.USPS.I_FIRST");
        gssShipMethodList.add("TANDATA_USPS.USPS.I_PRIORITY");
            gssShipMethodList.add("TANDATA_USPS.USPS.EXPR");
            System.out.println(css.getAssignedServiceCode());

            if (gssShipMethodList.contains(css.getAssignedServiceCode())){
             System.out.println("Doing gss reprint");

             try{
              List<LABELDATA> lds = gssShipment.reprintLabelFromBarcode(docRequest.getBARCODE());
                 for(LABELDATA lbls:lds){
                     response.getLABELDATA().add(lbls);
                 }
                 System.out.println("Done with the labels, unsetting tandata variable");
               tandata = false;
             } catch(Exception e){
                  e.printStackTrace();
                 
             }

            }
            if (tandata){
                System.out.println("Doing tandata labels");
            List<byte[]> docList = ConnectShipWebServices.printThermalLabels(docRequest.getBARCODE(), docRequest.getLABELPRINTER(),css.isInternational());

            for (byte[] rawDoc : docList) {
                LABELDATA ld = new LABELDATA();
                ld.setCopies_Needed("1");
                ld.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(rawDoc));
                response.getLABELDATA().add(ld);

          }
            }
            if (css.isAPOFPO()&& !css.getAssignedServiceCode().contains("UPS.SP") && !css.getAssignedServiceCode().contains("CONNECTSHIP_DHLGLOBALMAIL.DHL")){
               response.getINTLDOCDATA().addAll(gssShipment.reprintAPOLabel(docRequest.getBARCODE()));
            }
        }

        if (docRequest.getDOCTYPE().equals("INTLDOC") || docRequest.getDOCTYPE().equals("ALL")||css.getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.IECO")||css.getAssignedServiceCode().equals("TANDATA_FEDEXFSMS.FEDEX.IPRI")) {

            List<byte[]> docList = ConnectShipWebServices.printIntlDocPDF(css);
            for (byte[] rawDoc : docList) {
                INTLDOCDATA idd = new INTLDOCDATA();
                if (css.getAssignedCarrierCode().indexOf("USPS") >= 0) {
                    idd.setCopies_Needed("4");
                } else {
                    idd.setCopies_Needed("3");
                }
                idd.setValue(org.apache.commons.codec.binary.Base64.encodeBase64String(rawDoc));
                response.getINTLDOCDATA().add(idd);
            }

        } 

        if(!docRequest.getDOCTYPE().equals("LABEL") && !docRequest.getDOCTYPE().equals("ALL"))
        {
            throw new Exception("DOCTYPE value not recognized - must be LABEL or INTLDOC");
        }


        return response;
    }
}
