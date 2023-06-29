package com.owd.web.warehouse.asn;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.generated.AsnItem;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 8/9/11
 * Time: 4:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class AsnItemSortComparator implements Comparator<AsnItem> {
private final static Logger log =  LogManager.getLogger();
           public int compare(AsnItem o1, AsnItem o2){
               return o1.getInventoryFkey().compareTo(o2.getInventoryFkey());
           }
}
