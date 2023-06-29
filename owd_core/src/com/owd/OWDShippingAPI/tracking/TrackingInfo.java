package com.owd.OWDShippingAPI.tracking;


import com.owd.OWDShippingAPI.Models.shipServiceModel;
import com.owd.OWDShippingAPI.ShippingUtilities;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class TrackingInfo {
    private final static Logger log = LogManager.getLogger();

    public  void postTrackingInfo(TrackingBean trackInfo, String consolidatorCode,String country) throws Exception{

        try{
            ShippingUtilities shippingUtilities = new ShippingUtilities();
            shipServiceModel method = shippingUtilities.getOwdShipCodesFromProShipCodes(trackInfo.getServiceCode(),trackInfo.getWeight(),country);
            trackInfo.setServiceCode(method.getMethodCode());
            trackInfo.setCarrierCode(method.getCarrierCode());
        }catch (Exception e){
            //error is thrown to Logable Exception. Move on and store passed in value
        }
        log.debug("posting Tracking info");
        // String updatePackageSql = "update package set gss_shipment = 0, customs_docs = :customs, ship_time=getdate(),order_track_fkey=:trackid,owd_insurance_cost=:cost, insured_amount=:insured, external_id = :externalId where pack_barcode=:pack_barcode";
        //    String updatePackageSql = "execute sp_updatePackageShipAppExternal :customs, :trackid, :cost,:insured,:externalId,:pack_barcode";
        //String updatePackageSql = "update package set ship_time=getdate(),order_track_fkey=:trackid,owd_insurance_cost=:cost, insured_amount=:insured, label_string = :label, external_id=:externalId, dim_weight_lbs = :billedWeight where pack_barcode=:pack_barcode";
        String updatePackageSql = "execute updatePackageWithShippingData :trackid,:cost,:insured,:label,:externalId,:billedWeight,:pack_barcode,:customs";

        String updatePackageOrdersql = "update package_order set packs_shipped=(packs_shipped+1), consolidatorCode=:consolidator_code where owd_order_fkey=:orderfkey and is_void=0";
        String updateOrdersql   ="exec update_order_shipment_info :orderfkey";
        String  createTrackingRecord = "insert into owd_order_track (order_fkey, line_index,tracking_no,external_id, weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                + "msn,is_void,reported,email_sent,carrier_code,service_code,detailed_price,is_third_party,is_saturday,is_signature,is_ExpressPO) VALUES ( :order_fkey, :line_index, :tracking_no, :external_id,  :weight, :total_billed , 0 ,convert(datetime,convert(varchar,getdate(),101)),getdate(),:location,getdate(),:location,0,0, 0,0,:carrier_code,:service_code,:detailedPrice, :thirdParty, :saturday, :signature, :express)";


        Query q = HibernateSession.currentSession().createSQLQuery(createTrackingRecord);
        q.setParameter("order_fkey", trackInfo.getOrderFkey());
        q.setParameter("line_index",trackInfo.getLineIndex());
        q.setParameter("tracking_no",trackInfo.getTrackingNumber());
        q.setParameter("external_id",trackInfo.getExternalId());
        q.setParameter("weight",trackInfo.getWeight());
        q.setParameter("total_billed",String.valueOf(trackInfo.getTotalBilled()));
        q.setParameter("location",trackInfo.getLocation());
        q.setParameter("carrier_code",trackInfo.getCarrierCode());
        q.setParameter("service_code",trackInfo.getServiceCode());
        q.setParameter("detailedPrice",trackInfo.getDetailedPricing());
        q.setParameter("thirdParty",trackInfo.getThirdParty());
        q.setParameter("saturday",trackInfo.getIsSaturday());
        q.setParameter("signature",trackInfo.getIsSignature());
        q.setParameter("express",trackInfo.getIsExpressPO());


        log.debug("Creating Tracking");
        int results = q.executeUpdate();
        if(results<0) throw new Exception("Unable to create tracking for " + trackInfo.getTrackingNumber());


        //tracking has been updated.
///////////
        String sql = "select order_track_id from owd_order_track where order_fkey = :fkey and tracking_no = :track";
        Query  qq = HibernateSession.currentSession().createSQLQuery(sql);
        qq.setString("fkey", String.valueOf(trackInfo.getOrderFkey()));
        log.debug(trackInfo.getOrderFkey());
        qq.setString("track",trackInfo.getTrackingNumber());
        log.debug(trackInfo.getTrackingNumber());
        List l = qq.list();
        log.debug(l);
        log.debug("Got order track Id");
        String trackId = l.get(0).toString();
        log.debug("Tracking Id: "+ trackId);

        //grabbed tracking id

        //update package info

        Query packageUpdate = HibernateSession.currentSession().createSQLQuery(updatePackageSql);
        packageUpdate.setString("trackid",trackId);
        packageUpdate.setString("pack_barcode", trackInfo.getPackBarcode());
        packageUpdate.setParameter("billedWeight",trackInfo.getBilledWeight());

        //todo figure out insurance

        packageUpdate.setString("cost",trackInfo.getInsuranceCost());
        packageUpdate.setString("insured", trackInfo.getInsuredAmount());
        packageUpdate.setString("label",trackInfo.getLabel());

        packageUpdate.setParameter("externalId",trackInfo.getExternalId());
        packageUpdate.setParameter("customs",trackInfo.getCustoms());


        log.debug("Right before updateing package");
        results = packageUpdate.executeUpdate();
        log.debug("Done updateing pacakge with trackid");
        if (results < 1) throw new Exception("update for " + consolidatorCode + " returned 0");


        q = HibernateSession.currentSession().createSQLQuery(updatePackageOrdersql);
        q.setString("orderfkey",String.valueOf(trackInfo.getOrderFkey()));
        q.setString("consolidator_code", consolidatorCode);
        results = q.executeUpdate();
        log.debug("Done updating package order ");
        if (results < 1) throw new Exception("update for " + consolidatorCode + " Package Order returned 0");
        if(!isWeightVerifiedPacked(String.valueOf(trackInfo.getOrderFkey()))) {
            q = HibernateSession.currentSession().createSQLQuery(updateOrdersql);
            q.setString("orderfkey",String.valueOf(trackInfo.getOrderFkey()));
            results = q.executeUpdate();
            log.debug("Done updateting order sql");
            if (results < 1) throw new Exception("update Order returned 0");
        }
    }

    public  boolean isWeightVerifiedPacked(String orderId){
        boolean isWV = false;
        try{
            String sql = "SELECT\n" +
                    "    dbo.package_order.id\n" +
                    "FROM\n" +
                    "    dbo.owd_order_packs\n" +
                    "LEFT OUTER JOIN\n" +
                    "    dbo.package_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order_packs.order_fkey = dbo.package_order.owd_order_fkey)\n" +
                    "WHERE\n" +
                    "    dbo.owd_order_packs.order_fkey = :orderId\n" +
                    "AND dbo.package_order.packType > 2\n" +
                    "AND dbo.package_order.packType < 5 " +
                    "AND is_void = 0" +
                    ";";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId",orderId);
            if(q.list().size()>0){
                isWV = true;

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return isWV;

    }
}
