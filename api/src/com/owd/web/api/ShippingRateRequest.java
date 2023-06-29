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
import com.owd.core.managers.FacilitiesManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
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
public class ShippingRateRequest implements APIRequestHandler
{
private final static Logger log =  LogManager.getLogger();

    public static final String kRootTag = "OWD_SHIPPING_RATE_REQUEST";

    @Trace
    public APIResponse handle(Client client, Element root, boolean testing, double api_version) throws Exception
    {

        OwdClient newclient = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,Integer.parseInt(client.client_id));

        log.debug(""+
             "ENCODING:"+   XPathAPI.eval(XPathAPI.selectSingleNode(root, "./SHIPPING_ADDRESS"), "./POSTCODE").toString().trim());

        byte[] bs = XPathAPI.eval(XPathAPI.selectSingleNode(root, "./SHIPPING_ADDRESS"), "./POSTCODE").toString().trim().getBytes("UTF-8");
           for (int i=0;i<bs.length;i++)
        {
            log.debug("!!<"+bs[i]+">");
        }
        ShippingRateResponse response = new ShippingRateResponse(api_version);

        Order order = new Order(client.client_id);

        Node addrNode = XPathAPI.selectSingleNode(root, "./SHIPPING_ADDRESS");
        log.debug(root.toString());
        log.debug(root.getAttribute("package_lbs"));

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
            log.debug("validating "+(order.getShippingInfo().shipAddress));
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

        OrderRater rater = new OrderRater(order);
       rater.setCalculateKitWeights(true);
        try {

            String p_lbs = root.getAttribute("packaging_lbs");
            log.debug("got pkg weight " + p_lbs);
            Float p_lbs_float = 0.00f;
            if (p_lbs.length() > 0) {


                try {
                    p_lbs_float = Float.parseFloat(p_lbs);


                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                log.debug("Setting force package weight");
                rater.setForcePackagingWeight(p_lbs_float);
                rater.setForcePackageZeroWeight(true);

            }

            if ("457".equals(client.client_id))  //TOCC special
            {
                rater.setForcePackagingWeight(1.50f);
            }

       }catch(Exception exfloat)
       {
            exfloat.printStackTrace();
       }
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

        List svcCodes = new ArrayList();
        Map codeMap = new TreeMap();

        NodeList carrierNodes = XPathAPI.selectNodeList(root, "./SHIPMETHODS/CODE");
        if (carrierNodes != null)
        {

            for (int i = 0; i < carrierNodes.getLength(); i++)
            {

                String svcCode = XPathAPI.eval(carrierNodes.item(i), ".").toString();
                try
                {
                    //  log.debug("API adding " + svcCode + " to best way carrier list3");
                    svcCode = OrderRater.getNewServiceCode(OrderUtilities.getServiceList().getRefForValue(OrderUtilities.getServiceList().getValueForRef(svcCode)));
                      log.debug("API adding " + svcCode + " to best way carrier list4");

                    svcCodes.add(svcCode);
                    codeMap.put(svcCode, XPathAPI.eval(carrierNodes.item(i), ".").toString());
                } catch (Exception codeex)
                {
                    throw new APIContentException("Ship method code " + svcCode + " was not recognized as a valid ship method");
                }
            }

        }
        if (svcCodes.size() < 1)
            throw new Exception("No valid ship method codes were found. You must provide at least one valid ship method code in the BEST_WAY element node when using the BWY ship method");



        rater.setClientId(""+newclient.getClientId());

        if(api_version>=2.0)
        {
        XObject originNode=XPathAPI.eval(root, "./FACILITY_CODE");
            if(originNode==null)
            {
                throw new APIContentException("FACILITY_CODE value is required for API versions 2.0 or higher");

            }
            String origin= originNode.toString().trim();

            if("ALL".equals(origin))
            {
                for(String originCode:FacilitiesManager.getActiveFacilityCodes())
                {
                    rater.setOriginCode(originCode);
                    rater.rate(svcCodes);
                    response.setPackageWeight(rater.getCalculatedPackageWeight());
                    response.addResponse(originCode,rater.theResponse);
                    response.setShipMethodCodeMap(codeMap);
                }
            }   else
            {
                if(!FacilitiesManager.getActiveFacilityCodes().contains(origin))
                {
                    throw new APIContentException("Ship method code " + origin + " was not recognized as a valid facility code");

                }

                rater.setOriginCode(origin);
                rater.rate(svcCodes);
                response.setPackageWeight(rater.getCalculatedPackageWeight());
                response.addResponse(origin,rater.theResponse);
                response.setShipMethodCodeMap(codeMap);
            }

        }else
        {
            String originCode = newclient.getDefaultFacilityCode();
            if(FacilitiesManager.isClientMultiFacility(newclient.getClientId()))
            {
               originCode="DC1";
            }

            rater.setOriginCode(originCode);
            rater.rate(svcCodes);
            response.setPackageWeight(rater.getCalculatedPackageWeight());
            response.addResponse(originCode,rater.theResponse);
            response.setShipMethodCodeMap(codeMap);
        }
        order.getShippingInfo().shipAddress.country = countryCode;
     
        return response;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
