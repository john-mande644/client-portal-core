package com.owd.web.internal.warehouse.orderChanges;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.business.order.beans.shortShipBean;
import com.owd.core.business.order.beans.shortShipResultsBean;
import com.owd.core.business.order.shippingAdjustments.shortShipUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 10/2/2019.
 */
public class shortShipAction extends ActionSupport {
    private final static Logger log =  LogManager.getLogger();
    private OwdOrder order;
    private String orderNumber;
    private String orderId;
    private String clientId;
    private String user;
    private List<shortLines> editLines;
    private List<confirmLines> confirmLinesList = new ArrayList<>();
    private String[] reasons = {"stockout"};
    private String packingUrl = "";
    private List<queueOrders> queue;
    private String facility;
    private String fileName;
    private InputStream fileInputStream;
    private Integer queueId;



    public String loadOrder(){
        user = ServletActionContext.getRequest().getRemoteUser();
        log.debug("Doing load Order");
        log.debug(orderNumber.length());
        if(orderNumber.length()==0){

            addActionError("Please enter an order ");
            return ActionSupport.ERROR;
        }
        try{
            orderNumber = orderNumber.trim();
            Criteria criteria = HibernateSession.currentSession().createCriteria(OwdOrder.class);
            criteria.add(Restrictions.eq("orderNum",orderNumber));
            List results = criteria.list();
            order = (OwdOrder) results.get(0);
            log.debug(order.getBillLastName());
            if(order.getClientFkey()==55 || order.getClientFkey()==640|| order.getClientFkey()==471|| order.getClientFkey()==488|| order.getClientFkey()==534||order.getClientFkey()==626){

            }else{
                addActionError("This order is not supported at this time.");
                return ActionSupport.ERROR;
            }



        }catch (Exception e){
            addActionError(e.getMessage());
        }

     return ActionSupport.SUCCESS;
    }

    public String confirmShortShip(){
        log.debug("Confirm Short Ship action");
        try {
            order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(orderId));

            for(shortLines l:editLines){
                if(l.getQty().length()>0) {
                    confirmLines cl = new confirmLines();
                    cl.setLineId(l.getLineId());
                    cl.setReason(l.getReason());
                    cl.setRemoveQty(l.getQty());
                    OwdLineItem line = (OwdLineItem) HibernateSession.currentSession().load(OwdLineItem.class, Integer.parseInt(l.getLineId()));
                    cl.setInventoryNum(line.getInventoryNum());
                    cl.setDescription(line.getDescription());
                    cl.setQuantityActual(line.getQuantityActual() + "");
                    confirmLinesList.add(cl);
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            addActionError(e.getMessage());
            addActionError("Unable to process, please try again.");
            return ActionSupport.ERROR;
        }





        return ActionSupport.SUCCESS;
    }
    public String submitShortShip(){

        log.debug("Submit Short Ship action");

        List<shortShipBean> lines = new ArrayList<>();

        for(shortLines l:editLines){
            if(l.getQty().length()>0){
                log.debug("Qty: "+l.getQty());
                shortShipBean line = new  shortShipBean ();
                line.setReason(l.getReason());
                line.setQtyToAdjust(Integer.parseInt(l.getQty()));
                line.setLineItemId(Integer.parseInt(l.getLineId()));
              lines.add(line);
            }
            log.debug(l.getLineId());
        }
        if(lines.size()==0){
           addActionError("Please enter in at least one item to remove");
            try{
                order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(orderId));
            }catch (Exception e){
                addActionError(e.getMessage());
            }
            return ActionSupport.ERROR;



        }
        try {

            shortShipResultsBean resultsBean  =   shortShipUtilities.processShortShipRequest(lines, user, Integer.parseInt(clientId), Integer.parseInt(orderId));
          if(resultsBean.getUrl().length()>0){
              packingUrl = resultsBean.getUrl();
          }

            addActionMessage(resultsBean.getMessage());

        }catch (Exception e){
            addActionError(e.getMessage());
            return ActionSupport.ERROR;
        }


        return ActionSupport.SUCCESS;
    }
    public String download(){
        String sql = "select pdf_data,order_id from owd_print_queue_shortShip where id = :id";
        try {
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("id",queueId);
            List l = q.list();
            Object[] data = (Object[]) l.get(0);
            Blob pdf = (Blob) data[0];
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(data[1].toString()));
            fileName = order.getOrderNum() + "-" +order.getOrderRefnum()+".pdf";

            String updatesql = "update owd_print_queue_shortShip set printed = 1 where id = :id";
            Query qq = HibernateSession.currentSession().createSQLQuery(updatesql);

            qq.setParameter("id",queueId);
            qq.executeUpdate();
            HibUtils.commit(HibernateSession.currentSession());
            fileInputStream = new ByteArrayInputStream(pdf.getBytes(1,(int) pdf.length()));

        }catch (Exception e){
            e.printStackTrace();
        }

        return ActionSupport.SUCCESS;
    }

    public String viewQueue(){

        try {
            String sql = "select id, order_id from owd_print_queue_shortShip where pdf_data is not null and printed = 0 and facility = :facility";
           if (null==facility){
               facility = ServletActionContext.getRequest().getSession().getAttribute("owd_default_location").toString();
           }

            log.debug("This is the location");
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("facility",facility);
            List l = q.list();
            queue = new ArrayList<>();
            if(l.size()>0){
                for(Object result:l){
                    Object[] data = (Object[])result;
                    queueOrders qorder = new queueOrders();
                    qorder.setQueueId(Integer.parseInt(data[0].toString()));
                    qorder.setOrder((OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,Integer.parseInt(data[1].toString())));
                    qorder.setCustomerName(qorder.getOrder().getShipinfo().getShipFirstName() + " " + qorder.getOrder().getShipinfo().getShipLastName());
                    qorder.setClient(qorder.getOrder().getClient().getCompanyName());
                    queue.add(qorder);
                }


            }else{
                addActionMessage("We did not find any orders that are ready");
            }

        }catch (Exception e){
            addActionError(e.getMessage());
            return ActionSupport.ERROR;
        }

        return ActionSupport.SUCCESS;
    }


    public OwdOrder getOrder() {
        return order;
    }

    public void setOrder(OwdOrder order) {
        this.order = order;
    }



    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<shortLines> getEditLines() {
        return editLines;
    }

    public void setEditLines(List<shortLines> editLines) {
        this.editLines = editLines;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String[] getReasons() {
        return reasons;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<confirmLines> getConfirmLinesList() {
        return confirmLinesList;
    }

    public void setConfirmLinesList(List<confirmLines> confirmLinesList) {
        this.confirmLinesList = confirmLinesList;
    }

    public String getPackingUrl() {
        return packingUrl;
    }

    public void setPackingUrl(String packingUrl) {
        this.packingUrl = packingUrl;
    }

    public List<queueOrders> getQueue() {
        return queue;
    }

    public void setQueue(List<queueOrders> queue) {
        this.queue = queue;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }
}
