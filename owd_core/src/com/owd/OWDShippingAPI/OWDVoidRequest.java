package com.owd.OWDShippingAPI;

import com.owd.OWDShippingAPI.Models.VoidShipment;
import com.owd.OWDShippingAPI.Models.VoidShipmentResponse;
import com.owd.OWDShippingAPI.Services.OWDShippingAPIServiceGenerator;
import com.owd.OWDShippingAPI.Services.VoidService;
import com.owd.OWDShippingAPI.authentication.OWDShippingAPIToken;
import com.owd.connectship.xml.api.OWDShippingResponse.VOIDRESULT;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by danny on 11/18/2019.
 */
public class OWDVoidRequest {



    public List<VOIDRESULT>  voidBarcodeReturnXML(List<String> barcodes) throws Exception{
        List<VOIDRESULT> vrs = new ArrayList<>();
        Map<String,Boolean> voids = voidBarcodes(barcodes);

        for(String s: voids.keySet()){
            VOIDRESULT vr = new VOIDRESULT();
            vr.setBARCODE(s);
            vr.setResults(voids.get(s)?"SUCCESS":"ERROR");
            vrs.add(vr);
        }


        return vrs;
    }

    private Map<String,Boolean> voidBarcodes(List<String> barcodes) throws Exception{

            Map<String,Boolean> results = new TreeMap<>();
        for(String barcode: barcodes){
            results.put(barcode,voidSingleBarcode(lookupBarocdeVoidID(barcode)));
            com.owd.core.business.order.Package.voidPackageShipment(Integer.parseInt(lookupBarcodeTrackingFkey(barcode)), "OWDVoid");
        }


        return results;


    }

    private boolean voidSingleBarcode(String barcode) throws Exception{

        VoidShipment v = new VoidShipment();
        List<String> voids = new ArrayList<>();
        voids.add(barcode);
        v.setVoidIds(voids);
        String token= OWDShippingAPIToken.grabNewToken();
        VoidService sService = OWDShippingAPIServiceGenerator.createService(VoidService.class, token);

        Call<VoidShipmentResponse> callSync = sService.voidShipments(v);
        Response<VoidShipmentResponse> response = callSync.execute();
        System.out.println(response.code());
        VoidShipmentResponse shipment = response.body();

        return shipment.getVoidedIds().get(0).isSuccess();



    }

    private String lookupBarcodeTrackingFkey(String barcode) throws Exception{
        Query q = HibernateSession.currentSession().createSQLQuery("select order_track_fkey from package where pack_barcode = :barcode");
        q.setParameter("barcode",barcode);
        List l = q.list();
        if(l.size()==0) throw new Exception("Unable to find value to void track through OWDAPI");

        return l.get(0).toString();
    }

    private String lookupBarocdeVoidID(String barcode) throws Exception{
        Query q = HibernateSession.currentSession().createSQLQuery("select external_id from package where pack_barcode = :barcode");
        q.setParameter("barcode",barcode);
        List l = q.list();
        if(l.size()==0) throw new Exception("Unable to find value to void through OWDAPI");

        return l.get(0).toString();
    }




}
