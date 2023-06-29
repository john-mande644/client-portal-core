package com.owd.web.internal.displaytag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.displaytag.decorator.TableDecorator;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: May 19, 2004
 * Time: 2:13:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocationContentsTableDecorator extends TableDecorator {
private final static Logger log =  LogManager.getLogger();

    public String getInventoryLocationID()
            throws Exception {
        String id = (String) getCurrentRowObject();
        if (id == null) return "";

        return "";

    }


}
