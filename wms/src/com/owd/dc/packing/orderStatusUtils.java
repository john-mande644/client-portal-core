package com.owd.dc.packing;

import com.owd.dc.packing.beans.PackageBoxBean;
import com.owd.dc.packing.beans.packOrderStatus;
import com.owd.hibernate.HibernateSession;
import com.thoughtworks.xstream.XStream;
import org.hibernate.Criteria;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by danny on 1/7/2016.
 */
public class orderStatusUtils {
    private final static Logger log = LogManager.getLogger();


    public static void main(String args[] ){
        System.out.println("hello");
               packOrderStatus status = loadOrderStatusFromOrderId("10058228");
               log.debug(status.getStatus());
                log.debug(status.getError());

               for(PackageBoxBean box:status.getPackages()){
                   log.debug(box.getBarcode());
                   log.debug(box.getWeight());
               }
        XStream x = status.getXStream();

        log.debug(x.toXML(status));
    }



    public static String getOrderStatusXML(String orderId){
        packOrderStatus status = loadOrderStatusFromOrderId(orderId);

        XStream x = status.getXStream();
        return x.toXML(status);

    }
    public static packOrderStatus loadOrderStatusFromOrderId(String orderId){
        packOrderStatus status = new packOrderStatus();

        try{
            System.out.println("hello1");
            status.setError("");
            String sql = "SELECT\n" +
                    "    dbo.owd_order.order_id,\n" +
                    "    dbo.owd_order.order_status,\n" +
                    "    dbo.owd_order.is_shipping,\n" +
                    "    dbo.package.id,\n" +
                    "    dbo.package.pack_barcode,\n" +
                    "    dbo.package.weight_lbs," +
                    "       pack_end\n" +
                    "FROM\n" +
                    "    dbo.owd_order\n" +
                    "LEFT OUTER JOIN\n" +
                    "    dbo.package_order\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.owd_order.order_id = dbo.package_order.owd_order_fkey)\n" +
                    "LEFT OUTER JOIN\n" +
                    "    dbo.package\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.package_order.id = dbo.package.package_order_fkey)\n" +
                    "WHERE\n" +
                    "    dbo.owd_order.order_id = :orderId \n" +
                    "AND dbo.package_order.is_void = 0 ;" ;
            System.out.println("hello2");

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            System.out.println("hello3");
            q.setParameter("orderId",orderId);
            System.out.println("hello4");
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            System.out.println("hello5");
            List results = q.list();


               if(results.size()>0) {
                   int i =0;
                   List<PackageBoxBean> packages = new ArrayList<PackageBoxBean>();
                   for (Object row : results) {
                       Map data = (Map) row;
                       if(i==0){
                           log.debug("in the zero");
                          status.setStatus(data.get("order_status").toString());
                           status.setShipping(data.get("is_shipping").toString().equals("1"));
                           status.setTime(data.get("pack_end").toString());

                       }
                       PackageBoxBean pack = new PackageBoxBean();
                       pack.setId(data.get("id").toString());
                       pack.setBarcode(data.get("pack_barcode").toString());
                       pack.setWeight(data.get("weight_lbs").toString());
                       packages.add(pack);

                     i++;
                   }
                   status.setPackages(packages);

               }  else{
                   status.setError("Unable to find the order at all");
               }



        }  catch(Exception e){
            e.printStackTrace();
            status.setError("Error: "+e.getMessage());
        }




         return status;
    }
}
