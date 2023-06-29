package com.owd.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.autoship.AutoShipManager;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.web.autoship.AutoShipHome;
import org.displaytag.decorator.TableDecorator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 19, 2004
 * Time: 2:13:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AutoShipTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    public String getLinks()
            throws Exception {
        OwdOrderAuto aship = (OwdOrderAuto) getCurrentRowObject();
        if (aship == null) return "";

        StringBuffer sb = new StringBuffer();
        sb.append("<A HREF=\"./edit?" +
                AutoShipHome.kParamAdminAction + "=auto-edit&sub_id="
                + aship.getAutoShipId() + "\"><B>Edit</B></A>");


        sb.append("&nbsp;&nbsp;");

        return sb.toString();


    }

    public String getSubscriptionStatus()
            throws Exception {
        OwdOrderAuto aship = (OwdOrderAuto) getCurrentRowObject();
        if (aship == null) return "";

        return "" + AutoShipManager.getAutoShipStatus(aship.getStatus() + "");

    }

    public String getItemCount()
            throws Exception {
        OwdOrderAuto aship = (OwdOrderAuto) getCurrentRowObject();
        if (aship == null) return "";

        return "" + aship.getOwdOrderAutoItems().size();

    }

    public String getClient()
            throws Exception {
        OwdOrderAuto aship = (OwdOrderAuto) getCurrentRowObject();
        if (aship == null) return "";

        String cname = "Unknown";

        try {
            cname = Client.getClientForID(aship.getClientFkey() + "").company_name;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return cname;

    }

    public String getAddress()
            throws Exception {
        OwdOrderAuto aship = (OwdOrderAuto) getCurrentRowObject();
        if (aship == null) return "";

        String cname = "Unknown";

        try {
            cname = aship.getShipAddressOne()+" "+aship.getShipZip();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return cname;

    }

    public String getCity()
            throws Exception {
        OwdOrderAuto aship = (OwdOrderAuto) getCurrentRowObject();
        if (aship == null) return "";

        String cname = "Unknown";

        try {
            cname = aship.getShipCity()+" "+aship.getShipState();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return cname;

    }


}
