package com.owd.jobs;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.DuplicateOrderException;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.jobs.jobobjects.networksolutions.owd.NetworkSolutionsAPI;
import com.owd.jobs.jobobjects.networksolutions.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: StewartBuskirk
 * Date: Aug 3, 2010
 * Time: 12:41:50 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class NetworkSolutionsAbstractIntegration extends OWDStatefulJob
{
private final static Logger log =  LogManager.getLogger();


    abstract public void internalExecute();

    protected abstract Order getOrderForClientIdentifier();

    protected abstract void orderCompleted(Order order) throws Exception;
    protected abstract void correctShippingMethod(Order order) throws Exception;

    protected abstract String translateShippingMethodToOWDMethod(Order anOrder, String MQMethod) throws Exception;


    void addLineItemToOrder(Order anOrder, String qty, String sku, String price, String desc, String lineprice) throws Exception
    {


        anOrder.addLineItem(sku, qty, price, lineprice, desc, "", "");

    }

    public void checkForOrders(NetworkSolutionsAPI api)
    {
        try
        {
            List<OrderType> orders = api.getImportOrderList();
            for (OrderType os : orders)
            {

                //loop
                try
                {
                    log.debug("id:" + os.getOrderId() + " for:" + os.getOrderNumber());
                    log.debug("Shiptype:" + os.getShipping().getName());


                    Order anOrder = getOrderForClientIdentifier();
                    log.debug("(Initial)Order Number = " + os.getOrderNumber().trim());
                    anOrder.order_refnum = anOrder.order_type.substring(0, 2) + "M" + os.getOrderNumber().trim();
                    log.debug("order_refnum=" + anOrder.order_refnum);
                    if (OrderUtilities.clientOrderReferenceExists(anOrder.order_refnum, anOrder.clientID, false))
                    {
                        throw new DuplicateOrderException("");
                    }

                    AddressType ba = os.getCustomer().getBillingAddress();
                    AddressType sa = os.getCustomer().getShippingAddress();

                    anOrder.getBillingAddress().address_one = ba.getAddress1();
                    anOrder.getBillingAddress().address_two = ba.getAddress2();
                    anOrder.getBillingAddress().company_name = ba.getCompany();
                    anOrder.getBillingAddress().city = ba.getCity();
                    anOrder.getBillingAddress().state = ba.getStateProvince();
                    anOrder.getBillingAddress().zip = ba.getPostalCode();
                    anOrder.getBillingAddress().country = CountryNames.getCountryName(ba.getCountry().value());
                    anOrder.getBillingContact().phone = ba.getPhone();
                    anOrder.getBillingContact().setName(ba.getFirstName().trim() + " " + ba.getLastName().trim());
                    anOrder.getBillingContact().email = os.getCustomer().getEmailAddress();
                    anOrder.getBillingAddress().fixNulls();
                    anOrder.getShippingAddress().address_one = sa.getAddress1();
                    anOrder.getShippingAddress().address_two = sa.getAddress2();
                    anOrder.getShippingAddress().company_name = sa.getCompany();
                    anOrder.getShippingAddress().city = sa.getCity();
                    anOrder.getShippingAddress().state = sa.getStateProvince();
                    anOrder.getShippingAddress().zip = sa.getPostalCode();
                    anOrder.getShippingAddress().country = CountryNames.getCountryName(sa.getCountry().value());
                    anOrder.getShippingContact().phone = sa.getPhone();
                    anOrder.getShippingContact().setName(sa.getFirstName().trim() + " " + sa.getLastName().trim());
                    anOrder.getShippingAddress().fixNulls();
                    //   anOrder.getShippingContact().email = os.getCustomer().getEmailAddress();

                    for (LineItemType lit : os.getInvoice().getLineItemList())
                    {
                        log.debug("Line:" + lit.getName() + ":" + lit.getPartNumber() + ":" + lit.getQtySold() + ":" + lit.getUnitPrice().getValue());
                        addLineItemToOrder(anOrder, "" + lit.getQtySold(), lit.getPartNumber(), "" + lit.getUnitPrice().getValue(), lit.getName(), "0.00");

                    }
                    TaxAppliedType tax = os.getInvoice().getTax();
                    anOrder.total_tax_cost = (tax == null ? 0.00f : Float.parseFloat((tax.getAmount() + "")));
                    AmountType ship = os.getInvoice().getShipping();
                    AmountType handling = os.getInvoice().getHandling();
                    AmountType discount = os.getInvoice().getDiscount();

                    anOrder.total_shipping_cost = (ship == null ? 0.00f : Float.parseFloat((ship.getValue() + "")));
                    anOrder.total_shipping_cost += (handling == null ? 0.00f : Float.parseFloat((handling.getValue() + "")));
                    anOrder.discount = (discount == null ? 0.00f : -1.00f * (Math.abs(Float.parseFloat((discount.getValue() + "")))));

                    anOrder.recalculateBalance();

                    anOrder.getShippingInfo().setShipOptions(translateShippingMethodToOWDMethod(anOrder, os.getShipping().getName()), "Prepaid", "");

                    correctShippingMethod(anOrder);


                    //process order
                    //anOrder.is_future_ship = 1;
                    if (anOrder.skuList.size() < 1)
                        throw new Exception("Order has no SKUs!");


                    String reference = anOrder.saveNewOrder(OrderUtilities.kHoldForPayment, false);

                    if(reference==null)
                    {
                        throw new Exception("Unable to save new NetSol order : "+anOrder.last_error);
                    }

                    orderCompleted(anOrder) ;

                } catch (Exception ex)
                {

                    ex.printStackTrace();
                    StringBuffer sb = new StringBuffer();
                    String stamper = OWDUtilities.getSQLDateTimeForToday();
                    sb.append("\nException when importing order: " + ex.getMessage() + "\n\rReference ID: " + stamper + "\n\n");
                    //////////log.debug("BelAmiImporter stamper="+stamper);
                    if (ex instanceof DuplicateOrderException)
                    {


                    } else
                    {
                        //   forwardUnimportableMessage(ex.getMessage());
                        ex.printStackTrace();
                    }
                }

            }

        } catch (Exception ex)
        {
            if(!(ex.getMessage().contains("No orders found")))
            {
            ex.printStackTrace();
            StringBuffer sb = new StringBuffer();
            String stamper = OWDUtilities.getSQLDateTimeForToday();
            sb.append("\nException: " + ex.getMessage() + "\n\nStacktrace ID: " + stamper + "\n\n");
            ex.printStackTrace();
            Mailer.postMailMessage("Netsol Importer import error", sb.toString(), "casetracker@owd.com", "donotreply@owd.com");
        }
        }
    }
}
