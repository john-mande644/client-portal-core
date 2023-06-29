package com.owd.dc.inventory;

import com.owd.dc.inventorycount.historyBean;
import com.owd.dc.locations.locationInfoBean;
import com.owd.hibernate.*;
import com.owd.dc.setup.selectList;
import com.owd.dc.inventorycount.inventoryCountBean;
import com.owd.dc.receiving.beans.asnDTO;
import com.owd.hibernate.generated.*;
import org.hibernate.Session;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.Query;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Dec 22, 2005
 * Time: 4:01:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class inventoryUtilities {

    public static List getClientList() throws Exception {

        List clientList = new ArrayList();
        String query = "SELECT     client_id, company_name\n" +
                "FROM         owd_client (NOLOCK) \n" +
                "WHERE     (is_active = 1)\n" +
                "ORDER BY company_name";

        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
        int i = 0;
        while (rs.next()) {
            selectList btn = new selectList();
            btn.setAction(rs.getString(1));
            btn.setDisplay(rs.getString(2));
            clientList.add(i, btn);
            i++;
         //   System.out.println(rs.getString(1));
        }

        return clientList;
    }

    public static String getRequestId(String id) throws Exception {
        String retID = new String();
        if (id.startsWith("/") == false) {
            retID = id;
        } else {
            String query = "select id from warehouse_inventory  (NOLOCK) where location_to_assign = '" + id + "'";
            ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
            if (rs.next()) {
                retID = rs.getString(1);
            } else {
                throw new Exception("Location not associated with any Requests");
            }


        }

        return retID;
    }

    public static void insertLocation(WarehouseInventoryCounts wcount, String location) throws Exception {
        String Query = "select count(*)  from dbo.owd_inventory_locations  (NOLOCK) where inventory_fkey = " +
                wcount.getInventoryId() + "and location = '" + location + "'";
        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), Query);
        rs.next();
        if (rs.getString(1).equals("0")) {
            Calendar cal = Calendar.getInstance();
            String DATE_FORMAT = "MM/dd/yy hh:mm:ss";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
           // Connection cxn = ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();
            String sql = null;


            System.out.println("setting sql");
            Date date = cal.getTime();
            sql = "insert into dbo.owd_inventory_locations ( location, assigned_by, inventory_fkey, assign_date) values ('" + location + "','" +
                    wcount.getName() + "','" + wcount.getInventoryId() + "','" + sdf.format(date) + "')";
            //sql = "insert owd_inventory_location set location = ?, assigned_by = ?, inventory_fkey = ?, assign_date = ?";

            Query q = HibernateSession.currentSession().createSQLQuery(sql);
           // PreparedStatement stmt = cxn.prepareStatement(sql);

            int rowsUpdated = q.executeUpdate();

            //	if (rowsUpdated != 1)
            //		throw new Exception("Could not update database - location not assigned");
            System.out.println("Set new location for"+wcount.getInventoryId() + " to "+location);
            HibUtils.commit(HibernateSession.currentSession());
            
        }

    }

    public static WarehouseInventory getWarehouseInventoryFromId(String id)throws Exception{
         WarehouseInventory wi = (WarehouseInventory) HibernateSession.currentSession().load(WarehouseInventory.class,  new Integer(Integer.parseInt(id)));
      return wi;
    }

    public static List getInventoryList(int Id) throws Exception{
        List countList = new ArrayList();
        String query = "select inventory_id, inventory_num,  SUM(quanity) from warehouse_inventory_counts  (NOLOCK) where warehouse_inventory_fkey = "+ Id +" group by inventory_id, inventory_num order by inventory_id";
        ResultSet rs = HibernateSession.getResultSet(HibernateSession.currentSession(), query);
        int i = 0;
        while (rs.next()){
           inventoryCountBean iB = new inventoryCountBean();
            iB.setInventoryId(rs.getString(1));
            iB.setInventoryNum(rs.getString(2));
            iB.setQuanity(rs.getString(3));
            countList.add(i,iB);
            i++ ;
        }
        return countList;
    }

     public static void updateReceiveFromEditPage(Receive rcv, WarehouseInventory wi, String user, asnDTO rcvDTO, List items) throws Exception {

        Session sess = HibernateSession.currentSession();
        sess.saveOrUpdate(rcv);
        sess.saveOrUpdate(rcv.getAsn());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mmaa");
        rcv.setCreatedBy(user);
          long minutes = rcvDTO.getMinutes();
         Date date = Calendar.getInstance().getTime();
        rcv.setCreatedOn(date);
        rcv.setEndTimestamp(Calendar.getInstance().getTime());

        rcv.setStartTimestamp(date);
        rcv.setIsAsnFound(1);
        rcv.setIsPackSlipFound(0);
        rcv.setIsPackSlipMatch(0);
        rcv.setNotes(rcvDTO.getNotes());

        String receiveBy = rcvDTO.getReceivedBy();
        if (receiveBy.length() > 30) receiveBy = receiveBy.substring(0, 30);
        System.out.println("Received By: " + receiveBy);
        rcv.setReceiveBy(receiveBy);


        sess.saveOrUpdate(rcv);
        com.owd.hibernate.HibUtils.commit(sess);
        sess.flush();

        if (minutes < 1) {
            throw new Exception("You must set start and end times to reflect a duration of at least 1 minute");
        }
        rcv.setBilledMinutes(rcvDTO.getMinutes());
        try {
            rcv.setCartonCount(rcvDTO.getCarton());
        } catch (NumberFormatException nex) {
            throw new Exception("Carton count must be a whole number greater or equal to zero");
        }
        try {
            rcv.setPalletCount(rcvDTO.getPallet());

        } catch (NumberFormatException nex) {
            throw new Exception("Pallet count must be a whole number greater or equal to zero");
        }

        if (rcv.getPalletCount() + rcv.getCartonCount() < 1) {
            throw new Exception("Pallet or carton count must be a whole number greater or equal to 1");
        }

        Collection oldAsnItems = rcv.getAsn().getAsnItems();


        rcv.setReceiveItems(new ArrayList());

       // Enumeration enum = req.getParameterNames();
       // while (enum.hasMoreElements()) {
        Iterator itt = items.iterator();
         while(itt.hasNext()) {
             inventoryCountBean iB = (inventoryCountBean) itt.next();
             String sku =  iB.getInventoryNum();
            /*String name = (String) enum.nextElement();
            if (name.endsWith("_rcv_qty")) {
                String sku = name.substring(0, name.indexOf("_rcv_qty"));
                System.out.println("found sku " + sku);
                int rqty = 0;
                int dqty = 0;
                try {
                    System.out.println("decoding rqty");
                    rqty = Integer.decode((String) req.getParameter(name)).intValue();
                    System.out.println("decoding dqty");
                    dqty = Integer.decode((String) req.getParameter(sku + "_dmg_qty")).intValue();
                    System.out.println(rqty + ":" + dqty);

                    if (rqty < 0) throw new Exception("SKU quantities must be whole numbers >= 0");
                    if (dqty < 0) throw new Exception("SKU quantities must be whole numbers >= 0");
                } catch (NumberFormatException nex) {
                    throw new Exception("SKU quantities must be whole numbers >= 0");
                }*/


                ReceiveItem ritem = new ReceiveItem();
                
                ritem.setQtyDamage(0);
                ritem.setQtyReceive(Integer.parseInt(iB.getQuanity()));
                ritem.setReceive(rcv);
                ritem.setToLocation("");
                ritem.setNotes("");
                ritem.setQtyPackslip(0);

                rcv.getReceiveItems().add(ritem);


                Iterator it = oldAsnItems.iterator();
                boolean notFound = true;
                while (it.hasNext() && notFound) {
                    AsnItem item = (AsnItem) it.next();
                    if (item.getInventoryNum().equals(sku)) {
                        System.out.println("in if");
                        System.out.println("Doing "+sku);
                        /*Float weight = new Float(0.0);
                        try {
                            weight = new Float(req.getParameter(sku + "_weight"));
                        } catch (NullPointerException npe) {
                        } catch (NumberFormatException nfe) {
                        }
                        System.out.println("weight for " + sku + " " + weight);
                        if (weight.compareTo(new Float(0.0)) > 0) {
                            OwdInventory inv = (OwdInventory) HibernateSession.currentSession().load(OwdInventory.class, new Integer(item.getInventoryFkey()));
                            inv.setWeightLbs(weight);
                            HibernateSession.currentSession().saveOrUpdate(inv);
                            //  com.owd.hibernate.HibUtils.commit(HibernateSession.currentSession());
                        }*/
                        notFound = false;
                        if (null == item.getReceiveItems()) {
                            item.setReceiveItems(new TreeSet());
                        }
                       // item.setReceived(Integer.parseInt(iB.getQuanity()));
                      //  item.getReceiveItems().add(ritem);
                         System.out.println("before 3");
                        ritem.setAsnItem(item);
                         System.out.println("before 4");
                        ritem.setInventoryFkey(item.getInventoryFkey());

                    }
                }
            }
        }

public static boolean itemInASN( Asn asn, List items){
    boolean allThere = false;
   Collection asnItems = asn.getAsnItems();
    Iterator ait = asnItems.iterator();
    List asnIdList = new ArrayList();
    while(ait.hasNext()){
       AsnItem item = (AsnItem) ait.next();
       System.out.println(item.getInventoryFkey());
      asnIdList.add(item.getInventoryFkey()+"");
    }
    Iterator it = items.iterator();
    int i =1;
    while(it.hasNext()){
        inventoryCountBean iB = (inventoryCountBean) it.next();
      if(asnIdList.contains(iB.getInventoryId())){
         System.out.println("matchsId");
      }else if (asnIdList.contains(iB.getInventoryNum())){
          System.out.println("matchesNum");
      }else{
          System.out.println("no match"+i);
        allThere = false;
        return allThere;
      }
        i++;
    }

    allThere = true;

    return allThere;
}



    public static List<historyBean> getLocationHistoryFromId(String inventoryId ) {
        List<historyBean> locs = new ArrayList<historyBean>();
        try{
           String sql = "select location, assign_date, assigned_by, deleted_date from zz_deleted_locations  (NOLOCK) where \n"+
                   "inventory_fkey = :inventoryId";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("inventoryId",inventoryId);
            List results = q.list();
            if(results.size()>0){
                for(Object row:results) {
                    Object[] data = (Object[]) row;
                    System.out.println("in while" + data[0].toString());
                    historyBean hs = new historyBean();
                    hs.setLocation(data[0].toString());
                    hs.setAssignDate(data[1].toString());
                    hs.setUser(data[2].toString());
                    hs.setRemovedate(data[3].toString());
                    if (hs.getLocation().startsWith("//")) {
                        locationInfoBean lib = new locationInfoBean(hs.getLocation().replace("//", ""), HibernateSession.currentSession());
                        hs.setReadableLocation(lib.getFormatedPickString());
                    } else {
                        hs.setReadableLocation(hs.getLocation());
                    }
                    locs.add(hs);
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }


        return locs;

    }

    public static void main(String[] args){
        List<historyBean> l = getLocationHistoryFromId("36351");
        for(historyBean h:l){
            System.out.println(h.getLocation());
            System.out.println(h.getReadableLocation());
            System.out.println(h.getUser());
            System.out.println(h.getAssignDate());
            System.out.println(h.getRemovedate());

        }
    }
}
