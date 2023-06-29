package com.owd.jobs.jobobjects.utilities

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager
import com.owd.hibernate.generated.OwdLineItem

/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk1
 * Date: 8/30/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
 public abstract class LineItemVerifier {
private final static Logger log =  LogManager.getLogger();

     abstract public boolean includeLineItemInRequest(OwdLineItem line);
}
