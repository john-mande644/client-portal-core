package com.owd.jobs.clients;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by danny on 8/6/2019.
 */
public class DogEaredNordstromDSCOInventoryFeedJob extends OWDStatefulJob {
    private final static Logger log =  LogManager.getLogger();


    public static void main(String args[]){
        run();
    }


    public void internalExecute(){
        String sql = "SELECT\n" +
                "    dbo.owd_inventory.inventory_num,\n" +
                "    FLOOR(dbo.owd_inventory_oh.qty_on_hand*.16)\n" +
                "FROM\n" +
                "    dbo.owd_inventory\n" +
                "INNER JOIN\n" +
                "    dbo.owd_inventory_oh\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory.inventory_id = dbo.owd_inventory_oh.inventory_fkey)\n" +
                "INNER JOIN\n" +
                "    dbo.owd_tags\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_inventory.inventory_id = dbo.owd_tags.external_id and name = 'NORDSTROM_DSCO_SKU' ) ;";

        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            StringBuilder sb = new StringBuilder();
            sb.append("sku,upc,ean,mpn,gtin,isbn,partner_sku,title,brand,quantity_available,status,quantity_on_order,estimated_availability_date,warehouse_dsco_id_1,warehouse_code_1,warehouse_quantity_1,warehouse_retailer_code_1,warehouse_dsco_id_2,warehouse_code_2,warehouse_quantity_2,warehouse_retailer_code_2,warehouse_dsco_id_3,warehouse_code_3,warehouse_quantity_3,warehouse_retailer_code_3,dsco_item_id,dsco_product_id,dsco_retailer_assortments,dsco_supplier_id,dsco_supplier_name,dsco_trading_partner_id,dsco_trading_partner_name,dsco_create_date,dsco_last_quantity_update_date,dsco_last_update_date\n");
            for(Object row:l){
                Object[] data = (Object[])row;
                sb.append(data[0].toString());
                sb.append(",");
                sb.append(data[0].toString());
                sb.append(",,,,,,,,");
                sb.append(data[1].toString());
                sb.append(",,,,,,,,,,,,,,,,,,,,,,,,,\n");


            }

            //Files.write(Paths.get("test.csv"),sb.toString().getBytes());

            String SFTPHOST = "sftp.dsco.io";
            int SFTPPORT = 22;
            String SFTPUSER = "account1000006643";
            String SFTPPASS = "289845";
            String SFTPWORKINGDIR = "in/inventory";

            Session session = null;
            Channel channel = null;
            ChannelSftp channelSftp = null;
            System.out.println("preparing the host information for sftp.");
            try {
                JSch jsch = new JSch();
                session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
                session.setPassword(SFTPPASS);
                java.util.Properties config = new java.util.Properties();
                config.put("StrictHostKeyChecking", "no");
                session.setConfig(config);
                session.connect();
                System.out.println("Host connected.");
                channel = session.openChannel("sftp");
                channel.connect();
                System.out.println("sftp channel opened and connected.");
                channelSftp = (ChannelSftp) channel;
                channelSftp.cd(SFTPWORKINGDIR);
                // File f = new File(fileName);

                channelSftp.put(new ByteArrayInputStream(sb.toString().getBytes()), "Inventory_OWD.csv");
                log.info("File transfered successfully to host.");
            } catch (Exception ex) {
                System.out.println("Exception found while tranfer the response.");
            }
            finally{

                channelSftp.exit();
                System.out.println("sftp Channel exited.");
                channel.disconnect();
                System.out.println("Channel disconnected.");
                session.disconnect();
                System.out.println("Host Session disconnected.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
