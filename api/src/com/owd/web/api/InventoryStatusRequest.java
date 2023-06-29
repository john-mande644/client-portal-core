package com.owd.web.api;

import com.owd.hibernate.generated.OwdInventory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.hibernate.*;
import org.apache.xpath.XPathAPI;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.w3c.dom.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 6/29/11
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class InventoryStatusRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();


    //root node name

    public static final String kRootTag = "OWD_INVENTORY_STATUS_REQUEST";

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {

        try {


            List<OwdInventory> itemList = new ArrayList<OwdInventory>();
            InventoryStatusResponse response = new InventoryStatusResponse(api_version);

            NodeList filters = XPathAPI.selectNodeList(root, "./FILTER");

            Criteria crit = HibernateSession
                    .currentSession()
                    .createCriteria(OwdInventory.class);

            crit.createAlias("owdInventoryOh", "onhand");
         //   crit.createAlias("requiredSkus", "component");

            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            crit.add(Restrictions.eq("owdClient.clientId", Integer.parseInt(client.client_id)));

            log.debug("filter count = " + filters.getLength());
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
                if ("sku".equals(type)) {
                    log.debug("sku search");
                    crit.add(Restrictions.eq("inventoryNum", value));
                } else if ("skuContains".equals(type)) {
                    crit.add(Restrictions.like("inventoryNum", "%" + value + "%"));
                } else if ("type".equals(type)) {

                    if ("PHYSICAL".equals(value)) {
                        crit.add(Restrictions.eq("isAutoInventory", 0));
                        crit.add(Restrictions.eq("requireSerialNumbers", 0));
                    } else if ("SERIALIZED".equals(value)) {

                        crit.add(Restrictions.eq("isAutoInventory", 0));
                        crit.add(Restrictions.eq("requireSerialNumbers", 1));
                    } else if ("VIRTUAL".equals(value)) {

                        crit.add(Restrictions.eq("isAutoInventory", 1));
                        crit.add(Restrictions.sizeEq("requiredSkus", new Integer(0)));

                    } else if ("KIT".equals(value)) {
                        crit.add(Restrictions.eq("isAutoInventory", 1));
                        crit.add(Restrictions.sizeGt("requiredSkus", new Integer(0)));

                    } else {
                        throw new APIContentException("Filtering on 'type' key requires values of PHYSICAL, SERIALIZED, VIRTUAL or KIT");
                    }

                } else if ("containsComponent".equals(type)) {
                    crit.createCriteria("requiredSkus").createCriteria("owdInventory").add(Restrictions.eq("inventoryNum", value));
                } else if ("isActive".equals(type)) {
                    if (!("1".equals(value) || "0".equals(value))) {
                        throw new APIContentException("Filtering on 'isActive' requires passing a value of 1 or 0");
                    }
                    crit.add(Restrictions.eq("isActive", "1".equals(value) ? true : false));
                } else if ("stockLevelBelow".equals(type)) {
                    try {
                        crit.add(Restrictions.lt("onhand.qtyOnHand", new Integer(value)));
                    } catch (NumberFormatException num) {
                        throw new APIContentException("Filtering on 'stockLevelBelow' requires passing a value that can be converted to an whole number");
                    }
                } else  {
                        throw new APIContentException("Unrecognized FILTER type value of '"+type+"'");

                }
            }
            log.debug("found " + crit.list().size());
            response.setInventoryList(crit.list());

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
