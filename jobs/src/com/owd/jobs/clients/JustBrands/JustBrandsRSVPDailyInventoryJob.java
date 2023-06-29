package com.owd.jobs.clients.JustBrands;

import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.InMemorySourceFile;
import org.apache.commons.net.ftp.FTPClient;
import org.hibernate.Query;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JustBrandsRSVPDailyInventoryJob extends OWDStatefulJob {

    public static void main(String[] args) {
        JustBrandsRSVPDailyInventoryJob j = new JustBrandsRSVPDailyInventoryJob();
        try {
            j.sendRSVPFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void internalExecute() {

        try {
            sendRSVPFile();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void sendRSVPFile() throws Exception {


        String sql = "SELECT\n" +

                "    dbo.owd_inventory.inventory_num,\n" +

                "     dbo.owd_inventory_oh.qty_on_hand, owd_inventory.upc_code, owd_inventory.description\n" +

                "FROM\n" +
                "    dbo.owd_inventory\n" +
                "INNER JOIN\n" +
                "    dbo.owd_inventory_oh\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_oh.inventory_fkey)\n" +
                "WHERE\n" +
                "    dbo.owd_inventory.client_fkey = 626\n" +
                "AND dbo.owd_inventory.is_active = 1\n" +
                "AND (description like '%ActionHeat%' or description like '%Gerbing%') and len(upc_code)>0 ;";
        try {
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            StringBuilder sb = new StringBuilder();
            sb.append("SKU,QTY,UPC\r\n");
            for (Object row : l) {
                Object[] data = (Object[]) row;

                if (data[0].toString().equalsIgnoreCase("AH-CAR-12-01-One-Size")) {
                    sb.append("AH-CAR-12-01");
                } else {
                    sb.append(data[0].toString());
                }

                sb.append(",");
                sb.append(data[1].toString());
                sb.append(",");
                sb.append(data[2].toString());
                sb.append(",");
                sb.append(data[3].toString());
                sb.append("\r\n");


            }
             System.out.println(sb.toString());
            FTPClient client = new FTPClient();
            String filename2 = "justBrand.csv";


            client.connect("ftp.rsvpcomm.com");

            client.login("JustBrand", "Kick$H0ld3r13@s3");
            client.enterLocalPassiveMode();
            // client.changeWorkingDirectory("/fromvan");

            System.out.println(client.storeFile(filename2, new ByteArrayInputStream(sb.toString().getBytes())));
            System.out.println(client.getReplyCode());
            System.out.println(client.getReplyString());
            client.logout();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
