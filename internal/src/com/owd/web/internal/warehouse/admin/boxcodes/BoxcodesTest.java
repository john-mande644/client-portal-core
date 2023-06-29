/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 9, 2007
 * Time: 11:11:36 AM
 * To change this template use File | Settings | File Templates.
 */

package com.owd.web.internal.warehouse.admin.boxcodes;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdBoxtypes;
import com.owd.hibernate.generated.OwdBoxtypesMethods;
import junit.framework.TestCase;
import org.hibernate.Session;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BoxcodesTest extends TestCase {
private final static Logger log =  LogManager.getLogger();

    public BoxcodesTest(String test) {
        super(test);
    }

    Session sess;

    /**
     * The fixture set up called before every test method.
     */
    protected void setUp() throws Exception {
        sess = HibernateSession.currentSession();
    }

    /**
     * The fixture clean up called after every test method.
     */
    protected void tearDown() throws Exception {
        HibernateSession.closeSession();
    }

    public void testAddMethodToNewBoxcode() throws Exception {

        OwdBoxtypes box = BoxTypesDAOService.getNewBoxcode();
        Set set = new HashSet();
        OwdBoxtypesMethods method2  = new OwdBoxtypesMethods("TEST_METHOD2", box);
        OwdBoxtypesMethods method = new OwdBoxtypesMethods("TEST_METHOD", box);
        //log.debug("adding method");
        set.add(method);
        //log.debug("adding method 2");
        set.add(method2);
        //log.debug("size="+set.size());
        //log.debug("updating shipMethods");
    //    box.getShipMethods().addAll(set);
        //log.debug("size="+box.getShipMethods().size());
     box.getShipMethods().add(new OwdBoxtypesMethods("TEST_METHOD3", box));
     box.getShipMethods().add(new OwdBoxtypesMethods("TEST_METHOD4", box));
        assertTrue("method not added",box.getShipMethods().size()==2);
        assertTrue("Class not equal",(box.getShipMethods().toArray()[0].getClass()).equals(method2.getClass()));
        //log.debug(box.getShipMethods().toArray()[0].getClass());

       // box.getShipMethods().add(method2);
    }

    public void testClientOrderBy() throws Exception
    {

               List clients = HibernateSession.currentSession().createQuery("from OwdClient as client where client.isActive=1 order by client.companyName").list();
              Iterator it = clients.iterator();
        while(it.hasNext())
        {
            //log.debug(((OwdClient)it.next()).getCompanyName());
        }
    }

     public void testItemQuery() throws Exception
    {
    List items = HibernateSession.currentSession().createQuery("from OwdInventory as item where item.owdClient.clientId=? and item.inventoryId=? ")
         .setInteger(0,0==0?55:0)
         .setInteger(1,123)
          .list();
    }

     public void testAllByLocation() throws Exception
    {
    List items = HibernateSession.currentSession().createQuery("from OwdBoxtypes as boxtype where boxtype.location=:code").setString("code","DC1").list();
        log.debug("DC1 records: "+items.size());
    List items2 = HibernateSession.currentSession().createQuery("from OwdBoxtypes as boxtype where boxtype.location=:code").setString("code", "DC6").list();
         log.debug("DC6 records: "+items2.size());
    }

}
