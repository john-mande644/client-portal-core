package com.owd.dc.warehouse.vendorCompliance.labelsMaker.inventoryLabels;

/**
 * Created by danny on 10/23/2016.
 */
public interface vendorComplianceInventoryLabel {




    public boolean printInventoryLabels(String id, String qty, String printer) throws Exception;
}
