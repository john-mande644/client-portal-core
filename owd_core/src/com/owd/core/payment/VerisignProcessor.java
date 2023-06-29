/*
 * VerisignProcessor.java
 *
 * Created on August 22, 2003, 11:47 AM
 */

package com.owd.core.payment;

import com.owd.core.business.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import paypal.payflow.ClientInfo;
import paypal.payflow.PayflowAPI;
import paypal.payflow.SDKProperties;

import java.util.StringTokenizer;

/**
 * @author liheng
 */
public class VerisignProcessor implements PaymentProcessor {
private final static Logger log =  LogManager.getLogger();

    public static final String pTender = "TENDER";//1
    public static final String pTransactionType = "TRXTYPE";//1
    public static final String pUser = "USER";//64
    public static final String pPassword = "PWD";//32
    public static final String pPartner = "PARTNER";//12
    public static final String pVendor = "VENDOR";//64
    public static final String pAccount = "ACCT";//19
    public static final String pEXPDate = "EXPDATE";//4
    public static final String pAmount = "AMT";//10
    public static final String pZip = "ZIP";//9
    public static final String pComment1 = "COMMENT1";//128
    public static final String pComment2 = "COMMENT2";//128
    public static final String pOriginalID = "ORIGID";//12
    public static final String pPNRef = "PNREF";
    public static final String pCustRef = "CUSTREF";//12
    public static final String pCVV2 = "CVV2";//4
    public static final String pEndTime = "ENDTIME";//14, yyyymmddhhmmss
    public static final String pStartTime = "STARTTIME";
    public static final String pName = "NAME";//30
    public static final String pFirstName = "FIRSTNAME";//30
    public static final String pStreet = "STREET";//30
    public static final String pSwipe = "SWIPE";


    public static final String pResult = "RESULT";
    public static final String pResponseMessage = "RESPMSG";
    public static final String pAuthCode = "AUTHCODE";
    public static final String pAVSAddr = "AVSADDR";
    public static final String pAVSZip = "AVSZIP";
    public static final String pIAVS = "IAVS";
    public static final String pCVV2Match = "CVV2MATCH";


    protected String an_password = "";

    protected String an_login = "";


    public VerisignProcessor(String login, String password) {

        an_login = login;

        an_password = password;


    }



    private String addParameter(String parameters, String name, String value) throws Exception
    {
        if(name==null||value==null||name.length()<1)
        {
            throw new Exception("name or value to be added is null or empty");
        }

        if(value.indexOf("=") >= 0 || value.indexOf("&") >= 0)
        {
            name = name.toUpperCase()+"["+value.length()+"]";
        }

        String response = null;
        if(parameters==null||parameters.length()<1)
        {
            return name+"="+value;
        }
        else
        {
            return parameters+"&"+name+"="+value;
        }

    }



    public VerisignProcessor() {


    }


    public void setLogin(String login, String password) {

        an_login = login;

        an_password = password;


    }


    public void process(FinancialTransaction fTrans, boolean testingMode) throws Exception {

//decide the transaction server/port



//construct parameter list
        String paramList = addParameter(null, pTender, "C");


        switch (fTrans.getType()) {
            case FinancialTransaction.transTypeAuthCaptureRequest:
                paramList = addParameter(paramList, pTransactionType, "S");
                break;
            case FinancialTransaction.transTypeAuthOnlyRequest:
                paramList = addParameter(paramList, pTransactionType, "A");
                break;
            case FinancialTransaction.transTypeVoidTransactionRequest:
                paramList = addParameter(paramList, pTransactionType, "V");
                break;
            case FinancialTransaction.transTypeCreditRequest:
                paramList = addParameter(paramList, pTransactionType, "C");
                break;
            case FinancialTransaction.transTypeCheckTransactionRequest:
                paramList = addParameter(paramList, pTransactionType, "I");
                break;
            case FinancialTransaction.transTypeCaptureOutsideAuthRequest:
                paramList = addParameter(paramList, pTransactionType, "D");
                break;
            default:
                throw new Exception("Unknown Transaction Type");
        }

        if (an_password != null && an_password.length() > 0) {
            if (an_password.indexOf("|") >= 0)
            {   paramList = addParameter(paramList, pPassword, an_password.substring(0, an_password.indexOf("|")));
            }else{
                paramList = addParameter(paramList, pPassword, an_password);
        }
        }else {
            throw new Exception("password missing");
        }

        if (an_login != null && an_login.length() > 0) {
             if (an_login.indexOf("|") >= 0)
            {   paramList = addParameter(paramList, pUser, an_login.substring(0, an_login.indexOf("|")));
            }else{
                paramList = addParameter(paramList, pUser, an_login);
        }
            if (fTrans.vendor_id == null || fTrans.vendor_id.length() < 1) {
                //should throw an exception
                  if (an_login.indexOf("|") >= 0)
            {   paramList = addParameter(paramList, pVendor, an_login.substring(an_login.indexOf("|")+1));
            }else{
                paramList = addParameter(paramList, pVendor, an_login);
        }
            } else {
                paramList = addParameter(paramList, pVendor, fTrans.vendor_id);
            }
        } else {
            throw new Exception("username missing");
        }

//need to be changed if the client doesn't register through onlinedata
        if (fTrans.partner == null || fTrans.partner.length() < 1) {
            //should throw an exception, but ...
            if (an_password.indexOf("|") >= 0)
                paramList = addParameter(paramList, pPartner, an_password.substring(an_password.indexOf("|") + 1));

            //paramList = addParameter(paramList, pPartner, "Verisign");
        } else {
            paramList = addParameter(paramList, pPartner, fTrans.partner);
        }

        if (fTrans.getType() == FinancialTransaction.transTypeAuthCaptureRequest ||
                fTrans.getType() == FinancialTransaction.transTypeAuthOnlyRequest ||
                fTrans.getType() == FinancialTransaction.transTypeCreditRequest) {
            if (fTrans.proc_trans_id != null && fTrans.proc_trans_id.length() > 0) {
                paramList = addParameter(paramList, pOriginalID, fTrans.proc_trans_id);
            } else {
                String cc_num = fTrans.cc_number;
                String exp = fTrans.cc_exp;

                if (cc_num != null && cc_num.length() > 0 && exp != null && exp.length() > 0 && fTrans.getAmount() > 0) {
                    paramList = addParameter(paramList, pAccount, cc_num);
                    paramList = addParameter(paramList, pEXPDate, exp);
                    paramList = addParameter(paramList, pAmount, "" + fTrans.getAmount());
                } else {
                    throw new Exception("Amount, CC No, or Amount missing");
                }
            }

        }


        if (fTrans.getType() == FinancialTransaction.transTypeVoidTransactionRequest ||
                fTrans.getType() == FinancialTransaction.transTypeCheckTransactionRequest ||
                fTrans.getType() == FinancialTransaction.transTypeCaptureOutsideAuthRequest) {
            if (fTrans.proc_trans_id != null && fTrans.proc_trans_id.length() > 0) {
                paramList = addParameter(paramList, pOriginalID, fTrans.proc_trans_id);
            } else {
                if (fTrans.order_fkey.length() > 0) {
                    paramList = addParameter(paramList, pOriginalID, fTrans.order_fkey);
                } else {
                    throw new Exception("REF missing");
                }
            }

        }


//set OWD unique reference number
 if(fTrans.order_fkey == null) fTrans.order_fkey="0";
        paramList = addParameter(paramList, pComment1, fTrans.description);
        paramList = addParameter(paramList, pComment2, ""+Client.getClientIDForOrder(new Integer(fTrans.order_fkey).intValue()));

//set other optional parameters
        if (fTrans.lname.length() > 0 || fTrans.fname.length() > 0) {
            paramList = addParameter(paramList, pName, fTrans.fname + " " + fTrans.lname);
        }

        if (fTrans.address_one.length() > 0) {
            paramList = addParameter(paramList, pStreet, fTrans.address_one);
        }

        if (fTrans.zip.length() > 0) {
            paramList = addParameter(paramList, pZip, fTrans.zip);
        }

        if(fTrans.cvvCode.length()>0)
                {
                    paramList = addParameter(paramList, pCVV2, fTrans.cvvCode);
                }
             SDKProperties.setHostAddress(testingMode?"pilot-payflowpro.paypal.com":"payflowpro.paypal.com");
                  SDKProperties.setHostPort(443);
                  SDKProperties.setTimeOut(45);
        PayflowAPI pn = new PayflowAPI();
        String resp = null;
        try {

            String basepath = System.getProperty("catalina.base");
            if (basepath == null || "".equals(basepath)) {

                basepath = ".";
            }


        String requestId = pn.generateRequestId();

           ClientInfo clInfo = new ClientInfo();

                // Set the ClientInfo object of the PayflowAPI.
           //     pn.setClientInfo(clInfo);


            resp = pn.submitTransaction(paramList, requestId);

            // Create a new Client Information data object.
           //     ClientInfo clInfo = new ClientInfo();

                // Set the ClientInfo object of the PayflowAPI.
           //     pn.setClientInfo(clInfo);
            
        } catch (Exception ex) {
            throw ex;
        } finally {
          //  pn.DestroyContext();

        }
        String x_response_code = "";

        String x_response_subcode = "";

        String x_response_reason_code = "";

        String x_response_reason_text = "";

        String x_auth_code = "";

        String x_avs_code = "";

        String x_trans_id = "";

        String x_cvv_code = "";


        if (resp != null) {
            //log.debug(resp);

            StringTokenizer st = new StringTokenizer(resp, "&");
            while (st.hasMoreTokens()) {
                String p = st.nextToken();
                String v = p.substring(p.indexOf("=") + 1);

                if (p.indexOf(pResult) >= 0) {
                    x_response_code = v;
                } else if (p.indexOf(pResponseMessage) >= 0) {
                    x_response_reason_text = v;
                } else if (p.indexOf(pPNRef) >= 0) {
                    x_trans_id = v;
                } else if (p.indexOf(pAuthCode) >= 0) {
                    x_auth_code = v;
                } else if (p.indexOf(pAVSZip) >= 0) {
                    x_avs_code = v;
                }else if(p.indexOf(pCVV2Match)>=0)
                                {
                                    x_cvv_code = v;
                                }

            }


            log.debug("VS:" + x_response_code + ";" + x_response_subcode + ";" + x_response_reason_code + ";" + x_response_reason_text + ";" + x_auth_code + ";" + x_avs_code + ";" + x_trans_id);


            int response_code = -1;
            try {
                response_code = Integer.parseInt(x_response_code);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }


            if (response_code == 0) {

                fTrans.confirmCC(x_trans_id,x_auth_code,x_avs_code,x_response_reason_text,x_cvv_code);


            } else if (response_code == 12) {
                fTrans.declineCC(x_response_reason_text);

            } else if (response_code > 0 && response_code != 12) {
                fTrans.reportError(x_response_reason_text);
            } else {
                fTrans.reportError("Communication Error. No transaction performed");
            }


        } else {

            //log.debug("throwing no response CC processor exception ");

            throw new Exception("No response from the processor server");

        }


    }


    static public void main(String[] args) {

        FinancialTransaction trans = new FinancialTransaction("0",

                "1598702",

                "0",

                "",

                "",

                "",

                "",

                (float) 55.66,

                "",

                FinancialTransaction.TRANS_NEW, //status

                "" + FinancialTransaction.TRANS_CC,

                0,

                0,

                "" + FinancialTransaction.transTypeAuthOnlyRequest,

                "Stewart",

                "Buskirk",

                "21906 Old Poplar Way",

                "",

                "98036",

                "",

                "Brier",

                "USA",

                "WA",

                "owditadmin@owd.com",

                "",

                "invoicenum",

                "1.23",

                "3.21",

                "",

                "",

                "",

                "",

                "",

                "1212",

                "5105105105105100",

                "",

                "",

                "",

                "",

                "",

                "", 0, 0, "");


        VerisignProcessor processor = new VerisignProcessor("owdbuy|storebaol", "mygaws5421caf|paypal");
        try {

            processor.process(trans, false);
            log.debug(trans.error_reponse);

        } catch (Exception ex) {

            ex.printStackTrace();

        }



        //////////log.debug(trans.toString());


    }


}
