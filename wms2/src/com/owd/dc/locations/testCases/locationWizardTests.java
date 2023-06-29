package com.owd.dc.locations.testCases;

import com.owd.dc.warehouse.locationManagement.Utilities.Wizard.locationWizardBean;
import com.owd.dc.warehouse.locationManagement.Utilities.Wizard.locationWizardLoop;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 8/17/11
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationWizardTests extends TestCase {

    public void setUp() {


    }

    //  @After
    public void tearDown() {

    }

    public void testWizardBeanNames() {
        locationWizardBean lwb = new locationWizardBean();
        lwb.setDoSequence(true);
        lwb.setStartNumber(5);
        lwb.setNumberToDo(6);

        List l = lwb.getLocationsToCreateList();
        assertTrue(l.size() == 6);
        assertTrue(l.get(2).equals("7"));
        System.out.println(l);
        lwb.setDoSequence(false);
        lwb.setNameList("1,2,3,4,5,6");
        l = lwb.getLocationsToCreateList();
        System.out.println(l);
        assertTrue(l.size() == 6);
        assertTrue(l.get(2).equals("3"));

        lwb.setNameList("1\r\n2\r\n9\r\n4\r\n5\r\n6");
        l = lwb.getLocationsToCreateList();
        assertTrue(l.size() == 6);
        assertTrue(l.get(2).equals("9"));

    }

    public void testWizardLoop() {

        locationWizardBean lwb = new locationWizardBean();
        lwb.setDoSequence(true);
        lwb.setStartNumber(4);
        lwb.setNumberToDo(2);
        lwb.setType(7);
        lwb.setUser("Unit Test");
        lwb.setIpAddress("192.168.10.2");
        List<locationWizardBean> l = new ArrayList<locationWizardBean>();
        l.add(lwb);
        locationWizardBean lwb2 = new locationWizardBean();
        lwb2.setType(8);
        lwb2.setUser("Unit Test");
        lwb2.setIpAddress("192.168.10.2");
        lwb2.setDoSequence(false);
        lwb2.setNameList("1,2,3,4,5");
        l.add(lwb2);
        try {
            locationWizardLoop loop = new locationWizardLoop(0, l, 36432, HibernateSession.currentSession());
            loop.doLoop();
            HibUtils.commit(HibernateSession.currentSession());


        } catch (Exception e) {
            e.printStackTrace();

        }


    }
}
