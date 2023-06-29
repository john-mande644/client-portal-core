package com.owd.core.business.stock

import com.owd.core.managers.FacilitiesManager
import com.owd.hibernate.*
import com.owd.hibernate.generated.OwdInventory
import com.owd.hibernate.generated.OwdLineItem
import com.owd.hibernate.generated.OwdOrder
import com.owd.hibernate.generated.WLocation
import com.owd.hibernate.generated.WStock

/**
 * Created by stewartbuskirk1 on 6/25/15.
 */
class StockMovement {

    //todo add facility ID column to all location nodes?
    //todo manage availability of stock to main inventory numbers
    //todo note that Hibernate session must be committed by caller


    static final int kUnknownLocationType = 5 //Facility type
    static final String kDeleteStockEventType = "DELETE"
    static final String kReceiveStockEventType = "RECEIVE"
    static final String kShipStockEventType = "SHIPMENT"
    static final String kMoveStockEventType = "MOVE"
    static final String kCountStockEventType = "COUNT"


    public
    static moveStockToNewLocation(int inventoryId, int oldLocationId, int newLocationId, int units, Map<String, String> eventAttributes) {

        WStock oldStock = getStockForInventoryLocation(inventoryId, oldLocationId)
        WStock newStock = getStockForInventoryLocation(inventoryId, newLocationId)
        if (oldStock == null) {
            throw new Exception("No stock entry found for inventory id " + inventoryId + " and location id " + oldLocationId)

        }
        if (newStock == null) {
            newStock = new WStock()
            newStock.setInventory(oldStock.getInventory())
            newStock.setLot(oldStock.getLot())
            newStock.setExpires(oldStock.getExpires())
            newStock.setQuantity((Integer) 0)
            //todo verify new location can accept inventory
            WLocation location = HibernateSession.currentSession().load(WLocation.class, newLocationId);

            newStock.setLocation(location)
            location.getCurrentStock().add(newStock)
            newStock.getInventory().getStockLocations().add(newStock)
            HibernateSession.currentSession().saveOrUpdate(newStock);
            HibernateSession.currentSession().saveOrUpdate(location);
            HibernateSession.currentSession().saveOrUpdate(newStock.getInventory());
        }
        mergeStock(oldStock, newStock, units, eventAttributes)
        logStockEvent(kMoveStockEventType, eventAttributes)


    }

    public
    static boolean checkStockCount(int inventoryId, int locationId, int quantity, String lotCode, Date expiration, Map<String, String> eventAttributes) {
        //todo check if lotcode/expiration required by item

        WStock existing = getStockForInventoryLocation(inventoryId, locationId)

        if (existing == null || existing.getQuantity() != quantity || !(Objects.equals(lotCode, lotCode)) || !(Objects.equals(expiration, expiration))) {
            return false
        }
        return true
    }

    public
    static void saveStockCount(int inventoryId, int locationId, int quantity, String lotCode, Date expiration, Map<String, String> eventAttributes) {
        //todo check if lotcode/expiration required by item

        WLocation unknownLoc = getUnknownLocationForCurrentLocation(locationId)
        WStock existing = getStockForInventoryLocation(inventoryId, locationId)

        if (!checkStockCount(inventoryId, locationId, quantity, lotCode, expiration, eventAttributes)) {
            int originalQuantity = existing.getQuantity()
            //replace old values with new
            if (quantity == 0) {
                clearStock(existing)
            } else {
                existing.setExpires(expiration)
                existing.setLot(lotCode)
                existing.setQuantity(quantity)
                HibernateSession.currentSession().saveOrUpdate(existing)
            }

            int unknownTransferQty = quantity - originalQuantity
            if (unknownTransferQty != 0) {
                WStock existingUnknownStock = getStockForInventoryLocation(inventoryId, unknownLoc.getId())
                WStock stock = new WStock()
                stock.setInventory(existing.getInventory())
                stock.setLot(null)
                stock.setExpires(null)
                stock.setQuantity(-1 * unknownTransferQty) //can be negative!!!

                if (existingUnknownStock == null) {
                    stock.setLocation(unknownLoc)
                    unknownLoc.getCurrentStock().add(stock)
                    existing.getInventory().getStockLocations().add(stock)
                    HibernateSession.currentSession().saveOrUpdate(stock);
                    HibernateSession.currentSession().saveOrUpdate(unknownLoc);
                    HibernateSession.currentSession().saveOrUpdate(existing.getInventory());
                } else {
                    mergeStock(stock, existingUnknownStock, stock.quantity, eventAttributes)
                }


            }

        }

        logStockEvent(kCountStockEventType, eventAttributes)

    }


    protected static WLocation getUnknownLocationForCurrentLocation(int currentLocationId) {
        return HibernateSession.currentSession().load(WLocation.class, FacilitiesManager.getFacilityForLocationId(currentLocationId).getWlocNodeFkey())

    }

    static List<WStock> getStockListForLocation(int locationId) throws Exception {

        List<WStock> stockList = HibernateSession.currentSession().createQuery("from WStock where location.id=:lid").setParameter(1, locationId).list();
        if (!stockList || stockList.size() == 0) {
            return new ArrayList<WStock>();
        }

        return stockList
    }

    static WStock getStockForInventoryLocation(int inventoryId, int locationId) throws Exception {

        List<WStock> stockList = HibernateSession.currentSession().createQuery("from WStock where inventory.id=:iid and location.id=:lid").setParameter('iid', inventoryId).setParameter('lid', locationId).list();
        if (!stockList || stockList.size() == 0) {
            return null;
        }
        if (stockList.size() > 1) {
            throw new Exception("Multiple stock entries found for inventory id " + inventoryId + " and location id " + locationId)
        }

        return stockList.get(0)
    }

    public
    static void receiveStock(int inventoryId, int toLocationId, long quantity, String lotCode, Date expiration, Map<String, String> eventAttributes) {
        //todo ensure transactional consistency with receive/receiveitem update
        OwdInventory item = HibernateSession.currentSession().load(OwdInventory.class, inventoryId);
        WLocation location = HibernateSession.currentSession().load(WLocation.class, toLocationId);

        WStock existing = getStockForInventoryLocation(inventoryId, toLocationId)
        WStock stock = new WStock()
        stock.setInventory(item)
        stock.setLot(lotCode)
        stock.setExpires(expiration)
        stock.setQuantity((Integer) quantity)
        if (existing == null) {
            //todo verify new location can accept inventory
            stock.setLocation(location)
            location.getCurrentStock().add(stock)
            item.getStockLocations().add(stock)
            HibernateSession.currentSession().saveOrUpdate(stock);
            HibernateSession.currentSession().saveOrUpdate(location);
            HibernateSession.currentSession().saveOrUpdate(item);
        } else {
            mergeStock(stock, existing, stock.quantity, eventAttributes)
        }
        logStockEvent(kReceiveStockEventType, eventAttributes)

    }


    public static void shipOrderStock(int locationId, OwdOrder order, Map<String, String> eventAttributes) {

        //get list of stock entries for ship location
        List<WStock> stockList = getStockListForLocation(locationId)
        Map<Integer, Integer> shippedMap = new HashMap<Integer, Integer>()

        //summarize quantities being shipped in Map indexed by inventory ID
        for (OwdLineItem line : order.getLineitems()) {
            if (line.getQuantityActual() > 0) {
                if (!(shippedMap.keySet().contains(line.getOwdInventory().getInventoryId()))) {
                    shippedMap.put(line.getOwdInventory().getInventoryId(), 0)
                }
                shippedMap.put(line.getOwdInventory().getInventoryId(), shippedMap.get(line.getOwdInventory().getInventoryId()) + line.getQuantityActual())
            }
        }
        //subtract quantities shipped from stock entry list
        for (Integer invId : shippedMap.keySet()) {
            for (WStock stock : stockList()) {
                if (stock.getInventory().getInventoryId() == invId) {
                    stock.setQuantity(stock.getQuantity() - shippedMap.get(invId))
                }
            }
        }
        //save or clear stock entries depending on whether any quantity remains
        for (WStock stock : stockList()) {
            if (stock.getQuantity() < 0) {
                throw new Exception("Unable to complete stock changes due to order shipment - quantity shipped greater than quantity available for order id " + order.getOrderId() + " and inventory id " + stock.getInventory().getInventoryId())
            } else if (stock.getQuantity() == 0) {
                clearStock(stock)
            } else {
                HibernateSession.currentSession().saveOrUpdate(stock);
            }
        }
        logStockEvent(kShipStockEventType, eventAttributes)

    }


    public static void deleteStock(WStock stock, Map<String, String> eventAttributes) {
        if (stock.getLocation().getLocationType() != kUnknownLocationType) {
            throw new Exception("Stock can only be deleted from the UNKNOWN location")
        }

        HibernateSession.currentSession().delete(stock);
        logStockEvent(kDeleteStockEventType, eventAttributes)

    }

    private
    static void mergeStock(WStock fromStock, WStock toStock, Integer quantity, Map<String, String> eventAttributes) {

        //todo verify merge OK (lotcodes, etc.)

        if (fromStock.getQuantity() < quantity) {
            throw new Exception("Quantity requested for merge is more than source stock entry quantity available")
        }

        toStock.setQuantity(toStock.getQuantity() + quantity)
        fromStock.setQuantity(fromStock.getQuantity() - quantity)
        if (toStock.getQuantity() == 0) {
            clearStock(toStock)
        } else {
            HibernateSession.currentSession().saveOrUpdate(toStock);
        }
        if (fromStock.getQuantity() == 0) {
            clearStock(fromStock)
        } else {
            HibernateSession.currentSession().saveOrUpdate(fromStock);
        }


    }

    private static void clearStock(WStock fromStock) {
        fromStock.getInventory()?.getStockLocations()?.remove(fromStock)
        if (null != (fromStock.getInventory()?.getStockLocations())) {
            HibernateSession.currentSession().saveOrUpdate(fromStock.getInventory());
        }
        fromStock.getLocation()?.getCurrentStock()?.remove(fromStock)
        if (null != (fromStock.getLocation()?.getCurrentStock())) {
            HibernateSession.currentSession().saveOrUpdate(fromStock.getLocation());
        }
        HibernateSession.currentSession().delete(fromStock);
    }

    private static void logStockEvent(String eventType, Map<String, String> eventAttributes) {
        //todo
    }


}
