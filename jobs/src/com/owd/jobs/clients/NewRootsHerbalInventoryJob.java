package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.SftpConnector;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewRootsHerbalInventoryJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();

    private final static String sftpHost = "sftp.channeladvisor.com";
    private final static String sftpUser = "newrootssftpuser";
    private final static String sftpPass = "Wf$46*@ghd15";
    private final static String sftpEncryption = "aes128-ctr,aes128-cbc";
    private final static String remoteFolder = "/accounts/12045686/Inventory/Transform";
    private final static int clientId = 718;
    private final static String fieldSeparator = ",";
    private final static String lineSeparator = System.getProperty("line.separator");
    private final static String DateFormatNow = "yyyyMMddHHmmss";

    @Override
    public void internalExecute(){
        StringBuilder sb = getInventoryCounts();
        sendData(sb);
    }

    private StringBuilder getInventoryCounts() {
        StringBuilder sb = new StringBuilder(getFileHeaderLine());
        String sql = "SELECT \n" +
                "\ti.inventory_num, \n" +
                "\t[amazon_sku] = ji.jcpref,\n" +
                "\to.qty_on_hand \n" +
                "FROM owd_inventory i\n" +
                "JOIN owd_inventory_oh o\n" +
                "\tON i.inventory_id = o.inventory_fkey\n" +
                "JOIN jcpenney_itemreference ji \n" +
                "\tON i.inventory_num = ji.owdsku\n" +
                "WHERE i.client_fkey =" + clientId;

        try {
            java.sql.Connection conn = ConnectionManager.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.executeQuery();
            java.sql.ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                sb.append(rs.getString(1) + fieldSeparator + rs.getString(2) + fieldSeparator + rs.getString(3) + lineSeparator);
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
        return "SKU,VendorSKU,Quantity" + lineSeparator;
    }

    private void sendData(StringBuilder sb) {


        String fileName = "nrh_inv_" + new SimpleDateFormat(DateFormatNow).format(new Date()) + ".csv";

        try {
            SftpConnector connector = new SftpConnector(sftpHost, sftpUser, sftpPass, remoteFolder, sftpEncryption);
            connector.putFileData(fileName, sb.toString().getBytes());

        } catch (Throwable th) {
            log.error(th.getMessage());
        }
    }
}
