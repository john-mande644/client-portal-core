package com.owd.dc.packing;

import com.owd.dc.packing.beans.boxBean;
import com.owd.hibernate.generated.OwdOrder;
import org.hibernate.Query;
import org.hibernate.Session;
import com.owd.hibernate.HibernateSession;

import com.owd.core.TimeStamp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: May 10, 2007
 * Time: 11:14:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class boxUtil {
    public static List<boxBean> getBoxBeanListForOrderId(String orderId) throws Exception{
        List<boxBean> boxs = new ArrayList<boxBean>();
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));
           // grab ids
           String sql = "select owd_boxtypes_fkey from owd_boxtypes_methods  (NOLOCK) where td_reference = :ref";
        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("ref",getTdReferenceFromOrderId(orderId));
        List results = q.list();
       StringBuffer sb = new StringBuffer();
        for(Object row: results){
            sb.append(row.toString());
            sb.append(" ");
        }
        sql = "execute getBoxesForClientGroup :idList, :clientId, :facility, :groupName";
        q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("idList",sb.toString());
        q.setParameter("clientId",order.getClientFkey());
        q.setParameter("facility",order.getFacilityCode());
        q.setParameter("groupName",order.getGroupName());
        results = q.list();
        for(Object row:results){
           Object[] data = (Object[]) row;
            boxBean box = new boxBean();
            box.setId(data[0].toString());
            box.setName(data[1].toString());
            box.setDescription(data[2].toString());
            boxs.add(box);
        }


        return boxs;
    }
    public static String getTdReferenceFromOrderId(String orderId) throws Exception{
        String tdReference="";
         OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));

        //Pull off of carr_service first
        String listSql = "select td_reference from owd_lists where list_value = :list";
        Query q = HibernateSession.currentSession().createSQLQuery(listSql);
        q.setParameter("list",order.getShipinfo().getCarrService());
        List results = q.list();
        if(results.size()>0){
           return results.get(0).toString();
        }
        String refSql = "select td_reference from owd_lists  (NOLOCK) where reference_num = :ref";
         q = HibernateSession.currentSession().createSQLQuery(refSql);
        q.setParameter("ref",order.getShipinfo().getCarrServiceRefNum());
        results = q.list();
        tdReference = results.get(0).toString();

      return tdReference;
    }

    public static String getAllBoxes(){
        return getAllBoxes("DC1");
    }
    public static String getAllBoxes(String facility){
       StringBuffer sb = new StringBuffer();
        sb.append(Util.xmlHead);
        sb.append("<boxes>");
        try{
        Session sess = HibernateSession.currentSession();
            ResultSet rs = HibernateSession.getResultSet(sess,"select id,name,dim_depth, " +
                    "dim_width, dim_height, weight_lbs, cost,convert(varchar,dim_depth)+'x'+convert(varchar,dim_width)+'x'+convert(varchar,dim_height) \n" +
                    "as size,(dim_depth*dim_width*dim_height)as volume, description, inventory_fkey, ISNULL(barcode,'') as barcode, packaging_type  from dbo.owd_boxtypes  where facility_code = '"+facility+"'  order by name");
       while (rs.next()){
           sb.append(xmlCreate.box(rs.getString("id"),rs.getString("name"),rs.getString("dim_depth"),
                   rs.getString("dim_width"),rs.getString("dim_height"),rs.getString("weight_lbs"),rs.getString("cost"),rs.getString("size"),rs.getString("volume"), rs.getString("description"), rs.getString("inventory_fkey"),rs.getString("barcode"),rs.getString("packaging_type")));

       }
       sb.append("</boxes>");
        }catch (Exception ex){
            ex.printStackTrace();

            return Util.errorResponse(ex.getMessage());
        }
        return sb.toString();

    }

    public static void main(String[] args){
        try{
        System.out.println(boxUtil.getBoxes("LTL","TANDATA_FEDEXFSMS.FEDEX.IECO","622","21437294","DC7",""));
      //  System.out.println(getTdReferenceFromOrderId("8148190"));
           // List<boxBean> boxs = getBoxBeanListForOrderId("8148190");
           /* System.out.println(boxs.size());
            for(boxBean box:boxs){
                System.out.println(box.getName());
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
  //   public static String getBoxes(String service,String reference, String clientFkey,String orderfkey)throws Exception{
   //     return   getBoxes( service, reference, clientFkey, orderfkey,"DC1");
   // }
 public static String getBoxes(String service,String reference, String clientFkey,String orderfkey,String Facility, String groupName) throws Exception{
     TimeStamp tt = new TimeStamp();
      //look for valid ship id
     System.out.printf("Service: %s",service);
     System.out.println();
     System.out.printf("reference: %s",reference);
     String resp = null;
      ResultSet rs = null;

     try{

      if (reference.equals("OWD.NOBOX.PICKEDUP")){
          reference = "OWD.NOBOX.PICKEDUP";
      }   else if(service.equals("LTL")) {
        reference = "CONNECTSHIP_UPS.UPS.GND";
      }
           tt.print("before all");
           rs = HibernateSession.getResultSet("execute getBoxesForClientGroupNew '"+service+"','" +reference+"','"+clientFkey+"','"+orderfkey+"','"+Facility+"'"+",'"+groupName+"'");
           tt.print(":after all");
       resp = xmlCreate.boxesPrimary(rs);
           tt.print("after zml");


        rs.close();


           tt.print("before prefered");
           rs = HibernateSession.getResultSet(HibernateSession.currentSession(),"execute getPriorityBoxFromTag " + orderfkey);
           if(rs.next() == false){
               // Nothing returned from Tags used mostUsed Query
               rs = HibernateSession.getResultSet(HibernateSession.currentSession(), "execute getMostUsedBoxesOrderFacility "+orderfkey+","+Facility);

           }else{
               //can't reset to before first because of dialect, reload
               rs = HibernateSession.getResultSet(HibernateSession.currentSession(),"execute getPriorityBoxFromTag " + orderfkey);


           }
           tt.print("after prefered");
         resp = resp + xmlCreate.boxesPrefered(rs);
           tt.print("after xml");


     }catch(Exception ex){
         ex.printStackTrace();
     } finally{
         try{
             rs.close();
         } catch (Exception e){

         }
         //HibernateSession.closeSession();

     }
      return resp;
    }

}
