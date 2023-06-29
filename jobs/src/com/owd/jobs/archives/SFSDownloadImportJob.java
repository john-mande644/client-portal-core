package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.clients.SFSDownloadImporter;
import com.owd.jobs.OWDStatefulJob;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Nov 17, 2005
 * Time: 11:36:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class SFSDownloadImportJob   extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static String client_id = "143"; //SFS

    public static void main(String[] args) {

        run();

    }

   public void internalExecute() {

        StringBuffer aLine;

        try {


            SFSDownloadImporter imp = new SFSDownloadImporter();
            imp.checkForOrders(client_id);


        } catch (Exception ex) {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            //////////log.debug("CUTImporter stamper="+stamper);
            ex.printStackTrace();
            Mailer.postMailMessage("SFSDownloadImporter import error", sb.toString(), "casetracker@owd.com", "noop@owd.com");
        }

    }


}
