package com.owd.core.business.intacct;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import com.owd.hibernate.generated.OwdTran;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.XPathAPI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 31, 2006
 * Time: 9:57:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class IntacctActions {
private final static Logger log =  LogManager.getLogger();

    static public DateFormat dfIntacct = new SimpleDateFormat("'<year>'yyyy'</year><month>'MM'</month><day>'dd'</day>'");

    static transient String shipBatchID = null;
    static transient String activityBatchID = null;

    public static String getShipBatchID() {
        return shipBatchID;
    }

    public static void setShipBatchID(String shipBatchID) {
        IntacctActions.shipBatchID = shipBatchID;
    }

    public static String getActivityBatchID() {
        return activityBatchID;
    }

    public static void setActivityBatchID(String activityBatchID) {
        IntacctActions.activityBatchID = activityBatchID;
    }

    public static void pushARPaymentToIntacct(OwdTran trans) throws Exception {
        if (getActivityBatchID() == null) {
            setActivityBatchID(IntacctUtilities.getCurrentPaymentBatchID(IntacctUtilities.kMainAccountID));
        }
        if (getShipBatchID() == null) {
            setShipBatchID(IntacctUtilities.getCurrentPaymentBatchID(IntacctUtilities.kShippingAccountID));
        }

        StringBuffer xml = new StringBuffer();
        String key = null;

        OwdClient payingClient = trans.getFromClient();

        if (payingClient == null) {
            throw new Exception("Paying client reference invalid in transaction");
        }

        xml.append("<create_arpayment>");
        xml.append("<customerid>" + payingClient.getShipperSymbol() + (("SHIPPING".equals(trans.getAccountType()))?"_1":"")+"</customerid>");
        xml.append("<paymentamount>" + trans.getAmount() + "</paymentamount>");

        if ("SHIPPING".equals(trans.getAccountType())) {
            xml.append("<batchkey>" + getShipBatchID() + "</batchkey>");
        } else if ("ACTIVITY".equals(trans.getAccountType())) {
            xml.append("<batchkey>" + getActivityBatchID() + "</batchkey>");
        } else {
            throw new Exception("Invalid transaction account type " + trans.getAccountType() + " for trans ID " + trans.getTransId());
        }

        xml.append("<refid>Online " + (trans.getFop().equals("CC") ? "CC " + trans.getCcNumber().substring(trans.getCcNumber().length() - 3) + " " : "ECheck ") + trans.getProcTransId() + ":" + trans.getTransId() + "</refid>");
        xml.append("<datereceived>" + dfIntacct.format(trans.getTransTime()) + "</datereceived>");
        //Printed Check, Online, Cash, EFT, Credit Card
        xml.append("<paymentmethod>" + (trans.getFop().equals("CC") ? "Credit Card" : "EFT") + "</paymentmethod>");
        xml.append("</create_arpayment>");


    //    log.debug(xml);
          IntacctRequest req = new IntacctRequest();
     req.addFunction(xml.toString());
     IntacctResponse resp = IntacctUtilities.intacctAPI(req);


          /*     Transformer serializer =
           TransformerFactory.newInstance().newTransformer();
       serializer.setOutputProperty(
             OutputKeys.OMIT_XML_DECLARATION, "yes");


              serializer.transform(
            new DOMSource(resp.getRawResponse()),
            new StreamResult(System.out));
*/

        if(resp.isOk())
        {
            String transID = (XPathAPI.eval(resp.getRawResponse(),"/response/operation/result/key").toString());
            trans.setIntacctId(Integer.parseInt(transID));
            HibernateSession.currentSession().save(trans);
            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

        }else
        {
            throw new Exception("Error posting ARPayment to account for transaction id "+trans.getTransId()+"; "+resp.getErrorDescription());
        }
    }


    public static void main(String[] args) {
        try {
            List<OwdTran> txns = HibernateSession.currentSession().createQuery("from OwdTran where customer_fkey=55 and status='Approved' and from_client_fkey is not null and intacct_id is null").list();

            for (OwdTran trans : txns) {
                log.debug("pushing for trans ID "+trans.getTransId());
                pushARPaymentToIntacct(trans);
                //return;
                //todo save key
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
