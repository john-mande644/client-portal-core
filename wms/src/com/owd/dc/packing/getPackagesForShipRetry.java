package com.owd.dc.packing;

import com.owd.dc.packing.beans.OrderPackagesBean;
import com.owd.dc.packing.beans.PackageBoxBean;
import com.owd.dc.packing.beans.orderSortControl;
import com.owd.dc.packing.vendorCompliance.vendorComplianceUtils;
import com.owd.hibernate.HibernateSession;
import com.thoughtworks.xstream.XStream;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Aug 30, 2010
 * Time: 10:56:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class getPackagesForShipRetry {


    public static String getPackagesXml(String packageLookup, Session sess){

        OrderPackagesBean opb = new OrderPackagesBean();
        opb.setPackages(new ArrayList<PackageBoxBean>());

                   try{

                       String packageOrderFkey = getPackageOrderFkey(packageLookup,sess);
                      if(unshipped(packageOrderFkey,sess)){

                      String sql = "select pack_barcode, weight_lbs from package where package_order_fkey = :fkey order by pack_barcode";
                          Query q = sess.createSQLQuery(sql);
                          q.setString("fkey",packageOrderFkey);
                          List results = q.list();
                          if (results.size()>0){

                              for (Object row:results){
                                  Object[] data = (Object[]) row;
                                  PackageBoxBean pbb = new PackageBoxBean();
                                  pbb.setBarcode(data[0].toString());
                                  pbb.setWeight((data[1].toString()));
                                  opb.getPackages().add(pbb);


                              }
                              orderSortControl osc = Util.getOrderSortControlFromPackageOrderFkey(packageOrderFkey);
                              opb.getResults().setSortImageUrl(osc.getSortImageUrl());
                              opb.getResults().setSortText(osc.getSortText());
                              opb.getResults().setSortType(osc.getSortType());
                              opb.getResults().setSortSound(osc.getSortSound());
                              opb.getResults().setVcOrder(getVCOrderFromPackageOrderId(packageOrderFkey));
                              opb.getResults().setOrderNum(getOrderNumFromPackageOrderId(packageOrderFkey));
                          } else{
                              throw new Exception("Unable to find packages for order");
                          }



                      } else{
                           //check to see if the label scanned has been voided.
                          if(isPackageVoid(packageOrderFkey,sess)){
                              throw new Exception("The Package Label you have scanned has been voided. Please find the proper label or package!!!!!!!");
                          }
                          throw new Exception("It appears the order is already shipped. Please check carefully and reprint labels if necessary");
                      }


                   } catch(Exception e){
                       e.printStackTrace();
                       opb.getResults().setMessage("Error");
                       opb.getResults().setError(e.getMessage());
                   }
                  opb.getResults().setMessage("Success");

              XStream x = new XStream();

           x.alias("OrderPackages",opb.getClass());
        x.alias("Box",PackageBoxBean.class);

        return x.toXML(opb);
    }
   private static boolean isPackageVoid(String packageOrderFkey, Session sess)throws Exception{
             boolean packvoid = false;
              String sql = "select is_void from package_order where id = :id";
       Query q = sess.createSQLQuery(sql);
       q.setParameter("id",packageOrderFkey);
        List results = q.list();
       if(results.size()>0){
           if(results.get(0).toString().equals("1")){
               packvoid = true;

           }
       }

       return packvoid;
   }
   private static boolean unshipped(String packageOrderFkey, Session sess){
      boolean unshipped = false;
                        String sql = "select id from package_order where id =:id and is_void=0 and packs_shipped = 0";
                      Query q = sess.createSQLQuery(sql);
       q.setString("id",packageOrderFkey);
       if (q.list().size()>0) unshipped = true;

       return unshipped;
   }
    private static boolean getVCOrderFromPackageOrderId(String packageOrderId) throws  Exception{
        boolean vc = false;

        String sql = "select owd_order_fkey from package_order where id = :id";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("id",packageOrderId);
        List l = q.list();
        if(l.size()>0){
            vc = vendorComplianceUtils.isVendorComplianceOrder(l.get(0).toString());
        }
        return vc;
    }

    private static String getOrderNumFromPackageOrderId(String packageOrderId) throws Exception{
        String orderNum = "";
        String sql = "SELECT\n" +
                "    dbo.owd_order.order_num\n" +
                "FROM\n" +
                "    dbo.package_order\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.package_order.owd_order_fkey = dbo.owd_order.order_id)\n" +
                "WHERE\n" +
                "    dbo.package_order.id = :id ;";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("id",packageOrderId);
        List l = q.list();
        if(l.size()>0){
            orderNum = l.get(0).toString();
        }else{
            throw new Exception("unable to find order number from package order ID");
        }

        return orderNum;
    }

    private static String getPackageOrderFkey(String packageLookup, Session sess) throws Exception{
          String sql = "";
        if (packageLookup.contains("p")){
            sql = "select package_order_fkey from package where pack_barcode = :packageLookup";

        }   else{
            packageLookup = packageLookup.replaceAll("\\*|[rR].*","");
            sql ="select id from package_order where owd_order_fkey = (select order_id from owd_order where order_num = :packageLookup) and is_void = 0";
        }
        Query q = sess.createSQLQuery(sql);
        q.setString("packageLookup",packageLookup);

        List data = q.list();
        if (data.size()>0){
            System.out.println(data.get(0).toString());
            return data.get(0).toString();

        } else{

          throw new Exception("Unable to lookup valid packages for "+packageLookup);
        }
    }

   public static void main(String[] args){
try{
    System.out.println("hi");
    Session sess = HibernateSession.currentSession();
    System.out.println("goodbye");
    //    System.out.println(getPackagesXml("8000031R1",sess));
     /*  getPackageOrderFkey("5711170", sess)    ;
    System.out.println(unshipped("1692457",sess));*/
    System.out.println(isPackageVoid("4020298",sess));
}catch(Exception e){
    e.printStackTrace();
}
   }
}
