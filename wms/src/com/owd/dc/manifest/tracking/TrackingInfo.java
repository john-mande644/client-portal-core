package com.owd.dc.manifest.tracking;

import com.owd.dc.warehouse.ABShipments.ABUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class TrackingInfo {

    public static void postTrackingInfo(TrackingBean trackInfo, String consolidatorCode) throws Exception{

        // String updatePackageSql = "update package set gss_shipment = 0, customs_docs = :customs, ship_time=getdate(),order_track_fkey=:trackid,owd_insurance_cost=:cost, insured_amount=:insured, external_id = :externalId where pack_barcode=:pack_barcode";
        //    String updatePackageSql = "execute sp_updatePackageShipAppExternal :customs, :trackid, :cost,:insured,:externalId,:pack_barcode";
        String updatePackageSql = "update package set ship_time=getdate(),order_track_fkey=:trackid,owd_insurance_cost=:cost, insured_amount=:insured, label_string = :label, external_id=:externalId where pack_barcode=:pack_barcode";

        String updatePackageOrdersql = "update package_order set packs_shipped=(packs_shipped+1), consolidatorCode=:consolidator_code where owd_order_fkey=:orderfkey and is_void=0";
        String updateOrdersql   ="exec update_order_shipment_info :orderfkey";
        String  createTrackingRecord = "insert into owd_order_track (order_fkey, line_index,tracking_no,external_id, weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                + "msn,is_void,reported,email_sent,carrier_code,service_code) VALUES ( :order_fkey, :line_index, :tracking_no, :external_id,  :weight, :total_billed , 0 ,convert(datetime,convert(varchar,getdate(),101)),getdate(),:location,getdate(),:location,0,0, 0,0,:carrier_code,:service_code)";

        Session sess = HibernateSession.currentSession();
        Query q = sess.createSQLQuery(createTrackingRecord);
        q.setParameter("order_fkey", trackInfo.getOrderFkey());
        q.setParameter("line_index",trackInfo.getLineIndex());
        q.setParameter("tracking_no",trackInfo.getTrackingNumber());
        q.setParameter("external_id",trackInfo.getExtrnalId());
        q.setParameter("weight",trackInfo.getWeight());
        q.setParameter("total_billed",String.valueOf(trackInfo.getTotalBilled()));
        q.setParameter("location",trackInfo.getLocation());
        q.setParameter("carrier_code",trackInfo.getCarrierCode());
        q.setParameter("service_code",trackInfo.getServiceCode());

        System.out.println("Creating Tracking");
        int results = q.executeUpdate();
        if(results<0) throw new Exception("Unable to create tracking for " + trackInfo.getTrackingNumber());


        //tracking has been updated.
///////////
        String sql = "select order_track_id from owd_order_track where order_fkey = :fkey and tracking_no = :track";
        Query  qq = sess.createSQLQuery(sql);
        qq.setString("fkey", String.valueOf(trackInfo.getOrderFkey()));
        System.out.println(trackInfo.getOrderFkey());
        qq.setString("track",trackInfo.getTrackingNumber());
        System.out.println(trackInfo.getTrackingNumber());
        List l = qq.list();
        System.out.println(l);
        System.out.println("Got order track Id");
        String trackId = l.get(0).toString();
        System.out.println("Tracking Id: "+ trackId);

        //grabbed tracking id

        //update package info

        q = sess.createSQLQuery(updatePackageSql);
        q.setString("trackid",trackId);
        q.setString("pack_barcode", trackInfo.getPackBarcode());

        //todo figure out insurance
        q.setString("cost","0");
        q.setString("insured", "0");
        q.setString("label",trackInfo.getLabel());

        q.setParameter("externalId",trackInfo.getExtrnalId());


        System.out.println("Right before updateing package");
        results = q.executeUpdate();
        System.out.println("Done updateing pacakge with trackid");
        if (results < 1) throw new Exception("update for " + consolidatorCode + " returned 0");


        q = sess.createSQLQuery(updatePackageOrdersql);
        q.setString("orderfkey",String.valueOf(trackInfo.getOrderFkey()));
        q.setString("consolidator_code", consolidatorCode);
        results = q.executeUpdate();
        System.out.println("Done updating package order ");
        if (results < 1) throw new Exception("update for " + consolidatorCode + " Package Order returned 0");
        if(!ABUtils.isWeightVerifiedPacked(String.valueOf(trackInfo.getOrderFkey()))) {
            q = sess.createSQLQuery(updateOrdersql);
            q.setString("orderfkey",String.valueOf(trackInfo.getOrderFkey()));
            results = q.executeUpdate();
            System.out.println("Done updateting order sql");
            if (results < 1) throw new Exception("update Order returned 0");
        }
    }
}
