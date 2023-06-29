package com.owd.dc.manifest;

import com.owd.connectship.xml.api.OWDShippingResponse.LABELDATA;
import com.owd.dc.packing.AutoBatchPrinting.PackingABUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.sql.Clob;
import java.util.List;

public class ThirdPartyLabels {
    protected final static Logger log = LogManager.getLogger();

    public static LABELDATA reprintLabelFromPackageBarcode(String barcode) throws Exception{
        LABELDATA ld = new LABELDATA();
        String sql = "select label_string from package where pack_barcode = :barcode";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("barcode",barcode);
        List l = q.list();
        if(l.size()>0){
            Clob clob = (Clob) l.get(0);
            ld.setCopies_Needed("1");
            ld.setValue(PackingABUtils.convertClobToString(clob));
            log.debug("found label " + ld.getValue());
        }else{
            throw new Exception("No info found for Barcode: "+barcode);
        }


        return  ld;
    }
}
