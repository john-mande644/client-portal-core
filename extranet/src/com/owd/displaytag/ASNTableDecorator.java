package com.owd.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.business.Client;
import com.owd.core.business.asn.ASNManager;
import com.owd.hibernate.generated.Asn;
import com.owd.hibernate.generated.Receive;
import com.owd.web.warehouse.asn.ASNHome;
import org.displaytag.decorator.TableDecorator;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 19, 2004
 * Time: 2:13:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ASNTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    public String getLinks()
            throws Exception {
        Asn asn = (Asn) getCurrentRowObject();
        if (asn == null) return "";

        StringBuffer sb = new StringBuffer();
        sb.append("<A HREF=\"./edit?" + ASNHome.kParamAdminAction + "=edit-old&asn_id="
                + asn.getId() + "\"><B>" + ((asn.getReceiveCount() > 0) ? "View" : "Edit") + "</B></A>");
        sb.append("<B>&nbsp;||&nbsp;</B>");
        sb.append("<A HREF=\"./edit?" + ASNHome.kParamAdminAction + "=display-printable&asn_id="
                + asn.getId() + "\" target=\"#newwindisplay\" >Print&nbsp;ASN</A>");


        sb.append("&nbsp;&nbsp;");

        return sb.toString();


    }

    public String getItemCount()
            throws Exception {
        Asn asn = (Asn) getCurrentRowObject();
        if (asn == null) return "";

        return "" + asn.getAsnItems().size();

    }

    public String getStatus()
            throws Exception {
        Asn asn = (Asn) getCurrentRowObject();
        if (asn == null) return "";

        return ASNManager.getAsnStatus(asn.getStatus() + "");

    }

    public String getClient()
            throws Exception {
        Asn asn = (Asn) getCurrentRowObject();
        if (asn == null) return "";

        return Client.getClientForID(asn.getClientFkey() + "").company_name;

    }

    public Integer getUnpostedReceiptCount()
            throws Exception {

        int unposted = 0;
        try
        {
        Asn asn = (Asn) getCurrentRowObject();
        if (asn == null) return new Integer(0);

        Iterator rcvs = asn.getReceives().iterator();

        while (rcvs.hasNext()) {
            unposted += ((Receive) rcvs.next()).getIsPosted() == 1 ? 0 : 1;
        }


        }catch(Exception ex)
        {
            log.debug(ex);
            //throw ex;
        }

        return new Integer(unposted);

    }

    public String getReleaseType()
            throws Exception {
        Asn asn = (Asn) getCurrentRowObject();
        if (asn == null) return "";

        if (asn.getIsAutorelease() == 1) {
            return "Auto";
        } else {
            return "Manual";
        }


    }


}
