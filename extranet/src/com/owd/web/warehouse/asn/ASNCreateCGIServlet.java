package com.owd.web.warehouse.asn;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.asn.ASNFactory;
import com.owd.core.business.asn.ASNManager;
import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Session;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 25, 2004
 * Time: 9:04:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ASNCreateCGIServlet extends HttpServlet {
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

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        try {
//Start new ASN data entry

            Session sess = HibernateSession.currentSession();

            String poNum = req.getParameter("PO");
            String vendor = req.getParameter("VENDOR");
            String clientID = req.getParameter("ClientID");

            log.debug("got po/vendor/com.owd.api.client:" + poNum + "/" + vendor + "/" + clientID);

            Asn asn = ASNFactory.getNewAsn(new Integer(clientID).intValue());
            asn.setClientRef("ASN" + poNum.trim());
            asn.setClientPo(poNum.trim());
            Calendar expected = Calendar.getInstance();
            expected.add(Calendar.DATE, 7);
            asn.setExpectDate(expected.getTime());
            asn.setShipperName(vendor.trim());
            asn.setCreatedBy("Email Import");
            asn.setIsAutorelease(1);
            asn.setCreatedDate(Calendar.getInstance().getTime());


            Enumeration nameEnum = req.getParameterNames();
            while (nameEnum.hasMoreElements()) {
                String name = (String) nameEnum.nextElement();
                if (name.startsWith("SKUQTY")) {
                    log.debug("checking name " + name);
                    log.debug("sku name = " + "SKU" + name.substring(name.indexOf("SKU") + 6));
                    String qty = ((String) req.getParameter(name)).trim();
                    String sku = ((String) req.getParameter("SKU" + name.substring(name.indexOf("SKU") + 6))).trim();
                    log.debug("sku/qty = " + sku + "/" + qty);
                    ASNEditServlet.addItemToASN(new Integer(qty).intValue(), sku, asn, req);


                }
            }


            ASNManager.saveOrUpdateASN(asn);

            // sess.flush();
            // HibUtils.commit(sess);

            resp.getWriter().println("SUCCESS");


        } catch (Exception ex) {
            try {
                resp.getWriter().println("ERROR:" + ex.getMessage());
            } catch (Exception tx) {
                tx.printStackTrace();
            }
            ex.printStackTrace();


        } finally {
            HibernateSession.closeSession();
        }


    }


}
