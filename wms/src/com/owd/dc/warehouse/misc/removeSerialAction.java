package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 3/6/13
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class removeSerialAction extends ActionSupport {

     private String serial;


    public String execute(){

      if(serial.length()>0){

             try{

                 removeSerial(serial,HibernateSession.currentSession());
                  addActionMessage("Success for removing " + serial);
             }catch(Exception e){
                 e.printStackTrace();
                 addActionError(e.getMessage());
             }

      } else{
          addActionError("Please scan a valid serial");

      }




        return "success";
    }
    public static void main(String[] args){
        try{
             removeSerialAction rm = new removeSerialAction();
            rm.removeSerial("1900006169972240", HibernateSession.currentSession());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void removeSerial(String serial, Session sess) throws Exception{

        String sql = "select id from owd_inventory_serial where serial_number like :serial";

        try{
          Long.parseLong(serial);

            serial = "%UA:"+serial;
        } catch(Exception e){
            serial = "%Serial: "+serial;
        }
         System.out.println(serial);
        Query q = sess.createSQLQuery(sql);
        q.setParameter("serial",serial);
        List results = q.list();
        if(results.size()>0){
              String serialId = results.get(0).toString();
            System.out.println(serialId);
            String removeLine = "delete from owd_line_serial_link where serial_fkey = :serialId";
            Query qq = sess.createSQLQuery(removeLine);
            qq.setParameter("serialId",serialId);
            int i = qq.executeUpdate();
            if (i>0){
                 String removeSerial = "delete from owd_inventory_serial where id = :serialId";
                Query qqq = sess.createSQLQuery(removeSerial);
                qqq.setParameter("serialId",serialId);
                int ii = qqq.executeUpdate();
                if (ii>0){
                    HibUtils.commit(sess);
                }else{
                    throw new Exception("Remove of serial did not work");
                }

            } else{
                throw new Exception("Removing line link didn't work");
            }

        }else{
           throw new Exception("Serial not found");
        }

    }


    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }


}
