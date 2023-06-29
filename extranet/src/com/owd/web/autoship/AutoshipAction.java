package com.owd.web.autoship;

import com.owd.hibernate.generated.OwdInventory;
import com.owd.hibernate.generated.OwdOrderAuto;
import com.owd.hibernate.generated.OwdOrderAutoItem;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.interceptor.ParameterNameAware;
import com.owd.core.web.ClientSecurityContext;
import com.owd.core.web.ClientSecurityContextAware;
import com.owd.hibernate.*;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.math.BigDecimal;
import java.util.*;


public class AutoshipAction extends ActionSupport implements Preparable, ParameterNameAware, ClientSecurityContextAware {
private final static Logger log =  LogManager.getLogger();

    private OwdOrderAuto as = new OwdOrderAuto();
   // private Map shipMethods;

    private ClientSecurityContext context;

    private String newItemSKU = "";
    private Integer newItemQty = 0;
    private Double newItemPrice = 0.00;

    private Collection<ListItem> ListItems = new ArrayList<ListItem>();

    public void doCopy() throws Exception {
        OwdOrderAuto as2 = as = AutoshipDAOService.getNewAutoship(context.getCurrentClient().getClientId());
        as2.setBillCompany(as.getBillCompany());
        as2.setBillAddressOne(as.getBillAddressOne());
        as2.setBillAddressTwo(as.getBillAddressTwo());
        as2.setBillCity(as.getBillCity());
        as2.setBillState(as.getBillState());
        as2.setBillZip(as.getBillZip());
        as2.setBillCountry(as.getBillCountry());
        as2.setBillEmail(as.getBillEmail());
        as2.setBillName(as.getBillName());
        as2.setBillPhone(as.getBillPhone());

        as2.setCalendarUnit(as.getCalendarUnit());
        as2.setCcExpMon(as.getCcExpMon());
        as2.setCcExpYear(as.getCcExpYear());
        as2.setCcNum(as.getCcNum());
        as2.setFop(as.getFop());
        as2.setCkAbaNum(as.getCkAbaNum());
        as2.setCkAcct(as.getCkAcct());
        as2.setCkAcctNum(as.getCkAcctNum());
        as2.setCkBank(as.getCkBank());
        as2.setCkNumber(as.getCkNumber());
        as2.setNextShipmentDate(as.getNextShipmentDate());
        as2.setOrderSource(as.getOrderSource());
        as2.setRequirePayment(as.getRequirePayment());
        as2.setSalesTax(as.getSalesTax());
        as2.setShipCost(as.getShipCost());
        as2.setShipInterval(as.getShipInterval());
        as2.setShipMethod(as.getShipMethod());
        as2.setStatus(0);

        as2.setShipCompany(as.getShipCompany());
        as2.setShipAddressOne(as.getShipAddressOne());
        as2.setShipAddressTwo(as.getShipAddressTwo());
        as2.setShipCity(as.getShipCity());
        as2.setShipState(as.getShipState());
        as2.setShipZip(as.getShipZip());
        as2.setShipCountry(as.getShipCountry());
        as2.setShipEmail(as.getShipEmail());
        as2.setShipName(as.getShipName());
        as2.setShipPhone(as.getShipPhone());

        for (OwdOrderAutoItem item : as.getOwdOrderAutoItems()) {
            OwdOrderAutoItem newitem = new OwdOrderAutoItem();
            newitem.setQuantity(item.getQuantity());
            newitem.setSku(item.getSku());
            newitem.setUnitPrice(item.getUnitPrice());
            newitem.setOwdOrderAuto(as2);

            as2.getOwdOrderAutoItems().add(newitem);
        }


        as = as2;
    }

    public void doAddItem() throws Exception {


        try {


            Session sess = HibernateSession.currentSession();

            Criteria crit = sess.createCriteria(OwdInventory.class);

            crit.setMaxResults(10);
            crit.add(Restrictions.eq("owdClient.clientId", as.getClientFkey()))
                    .add(Restrictions.eq("inventoryNum", newItemSKU));

            List list = crit.list();
            if (list.size() == 0)
                throw new Exception("SKU " + newItemSKU + " not found. SKU values are case-sensitive. Please check the SKU and try again.");
            OwdInventory inventoryItem = null;
            if (list.size() == 1) {
                if (((OwdInventory) list.get(0)).getInventoryNum().equals(newItemSKU)) {
                    inventoryItem = ((OwdInventory) list.get(0));
                }
            } else {
                Iterator iti = list.iterator();
                while (iti.hasNext()) {
                    OwdInventory item = (OwdInventory) iti.next();

                    if (item.getInventoryNum().equals(newItemSKU)) {
                        inventoryItem = item;

                    }
                }
            }

            if (inventoryItem == null) {
                throw new Exception("SKU " + newItemSKU + " not found. SKU values are case-sensitive. Please check the SKU and try again.");
            }


            if (newItemQty < 1) throw new Exception("SKU quantity must be a whole number greater than zero");

            boolean foundItem = false;

            if (newItemSKU.length() < 1) throw new Exception("You must provide a valid SKU to add an item.");


            OwdOrderAutoItem item = new OwdOrderAutoItem();

            //no longer blocking multiple lines with same item. Change made to support variable reshipment schedules per unit
            /*  Iterator it = as.getOwdOrderAutoItems().iterator();
            while (it.hasNext()) {
                OwdOrderAutoItem itx = (OwdOrderAutoItem) it.next();

                if ((itx).getSku().equals(inventoryItem.getInventoryNum())) {
                    foundItem = true;
                    item = itx;
                    item.setQuantity(newItemQty + item.getQuantity());
                    item.setUnitPrice(new BigDecimal(newItemPrice));


                }

            }*/

            if (!foundItem) {
                item.setOwdOrderAuto(as);
                item.setSku(inventoryItem.getInventoryNum());
                item.setQuantity(newItemQty);
                item.setUnitPrice(new BigDecimal(newItemPrice));

             
                //   item.setInventoryFkey(inventoryItem.getInventoryId().intValue());
                as.getOwdOrderAutoItems().add(item);

                AutoshipDAOService.updateAutoship(as, "Added new item " + newItemSKU + "/" + newItemQty + "/" + newItemPrice);
                HibUtils.commit(HibernateSession.currentSession());
            }
        } catch (Exception ex) {

            ex.printStackTrace();
            throw ex;
        } finally {
            HibernateSession.closeSession();
        }

    }

    public String doSave() {

           try {
               // OwdOrderAutoItem item;
              // item.
               if (as.getAutoShipId() == null) {
                   AutoshipDAOService.insertAutoship(as,"");
               } else {
                   AutoshipDAOService.updateAutoship(as,"");
               }
               return SUCCESS;
           } catch (Exception ex) {
               ex.printStackTrace();
               addActionError("An unexpected error occurred: " + ex.getMessage());
               return INPUT;
           }
       }

       public String doDelete() {
           try {
               AutoshipDAOService.deleteAutoship(as.getAutoShipId());
               return SUCCESS;
           } catch (Exception ex) {
               ex.printStackTrace();
               addActionError("An unexpected error occurred: " + ex.getMessage());
               return INPUT;
           }
       }


    public void prepare() throws Exception {
        try {
            if (as != null && as.getAutoShipId() != null) {
                log.debug("load as");
                as = AutoshipDAOService.getAutoship(as.getAutoShipId());

            } else if (as == null) {
                log.debug("new as");
                as = AutoshipDAOService.getNewAutoship(context.getCurrentClient().getClientId());
            }

            //log.debug("clients created");

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    /**
     * This method will filter out parameters that
     * start with "d-" followed by a numeric digit,
     * because parameters of this form are generated by displayTag,
     * and are treated by webwork as an invalid OGNL expressions causing
     * webwork to throw an ognl.InappropriateExpressionException exception.
     * Note that the exception is only thrown when webwork is set in devmode.
     * However, to prevent this error, the ParameterNameAware interface has been
     * implemented which requires this method.
     * This method could be implemented more simply using java's regular expression
     * support, but such an implementation may suffer from readability except for
     * people who are very strong in understanding java's RegEX semantics.
     */
    public boolean acceptableParameterName(String parameterName) {
        boolean retVal = true;
        if (parameterName != null && parameterName.startsWith("d-"))
            if (parameterName.length() > 2) {
                String thirdCharacter = parameterName.substring(2, 3);
                if (StringUtils.isNumeric(thirdCharacter)) {
                    retVal = false;
                }
            }
        return retVal;
    }

    public String doInput() {

        return SUCCESS;
    }

    public void validate() {
        //check if as object has valid values
        //not called if input() method is called on action

    }


    public ClientSecurityContext getContext() {
        return context;
    }

    public void setClientSecurityContext(ClientSecurityContext clientSecurityContext) {
        context = clientSecurityContext;
    }

    public class ListItem{
        private OwdInventory item;
        private OwdOrderAutoItem autoShipItem;

        public OwdInventory getItem() {
            return item;
        }

        public void setItem(OwdInventory item) {
            this.item = item;
        }

        public OwdOrderAutoItem getAutoShipItem() {
            return autoShipItem;
        }

        public void setAutoShipItem(OwdOrderAutoItem autoShipItem) {
            this.autoShipItem = autoShipItem;
        }
    }

    public OwdOrderAuto getAs() {
        return as;
    }

    public void setAs(OwdOrderAuto as) {
        this.as = as;
    }

    public String getNewItemSKU() {
        return newItemSKU;
    }

    public void setNewItemSKU(String newItemSKU) {
        this.newItemSKU = newItemSKU;
    }

    public Integer getNewItemQty() {
        return newItemQty;
    }

    public void setNewItemQty(Integer newItemQty) {
        this.newItemQty = newItemQty;
    }

    public Double getNewItemPrice() {
        return newItemPrice;
    }

    public void setNewItemPrice(Double newItemPrice) {
        this.newItemPrice = newItemPrice;
    }
}
