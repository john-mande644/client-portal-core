package com.owd.jobs.jobobjects.billing.AllInclusive;

import com.owd.LogableException;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by danny on 12/5/2017.
 */
public class AllInclusiveUtilities {
    private final static Logger log = LogManager.getLogger();

    private static final String XServices = "service";
    private static final String XShipping = "shipping";
    private static final String Xboxes = "boxes";
    private static final String XReceiving = "receiving";
    private static final String XWarehousing = "warehousing";
    private static final String XReturns = "returns";
    private static final BigDecimal XHandPackMultiplyer = new BigDecimal(1.0);

    private static final List<String> internationalMethods = new ArrayList<>(Arrays.asList("FedEx International Economy", "FedEx International Priority", "UPS Expedited", "UPS Express", "UPS Express Plus", "UPS Express Saver", "UPS Standard Canada", "USPS First-Class Mail International", "USPS Priority Mail Express International", "USPS Priority Mail International", "DHL Express Worldwide nondoc", "DHL Express Worldwide (nondoc)"));
    private static final List<String> secondDayMethods = new ArrayList<>(Arrays.asList("FedEx 2Day", "UPS 2nd Day Air", "UPS 2nd Day Air A.M.", "UPS 3 Day Select"));
    private static final List<String> nextDayMethods = new ArrayList<>(Arrays.asList("FedEx Priority Overnight", "FedEx Standard Overnight", "UPS Next Day Air", "UPS Next Day Air A.M.", "UPS Next Day Air Saver", "USPS Priority Mail Express"));
    private static final List<String> threeDayMethods = new ArrayList<>(Arrays.asList("USPS Priority Mail"));

    public static void main(String[] args) {

        try {

            // log.debug(updateBillingAndShippingRecords("13975620", "6427814", "OSM Domestic", "2", "607", "2017-12-01"));
          //  Map<String,String> prices = loadPrice("DHL Express Worldwide nondoc","44","607","14747260");

           // System.out.println(prices);

          /*  Map<String,String> prices = lookupPricesForEquivilentWeightAndService("USPS Priority Mail","2","607");
                    System.out.println(prices);
            prices = lookupPricesForWeightAndService("USPS Priority Mail","2","607",0);
            System.out.println(prices);
            prices = lookupPricesForWeightAndService("USPS Priority Mail","2","607",1);
            System.out.println(prices);*/



              // updateAllInclusivePricingForDate("2017-12-19");
            checkAndFixShippingRecords();
          //  System.out.println(loadMultiPiecePrices("14025360"));
          //    updateAllInclusivePricingForDate("2018-03-01");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean updateAllInclusivePricingForDate(String recordedDate) throws Exception {
        boolean success = false;
        String sql = "SELECT\n" +
                "\n" +
                "                    dbo.sp_bill_pickpack.oid,\n" +
                "                    dbo.sp_bill_pickpack.id,\n" +
                "                    dbo.owd_order_ship_info.carr_service,\n" +
                "                   ceiling(max(dbo.package.weight_lbs)) as weight,\n" +
                "                    dbo.sp_bill_pickpack.[Client ID],\n" +
                "                    dbo.owd_boxtypes.allInclusiveValid,\n" +
                "                   max(dbo.owd_boxtypes.cost) as cost,\n" +
                "                    num_packs, isnull(convert(varchar,owd_tags.value),0) as clientPicked\n" +
                "                FROM\n" +
                "                    dbo.sp_bill_pickpack\n" +
                "                INNER JOIN\n" +
                "                    dbo.owd_client\n" +
                "                ON\n" +
                "                    (\n" +
                "                        dbo.sp_bill_pickpack.[Client ID] = dbo.owd_client.client_id)\n" +
                "                INNER JOIN\n" +
                "                    dbo.package_order\n" +
                "                ON\n" +
                "                    (\n" +
                "                        dbo.sp_bill_pickpack.oid = dbo.package_order.owd_order_fkey)\n" +
                "                INNER JOIN\n" +
                "                    dbo.package\n" +
                "                ON\n" +
                "                    (\n" +
                "                        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                "                INNER JOIN\n" +
                "                    dbo.owd_boxtypes\n" +
                "                ON\n" +
                "                    (\n" +
                "                        dbo.package.owd_boxtypes_fkey = dbo.owd_boxtypes.id)\n" +
                "                INNER JOIN\n" +
                "                    dbo.owd_order_ship_info\n" +
                "                ON\n" +
                "                    (\n" +
                "                        dbo.sp_bill_pickpack.oid = dbo.owd_order_ship_info.order_fkey)\n" +
                "                        Left outer join \n" +
                "                        owd_tags\n" +
                "                        on(\n" +
                "                        sp_bill_pickpack.oid = owd_tags.external_id and owd_tags.name = 'COM.OWD.SHIPPING.ALLINCLUSIVE'\n" +
                "                        )\n" +
                "                WHERE\n" +
                "                    dbo.owd_client.AllInclusive = 1\n" +
                "                AND dbo.sp_bill_pickpack.shipped_on >= dbo.owd_client.AllInclusive_begin_date\n" +
                "                AND dbo.package_order.is_void = 0\n" +
                "                \n" +
                "                AND dbo.sp_bill_pickpack.shipped_on = :recordedDate \n" +
                "                group by \n" +
                "                 dbo.sp_bill_pickpack.oid,\n" +
                "                    dbo.sp_bill_pickpack.id,\n" +
                "                    dbo.owd_order_ship_info.carr_service,\n" +
                "                     dbo.sp_bill_pickpack.[Client ID],\n" +
                "                    dbo.owd_boxtypes.allInclusiveValid,\n" +
                "                    num_packs, owd_tags.value";

        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("recordedDate", recordedDate);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);


        List results = q.list();
        log.debug(results.size());

        for (Object row : results) {
            Map info = (Map) row;

            log.debug(info.get("oid"));
            log.debug(info.get("cost"));


            updateBillingAndShippingRecords(info.get("oid").toString(), info.get("id").toString(), info.get("carr_service").toString(), info.get("weight").toString(),
                    info.get("Client ID").toString(), recordedDate, (boolean) info.get("allInclusiveValid"), new BigDecimal(info.get("cost").toString()), Integer.parseInt(info.get("num_packs").toString()),Integer.parseInt(info.get("clientPicked").toString()));


        }



        return success;
    }


   /* public static boolean updateAllInclusivePricingForDateInternational(String recordedDate) throws Exception{
        boolean success = false;

        Query q = HibernateSession.currentSession().createSQLQuery(internationalSql);
        q.setParameter("recordedDate",recordedDate);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);


        List results = q.list();

        for (Object row : results) {
            Map info = (Map) row;

            log.debug(info.get("oid"));

          success =  updateBillingAndShippingRecords(info.get("oid").toString(), info.get("id").toString(), "OSM Domestic", info.get("weight").toString(),
                  info.get("Client ID").toString(), recordedDate,(boolean) info.get("allInclusiveValid"),new BigDecimal(info.get("cost").toString()), Integer.parseInt(info.get("num_packs").toString()) );


        }

        return success;
    }*/

    public static Map<String, String> loadPrice(String carrService, String weight, String clientId, String orderFkey,int clientPicked) throws Exception {
        boolean copyShipping = false;
        Map<String, String> prices;
        try {
            prices = lookupPricesForWeightAndService(carrService, weight, clientId,clientPicked);

        } catch (Exception e) {
            //did not get a good lookup
            copyShipping = true;

            try {
                prices = lookupPricesForEquivilentWeightAndService(carrService, weight, clientId);

            } catch (Exception ee) {
                ee.printStackTrace();
                prices = lookupDefaultPrice(carrService, clientId, orderFkey);
            }
        }
        if (copyShipping) {
            log.debug("Copy: " + copyShipping);
            recordBadShipMethod(orderFkey, clientId);
        }

        return prices;

    }

    public static Map<String, String> loadPrices(String carrService, String weight, String clientId, String orderFkey, int numPacks, int clientPicked) throws Exception {

        Map<String, String> prices;
        if (numPacks == 1) {
            prices = loadPrice(carrService, weight, clientId, orderFkey,clientPicked);
        } else {
            //Load all prices then add on and return

            prices = loadMultiPiecePrices(orderFkey,clientPicked);

        }


        return prices;
    }

    public static Map<String, String> loadMultiPiecePrices(String orderFkey,int clientPicked) throws Exception {
        log.debug("Loading Mulitpiece data for fkey: " + orderFkey);

        double pick = 0.0d;
        double ship = 0.0d;
        double box = 0.0d;
        double receiving = 0.0d;
        double warehousing = 0.0d;
        double returns = 0.0d;



        try {
            String sql = "SELECT\n" +
                    "    dbo.sp_bill_pickpack.oid,\n" +
                    "    dbo.sp_bill_pickpack.id,\n" +
                    "    dbo.owd_order_ship_info.carr_service,\n" +
                    "   ceiling(max(dbo.package.weight_lbs)) as weight,\n" +
                    "    dbo.sp_bill_pickpack.[Client ID],\n" +
                    "    dbo.owd_boxtypes.allInclusiveValid,\n" +
                    "   max(dbo.owd_boxtypes.cost) as cost,\n" +
                    "    num_packs\n" +
                    "FROM\n" +
                    "    dbo.sp_bill_pickpack\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_client\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.sp_bill_pickpack.[Client ID] = dbo.owd_client.client_id)\n" +
                    "INNER JOIN\n" +
                    "    dbo.package_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.sp_bill_pickpack.oid = dbo.package_order.owd_order_fkey)\n" +
                    "INNER JOIN\n" +
                    "    dbo.package\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_boxtypes\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package.owd_boxtypes_fkey = dbo.owd_boxtypes.id)\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_order_ship_info\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.sp_bill_pickpack.oid = dbo.owd_order_ship_info.order_fkey)\n" +
                    "WHERE\n" +
                    "    dbo.owd_client.AllInclusive = 1\n" +
                    "AND dbo.sp_bill_pickpack.shipped_on >= dbo.owd_client.AllInclusive_begin_date\n" +
                    "AND dbo.package_order.is_void = 0\n" +
                    "\n" +
                    "AND oid = :orderFkey\n" +
                    "group by \n" +
                    " dbo.sp_bill_pickpack.oid,\n" +
                    "    dbo.sp_bill_pickpack.id,\n" +
                    "    dbo.owd_order_ship_info.carr_service,\n" +
                    "     dbo.sp_bill_pickpack.[Client ID],\n" +
                    "    dbo.owd_boxtypes.allInclusiveValid,\n" +
                    "    num_packs;";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderFkey", orderFkey);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);


            List results = q.list();
            log.debug("Found " + results.size() + " packages");
            for (Object row : results) {
                Map info = (Map) row;

                Map<String, String> price = loadPrice(info.get("carr_service").toString(), info.get("weight").toString(), info.get("Client ID").toString(), info.get("oid").toString(),clientPicked);

                pick = pick + Double.parseDouble(price.get(XServices));
                ship = ship + Double.parseDouble(price.get(XShipping));
                box = box + Double.parseDouble(price.get(Xboxes));
                receiving = receiving + Double.parseDouble(price.get(XReceiving));
                warehousing = warehousing + Double.parseDouble(price.get(XWarehousing));
                returns =returns + Double.parseDouble(price.get(XReturns));



            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> prices = new HashMap<>();
        prices.put(XServices, pick + "");
        prices.put(XShipping, ship + "");
        prices.put(Xboxes, box+"");
        prices.put(XReceiving, receiving+"");
        prices.put(XWarehousing,warehousing+"");
        prices.put(XReturns,returns+"");

        return prices;

    }

    public static void checkAndFixShippingRecords(){
        String sql = "SELECT\n" +
                "    dbo.sp_bill_shipping.order_id,\n" +
                "    dbo.owd_order_ship_info.carr_service,\n" +
                "   ceiling( dbo.package.weight_lbs) as weight,\n" +
                "    dbo.owd_order.client_fkey,\n" +
                "    dbo.owd_order.ship_packs,\n" +
                "    dbo.sp_bill_shipping.recorded_date,\n" +
                "     isnull(convert(varchar,owd_tags.value),0) as clientPicked\n" +
                "FROM\n" +
                "    dbo.sp_bill_shipping\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.sp_bill_shipping.order_id = dbo.owd_order.order_id)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.sp_bill_shipping.order_id = dbo.owd_order_ship_info.order_fkey)\n" +
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
                "    dbo.owd_client\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.client_fkey = dbo.owd_client.client_id)\n" +
                "left outer join\n" +
                "        owd_tags\n" +
                "        on(\n" +
                "        owd_order.order_id = owd_tags.external_id and name = 'COM.OWD.SHIPPING.ALLINCLUSIVE'\n" +
                "        )\n" +
                "                \n" +
                "WHERE\n" +
                "    dbo.package_order.is_void = 0\n" +
                "AND dbo.sp_bill_shipping.weight = 0\n" +
                "AND dbo.owd_client.AllInclusive = 1  and recorded_date > '2018-07-01';";
        try {
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List results = q.list();
            for(Object row: results){
                Map info = (Map) row;
                updateShippingRecord(info.get("order_id").toString(),info.get("carr_service").toString(),info.get("weight").toString(),info.get("client_fkey").toString(),info.get("recorded_date").toString(),Integer.parseInt(info.get("ship_packs").toString()),Integer.parseInt(info.get("clientPicked").toString()));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean updateShippingRecord(String orderFkey,  String carrService, String weight, String clientId, String recordedDate, int numPacks,int clientPicked) throws Exception {

        boolean success = false;

        log.debug("Lookup up prices");
        Map<String, String> prices;
        try {
            prices = loadPrices(carrService, weight, clientId, orderFkey, numPacks,clientPicked);

            log.debug("Loaded Prices");

           // success = updateBillingRecord(billingId, prices.get(XServices), AllInclusive, cost, numPacks);



                success = updateShipppingRecord(orderFkey, prices.get(XShipping), recordedDate, weight);



        } catch (LogableException e) {
            //caught logsable now move along
        }


        return success;

    }


    public static boolean updateBillingAndShippingRecords(String orderFkey, String billingId, String carrService, String weight, String clientId, String recordedDate, boolean AllInclusive, BigDecimal cost, int numPacks, int clientPicked) throws Exception {

        boolean success = false;

        log.debug("Lookup up prices");
        Map<String, String> prices;
        try {
            prices = loadPrices(carrService, weight, clientId, orderFkey, numPacks,clientPicked);

            log.debug("Loaded Prices");

            success = updateBillingRecord(billingId, prices, AllInclusive, cost, numPacks);

            if (success) {

                success = updateShipppingRecord(orderFkey, prices.get(XShipping), recordedDate, weight);

            }

        } catch (LogableException e) {
            //caught logsable now move along
        }


        return success;

    }

    public static boolean recordBadShipMethod(String orderId, String clientId) throws Exception {
        try {
            boolean success = false;
            String sql = "insert into sp_bill_AllInclusive_Errors (order_fkey, client_fkey) values (:orderId, :clientId)";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("orderId", orderId);
            q.setParameter("clientId", clientId);
            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            return success;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateShipppingRecord(String orderId, String shippingFee, String recordedDate, String weight) throws Exception {

        boolean success = false;
        String sql = "update sp_bill_shipping set amount = :shippingFee, original_amount = amount, weight = :weight where order_id = :orderId and recorded_date = :recordedDate";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("orderId", orderId);
        q.setParameter("shippingFee", shippingFee);
        q.setParameter("recordedDate", recordedDate);
        q.setParameter("weight", weight);
        int i = q.executeUpdate();
        if (i > 0) {
            HibUtils.commit(HibernateSession.currentSession());
            success = true;

        }


        return success;
    }

    public static boolean updateBillingRecord(String billingId, Map<String,String> prices, boolean AllInclusive, BigDecimal cost, int numPacks) throws Exception {
        boolean success = false;
        log.debug("Updating: " + billingId);
        String sql = "update sp_bill_pickpack set [International Fees] = 0.0, colorprint = 0.0, packageCost = :cost, " +
                "[Order Fees] = :orderFees, [Additional Pick Fees] = 0.0, [Insert Fees] = 0.0, [Order Change Fees] = 0.0, inclusive_boxes = :boxes," +
                "inclusive_receiving = :receiving, inclusive_warehousing = :warehousing, inclusive_returns = :returns, bulky_pick_fees = 0.0, bulky_pack_fees = 0.0 where id = :id";

        Query q = HibernateSession.currentSession().createSQLQuery(sql);

        q.setParameter("orderFees", prices.get(XServices));
        q.setParameter("boxes",prices.get(Xboxes));
        q.setParameter("receiving",prices.get(XReceiving));
        q.setParameter("warehousing",prices.get(XWarehousing));
        q.setParameter("returns",prices.get(XReturns));
        if (AllInclusive) {
            q.setParameter("cost", "0.0");

        } else {
            q.setParameter("cost", cost.multiply(XHandPackMultiplyer));
        }

        q.setParameter("id", billingId);

        int i = q.executeUpdate();
        if (i > 0) {
            HibUtils.commit(HibernateSession.currentSession());
            success = true;

        }

        return success;
    }

    public static Map<String, String> lookupDefaultPrice(String carrService, String clientId, String orderId) throws Exception {
        log.debug("Doing equivilent");
        log.debug(carrService);
        log.debug(clientId);

        Map<String, String> prices = new HashMap<>();
        //defualt to ground lookup verify international and second and next
        String lookup = "G";
        if (internationalMethods.contains(carrService)) {
            lookup = "I";
        }
        if (secondDayMethods.contains(carrService)) {
            lookup = "S";
        }
        if (nextDayMethods.contains(carrService)) {
            lookup = "N";
        }
        if (threeDayMethods.contains(carrService)){
            lookup = "3DAY";
        }

        log.debug(lookup);
        String sql = "select service, shipping,boxes,receiving,warehousing,returns from owd_client_AllInclusive_Fees where client_fkey = :clientId and service_type = :service and weight = :weight";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId", clientId);
        q.setParameter("service", lookup);
        q.setParameter("weight", "9999");


        List l = q.list();

        if (l.size() > 0) {
            Object[] data = (Object[]) l.get(0);
            prices.put(XServices, data[0].toString());
            prices.put(XShipping, data[1].toString());
            prices.put(Xboxes,data[2].toString());
            prices.put(XReceiving,data[3].toString());
            prices.put(XWarehousing,data[4].toString());
            prices.put(XReturns,data[5].toString());

        } else {
            throw new LogableException("Invalid lookup for " + carrService + ": " + orderId, orderId, clientId, "All Inclusive Billing", LogableException.errorTypes.INTERNAL);
        }


        return prices;
    }

    public static Map<String, String> lookupPricesForEquivilentWeightAndService(String carrService, String weight, String clientId) throws Exception {
        log.debug("Doing equivilent");
        log.debug(carrService);
        log.debug(clientId);
        log.debug(weight);
        Map<String, String> prices = new HashMap<>();
        //defualt to ground lookup verify international and second and next
        String lookup = "G";
        if (internationalMethods.contains(carrService)) {
            lookup = "I";
        }
        if (secondDayMethods.contains(carrService)) {
            lookup = "S";
        }
        if (nextDayMethods.contains(carrService)) {
            lookup = "N";
        }
        if (threeDayMethods.contains(carrService)){
            lookup = "3DAY";
        }

        log.debug(lookup);
        String sql = "select service, shipping,boxes,receiving,warehousing,returns from owd_client_AllInclusive_Fees where client_fkey = :clientId and service_type = :service and weight = :weight";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("clientId", clientId);
        q.setParameter("service", lookup);
        q.setParameter("weight", weight);


        List l = q.list();

        if (l.size() > 0) {
            Object[] data = (Object[]) l.get(0);
            prices.put(XServices, data[0].toString());
            prices.put(XShipping, data[1].toString());
            prices.put(Xboxes,data[2].toString());
            prices.put(XReceiving,data[3].toString());
            prices.put(XWarehousing,data[4].toString());
            prices.put(XReturns,data[5].toString());

        } else {
            throw new Exception("Invalid lookup for " + carrService + ": " + weight + "lbs");
        }


        return prices;
    }

    public static Map<String, String> lookupPricesForWeightAndService(String carrService, String weight, String clientId,int clientPicked) throws Exception {
        Map<String, String> prices = new HashMap<>();
        try {

            if(clientId.equals("607")){
                if(carrService.equalsIgnoreCase("USPS Priority Mail")){
                    if(clientPicked==0){
                        carrService = "Ground Default";
                        log.debug("OWD switched, use ground pricing");
                    }
                }
            }

            String sql = "SELECT\n" +
                    "    dbo.owd_client_AllInclusive_Fees.service,\n" +
                    "    dbo.owd_client_AllInclusive_Fees.shipping,boxes,receiving,warehousing,returns \n" +
                    "FROM\n" +
                    "    dbo.owd_client_AllInclusive_Methods\n" +
                    "INNER JOIN\n" +
                    "    dbo.owd_client_AllInclusive_Fees\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_client_AllInclusive_Methods.service_type =\n" +
                    "        dbo.owd_client_AllInclusive_Fees.service_type)\n" +
                    "WHERE\n" +
                    "    dbo.owd_client_AllInclusive_Methods.client_fkey = :clientId\n" +
                    "AND dbo.owd_client_AllInclusive_Methods.ship_method = :carrService\n" +
                    "AND dbo.owd_client_AllInclusive_Fees.weight = :weight ";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId", clientId);
            q.setParameter("carrService", carrService);
            q.setParameter("weight", weight);
            List l = q.list();

            if (l.size() > 0) {
                Object[] data = (Object[]) l.get(0);
                prices.put(XServices, data[0].toString());
                prices.put(XShipping, data[1].toString());
                prices.put(Xboxes,data[2].toString());
                prices.put(XReceiving,data[3].toString());
                prices.put(XWarehousing,data[4].toString());
                prices.put(XReturns,data[5].toString());

            } else {
                throw new Exception("Invalid lookup");
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to load Prices for carrService and weight " + carrService + ":" + weight);

        }


        return prices;
    }


    private static String internationalSql = "SELECT\n" +
            "    dbo.sp_bill_pickpack.oid,\n" +
            "    dbo.sp_bill_pickpack.id,\n" +
            "    dbo.owd_order_ship_info.carr_service,\n" +
            "    ceiling(dbo.package.weight_lbs) as weight,\n" +
            "    dbo.sp_bill_pickpack.[Client ID],\n" +
            "    dbo.package_order.num_packs\n" +
            "FROM\n" +
            "    dbo.sp_bill_pickpack\n" +
            "INNER JOIN\n" +
            "    dbo.owd_client\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.sp_bill_pickpack.[Client ID] = dbo.owd_client.client_id)\n" +
            "INNER JOIN\n" +
            "    dbo.package_order\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.sp_bill_pickpack.oid = dbo.package_order.owd_order_fkey)\n" +
            "INNER JOIN\n" +
            "    dbo.package\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
            "INNER JOIN\n" +
            "    dbo.owd_boxtypes\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.package.owd_boxtypes_fkey = dbo.owd_boxtypes.id)\n" +
            "INNER JOIN\n" +
            "    dbo.owd_order_ship_info\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.sp_bill_pickpack.oid = dbo.owd_order_ship_info.order_fkey)\n" +
            "INNER JOIN\n" +
            "    dbo.owd_client_AllInclusive_Methods\n" +
            "ON\n" +
            "    (\n" +
            "        dbo.owd_order_ship_info.carr_service = dbo.owd_client_AllInclusive_Methods.ship_method)\n" +
            "WHERE\n" +
            "    dbo.owd_client.AllInclusive = 1\n" +
            "AND dbo.sp_bill_pickpack.shipped_on >= dbo.owd_client.AllInclusive_begin_date\n" +
            "AND dbo.package_order.is_void = 0\n" +
            "AND dbo.sp_bill_pickpack.shipped_on = :recordedDate\n" +
            "AND dbo.owd_client_AllInclusive_Methods.service_type = 'I' ;";
}
