package com.owd.core.business.user;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserFactory {
private final static Logger log =  LogManager.getLogger();

    public static OwdUser getNewUser() {
        OwdUser user = new OwdUser();
        user.setClientFkey(112);
        user.setEmail("");
        user.setIsActive(true);
        user.setIsAdmin(false);
        user.setIsClientAdmin(false);
        user.setLogin("");
        user.setName("");
        user.setPassword("");
        user.setGroups(new TreeSet());
        
        return user;
    }

    public static OwdUser getOwdUserFromLogin(String loginName) throws Exception {
        try {

            Session sess = HibernateSession.currentSession();
            List userList = sess.createQuery("from OwdUser where login='"+loginName+"'").list();
            if(userList.size()<1)
            {
                throw new Exception("User record not found for name" + loginName);
            }

            return (OwdUser) userList.get(0);

        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;
        } finally {

          //  // HibernateSession.closeSession();
        }


    }
    public static OwdUser getUserFromUserID(Integer id, int clientID, boolean isInternal) throws Exception {
        try {

            Session sess = HibernateSession.currentSession();
            OwdUser user = (OwdUser) sess.load(OwdUser.class, id);
            if (user == null) throw new Exception("User record not found for ID " + id);
            if (!isInternal && user.getClientFkey() != clientID) throw new Exception("Requested client record did not match current client context");
            //init collection
        //    int loader = client.getOwdClientMethods().size();


            return user;
        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;
        } finally {

        //    // HibernateSession.closeSession();
        }


    }
}
