package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.jobs.OWDStatefulJob;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.util.Calendar;

public class DomestifyDuoplaneInventoryUpdateJob  extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    private final static String ftpHost = "ftp.owd.com";
    private final static String ftpUser = "domestify";
    private final static String ftpPass = "dmstfy2021";
    private final static int clientId = 682;
    private final static String fieldSeparator = "|";
    private final static String lineSeparator = System.getProperty("line.separator");

    @Override
    public void internalExecute(){
        StringBuilder sb = getInventoryCounts();
        sendData(sb);
    }

    private StringBuilder getInventoryCounts() {
        StringBuilder sb = new StringBuilder(getFileHeaderLine());
        String sql = "SELECT i.inventory_num, o.qty_on_hand FROM owd_inventory i\n" +
                "JOIN owd_inventory_oh o\n" +
                "\tON i.inventory_id = o.inventory_fkey\n" +
                "WHERE i.client_fkey = 682";

        try {
            java.sql.Connection conn = ConnectionManager.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.executeQuery();
            java.sql.ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                sb.append(rs.getString(1) + fieldSeparator + rs.getString(2) + lineSeparator);
            }
        } catch (Exception ex) {
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:" + Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    this.getClass().getName(),
                    LogableException.errorTypes.INVENTORY);
        }

        return sb;
    }

    private String getFileHeaderLine()
    {
        return "SKU|qty" + lineSeparator;
    }

    private void sendData(StringBuilder sb) {
        FTPClient client = new FTPClient();
        String fileName = "domestify.csv";

        try {
            client.connect(ftpHost);
            client.login(ftpUser, ftpPass);
            client.enterLocalPassiveMode();
            client.changeWorkingDirectory("files/inventory");

            System.out.println(client.storeFile(fileName, new ByteArrayInputStream(sb.toString().getBytes())));
            System.out.println(client.getReplyCode());
            System.out.println(client.getReplyString());
            client.logout();

        } catch (Throwable th) {

        }
    }
}
