package com.owd.dc.warehouse.supplyTracking;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdInventory;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 9/17/12
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class supplyTrackingBean {

    private int inventoryId;
    private String inventoryNum;
    private String description;
    private String email;
    private int sort_order;
    private boolean active;
    private int threshold;
    private String facility;
    private String groupType;
    private String countType;

     public void load(String invId,Session sess) throws Exception{
         load(Integer.parseInt(invId),sess);
     }
     public void load(int invId,Session sess) throws Exception{
         String sql="SELECT\n" +
                 "   \n" +
                 "    dbo.owd_inventory.inventory_num,\n" +
                 "    dbo.owd_inventory.description,\n" +
                 "    dbo.supply_tracking.email,\n" +
                 "    dbo.supply_tracking.sort_order,\n" +
                 "    dbo.supply_tracking.active,\n" +
                 "    dbo.supply_tracking.threshold,\n" +
                 "    dbo.supply_tracking.facility,\n" +
                 "    dbo.supply_tracking.group_type,\n" +
                 "    dbo.supply_tracking.count_type\n" +
                 "FROM\n" +
                 "    dbo.supply_tracking\n" +
                 "INNER JOIN dbo.owd_inventory\n" +
                 "ON\n" +
                 "    (\n" +
                 "        dbo.supply_tracking.inventory_id = dbo.owd_inventory.inventory_id\n" +
                 "    )\n" +
                 "WHERE\n" +
                 "    dbo.supply_tracking.inventory_id = :invId ;";

        Query q = sess.createSQLQuery(sql);
         q.setParameter("invId",invId);
         List results = q.list();
         if(results.size()>0){
                Object row = results.get(0);
                 Object[] data = (Object[]) row;
                 inventoryId = invId;
                 inventoryNum = data[0].toString();
                 description = data[1].toString();
                 email = data[2].toString();
                 sort_order = Integer.parseInt(data[3].toString());
                 active = Integer.parseInt(data[4].toString())==1;
                 threshold = Integer.parseInt(data[5].toString());
                 facility = data[6].toString();
                 groupType = data[7].toString();
                 countType = data[8].toString();



             } else{
             throw new Exception(invId +" does not appear to be a tracked id");
         }


    }

     public boolean isFilledBean(){
         boolean filled = false;
               try{
                if (inventoryId != 0&&inventoryNum.length()>0&&description.length()>0&&email.length()>0&&facility.length()>0&&groupType.length()>0&&countType.length()>0){
                   filled = true;
                }

               } catch(Exception e){

               }
         return filled;
     }
     public  void createNew(Session sess) throws Exception{
            String sql = "INSERT\n" +
                    "INTO\n" +
                    "    dbo.supply_tracking\n" +
                    "    (\n" +
                    "       \n" +
                    "        inventory_id,\n" +
                    "        email,\n" +
                    "        count_type,\n" +
                    "        sort_order,\n" +
                    "        threshold,\n" +
                    "        facility,\n" +
                    "        group_type\n" +
                    "    )\n" +
                    "    VALUES\n" +
                    "    (:inventoryId, :email, :countType, :sortOrder, :threshold, :facility, :groupType   )" ;

         Query q = sess.createSQLQuery(sql);
         q.setParameter("inventoryId", inventoryId);
         q.setParameter("email",email);
         q.setParameter("countType",countType);
         q.setParameter("sortOrder",sort_order);
         q.setParameter("threshold",threshold);
         q.setParameter("facility",facility);
         q.setParameter("groupType",groupType);

         int results = q.executeUpdate();

         if (results!=1){
             throw new Exception("Error inserting record please contact IT");
         }
         HibUtils.commit(sess);


     }

    public void update(Session sess) throws Exception{
            String sql = "UPDATE\n" +
                    "    dbo.supply_tracking\n" +
                    "SET\n" +
                    "   \n" +
                    "    email = :email,\n" +
                    "    count_type = :countType,\n" +
                    "    sort_order = :sortOrder,\n" +
                    "    threshold = :threshold,\n" +
                    "    active = :active,\n" +
                    "    facility = :facility,\n" +
                    "    group_type = :groupType\n" +
                    "WHERE\n" +
                    "    inventory_id = :inventoryId";
        if(!isFilledBean()){
            throw new Exception("Something is not filled in properly");
        }
        System.out.println("filling quety");
        Query q = sess.createSQLQuery(sql);
        q.setParameter("email",email);
        q.setParameter("countType",countType);
        q.setParameter("sortOrder",sort_order);
        q.setParameter("threshold",threshold);
        q.setParameter("active",active);
        q.setParameter("facility",facility);
        q.setParameter("groupType",groupType);
        q.setParameter("inventoryId",inventoryId);
        int results = q.executeUpdate();
        System.out.println();
        if (results ==1){
            OwdInventory inv = (OwdInventory) sess.load(OwdInventory.class,inventoryId);
            if(!inv.getDescription().equals(description)){
                inv.setDescription(description);
                sess.save(inv);

            }
            HibUtils.commit(sess);
        }else{
            throw new Exception("Unable to update");
        }
    }
     public static void main(String args[]){
         supplyTrackingBean stb = new supplyTrackingBean();
         try{
             System.out.println(stb.isFilledBean());

        stb.load(160791, HibernateSession.currentSession());
             System.out.println(stb.getInventoryNum());
             System.out.println(stb.getGroupType());
         }catch(Exception e){
                             e.printStackTrace();
         }
     }
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(String inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }
}
