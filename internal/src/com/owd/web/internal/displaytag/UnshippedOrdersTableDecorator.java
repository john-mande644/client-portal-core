package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.order.OrderFactory;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.OwdOrderShipHold;
import org.apache.commons.beanutils.DynaProperty;
import org.displaytag.decorator.TableDecorator;
import org.apache.commons.beanutils.DynaBean;

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
public class UnshippedOrdersTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    static DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);

    public String getOrderLink() {

        try {
            DynaBean bean = (DynaBean) getCurrentRowObject();

            if (bean == null) return "";

            //log.debug("id=" + bean.get("ixBug"));

            //log.debug("owdref=" + bean.get("OWD Ref"));
            String owdref = (String) bean.get("OWD Ref");

            OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(owdref);

            return "<A HREF=\"https://service.owd.com/webapps/extranet/extranet.jsp?act=cngCid&cid=" + order.getClientFkey() + "&ordermgr=edit&oid=" + order.getOrderId() + "\" target=###_###><B>View</B></>";
        }catch(Exception ex)
        {
            return "";
        }

    }

    public String getPrintStatusLink()  {
        try {

            DynaBean bean = (DynaBean) getCurrentRowObject();
            log.debug(bean);

            if (bean == null) return "";

            for (DynaProperty prop:bean.getDynaClass().getDynaProperties())
            {
                log.debug(prop.getName()+":"+prop.getType().getCanonicalName());
            }
            log.debug("owdref=" + bean.get("OWD Ref"));
            String owdref = (String) bean.get("OWD Ref");

            OwdOrder order = OrderFactory.getOwdOrderFromOwdReference(owdref);


            return "<A target=\"reprinter_\" HREF=\"/internal/warehouse/admin/printing/reprint.jsp?owdref=" + owdref + "\"><IMG SRC=\"/internal/images/print.gif\" border=\"0\"></A>&nbsp;" + order.getOrderNumBarcode().replaceAll("\\*", "") +
                    (order.getOrderNumBarcode().indexOf("R") > 0 ? "<BR>" + order.getOrderNumBarcode().substring(order.getOrderNumBarcode().indexOf("R")).replaceAll("\\*", "") + ":" + df.format((order.getModifiedDate() == null ? order.getPostDate() : order.getModifiedDate())).replaceAll(" ", "&nbsp;") : "");

        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR";
        }
    }

    public String getHoldStatusLink()  {


        DynaBean bean = (DynaBean) getCurrentRowObject();

        if (bean == null) return "";

        //log.debug("owdref=" + bean.get("OWD Ref"));
        String owdref = (String) bean.get("OWD Ref");

        try {
            //log.debug("getting hold info");
            OwdOrderShipHold holder = OrderFactory.getOwdOrderFromOwdReference(owdref).getHoldinfo();
            //log.debug("got hold info=" + holder);
            if (holder == null) {
                return "<FORM METHOD=POST ACTION=/internal/warehouse/admin/holds/sethold.jsp><INPUT TYPE=HIDDEN NAME=owdref VALUE=" + owdref + "><INPUT TYPE=SUBMIT NAME=\"Hold Order\" VALUE=\"Hold Order\"></FORM>";

            } else if (holder.getIsOnWhHold() == null || holder.getIsOnWhHold().intValue() == 0) {
                return "<FORM METHOD=POST ACTION=/internal/warehouse/admin/holds/sethold.jsp><INPUT TYPE=HIDDEN NAME=owdref VALUE=" + owdref + "><INPUT TYPE=SUBMIT NAME=\"Hold Order\" VALUE=\"Hold Order\"></FORM>";
            } else {
                return "<B>" + holder.getWhHoldReason() + "</B><P><FORM METHOD=POST ACTION=/internal/warehouse/admin/holds/resolvehold.jsp><INPUT TYPE=HIDDEN NAME=owdref VALUE=" + owdref + "><INPUT TYPE=SUBMIT NAME=\"Resolve Hold\" VALUE=\"Resolve Hold\"></FORM>";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }


}

