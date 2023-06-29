package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.inventorycount.wInventoryUtilities;
import com.owd.hibernate.HibernateSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 1/11/13
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class midcoBarcodeAction extends ActionSupport {
        public String serialnum;
    public String uanum;


    public String execute(){

           System.out.println("in execute");

        return "success";
    }
    public String getSerial(){
         try{
             System.out.println("in get serial");
             String sql = "select expression from owd_inventory_data_rules where id = 3";
             Query q = HibernateSession.currentSession().createSQLQuery(sql);
             String reg = q.list().get(0).toString();
             Pattern pat = Pattern.compile(reg);
                                Matcher match = pat.matcher(serialnum);
                                if(match.find()){
                                    System.out.println("yeah");

                                } else{

                                    throw new Exception(serialnum+" does not match the correct barcode pattern for serial num, please rescan");
                                }
         } catch(Exception e){
             serialnum = null;
             e.printStackTrace();
             addActionError(e.getMessage());
         }

        return "success";
    }
    public String getUa(){
            try{
                HttpServletRequest request = ServletActionContext.getRequest();
                int userId = wInventoryUtilities.getIdFromCookies(request);
                System.out.println(userId);
                String sql = "select expression from owd_inventory_data_rules where id = 7";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                String reg = q.list().get(0).toString();
                Pattern pat = Pattern.compile(reg);
                                   Matcher match = pat.matcher(uanum);
                                   if(match.find()){
                                       System.out.println("yeah on ua");
                                       printUtils.printMidcoDTAViaUserId(serialnum,uanum,userId+"");

                                      serialnum = null;
                                              uanum=null;
                                   } else{

                                       throw new Exception(uanum+" does not match the correct barcode pattern for ua num, please rescan");
                                   }
            } catch(Exception e){
                serialnum = null;
                e.printStackTrace();
                addActionError(e.getMessage());
            }

           return "success";
       }

    public String getSerialnum() {
        return serialnum;
    }

    public void setSerialnum(String serialnum) {
        this.serialnum = serialnum;
    }

    public String getUanum() {
        return uanum;
    }

    public void setUanum(String uanum) {
        this.uanum = uanum;
    }
}
