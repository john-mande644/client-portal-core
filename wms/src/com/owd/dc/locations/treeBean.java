package com.owd.dc.locations;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Mar 5, 2009
 * Time: 10:50:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class treeBean {
    private final boolean hasChildren;
         private final String id;
         private final String label;

         public treeBean(boolean hasChildren, String id, String label) {
                 this.hasChildren = hasChildren;
                 this.id = id;
                 this.label = label;
         }
         public boolean isHasChildren() {
                 return hasChildren;
         }
         public String getId() {
                 return id;
         }
         public String getLabel() {
                 return label;
         }

}
