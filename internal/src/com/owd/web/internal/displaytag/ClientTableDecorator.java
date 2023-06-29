package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.generated.OwdClient;
import com.owd.web.internal.client.ClientHome;
import org.displaytag.decorator.TableDecorator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 19, 2004
 * Time: 2:13:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    public String getLinks()
            throws Exception {
        OwdClient client = (OwdClient) getCurrentRowObject();
        if (client == null) return "";

        StringBuffer sb = new StringBuffer();

         sb.append("<A HREF=\"./edit?" +
                    ClientHome.kParamAdminAction + "=client-edit&client_id="
                    + client.getClientId() + "\"><B>Edit</B></A>");


            sb.append("&nbsp;&nbsp;");
         sb.append("<A target=\"_sla\" HREF=\"./edit?" +
                    ClientHome.kParamAdminAction + "=client-sla&client_id="
                    + client.getClientId() + "\"><B>SLA</B></A>");
        sb.append("&nbsp;&nbsp;");
               sb.append("<A target=\"_acct\" HREF=\"./edit?" +
                          ClientHome.kParamAdminAction + "=client-acct&client_id="
                          + client.getClientId() + "\"><B>Accounts</B></A>");


            sb.append("&nbsp;&nbsp;");
        return sb.toString();



    }


}
