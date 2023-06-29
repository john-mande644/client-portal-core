package com.owd.jobs.archives;

import com.owd.LogableException;
import com.owd.core.Mailer;
import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.order.Order;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.magento.MagentoImportOrderCustomizer;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;
import com.owd.jobs.jobobjects.walmart.WalmartDsvApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class GildanWalmartDsvOrderImportJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();


    public static void main(String[] args) throws Exception {

        run();

    }

    public void internalExecute() {

        try {

            WalmartDsvApi api = new WalmartDsvApi("ftp.owd.com", "gildanwalmart", "GildanOWD17!!", "272112", "Gildan");
            api.setOldsftpHost("bedrock.walmart.com");
            api.setOldsftpUser("gildan");
            api.setOldsftpPass("v38wVpaM");
            api.setOldSend(true);
            api.setClientId(471);

            api.retrieveOrderFiles();
            api.processOrderFiles();
            api.pushInventoryFile();

        } catch (Exception ex) {

            ex.printStackTrace();
            Exception exl = new LogableException(ex, "Generic order import error from Walmart.com:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "471", "Walmart.com", LogableException.errorTypes.ORDER_IMPORT);

        } finally {


            HibernateSession.closeSession();
        }
    }


}
