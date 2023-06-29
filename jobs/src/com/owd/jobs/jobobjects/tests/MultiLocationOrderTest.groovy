package com.owd.jobs.jobobjects.tests

import com.owd.core.OWDUtilities
import com.owd.core.business.order.Event
import com.owd.core.business.order.Order
import com.owd.core.managers.ConnectionManager
import com.owd.core.managers.InventoryManager
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdInventory
import com.owd.hibernate.generated.OwdOrderShipInfo
import junit.framework.TestCase

import java.sql.Connection
import java.sql.Statement

import static com.owd.core.business.Client.getClientForID

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/13/12
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
class MultiLocationOrderTest extends TestCase {




    public void tearDown() throws Exception
    {
    }

    public void testAllDC5Lines()
    throws Exception
    {
        //create acme bbq order and release to ship

        //mark as verified

        //verify At Warehouse

        //assert all lines DC5

        //update order facility code

        //assert order updated to DC5


    }


    public void testSplitDC5Lines()
    throws Exception
    {
          Order order = createOrder();


        Map<String,Integer> facilityLineMap = new TreeMap<String,Integer>()

        String lineCountQuery="select ISNULL(i.default_facility_code,'DC1') as default_facility_code,count(*) as linecnt from owd_inventory i join owd_line_item l join owd_order o on l.order_fkey=order_id on i.inventory_id=l.inventory_id\n" +
                "where order_id=? and quantity_actual>0\n" +
                "group by default_facility_code"
        List<Object[]> data = HibernateSession.currentSession().createSQLQuery(lineCountQuery).setInteger(0,Integer.parseInt(order.orderID)).list();

        for(Object[] facilityCount:data)
        {
            facilityLineMap.put(facilityCount[0].toString(),Integer.parseInt(facilityCount[1].toString()))

                    }


        List<OwdOrderShipInfo> infoList = (List<OwdOrderShipInfo>) HibernateSession.currentSession().createQuery("from OwdOrderShipInfo where order.id=?").setInteger(0, Integer.parseInt(order.orderID)).list();
        if (infoList.size() == 1) //should always be true
        {
            OwdOrderShipInfo info = infoList.get(0);
            assertEquals(info.getOrder().getFacilityCode(),"DC1")

            if (facilityLineMap.keySet().size()==1)
            {
                info.getOrder().setFacilityCode(facilityLineMap.keySet().toList().get(0))
                HibernateSession.currentSession().saveOrUpdate(info.getOrder())
                HibUtils.commit(HibernateSession.currentSession())

            }   else
            {
                 println "multi"
                //we assume only two facilities for now

                //pick a facility for this order

                info.getOrder().setFacilityCode(facilityLineMap.keySet().toList().get(0))
                HibernateSession.currentSession().saveOrUpdate(info.getOrder())
                HibUtils.commit(HibernateSession.currentSession())

                //duplicate order to new order
                int originalOrderId = info.getOrder().getOrderId();
                String originalOrderNum = info.getOrder().getOrderNum();
                String newOrderNum = ConnectionManager.getNextID("Order");


                HibernateSession.currentSession().evict(info.getOrder())
                HibernateSession.currentSession().evict(info)

                Statement stmt = null;
                Connection cxn = null;

                try {
                    String esql = "exec split_order " + originalOrderId + ",\'" + originalOrderNum + "\',\'" + newOrderNum + "\',\'"+facilityLineMap.keySet().toList().get(1)+"\'   ";

                    cxn = ConnectionManager.getConnection();
                    stmt = cxn.createStatement();

                    boolean hasResultSet = stmt.execute(esql);

                    stmt.close();

                    cxn.commit();

                    cxn.close();

                } catch (Exception ex) {
                    throw ex;

                } finally {

                    try {
                        stmt.close();
                    } catch (Exception ex) {
                    }

                    try {
                        ConnectionManager.freeConnection(cxn);
                    } catch (Exception ex) {
                    }

                }
                //transfer qty_actual>0 line items from old to new order by matching facility


                //insert new order into print queue with notes
                Event.addOrderEvent(originalOrderId, Event.kEventTypeHandling, "Split location order; split items assigned to OWD order reference "+newOrderNum, "System");



            }

            HibUtils.commit(HibernateSession.currentSession());



        }


        infoList = (List<OwdOrderShipInfo>) HibernateSession.currentSession().createQuery("from OwdOrderShipInfo where order.id=?").setInteger(0, Integer.parseInt(order.orderID)).list();
        assertEquals(infoList.size(),1)

                OwdOrderShipInfo  info = infoList.get(0);
                println (info.getOrder().getFacilityCode())




        }



    def static Order createOrder()
    {


        def order = new Order("" + 112) //acme bbq

        def policy = getClientForID(order.clientID).getClientPolicy();

        order.getShippingInfo().carr_service = 'USPS Priority Mail'
        order.getShippingInfo().setShipOptions(order.getShippingInfo().carr_service,"Prepaid","");


        assert order.getShippingInfo().carr_service != null
        order.order_type = "TEST"

        order.order_refnum = new Date().time


                    order.getShippingContact().with {
                        name = 'test test'
                        email = 'test@owd.com'
                        phone = '1231231233'
                    }
                    order.getShippingAddress().with {
                        address_one = '123 Main Test'
                        address_two = ''
                        city = 'Mobridge'
                        state = 'SD'
                        zip = '57601'
                        country = 'USA'
                    }


                    order.getBillingContact().with {
                        name = 'test test'
                        email = 'test@owd.com'
                        phone = '1231231233'
                    }
                    order.getBillingAddress().with {
                        address_one = '123 Main Test'
                        address_two = ''
                        city = 'Mobridge'
                        state = 'SD'
                        zip = '57601'
                        country = 'USA'
                    }

              OwdInventory dcFive1 = InventoryManager.getOwdInventoryForClientAndSku("112","TestDC5_1");

              InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(dcFive1.getInventoryId(),112,999, OWDUtilities.getSQLDateTimeForToday(),""+(new Date().time), HibernateSession.currentSession());
        OwdInventory dcOne1 = InventoryManager.getOwdInventoryForClientAndSku("112","TestDC1_1");

        InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(dcOne1.getInventoryId(),112,999, OWDUtilities.getSQLDateTimeForToday(),""+(new Date().time), HibernateSession.currentSession());

        policy.addLineItemToOrder(order, 'TestDC5_1', '', "0.00", '1')

        order.skuList.last().client_ref_num = '456';
        policy.addLineItemToOrder(order, 'TestDC1_1', '', "0.00", '1')

            order.skuList.last().client_ref_num = '123';




        //  if (clientID != 112)
        //  { order.is_future_ship = 1; }

        policy.saveNewOrder(order, false)

        return order;
    }

}
