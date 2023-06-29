package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdReceive;
import org.apache.xpath.XPathAPI;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/29/11
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryReportRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_INVENTORY_REPORT_REQUEST";
    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {


            List<OwdReceive> itemList = new ArrayList<OwdReceive>();
            InventoryReportResponse response = new InventoryReportResponse(api_version);

            NodeList filters = XPathAPI.selectNodeList(root, "./FILTER");

            Criteria crit = HibernateSession
                    .currentSession()
                    .createCriteria(OwdReceive.class);

         //   crit.createAlias("owdReceiveItems", "owdReceiveItems");
           // crit.createAlias("requiredSkus", "component");

            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            crit.setMaxResults(2000);

            crit.add(Restrictions.eq("owdClient.clientId", Integer.parseInt(client.client_id)));
            crit.add(Restrictions.eq("isVoid", false));
            crit.add(Restrictions.or(Restrictions.ne("createdBy","SKU Swap"),Restrictions.isNull("createdBy")));

            log.debug("filter count = " + filters.getLength());
            for (int i = 0; i < filters.getLength(); i++) {
                Node currentFilter = filters.item(i);

                String type = XPathAPI.eval(currentFilter, "./TYPE").toString();
                String value = XPathAPI.eval(currentFilter, "./VALUE").toString();
                log.debug("got type:" + type);
                log.debug("got value:" + value);
                if ("facilityCode".equals(type)) {
                    log.debug("facility search");
                    crit.add(Restrictions.eq("facilityCode", value));
                } else if ("containsSku".equals(type)) {
                    log.debug("sku search");
                    crit.createCriteria("owdReceiveItems").add(Restrictions.eq("inventoryNum", value));
                } else if ("type".equals(type)) {

                    if ("RETURN".equals(value)) {
                        crit.add(Restrictions.eq("type", "RETURN"));
                    } else if ("RECEIVE".equals(value)) {

                        crit.add(Restrictions.eq("type", "RECEIVE"));
                    } else if ("ADJUSTMENT".equals(value)) {

                        crit.add(Restrictions.eq("type", "ADJUSTMENT"));

                    } else if (!"ALL".equals(value)) {
                        throw new APIContentException("Filtering on 'type' key requires values of RETURN, RECEIVE, ADJUSTMENT or ALL");
                    }

                } else if ("startDate".equals(type)) {
 try {
                        crit.add(Restrictions.ge("createdDate", df.parse(value)));
                    } catch (ParseException num) {
                        throw new APIContentException("Filtering on 'startDate' requires passing a value that can be converted to date. Value should be in YYYYMMDD format.");
                    }
                } else if ("endDate".equals(type)) {
                    try {
                        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(df.parse(value));
        gc.add(Calendar.DATE, 1);
                        Date newDate =  new java.util.Date(gc.getTime().getTime());
                        crit.add(Restrictions.lt("createdDate", newDate));
                    } catch (ParseException num) {
                        throw new APIContentException("Filtering on 'endDate' requires passing a value that can be converted to date. Value should be in YYYYMMDD format.");
                    }
                }
            }
            log.debug("found " + crit.list().size());
            response.setOwdReceiveList(crit.list());

            StringBuffer returnRefList = new StringBuffer();
            StringBuffer asnRefList = new StringBuffer();
            String sep1 = "";
            String sep2 = "";
            for(OwdReceive rcv:response.getOwdReceiveList()) {
                if("RETURN".equalsIgnoreCase(rcv.getType()))
                {
                    returnRefList.append(sep1+"'"+rcv.getRefNum()+"'");     sep1=",";
                }
                if("RECEIVE".equalsIgnoreCase(rcv.getType()))
                {
                    String rcvId = rcv.getTransactionNum();
                    if(rcvId.contains("-")) {
                        rcvId = rcvId.substring(rcvId.indexOf("-") + 1);
                    }
                    asnRefList.append(sep2+rcvId);     sep2=",";
                }
            }
            System.out.println(asnRefList.toString());
            if(returnRefList.length()>0) {
                ResultSet rs = HibernateSession.getResultSet("select order_num,order_refnum from owd_order where client_fkey=" + client.client_id + " and order_num in (" + returnRefList.toString() + ")");
                while (rs.next()) {
                    response.orderRefMap.put(rs.getString(1), rs.getString(2));
                }
                rs.close();
            }
            if(asnRefList.length()>0) {
                String asnSql="select receive.id,asn.id as aid,client_ref,client_po from receive (NOLOCK) join asn (NOLOCK) on asn_fkey=asn.id where asn.client_fkey=" + client.client_id + " and receive.id in (" + asnRefList.toString()+")";
              log.debug(asnSql);
                List<Object[]> data = HibernateSession.currentSession()
                        .createSQLQuery(asnSql)
                        .list();
                if (data.size() > 0) {
                    for (Object[] datum : data) {


                        response.asnInfoMap.put(datum[0].toString(), new AsnData(datum[1].toString(), datum[2].toString(), datum[3].toString()));

                    }
                }
            }
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
