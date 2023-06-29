package com.owd.jobs.clients;

import com.owd.core.business.order.OrderFactory;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.generated.OrderShipInfo2;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.OWDStatefulJob;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomestifyDuoplaneOrderStatusUpdateJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    private final static String ftpHost = "ftp.owd.com";
    private final static String ftpUser = "domestify";
    private final static String ftpPass = "dmstfy2021";
    private final static int clientId = 682;
    private final static String fieldSeparator = ",";
    private final static String lineSeparator = System.getProperty("line.separator");

    @Override
    public void internalExecute() {
        sendDuoplaneOrderStatusFile();
    }

    public void sendDuoplaneOrderStatusFile() {
        List<String> orderIds = getOrdersShippedToday();

        if (!processShippedOrders(orderIds)) {
            log.error("Failed to process shipped orders for Duoplane");
        }
    }

    private List<String> getOrdersShippedToday() {
        List<String> orderIds = new ArrayList();

        String sql = "SELECT order_id FROM owd_order WHERE shipped_on >= CAST(GETDATE() AS DATE) AND client_fkey = 682 AND order_type = 'Duoplane'";

        try {
            java.sql.Connection conn = ConnectionManager.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.executeQuery();
            java.sql.ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                orderIds.add(rs.getString(1));
            }
        } catch (Throwable th) {
            log.error(th.getMessage());
        }

        return orderIds;
    }

    // TODO: Reduce time complexity - currently On2
    private StringBuilder generateUpdateString(List<String> orderIds) {
        StringBuilder sb = new StringBuilder(getFileHeaderLine());

        // TODO: Loop through orderIds

        for (String orderId : orderIds) {
            try {
                OwdOrder order = OrderFactory.getOwdOrderFromOrderID(Integer.parseInt(orderId), clientId, true);

                // TODO: Get order packages
                for (com.owd.core.business.order.Package p : com.owd.core.business.order.Package.getPackagesForOrderId(Integer.parseInt(orderId))) {
                    String serialCode = com.owd.core.business.order.Package.getSSCCCodeForPackage(p.order_track_id);
                    String trackingNumber = p.tracking_no.contains(":") ? p.tracking_no.substring(p.tracking_no.indexOf(":") + 1) : p.tracking_no;
                    String clientRef = order.getOrderRefnum();
                    String purchaseOrderNumber = order.getPoNum();
                    String vendorTaxCost = "";
                    String sendTrackingNotice = "";

                    // TODO: Loop through items in package
                    for (Map item : com.owd.core.business.order.Package.getEDILineItemsForPackage(p.order_track_id,true)) {
                        String upc = (String) item.get("UPC");
                        String itemId = (String) item.get("SKU");
                        String shipMethod = p.shipMethod;

                        sb.append(clientRef + fieldSeparator);
                        sb.append(shipMethod + fieldSeparator);
                        sb.append(trackingNumber + fieldSeparator);
                        sb.append(vendorTaxCost + fieldSeparator);
                        sb.append(purchaseOrderNumber + fieldSeparator);
                        sb.append(itemId + fieldSeparator);
                        sb.append(upc + fieldSeparator);
                        sb.append(serialCode + fieldSeparator);
                        sb.append(sendTrackingNotice + lineSeparator);
                    }
                }

            } catch (Throwable th) {
                log.error(th.getMessage());
            }
        }

        return sb;
    }

    private String getFileHeaderLine() {
        return "Client Reference,Carrier Ship Method,Tracking Number,vendor_tax_cost,purchase_order_id,order_item_id,upc,serial_code_list,send_tracking_notice" + lineSeparator;
    }

    private boolean processShippedOrders(List<String> orderIds) {
        try {
            StringBuilder updateString = generateUpdateString(orderIds);
            sendData(updateString);
        } catch (Throwable th) {
            return false;
        }

        return true;

    }

    private void sendData(StringBuilder sb) {
        FTPClient client = new FTPClient();
        String fileName = "domestify.csv";

        try {
            client.connect(ftpHost);
            client.login(ftpUser, ftpPass);
            client.enterLocalPassiveMode();
            client.changeWorkingDirectory("files/shipments");

            System.out.println(client.storeFile(fileName, new ByteArrayInputStream(sb.toString().getBytes())));
            System.out.println(client.getReplyCode());
            System.out.println(client.getReplyString());
            client.logout();

        } catch (Throwable th) {

        }

    }
}
