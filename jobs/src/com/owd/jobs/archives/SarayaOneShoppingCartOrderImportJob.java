package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.oneshoppingcart.OneShoppingCartAPIService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: eric
 * Date: Aug 27, 2008
 * Time: 4:01:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class SarayaOneShoppingCartOrderImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    static final Pattern ORDER_REF_PATTERN = Pattern
            .compile("^https://(.*)/API/(.*)/Orders/(.*)");

    public static void main(String[] args) throws Exception {

        run();
        /*   Matcher m = ORDER_REF_PATTERN.matcher("https://www.mcssl.com/API/157562/Orders/148899722");
    if (m.find()) {

        log.debug("found:"+m.group(3));                }
        */

      //  OneShoppingCartAPIService.importOneShoppingCartOrder(444, "https://www.mcssl.com/API/157562/Orders/172305134", "BC8576B1CA4D4A92845EBBFF405B0D29");
      //  OneShoppingCartAPIService.importOneShoppingCartOrder(444, "https://www.mcssl.com/API/157562/Orders/172260425", "BC8576B1CA4D4A92845EBBFF405B0D29");

    }

    String getOrderReferenceFromLink(String link) {
        String ref = null;
        Matcher m = ORDER_REF_PATTERN.matcher(link);
        if (m.find()) {
            ref = m.group(3);
        }
        return ref;
    }

    public void internalExecute() {

        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -4);
            Date startDate = cal.getTime();
            Date endDate = Calendar.getInstance().getTime();

            List<String> urls = OneShoppingCartAPIService.getPendingOrders("157562", "BC8576B1CA4D4A92845EBBFF405B0D29", startDate, endDate, 0);
           // log.debug("*\r\n" + urls);
            for (String link : urls) {
                log.debug("Link:" + link);
                String ref = getOrderReferenceFromLink(link);
                try {
                 //   if (ref != null && !OrderUtilities.clientOrderReferenceExists(ref, "444", false)) {
                        OneShoppingCartAPIService.importOneShoppingCartOrder(444, link, "BC8576B1CA4D4A92845EBBFF405B0D29");
                 //   }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
    }

}
