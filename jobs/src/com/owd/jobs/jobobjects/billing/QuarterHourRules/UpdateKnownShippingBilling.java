package com.owd.jobs.jobobjects.billing.QuarterHourRules;

import com.owd.LogableException;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 11/22/2019.
 */
public class UpdateKnownShippingBilling extends OWDStatefulJob {



    public static void main(String[] args){
        run();

    }

    public void internalExecute(){
            processUSPSpricing();
           // processDHLpricing();
        processUSPSOtherpricing();

        processPassportPricing();

        fixUSPSThridParty();





    }
    private void fixUSPSThridParty(){
        String sql = "update owd_order_ship_info set carr_freight_terms = 'Prepaid', carr_freight_terms_ref_num = 'SHIPPER' where order_fkey in (\n" +
                "\n" +
                "\n" +
                "SELECT \n" +
                "    dbo.owd_order_ship_info.order_fkey \n" +
                "FROM \n" +
                "    dbo.owd_order_track \n" +
                "INNER JOIN \n" +
                "    dbo.owd_order_ship_info \n" +
                "ON \n" +
                "    ( \n" +
                "        dbo.owd_order_track.order_fkey = dbo.owd_order_ship_info.order_fkey) \n" +
                "WHERE \n" +
                "    dbo.owd_order_track.carrier_code = 'TANDATA_USPS.USPS' \n" +
                "AND dbo.owd_order_ship_info.carr_freight_terms = 'Third Party Billing' \n" +
                "AND dbo.owd_order_ship_info.carr_freight_terms_ref_num = '.' \n" +
                "AND dbo.owd_order_ship_info.carr_service_ref_num NOT LIKE '%FLATRATE%'\n" +
                "and owd_order_track.ship_date > '2021-01-25'\n" +
                ");";

        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void processDHLpricing(){
        List<String> methods = new ArrayList<>();
        methods.add("DHL Express Worldwide nondoc");
        methods.add("DHL GlobalMail Packet Priority");
        methods.add("DHL GlobalMail Packet Standard");
        methods.add("DHL Parcel International Standard");
        methods.add("DHL SmartMail Parcel Expedited");
        methods.add("DHL SmartMail Parcel Expedited Max");
        methods.add("DHL SmartMail Parcel Ground");
        methods.add("DHL SmartMail Parcel Plus Expedited");
        methods.add("DHL SmartMail Parcel Plus Expedited Max");
        methods.add("DHL SmartMail Parcel Plus Ground");


        for(String method : methods) {
            System.out.println("Doing: " + method);
            String updateQuery = "update owd_order_track set bundle_id = 1, total_billed = total_billed * (1+ dbo.udf_getApiRate(:clientFkey,'DHL_ADD_PERCENT',:facility,:group)) \n" +
                    "where order_track_id = :trackId";


            String sqlQuery =
                    "SELECT\n" +
                    "    dbo.owd_order_track.order_track_id, owd_order.client_fkey, facility_code, group_name\n" +
                    "   \n" +
                    "FROM\n" +
                    "    dbo.owd_order\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order_ship_info\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey)\n" +
                    "INNER JOIN\n" +
                    "    dbo.package_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order.order_id = dbo.package_order.owd_order_fkey)\n" +
                    "INNER JOIN\n" +
                    "    dbo.package\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order_track\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package.order_track_fkey = dbo.owd_order_track.order_track_id)\n" +
                    "WHERE\n" +
                    "    dbo.package_order.is_void = 0\n" +
                    "AND dbo.owd_order_track.is_void = 0\n" +
                    "AND dbo.owd_order_track.bundle_id IS NULL\n" +
                    "AND dbo.owd_order_ship_info.carr_service = :method\n" +
                    "AND dbo.package_order.consolidatorCode = 'OWDShipAPI' ;";
            try {
                Query q = HibernateSession.currentSession().createSQLQuery(sqlQuery);
                q.setParameter("method", method);
                List l = q.list();
                System.out.println("Doing "+l.size()+" for "+method);

                for(Object row : l){
                    Object[] data = (Object[]) row;
                    Query qq = HibernateSession.currentSession().createSQLQuery(updateQuery);
                    qq.setParameter("trackId",data[0].toString());
                    qq.setParameter("clientFkey",data[1].toString());
                    qq.setParameter("facility",data[2].toString());
                    qq.setParameter("group",data[3].toString());
                    qq.executeUpdate();
                }
                HibUtils.commit(HibernateSession.currentSession());
            } catch (Exception e) {
                e.printStackTrace();
                LogableException ee = new LogableException(e, "Error updateing billing for DHL", method, "55", "Billing", LogableException.errorTypes.INTERNAL);
            }
        }
    }

    private void processUSPSpricing(){
        List<String> methods = new ArrayList<>();
        methods.add("USPS Priority Mail");
        methods.add("USPS Priority Mail Express");


        for(String method : methods) {
            System.out.println("Doing: " + method);
            String sqlQuery = "execute processUSPSBilling :method";
            try {
                Query q = HibernateSession.currentSession().createSQLQuery(sqlQuery);
                q.setParameter("method", method);
                int i = q.executeUpdate();
                System.out.println(i + " Record updated for billing for : " + method);
                HibUtils.commit(HibernateSession.currentSession());
            } catch (Exception e) {
                e.printStackTrace();
                LogableException ee = new LogableException(e, "Error updateing billing for USPS", method, "55", "Billing", LogableException.errorTypes.INTERNAL);
            }
        }
        }
    private void processUSPSOtherpricing(){
        List<String> methods = new ArrayList<>();
        methods.add("USPS First-Class Mail");
        methods.add("USPS Parcel Select Nonpresort");
        methods.add("USPS Media Mail Single-Piece");



        for(String method : methods) {
            System.out.println("Doing: " + method);
            String sqlQuery = "execute processUSPSOtherBilling :method";
            try {
                Query q = HibernateSession.currentSession().createSQLQuery(sqlQuery);
                q.setParameter("method", method);
                int i = q.executeUpdate();
                System.out.println(i + " Record updated for billing for : " + method);
                HibUtils.commit(HibernateSession.currentSession());
            } catch (Exception e) {
                e.printStackTrace();
                LogableException ee = new LogableException(e, "Error updateing billing for USPS", method, "55", "Billing", LogableException.errorTypes.INTERNAL);
            }
        }
    }

    private void  processPassportPricing()
    {
        List<String> methods = new ArrayList<>();
        methods.add("Passport Priority DDP");
        methods.add("Passport Priority DDU");

        for(String method : methods) {
            System.out.println("Doing: " + method);
            String sqlQuery = "execute processPassportBilling :method";
            try {
                Query q = HibernateSession.currentSession().createSQLQuery(sqlQuery);
                q.setParameter("method", method);
                int i = q.executeUpdate();
                System.out.println(i + " Record updated for billing for : " + method);
                HibUtils.commit(HibernateSession.currentSession());
            } catch (Exception e) {
                e.printStackTrace();
                LogableException ee = new LogableException(e, "Error updateing billing for PASSPORT", method, "55", "Billing", LogableException.errorTypes.INTERNAL);
            }
        }
    }
}
