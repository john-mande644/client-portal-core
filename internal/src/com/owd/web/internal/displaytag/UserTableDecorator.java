package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.generated.OwdUser;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.web.internal.user.UserHome;
import org.displaytag.decorator.TableDecorator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 19, 2004
 * Time: 2:13:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    public String getLinks()
            throws Exception {
        OwdUser user = (OwdUser) getCurrentRowObject();
        if (user == null) return "";

        StringBuffer sb = new StringBuffer();
        sb.append("<A HREF=\"./edit?" +
                UserHome.kParamAdminAction + "=user-edit&user_id="
                + user.getId() + "\"><B>"+user.getLogin()+"</B></A>");


        sb.append("&nbsp;&nbsp;");

        return sb.toString();


    }

      public String getClient()
            throws Exception {
        OwdUser user = (OwdUser) getCurrentRowObject();
        if (user == null) return "";

          int clientID=user.getClientFkey();
          if(clientID==0 || clientID==55)
          {
              return "OWD";
          }                else
          {
              return (String) ((OwdClient)HibernateSession.currentSession().load(OwdClient.class,new Integer(clientID))).getCompanyName();

          }


    }
}
