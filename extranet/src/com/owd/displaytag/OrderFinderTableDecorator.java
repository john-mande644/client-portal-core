package com.owd.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.web.servlet.ExtranetServlet;
import com.owd.web.servlet.OrdersManager;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 19, 2004
 * Time: 2:13:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderFinderTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    static DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mm aa", Locale.US);

    public String getOrderStatus()
            throws DecoratorException {
        OwdOrder order = (OwdOrder) getCurrentRowObject();
        if (order == null) return "null order";

        boolean isShipped = false;

        if (order.getIsVoid() == 1) return "&nbsp;Canceled";

       
        if (order.getPostDate() != null && order.getIsFutureShip() == 0) return "<IMG SRC=\"/webapps/images/status_resolved.gif\" >&nbsp;Released&nbsp;" + df.format(order.getPostDate());
        if (order.getIsFutureShip() == 1) return "<IMG SRC=\"/webapps/images/priority_blocker.gif\" >&nbsp;On Hold";
        if (order.isIsBackorder() && order.getIsFutureShip() == 0) return "<IMG SRC=\"/webapps/images/status_inprogress.gif\" >&nbsp;Pending Backorder (" + order.getBackorderLevel() + ")";

        return null;

    }

    public String getEditLink()
            throws DecoratorException {
        OwdOrder order = (OwdOrder) getCurrentRowObject();
        if (order == null) return "";

        int index = getListIndex();
        return "<A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + OrdersManager.kParamOrderMgrAction + "=" + OrdersManager.kParamOrderEditAction + "&listindex=" + index + "\">" + order.getOrderNum() + "</A>";
    }

    public String getClientName()
            throws Exception {
        OwdOrder order = (OwdOrder) getCurrentRowObject();
        if (order == null) return "";

        return order.getClient().getCompanyName();


    }

    public String getCustomer()
            throws DecoratorException {
        OwdOrder order = (OwdOrder) getCurrentRowObject();
        if (order == null) return "";

        if (order.getBillCompanyName().trim().length() > 2) {
            return order.getBillCompanyName();
        } else {
            return order.getBillFirstName() + " " + order.getBillLastName();
        }

    }


}
