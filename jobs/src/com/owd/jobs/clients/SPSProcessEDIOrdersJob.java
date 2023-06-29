package com.owd.jobs.clients;

import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceBaseClient;
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP;
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities;

/**
 * Created by danny on 11/8/2018.
 */
public class SPSProcessEDIOrdersJob extends OWDStatefulJob {




    public static void main(String[] args) throws Exception{

        SPSCommerceBaseClient.processPendingPos();


    }

    public void internalExecute(){

        SPSCommerceRemoteFTP ftp = new SPSCommerceRemoteFTP("onworldir","tFdfCT7ms2","ftp.spscommerce.net");
       SPSCommerceBaseClient.processRemotePOs(ftp, SPSCommerceRemoteFTP.FolderPath.receiveDirPath.getPath());

        SPSCommerceBaseClient.processPendingPos();



    }
}
