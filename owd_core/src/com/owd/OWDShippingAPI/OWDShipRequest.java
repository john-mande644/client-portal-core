package com.owd.OWDShippingAPI;

import com.owd.OWDShippingAPI.Models.*;
import com.owd.OWDShippingAPI.Models.Package;
import com.owd.connectship.xml.api.OWDShippingRequest.PACKAGE;
import com.owd.connectship.xml.api.OWDShippingRequest.SHIPREQUEST;
import com.owd.connectship.xml.api.OWDShippingResponse.INTLDOCDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.connectship.xml.api.OWDShippingResponse.SHIPMENT;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 11/16/2019.
 */
public class OWDShipRequest {


    /*  Example of the data coming through. two package shipment
    <OWDSHIPPINGREQUEST xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" api_version="55" client_id="55" client_authorization="51">
  <SHIPREQUEST LOCATIONCODE="DC6" SHIPPER="DC6" LABELPRINTER="Zebra.ZebraZ4Mplus">
    <PACKAGELIST>
      <PACKAGE>
        <BARCODE>p13774803*28232700R1*b1</BARCODE>
        <WEIGHT_LBS>23.82</WEIGHT_LBS>
      </PACKAGE>
      <PACKAGE>
        <BARCODE>p13774803*28232700R1*b2</BARCODE>
        <WEIGHT_LBS>14.79</WEIGHT_LBS>
      </PACKAGE>
    </PACKAGELIST>
  </SHIPREQUEST>
</OWDSHIPPINGREQUEST>
     */
    public List<SHIPMENT> processShipmentReturnXML(SHIPREQUEST shipRequest) throws Exception{
        List<SHIPMENT> shipments = new ArrayList<>();
        List<Package> packages = new ArrayList<>();
        for(PACKAGE p : shipRequest.getPACKAGELIST().getPACKAGE()){
            Package pack = new Package();
            pack.setPackageReference(p.getBARCODE());
            pack.setWeight(Double.parseDouble(p.getWEIGHT_LBS()));
            packages.add(pack);
        }

        ShipShipmentResponse response = processShipments(packages,shipRequest.getLOCATIONCODE());


        shipments = loadXMLResponse(response);

        return shipments;
    }


    /*
    Example of xml returned data
    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<OWDSHIPPINGRESPONSE error_response="" error_type="SUCCESS">
    <SHIPRESPONSE>
        <SHIPMENT>
            <MSN>18930847</MSN>
            <SHIPMETHOD>UPS GROUND</SHIPMETHOD>
            <LABELDATA copies_needed="1">flNEMjANXlhBDV5QVzgxMg1eQ0kxMw1eTEgwLDANXlBSNCw0LDINXkZUMCwxMDE2XkdCODA5LDAsMjBeRlMNXkZUMCw3NTheR0I4MDksMCwyXkZTDV5GVDAsNjU2XkdCODA5LDAsMjBeRlMNXkZUMjQ0LDYzNl5HQjAsMjE1LDJeRlMNXkZUMCw0MjFeR0I4MDksMCwyXkZTDV5GVDQwNiw1MV5BME4sMzcsNDZeRkQ1IExCU15GUw1eRlQ2MzAsNTFeQTBOLDI4LDM1XkZEMSBPRiAxXkZTDV5GVDIwLDM0XkEwTiwxOCwyMl5GRFNISVBQSU5HIERFUEFSVE1FTlReRlMNXkZUMjAsNTheQTBOLDE4LDIyXkZIXkZEKDk5OSkgOTk5X0YwOTk5OV5GUw1eRlQyMCw4Ml5BME4sMTgsMjJeRkRPTkUgV09STEQgRElSRUNUXkZTDV5GVDIwLDEwN15BME4sMTgsMjJeRkQxOTE1IDEwVEggQVZFIFdeRlMNXkZUMjAsMTMxXkEwTiwxOCwyMl5GRE1PQlJJREdFIFNEIDU3NjAxXkZTDV5GVDIwLDE4M15BME4sMjgsMzVeRkRTSElQXkZTDV5GVDIwLDIyNF5BME4sMjgsMzVeRkQgVE86XkZTDV5GVDEyMiwxNzFeQTBOLDI4LDM1XkZEREFOTlkgTklDS0VMU15GUw1eRlQxMjIsMjAzXkEwTiwyOCwzNV5GSF5GRCg4NjYpIDIwOV9GMDE0MTNeRlMNXkZUMTIyLDIzNl5BME4sMjgsMzVeRkREQU5OWSBOSUNLRUxTXkZTDV5GVDEyMiwyNjheQTBOLDI4LDM1XkZEMTAgMVNUIEFWRSBFXkZTDV5GVDEyMiwzMDdeQTBOLDM3LDQ2XkZIXkZETU9CUklER0UgU0QgNTc2MDFfRjAyNjAzXkZTDV5GVDIwLDYzMF5CRDJeRkheRkQwMDM4NDA1NzYwMTI2MDNbKT5fMUUwMV8xRDk2MVozODM0MTE5M18xRFVQU05fMURFNTg3MTVfMUUwNyhFM18xRFgsUEhHLCA2IyNOMV8xQ0dOLVNCSjpTJF8wRDImTVFETixfMUQ1K0wyRDc1S1BfMERfMUVfMDReRlMNXkZUMjg0LDQ5Nl5BME4sNjUsODFeRkheRkQgU0QgNTc2IDBfRjAwMV5GUw1eQlk0LCwxMDJeRlQzMzAsNjIwXkJDTiwsTl5GRD47NDIwNTc2MDEyNjAzXkZTDV5GVDIwLDcxMV5BME4sNDYsNTheRkRVUFMgR1JPVU5EXkZTDV5GVDIwLDc0MF5BME4sMjMsMjleRkRUUkFDS0lORyAjOiAxWiBFNTggNzE1IDAzIDM4MzQgMTE5M15GUw1eRlQ2ODcsNzU2XkdCMTIyLDAsMTAwXkZTDV5CWTMsLDIwM15GVDE1Niw5NzleQkNOLCxOXkZEPjoxWkU1PjU4NzE1MDMzODM0MTE5M15GUw1eRlQxMCwxMDM4XkEwTiwyMywyOV5GREJJTExJTkc6IFAvUF5GUw1eRlQxMCwxMDYzXkEwTiwyMywyOV5GSF5GRFJFRjE6IDI4MzA5OTc1X0YwRUJBWV5GUw1eRlQxMCwxMDg3XkEwTiwyMywyOV5GRFJFRjI6IDEyODMwOTk3NV5GUw1eRlQ0OTcsMTIwNF5BME4sMTgsMjJeRkRDV1UgMTMuNiBaNE0rIDIwLjVWIDEwLzIwMTleRlMNXlha</LABELDATA>
        </SHIPMENT>
    </SHIPRESPONSE>
</OWDSHIPPINGRESPONSE>
     */



    private List<SHIPMENT> loadXMLResponse(ShipShipmentResponse response){
        //todo account for customs forms
        List<SHIPMENT> shipments = new ArrayList<>();
        for(Package p:response.getPackages()) {
            SHIPMENT shipment = new SHIPMENT();
            shipment.setMSN(p.getVoidPackageId());
            shipment.setSHIPMETHOD(response.getShipMethod());
            List<LABELDATA> labels = new ArrayList<>();
         //Call tags do not return a label so this may be null
            if (null != p.getLabel()) {
                for (String l : p.getLabel()) {
                    LABELDATA label = new LABELDATA();
                    label.setCopies_Needed("1");
                    label.setValue(l);
                    labels.add(label);
                }
                shipment.getLABELDATA().addAll(labels);
                shipments.add(shipment);
            }
        }
        
        //Check for Customs docs needed and add them
        List<INTLDOCDATA> docData = loadNeededInternationalDocs(response);
        if(docData.size()>0){
            shipments.get(0).getINTLDOCDATA().addAll(docData);
        }



        return shipments;
    }

    /**
     * The function will ship all packages sent in. These need to be all from the same pack. The data from the first package will
     * used for the shipment data. All other packages will load dimensions and line it for international shipments
     *
     * @param packagesToShip List of packages to ship with Id's and weights.
     * @param facility facility code where shipment is happening
     * @return ShipShipmentResponse will all data to print labels
     * @throws Exception any shipment errors will be thrown. Example: Invalid weight for Ship Service
     */
    private ShipShipmentResponse processShipments(List<Package> packagesToShip, String facility) throws Exception{
        ShipShipmentResponse response = new ShipShipmentResponse();
        //load DAta
        ShippingUtilities shippingUtilities = new ShippingUtilities();
        ShipShipment shipment = shippingUtilities.LoadDataFromPackages(packagesToShip,facility);

        //Check for Possible Errors
        shippingUtilities.CheckShipmentForErrors(shipment);

        //Ship
        response = shippingUtilities.processShipmentAndAttemptFixes(shipment);

        //Response with labels etc..

        return response;
    }

    private List<INTLDOCDATA> loadNeededInternationalDocs(ShipShipmentResponse response){
        ShippingUtilities utilities = new ShippingUtilities();

        return utilities.loadNeededInternationalDocs(response);



    }
}
