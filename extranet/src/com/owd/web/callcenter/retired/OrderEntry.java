package com.owd.web.callcenter.retired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;

import java.sql.PreparedStatement;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Sep 10, 2004
 * Time: 8:36:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderEntry {
private final static Logger log =  LogManager.getLogger();


    public String getBillingName() {
        return billingName;
    }


    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public String getBillingCompany() {
        return billingCompany;
    }

    public void setBillingCompany(String billingCompany) {
        this.billingCompany = billingCompany;
    }

    public String getBillingAddressOne() {
        return billingAddressOne;
    }

    public void setBillingAddressOne(String billingAddressOne) {
        this.billingAddressOne = billingAddressOne;
    }

    public String getBillingAddressTwo() {
        return billingAddressTwo;
    }

    public void setBillingAddressTwo(String billingAddressTwo) {
        this.billingAddressTwo = billingAddressTwo;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }

    public String getMailingName() {
        return mailingName;
    }

    public void setMailingName(String mailingName) {
        this.mailingName = mailingName;
    }

    public String getMailingCompany() {
        return mailingCompany;
    }

    public void setMailingCompany(String mailingCompany) {
        this.mailingCompany = mailingCompany;
    }

    public String getMailingAddressOne() {
        return mailingAddressOne;
    }

    public void setMailingAddressOne(String mailingAddressOne) {
        this.mailingAddressOne = mailingAddressOne;
    }

    public String getMailingAddressTwo() {
        return mailingAddressTwo;
    }

    public void setMailingAddressTwo(String mailingAddressTwo) {
        this.mailingAddressTwo = mailingAddressTwo;
    }

    public String getMailingCity() {
        return mailingCity;
    }

    public void setMailingCity(String mailingCity) {
        this.mailingCity = mailingCity;
    }

    public String getMailingState() {
        return mailingState;
    }

    public void setMailingState(String mailingState) {
        this.mailingState = mailingState;
    }

    public String getMailingZip() {
        return mailingZip;
    }

    public void setMailingZip(String mailingZip) {
        this.mailingZip = mailingZip;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpiration() {
        return ccExpiration;
    }

    public void setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public String getCcCVV() {
        return ccCVV;
    }

    public void setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
    }

    public String getCcType() {
        return ccType;
    }

    public void setCcType(String ccType) {
        this.ccType = ccType;
    }

    public String getBillTelephone() {
        return billTelephone;
    }

    public void setBillTelephone(String billTelephone) {
        this.billTelephone = billTelephone;
    }

    public String getBillEmail() {
        return billEmail;
    }

    public void setBillEmail(String billEmail) {
        this.billEmail = billEmail;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getReferredFrom() {
        return referredFrom;
    }

    public void setReferredFrom(String referredFrom) {
        this.referredFrom = referredFrom;
    }

    public int getOptinSternMailingList() {
        return optinSternMailingList;
    }

    public void setOptinSternMailingList(int optinSternMailingList) {
        this.optinSternMailingList = optinSternMailingList;
    }

    public int getOptinPartnerInfo() {
        return optinPartnerInfo;
    }

    public void setOptinPartnerInfo(int optinPartnerInfo) {
        this.optinPartnerInfo = optinPartnerInfo;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public int getOrderTypeMail() {
        return orderTypeMail;
    }

    public void setOrderTypeMail(int orderTypeMail) {
        this.orderTypeMail = orderTypeMail;
    }

    public int getOrderTypeElectronic() {
        return orderTypeElectronic;
    }

    public void setOrderTypeElectronic(int orderTypeElectronic) {
        this.orderTypeElectronic = orderTypeElectronic;
    }

    int orderTypeElectronic = 0;
    int orderTypeMail = 0;
    String billingName = "";
    String billingCompany = "";
    String billingAddressOne = "";
    String billingAddressTwo = "";
    String billingCity = "";
    String billingState = "";
    String billingZip = "";

    String mailingName = "";
    String mailingCompany = "";
    String mailingAddressOne = "";
    String mailingAddressTwo = "";
    String mailingCity = "";
    String mailingState = "";
    String mailingZip = "";

    String ccNumber = "";
    String ccExpiration = "";
    String ccCVV = "";
    String ccType = "";

    String billTelephone = "";
    String billEmail = "";

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    String currentUser = "";
    String transactionDate = "";

    public String getBillNameFirst() {
        return billNameFirst;
    }

    public void setBillNameFirst(String billNameFirst) {
        this.billNameFirst = billNameFirst;
    }

    public String getMailNameFirst() {
        return mailNameFirst;
    }

    public void setMailNameFirst(String mailNameFirst) {
        this.mailNameFirst = mailNameFirst;
    }

    String referredFrom = "";
    int optinSternMailingList = 0;
    int optinPartnerInfo = 0;
    String failureReason = "";
    String comments = "";
    String billNameFirst = "";
    String mailNameFirst = "";

    public void saveToDB() throws Exception {
        String insertSQL = "" +
                "insert into sterndata (order_type_electronic,bill_name,bill_company,bill_address_one,bill_address_two," +
                "bill_city,bill_state,bill_zip,mail_name,mail_company,mail_address_one,mail_address_two," +
                "mail_city,mail_state,mail_zip,cc_num,cc_exp,cc_cvv,cc_type,bill_phone,bill_email,referral" +
                ",noorder_reason,comments,optin_sternlist,optin_partnerinfo,order_type_mail,bill_name_first,mail_name_first,agent) values " +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = HibernateSession.getPreparedStatement(insertSQL);
            stmt.setInt(1, orderTypeElectronic);
            stmt.setString(2, billingName);
            stmt.setString(3, billingCompany);
            stmt.setString(4, billingAddressOne);
            stmt.setString(5, billingAddressTwo);
            stmt.setString(6, billingCity);
            stmt.setString(7, billingState);
            stmt.setString(8, billingZip);
            stmt.setString(9, mailingName);
            stmt.setString(10, mailingCompany);
            stmt.setString(11, mailingAddressOne);
            stmt.setString(12, mailingAddressTwo);
            stmt.setString(13, mailingCity);
            stmt.setString(14, mailingState);
            stmt.setString(15, mailingZip);
            stmt.setString(16, ccNumber);
            stmt.setString(17, ccExpiration);
            stmt.setString(18, ccCVV);
            stmt.setString(19, ccType);
            stmt.setString(20, billTelephone);
            stmt.setString(21, billEmail);
            stmt.setString(22, referredFrom);
            stmt.setString(23, failureReason);
            stmt.setString(24, comments);
            stmt.setInt(25, optinSternMailingList);
            stmt.setInt(26, optinPartnerInfo);

            stmt.setInt(27, orderTypeMail);
            stmt.setString(28, billNameFirst);

            stmt.setString(29, mailNameFirst);
            stmt.setString(30, currentUser);


            stmt.executeUpdate();

            HibUtils.commit(HibernateSession.currentSession());

        } catch (Exception ex) {
            throw ex;
        } finally {
            HibernateSession.closeSession();


        }
    }

}
