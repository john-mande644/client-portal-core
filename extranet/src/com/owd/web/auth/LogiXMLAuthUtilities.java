package com.owd.web.auth;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateAdHocSession;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdUser;
import org.hibernate.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 31, 2004
 * Time: 2:38:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class LogiXMLAuthUtilities {
private final static Logger log =  LogManager.getLogger();


    static Vector getVectorFromLogiXMLAuthString(String authString) {
        log.debug("parsing:" + OWDUtilities.decryptData(authString));
        return OWDUtilities.parseableStringToVector(OWDUtilities.decryptData(authString));
    }

    public static String getLogiXMLResponseFromLogiXMLAuthString(String authString) throws Exception {
        try {
            Session owdSess = HibernateSession.currentSession();
            log.debug("getting adhoc response for " + authString);
            Vector loginData = getVectorFromLogiXMLAuthString(authString);
            if (loginData.size() != 3) throw new Exception();

            String user = (String) loginData.elementAt(0);
            String pass = (String) loginData.elementAt(1);
            String clientID = (String) loginData.elementAt(2);

            List matchedusers = owdSess.createQuery("from User where u_name=\'" + user + "\' and u_password=\'" + pass + "\'").list();
            if (matchedusers.size() != 1) throw new Exception();

            List gooduser = owdSess.createQuery("from OwdUser where Login=\'" + user + "\'").list();
            if (gooduser.size() != 1) throw new Exception();

            addOrUpdateUserToAdHocDB(user, pass);
            return "OK" + "\\" + user + "\\" + pass + "\\" + ((OwdUser) gooduser.get(0)).getClientFkey() + "\\" + (((OwdUser) gooduser.get(0)).isIsAdmin() ? 1 : 0);


        } catch (Exception ex) {
            ex.printStackTrace();
            return "bad";

        } finally {
            HibernateSession.closeSession();
        }

    }

    public static boolean isUserInAdHocDB(String user) throws Exception {
        boolean isInDB = false;
        try {

            String cksql = "select count(*) from users where username=?";

            PreparedStatement ps = HibernateAdHocSession.getPreparedStatement(cksql);
            ps.setString(1, user);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) >= 1) {
                    isInDB = true;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;

        } finally {
            HibernateAdHocSession.closeSession();
        }


        return isInDB;

    }

    public static void addOrUpdateUserToAdHocDB(String user, String password) throws Exception {

        try {
            if (isUserInAdHocDB(user)) {
                String updateUserSql = "update users set password=? where username = ?";

                PreparedStatement ps = HibernateAdHocSession.getPreparedStatement(updateUserSql);
                ps.setString(1, password);
                ps.setString(2, user);

                int rows = ps.executeUpdate();

                HibernateAdHocSession.currentSession().flush();
                HibUtils.commit(HibernateAdHocSession.currentSession());


            } else {
                String insertUserSql = "insert into users (username,password) values (?,?)";
                String insertUserRoleSql = "insert into userrole (userID,roleID) values (?,?)";

                PreparedStatement ps = HibernateAdHocSession.getPreparedStatement(insertUserSql);
                ps.setString(1, user);
                ps.setString(2, password);

                int rows = ps.executeUpdate();
                if (rows == 1) {
                    ps = HibernateAdHocSession.getPreparedStatement("select @@IDENTITY");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        int userID = rs.getInt(1);
                        rs.close();
                        ps = HibernateAdHocSession.getPreparedStatement(insertUserRoleSql);
                        ps.setInt(1, userID);
                        ps.setInt(2, 3);
                        rows = ps.executeUpdate();
                        HibernateAdHocSession.currentSession().flush();
                HibUtils.commit(HibernateAdHocSession.currentSession());

                    }

                } else {
                    throw new Exception("AdHoc User not added!");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;

        } finally {
            HibernateAdHocSession.closeSession();
        }


    }
}
