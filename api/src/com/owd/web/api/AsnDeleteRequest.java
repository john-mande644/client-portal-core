package com.owd.web.api;

import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.generated.AsnItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.hibernate.*;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/29/11
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsnDeleteRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_ASN_DELETE_REQUEST";
    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {

            log.debug("handling asn update");
            List<Asn> asnList = new ArrayList<Asn>();
            AsnStatusResponse response = new AsnStatusResponse(api_version);

            deleteAsn(client.client_id, XPathAPI.eval(root, "@id").toString());


            log.debug("testing:" + testing);
            if (!testing) {
                HibUtils.commit(HibernateSession.currentSession());
            }


           // asnList.add(asn);
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

    private static void deleteAsn(String clientId, String asnId) throws Exception {
        Asn asn = null;

        try {
            asn = (Asn) HibernateSession.currentSession().load(Asn.class, Integer.parseInt(asnId));
            if(asn == null) { throw new Exception("Asn ID "+asnId+" not found");}
            if (asn.getClientFkey() != Integer.parseInt(clientId)) {
                throw new Exception("No ASN record for id " + asnId + " found");
            }
            if (asn.getReceives().size() > 0) {
                throw new Exception("Cannot delete ASN after receiving has occurred or been started");
           }
        } catch (NumberFormatException ex) {
            throw new APIContentException("Asn id attribute must be a valid integer value");
        } catch (Exception ex) {
            throw new APIContentException(ex.getMessage());
        }


        for (AsnItem aitem : asn.getAsnItems()) {
            HibernateSession.currentSession().delete(aitem);
        }
        asn.getAsnItems().clear();
        HibernateSession.currentSession().delete(asn);

        HibernateSession.currentSession().flush();

    }

    public static void main(String[] args)  throws Exception
    {
        ResultSet rs = HibernateSession.getResultSet(" select top 2 id from asn where client_fkey=489 and status=0 and receive_count=0 order by id asc");
        while(rs.next())
        {
            log.debug("delete");
          deleteAsn("489",""+rs.getInt(1));
          HibUtils.commit(HibernateSession.currentSession());
        }
    }

}
