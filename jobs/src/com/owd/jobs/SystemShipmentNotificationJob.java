
package com.owd.jobs;

import com.owd.core.*;
import com.owd.core.business.order.OrderUtilities;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.jobobjects.clients.StanleyBlackAndDeckerService;
import com.owd.jobs.jobobjects.commercehub.ClientVendorSetups.*;
import com.owd.jobs.jobobjects.rakuten.MarineEssentialsIntegration;
import com.owd.jobs.jobobjects.rakuten.RakutenOrderProcessor;
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient;
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceHVMNAmazonUtilities;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.*;
import com.owd.jobs.clients.*;
import com.owd.jobs.jobobjects.amazon.AmazonAPI;
import com.owd.jobs.jobobjects.amazon.AmazonConfig;
import com.owd.jobs.jobobjects.bigcommerce.BigCommerceAPI;
import com.owd.jobs.jobobjects.clients.OriliService;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;
import com.owd.jobs.jobobjects.retailops.RetailOpsApi;
import com.owd.jobs.jobobjects.sears.SearsApi;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP;
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities;
import com.owd.jobs.jobobjects.woocommerce.WooCommerceAPI;

import java.io.BufferedReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 30, 2004
 * Time: 4:14:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class SystemShipmentNotificationJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();
    private final OriliService oriliService = new OriliService();

    public SystemShipmentNotificationJob() { }

    static String sweepSQL = "EXECUTE getShipNoticeOrders";

    void sendMail(Integer orderID, Map<AmazonConfig, List<Integer>> amazonShipments) {
        Connection cxn = null;
        Statement stmt = null;
        Statement astmt = null;
        ResultSet rs = null;
        String orderReference;
        String carrier;
        String tracker;
        String clientID;
        String source;

        boolean gotMessage = false;

        if (orderID != null) {
            try {
                cxn = ConnectionManager.getConnection();
                stmt = cxn.createStatement();
                stmt.execute("EXECUTE getOrderShipNoticeInfo " + orderID);
                rs = stmt.getResultSet();

                if (rs.next()) {
                    orderReference = rs.getString(1).trim();
                    carrier = rs.getString(2);
                    tracker = rs.getString(3);
                    clientID = rs.getString(4);
                    source = rs.getString(5);

                    rs.close();
                    stmt.close();

                    if (tracker.length() < 10) {
                        tracker = "";
                    }

                    Client client = Client.getClientForID(clientID);
                    log.debug("" + clientID);
                    gotMessage = false;

                    if (client.client_id.equals("477")) {
                        // Orili

                        try {
                            oriliService.sendOrderShipmentNotification(orderID);

                            gotMessage = true;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else if (client.client_id.equals("632")) {
                        // Stanley - Black & Decker

                        try {
                            gotMessage = StanleyBlackAndDeckerService.postStanleyTrackingAndSerials(orderID+"");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else if (client.client_id.equals("611")) {
                        // BioScience

                        log.debug("611 ck " + source);

                        if (source.toUpperCase().contains("SHOPIFY")&&!source.toUpperCase().contains("DRAFT"))
                        {
                            try {
                                ShopifyAPI api = new ShopifyAPI(
                                        "10b4dfd80ae1dd97ee93e7058bff7896",
                                        "4aee7720383d44541c547cdbb8c72c6c",
                                        "astamatrix.myshopify.com","33574162");

                                gotMessage =  api.reportShipment(orderID, tracker, true);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }else{
                            gotMessage = true;
                        }
                    } else if (client.client_id.equals("575")) {
                         // Bumbleride

                         log.debug("575 edi ck " + source);

                         if (source.toUpperCase().contains(":EDI:"))
                         {
                             try {
                                 SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();
                                 String asnData = SpsCommerceUtilities.generateASN(orderID, 575);
                                 TagUtilities.setOrderTag(orderID, TagUtilities.kEDIAsn, asnData);

                                 if(asnData.contains("7F6FWSBUMBLSYMP")||asnData.contains("080FWSBUMBLSYMP")){
                                     ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.getBytes(), SPSCommerceRemoteFTP.FolderPath.sendDirSymPath);

                                 }else{
                                     if(asnData.contains("61YFWDBUMBLONEW")||asnData.contains("APOFWDBUMBLERID")){
                                         SPSCommerceBaseClient.submitASN(orderID, 575);
                                     }else {
                                         ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.getBytes(), SPSCommerceRemoteFTP.FolderPath.sendDirPath);
                                     }
                                 }

                                 gotMessage = true;
                             } catch (Exception ex) {
                                 ex.printStackTrace();
                             }
                         } else {
                             gotMessage = true;
                         }
                     } else if (client.client_id.equals("378")) {
                        // RIE

                        log.debug("378 ck " + source);

                        try {
                            WooCommerceAPI api = new WooCommerceAPI(
                                    378,
                                    "https://www.rie.org/",
                                    "ck_cdec9a83766179c263bfd83ad1215038",
                                    "cs_f5ce034019301f2d8266776e9e5a6d8c",
                                    "2"
                            );
                            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderID);

                            try {
                                api.markOrderShipped(Integer.parseInt(order.getPoNum()));
                            } catch (NumberFormatException n) {
                                log.error("Could not get PO number for order");
                            } finally {
                                gotMessage = true;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else if (client.client_id.equals("494")) {
                        // Nugenics

                        log.debug("494 ck " + source);

                        if (source.toUpperCase().contains("AMAZON"))
                        {
                            try {
                                addOrderToAmazonShipments(amazonShipments, NugenicsAmazonDownloader.configList.get(source.toUpperCase()), orderID);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else if (source.toUpperCase().contains("RAKUTEN")) {
                            try {
                                OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderID);
                                RakutenOrderProcessor rdp = MarineEssentialsIntegration.getRakutenOrderProcessor();
                                rdp.reportShipment(order);

                                gotMessage = true;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else if (source.toUpperCase().contains("SEARS")) {
                            try {
                                OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, orderID);
                                SearsApi api = new SearsApi("494", "kathleen@marined3.com", "Vic321");
                                api.reportShipment(order);

                                gotMessage = true;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else if (source.toUpperCase().contains("EBAY")) {
                            try {
                                gotMessage = sendMarineEssentialsEBayShipmentConfirmation(orderID, orderReference, carrier, source);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else if (source.toUpperCase().contains("BIGCOMMERCE")) {
                            try {
                                OrderStatus os = new OrderStatus("" + orderID);
                                BigCommerceAPI api = new BigCommerceAPI(
                                        "owd",
                                        "db007c2892cf915eda6e566792bbeff879a04ec0",
                                        "https://shopping.marineessentials.com/api/v2/",
                                        "BC"
                                );

                                int orderId = Integer.parseInt(os.orderReference.replaceAll("BC", ""));
                                int addId = Integer.parseInt(os.po_num);
                                api.reportShipment(orderId, addId, tracker, os.shipping.carr_service, os.items);

                                gotMessage = true;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            gotMessage = true;
                        }
                    }  else if (client.client_id.equals("266")) {
                        // AlgaeCal

                        log.debug("266 ck " + source);

                        if (source.toUpperCase().contains("AMAZONCC")) {
                            try {
                                addOrderToAmazonShipments(amazonShipments, AlgaeCalBrazilAmazonDownloader.configList.get("AMAZONCC"), orderID);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            if (source.toUpperCase().contains("AMAZON")) {
                                try {
                                    addOrderToAmazonShipments(amazonShipments, AlgaeCalAmazonDownloader.config, orderID);

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                gotMessage = true;
                            }
                        }
                    } else if (client.client_id.equals("634")) {
                        // Dogeared

                        if (source.toUpperCase().contains(":EDI:")) {
                            gotMessage = SPSCommerceBaseClient.submitASN(orderID, 634);
                        } else {
                            gotMessage = true;
                        }
                    } else if(client.client_id.equals("651")){
                        // Lawless  Beauty

                         if (source.toUpperCase().contains(":EDI:")) {
                             gotMessage = SPSCommerceBaseClient.submitASN(orderID, 651);
                         } else if(source.equalsIgnoreCase("QVC")){
                             LawlessQVCCommerceHubAPI api = new LawlessQVCCommerceHubAPI();
                             api.configure("lawless.sftp.commercehub.com", "lawless", "Effectively38utton9Officer6$", "qvc", "Lawless", "iqvc", 651);
                             OrderStatus os = new OrderStatus(orderID+"");
                             api.confirmOrderShipment(os);

                             gotMessage = true;
                         } else {
                             gotMessage = true;
                         }
                     } else if (client.client_id.equals("667")) {
                        // ROVR

                         if(source.toUpperCase().contains(":EDI:")){
                             gotMessage =   SPSCommerceBaseClient.submitASN(orderID, 667);
                         } else {
                             gotMessage = true;
                         }
                    } else if (client.client_id.equals("680")) {
                        // Blue Tees Golf

                        if (source.toUpperCase().contains(":EDI:")) {
                            gotMessage =   SPSCommerceBaseClient.submitASN(orderID, 680);
                        } else {
                            gotMessage = true;
                        }

                    } else if (client.client_id.equals("626")) {
                        //JustBrands

                         if (source.equalsIgnoreCase("RETAILOPS")) {
                             RetailOpsApi api  = new RetailOpsApi(626, "1975", "u9OLdvwM-Nulz3XwDKWn7yqY1XRxL3HEzZ0yQ2LTH");

                             gotMessage = (!(api.reportShipment(new OrderStatus(orderID + "")).contains("ERROR")));
                         } else if(source.toUpperCase().contains(":EDI:")) {
                           gotMessage =   SPSCommerceBaseClient.submitASN(orderID, 626);
                         } else if(source.equalsIgnoreCase("DSG")) {
                             JustBrandsDicksCommerceHubAPI api = new JustBrandsDicksCommerceHubAPI();
                             api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "dsg", "Just Brand Limited", "dsg", 626);
                             OrderStatus os = new OrderStatus(orderID+"");
                             api.confirmOrderShipment(os);

                             gotMessage = true;
                         } else if (source.equalsIgnoreCase("THEHOMEDEPOT")) {
                             JustBrandsHomeDepotCommerceHubAPI api = new JustBrandsHomeDepotCommerceHubAPI();
                             api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "thehomedepot", "Just Brand Limited", "thehomedepot", 626);
                             OrderStatus os = new OrderStatus(orderID+"");
                             api.confirmOrderShipment(os);

                             gotMessage = true;
                         } else if (source.equalsIgnoreCase("BEDBATH")) {
                             JustBrandsBedBathCommerceHubAPI api = new JustBrandsBedBathCommerceHubAPI();
                             api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "bedbath", "Just Brand Limited", "bedbath", 626);
                             OrderStatus os = new OrderStatus(orderID+"");
                             api.confirmOrderShipment(os);

                             gotMessage = true;
                         } else if (source.equalsIgnoreCase("MACYS")) {
                             JustBrandsMacysCommerceHubAPI api = new JustBrandsMacysCommerceHubAPI();
                             api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "macys", "Just Brand Limited", "macys", 626);
                             OrderStatus os = new OrderStatus(orderID+"");
                             api.confirmOrderShipment(os);

                             gotMessage = true;
                         } else if (source.equalsIgnoreCase("BLUESTEM")) {
                             JustBrandsBlueStemCommerceHubAPI api = new  JustBrandsBlueStemCommerceHubAPI ();
                             api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "bluestem", "Just Brand Limited", "bluestem", 626);
                             OrderStatus os = new OrderStatus(orderID+"");
                             api.confirmOrderShipment(os);

                             gotMessage = true;
                         } else if (source.equalsIgnoreCase("QVC")) {
                             JustBrandsQVCCommerceHubAPI api = new JustBrandsQVCCommerceHubAPI();
                             api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "qvc", "Just Brand Limited", "qvc", 626);
                             OrderStatus os = new OrderStatus(orderID+"");
                             api.confirmOrderShipment(os);

                             gotMessage = true;
                         } else if (source.equalsIgnoreCase("WALMART")) {
                             JustBrandsWalmartCommerceHubAPI api = new JustBrandsWalmartCommerceHubAPI();
                             api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "WALMART", "Just Brand Limited", "WALMART", 626);
                             OrderStatus os = new OrderStatus(orderID+"");
                             api.confirmOrderShipment(os);

                             gotMessage = true;
                         } else if (source.equalsIgnoreCase("LOWES")) {
                            JustBrandsLowesCommerceHubAPI api = new JustBrandsLowesCommerceHubAPI();
                            api.configure("justbrand.sftp.commercehub.com", "justbrand", "Terribly#Forev3r!Panic6", "lowes", "Just Brand Limited", "lowes", 626);
                            OrderStatus os = new OrderStatus(orderID+"");
                            api.confirmOrderShipment(os);

                            gotMessage = true;
                         } else {
                             gotMessage = true;
                         }
                     } else if (client.client_id.equals("179")) {
                        // Phion

                        if (source.toUpperCase().contains("MAGENTO")) {
                            try {
                                MagentoRemoteService service = new MagentoRemoteService(
                                        "https://phionbalance.com/api",
                                        "oneworld",
                                        "oneworld123",
                                        179);
                                OrderStatus os = new OrderStatus(orderID + "");

                                gotMessage = service.testOrderShippedWithItems(orderReference, tracker, carrier, os.items);
                            } catch (Exception ex) {
                                if (ex.getMessage().contains("Can not do shipment")) {

                                    gotMessage = true;
                                } else {
                                    ex.printStackTrace();
                                }
                            }
                        } else {
                            gotMessage = true;
                        }
                    } else if (client.client_id.equals("382")) {
                        // OWS

                        OrderStatus os = new OrderStatus("" + orderID);
                        log.debug("382 reporting " + os.orderReference);

                        if ("BIGCOMMERCE".equalsIgnoreCase(os.order_type)) {
                            if (os.orderReference.startsWith("BCJ")) {
                                BigCommerceAPI api = new BigCommerceAPI(
                                        "OneWorldDirect",
                                        "3e7c87626e46898a10c93cb9c96d5020fc51cb05",
                                        "https://store-d8r4sih.mybigcommerce.com/api/v2/", "BCJ"
                                );

                                try {
                                    int orderId = Integer.parseInt(os.orderReference.replaceAll("BCJ", ""));
                                    int addId = Integer.parseInt(os.po_num);
                                    api.reportShipment(orderId, addId, tracker, os.shipping.carr_service, os.items);

                                    gotMessage = true;
                                } catch (Exception ex) {
                                    // ignore
                                }
                            } else {
                                BigCommerceAPI api = new BigCommerceAPI(
                                        "admin",
                                        "52e9c1961343ce275d1d4ac6be86b58c43de7ded",
                                        "https://www.choppertown.com/api/v2/",
                                        "BC"
                                );

                                try {
                                    int orderId = Integer.parseInt(os.orderReference.replaceAll("BC", ""));
                                    int addId = Integer.parseInt(os.po_num);

                                    api.reportShipment(orderId, addId, tracker, os.shipping.carr_service, os.items);
                                } catch (Exception ex) {
                                    //ignore
                                }

                                gotMessage = true;
                            }
                        } else {
                            String sql = "SELECT backorder_order_num FROM owd_order WHERE order_id = " + orderID;
                            stmt = cxn.createStatement();
                            stmt.execute(sql);
                            rs = stmt.getResultSet();

                            if (rs.next()) {
                                String number = rs.getString(1);
                                if (null == number) {
                                    gotMessage = sendOneWorldStudiosShipmentConfirmation(orderID, orderReference);
                                } else {
                                    gotMessage = true;
                                }
                            }
                        }
                    } else if (client.client_id.equals("603")) {
                        // Freely

                         OrderStatus os = new OrderStatus("" + orderID);
                         log.debug("603 reporting " + os.orderReference);

                         if ("BIGCOMMERCE".equalsIgnoreCase(os.order_type)) {
                             if (os.orderReference.startsWith("YPIC")) {
                                 BigCommerceAPI api = new BigCommerceAPI(
                                         "OWD",
                                         "86e1eefde2b027c1e2e283f645357615f157abbe",
                                         "https://store-ryvwuu0w08.mybigcommerce.com/api/v2/",
                                         "YPIC"
                                 );

                                 try {
                                     int orderId = Integer.parseInt(os.orderReference.replaceAll("YPIC", ""));
                                     int addId = Integer.parseInt(os.po_num);
                                     api.getEndpoint().ignoreSSLIssues();
                                     api.reportShipment(orderId, addId, tracker, os.shipping.carr_service, os.items);

                                     gotMessage = true;
                                 } catch (Exception ex) {
                                     //ignore
                                 }
                             }
                         }
                    } else if (client.client_id.equals("576")) {
                        // HVMN

                         OrderStatus os = new OrderStatus(""+orderID);

                         if (":EDI:".equalsIgnoreCase(os.order_type)) {
                             try {
                                 SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();
                                 //update tracking /package info for proper reporting
                                 OrderUtilities.checkLTLAndUpdateTrackingAndPackages(orderID+"");

                                 String asnData = SpsCommerceHVMNAmazonUtilities.generateASN(orderID, 576);
                                 TagUtilities.setOrderTag(orderID,TagUtilities.kEDIAsn,asnData);

                                 ftp.putDataFile(SPSCommerceRemoteFTP.fileType.SH, asnData.getBytes(), SPSCommerceRemoteFTP.FolderPath.sendDirPath);

                                 gotMessage = true;
                             } catch (Exception ex) {
                                 ex.printStackTrace();
                             }
                         }else{
                             gotMessage = true;
                         }
                     } else if (client.client_id.equals("528")) {
                        // PM Concepts

                        if (source.toUpperCase().contains("MAGENTO")) {
                            try {
                                MagentoRemoteService service = new MagentoRemoteService(
                                        "https://amatalife.com/index.php/api/"
                                        , "oneworld"
                                        , "oneworld123"
                                        , 528);
                                OrderStatus os = new OrderStatus(orderID + "");

                                gotMessage = service.testOrderShippedWithItems(orderReference, tracker, carrier, os.items);
                            } catch (Exception ex) {
                                if (ex.getMessage().contains("Can not do shipment")) {
                                    log.debug("Got bad PMConcepts Magento order shipped update - clearing report flag");

                                    gotMessage = true;
                                } else {
                                    ex.printStackTrace();
                                }
                            }
                        } else {
                            gotMessage = true;
                        }
                    } else if (client.client_id.equals("696")) {
                         if (source.equalsIgnoreCase("macys")) {
                             BeautyCropMacysCommerceHubAPI api = new BeautyCropMacysCommerceHubAPI();
                             api.configure("cropbeauty.sftp.commercehub.com", "cropbeauty", "High1y2Singer#Stable!", "macys", "BeautyCrop", "macys", 696);
                             OrderStatus os = new OrderStatus(orderID+"");
                             api.confirmOrderShipment(os);

                             gotMessage = true;
                         } else {
                             gotMessage = true;
                         }
                    } else if (client.client_id.equals("636")) {
                        if (source.equalsIgnoreCase("macys")) {
                            SokoMacysCommerceHubAPI api = new SokoMacysCommerceHubAPI();
                            api.configure("soko.sftp.commercehub.com", "soko", "Proper0Fuel3Organize$", "macys", "Soko", "macys", 636);
                            OrderStatus os = new OrderStatus(orderID+"");
                            api.confirmOrderShipment(os);

                            gotMessage = true;
                        } else {
                            gotMessage = true;
                        }
                    } else if (client.client_id.equals("709")) {
                        if (source.equalsIgnoreCase("Netsuite") || source.equalsIgnoreCase("Lord and Taylor") || source.equalsIgnoreCase(":EDI:")) {
                            gotMessage = SPSCommerceBaseClient.submitASN(orderID, 709);
                        } else {
                            gotMessage = true;
                        }
                    } else if (client.client_id.equals("710")) {
                        if (source.equalsIgnoreCase("Popular Holdings")) {
                            gotMessage = SPSCommerceBaseClient.submitASN(orderID, 710);
                        } else {
                            gotMessage = true;
                        }
                    } else if (client.client_id.equals("722")) {
                        // Bread Beauty
                        OrderStatus os = new OrderStatus(orderID+"");

                        if (os.shipping.carr_service.equalsIgnoreCase("LTL")) {
                            sendBreadBeautyLtlShipmentConfirmation(os.orderReference);
                        }

                        gotMessage = true;

                    } else {
                        gotMessage = true;
                    }
                }

                if (gotMessage) {
                    stmt = cxn.createStatement();
                    int rowsUpdated = stmt.executeUpdate("UPDATE owd_order_track SET shipnotice_sent = 1 WHERE order_fkey = " + orderID);

                    if (rowsUpdated > 0) {
                        cxn.commit();
                    } else {
                        cxn.rollback();
                    }
                } else {
                    cxn.rollback();
                }
            } catch (Exception ex) {
                try {
                    cxn.rollback();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

                Mailer.postMailMessage("ShipNotify err", ex.getMessage() + "\n\n\n" + OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "noop@owd.com");
                ex.printStackTrace();
            } finally {
                try {
                    rs.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
                try {
                    stmt.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
                try {
                    astmt.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
                try {
                    cxn.close();
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            }
        }
    }

    public void internalExecute() {
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            cxn = ConnectionManager.getConnection();
            Vector<Integer> ids = new Vector<>();
            stmt = cxn.createStatement();
            log.debug("sweeping...");
            stmt.execute(sweepSQL);
            rs = stmt.getResultSet();
            if (rs != null) {
                while (rs.next()) {
                    log.debug("got id " + rs.getInt(1));
                    ids.addElement(rs.getInt(1));
                }
            }

            log.debug("sweep done");
            rs.close();
            stmt.close();
            cxn.rollback();

            Map<AmazonConfig, List<Integer>> amazonShipments = new TreeMap<>();

            for (int i = 0; i < ids.size(); i++) {
                log.debug("sending..." + ids.elementAt(i));
                sendMail(ids.elementAt(i), amazonShipments);
            }
            cxn.commit();

            if (amazonShipments.keySet().size() > 0) {
                for (AmazonConfig config : amazonShipments.keySet()) {
                    try {

                        List<AmazonAPI.AmazonOrderShipmentInfo> shipList = new ArrayList<>();
                        int clientid = 0;

                        for (Integer oid : amazonShipments.get(config)) {
                            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, oid);
                            clientid = order.getClientFkey();
                            AmazonAPI.AmazonOrderShipmentInfo info = new AmazonAPI.AmazonOrderShipmentInfo();
                            info.setAmazonOrderRef(order.getOrderRefnum());
                            info.setShipMethod(order.getShipinfo().getCarrService());
                            info.setTracking(order.getTrackingNums());
                            info.setShippedLines(new ArrayList<>(order.getLineitems()));
                            log.debug(info);
                            shipList.add(info);
                        }
                        if (shipList.size() > 0) {
                            AmazonAPI api = new AmazonAPI(config, clientid);
                            api.markOrdersShipped(shipList);
                            for (Integer oid : amazonShipments.get(config)) {
                                stmt = cxn.createStatement();

                                int rowsUpdated = stmt.executeUpdate("UPDATE owd_order_track SET shipnotice_sent = 1 WHERE order_fkey = " + oid);

                                if (rowsUpdated > 0) {
                                    cxn.commit();

                                } else {
                                    cxn.rollback();
                                }
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            log.debug("Updating OM");
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
            try {
                stmt.close();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
            try {
                cxn.close();
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // run();
        try{
            RetailOpsApi tester = new RetailOpsApi(626, "1975", "u9OLdvwM-Nulz3XwDKWn7yqY1XRxL3HEzZ0yQ2LTH");
            tester.reportShipment(new OrderStatus(("21884242")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static boolean sendMarineEssentialsEBayShipmentConfirmation(int orderId, String owdEbayOrderReference, String shipmethod, String orderType) {
        return MarineEssentialsEBayImportJob.postTrackingInformation(orderId, owdEbayOrderReference, shipmethod, orderType);
    }

    static boolean sendOneWorldStudiosShipmentConfirmation(Integer orderID, String orderReference) //returns true if OK to clear shipment notification flag
    {
        StringBuilder sb = new StringBuilder();

        try {
            WebResource server = new WebResource("http://www.choppertown.com/capoeira/owd_ship_notice.php", WebResource.kPOSTMethod);
            server.addParameter("orderNum", orderReference);
            server.addParameter("passwd", "zU1leeS1VW2hpOwWX/K2TQM=");

            //log.debug("updating One World Studios order");
            BufferedReader response = server.getResource();

            if (response == null) {
                sb.append("Got null response buffer from server");
                throw new Exception();
            }
            int line;
            line = response.read();

            while (line != -1) {
                sb.append((char) line);
                line = response.read();
            }

            log.debug(sb.toString());

            if (sb.toString().toUpperCase().contains("UPDATE") || sb.toString().toUpperCase().contains("NOT NUMERIC")) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Mailer.postMailMessage("One World Studios Importer import error", sb.toString(), "ejackman@owd.com", "ejackman@owd.com");

            if (ex.getMessage().indexOf("Cannot insert duplicate key") > 0) {
                return true;
            }
            if (ex instanceof NumberFormatException) {
                return true;
            }
        }

        return false;
    }

    static void addOrderToAmazonShipments(Map<AmazonConfig, List<Integer>> aships, AmazonConfig config, Integer orderID) {
        if (!(aships.containsKey(config))) {
            aships.put(config, new ArrayList<>());
        }

        aships.get(config).add(orderID);
    }

    static void sendBreadBeautyLtlShipmentConfirmation(String orderNumber) {
        Vector emailsx = new Vector();
        emailsx.add("mrhoades@owd.com");
        emailsx.add("matthew.e.rhoades@gmail.com");

        try {
            Mailer.postMailMessage("LTL Shipment Notification", "The following order has shipped LTL: \n\n" + orderNumber,  emailsx, "noop@owd.com");
        } catch (MailException me) {
            me.printStackTrace();
        }
    }
}