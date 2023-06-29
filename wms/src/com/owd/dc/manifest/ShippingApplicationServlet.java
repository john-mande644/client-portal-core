package com.owd.dc.manifest;

import com.owd.core.OWDUtilities;
import com.owd.core.TimeStamp;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.OrderFactory;
import com.owd.core.business.order.Package;
import com.owd.core.managers.ConnectionManager;
import com.owd.dc.warehouse.ABShipments.ABUtils;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipHold;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tools.ant.taskdefs.Pack;
import org.hibernate.Session;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;


public class ShippingApplicationServlet extends HttpServlet {
    private final static Logger log =  LogManager.getLogger();


    public static final String kLoadOrderAction4 = "lo4";
    public static final String kSaveShipmentAction = "ss";
    public static final String kVoidShipmentAction = "vs";
    public static final String kGetCarrierDataAction = "cd";
    public static final String kIsDPBetterAction = "dp";
    public static final String kGetLocationCodesAction = "gl";
    public static final String kHoldOrderAction = "dh";

    public static final String kActionTypeTag = "fn";

    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);

    }

    public void destroy() {
        super.destroy();

    }

    //GET requests not supported
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        try {
            doAction(req, resp);

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    protected void doAction(HttpServletRequest req, HttpServletResponse resp) {
        String action = getStringParam(req, kActionTypeTag, "");

        System.out.println("Action: " + action);

        if (action.equals(kSaveShipmentAction)) {
            _saveShipment(req, resp);
        } else if (action.equals(kVoidShipmentAction)) {
            _voidShipment(req, resp);
        } else if (action.equals(kGetCarrierDataAction)) {
            _getCarrierData(req, resp);
        } else if (action.equals(kLoadOrderAction4)) {
            _loadOrder4(getStringParam(req, "or", ""), resp);
        } else if (action.equals(kHoldOrderAction)) {
            setPostedOrderOnDCHold(req, resp);
        } else if (action.equals(kGetLocationCodesAction)) {
            _getLocationData(req, resp);
        } else {
            try {
                sendRFError(resp, "Request not recognized.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    protected void setPostedOrderOnDCHold(HttpServletRequest req, HttpServletResponse resp) {

        try {
            Session sess = HibernateSession.currentSession();

            OwdOrder order = (OwdOrder) sess.load(OwdOrder.class, new Integer(getStringParam(req, "order_fkey", "")));

            if (order == null) {
                throw new Exception("Order not found in setPostedOrderOnDCHold");
            }
            OwdOrderShipHold holder = order.getHoldinfo();
            if (holder != null) {
                //request.getSession(true).setAttribute("ordershiphold.currshiphold",holder);
                if (holder.getIsOnWhHold().intValue() == 1) {
                    throw new Exception("This order is already on an active DC Hold - clear existing hold before adding a new one!");

                } else {
                    sess.saveOrUpdate(order);
                    sess.saveOrUpdate(order.getHoldinfo());
                    sess.delete(order.getHoldinfo());
                    sess.flush();
                    holder = OrderFactory.getNewOwdOrderShipHold();
                    order.setHoldinfo(holder);
                    holder.setOrder(order);


                }
            } else {
                holder = OrderFactory.getNewOwdOrderShipHold();
                order.setHoldinfo(holder);
                holder.setOrder(order);

            }


            holder.setNeedsReview(new Integer(1));

            //get info from request and update or create OwdOrderShipHold object
            holder.setWhHoldReason("Other");
            holder.setWhHoldNotes("WEIGHT CHECK (Scale:" + getStringParam(req, "weight", "") + " Ref:" +
                    getStringParam(req, "ref_weight_min", "") + "-" + getStringParam(req, "ref_weight_max", "") + ") " + getStringParam(req, "weight_notes", ""));


            System.out.println("WEIGHT NOTES:" + holder.getWhHoldNotes());

            holder.setResNotifyPhone("");


            holder.setResNotifyEmail("");


            holder.setResNotifyAM(order.getClient().getAmEmail());

            holder.setNotifyIT("0");

            holder.setNotifyEmail("");

            holder.setNotifyUser("kyle@owd.com");


            holder.setCreatedBy("WEIGHT CHECKER");
            holder.setCreatedDate(Calendar.getInstance().getTime());

            //if invalid entry, throw Exception


            holder.setIsOnWhHold(new Integer("1"));
            holder.setOrder(order);
            order.setHoldinfo(holder);
            sess.saveOrUpdate(order);
            sess.saveOrUpdate(holder);
            sess.flush();
            com.owd.hibernate.HibUtils.commit(sess);

            //update order with event information

            Event.addOrderEvent(order.getOrderId().intValue(), Event.kEventTypeHandling, "Order placed on warehouse hold due to " + holder.getWhHoldReason() + "\r\n" + holder.getWhHoldNotes(), "WEIGHT CHECKER");
//Done!
            sendRFSuccess(resp, "Hold OK!");
//   tsx.print("_loadOrder2 Proc Timer");
        } catch (Throwable th) {


            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                HibernateSession.closeSession();
            } catch (Exception ex) {
            }
        }
    }

    protected void _loadOrder4(String orderBarcode, HttpServletResponse resp) {
        ResultSet rs = null;

        //  TimeStamp tsx = new TimeStamp("_loadOrder2 Proc Timer");
        TimeStamp ts = new TimeStamp("loadorder");

        try {

            StringBuffer results = new StringBuffer();
            StringBuffer liresults = new StringBuffer();

            if (orderBarcode == null || orderBarcode.length() < 1) {
                throw new Exception("Blank barcode");
            }

            orderBarcode = orderBarcode.replace('?', ' ').trim();

            String orderNum = null;
            String revision = "1";


            String boxID = "1";
            String packageBarcode = "";

            if (orderBarcode.startsWith("p") || orderBarcode.startsWith("P")) {
                //is a package barcode
                packageBarcode = orderBarcode.toUpperCase();
                orderBarcode = packageBarcode.substring(packageBarcode.indexOf("*") + 1);
                orderBarcode = orderBarcode.substring(0, orderBarcode.indexOf("*"));
                boxID = packageBarcode.substring(packageBarcode.indexOf("B") + 1);

                if (orderBarcode.toUpperCase().indexOf("R") > 0) {
                    orderNum = orderBarcode.substring(0, orderBarcode.toUpperCase().indexOf("R"));
                    revision = orderBarcode.substring(orderBarcode.toUpperCase().indexOf("R") + 1);

                } else {
                    orderNum = orderBarcode;
                }

            } else {

                if (orderBarcode.toUpperCase().indexOf("R") > 0) {
                    orderNum = orderBarcode.substring(0, orderBarcode.toUpperCase().indexOf("R"));
                    revision = orderBarcode.substring(orderBarcode.toUpperCase().indexOf("R") + 1);
                } else {
                    orderNum = orderBarcode;
                }
            }

            orderBarcode = '*' + orderBarcode + '*';

            System.out.println("orderBarcode=" + orderBarcode);
            System.out.println("packageBarcode=" + packageBarcode);
            System.out.println("orderNum=" + orderNum);
            System.out.println("revision=" + revision);
            System.out.println("boxID=" + boxID);
            ts.print("loadorder-lo query start");

            //  String sql2 = "exec sp_loadConnectShipOrderData '" + orderBarcode.trim() + "'";
            PreparedStatement ckup = HibernateSession.getPreparedStatement("exec dbo.sp_virtualPackCalltag '" + orderBarcode.trim() + "'");
            ckup.execute();

            if (packageBarcode.length() > 0) {
                rs = HibernateSession.getResultSet("exec sp_loadConnectShipDataFromPackage '" + packageBarcode.trim() + "','" + orderBarcode.trim() + "'");
            } else {
                rs = HibernateSession.getResultSet("exec sp_loadConnectShipDataFromOrder '" + orderBarcode.trim() + "'");
            }

            ts.print("loadorder-lo query done");

            int skus = 0;

            while (rs.next()) {

                //  System.out.println("shipped_packs="+rs.getInt(63));
                if ((packageBarcode.length() == 0 && rs.getInt(63) != 0) || (rs.getInt(63) >= rs.getInt(58))) {
                    throw new Exception("Order has already been shipped! Verify order number and barcode. Find and void previous shipments in Search tab if needed.");
                }
                if (rs.getInt(75) != 0 && packageBarcode.equals(rs.getString(74))) {
                    throw new Exception("This package has already been shipped! Verify package ID and barcode. ");

                }
                if (rs.getInt(64) != 0) {
                    throw new Exception("This client is on shipping hold and cannot ship! Place in shipping hold area.");
                }
                if (rs.getInt(66) != 0) {
                    throw new Exception("This order is on DC Hold - DC Hold must be cleared before shipping!");
                }
                if (skus == 0) {
                    results.append(rs.getString(1).replaceAll("\"", ""));

                    for (int i = 2; i <= 48; i++) {

                        results.append("\t" + rs.getString(i).replaceAll("\"", ""));

                    }

                    results.append("\t" + OWDUtilities.roundFloat(rs.getFloat(65), 2));
                    results.append("\t" + rs.getInt(67));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                    results.append("\t" + rs.getString(68));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                    results.append("\t" + rs.getString(69));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                    results.append("\t" + rs.getString(70));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                    results.append("\t" + rs.getString(71));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                    results.append("\t" + rs.getString(72));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                    results.append("\t" + rs.getString(73));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages
                    results.append("\t" + rs.getString(74));     //ss_proof_delivery from owd_order_ship_info - used to flag sig confirmation for USPS packages

                    //NOTE: referenceweight call returns two columns - min and max weights, with a tab in between the two values. Unfounf refeerences return 0.00 for both values
                    results.append("\t" + getReferenceWeights(rs.getString("RID"), rs.getString("RBOX"), rs.getInt("RCID")));

                    results.append("\r\n");
                }


                skus++;
                String sku = rs.getString(49);
                System.out.println("sku: " + sku);
                if (sku != null && sku.trim().length() > 0) {
                    liresults.append(rs.getString(49));
                    for (int i = 50; i < 63; i++) {
                        liresults.append("\t" + rs.getString(i).replace('\"', ' '));
                    }


                    liresults.append("\r\n");
                }


            }
            rs.close();
            results.append(liresults);

            //Recheck with the database to determine more detail about why no shippable items were found.
            //The stored procedure used above already checks for previous shipments and shipping hold status
            //The if-block below should check with the server and return a different error message/exception
            //for an order that has been replaced with a new version/barcode or an order that has been voided.
            //Orders that get here but don't fall into one of those two categories should get the generic error message
            //currently used below


            if (skus < 1) {
                ResultSet rs1 = HibernateSession.getResultSet("select is_void,order_num_barcode from owd_order  (NOLOCK) where order_num='" + orderNum + "' and is_void=1");
                if (rs1.next()) {
                    int is_void = rs1.getInt(1);
                    String ref = rs1.getString(2);

                    String currRevision = "1";
                    if (ref.toUpperCase().indexOf("R") > 0 && ref.toUpperCase().indexOf("R") < ref.length()) {
                        currRevision = ref.substring(ref.toUpperCase().indexOf("R") + 1);
                    } else {
                        orderNum = orderBarcode;
                    }

                    if (is_void == 1) {
                        throw new Exception("Order ref " + orderNum + " has been voided. Return package for putaway back to stock");
                    } else if (!(revision.equals(currRevision))) {
                        throw new Exception("Order ref " + orderNum + " has been revised and has a new packing slip! Place on DC Hold for review.");
                    } else {
                        throw new Exception("Order ref " + orderNum + " contains no shippable SKUs! Place on DC Hold for review.");
                    }
                } else {
                    System.out.println("Well something is wrong let us see what it could be");
                    throw new Exception("Order ref " + orderNum + " not valid! Check order number/barcode or rescan.");
                }


            }

            ts.print("loadorder-lo items done");

            sendRFSuccess(resp, results.toString());
//   tsx.print("_loadOrder2 Proc Timer");
        } catch (Throwable th) {


            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                HibernateSession.closeSession();
            } catch (Exception ex) {
            }
        }

        // tsx.print("_loadOrder2 Proc Timer");


    }

    protected String getReferenceWeights(String itemID, String boxName, int cid) {
        String reflbs = "0.00\t0.00";
        try {
            PreparedStatement stmt = HibernateSession.getPreparedStatement("select CONVERT(varchar,ISNULL([min weight],0.00))+'\t'+CONVERT(varchar,ISNULL([max weight],0.00)) from ref_lbs (NOLOCK) where ID=? and Box=? and ClientID=?");
            stmt.setString(1, itemID);
            stmt.setString(2, boxName);
            stmt.setInt(3, cid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                reflbs = rs.getString(1);
            }
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return reflbs;

    }


    protected void _getCarrierData(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("We are getting the carrier Data. Yup Yup Yup");
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            StringBuffer results = new StringBuffer();

            cxn = ConnectionManager.getConnection();

            String sql = "select * from owd_ship_methods (NOLOCK) ";


            stmt = cxn.prepareStatement(sql);

            stmt.executeQuery();

            rs = stmt.getResultSet();

            while (rs.next()) {
                System.out.println(rs.getString(2) +", "+ rs.getString(3)+" , "+rs.getString(20));
                results.append(rs.getString(1));
                for (int i = 2; i < 23; i++) {
                    results.append("\t" + rs.getString(i));
                }
                results.append("\n");
            }

            sendRFSuccess(resp, results.toString());
            cxn.rollback();

        } catch (Throwable th) {

            try {
                cxn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }

    }

    protected void _getLocationData(HttpServletRequest req, HttpServletResponse resp) {

        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            StringBuffer results = new StringBuffer();

            cxn = ConnectionManager.getConnection();

            String sql = "select loc_name,loc_code,address,city,state,zip from owd_facilities (NOLOCK) ";


            stmt = cxn.prepareStatement(sql);

            stmt.executeQuery();

            rs = stmt.getResultSet();

            while (rs.next()) {

                results.append(rs.getString(1) + "\t" + rs.getString(2)+ "\t" + rs.getString(3)+ "\t" + rs.getString(4)+ "\t" + rs.getString(5) + "\t" + rs.getString(6)+ "\n");

            }

            sendRFSuccess(resp, results.toString());
            cxn.rollback();

        } catch (Throwable th) {

            try {
                cxn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }

    }


    protected void _saveShipment(HttpServletRequest req, HttpServletResponse resp) {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        TimeStamp ts = new TimeStamp("savepackage");

        String packBarcode = getStringParam(req, "package_barcode", "");

        try {
            cxn = ConnectionManager.getConnection();

//System.out.println("initializing pack: "+getStringParam(req,"order_fkey","")+":"+getStringParam(req,"line_index","")+":"+getStringParam(req,"msn",""));

            ts.print("savepackage-lo query start"  +packBarcode);
            System.out.println(":Insurance Cost:" + getStringParam(req, "ins_cost", "0.00"));
            System.out.println(":Insurance Value:" + getStringParam(req, "ins_value", "0.00"));
            System.out.println("MSN: "+getStringParam(req, "msn", ""));
            System.out.println("1");
System.out.println("Line index: "+getStringParam(req, "line_index", ""));
            System.out.println("Bundel Id: "+getStringParam(req, "bundle_id", "0"));
             //This saves the tracking into to the server
            com.owd.core.business.order.Package pack = new Package("0", getStringParam(req, "order_fkey", ""), getStringParam(req, "line_index", ""),
                    getStringParam(req, "tracking_no", ""), getStringParam(req, "weight", "0.00"), (getStringParam(req, "shipper", "").equals("ROW") ? "0.00" : getStringParam(req, "total_billed", "0.00")),
                    getStringParam(req, "cost_of_goods", "0.00"), "", getStringParam(req, "msn", ""), "0", "0", "", "", "", "",
                    getStringParam(req, "bundle_id", "0"),getStringParam(req, "carrier_code", ""),getStringParam(req, "service_code", ""),getStringParam(req, "label_code", ""));
            //System.out.println("initialized pack: "+getStringParam(req,"order_fkey","")+":"+getStringParam(req,"line_index","")+":"+getStringParam(req,"msn",""));

            System.out.println("2");

            //System.out.println("save pack: "+getStringParam(req,"order_fkey","")+":"+getStringParam(req,"line_index","")+":"+getStringParam(req,"msn",""));
            pack.dbsave(cxn, getStringParam(req, "user", "DC1"), getStringParam(req, "shipper", ""));
           ts.print("savepackage-lo pack.dbsave done " + packBarcode);
            double insuredAmount = 0.00;
            double insuranceCost = 0.00;

            try {
                insuredAmount = new Double(getStringParam(req, "ins_value", "0.00")).doubleValue();
            } catch (Exception ex) {

            }
            try {
                insuranceCost = new Double(getStringParam(req, "ins_cost", "0.00")).doubleValue();
            } catch (Exception ex) {

            }
            System.out.println("ordertrack" + pack.order_track_id);
            System.out.println("order_fkey" + getStringParam(req, "order_fkey", ""));
            System.out.println("msn" + getStringParam(req, "msn", ""));
            System.out.println("packBarcode" + packBarcode);
            if (packBarcode.length() > 0) {
                /*stmt = cxn.prepareStatement("update package set ship_time=getdate(),order_track_fkey=?, ip_address=?," +
                        "owd_insurance_cost=?,insured_amount=?,weight_verify=?,customs_docs=? " +
                        "where pack_barcode=? ");
                */
                   stmt = cxn.prepareStatement("exec sp_update_package_shipapp ?,?,?,?,?,?,?;");
                stmt.setInt(1, new Integer(pack.order_track_id).intValue());
                stmt.setString(2, getStringParam(req, "ip_address", ""));
                stmt.setDouble(3, insuranceCost);
                stmt.setDouble(4, insuredAmount);
                stmt.setString(5, getStringParam(req, "weight_notes", ""));
                stmt.setInt(6, getIntegerParam(req, "customs_docs", 0));
                stmt.setString(7, packBarcode);
                stmt.executeUpdate();
                   ts.print("savepackage-lo updateting package" + packBarcode);
                /*   stmt=cxn.prepareStatement("update package set owd_insurance_cost=?,insured_amount=? " +
                          "where pack_barcode=?");
                stmt.setDouble(1,insuranceCost);
stmt.setDouble(2,insuredAmount);
                stmt.setString(3,packBarcode);
                stmt.executeUpdate();*/


                stmt = cxn.prepareStatement("update package_order set packs_shipped=(packs_shipped+1) where owd_order_fkey=? and is_void=0");
                stmt.setInt(1, new Integer(getStringParam(req, "order_fkey", "")).intValue());
                stmt.executeUpdate();
                   ts.print("savepackage-lo updateting package_order " + packBarcode);

            } else {
                stmt = cxn.prepareStatement("update package set ship_time=getdate(),order_track_fkey=?, ip_address=?," +
                        "owd_insurance_cost=?,insured_amount=?,weight_verify=?,customs_docs=? " +
                        " from package\n" +
                        "join package_order po on po.id=package.package_order_fkey and po.is_void=0 and owd_order_fkey=?\n" +
                        "join owd_order_track t on t.order_track_id=order_track_fkey and msn=?");


                stmt.setInt(1, new Integer(pack.order_track_id).intValue());
                stmt.setString(2, getStringParam(req, "ip_address", ""));
                stmt.setDouble(3, insuranceCost);
                stmt.setDouble(4, insuredAmount);
                stmt.setString(5, getStringParam(req, "weight_notes", ""));
                stmt.setInt(6, getIntegerParam(req, "customs_docs", 0));

                stmt.setInt(7, new Integer(getStringParam(req, "order_fkey", "")).intValue());
                stmt.setString(8, getStringParam(req, "msn", ""));
                stmt.executeUpdate();
                    ts.print("savepackage-lo updateting package with barcode" + packBarcode);

                stmt = cxn.prepareStatement("update package_order set packs_shipped=(packs_shipped+1) where owd_order_fkey=? and is_void=0");
                stmt.setInt(1, new Integer(getStringParam(req, "order_fkey", "")).intValue());
                stmt.executeUpdate();


                   ts.print("savepackage-lo update backage_order with barcode " + packBarcode);

            }
             System.out.println(getStringParam(req, "ship_method", "SAME"));

                System.out.println(getStringParam(req, "ship_method_name", "SAME"));
                System.out.println(getStringParam(req, "ship_method_name_original", "SAME"));

                String shipMethod = getStringParam(req, "ship_method", "SAME");
                if (!(shipMethod.equals("SAME"))) {
                    String shipMethodName = getStringParam(req, "ship_method_name", "SAME");
                    String originalShipMethodName = getStringParam(req, "ship_method_name_original", "SAME");

                    Event.addOrderEvent(cxn,new Integer(getStringParam(req, "order_fkey", "")),Event.kEventTypeHandling,"Manifesting auto-switched ship method from "+originalShipMethodName+" to "+shipMethodName,"Manifesting Server");

                         ts.print("Added Event Auto Switch " + packBarcode);
                           String newShipName=null;
                    try
                    {
                        newShipName = (String) OrderRater.getRateableServicesMap().get(shipMethod);
                    }         catch(Exception exsn)
                    {
                        exsn.printStackTrace();
                    }
                    if(newShipName!=null)
                    {
                    stmt = cxn.prepareStatement("update owd_order_ship_info set carr_service=?, carr_service_ref_num=?" +
                            " where order_fkey=?");

                    stmt.setString(1,newShipName );
                     stmt.setString(2, shipMethod);
                    stmt.setInt(3, new Integer(getStringParam(req, "order_fkey", "")).intValue());
                    stmt.executeUpdate();
                    }

                }
            cxn.commit();
            ts.print("savepackage-lo insert done" + packBarcode);

            //todo look for weight verified order and not process this line
            System.out.println("WV??");
            //System.out.println(ABUtils.isWeightVerifiedPacked(getStringParam(req, "order_fkey", "")));
            if(!ABUtils.isWeightVerifiedPacked(getStringParam(req, "order_fkey", ""))) {
                System.out.println("We are updateing the order to shipped etc........");

            String esql = "exec update_order_shipment_info " + new Integer(getStringParam(req, "order_fkey", "")).intValue();

            Statement astmt = cxn.createStatement();

            astmt.executeUpdate(esql);

            //   astmt.executeUpdate("");


            cxn.commit();
            }
            ts.print("savepackage-lo update done"  +packBarcode);
            //getStringParam(req, "order_fkey", "")

            sendRFSuccess(resp, "");

        } catch (Throwable th) {

            try {
                cxn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }

    }

    protected void _voidShipment(HttpServletRequest req, HttpServletResponse resp) {

        try {

            String order_num = getStringParam(req, "order_num", "").trim();
            int order_id = getIntegerParam(req, "order_fkey", 0);
            String msn = getStringParam(req, "msn", "").trim();
            String packBarcode = getStringParam(req, "package_barcode", "").trim();

            int orderTrackId = 0;

            if(msn.length()>0 && order_id>0) {
                orderTrackId = Package.getOrderTrackIdFromMsnAndOrderId(msn,order_id);
            } else  if(msn.length()>0 && order_num.length()>0) {
                orderTrackId = Package.getOrderTrackIdFromMsnAndOrderNum(msn,order_num);
            }  else  if(msn.length()>0 && packBarcode.length()>0) {
                orderTrackId = Package.getOrderTrackIdFromMsnAndPackBarcode(msn,packBarcode);
            } else {
                throw new Exception("Package void requires MSN for an unvoided package and one of order id, OWD order reference or package barcode");
            }
            Package.voidPackageShipment(orderTrackId,"ShipperServlet");
            int orderId = Package.getOrderIdFromOrderTrack(orderTrackId);

            OrderUtilities.stopShipping(""+orderId);
            ABUtils.resetWVShipment(""+orderId);
            log.debug("pv5");

            HibUtils.commit(HibernateSession.currentSession());

            sendRFSuccess(resp, "");

        } catch (Throwable th) {

            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /*  protected void _isDPBetter(HttpServletRequest req, HttpServletResponse resp) {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            cxn = ConnectionManager.getConnection();

            String country_str = getStringParam(req, "country_name", "");
            String lbs_str = getStringParam(req, "weight_lbs", "");
            lbs_str = lbs_str.replace('?', ' ').trim();
            Float weight = null;
            try {
                weight = new Float(lbs_str);
            } catch (Exception e) {
                e.printStackTrace();
                sendRFError(resp, e.getMessage());
            }

            System.out.println("Is DP better? " + country_str + " " + lbs_str);

            stmt = cxn.prepareStatement("select dp_cost from dp_compare where iana_code = ? and pounds = (select min(pounds) from dp_compare where iana_code = ? and pounds >= ?)");
            stmt.setString(1, country_str);
            stmt.setString(2, country_str);
            stmt.setFloat(3, weight.floatValue());

            stmt.executeQuery();

            rs = stmt.getResultSet();

            if (rs.next()) {
                float dpAdvantage = rs.getFloat(1);
                if (dpAdvantage >= 0f) {
                    sendRFSuccess(resp, "DP\n");
                } else {
                    sendRFSuccess(resp, "USPS\n");
                }
            } else {
                sendRFSuccess(resp, "USPS\n");
            }


            cxn.rollback();

        } catch (Throwable th) {

            try {
                cxn.rollback();
            } catch (Exception ex) {
            }
            th.printStackTrace();
            try {
                sendRFError(resp, (th.getMessage().startsWith(".") ? th.getMessage().substring(1) : "Server error: " + th.getMessage()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
            }
            try {
                stmt.close();
            } catch (Exception ex) {
            }
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }
    }*/

    protected static String getStringParam(HttpServletRequest req, String paramName, String defaultValue) {
        String param = req.getParameter(paramName);

        if (param == null)
            param = defaultValue;

        return param;

    }

    protected static int getIntegerParam(HttpServletRequest req, String paramName, int defaultValue) {
        int param = 0;

        try {
            param = new Integer(req.getParameter(paramName)).intValue();
        } catch (Exception ex) {
            param = defaultValue;
        }

        return param;

    }

    public static void main(String[] args) {
        ShippingApplicationServlet servlet = new ShippingApplicationServlet();
        //   servlet._loadOrder3("4878721", null);

        try {
            // System.out.println(servlet.getReferenceWeights("3099050:1","CUSTOM",304));

            // System.out.println(OrderRater.getOldServiceCode("USPS First-Class Mail"));
            System.out.println(OrderRater.getNewServiceCode("OWD.1ST.PRIORITY"));
            System.out.println(OrderRater.getSafeServiceCode("OWD.1ST.PRIORITY"));

            // servlet._loadOrder3("p50777*5766952*B2", null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static final public String kServletName = "ShippingAppServlet";

    public String getServletInfo() {
        return "One World Shipping App Services";
    }

    private void sendRFSuccess(HttpServletResponse resp, String data) throws Exception {
        if (resp == null) {
            System.out.println("SUCCESS RESPONSE:" + data);
        } else {
            if (data == null) data = "";
            resp.getWriter().write("SUCCESS\r\n");
            resp.getWriter().write(data);
        }
    }

    private void sendRFError(HttpServletResponse resp, String err) throws Exception {

        if (resp == null) {
            System.out.println("ERROR RESPONSE:" + err);
        } else {
            if (err == null) err = "";

            resp.getWriter().write("ERROR\r\n");

            resp.getWriter().write(err);
        }
    }


}
