package com.owd.core.business.user;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdGroup;
import com.owd.hibernate.generated.OwdUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.sql.Connection;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserManager {
private final static Logger log =  LogManager.getLogger();


   public static void deleteUser(OwdUser user)
   {

       try
         {
                 //delete old records if present
             // User userOld = User.getUserForID(user.getLogin());
             // userOld.dbdelete();

           HibernateSession.currentSession().delete(user);
           HibernateSession.currentSession().flush();
           HibUtils.commit(HibernateSession.currentSession());

             }catch(Exception ex)
             {
           ex.printStackTrace();
                    //ignore it if the old user is not present
             }




   }
    public static void saveOrUpdateUser(OwdUser user) throws Exception {

        Session sess = HibernateSession.currentSession();
       

      //  sess.lock( user, LockMode.NONE);
        if(user.getGroups()==null) user.setGroups(new HashSet());

        user.getGroups().clear();

        ///Iterator itx = user.getGroups().iterator();

       // while(itx.hasNext())
        ////{
        //    user.getGroups().remove(itx.next());
        //}


        sess.saveOrUpdate(user);
        sess.flush();

       List allGroups =  HibernateSession.currentSession().createQuery("from OwdGroup").list();

       List internalGroups = new ArrayList();
       List clientGroups = new ArrayList();
       List adminGroups = new ArrayList();
        List clientonlyGroups = new ArrayList();

        Iterator it = allGroups.iterator();
        while(it.hasNext())
        {
            OwdGroup group = (OwdGroup) it.next();
            if("internal".equals(group.getName()))
            {
                internalGroups.add(group);
            } else if("extranet".equals(group.getName()))
            {
                clientGroups.add(group);
            } else if("Admin".equals(group.getName()))
            {
                adminGroups.add(group);
            } else if("bgbooks".equals(group.getName()))
            {
                clientGroups.add(group);
            } else if("callcenter".equals(group.getName()))
            {
                internalGroups.add(group);
            } else if("kenmark".equals(group.getName()))
            {
                clientGroups.add(group);
            } else if("phion".equals(group.getName()))
            {
                clientGroups.add(group);
            } else if("proalert".equals(group.getName()))
            {
                clientGroups.add(group);
            } else if("client".equals(group.getName()))
            {
                clientonlyGroups.add(group);
            } else if("adhocAdmin".equals(group.getName()))
            {
                adminGroups.add(group);
            }

        }

        if(user.isIsActive())
        {
            Iterator it1 = clientGroups.iterator();
            while(it1.hasNext())
            {
                user.getGroups().add((OwdGroup)it1.next());
            }
        }
        if(user.getClientFkey()==0)
        {
            Iterator it1 = internalGroups.iterator();
            while(it1.hasNext())
            {
                user.getGroups().add((OwdGroup)it1.next());
            }
        }else
        {
            Iterator it1 = clientonlyGroups.iterator();
            while(it1.hasNext())
            {
                user.getGroups().add((OwdGroup)it1.next());
            }
        }
        if(user.isIsAdmin() && user.getClientFkey()==0)
        {
            Iterator it1 = adminGroups.iterator();
            while(it1.hasNext())
            {
                user.getGroups().add((OwdGroup)it1.next());
            }
        }



        sess.save(user);

        sess.flush();




        //log.debug("commit user");
        HibUtils.commit(sess);

        //update old user records
      //  saveOrUpdateOldUserRecords(user);
    }



    public static void main(String[] args)
    {
             //get list of groups for user name

        try
        {
        OwdUser user = (OwdUser) HibernateSession.currentSession().createQuery("from OwdUser where login='sbuskirk'").list().get(0);

            if(user==null)
            {
                //log.debug("No user found!");

            } else {

                //log.debug("User: "+user.getLogin()+", Pass:"+user.getPassword());
        Set groups = user.getGroups();
                if(groups==null){
                    //log.debug("No groups found!");
                }else
                {
            //log.debug("Found "+groups.size()+" groups!");
        Iterator it = groups.iterator();
            OwdGroup d = null;
            while(it.hasNext())
            {
                OwdGroup g = ((OwdGroup)it.next());

                //log.debug("User: "+user.getLogin()+", Group:"+g.getName()+", Pass:"+user.getPassword());
                if("test".equals(g.getName()))
                {
                   d = g;

                }
            }

                //log.debug("User: "+user.getLogin()+", Pass:"+user.getPassword());
            if(d!=null)
            {
                user.getGroups().remove(d);
                HibernateSession.currentSession().save(user);
               // HibernateTestSession.currentSession().delete(d);
         //  HibernateTestSession.currentSession().flush();
         //   HibernateTestSession.currentSession().commit();
            }
         OwdGroup gp = new OwdGroup();
         gp.setName("test");
                    HibernateSession.currentSession().save(gp);
         user.getGroups().add(gp);

         //gp.getUsers().add(user);


          HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());
                                       //log.debug("User: "+user.getLogin()+", Pass:"+user.getPassword());
                    saveOrUpdateUser(user);
                 //log.debug("User: "+user.getLogin()+", Pass:"+user.getPassword());
                }
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }                        finally
        {
         //   // HibernateSession.closeSession();
        }

    }

   /* public static void saveOrUpdateOldUserRecords(OwdUser newUser) throws Exception
    {

        //deletes any current old-style user records and creates new records based on new user information
            try
        {
                //delete old records if present
             User userOld = User.getUserForID(newUser.getLogin());
             userOld.dbdelete();

            }catch(Exception ex)
            {
                   //ignore it if the old user is not present
            }


        //create and save new records
        User user = new User();


                      user.name = newUser.getLogin();

                      user.password = newUser.getPassword();

        Vector groupsVector = new Vector();
        Iterator it = newUser.getGroups().iterator();
        while(it.hasNext())
        {
            groupsVector.add(((OwdGroup)it.next()).getName());
        }

                      user.groupNames = groupsVector;

                      user.is_admin = (newUser.isIsAdmin()?1:0);

                      user.client_id = newUser.getClientFkey();


                      user.dbsave();

    }
*/
   public static void validateUserUnique(OwdUser user) throws Exception
   {
       try
            {
                 OwdUser userOld = UserFactory.getOwdUserFromLogin(user.getLogin());

                if(userOld!=null)
                 throw new Exception("<strong>Login \""+user.getLogin()+"\" already in use</strong>: Please choose a different login for this user. All logins must be unique.");

       }catch(Exception ex)
           {
             if(ex.getMessage().indexOf("already in use")>0)
             {
                throw ex;
             }
                }

   }

    public static Vector listAllGroupsForUser(OwdUser user) {

      Vector groups = new Vector();




      try {

          Set groupList = user.getGroups();

          Iterator it = groupList.iterator();

          while (it.hasNext()) {

              groups.addElement(((OwdGroup)it.next()).getName());

          }


      } catch (Exception ex) {


          //////////log.debug("got Exception in User.dbload: "+ex);


      }

      return groups;

  }

    public static Vector listAllUserGroups() {

      Vector groups = new Vector();




      try {

          List groupList = HibernateSession.currentSession().createQuery("from OwdGroup").list();

          Iterator it = groupList.iterator();

          while (it.hasNext()) {

              groups.addElement(((OwdGroup)it.next()).getName());

          }


      } catch (Exception ex) {


          //////////log.debug("got Exception in User.dbload: "+ex);


      }

      return groups;

  }

    public static OwdUser getUserForID2(String ID) throws Exception {

        //load existing package

        return (OwdUser) HibernateSession.currentSession().load(OwdUser.class,new Integer(ID));

    }

    public static boolean isValidInternalUser(String userName,
                                              String password) {

        try
                 {
           OwdUser user = UserFactory.getOwdUserFromLogin(userName);
           if(user==null) throw new Exception("User not found: "+userName);

           return (password.equals(user.getPassword()) && isInternal(user));

                 }catch(Exception ex)
                 {
                     return false;
                 }

    }

    public static boolean isValidUser(String userName,
                                      String password) {

              try
              {
        OwdUser user = UserFactory.getOwdUserFromLogin(userName);
        if(user==null) throw new Exception("User not found: "+userName);

        return (password.equals(user.getPassword()));

              }catch(Exception ex)
              {
                  return false;
              }

    }

    public static String getPassword(String userName) throws Exception {

        OwdUser user = UserFactory.getOwdUserFromLogin(userName);
        if(user==null) throw new Exception("User not found: "+userName);

        return user.getPassword();


    }

    static public boolean isInternal(OwdUser user) {


        return user.getClientFkey() <= 0;


    }

    public static boolean isAdmin(Connection cxn, String user) throws Exception {
        return UserFactory.getOwdUserFromLogin(user).isIsAdmin();
    }
}







