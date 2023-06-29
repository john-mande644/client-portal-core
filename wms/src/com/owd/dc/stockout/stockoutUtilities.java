package com.owd.dc.stockout;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.hibernate.Query;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Jan 23, 2006
 * Time: 9:46:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class stockoutUtilities {

    public static stockoutForm getNextStockoutLocation(String name, String warehouse) throws Exception{
        stockoutForm form = new stockoutForm();
          String query = null;

         if (warehouse.equals("1")){
          query ="SELECT     TOP 1 id, inventory_id, location, client_fkey\n" +
                  "FROM         w_stockout (NOLOCK) \n" +
                  "WHERE     (verified = 0) AND location  not like '%--DC2' AND (verifying = '0' OR\n" +
                  "                      verifying = '"+name+"')\n" +
                  "ORDER BY location";}
        if(warehouse.equals("2")){
           query ="SELECT     TOP 1 id, inventory_id, location, client_fkey\n" +
                  "FROM         w_stockout (NOLOCK) \n" +
                  "WHERE     (verified = 0) AND location  like '%--DC2' AND (verifying = '0' OR\n" +
                  "                      verifying = '"+name+"')\n" +
                  "ORDER BY location";

        }

        System.out.println(query);
        ResultSet rs = HibernateSession.getResultSet(query);
       if(rs.next()){
        //  Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();

          String sql = "update dbo.w_stockout set verifying = '"+name+"' where id ="+rs.getString(1);
         Query q = HibernateSession.currentSession().createSQLQuery(sql);

          //PreparedStatement stmt = cxn.prepareStatement(sql);
                    int rowsUpdated = q.executeUpdate();
           //         cxn.commit();
           HibUtils.commit(HibernateSession.currentSession());
         form.setsInventoryId(rs.getString(2));
           form.setsLocation(rs.getString(3));
           form.setClientFkey(rs.getString(4));
          form.setId(rs.getString(1));
       } else{
           throw new Exception("All done!!!!!!!!!!!!");
       }


        return form;
    }

   public static void markVerified(int result, String name, String id)throws Exception{
     // Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();

          String sql = "update dbo.w_stockout set verified = 1, result ="+result+", verified_by='"+name+
                  "' , verified_date = getdate(), verifying = 1 where  id ='"+id+"'";
         // PreparedStatement stmt = cxn.prepareStatement(sql);
       Query q = HibernateSession.currentSession().createSQLQuery(sql);
         int rowsUpdated = q.executeUpdate();
       HibUtils.commit(HibernateSession.currentSession());
        // cxn.commit();


   }
}
