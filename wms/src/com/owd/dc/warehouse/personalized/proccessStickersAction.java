package com.owd.dc.warehouse.personalized;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Calendar;

import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Nov 6, 2008
 * Time: 11:27:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class proccessStickersAction extends ActionSupport implements SessionAware {
    private Map<String,borOrderInfo> orderInfo;
     private int numberDone;
    private String info;
    private String clientId;
   private Map session;
    
    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String execute() {
            session.remove("borOrderList");
        numberDone = 0;
      try{
       Session sess = HibernateSession.currentSession();
       orderInfo = BORStickerReceipt.loadOrderInfo(clientId, sess);
       String groupId = Calendar.getInstance().getTimeInMillis()+"";
       for(borOrderInfo b: orderInfo.values()){
         System.out.println(b.getOrderId());
         b.setInfo(b.getOrderRefnum() + ":"+b.getOrderNum()+"  "+BORStickerReceipt.releaseConfirmedOrders(b.getClientId(),b.getOrderId(),b.getItems(),sess,groupId));

           numberDone++;
         if("success".equals(BORStickerReceipt.setDoneFlag(b.getOrderId(),HibernateSession.currentSession()))){
             HibUtils.commit(HibernateSession.currentSession());
         } else{
             HibUtils.rollback(HibernateSession.currentSession());
         }

       }



      }catch (Exception e){
          e.printStackTrace();
         addActionError(e.getMessage());
      }
        return "SUCCESS";
    }


    public Map<String, borOrderInfo> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Map<String, borOrderInfo> orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getNumberDone() {
        return numberDone;
    }

    public void setNumberDone(int numberDone) {
        this.numberDone = numberDone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
