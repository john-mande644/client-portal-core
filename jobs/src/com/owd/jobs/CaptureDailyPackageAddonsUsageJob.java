package com.owd.jobs;

import com.owd.core.OWDUtilities;
import com.owd.core.business.order.packing.BoxData;
import com.owd.core.managers.InventoryManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdInventoryOh;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import java.text.SimpleDateFormat;
import java.util.*;

public class CaptureDailyPackageAddonsUsageJob extends OWDStatefulJob{
    private final static Logger log =  LogManager.getLogger();

    private static int offset = -1;

    public static void main(String[] args) {
        run();
    }

    public void internalExecute() {
        try {
                log.info("starting internalExecute CaptureDailyPackageAddonsUsageJob");
                int date = Calendar.DATE;

               ArrayList<BoxData> boxData = getAddonsUsageForDay(date);
               saveChangesForBoxDataList(boxData, date);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static ArrayList<BoxData> getAddonsUsageForDay(int date){
        try{
            log.info("start getAddonsUsageForDay");

            Query query = HibernateSession.currentSession().createSQLQuery("select sum(pa.quantity) as qty, oo.shipped_on as shipDate, " +
                    "oo.facility_code as facilityCode, " +
                    "oi.inventory_id as inventoryId, " +
                    "case when oi.client_fkey = 0 then 9999 else\n" +
                    "case when oi.client_fkey is null then\n" +
                    "case\n" +
                    "when oo.facility_code = 'DC1' then 55\n" +
                    "when oo.facility_code = 'DC6' then 488\n" +
                    "when oo.facility_code = 'DC7' then 534\n" +
                    "end\n" +
                    "else\n" +
                    "oi.client_fkey\n" +
                    "end end\n" +
                    "as clientId from package_addons pa\n" +
                    "join dbo.supply_tracking st\n" +
                    "on st.id = pa.supply_tracking_fkey\n" +
                    "join dbo.package p\n" +
                    "on p.id = convert(INT,REPLACE(pa.package_fkey,char(0),''))\n" +
                    "join dbo.package_order po \n" +
                    "on po.id = p.package_order_fkey\n" +
                    "join dbo.owd_order oo \n" +
                    "on oo.order_id = po.owd_order_fkey\n" +
                    "join dbo.owd_inventory oi \n" +
                    "on oi.inventory_id = st.inventory_id\n" +
                    "where oo.shipped_on = :date and oo.is_void=0\n" +
                    "group by oi.inventory_id,oo.shipped_on, oo.facility_code, oi.client_fkey");
            log.info("set query");
            query.setString("date", OWDUtilities.getSQLDateNoTimeForAdjustedDate(date,offset))
                    .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            log.info("set string and result Transformer");
            List list = query.list();
            log.info("ran query");
            ArrayList<BoxData> supplyList = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for(int i = 0; i < list.size(); i++){
                Map map = (Map)(list.get(i));
                BoxData box = new BoxData(
                                (int) map.get("inventoryId"),
                                (int) map.get("clientId"),
                                (int) map.get("qty"),
                                dateFormat.format(map.get("shipDate")),
                                (String)map.get("facilityCode")
                        );
                printBoxData("looking up OH amount",box);
                int id = box.getInventoryId();
                OwdInventory inventory = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,id);
                OwdInventoryOh onHandRecords = inventory.getOwdInventoryOh();
                if(onHandRecords.getQtyOnHand() < box.getQty()){
                    box.setQty(onHandRecords.getQtyOnHand() * -1);
                }else{
                    box.setQty(box.getQty() * -1);
                }
                supplyList.add(box);
                log.info("adjustment amount: " + box.getQty());
            }
            return supplyList;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static void saveChangesForBoxDataList(List<BoxData> boxData, int date){
        try {
            if(boxData != null){
                for (BoxData box : boxData) {
                    printBoxData("Saving",box);
                    try {
                        InventoryManager.postInventoryAdjustmentAsAdjustmentToCurrentValue(box.getInventoryId(), box.getClientId(), (int) box.getQty(),
                                OWDUtilities.getSQLDateNoTimeForAdjustedDate(date, offset, true),
                                "ADDBOXADJ-" + OWDUtilities.getSQLDateNoTimeForAdjustedDate(date, offset, true) + "-" + box.getClientId() + "-" + box.getInventoryId(),
                                box.getFacilityCode(), HibernateSession.currentSession());
                        HibUtils.commit(HibernateSession.currentSession());
                    } catch (Exception ex) {
                        if (ex.getMessage().contains("already exists")) {
                            log.debug(ex.getMessage() + " for " + "ADDBOXADJ-" + OWDUtilities.getSQLDateNoTimeForAdjustedDate(date, offset, true) + "-" + box.getClientId() + "-" + box.getInventoryId());
                        } else {
                            HibernateSession.closeSession();
                            ex.printStackTrace();
                        }
                        HibUtils.rollback(HibernateSession.currentSession());
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static void printBoxData(String label, BoxData box){
        log.info(label);
        log.info(label);
        log.info("Inventory:    " + box.getInventoryId());
        log.info("clientId:     " + box.getClientId());
        log.info("FacilityCode: " + box.getFacilityCode());
        log.info("ShipDate:     " + box.getShipDate());
        log.info("Qty:          " + box.getQty());
    }
}
