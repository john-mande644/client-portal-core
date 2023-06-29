package com.owd.core.business.client;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdClientPaypalData;
import com.owd.hibernate.generated.OwdClientShipInvoiceConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 13, 2004
 * Time: 3:41:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientFactory {
private final static Logger log =  LogManager.getLogger();

    public static OwdClient getNewClient() {
        OwdClient client = new OwdClient();
        client.setAddressCorrectionEmail("");
        client.setAddressOne("");
        client.setAddressTwo("");
        client.setAmEmail("");
        client.setAsnRcvEmail("");
        client.setAutoShipEmail("");
        client.setCity("");
        client.setCompanyName("");
        client.setContactName("");
        client.setCountry("USA");
        client.setCountryRefNum("UNITED_STATES");
        client.setDefaultCustomsDesc("");
        client.setFedexAcct("");
        client.setIsActive(true);
        client.setIsBackship(new Integer(0));
        client.setLastInvoiceNum(new Integer(0));
        client.setLowInventoryEmail("");
        client.setModifiedBy("");
        client.setOriginalShipCarrier("");
        client.setOriginalShipType(new Integer(0));
        client.setPartialShipCarrier("");
        client.setPartialShipType(new Integer(0));
        client.setPolicyClass("");
        client.setPpLogin("");
        client.setPpPass("");
        client.setPrimaryEmailAddress("");
        client.setPrimaryFaxNum("");
        client.setPrimaryPhoneNum("");
        client.setRetAddr1("");
        client.setRetAddr2("");
        client.setRowIsLocked(false);
        client.setScheduledDelay(0);
        client.setShipEmail(new Integer(0));
        client.setShipEmailBcc("");
        client.setShipEmailCc("");
        client.setShipEmailFtr("");
        client.setShipId("");
        client.setShipIdPassword("");
        client.setShipInvoiceReqd(false);
        client.setShipPackReqd(true);
        client.setShipperSymbol("");
        client.setShipPickReqd(false);
        client.setShipPriceonslipReqd(true);
        client.setState("");
        client.setTellYahoo(new Integer(0));
        client.setUpsAcct("");
        client.setUrlString("");
        client.setUsedcFirstclass(new Integer(0));
        client.setYahooPass("");
        client.setZip("");
        client.setIsShippingOnHold(new Integer("0"));
        client.setPpProc("");
        client.setUnbilledBilling(new Double(0.00));
        client.setUnbilledShipping(new Double(0.00));
        client.setBillingBalance(new Double(0.00));
        client.setShippingBalance(new Double(0.00));
        client.setDefaultFacilityCode("DC1");
        client.setBulkyPackCubicFeetDefault(0.0f);
        client.setBulkyPickCubicFeetDefault(0.0f);
        client.setBulkyPackWeightDefault(0.0f);
        client.setBulkyPickWeightDefault(0.0f);
        client.setBulkyPickLongDefault(BigDecimal.ZERO);
        client.setBulkyPickMedianDefault(BigDecimal.ZERO);
        client.setBulkyPickShortDefault(BigDecimal.ZERO);

        client.setBulkyPackLongDefault(BigDecimal.ZERO);
        client.setBulkyPackMedianDefault(BigDecimal.ZERO);
        client.setBulkyPackShortDefault(BigDecimal.ZERO);
        client.setUspsOtherPackFee(BigDecimal.ZERO);
        client.setUspsPriorityExpressPackFee(BigDecimal.ZERO);
        client.setPassportFee(BigDecimal.ZERO);
        client.setDimFactor(0);
        client.setIntlDimFactor(0);

      //  client.setPrintDelayHours(0);
        
        if(client.getShipInvoiceConfig()==null)
        {
            OwdClientShipInvoiceConfig config = getNewInvoiceConfig();
            config.setClient(client);
            client.setShipInvoiceConfig(config);




        }
          if(client.getPaypalData()==null)
        {
            OwdClientPaypalData paypal = getNewPaypalData();
            paypal.setClient(client);
            client.setPaypalData(paypal);


        }

      
        return client;
    }

    public static OwdClientPaypalData getNewPaypalData()
    {
            OwdClientPaypalData paypal = new OwdClientPaypalData();
            paypal.setPaypalSignature("");
            paypal.setPaypalApiUsername("");
            paypal.setPaypalApiPassword("");
            paypal.setUsePaypal(0);

        return paypal;
    }
    public static OwdClientShipInvoiceConfig getNewInvoiceConfig()
    {
              OwdClientShipInvoiceConfig config = new OwdClientShipInvoiceConfig();
            config.setInvoiceAmount(new BigDecimal(0.00));
            config.setInvoiceAmountType("BALANCE");
            config.setInvoiceEmails("");
            config.setInvoiceFlag(new Integer(0));
            config.setInvoiceToAddress("");
            config.setInvoiceTriggerLevel(new BigDecimal(0.00));
            config.setInvoiceTriggerType("WEEKLY");
            config.setNotifyEmails("");
            config.setNotifyFlag(new Integer(0));
             return config;
    }

    public static OwdClient getClientFromClientID(Integer id) throws Exception {
        return getClientFromClientID(id,0,true);
    }
    public static OwdClient getClientFromClientID(Integer id, int clientID, boolean isInternal) throws Exception {
        try {

            Session sess = HibernateSession.currentSession();
            OwdClient client = (OwdClient) sess.load(OwdClient.class, id);
            if (client == null) throw new Exception("Client record not found for ID " + id);
            if (!isInternal && client.getClientId().intValue() != clientID) throw new Exception("Requested client record did not match current client context");
            //init collection


            return client;
        } catch (Exception ex) {

            //ex.printStackTrace();
            throw ex;
        } finally {

         //   // HibernateSession.closeSession();
        }


    }

     public static OwdClient getClientFromIntacctID(String intacctID,Session sess) throws Exception {
        try {

            List clients = sess.createQuery("from OwdClient where shipper_symbol='"+intacctID+"'").list();

            if (clients.size()<1) throw new Exception("Client record not found for Intacct ID " + intacctID);

            OwdClient client = (OwdClient) clients.get(0);
            //init collection


            return client;
        } catch (Exception ex) {

           // ex.printStackTrace();
            throw ex;
        } finally {

        }


    }

    public static List getAllClientsWithIntacctID(Session sess) throws Exception {
        try {

            List clients = sess.createQuery("from OwdClient where len(shipper_symbol)>0 order by client_id asc").list();

            if (clients.size()<1) throw new Exception("Client records not found");

            return clients;
        } catch (Exception ex) {

           // ex.printStackTrace();
            throw ex;
        } finally {

        }


    }
}
