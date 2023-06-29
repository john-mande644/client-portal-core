package com.owd.dc.warehouse.licensePlate.labelMaker;

import com.owd.core.dbUtilities;
import com.owd.dc.warehouse.licensePlate.licensePlateUtilities;
import com.owd.dc.warehouse.vendorCompliance.labelsMaker.printingUtilities.printToZebraNetworkPrinter;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdBoxtypes;

/**
 * Created by danny on 12/3/2016.
 */
public class packagingLicensePlate {

    public static final String packagingPrefix = "PP";

    
    public static void main(String args[]) {

        int i = 0;
        try {


            while (i < 14) {


                printQtyOfLabels(537, 80, "zebra2.dc5.owd.com");
                System.out.println("just did lap " + i);
                Thread.sleep(120000);
                i++;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static String printQtyOfLabels(Integer boxId, int qty,String printer){
        String response = "failed";

        try{
            OwdBoxtypes box = (OwdBoxtypes) HibernateSession.currentSession().load(OwdBoxtypes.class, boxId );
            System.out.println(box.getName());

            int i = 0;
            StringBuilder labelQueue = new StringBuilder();
            boolean lastLabel = false;
            while (i < qty){


                String id = licensePlateUtilities.getNextPackagingId();
                String barcode = licensePlateUtilities.getPackagingLicensePlateBarcode(packagingPrefix,boxId,id);
                if(qty-i == 1){
                    lastLabel = true;
                }


                String label = licensePlateUtilities.getPackagingLicensePlateLabel(barcode,id,box.getName(),lastLabel);
                System.out.println(label);
                labelQueue.append(label);


                if(i%10 == 0 && i>1){

                    printToZebraNetworkPrinter.sendToPrinter(labelQueue.toString(),printer,1);
                    labelQueue = new StringBuilder();
                }



                i++;
            }
            if(labelQueue.length()>0){
                System.out.println(labelQueue.toString());
                printToZebraNetworkPrinter.sendToPrinter(labelQueue.toString(),printer,1);
            }









        }catch (Exception ex ){
            ex.printStackTrace();
        }




response = "success";

       return response;
    }


}
