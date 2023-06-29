package com.owd.web.internal.warehouse.orderChanges.privacyRequest;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 2/6/2020.
 */
public class privacyRequestAction extends ActionSupport {


    private String email;
    private String requestId;



    public String processRequest(){

        if(email.length()<2||!email.contains("@")){
            addActionError("Please submit a valid email");
            return ActionSupport.ERROR;
        }
        if(requestId.length()<2){
            addActionError("Please submit a valid requestID");
            return ActionSupport.ERROR;
        }
        try {
            List<Integer> orders = lookupOrders(email, "529");
            String user = ServletActionContext.getRequest().getRemoteUser();
            int clientId = 529;
            int count = orders.size();

            for(Integer id: orders){
                clearOrder(id);
            }

            String sql = "INSERT\n" +
                    "INTO\n" +
                    "    cleared_privacy\n" +
                    "    (\n" +
                    "        \n" +
                    "        caseId,\n" +
                    "        who,\n" +
                    "        clientId,\n" +
                    "        ordersCleared\n" +
                    "    )\n" +
                    "    VALUES\n" +
                    "    (\n" +
                    "        \n" +
                    "        :caseId,\n" +
                    "        :who,\n" +
                    "        :clientId,\n" +
                    "        :thecount\n" +
                    "    );\n";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("caseId",requestId);
            q.setParameter("who",user);
            q.setParameter("clientId",clientId);
            q.setParameter("thecount",count);
            q.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());

            addActionMessage("Cleared " + count + " orders for request "+requestId);


        }catch (Exception e){
            addActionError(e.getMessage());
            return ActionSupport.ERROR;
        }




        return ActionSupport.SUCCESS;
    }
    private final static Logger log =  LogManager.getLogger();
    private void clearOrder(Integer orderId) throws Exception{
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
        log.debug("Clearing order num: "+ order.getOrderNum());
        order.setBillFirstName("X");
        order.setBillLastName("X");
        order.setBillAddressOne("X");
        order.setBillAddressTwo("X");
        order.setBillEmailAddress("X");
        order.setBillPhoneNum("X");
        order.getShipinfo().setShipFirstName("X");
        order.getShipinfo().setShipLastName("X");
        order.getShipinfo().setShipAddressOne("X");
        order.getShipinfo().setShipAddressTwo("X");
        order.getShipinfo().setShipEmailAddress("X");
        order.getShipinfo().setShipPhoneNum("X");

        HibernateSession.currentSession().saveOrUpdate(order);
        HibernateSession.currentSession().saveOrUpdate(order.getShipinfo());

        HibUtils.commit(HibernateSession.currentSession());
    }

    private List<Integer> lookupOrders(String email, String clientId) throws Exception{
        List<Integer> orders = new ArrayList<>();
        String sql = "SELECT\n" +
                "    dbo.owd_order.order_id\n" +
                "FROM\n" +
                "    dbo.owd_order\n" +
                "INNER JOIN\n" +
                "    dbo.owd_order_ship_info\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_order.order_id = dbo.owd_order_ship_info.order_fkey)\n" +
                "WHERE\n" +
                "    (\n" +
                "        dbo.owd_order_ship_info.ship_email_address = :email\n" +
                "    )\n" +
                "AND dbo.owd_order.client_fkey = :clientId\n" +
                "AND (\n" +
                "        dbo.owd_order.is_void = 1\n" +
                "    OR  dbo.owd_order.order_status = 'Shipped')";

        Query q = HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("email",email);
        q.setParameter("clientId",clientId);
        List l = q.list();
        for(Object data:l){
            orders.add(Integer.parseInt(data.toString()));
        }

        return orders;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
