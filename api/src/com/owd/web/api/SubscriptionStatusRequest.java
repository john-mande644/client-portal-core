package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrderAuto;
import org.hibernate.type.IntegerType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.apache.xpath.XPathAPI;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 7, 2008
 * Time: 1:46:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscriptionStatusRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();


    public static final String kRootTag = "OWD_SUBSCRIPTION_STATUS_REQUEST";

    //root node attributes

    public static final String kDaysSinceCreated = "daysSinceCreated";
    public static final String kCreatedOn = "createdDate";
    public static final String kLastName = "customerNameContains";
    public static final String kSourceOrderClientRef = "sourceOrderClientRef";
    public static final String kSourceOrderOWDRef = "sourceOrderOwdRef";
    public static final String kContainsSKU = "containsSKU";
    public static final String kSubStatus = "currentStatus";
    public static final String kSubId = "subscriptionId";


    protected static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception

    {

        try {


            SubscriptionStatusResponse response = new SubscriptionStatusResponse(api_version);

            //add limits (AND logic for all)
            NodeList filters = XPathAPI.selectNodeList(root, "./FILTER");

            Criteria crit = HibernateSession
                    .currentSession()
                    .createCriteria(OwdOrderAuto.class);

            crit.createAlias("owdOrderAutoItems", "i");
            crit.createAlias("sourceOrders", "o");

            crit.add(Restrictions.eq("clientFkey", Integer.parseInt(client.client_id)));

            log.debug("filter count = "+filters.getLength());
            for (int i=0;i<filters.getLength();i++)
               {
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
                if (kDaysSinceCreated.equals(type)) {
                    crit.add(Restrictions.sqlRestriction("datediff(day,{alias}.created,getdate())<= ?", Integer.parseInt(value), IntegerType.INSTANCE));
                } else if (kCreatedOn.equals(type)) {
                    crit.add(Restrictions.eq("created", df.parse(value)));
                } else if (kLastName.equals(type)) {
                    crit.add(Restrictions.like("name", "'%" + value + "%'"));
                } else if (kSourceOrderClientRef.equals(type)) {
                    crit.add(Restrictions.eq("o.orderRefNum", value));
                } else if (kSourceOrderOWDRef.equals(type)) {
                    crit.add(Restrictions.eq("o.orderNum", value));
                } else if (kContainsSKU.equals(type)) {
                    crit.add(Restrictions.eq("i.sku", value));
                } else if (kSubStatus.equals(type)) {
                    crit.add(Restrictions.eq("status", Integer.parseInt(value)));
                } else if (kSubId.equals(type)) {
                    crit.add(Restrictions.eq("autoShipId", Integer.parseInt(value)));
                }
            }

            response.setSubscriptionList(crit.list());

            return response;
        } catch (Exception ex) {
            //ex.printStackTrace();
            throw ex;

        }
    }
}
