package com.owd.OWDShippingAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 11/18/2019.
 */
public class OWDDocRequest {


    public List<LABELDATA> getAvailableLabelsForBarcodeXML(String packBarcode) throws Exception{
        List<LABELDATA> lds = new ArrayList<>();
        List<String> labelStrings = getAvailableLabels(packBarcode);
        if(labelStrings.size()>0){
            for(String s: labelStrings){
                LABELDATA data = new LABELDATA();
                data.setCopies_Needed("1");
                data.setValue(s);
                lds.add(data);

            }
        }


        return lds;
    }



    private List<String> getAvailableLabels(String packBarcode) throws Exception{
        String sql = "select label_string from package where pack_barcode = :barcode and label_string is not null";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("barcode",packBarcode);

        List l = q.list();
        if(l.isEmpty()){
            return new ArrayList<>();
        }

        ShippingUtilities shippingUtilities = new ShippingUtilities();
        String s = shippingUtilities.convertClobToString((Clob) l.get(0));
        List<String> labels = new ArrayList<>();
        if(s.startsWith("[\"")){
            //Label array
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            labels = gson.fromJson(s,new TypeToken<List<String>>(){}.getType());
        }else{
            labels.add(s);
        }

        return labels;
    }
}
