package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.generated.OwdBoxtypes;
import org.displaytag.decorator.TableDecorator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 19, 2004
 * Time: 2:13:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BoxCodesTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    public String getLinks()
            throws Exception {
        OwdBoxtypes box = (OwdBoxtypes) getCurrentRowObject();
        if (box == null) return "";

        StringBuffer sb = new StringBuffer();

        sb.append("<A HREF=\"./crud!input.action?" +
                "boxcode.Id=" + box.getId() + "\"><B>Edit</B></A>");


        sb.append("&nbsp;&nbsp;");
        sb.append("<A onclick=\"return confirm('Are you sure you want to delete this box type? This action cannot be undone.')\" HREF=\"./crud!delete.action?" +
                "boxcode.Id=" + box.getId() + "\"><B>Delete</B></A>");


        sb.append("&nbsp;&nbsp;");
        return sb.toString();


    }

    public String getClientAndSkuData()
            throws Exception {
        OwdBoxtypes box = (OwdBoxtypes) getCurrentRowObject();
        if (box == null) return "";

        StringBuffer sb = new StringBuffer();

        if (box.getOwdClient() != null) {
            sb.append(box.getOwdClient().getCompanyName());

        }
          if (box.getOwdInventory() != null) {
                sb.append((box.getOwdClient()==null?"Any Client":"")+"<BR><B>" + box.getOwdInventory().getInventoryNum() + " - " + box.getOwdInventory().getDescription() + "</B>");
            }
        sb.append("&nbsp;&nbsp;");
        return sb.toString();


    }

}
