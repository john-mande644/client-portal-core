package com.owd.web.api;

import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.generated.Receive;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.asn.ASNManager;
import com.owd.hibernate.*;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/29/11
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsnReceiveReleaseRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_ASN_RECEIVE_RELEASE_REQUEST";
    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {

            List<Asn> asnList = new ArrayList<Asn>();
            AsnStatusResponse response = new AsnStatusResponse(api_version);

            String rcvId = XPathAPI.eval(root, "@id").toString();
            Asn asn = null;
            Receive rcv = null;

            try {
                rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.parseInt(rcvId));
                asn = rcv.getAsn();

                if(asn == null || rcv == null) { throw new Exception("Receive ID "+rcvId+" not found");}

                if (asn.getClientFkey() != Integer.parseInt(client.client_id)) {
                    throw new Exception("No Receive record for id " + rcvId + " found");
                }
                if (rcv.getIsPosted()==1) {
                    throw new Exception("Cannot release receive to inventory - this receive has already been released");
               }
            } catch (NumberFormatException ex) {
                throw new APIContentException("Receive id attribute must be a valid integer value");
            } catch (Exception ex) {
                throw new APIContentException(ex.getMessage());
            }

            ASNManager.postReceiveToInventory(rcv);
            HibernateSession.currentSession().flush();


            log.debug("testing:" + testing);
            if (!testing) {
                HibUtils.commit(HibernateSession.currentSession());
            }

            rcv.setIsPosted(1);
            rcv.setPostDate(Calendar.getInstance().getTime());
            asnList.add(asn);
            response.setAsnList(asnList);

            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            HibUtils.rollback(HibernateSession.currentSession());

            throw ex;

        } finally {
            HibUtils.rollback(HibernateSession.currentSession());

        }

    }

}
