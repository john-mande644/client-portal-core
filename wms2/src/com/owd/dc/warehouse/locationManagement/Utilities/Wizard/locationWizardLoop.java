package com.owd.dc.warehouse.locationManagement.Utilities.Wizard;

import com.owd.dc.locations.addNewLocation;
import com.owd.dc.locations.locationUtilities;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 8/17/11
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationWizardLoop {

    int beanNumber;
    List<locationWizardBean> locs;
    int parentId;
    Session sess;

    public locationWizardLoop(int indexNumber, List<locationWizardBean> locations, int IdOfParent, Session ss) {
        System.out.println("We are creating a new loop here.");
        System.out.println("We are working on index number " + indexNumber);
        System.out.println("This is the parent Id we are working we wil be adding to: " + IdOfParent);
        beanNumber = indexNumber;
        locs = locations;
        parentId = IdOfParent;

        sess = ss;
    }

    public void doLoop() throws Exception {
        locationWizardBean thisBean = locs.get(beanNumber);
        System.out.println("Doing type" + thisBean.getType());
        List<String> nameList = thisBean.getLocationsToCreateList();
        for (String name : nameList) {

            System.out.println("Creating " + name + " For type " + thisBean.getType());
            String locName = locationUtilities.getLocationName(sess, name, thisBean.getType() + "");
            System.out.println("This is the parent id we are adding to " + parentId);
            int id = addNewLocation.addLocation(sess, parentId + "", locName, thisBean.getType() + "", thisBean.getUser(), thisBean.getIpAddress());

            System.out.println("Got new ID it is " + id);
            if (beanNumber + 1 < locs.size()) {
                locationWizardLoop l = new locationWizardLoop(beanNumber + 1, locs, id, sess);
                l.doLoop();
            }


        }


    }

}
