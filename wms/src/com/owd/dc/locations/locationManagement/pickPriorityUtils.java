package com.owd.dc.locations.locationManagement;

import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 12/12/14
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class pickPriorityUtils {

    public static void main(String[] args){
        try{
            updatePriorityForParentAndChildren("36433","1");

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void updatePriorityForParentAndChildren(String location, String priority) throws Exception{
        if(location.startsWith("//")) location = location.replace("//","");


        String sql = "SELECT distinct\n" +
                "    dbo.w_location.ixNode\n" +
                "FROM\n" +
                "    dbo.w_location\n" +
                "LEFT OUTER JOIN dbo.w_location_tree\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.w_location.ixNode = dbo.w_location_tree.ixNode\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.w_location.ixNode = :location\n" +
                "or dbo.w_location_tree.ixParent = :location1 ;";

         Query q = HibernateSession.currentSession().createSQLQuery(sql);
       // q.setParameter("priority",priority);
        q.setParameter("location",location);
        q.setParameter("location1",location);
        List l = q.list();
        int i = 0;
        for(Object data : l){
            String sql2 = "update w_location set pickPriority = :priority where ixNode = :ixNode ";
            Query qq = HibernateSession.currentSession().createSQLQuery(sql2);
            qq.setParameter("priority",priority);
            qq.setParameter("ixNode",data.toString());
            qq.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            i++;
        }
       /* if (i<=0) throw new Exception("Unable to modify that parameter for those locations");*/
         System.out.println("We updated this many records : "+i);
        HibUtils.commit(HibernateSession.currentSession());
    }
}
