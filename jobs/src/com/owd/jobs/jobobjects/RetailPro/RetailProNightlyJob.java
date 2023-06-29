package com.owd.jobs.jobobjects.RetailPro;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.RetailPro.models.Adjustment;
import com.owd.jobs.jobobjects.RetailPro.models.MemoAdjustment;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/23/12
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class RetailProNightlyJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();


    public static void main(String[] args) throws Exception {
        run();

    }

    public void internalExecute() {
        try {
            List<String> rproSkuList = InventoryManager.updateOWDInventoryFromRetailPro();
            Adjustment.reportAdjustments(rproSkuList);

            MemoAdjustment.exportCurrentInventoryOnHand(rproSkuList);
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                Mailer.sendMail("Babeland Retail Pro Nightly Job Error", ex.getMessage(), "owditadmin@owd.com", "donotreply@owd.com");
            } catch (Exception exx) {
            } finally {

            }

        }

    }
}
