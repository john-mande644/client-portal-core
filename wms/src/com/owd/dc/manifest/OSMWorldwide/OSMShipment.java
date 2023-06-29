package com.owd.dc.manifest.OSMWorldwide;

import com.owd.connectship.xml.api.OWDShippingResponse.SHIPMENT;
import com.owd.core.business.order.Event;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.ConnectionManager;
import com.owd.dc.manifest.api.internal.AMPConnectShipShipment;
import com.owd.dc.manifest.api.internal.ShipConfig;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 11/23/14
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class OSMShipment {
    OSMOrderInfo orderInfo = new OSMOrderInfo();
    List<OSMPackage> packages = new ArrayList<OSMPackage>();

    protected final static Logger log = LogManager.getLogger();


    public OSMShipment(List<AMPConnectShipShipment> packlist, OwdClient client, String facility) throws Exception {

        //check for Photojojo only. OWD acceptable for testing purposes.
        if (client.getClientId() == 607 || client.getClientId() == 55 || client.getClientId() == 488) {

            //load address info from first package.
            //load package info for the rest
            int index = 1;
            for (AMPConnectShipShipment ship : packlist) {
                if (packages.size() == 0) {
                    orderInfo = OSMUtils.loadOrderInfo(ship);
                }
                packages.add(OSMUtils.loadPackageInfo(ship, index));
                updateShipMethod(ship);
                index++;
            }
            //info loaded Calculate tracking numbers as needed
            for (OSMPackage pkg : packages) {
                OSMUtils.calculateTracking(pkg, orderInfo, OSMUtils.lookupOSMMailerIdByClientId(client.getClientId()));
            }


        } else {
            throw new Exception(client.getCompanyName() + " is not allowed to ship with OSM. Please change shipmethod. ");
        }


    }

    private void updateShipMethod(AMPConnectShipShipment ship) {
        //NEW code
        try {
            Connection cxn = ConnectionManager.getConnection();
            PreparedStatement stmt = null;
            String shipMethod = ship.getAssignedCarrierCode();
            if (!(shipMethod.equals("OWD.OSM.DEM")) || !(shipMethod.equals("OWD.OSM.INTL"))) {
                String shipMethodName = ship.getAssignedCarrierCode();
                String originalShipMethodName = ship.getOriginalAssignedServiceCode();

                Event.addOrderEvent(cxn, new Integer(ship.getValueAsString(ShipConfig.OWD_ORDER_ID)), Event.kEventTypeHandling, "Manifesting auto-switched ship method from " + originalShipMethodName + " to " + shipMethodName, "Manifesting Server");

                log.debug("Added Event Auto Switch " + ship.getValueAsString("PACKAGE_BARCODE"));
                String newShipName = null;
               /* try {
                    newShipName = (String) OrderRater.getRateableServicesMap().get(shipMethod);
                } catch (Exception exsn) {
                    exsn.printStackTrace();
                }*/
               // if (newShipName != null) {
                    stmt = cxn.prepareStatement("Update owd_order_ship_info set carr_service=?, carr_service_ref_num=?" +
                            " where order_fkey=?");

                    stmt.setString(1, "OSM Domestic");
                    stmt.setString(2, "OWD.OSM.DOM");
                    stmt.setInt(3, new Integer(new Integer(ship.getValueAsString(ShipConfig.OWD_ORDER_ID))));
                    stmt.executeUpdate();
                //}

            }
            cxn.commit();
            cxn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //END NEW CODE

    }

    public List<SHIPMENT> shipOSMPackages() throws Exception {
        List<SHIPMENT> shippedPackages = new ArrayList<SHIPMENT>();
        //post tracking

        postTracking();
        //getLabels
        for (OSMPackage pkg : packages) {
            System.out.println("Getting labels");
            shippedPackages.add(OSMUtils.getLabelData(pkg, orderInfo));
        }
        return shippedPackages;
    }

    public void postTracking() throws Exception {

        for (OSMPackage pkg : packages) {
            postTrackingINFO(pkg, HibernateSession.currentSession(), orderInfo);
        }


    }

    private void postTrackingINFO(OSMPackage pkg, Session sess, OSMOrderInfo info) throws Exception {

        String updatePackageSql = "update package set ship_time=getdate(),order_track_fkey=:trackid,owd_insurance_cost=:cost, insured_amount=:insured, label_string = :label where id=:id";
        String updatePackageOrdersql = "update package_order set packs_shipped=(packs_shipped+1), consolidatorCode = 'OSM' where owd_order_fkey=:orderfkey and is_void=0";
        String updateOrdersql = "exec update_order_shipment_info :orderfkey";
        String createTrackingRecord = "insert into owd_order_track (order_fkey, line_index,tracking_no,weight,total_billed,cost_of_goods,ship_date,created_date,created_by,modified_date,modified_by,"
                + "msn,is_void,reported,email_sent) VALUES ( :order_fkey, :line_index, :tracking_no, :weight, :total_billed , 0 ,convert(datetime,convert(varchar,getdate(),101)),getdate(),:location,getdate(),:location,0,0, 0,0)";

        try {
            Query q = sess.createSQLQuery(createTrackingRecord);
            q.setString("order_fkey", info.getOrderId());

            q.setInteger("line_index", pkg.getPackageIndex());
            if (pkg.isPostTracking()) {
                q.setString("tracking_no", pkg.getTrackingNumber());
            } else {
                q.setString("tracking_no", "");
            }

            q.setDouble("weight", pkg.getPackageWeight());
            boolean doUSPSRateStuff = true;

            q.setDouble("total_billed", pkg.getShipCost());
            q.setString("location", orderInfo.getFacilityCode());

            int results = q.executeUpdate();
            System.out.println(results);

            System.out.println("Done creating TRacking record");

            if (results < 1) throw new Exception("Insert returned 0");

            String sql = "select order_track_id from owd_order_track where order_fkey = :fkey and tracking_no = :track";
            Query qq = sess.createSQLQuery(sql);
            qq.setString("fkey", orderInfo.getOrderId());
            if (pkg.isPostTracking()) {
                qq.setString("track", pkg.getTrackingNumber());
            } else {
                qq.setString("track", "");
            }

            List l = qq.list();
            System.out.println(l);
            System.out.println("Got order track Id");
            String trackId = l.get(0).toString();
            System.out.println("hhhhhhhhh");
            q = sess.createSQLQuery(updatePackageSql);
            q.setString("trackid", trackId);
            q.setString("id", pkg.getPackageId());
            q.setDouble("cost", pkg.getShipCost());
            q.setString("insured", "0");
            q.setString("label", OSMUtils.getLabelData(pkg, orderInfo).getLABELDATA().get(0).getValue());
            System.out.println("Right before updateing package");
            results = q.executeUpdate();
            System.out.println("Done updateing pacakge with trackid");
            if (results < 1) throw new Exception("update for Package returned 0");
            q = sess.createSQLQuery(updatePackageOrdersql);
            q.setString("orderfkey", orderInfo.getOrderId());
            results = q.executeUpdate();
            System.out.println("Done updateing package order ");
            if (results < 1) throw new Exception("update for Package ORder returned 0");
            q = sess.createSQLQuery(updateOrdersql);
            q.setString("orderfkey", orderInfo.getOrderId());
            results = q.executeUpdate();
            System.out.println("Done updateting order sql");
            if (results < 1) throw new Exception("update Order returned 0");


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to post tracking info: " + e.getMessage());
        }

    }
}
