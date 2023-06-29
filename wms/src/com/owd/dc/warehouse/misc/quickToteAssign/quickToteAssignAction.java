package com.owd.dc.warehouse.misc.quickToteAssign;

import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.warehouse.licensePlate.licensePlateUtilities;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;

import java.util.List;

/**
 * Created by danny on 12/4/2019.
 */
public class quickToteAssignAction extends ActionSupport {


    private String toteId;
    private String orderBarcode;


    private final static Logger log = LogManager.getLogger();

    public String getOrder(){
        log.debug(orderBarcode);

        String sql = "select order_id from owd_order where order_num_barcode = :barcode";
        try{
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setString("barcode", "*" + orderBarcode+"*");

            List l = q.list();
            if(l.size()==0) {

                throw new Exception("Please scan a valid order");
            }
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class, Integer.parseInt(l.get(0).toString()));
            order.setLicensePlate(toteId);
            HibernateSession.currentSession().saveOrUpdate(order);
            HibUtils.commit(HibernateSession.currentSession());

            addActionMessage("Assgined order "+ order.getOrderNum()+ " to tote "+ toteId);
            toteId="";
            orderBarcode="";


        }catch (Exception e){
            orderBarcode="";
            addActionError(e.getMessage());
            return "error";
        }


        return "success";
    }

    public String getTote(){
        if(null== toteId|| toteId.length()<2){
            addActionError("Please Scan a Tote:");
            return "error";
        }

        try{
            if(licensePlateUtilities.canThisPackageLicenseBeUsed(toteId)){

                return "success";


            }else{
                addActionError("This Tote is already assigned to another order");
                return "error";
            }

        }catch (Exception e){
            addActionError(e.getMessage());
            return "error";
        }






    }





    public String getToteId() {
        return toteId;
    }

    public void setToteId(String toteId) {
        this.toteId = toteId;
    }

    public String getOrderBarcode() {
        return orderBarcode;
    }

    public void setOrderBarcode(String orderBarcode) {
        this.orderBarcode = orderBarcode;
    }
}
