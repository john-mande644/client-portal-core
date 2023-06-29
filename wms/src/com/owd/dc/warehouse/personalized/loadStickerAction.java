package com.owd.dc.warehouse.personalized;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.HibUtils;

import java.util.*;

import org.hibernate.Session;
import org.apache.struts2.interceptor.SessionAware;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Nov 6, 2008
 * Time: 11:26:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class loadStickerAction extends ActionSupport  implements SessionAware{

    private String orderRefNum;
    private String clientId;
    private String stickerSku;
    private String forSku;
    private List<String> orderList;
    private Map session;
    public String execute(){

        System.out.println("loadStiker");
        try{
Session sess = HibernateSession.currentSession();
            try{
             orderList = (List) session.get("borOrderList");
            }catch (Exception exx){

            }
              getOrderList();
            String results =(BORStickerReceipt.addScannedOrderToConfirmedList(sess,orderRefNum,stickerSku,forSku,clientId));
            orderList.add(results);
            addActionMessage(results);
       System.out.println("done finding, add to session then commit");
            
          session.put("borOrderList",orderList);
            HibUtils.commit(sess);
        } catch (Exception e){
            try{
            HibUtils.rollback(HibernateSession.currentSession());
            }catch ( Exception ex){};

           System.out.println(e.getMessage());
            e.printStackTrace();
            addActionError(e.getMessage());

        }


        return "SUCCESS";
    }


    
     public String getOrderRefNum() {
        return orderRefNum;
    }

    public void setOrderRefNum(String orderRefNum) {
        this.orderRefNum = orderRefNum;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getStickerSku() {
        return stickerSku;
    }

    public void setStickerSku(String stickerSku) {
        this.stickerSku = stickerSku;
    }

    public String getForSku() {
        return forSku;
    }

    public void setForSku(String forSku) {
        this.forSku = forSku;
    }

    public List<String> getOrderList() {
        if (orderList == null){
             orderList = new ArrayList();

         }
        return orderList;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public void setOrderList(List<String> orderList) {
        this.orderList = orderList;
    }
   
}
