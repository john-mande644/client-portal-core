package com.owd.dc.locations;

import com.owd.core.managers.FacilitiesManager;
import com.owd.dc.inventory.beans.locationPriority;
import com.owd.dc.picking.itemSortingUtilities;
import com.owd.dc.picking.locationListBean;
import com.owd.hibernate.HibernateSession;
import com.owd.dc.inventory.upcBarcodeUtilities;
import com.owd.hibernate.generated.OwdLotValue;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import java.awt.datatransfer.StringSelection;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Feb 17, 2005
 * Time: 8:39:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class LocationBarcodeUtilities {

    public static String LOC_PREFIX_PICK = "P"; //pick locations
    public static String LOC_PREFIX_REPLENISH = "R";  //replenishment, non-pallet locations
    public static String LOC_PREFIX_SECONDARY = "S";  //pallet storage

    public static final String REGEX_STANDARD_PICK_LOC_PATTERN = "^/([a-zA-Z]{1})[\\s]?([0-9]{2})[\\s]?([a-zA-Z]{1})[\\s]?([0-9]{2})[\\s]?([a-zA-Z]{1})?(--.*)*$";
    public static final String REGEX_STANDARD_PALLET_LOC_PATTERN = "^/([a-zA-Z]{1})[\\s]?([0-9]{2})[\\s]?([0-9]{2})[\\s]?([a-zA-Z]{1})[\\s]?(--.*)*$";


    static Pattern pickPattern = Pattern.compile(REGEX_STANDARD_PICK_LOC_PATTERN);
    static Pattern palletPattern = Pattern.compile(REGEX_STANDARD_PALLET_LOC_PATTERN);

    /*
      Current rules for location identification:

      All valid locations begin with "/"
      All locations match one of the two regex patterns above
      All locations have at least 4 groups
      Group 4 for pick locations is numeric (2 chars)
      Group 4 for pallet locations is alpha (1 char)
      If location is a pick location and value of group 4 >= 5, it is a replenishment secondary non-pallet location
      If location is a pick location and the value of group 4 < 5, it is a pick shelf location


    */

    public static void main(String[] args)
    {

        try
        {
            List<locationPriority> a = getLocationsForInventoryIDList(121672,HibernateSession.currentSession(),"DC1");
            System.out.println(a.size());
            locationPriority lp = a.get(0);
             System.out.println(lp.getLot().getLotValue());

          /* String alist = getLocationsForInventoryID(121672,HibernateSession.currentSession(),"DC1");
            System.out.println("list:"+alist);
           String[] ss =alist.split("\r");
            for (String s : ss){
                System.out.println(s);
            }*/

        } catch(Exception ex)
        {
               ex.printStackTrace();
        }   finally
        {
            HibernateSession.closeSession();
        }

    }

    /**
     * Gets all known locations for the given inventory ID value
     * Optionally format location by adding a prefix letter according to location type
     * Only returns valid location values
     * Corrects and removes any internal whitespace in barcode
     *
     * @param inventoryID
     * @param sess        - caller is responsible to create and close this Hibernate Session object. This method does not
     *                    affect the current transaction, if any
     * @return ArrayList of LocationAssignment objects - could be zero or more
     */
    public static String getLocationsForInventoryID(int inventoryID, Session sess,String facility) {

        List<locationListBean> newLocList = new ArrayList<locationListBean>();
        List<LocationAssignment> locList = new ArrayList();

        try {
            String facNode = FacilitiesManager.getFacilityForCode(facility).getWlocNodeFkey()+"";
       /*     String sql = "select location,max(assign_date) as 'assign_date' from owd_inventory (NOLOCK) left outer join owd_inventory_locations (NOLOCK)" +
                    " on inventory_id=inventory_fkey and left(location,1)='/' where inventory_id = " + inventoryID+" group by location";*/
            String sql = "SELECT\n" +
                    "    location,\n" +
                    "    formatedPickString,\n" +
                    "    SortString\n" +
                    "FROM\n" +
                    "    owd_inventory_locations ,\n" +
                    "    w_location\n" +
                    "LEFT OUTER JOIN dbo.w_location_tree\n" +
                    "ON\n" +
                    "    (\n" +
                    "        dbo.w_location.ixNode = dbo.w_location_tree.ixNode\n" +
                    "    )\n" +
                    "WHERE\n" +
                    "    location = '//' + CAST(w_location.ixNode AS VARCHAR(50))\n" +
                    "AND inventory_fkey = "+inventoryID+"\n" +
                    "AND w_location_tree.ixParent="+facNode+"\n" +
                    "GROUP BY\n" +
                    "    location,\n" +
                    "    formatedPickString,\n" +
                    "    SortString\n" +
                    "ORDER BY\n" +
                    "    SortString";
           // System.out.println(sql);
            ResultSet rs = HibernateSession.getResultSet(sess, sql);

             boolean newLocations=false;
            while (rs.next()) {
              //  System.out.println("loading loc");
                  if(rs.getString("location").startsWith("//")){
                      newLocations = true;
                      locationListBean llb = new locationListBean();
                      llb.setLocation(rs.getString("formatedPickString"));
                      llb.setSort(rs.getString("SortString"));
                     newLocList.add(llb);

                  } else{
                     // System.out.println("adding loc");

                   locList.add(new LocationAssignment(getDisplayableBarcodeValue(rs.getString("location")), rs.getTimestamp("assign_date")));
                  }

            }
            if (newLocations){
                System.out.println("is new loc");
               //   System.out.println(locList.size());
               // System.out.println("This is the location list");
              //  System.out.println(locList);
              //  System.out.println("Done printing list");
                  if(locList.size()>0){
                    //  System.out.println("this is teh size" +newLocList.size());
                      for(LocationAssignment s :  locList) {
                     //     System.out.println(s.getBarcode());
                      locationListBean llb = new locationListBean();
                                            llb.setLocation((s.getBarcode()));
                                            llb.setSort((s.getBarcode()));
                                           newLocList.add(llb);
                      }
                  }
              //  System.out.println("this is teh size" +newLocList.size());
               return itemSortingUtilities.sortPickItemLocationList(newLocList);
            } else{
              //  System.out.println("no new loc");

            Collections.sort(locList);
              Iterator itl = locList.iterator();
               StringBuffer locBuffer = new StringBuffer();

                        while (itl.hasNext()) {
                            locBuffer.append(((LocationBarcodeUtilities.LocationAssignment) itl.next()).getBarcode() + "\r");
                        }
                if(locBuffer.length()==0){
                    locBuffer.append("No Locations Assigned?");
                }
               return locBuffer.toString();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
           HibernateSession.closeStatement();
        }
        return "No Locations Assigned";
    }

    public static List<locationPriority> getLocationsForInventoryIDList(int inventoryID, Session sess,String facility) {


        List<locationPriority> locList = new ArrayList<locationPriority>();
        try {
            System.out.println(inventoryID);
            System.out.println(facility);
             String sql = "execute getLocationsPrioritySortedAll :id,:facility,1";
            Query q = sess.createSQLQuery(sql);
            q.setParameter("id",inventoryID);
            q.setParameter("facility",facility);
            q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            List results = q.list();
            System.out.println(results.size() + " of locations");
            for (Object row : results){
                Map data = (Map)row;
                locationPriority lp = new locationPriority();
                lp.setLocation(data.get("formatedPickString").toString());
                switch (Integer.parseInt(data.get("pickPriority").toString())){
                    case 1: lp.setPriority("Primary");
                        break;
                    case 2: lp.setPriority("Secondary");
                        break;
                    default:lp.setPriority("Default");
                        break;
                }
                lp.setAssignDate(data.get("assign_date").toString().substring(0,10));
                lp.setNotes(data.get("note").toString());
                if(data.get("lot_fkey")!=null){
                    OwdLotValue lot = (OwdLotValue) sess.load(OwdLotValue.class,new Integer (data.get("lot_fkey").toString()));
                    lp.setLot(lot);
                }
                locList.add(lp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeStatement();
        }
        return locList;
    }

    public static List getLocationBarcodesForInventoryIDList(int inventoryID, Session sess,String facility) {


        List locList = new ArrayList();
        try {
            String sql = "SELECT\n" +
                    "    dbo.owd_inventory_locations.location,\n" +
                    "    dbo.owd_inventory_locations.assign_date\n" +
                    "FROM\n" +
                    "    dbo.owd_inventory_locations\n" +
                    "LEFT OUTER JOIN dbo.w_location_tree\n" +
                    "ON\n" +
                    "    (\n" +
                    "        CASE\n" +
                    "when location = 'UNKNOWN' then 1\n" +
                    "      else CAST(Replace(dbo.owd_inventory_locations.location,'//','') as int)\n" +
                    "end = dbo.w_location_tree.ixNode\n" +
                    "    ) where inventory_fkey = "+inventoryID+" and ixParent = "+ FacilitiesManager.getFacilityForCode(facility).getWlocNodeFkey();

            ResultSet rs = HibernateSession.getResultSet(sess, sql);


            while (rs.next()) {


                   locList.add(rs.getString("location"));


            }

            Collections.sort(locList);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateSession.closeStatement();
        }
        return locList;
    }

    /**
     * Modifies provided value to remove internal spaces and identify with type prefix letter.
     * Provided to support older labels with internal spaces or other issues
     *
     * @param rawBarcode
     * @return the modified barcode value string (empty string if null)
     */
    public static String getDisplayableBarcodeValue(String rawBarcode) {
           if(rawBarcode.startsWith("//")){
               try{
                       locationInfoBean lib = new locationInfoBean(rawBarcode.replace("//",""),HibernateSession.currentSession());
                    return lib.getFormatedPickString();
               }catch (Exception e){
                  e.printStackTrace();
               }
           }
        if (rawBarcode == null) return "";
       System.out.println(rawBarcode);
        rawBarcode = rawBarcode.replaceAll(" ", "");
        System.out.println(rawBarcode);
        System.out.println("test");
        if(rawBarcode.toUpperCase().endsWith("--DC2")){
            return LOC_PREFIX_SECONDARY + rawBarcode;
        }
        Matcher pickMatch = pickPattern.matcher(rawBarcode);
        if (pickMatch.matches()) {

            String zone = new String(pickMatch.group(1)).toString();
            int aisle = new Integer(pickMatch.group(2)).intValue();
            int shelfValue = new Integer(pickMatch.group(4)).intValue(); //shelf value, guaranteed to exist and be numeric by regex pattern
            if(zone.equals("A")&& (aisle == 4 || aisle ==5 || aisle==6 || aisle==7 || aisle==8)){
                if(shelfValue >5){
                    return LOC_PREFIX_REPLENISH + rawBarcode;
                } else{
                    return LOC_PREFIX_PICK + rawBarcode;
                }
            } else{


            if (shelfValue >= 5) {
                return LOC_PREFIX_REPLENISH + rawBarcode;

            } else {
                return LOC_PREFIX_PICK + rawBarcode;
            } }
        } else {
            Matcher palletMatch = palletPattern.matcher(rawBarcode);
            if (palletMatch.matches()) {
                return LOC_PREFIX_SECONDARY + rawBarcode;
            } else {
                return "";
            }
        }

    }

    public static class LocationAssignment implements Comparable
    {

        String barcode = null;
        Date assigned = null;

        public LocationAssignment(String barcode, Date assigned) {
                this.barcode = barcode;
                this.assigned = assigned;
            }

        public String getBarcode() {
             return barcode;
        }



        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public Date getAssigned() {
            return assigned;
        }

        public void setAssigned(Date assigned) {
            this.assigned = assigned;
        }

        public int compareTo(Object la)
        {
            int firstSortLevelResult = getBarcode().substring(0,1).compareTo(((LocationAssignment)la).getBarcode().substring(0,1));

            if (firstSortLevelResult==0)
            {
                return (-1*((getAssigned().compareTo(((LocationAssignment)la).getAssigned()))));
            }   else
            {
                return firstSortLevelResult;
            }

        }
        public String toString()
        {
            return "[("+getBarcode()+"),("+getAssigned()+")]";
        }


    }
}
