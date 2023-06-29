package com.owd.dc.warehouse.vendorCompliance.labelsMaker.inventoryLabels;

import com.owd.dc.warehouse.vendorCompliance.labelsMaker.printingUtilities.printToZebraNetworkPrinter;

/**
 * Created by danny on 10/23/2016.
 */
public class vendorComplianceInventoryLabelBase implements vendorComplianceInventoryLabel{


    public boolean printInventoryLabels(String id, String qty, String printer) throws Exception{
        boolean success = false;


        String label = getLabelString(id);
        printToZebraNetworkPrinter.sendToPrinter(label,printer,Integer.parseInt(qty));






        return success;
    }

    public String getLabelString(String id) throws Exception{

        throw new Exception("Please define a get Labe for your Label class");
    }



}
