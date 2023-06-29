package com.owd.jobs.clients;

import com.owd.core.business.order.OrderFactory;
import com.owd.core.managers.ConnectionManager;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.jobs.OWDStatefulJob;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class LivingoodCheckoutChampOrderStatusUpdateJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    private final static String ftpHost = "ftp.owd.com";
    private final static String ftpUser = "livingood";
    private final static String ftpPass = "DLG2022ftp!";
    private final static int clientId = 652;
    private final static String fieldSeparator = ",";
    private final static String lineSeparator = System.getProperty("line.separator");

    @Override
    public void internalExecute() {
        sendOrderStatusFile();
    }

    public void sendOrderStatusFile() {
        List<String> orderIds = getOrdersShippedToday();

        if (!processShippedOrders(orderIds)) {
            log.error("Failed to process shipped orders for Checkout Champ");
        }
    }

    private List<String> getOrdersShippedToday() {
        List<String> orderIds = new ArrayList<>();

        String sql = "SELECT order_id FROM owd_order WHERE shipped_on >= CAST(GETDATE() AS DATE) AND client_fkey = 652 AND order_type = 'checkout_champ'";

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
                    String trackingNumber = p.tracking_no.contains(":") ? p.tracking_no.substring(p.tracking_no.indexOf(":") + 1) : p.tracking_no;
                    String shipMethod = p.shipMethod;
                    String carrier = p.carrierName;

                    sb.append(orderId).append(fieldSeparator);
                    sb.append(trackingNumber).append(fieldSeparator);
                    sb.append(carrier).append(fieldSeparator);
                    sb.append(shipMethod).append(lineSeparator);
                }

            } catch (Throwable th) {
                log.error(th.getMessage());
            }
        }

        return sb;
    }

    private String getFileHeaderLine() {
        return "orderId,trackingNumber,shipCarrier,shipMethod" + lineSeparator;
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
        String fileName = "livingood.csv";

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
