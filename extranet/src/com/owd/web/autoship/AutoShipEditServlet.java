package com.owd.web.autoship;

import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.hibernate.generated.OwdOrderAutoHistory;
import com.owd.hibernate.generated.OwdOrderAutoItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.autoship.AutoShipFactory;
import com.owd.core.business.autoship.AutoShipManager;
import com.owd.hibernate.*;
import com.owd.web.servlet.ExtranetServlet;
import com.owd.web.warehouse.asn.ASNHome;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**                                                                  
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:56:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoShipEditServlet extends HttpServlet {
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

    static DateFormat asdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    //all requests should be POST
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        String error = "";

        String action = ((String) req.getParameter(AutoShipHome.kParamAdminAction));

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

        AutoShipHome.authenticateUser(req);


        log.debug(action);
        try {
//Start new subscription data entry
            if (action.equals("create")) {

                String cid = AutoShipHome.getSessionString(req, AutoShipHome.kExtranetClientID);
                if ("0".equals(cid)) cid = "55";

                OwdOrderAuto autos = AutoShipFactory.getNewAutoShip(new Integer(cid).intValue());


                req.setAttribute("autoship.currentautoship", autos);

                req.getRequestDispatcher("/clienttools/autoship/editauto.jsp").forward(req, resp);

            } else if (action.equals("auto-edit")) {
//edit existing
                try {
                    OwdOrderAuto aship = AutoShipFactory.getAutoshipFromAutoShipID(new Integer((String) req.getParameter("sub_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), AutoShipHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));

                    if (aship == null) throw new Exception("Unexpected error finding indicated subscription for editing.");


                    req.setAttribute("autoship.currentautoship", aship);
                  

                    req.getRequestDispatcher("/clienttools/autoship/editauto.jsp").forward(req, resp);

                } catch (Exception bex) {
                    req.setAttribute("errormessage", bex.getMessage());
                    bex.printStackTrace();
                    req.getRequestDispatcher("/clienttools/autoship/home.jsp").forward(req, resp);
                }
                //search
            } else if (action.equals("auto-search")) {

                try {

                    req.getRequestDispatcher("/clienttools/autoship/searchauto.jsp").forward(req, resp);

                } catch (Exception bex) {
                    req.setAttribute("errormessage", bex.getMessage());
                    bex.printStackTrace();
                    req.getRequestDispatcher("/clienttools/autoship/home.jsp").forward(req, resp);
                }

//save autoship
            } else if (action.equals("edit-save") || action.equals("create-save")) {
                try {

                    OwdOrderAuto aship = null;
                    if(action.equals("edit-save"))
                    {
                        aship = AutoShipFactory.getAutoshipFromAutoShipID(new Integer((String) req.getParameter("sub_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), AutoShipHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));
                    }   else
                    {
                        String cid = AutoShipHome.getSessionString(req, AutoShipHome.kExtranetClientID);
                        if ("0".equals(cid)) cid = "55";

                        aship = AutoShipFactory.getNewAutoShip(new Integer(cid).intValue());

                    }

                    if (aship == null) throw new Exception("Unexpected error finding current subscription for editing.");

                    updateAutoShipFromEditPage(aship, req);
                    saveOrUpdateAutoShip(aship, req);

                    aship = (OwdOrderAuto) HibernateSession.currentSession().load(OwdOrderAuto.class, aship.getAutoShipId());
                    req.setAttribute("autoship.currentautoship", aship);
                    req.getRequestDispatcher("/clienttools/autoship/editauto.jsp").forward(req, resp);

                } catch (Exception bex) {
                    req.setAttribute("errormessage", bex.getMessage());
                    bex.printStackTrace();
                    req.getRequestDispatcher("/clienttools/autoship/editauto.jsp").forward(req, resp);
                }
//add memo
            } else if (action.equals("add-comment")) {
                try {
                    OwdOrderAuto aship = AutoShipFactory.getAutoshipFromAutoShipID(new Integer((String) req.getParameter("sub_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), AutoShipHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));
                                      if (aship == null) throw new Exception("Unexpected error finding current subscription for editing.");

                    OwdOrderAutoHistory autoh = new OwdOrderAutoHistory();
                    autoh.setCreated(Calendar.getInstance().getTime());
                    autoh.setType(1);
                    autoh.setOwdOrderAuto(aship);
                    aship.getOwdOrderAutoHistories().add(autoh);


                    autoh.setMessage(req.getParameter("message") + " (by " + req.getUserPrincipal().getName() + ")");

                    updateAutoShipFromEditPage(aship, req);
                    saveOrUpdateAutoShip(aship, req);

                    aship = (OwdOrderAuto) HibernateSession.currentSession().load(OwdOrderAuto.class, aship.getAutoShipId());
                    req.setAttribute("autoship.currentautoship", aship);

                    req.getRequestDispatcher("/clienttools/autoship/editauto.jsp").forward(req, resp);

                } catch (Exception bex) {
                    req.setAttribute("errormessage", bex.getMessage());
                    bex.printStackTrace();
                    req.getRequestDispatcher("/clienttools/autoship/editauto.jsp").forward(req, resp);
                }
//delete autoship
            } else if (action.equals("delete-auto")) {
                try {
                    OwdOrderAuto aship = AutoShipFactory.getAutoshipFromAutoShipID(new Integer((String) req.getParameter("sub_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), AutoShipHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));
                    if (aship == null) throw new Exception("Unexpected error finding current subscription for editing.");

                    AutoShipManager.deleteAutoShip(aship);

                      req.getRequestDispatcher("/clienttools/autoship/searchauto.jsp").forward(req, resp);
                } catch (Exception bex) {
                    req.setAttribute("errormessage", bex.getMessage());
                    bex.printStackTrace();
                    req.getRequestDispatcher("/clienttools/autoship/editauto.jsp").forward(req, resp);
                }
//add item
            } else if (action.equals("add-item")) {

                OwdOrderAuto aship = AutoShipFactory.getAutoshipFromAutoShipID(new Integer((String) req.getParameter("sub_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), AutoShipHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));
                

                if (aship == null) throw new Exception("Unexpected error finding current subscription for editing.");

                try {

                    updateAutoShipFromEditPage(aship, req);

                    int quantity_expected = 0;
                    double unit_price = 0.00;

                    try {
                        quantity_expected = Integer.decode((String) req.getParameter("qtysku")).intValue();
                    } catch (NumberFormatException nex) {
                        throw new Exception("SKU quantity must be a whole number greater than zero");
                    }

                    try {
                        unit_price = new Double((String) req.getParameter("pricesku")).doubleValue();
                    } catch (NumberFormatException nex) {
                        throw new Exception("SKU price must be a decimal number greater or equal to zero");
                    }

                    String addSKU = req.getParameter("addsku");
                    if (addSKU == null) addSKU = "";
                    addSKU = addSKU.trim();

                    addItemToAutoShip(quantity_expected, addSKU, unit_price, aship);
                    saveOrUpdateAutoShip(aship, req);

                } catch (Exception nex) {
                    req.setAttribute("errormessage", nex.getMessage());
                    nex.printStackTrace();
                }
                    req.setAttribute("autoship.currentautoship", aship);
                req.getRequestDispatcher("/clienttools/autoship/editauto.jsp").forward(req, resp);

                //change com.owd.api.client
            } else if (AutoShipHome.kParamChangeClientAction.equals(action)) {

                if (AutoShipHome.getSessionFlag(req, AutoShipHome.kExtranetAuthenticatedFlag) && AutoShipHome.getSessionFlag(req, AutoShipHome.kExtranetInternalFlag)) {
                    //req.getSession(true).removeAttribute("autoship.currentautoship");
                    AutoShipHome.setSessionString(req, AutoShipHome.kExtranetClientID, req.getParameter(AutoShipHome.kParamChangeClientTo));
                }
                req.getRequestDispatcher("/clienttools/autoship/home.jsp").forward(req, resp);
//Default servlet response
            } else {
                req.getRequestDispatcher("/clienttools/autoship/home.jsp").forward(req, resp);
            }


        } catch (Exception ex) {
            req.setAttribute("errormessage", ex.getMessage());
            ex.printStackTrace();

            try {
                req.getRequestDispatcher("/clienttools/autoship/home.jsp").forward(req, resp);
            } catch (Exception exe) {
                exe.printStackTrace();
            }
        } finally {
            HibernateSession.closeSession();
        }


    }

    public static void addItemToAutoShip(int quantity, String addSKU, double unitPrice, OwdOrderAuto autos) throws Exception {
        try {


            Session sess = HibernateSession.currentSession();

            Criteria crit = sess.createCriteria(OwdInventory.class);

            crit.setMaxResults(1000);
            crit.add(Restrictions.eq("owdClient.clientId", autos.getClientFkey()))
                    .add(Restrictions.eq("inventoryNum", addSKU));

            List list = crit.list();
            if (list.size() == 0) throw new Exception("SKU " + addSKU + " not found. SKU values are case-sensitive. Please check the SKU and try again.");
            OwdInventory inventoryItem = null;
            if (list.size() == 1) {
                inventoryItem = ((OwdInventory) list.get(0));
            } else {
                Iterator iti = list.iterator();
                while (iti.hasNext()) {
                    OwdInventory item = (OwdInventory) iti.next();

                    if (item.getInventoryNum().equals(addSKU)) {
                        inventoryItem = item;

                    }
                }
            }

            if (inventoryItem == null) {
                throw new Exception("SKU " + addSKU + " not found. SKU values are case-sensitive. Please check the SKU and try again.");


            }


            if (quantity < 1) throw new Exception("SKU quantity must be a whole number greater than zero");

            boolean foundItem = false;
            if (addSKU.length() < 1) throw new Exception("You must provide a valid SKU to add an item.");

          //  OwdOrderAutoHistory h = new OwdOrderAutoHistory();
          //  h.

          
            OwdOrderAutoItem item = new OwdOrderAutoItem();
            Iterator it = autos.getOwdOrderAutoItems().iterator();
            while (it.hasNext()) {
                OwdOrderAutoItem itx = (OwdOrderAutoItem) it.next();

                if ((itx).getSku().equals(inventoryItem.getInventoryNum())) {
                    foundItem = true;
                    item = itx;
                    item.setQuantity(quantity + item.getQuantity());
                    item.setUnitPrice(new BigDecimal(unitPrice));


                }

            }

            if (!foundItem) {
                item.setOwdOrderAuto(autos);
                item.setSku(inventoryItem.getInventoryNum());
                item.setQuantity(quantity);
                item.setUnitPrice(new BigDecimal(unitPrice));

                //   item.setInventoryFkey(inventoryItem.getInventoryId().intValue());
                autos.getOwdOrderAutoItems().add(item);
            }
        } catch (Exception ex) {

            throw ex;
        } finally {
            HibernateSession.closeSession();
        }
    }

    private static void saveOrUpdateAutoShip(OwdOrderAuto autos, HttpServletRequest req) throws Exception {
        try {


            AutoShipManager.saveOrUpdateAutoShip(autos, autos.getAutoShipId() == null ? "New subscription created by " + req.getUserPrincipal().getName() : "Subscription edited by " + req.getUserPrincipal().getName());


        } catch (Exception ex) {
            req.setAttribute("errormessage", ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
            HibernateSession.closeSession();
        }
    }

    /*

    private void updateRcvedASNFromEditPage(Asn asn, HttpServletRequest req, DateFormat df) throws Exception {
        asn.setShipperName(req.getParameter("shipperName"));
        asn.setCarrier(req.getParameter("carrier"));
        asn.setClientPo(req.getParameter("clientPo"));
        asn.setClientRef(req.getParameter("clientRef"));
        asn.setIsAutorelease(("1".equals(req.getParameter("isAutoRelease")) ? 1 : 0));
        asn.setNotes(req.getParameter("notes"));
        asn.setHasBlind(("1".equals(req.getParameter("hasBlind")) ? 1 : 0));



        //asn.setCreatedDate(Calendar.getInstance().getTime());
        //asn.set(req.getUserPrincipal().getName());



    }

   */
    private void updateAutoShipFromEditPage(OwdOrderAuto autos, HttpServletRequest req) throws Exception {
        autos.setBillName(req.getParameter("billName"));
        autos.setBillAddressOne(req.getParameter("billAddressOne"));
        autos.setBillAddressTwo(req.getParameter("billAddressTwo"));
        autos.setBillCompany(req.getParameter("billCompany"));
        autos.setBillCity(req.getParameter("billCity"));
        autos.setBillState(req.getParameter("billState"));
        autos.setBillZip(req.getParameter("billZip"));
        autos.setBillCountry(req.getParameter("billCountry"));
        autos.setShipName(req.getParameter("shipName"));
        autos.setShipAddressOne(req.getParameter("shipAddressOne"));
        autos.setShipAddressTwo(req.getParameter("shipAddressTwo"));
        autos.setShipCompany(req.getParameter("shipCompany"));
        autos.setShipCity(req.getParameter("shipCity"));
        autos.setShipState(req.getParameter("shipState"));
        autos.setShipZip(req.getParameter("shipZip"));
        autos.setShipCountry(req.getParameter("shipCountry"));
        autos.setShipPhone(req.getParameter("shipPhone"));
        autos.setBillPhone(req.getParameter("billPhone"));
        autos.setShipEmail(req.getParameter("shipEmail"));
        autos.setBillEmail(req.getParameter("billEmail"));

        autos.setCkBank(req.getParameter("ck_bank"));
        autos.setCkAcct(req.getParameter("ck_acct"));
        autos.setCkNumber(req.getParameter("ck_number"));
        autos.setFop(Integer.decode(req.getParameter("fop")));

        try {
            autos.setStatus(Integer.decode(req.getParameter("status")));

        } catch (NumberFormatException nex) {
            throw new Exception("Could not interpret newstatus setting - subscription not changed");
        }

        autos.setCalendarUnit(req.getParameter("calendarUnit").trim());
        autos.setCcNum(req.getParameter("ccNum"));

        autos.setRequirePayment(new Integer(("1".equals(req.getParameter("requirePayment")) ? 1 : 0)));

        autos.setShipMethod(req.getParameter("shipMethod"));
        autos.setNextShipmentDate(asdf.parse(req.getParameter("next_shipment_date")));


        try {
            autos.setCcExpMon(Integer.decode(req.getParameter("ccExpMon")));

        } catch (NumberFormatException nex) {
            throw new Exception("CC expiration month must be a whole number greater or equal to zero (Jan=1)");
        }
        try {
            autos.setCcExpYear(Integer.decode(req.getParameter("ccExpYear")));

        } catch (NumberFormatException nex) {
            throw new Exception("CC expiration Year must be a whole number greater or equal to zero");
        }

        try {
            autos.setShipCost(new BigDecimal(req.getParameter("shipCost")));

        } catch (NumberFormatException nex) {
            throw new Exception("Shipping cost value must be a decimal number greater or equal to zero");
        }

        try {
            autos.setSalesTax(new BigDecimal(req.getParameter("salesTax")));

        } catch (NumberFormatException nex) {
            throw new Exception("Sales tax value must be a decimal number greater or equal to zero");
        }
        try {
            autos.setShipInterval(Integer.decode(req.getParameter("shipInterval")));

        } catch (NumberFormatException nex) {
            throw new Exception("Subscription interval must be a whole number greater or equal to zero");
        }






        //  autos.setCreated(Calendar.getInstance().getTime());
        autos.setCreatedBy(req.getUserPrincipal().getName());

        Collection oldAutoItems = autos.getOwdOrderAutoItems();

        Enumeration enumm = req.getParameterNames();
        while (enumm.hasMoreElements()) {
            String name = (String) enumm.nextElement();
            if (name.endsWith("_qty")) {
                String sku = name.substring(0, name.indexOf("_qty"));
                int qty = 0;
                try {
                    qty = Integer.decode((String) req.getParameter(name)).intValue();
                    if (qty < 0) throw new Exception("SKU quantities must be whole numbers >= 0");
                } catch (NumberFormatException nex) {
                    throw new Exception("SKU quantities must be whole numbers >= 0");
                }
                float price = 0.0000f;
                try {
                    price = new Float(((String) req.getParameter(sku + "_price"))).floatValue();
                    if (price < 0) throw new Exception("SKU unit prices must be decimal numbers >= 0.00");
                } catch (NumberFormatException nex) {
                    throw new Exception("SKU unit prices must be decimal numbers >= 0.00");
                }
                Iterator it = oldAutoItems.iterator();
                boolean notFound = true;
                while (it.hasNext() && notFound) {
                    OwdOrderAutoItem item = (OwdOrderAutoItem) it.next();
                    if (item.getSku().equals(sku)) {
                        notFound = false;
                        log.debug("updating item qty=" + qty + ", price=" + price);
                        item.setQuantity(qty);
                        item.setUnitPrice(new BigDecimal(price));

                    }
                }
            }
        }

    }

    /*
    private void updateReceiveFromEditPage(Receive rcv, HttpServletRequest req) throws Exception {

        Session sess = HibernateSession.currentSession();
        sess.saveOrUpdate(rcv);
        sess.saveOrUpdate(rcv.getAsn());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mmaa");

        rcv.setCreatedBy(req.getUserPrincipal().getName());
        rcv.setCreatedOn(Calendar.getInstance().getTime());

        rcv.setEndTimestamp(df.parse(req.getParameter("endTimestamp")));
        rcv.setStartTimestamp(df.parse(req.getParameter("startTimestamp")));

        rcv.setIsAsnFound(("1".equals(req.getParameter("asnFound")) ? 1 : 0));
        rcv.setIsPackSlipFound(("1".equals(req.getParameter("packSlipFound")) ? 1 : 0));

        rcv.setNotes(req.getParameter("notes"));


        rcv.setReceiveBy(req.getParameter("receiveBy"));
        long minutes = (rcv.getEndTimestamp().getTime()-rcv.getStartTimestamp().getTime())/(60*1000);

        if(minutes<1)
        {
            throw new Exception("You must set start and end times to reflect a duration of at least 1 minute");
        }
        rcv.setBilledMinutes((int)(rcv.getEndTimestamp().getTime()-rcv.getStartTimestamp().getTime())/(60*1000));
            try {
            rcv.setCartonCount(Integer.decode(req.getParameter("cartonCount")).intValue());
        } catch (NumberFormatException nex) {
            throw new Exception("Carton count must be a whole number greater or equal to zero");
        }
        try {
            rcv.setPalletCount(Integer.decode(req.getParameter("palletCount")).intValue());

        } catch (NumberFormatException nex) {
            throw new Exception("Pallet count must be a whole number greater or equal to zero");
        }

        if (rcv.getPalletCount()+rcv.getCartonCount() < 1)
        {
           throw new Exception("Pallet or carton count must be a whole number greater or equal to zero");
        }


        Collection oldAsnItems = rcv.getAsn().getAsnItems();

                rcv.setReceiveItems(new ArrayList());

        Enumeration enum = req.getParameterNames();
        while (enum.hasMoreElements())
        {
            String name = (String)enum.nextElement();
            if(name.endsWith("_rcv_qty"))
            {
                String sku = name.substring(0,name.indexOf("_rcv_qty"));
                log.debug("found sku "+sku);
                int rqty = 0;
                int dqty = 0;
                try
                {
                    log.debug("decoding rqty");
                rqty = Integer.decode((String)req.getParameter(name)).intValue();
                    log.debug("decoding dqty");
                dqty = Integer.decode((String)req.getParameter(sku+"_dmg_qty")).intValue();
                    log.debug(rqty+":"+dqty);

                if(rqty<0) throw new Exception("SKU quantities must be whole numbers >= 0");
                if(dqty<0) throw new Exception("SKU quantities must be whole numbers >= 0");
                }catch(NumberFormatException nex)
                {
                    throw new Exception("SKU quantities must be whole numbers >= 0");
                }


                ReceiveItem ritem = new ReceiveItem();

                ritem.setQtyDamage(dqty);
                ritem.setQtyReceive(rqty);
                ritem.setReceive(rcv);
                ritem.setToLocation("");
                ritem.setNotes("");
                ritem.setQtyPackslip(0);

                rcv.getReceiveItems().add(ritem);


                Iterator it = oldAsnItems.iterator();
                boolean notFound=true;
                while(it.hasNext() && notFound)
                {
                    AsnItem item = (AsnItem)it.next();
                    if(item.getInventoryNum().equals(sku))
                    {

                        notFound = false;
                        if(null==item.getReceiveItems())
                        {
                            item.setReceiveItems(new TreeSet());
                        }
                        //item.setReceived(item.getReceived()+rqty);
                        //item.getReceiveItems().add(ritem);
                        ritem.setAsnItem(item);
                        ritem.setInventoryFkey(item.getInventoryFkey());

                    }
                }
            }
        }

    }

    */


    /*

     public static void returnReportDataToBrowser(HttpServletResponse resp, byte[] data,String attName) throws Exception {


        try {

            resp.reset();
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + attName.trim().replace(' ', '_') + "\"");

            log.debug("datalen=" + data.length);
            resp.setContentLength(data.length);
            resp.getOutputStream().write(data);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("There was a problem running your report. Please check your parameters.");
        } finally {


        }

    }*/

}
