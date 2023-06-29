package com.owd.core.business.client;

import com.owd.core.business.order.LineItem;
import com.owd.core.business.order.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 1, 2004
 * Time: 2:16:00 PM
 * To change this template use File | Settings | File Templates.
 */

public class ChoicesClientPolicy extends DefaultClientPolicy{
private final static Logger log =  LogManager.getLogger();

    public int getUnitCount(Order order)
    {
        int unitCnt = 0;
        for(int i=0;i<order.skuList.size();i++)
        {
            String part = ((LineItem)order.skuList.get(i)).client_part_no;

            if(!(part.toUpperCase().startsWith("WAC")
            && (!(part.toUpperCase().endsWith("A")
                    ||  part.toUpperCase().endsWith("B")
                    ||  part.toUpperCase().endsWith("C")
                    ||  part.toUpperCase().endsWith("D")
                    ||  part.toUpperCase().endsWith("E")
                    ||  part.toUpperCase().endsWith("F")
                    ||  part.toUpperCase().endsWith("G")

                    ))  ))
            {
               unitCnt += ((LineItem)order.skuList.get(i)).quantity_request;
            }
        }


        return unitCnt;


    }

        public int getTotalUnitCount(Order order)
    {
        int totalCnt = 0;
            for(int i=0;i<order.skuList.size();i++)
        {

               totalCnt += ((LineItem)order.skuList.get(i)).quantity_request;

        }


        return totalCnt;

    }

    public int getSeriesCount(Order order)
    {
        int seriesCnt = 0;
            for(int i=0;i<order.skuList.size();i++)
        {
            String part = ((LineItem)order.skuList.get(i)).client_part_no;

            if(part.toUpperCase().startsWith("WAC")
            && (!(part.toUpperCase().endsWith("A")
                    ||  part.toUpperCase().endsWith("B")
                    ||  part.toUpperCase().endsWith("C")
                    ||  part.toUpperCase().endsWith("D")
                    ||  part.toUpperCase().endsWith("E")
                    ||  part.toUpperCase().endsWith("F")
                    ||  part.toUpperCase().endsWith("G")

                    ))  )
            {
               seriesCnt += ((LineItem)order.skuList.get(i)).quantity_request;
            }
        }


        return seriesCnt;

    }
    public List getShipOptions(Order order, float defaultCost) {
          List options = super.getShipOptions(order,defaultCost);

          Iterator it = options.iterator();
        while(it.hasNext())
        {
            ShippingOption so = (ShippingOption) it.next();


            if(order.getShippingAddress().isInternational())
            {
              if(getSeriesCount(order)>0)
                  {
                      so.customerCost = (15.00f*(getSeriesCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }else
                  {
                      so.customerCost = (10.00f*(getUnitCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }
            } else
            {
            if (so.desc.indexOf("3 Day")>=0)
            {
             if(getSeriesCount(order)>0)
                  {
                      so.customerCost = (15.75f*(getSeriesCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }else
                  {
                      so.customerCost = (14.46f*(getUnitCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }
            }else if (so.desc.indexOf("2nd Day")>= 0)
            {
                  if(getSeriesCount(order)>0)
                  {
                      so.customerCost = (18.44f*(getSeriesCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }else
                  {
                      so.customerCost = (16.50f*(getUnitCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }
            }else if (so.desc.indexOf("Next Day")>= 0)
            {
              if(getSeriesCount(order)>0)
                  {
                      so.customerCost = (35.37f*(getSeriesCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }else
                  {
                      so.customerCost = (31.88f*(getUnitCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }
            }else
            {
                  if(getSeriesCount(order)>0)
                  {
                      so.customerCost = (10.00f*(getSeriesCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }else
                  {
                      so.customerCost = (6.00f*(getUnitCount(order)))+(2.00f*(getTotalUnitCount(order)-1));
                  }
            }
            }

        }
          return options;

      }




}
