package com.owd.jobs.archives;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.jobobjects.utilities.RateShopper;
import com.owd.jobs.jobobjects.shopify.ShopifyAPI;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 2/1/12
 * Time: 9:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class MaxtonMenImportJob extends OWDStatefulJob {
private final static Logger log =  LogManager.getLogger();


    public static List<String> hazmatSkus;

    public static List<String> bigBoxSkus;

    static {
        hazmatSkus = new ArrayList<String>();

        hazmatSkus.add("SAPHCRMEDBRN");
        hazmatSkus.add("SAPHCRMELBRN");
        hazmatSkus.add("SAPHCRMEBLK");
        hazmatSkus.add("SAPHCRMEMBRN");
        hazmatSkus.add("SAPHRENO");
        hazmatSkus.add("SAPHWAXNEUT");
        hazmatSkus.add("SAPHWAXMBRN");
        hazmatSkus.add("SAPHWAXLBRN");
        hazmatSkus.add("SAPHWAXBLK");

        bigBoxSkus = Arrays.asList("ECOCAMBRIDGEANTH","ECOCAMBRIDGENVY","ECOLAANTH","ECOLANVY","ECOLONDONNVY","HEXACADBACK","HEXDRAKEBACK","HOLMESBLK13","HOLMESBLK15","HOLMESBRN13","HOLMESBRN15","HOLMESCHA13","HOLMESCHA15","HSCHERITAGEBLK","HSCHERITAGECAMOORG","HSCHERITAGENVY","HSCLITTLECANARMY","HSCLITTLECANBLK","HSCLITTLECANNVY","HSCLITTLEGRY","HSCLITTLENVY","HSCNOVBLKTAN","HSCNOVCANARMY","HSCNOVCANBLK","HSCNOVGRYTAN","HSCNOVKNITKHK","HSCNOVKNITNVY","HSCNOVNVYTAN","HSCNOVWCAMO","HSCNOVWCAMONVYRED","HSCPOPBLK","HSCPOPCAMO","HSCPOPGRY","HSCPOPNVY","HSCPOPNVYRED","HSCPOPRED","HSCRAVBLKTAN","HSCRAVGRYTAN","HSCRAVNVYRED","HSCRAVNVYTAN","HSCSETTGRY","HSCSETTNVY","HSCSURNVY","HSCSURTPE","HSCSUTTONCAMO","HSCSUTTONMIDKHKBURG","ROTHEXPLORERBLK","ROTHEXPLORERBRN","ROTHEXPLOREROLV","SARUNAWAY","WALLYPOCK40","WALLYPOCK40NVY","WALLYPOCK45","WALLYSLIM45","WINNDUFFELBLK","WINNDUFFELCHOC","WINNDUFFELTAN","WMBABYLONBRN","WMBABYLONCHAR");
    }

    public static void main(String[] args) {

        // CasetrackerAPI.createCasetrackerCase("Client ID (55) order tester received on hold3", "Unable to determine ship method for this order; address or items invalid for available ship method options - " +
        //         "attempt to correct address and assign to IT Work Orders if needed.", 5555);

        run();
        log.debug(OWDUtilities.encryptData("502"));
        //   api.reportShipment(5156008, "9156901074187000034863", true);

    }

    static boolean isHazmat(Order order)
    {
        for (LineItem line:(Vector <LineItem>)order.skuList)
        {
            if(hazmatSkus.contains(line.client_part_no))
            {
                return true;
            }
        }
        return false;
    }

    public void internalExecute() {

        try {
            log.debug("starting MaxtonMenImportJob");

            ShopifyAPI api = new ShopifyAPI("1b37af97527198bc4e2efc7f9f93eded",
                    "f6f24b4fdf0cb5b246cbbbe251573d6a", "midfieldmen.myshopify.com","") {

                @Override
                public void doFinalStuffBeforeSavingOrder(Order order)  throws Exception {

                    int boxes = 0;

                    for(LineItem line:(Vector<LineItem>)order.skuList)
                    {
                         if(bigBoxSkus.contains(line.client_part_no))
                         {
                             boxes+=line.quantity_request;
                         }
                    }

                    if(boxes>0)
                    {
                        order.addLineItem("20X14X6BOX", boxes+"", "0.00", "0.00", "", "", "");

                    }

                }


                @Override
                public String getActualShipMethod(Order order, String oldMethod) throws Exception {

                    List<String> currMethods;

                    if(isHazmat(order))
                    {
                        if(!(order.getShippingAddress().isUS()))
                        {
                          order.is_future_ship=1;
                          order.getShippingInfo().comments="Held due to international shipping address for unqualified item";
                          return "UPS Ground";
                    }
                        currMethods  = Arrays.asList("CONNECTSHIP_UPS.UPS.GND");

                    }   else
                    {
                        if (shipMethodMap.containsKey(oldMethod.toUpperCase())) {
                            return shipMethodMap.get(oldMethod.toUpperCase()).get(0);
                        }
                      currMethods  = Arrays.asList("TANDATA_USPS.USPS.FIRST", "TANDATA_USPS.USPS.PRIORITY",
                            "CONNECTSHIP_UPS.UPS.GND", "CONNECTSHIP_UPS.UPS.STD", "TANDATA_USPS.USPS.I_FIRST", "TANDATA_USPS.USPS.I_PRIORITY", "UPS.STDCAMX");

                    }



                    return RateShopper.rateShop(order, currMethods);

                }
            };

            api.setClientInfo(497, null);
            api.shipMethodMap = new TreeMap<String, List<String>>();
            api.shipMethodMap.put("USPS Priority Mail (2-3 business days)".toUpperCase(), Arrays.asList("USPS Priority Mail"));
            api.shipMethodMap.put("USPS Priority Mail Express (1-2 business days)".toUpperCase(), Arrays.asList("USPS Priority Mail Express"));

            api.setOtherFulfillmentSku("SEPARATE");
            api.setFulfillmentServiceCode("stewart");
            api.importCurrentOrders(ShopifyAPI.kStatusTypePaid,false);


        } catch (Exception ex) {

            ex.printStackTrace();
        } finally {


            HibernateSession.closeSession();
        }

    }


    public void updateInventoryLevels() throws Exception {


    }

}
