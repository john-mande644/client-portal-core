package com.owd.dc.manifest.api;

import com.owd.connectship.soap.DataDictionary;
import com.owd.connectship.xml.ShipmentRequest.PKG;
import com.owd.dc.manifest.api.internal.ConnectShipShipment;

import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Aug 14, 2008
 * Time: 10:00:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class MultiPackageShipment {
  String orderNum;
    Map<String, PackageData> pkgMap = new TreeMap<String, PackageData>();
    int packagesInOrder = 0;

    public Collection<PackageData> getPackageList() {
        return pkgMap.values();
    }

    public MultiPackageShipment(String OWDOrderRef, int packs) {
        packagesInOrder = packs;
        orderNum = OWDOrderRef;
    }

    boolean isReadyToShip() {
        return packagesInOrder == pkgMap.size();
    }

    public PackageData getPackageForBarcode(String packageBarcode) {
        return pkgMap.get(packageBarcode);
    }
   public void removePackageElement(String packageBarcode){
          try{
              pkgMap.remove(packageBarcode);
          } catch (Exception e){

          }
   }
   public void addNewPackageElement(String packageBarcode, DataDictionary pkg, int sequenceID, ConnectShipShipment shipment) throws Exception {

       if (packagesInOrder == pkgMap.size())
            throw new Exception("This shipment has already had all its packages scanned!");

        if (pkg == null) throw new Exception("Invalid package object sent to addNewPackageElement!");

        if (packageBarcode == null) throw new Exception("Invalid package barcode sent to addNewPackageElement!");

        if (pkgMap.get(packageBarcode) != null) throw new Exception("This package has already been scanned!");

        pkgMap.put(packageBarcode, new PackageData(packageBarcode, sequenceID, pkg, shipment));
    }

    /*public void updatePackageShipmentDataInsuranceValues() throws Exception
    {
       if(packagesInOrder != pkgMap.size())
       {
           throw new Exception("Can't update declared value until all packages scanned");
       }

        Iterator it = pkgMap.values().iterator();
        while(it.hasNext())
        {
            PackageData pkg = (PackageData) it.next();
            ConnectShipShipment ship = pkg.shipment;
            double packageCustomsValue =0.00;
            if(ship.getValue("OWDCUSTOMSSHIPMENTVALUE")==null)
            {

            }
        }

    }*/

  public  class PackageData {
        String packageBarcode = "";
        int packageSequenceNumber = 1;
        public DataDictionary pkgData = null;
        boolean isPrinted = false;
      public  ConnectShipShipment shipment = null;

        public PackageData(String barcode, int sequenceID, DataDictionary data, ConnectShipShipment pkgShipment) {
            packageBarcode = barcode;
            packageSequenceNumber = sequenceID;
            pkgData = data;
            shipment = pkgShipment;
        }


    }


}
