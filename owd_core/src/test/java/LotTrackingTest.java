import com.owd.core.OWDUtilities;
import com.owd.core.business.asn.ASNFactory;
import com.owd.core.business.asn.ASNManager;
import com.owd.core.business.order.Package;
import com.owd.core.managers.InventoryManager;
import com.owd.core.managers.LotManager;
import com.owd.core.tests.BaseTest;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.*;
import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.util.*;

import static junit.framework.Assert.*;

/**
 * Created by stewartbuskirk1 on 3/28/16.
 */
public class LotTrackingTest extends BaseTest {
    private final static Logger log =  LogManager.getLogger();

    @Test
    public void testGetLotValueMap() throws Exception {

        try {
            int lotId = LotManager.getLotIdForValue("hello",20);
            int lotId2 = LotManager.getLotIdForValue("world",20);
            int lotId3 = LotManager.getLotIdForValue("goodbye",21);
            int lotId4 = LotManager.getLotIdForValue("world",21);


            Map<String,List<String>> map = LotManager.getLotValueMapForInventoryIds(Arrays.asList(20,21));

            assertTrue("lot key value is missing",map.keySet().contains("BL-1BR"));
            assertTrue("lot key value is missing",map.keySet().contains("DH-1C"));
            assertTrue("lot mapped value is missing",map.get("BL-1BR").contains("hello")) ;
            assertTrue("lot mapped value is missing",map.get("BL-1BR").contains("world")) ;
            assertTrue("lot mapped value is missing",map.get("DH-1C").contains("goodbye")) ;
            assertTrue("lot mapped value is missing",map.get("DH-1C").contains("world")) ;
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }

    @Test
    public void testGetHibernateLotValue() throws Exception {

        try {
            int lotId = LotManager.getLotIdForValue("hello",20);

            OwdLotValue lotValue = (OwdLotValue) HibernateSession.currentSession().load(OwdLotValue.class,lotId);
            assertEquals("OwdLotValue lot value invalid",lotValue.getLotValue(),"hello");
            assertEquals("OwdLotValue inventory id invalid",lotValue.getOwdInventory().getInventoryId().intValue(),20);
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }

    @Test
    public void testGetLotIdForValue() throws Exception {

        try {
            int lotId = LotManager.getLotIdForValue("",2);
            int lotId2 = LotManager.getLotIdForValue("",3);   //should return different value due to different inventory id
            int lotId3 = LotManager.getLotIdForValue("",3);   //should return same value as previous
            if(lotId<1 || lotId==lotId2 || lotId2!=lotId3) {
                fail("Lot insert check failed : "+lotId+" : "+lotId2+" : "+lotId3);
            }
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }

    @Test
    public void testNullLotValue() throws Exception {

        try {
            int lotId = LotManager.getLotIdForValue(null,2);

            fail("Null lot value failed to produce exception");

        } catch (Exception e) {
            //good path
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }


    @Test
    public void testPatternMatcher() throws Exception{

        assertTrue("Invalid pattern match",LotManager.patternMatch("123456","^\\d{6}$"));
        assertFalse("This should have been false",LotManager.patternMatch("12345678","^\\d{6}$"));

    }

    @Test
     public void testLotValidForInventoryId() throws Exception{
         OwdInventory oi = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, 36351);
         oi.setLotPattern("^\\d{6}$");
         HibernateSession.currentSession().flush();

         assertTrue("Invalid pattern match",LotManager.isLotValueValidForInventoryId("123456",36351));
         assertFalse("This should have been a non matching",LotManager.isLotValueValidForInventoryId("12345678",36351));
     }

    @Test
    public void testIsInventoryIdLotControlled() throws  Exception{
        OwdInventory oi = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, 36351);
        oi.setLotTrackingRequired(1);
        HibernateSession.currentSession().flush();

         oi = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, 121672);
        oi.setLotTrackingRequired(1);
        HibernateSession.currentSession().flush();
        oi = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, 113389);
        oi.setLotTrackingRequired(0);
        HibernateSession.currentSession().flush();

        assertTrue("This should be lot controlled",LotManager.isInventoryIdLotControlled(36351));
        assertTrue("This should be lot controlled",LotManager.isInventoryIdLotControlled(121672));
        assertFalse("This should not be lot controlled",LotManager.isInventoryIdLotControlled(113389));
    }

    @Test
    public void testPostAbsoluteAdjustmentWithLots() throws Exception {

        try {
            int lotId = LotManager.getLotIdForValue("hello",20);

            Map<String,Integer> lotMap = new HashMap<String,Integer>();


            lotMap.put("hello",10);

            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(20,53,10, OWDUtilities.getSQLDateForToday(),"testRun1"+ Calendar.getInstance().getTimeInMillis(),"DC1",HibernateSession.currentSession(),lotMap);
            lotMap.remove("hello");
            lotMap.put("world",5);
            lotMap.put("goodbye",3);
            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(20,53,8,OWDUtilities.getSQLDateForToday(),"testRun2"+ Calendar.getInstance().getTimeInMillis(),"DC6",HibernateSession.currentSession(),lotMap);

            HibUtils.commit(HibernateSession.currentSession());
            int skuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");
            int skuDc6Stock = InventoryManager.getStockLevelForFacility(20,"DC6");

            int lotDc1HelloStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1",lotId);
            int lotDc6WorldStock = InventoryManager.getStockLevelForFacilityLot(20,"DC6", LotManager.getLotIdForValue("world",20));
            int lotDc1WorldStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1", LotManager.getLotIdForValue("world",20));
            int lotDc6GoodbyeStock = InventoryManager.getStockLevelForFacilityLot(20,"DC6", LotManager.getLotIdForValue("goodbye",20));


            assertEquals("skuDc1Stock value invalid",skuDc1Stock,10);
            assertEquals("skuDc6Stock value invalid",skuDc6Stock,8);
            assertEquals("lotDc1HelloStock value invalid",lotDc1HelloStock,10);
            assertEquals("lotDc6WorldStock value invalid",lotDc6WorldStock,5);
            assertEquals("lotDc1WorldStock value invalid",lotDc1WorldStock,0);
            assertEquals("lotDc6GoodbyeStock value invalid",lotDc6GoodbyeStock,3);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }

    @Test
    public void testPostAbsoluteAdjustmentNoLots() throws Exception {

        try {



            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(20,53,10, OWDUtilities.getSQLDateForToday(),"testRun1"+ Calendar.getInstance().getTimeInMillis(),"DC1",HibernateSession.currentSession());

            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(20,53,8,OWDUtilities.getSQLDateForToday(),"testRun2"+ Calendar.getInstance().getTimeInMillis(),"DC6",HibernateSession.currentSession());

            HibUtils.commit(HibernateSession.currentSession());
            int skuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");
            int skuDc6Stock = InventoryManager.getStockLevelForFacility(20,"DC6");

            assertEquals("skuDc1Stock value invalid",10,skuDc1Stock);
            assertEquals("skuDc6Stock value invalid",8,skuDc6Stock);

        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }

    @Test
    public void testPostAdjustmentWithLots() throws Exception {

        try {
            int lotId = LotManager.getLotIdForValue("hello",20);

            Map<String,Integer> lotMap = new HashMap<String,Integer>();


            int originalSkuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");
            int originalSkuDc6Stock = InventoryManager.getStockLevelForFacility(20,"DC6");
            int originalLotDc1HelloStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1",lotId);
            int originalLotDc6WorldStock = InventoryManager.getStockLevelForFacilityLot(20,"DC6", LotManager.getLotIdForValue("world",20));
            int originalLotDc1WorldStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1", LotManager.getLotIdForValue("world",20));
            int originalLotDc6GoodbyeStock = InventoryManager.getStockLevelForFacilityLot(20,"DC6", LotManager.getLotIdForValue("goodbye",20));

            lotMap.put("hello",10);

            InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(20,53,10, OWDUtilities.getSQLDateForToday(),"testRun1"+ Calendar.getInstance().getTimeInMillis(),"DC1",HibernateSession.currentSession(),"test",lotMap);
            lotMap.remove("hello");
            lotMap.put("world",5);
            lotMap.put("goodbye",3);
            InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(20,53,8,OWDUtilities.getSQLDateForToday(),"testRun2"+ Calendar.getInstance().getTimeInMillis(),"DC6",HibernateSession.currentSession(),"test",lotMap);

            HibUtils.commit(HibernateSession.currentSession());
            int skuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");
            int skuDc6Stock = InventoryManager.getStockLevelForFacility(20,"DC6");

            int lotDc1HelloStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1",lotId);
            int lotDc6WorldStock = InventoryManager.getStockLevelForFacilityLot(20,"DC6", LotManager.getLotIdForValue("world",20));
            int lotDc1WorldStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1", LotManager.getLotIdForValue("world",20));
            int lotDc6GoodbyeStock = InventoryManager.getStockLevelForFacilityLot(20,"DC6", LotManager.getLotIdForValue("goodbye",20));


            assertEquals("skuDc1Stock value invalid",skuDc1Stock-originalSkuDc1Stock,10);
            assertEquals("skuDc6Stock value invalid",skuDc6Stock-originalSkuDc6Stock,8);
            assertEquals("lotDc1HelloStock value invalid",lotDc1HelloStock-originalLotDc1HelloStock,10);
            assertEquals("lotDc6WorldStock value invalid",lotDc6WorldStock-originalLotDc6WorldStock,5);
            assertEquals("lotDc1WorldStock value invalid",lotDc1WorldStock-originalLotDc1WorldStock,0);
            assertEquals("lotDc6GoodbyeStock value invalid",lotDc6GoodbyeStock-originalLotDc6GoodbyeStock,3);

        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }

    @Test
    public void testPostAdjustmentNoLots() throws Exception {

        try {

            Map<String,Integer> lotMap = new HashMap<String,Integer>();


            int originalSkuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");
            int originalSkuDc6Stock = InventoryManager.getStockLevelForFacility(20,"DC6");


            InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(20,53,10, OWDUtilities.getSQLDateForToday(),"testRun1"+ Calendar.getInstance().getTimeInMillis(),"DC1",HibernateSession.currentSession(),"test");

            InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(20,53,8,OWDUtilities.getSQLDateForToday(),"testRun2"+ Calendar.getInstance().getTimeInMillis(),"DC6",HibernateSession.currentSession(),"test");

            HibUtils.commit(HibernateSession.currentSession());
            int skuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");
            int skuDc6Stock = InventoryManager.getStockLevelForFacility(20,"DC6");


            assertEquals("skuDc1Stock value invalid",skuDc1Stock-originalSkuDc1Stock,10);
            assertEquals("skuDc6Stock value invalid",skuDc6Stock-originalSkuDc6Stock,8);

        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }


    @Test
    public void testPostReceiveWithLots() throws Exception {

        try {


            int originalSkuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");
            int originalLotDc1HelloStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1",LotManager.getLotIdForValue("hello",20));
            int originalLotDc1WorldStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1", LotManager.getLotIdForValue("world",20));


            Asn asn = ASNFactory.getNewAsn(53);
            asn.setFacilityCode("DC1");
            asn.setCreatedDate(Calendar.getInstance().getTime());
            asn.setIsAutorelease(1);
            AsnItem ai = new AsnItem();
            ai.setInventoryFkey(20);
            ai.setInventoryNum("BL-1BR");
            ai.setIsBlind(0);
            ai.setExpected(3);
            ai.setTitle("test");
            ai.setDescription("");
            ai.setReceived(0);
            ai.setNote("");
            ai.setAsn(asn);
            asn.getAsnItems().add(ai);

            Receive rcv = ASNFactory.getNewReceive(asn);
            rcv.setFacilityCode("DC1");
            rcv.setAsn(asn);
            rcv.setCreatedBy("bob");

            rcv.setStartTimestamp(Calendar.getInstance().getTime());
            rcv.setEndTimestamp(Calendar.getInstance().getTime());
            asn.getReceives().add(rcv);

            ReceiveItem ri = rcv.getReceiveItems().iterator().next();
            ri.setInventoryFkey(20);
            ri.setQtyReceive(3);
            ri.setQtyDamage(1);
            ri.setToLocation("");
            ri.setAsnItem(ai);
            ri.setQtyPackslip(3);
            ri.setNotes("") ;


            HibernateSession.currentSession().save(asn);
            HibernateSession.currentSession().save(ai);
            HibernateSession.currentSession().save(rcv);
            HibernateSession.currentSession().save(ri);

            HibUtils.commit(HibernateSession.currentSession());

            OwdLotReceiveItem lotRi = new OwdLotReceiveItem();
            lotRi.setQuantityChange(2);
            lotRi.setQuantityDamage(0);
            lotRi.setReceiveItem(ri);
            lotRi.setLotValue("hello");
            ri.getLots().add(lotRi);
            HibernateSession.currentSession().save(lotRi);

            lotRi = new OwdLotReceiveItem();
            lotRi.setQuantityChange(1);
            lotRi.setQuantityDamage(0);
            lotRi.setReceiveItem(ri);
            lotRi.setLotValue("world");
            ri.getLots().add(lotRi);
            HibernateSession.currentSession().save(ri);
            HibernateSession.currentSession().save(lotRi);


            ASNManager.saveOrUpdateReceive(rcv,true);


            int skuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");

            int lotDc1HelloStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1",LotManager.getLotIdForValue("hello",20));
            int lotDc1WorldStock = InventoryManager.getStockLevelForFacilityLot(20,"DC1", LotManager.getLotIdForValue("world",20));


            assertEquals("skuDc1Stock value invalid",skuDc1Stock-originalSkuDc1Stock,3);
            assertEquals("lotDc1HelloStock value invalid",lotDc1HelloStock-originalLotDc1HelloStock,2);
            assertEquals("lotDc1WorldStock value invalid",lotDc1WorldStock-originalLotDc1WorldStock,1);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }

    @Test
    public void testPostReceiveNoLots() throws Exception {

        try {


            int originalSkuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");


            Asn asn = ASNFactory.getNewAsn(53);
            asn.setFacilityCode("DC1");
            asn.setCreatedDate(Calendar.getInstance().getTime());
            asn.setIsAutorelease(1);
            AsnItem ai = new AsnItem();
            ai.setInventoryFkey(20);
            ai.setInventoryNum("BL-1BR");
            ai.setIsBlind(0);
            ai.setExpected(3);
            ai.setTitle("test");
            ai.setDescription("");
            ai.setReceived(0);
            ai.setNote("");
            ai.setAsn(asn);
            asn.getAsnItems().add(ai);

            Receive rcv = ASNFactory.getNewReceive(asn);
            rcv.setFacilityCode("DC1");
            rcv.setAsn(asn);
            rcv.setCreatedBy("bob");

            rcv.setStartTimestamp(Calendar.getInstance().getTime());
            rcv.setEndTimestamp(Calendar.getInstance().getTime());
            asn.getReceives().add(rcv);

            ReceiveItem ri = rcv.getReceiveItems().iterator().next();
            ri.setInventoryFkey(20);
            ri.setQtyReceive(3);
            ri.setQtyDamage(1);
            ri.setToLocation("");
            ri.setAsnItem(ai);
            ri.setQtyPackslip(3);
            ri.setNotes("") ;


            HibernateSession.currentSession().save(asn);
            HibernateSession.currentSession().save(ai);
            HibernateSession.currentSession().save(rcv);
            HibernateSession.currentSession().save(ri);

            HibUtils.commit(HibernateSession.currentSession());


            ASNManager.saveOrUpdateReceive(rcv,true);


            int skuDc1Stock = InventoryManager.getStockLevelForFacility(20,"DC1");


            assertEquals("skuDc1Stock value invalid",skuDc1Stock-originalSkuDc1Stock,3);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }

    @Test
    public void testShippedAndCanceledQuantities() throws Exception {

        try {
            int lotId = LotManager.getLotIdForValue("hello",287081);

            int packageLineId = 13047801;
            int orderId = 7987141;

            Map<String,Integer> lotMap = new HashMap<String,Integer>();


            lotMap.put("hello",10);

            InventoryManager.postInventoryAdjustmentAsNewAbsoluteValue(287081,112,10, OWDUtilities.getSQLDateForToday(),"testRun1"+ Calendar.getInstance().getTimeInMillis(),"DC1",HibernateSession.currentSession(),lotMap);


            int originalSkuDc1Stock = InventoryManager.getStockLevelForFacility(287081,"DC1");
            int originalLotDc1HelloStock = InventoryManager.getStockLevelForFacilityLot(287081,"DC1",lotId);

            assertEquals("skuDc1Stock initial value invalid",originalSkuDc1Stock,10);
            assertEquals("lotDc1HelloStock initial value invalid",originalLotDc1HelloStock,10);


            PreparedStatement ps = HibernateSession.getPreparedStatement("insert owd_lot_packageline (package_line_fkey, lot_fkey, qty) values(?,?,?);");
            ps.setInt(1,packageLineId);
            ps.setInt(2,lotId);
            ps.setInt(3,8);
            ps.executeUpdate();
            int skuDc1Stock = InventoryManager.getStockLevelForFacility(287081,"DC1");
            int lotDc1HelloStock = InventoryManager.getStockLevelForFacilityLot(287081,"DC1",lotId);

            assertEquals("skuDc1Stock post insert value invalid",skuDc1Stock-originalSkuDc1Stock,0);
            assertEquals("lotDc1HelloStock post insert value invalid",lotDc1HelloStock,2);

            Package.removeLotDataForOrder(orderId);

             skuDc1Stock = InventoryManager.getStockLevelForFacility(287081,"DC1");
             lotDc1HelloStock = InventoryManager.getStockLevelForFacilityLot(287081,"DC1",lotId);
            assertEquals("skuDc1Stock post delete value invalid",skuDc1Stock-originalSkuDc1Stock,0);
            assertEquals("lotDc1HelloStock post delete value invalid",lotDc1HelloStock,10);


        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        } finally {
            HibUtils.rollback(HibernateSession.currentSession());
        }
    }

    @Test
    public void testGetExistingOwdLotValueForString() throws Exception{
        int lotId = LotManager.getLotIdForValue("123456",121672);

        OwdLotValue lotValue =  LotManager.getExistingOwdLotValueForString("123456",121672);
        int lotId2 = lotValue.getId();
           assertEquals("lotvlaue inventory Id combo not found",lotId,lotId2);



    }



}
