package com.owd.jobs.jobobjects.vendorCompliance;

import com.owd.core.business.Address;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by danny on 10/25/2016.
 */
public class addressUtils {

    public static void main(String[] args){
        System.setProperty("com.owd.environment","test");
        try{
            HibernateSession.currentSession();
            System.out.println("hello");
            Order order = new Order("55");
            loadShippingAddressFromReference(order,"PHL1","Amazon");
            System.out.println(order.getShippingAddress().company_name);
            System.out.println(order.getShippingContact().name);

        }catch (Exception e){
            e.printStackTrace();
        }


    }



    public static void loadShippingAddressFromReference(Order order,String reference, String vendor) throws Exception{

        System.out.println("Reference: " + reference +";;;");
        System.out.println("vendor: " + vendor +";;;");

        Address addy = new Address();
        String sql = "select * from edi_Vendor_Ship_Locations where vendor_identifier = :vendorID and vendor = :vendor";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("vendorID",reference);
        q.setParameter("vendor",vendor);
        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List l = q.list();

        if(l.size()>0){
            Map data = (Map) l.get(0);
            order.getShippingAddress().company_name = data.get("company").toString();
            order.getShippingAddress().address_one = data.get("address1").toString();
            order.getShippingAddress().address_two = data.get("address2").toString();
            order.getShippingAddress().city = data.get("city").toString();
            order.getShippingAddress().state = data.get("state").toString();
            order.getShippingAddress().zip = data.get("zip").toString();

            order.getShippingContact().name = data.get("name").toString();

        }else{
            throw new Exception("Unable to lookup location code map");
        }



    }
}
