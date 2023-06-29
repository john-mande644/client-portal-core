package com.owd.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import com.owd.web.servlet.ExtranetServlet;
import com.owd.web.servlet.InventoryManager;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;

import java.sql.ResultSet;
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
public class InventoryFinderTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    static DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mm aa", Locale.US);


    public String getEditLink()
            throws DecoratorException {
        OwdInventory item = (OwdInventory) getCurrentRowObject();
        if (item == null) return "";

        int index = getListIndex();
        return "<A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + InventoryManager.kParamInvMgrAction + "=" + InventoryManager.kParamInvEditAction + "&listindex=" + index + "\">" + item.getInventoryNum() + "</A>";
    }

    public String getClientName()
            throws Exception {
        OwdInventory item = (OwdInventory) getCurrentRowObject();
        if (item == null) return "";

        return item.getOwdClient().getCompanyName();


    }

    public String getOnHand() {

        try {
            OwdInventory item = (OwdInventory) getCurrentRowObject();
            if (item == null) return "";

            return "" + item.getOwdInventoryOh().getQtyOnHand();
        } catch (Exception ex) {

            ex.printStackTrace();
            return "xxx";
        }

    }

    public String getBackordered()
            throws Exception {

        try {
            OwdInventory item = (OwdInventory) getCurrentRowObject();
            if (item == null) return "";

            ResultSet rs = HibernateSession.getResultSet("select sum(quantity_request) from owd_order join owd_line_item on " +
                    "order_id=order_fkey where client_fkey=" + item.getOwdClient().getClientId() + " and inventory_num=\'" + item.getInventoryNum() + "\' and is_void=0 " +
                    "and is_future_ship=0 and is_backorder = 1 and post_date is null");
            if (rs.next()) {
                return rs.getString(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeSession();
        }
        return "0";


    }


}
