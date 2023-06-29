package com.owd.callcenter;

import com.owd.callcenter.beans.selectList;
import com.owd.hibernate.HibernateSession;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 3, 2006
 * Time: 2:46:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ccClientList {
    private List clients;
    private static long created;

    public List getclients(){
        return clients;
    }
   private static ccClientList me;

    public synchronized static ccClientList getInstance(){
        if (ccClientList.me == null){
            ccClientList.me = new ccClientList();
              } else{
            
            System.out.println(created);
            System.out.println(Calendar.getInstance().getTimeInMillis());
            if(Calendar.getInstance().getTimeInMillis()-created>900000){
                 ccClientList.me = new ccClientList(); 
            }
        }
        return ccClientList.me;
    }
   public ccClientList() {
       created = Calendar.getInstance().getTimeInMillis();
      System.out.println("settign client list");
       List clientList = new ArrayList();
        String query = "select Mlog_campaign_name  from owd_client_callcenter order by Mlog_campaign_name";
       try{
        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
        int i = 0;
        while (rs.next()) {
            System.out.println(rs.getString(1));
            selectList btn = new selectList();
            btn.setAction(rs.getString(1));
            btn.setDisplay(rs.getString(1));
            clientList.add(i, btn);
            i++;
         //   System.out.println(rs.getString(1));
        }
           rs.close();
       }catch (Exception e){
                                                e.printStackTrace();
       }
       System.out.println("done settign cc client list");
        clients = clientList;


   }


}
