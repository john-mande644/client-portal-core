package com.owd.jobs.clients.JustBrands;

import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.InMemorySourceFile;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.apache.commons.net.ftp.FTPClient;
import org.hibernate.Query;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JustBrandsSPSDailyInventoryJob extends OWDStatefulJob {

public static void main(String[] args){

}

    public void internalExecute(){
        String sql = "SELECT\n" +
                "'ActionHeat',\n" +
                "    dbo.owd_inventory.inventory_num,\n" +
                "    REPLACE(dbo.owd_inventory.description, ',', ' -') AS 'description',\n" +
                "    case when dbo.owd_inventory_oh.qty_on_hand <8 then 0 else dbo.owd_inventory_oh.qty_on_hand end as onHand,\n" +
                "    '123ALLJUSTBRAND' as partner,\n" +
                "    dbo.owd_inventory.upc_code\n" +
                "    ,'' as number,'EA' as UOM, item_cost as Price\n" +
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
                "AND item_cost > 0 and len(upc_code) >0 ;";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            List l = q.list();
            StringBuilder sb = new StringBuilder();
            sb.append("Brand,SKU,Name,QTY,Trading Partner,UPC,Buyer's Part Number,Quantity Unit of Measure,Price\r\n");
            for(Object row:l){
                Object[] data = (Object[]) row;
                sb.append(data[0].toString());
                sb.append(",");
                if(data[1].toString().equalsIgnoreCase("AH-CAR-12-01-One-Size")){
                    sb.append("AH-CAR-12-01");
                }else{
                    sb.append(data[1].toString());
                }

                sb.append(",");
                sb.append(data[2].toString());
                sb.append(",");
                sb.append(data[3].toString());
                sb.append(",");
                sb.append(data[4].toString());
                sb.append(",");
                sb.append(data[5].toString());
                sb.append(",");
                sb.append(data[6].toString());
                sb.append(",");
                sb.append(data[7].toString());
                sb.append(",");
                sb.append(data[8].toString());

                sb.append("\r\n");
            }

            System.out.println(sb);
            sendFile(sb);

           try {
               sendOnyxFile(sb);
           } catch (Exception e) {
               System.out.println("Bad Onyx"+ e.getMessage());
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendOnyxFile(StringBuilder sb) throws Exception{

        FTPClient client = new FTPClient();
        String filename2 = "IB_justBrand.csv";

        client.connect("ftp.onyxenterprises.com");

        client.login("Action_Heat_FTP","AeLui3vieGhaiteepe8Y");
        client.enterLocalPassiveMode();

        System.out.println(client.storeFile(filename2, new ByteArrayInputStream(sb.toString().getBytes())));
        System.out.println(client.getReplyCode());
        System.out.println(client.getReplyString());
        client.logout();
    }

    public void sendFile(StringBuilder sb) throws  Exception{

    SSHClient client = setupSshj();
        SFTPClient sftpClient = client.newSFTPClient();
        InMemorySourceFile memorySourceFile = new InMemorySourceFile() {
            @Override
            public String getName() {
                return "IB_Generic_JustBrand_846.csv";
            }

            @Override
            public long getLength() {
                return sb.length();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(sb.toString().getBytes());
            }
        };

        sftpClient.put(memorySourceFile,"in/");

        sftpClient.close();
        client.disconnect();

    }
    private SSHClient setupSshj() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect("sftp.spscommerce.net",10022);
        client.authPassword("justbrand", "n!5gSV?m2jx");

        return client;
    }

}
