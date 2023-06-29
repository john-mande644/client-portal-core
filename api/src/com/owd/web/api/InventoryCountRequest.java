package com.owd.web.api;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.hibernate.HibernateSession;
import org.w3c.dom.Element;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class InventoryCountRequest implements APIRequestHandler

{
private final static Logger log =  LogManager.getLogger();

    //root node name

    public static final String kRootTag = "OWD_INVENTORY_COUNT_REQUEST";

    //root node attributes

    public static final String kSKU = "part_reference";


    public static final String kShowDescription = "show_description";
    public static final String kShowActive = "show_active";
    public static final String kShowBackorders = "show_backorders";

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception

    {

        InventoryCountResponse response = new InventoryCountResponse(api_version);

        //////log.debug("in InventoryCountRequest Handler");

        boolean showDescription = false;
        boolean showActive = false;
        boolean showBackorders = false;

        String sku = root.getAttribute(kSKU).trim();

        if (api_version >= 1.1) {
            if ("1".equals(root.getAttribute(kShowDescription))) {
                showDescription = true;
            }
            if ("1".equals(root.getAttribute(kShowActive))) {
                showActive = true;
            }
            if ("1".equals(root.getAttribute(kShowBackorders))) {
                showBackorders = true;
            }
        }

        if (api_version >= 2.0) {
            getMultiFacilitySnapshot(sku, client.client_id, response, showDescription, showActive, showBackorders,api_version);

        } else {


            getSnapshot(sku, client.client_id, response, showDescription, showActive, showBackorders);
        }
        return response;

    }

    public void getMultiFacilitySnapshot(String sku, String client_key, InventoryCountResponse resp, boolean showDescription, boolean showActive, boolean showBackorders,double api_version) throws Exception

    {

         /*

         Set new query to union current and facility-specific lines, then resort result in
         order needed by XML constructor

          */
        if (sku == null) sku = "";
        sku = sku.trim();
        try {

            PreparedStatement ps;
            if (sku.length() <= 0) {

                ps = HibernateSession.getPreparedStatement("exec sp_API_inventory_count_loc_percent ?");
                ps.setInt(1, Integer.parseInt(client_key));

            } else {
                ps = HibernateSession.getPreparedStatement("exec sp_API_inventory_count_bysku_loc_percent ?,?");
                ps.setInt(1, Integer.parseInt(client_key));
                ps.setString(2, sku);
            }


            ResultSet rs = ps.executeQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("<" + InventoryCountResponse.kRootTag + ">");

            String currentSKU = null;
            int rows = 0;
            while (rs.next())

            {
                sku = rs.getString(1);
                rows++;
                log.debug("testing row " + rows + " loc:" + rs.getString("Location") + " for " + rs.getString(1));
                if (("" + rs.getString("Location")).length() >= 3) {

                    Integer totalQuantity = Integer.parseInt(rs.getString("On Hand"));
                    Integer reportedQuantity = (int) (totalQuantity * (Integer.parseInt(rs.getString("reported_percent"))/100d));
                    if( totalQuantity < 5 && Integer.parseInt(rs.getString("reported_percent"))>0) reportedQuantity = totalQuantity;
                    //is a location
                    assert currentSKU != null;
                    assert currentSKU.equals(rs.getString(1));

                    sb.append("<" + InventoryCountResponse.kFacilityCountTag + " "

                            + API.buildAttribute(InventoryCountResponse.kAttFacilityCode, rs.getString("Location")) + " "
                            + API.buildAttribute(InventoryCountResponse.kAttCount,reportedQuantity+"") + " "
                            + API.buildAttribute(InventoryCountResponse.kAttExpected, rs.getString("Expected")) + " "
                            + API.buildAttribute(InventoryCountResponse.kAttPendingPOs, rs.getString("POs")) + " "
                            + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttBackordered, rs.getString("On Order")) + " " : "")
                            + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttPendingOrders, rs.getString("Orders")) + " " : "")
                            + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttHeld, rs.getString("On Hold")) + " " : "")
                            + API.buildAttribute(InventoryCountResponse.kAttNextArrival, rs.getString("nextDate")) + " "
                            + (api_version>2.5?API.buildAttribute(InventoryCountResponse.kAttTotalQty,totalQuantity+""):"")+ " />");

                } else {
                    if (sku.length() > 0) {
                        if (currentSKU != null) {
                            //close previous
                            sb.append("</" + InventoryCountResponse.kCountTag + ">");
                        }
                        currentSKU = sku;

                        //  resp.addCount(rs.getString(1), (showDescription ? rs.getString(2) : null), (showActive ? rs.getString(3) : null), showBackorders, rs.getString("On Hand"),
                        //          rs.getString("On Order"),rs.getString("On Hold"),  rs.getString("Expected"), rs.getString("Orders"), rs.getString("POs"), rs.getString("nextDate"));

                        //            arrivals.addElement(arrivalDate.substring(6)+arrivalDate.substring(0,2)+arrivalDate.substring(3,5));

                        String arrivalDate = rs.getString("nextDate");
                        Integer totalQuantity = Integer.parseInt(rs.getString("On Hand"));
                        Integer reportedQuantity = (int) (totalQuantity * (Integer.parseInt(rs.getString("reported_percent"))/100d));
                        if( totalQuantity < 5 && Integer.parseInt(rs.getString("reported_percent"))>0) reportedQuantity = totalQuantity;

                        sb.append("<" + InventoryCountResponse.kCountTag + " "
                                + API.buildAttribute(InventoryCountResponse.kAttSKU, rs.getString(1)) + " "
                                + API.buildAttribute(InventoryCountResponse.kAttCount, reportedQuantity+"") + " "
                                + ((showDescription) ? API.buildAttribute(InventoryCountResponse.kAttDescription, rs.getString(2)) + " " : "")
                                + ((showActive) ? API.buildAttribute(InventoryCountResponse.kAttIsActive, rs.getString(3)) + " " : "")
                                + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttBackordered, rs.getString("On Order")) + " " : "")
                                + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttPendingOrders, rs.getString("Orders")) + " " : "")
                                + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttExpected, rs.getString("Expected")) + " " : "")
                                + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttPendingPOs, rs.getString("POs")) + " " : "")
                                + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttNextArrival, arrivalDate.substring(6) + arrivalDate.substring(0, 2) + arrivalDate.substring(3, 5)) + " " : "")
                                + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttHeld, rs.getString("On Hold")) + " " : "")
                                + (api_version>2.5?API.buildAttribute(InventoryCountResponse.kAttTotalQty,totalQuantity+""):"")+ ">");


                    }
                }
            }

            if (rows == 0) throw new Exception("No matching inventory items found!");
            sb.append("</" + InventoryCountResponse.kCountTag + ">");
            sb.append("</" + InventoryCountResponse.kRootTag + ">");

            resp.setXml(sb.toString());

        } catch (Throwable th)

        {

            Exception ex;


            if (th instanceof Exception)

            {

                ex = (Exception) th;

            } else {

                ex = new Exception(th.toString());

            }

            //////log.debug("in InventoryCountRequest getsnapshot ex");

            ex.printStackTrace();


            throw ex;

        } finally

        {

            HibernateSession.closeSession();

        }


    }

    public void getSnapshot(String sku, String client_key, InventoryCountResponse resp, boolean showDescription, boolean showActive, boolean showBackorders) throws Exception

    {


        if (sku == null) sku = "";
        sku = sku.trim();
        try {

            PreparedStatement ps;
            if (sku.length() <= 0) {

                ps = HibernateSession.getPreparedStatement("exec sp_API_inventory_count ?");
                ps.setInt(1, Integer.parseInt(client_key));

            } else {
                ps = HibernateSession.getPreparedStatement("exec sp_API_inventory_count_bysku ?,?");
                ps.setInt(1, Integer.parseInt(client_key));
                ps.setString(2, sku);
            }


            ResultSet rs = ps.executeQuery();
            StringBuffer sb = new StringBuffer();
            sb.append("<" + InventoryCountResponse.kRootTag + ">");

            int rows = 0;
            while (rs.next())

            {
                rows++;
                if (sku.length() > 0 || (!showActive || rs.getInt(3) == 1)) {

                    String arrivalDate = rs.getString("nextDate");

                    sb.append("<" + InventoryCountResponse.kCountTag + " "
                            + API.buildAttribute(InventoryCountResponse.kAttSKU, rs.getString(1)) + " "
                            + API.buildAttribute(InventoryCountResponse.kAttCount, rs.getString("On Hand")) + " "
                            + ((showDescription) ? API.buildAttribute(InventoryCountResponse.kAttDescription, rs.getString(2)) + " " : "")
                            + ((showActive) ? API.buildAttribute(InventoryCountResponse.kAttIsActive, rs.getString(3)) + " " : "")
                            + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttBackordered, rs.getString("On Order")) + " " : "")
                            + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttPendingOrders, rs.getString("Orders")) + " " : "")
                            + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttExpected, rs.getString("Expected")) + " " : "")
                            + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttPendingPOs, rs.getString("POs")) + " " : "")
                            + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttNextArrival, arrivalDate.substring(6) + arrivalDate.substring(0, 2) + arrivalDate.substring(3, 5)) + " " : "")
                            + ((showBackorders) ? API.buildAttribute(InventoryCountResponse.kAttHeld, rs.getString("On Hold")) + " " : "")
                            + " />");

                }
            }
            if (rows == 0) throw new Exception("No matching inventory items found!");

            sb.append("</" + InventoryCountResponse.kRootTag + ">");

            resp.setXml(sb.toString());

        } catch (Throwable th)

        {

            Exception ex;


            if (th instanceof Exception)

            {

                ex = (Exception) th;

            } else {

                ex = new Exception(th.toString());

            }

            //////log.debug("in InventoryCountRequest getsnapshot ex");

            //ex.printStackTrace();


            throw ex;

        } finally

        {

            HibernateSession.closeSession();

        }


    }


}
