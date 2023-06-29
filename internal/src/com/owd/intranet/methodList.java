package com.owd.intranet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.intranet.beans.selectList;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Aug 7, 2006
 * Time: 3:16:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class methodList {
private final static Logger log =  LogManager.getLogger();
    private List methods;
        public List getclients(){
            return methods;
        }
       private static methodList me;

        public synchronized static methodList getInstance(){
            if (me == null){
                me = new methodList();
                  }
            return me;
        }
       public methodList() {
          //log.debug("settign method list");
           List clientList = new ArrayList();
            String query = "select distinct method_name from owd_ship_methods order by method_name";
           try{
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
            int i = 0;
            while (rs.next()) {
//                //log.debug(rs.getString(1));
                selectList btn = new selectList();
                btn.setAction(rs.getString(1));
                btn.setDisplay(rs.getString(1));
                clientList.add(i, btn);
                i++;
             //   //log.debug(rs.getString(1));
            }
               rs.close();
           }catch (Exception e){
                                                    e.printStackTrace();
           }
           //log.debug("done settign client list");
           methods = clientList;


       }

}
