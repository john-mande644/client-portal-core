package com.owd.alittlePlaying.flatRate;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

/**
 * Created by danny on 5/26/2019.
 */
public class insertFlatRatePricing {


    
    public static void main(String[] args){

        String facility = "DC6";
        runThemAll("489","fredericks",facility);
        runThemAll("551","",facility);
        runThemAll("576","BD Influencer",facility);
        runThemAll("576","",facility);
        runThemAll("576","BD College",facility);
        runThemAll("576","Amazon Seller",facility);
        runThemAll("576","Donation",facility);
        runThemAll("576","Institutional Ketone",facility);
        runThemAll("576","Retail Tradeshow",facility);
        runThemAll("576","Ecommerce",facility);
        runThemAll("576","BD PR",facility);
        runThemAll("576","Ketone Institutional",facility);
        runThemAll("576","Ecommerce Support",facility);
        runThemAll("576","Amazon Other",facility);
        runThemAll("576","Retail Ecommerce",facility);
        runThemAll("576","BD Wholesale",facility);
        runThemAll("576","Amazon",facility);
        runThemAll("576","Retail C-Store",facility);
        runThemAll("576","BD Sub Box",facility);
        runThemAll("576","Retail/Wholesale",facility);
        runThemAll("576","Transfer",facility);
        runThemAll("607","",facility);
        runThemAll("622","",facility);
        runThemAll("630","",facility);
        runThemAll("631","Replenishment ",facility);
        runThemAll("631","shopify",facility);
        runThemAll("631","Wholesale",facility);
        runThemAll("631","",facility);
        runThemAll("632","",facility);
        runThemAll("633","",facility);
        runThemAll("634","joor",facility);
        runThemAll("634","Fireworks Distribution Center",facility);
        runThemAll("634","Fred Segal / Ron Robinson",facility);
        runThemAll("634","",facility);
        runThemAll("634","Axcellent Co., Ltd.",facility);
        runThemAll("634","ecommerce",facility);



    }


    public static void runThemAll(String clientId, String groupName, String facility){
        insertServiceLevel1(clientId, groupName, facility);
        insertServiceLevel2(clientId, groupName, facility);
        insertServiceLevel8(clientId,groupName,facility);

    }
    
    
    
    public static boolean deleteMap(String clientId, String groupName, String facility, String serviceLevel){
        boolean success = false;
        String sql = "delete from owd_ship_service_flatrate_map where client_id = :clientId and group_name = :groupName and facility = :facility and service_level_id = :serviceLevel";
        try {
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("groupName",groupName);
            q.setParameter("facility", facility);
            q.setParameter("serviceLevel",serviceLevel);
            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
                success = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }
    
    
    public static boolean insertServiceLevel1(String clientId, String groupName, String facility){
        deleteMap(clientId,groupName,facility,"1");
        
        String sql = "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 1, 1, 1, 128, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 1, 0, 0, 126, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 1, 2, 71, 115, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 1, 72, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 2, 0, 0, 126, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 2, 1, 1, 128, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 2, 2, 71, 115, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 2, 72, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 3, 0, 0, 126, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 3, 1, 1, 128, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 3, 2, 71, 115, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 3, 72, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 4, 0, 0, 126, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 4, 1, 1, 128, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 4, 2, 71, 115, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 4, 72, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 5, 0, 0, 126, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 5, 1, 1, 128, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 5, 2, 56, 115, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 5, 57, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 6, 0, 0, 126, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 6, 1, 1, 128, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 6, 2, 37, 115, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 6, 38, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 7, 0, 0, 126, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 7, 1, 1, 128, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 7, 2, 32, 115, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 7, 33, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 8, 0, 0, 126, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 8, 1, 1, 128, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 8, 2, 29, 115, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (1, 8, 30, 75, 107, :clientId, :groupName, :facility);\n";

        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("groupName",groupName);
            q.setParameter("facility",facility);
            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        
        return false;
    }

    public static boolean insertServiceLevel2(String clientId, String groupName, String facility){
        deleteMap(clientId,groupName,facility,"2");

        String sql = "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 1, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 1, 1, 6, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 1, 7, 11, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 1, 12, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 2, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 2, 1, 6, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 2, 7, 11, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 2, 12, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 3, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 3, 1, 6, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 3, 7, 10, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 3, 11, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 4, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 4, 1, 5, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 4, 6, 10, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 4, 11, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 5, 0, 0, 125, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 5, 1, 4, 127, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 5, 5, 5, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 5, 6, 10, 7, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 5, 11, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 6, 0, 0, 125, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 6, 1, 4, 127, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 6, 5, 10, 7, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 6, 11, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 7, 0, 0, 125, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 7, 1, 3, 127, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 7, 4, 10, 7, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 7, 11, 75, 107, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 8, 0, 0, 125, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 8, 1, 1, 127, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 8, 2, 3, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 8, 4, 10, 7, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (2, 8, 11, 75, 107, :clientId, :groupName, :facility);\n";

        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("groupName",groupName);
            q.setParameter("facility",facility);
            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }


    public static boolean insertServiceLevel8(String clientId, String groupName, String facility){
        deleteMap(clientId,groupName,facility,"8");

        String sql = "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 1, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 1, 1, 4, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 1, 5, 75, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 2, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 2, 1, 4, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 2, 5, 75, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 3, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 3, 1, 4, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 3, 5, 9, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 3, 10, 25, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 3, 26, 75, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 4, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 4, 1, 4, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 4, 5, 5, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 4, 6, 6, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 4, 7, 75, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 5, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 5, 1, 4, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 5, 5, 5, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 5, 6, 6, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 5, 7, 75, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 6, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 6, 1, 6, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 6, 7, 75, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 7, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 7, 1, 22, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 7, 23, 75, 86, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 8, 0, 0, 141, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 8, 1, 25, 142, :clientId, :groupName, :facility);\n" +
                "INSERT INTO owd_ship_service_flatrate_map (service_level_id, zone, start_weight, end_weight, ship_method_id, client_id, group_name, facility) VALUES (8, 8, 26, 75, 86, :clientId, :groupName, :facility);\n";

        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("clientId",clientId);
            q.setParameter("groupName",groupName);
            q.setParameter("facility",facility);
            int i = q.executeUpdate();
            if(i>0){
                HibUtils.commit(HibernateSession.currentSession());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
