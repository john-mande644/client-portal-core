package com.owd.dc;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Mar 1, 2010
 * Time: 1:26:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class loginAround {
     public static void main(String[] args){
       System.out.println(checkLogin("51"));
         System.out.println(checkLogin("51"));
         System.out.println(checkLogin("51"));
         System.out.println(checkLogin("51")); System.out.println(checkLogin("51"));
         System.out.println(checkLogin("51"));
         System.out.println(checkLogin("51"));


         try{
            // System.out.println(canPickAll(HibernateSession.currentSession(),"51"));
         }catch(Exception e){

         }
     }
    private static Map<String,String> names = new TreeMap<String,String>();
    private static long updated = 0;

    private static boolean shouldWeUpdateMap(){
        if (updated == 0) return true;
        if (Calendar.getInstance().getTimeInMillis()-updated>900000) return true;
        return false;
    }
    private static void updateTable(Session sess) throws Exception{

        String sql = "OWD.dbo.updatWLogins";
        System.out.println("1");
        Query q = sess.createSQLQuery(sql);
         System.out.println("2");
      int i = q.executeUpdate();
        
         System.out.println("3");
        HibUtils.commit(sess);
         System.out.println("4");

    }
    public static String checkLogin(String id, HttpServletRequest request){
              String name = checkLogin(id);
        if(name.length()>0){
          try{
          if(canPickAll(HibernateSession.currentSession(),id)){
              request.getSession(true).setAttribute("canPickAll","yes");
          } else{
               request.getSession(true).setAttribute("canPickAll","no");
          }
           }catch(Exception e){
               e.printStackTrace();
           }
        }
         return name;
    }

    public static String checkLogin(String id) {
System.out.println("checking login for " + id);
        if(shouldWeUpdateMap()){
            System.out.println("We are updateing the login mapppppppppppppppppp");
            names = new TreeMap<String,String>();
            System.out.println("Setting the names thingy yes we are");
            String sql = "select cardnumber, firstname +' ' + lastname  as name from w_logins where active = 1";
           try{
              //    updateTable(HibernateSession.currentSession());
           Query q = HibernateSession.currentSession().createSQLQuery(sql);
          List l = q.list();

               for (Object data:l.toArray()){
                   Object[] row = (Object[])data;
                   names.put(row[0].toString(),row[1].toString());

               }
          
             updated = Calendar.getInstance().getTimeInMillis();

           }catch (Exception e){
               e.printStackTrace();
           }

            
        }

       if(names.containsKey(id)){

           return names.get(id);

       }
        return "";
    }


    public static boolean canPickAll(Session sess,String id){
       boolean pickall = false;
        try{
           String sql = "select pickAll from handheld_setup where loginId = :id";
            Query q = sess.createSQLQuery(sql);
            q.setString("id",id);
            List results = q.list();
            if (results.size()>0){
              if (results.get(0).toString().equals("1")){
                  pickall = true;
              }
            }
        } catch(Exception e){
          e.printStackTrace();
        }

       return pickall;
    }

}
