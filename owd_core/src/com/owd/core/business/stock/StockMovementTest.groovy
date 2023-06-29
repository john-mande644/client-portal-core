package com.owd.core.business.stock

import com.owd.core.business.locations.addNewLocation
import com.owd.core.business.locations.removeLocation
import com.owd.core.managers.DataSourceManager
import com.owd.core.managers.FacilitiesManager
import com.owd.hibernate.HibUtils
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdInventory
import com.owd.hibernate.generated.WStock
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.hibernate.Query
import org.hibernate.Session
import org.junit.*

import static org.junit.Assert.*

/**
 * Created by stewartbuskirk1 on 6/26/15.
 */
class StockMovementTest {
    private final static Logger log =  LogManager.getLogger();

    static Session sess = null;
    static Map<String, Integer> locMap = new HashMap<String, Integer>()
    static OwdInventory item1 = null

    public StockMovementTest() {
        super();
        System.setProperty("com.owd.environment", "test")

        log.debug("StockMovementTest init");
    }

    /**
     * The fixture set up called once for this class.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        System.setProperty("com.owd.environment", "test")
        log.debug("setUpClass");
        //  println "Environment="+System.getProperty("com.owd.environment")
        sess = HibernateSession.currentSession();

        println 'add zone'
        int newZoneId = addNewLocation.addLocation(sess, '' + FacilitiesManager.getFacilityForCode("DC1").getWlocNodeFkey(), 'TestZone' + Calendar.getInstance().getTimeInMillis(), '6', 'test', '127.0.0.1')
        locMap.put('zone', newZoneId)
        println 'add aisle'
        int newAisleId = addNewLocation.addLocation(sess, '' + newZoneId, 'TestAisle', '7', 'test', '127.0.0.1')
        locMap.put('aisle', newAisleId)
        println 'add rack'
        int newRackId = addNewLocation.addLocation(sess, '' + newAisleId, 'TestShelf', '8', 'test', '127.0.0.1')
        locMap.put('rack', newRackId)
        println 'add shelf'
        int newShelfId = addNewLocation.addLocation(sess, '' + newRackId, 'TestShelf', '9', 'test', '127.0.0.1')
        locMap.put('shelf', newShelfId)
        println 'add bin1'
        int newBin1Id = addNewLocation.addLocation(sess, '' + newShelfId, 'TestBin1', '10', 'test', '127.0.0.1')
        locMap.put('bin1', newBin1Id)
        println 'add bin2'
        int newBin2Id = addNewLocation.addLocation(sess, '' + newShelfId, 'TestBin2', '10', 'test', '127.0.0.1')
        locMap.put('bin2', newBin2Id)
        println 'add floor'
        int newFloorId = addNewLocation.addLocation(sess, '' + locMap.get('zone'), 'TestFloor', '17', 'test', '127.0.0.1')
        locMap.put('floor', newFloorId)
        println 'load inventory'

        item1 = (OwdInventory) sess.load(OwdInventory.class, 12341);


        HibUtils.commit(sess);

    }

    /**
     * The fixture clean up called once for this class.
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
        log.debug("tearDownClass");

        if (removeLocation.remove(sess, '' + locMap.get('zone'), "JUnit Test", "test", "127.0.0.1")) {

            HibUtils.commit(sess);
        }
    }

    /**
     * The fixture set up called before every test method.
     */
    @Before
    public void setUp() throws Exception {
        //  System.setProperty("com.owd.environment","test")
        log.debug("setUp");
        //  println "Environment="+System.getProperty("com.owd.environment")

        assertEquals(DataSourceManager.getMasterEnvironment(), "test")
    }

    /**
     * The fixture clean up called after every test method.
     */
    @After
    public void tearDown() throws Exception {
        log.debug("tearDown");
        for (int lid : locMap.values()) {
            deleteInventoryLocationRecord(sess, lid, item1.getInventoryId())
        }

        deleteInventoryLocationRecord(sess, StockMovement.getUnknownLocationForCurrentLocation(locMap.get('floor')).getId(), item1.getInventoryId())

        HibUtils.commit(sess);

    }

    @Test
    public void testGetStockIfNone() {
        log.debug("testGetStockIfNone");

        WStock stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('bin1'))
        assertNull(stock)
    }

    @Test
    public void testReceiveStockToFloor() {
        log.debug("testReceiveStockToFloor");

        StockMovement.receiveStock(item1.getInventoryId(), locMap.get('floor'), 100, null, null, null)
        WStock stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        HibUtils.commit(sess);

        assertNotNull(stock)
        assertEquals(100, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

    }

    @Test
    public void testReceiveStockToFloorAndMoveAllToBin() {
        log.debug("testReceiveStockToFloorAndMoveAllToBin");

        StockMovement.receiveStock(item1.getInventoryId(), locMap.get('floor'), 100, null, null, null)
        WStock stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        HibUtils.commit(sess);

        assertNotNull(stock)
        assertEquals(100, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

        StockMovement.moveStockToNewLocation(item1.getInventoryId(), locMap.get('floor'), locMap.get('bin1'), 100, null)
        stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        WStock binStock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('bin1'))
        HibUtils.commit(sess);

        assertNull(stock)
        assertNotNull(binStock)
        assertEquals(100, binStock.getQuantity())
        assertEquals(item1.getInventoryId(), binStock.getInventory().getInventoryId())
        assertNull(binStock.getLot())


    }

    @Test
    public void testReceiveStockToFloorAndMovePartialToBin() {
        log.debug("testReceiveStockToFloorAndMovePartialToBin");

        StockMovement.receiveStock(item1.getInventoryId(), locMap.get('floor'), 100, null, null, null)
        WStock stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        HibUtils.commit(sess);

        assertNotNull(stock)
        assertEquals(100, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

        StockMovement.moveStockToNewLocation(item1.getInventoryId(), locMap.get('floor'), locMap.get('bin1'), 25, null)
        stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        WStock binStock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('bin1'))
        HibUtils.commit(sess);

        assertNotNull(stock)
        assertEquals(75, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

        assertNotNull(binStock)
        assertEquals(25, binStock.getQuantity())
        assertEquals(item1.getInventoryId(), binStock.getInventory().getInventoryId())
        assertNull(binStock.getLot())


    }

    @Test
    public void testShortCount() {
        log.debug("testShortCount");

        StockMovement.receiveStock(item1.getInventoryId(), locMap.get('floor'), 100, null, null, null)
        WStock stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        HibUtils.commit(sess);

        assertNotNull(stock)
        assertEquals(100, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

        boolean goodCount = StockMovement.checkStockCount(item1.getInventoryId(), locMap.get('floor'), 10, null, null, null)
        assertFalse(goodCount)
        goodCount = StockMovement.checkStockCount(item1.getInventoryId(), locMap.get('floor'), 100, null, null, null)
        assertTrue(goodCount)


        StockMovement.saveStockCount(item1.getInventoryId(), locMap.get('floor'), 10, null, null, null)
        HibUtils.commit(sess)

        stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        assertNotNull(stock)
        assertEquals(10, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

        stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), StockMovement.getUnknownLocationForCurrentLocation(locMap.get('floor')).getId())
        assertNotNull(stock)
        assertEquals(90, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

    }

    @Test
    public void testLongCount() {
        log.debug("testLongCount");

        StockMovement.receiveStock(item1.getInventoryId(), locMap.get('floor'), 100, null, null, null)
        WStock stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        HibUtils.commit(sess);

        assertNotNull(stock)
        assertEquals(100, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

        boolean goodCount = StockMovement.checkStockCount(item1.getInventoryId(), locMap.get('floor'), 110, null, null, null)
        assertFalse(goodCount)
        goodCount = StockMovement.checkStockCount(item1.getInventoryId(), locMap.get('floor'), 100, null, null, null)
        assertTrue(goodCount)


        StockMovement.saveStockCount(item1.getInventoryId(), locMap.get('floor'), 110, null, null, null)
        HibUtils.commit(sess)

        stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        assertNotNull(stock)
        assertEquals(110, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

        stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), StockMovement.getUnknownLocationForCurrentLocation(locMap.get('floor')).getId())
        assertNotNull(stock)
        assertEquals(-10, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

    }

    @Test
    public void testMergeUnknownToZero() {
        log.debug("testMergeUnknownToZero");

        StockMovement.receiveStock(item1.getInventoryId(), locMap.get('floor'), 100, null, null, null)
        WStock stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor'))
        HibUtils.commit(sess);

        assertNotNull(stock)
        assertEquals(100, stock.getQuantity())
        assertEquals(item1.getInventoryId(), stock.getInventory().getInventoryId())
        assertNull(stock.getLot())

        StockMovement.saveStockCount(item1.getInventoryId(), locMap.get('floor'), 110, null, null, null)
        HibUtils.commit(sess)
        assertEquals(110, StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor')).getQuantity())
        assertEquals(-10, StockMovement.getStockForInventoryLocation(item1.getInventoryId(), StockMovement.getUnknownLocationForCurrentLocation(locMap.get('floor')).getId()).getQuantity())

        StockMovement.saveStockCount(item1.getInventoryId(), locMap.get('floor'), 90, null, null, null)
        HibUtils.commit(sess)
        assertEquals(90, StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor')).getQuantity())
        assertEquals(10, StockMovement.getStockForInventoryLocation(item1.getInventoryId(), StockMovement.getUnknownLocationForCurrentLocation(locMap.get('floor')).getId()).getQuantity())

        StockMovement.saveStockCount(item1.getInventoryId(), locMap.get('floor'), 100, null, null, null)
        HibUtils.commit(sess)
        assertEquals(100, StockMovement.getStockForInventoryLocation(item1.getInventoryId(), locMap.get('floor')).getQuantity())


        stock = StockMovement.getStockForInventoryLocation(item1.getInventoryId(), StockMovement.getUnknownLocationForCurrentLocation(locMap.get('floor')).getId())
        assertNull(stock)


    }

    //never ever do this in production
    private static void deleteInventoryLocationRecord(Session sess, int locId, int invId) throws Exception {
        boolean success = false;
        Query inv = sess.createSQLQuery("delete from w_location_inventory where ixNode = :id and ixInventory = :invId");
        inv.setParameter("id", locId);
        inv.setParameter("invId", invId);
        int i = inv.executeUpdate();


    }

}
