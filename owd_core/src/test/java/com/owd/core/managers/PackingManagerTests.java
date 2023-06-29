package com.owd.core.managers;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PackingManagerTests {

    @Test
    public  void  When_Ship_With_Packing_Manager_Than_Carrier_And_Service_Code_Should_Be_Filled_From_Ship_Info() throws Exception {

        Integer orderId = 20799108; //UPS GROUND
        unPickUnShipIfShipped(orderId);
        OwdShipMethod shipMethod = getOrderShipMethod(orderId);

        PackingManager.packAndShip(orderId, 212, 0.00, "64fffa");

        OwdOrderTrack actualOrderTrack = getOrderTrack(orderId);
        assertNotNull(actualOrderTrack);
        assertNotNull(shipMethod);
        assertEquals(shipMethod.getCarrierCode(), actualOrderTrack.getCarrierCode());
        assertEquals(shipMethod.getMethodCode(), actualOrderTrack.getServiceCode());
    }

    @Test
    public  void  When_Ship_With_Packing_Manager_MAP_Than_Carrier_And_Service_Code_Should_Be_Filled_From_Ship_Info() throws Exception {

        Integer orderId = 20799108; //UPS GROUND
        unPickUnShipIfShipped(orderId);
        OwdShipMethod shipMethod = getOrderShipMethod(orderId);

        List<PackingManager.packOutInfo> pois = new ArrayList<>();
        pois.add(new PackingManager.packOutInfo(2,0.00,"test10",1, "000000000000000"));

        PackingManager.packAndShipMap(orderId, pois, true);

        OwdOrderTrack actualOrderTrack = getOrderTrack(orderId);
        assertNotNull(actualOrderTrack);
        assertNotNull(shipMethod);
        assertEquals(shipMethod.getCarrierCode(), actualOrderTrack.getCarrierCode());
        assertEquals(shipMethod.getMethodCode(), actualOrderTrack.getServiceCode());
    }


    @Test
    public  void  When_Ship_With_Packing_Manager_And_Economy_Carrier_Shipment_Should_Be_Successed() throws Exception {

        Integer orderId = 20799115;//Economy
        unPickUnShipIfShipped(orderId);
        OwdShipMethod shipMethod = getOrderShipMethod(orderId);

        PackingManager.packAndShip(orderId, 212, 0.00, "64fffa");

        OwdOrderTrack actualOrderTrack = getOrderTrack(orderId);
        assertNotNull(actualOrderTrack);
        assertNotNull(shipMethod);
        assertEquals(shipMethod.getCarrierCode(), actualOrderTrack.getCarrierCode());
        assertEquals(shipMethod.getMethodCode(), actualOrderTrack.getServiceCode());
    }

    private OwdShipMethod getOrderShipMethod(Integer orderId) throws  Exception  {
        String shipMethodName = getShipMethodName(orderId);
        Criteria crit = HibernateSession.currentSession().createCriteria(OwdShipMethod.class);
        crit.add(Restrictions.eq("methodName", shipMethodName));
        List<OwdShipMethod> owdShipMethods = crit.list();

        if(owdShipMethods.size() == 1) {
            return owdShipMethods.get(0);
        }

        return  null;
    }

    private static String getShipMethodName(Integer orderId) throws  Exception {
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderId);
        OwdOrderShipInfo shipInfo = order.getShipinfo();

        String result = shipInfo.getCarrService();

        if (result.equals("Economy")) {
            return  "Ecomony";
        }

        return  result;
    }

    private OwdOrderTrack getOrderTrack(Integer orderId) throws Exception {
        Criteria crit = HibernateSession.currentSession().createCriteria(OwdOrderTrack.class);
        crit.add(Restrictions.eq("orderFkey", orderId));
        crit.add(Restrictions.eq("isVoid",0));
        List<OwdOrderTrack> owdOrderTracks = crit.list();

        if(owdOrderTracks.size() == 1) {
            return owdOrderTracks.get(0);
        }

        return  null;
    }

    private void unPickUnShipIfShipped(Integer orderId) throws Exception {
        PreparedStatement stmt = HibernateSession.getPreparedStatement(
                "\tDECLARE @order_id int = ? ;\n" +
                "\n" +
                "\tdelete package\n" +
                "\tfrom owd_order_track\n" +
                "\tjoin package\n" +
                "\t\ton package.order_track_fkey = owd_order_track.order_track_id\n" +
                "\twhere owd_order_track.order_fkey = @order_id;\n" +
                "\n" +
                "\tdelete owd_order_track\n" +
                "\tfrom owd_order_track\n" +
                "\twhere owd_order_track.order_fkey = @order_id;");

        stmt.setInt(1, orderId);
        stmt.execute();
        HibUtils.commit(HibernateSession.currentSession());
        HibernateSession.closePreparedStatement();
        HibernateSession.currentSession().flush();
        HibUtils.commit(HibernateSession.currentSession());
    }
}
