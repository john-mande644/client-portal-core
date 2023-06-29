package com.owd.web.warehouse.asn;

import com.owd.LogableException;
import com.owd.core.managers.LotManager;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.TabReader;
import com.owd.core.business.asn.ASNFactory;
import com.owd.core.business.asn.ASNManager;
import com.owd.core.managers.InventoryManager;
import com.owd.core.managers.ScanManager;
import com.owd.hibernate.*;
import com.owd.web.servlet.ExtranetServlet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:56:20 PM
 * 
 */
public class ASNEditServlet extends HttpServlet {
private final static Logger log =  LogManager.getLogger();


    public static void main(String[] args)
    {
         try {
          //  updateASNItemsReceivedCounts((Asn) HibernateSession.currentSession().load(Asn.class, 13719));
            //Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, 81593);
             Asn theasn = (Asn) HibernateSession.currentSession().load(Asn.class,81593);
             Collection<AsnItem> theItems = theasn.getAsnItems();

             Iterator<AsnItem> it = theItems.iterator();
             while(it.hasNext()){
                 AsnItem ai = (AsnItem) it.next();
                 log.debug(ai.getTitle());
                 byte[] b = ai.getTitle().getBytes();
                 log.debug(new String(b));
                 StringBuffer sb = new StringBuffer();
                 sb.append(ai.getTitle());
                 log.debug(sb.toString());

             }
           /*  String sku = "Crystal of My Eye Ring";
                     ReceiveItem ritem = null;*/
            /* Collection oldAsnItems = rcv.getAsn().getAsnItems();

             Iterator itr = rcv.getReceiveItems().iterator();
             while (itr.hasNext()) {
                 log.debug("in iteratorxxxxxxxxxxxxxxxxxxx");
                 ReceiveItem ri = (ReceiveItem) itr.next();
                 log.debug("ri:"+ri);
                 log.debug(ri.getAsnItem().getTitle());

                 *//*if (ri.getAsnItem().getInventoryNum().equals(sku)) {
                     log.debug("setting ritem");
                     ritem = ri;
                 }*//*
             }*/
/*
           *//*  if(ritem==null)
             {
                 log.debug("ritem is null");
                 ritem = new ReceiveItem();
                 rcv.getReceiveItems().add(ritem);
             }*//*


             int rqty=1;

             ritem.setQtyDamage(0);
             ritem.setQtyReceive(1);
             ritem.setReceive(rcv);
             ritem.setToLocation("");
             ritem.setNotes("");
             ritem.setQtyPackslip(0);
             ritem.setCountMethod("Manual");
             ritem.setNotes("Notes");

             //  if (pending.equals("0")) {
//                  /
             //  }   rcv.getReceiveItems().add(ritem);

             Iterator it = oldAsnItems.iterator();
             boolean notFound = true;
             while (it.hasNext() && notFound) {
                 AsnItem item = (AsnItem) it.next();
                 if (item.getInventoryNum().equals(sku)) {
                     Float weight = new Float(0.0);
                     try {
                         weight = new Float("0.1");
                     } catch (NullPointerException npe) {
                     } catch (NumberFormatException nfe) {
                     }
                     log.debug("weight for " + sku + " " + weight);
                     if (weight.floatValue() <= 0.00 && rqty > 0) {

                         throw new Exception("A non-zero weight must be provided for all SKUs - " + weight + " for " + item.getInventoryNum() + " is not valid!");
                     }
                     if (weight.compareTo(new Float(0.0)) > 0) {
                         log.debug("Setting weight "+weight+" for inv ID "+item.getInventoryFkey());
                         OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(item.getInventoryFkey()));

                         inv.setWeightLbs(weight);
                         HibernateSession.currentSession().saveOrUpdate(inv);
                         //  HibUtils.commit(HibernateSession.currentSession());
                     }
                     notFound = false;
                     if (null == item.getReceiveItems()) {
                         item.setReceiveItems(new TreeSet());
                     }
                     //item.setReceived(item.getReceived()+rqty);
                     //item.getReceiveItems().add(ritem);
                     ritem.setAsnItem(item);
                     ritem.setInventoryFkey(item.getInventoryFkey());

                 }
             }*/

        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
      HibernateSession.closeSession();
        }
    }
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

    public static Object ASNMgrLockObject = new Object();

    //all requests should be POST
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException {

        {
        String error = "";

        String action = ((String) req.getParameter(ASNHome.kParamAdminAction));

        if (req.getParameter("subactionSubmit") != null) {
            action = req.getParameter("subaction");
        }
        if (req.getParameter("subaction2Submit") != null) {
            action = req.getParameter("subaction2");
        }

        if (action == null) action = "";

        ASNHome.authenticateUser(req);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        log.debug(action);
        try {
//Start new ASN data entry
            if (action.equals("create")) {

                createASNAction(req, resp);
//Clone existing ASN
            } else if (action.equals("clone-asn")) {
                cloneASNAction(req, resp);
//Create new receive
            } else if (action.equals("create-receive")) {
                createNewReceiveAction(req, resp);
//Display search results for ASNs
            } else if (action.equals("asn-search")) {

                goToSearchASNPageAction(req, resp);

//Display Printable Receive Page
            } else if (action.equals("display-receive-printable")) {
                displayReceivePrintableAction(req, resp);
//Display Printable ASN Page
            } else if (action.equals("display-printable")) {
                displayPrintableAction(req, resp);
//testing getting scan
            } else if (action.equals("get-scan")) {
                getScanAction(req, resp);
//Download CSV of ASN
            }else if (action.equals("download-asn")) {
                downloadASNAsCSVAction(req, resp);

//Edit indicated ASN
            } else if (action.equals("edit-old")) {
                editExistingASNAction(req, resp);
//Save edited received ASN
            } else if (action.equals("rcvasn-save")) {
                UpdateEditedAndReceivedASNAction(req, df, resp);

//Save edited or new ASN
            } else if (action.equals("edit-save") || action.equals("create-save")) {
                saveOrUpdateASNAction(req, df, resp);
            } else if (action.equals("clear-sku")) {
                removeItemFromASNAction(req, resp);
//delete current asn
            } else if (action.equals("delete-asn")) {
                deleteASNAction(req, resp);

//marks asn as complete
            } else if (action.equals("complete-asn")) {
                markASNAsCompleteAction(req, resp);


//save edited receive //mark
            } else if (action.equals("receive-save")) {
                saveOrUpdateReceiveAction(req, resp);

//load pending receive
            } else if (action.equals("receive-edit")) {


                editExistingReceiveAction(req, resp);

//delete receive
            } else if (action.equals("delete-receive")) {

                deleteExistingReceiveAction(req, resp);


            }
//post receive
            else if (action.equals("post-receive")) {

                ReleaseReceiveInventoryToStockAction(req, resp);



//unpost receive
            } else if (action.equals("unpost-receive")) {

                removeReceivedInventoryFromStockAction(req, resp);


//Add item to ASN
            } else if (action.equals("add-item")) {

                addSingleItemToASNAction(req, df, resp);

                //Import items into ASN
            } else if (action.equals("import-items")) {

                importMultipleItemsToASNAction(req, df, resp);
//Change current Client
            } else if (ASNHome.kParamChangeClientAction.equals(action)) {

                setCurrentClientForASNViewAction(req, resp);
//Default servlet response
            } else {
                req.getRequestDispatcher("/warehouse/asn/home.jsp").forward(req, resp);
            }


        } catch (Exception ex) {
            req.setAttribute("errormessage", ex.getMessage());
            ex.printStackTrace();

            try {
                req.getRequestDispatcher("/warehouse/asn/home.jsp").forward(req, resp);
            } catch (Exception exe) {
                exe.printStackTrace();
            }
        } finally {
            HibernateSession.closeSession();
        }

        }
    }

    private void deleteExistingReceiveAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rcvID = (String) req.getParameter("rcvid");


        try {

            if (rcvID == null) throw new Exception("Unexpected error finding indicated receive for deletion.");


            Session sess = HibernateSession.currentSession();
            Receive rcv = (Receive) sess.load(Receive.class, new Integer(rcvID));
            if (rcv == null) throw new Exception("Unexpected error finding indicated receive for deletion.");

            if (rcv.getClientFkey() == new Integer((String) (ASNHome.getSessionString(req, ASNHome.kExtranetClientID))).intValue()) {

            } else {
                if (rcv == null) throw new Exception("Indicated receive record does not belong to current com.owd.api.client.");

            }


            Asn asn = rcv.getAsn();

            req.getSession(true).setAttribute("asn.currentasn", asn);

            ASNManager.deleteReceive(rcv);

            sess.flush();
            HibUtils.commit(sess);


            sess.refresh(asn);


            req.getSession(true).setAttribute("asn.currentasn", asn);

            if (rcv.getAsn().getReceiveCount() < 1) {
                req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);

            }

        } catch (Exception nex) {
            req.setAttribute("errormessage", nex.getMessage());
            nex.printStackTrace();
            req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);

        }
    }

    private void editExistingReceiveAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class, new Integer(req.getParameter("pendingrcvid")));


        try {
            if (rcv == null) throw new Exception("Unexpected error finding current receive for saving.");

            req.getSession(true).setAttribute("asn.currentrcv", rcv);


            Asn asn = (Asn) HibernateSession.currentSession().load(Asn.class, rcv.getAsn().getId());
            req.getSession(true).setAttribute("asn.currentasn", asn);


            req.getRequestDispatcher("/warehouse/asn/receive.jsp").forward(req, resp);
        } catch (Exception nex) {

            req.setAttribute("errormessage", nex.getMessage());
            nex.printStackTrace();

            req.getRequestDispatcher("/warehouse/asn/receive.jsp").forward(req, resp);

        } finally {
            HibernateSession.closeSession();
        }
    }

    private void setCurrentClientForASNViewAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ASNHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag)) {
            req.getSession(true).removeAttribute("asn.currentasn");
            ASNHome.setSessionString(req, ASNHome.kExtranetClientID, req.getParameter(ASNHome.kParamChangeClientTo));
        }
        req.getRequestDispatcher("/warehouse/asn/home.jsp").forward(req, resp);
    }

    private void importMultipleItemsToASNAction(HttpServletRequest req, DateFormat df, HttpServletResponse resp) throws ServletException, IOException {
        Asn asn = (Asn) req.getSession(true).getAttribute("asn.currentasn");

        try {

            if (asn == null) throw new Exception("Unexpected error finding current ASN for editing.");
            updateASNFromEditPage(asn, req, df);


            String importText = req.getParameter("importskus");
            if (importText != null) {
                TabReader myReader = new TabReader(new BufferedReader(new StringReader(importText)), false);
                int rows = myReader.getRowCount();
                for (int i = 0; i < rows; i++) {
                    if (myReader.getRowSize(i) > 1) {
                        String sku = myReader.getStrValue(0, i, "").trim();
                        int qty = myReader.getIntValue(1, i, 0);
                        addItemToASN(qty, sku, asn, req);
                    }
                }

            }
        } catch (Exception nex) {
            req.setAttribute("errormessage", nex.getMessage());
            nex.printStackTrace();
        }
        req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
    }

    private void addSingleItemToASNAction(HttpServletRequest req, DateFormat df, HttpServletResponse resp) throws ServletException, IOException {
        Asn asn = (Asn) req.getSession(true).getAttribute("asn.currentasn");

        try {

            if (asn == null) throw new Exception("Unexpected error finding current ASN for editing.");
            updateASNFromEditPage(asn, req, df);

            int quantity_expected = 0;

            try {
                quantity_expected = Integer.decode((String) req.getParameter("qtysku")).intValue();
            } catch (NumberFormatException nex) {
                throw new Exception("SKU quantity must be a whole number greater than zero");
            }

            String addSKU = req.getParameter("addsku");
            if (addSKU == null) addSKU = "";
            addSKU = addSKU.trim();

            addItemToASN(quantity_expected, addSKU, asn, req);

        } catch (Exception nex) {
            req.setAttribute("errormessage", nex.getMessage());
            nex.printStackTrace();
        }

        req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
    }

    private void removeReceivedInventoryFromStockAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rcvID = (String) req.getParameter("rcvid");


        try {

            if (rcvID == null) throw new Exception("Unexpected error finding indicated receive for posting.");


            Session sess = HibernateSession.currentSession();
            Receive rcv = (Receive) sess.load(Receive.class, new Integer(rcvID));
            if (rcv == null) throw new Exception("Unexpected error finding indicated receive for posting.");

            if (rcv.getClientFkey() == new Integer((String) (ASNHome.getSessionString(req, ASNHome.kExtranetClientID))).intValue()) {

            } else {
                if (rcv == null) throw new Exception("Indicated receive record does not belong to current com.owd.api.client.");

            }

            Asn asn = rcv.getAsn();

            req.getSession(true).setAttribute("asn.currentasn", asn);

            if (rcv.getIsPosted() == 0) {
                throw new Exception("Indicated receive record is not yet posted.");
            }
            ASNManager.cancelPostReceiveToInventory(rcv);

            sess.flush();
            HibUtils.commit(sess);

            if (rcv.getAsn().getReceiveCount() < 1) {
                req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);

            }

        } catch (Exception nex) {
            req.setAttribute("errormessage", nex.getMessage());
            nex.printStackTrace();
            req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);

        }
    }

    private void ReleaseReceiveInventoryToStockAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rcvID = (String) req.getParameter("rcvid");


        try {

            if (rcvID == null) throw new Exception("Unexpected error finding indicated receive for posting.");


            Session sess = HibernateSession.currentSession();
            Receive rcv = (Receive) sess.load(Receive.class, new Integer(rcvID));
            if (rcv == null) throw new Exception("Unexpected error finding indicated receive for posting.");

            if (rcv.getClientFkey() == new Integer((String) (ASNHome.getSessionString(req, ASNHome.kExtranetClientID))).intValue()) {

            } else {
                if (rcv == null) throw new Exception("Indicated receive record does not belong to current com.owd.api.client.");

            }


            Asn asn = rcv.getAsn();

            req.getSession(true).setAttribute("asn.currentasn", asn);


            if (rcv.getIsPosted() == 1) {
                throw new Exception("Indicated receive record is already posted.");
            }
            ASNManager.postReceiveToInventory(rcv);

            sess.flush();
            HibUtils.commit(sess);


            sess.refresh(asn);

            req.getSession(true).setAttribute("asn.currentasn", asn);

            if (asn.getReceiveCount() < 1) {
                req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);

            }

        } catch (Exception nex) {
            req.setAttribute("errormessage", nex.getMessage());
            nex.printStackTrace();
            req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);

        }
    }

    private void saveOrUpdateReceiveAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Receive rcv = (Receive) req.getSession(true).getAttribute("asn.currentrcv");
        try {
            if (rcv == null) throw new Exception("Unexpected error finding current receive for saving.");
            String pendingid = "0";
            if (!req.getParameter("pendingid").equals("null")) {
                log.debug("setting pendingid");
                pendingid = req.getParameter("pendingid");
                if (!pendingid.equals("0")) {
                    rcv = (Receive) HibernateSession.currentSession().load(Receive.class, Integer.valueOf(pendingid));
                }
            }

             if( InventoryManager.countInProgress(HibernateSession.currentSession(), rcv.getClientFkey())){

                      throw new Exception("Inventory Count in progress for Client, You cannot save a receive at this time");
                  }
            updateReceiveFromEditPage(rcv, req, pendingid);

            if (rcv.getCreatedBy().equals("PENDING")) {
                ASNManager.releasePendingReceive(rcv, req.getUserPrincipal().getName(), false);
            } else {
                ASNManager.saveOrUpdateReceive(rcv, false);
            }
            ASNManager.updateASNItemsReceivedCounts(rcv.getAsn(), rcv, true);

            HibernateSession.currentSession().flush();

            HibUtils.commit(HibernateSession.currentSession());
            rcv.setCreatedBy(req.getUserPrincipal().getName());

            HibernateSession.currentSession().flush();

            HibUtils.commit(HibernateSession.currentSession());


            ASNManager.sendAMNotificationOfReceipt(rcv);

            HibernateSession.closeSession();

                               Asn asn = (Asn) HibernateSession.currentSession().load(Asn.class, rcv.getAsn().getId());
                               req.getSession(true).setAttribute("asn.currentasn", asn);

          log.debug("Note Size" + rcv.getNotes());
            if ("1".equals(req.getParameter("notifyAM"))&& rcv.getNotes().length()>0) {
              log.debug("Sending Notes");
                OwdClient client = ((OwdClient) HibernateSession.currentSession().load(OwdClient.class, new Integer(rcv.getAsn().getClientFkey())));

                String amEmail = "owditadmin@owd.com";
                if (client != null) {
                    if (client.getAmEmail().length() > 5) {
                        amEmail = client.getAmEmail();

                    }
                }

                Mailer.sendMail("AM Alert from Receiving!", "Alert from " + rcv.getReceiveBy() + " for " + client.getCompanyName() + ", OWD ASN ID:" + rcv.getAsn().getId() + "\r\nLink to ASN Detail: http://service.owd.com/webapps/warehouse/asn/edit?act=edit-old&asn_id=" + asn.getId() + "\r\nNotes:\r\n" + rcv.getNotes(), amEmail, "do-not-reply@owd.com");
            }


            req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);
        } catch (Exception nex) {

            Enumeration enumm = req.getParameterNames();
            Map receivedqty = new HashMap();
            while (enumm.hasMoreElements()) {
                String name = (String) enumm.nextElement();
                if (name.endsWith("_rcv_qty")) {
                    Map qty = new HashMap();
                    String sku = name.substring(0, name.indexOf("_rcv_qty"));
                    qty.put("rqty", req.getParameter(name));
                    qty.put("dqty", req.getParameter(sku + "_dmg_qty"));
                    qty.put("weight", req.getParameter(sku + "_weight"));
                    receivedqty.put(sku, qty);
                }
            }
            receivedqty.put("cartonCount", req.getParameter("cartonCount"));
            receivedqty.put("palletCount", req.getParameter("palletCount"));
            req.setAttribute("receivedqty", receivedqty);
            req.setAttribute("errormessage", nex.getMessage());
            nex.printStackTrace();

            req.getRequestDispatcher("/warehouse/asn/receive.jsp").forward(req, resp);

        } finally {
            log.debug("final close 000000000000000000000000000xxxxxxxxx");
            HibernateSession.closeSession();
        }
    }

    private void markASNAsCompleteAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("in compelte asn");
        try {
            Session sess = HibernateSession.currentSession();
            Asn asn = (Asn) req.getSession(true).getAttribute("asn.currentasn");
            if(asn.getId()!=Integer.parseInt(req.getParameter("asn_id"))){
                try {
                    throw new LogableException("Attempted ASN cross save. Please close all open ASN pages and reload", "Tried: " + req.getParameter("loadedAsnId") + ", Loaded: " + asn.getId() + ", BY: " + req.getUserPrincipal().getName(), asn.getClientFkey() + "", this.getClass().getName(), LogableException.errorTypes.ASN_IMPORT);
                }catch (Exception e){
                    throw new Exception("Attempted ASN cross save. Please close all open ASN pages and reload");
                }
            }
            if (asn == null) throw new Exception("Unexpected error finding current ASN for updating.");
            log.debug("setting asn complete, old status=" + asn.getStatus());
            ASNManager.setASNComplete(asn);
            log.debug("set asn complete, new status=" + asn.getStatus());
            sess.saveOrUpdate(asn);
            sess.flush();
            HibUtils.commit(sess);

            asn = (Asn) HibernateSession.currentSession().load(Asn.class, asn.getId());
            req.getSession(true).setAttribute("asn.currentasn", asn);
            log.debug("updated asn complete, current status=" + asn.getStatus());
            req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);

        } catch (Exception bex) {
            req.setAttribute("errormessage", bex.getMessage());
            bex.printStackTrace();
            req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);
        }
    }

    private void deleteASNAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Session sess = HibernateSession.currentSession();
            Asn asn = (Asn) req.getSession(true).getAttribute("asn.currentasn");
            if (asn == null) throw new Exception("Unexpected error finding current ASN for deleting.");
           Integer id = Integer.valueOf(req.getParameter("page-asn"));

            sess.refresh(asn);

            if (id.intValue()==asn.getId().intValue()){

            if(asn.getReceiveCount()==0&&asn.getReceives().size()==0){
            ASNManager.deleteASN(asn);
            sess.flush();
            HibUtils.commit(sess);
           }  else{
                throw new Exception("Receive's Created!!!! You are not allowed to delete.");
            }
            }else{
                throw new Exception("Session is bad, please start over.");
            }
            req.getRequestDispatcher("/warehouse/asn/searchasn.jsp").forward(req, resp);
        } catch (Exception bex) {
            req.setAttribute("errormessage", bex.getMessage());
            bex.printStackTrace();
            req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
        }
    }

    private void removeItemFromASNAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Asn asn = (Asn) req.getSession(true).getAttribute("asn.currentasn");
            if (asn == null) throw new Exception("Unexpected error finding current ASN for editing.");
            Iterator it = asn.getAsnItems().iterator();
            log.debug("found" + asn.getAsnItems().size() + "items zzzzzzzzzzzzzzzz");
            while (it.hasNext()) {
                AsnItem item = (AsnItem) it.next();
                log.debug("Removoing" + item.getInventoryFkey());
                //todo
                it.remove();
                asn.getAsnItems().remove(item);
            }

            //  asn = (Asn) HibernateSession.currentSession().load(Asn.class, asn.getId());
            req.getSession(true).setAttribute("asn.currentasn", asn);
            req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);

        } catch (Exception bex) {
            req.setAttribute("errormessage", bex.getMessage());
            bex.printStackTrace();
            req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
        }
    }

    private void saveOrUpdateASNAction(HttpServletRequest req, DateFormat df, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Asn asn = (Asn) req.getSession(true).getAttribute("asn.currentasn");

            if(req.getParameterMap().containsKey("loadedAsnId")){
                if(asn.getId()!= Integer.parseInt(req.getParameter("loadedAsnId"))){

                    try {
                        throw new LogableException("Attempted ASN cross save. Please close all open ASN pages and reload", "Tried: " + req.getParameter("loadedAsnId") + ", Loaded: " + asn.getId() + ", BY: " + req.getUserPrincipal().getName(), asn.getClientFkey() + "", this.getClass().getName(), LogableException.errorTypes.ASN_IMPORT);
                    }catch (Exception e){
                        throw new Exception("Attempted ASN cross save. Please close all open ASN pages and reload");
                    }
                }
            }
            if (asn == null) throw new Exception("Unexpected error finding current ASN for editing.");

            updateASNFromEditPage(asn, req, df);
            saveOrUpdateASN(asn, req, df);

            asn = (Asn) HibernateSession.currentSession().load(Asn.class, asn.getId());
            req.getSession(true).setAttribute("asn.currentasn", asn);
            req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);

        } catch (Exception bex) {
            req.setAttribute("errormessage", bex.getMessage());
            bex.printStackTrace();
            req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
        }
    }

    private void UpdateEditedAndReceivedASNAction(HttpServletRequest req, DateFormat df, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Asn asn = (Asn) req.getSession(true).getAttribute("asn.currentasn");
            if (asn == null) throw new Exception("Unexpected error finding current ASN for editing.");
            if(req.getParameterMap().containsKey("loadedAsnId")){
                if(asn.getId()!= Integer.parseInt(req.getParameter("loadedAsnId"))){

                    try {
                        throw new LogableException("Attempted ASN cross save. Please close all open ASN pages and reload", "Tried: " + req.getParameter("loadedAsnId") + ", Loaded: " + asn.getId() + ", BY: " + req.getUserPrincipal().getName(), asn.getClientFkey() + "", this.getClass().getName(), LogableException.errorTypes.ASN_IMPORT);
                    }catch (Exception e){
                        throw new Exception("Attempted ASN cross save. Please close all open ASN pages and reload");
                    }
                }
            }


            log.debug("ASNLOC1:"+asn.getFacilityCode());
            updateRcvedASNFromEditPage(asn, req, df);
            log.debug("ASNLOC2:"+asn.getFacilityCode());

            saveOrUpdateASN(asn, req, df);

            asn = (Asn) HibernateSession.currentSession().load(Asn.class, asn.getId());
            req.getSession(true).setAttribute("asn.currentasn", asn);
            req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);

        } catch (Exception bex) {
            req.setAttribute("errormessage", bex.getMessage());
            bex.printStackTrace();
            req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);
        }
    }

    private void downloadASNAsCSVAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Asn asn = (Asn) req.getSession(true).getAttribute("asn.currentasn");
        if (asn == null) throw new Exception("Unexpected error finding current ASN for download.");

        returnReportDataToBrowser(resp, ASNManager.getASNAsExcelSheet(asn), (asn.getClientRef().length() > 0 ? asn.getClientRef() : asn.getId() + "") + ".csv");
    }

    private void goToSearchASNPageAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            req.getRequestDispatcher("/warehouse/asn/searchasn.jsp").forward(req, resp);

        } catch (Exception bex) {
            req.setAttribute("errormessage", bex.getMessage());
            bex.printStackTrace();
            req.getRequestDispatcher("/warehouse/asn/home.jsp").forward(req, resp);
        }
    }

    private void createNewReceiveAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Asn asn = ASNFactory.getAsnFromAsnID(new Integer((String) req.getParameter("asn_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), ASNHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));
        if (asn == null) throw new Exception("Unexpected error loading ASN.");

        Receive rcv = ASNFactory.getNewReceive(asn);
        req.getSession(true).setAttribute("asn.currentrcv", rcv);

        req.getRequestDispatcher("/warehouse/asn/receive.jsp").forward(req, resp);
    }

    private void cloneASNAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String cid = ASNHome.getSessionString(req, ASNHome.kExtranetClientID);
        if ("0".equals(cid)) cid = "55";


        Asn oldasn = ASNFactory.getAsnFromAsnID(new Integer((String) req.getParameter("asn_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), ASNHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));
        if (oldasn == null) throw new Exception("Unexpected error loading ASN.");

        if (oldasn.getClientFkey() != Integer.decode(cid).intValue()) {
            throw new Exception("Current com.owd.api.client context does not match source ASN com.owd.api.client - please choose the correct com.owd.api.client context before cloning the ASN");
        }

        Asn asn = ASNFactory.getNewAsn(Integer.decode(cid).intValue());
        asn.setIsAutorelease(oldasn.getIsAutorelease());
        asn.setCarrier(oldasn.getCarrier());
        asn.setCartonCount(oldasn.getCartonCount());
        asn.setPalletCount(oldasn.getPalletCount());
        asn.setClientPo(oldasn.getClientPo());
        asn.setClientRef(oldasn.getClientRef());
        asn.setNotes(oldasn.getNotes());
        asn.setShipperName(oldasn.getShipperName());
        asn.setOwdLabels(oldasn.getOwdLabels());
        //   asn.setStatus(0);

        Iterator it = oldasn.getAsnItems().iterator();
        while (it.hasNext()) {
            //todo
            AsnItem item = (AsnItem) it.next();
            addItemToASN(item.getExpected(), item.getInventoryNum(), asn, req);
        }
        if (1 == ASNHome.getIntegerParam(req, "blind", 0)) {
            asn.setHasBlind(1);
        }

        req.getSession(true).setAttribute("asn.currentasn", asn);

        req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
    }

    protected void createASNAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, Exception {
        String cid = ASNHome.getSessionString(req, ASNHome.kExtranetClientID);
        if ("0".equals(cid)) cid = "55";

        Asn asn = ASNFactory.getNewAsn(Integer.decode(cid).intValue());
        if (1 == ASNHome.getIntegerParam(req, "blind", 0)) {
            asn.setHasBlind(1);
        }

        req.getSession(true).setAttribute("asn.currentasn", asn);

        req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
    }

    public static void addItemToASN(int quantity_expected, String addSKU, Asn asn, HttpServletRequest req) throws Exception {
        try {


            Session sess = HibernateSession.currentSession();

            Criteria crit = sess.createCriteria(OwdInventory.class);

            crit.setMaxResults(1000);
            crit.add(Restrictions.eq("owdClient.clientId", new Integer(asn.getClientFkey())))
                    .add(Restrictions.eq("inventoryNum", addSKU));

            List list = crit.list();
            if (list.size() == 0) throw new Exception("SKU " + addSKU + " not found. SKU values are case-sensitive. Please check the SKU and try again.");

            OwdInventory inventoryItem = ((OwdInventory) list.get(0));

            if (quantity_expected < 1) throw new Exception("SKU quantity must be a whole number greater than zero");

            boolean foundItem = false;
            if (addSKU.length() < 1) throw new Exception("You must provide a valid SKU to add an item.");


            AsnItem item = new AsnItem();
                  item.setReceived(0);
                item.setIsBlind(0);
                item.setExpected(0);
            Iterator it = asn.getAsnItems().iterator();
            while (it.hasNext()) {
                AsnItem itx = (AsnItem) it.next();

                if ((itx).getInventoryNum().equals(addSKU)) {
                    foundItem = true;
                    item = itx;
                    item.setExpected(quantity_expected + item.getExpected());
                }

            }

            if (!foundItem) {
                item.setAsn(asn);
                item.setInventoryNum(addSKU);
                item.setExpected(quantity_expected);

                item.setDescription("");
                item.setNote("");
                if (asn.getHasBlind() == 1) {
                    item.setIsBlind(1);
                }


                item.setTitle(inventoryItem.getDescription() == null ? "" : inventoryItem.getDescription());
                item.setInventoryFkey(inventoryItem.getInventoryId().intValue());
                asn.getAsnItems().add(item);
            }
        } catch (Exception ex) {

            throw ex;
        } finally {
            HibernateSession.closeSession();
        }
    }


    private void saveOrUpdateASN(Asn asn, HttpServletRequest req, DateFormat df) throws Exception {
        try {
            String action = ((String) req.getParameter(ASNHome.kParamAdminAction));
            ASNManager.saveOrUpdateASN(asn);


        } catch (Exception ex) {
            req.setAttribute("errormessage", ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
            HibernateSession.closeSession();
        }
    }


    private void updateRcvedASNFromEditPage(Asn asn, HttpServletRequest req, DateFormat df) throws Exception {
        asn.setShipperName(req.getParameter("shipperName"));
        asn.setCarrier(req.getParameter("carrier"));
        asn.setClientPo(req.getParameter("clientPo"));
        asn.setClientRef(req.getParameter("clientRef"));
        asn.setIsAutorelease(("1".equals(req.getParameter("isAutoRelease")) ? 1 : 0));
        asn.setNotes(req.getParameter("notes"));
        asn.setHasBlind(("1".equals(req.getParameter("hasBlind")) ? 1 : 0));
        asn.setOwdLabels(("1".equals(req.getParameter("owdLabels")) ? 1 : 0));


        //asn.setCreatedDate(Calendar.getInstance().getTime());
        //asn.set(req.getUserPrincipal().getName());


    }


    private void updateASNFromEditPage(Asn asn, HttpServletRequest req, DateFormat df) throws Exception {
        asn.setShipperName(req.getParameter("shipperName"));
        asn.setCarrier(req.getParameter("carrier"));
        asn.setClientPo(req.getParameter("clientPo"));
        asn.setFacilityCode(req.getParameter("facilityCode"));
        asn.setClientRef(req.getParameter("clientRef"));
        asn.setExpectDate(df.parse(req.getParameter("expect_date")));
        asn.setIsAutorelease(("1".equals(req.getParameter("isAutoRelease")) ? 1 : 0));
        asn.setNotes(req.getParameter("notes"));
        asn.setOwdLabels(("1".equals(req.getParameter("owdLabels")) ? 1 : 0));
        log.debug(asn.getOwdLabels());
        try {
            asn.setCartonCount(Integer.decode(req.getParameter("cartonCount")).intValue());
        } catch (NumberFormatException nex) {
            throw new Exception("Carton count must be a whole number greater or equal to zero");
        }
        try {
            asn.setPalletCount(Integer.decode(req.getParameter("palletCount")).intValue());

        } catch (NumberFormatException nex) {
            throw new Exception("Pallet count must be a whole number greater or equal to zero");
        }

        if (asn.getId() == null) {
            asn.setCreatedDate(Calendar.getInstance().getTime());
            asn.setCreatedBy(req.getUserPrincipal().getName());
        }

        Collection oldAsnItems = asn.getAsnItems();

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

                Iterator it = oldAsnItems.iterator();
                boolean notFound = true;
                while (it.hasNext() && notFound) {
                    AsnItem item = (AsnItem) it.next();
                    if (item.getInventoryNum().equals(sku)) {
                        notFound = false;

                        item.setExpected(qty);

                    }
                }
            }
        }

    }

    private void updateReceiveFromEditPage(Receive rcv, HttpServletRequest req, String pending) throws Exception {
        log.debug("in update pending id passed " + pending);

        Session sess = HibernateSession.currentSession();
        sess.saveOrUpdate(rcv);
        sess.saveOrUpdate(rcv.getAsn());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mmaa");


        //rcv.setCreatedOn(Calendar.getInstance().getTime());
        rcv.setEndTimestamp(df.parse(req.getParameter("endTimestamp")));
        rcv.setStartTimestamp(df.parse(req.getParameter("startTimestamp")));
        rcv.setIsAsnFound(("1".equals(req.getParameter("asnFound")) ? 1 : 0));
        rcv.setIsPackSlipFound(("1".equals(req.getParameter("packSlipFound")) ? 1 : 0));
        if ("1".equals(req.getParameter("packSlipFound"))) {
            rcv.setIsPackSlipMatch(("1".equals(req.getParameter("packSlipMatch")) ? 1 : 0));
        } else {
            rcv.setIsPackSlipMatch(0);
        }
        rcv.setNotes(req.getParameter("notes"));
        rcv.setCountMethod(req.getParameter("count_method"));
        if(rcv.getCountMethod()==null)
        {
            throw new Exception("Count Method must be filled in");
        }
         if(rcv.getCountMethod().equals(""))
        {
            throw new Exception("Count Method must be filled in");
        }
        String receiveBy = req.getParameter("receiveBy");
        if (receiveBy.length() > 30) receiveBy = receiveBy.substring(0, 30);
        log.debug("Received By: " + receiveBy);
        rcv.setReceiveBy(receiveBy);

        long minutes = (rcv.getEndTimestamp().getTime() - rcv.getStartTimestamp().getTime()) / (60 * 1000);


        if (minutes < 1) {
            throw new Exception("You must set start and end times to reflect a duration of at least 1 minute");
        }
        rcv.setBilledMinutes((int) (rcv.getEndTimestamp().getTime() - rcv.getStartTimestamp().getTime()) / (60 * 1000));
        Calendar end = Calendar.getInstance();
        rcv.setEndTimestamp(end.getTime());
        Calendar start = Calendar.getInstance();
        start.setTimeInMillis(end.getTimeInMillis());
        start.add(Calendar.MINUTE,(int)(-1*minutes));


        rcv.setStartTimestamp(start.getTime());

        sess.saveOrUpdate(rcv);
        HibUtils.commit(sess);
        sess.flush();

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

        if (rcv.getPalletCount() + rcv.getCartonCount() < 1) {
            throw new Exception("Pallet or carton count must be a whole number greater or equal to 1");
        }

        Collection oldAsnItems = rcv.getAsn().getAsnItems();
       // if (pending.equals("0")) {
         //   rcv.setReceiveItems(new ArrayList());
      //  }
        log.debug("checking receive items");
         if (null == rcv.getReceiveItems()) {
                           rcv.setReceiveItems(new ArrayList());
                        }
        Enumeration enumm = req.getParameterNames();
        log.debug(req.getParameterMap());
        while (enumm.hasMoreElements()) {
            String name = (String) enumm.nextElement();
            if (name.endsWith("_rcv_qty")) {
                String sku = name.substring(0, name.indexOf("_rcv_qty"));
                log.debug("found sku " + sku);
                int rqty = 0;
                int dqty = 0;
                String countMethod = "";
                String itemNotes = "";
                try {
                    log.debug("decoding rqty");
                    rqty = Integer.decode((String) req.getParameter(name)).intValue();
                    log.debug("decoding dqty");
                    dqty = Integer.decode((String) req.getParameter(sku + "_dmg_qty")).intValue();
                    log.debug(rqty + ":" + dqty);
                    countMethod = req.getParameter(sku + "_count_method");
                    itemNotes = req.getParameter(sku + "_notes");


                    if (rqty < 0) throw new Exception("SKU quantities must be whole numbers >= 0");
                    if (dqty < 0) throw new Exception("SKU quantities must be whole numbers >= 0");
                } catch (NumberFormatException nex) {
                    throw new Exception("SKU quantities must be whole numbers >= 0");
                }

                ReceiveItem ritem = null;

                //  if (pending.equals("0")) {
                // ritem = new ReceiveItem();
                //   } else {
                Iterator itr = rcv.getReceiveItems().iterator();
                while (itr.hasNext()) {
                    log.debug("in iteratorxxxxxxxxxxxxxxxxxxx");
                    ReceiveItem ri = (ReceiveItem) itr.next();
                    if (ri.getAsnItem().getInventoryNum().equals(sku)) {
                        log.debug("setting ritem");
                        ritem = ri;
                    }
                }

                if (ritem == null) {
                    ritem = new ReceiveItem();
                    rcv.getReceiveItems().add(ritem);
                }
                /// }


                ritem.setQtyDamage(dqty);
                ritem.setQtyReceive(rqty);
                ritem.setReceive(rcv);
                ritem.setToLocation("");
                ritem.setNotes("");
                ritem.setQtyPackslip(0);
                ritem.setCountMethod(countMethod);
                ritem.setNotes(itemNotes);

              //  if (pending.equals("0")) {
//                  /
                //  }   rcv.getReceiveItems().add(ritem);
                //check for lots and update
                if (LotManager.isInventoryIdLotControlled(ritem.getInventoryFkey())) {
                    //do lots already recorded for pending receives
                    int lotTotalqty = 0;
                    int lotTodaldmq = 0;
                    if (ritem.getLots().size() > 0) {

                        for (OwdLotReceiveItem lotItem : ritem.getLots()) {
                            log.debug("this is the lot receive item we are working with");
                            log.debug(lotItem.getId());
                            String lotValue = req.getParameter("lots_" + lotItem.getId() + "_lotvalue");
                            int lotQty = Integer.parseInt(req.getParameter("lots_" + lotItem.getId() + "_qty"));
                            int dmgQty = Integer.parseInt(req.getParameter("lots_" + lotItem.getId() + "_dmg"));
                            if (!LotManager.isLotValueValidForInventoryId(lotValue, ritem.getAsnItem().getInventoryFkey())) {
                                throw new Exception(lotValue + " is not valid for " + ritem.getAsnItem().getInventoryNum());
                            }
                            lotItem.setLotValue(lotValue);
                            lotItem.setQuantityChange(lotQty);
                            lotItem.setQuantityDamage(dmgQty);
                            lotTodaldmq += dmgQty;
                            lotTotalqty += lotQty;
                            HibernateSession.currentSession().saveOrUpdate(lotItem);
                        }

                    }

                    //do new lots not already recorded
                    log.debug(req.getParameter("newLot_"+ritem.getInventoryFkey()+"_total"));

                    if(null!=req.getParameter("newLot_"+ritem.getInventoryFkey()+"_total")){
                        log.debug("We have new lots");
                        int newLots = Integer.parseInt(req.getParameter("newLot_"+ritem.getInventoryFkey()+"_total"));

                        int counter=1;
                        while (counter<= newLots){
                            log.debug("in the new one");
                            OwdLotReceiveItem lotItem = new OwdLotReceiveItem();
                            String lotValue= req.getParameter("new_lot_"+counter+"_"+ritem.getInventoryFkey()+"_lotValue");
                            int lotQty = Integer.parseInt(req.getParameter("new_lot_"+counter+"_"+ritem.getInventoryFkey()+"_qty"));
                            int dmgQty = Integer.parseInt(req.getParameter("new_lot_"+counter+"_"+ritem.getInventoryFkey()+"_dmg"));
                            if (!LotManager.isLotValueValidForInventoryId(lotValue, ritem.getInventoryFkey())) {
                                throw new Exception(lotValue + " is not valid for " + ritem.getAsnItem().getInventoryNum());
                            }
                            lotItem.setLotValue(lotValue);
                            lotItem.setQuantityChange(lotQty);
                            lotItem.setQuantityDamage(dmgQty);
                            lotTodaldmq += dmgQty;
                            lotTotalqty += lotQty;
                            HibernateSession.currentSession().save(ritem);
                            lotItem.setReceiveItem(ritem);
                            ritem.getLots().add(lotItem);

                            HibernateSession.currentSession().save(lotItem);
                            counter++;
                        }




                    }

                    if (lotTotalqty != ritem.getQtyReceive() || lotTodaldmq != ritem.getQtyDamage()) {
                        throw new Exception("Lot quantities are off for : " + ritem.getAsnItem().getInventoryNum() + " Please fix");
                    }
                }
                    Iterator it = oldAsnItems.iterator();
                    boolean notFound = true;
                    while (it.hasNext() && notFound) {
                        AsnItem item = (AsnItem) it.next();
                        if (item.getInventoryNum().equals(sku)) {
                            Float weight = new Float(0.0);
                            try {
                                weight = new Float(req.getParameter(sku + "_weight"));
                            } catch (NullPointerException npe) {
                            } catch (NumberFormatException nfe) {
                            }
                            log.debug("weight for " + sku + " " + weight);
                            if (weight.floatValue() <= 0.00 && rqty > 0) {

                                throw new Exception("A non-zero weight must be provided for all SKUs - " + weight + " for " + item.getInventoryNum() + " is not valid!");
                            }
                            if (weight.compareTo(new Float(0.0)) > 0) {
                                OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(item.getInventoryFkey()));
                                inv.setWeightLbs(weight);
                                HibernateSession.currentSession().saveOrUpdate(inv);
                                //  HibUtils.commit(HibernateSession.currentSession());
                            }
                            notFound = false;
                            if (null == item.getReceiveItems()) {
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

    private void editExistingASNAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Asn asn = ASNFactory.getAsnFromAsnID(new Integer((String) req.getParameter("asn_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), ASNHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));
        if (asn == null) throw new Exception("Unexpected error creating new ASN template.");
        req.getSession(true).setAttribute("asn.currentasn", asn);

        if (asn.getReceiveCount() > 0) {
            req.getRequestDispatcher("/warehouse/asn/receivedasn.jsp").forward(req, resp);

        } else {
            req.getRequestDispatcher("/warehouse/asn/editasn.jsp").forward(req, resp);
        }
    }

    private void displayPrintableAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Asn asn = ASNFactory.getAsnFromAsnID(new Integer((String) req.getParameter("asn_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), ASNHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));
        if (asn == null) throw new Exception("Unexpected error creating new ASN template.");
        req.getSession(true).setAttribute("asn.currentasn", asn);

        req.getRequestDispatcher("/warehouse/asn/printasn.jsp").forward(req, resp);
    }

    private void displayReceivePrintableAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Asn asn = ASNFactory.getAsnFromAsnID(new Integer((String) req.getParameter("asn_id")), Integer.decode(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)).intValue(), ASNHome.getSessionFlag(req, ASNHome.kExtranetInternalFlag));
        if (asn == null) throw new Exception("Unexpected error creating new ASN template.");
        req.getSession(true).setAttribute("asn.currentasn", asn);

        req.getRequestDispatcher("/warehouse/asn/printrcv.jsp").forward(req, resp);
    }

    public static void returnReportDataToBrowser(HttpServletResponse resp, byte[] data, String attName) throws Exception {


        try {

            resp.reset();
            resp.setContentType("application/comma-separated-values");
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + attName.trim().replace(' ', '_') + "\"");

            log.debug("datalen=" + data.length);
            PrintWriter out = resp.getWriter();
            //resp.setContentLength(data.length);
            //resp.getOutputStream().write(data);

                out.print(new String(data));

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("There was a problem running your report. Please check your parameters.");
        } finally {


        }

    }

    public static String returnNeedToWeigh(int id) {
        String weigh = "";
        try {
            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(id));

            weigh = (inv.getWeightLbs() == null ? "0.00" : "" + inv.getWeightLbs());


        } catch (Exception ex) {
            return weigh;
        }

        return weigh;
    }
    public static void getScanAction(HttpServletRequest req, HttpServletResponse resp)

            throws ServletException {

     try
     {
         String rcvid = req.getParameter("rcvid");

    String filename = req.getParameter("filename");
    String cid = req.getParameter("cid");

      if(req.getParameter("auth") != null)
      {

          String[] result = OWDUtilities.decryptData(req.getParameter("auth")).split("/");
     if(result.length == 3)
     {

        rcvid=result[0];
         filename=result[1];
         cid=result[2];
     }
      }
        Receive rcv = (Receive) HibernateSession.currentSession().load(Receive.class,new Integer(rcvid));

        returnScanDataToBrowser(resp, ScanManager.getScanFromReceive(rcv,filename), filename);
     }catch(Exception ex)
     {
        ex.printStackTrace();
         //todo redirect

     }   finally
     {
         HibernateSession.closeSession();
     }
    }




     public static void returnScanDataToBrowser(HttpServletResponse resp, byte[] data, String attName) throws Exception {


        try {

            resp.reset();
            if(attName.toUpperCase().endsWith(".PDF"))
            {
                resp.setContentType("application/pdf");
            }   else
            {
            resp.setContentType("application/octet-stream");
            }
            resp.setHeader("Content-Disposition", "attachment; filename=\"" + attName.trim().replace(' ', '_').replace(':','-') + "\"");

            log.debug("datalen=" + data.length);
            resp.setContentLength(data.length);
            resp.getOutputStream().write(data);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("There was a problem running your report. Please check your parameters.");
        } finally {


        }

    }
}
