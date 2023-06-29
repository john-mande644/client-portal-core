package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.managers.ConnectionManager;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.pgp.LyftPgp;
import com.owd.jobs.jobobjects.pgp.PgpUtil;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.InMemorySourceFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;

import java.io.*;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by stewartbuskirk1 on 1/4/16.
 * Updated per Lyft requirements 0/19/21.
 */
public class LyftDailyShippedSpreadsheetJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();

    private final static String ftpHost = "ftp.owd.com";
    private final static String ftpUser = "lyftreports";
    private final static String ftpPass = "lyftFTP!!";
    private final static String fieldSeparator = ",";
    private final static String lineSeparator = System.getProperty("line.separator");

    public static void main(String[] args) {
        run();
    }

    public void internalExecute() {

        try {
            Security.addProvider(new BouncyCastleProvider());
            String transactions = getInventoryTransactions();

            if (!sendTransactionData(transactions)) {
                log.error("Unable to upload transaction data.");

                return;
            }
        } catch (Exception ex) {
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:"+Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    this.getClass().getName(),
                    LogableException.errorTypes.INVENTORY);
        }
    }

    private String getInventoryTransactions() {
        StringBuilder sb = new StringBuilder(getFileHeaderLine());
        StringBuilder sbSql = new StringBuilder("SELECT \n" +
                "                o.shipped_on,\n" +
                "                o.order_num,\n" +
                "                i.inventory_num, \n" +
                "                '\"' + i.[description] + '\"',\n" +
                "                l.quantity_actual,\n" +
                "                i.price,\n" +
                "                i.price * l.quantity_actual\n" +
                "                FROM owd_order o\n" +
                "                JOIN owd_line_item l\n" +
                "                ON o.order_id = l.order_fkey\n" +
                "                JOIN owd_inventory i\n" +
                "                ON i.inventory_num = l.inventory_num\n" +
                "                WHERE o.client_fkey = 529\n" +
                "                AND shipped_on = CAST(GETDATE() AS date)");

        List<String> excludedSkus = getExcludedSkus();

        if (excludedSkus.size() > 0)
        {
            String skuList = excludedSkus.stream()
                    .map(n -> String.valueOf(n))
                    .collect(Collectors.joining("','", "('","')"));
            sbSql.append(" AND i.inventory_num NOT IN ");
            sbSql.append(skuList);
        }

        String sql = sbSql.toString();

        try {
            java.sql.Connection conn = ConnectionManager.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.executeQuery();
            java.sql.ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                sb.append(rs.getString(1) + fieldSeparator + rs.getString(2) + fieldSeparator + rs.getString(3) + fieldSeparator + rs.getString(4) + fieldSeparator + rs.getString(5) + fieldSeparator + rs.getString(6) + fieldSeparator + rs.getString(6) + lineSeparator);
            }
        } catch (Exception ex) {
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:" + Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    this.getClass().getName(),
                    LogableException.errorTypes.INVENTORY);
        }

        return sb.toString();
    }

    private String getFileHeaderLine() {
        return "Shipped, Client Reference, SKU, Line Description, Qty Shipped, Unit Price, Total Price" + lineSeparator;
    }

    private boolean sendTransactionData(String transactionString) {
        try {
            SSHClient sClient = setupSshj();
            SFTPClient client = sClient.newSFTPClient();
            String encryptedData = getEncryptedString(transactionString);

            InMemorySourceFile memorySourceFile = new InMemorySourceFile() {
                @Override
                public String getName() {
                    return getFileName();
                }

                @Override
                public long getLength() {
                    return encryptedData.length();
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return new ByteArrayInputStream(encryptedData.getBytes());
                }
            };

            client.put(memorySourceFile, "ftp/files/");
            client.close();
            sClient.disconnect();
        } catch (Exception ex) {
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:" + Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    this.getClass().getName(),
                    LogableException.errorTypes.INVENTORY);

            return false;
        }

        return true;
    }

    private String getEncryptedString(String inputString) {
        try {
            Security.setProperty("crypto.policy", "unlimited");
            PGPPublicKey key = PgpUtil.readPublicKey(LyftPgp.publicKey);
            byte[] encryptedData = PgpUtil.createEncryptedData(key, inputString.getBytes());

            return new String(Base64.getEncoder().encode(encryptedData));
        } catch (Exception ex) {
            LogableException le = new LogableException(ex, ex.getMessage(),
                    "TS:" + Calendar.getInstance().getTimeInMillis(),
                    OWDUtilities.getStackTraceAsString(ex),
                    this.getClass().getName(),
                    LogableException.errorTypes.INVENTORY);
        }

        return null;
    }

    private String getFileName() {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append("Lyft_OWD_Onhand_");
        fileNameBuilder.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        fileNameBuilder.append(".csv");

        return fileNameBuilder.toString();
    }

    private SSHClient setupSshj() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(ftpHost);
        client.authPassword(ftpUser, ftpPass);

        return client;
    }

    private List<String> getExcludedSkus() {
        List<String> excludedSkus = new ArrayList();

        excludedSkus.add("LYFT165");
        excludedSkus.add("LYFT126-KITM2XL");
        excludedSkus.add("LYFT153B");
        excludedSkus.add("LYFT175");
        excludedSkus.add("LYFT354");
        excludedSkus.add("LYFT152");
        excludedSkus.add("LYFT099");
        excludedSkus.add("LYFT376");
        excludedSkus.add("LYFT126-KITWS");
        excludedSkus.add("LYFT157");
        excludedSkus.add("LYFT315");
        excludedSkus.add("LYFT170");
        excludedSkus.add("LYFT215");

        return excludedSkus;
    }
}