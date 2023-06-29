package com.owd.dc.picking;

import com.owd.dc.locations.locationInfoBean;
import com.owd.hibernate.*;
import com.owd.hibernate.generated.OrderPickItem;
import com.owd.hibernate.generated.OrderPickStatus;
import com.owd.hibernate.generated.WLocation;
import org.hibernate.Query;
import org.hibernate.Session;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 11/1/11
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class itemSortingUtilities {

          public static void main(String[] args){

              try{
                Session sess = HibernateSession.currentSession();
                  OrderPickStatus ps = (OrderPickStatus) sess.load(OrderPickStatus.class,2587471);
                  sortOrderViaOrderStatus(ps,sess);
                 // System.out.println(getFrontLocation(sess,"81854"));
                 HibUtils.commit(sess);
              } catch (Exception e){
                  e.printStackTrace();
              }
          }
    public static String getFrontLocation(Session sess, String invFkey)throws Exception{
           String s = "UNKNOWN";
             String sql = "select location,primary_pick from owd_inventory_locations where inventory_fkey = :invFkey and location !='UNKNOWN' order by primary_pick, assign_date";
        Query q = sess.createSQLQuery(sql);
         q.setParameter("invFkey",invFkey);
        List results = q.list();
        boolean hasPrimary = false;
        boolean isNew = false;
        if (results.size()>0) {
            if(results.size()>1){
                System.out.println("checking stuff");
                for(Object row:results){
                    Object[] data = (Object[]) row;
                   if(data[1].toString().equals("1")){
                       hasPrimary = true;
                   }
                   if(data[0].toString().startsWith("//")){
                       isNew = true;
                   }
                }
            }
            if (hasPrimary == false && isNew){
             System.out.println("We have no primary, we need to do some sorting");
                List<locationListBean> llb = new ArrayList<locationListBean>();
                for(Object row:results){
                    locationListBean lbean = new locationListBean();
                    Object[] data = (Object[]) row;
                    if(data[0].toString().startsWith("//")){
                   locationInfoBean linfo = new locationInfoBean(data[0].toString().replace("//",""),sess);

                    lbean.setLocation(data[0].toString());
                    lbean.setSort(linfo.getSortString());
                    }else{
                        System.out.println("We have some funky stuff, gracefully falling back to primitimve location");
                      lbean.setLocation(data[0].toString());
                        lbean.setSort(data[0].toString());
                    }

                   llb.add(lbean);

                }

                   TreeSet<locationListBean> sortedList = new TreeSet<locationListBean>(new locationListBeanComparator());
        sortedList.addAll(llb);
                    System.out.println("here we are la la la la la ala la la");
                for(locationListBean lib : sortedList){
                       System.out.println(lib.getLocation());
                    System.out.println(lib.getSort());
                }
             s = sortedList.first().getLocation();

            }   else{
           // System.out.println("We have a result " + results.get(0).toString());
               Object row = results.get(0);
                Object[] data = (Object[]) row;

            s = data[0].toString();
            }
        }

        return s;
    }

    public static void sortOrderViaOrderStatus(OrderPickStatus pstat,Session sess)throws Exception{
         boolean newstuff = false;
       if(pstat.getOrderPickItems().size()>1){
        for (OrderPickItem pi : pstat.getOrderPickItems()){
            System.out.println("This is the item "+pi.getId());
            System.out.println(pi.getDefaultLoc());
            if(pi.getDefaultLoc().equals("UNKNOWN")||pi.getDefaultLoc().length()==0){
                System.out.println("lala");
                pi.setDefaultLoc(getFrontLocation(sess,pi.getItemBarcode().toString()));
            }

            if (pi.getDefaultLoc().startsWith("//")){
                newstuff = true  ;
                WLocation loc = (WLocation) sess.load(WLocation.class,Integer.parseInt(pi.getDefaultLoc().replace("//","")));
                pi.setSort(loc.getSortString());
                sess.save(pi);

            }
            if (pi.getDefaultLoc().equals("UNKNOWN")){
                pi.setSort("0");
            }

        }
         HibUtils.commit(sess);
         if (newstuff){
             sess.refresh(pstat);
             TreeSet<OrderPickItem> opi = new TreeSet<OrderPickItem>(new OrderPickItemComparator());
             opi.addAll(pstat.getOrderPickItems());
             System.out.println(pstat.getOrderPickItems().size());
                System.out.println(opi.size());
             int i = 0;
             for(OrderPickItem pi : opi){

                 System.out.println(pi.getSort());
                 pi.setIndexId(i);
                 i++;
             }
               HibUtils.commit(sess);
         }

       }

    }
    public static String sortPickItemLocationList(List<locationListBean> newLocList){
        StringBuffer ss = new StringBuffer();
              TreeSet<locationListBean> sortedList = new TreeSet<locationListBean>(new locationListBeanComparator());
        sortedList.addAll(newLocList);
        for (locationListBean llb : sortedList){
           ss.append(llb.getLocation()+"\r");
        }
        return ss.toString();
    }

}

