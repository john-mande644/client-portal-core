package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.rakuten.MarineEssentialsIntegration;
import com.owd.jobs.jobobjects.rakuten.RakutenOrderProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Nov 13, 2008
 * Time: 10:55:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MarineEssentialsRakutenOrderImportJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        run();
    }

    public void internalExecute() {
        int clientID = 494;

        try {
            RakutenOrderProcessor rdp = MarineEssentialsIntegration.getRakutenOrderProcessor();

            rdp.importOrders();

        } catch (Exception ex) {
            throw new LogableException(ex, ex.getMessage(),
                    "GENERIC",
                    "" + clientID,
                    this.getClass().getName(),
                    LogableException.errorTypes.ORDER_IMPORT);
        }
    }
}
