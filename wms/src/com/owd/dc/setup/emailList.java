package com.owd.dc.setup;

import com.owd.hibernate.HibernateSession;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Sep 6, 2006
 * Time: 11:43:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class emailList {
    private List clients;
    private String stockoutEmail;
    private String holdEmail;

    public String getStockoutEmail() {
        return stockoutEmail;
    }



    public String getHoldEmail() {
        return holdEmail;
    }



    public List getclients(){
         return clients;
     }
    private static emailList me;

     public synchronized static emailList getInstance(){
         if (me == null){
             me = new emailList();
               }
         return me;
     }
    public void reloadList(){
          me=null;
        getInstance();
    }

    public emailList() {
       System.out.println("setting email list");
        List email = new ArrayList();
         String query = "SELECT     t5, t6\n" +
                 "FROM         handheld_setup (NOLOCK) \n" +
                 "WHERE     (loginId = 1919220)";
        try{
         ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);

        rs.next();

           stockoutEmail = rs.getString(1);
            holdEmail = rs.getString(2);

          //   System.out.println(rs.getString(1));

            rs.close();
        }catch (Exception e){
                                                 e.printStackTrace();
        }
        System.out.println("done settign client list");
         clients = email;


    }
   public void updateList(String hold, String stockout, Connection conn) throws Exception{
      Statement stmt = conn.createStatement();
       System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                    String sqlQuery = "update handheld_setup set t5= '"+stockout+"', t6='"+hold+"' where loginId = 1919220" ;

                    int rows = stmt.executeUpdate(sqlQuery);
       System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxvvvvvvvvvvvvvvvvvvfffffffx");
     /*  if(rows<1){
           throw new Exception("Unable to update DB");
       }*/
       conn.commit();
       reloadList();

   }

}
