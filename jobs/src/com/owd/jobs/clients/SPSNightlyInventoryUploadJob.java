package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.core.Mailer;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.EdiSpsConfigdata;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient;
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP;
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceDicksSportingGoodsUtilities;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by danny on 2/25/2017.
 */
public class SPSNightlyInventoryUploadJob extends OWDStatefulJob {

    public static void main(String[] args){

        try {
            EdiSpsConfigdata config = (EdiSpsConfigdata) HibernateSession.currentSession().load(EdiSpsConfigdata.class,
                    23);
         String   inventory = SPSCommerceBaseClient.generate846Inventory(config,config.getClientFkey());

         System.out.println(inventory);
         
            SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");
            //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
            ftp.putDataFile(SPSCommerceRemoteFTP.fileType.IN, inventory.getBytes(), SPSCommerceRemoteFTP.FolderPath.sendDirPath);

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public void internalExecute(){
    String clientId="";



try {
    try {
        System.out.println("Hello");

        Query q = HibernateSession.currentSession().createQuery("from EdiSpsConfigdata where nightlyInventory=1 ");

        System.out.println("Query");
        List<EdiSpsConfigdata> configs = (List<EdiSpsConfigdata>) q.list();
        if(configs.size()>0){
            System.out.println("We have configs to post");
            for(EdiSpsConfigdata config:configs){
                String inventory="";

                clientId = config.getClientFkey()+"";
                if(config.getName().equals("Dicks")){
                    inventory= SpsCommerceDicksSportingGoodsUtilities.generate846Inventory(config,config.getClientFkey());
                    System.out.println(inventory);
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP();
                }else{
                    inventory = SPSCommerceBaseClient.generate846Inventory(config,config.getClientFkey());
                    SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");
                    //uncomment when you're ready to deliver the test ASN to SPSCommmerce test folder
                    ftp.putDataFile(SPSCommerceRemoteFTP.fileType.IN, inventory.getBytes(), SPSCommerceRemoteFTP.FolderPath.sendDirPath);

                }

               // Mailer.sendMailWithAttachment("Inventory File "+ config.getSpsaccount(), "Attached is the inventory list", "owditadmin@owd.com", inventory.getBytes(), "inventory.xml", "text/xml");


            }
        }

    } catch (Exception e) {
        //e.printStackTrace();
        throw new LogableException("Error with nightly Inventory files: " + e.getMessage(), "", clientId + "", "EDI 846", LogableException.errorTypes.UNDEFINED_ERROR);
    }

}catch (Exception ex){
    ex.printStackTrace();
}

    }
}
