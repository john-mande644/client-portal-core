package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.displaytag.decorator.TableDecorator;
import org.apache.commons.beanutils.BasicDynaBean;

import java.util.List;
import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 2, 2006
 * Time: 2:28:47 PM
 * To change this template use File | Settings | File Templates.
 */


/**
 * This decorator only does a summing of different groups in the reporting style examples...
 * @author epesh
 * @version $Revision $ ($Author $)
 */
public class AccountingTableDecorator extends TableDecorator
{
private final static Logger log =  LogManager.getLogger();

    /**
     * total amount.
     */
    private double creditTotal;

    /**
     * total amount for city.
     */
    private double debitTotal;

    private int pageRows=0;

    /**
     * After every row completes we evaluate to see if we should be drawing a new total line and summing the results
     * from the previous group.
     * @return String
     *
     *
     * <tr class="odd">
<td>2005-03-31 00:00:00.0</td>
<td>Adjustment</td>
<td>890:</td>
<td></td>
<td></td>
<td>429.180000</td>
<td>0.0000</td></tr>



    */public final String finishRow()
    {

        pageRows++;

        DecimalFormat df = new DecimalFormat("$#,###,##0.00");


        StringBuffer buffer = new StringBuffer(1000);


        //log.debug("check total:"+getDecoratedObject().getClass().getName());

        // Grand totals...
        //log.debug("view:"+getViewIndex());
        //log.debug("list:"+getListIndex());
        //log.debug("size:"+new Integer((String) getPageContext().getAttribute("pagesize")).intValue());

        if ((getListIndex() == ((List)getDecoratedObject()).size()-1)
        || (getViewIndex() == 10 - 1))
        {
            for(int i=0;i<((List) getDecoratedObject()).size();i++)
            {
                this.creditTotal += new Double(""+(((BasicDynaBean)((List) getDecoratedObject()).get(i)).get("Credit"))).doubleValue();
                this.debitTotal += new Double(""+(((BasicDynaBean)((List) getDecoratedObject()).get(i)).get("Debit"))).doubleValue();
            }
            buffer.append("<tr><td colspan=\"7\"><hr noshade size=\"1\"></td></tr>");
            buffer.append("<tr class=\"odd\"><td colspan=5>&nbsp;<b>Account Balance: "+df.format(this.creditTotal-this.debitTotal)+"</td>");
            buffer.append("<td style=\"text-align:right\"><b>"+df.format(this.creditTotal)+"</b></td><td style=\"text-align:right\"><b>");
            buffer.append(df.format(this.debitTotal));
            buffer.append("</b></td></tr>");
        }else
        {


        }

        return buffer.toString();
    }

}
