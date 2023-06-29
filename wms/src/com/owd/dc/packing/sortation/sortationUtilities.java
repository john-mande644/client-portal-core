package com.owd.dc.packing.sortation;

import com.owd.dc.packing.sortation.beans.ObjectFactory;
import com.owd.dc.packing.sortation.beans.SortationTrackingType;
import com.owd.dc.packing.sortation.beans.TrackerType;

import com.owd.hibernate.HibernateSession;
import org.hibernate.Criteria;
import org.hibernate.Query;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 6/10/2017.
 */
public class sortationUtilities {



    public static void main(String[] args){
        try{
            String s = getTrackingXmlForFacility("12583763","DC6");
            System.out.println(s);
        }catch (Exception e){
            e.printStackTrace();
        }



    }


    public static String getTrackingXmlForFacility(String trackingFkey, String facility) throws Exception{


        ObjectFactory of = new ObjectFactory();
        SortationTrackingType stt = of.createSortationTrackingType();
        String sql = "execute sp_GetSortationInfoForWarehouse :trackingFkey, :facility ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("trackingFkey",trackingFkey);
        q.setParameter("facility",facility);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        List results = q.list();
        System.out.println(results.size());

        if(results.size()>0) {

            for (Object row : results) {
                Map data = (Map) row;

                TrackerType tt = of.createTrackerType();
                tt.setTrackingNo(data.get("tracking_no").toString());
                tt.setShipMethod(data.get("ship_Method").toString());
                tt.setLane(data.get("lane").toString());
                tt.setWeight(data.get("weight").toString());
                tt.setTrackingFkey(data.get("tracking_fkey").toString());
                stt.getTracker().add(tt);

            }



        }







        JAXBContext jaxbContext = JAXBContext.newInstance(SortationTrackingType.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal( of.createSortationTracking(stt),sw);




return sw.toString();
    }
}
