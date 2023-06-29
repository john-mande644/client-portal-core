package com.owd.intranet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.intranet.beans.selectList;

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
public class ClientList {
private final static Logger log =  LogManager.getLogger();
    private List clients;
    public List getclients(){
        return clients;
    }
    private long updatetime;
   private static ClientList me;

    public synchronized static ClientList getInstance(){
        if (me == null){
            log.debug("Null Clients");
            me = new ClientList();
            me.updatetime = Calendar.getInstance().getTimeInMillis();

              }else{
            if(dateDiff(me.updatetime)){
                log.debug("DateDiff true redioing");
                me = new ClientList();
            me.updatetime = Calendar.getInstance().getTimeInMillis();  
            }
        }
        return me;
    }
   public ClientList() {
      //log.debug("settign client list");
       List clientList = new ArrayList();
        String query = "SELECT     client_id, company_name\n" +
                "FROM         owd_client\n" +
                "WHERE     (is_active = 1)\n" +
                "ORDER BY company_name";
       try{
        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
        int i = 0;
        while (rs.next()) {
            //log.debug(rs.getString(1));
            selectList btn = new selectList();
            btn.setAction(rs.getString(1));
            btn.setDisplay(rs.getString(2));
            clientList.add(i, btn);
            i++;
         //   //log.debug(rs.getString(1));
        }
           rs.close();
       }catch (Exception e){
                                                e.printStackTrace();
       }
       //log.debug("done settign client list");
        clients = clientList;


   }

    private static boolean dateDiff(long storedTime){
         log.debug(Calendar.getInstance().getTimeInMillis() - storedTime);
        return (Calendar.getInstance().getTimeInMillis() - storedTime)>3600000;
    }

}
