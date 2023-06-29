package com.owd.core.business.billing;

import com.owd.core.OWDUtilities;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jun 2, 2008
 * Time: 1:28:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class AccountSummary {
private final static Logger log =  LogManager.getLogger();

    List<AccountSummaryTransactionDetail> transactions;

    String type = kServiceType;
    public final static String kServiceType = "Service";
    public final static String kShippingType = "Shipping";


    public String getType() {
        return type;
    }

    double currentBalance = 0.00;
    double perDayAverage = 0.00;
    double daysRemaining = 0.00;
    double runningTotal = 0.00;
    double creditTotal = 0.00;
    double debitTotal = 0.00;
    double totalShippingCharges = 0.00;
    double totalAmount = 0.00;

    Date summaryStartDate;
    Date summaryEndDate;
    double balanceBefore = 0.00;

    public double getTotalAmount() {
        return totalAmount;
    }

    public Date getSummaryStartDate() {
        return summaryStartDate;
    }

    public Date getSummaryEndDate() {
        return summaryEndDate;
    }

    public static AccountSummary getServiceAccountSummary(int clientID, Date startDate, Date endDate) throws Exception {
        AccountSummary summary = new AccountSummary();
        summary.summaryStartDate = startDate;
        summary.summaryEndDate = endDate;
        summary.type = kServiceType;


        PreparedStatement stmt = HibernateSession.getPreparedStatement("exec getbillingsummary ?,?,?");


        stmt.setDate(1, new java.sql.Date(startDate.getTime()));
        stmt.setDate(2, new java.sql.Date(endDate.getTime()));
        stmt.setInt(3, clientID);

        ResultSet rs = stmt.executeQuery();
        summary.calculateSummaryTable(rs);
        HibernateSession.closeStatement();

        return summary;
    }

    public static AccountSummary getShippingAccountSummary(int clientID, Date startDate, Date endDate) throws Exception {
        AccountSummary summary = new AccountSummary();
        summary.summaryStartDate = startDate;
        summary.summaryEndDate = endDate;
        summary.type = kShippingType;
        summary.totalShippingCharges = 0.00;


        PreparedStatement stmt = HibernateSession.getPreparedStatement("exec getshippingtransactionsummary ?,?,?");


        stmt.setDate(1, new java.sql.Date(startDate.getTime()));
        stmt.setDate(2, new java.sql.Date(endDate.getTime()));
        stmt.setInt(3, clientID);

        ResultSet rs = stmt.executeQuery();
        summary.calculateSummaryTable(rs);
        HibernateSession.closeStatement();

       
        summary.perDayAverage = summary.totalShippingCharges/((endDate.getTime()-startDate.getTime())/(24*60*60*1000));
        summary.daysRemaining = summary.currentBalance/summary.perDayAverage;
        if(summary.daysRemaining<=0)
        {
           summary.daysRemaining = 0.00;
        }
        return summary;


    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public double getPerDayAverage() {
        return perDayAverage;
    }

    public double getDaysRemaining() {
        return daysRemaining;
    }

    public static void main(String[] args) {
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            AccountSummary as = AccountSummary.getShippingAccountSummary(117, df.parse("5/1/2012"), df.parse("6/30/2012"));
            log.debug(as.getTransactions());
            log.debug(as.getCurrentBalance());
            log.debug(as.getDaysRemaining());
            log.debug(as.getPerDayAverage());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    private void calculateSummaryTable(ResultSet rs) throws Exception {

        DecimalFormat moneyFormat = new DecimalFormat("$#,###,##0.00");

        List<AccountSummaryTransactionDetail> cats = new ArrayList<AccountSummaryTransactionDetail>();


        while (rs.next()) {

            log.debug("amount="+rs.getDouble("Total"));
            double amount = rs.getDouble("Total");
            if(rs.getString(2).contains("Invoice") || rs.getString(2).contains("Uninvoiced"))
            {
                totalShippingCharges += -1.00*amount;
            }
            if (amount != 0.00f || "Previous Balance".equals(rs.getString(2))) {
                runningTotal += amount;
                if ("Previous Balance".equals(rs.getString(2))) {
                    balanceBefore = runningTotal;

                } else {

                    AccountSummaryTransactionDetail detail = new AccountSummaryTransactionDetail();
                    detail.category = rs.getString(2);
                    detail.description = rs.getString("description");
                    detail.runningBalance = runningTotal;
                    detail.transactionDate = OWDUtilities.getDateForSQLDateString(rs.getString("post_date"));
                    detail.amount = amount;
                    totalAmount+=amount;
                    cats.add(detail);
                }
            }
        }
        currentBalance = runningTotal;

        transactions = cats;
    }

    public List<AccountSummaryTransactionDetail> getTransactions() {
        return transactions;
    }

    public double getRunningTotal() {
        return runningTotal;
    }

    public double getCreditTotal() {
        return creditTotal;
    }

    public double getDebitTotal() {
        return debitTotal;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getTotalShippingCharges() {
        return totalShippingCharges;
    }

    public class AccountSummaryTransactionDetail {
        String category;
        String description;
        Date transactionDate;
        double amount;
        double runningBalance;

        public double getAmount() {
            return amount;
        }

        public String getCategory() {
            return category;
        }

        public String getDescription() {
            return description;
        }

        public Date getTransactionDate() {
            return transactionDate;
        }



        public double getRunningBalance() {
            return runningBalance;
        }

        public String toString() {
            return "\nAccountSummaryTransactionDetail{" +
                    "category='" + category + '\'' +
                    ", description='" + description + '\'' +
                    ", transactionDate=" + transactionDate +
                    ", amount=" + amount +
                    ", runningBalance=" + runningBalance +
                    '}';
        }
    }

}

