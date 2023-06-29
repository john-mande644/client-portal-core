package com.owd.web.internal.client;


import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.generated.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.client.ClientFactory;
import com.owd.core.business.client.ClientManager;
import com.owd.hibernate.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

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
public class ClientEditServlet extends HttpServlet {
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

        String action = ((String) req.getParameter(ClientHome.kParamAdminAction));

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
            ClientHome.authenticateUser(req);
            if (!(ClientHome.getSessionFlag(req, ClientHome.kExtranetAdminFlag) && ClientHome.getSessionFlag(req, ClientHome.kExtranetAuthenticatedFlag))) {
                // req.getRequestDispatcher("/client/editclient.jsp").forward(req, resp);

            } else {

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                //log.debug(action);

//Start new client data entry
                if (action.equals("create")) {

                    String cid = ClientHome.getSessionString(req, ClientHome.kExtranetClientID);
                    if ("0".equals(cid)) cid = "55";

                    OwdClient client = ClientFactory.getNewClient();

                    //log.debug("new client id = "+client.getClientId());

                    req.getSession(true).setAttribute("client.currentclient.0", client);
                    req.getSession(true).setAttribute("client.currentclient", client);
                    req.getRequestDispatcher("/client/client_editor.jsp").forward(req, resp);

                } else if (action.equals("client-edit")) {
//edit existing
                    try {
                        OwdClient client = ClientFactory.getClientFromClientID(new Integer((String) req.getParameter("client_id")), Integer.decode(ClientHome.getSessionString(req, ClientHome.kExtranetClientID)).intValue(), ClientHome.getSessionFlag(req, ClientHome.kExtranetInternalFlag));
                        if (client == null) throw new Exception("Unexpected error finding indicated client for editing.");


                        req.getSession(true).setAttribute("client.currentclient."+client.getClientId(), client);
                        req.getSession(true).setAttribute("client.currentclient", client);

                        req.getRequestDispatcher("/client/client_editor.jsp").forward(req, resp);

                    } catch (Exception bex) {
                        req.setAttribute("errormessage", bex.getMessage());
                        bex.printStackTrace();
                        req.getRequestDispatcher("/client/home.jsp").forward(req, resp);
                    }
                    //search
                } else if (action.equals("client-sla")) {
//edit existing
                    try {
                        OwdClient client = ClientFactory.getClientFromClientID(new Integer((String) req.getParameter("client_id")), Integer.decode(ClientHome.getSessionString(req, ClientHome.kExtranetClientID)).intValue(), ClientHome.getSessionFlag(req, ClientHome.kExtranetInternalFlag));
                        if (client == null) throw new Exception("Unexpected error finding indicated client for SLA retrieval.");


                        req.getSession(true).setAttribute("client.currentclient", client);

                        req.getRequestDispatcher("/client/clientsla.jsp").forward(req, resp);

                    } catch (Exception bex) {
                        req.setAttribute("errormessage", bex.getMessage());
                        bex.printStackTrace();
                        req.getRequestDispatcher("/client/home.jsp").forward(req, resp);
                    }
                    //search
                } else if (action.equals("client-acct")) {
//edit existing
                    try {
                        OwdClient client = ClientFactory.getClientFromClientID(new Integer((String) req.getParameter("client_id")), Integer.decode(ClientHome.getSessionString(req, ClientHome.kExtranetClientID)).intValue(), ClientHome.getSessionFlag(req, ClientHome.kExtranetInternalFlag));
                        if (client == null) throw new Exception("Unexpected error finding indicated client for SLA retrieval.");


                        req.getSession(true).setAttribute("client.currentclient", client);

                        req.getRequestDispatcher("/client/clientaccounthistory.jsp").forward(req, resp);

                    } catch (Exception bex) {
                        req.setAttribute("errormessage", bex.getMessage());
                        bex.printStackTrace();
                        req.getRequestDispatcher("/client/home.jsp").forward(req, resp);
                    }
                    //search
                } else if (action.equals("client-search")) {

                    try {

                        req.getRequestDispatcher("/client/searchclient.jsp").forward(req, resp);

                    } catch (Exception bex) {
                        req.setAttribute("errormessage", bex.getMessage());
                        bex.printStackTrace();
                        req.getRequestDispatcher("/client/home.jsp").forward(req, resp);
                    }

//save client
                } else if (action.equals("edit-save") || action.equals("create-save")) {
                    try {

                        //log.debug("in clientmgr edit/create save...");
                        OwdClient client=null;
                        if("0".equals((String) req.getParameter("client_id")))
                      {
                           client = ClientFactory.getNewClient();
                      }   else
                      {    client = ClientFactory.getClientFromClientID(new Integer((String) req.getParameter("client_id")), Integer.decode(ClientHome.getSessionString(req, ClientHome.kExtranetClientID)).intValue(), ClientHome.getSessionFlag(req, ClientHome.kExtranetInternalFlag));


                      }

                        if (client == null)  throw new Exception("Unexpected error finding current client for editing.");
                        req.getSession(true).setAttribute("client.currentclient."+client.getClientId(), client);
                                               req.getSession(true).setAttribute("client.currentclient", client);
                                               req.getSession(true).setAttribute("client.currentclient.0", null);


                        //log.debug("updating...");
                        updateClientFromEditPage(client, req);
                        //log.debug("updating2...");
                        saveOrUpdateClient(client, req);


                        HibernateSession.currentSession().flush();
                        HibUtils.commit(HibernateSession.currentSession());

                        //client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, client.getClientId());

                        req.getSession(true).setAttribute("client.currentclient."+client.getClientId(), client);
                        req.getSession(true).setAttribute("client.currentclient", client);
                        req.getSession(true).setAttribute("client.currentclient.0", null);
                        req.getRequestDispatcher("/client/client_editor.jsp").forward(req, resp);

                    } catch (Exception bex) {
                        //log.debug(bex.getMessage());
                        req.setAttribute("errormessage", bex.getMessage());
                        bex.printStackTrace();
                        req.getRequestDispatcher("/client/client_editor.jsp").forward(req, resp);
                    }
//default
                } else {
                    req.getRequestDispatcher("/client/home.jsp").forward(req, resp);
                }

            }
        } catch (Exception ex) {
            req.setAttribute("errormessage", ex.getMessage());
            ex.printStackTrace();

            try {
                req.getRequestDispatcher("/client/home.jsp").forward(req, resp);
            } catch (Exception exe) {
                exe.printStackTrace();
            }
        } finally {
            HibernateSession.closeSession();

        }


    }


    private void saveOrUpdateClient(OwdClient client, HttpServletRequest req) throws Exception {
        try {

            ClientManager.saveOrUpdateClient(client, req.getRemoteUser());


        } catch (Exception ex) {
            req.setAttribute("errormessage", ex.getMessage());
            ex.printStackTrace();
            throw ex;
        } finally {
          //  HibernateSession.closeSession();
        }
    }


    private void updateClientFromEditPage(OwdClient client, HttpServletRequest req) throws Exception {
       // validateRequest(req);
        //    client.setAddressCorrectionEmail(req.getParameter("addressCorrectionEmail"));
        client.setUspsIntlDiscountSharePct(BigDecimal.ZERO);
       // client.setUspsPriorityDiscountSharePct(BigDecimal.ZERO);
        client.setUspsPriorityExpressPackFee(new BigDecimal(req.getParameter("uspsPriorityExpressPackFee")));
        if(null==req.getParameter("passportFee")){
            client.setPassportFee(BigDecimal.ZERO);
        }else{
            client.setPassportFee(new BigDecimal(req.getParameter("passportFee")));
        }
        if(null==req.getParameter("dimFactor")||req.getParameter("dimFactor").toString().equals("")){
            client.setDimFactor(0);
        }else{

            client.setDimFactor(Integer.parseInt(req.getParameter("dimFactor")));
        }
        if(null==req.getParameter("intlDimFactor")||req.getParameter("intlDimFactor").toString().equals("")){
            client.setIntlDimFactor(0);
        }else{

            client.setIntlDimFactor(Integer.parseInt(req.getParameter("intlDimFactor")));
        }

        client.setAddressOne(req.getParameter("addressOne"));
        client.setAddressTwo(req.getParameter("addressTwo"));
        client.setAmEmail(req.getParameter("amEmail"));
        client.setAsnRcvEmail(req.getParameter("asnRcvEmail"));
        client.setAutoShipEmail(req.getParameter("autoShipEmail"));
        client.setCity(req.getParameter("city"));
        client.setCompanyName(req.getParameter("companyName"));
        client.setContactName(req.getParameter("contactName"));
        client.setCountry(req.getParameter("country"));
        //    client.setCountryRefNum(req.getParameter("countryRefNum"));
        client.setDefaultCustomsDesc(req.getParameter("defaultCustomsDesc"));
        client.setFedexAcct(req.getParameter("fedexAcct"));
        client.setLowInventoryEmail(req.getParameter("lowInventoryEmail"));
        client.setLowInventoryEmail(req.getParameter("lowInventoryEmail"));
        client.setOriginalShipCarrier(req.getParameter("originalShipCarrier"));
        client.setPartialShipCarrier(req.getParameter("partialShipCarrier"));
        // client.setPolicyClass(req.getParameter("policyClass"));
        client.setPpProc(req.getParameter("ppProc"));
        client.setPpLogin(req.getParameter("ppLogin"));
        client.setPpPass(req.getParameter("ppPass"));
        if(null==req.getParameter("doEcheck")){
           client.setDoEcheck(new Integer(0));
        } else{
        client.setDoEcheck(Integer.valueOf(req.getParameter("doEcheck")));
        }
        client.setCheckLogin(req.getParameter("checkLogin"));
        client.setCheckPass(req.getParameter("checkPass"));
        client.setCheckProc(req.getParameter("checkProc"));
        client.setPrimaryEmailAddress(req.getParameter("primaryEmailAddress"));
        client.setPrimaryFaxNum(req.getParameter("primaryFaxNum"));
        client.setPrimaryPhoneNum(req.getParameter("primaryPhoneNum"));
        client.setRetAddr1(req.getParameter("retAddr1"));
        client.setRetAddr2(req.getParameter("retAddr2"));
        client.setShipEmailFrom(req.getParameter("shipEmailFrom"));
        client.setShipEmailBcc(req.getParameter("shipEmailBcc"));
        client.setShipEmailCc(req.getParameter("shipEmailCc"));
        client.setShipEmailFtr(req.getParameter("shipEmailFtr"));
       // client.setShipId(req.getParameter("shipId"));
        client.setShipIdPassword(req.getParameter("shipIdPassword"));
        client.setState(req.getParameter("state"));
        client.setUpsAcct(req.getParameter("upsAcct"));
        client.setUrlString(req.getParameter("urlString"));
        client.setYahooPass(req.getParameter("yahooPass"));
        client.setDefaultFacilityCode(req.getParameter("defaultFacilityCode"));
        client.setZip(req.getParameter("zip"));
        client.setShipperSymbol(req.getParameter("shipperSymbol").replaceAll("_"," "));


        client.setShipInvoiceReqd(("1".equals(req.getParameter("shipInvoiceReqd"))));
        client.setShipPackReqd(("1".equals(req.getParameter("shipPackReqd"))));
        client.setShipPickReqd(("1".equals(req.getParameter("shipPickReqd"))));
        client.setIsActive(("1".equals(req.getParameter("isActive"))));
        client.setShipPriceonslipReqd(("1".equals(req.getParameter("shipPriceonslipReqd"))));

        client.setPartialShipType(new Integer(("1".equals(req.getParameter("partialShipType")) ? 1 : 0)));

        client.setOriginalShipType(new Integer(("1".equals(req.getParameter("originalShipType")) ? 1 : 0)));
        client.setIsBackship(new Integer(("1".equals(req.getParameter("isBackShip")) ? 1 : 0)));

        //client.setScheduledDelay(0);
        client.setShipEmail(new Integer(("1".equals(req.getParameter("shipEmail")) ? 1 : 0)));
        client.setTellYahoo(new Integer(("1".equals(req.getParameter("tellYahoo")) ? 1 : 0)));
        client.setUsedcFirstclass(new Integer(("1".equals(req.getParameter("usedcFirstclass")) ? 1 : 0)));
        client.setLowInventoryAlert(new Integer(("1".equals(req.getParameter("lowInventoryAlert")) ? 1 : 0)).intValue());

        client.setBulkyPickCubicFeetDefault(new Float(req.getParameter("bulkyPickCubicFeetDefault")));
        client.setBulkyPickWeightDefault(new Float(req.getParameter("bulkyPickWeightDefault")));
        client.setBulkyPackCubicFeetDefault(new Float(req.getParameter("bulkyPackCubicFeetDefault")));
        client.setBulkyPackWeightDefault(new Float(req.getParameter("bulkyPackWeightDefault")));
        client.setBulkyPickLongDefault(new BigDecimal(req.getParameter("bulkyPickLongDefault")));
        client.setBulkyPickMedianDefault(new BigDecimal(req.getParameter("bulkyPickMedianDefault")));
        client.setBulkyPickShortDefault(new BigDecimal(req.getParameter("bulkyPickShortDefault")));

        client.setBulkyPackLongDefault(new BigDecimal(req.getParameter("bulkyPackLongDefault")));
        client.setBulkyPackMedianDefault(new BigDecimal(req.getParameter("bulkyPackMedianDefault")));
        client.setBulkyPackShortDefault(new BigDecimal(req.getParameter("bulkyPackShortDefault")));


        client.setIsShippingOnHold(new Integer(("1".equals(req.getParameter("isShippingOnHold")) ? 1 : 0)));
        client.setBillWeekly((("1".equals(req.getParameter("billWeekly")))));
        client.setBillCubicStorage(("1".equals(req.getParameter("billCubicStorage"))));
        client.setUspsOtherPackFee(new BigDecimal((req.getParameter("uspsOtherPackFee"))));
      //  client.setShipId(req.getParameter("printerName"));


        client.setChargeOnShip(new Integer(("1".equals(req.getParameter("chargeOnShip")) ? 1 : 0)));
        //todo verify qualifications for charge on ship

       /*  for(OwdClientMethod cm: client.getOwdClientMethods())
        {
            HibernateSession.currentSession().delete(cm);

        }
        client.getOwdClientMethods().clear();*/
       // HibernateSession.currentSession().flush();
        //log.debug("count of ship methods after removeAll " + client.getOwdClientMethods().size());

           if(client.getPackingInstructions()==null)
        {
            ClientPackingInstruction packNotes = new ClientPackingInstruction();
            packNotes.setInstructions("");
            packNotes.setClient(client);
            client.setPackingInstructions(packNotes);

        }

        client.getPackingInstructions().setInstructions(req.getParameter("packingInstructions")==null?"":req.getParameter("packingInstructions").trim());

         if(client.getPaypalData()==null)
        {
            OwdClientPaypalData config = ClientFactory.getNewPaypalData();
            config.setClient(client);
            client.setPaypalData(config);

        }
        if(client.getShipInvoiceConfig()==null)
        {
            OwdClientShipInvoiceConfig config = ClientFactory.getNewInvoiceConfig();
            config.setClient(client);
            client.setShipInvoiceConfig(config);

        }


        client.getShipInvoiceConfig().setInvoiceFlag(new Integer(("1".equals(req.getParameter("invoiceFlag")) ? 1 : 0)));
        client.getShipInvoiceConfig().setNotifyFlag(new Integer(("1".equals(req.getParameter("notifyFlag")) ? 1 : 0)));
        client.getShipInvoiceConfig().setNotifyEmails(req.getParameter("notifyEmails"));
        try
        {
            client.getShipInvoiceConfig().setInvoiceAmount(new BigDecimal(req.getParameter("invoiceAmount")));
        }catch(NumberFormatException nex)
        {
            client.getShipInvoiceConfig().setInvoiceAmount(new BigDecimal(0.00));
        }
        client.getShipInvoiceConfig().setInvoiceAmountType(req.getParameter("invoiceAmountType"));
        client.getShipInvoiceConfig().setInvoiceEmails(req.getParameter("invoiceEmails"));
        client.getShipInvoiceConfig().setInvoiceToAddress(req.getParameter("invoiceToAddress"));
        client.getShipInvoiceConfig().setInvoiceTriggerType(req.getParameter("invoiceTriggerType"));

        try
        {
            client.getShipInvoiceConfig().setInvoiceTriggerLevel(new BigDecimal(req.getParameter("invoiceTriggerLevel")));
        }catch(NumberFormatException nex)
        {
            client.getShipInvoiceConfig().setInvoiceTriggerLevel(new BigDecimal(0.00));
        }

        client.getPaypalData().setPaypalApiPassword(req.getParameter("paypalApiPassword"));
        client.getPaypalData().setPaypalApiUsername(req.getParameter("paypalApiUsername"));
        client.getPaypalData().setPaypalSignature(req.getParameter("paypalApiSignature"));
        client.getPaypalData().setUsePaypal(new Integer(("1".equals(req.getParameter("usePaypal")) ? 1 : 0)).intValue());

        try
        {
            client.setDiscountSharePct(new BigDecimal(req.getParameter("discountSharePct")));
        }catch(NumberFormatException nex)
        {
            client.setDiscountSharePct(new BigDecimal(0.00));
        }
/*
            try
        {
            client.setUspsPriorityDiscountSharePct(new BigDecimal(req.getParameter("uspsPriorityDiscountPct")));
        }catch(NumberFormatException nex)
        {
            client.setUspsPriorityDiscountSharePct(new BigDecimal(0.00));
        }

            try
        {
            client.setUspsIntlDiscountSharePct(new BigDecimal(req.getParameter("uspsIntlDiscountPct")));
        }catch(NumberFormatException nex)
        {
            client.setUspsIntlDiscountSharePct(new BigDecimal(0.00));
        }
            */
        try
        {
            client.setUspsPriorityExpressPackFee(new BigDecimal(req.getParameter("uspsPriorityExpressPackFee")));
            if(client.getUspsPriorityExpressPackFee().doubleValue()<0.00)
            {
                throw new Exception("Priority and Express Package fee must be greater than or equal to zero");
            }
        }catch(NumberFormatException nex)
        {
            client.setUspsPriorityExpressPackFee(new BigDecimal(0.10));
        }



        if(client.getPackingInstructions().getInstructions().length()>2048)
        {
            throw new Exception("Packing instructions must be 2048 characters or less. Please revise your text to fit within that limit.");
        }


        client.setCcGroupName(req.getParameter("ccGroupName"));

        HibernateSession.currentSession().saveOrUpdate(client);
        HibernateSession.currentSession().flush();

        List<String> fcodes = FacilitiesManager.getActiveFacilityCodes();
        Criteria criteria = HibernateSession.currentSession().createCriteria(OwdClientBillTypes.class);
        criteria.setProjection(Projections.distinct(Projections.property("code")));
        List<String> billTypes = criteria.list();



        Map<String,OwdClientBillTypes> billingMap = new TreeMap<String,OwdClientBillTypes>();
        for(OwdClientBillTypes btype:client.getBillTypes()) {
            if(btype.getGroupName().equalsIgnoreCase("")) {
                billingMap.put(btype.getLocationCode() + btype.getCode(), btype);
            }

        }
        for(String code:billTypes)
        {
            for(String loc:fcodes) {
                String value = req.getParameter(loc+code);
                String subtype = req.getParameter(loc+code+"subtype");
                if(value==null) {
                     //ignore if not found
                }   else
                {
                    if(billingMap.keySet().contains(loc+code))
                    {
                        billingMap.get(loc+code).setRate(new BigDecimal(value));
                        billingMap.get(loc+code).setSubtype(subtype);
                        HibernateSession.currentSession().saveOrUpdate(billingMap.get(loc+code));

                    }else{
                        OwdClientBillTypes type = new OwdClientBillTypes();
                        type.setCode(code);
                        type.setLocationCode(loc);
                        type.setSubtype(subtype);
                        type.setOwdClient(client);
                        type.setRate(new BigDecimal(value));
                        type.setGroupName("");
                        client.getBillTypes().add(type);
                        HibernateSession.currentSession().saveOrUpdate(type);

                    }
                    log.debug("got value "+value+":"+subtype+" for "+loc+":"+code);
                }
            }

        }

        
    }

    private void validateRequest(HttpServletRequest req) throws Exception {
        BigDecimal passportFee = new BigDecimal(req.getParameter("passportFee"));

        if(passportFee.doubleValue()<0.00) {
            throw new Exception("Passport fee must be greater than or equal to zero");
        }
    }

}
