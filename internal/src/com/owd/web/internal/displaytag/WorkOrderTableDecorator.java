package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.displaytag.decorator.TableDecorator;
import org.apache.commons.beanutils.DynaBean;
import com.owd.web.internal.workorders.WorkOrder;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 19, 2004
 * Time: 2:13:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkOrderTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    public String getBugLink()
            throws Exception {

            DynaBean bean = (DynaBean) getCurrentRowObject();

            if (bean == null) return "";

            //log.debug("id=" + bean.get("ixBug"));

        if (bean.get("ixBug") == null) return "";

        return "<A HREF=\"http://abweb2.internal.owd.com/casetracker/default.asp?pg=pgEditBug&command=view&ixBug=" + bean.get("ixBug") + "\" target=###_###><B>View</B></>";


    }


}
