package com.owd.dc.warehouse.misc;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibernateSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 12/4/2019.
 */
public class menuAction extends ActionSupport {

    private boolean quickAssign = false;

    public String menu(){


        try {
            String name = "";
            for(Cookie c: ServletActionContext.getRequest().getCookies()){
                if(c.getName().equals("tcname")){
                    name = c.getValue();
                }
            }


            quickAssign = quickAssignByName(name);
        }catch (Exception e){
            e.printStackTrace();
        }



        return "success";
    }

    private static boolean quickAssignByName(String name){
        boolean teleport = false;
        System.out.println(name);
        try{
            String sql = "select quickAssign from handheld_setup where name= :name";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("name",name);
            List Results = q.list();
            System.out.println(Results.get(0).toString());
            if(Results.get(0).toString().equals("true")) teleport = true;


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Unable to determine teleport abilites. Using false");
        }

        return teleport;
    }

    public boolean isQuickAssign() {
        return quickAssign;
    }

    public void setQuickAssign(boolean quickAssign) {
        this.quickAssign = quickAssign;
    }
}
