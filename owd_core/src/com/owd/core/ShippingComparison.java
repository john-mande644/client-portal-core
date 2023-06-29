package com.owd.core;


import com.owd.core.business.PackageRate;
import com.owd.core.csXml.OrderRater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 7, 2006
 * Time: 1:28:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShippingComparison {
private final static Logger log =  LogManager.getLogger();

    public static String getValues(List<String> shippingCodes, double[] weights, String city, String state, String zip, String country) throws Exception {
       StringBuffer sb = new StringBuffer();
        sb.append("Zip Code,Ship Method,Weight,Rate\r\n");


              for(int weightindex=0;weightindex<weights.length;weightindex++)
        {

        try {

            OrderRater rater = new OrderRater();
            List<PackageRate> resp =  rater.getRates("123 main", city, state,
                    zip, country
                    , (float) weights[weightindex], false, false, OrderRater.getShipperForLocation("DC1"), 0, shippingCodes);

            if (resp != null) {


                java.text.DecimalFormat formatter = new java.text.DecimalFormat("$#,###,##0.00");

                for (PackageRate currShipment:resp)
                {
                    //log.debug("service:"+currShipment.getSHIPMENTSERVICE().getSCS());


                        if (currShipment.getErrorCode()==0 )
                        {

                            float shiprate = new Float(currShipment.getFinalRate());

                            String rateStr;

                            sb.append(zip).append(",");
                            sb.append((currShipment.getMethodName().contains("(") ? currShipment.getMethodName().substring(0, currShipment.getMethodName().indexOf("(")) : currShipment.getMethodName()));
                            sb.append(",");
                            sb.append(weights[weightindex]);
                            sb.append(",");
                            sb.append(formatter.format(com.owd.core.OWDUtilities.roundFloat(shiprate, 2)));
                            sb.append("\r\n");

                        } else {

                            //log.debug("err: "+currShipment.getERROR().getERRORCODE() +" desc:"+ currShipment.getERROR().getERRORDESCRIPTION());
                        }



                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }


        return sb.toString();
    }

    public static void main (String[] args)
    {

        try
        {
            List<String> codes= new ArrayList<String>(Arrays.asList("CONNECTSHIP_UPS.UPS.GND", "TANDATA_USPS.USPS.PRIORITY", "TANDATA_FEDEXFSMS.FEDEX.SP_PS"));
            double[] weights ={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

            log.debug(getValues(codes,weights,"Los Angeles","CA","90025","USA")
            +getValues(codes,weights,"New York","NY","10036","USA"));
        }   catch(Exception exx)
        {
            exx.printStackTrace();
        }
    }
}
