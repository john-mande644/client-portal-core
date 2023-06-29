package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.skymall.SkymallApi;

/**
 * Created by stewartbuskirk1 on 10/20/14.
 */
public class TalkEnterprisesSkyMallOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args) {

        run();

    }

    public void internalExecute() {

        SkymallApi api = new SkymallApi("skypartners.skymall.com","tkm","B8=$P2+");
        api.setClientId(504);
        api.setUpsAcct("R2224X");
        api.checkForOrdersFromFtp();
        // api.sendShippedReport(new OrderStatus(((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(),"7928734"));

    }
}
