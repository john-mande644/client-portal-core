package com.owd.dc.warehouse.labelMaker;

import com.owd.dc.beans.jsonLabelMakerInventoryBean;
import com.owd.dc.beans.jsonResultBean;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jun 19, 2009
 * Time: 10:07:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class labelMakerUtilities {
    public static void main(String[] args) {
        try {
                /* List<String> l = new ArrayList<String>();

l.add("37140");
l.add("37141");
l.add("37142");

                 for (String s : l){
                    printSingleLocation(s,"zebra1.dc5.owd.com", HibernateSession.currentSession(),"Bin "+s);

                 }*/
            Map printers = loadPrinterMap(HibernateSession.currentSession());
            System.out.println(getPrinterTabs(printers));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String facilityLookupSql = "select ixNode, locname from w_location where ixLocType = 5 order by locname";
    private static String printerLookupSql = "select value,display from dbo.app_data where project = 'wms' and description = 'labelMaker' and variable = 'printer'\n" +
            "order by display";
    private static String clientLookupSql = "select client_id, company_name from owd_client where is_active = 1 order by company_name ";
    private static String loadInventoryForClientSql = "select inventory_id, inventory_num, description from owd_inventory where client_fkey = :clientFkey and is_active = 1";

    public static jsonLabelMakerInventoryBean getInventoryForClient(Session sess, String clientFkey) throws Exception {
        jsonLabelMakerInventoryBean JIB = new jsonLabelMakerInventoryBean();
        Query q = sess.createSQLQuery(loadInventoryForClientSql);
        q.setParameter("clientFkey", clientFkey);
        List results = q.list();
        List<jsonLabelMakerInventoryBean.labelMakerInventoryBean> inventory = new ArrayList<jsonLabelMakerInventoryBean.labelMakerInventoryBean>();
        for (Object row : results) {
            Object[] data = (Object[]) row;
            jsonLabelMakerInventoryBean.labelMakerInventoryBean ib = new jsonLabelMakerInventoryBean.labelMakerInventoryBean();
            ib.setId(data[0].toString());
            ib.setSku(data[1].toString());
            ib.setDescription(data[2].toString());
            inventory.add(ib);


        }
        JIB.setItems(inventory);
        return JIB;

    }

    public static Map<String, String> loadClientMap(Session sess) throws Exception {
        Map<String, String> facility = new TreeMap<String, String>();
        Query q = sess.createSQLQuery(clientLookupSql);
        List results = q.list();
        for (Object row : results.toArray()) {
            Object[] data = (Object[]) row;
            facility.put(data[1].toString(), data[0].toString());
        }


        return facility;
    }

    public static Map<String, String> loadFacilityMap(Session sess) throws Exception {
        Map<String, String> facility = new HashMap<String, String>();
        Query q = sess.createSQLQuery(facilityLookupSql);
        List results = q.list();
        for (Object row : results.toArray()) {
            Object[] data = (Object[]) row;
            facility.put(data[0].toString(), data[1].toString());
        }


        return facility;
    }

    public static String getSizeFromPrinter(String printer, Session sess) throws Exception {
        String labelSize = "unknown";
        String getPrinterInfoSQL = "select display from app_data where value = :value";

        Query q = sess.createSQLQuery(getPrinterInfoSQL);
        q.setParameter("value", printer);
        String result = q.list().get(0).toString();
        System.out.println(result);
        if (result.contains("--Large")) {
            labelSize = "large";
        }
        if (result.contains("--Small")) {
            labelSize = "small";
        }


        return labelSize;
    }

    public static String getLargePrinterForMobileViaParent(Session sess, String parentId) {
        String printer = "zebra1.dc1.owd.com";
        String sql = "SELECT\n" +
                "    value\n" +
                "FROM\n" +
                "    dbo.w_location,\n" +
                "    dbo.app_data\n" +
                "WHERE\n" +
                "    dbo.w_location.ixNode = :parentId\n" +
                "AND dbo.app_data.display LIKE (locname+'%Large') ;";
        Query q = sess.createSQLQuery(sql);
        q.setParameter("parentId", parentId);
        printer = q.list().get(0).toString();
        return printer;
    }

    public static Map<String, String> loadPrinterMap(Session sess) throws Exception {
        Map<String, String> Printer = new TreeMap<String, String>();
        Query q = sess.createSQLQuery(printerLookupSql);
        List results = q.list();
        for (Object row : results.toArray()) {
            Object[] data = (Object[]) row;
            Printer.put(data[0].toString(), data[1].toString());
        }


        return Printer;
    }

    public static Map<String, Map<String, String>> getPrinterTabs(Map<String, String> printers) {
        Map<String, Map<String, String>> tprint = new TreeMap<String, Map<String, String>>();

        for (String purl : printers.keySet()) {
            String loc = purl.split("\\.")[1];
            if (tprint.containsKey(loc)) {
                tprint.get(loc).put(purl, printers.get(purl));
            } else {
                tprint.put(loc, new TreeMap<String, String>());
                tprint.get(loc).put(purl, printers.get(purl));
            }


        }


        return tprint;
    }

    public static jsonResultBean printTreeMap(Map<String, String> locs, String printer, Session sess) {
        jsonResultBean b = new jsonResultBean();
        try {
            com.owd.dc.locations.resultBean result = com.owd.dc.locations.printLocationLabel.printTreeForLocationInMap(locs, printer, sess);
            if (result.isSuccess()) {
                b.setMessage("Printed Location tree just fine!!");
            } else {
                b.setError("Could not print labels because: " + result.getErrors());
            }
        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());
        }
        return b;
    }

    public static jsonResultBean printLocationMap(Map<String, String> locs, String printer, Session sess) {
        jsonResultBean b = new jsonResultBean();
        try {
            com.owd.dc.locations.resultBean result = com.owd.dc.locations.printLocationLabel.printLocationsInMap(locs, printer, sess);
            if (result.isSuccess()) {
                b.setMessage("Printed All locations just fine!!");
            } else {
                b.setError("Could not print labels because: " + result.getErrors());
            }
        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());
        }
        return b;
    }

    public static jsonResultBean printDirectChildrenLocation(String id, String printer, Session sess, String locName) {
        System.out.println("in printdirectDhildrenlocation");
        jsonResultBean b = new jsonResultBean();
        try {
            com.owd.dc.locations.resultBean result = com.owd.dc.locations.printLocationLabel.printDirectChildrenNoMobile(id, printer, sess);
            if (result.isSuccess()) {
                b.setMessage("Printed label list for " + locName);
            } else {
                b.setError("Could not print labels because: " + result.getErrors());
            }
        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());
        }
        return b;
    }

    public static jsonResultBean printSingleLocation(String id, String printer, Session sess, String locName) {
        jsonResultBean b = new jsonResultBean();
        try {
            System.out.println("doing printing for " + id);
            com.owd.dc.locations.resultBean result = com.owd.dc.locations.printLocationLabel.printLocationById(id, printer, sess);
            if (result.isSuccess()) {
                b.setMessage("Printed label for " + locName);
            } else {
                b.setError("Could not print label because: " + result.getErrors());
            }


        } catch (Exception e) {
            e.printStackTrace();
            b.setError(e.getMessage());

        }
        return b;
    }
}
