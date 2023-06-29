package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.magento.owd.MagentoRemoteService;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class GymBodiesMagentoOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }

    public void internalExecute() {

        try {
            //step one get a result set of clients from SQLserver
            MagentoRemoteService service = new MagentoRemoteService(
                    "https://www.gymnasticbodies.com/store/index.php/api/index/index/"
                    , "oneworld"
                    , "oneworld7172"
                    , 411);
            service.setProcessingOrders(true);
            service.setPendingOrders(false);
            service.setPendingCheckOrdersInvoiced(false);
            service.setAllowFreeItems(true);

            service.importMagentoOrders(100000001,-10);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }

}
