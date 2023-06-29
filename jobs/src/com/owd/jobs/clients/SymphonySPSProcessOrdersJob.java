package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.spscommerce.SPSCommerceRemoteFTP;
import com.owd.jobs.jobobjects.spscommerce.SpsCommerceUtilities;

/**
 * Created by stewartbuskirk1 on 4/6/15.
 */
public class SymphonySPSProcessOrdersJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();



    public void internalExecute() {

        SpsCommerceUtilities.processRemotePOs(new SPSCommerceRemoteFTP(),SPSCommerceRemoteFTP.FolderPath.receiveDirPath.getPath(), 489);
        SpsCommerceUtilities.processRemotePOs(new SPSCommerceRemoteFTP(),SPSCommerceRemoteFTP.FolderPath.receiveDirSymPath.getPath(), 489);
        SpsCommerceUtilities.processPendingPos();




    }
    public static void main(String[] args) throws Exception{

        run();


    }
}
