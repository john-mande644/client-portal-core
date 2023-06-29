package com.owd.dc.beans;

import com.owd.dc.locations.locationUtilities;
import com.owd.dc.warehouse.locationManagement.Utilities.locationInfoLookupUtil;
import com.owd.dc.warehouse.locationManagement.Utilities.locationManagementUtils;
import com.owd.hibernate.generated.WLocation;
import org.hibernate.Session;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Apr 17, 2009
 * Time: 4:27:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class locationInfoBean {
    private String name;
    private Integer locationType;
    private String id;
    private Map<String, String> parents;
    private List<locAttributeBean> locationAttributes;
    private List<locAttributeBean> inventoryAttributes;
    private Map<String, String> allowedChildren;
    private boolean hasInventory;
    private boolean hasDirectInventory;
    private List inventory;
    private Map<String, String> children;
    private String parentString;

    public locationInfoBean(String locId, Session sess) throws Exception {
        id = locId;
        WLocation loc = (WLocation) sess.load(WLocation.class, Integer.valueOf(id));
        name = loc.getLocationName();
        locationType = loc.getLocationType();
        parents = com.owd.dc.locations.locationUtilities.getParentMapForId(sess, locId);
        System.out.println("done with parent mape");
        locationAttributes = new ArrayList<locAttributeBean>();
        inventoryAttributes = new ArrayList<locAttributeBean>();
        locationInfoLookupUtil.loadAttributes(sess, locationAttributes, inventoryAttributes, locId);
        allowedChildren = new TreeMap<String, String>();
        locationInfoLookupUtil.loadAllowedChildren(sess, allowedChildren, loc.getLocationType() + "");
        children = new TreeMap<String, String>();
        locationManagementUtils.getChildrenMapForParent(sess, children, id);
        hasDirectInventory = locationUtilities.locationHasDirectInventory(sess, id);

    }

    public String getParentString() {
        StringBuffer s = new StringBuffer();
        try {
            if (parents.size() > 0) {
                Iterator it = parents.values().iterator();
                while (it.hasNext()) {
                    s.append(it.next());
                    if (it.hasNext()) s.append(", ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s.toString();
    }

    public Map<String, String> getAllowedChildren() {
        return allowedChildren;
    }

    public void setAllowedChildren(Map<String, String> allowedChildren) {
        this.allowedChildren = allowedChildren;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getParents() {
        return parents;
    }

    public void setParents(Map<String, String> parents) {
        this.parents = parents;
    }

    public List<locAttributeBean> getLocationAttributes() {
        return locationAttributes;
    }

    public void setLocationAttributes(List<locAttributeBean> locationAttributes) {
        this.locationAttributes = locationAttributes;
    }

    public List<locAttributeBean> getInventoryAttributes() {
        return inventoryAttributes;
    }

    public void setInventoryAttributes(List<locAttributeBean> inventoryAttributes) {
        this.inventoryAttributes = inventoryAttributes;
    }

    public boolean getHasInventory() {
        return hasInventory;
    }

    public void setHasInventory(boolean hasInventory) {
        this.hasInventory = hasInventory;
    }

    public List getInventory() {
        return inventory;
    }

    public void setInventory(List inventory) {
        this.inventory = inventory;
    }

    public Map<String, String> getChildren() {
        return children;
    }

    public void setChildren(Map<String, String> children) {
        this.children = children;
    }

    public boolean getHasDirectInventory() {
        return hasDirectInventory;
    }

    public void setHasDirectInventory(boolean hasDirectInventory) {
        this.hasDirectInventory = hasDirectInventory;
    }

    public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
        this.locationType = locationType;
    }
}
