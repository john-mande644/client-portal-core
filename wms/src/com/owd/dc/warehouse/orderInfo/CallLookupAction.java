package com.owd.dc.warehouse.orderInfo;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Query;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 7/30/12
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class CallLookupAction extends ActionSupport {
    private String answer ="Not Found";
        private String file;

       public String execute(){
           try{
                String id = file.replace(".wav","");
           System.out.println(id);
           String sql = "select client_id from cc_cl_contacts where contact_id = :id";


               Query q = HibernateSession.currentSession().createSQLQuery(sql);
               q.setString("id",id);
               List results = q.list();
               if (results.size()>0){
                   answer = results.get(0).toString();
               } else{
                   answer = "Not Found";
               }
           }catch (Exception e){
               e.printStackTrace();
           }




           return "success";
       }

       public String getAnswer() {
           return answer;
       }

       public void setAnswer(String answer) {
           this.answer = answer;
       }

       public String getFile() {
           return file;
       }

       public void setFile(String file) {
           this.file = file;
       }

}
