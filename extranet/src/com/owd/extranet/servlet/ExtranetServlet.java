package com.owd.extranet.servlet;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.user.UserFactory;
import com.owd.core.business.user.UserManager;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.managers.JasperReportPrintManager;
import com.owd.hibernate.generated.OwdUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;


public class ExtranetServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();



    public static final String kExtranetCurrMgr ="extranet.currmgr";

    public static final String kExtranetClientID ="extranet.clientid";

    public static final String kExtranetAdminFlag ="extranet.isAdmin";

    public static final String kExtranetInternalFlag ="extranet.isInternal";

    public static final String kExtranetAuthenticatedFlag ="extranet.auth";

    public static final String kExtranetCSRFlag ="extranet.callcenter";



    public static final String kParamAdminAction ="act";



    public static final String kParamChangeClientTo ="cid";

    public static final String kParamChangeClientAction ="cngCid";



    public static final String kParamChangeMgrTo ="mgr";

    public static final String kParamChangeMgrAction ="cngMgr";





    public static final String kParamDownloadAction= "download";



    public static final String kBulkLoadSaveDir="."+File.separator+"uploads";



    public static final String kExtranetURLPath = "/extranet/extranet.jsp";

    public static final String kExtranetServletPath = "";





    public void init(ServletConfig config)

        throws ServletException

    {
        log.debug("initting extranet servlet");
        super.init(config);



    }



    public void destroy()

    {

        super.destroy();



    }



    public static  boolean getSessionFlag(HttpServletRequest req, String key)

    {

        String val = "";



        try{

            val = (String) req.getSession(true).getAttribute(key);

            if (val.equals("Y"))

                return true;

            else

                return false;

        }catch(Exception ex){



            return false;

        }



    }



    public static  void setSessionFlag(HttpServletRequest req, String key, boolean val)

    {

        try{

            if (val)

                req.getSession(true).setAttribute(key,"Y");

            else

                req.getSession(true).setAttribute(key,"N");

        }catch(Exception ex){}

    }



    public static String getSessionString(HttpServletRequest req, String key)

    {

        String val = "";



        try{

            val = (String) req.getSession(true).getAttribute(key);

            if (val == null)

                val = "";

        }catch(Exception ex){}



        return val.trim();

    }



    public static  void setSessionString(HttpServletRequest req, String key, String val)

    {

        try{

            req.getSession(true).setAttribute(key,val);

        }catch(Exception ex){}

    }



    //GET requests not supported

    public void doGet (HttpServletRequest req, HttpServletResponse resp)

        throws ServletException

    {
        log.debug("doGet");
        doPost(req,resp);

    }



    //all requests should be POST

    public void doPost (HttpServletRequest req, HttpServletResponse resp)

        throws ServletException

    {
        log.debug("doPost");
        try{



            //////log.debug("authenticating");

            if("logout".equals(getStringParam(req,"logout","")))

            {



                setSessionFlag(req,kExtranetAuthenticatedFlag,false);

                setSessionString(req,kExtranetClientID,null);

                req.getSession(true).invalidate();

                req.getRequestDispatcher("/defaulter.jsp").forward(req,resp);



            }else{

            authenticateUser(req);

            if(getSessionFlag(req,kExtranetAuthenticatedFlag))

            {

                if(kParamChangeClientAction.equals(getStringParam(req,kParamAdminAction,"")))

                {

                    if(getSessionFlag(req,kExtranetInternalFlag) || getSessionFlag(req,kExtranetCSRFlag))

                    {

                        setSessionString(req,kExtranetClientID,getStringParam(req,kParamChangeClientTo,""));

                    }

                }

                if(kParamChangeMgrAction.equals(getStringParam(req,kParamAdminAction,"")))

                {

                    if(getSessionFlag(req,kExtranetInternalFlag))

                    {

                        setSessionString(req,kExtranetCurrMgr,getStringParam(req,kParamChangeMgrTo,""));

                    }

                }

                if(kParamDownloadAction.equals(getStringParam(req,kParamAdminAction,"")))

                {

                    if(ExtranetManager.kInvMgrName.equals(getStringParam(req,ExtranetServlet.kParamChangeMgrTo,"")))

                    {

                            getManager(req,resp).download(req,resp);



                    }



                } else  if("get-scan".equals(getStringParam(req,OrdersManager.kParamOrderMgrAction,"")))
                    {
                        OrdersManager.getScan(req,resp);
                    }else  if("get-comminvoice".equals(getStringParam(req,OrdersManager.kParamOrderMgrAction,"")))
                    {
                        JasperReportPrintManager.getCommercialInvoice(req, resp);
                    }else

                {
                    startup(req,resp);
                    resp.setContentType("text/html");

                    req.getRequestDispatcher("/extranet/extranetdisplaytop.jsp").include(req,resp);
                    ExtranetServlet.getManager(req,resp).getManagerContent(req,resp);
                    req.getRequestDispatcher("/extranet/extranetdisplaybottom.jsp").include(req,resp);
                }



            }else{

                //not authenticatable

                req.getRequestDispatcher("/error.jsp").forward(req,resp);

            }

            }



        }catch(Exception ex){ex.printStackTrace();}



    }



    public void startup(HttpServletRequest req, HttpServletResponse resp)

        throws ServletException

    {

        try{

            //////log.debug("authenticating");

            authenticateUser(req);

            if(getSessionFlag(req,kExtranetAuthenticatedFlag))

            {

                if(kParamChangeClientAction.equals(getStringParam(req,kParamAdminAction,"")))

                {

                    setSessionString(req,kExtranetClientID,getStringParam(req,kParamChangeClientTo,""));


                }

                if(kParamChangeMgrAction.equals(getStringParam(req,kParamAdminAction,"")))

                {

                    setSessionString(req,kExtranetCurrMgr,getStringParam(req,kParamChangeMgrTo,""));

                }

                //getHeader(req,resp);

                //doAction(req,resp);

                //getFooter(req,resp);

            }else{

                //not authenticatable

                ServletContext sc = getServletConfig().getServletContext();

                RequestDispatcher rd = sc.getRequestDispatcher("/error.jsp") ;

                rd.forward(req,resp);

            }



        }catch(Exception ex){ex.printStackTrace();}



    }


    public static final String kExtranetAuthenticatedUserName = "extranet.authlogin";

    public static void authenticateUser(HttpServletRequest req)

    {
        log.debug("Principal="+req.getUserPrincipal());
         if(req.getUserPrincipal() !=null)
         {
             log.debug("Name="+req.getUserPrincipal().getName());
         }
        log.debug("AuthName="+getSessionString(req,kExtranetAuthenticatedUserName));
        if (!getSessionFlag(req,kExtranetAuthenticatedFlag) || (!(req.getUserPrincipal().getName().equals(getSessionString(req,kExtranetAuthenticatedUserName))))) {
                   setSessionFlag(req, kExtranetAuthenticatedFlag, false);
             log.debug("bad");
            setSessionFlag(req,kExtranetAdminFlag,false);

        //	setSessionFlag(req,kExtranetInternalFlag,rs.getString(1).equals("0")?true:false);

            setSessionString(req,kExtranetClientID,"");

            setSessionString(req,kExtranetCurrMgr,"Home");

            setSessionFlag(req,kExtranetAuthenticatedFlag,false);
            setSessionFlag(req,kExtranetCSRFlag,false);
            setSessionFlag(req,kExtranetInternalFlag,false);


            String user = req.getUserPrincipal().getName();



            Connection conn = null;

            Statement stmt = null;

            ResultSet rs = null;

            log.debug("before try");



            try{

                OwdUser currUser = UserFactory.getOwdUserFromLogin(user);
                log.debug("got OwdUser");
                if(currUser != null)
                {

                    setSessionFlag(req,kExtranetCSRFlag, UserManager.listAllGroupsForUser(currUser).contains("callcenter"));
                    setSessionFlag(req,kExtranetInternalFlag, UserManager.listAllGroupsForUser(currUser).contains("internal"));
                       setSessionString(req, kExtranetAuthenticatedUserName, user);
                }





                conn = ConnectionManager.getConnection();



                   stmt = conn.createStatement();



               String sqlQuery = "select client_fkey, is_admin "

                               +"from owd_users (NOLOCK) where login = \'"+user+"\' and (client_fkey = 0 OR client_fkey in (select client_id from owd_client))";

     //////log.debug(sqlQuery);

                   rs = stmt.executeQuery(sqlQuery);


                    log.debug("got OwdUser stmt");
                  if(rs.next())

                  {

                               //////log.debug("found user");

                      setSessionFlag(req,kExtranetAdminFlag,(rs.getInt(2)==1));

                  //	setSessionFlag(req,kExtranetInternalFlag,rs.getString(1).equals("0")?true:false);

                      setSessionString(req,kExtranetClientID,rs.getString(1).equals("0")?"55":rs.getString(1));

                      setSessionString(req,kExtranetCurrMgr,"Home");

                      setSessionFlag(req,kExtranetAuthenticatedFlag,true);

                  }




                   } catch(Exception e)

             {


                    setSessionString(req, kExtranetAuthenticatedUserName, "");
                 setSessionFlag(req,kExtranetAuthenticatedFlag,false);

             }



          finally {



            try {

                    if (rs != null)

                      rs.close();

                      if (stmt != null)

                      stmt.close();

                      conn.close();

                }

                    catch (Exception e) {
                         e.printStackTrace();


                    }



              }

        }

        else

        {

           log.debug("GOOD!");

            //get user info from database

        }

    }



    protected void doAction(HttpServletRequest req, HttpServletResponse resp) throws Exception

    {







        ServletContext sc = getServletConfig().getServletContext();

        RequestDispatcher rd = sc.getRequestDispatcher("/webapps/extranetdisplay.jsp") ;

        rd.forward(req,resp);

    }



    public static ExtranetManager getManager(HttpServletRequest req, HttpServletResponse resp)

    {

        if(ExtranetManager.kInvMgrName.equals(getSessionString(req,ExtranetServlet.kExtranetCurrMgr)))

        {

                //////log.debug("invmgr");

                return new InventoryManager();



        } else if(ExtranetManager.kOrdersMgrName.equals(getSessionString(req,ExtranetServlet.kExtranetCurrMgr)))

        {

                //////log.debug("ordersmgr");

                return new OrdersManager();

        }
else if(ExtranetManager.kCouponsMgrName.equals(getSessionString(req,ExtranetServlet.kExtranetCurrMgr)))

        {

                //////log.debug("ordersmgr");

                return new CouponManager();



        } else if(ExtranetManager.kSuppliersMgrName.equals(getSessionString(req,ExtranetServlet.kExtranetCurrMgr)))

        {

                //////log.debug("ordersmgr");

                return new SupplierManager();



        }else if(ExtranetManager.kSuppliersMgrName.equals(getSessionString(req,ExtranetServlet.kExtranetCurrMgr)))

        {

                //////log.debug("autoshipmgr");

                return new SupplierManager();



        }
        else{

                //////log.debug("unrecognized action - go home");

                ExtranetServlet.setSessionString(req,ExtranetServlet.kExtranetCurrMgr,ExtranetManager.kManagers[0]);

                return getManager(req,resp);

        }



    }







    public static String getStringParam(HttpServletRequest req,String paramName, String defaultValue)

    {

        String param = req.getParameter(paramName);



        if(param == null)

            param = defaultValue;



        return param;



    }



    public static Vector getStringVector(HttpServletRequest req,String paramName, Vector defaultValue)

    {

        String[] param_ay = req.getParameterValues(paramName);

        Vector param = new Vector();



        if(param_ay == null)

            param = defaultValue;

        else

        {

            if (param_ay.length > 0)

            {

                for (int i=0;i<param_ay.length;i++)

                {

                param.addElement(param_ay[i]);

                }

            }

        }



        return param;



    }



    public static int getIntegerParam(HttpServletRequest req,String paramName, int defaultValue)

    {

        int param = 0;



        try{

            param = new Integer(req.getParameter(paramName)).intValue();

        }catch(Exception ex){param = defaultValue;}



        return param;



    }



    public static void getHeader(HttpServletRequest req, HttpServletResponse resp) throws IOException

    {

            PrintWriter out = resp.getWriter();

        out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\">");

out.write("<HTML>");

out.write("<HEAD>");

out.write("<TITLE>");

out.write("One World Direct Extranet");

out.write("</TITLE>");

out.write("</HEAD>");

out.write("<BODY bgcolor=\"#FFFFFF\">");

out.write("<BASEFONT FACE=\"Geneva,Verdana,Helvetica\">");



/*

out.write("<TABLE BORDER=\"0\" WIDTH=\"100%\">");

out.write("<TR VALIGN=\"CENTER\">");

out.write("<TD ALIGN=\"left\" NOWRAP>");

out.write("<FONTx SIZE=\"+2\">");

out.write("<B>One World Direct Extranet</B>");

out.write("</FONT>");

out.write("</TD>");

out.write("<TD ALIGN=\"right\" VALIGN=\"CENTER\" WIDTH=\"100%\" NOWRAP>");

out.write(getClientSelector(req));

out.write("</TD>");

out.write("</TR>");

out.write("</TABLE>");

*/

out.write("<TABLE  width='100%' cellpadding='0' cellspacing='0' border='0' bgcolor='#000000'>");

out.write("<TR>");

out.write("<TD bgcolor=#CCCCFF align=left valign=CENTER><B>One World Direct Extranet</B></FONT></TD>");

out.write("<TD bgcolor=#CCCCFF align=right valign=CENTER>"+getClientSelector(req)+"</TD>");

out.write("</TD></TR></TABLE>");



out.write("<HR ALIGN=\"center\" SIZE=\"5\" NOSHADE>");

out.write("<!-- End Header -->");

    }



    public static void getFooter(HttpServletRequest req, HttpServletResponse resp)  throws IOException

    {



        PrintWriter out = resp.getWriter();



        out.write("<!-- Begin Footer -->");

out.write("<HR ALIGN=\"center\" SIZE=\"5\" NOSHADE>");


out.write("Copyright 2000,");

out.write("<A HREF=\"http://www.owd.com/\">");

out.write("One World Direct</A>. All Rights Reserved.");


out.write("</BODY>");

out.write("</HTML>");

    }





    static final public String kServletName = "Extranet";



    public String getServletInfo() {

        return "One World Extranet v";

    }





    public static String getClientSelector(HttpServletRequest req)

    {

        StringBuffer sb = new StringBuffer();



        if(getSessionFlag(req,kExtranetInternalFlag) || getSessionFlag(req,kExtranetCSRFlag))

        {

            return "<INPUT TYPE=HIDDEN NAME=\""+com.owd.extranet.servlet.ExtranetServlet.kParamAdminAction

                        +"\"  VALUE=\""+com.owd.extranet.servlet.ExtranetServlet.kParamChangeClientAction

                        +"\"><SELECT NAME=\""+com.owd.extranet.servlet.ExtranetServlet.kParamChangeClientTo+"\" onChange=\"this.form.submit()\">"

                        +getClientSelector(req,kParamChangeClientTo,getSessionString(req,kExtranetClientID))

                        +"</SELECT>";

        }else{

        Connection con = null;







            try{






                con = com.owd.core.managers.ConnectionManager.getConnection();



            Client client = Client.getClientForID(con,getSessionString(req,kExtranetClientID));

            sb.append(client.company_name);



                   } catch(Exception e)

             {

                 e.printStackTrace();

             }



          finally {

                  try{con.close();}catch(Exception ex){}

              }





        }





        return sb.toString();



    }



static public String getClientSelector(HttpServletRequest req, String name, String id)

{



    String currClient = id;



    Hashtable clientNames = new Hashtable();





            Connection conn = null;

            Statement stmt = null;

            ResultSet rs = null;

            StringBuffer selector = new StringBuffer();



            try{







                conn = com.owd.core.managers.ConnectionManager.getConnection();



                   stmt = conn.createStatement();



               String sqlQuery = "select client_id, company_name "+"from owd_client (NOLOCK) where is_active = 1 order by company_name";



                   rs = stmt.executeQuery(sqlQuery);



                  while(rs.next())

                  {

                            if(!(rs.getString(1).equals("")))

                      {

                      selector.append("<OPTION VALUE="+rs.getString(1));



                if(currClient != null & (rs.getString(1).equals(currClient)))

                    selector.append(" SELECTED");



                      selector.append(">"+rs.getString(2));

                      clientNames.put(rs.getString(1),rs.getString(2));

                      }

                  }



                   } catch(Exception e)

             {

                 selector.append("<OPTION>SQLException");

             }



          finally {



            try {

                    if (rs != null)

                      rs.close();

                      if (stmt != null)

                      stmt.close();

                      conn.close();

                }

                    catch (Exception e) {

                          return selector.toString();

                    }

                return selector.toString();

              }















}



}

