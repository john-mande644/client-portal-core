package com.owd.web.billing;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.CreditCard;
import com.owd.core.OWDUtilities;
import com.owd.core.business.billing.AccountSummary;
import com.owd.core.payment.FinancialTransaction;
import com.owd.core.web.ClientSecurityContext;
import com.owd.core.web.ClientSecurityContextAware;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.hibernate.engine.spi.SessionImplementor;

import java.text.DecimalFormat;
import java.util.Calendar;


public class PayAction extends ActionSupport implements ClientSecurityContextAware {
private final static Logger log =  LogManager.getLogger();

    private FinancialTransaction ft;
    private int ccExpMonth;
    private int ccExpYear;
    private String name;

    private AccountSummary serviceAccountSummary;
    private AccountSummary shippingAccountSummary;

    private ClientSecurityContext context;

    public FinancialTransaction getFt() {

        if (ft == null) {
            ft = new FinancialTransaction();
            ft.fop = "" + FinancialTransaction.TRANS_ECK;
            //ensure that owdauth interceptor is prior to params interceptor on interceptor stack for this action
            //otherwise, the next line will silently fail and struts will eat the NullPointerException on the context var
            ft.setFrom_client_fkey(getContext().getCurrentClient().getClientId());
            ft.setCustomer_fkey("55");   //payment owned by OWD
        }
        return ft;
    }

    public AccountSummary getServiceAccountSummary() {

        if (serviceAccountSummary == null) {
            try {
                serviceAccountSummary = AccountSummary.getServiceAccountSummary(getContext().getCurrentClient().getClientId(),
                        OWDUtilities.getDateForSQLDateString(OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.MONTH, -1)),
                        OWDUtilities.getDateForSQLDateString(OWDUtilities.getSQLDateNoTimeForToday()));

            } catch (Exception ex) {
                ex.printStackTrace();
                addActionError("Unable to load updated summary table for service account");
            }
        }

        return serviceAccountSummary;
    }

    public AccountSummary getShippingAccountSummary() throws Exception {
        if (shippingAccountSummary == null) {
            try {
                shippingAccountSummary = AccountSummary.getShippingAccountSummary(getContext().getCurrentClient().getClientId(),
                        OWDUtilities.getDateForSQLDateString(OWDUtilities.getSQLDateNoTimeForAdjustedDate(Calendar.MONTH, -1)),
                        OWDUtilities.getDateForSQLDateString(OWDUtilities.getSQLDateNoTimeForToday()));
            } catch (Exception ex) {
                ex.printStackTrace();
                addActionError("Unable to load updated summary table for shipping account");
            }
        }
        return shippingAccountSummary;

    }

    public String makePayment() {

        try {

            ft.fname = OWDUtilities.getFirstNameFromWholeName(getName());
            ft.lname = OWDUtilities.getLastNameFromWholeName(getName());
            DecimalFormat padLeft = new DecimalFormat("00");

            OwdClient owd = (OwdClient) HibernateSession.currentSession().load(OwdClient.class, 55);


            if (ft.fop.equals("" + FinancialTransaction.TRANS_CC)) {
                //CC-type charge
                ft.cc_exp = padLeft.format(ccExpMonth+1) + padLeft.format(ccExpYear);

                //todo add 3% processing fee to transaction amount
                

                ft.chargeCC(owd, false);
            } else {
                //CK type charge
                ft.chargeEcheck(owd, false);
            }

            ft.dbsave( ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection());

            HibernateSession.currentSession().flush();
            HibUtils.commit(HibernateSession.currentSession());

            if (ft.getStatus().equals(FinancialTransaction.TRANS_OK)) {
                setFt(null);
                return "paid";
            } else {
                addActionError("Error completing payment: " + ft.error_reponse);
            }

        } catch (Exception ex) {
            //todo remove any 3% processing fees that were included
            ex.printStackTrace();
            addActionError("Unable to complete transaction: " + ex.getMessage());
            return INPUT;
        }

        return SUCCESS;
    }

    public String input() {

        return SUCCESS;
    }

    public void validate() {
        //check if ft object has valid values
        //not called if input() method is called on action
        if(ft.amount<0.01)
        {
            addFieldError("ft.amount", "Payment amount must be 0.01 or greater");
            return; //short-circuit
        }
        try {
            if (!CreditCard.isValid(Long.parseLong(ft.cc_number))) {
                throw new Exception();
            }

        } catch (Exception ex) {
            addFieldError("ft.cc_number", "Card number is not recognized. Enter a valid credit card account number");
            return; //short-circuit
        }
        if (ft.fop.equals("" + FinancialTransaction.TRANS_CC)) {

            try {
                if (!CreditCard.isValid(Long.parseLong(ft.cc_number))) {
                    throw new Exception();
                }

            } catch (Exception ex) {
                addFieldError("ft.cc_number", "Card number is not recognized. Enter a valid credit card account number");
                return; //short-circuit
            }

            //check expiration
            Calendar cal = Calendar.getInstance();
            try {
                int thisMonth = cal.get(Calendar.MONTH);
                if (thisMonth > ccExpMonth) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                addFieldError("ccExpMonth", "Expiration month must be this month or later");
                return; //short-circuit
            }

            try {
                int thisYear = cal.get(Calendar.YEAR);
                if ((thisYear) > (ccExpYear)) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                addFieldError("ccExpYear", "Expiration year must be this year or later");
                return; //short-circuit
            }

        } else {
            //E Check

            //check account number
            if (ft.getCk_acct_num().trim().length() < 1 || (!ft.getCk_acct_num().matches("^[0-9]*$"))) {

                addFieldError("ft.ck_acct_num", "A valid account number must be provided. Use only digits (0-9)");
                return; //short-circuit
            }

            //check routing number
            if (ft.getCk_bank().trim().length() != 9 || (!ft.getCk_bank().matches("^[0-9]*$"))) {

                addFieldError("ft.ck_bank", "A 9-digit routing number must be provided. Use only digits (0-9)");
                return; //short-circuit
            }

            //check check number
            if (ft.getCk_number().trim().length() < 1) {

                addFieldError("ft.ck_number", "A check number or reference must be provided");
                return; //short-circuit
            }

        }

    }


    public ClientSecurityContext getContext() {
        return context;
    }

    public void setClientSecurityContext(ClientSecurityContext clientSecurityContext) {
        context = clientSecurityContext;
    }


    public void setFt(FinancialTransaction ft) {
        this.ft = ft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
