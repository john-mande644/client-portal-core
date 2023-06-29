package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.CountryNames;
import com.owd.core.business.Address;
import com.owd.core.business.Client;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.csXml.OrderRater;
import com.owd.core.managers.AddressManager;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: Stewart Buskirk
 * Date: May 28, 2010
 * Time: 11:01:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class PhionShippingRateRequest implements APIRequestHandler
{
private final static Logger log =  LogManager.getLogger();

    public static final String kRootTag = "PHION_SHIPPING_RATE_REQUEST";
    static
    Map<String,String> svcCodeMap = new TreeMap<String,String>();
    static {
        svcCodeMap.put("TANDATA_UPS.UPS.NDS","12");
        svcCodeMap.put("TANDATA_UPS.UPS.2DA","1");
        svcCodeMap.put("TANDATA_UPS.UPS.GND","0");
        svcCodeMap.put("TANDATA_USPS.USPS.FIRST","15");
        svcCodeMap.put("TANDATA_USPS.USPS.PRIORITY","16");
        svcCodeMap.put("TANDATA_USPS.USPS.I_PRIORITY","19");
        svcCodeMap.put("TANDATA_USPS.USPS.EXPR","14");
        svcCodeMap.put("TANDATA_USPS.USPS.I_FIRST","18");
        svcCodeMap.put("TANDATA_USPS.USPS.I_EXP_DMND","17");
        svcCodeMap.put("TANDATA_UPS.UPS.WSTD","24");
        svcCodeMap.put("TANDATA_UPS.UPS.WEXP","26");
        svcCodeMap.put("TANDATA_UPS.UPS.WEPD","25");

    }

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception
    {

     /*   log.debug(""+
             "ENCODING:"+   XPathAPI.eval(XPathAPI.selectSingleNode(root, "./SHIPPING_ADDRESS"), "./POSTCODE").toString().trim());

        byte[] bs = XPathAPI.eval(XPathAPI.selectSingleNode(root, "./SHIPPING_ADDRESS"), "./POSTCODE").toString().trim().getBytes("UTF-8");
           for (int i=0;i<bs.length;i++)
        {
            log.debug("!!<"+bs[i]+">");
        }*/
        PhionShippingRateResponse response = new PhionShippingRateResponse(api_version);

        Order order = new Order(client.client_id);


        String orderValue = XPathAPI.eval(root, "./VALUE").toString().trim();
        log.debug("order value="+orderValue);
      
        
        Node addrNode = XPathAPI.selectSingleNode(root, "./SHIPPING_ADDRESS");

        order.getShippingInfo().shipAddress = new Address("",
                XPathAPI.eval(addrNode, "./ADDRESS_1").toString().trim(),
                "",//XPathAPI.eval(addrNode, "./ADDRESS_2").toString().trim(),
                XPathAPI.eval(addrNode, "./CITY").toString().trim(),
                XPathAPI.eval(addrNode, "./REGION").toString().trim(),
                XPathAPI.eval(addrNode, "./POSTCODE").toString().trim(),
                XPathAPI.eval(addrNode, "./COUNTRYCODE").toString().trim());

        Address oldAddress = new Address("",
                XPathAPI.eval(addrNode, "./ADDRESS_1").toString().trim(),
                XPathAPI.eval(addrNode, "./ADDRESS_2").toString().trim(),
                XPathAPI.eval(addrNode, "./CITY").toString().trim(),
                XPathAPI.eval(addrNode, "./REGION").toString().trim(),
                XPathAPI.eval(addrNode, "./POSTCODE").toString().trim(),
                XPathAPI.eval(addrNode, "./COUNTRYCODE").toString().trim());

        String countryCode = XPathAPI.eval(addrNode, "./COUNTRYCODE").toString();

        if ((order.getShippingInfo().shipAddress.country == null) || !(CountryNames.exists(order.getShippingInfo().shipAddress.country)))
        {
            throw new APIContentException("Addressee country name missing or not valid");
        } else
        {
            order.getShippingInfo().shipAddress.country = CountryNames.getCountryName(order.getShippingInfo().shipAddress.country);
        }


        try
        {
            response.setAddressCorrected(false);
            AddressManager.validate(order.getShippingInfo().shipAddress);
            response.setAddressCorrected(true);
            if (
                   (0 == oldAddress.address_one.compareTo(order.getShippingInfo().shipAddress.address_one.trim())) &&
                (0 == oldAddress.address_two.compareTo(order.getShippingInfo().shipAddress.address_two.trim()))&&
                (0 == oldAddress.city.compareTo(order.getShippingInfo().shipAddress.city.trim())) &&
                (0 == oldAddress.state.compareTo(order.getShippingInfo().shipAddress.state.trim())) &&
                (0 == oldAddress.zip.compareTo(order.getShippingInfo().shipAddress.zip.trim())) )
            {
                response.setAddressValid(true);
            }

        } catch (Exception exx)
        {
            if (!(exx.getMessage().contains("USPS DOWN")))
            {
                log.debug("" + exx.getMessage());
            } else
            {
                log.debug("USPS Unavailable - passing order through uncorrected... \n");
            }
        }


        order.getShippingInfo().shipAddress.address_two = oldAddress.address_two.toUpperCase();
        response.setRatedAddress(order.getShippingInfo().shipAddress);

        try
        {
            double orderValueDouble =  Double.parseDouble(orderValue);
            if(orderValueDouble>=100.00 && order.getShippingInfo().shipAddress.isUS())
            {
                response.setHasFreeMethod(true);
            }
        }   catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        OrderRater rater = new OrderRater(order);
       rater.setCalculateKitWeights(true);

        NodeList itemNodes = XPathAPI.selectNodeList(root, "./ITEMS/ITEM");
        if (itemNodes != null)
        {
            if (itemNodes.getLength() > 0)
            {
                for (int i = 0; i < itemNodes.getLength(); i++)
                {
                    Node item = itemNodes.item(i);
                    String sku = XPathAPI.eval(item, "@sku").toString().trim();
                    String qtyStr = XPathAPI.eval(item, "@qty").toString().trim();
                    int qty = 0;
                    try
                    {
                        qty = Integer.parseInt(qtyStr);
                    } catch (Exception ex)
                    {

                    }
                    if (qty <= 0)
                    {
                        throw new APIContentException("Item quantity for sku=" + sku + " is not a valid integer or is less than 1");
                    }

                    if (LineItem.SKUExists(client.client_id, sku))
                    {

                        order.addLineItem(sku, qty, 0.00f, 0.00f, "", "", "");

                    } else
                    {
                        throw new APIContentException("No inventory found matching SKU " + sku + " for com.owd.api.client ID " + client.client_id);
                    }
                }
            }
        }




        rater.rate(new ArrayList<String>(svcCodeMap.keySet()));
        response.setResponse(rater.theResponse);
        response.setShipMethodCodeMap(svcCodeMap);
        order.getShippingInfo().shipAddress.country = countryCode;
     
        return response;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
