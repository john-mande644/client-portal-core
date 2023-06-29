package com.owd.alittlePlaying.inventory;

/**
 * Created by danny on 7/5/2018.
 */
public class cloningTask implements Runnable {

    private Integer inventoryId;
    private Integer newClientId;
    private String newSku;

    cloningTask(Integer iId, Integer cId, String sku){
        inventoryId = iId;
        newClientId = cId;
        newSku = sku;

    }

    public void run(){

        clonesSkusToNewClient.cloneTheSkuNewSku(inventoryId,newClientId,newSku);

    }
}
