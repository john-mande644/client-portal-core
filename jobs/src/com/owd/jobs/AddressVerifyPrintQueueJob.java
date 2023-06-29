package com.owd.jobs;

import com.owd.OWDShippingAPI.Returns.ReturnUtilities;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.EventFeeds;
import com.owd.core.business.order.Event;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.AddressManager;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.managers.JasperReportPrintManager;
import com.owd.core.managers.PackingManager;
import com.owd.core.payment.FinancialTransaction;
import com.owd.fedEx.SmartPostReturn;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import com.owd.jobs.jobobjects.AddressVerifyTask;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import com.owd.jobs.jobobjects.symphony.candleCount;
import com.owd.jobs.jobobjects.utilities.LineItemVerifier;
import com.owd.jobs.jobobjects.utilities.OrderInvalidatedException;
import com.owd.jobs.jobobjects.utilities.RemoteOrderReleaseAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Jul 13, 2006
 * Time: 11:36:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddressVerifyPrintQueueJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();

    static List<String> badOrderRefNums = new ArrayList<String>();


    public static void main(String[] args) throws Exception {

//        Sean 2019-12-24 pointing to devBox
//        System.setProperty("com.owd.environment","test");

   run();
            /*log.debug("Test");
    try {
        byte[] pdfdata = RemoteOrderReleaseAPI.getOrderReleaseApprovalAndPackingSlip("https://manage.symphonycommerce.com/fulfill", 11202933, true,
                new LineItemVerifier() {

                    @Override
                    public boolean includeLineItemInRequest(OwdLineItem line) {
                        return line.getCustRefnum().length() > 0;
                    }

                }
        );

    }catch (Exception e){
        e.printStackTrace();
    }*/
     //   log.debug(OrderUtilities.getPrintTemplateCustomCode(9361770).length());

       /* try{

            byte[] pdfdata = RemoteOrderReleaseAPI.getOrderReleaseApprovalAndPackingSlip("https://api.photojojo.com/v1/owd/get-packing-slip", 10698553, false);
           log.debug(pdfdata.length);

*//*
            for(Integer i: l){
                OwdOrder order =(OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, i);
               // candleCount ccount = getCandleCountForOrder(order);
               //  log.debug(ccount.getCount());
                //  log.debug(ccount.getType());
                setPredictedPack(order);
            }*//*

        }catch (Exception e){
            e.printStackTrace();
        }*/
       // System.out.println("PDF:"+pdfdata.length);


    }

    public static void testReleaseCheck(int orderId)  throws Exception
    {
        AddressVerifyPrintQueueJob job = new AddressVerifyPrintQueueJob();
        log.debug("results="+job.doReleaseCheck(orderId, ((OwdOrder)HibernateSession.currentSession().load(OwdOrder.class,orderId)).getShipinfo(), null));

    }


    static Map<String, String> serviceSwitchMap = new TreeMap<String, String>();
    static Map<String, String> newServiceCodeMap = new TreeMap<String, String>();

    {

        //   serviceSwitchMap.put("FedEx 2Day", "UPS 2nd Day Air");
        //   newServiceCodeMap.put("UPS 2nd Day Air", "UPS.2DA");
        //   serviceSwitchMap.put("FedEx Express Saver", "UPS 3 Day Select");
        //  newServiceCodeMap.put("UPS 3 Day Select", "UPS.3DA");
        //  serviceSwitchMap.put("FedEx Ground", "UPS Ground");
        //   newServiceCodeMap.put("UPS Ground", "UPS.GND");
        serviceSwitchMap.put("FedEx Ground U.S. to Canada", "UPS Standard Canada");
        newServiceCodeMap.put("UPS Standard Canada", "UPS.STDCAMX");
     //   serviceSwitchMap.put("FedEx International Economy", "UPS Worldwide Expedited");
     //   newServiceCodeMap.put("UPS Worldwide Expedited", "UPS.WEPD");
        serviceSwitchMap.put("FedEx International First", "UPS Worldwide Express");
        newServiceCodeMap.put("UPS Worldwide Express", "UPS.WEXP");
     //   serviceSwitchMap.put("FedEx International Priority", "UPS Worldwide Express Saver");
     //   newServiceCodeMap.put("UPS Worldwide Express Saver", "CONNECTSHIP_UPS.UPS.EXPSVR");
        //  serviceSwitchMap.put("FedEx Priority Overnight", "UPS Next Day Air");
        //  newServiceCodeMap.put("UPS Next Day Air", "UPS.NDA");
        //  serviceSwitchMap.put("FedEx Standard Overnight", "UPS Next Day Air Saver");
        //  newServiceCodeMap.put("UPS Next Day Air Saver", "UPS.NDS");


    }

    public static void switchFedexShippingToUps(int orderId, OwdOrderShipInfo info) {
        String oldService = info.getCarrService();

        try {
            log.debug("checking for fedex switch for " + oldService);
            String newService = serviceSwitchMap.get(oldService);

            if (oldService.equalsIgnoreCase("FEDEX GROUND") && (info.getShipCountry().equalsIgnoreCase("MEXICO") || info.getShipCountry().equalsIgnoreCase("CANADA"))) {
                if (info.getOrder().getClientFkey() == 494) {

                    info.setCarrService("USPS Priority Mail International");
                    info.setCarrServiceRefNum("OWD_USPS_I_PRIORITY");
                    HibernateSession.currentSession().save(info);
                    HibUtils.commit(HibernateSession.currentSession());
                    Event.addOrderEvent(orderId, Event.kEventTypeHandling, "Ship method switched from " + oldService + " to " + "UPS Standard Canada", "Address Verification Check");

                } else {
                  //  info.setCarrService("UPS Standard Canada");
                  //  info.setCarrServiceRefNum("UPS.STDCAMX");
                }

            }
            if ((newService != null)) {
                log.debug("Switching FEDEX " + oldService + " to " + newService);
                info.setCarrService(newService);
                info.setCarrServiceRefNum(newServiceCodeMap.get(newService));
                HibernateSession.currentSession().save(info);
                HibUtils.commit(HibernateSession.currentSession());
                Event.addOrderEvent(orderId, Event.kEventTypeHandling, "Ship method switched from " + oldService + " to " + newService, "Address Verification Check");
            }
        } catch (Exception ex) {
            log.debug("ERROR FEDEX checking oid=" + orderId);
            ex.printStackTrace();
        }

    }

    public void internalExecute() {

        try {

            PreparedStatement stmt = HibernateSession.getPreparedStatement("delete from  owd_print_queue3  from owd_print_queue3 p join owd_order o on o.order_id=p.order_id and (order_status<>'At Warehouse')");
            stmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            stmt.close();

            stmt = HibernateSession.getPreparedStatement("delete from  owd_print_queue3 where order_id in \n" +
                    "(select owd_order.order_id from owd_order left join owd_print_queue3 on owd_order.order_id = owd_print_queue3.order_id where  group_name = 'MH' and owd_order.client_fkey = 529)");
            stmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            stmt.close();

            //excludes orders for clients on shipping hold
            ResultSet rs = HibernateSession.getResultSet("select p.order_id,print_queue_id  from owd_print_queue3 p  join owd_order o (NOLOCK) on o.order_id=p.order_id join owd_client c on c.client_id=p.client_id\n" +
                    "                    where isVerified=0  \n" +
//                    "and o.facility_code in ('DC6','DC1')  \n" +
                    "                     and datediff(second,post_date,getdate())>0 order by dbo.udf_getsladate(o.order_id)");

            // Sean 2019-12-24 case 715152 DevBox Query top 10
            /*ResultSet rs = HibernateSession.getResultSet("select top 10 p.order_id,print_queue_id  from " +
                    "owd_print_queue3 p  join owd_order o (NOLOCK) on o.order_id=p.order_id join owd_client c on c.client_id=p.client_id\n" +
                    "                    where isVerified=0\n" +
                    "                     and datediff(second,post_date,getdate())>0 order by dbo.udf_getsladate(o.order_id)");*/

            List<Integer> ids = new ArrayList<Integer>();
            Map<Integer, List<Integer>> printidMap = new TreeMap<Integer, List<Integer>>();
            while (rs.next()) {
                ids.add(rs.getInt(1));
                if (printidMap.containsKey(rs.getInt(1))) {
                    printidMap.get(rs.getInt(1)).add(rs.getInt(2));
                } else {
                    List<Integer> pqidList = new ArrayList<Integer>();
                    pqidList.add(rs.getInt(2));
                    printidMap.put(rs.getInt(1), pqidList);
                }

            }
            HibernateSession.closeStatement();

            stmt = HibernateSession.getPreparedStatement("update  owd_order_ship_info set carr_service='LTL' where carr_service is null and carr_service_ref_num like '%LTL%';");


            stmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            HibernateSession.closeStatement();

            ExecutorService exec = Executors.newFixedThreadPool(5); // 5 threads
            for (Integer id : ids) {
                log.debug("Creating task for order ID: "+id) ;
                exec.submit(new AddressVerifyTask(id,printidMap.get(id)));
            }

            exec.shutdown();
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        } catch (Exception ex) {
            log.debug(ex.getMessage());
        } finally {
            try {
              //  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().close();
            } catch (Exception exc) {
            }

        }


    }

    protected boolean isVirtualOrder() {
        boolean isVirtual = false;
        //is calltag order?


        return isVirtual;
    }

    public static boolean shipVirtualOrder(Integer id) throws Exception {

        boolean sendToPrintQueue = true;
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, id);
        if (order.getClientFkey() == 463) {

            sendToPrintQueue = handleHighTimesDropshipItems(order);

        }
        return sendToPrintQueue;
    }

    public static boolean handleHighTimesDropshipItems(OwdOrder order) throws Exception {
        boolean allDrops = true;
        Map<String, List<OwdLineItem>> lineMap = new TreeMap<String, List<OwdLineItem>>();

        //check all lines with units to ship
        for (OwdLineItem line : order.getLineitems()) {
            OwdInventory item = line.getOwdInventory();

            //verify drop item status
            if (item.getNotes().trim().toUpperCase().startsWith("DROP:") && line.getQuantityActual() > 0) {
                String dropnote = item.getNotes().trim().toUpperCase();

                String[] dropper = dropnote.split(":");

                //verify format
                if (dropper.length == 3 && (dropper[1].equals("EMAIL") || dropper[1].equals("FAX"))) {

                    //check if we've handled this line previously
                    if (!(line.getDescription().toUpperCase().indexOf("(SHIPPED SEPARATELY)") >= 0)) {
                        line.setDescription(line.getDescription() + " (SHIPPED SEPARATELY)");

                        //add to linemap by shipper
                        if (!(lineMap.containsKey(dropnote))) {
                            List<OwdLineItem> lineList = new ArrayList<OwdLineItem>();
                            lineMap.put(dropnote, lineList);
                        }
                        lineMap.get(dropnote).add(line);
                    }

                } else {
                    throw new Exception("Invalid drop ship instruction notes for SKU " + item.getInventoryNum());
                }

            } else {
                if (line.getQuantityActual() > 0 && item.getIsAutoInventory() == 0) {
                    allDrops = false;
                }
            }
        }

        //check linemap for size
        String token = CasetrackerAPI.getLoginToken();

        for (String dropDirection : lineMap.keySet()) {
            String[] dropper = dropDirection.split(":");

            if (dropper[1].equals("EMAIL")) {
                //  static public void createNewSendEmailCaseForClient(String title, String body, String clientID, String toEmail, String token) {
                String emailBody = "Drop ship request from High Times Head Shop (Trans High):\n" +
                        "\n" +
                        "Ship the following items:\n\n";
                for (OwdLineItem lineitem : lineMap.get(dropDirection)) {
                    emailBody = emailBody + lineitem.getQuantityActual() + (lineitem.getQuantityActual() > 1 ? " units of item " : " unit of item ") +
                            lineitem.getInventoryNum() + " (" + lineitem.getDescription().replaceAll("\\(SHIPPED SEPARATELY\\)", "") + ")\n";
                }
                emailBody = emailBody + "\n" +
                        "to:\n" +
                        "\n" +
                        order.getShipinfo().getShipFirstName() + " " + order.getShipinfo().getShipLastName() + "\n" +
                        (order.getShipinfo().getShipCompanyName().length() > 0 ? order.getShipinfo().getShipCompanyName() + "\n" : "") +
                        order.getShipinfo().getShipAddressOne() + "\n" +
                        (order.getShipinfo().getShipAddressTwo().length() > 0 ? order.getShipinfo().getShipAddressTwo() + "\n" : "") +
                        order.getShipinfo().getShipCity() + ", " + order.getShipinfo().getShipState() + "  " + order.getShipinfo().getShipZip() + "\n" +
                        order.getShipinfo().getShipCountry() + "\n\nOrder #" + order.getOrderRefnum();
                String caseID = CasetrackerAPI.createNewSendEmailCaseForClient("High Times Headshop Drop Ship Request #" + order.getOrderRefnum(),
                        emailBody, 463 + "", dropper[2], token);
                Event.addOrderEvent(order.getOrderId(), Event.kEventTypeHandling, "Sent drop ship request to " + dropper[1] + "; case ID " + caseID, "System");
                for (OwdLineItem lineitem : lineMap.get(dropDirection)) {
                    HibernateSession.currentSession().save(lineitem);
                }
                HibUtils.commit(HibernateSession.currentSession());


            } else if (dropper[1].equals("FAX")) {
                //  static public void createNewCaseForClient(String title, String body, String clientID, String email, String token) {
                String faxBody = "\nFor OWD personnel: Fax the following shipment request to " + dropper[2] + "\n" +
                        "\n-----------------------------------------------------------------" +
                        "\n" +
                        "Drop ship request from High Times Head Shop (Trans High):\n" +
                        "\n" +
                        "Ship the following items:\n\n";
                for (OwdLineItem lineitem : lineMap.get(dropDirection)) {
                    faxBody = faxBody + lineitem.getQuantityActual() + (lineitem.getQuantityActual() > 1 ? " units of item " : " unit of item ") +
                            lineitem.getInventoryNum() + " (" + lineitem.getDescription().replaceAll("\\(SHIPPED SEPARATELY\\)", "") + ")\n";
                }
                faxBody = faxBody + "\n" +
                        "to:\n" +
                        "\n" +
                        order.getShipinfo().getShipFirstName() + " " + order.getShipinfo().getShipLastName() + "\n" +
                        (order.getShipinfo().getShipCompanyName().length() > 1 ? order.getShipinfo().getShipCompanyName() + "\n" : "") +
                        order.getShipinfo().getShipAddressOne() + "\n" +
                        (order.getShipinfo().getShipAddressTwo().length() > 0 ? order.getShipinfo().getShipAddressTwo() + "\n" : "") +
                        order.getShipinfo().getShipCity() + ", " + order.getShipinfo().getShipState() + "  " + order.getShipinfo().getShipZip() + "\n" +
                        order.getShipinfo().getShipCountry() + "\n\nOrder #" + order.getOrderRefnum();

                String caseID = CasetrackerAPI.createNewCaseForClient("High Times Headshop Drop Ship Request #" + order.getOrderRefnum(),
                        faxBody, 463 + "", null, token);
                Event.addOrderEvent(order.getOrderId(), Event.kEventTypeHandling, "Sent drop ship request to " + dropper[1] + "; case ID " + caseID, "System");
                for (OwdLineItem lineitem : lineMap.get(dropDirection)) {
                    HibernateSession.currentSession().save(lineitem);

                }
                HibUtils.commit(HibernateSession.currentSession());

            }


        }


        if (allDrops) {

            PackingManager.packAndShip(order.getOrderId());

            return false;
        } else {
            //continue normally
            return true;
        }
    }

    public static boolean checkOrderAddress(Integer id, OwdOrderShipInfo info, Map<Integer, List<Integer>> printidMap) throws Exception {
        try {

          if(info.getCarrServiceRefNum().equalsIgnoreCase("OWD.NOBOX.PICKEDUP"))
          {
              return true;
          } else{

              //Update Flat Rate Domestic methods for non US
              AddressManager.checkFlatRateDomesticVsInternational(info);

            Address testAddr = new Address(info.getShipCompanyName(),
                    info.getShipAddressOne(), info.getShipAddressTwo(),
                    info.getShipCity(), info.getShipState(), info.getShipZip(), info.getShipCountry());

            StringBuffer sb = new StringBuffer();
            try {
                // if (info.getCarrService().toUpperCase().contains("FEDEX")) {
                //    throw new Exception("FedEx ship methods not available and no equivalent UPS method was found. Correct manually and resubmit. (Shipping Address)");
                //  }

                //deliverr does not want to use our address validation. Treat it like AVS Override
                if(info.getOrder().getClient().getClientId()==622) info.setAvsOverRide(true);

                if(info.getOrder().getClient().getClientId()==531) info.setAvsOverRide(true);
                //Lyft PPE KIT Bypass AVS
               // if(info.getOrder().getClientFkey()==529&&info.getOrder().getGroupName().equalsIgnoreCase("PPE KIT")) info.setAvsOverRide(true);

                    if (!info.isAvsOverRide()) {

                        AddressManager.checkAndCorrectAddressForLocation(testAddr, info.getCarrServiceRefNum(), info.getOrder().getFacilityCode() == null ? "DC1" : info.getOrder().getFacilityCode(), info.getOrder().getClientFkey());

                }
            } catch (Exception exx) {
                if (!(exx.getMessage().contains("USPS DOWN"))) {
                    throw exx;
                } else {
                    sb.append("USPS Unavailable - passing order through uncorrected... \n");
                }
            }
              AddressManager.invalidCharacterCheckForAddress(testAddr);
              if(info.getShipFirstName().contains("??")||info.getShipLastName().contains("??")){
                  throw new Exception("ERROR: Invalid Name. Please update and re-release");
              }


            log.debug("Success!:" + testAddr);
            //create order comment on any change

            sb.append("Shipping Address Checked \n");
            sb.append("Changes: \n");
           /* if (0 != info.getShipCompanyName().compareTo(testAddr.company_name)) {
                sb.append("Company: " + info.getShipCompanyName() + " -> " + testAddr.company_name + "\n");
                info.setShipCompanyName(testAddr.company_name);

            }*/
            if (0 != info.getShipAddressOne().compareTo(testAddr.address_one)) {
                sb.append("Addr 1: " + info.getShipAddressOne() + " -> " + testAddr.address_one + "\n");
                info.setShipAddressOne(testAddr.address_one);

            }
            if (0 != info.getShipAddressTwo().compareTo(testAddr.address_two)) {
                sb.append("Addr 2: " + info.getShipAddressTwo() + " -> " + testAddr.address_two + "\n");
                info.setShipAddressTwo(testAddr.address_two);

            }
            if (0 != info.getShipCity().compareTo(testAddr.city)) {
                sb.append("City: " + info.getShipCity() + " -> " + testAddr.city + "\n");
                info.setShipCity(testAddr.city);

            }
            if (0 != info.getShipState().compareTo(testAddr.state)) {
                sb.append("State: " + info.getShipState() + " -> " + testAddr.state + "\n");
                info.setShipState(testAddr.state);

            }
            if (0 != info.getShipZip().compareTo(testAddr.zip)) {
                sb.append("Postal Code: " + info.getShipZip() + " -> " + testAddr.zip + "\n");
                info.setShipZip(testAddr.zip);

            }
            if (0 != info.getShipCountry().compareTo(testAddr.country)) {
                sb.append("Country: " + info.getShipCountry() + " -> " + testAddr.country + "\n");
                info.setShipCountry(testAddr.country);
                info.setShipCountryRefNum(OrderUtilities.getCountryList().getRefForValue(testAddr.country));


            }
              sb.append(testAddr.verificationNote);
              info.setSsResidential(testAddr.isResidential);
            if(info.isAvsOverRide()) {
                sb.append("\nAVS OverRide. Address not corrected.");
            }
            log.debug(sb);
            HibernateSession.currentSession().save(info);
            HibernateSession.currentSession().flush();


            HibUtils.commit(HibernateSession.currentSession());
            //save change

            //save comment
            Event.addOrderEvent(info.getOrder().getOrderId(), Event.kEventTypeHandling, sb.toString(), "Address Verification Check");
            //notify project mailbox  on failure

              //Place client specific address verification rules in here.
              //todo move this to drools.
              if(info.getOrder().getClientFkey()==625){
                  if(OrderUtilities.isPO(info)){
                      throw new Exception("ERROR: All PO Boxes are to be put on hold per client.");
                  }
                  if(OrderUtilities.isApo(info)&&!info.getCarrService().contains("USPS")){
                      throw new Exception("ERROR: Invalid ship method for APO address");
                  }
                  if(info.getCarrService().contains("USPS") && !OrderUtilities.isApo(info)){
                      throw new Exception("ERROR: Invalid ship method per client, only APO can go USPS");
                  }



              }



            return true;
          }
        } catch (Exception ex) {
            log.debug("*** Unable to verify or correct: " + ex.getMessage() + " for order id " + id);
            if(info.getOrder().getClientFkey()==529){
                if(info.getOrder().getGroupName().equals("MH")){
                    //if Mailhouse order bypass address verify errors. Mail house does their own validation
                    return true;
                }
            }
            HibUtils.rollback(HibernateSession.currentSession());
            //set on hold
            rejectOrder(id, ex.getMessage().indexOf("ERROR") >= 0 ? ex.getMessage() : "Shipping Address Failed Check! ", printidMap);
            return false;
        }
    }

    public static boolean doReleaseCheck(Integer id, OwdOrderShipInfo info, Map<Integer, List<Integer>> printidMap)
            throws Exception {
         boolean okToContinue = true;
         log.debug("dong release check for oid "+id+" client id: "+info.getOrder().getClientFkey());
         if(OrderUtilities.getPrintTemplateCustomCode(id).length()<1) {   //custom template value overrides default behavior

            if (info.getOrder().getClientFkey() == 387) {
                okToContinue = doPhotojojoPackingSlip(id, info, printidMap);
            } else if (info.getOrder().getClientFkey() == 483) {
                okToContinue = doKarmaPackingSlip(id, info, printidMap);
            } else if (info.getOrder().getClientFkey() == 482) {
                okToContinue = checkBabelandRules(id, info);
            } else if (info.getOrder().getClientFkey() == 489 || info.getOrder().getClientFkey() == 616 || info.getOrder().getClientFkey() == 625) {
                okToContinue = doSneakpeeqPackingSlip(id, info, printidMap);
            } else if (info.getOrder().getClientFkey() == 494) {
                okToContinue = doMarineEssentialsPackingSlip(id, info, printidMap);

            }else if(info.getOrder().getClientFkey() == 529||info.getOrder().getClientFkey() == 657||info.getOrder().getClientFkey() == 664){
                okToContinue = doInternalJasperTemplatePackingSlip(id, info,printidMap);
            }else if(info.getOrder().getClientFkey() == 531 && !info.getOrder().getOrderType().equalsIgnoreCase("SHOPIFYWAREHOUSE")){
                okToContinue = doInternalJasperTemplatePackingSlip(id, info,printidMap);

            /* // 1086777 remove rule
            }else if(info.getOrder().getClientFkey() == 266 && OrderUtilities.prtPackRqd(id)){
                okToContinue = doInternalJasperTemplatePackingSlip(id, info,printidMap);*/

            }else if(info.getOrder().getClientFkey() == 679 ){
                okToContinue = doInternalJasperTemplatePackingSlip(id, info,printidMap);

            /*}else if(info.getOrder().getClientFkey() == 696 && info.getOrder().getGroupName().equalsIgnoreCase("macys")){
                okToContinue = doInternalJasperTemplatePackingSlip(id, info,printidMap);*/

            }else if(info.getOrder().getClientFkey() == 640 ){
                okToContinue = doInternalJasperTemplatePackingSlip(id, info,printidMap);
            }

        } else {
            okToContinue = doInternalJasperTemplatePackingSlip(id, info,printidMap);
        }
        // to set PreMailingValidator to active remove or comment the next line setting debug to true
//        PreMailingValidator.setDebug(true);

//        PreMailingValidator.validate(info);

        // Sean refactor
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, id);
        if(SmartPostReturn.isSmartPostReturnItem(order)){
            SmartPostReturn.setSmartPostReturnFlag(info);
        }

        if(ReturnUtilities.doesOrderNeedMIReturnLabel(info.getOrder())==1&&okToContinue){
            ReturnUtilities.generateAndMISaveLabel(order);
        }

        if(SmartPostReturn.doesOrderNeedReturnLabel(info.getOrder())==1&&okToContinue){
            SmartPostReturn.generateAndSaveLabel(info.getOrder());
        }

        return okToContinue;
    }

    public static boolean doInternalJasperTemplatePackingSlip(Integer id, OwdOrderShipInfo info, Map<Integer, List<Integer>> printidMap) throws Exception {

        boolean okToContinue = true;



            log.debug("***OWD PDF TEMPLATE PRINT***");
            try {
                byte[] pdfdata = JasperReportPrintManager.getPackingSlip(info.getOrder().getOrderNum(), info.getOrder().getClientFkey(), OrderUtilities.getPrintTemplateCustomCode(id));



                if (pdfdata != null && pdfdata.length>1020) //if no exception thrown but no data received, then proceed but punt to normal template
                {

                    for (Integer pqid : printidMap.get(id)) {
                        Query q = HibernateSession.currentSession().createSQLQuery("update owd_print_queue3 set pdf_binary= :pdfdata where print_queue_id=:orderId");
                        q.setParameter("pdfdata", pdfdata);
                        q.setParameter("orderId", pqid);
                        q.executeUpdate();
                        HibUtils.commit(HibernateSession.currentSession());
                    }
                } else {
                    throw new Exception("Invalid or missing PDF data returned from packing slip template print task");
                }
            } catch (Exception ex) {
                Date poster = info.getOrder().getPostDate();
                Date now = new Date();

                if ((now.getTime() - poster.getTime()) > (1000L * 60L * 60L * 9L))  //9 hours
                {

                    okToContinue = false;
                    badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
                    Mailer.sendMail("OWD Order Pack Slip Template Order Held (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "servicerequests@owd.com", "donotreply@owd.com");

                    throw new Exception("ERROR: Unspecified error (" + ex.getMessage() + ") order template print method");

                } else {
                    okToContinue = false;
                    if (!(badOrderRefNums.contains(info.getOrder().getOrderNumBarcode()))) {
                        Mailer.sendMail("OWD Order Pack Slip Template Order Print Fail (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "servicerequests@owd.com", "donotreply@owd.com");

                        badOrderRefNums.add(info.getOrder().getOrderNumBarcode());
                    }
                }
            }

        return okToContinue;
    }

    public static boolean checkBabelandRules(Integer id, OwdOrderShipInfo info) throws Exception {
        boolean okToContinue = true;
        OrderStatus os = new OrderStatus("" + id);
        log.debug("b check id " + id);
        if (!(os.shipping.shipAddress.isUSOrCanada())) {
            log.debug("b is not us/canada");
            for (OwdLineItem line : info.getOrder().getLineitems()) {
                log.debug("b checking item " + line.getInventoryNum());
                if (line.getInventoryNum().equalsIgnoreCase("01 031 00") || line.getInventoryNum().equalsIgnoreCase("14 503 00")) {
                    Mailer.sendMail("Hitachi shipment outside N. America blocked : " + info.getOrder().getOrderRefnum(), "\r\nOrder " + info.getOrder().getOrderRefnum() + " was blocked by OWD from shipping because it contained 01 031 00 or 14 503 00 " +
                            "and was set to ship outside of North America.\r\n\r\nThe order is currently On Hold at OWD.", "fulfillment@babeland.com");
                    throw new Exception("ERROR: Unposted due to ineligible item shipping outside North America; fulfillment@babeland.com notified");
                }
            }
        }


        return okToContinue;

    }

    public static boolean doMarineEssentialsPackingSlip(Integer id, OwdOrderShipInfo info, Map<Integer, List<Integer>> printidMap) throws Exception {

        boolean okToContinue = true;
        log.debug("X:" + info.getOrder().getClientFkey());
        log.debug("X:" + info.getOrder().getOrderType());
        log.debug("X:" + info.getOrder().getCreatedBy());
        log.debug("X:" + info.getOrder().getOrderNum());
        log.debug("X:" + info.getOrder().getOrderRefnum());


        if (info.getOrder().getClientFkey() == 494 && (info.getOrder().getOrderType().toUpperCase().contains("INFUSIONSOFT"))) {
            log.debug("***ME PDF/RELEASE CHECK***");
            try {
                Map<String, String> mapper = new TreeMap<String, String>();
                mapper.put("orderId", info.getOrder().getOrderRefnum());
                mapper.put("barcode", info.getOrder().getOrderNumBarcode().replaceAll("\\*", ""));

                byte[] pdfdata = RemoteOrderReleaseAPI.getMarineEssentialsPackingSlip("http://shopping.marineessentials.com/marine-d3/thinvoice/generate_invoice.php", mapper);

                if (pdfdata != null) //if no exception thrown but no data received, then proceed but punt to normal template
                {

                    for (Integer pqid : printidMap.get(id)) {
                        Query q = HibernateSession.currentSession().createSQLQuery("update owd_print_queue3 set pdf_binary= :pdfdata where print_queue_id=:orderId");
                        q.setParameter("pdfdata", pdfdata);
                        q.setParameter("orderId", pqid);
                        q.executeUpdate();
                        HibernateSession.currentSession().flush();
                        HibUtils.commit(HibernateSession.currentSession());
                    }
                } else {
                    throw new Exception("Invalid or missing PDF data returned from Marine Essentials server");
                }
            } catch (OrderInvalidatedException oiex) {
                badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
            } catch (Exception ex) {
                Date poster = info.getOrder().getPostDate();
                Date now = new Date();

                if ((now.getTime() - poster.getTime()) > (1000L * 60L * 60L * 1L))  //9 hours
                {

                    okToContinue = true; //let it go, default to OWD template
                    badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
                   // Mailer.sendMail("OWD-Marine Essentials Order Release API Order Held (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "ritchie@marined3.com", "donotreply@owd.com");

                  //  throw new Exception("ERROR: Unspecified error (" + ex.getMessage() + ") remote order release API");

                } else {
                    okToContinue = false;
                    if (!(badOrderRefNums.contains(info.getOrder().getOrderNumBarcode()))) {
                        Mailer.sendMail("OWD-Marine Essentials Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "michael@marined3.com", "donotreply@owd.com");
                        Mailer.sendMail("OWD-Marine Essentials Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "owditadmin@owd.com", "donotreply@owd.com");
                        badOrderRefNums.add(info.getOrder().getOrderNumBarcode());
                    }
                }
            }
        }

        return okToContinue;
    }


    public static boolean doSneakpeeqPackingSlip(Integer id, OwdOrderShipInfo info, Map<Integer, List<Integer>> printidMap) throws Exception {
        boolean badlines = false;
        boolean okToContinue = true;

        for (OwdLineItem line : info.getOrder().getLineitems()) {
            if (line.getCustRefnum().trim().length() < 1 && line.getIsInsert()==0) {
                badlines = true;
            }
        }
        OwdOrder oo = info.getOrder();
        log.debug("clientFkey="+oo.getClientFkey());
        log.debug("createdby="+oo.getCreatedBy());
        String createdBy = info.getOrder().getCreatedBy()==null?"":info.getOrder().getCreatedBy();
        log.debug("createdby="+createdBy);
        if ((info.getOrder().getClientFkey() == 489 ||info.getOrder().getClientFkey() == 640 ||info.getOrder().getClientFkey() == 616 ||info.getOrder().getClientFkey() == 625 )&& createdBy.trim().length() == 0 && (!(badlines))) {

            log.debug("***SP PDF/RELEASE CHECK***");
            try {
                byte[] pdfdata = RemoteOrderReleaseAPI.getOrderReleaseApprovalAndPackingSlip("https://manage.symphonycommerce.com/fulfill", id, true,
                        new LineItemVerifier(){

                            @Override
                            public boolean includeLineItemInRequest(OwdLineItem line)
                            {
                                return line.getCustRefnum().length()>0;
                            }

                        }
                );

                //byte[] pdfdata = RemoteOrderReleaseAPI.getSymphonyPackingSlip(id);      //per Symphony Steven Doubilet

                if (pdfdata != null) //if no exception thrown but no data received, then proceed but punt to normal template
                {

                    for (Integer pqid : printidMap.get(id)) {
                        Query q = HibernateSession.currentSession().createSQLQuery("update owd_print_queue3 set pdf_binary= :pdfdata where print_queue_id=:orderId");
                        q.setParameter("pdfdata", pdfdata);
                        q.setParameter("orderId", pqid);
                        q.executeUpdate();
                        HibUtils.commit(HibernateSession.currentSession());
                    }
                } else {
                    throw new Exception("Invalid or missing PDF data returned from packing slip server");
                }
            } catch (OrderInvalidatedException oiex) {
                badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
            } catch (Exception ex) {
                System.out.println("Packing slip erorrrororrr");
                ex.printStackTrace();

                Date poster = info.getOrder().getPostDate();
                Date now = new Date();

                if ((now.getTime() - poster.getTime()) > (1000L * 60L * 60L * 9L))  //9 hours
                {

                    okToContinue = false;
                    badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
                    Mailer.sendMail("OWD-SP Order Release API Order Held (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "api-support@sneakpeeq.com", "donotreply@owd.com");

                    throw new Exception("ERROR: Unspecified error (" + ex.getMessage() + ") remote order release API");

                } else {
                    okToContinue = false;
                    if (!(badOrderRefNums.contains(info.getOrder().getOrderNumBarcode()))) {
                        ex.printStackTrace();
                       /* Vector vec = new Vector<String>();
                        vec.add("kunjal@symphonycommerce.com");
                        vec.add("mingfei@symphonycommerce.com");
                        vec.add("api-support@sneakpeeq.com");
                        Mailer.sendMail("OWD-SP Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), vec.toArray(), "donotreply@owd.com");
                       */
                        Mailer.sendMail("OWD-SP Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "owditadmin@owd.com", "donotreply@owd.com");
                        badOrderRefNums.add(info.getOrder().getOrderNumBarcode());
                    }
                }
            }
        }
        return okToContinue;
    }

    public static boolean doKarmaPackingSlip(Integer id, OwdOrderShipInfo info, Map<Integer, List<Integer>> printidMap) throws Exception {
        boolean okToContinue = true;
        if (info.getOrder().getClientFkey() == 483 && info.getOrder().getOrderType().trim().length() == 0 && info.getOrder().getOrderRefnum().length() >= 32) {
            log.debug("***KARMA PDF/RELEASE CHECK***");
            try {
                byte[] pdfdata = RemoteOrderReleaseAPI.getOrderReleaseApprovalAndPackingSlip("http://ws.getkarma.com/shipment/approve", id, true);

                if (pdfdata != null) //if no exception thrown but no data received, then proceed but punt to normal template
                {

                    for (Integer pqid : printidMap.get(id)) {
                        Query q = HibernateSession.currentSession().createSQLQuery("update owd_print_queue3 set pdf_binary= :pdfdata where print_queue_id=:orderId");
                        q.setParameter("pdfdata", pdfdata);
                        q.setParameter("orderId", pqid);
                        q.executeUpdate();
                        HibUtils.commit(HibernateSession.currentSession());
                    }
                } else {
                    throw new Exception("Invalid or missing PDF data returned from Karma server");
                }
            } catch (OrderInvalidatedException oiex) {
                badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
            } catch (Exception ex) {
                Date poster = info.getOrder().getPostDate();
                Date now = new Date();

                if ((now.getTime() - poster.getTime()) > (1000L * 60L * 60L * 9L))  //9 hours
                {

                    okToContinue = false;
                    badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
                    Mailer.sendMail("OWD-Karma Order Release API Order Held (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "support@getkarma.com", "donotreply@owd.com");

                    throw new Exception("ERROR: Unspecified error (" + ex.getMessage() + ") remote order release API");

                } else {
                    okToContinue = false;
                    if (!(badOrderRefNums.contains(info.getOrder().getOrderNumBarcode()))) {
                        Mailer.sendMail("OWD-Karma Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "support@getkarma.com", "donotreply@owd.com");
                        Mailer.sendMail("OWD-Karma Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "owditadmin@owd.com", "donotreply@owd.com");
                        badOrderRefNums.add(info.getOrder().getOrderNumBarcode());
                    }
                }
            }
        }
        return okToContinue;
    }

    public static boolean doPhotojojoPackingSlip(Integer id, OwdOrderShipInfo info, Map<Integer, List<Integer>> printidMap) throws Exception {
        boolean okToContinue = true;
        if (info.getOrder().getClientFkey() == 387 && info.getOrder().getOrderRefnum().contains("-"))//suspended, switch value to 387 when live //photojojo special pdf/release stuff
        {
            log.debug("***PHOTOJOJO PDF/RELEASE CHECK***");
            try {
                byte[] pdfdata = RemoteOrderReleaseAPI.getOrderReleaseApprovalAndPackingSlip("https://api.photojojo.com/v1/owd/get-packing-slip", id, false);

                if (pdfdata != null) //if no exception thrown but no data received, then proceed but punt to normal template
                {

                    for (Integer pqid : printidMap.get(id)) {
                        Query q = HibernateSession.currentSession().createSQLQuery("update owd_print_queue3 set pdf_binary= :pdfdata where print_queue_id=:orderId");
                        q.setParameter("pdfdata", pdfdata);
                        q.setParameter("orderId", pqid);
                        q.executeUpdate();
                    }
                }
            } catch (OrderInvalidatedException oiex) {
                badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
                throw new OrderInvalidatedException("ERROR: Received order release rejection from photojojo via remote order release API");
            } catch (Exception ex) {
                Date poster = info.getOrder().getPostDate();
                Date now = new Date();

                if ((now.getTime() - poster.getTime()) > (1000L * 60L * 60L * 9L))  //9 hours
                {
                    badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
                    throw new Exception("ERROR: Unspecified error (" + ex.getMessage() + ") remote order release API");

                } else {
                    okToContinue = false;
                    if (!(badOrderRefNums.contains(info.getOrder().getOrderNumBarcode()))) {
                      //  Mailer.sendMail("OWD-Photojojo Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "laurel@photojojo.com", "donotreply@owd.com");
                       // Mailer.sendMail("OWD-Photojojo Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "jules@photojojo.com", "donotreply@owd.com");

                        Mailer.sendMail("OWD-Photojojo Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "photojojo.system@gmail.com", "donotreply@owd.com");
                        Mailer.sendMail("OWD-Photojojo Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "owditadmin@owd.com", "releaser@owd.com");
                        badOrderRefNums.add(info.getOrder().getOrderNumBarcode());
                    }
                }
            }
        } else if (info.getOrder().getClientFkey() == 387) {
            log.debug("***PHOTOJOJO PDF GETTER FOR INTERNAL ORDER***");
            try {
                byte[] pdfdata = RemoteOrderReleaseAPI.getOrderReleaseApprovalAndPackingSlip("https://api.photojojo.com/v1/owd/get-packing-slip", id, true);

                if (pdfdata != null) //if no exception thrown but no data received, then proceed but punt to normal template
                {
                    for (Integer pqid : printidMap.get(id)) {
                        Query q = HibernateSession.currentSession().createSQLQuery("update owd_print_queue3 set pdf_binary= :pdfdata where print_queue_id=:orderId");
                        q.setParameter("pdfdata", pdfdata);
                        q.setParameter("orderId", pqid);
                        q.executeUpdate();
                    }
                }
            } catch (OrderInvalidatedException oiex) {

                badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
//use OWD packing slip
            } catch (Exception ex) {
                badOrderRefNums.remove(info.getOrder().getOrderNumBarcode());
                //use OWD packing slip
                /* //if order has been less than an hour since posted, retry
                //otherwise reject
                Date poster = info.getOrder().getPostDate();
                Date now = new Date();

                if ((now.getTime() - poster.getTime()) > 5400L)
                {
                    //reject order
                    badOrderRefNums.remove(info.getOrder().getOrderRefnum());

                    throw new Exception("ERROR: Unspecified error (" + ex.getMessage() + ") remote order release API");

                } else
                {
                    okToContinue = false;
                    if (!(badOrderRefNums.contains(info.getOrder().getOrderRefnum())))
                    {
                        Mailer.sendMail("OWD-Photojojo Order Release API Error (" + info.getOrder().getOrderRefnum() + ")", ex.getMessage(), "susan@photojojo.com", "donotreply@owd.com");
                        badOrderRefNums.add(info.getOrder().getOrderRefnum());
                    }
                }*/

            }
        }
        return okToContinue;
    }

    public static void sendToPrintQueue(Integer id, OwdOrderShipInfo info, Map<Integer, List<Integer>> printidMap)
            throws Exception {
        for (Integer pqid : printidMap.get(id)) {
            PreparedStatement ps = HibernateSession.getPreparedStatement("update owd_print_queue3 set isVerified=1, printer_name=dbo.getSlaPrinterName(order_id,getdate()) where print_queue_id=?");
            ps.setInt(1, pqid);
            //
            //
            ps.execute();
            ps.close();
            try {
                ps = HibernateSession.getPreparedStatement("exec insertOrderFingerprint " + id);
                ps.execute();

            } catch (Exception e) {
             //   Mailer.sendMail("Error fingerprint", e.getMessage(), "dnickels@owd.com", "dnickels@owd.com");
                e.printStackTrace();
            }
            HibUtils.commit(HibernateSession.currentSession());
        }
    }

    public static void rejectOrder(Integer id, String message, Map<Integer, List<Integer>> printidMap) throws Exception {

        String token = CasetrackerAPI.getLoginToken();
        OrderStatus status = new OrderStatus(id + "");
        log.debug("reject order " + id + " with message " + message);
        try {

            if (!status.is_unpicked) {
                status.unpickOrder();
                Event.addOrderEvent(id, Event.kEventTypeHandling, "Pick cleared as part of unposting process by " + "Address Verification Check", "Address Verification Check");
            } else {
                // throw new Exception("Order is not currently in posted status; cannot unpost this order.");
            }
        } catch (Exception exx) {
            exx.printStackTrace();
        } finally {

        }
        try {
            if (status.is_posted) {
                status.unpostOrder();
                Event.addOrderEvent(id, Event.kEventTypeHandling, "Order unposted after posting by " + "Address Verification Check", "Address Verification Check");
            } else {
                throw new Exception("Order is not currently in posted status; cannot unpost this order.");
            }
        } catch (Exception exx) {
            exx.printStackTrace();
        } finally {

        }

        try {
            for (Integer pqid : printidMap.get(id)) {
                Query q = HibernateSession.currentSession().createSQLQuery("delete from owd_print_queue3 where print_queue_id=" + pqid);

                q.executeUpdate();
                HibUtils.commit(HibernateSession.currentSession());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();
        sb.append(message);
        log.debug("adding event " + sb.toString());
        //add event
        Event.addOrderEvent(id, Event.kEventTypeHandling, sb.toString(), "ORDER RELEASE CHECK");

        EventFeeds.reportOrderHeld(id, Integer.parseInt(status.client_id),
                EventFeeds.kAddressVerifySourceType, sb.toString());

        log.debug("event added " + sb.toString());
        //notify project mailbox
        if (message.contains("Shipping Address") && "146".equals(status.client_id)) {
            Mailer.sendMail("Unshippable Address/Ship Method for " + status.orderReference + " (OWD Ref:" + status.OWDorderReference + ")", sb.toString(), "apialerts@vitaminhealthbrands.com", "donotreply@owd.com");
        }

//        if (message.contains("Shipping Address") && "576".equals(status.client_id)) {
//            Mailer.sendMail("Unshippable Address/Ship Method for " + status.orderReference + " (OWD Ref:" + status.OWDorderReference + ")", sb.toString(), "care_team@hvmn.com", "donotreply@owd.com");
//        }

        CasetrackerAPI.createNewCaseForClient((message.contains("Shipping Address") ? "Unshippable Address for " : "Payment Capture Error for ") + status.OWDorderReference + " (" + status.orderReference + ")",
                sb.toString(), status.client_id, null, token);
        //   Mailer.sendMail((message.contains("Shipping Address") ? "Unshippable Address for " : "Payment Capture Error for ") + status.OWDorderReference + " (" + status.orderReference + ")", sb.toString(), getClientSupportBoxEmail(HibernateSession.currentSession(), Integer.parseInt(status.client_id)), "owditadmin@owd.com");
    }

    public static void captureFundsIfNeeded(OwdOrder order) throws Exception {
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;
        log.debug("in capture funds");
        try {
            if (order.getClient().getChargeOnShip() == 1) {
                //check if charge on ship client, and if order is chargeable via CC
                log.debug("is cos client");
                //update order posting process to recalculate tax and subtotal for order and backorder
                //exc sp_updateordershiptotals @orderid;
                String esql = "exec sp_updateordershiptotals " + order.getOrderId();
                cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
                stmt = cxn.createStatement();
                stmt.executeUpdate(esql);
                cxn.commit();
                stmt.close();
                float chargeAmount = 0.00f;

                if (order.getCcType().equalsIgnoreCase("CK") ||  order.getOrderType().contains("Mail Order") || (order.getClientFkey() == 463 && ((order.getCcType().equalsIgnoreCase("FREE")
                        || order.getCcType().equalsIgnoreCase("CK"))))) {
                    //let it through, can't check mail orders
                } else {
                    stmt = cxn.createStatement();
                    rs = stmt.executeQuery("select v.*,round(v.order_total-[charged cc]-[credit cc],2) as 'charge' " +
                            "from vw_order_cc_summary v where order_id=" + order.getOrderId());
                    if (rs.next()) {
                        chargeAmount = rs.getFloat("charge");
                        log.debug("got charge amount " + chargeAmount);
                    } else {
                        throw new Exception("ERROR: Unable to obtain amount to charge or credit");
                    }

                    rs.close();
                    stmt.close();


                    List oldTransList = FinancialTransaction.getTransactionsForCustomer(cxn, order.getCustomerNum() + "", true);
                    if (oldTransList.size() < 1) {
                        if (chargeAmount > 0.00) {
                            log.debug("got oldtranslist size " + oldTransList.size() + " for " + order.getCustomerNum());

                            FinancialTransaction trans = new FinancialTransaction("0", order.getOrderId() + "", "0", "", "OWDCAPTURE", "OWDCAPTURE", "",
                                    chargeAmount, "", FinancialTransaction.TRANS_NEW,
                                    FinancialTransaction.TRANS_CC + "", 0, 0, FinancialTransaction.transTypeAuthCaptureRequest + "",
                                    order.getBillLastName(),
                                    order.getBillFirstName(),
                                    order.getBillAddressOne(), "",
                                    order.getBillZip(), "",
                                    "", "", "", "", "",
                                    order.getOrderRefnum(), "0", "0", "", "", "", "", "",
                                    OWDUtilities.getExpDateStr(order.getCcExpMon(), order.getCcExpYear() + 1), order.getCcNum(), "", "", "", "", "", "", 0, 0,
                                    OWDUtilities.getSQLDateTimeForToday());


                            log.debug("charging order");
                            trans.chargeCCOwdClient(order.getClient(), false);
                            trans.dbsave(cxn);

                            cxn.commit();

                            if (!trans.status.equals("Approved")) {
                                List oldBadTransList = FinancialTransaction.getTransactionsForCustomer(cxn, order.getCustomerNum() + "");
                                if (oldBadTransList.size() > 0) {
                                    throw new Exception("ERROR: Could not charge order for shipping; no previous approved CC transactions found. ");
                                } else {
                                    //not a cc order, ignore and continue
                                    if (order.getOrderType().equals("Mail Order")) {
                                        //let it through, can't check mail orders
                                    } else {
                                        throw new Exception("ERROR: No CC information available to use for transaction. Enter a manual CC transaction and re-release. ");
                                    }
                                }
                            }
                        } else if (chargeAmount < 0.00) {
                            throw new Exception("ERROR: Order has been overpaid. Please credit appropriately before re-releasing.");
                        }
                    } else {
                        log.debug("getting ft source");
                        FinancialTransaction ft = (FinancialTransaction) oldTransList.get(0);

                        if (chargeAmount > 0.00) {
                            FinancialTransaction trans = new FinancialTransaction("0", order.getOrderId() + "", "0", "", "OWDCAPTURE", "OWDCAPTURE", "",
                                    chargeAmount, "", FinancialTransaction.TRANS_NEW,
                                    FinancialTransaction.TRANS_CC + "", 0, 0, FinancialTransaction.transTypeAuthCaptureRequest + "",
                                    order.getBillLastName(),
                                    order.getBillFirstName(),
                                    order.getBillAddressOne(), "",
                                    order.getBillZip(), "",
                                    "", "", "", "", "",
                                    order.getOrderRefnum(), "0", "0", "", "", "", "", "",
                                    ft.cc_exp, OWDUtilities.decryptData(ft.cc_number), "", "", "", "", "", "", 0, 0,
                                    OWDUtilities.getSQLDateTimeForToday());


                            log.debug("charging order");
                            trans.chargeCCOwdClient(order.getClient(), false);
                            trans.dbsave(cxn);

                            cxn.commit();

                            if (!trans.status.equals("Approved")) {
                                trans = new FinancialTransaction("0", order.getOrderId() + "", "0", "", "OWDCAPTURE", "OWDCAPTURE", "",
                                        chargeAmount, "", FinancialTransaction.TRANS_NEW,
                                        FinancialTransaction.TRANS_CC + "", 0, 0, FinancialTransaction.transTypeAuthCaptureRequest + "",
                                        order.getBillLastName(),
                                        order.getBillFirstName(),
                                        order.getBillAddressOne(), "",
                                        order.getBillZip(), "",
                                        "", "", "", "", "",
                                        order.getOrderRefnum(), "0", "0", "", "", "", "", "",
                                        OWDUtilities.getExpDateStr(order.getCcExpMon(), order.getCcExpYear()), order.getCcNum(), "", "", "", "", "", "", 0, 0,
                                        OWDUtilities.getSQLDateTimeForToday());


                                log.debug("charging order");
                                trans.chargeCCOwdClient(order.getClient(), false);
                                trans.dbsave(cxn);

                                cxn.commit();

                                if (!trans.status.equals("Approved")) {
                                    throw new Exception("ERROR: Could not charge order for shipping; failed to confirm successful charge on ship ");

                                }
                            }
                        } else if (chargeAmount < 0.00) {
                            throw new Exception("ERROR: Order has been overpaid. Please credit appropriately before re-releasing.");
                        }

                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                cxn.rollback();
            } catch (Exception exc) {
            }

            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                stmt.close();
            } catch (Exception exc) {
            }

        }
    }

    public static String getClientSupportBoxEmail(Session
                                                          sess, int clientID) throws Exception {
        log.debug("getting email");
        String sql = "select top 1 ISNULL(address,'casetracker@owd.com') as 'address' from  vw_client_mailboxes where cid=" + clientID + ";";
        ResultSet rs = HibernateSession.getResultSet(sess, sql);
        String address = "customerservice@owd.com";
        if (rs.next()) {
            address = rs.getString(1);
        }

        rs.close();
        return address;
    }

    public static String getClientSupportProjectID(Session
                                                           sess, int clientID) throws Exception {
        log.debug("getting email");
        String sql = "select top 1 ISNULL(pid,16) as 'pid' from  vw_client_projects where cid=" + clientID + "; ";
        ResultSet rs = HibernateSession.getResultSet(sess, sql);
        String address = "16"; //Inbox
        if (rs.next()) {
            address = "" + rs.getInt(1);
        }

        rs.close();
        return address;
    }

/*
    static OwdOrderShipInfo handleSneakpeeqMultiLocations(OwdOrderShipInfo info) throws Exception {


        int originalOrderId = info.getOrder().getOrderId();
        String originalOrderNum = info.getOrder().getOrderNum();

      //  if(info.getOrder().getFacility)
        String newOrderNum = ConnectionManager.getNextID("Order");


        Map<String, Integer> facilityLineMap = new TreeMap<String, Integer>();

        String lineCountQuery = "select ISNULL(i.default_facility_code,'DC1') as default_facility_code,count(*) as linecnt from owd_inventory i join owd_line_item l join owd_order o on l.order_fkey=order_id on i.inventory_id=l.inventory_id\n" +
                "where order_id=? and quantity_actual>0\n" +
                "group by default_facility_code";

        List<Object[]> data = HibernateSession.currentSession().createSQLQuery(lineCountQuery).setInteger(0, info.getOrder().getOrderId()).list();

        for (Object[] facilityCount : data) {
            facilityLineMap.put(facilityCount[0].toString(), Integer.parseInt(facilityCount[1].toString()));

        }


        if (facilityLineMap.keySet().size() == 1) {
            info.getOrder().setFacilityCode((String) (facilityLineMap.keySet().toArray()[0]));
            HibernateSession.currentSession().saveOrUpdate(info.getOrder());
            HibUtils.commit(HibernateSession.currentSession());

        } else {
            //we assume only two facilities for now

            //pick a facility for this order

            info.getOrder().setFacilityCode((String) (facilityLineMap.keySet().toArray()[0]));
            HibernateSession.currentSession().saveOrUpdate(info.getOrder());
            HibUtils.commit(HibernateSession.currentSession());

            //duplicate order to new order


            HibernateSession.currentSession().evict(info.getOrder());
            HibernateSession.currentSession().evict(info);

            Statement stmt = null;
            Connection cxn = null;

            try {
                String esql = "exec split_order " + originalOrderId + ",\'" + originalOrderNum + "\',\'" + newOrderNum + "\',\'" + facilityLineMap.keySet().toArray()[1] + "\'   ";

                cxn = ConnectionManager.getConnection();
                stmt = cxn.createStatement();

                boolean hasResultSet = stmt.execute(esql);

                stmt.close();

                cxn.commit();

                cxn.close();

            } catch (Exception ex) {
                throw ex;

            } finally {

                try {
                    stmt.close();
                } catch (Exception ex) {
                }

                try {
                    ConnectionManager.freeConnection(cxn);
                } catch (Exception ex) {
                }

            }
            //transfer qty_actual>0 line items from old to new order by matching facility


            //insert new order into print queue with notes
            Event.addOrderEvent(originalOrderId, Event.kEventTypeHandling, "Split location order; split items assigned to OWD order reference " + newOrderNum, "System");


        }

        HibUtils.commit(HibernateSession.currentSession());


        List<OwdOrderShipInfo> infoList = (List<OwdOrderShipInfo>) HibernateSession.currentSession().createQuery("from OwdOrderShipInfo where order.id=?").setInteger(0, originalOrderId).list();

        return infoList.get(0);


    }
*/

    static List<String> meRequiredSkus = new ArrayList<String>();
    static List<String> meBottleSkus = new ArrayList<String>();
    static List<String> meBoxSkus = new ArrayList<String>();

    static List<String> spRequiredSkus = new ArrayList<String>();
    static List<String> spBoxSkus = new ArrayList<String>();


    static {
        meBottleSkus.add("BRO-B60");
        meBottleSkus.add("TEA-B60");
        meBottleSkus.add("FLEX-B60");
        meBottleSkus.add("BE-B60");
        meBottleSkus.add("RYR-B60");
        meBottleSkus.add("NAT-B60");
        meBottleSkus.add("TR01-B60");
        meBottleSkus.add("PRC-B60");
        meBottleSkus.add("LWP-B60");
        meBottleSkus.add("2XR-B60");
        meBottleSkus.add("NBO-B60");
        meBottleSkus.add("MD3-B60");
        meBottleSkus.add("ESF-B30");
        meBottleSkus.add("TF-B90");

        meBoxSkus.add("BOX-16B");
        meBoxSkus.add("BOX-M");
        meBoxSkus.add("BOX-L");
        meBoxSkus.add("BOX-XL");
        meBoxSkus.add("BOX-S");
      //  meBoxSkus.add("NUM-STK");


        meRequiredSkus.add("MD3-B60");

        spRequiredSkus.add("P70053");
        spRequiredSkus.add("P70055");
        spRequiredSkus.add("P70057");
        spRequiredSkus.add("P70059");
        spRequiredSkus.add("P70061");

        spBoxSkus.add("P70329");
    }


    static OwdOrderShipInfo updateSneakpeeqBoxLines(OwdOrderShipInfo shipInfo) throws Exception {
        OrderStatus order = new OrderStatus("" + shipInfo.getOrder().getOrderId());
        boolean modified = false;
        List<String> removeIds = new ArrayList<String>();
        boolean skipit = true;




        if (order != null) {
            //get bottle count
            for (LineItem line : ((Vector<LineItem>) order.items)) {

                if (spBoxSkus.contains(line.client_part_no)) {
                    removeIds.add(line.line_item_id);
                }

                if (spRequiredSkus.contains(line.client_part_no)) {
                    skipit = false;
                }

            }

            if (!skipit) {
                //remove any old boxes
                if (removeIds.size() > 0) {
                    modified = true;
                    for (String id : removeIds) {
                        OwdLineItem item = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class, Integer.parseInt(id));
                        item.setQuantityActual(0);
                        HibernateSession.currentSession().saveOrUpdate(item);
                        HibUtils.commit(HibernateSession.currentSession());

                        HibernateSession.currentSession().delete(item);

                    }
                    HibUtils.commit(HibernateSession.currentSession());
                }



                OwdOrder ord = shipInfo.getOrder();
                    addPackingItemToExistingOrder(ord, 190014,"Box",1);
                    modified = true;


                HibUtils.commit(HibernateSession.currentSession());
            }

        }

        if (modified) {
            int shipInfoId = shipInfo.getOrderShipInfoId();
            HibernateSession.currentSession().evict(shipInfo.getOrder());
            HibernateSession.currentSession().evict(shipInfo);

            return (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, shipInfoId);

        } else {
            return shipInfo;
        }
    }

    static public OwdOrderShipInfo uffpdateMarineEssentialsBoxLines(OwdOrderShipInfo shipInfo) throws Exception {





        OrderStatus order = new OrderStatus("" + shipInfo.getOrder().getOrderId());
        boolean modified = false;
        int bottleCount = 0;
        List<String> removeIds = new ArrayList<String>();
        boolean skipit = true;

        for (LineItem line : ((Vector<LineItem>) order.items)) {

            if(line.description.equals("Included") && line.client_part_no.equals("BRO-B60"))
            {
                removeIds.add(line.line_item_id);
            }

        }

        int  broUnits = order.getRequestQuantityForSKU("WB-4B-SUB");
        broUnits += order.getRequestQuantityForSKU("WB-4B-UP");

        if (broUnits>0 && removeIds.size() > 0) {
            modified = true;
            for (String id : removeIds) {
                OwdLineItem item = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class, Integer.parseInt(id));
                item.setQuantityActual(0);
                HibernateSession.currentSession().saveOrUpdate(item);
                HibUtils.commit(HibernateSession.currentSession());

                HibernateSession.currentSession().delete(item);

            }
            addPackingItemToExistingOrder(shipInfo.getOrder(),187864,"Body Ready Omega 1 Bottle - 60 pills Included",broUnits);

            HibUtils.commit(HibernateSession.currentSession());
        }







        //Bypassed per client for time being
        if (modified) {
            int shipInfoId = shipInfo.getOrderShipInfoId();
            HibernateSession.currentSession().evict(shipInfo.getOrder());
            HibernateSession.currentSession().evict(shipInfo);

            return (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, shipInfoId);

        } else {
            return shipInfo;
        }


    }

    public static void addPackingItemToExistingOrder(OwdOrder ord, int itemId, String description, int quantity) throws Exception {
        OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, itemId);
        if (OrderUtilities.getAvailableInventory(""+inv.getInventoryId(), FacilitiesManager.getFacilityForCode(ord.getFacilityCode()).getId()) > (quantity+2)) {
            OwdLineItem newline = new OwdLineItem();
            newline.setInventoryNum(inv.getInventoryNum());
            newline.setDescription(description);
            newline.setIsInsert(0);
            newline.setItemSize("");
            newline.setItemColor("");
            newline.setQuantityRequest(quantity);
            newline.setQuantityBack(0);
            newline.setQuantityActual(quantity);
            newline.setOrder(ord);
            ord.getLineitems().add(newline);
            newline.setPrice(new BigDecimal(0.00));
            newline.setTotalPrice(new BigDecimal(0.00));
            newline.setOwdInventory(inv);

            HibernateSession.currentSession().save(newline);
            HibernateSession.currentSession().saveOrUpdate(ord);
        }

    }

    public static candleCount getCandleCountForOrder(OwdOrder order)
    {
        int count = 0;
        int totalCount = 0;
        int luxe = 0;
        int diamond = 0;
        for (OwdLineItem line:order.getLineitems())
        {
            log.debug(line.getOwdInventory().getDescription());
            if(                          (line.getOwdInventory().getWeightLbs()>2.0 && line.getOwdInventory().getWeightLbs()<3.0)

                    ||
                    (line.getOwdInventory().getDescription().toUpperCase().contains("CANDLE") && line.getOwdInventory().getWeightLbs()>=1.0)
                    )
            {
                if(line.getOwdInventory().getDescription().toUpperCase().contains("LUXE")){
                    luxe += line.getQuantityActual();
                }else{
                    diamond += line.getQuantityActual();
                }
                count += line.getQuantityActual();
                totalCount += line.getQuantityActual();
            } else{
                //check auto inventory. These are items on the order that are not packed or shipped
                if(line.getOwdInventory().getIsAutoInventory()==0){


                    //ignore sticker code
                    if(!line.getInventoryNum().equals("P161583")){
                        //ignore sku's less than .03 these are gift cards etc that can fit in and not throw off weights
                        if(line.getOwdInventory().getWeightLbs()>0 & line.getOwdInventory().getWeightLbs()<0.05){
                            log.debug("Light sku");
                        }  else{
                            totalCount+=line.getQuantityActual();
                        }
                    }
                }
            }
        }
        log.debug("candle count "+count);
        log.debug("total line count "+totalCount);
        candleCount cCount = new candleCount();
        if (count == totalCount){
            cCount.setCount(count);
            if(luxe  >0){
                if(diamond>0){
                    cCount.setType("mixed");
                }else{
                    cCount.setType("luxe");
                }
            }else{
                cCount.setType("diamond");
            }
            return cCount;
        }
        cCount.setCount(0);
        return cCount;
    }

    public static float getNonCandleWeightForOrder(OwdOrder order)
    {
        float totalWeight = 0;
        for (OwdLineItem line:order.getLineitems())
        {
            if(!((line.getOwdInventory().getWeightLbs()>2.0 && line.getOwdInventory().getWeightLbs()<3.0)

                ||
            (line.getOwdInventory().getDescription().toUpperCase().contains("CANDLE") && line.getOwdInventory().getWeightLbs()>=1.0)
                    ))
           {
                //check auto inventory. These are items on the order that are not packed or shipped
                if(line.getOwdInventory().getIsAutoInventory()==0){
                    //ignore sticker code
                    if(!line.getInventoryNum().equals("P161583")){
                        //ignore sku's less than .03 these are gift cards etc that can fit in and not throw off weights
                        if(!(line.getOwdInventory().getWeightLbs()>0 & line.getOwdInventory().getWeightLbs()<0.05)){
                            totalWeight+= line.getOwdInventory().getWeightLbs()*line.getQuantityActual();
                        }
                    }
                }
            }
        }
        return totalWeight;
    }


    public static void setPredictedPack(OwdOrder order)
    {

        try {

            PreparedStatement stmt = HibernateSession.getPreparedStatement("delete from owd_order_packs where order_fkey=" + order.getOrderId());
            stmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            stmt.close();

            if (
                    (order.getClientFkey() != 489)
                            || (!("diamondcandles".equals(order.getGroupName()) || "G_holliconShollicon".equals(order.getGroupName())))
                    ) {
                log.debug("unqualified for candle pack, try default");
                PreparedStatement pspack = HibernateSession.getPreparedStatement("exec sp_setpackfromfingerprint "+order.getOrderId());
                pspack.executeUpdate();
                HibUtils.commit(HibernateSession.currentSession());

                pspack.close();
            }  else {
                //Symphony candles only


                candleCount cCount = getCandleCountForOrder(order);
                int candleCount = cCount.getCount();
                float extraweight = getNonCandleWeightForOrder(order); //small insert items
                if ((candleCount > 0 && cCount.getType().equals("diamond"))||(candleCount>0 && candleCount <3 && cCount.getType().equals("luxe"))) {
                    float weight = 0.00f;
                    int boxId = 0;
                    String boxName = "";

                    switch (candleCount) {
                        case 1:
                            if(cCount.getType().equals("luxe")){
                                weight = 2.3f+extraweight;
                            }else{
                                weight = 2.93f+extraweight;
                            }
                            break;
                        case 2:
                            if(cCount.getType().equals("luxe")){
                                weight = 4.11f+extraweight;
                            }else{
                                weight = 5.93f+extraweight;
                            }

                            break;
                        case 3:
                            weight = 8.93f+extraweight;
                            break;
                        case 4:
                            weight = 11.93f+extraweight;
                            break;
                        case 5:
                            weight = 14.03f+extraweight;
                            break;
                        case 6:
                            weight = 16.93f+extraweight;
                            break;
                        case 7:
                            weight = 19.93f+extraweight;
                            break;
                        case 8:
                            weight = 22.93f+extraweight;
                            break;
                        default:
                            throw new Exception("More candles than we can handle do something else");
                    }


                    if (order.getFacilityCode().equals("DC7")) {
                        switch (candleCount) {
                            case 1:
                                if (cCount.getType().equals("luxe")) {
                                    boxId = 475;
                                    boxName = "LUXE955";
                                 }else{
                                    boxId = 338;
                                    boxName = "DC7-SP-1064";

                                }

                                break;
                            case 2:
                                if (cCount.getType().equals("luxe")) {
                                    boxId = 475;
                                    boxName = "LUXE955";
                                }else {
                                    boxId = 339;
                                    boxName = "DC7-SP-10124";
                                }
                                break;
                            case 3:
                                boxId = 339;
                                boxName = "DC7-SP-10124";
                                break;
                            case 4:
                                boxId = 340;
                                boxName = "DC7-SP-10184";
                                break;
                            case 5:
                            case 6:
                                boxId = 341;
                                boxName = "DC7-SP-10244";
                                break;
                            case 7:
                                boxId = 342;
                                boxName = "DC7-SP-10304";

                                break;
                            case 8:
                                boxId = 343;
                                boxName = "DC7-SP-10364";
                                break;
                            default:
                                throw new Exception("More candles than we can handle do something else");
                        }


                    } else if (order.getFacilityCode().equals("DC6")) {
                        switch (candleCount) {
                            case 1:
                                boxId = 344;
                                boxName = "DC6-SP-1064";
                                break;
                            case 2:
                            case 3:
                                boxId = 345;
                                boxName = "DC6-SP-10124";
                                break;
                            case 4:
                                boxId = 346;
                                boxName = "DC6-SP-10184";
                                break;
                            case 5:
                            case 6:
                                boxId = 347;
                                boxName = "DC6-SP-10244";
                                break;
                            case 7:
                                boxId = 348;
                                boxName = "DC6-SP-10304";

                                break;
                            case 8:
                                boxId = 349;
                                boxName = "DC6-SP-10364";
                                break;
                            default:
                                throw new Exception("More candles than we can handle do something else");
                        }
                    } else {
                        switch (candleCount) {
                            case 1:
                                boxId = 231;
                                boxName = "SP-1064";
                                break;
                            case 2:
                            case 3:
                                boxId = 232;
                                boxName = "SP-10124";
                                break;
                            case 4:
                                boxId = 233;
                                boxName = "SP-10184";
                                break;
                            case 5:
                            case 6:
                                boxId = 234;
                                boxName = "SP-10244";
                                break;
                            case 7:
                                boxId = 235;
                                boxName = "SP-10304";
                                break;
                            case 8:
                                boxId = 236;
                                boxName = "SP-10364";
                                break;
                            default:
                                throw new Exception("More candles than we can handle do something else");


                        }


                    }

                    stmt = HibernateSession.getPreparedStatement("insert into owd_order_packs (box_name,box_fkey,weight_lbs,order_fkey) VALUES (?,?,?,?);");
                    stmt.setString(1, boxName);
                    stmt.setInt(2, boxId);
                    stmt.setFloat(3, weight);
                    stmt.setInt(4, order.getOrderId());

                    stmt.executeUpdate();
                    HibUtils.commit(HibernateSession.currentSession());

                    stmt.close();
                }
            }

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }


    static public  OwdOrderShipInfo updateMarineEssentialsBoxLines(OwdOrderShipInfo shipInfo) throws Exception {
        OrderStatus order = new OrderStatus("" + shipInfo.getOrder().getOrderId());
        boolean modified = false;
        int bottleCount = 0;
        List<String> removeIds = new ArrayList<String>();
        boolean skipit = true;

        if (order != null) {
            //get bottle count
            for (LineItem line : ((Vector<LineItem>) order.items)) {
                if (meBottleSkus.contains(line.client_part_no)) {
                    bottleCount += line.quantity_actual;
                }
                if (meBoxSkus.contains(line.client_part_no)) {
                    removeIds.add(line.line_item_id);
                }

              /*  if (meRequiredSkus.contains(line.client_part_no)) {
                    skipit = false;
                }
                */

            }

            if (bottleCount>0) {
                //remove any old boxes
                if (removeIds.size() > 0) {
                    modified = true;
                    for (String id : removeIds) {
                        OwdLineItem item = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class, Integer.parseInt(id));
                        item.setQuantityActual(0);
                        HibernateSession.currentSession().saveOrUpdate(item);
                        HibUtils.commit(HibernateSession.currentSession());

                        HibernateSession.currentSession().delete(item);

                    }
                    HibUtils.commit(HibernateSession.currentSession());
                }

            /*
            If an order contains 1, 2 or 3 bottles of any combination of the bottles I listed, order would call for BOX-S.

                If an order contains 4, 5 or 6 bottles of any combination of the bottles I listed, order would call for BOX-M.

                If an order contains 7 or more bottles we will package them as we have been until the Box-L and the Box-XL arrive at a later date.
                 At the time, order for 7, 8, 9, 10, 11 or 12 will call for Box-L and all orders that contain 13, 14, 15, 16, 17, 18, 19 & 20 bottles will call for Box-XL.  I am unsure of the expected date for the arrival of Box-L and Box-XL.

                If you have any further questions, please let me know.   */

                OwdOrder ord = shipInfo.getOrder();

                log.debug("bottleCount=" + bottleCount);
                if (bottleCount >= 1 && bottleCount <= 3) {
                    addPackingItemToExistingOrder(ord, 190014,"Box",1);
                    //   addPackingItemToExistingOrder(ord, 199353,"Sticker");
                    modified = true;


                } else if (bottleCount >= 4 && bottleCount <= 6) {
                    addPackingItemToExistingOrder(ord, 190015,"Box",1);
                    //    addPackingItemToExistingOrder(ord, 199353,"Sticker");
                    modified = true;

                } else if (bottleCount >= 7 && bottleCount <= 12) {
                    addPackingItemToExistingOrder(ord, 190019,"Box",1);
                    //     addPackingItemToExistingOrder(ord, 199353,"Sticker");
                    modified = true;

                } else if (bottleCount >= 13 && bottleCount <= 20) {
                    addPackingItemToExistingOrder(ord, 190020,"Box",1);
                    //     addPackingItemToExistingOrder(ord, 199353,"Sticker");
                    modified = true;

                }

                HibUtils.commit(HibernateSession.currentSession());
            }

        }

        if (modified) {
            int shipInfoId = shipInfo.getOrderShipInfoId();
            HibernateSession.currentSession().evict(shipInfo.getOrder());
            HibernateSession.currentSession().evict(shipInfo);

            return (OwdOrderShipInfo) HibernateSession.currentSession().load(OwdOrderShipInfo.class, shipInfoId);

        } else {
            return shipInfo;
        }
    }

}
