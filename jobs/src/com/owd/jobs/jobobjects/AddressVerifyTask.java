package com.owd.jobs.jobobjects;

import com.owd.core.business.order.Event;
import com.owd.core.managers.AddressManager;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipInfo;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.*;
import com.owd.jobs.AddressVerifyPrintQueueJob;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by stewartbuskirk1 on 1/16/15.
 */
public class AddressVerifyTask implements Runnable {
private final static Logger log =  LogManager.getLogger();
    private int orderId;
    private Map<Integer, List<Integer>> printidMap = new TreeMap<Integer, List<Integer>>();

    public AddressVerifyTask(int data, List<Integer> queueIdList) {
        this.orderId = data;
        this.printidMap.put(this.orderId,queueIdList);
    }

    public void run() {
        try {
            log.debug("verifying id " + orderId);

            //set proper facility code for order record - currently one per client
            PreparedStatement locstmt = HibernateSession.getPreparedStatement("sp_updateOrderFacility " + orderId + ";");
            locstmt.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
            locstmt.close();

            List<OwdOrderShipInfo> infoList;
            infoList = (List<OwdOrderShipInfo>) HibernateSession.currentSession().createQuery("from OwdOrderShipInfo where order.id=?").setInteger(0, orderId).list();
            if (infoList.size() == 1) //should always be true
            {
                OwdOrderShipInfo info = infoList.get(0);

                if (info != null) {
                    if (info.getOrder().getClientFkey() == 494) //marine essentials box add logic
                    {

                        if(!(info.getOrder().getPoNum().toUpperCase().contains("AMAZON") || info.getOrder().getOrderType().toUpperCase().contains("EBAY") || info.getOrder().getOrderType().equals("AMAZONUK") || info.getOrder().getOrderType().equals("AMAZONFR") || info.getOrder().getOrderType().equals("AMAZONDE"))
                                && (!(info.getCarrService().startsWith("USPS")))
                                && (!(info.getShipCountry().equalsIgnoreCase("USA") || info.getShipCountry().equalsIgnoreCase("UNITED STATES"))))
                        {
                            info.setCarrService("USPS Priority Mail International");
                            info.setCarrServiceRefNum("TANDATA_USPS.USPS.I_PRIORITY");
                            HibernateSession.currentSession().saveOrUpdate(info);
                        }
                        info = AddressVerifyPrintQueueJob.updateMarineEssentialsBoxLines(info);
                    }

                    AddressVerifyPrintQueueJob.switchFedexShippingToUps(orderId, info);

                    boolean okToContinue;
                    log.debug("checking order address "+Thread.currentThread().getName());
                    if (AddressVerifyPrintQueueJob.checkOrderAddress(orderId, info, printidMap)) {
                        try {
                            AddressVerifyPrintQueueJob.captureFundsIfNeeded(info.getOrder());

                            log.debug("doing release check "+Thread.currentThread().getName());

                            okToContinue = AddressVerifyPrintQueueJob.doReleaseCheck(orderId, info, printidMap);

                            if (okToContinue) {
                                if (AddressVerifyPrintQueueJob.shipVirtualOrder(orderId)) {
                                    AddressVerifyPrintQueueJob.sendToPrintQueue(orderId, info, printidMap);
                                }
                            }
                            log.debug("set predicted pack "+Thread.currentThread().getName());

                            AddressVerifyPrintQueueJob.setPredictedPack(info.getOrder());


                        } catch (Exception exr) {
                            exr.printStackTrace();
                            log.debug("*** Unable to verify or correct: " + exr.getMessage() + " for order id " + orderId);
                            HibUtils.rollback(HibernateSession.currentSession());
                            //set on hold
                            AddressVerifyPrintQueueJob.rejectOrder(orderId, exr.getMessage(), printidMap);
                        }


                        //AES ITN.
                        OwdOrder order = info.getOrder();
                        Boolean shouldBeNotifiedToCheckAES = AddressManager.doesAESRequired(order);

                        if (shouldBeNotifiedToCheckAES) {
                            Integer clientId = order.getClientFkey();
                            String notificationSubject = "AES required for the order refNum: " + order.getOrderRefnum();
                            String aesITNCheckMessage = "Please check the order and obtain AES ITN number if needed";
                            String recipient = getClientSupportBoxEmail(clientId);
                            String token = CasetrackerAPI.getLoginToken();

                            CasetrackerAPI.createNewCaseForClient(notificationSubject,aesITNCheckMessage, clientId.toString(),
                                    recipient,
                                    token);
                        }

                        String phoneNumber = order.getShipinfo().getShipPhoneNum();
                        Boolean isPhoneNumberCorrect = AddressManager.checkAndClearIncorrectShippingPhoneNumber(order);

                        if(phoneNumber.length()>0 && !isPhoneNumberCorrect) {
                            String eventMessage = "The phone number was cleared because of incorrect format: " + phoneNumber;
                            Event.addOrderEvent(order.getOrderId(), Event.kEventTypeHandling, eventMessage, "Address Verification Check");
                        }
                    }
                    // }
                }
            }

            try{HibUtils.commit(HibernateSession.currentSession());     }catch(Exception exx){exx.printStackTrace();}
        } catch (Exception ex) {
            log.debug("ADDRESSVERIFY EPIC FAIL!!!!");
            ex.printStackTrace();
        } finally {
            //log.debug("*** Unable to verify or correct: "+ex.getMessage()+" for order id "+id);
            try{HibUtils.rollback(HibernateSession.currentSession());     }catch(Exception exx){exx.printStackTrace();}
            HibernateSession.closeSession();
            HibernateFogbugzSession.closeSession();
            HibernateClientReportsSession.closeSession();
        }
    }

    public static String getClientSupportBoxEmail(int clientID) throws Exception {
        log.debug("getting email");
        String sql = "select top 1 ISNULL(address,'casetracker@owd.com') as 'address' from  vw_client_mailboxes where cid=" + clientID + ";";
        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), sql);
        String address = "customerservice@owd.com";
        if (rs.next()) {
            address = rs.getString(1);
        }

        rs.close();
        return address;
    }
}
