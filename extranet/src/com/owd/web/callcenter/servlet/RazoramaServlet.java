package com.owd.web.callcenter.servlet;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.managers.ConnectionManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jul 30, 2003
 * Time: 8:25:03 AM
 * To change this template use Options | File Templates.
 */
public class RazoramaServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();
    java.sql.Connection cxn = null;
    java.sql.PreparedStatement stmt = null;
    String uqhandle = new String();
    String order_refnum = new String();
    int client = 0;
    double subtotal = 0.0;

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

        try {


            uqhandle = req.getParameter("uqhandle");
            order_refnum = req.getParameter("order_refnum");
            client = Integer.parseInt(req.getParameter("com.owd.api.client"));
            subtotal = Double.parseDouble(stripmoney(req.getParameter("total").toString()));

            log.debug(uqhandle);
            log.debug(order_refnum);
            log.debug(client);
            log.debug(subtotal);

            String sql = "insert into dbo.callcenter_orders (client_fkey, order_refnum, order_subtotal, uqcontact_id) values (?,?,?,?)";

            cxn = ConnectionManager.getConnection();

            stmt = cxn.prepareStatement(sql);
            stmt.setInt(1, client);
            stmt.setString(2, order_refnum);
            stmt.setDouble(3, subtotal);
            stmt.setString(4, uqhandle);


            int rows = stmt.executeUpdate();
            cxn.commit();
            cxn.close();

            //req.getRequestDispatcher("http://danny.owd.com:8080/webapps/callcenter/callformcomplete.jsp").forward(req, resp);
            resp.sendRedirect("http://danny.owd.com:8080/webapps/callformcomplete.jsp");


        } catch (Exception ex) {
            System.out.print(ex.toString());
        } finally {
            try {
                cxn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static String stripmoney(String amount) {
        String newamount = amount.replace('$', ' ');
        return newamount.trim();
    }

}
