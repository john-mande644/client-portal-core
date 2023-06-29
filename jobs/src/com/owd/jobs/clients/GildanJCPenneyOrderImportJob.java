package com.owd.jobs.clients;

import com.owd.LogableException;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.commercehub.CommerceHubApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class GildanJCPenneyOrderImportJob extends OWDStatefulJob {
    private final static Logger log = LogManager.getLogger();


    public static void main(String[] args) throws Exception {
        run();


    }

    public void internalExecute() {

        try {

            CommerceHubApi api = new CommerceHubApi("gildanusa.sftp.commercehub.com", "gildanusa", "Bell!Improve$Already5","jcpenney","Gildan USA Inc.","JC Penney Company, Inc.",471);

            api.retrieveOrderFiles();
            api.processOrderFiles();
            api.pushInventoryFile();

            // boolean import onhold
            // check order and flip flag before svaing it
            /*if(is_JCP_order()){


            }*/

        } catch (Exception ex) {

            ex.printStackTrace();
            Exception exl = new LogableException(ex, "Generic order import error from JCPenney:" + ex.getMessage(), "TS:" + Calendar.getInstance().getTimeInMillis(), "471", "JCPenney.com", LogableException.errorTypes.ORDER_IMPORT);

        } finally {


            HibernateSession.closeSession();
        }
    }

    /**
     * Sean 2020/5/26 case 813893
     * @param info
     * @return
     */
    public static boolean is_JCP_order(OwdOrderShipInfo info){
        boolean is_JCP_order = false;
        if (info.getOrder().getGroupName().equals("JCPENNEY")){
            is_JCP_order = true;
        }
        return is_JCP_order;
    }


}
