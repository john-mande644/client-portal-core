package com.owd.web.callcenter.retired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.Contact;
import com.owd.core.managers.ConnectionManager;

import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Aug 22, 2003
 * Time: 11:25:23 AM
 * To change this template use Options | File Templates.
 */
public class SubscriptionData {
private final static Logger log =  LogManager.getLogger();
    //constants//primary key ID
    private int id = 0;
    public static final int kPendant = 1;
    public static final int kWatch = 2;

    public static final int kCreditCard = 1;
    public static final int kCheck = 2;

    //billing information
    Address billingAddress = new Address();
    Address shippingAddress = new Address();

    String subscriberRelationshipToCaller = "";

    Contact billingContact = new Contact();
    Contact shippingContact = new Contact();

    int paymentType = kCreditCard;
    String ckBankRoutingNumber = "";
    String ckBankAccountNumber = "";
    String ckCheckNumber = "";

    String ccNum = "";
    int ccExpMonth = 1;
    int ccExpYear = 2003;


    //subscriber information
    Contact subscriberContact = new Contact();

    Address subscriberAddress = new Address();
    String subscriberAddressCrossStreets = "";

    //unit choices
    int unitType = 1;

    boolean addSecondUnit = false;
    int secondUnitType = 1;

    final static float baseCost = 89.85f + 29.95f + 199.00f;
    final static float secondUnitCost = 69.00f;


    String comments = "";

    public boolean isCustomerSameAsSubscriber() {
        return customerSameAsSubscriber;
    }

    public void setCustomerSameAsSubscriber(boolean customerSameAsSubscriber) {
        this.customerSameAsSubscriber = customerSameAsSubscriber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    boolean customerSameAsSubscriber = false;

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getSubscriberRelationshipToCaller() {
        return subscriberRelationshipToCaller;
    }

    public void setSubscriberRelationshipToCaller(String subscriberRelationshipToCaller) {
        this.subscriberRelationshipToCaller = subscriberRelationshipToCaller;
    }

    public Contact getShippingContact() {
        return shippingContact;
    }

    public void setShippingContact(Contact shippingContact) {
        this.shippingContact = shippingContact;
    }

    public float getTotalOrderCost() {
        return baseCost + (addSecondUnit ? secondUnitCost : 0.00f);

    }

    public SubscriptionData() {

        billingAddress.company_name = "";
        shippingAddress.company_name = "";
        subscriberAddress.company_name = "";
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Contact getBillingContact() {
        return billingContact;
    }

    public void setBillingContact(Contact billingContact) {
        this.billingContact = billingContact;
    }

    public Contact getSubscriberContact() {
        return subscriberContact;
    }

    public void setSubscriberContact(Contact subscriberContact) {
        this.subscriberContact = subscriberContact;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public String getCkBankRoutingNumber() {
        return ckBankRoutingNumber;
    }

    public void setCkBankRoutingNumber(String ckBankRoutingNumber) {
        this.ckBankRoutingNumber = ckBankRoutingNumber;
    }

    public String getCkBankAccountNumber() {
        return ckBankAccountNumber;
    }

    public void setCkBankAccountNumber(String ckBankAccountNumber) {
        this.ckBankAccountNumber = ckBankAccountNumber;
    }

    public String getCkCheckNumber() {
        return ckCheckNumber;
    }

    public void setCkCheckNumber(String ckCheckNumber) {
        this.ckCheckNumber = ckCheckNumber;
    }

    public String getCcNum() {
        return ccNum;
    }

    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public int getCcExpMonth() {
        return ccExpMonth;
    }

    public void setCcExpMonth(int ccExpMonth) {
        this.ccExpMonth = ccExpMonth;
    }

    public int getCcExpYear() {
        return ccExpYear;
    }

    public void setCcExpYear(int ccExpYear) {
        this.ccExpYear = ccExpYear;
    }

    public Address getSubscriberAddress() {
        return subscriberAddress;
    }

    public void setSubscriberAddress(Address subscriberAddress) {
        this.subscriberAddress = subscriberAddress;
    }

    public String getSubscriberAddressCrossStreets() {
        return subscriberAddressCrossStreets;
    }

    public void setSubscriberAddressCrossStreets(String subscriberAddressCrossStreets) {
        this.subscriberAddressCrossStreets = subscriberAddressCrossStreets;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public boolean isAddSecondUnit() {
        return addSecondUnit;
    }

    public void setAddSecondUnit(boolean addSecondUnit) {
        this.addSecondUnit = addSecondUnit;
    }

    public int getSecondUnitType() {
        return secondUnitType;
    }

    public void setSecondUnitType(int secondUnitType) {
        this.secondUnitType = secondUnitType;
    }


    public void saveOrderData() throws Exception {

        java.sql.Connection cxn = null;
        java.sql.PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            cxn = ConnectionManager.getConnection();
            if (id != 0) {
                stmt = cxn.prepareStatement("delete from proalert_orders where id=?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                stmt.close();


            }
            stmt = cxn.prepareStatement("insert into proalert_orders "
                    + "(bill_first_name,bill_address1,bill_address2,bill_city,bill_state, "
                    + "bill_zip,bill_phone,bill_pay_type,bill_cc_num,bill_cc_exp_mon, "
                    + "bill_cc_exp_year,bill_ck_routing,bill_ck_account,bill_ck_number,bill_last_name"
                    + ",sub_first_name,sub_last_name,sub_address1,sub_address2,sub_city, "
                    + "sub_state,sub_zip,sub_phone,sub_crossstreets,comments, "
                    + "unit_type,second_unit_type,request_second_unit,ship_name,ship_address1, "
                    + "ship_address2,ship_city,ship_state,ship_zip)"
                    + " VALUES "
                    + "(?,?,?,?,?,"
                    + "?,?,?,?,?,"
                    + "?,?,?,?,?,"
                    + "?,?,?,?,?,"
                    + "?,?,?,?,?,"
                    + "?,?,?,?,?,"
                    + "?,?,?,?)");
            int currIndex = 1;
            stmt.setString(currIndex, OWDUtilities.getFirstNameFromWholeName(getBillingContact().getName()));
            currIndex++;
            stmt.setString(currIndex, getBillingAddress().address_one);
            currIndex++;
            stmt.setString(currIndex, getBillingAddress().address_two);
            currIndex++;
            stmt.setString(currIndex, getBillingAddress().city);
            currIndex++;
            stmt.setString(currIndex, getBillingAddress().state);
            currIndex++;
            stmt.setString(currIndex, getBillingAddress().zip);
            currIndex++;
            stmt.setString(currIndex, getBillingContact().phone);
            currIndex++;
            stmt.setInt(currIndex, getPaymentType());
            currIndex++;
            stmt.setString(currIndex, getCcNum());
            currIndex++;
            stmt.setInt(currIndex, getCcExpMonth());
            currIndex++;
            stmt.setInt(currIndex, getCcExpYear());
            currIndex++;
            stmt.setString(currIndex, getCkBankRoutingNumber());
            currIndex++;
            stmt.setString(currIndex, getCkBankAccountNumber());
            currIndex++;
            stmt.setString(currIndex, getCkCheckNumber());
            currIndex++;
            stmt.setString(currIndex, OWDUtilities.getLastNameFromWholeName(getBillingContact().getName()));
            currIndex++;
            stmt.setString(currIndex, OWDUtilities.getFirstNameFromWholeName(getSubscriberContact().getName()));
            currIndex++;
            stmt.setString(currIndex, OWDUtilities.getLastNameFromWholeName(getSubscriberContact().getName()));
            currIndex++;
            stmt.setString(currIndex, getSubscriberAddress().address_one);
            currIndex++;
            stmt.setString(currIndex, getSubscriberAddress().address_two);
            currIndex++;
            stmt.setString(currIndex, getSubscriberAddress().city);
            currIndex++;
            stmt.setString(currIndex, getSubscriberAddress().state);
            currIndex++;
            stmt.setString(currIndex, getSubscriberAddress().zip);
            currIndex++;
            stmt.setString(currIndex, getSubscriberContact().phone);
            currIndex++;
            stmt.setString(currIndex, getSubscriberAddressCrossStreets());
            currIndex++;
            stmt.setString(currIndex, getComments());
            currIndex++;
            stmt.setInt(currIndex, getUnitType());
            currIndex++;
            stmt.setInt(currIndex, isAddSecondUnit() ? getSecondUnitType() : 0);
            currIndex++;
            stmt.setInt(currIndex, isAddSecondUnit() ? 1 : 0);
            currIndex++;
            stmt.setString(currIndex, getShippingContact().getName());
            currIndex++;
            stmt.setString(currIndex, getShippingAddress().address_one);
            currIndex++;
            stmt.setString(currIndex, getShippingAddress().address_two);
            currIndex++;
            stmt.setString(currIndex, getShippingAddress().city);
            currIndex++;
            stmt.setString(currIndex, getShippingAddress().state);
            currIndex++;
            stmt.setString(currIndex, getShippingAddress().zip);
            currIndex++;
            stmt.executeUpdate();

            stmt.close();


            stmt = cxn.prepareStatement("select @@IDENTITY");

            stmt.executeQuery();
            rs = stmt.getResultSet();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
            cxn.commit();
            stmt.close();

            cxn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }

            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }


    }


}
