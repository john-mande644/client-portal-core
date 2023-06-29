import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.OrderStatus;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.managers.FacilitiesManager;
import com.owd.core.xml.OrderXMLDoc;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.engine.spi.SessionImplementor;


import java.util.*;

class Quicksort {

    private final static Logger log =  LogManager.getLogger();

    public static void main(String args[])  // entry point from OS
    {
        log.debug("501:"+ OWDUtilities.encryptData("501"));

    }


    public static void repostorder(int orderid)
    {

        try{
        OrderStatus order = new OrderStatus(""+orderid);
        order.unpostOrder();

            Map skuMap = OrderUtilities.updateLineItemsForAvailability( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection(), order.items, OrderXMLDoc.kBackOrderAll, true, FacilitiesManager.getFacilityForCode(order.shipLocation).getId() );

            String backorderRef = OrderUtilities.shipExistingOrder(order);

        HibUtils.commit(HibernateSession.currentSession());
        }catch(Exception ex)
        {
            log.debug("ERROR:"+orderid);
            ex.printStackTrace();
        }
    }

}  /*  end of program  */



