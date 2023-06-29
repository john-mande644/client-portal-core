package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdInventory;
import org.w3c.dom.Element;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 9/26/11
 * Time: 10:22 PM
 * To change this template use File | Settings | File Templates.
 */

public class TestInventorySetCountRequest implements APIRequestHandler {
private final static Logger log =  LogManager.getLogger();

      public static final String kRootTag = "OWD_TEST_INVENTORY_SETCOUNT_REQUEST";

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            OwdInventory item = null;
            OwdClient owdClient = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,Integer.parseInt(client.client_id));
            String defaultFacilityCode = owdClient.getDefaultFacilityCode();

            InventoryStatusResponse response = new InventoryStatusResponse(api_version);
             int isTest = 0;

            ResultSet rs = HibernateSession.getResultSet("select count(*) from owd_client_test_accounts where client_fkey="+client.client_id);
            while(rs.next())
            {
                isTest = rs.getInt(1);
            }
            rs.close();
            HibernateSession.closeSession();
            if(isTest!=1 && (!("test".equalsIgnoreCase(System.getProperty("com.owd.environment")))))
            {
                throw new APIContentException("This call can only be used with test accounts!");
            }
            String sku = xPath.evaluate("./SKU", root);
            if (sku == null) {
                throw new APIContentException("Required SKU element not found");
            }
            sku = sku.trim();

            if (sku.length() < 1) {
                throw new APIContentException("Required SKU element empty - must have a minimum length of one character");
            }
            if (!(ConnectionManager.InventoryExists(sku, client.client_id))) {
                //throw
                throw new APIContentException("SKU value " + sku + " not found");

            } else {

                  try
            {
                 item = InventoryManager.getOwdInventoryForClientAndSku(client.client_id, sku);
                String itemType = (item.getIsAutoInventory()==1?(item.getRequiredSkus().size()>0?("KIT"):("VIRTUAL")):(item.getRequireSerialNumbers()==1?("SERIALIZED"):("PHYSICAL")));
                 if(item.getIsAutoInventory()==1)
                 {
                     throw new Exception("Stock levels cannot be set on items of types VIRTUAL or KIT");
                 }
            }catch(Exception ex)
            {
                throw new APIContentException(ex.getMessage());
            }


                 String type = xPath.evaluate( "./TYPE",root);
                if (!("SET".equals(type) || "ADJUST".equals(type))) {
                    throw new APIContentException("TYPE value " + sku + " must be SET or ADJUST");
                }

                try {
                    int newValue = new Integer(xPath.evaluate("./VALUE",root));
                    if (newValue < 0 && "SET".equals(type)) {
                        throw new APIContentException("VALUE must be a non-negative integer for SET adjustment type");
                    }

                    if(FacilitiesManager.isClientMultiFacility(owdClient.getClientId()))
                    {
                        if(api_version>=2.0)
                        {
                            defaultFacilityCode =  xPath.evaluate("./FACILITY_CODE",root);
                        }   else
                        {
                            defaultFacilityCode="DC1";
                        }
                    }

                    if(newValue+(item.getOwdInventoryOh().getQtyOnHand())<0)
                    {
                        throw new APIContentException("Final stock quantity less than zero not allowed with ADJUST type - current stock level is "+item.getOwdInventoryOh().getQtyOnHand());
                    }

                    if("SET".equals(type))
                    {
                        InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(item.getInventoryId(), Integer.parseInt(client.client_id), newValue, OWDUtilities.getSQLDateNoTimeForToday(), "API"+Calendar.getInstance().getTimeInMillis(), defaultFacilityCode,HibernateSession.currentSession());
                    }   else
                    {
                        //adjust

                    InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(item.getInventoryId(), Integer.parseInt(client.client_id), newValue, OWDUtilities.getSQLDateNoTimeForToday(), "API"+Calendar.getInstance().getTimeInMillis(), defaultFacilityCode,HibernateSession.currentSession());



                    }

                } catch (NumberFormatException nex) {
                    throw new APIContentException("VALUE must be an integer value");
                }

            }
            List<OwdInventory> itemList = new ArrayList<OwdInventory>();

             itemList.add(item);
            response.setInventoryList(itemList);

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
