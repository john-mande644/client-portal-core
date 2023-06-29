package com.owd.web.api;

import com.owd.hibernate.generated.Asn;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.hibernate.*;
import org.apache.xpath.XPathAPI;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.w3c.dom.*;

import java.text.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/29/11
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsnStatusRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_ASN_STATUS_REQUEST";
    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {


            List<Asn> itemList = new ArrayList<Asn>();
            AsnStatusResponse response = new AsnStatusResponse(api_version);

            NodeList filters = XPathAPI.selectNodeList(root, "./FILTER");

            Criteria crit = HibernateSession
                    .currentSession()
                    .createCriteria(Asn.class)
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .add(Restrictions.eq("clientFkey", Integer.parseInt(client.client_id)));

            DetachedCriteria unreleasedReceivesSubquery = DetachedCriteria.forClass(Asn.class, "asn")
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .add(Restrictions.eq("clientFkey", Integer.parseInt(client.client_id)))
                    .createCriteria("receives").add(Restrictions.eq("isPosted", 1))
                    .setProjection( Projections.id() );


            log.debug("cid:" + Integer.parseInt(client.client_id));
            log.debug("filter count = " + filters.getLength());
            if(filters.getLength()<1)
            {
                throw new APIContentException("ASN_STATUS_REQUEST must contain one or more valid FILTER elements");
            }
            for (int i = 0; i < filters.getLength(); i++) {
                Node currentFilter = filters.item(i);

                String type = XPathAPI.eval(currentFilter, "./@type").toString();
                String value = XPathAPI.eval(currentFilter, "./@value").toString();
                if(type.length()<1 && value.length()<1)
                {
                      type = XPathAPI.eval(currentFilter, "./TYPE").toString();
                 value = XPathAPI.eval(currentFilter, "./VALUE").toString();
                }
                  if(type.length()<1 && value.length()<1)
                {
                      type = XPathAPI.eval(currentFilter, "./FILTER_TYPE").toString();
                 value = XPathAPI.eval(currentFilter, "./FILTER_VALUE").toString();
                }
                   log.debug("got type:"+type);
                 //   log.debug("got type1:"+type1);
                   log.debug("got value:"+value);
                if ("reference".equals(type)) {
                    crit.add(Restrictions.eq("clientRef", value));
                } else if ("containsSku".equals(type)) {
                    crit.createCriteria("asnItems").add(Restrictions.eq("inventoryNum", value));
                } else if ("status".equals(type)) {
                    if (AsnStatusResponse.statusMap.containsValue(value)) {
                        for (Integer key : AsnStatusResponse.statusMap.keySet()) {
                            if (value.equals(AsnStatusResponse.statusMap.get(key))) {
                                crit.add(Restrictions.eq("status", key));
                                break;
                            }
                        }

                    } else {
                        throw new APIContentException("Filtering on 'status' requires that the value must be one of UNRECEIVED, PARTIALRECEIPT, CANCELLED or COMPLETE");
                    }
                } else if ("receivedOn".equals(type)) {
                    try {
                        crit.createCriteria("receives").add(Restrictions.eq("createdOn", df.parse(value)));
                    } catch (ParseException num) {
                        throw new APIContentException("Filtering on 'receivedOn' requires passing a value that can be converted to date. Value should be in YYYYMMDD format.");
                    }
                } else if ("receivedSince".equals(type)) {
                    try {
                        crit.createCriteria("receives").add(Restrictions.ge("createdOn", df.parse(value)));
                    } catch (ParseException num) {
                        throw new APIContentException("Filtering on 'receivedOn' requires passing a value that can be converted to date. Value should be in YYYYMMDD format.");
                    }
                } else if ("hasUnreleasedReceives".equals(type)) {
                    if (!("1".equals(value) || "0".equals(value))) {
                        throw new APIContentException("Filtering on 'hasUnreleasedReceives' requires passing a value of 1 or 0");
                    }
                    if("1".equals(value))
                    {
                        crit.createCriteria("receives").add(Restrictions.eq("isPosted", new Integer(value)));
                    }else{
                        crit.add(Subqueries.notExists(unreleasedReceivesSubquery));
                    }

                } else if ("owdReference".equals(type)) {
                    try {
                        crit.add(Restrictions.eq("id", new Integer(value)));
                    } catch (NumberFormatException num) {
                        throw new APIContentException("Filtering on 'owdReference' requires passing a value that can be converted to an whole number");
                    }
                } else if ("poReference".equals(type)) {
                    crit.add(Restrictions.eq("clientPo", value));
                } else if ("shipper".equals(type)) {
                    crit.add(Restrictions.eq("shipper", value));

                } else
                {
                    throw new APIContentException("Invalid FILTER/TYPE value in ASN_STATUS_REQUEST");

                }
            }
            log.debug("found " + crit.list().size());
            response.setAsnList(crit.list());

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
