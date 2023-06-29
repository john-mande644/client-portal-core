package com.owd.dc.manifest.api;

import com.owd.OWDShippingAPI.OWDVoidRequest;
import com.owd.connectship.xml.api.OWDShippingResponse.VOIDRESPONSE;
import com.owd.connectship.xml.api.OWDShippingResponse.VOIDRESULT;
import com.owd.connectship.xml.api.OWDShippingRequest.VOIDREQUEST;
import com.owd.connectship.services.ConnectShipCommunications;
import com.owd.connectship.services.CSVoidShipmentService;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.ManifestingManager;
import com.owd.dc.manifest.BluePackage.BluePackageApi;
import com.owd.dc.manifest.ExternalApis.OWDEasyPost.EasyShipmentUtils;
import com.owd.dc.manifest.ExternalApis.deliverr.DeliverrApi;
import com.owd.dc.manifest.gssVoidShipment;
import com.owd.dc.warehouse.ABShipments.ABUtils;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.dc.manifest.api.internal.ConnectShipWebServices;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 7, 2008
 * Time: 8:52:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class
VoidRequest
{
    protected final static Logger log = LogManager.getLogger();

        public static VOIDRESPONSE handle(VOIDREQUEST voidRequest, boolean testing, double api_version) throws Exception

        {
            OWDVoidRequest owdvr = new OWDVoidRequest();
            try{
                List<VOIDRESULT>  results = owdvr.voidBarcodeReturnXML(voidRequest.getBARCODE());
                if(results.size()>0){
                    VOIDRESPONSE response = new VOIDRESPONSE();
                    response.getVOIDRESULT().addAll(results);
                    return response;
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }


            PreparedStatement ps;
            Boolean isShipping = OrderUtilities.isShippingFromPackageBarcode(voidRequest.getBARCODE().get(0));
            if(isShipping){
                ps = HibernateSession.getPreparedStatement("select ISNULL(t.carrier_code,''),msn,gss_shipment,ISNULL(p.external_id,''),owd_order_fkey, po.consolidatorCode  from package_order po  (NOLOCK) join owd_order_ship_info s  (NOLOCK) on s.order_fkey=po.owd_order_fkey\n" +
                        "join package p   (NOLOCK) join owd_order_track t  (NOLOCK) on t.order_track_id=order_track_fkey on p.package_order_fkey=po.id where pack_barcode=? \n" +
                        "and po.is_void=0 and t.is_void=0");
            }else{
                ps = HibernateSession.getPreparedStatement("select ISNULL(t.carrier_code,''),msn,gss_shipment,ISNULL(p.external_id,''),owd_order_fkey, po.consolidatorCode from package_order po  (NOLOCK) join owd_order_ship_info s  (NOLOCK) on s.order_fkey=po.owd_order_fkey\n" +
                   "join package p   (NOLOCK) join owd_order_track t  (NOLOCK) on t.order_track_id=order_track_fkey on p.package_order_fkey=po.id where pack_barcode=? and abs(datediff(day,t.ship_date,getdate()))=0\n" +
                   "and po.is_void=0 and t.is_void=0");
            }



             VOIDRESPONSE response = new VOIDRESPONSE();

            voidRequest.setSHIPPER(ManifestingManager.getLiveShipperForLocation(voidRequest.getLOCATIONCODE()));

            log.debug(voidRequest.getSHIPPER());

            for (String barcode: voidRequest.getBARCODE())
            {
            VOIDRESULT vr = new  VOIDRESULT();
                vr.setBARCODE(barcode);
                vr.setResults("SUCCESS");
               response.getVOIDRESULT().add(vr);
           try
           {



            ps.setString(1,barcode);

            ResultSet rs = ps.executeQuery();
            List<String> msnList = new ArrayList<String>();

            if(rs.next())
            {



                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));

              if(rs.getString(3).equals("1")){
                  Session sess = HibernateSession.currentSession();
                  gssVoidShipment gssvoid = new gssVoidShipment();
                  gssvoid.voidPostedShipment(barcode,sess);
                       HibUtils.commit(sess);

              }else if(rs.getString(4).length()>0)
              {
                  System.out.println(rs.getString(1));
                  if(rs.getString(6).contains("DLVR")) {
                      DeliverrApi.cancelShipment(rs.getInt(5), rs.getString(4), barcode);
                  }else{
                      EasyShipmentUtils.voidOrderShipmentsByPackBarcode(barcode);
                  }

              }else
              {
               msnList.add(rs.getString(2));


                vr.setMSN(rs.getString(2));
              try {
                  log.debug(rs.getString(2));
              CSVoidShipmentService.voidShipment(ShipConfig.csComm, voidRequest.getSHIPPER(), rs.getString(1), msnList);
              }catch(Exception e){
                  if(!(isShipping & e.getMessage().contains("The specified package was not found"))){
                      throw new Exception(e.getMessage());
                  }
              }
              ConnectShipWebServices.postVoidShipmentForShippedPackage(barcode,rs.getString(2));
              }

            }else
            {
                throw new Exception("This barcode has no qualifying shipments available to void");
            }

               //success new to clear potential preshipweight.
               System.out.println("before clearing weight.");
               String sql = "update owd_order set pre_ship_weight = 0 where order_id = :orderId";
               Query q = HibernateSession.currentSession().createSQLQuery(sql);
               q.setParameter("orderId",rs.getString(5));
               q.executeUpdate();
               HibUtils.commit(HibernateSession.currentSession());

           }catch(Exception exx)
           {
               exx.printStackTrace();

                vr.setResults("ERROR");
               vr.setError_Response(exx.getMessage());
           }
            }

            return response;
        }

      public  static void main(String[] args){

         VOIDREQUEST v = new VOIDREQUEST();
         List<String> l = new ArrayList();

            v.getBARCODE().add("p12859863*26536296*b1");
            v.setSHIPPER("OWD");
            v.setLOCATIONCODE("DC6");
          try{
               handle(v,false,1.0);

          }catch (Exception e){
             e.printStackTrace();

          }
//          List l = new ArrayList();
         // l.add("17282734");
        //  l.add("17282735");
        //  l.add("17282736");
        //  l.add("17282737");
        //  l.add("17282738");
        //  l.add("17282740");
        //  l.add("17282741");
         // l.add("17283083");
        //  l.add("17283085");
        //  l.add("17283102");
       /*   l.add("17283427");
          l.add("17283500");
          l.add("17283936");
          l.add("17283956");
          l.add("17283978");
          l.add("17283983");
          l.add("17284073");*/

         // l.add("17400345");





//          try {
//             // CSVoidShipmentService.voidShipment(ShipConfig.csComm, "OWD_PROD_DC6", "TANDATA_FEDEXFSMS.FEDEX", l);
//
//              VOIDREQUEST vr = new VOIDREQUEST();
//              vr.setSHIPPER("DC6");
//              vr.setLOCATIONCODE("DC6");
//              vr.getBARCODE().add("p12742103*26298082*b1");
//
//              handle(vr,false,1.0);
//
//          }catch (Exception e){
//              e.printStackTrace();
//          }








      }
}
