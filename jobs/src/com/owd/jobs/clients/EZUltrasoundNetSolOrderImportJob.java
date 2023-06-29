package com.owd.jobs.clients;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Client;
import com.owd.core.business.order.Order;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.jobs.NetworkSolutionsAbstractIntegration;
import com.owd.jobs.jobobjects.networksolutions.owd.NetworkSolutionsAPI;
import com.owd.jobs.jobobjects.casetracker.CasetrackerAPI;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import com.sun.xml.bind.v2.runtime.MarshallerImpl;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.NamespaceContext;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Aug 3, 2010
 * Time: 1:55:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class EZUltrasoundNetSolOrderImportJob extends NetworkSolutionsAbstractIntegration {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) {
        run();
    }

    @Override
    protected void orderCompleted(Order order) throws Exception {

        if ((order.containsSKU("US1000") || order.containsSKU("ZZA1000") || order.containsSKU("PainShield")) && order.getShippingAddress().isCanada() && order.is_future_ship == 1) {
         //   Event.addOrderEvent(Integer.parseInt(order.orderID), Event.kEventTypeHandling, "Order held due to blocked product shipping to Canada", "ORDER RELEASE CHECK");

        //    Mailer.sendMail("Canadian Order Placed On Hold (OWD Ref:" + order.orderNum + ")", "Order held at OWD due to blocked product shipping to Canada", "sales@ezultrasound.com", "donotreply@owd.com");

           CasetrackerAPI.createNewCaseForClient("Canadian Order Placed On Hold (OWD Ref:" + order.orderNum + ")",
                    "Order held due to blocked product shipping to Canada - please notify customer and issue refund. Update client with status and order information.", order.clientID, null,CasetrackerAPI.getLoginToken());
        }

        Client.getClientForID("432").getClientPolicy().sendCustomerEmailConfirmation(order);


    }


    @Override
    public void internalExecute() {
        NetworkSolutionsAPI api = new NetworkSolutionsAPI("internal app", "1b706ccce4934c958ce16ba58a278539", "d9CTc54NqFy87DmMf63KgBa2z7R6Etb3", 432);
             //resp:j4LGa57WeMg96HrKo23ZiJz8w6NBp2f8
//        10:35:41.149 [main] DEBUG com.owd.jobs.jobobjects.networksolutions.owd.NetworkSolutionsAPI - token=j4LGa57WeMg96HrKo23ZiJz8w6NBp2f8
        checkForOrders(api);

    }


    @Override
    protected Order getOrderForClientIdentifier() {

        Order anOrder = null;

        try{
            anOrder = new Order("432");

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }


        anOrder.order_type = "EZU NetSol API Importer";

        anOrder.backorder_rule = OrderXMLDoc.kBackOrderAll;
        anOrder.ship_call_date = OWDUtilities.getSQLDateForToday();
        anOrder.payment_status = OrderXMLDoc.kPaymentStatusClientManaged; //no payment processing


        return anOrder;
    }

    @Override
    protected void correctShippingMethod(Order anOrder) throws Exception {
        if (anOrder.getShippingInfo().carr_service.equals("UPS Ground") &&
                (anOrder.getShippingAddress().isPOAPO())) {
            anOrder.getShippingInfo().setShipOptions("Priority Mail", "Prepaid", "");
        }


        if (anOrder.getShippingAddress().isInternational()) {

            anOrder.backorder_rule = OrderXMLDoc.kBackOrderAll;
        }

        if (anOrder.getShippingAddress().isInternational() && anOrder.getShippingInfo().carr_service.contains("Expr Mail")) {
            anOrder.getShippingInfo().setShipOptions("USPS Priority Mail Express International", "Prepaid", "");
        }

        if ((anOrder.containsSKU("US1000") || anOrder.containsSKU("ZZA1000") || anOrder.containsSKU("PainShield")) && anOrder.getShippingAddress().isCanada()) {
            //cannot ship these to canada
       //     anOrder.is_future_ship = 1;

        }


        if (!anOrder.containsSKU("returnslip")) {
            anOrder.addInsertItemIfAvailable("returnslip", 1);
        }
            if (anOrder.containsSKU("ZZA1000")) {

                anOrder.addLineItem("ChinaSample", ""+anOrder.getQuantityForSKU("ZZA1000"),"0.00","0.00","","","");

        }
           if (anOrder.containsSKU("US1000")) {

                anOrder.addLineItem("Sombra4", ""+anOrder.getQuantityForSKU("US1000"),"0.00","0.00","","","");

        }

        if (anOrder.containsSKU("UCPro")) {

                anOrder.addLineItem("Sombra4", ""+anOrder.getQuantityForSKU("UCPro"),"0.00","0.00","","","");

        }

         if (anOrder.containsSKU("UCPro") || anOrder.containsSKU("US1000") || anOrder.containsSKU("Sombra4") || anOrder.containsSKU("Sombra8")) {

                anOrder.addInsertItemIfAvailable("Sombra-Brochure",1);

        }

        if (anOrder.containsSKU("UCPro") || anOrder.containsSKU("US1000")) {

            anOrder.addInsertItemIfAvailable("User-Reference-Guide", 1);

        }

    }

    @Override
    protected String translateShippingMethodToOWDMethod(Order anOrder, String MQMethod) throws Exception {

        if (MQMethod.toUpperCase().indexOf("GROUND") >= 0) {
            return "UPS Ground";
        } else if (MQMethod.toUpperCase().indexOf("UPS THREE") >= 0
                || MQMethod.toUpperCase().indexOf("UPS 3") >= 0) {
            return "UPS 3 Day Select";
        } else if (MQMethod.toUpperCase().indexOf("UPS TWO") >= 0
                || MQMethod.toUpperCase().indexOf("UPS 2") >= 0) {
            return "UPS 2nd Day Air";
        } else if (MQMethod.toUpperCase().indexOf("UPS STANDARD") >= 0) {
            return "UPS Standard Canada";
        } else if (MQMethod.toUpperCase().indexOf("UPS WORLDWIDE EXPED") >= 0) {
            return "UPS Worldwide Expedited";
        } else if (MQMethod.toUpperCase().indexOf("UPS WORLDWIDE SAVER") >= 0) {
            return "UPS Worldwide Express Saver";
        } else if (MQMethod.toUpperCase().indexOf("GLOBAL") >= 0 ||
                (MQMethod.toUpperCase().indexOf("EXPRESS") >= 0 && anOrder.getShippingAddress().isInternational())) {
            return "USPS Priority Mail Express International";
        } else if (MQMethod.toUpperCase().indexOf("NEXT DAY") >= 0) {
            return "UPS Next Day Air Saver";
        } else if (MQMethod.toUpperCase().indexOf("PRIORITY") >= 0 &&
                MQMethod.toUpperCase().indexOf("INTERNATIONAL") >= 0) {
            return "USPS Priority Mail International";
        } else if (MQMethod.toUpperCase().indexOf("EXPRESS") >= 0) {
            return "USPS Priority Mail Express";
        } else if (MQMethod.toUpperCase().indexOf("PRIORITY") >= 0) {
            return "USPS Priority Mail";
        } else if (MQMethod.toUpperCase().indexOf("MEDIA MAIL") >= 0) {
            return "USPS Media Mail Single-Piece";
        } else {
            //default

            List<String> alternateShipMethodList = new ArrayList<String>();
            alternateShipMethodList.add("TANDATA_USPS.USPS.PRIORITY");
            alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.GND");
            alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.FHD");
            alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_PS");
            alternateShipMethodList.add("TANDATA_FEDEXFSMS.FEDEX.SP_STD");

            return  RateShopper.rateShop(anOrder, alternateShipMethodList);
        }


    }
}
