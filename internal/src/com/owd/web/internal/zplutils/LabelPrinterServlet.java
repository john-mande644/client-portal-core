package com.owd.web.internal.zplutils;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LabelPrinterServlet extends HttpServlet {
    private final static Logger log = LogManager.getLogger();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("locations", DBUtils.getLocations());
        request.setAttribute("vendors", DBUtils.getVendors());
        request.getRequestDispatcher("label_printer.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numCartons = Integer.parseInt(request.getParameter("num_cartons"));
        String orderNum = request.getParameter("order_num");
        String PONum = request.getParameter("PO_num");
        int fullCartonQty = Integer.parseInt(request.getParameter("full_carton_qty"));
        int partialCartonQty = Integer.parseInt(request.getParameter("partial_carton_qty"));
        String itemNum = request.getParameter("item_num");
        int shipLocation = Integer.parseInt(request.getParameter("ship_location"));
        int vendorId = Integer.parseInt(request.getParameter("vendor"));

        // checks if advanced carton options were used
        boolean advanced = "true".equals(request.getParameter("advanced"));
        List<Carton> advCartonInfo = new ArrayList<>();

        if(advanced) {
            for(int i = 1; i <= numCartons; i++) {
                String advItemNum = request.getParameter("adv_item_num" + i);
                if(advItemNum.isEmpty()) advItemNum = itemNum; // if no item number is specified, use the default

                boolean partial = "true".equals(request.getParameter("adv_partial"));

                String advQtyStr = request.getParameter("adv_qty" + i);
                // if no item quantity is specified, use the partial or full carton quantity
                int advQty = !advQtyStr.isEmpty() ? Integer.parseInt(advQtyStr) : (partial ? partialCartonQty : fullCartonQty);

                advCartonInfo.add(new Carton(advItemNum, advQty, i, partial));
            }
        }

        String error = "";
        boolean validated = true;

        // check that the item number exists in the database
        // If it doesn't, return an error
        try {
            // if not advanced, only validate the main item number
            if(!advanced) {
                ResultSet vRes = HibernateSession.getResultSet("SELECT * FROM zpl_products WHERE sku='" + itemNum + "';");
                if (!vRes.next()) {
                    validated = false;
                    error += "No product associated with the given item number. Please check the item number is correct and try again.";
                }
            } else {
                // if advanced, verify every item number
                for(Carton c : advCartonInfo) {
                    ResultSet vRes = HibernateSession.getResultSet("SELECT * FROM zpl_products WHERE sku='" + c.getItemNum() + "';");
                    if (!vRes.next()) {
                        validated = false;
                        error += "Carton " + c.getNum() + ": No product associated with the given item number. Please check the item number is correct and try again.";
                    }
                }
            }
        } catch (Exception e) {
            validated = false;
            error += "exception occurred when validating the item number:<br/>" + e;
        }

        // don't process the request if the input is invalid
        if (validated) {
            try {
                // clear the zpl_info and carton_info tables if the cleardb option was checked
                // if("clear".equals(request.getParameter("clear_db"))) {
                    Query q = HibernateSession.currentSession().createSQLQuery("TRUNCATE TABLE zpl_info; TRUNCATE TABLE carton_info;");
                    q.executeUpdate();
                // }

                String sscc = DBUtils.getSSCC();

                // add all the data to the zpl_info table
                String query = "INSERT INTO zpl_info (sscc, order_num, num_cartons, PO_num, full_carton_qty, partial_carton_qty, ship_location, vendor_id) "
                        + "VALUES (:sscc, :orderNum, :numCartons, :PONum, :fullCartonQty, :partialCartonQty, :shipLocation, :vendorId);";

                q = HibernateSession.currentSession().createSQLQuery(query);
                q.setParameter("sscc", sscc);
                q.setParameter("orderNum", orderNum);
                q.setParameter("numCartons", numCartons);
                q.setParameter("PONum", PONum);
                q.setParameter("fullCartonQty", fullCartonQty);
                q.setParameter("partialCartonQty", partialCartonQty);
                q.setParameter("shipLocation", shipLocation);
                q.setParameter("vendorId", vendorId);

                int c = q.executeUpdate();
                System.out.println("updated " + c);
                if (c == 0) {
                    error += "No changes made to database\n";
                }


                ResultSet r = HibernateSession.getResultSet("SELECT * FROM zpl_info WHERE sscc='" + sscc + "';");
                r.next();
                int index = r.getInt("ID");


                if(!advanced) {
                    // if not advanced, iterate to the number of cartons and add them to the db
                    for (int i = 1; i <= numCartons; i++) {
                        sscc = DBUtils.getSSCC();

                        query = "INSERT INTO carton_info (carton_sscc, carton_num, label_index, carton_qty, item_num, type) VALUES (:cartonSscc, :cartonNum, :labelIndex, :cartonQty, :itemNum, :type);";
                        q = HibernateSession.currentSession().createSQLQuery(query);
                        q.setParameter("cartonSscc", sscc);
                        q.setParameter("cartonNum", i);
                        q.setParameter("labelIndex", index);
                        q.setParameter("itemNum", itemNum);

                        // only mark carton as partial if it is the last carton and the partial carton quantity doesn't match master
                        q.setParameter("type", i == numCartons && partialCartonQty != fullCartonQty ? "partial" : "master");

                        // if this is the last carton, set the quantity as the partial carton quantity
                        int qty = i == numCartons ? partialCartonQty : fullCartonQty;
                        q.setParameter("cartonQty", qty);
                        c = q.executeUpdate();

                        if (c > 0) {
                            HibUtils.commit(HibernateSession.currentSession());
                        }
                    }
                } else {
                    // if advanced, use the carton info from the input
                    for(Carton carton : advCartonInfo) {
                        sscc = DBUtils.getSSCC();

                        query = "INSERT INTO carton_info (carton_sscc, carton_num, label_index, carton_qty, item_num, type) VALUES (:cartonSscc, :cartonNum, :labelIndex, :cartonQty, :itemNum, :type);";
                        q = HibernateSession.currentSession().createSQLQuery(query);
                        q.setParameter("cartonSscc", sscc);
                        q.setParameter("cartonNum", carton.getNum());
                        q.setParameter("labelIndex", index);
                        q.setParameter("itemNum", carton.getItemNum());
                        q.setParameter("cartonQty", carton.getQty());
                        q.setParameter("type", carton.isPartial() ? "partial" : "master");
                        c = q.executeUpdate();

                        if (c > 0) {
                            HibUtils.commit(HibernateSession.currentSession());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                error += "Exception occurred writing to the database:\n" + e + "\n";
            }

            try {
                String str = convertTOZPL();

                // line breaks won't work in the paragraph element, so replace them with a html line break
                str = str.replace("\n", "<br/>");

                request.setAttribute("zpl", str);
            } catch (Exception e) {
                e.printStackTrace();
                error += e;
            }

        }

        // set the parameters to be sent to the page
        request.setAttribute("locations", DBUtils.getLocations());
        request.setAttribute("vendors", DBUtils.getVendors());

        // if an error occurred, send the info to the page
        if (!error.isEmpty()) {
            request.setAttribute("error", error.replace("\n", "<br/>"));
        }

        // navigate back to the page
        request.getRequestDispatcher("label_printer.jsp").forward(request, response);
    }

    /**
     * Creates a zpl format string from the information in the zpl_info and carton_info tables
     * Multiple labels will be merged into a single file
     * @return
     */
    private String convertTOZPL() throws Exception {
        StringBuilder sb = new StringBuilder();
        // selects everything from the zpl_info table, and merges the columns for the corresponding ship locations into them
        PreparedStatement stmt = HibernateSession.getPreparedStatement("SELECT * FROM zpl_info " +
                "INNER JOIN ship_locations ON (zpl_info.ship_location=ship_locations.num)" +
                "INNER JOIN vendors ON (zpl_info.vendor_id=vendors.num)" +
                "INNER JOIN carton_info ON (zpl_info.ID=carton_info.label_index)" +
                "INNER JOIN zpl_products ON (carton_info.item_num=zpl_products.sku) " +
                "ORDER BY zpl_info.ID, carton_num;"
        );
        ResultSet res = stmt.executeQuery();
        while (res.next()) {
            // not all parameters here are used, but may be needed in the future.
            String itemNum = res.getString("item_num");
            String orderNum = res.getString("order_num");  // OrderNum
            String ctnQty = res.getString("carton_qty"); // CartonQuantity
            String desc = (itemNum.length() > 6 ? itemNum.substring(itemNum.length() - 6) : itemNum) + " " + res.getString("description"); // VendorItemDesc
            String poNum = res.getString("PO_num"); // PoNumber
            String countryOfOrigin = "US"; // CountryOfOrigin
            String vendorName = res.getString("vendor_name"); // VendorName
            String vendorId = res.getString("vendor_num"); // VendorId
            String expirationDate = "NULL"; // ExpirationDate
            String partialText = res.getString("type").equals("partial") ? "PARTIAL CARTON" : "";
            String ctnsscc = res.getString("carton_sscc"); // SerializedShippingContainerCode
            String ctnNum = res.getString("carton_num"); // CartonNumber
            String orderCartons = res.getString("num_cartons"); // OrderCartons
            String mixedProducts = "NULL"; // MixedProducts
            String shipTo = res.getString("ship_to"); // ShippedToCompany
            String address1 = res.getString("address1"); // ShippedToStreet1
            String address2 = res.getString("address2"); //ShippedToStreet2
            String city = res.getString("city"); // ShippedToCity
            String state = res.getString("state"); // ShippedToState
            String zipCode = res.getString("zip_code"); // ShippedToZIPCode
            String ctnString = ctnNum + " of " + orderCartons;
            String address3 = city + ", " + state + ", " + zipCode;
            sb.append(ZPLLabelCreationUtil.getZPLLabel(poNum, ctnsscc, ctnQty, ctnString, shipTo, address1, address2, address3, countryOfOrigin, desc, itemNum, partialText, vendorName, vendorId) + "\n");
        }

        stmt.close();

        return sb.toString();
    }
}
