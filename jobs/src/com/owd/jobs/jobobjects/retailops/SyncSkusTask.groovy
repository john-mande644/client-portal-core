package com.owd.jobs.jobobjects.retailops

import com.owd.hibernate.generated.OwdInventory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.*

/**
 * Created by stewartbuskirk1 on 1/16/15.
 */
public class SyncSkusTask implements Runnable {
private final static Logger log =  LogManager.getLogger();
    private int itemId;
    private String sku;
    private RetailOpsApi api;

    public SyncSkusTask(int data, String sku, RetailOpsApi api) {
        this.itemId = data;
        this.sku=sku;
        this.api=api;
    }

    public void run() {
        try {
            api.getNewEndpoint().post(path: api.getSkusUrl,
                    body: [sku_string: sku, with_inventory: true, vendor_id: api.vendorId, limit: 99999, start: 0],
            requestContentType: 'application/json'
            ) { resp, reader ->

                if (reader.images != null) {
                    println reader.images?.size() + ' images found!'

                    OwdInventory item = null;

                    if (reader.images.size() > 0) {
                        println reader.images.get(0).url_thumb + '  found!'
                        item = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,itemId)
                        item.setImageThumbUrl(reader.images.get(0).url_thumb)
                        item.setImageUrl(reader.images.get(0).url_thumb)
                        HibernateSession.currentSession().save(item)


                    }

                    if (reader.images.size() > 1) {
                        println reader.images.get(1).url_thumb + '  found!'
                        if (item == null) {
                            item = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class,itemId)
                        }
                        // item.setImageThumbUrl(reader.images.get(1).url_thumb)
                        item.setImageUrl(reader.images.get(1).url_thumb)
                        HibernateSession.currentSession().save(item)
                    }
                    HibUtils.commit(HibernateSession.currentSession())
                    // println reader.toString()
                    //  println prettyPrint(toJson(reader))

                }
            }
        } catch (Exception ex) {
            log.debug("SYNCSKU EPIC FAIL!!!!");
            ex.printStackTrace();
        } finally {
            //log.debug("*** Unable to verify or correct: "+ex.getMessage()+" for order id "+id);
            try{HibUtils.rollback(HibernateSession.currentSession());     }catch(Exception exx){exx.printStackTrace();}
            HibernateSession.closeSession();
            HibernateFogbugzSession.closeSession();
            HibernateClientReportsSession.closeSession();
        }
    }
}
