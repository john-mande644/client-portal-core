package com.owd.web.internal.user;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.user.UserFactory;
import com.owd.core.business.user.UserManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdUser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:56:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserEditServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
    }

    public void destroy() {
        super.destroy();
    }

    //GET requests not supported
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {
        doPost(req, resp);
    }

    //all requests should be POST
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        String error = "";

        String action = ((String) req.getParameter(UserHome.kParamAdminAction));

        if (req.getParameter("subactionSubmit") != null) {
            action = req.getParameter("subaction");
        }
        if (req.getParameter("subaction2Submit") != null) {
            action = req.getParameter("subaction2");
        }
        if (req.getParameter("subaction3Submit") != null) {
            action = req.getParameter("subaction3");
        }

        if (action == null) action = "";
        try {
            UserHome.authenticateUser(req);
            if (!(UserHome.getSessionFlag(req, UserHome.kExtranetAdminFlag) && UserHome.getSessionFlag(req, UserHome.kExtranetAuthenticatedFlag))) {
                // req.getRequestDispatcher("/user/edituser.jsp").forward(req, resp);

            } else {

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                //log.debug(action);

//Start new user data entry
                if (action.equals("create")) {


                    OwdUser user = UserFactory.getNewUser();


                    req.getSession(true).setAttribute("user.currentuser", user);

                    req.getRequestDispatcher("/user/user_editor.jsp").forward(req, resp);

                } else if (action.equals("user-edit")) {
//edit existing
                    try {
                        OwdUser user = UserFactory.getUserFromUserID(new Integer((String) req.getParameter("user_id")), Integer.decode(UserHome.getSessionString(req, UserHome.kExtranetClientID)).intValue(), UserHome.getSessionFlag(req, UserHome.kExtranetInternalFlag));
                        if (user == null) throw new Exception("Unexpected error finding indicated user for editing.");


                        req.getSession(true).setAttribute("user.currentuser", user);

                        req.getRequestDispatcher("/user/user_editor.jsp").forward(req, resp);

                    } catch (Exception bex) {
                        req.setAttribute("errormessage", bex.getMessage());
                        bex.printStackTrace();
                        req.getRequestDispatcher("/user/home.jsp").forward(req, resp);
                    }
                    //search
                } else if (action.equals("user-search")) {

                    try {

                        req.getRequestDispatcher("/user/home.jsp").forward(req, resp);

                    } catch (Exception bex) {
                        req.setAttribute("errormessage", bex.getMessage());
                        bex.printStackTrace();
                        req.getRequestDispatcher("/user/home.jsp").forward(req, resp);
                    }

//save user
                }  else if (action.equals("user-delete")) {

                    try {

                        OwdUser user = (OwdUser) req.getSession(true).getAttribute("user.currentuser");
                        if (user == null) user = new OwdUser();

                        if(user.getId()==null)
                        {
                            user = new OwdUser();
                        }else
                        {
                            user = (OwdUser) HibernateSession.currentSession().load(OwdUser.class, user.getId());
                        }
                        req.getSession(true).setAttribute("user.currentuser", user);


                        if(user.getId()!=null)
                        {
                            UserManager.deleteUser(user);

                        }


                        req.getSession(true).setAttribute("user.currentuser", null);
                        req.getRequestDispatcher("/user/home.jsp").forward(req, resp);


                    } catch (Exception bex) {
                        req.setAttribute("errormessage", bex.getMessage());
                        bex.printStackTrace();
                        req.getRequestDispatcher("/user/user_editor.jsp").forward(req, resp);
                    }

//save user
                }else if (action.equals("edit-save") || action.equals("create-save")) {
                    try {
                        OwdUser user = (OwdUser) req.getSession(true).getAttribute("user.currentuser");
                        if (user == null) user = new OwdUser();

                        if(user.getId()==null)
                        {
                            user = new OwdUser();
                        }else
                        {
                        user = (OwdUser) HibernateSession.currentSession().load(OwdUser.class, user.getId());
                        }
                        req.getSession(true).setAttribute("user.currentuser", user);
                        updateUserFromEditPage(user, req);


                        //log.debug("CHECKING user id; "+user.getId());
                        if(user.getId()==null)
                        {
                            //log.debug("VALIDATING user id; "+user.getId());

                            UserManager.validateUserUnique(user);
                            //log.debug("VALIDATED user id; "+user.getId());

                        }
                        UserManager.saveOrUpdateUser(user);

                        user = (OwdUser) HibernateSession.currentSession().load(OwdUser.class, user.getId());
                        req.getSession(true).setAttribute("user.currentuser", user);
                        req.getRequestDispatcher("/user/user_editor.jsp").forward(req, resp);

                    } catch (Exception bex) {
                        req.setAttribute("errormessage", bex.getMessage());
                        bex.printStackTrace();
                        req.getRequestDispatcher("/user/user_editor.jsp").forward(req, resp);
                    }
//default
                } else {
                    req.getRequestDispatcher("/user/home.jsp").forward(req, resp);
                }

            }
        } catch (Exception ex) {
            req.setAttribute("errormessage", ex.getMessage());
            ex.printStackTrace();

            try {
                req.getRequestDispatcher("/user/home.jsp").forward(req, resp);
            } catch (Exception exe) {
                exe.printStackTrace();
            }
        } finally {
         //   HibernateSession.closeSession();

        }


    }




    private void updateUserFromEditPage(OwdUser user, HttpServletRequest req) throws Exception {

        user.setEmail(req.getParameter("user[email]"));
        user.setName(req.getParameter("user[name]"));
        user.setLogin(req.getParameter("user[login]"));
        user.setPassword(req.getParameter("user[password]"));

        user.setClientFkey(Integer.decode(req.getParameter("clientFkey")).intValue());

        user.setIsClientAdmin(req.getParameter("isClientAdmin")!=null);

        user.setIsAdmin(user.isIsClientAdmin() && user.getClientFkey()==0);
                    //log.debug("active flag="+req.getParameter("grp_extranet"));
        //currently, this field is unused and is always set to true
        user.setIsActive(req.getParameter("grp_extranet")!=null);

    }


}
