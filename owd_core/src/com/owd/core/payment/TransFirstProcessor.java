package com.owd.core.payment;

import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

public class TransFirstProcessor implements PaymentProcessor {
private final static Logger log =  LogManager.getLogger();


    protected static final String kTransactURL = "https://epaysecure1.transfirst.com/elink/";

    protected static final String kAuthorizeURL = "https://epaysecure1.transfirst.com/elink/authpd.asp";

    protected static final String kAuthReversalURL = "https://epaysecure1.transfirst.com/elink/authpd.asp";

    protected static final String kSettlementURL = "https://epaysecure1.transfirst.com/elink/settlepd.asp";

    protected static final String kVoidURL = "https://epaysecure1.transfirst.com/elink/voidpd.asp";

    protected static final String kCreditURL = "https://epaysecure1.transfirst.com/elink/creditpd.asp";

    protected static final String kECheckURL = "https://epaysecure1.transfirst.com/elink/checkpd.asp";

    protected static final String kECheckReturnURL = "https://epaysecure1.transfirst.com/elink/checkcreditPD.asp";

    protected static final String[] kTransFirstTransTypes = {"AUTH_ONLY", "PRIOR_AUTH_CAPTURE", "AUTH_CAPTURE", "VOID", "CREDIT", "CHECK","NOTSUPPORTED","CHECK"};

    protected static final Map kTransFirstCodeMap = doCodes();

    protected static final String[] kTransFirstPaymentTypes = {"CC", "ECHECK"};

    protected static final int kResponceCode = 10;
    protected static final String kResponceCodet = "response_code";
    //protected static final int kResponceSubcode = 26;

    protected static final int kResponceReasonCode = 26;
    protected static final String kResponceReasonCodet = "response_reason_code";
    protected static final String kResonceReasonText = "x_response_reason_text";

    protected static final int kAuthCode = 14;
    protected static final String kAuthCodet = "auth_code";
    protected static final int kAVSCode = 21;
    protected static final String kAVSCodet = "avd_code";
    protected static final int kTransID = 17;
    protected static final String kTransIDt = "trans_id";
    protected static final String kEpayRefNumt = "epay_ref";
    protected static final int kEpayRefNum = 13;
    protected String tf_password = "";

    protected String tf_login = "";
    protected static DecimalFormat myFormatter = new DecimalFormat("#00.00");

    public TransFirstProcessor(String login, String password) {

        tf_login = login;

        tf_password = password;


    }


    public TransFirstProcessor() {


    }


    public void setLogin(String login, String password) {

        tf_login = login;

        tf_password = password;


    }


    public void process(FinancialTransaction fTrans, boolean testingMode) throws Exception {
        Map m = new TreeMap();
        String anTesting = "N";


        if (testingMode)

            anTesting = "Y";


        try {
            String code = (String) kTransFirstCodeMap.get(kTransFirstTransTypes[fTrans.getType()]);

            if (code.equals("30") || code.equals("32")) {
                log.debug("AUTH");
                doAuthorize(fTrans, anTesting, code, tf_login, tf_password, m);
            }
            if (code.equals("36")) {
                doAuthorizeReversal(fTrans, anTesting, code, tf_login, tf_password, m);
            }
            if (code.equals("40")) {
                log.debug("SETTLE");
                doSettlement(fTrans, anTesting, code, tf_login, tf_password, m);

            }
            if (code.equals("61")) {
                doVoid(fTrans, anTesting, code, tf_login, tf_password, m);
            }
            if (code.equals("20")) {
                doCreditRequest(fTrans, anTesting, code, tf_login, tf_password, m);
            }
            if (code.equals("0")) {
                echeckRequest(fTrans, anTesting, code, tf_login, tf_password, m);
            }

            if (m.containsKey(kResponceCodet)) {
               

                //todo
                String x_response_code = m.get(kResponceCodet).toString();
                String x_response_reason_code = m.get(kResponceReasonCodet).toString();
                //Append transaction id to Reason Text
                String x_response_reason_text = m.get(kResonceReasonText).toString() + " (" + m.get(kTransIDt).toString() + ")";

                String x_auth_code = m.get(kAuthCodet).toString();

                String x_avs_code = m.get(kAVSCodet).toString();

                //use Epay Reference number as Transaction ID
                String x_trans_id = m.get(kEpayRefNumt).toString();
                //log.debug(x_response_code);
                //log.debug(m.get(kEpayRefNumt).toString());
                if ("00".equals(x_response_code) || "11".equals(x_response_code)
                        || "85".equals(x_response_code)
                        || "0P0".equals(x_response_code)
                        || "P00".equals(x_response_code)) {
                    //log.debug("Confirm");
                    fTrans.confirmCC(x_trans_id, x_auth_code, x_avs_code, x_response_reason_text);

                } else /*if ("05".equals(x_response_code) || getResponseCode(x_response_code).indexOf("declined") > 0)*/ {
                    //log.debug("Decline");
                    fTrans.declineCC(x_response_code + ":" + x_response_reason_text);

                }


                log.debug("TF:" + x_response_code + ";" + x_response_reason_code + ";" + x_response_reason_text + ";" + x_auth_code + ";" + x_avs_code + ";" + x_trans_id);


            } else {

                //log.debug("throwing no response Transfirst exception ");

                throw new Exception("no response");

            }


        } catch (Exception ex) {

            ex.printStackTrace();

            fTrans.reportError("TransFirst server not responding or timed out");

            throw new Exception("TransFirst server not responding or timed out. Check account manually before continuing! If the transaction was completed, void it before trying again from this interface.");

        }


    }


    static public void main(String[] args) {

        FinancialTransaction trans = new FinancialTransaction("0",

                "533340",
                "0",
                OWDUtilities.getSQLDateTimeForToday(),
                OWDUtilities.getCurrentUserName(),
                OWDUtilities.getCurrentUserName(),
                OWDUtilities.getSQLDateTimeForToday(),
                (float) 1.00,
                OWDUtilities.getSQLDateTimeForToday(),
                FinancialTransaction.TRANS_NEW, //status
                "" + FinancialTransaction.TRANS_CC,
                0,
                0,
                "" + FinancialTransaction.transTypeAuthOnlyRequest,
                "Stewart",
                "Buskirk",
                "P.O. Box 6",
                "",
                "57601",
                "",
                "Mobridge",
                "USA",
                "SD",
                "owditadmin@owd.com",
                "6058457172",
                "invoicenum",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "0911",
                "4856200220513983",
                "",
                "",
                "",
                "",
                "",
                "", 0, 0, OWDUtilities.getSQLDateTimeForToday());
        trans.setCvvCode("450");

        TransFirstProcessor processor = new TransFirstProcessor("81593315", "oneworld1");

  //     TransFirstProcessor processor = new TransFirstProcessor("81593303", "YELLOW99");

        //81593302      81593301credit card
        try {

            processor.process(trans, true);
        } catch (Exception ex) {

            ex.printStackTrace();

        }

        //////////log.debug(trans.toString());


    }

    //process Authorize or Authorize and settle transaction
    private static void doAuthorize(FinancialTransaction fTrans, String testingMode, String code, String login, String pass, Map m) throws Exception {
        WebResource server = new WebResource(kAuthorizeURL, WebResource.kPOSTMethod);

        server.addParameter("TestTransaction", testingMode);
        server.addParameter("ePayAccountNum", login);
        server.addParameter("Password", pass);
        server.addParameter("TransactionCode", code);
        server.addParameter("OrderNum", fTrans.order_fkey);
        server.addParameter("TransactionAmount", myFormatter.format(fTrans.getAmount()));
        server.addParameter("CardAccountNum", fTrans.cc_number);
        server.addParameter("ExpirationDate", fTrans.cc_exp);
        server.addParameter("CardHolderZip", fTrans.zip);
        server.addParameter("MerchantCustServNum", "0000000000");
        server.addParameter("InstallmentNum", "01");
        server.addParameter("InstallmentOf", "01");
        server.addParameter("CardHolderName", fTrans.fname + " " + fTrans.lname);
        server.addParameter("CardHolderAddress", fTrans.address_one + " " + fTrans.address_two);
        server.addParameter("CardHolderCity", fTrans.city);
        server.addParameter("CardHolderState", fTrans.state);
        server.addParameter("CardHolderEmail", fTrans.email);
        server.addParameter("CardHolderPhone", fTrans.phone);
        server.addParameter("CustomerNum", fTrans.customer_fkey);
        server.addParameter("Misc1", "");
        server.addParameter("Misc2", "");
        server.addParameter("CVV2", fTrans.cvvCode);
        server.addParameter("Ecommerce", "Y");
        server.addParameter("CardHolderCountry", fTrans.country);
        server.addParameter("DuplicateChecking", "Y");


        //log.debug("talking to epay server::");
        BufferedReader reader = null;

        try {
            reader = server.getResource();
        } catch (Exception ex) {

            if (reader == null)

                reader = server.getResource();

        }
        String response = reader.readLine();
        log.debug(response);


        String[] resp = response.split("\\|");
        //log.debug(resp.length);
        for (int i = 0; i < resp.length; i++) {
            log.debug(i + 1 + ": " + resp[i]);
        }

        m.put(kResponceCodet, resp[kResponceCode]);
        // String x_response_subcode = resp[];


        m.put(kResponceReasonCodet, resp[kResponceReasonCode]);
        if (m.get(kResponceCodet).toString().equals("00") ) {
            m.put(kResonceReasonText, getResponseCode(m.get(kResponceCodet).toString()));
        } else {
            m.put(kResonceReasonText, getDeclineError(m.get(kResponceReasonCodet).toString()));
        } //todo

        m.put(kAuthCodet, resp[kAuthCode]);
        String x_avs_code = resp[kAVSCode];
           if (x_avs_code.equalsIgnoreCase("A")) {
                    x_avs_code = "ADDRESS";
                } else if (x_avs_code.equalsIgnoreCase("W") || x_avs_code.equalsIgnoreCase("Z")) {
                    x_avs_code = "ZIP";
                } else if (x_avs_code.equalsIgnoreCase("P") || x_avs_code.equalsIgnoreCase("S")) {
                    x_avs_code = "X";
                } else if (x_avs_code.equalsIgnoreCase("X") || x_avs_code.equalsIgnoreCase("Y")) {
                    x_avs_code = "Y";
                } else {
                    x_avs_code = "N";
                }
        m.put(kAVSCodet, x_avs_code);
        m.put(kTransIDt, resp[kTransID]);
        m.put(kEpayRefNumt, resp[kEpayRefNum]);

        log.debug(""+m);
    }
    //Authorization Reversal

    private static void doAuthorizeReversal(FinancialTransaction fTrans, String testingMode, String code, String login, String pass, Map m) throws Exception {
        //log.debug("TransFirst: Reversal");
        WebResource server = new WebResource(kAuthReversalURL, WebResource.kPOSTMethod);

        server.addParameter("TestTransaction", testingMode);
        server.addParameter("ePayAccountNum", login);
        server.addParameter("Password", pass);
        server.addParameter("TransactionCode", code);
        server.addParameter("OrderNum", fTrans.order_fkey);
        server.addParameter("TransactionAmount", myFormatter.format(fTrans.getAmount()));
        server.addParameter("CardAccountNum", fTrans.cc_number);
        server.addParameter("ReferenceNum", fTrans.old_trans_id);

        /*server.addParameter("ExpirationDate", fTrans.cc_exp);
        server.addParameter("CardHolderZip", fTrans.zip);
        server.addParameter("MerchantCustServNum", "0000000000");
        server.addParameter("InstallmentNum", "01");
        server.addParameter("InstallmentOf", "01");
        server.addParameter("CardHolderName", fTrans.fname);
        server.addParameter("CardHolderAddress", fTrans.address_one + " " + fTrans.address_two);
        server.addParameter("CardHolderCity", fTrans.city);
        server.addParameter("CardHolderState", fTrans.state);
        server.addParameter("CardHolderEmail", fTrans.email);
        server.addParameter("CardHolderPhone", fTrans.phone);
        server.addParameter("CustomerNum", fTrans.customer_fkey);
        server.addParameter("Misc1", "");
        server.addParameter("Misc2", "");
        server.addParameter("CVV2", fTrans.cvvCode);
        server.addParameter("Ecommerce", "Y");
        server.addParameter("CardHolderCountry", fTrans.country);
        server.addParameter("DuplicateChecking", "Y");*/


        //log.debug("talking to epay server::");
        BufferedReader reader = null;

        try {
            reader = server.getResource();
        } catch (Exception ex) {

            if (reader == null)

                reader = server.getResource();

        }
        String response = reader.readLine();
        //log.debug(response);


        String[] resp = response.split("\\|");
        //log.debug(resp.length);
        for (int i = 0; i < resp.length; i++) {
            //log.debug(i + 1 + ": " + resp[i]);
        }

        m.put(kResponceCodet, resp[kResponceCode]);
        // String x_response_subcode = resp[];


        m.put(kResponceReasonCodet, resp[kResponceReasonCode]);
        if (m.get(kResponceCodet).toString().equals("00") || m.get(kResponceCodet).toString().equals("11")
                || m.get(kResponceCodet).toString().equals("85")
                || m.get(kResponceCodet).toString().equals("0P0")) {
            m.put(kResonceReasonText, getResponseCode(m.get(kResponceCodet).toString()));
        } else {
            m.put(kResonceReasonText, getDeclineError(m.get(kResponceCodet).toString()));
        } //todo test for what code are returned

        m.put(kAuthCodet, resp[kAuthCode]);
        String x_avs_code = resp[kAVSCode];
           if (x_avs_code.equalsIgnoreCase("A")) {
                    x_avs_code = "ADDRESS";
                } else if (x_avs_code.equalsIgnoreCase("W") || x_avs_code.equalsIgnoreCase("Z")) {
                    x_avs_code = "ZIP";
                } else if (x_avs_code.equalsIgnoreCase("P") || x_avs_code.equalsIgnoreCase("S")) {
                    x_avs_code = "X";
                } else if (x_avs_code.equalsIgnoreCase("X") || x_avs_code.equalsIgnoreCase("Y")) {
                    x_avs_code = "Y";
                } else {
                    x_avs_code = "N";
                }
        m.put(kAVSCodet, x_avs_code);
        m.put(kTransIDt, resp[kTransID]);
        m.put(kEpayRefNumt, resp[kEpayRefNum]);


    }

    private static void doSettlement(FinancialTransaction fTrans, String testingMode, String code, String login, String pass, Map m) throws Exception {
        //log.debug("TransFirst: settlement");
        WebResource server = new WebResource(kSettlementURL, WebResource.kPOSTMethod);

        server.addParameter("TestTransaction", testingMode);
        server.addParameter("ePayAccountNum", login);
        server.addParameter("Password", pass);
        server.addParameter("ReferenceNum", fTrans.old_trans_id);


        //log.debug("talking to epay server::");
        BufferedReader reader = null;

        try {
            reader = server.getResource();
        } catch (Exception ex) {

            if (reader == null)

                reader = server.getResource();

        }
        String response = reader.readLine();
        //log.debug(response);


        String[] resp = response.split("\\|");
        //log.debug(resp.length);
        for (int i = 0; i < resp.length; i++) {
            //log.debug(i + 1 + ": " + resp[i]);
        }

        m.put(kResponceCodet, resp[8]);
        // String x_response_subcode = resp[];


        m.put(kResponceReasonCodet, resp[13]);
        if (m.get(kResponceCodet).toString().equals("00")
                || m.get(kResponceCodet).toString().equals("11")
                || m.get(kResponceCodet).toString().equals("85")
                || m.get(kResponceCodet).toString().equals("0P0")) {
            m.put(kResonceReasonText, getResponseCode(m.get(kResponceCodet).toString()));
        } else {
            m.put(kResonceReasonText, getDeclineError(m.get(kResponceCodet).toString()));
        }

        m.put(kAuthCodet, "");
        String x_avs_code = "N";
        /* if (x_avs_code.equalsIgnoreCase("A")) {
            x_avs_code = AvsCodeType.ADDRESS.toString();
        } else if (x_avs_code.equalsIgnoreCase("W") || x_avs_code.equalsIgnoreCase("Z")) {
            x_avs_code = AvsCodeType.ZIP.toString();
        } else if (x_avs_code.equalsIgnoreCase("P") || x_avs_code.equalsIgnoreCase("S")) {
            x_avs_code = AvsCodeType.X.toString();
        } else if (x_avs_code.equalsIgnoreCase("X") || x_avs_code.equalsIgnoreCase("Y")) {
            x_avs_code = AvsCodeType.Y.toString();
        } else {
            x_avs_code = AvsCodeType.N.toString();
        }*/
        m.put(kAVSCodet, x_avs_code);
        m.put(kTransIDt, "");
        m.put(kEpayRefNumt, resp[9]);


    }

    private static void doVoid(FinancialTransaction fTrans, String testingMode, String code, String login, String pass, Map m) throws Exception {
        //log.debug("TransFirst: Void");
        WebResource server = new WebResource(kVoidURL, WebResource.kPOSTMethod);

        server.addParameter("TestTransaction", testingMode);
        server.addParameter("ePayAccountNum", login);
        server.addParameter("Password", pass);
        //log.debug(fTrans.old_trans_id);
        server.addParameter("ReferenceNum", fTrans.old_trans_id);


        //log.debug("talking to epay server::");
        BufferedReader reader = null;

        try {
            reader = server.getResource();
        } catch (Exception ex) {

            if (reader == null)

                reader = server.getResource();

        }
        String response = reader.readLine();
        //log.debug(response);


        String[] resp = response.split("\\|");
        //log.debug(resp.length);
        for (int i = 0; i < resp.length; i++) {
            //log.debug(i + 1 + ": " + resp[i]);
        }

        m.put(kResponceCodet, resp[6]);
        // String x_response_subcode = resp[];


        m.put(kResponceReasonCodet, resp[11]);
        if (m.get(kResponceCodet).toString().equals("00")
                 || m.get(kResponceCodet).toString().equals("11")
                 || m.get(kResponceCodet).toString().equals("85")
                || m.get(kResponceCodet).toString().equals("0P0")) {
            m.put(kResonceReasonText, getResponseCode(m.get(kResponceCodet).toString()));
        } else {
            m.put(kResonceReasonText, getDeclineError(m.get(kResponceCodet).toString()));
        }

        m.put(kAuthCodet, "");
        String x_avs_code = "N";
        /* if (x_avs_code.equalsIgnoreCase("A")) {
            x_avs_code = AvsCodeType.ADDRESS.toString();
        } else if (x_avs_code.equalsIgnoreCase("W") || x_avs_code.equalsIgnoreCase("Z")) {
            x_avs_code = AvsCodeType.ZIP.toString();
        } else if (x_avs_code.equalsIgnoreCase("P") || x_avs_code.equalsIgnoreCase("S")) {
            x_avs_code = AvsCodeType.X.toString();
        } else if (x_avs_code.equalsIgnoreCase("X") || x_avs_code.equalsIgnoreCase("Y")) {
            x_avs_code = AvsCodeType.Y.toString();
        } else {
            x_avs_code = AvsCodeType.N.toString();
        }*/
        m.put(kAVSCodet, x_avs_code);
        m.put(kTransIDt, "");
        m.put(kEpayRefNumt, resp[8]);


    }

    private static void doCreditRequest(FinancialTransaction fTrans, String testingMode, String code, String login, String pass, Map m) throws Exception {
        //log.debug("TransFirst: Credit Request");
        WebResource server = new WebResource(kCreditURL, WebResource.kPOSTMethod);

        server.addParameter("TestTransaction", testingMode);
        server.addParameter("ePayAccountNum", login);
        server.addParameter("Password", pass);
        server.addParameter("ReferenceNum", fTrans.old_trans_id);
        server.addParameter("OrderNum", fTrans.order_fkey);
        server.addParameter("TransactionAmount", myFormatter.format(fTrans.getAmount()));
        //log.debug(fTrans.cc_number);
        server.addParameter("CardAccountNum", fTrans.cc_number);
        server.addParameter("ExpirationDate", fTrans.cc_exp);

        //log.debug("talking to epay server::");
        BufferedReader reader = null;

        try {
            reader = server.getResource();
        } catch (Exception ex) {

            if (reader == null)

                reader = server.getResource();

        }
        String response = reader.readLine();
        //log.debug(response);


        String[] resp = response.split("\\|");
        //log.debug(resp.length);
        for (int i = 0; i < resp.length; i++) {
            //log.debug(i + 1 + ": " + resp[i]);
        }

        m.put(kResponceCodet, resp[8]);
        // String x_response_subcode = resp[];


        m.put(kResponceReasonCodet, resp[13]);
        if (m.get(kResponceCodet).toString().equals("00") || m.get(kResponceCodet).toString().equals("85")
                || m.get(kResponceCodet).toString().equals("0P0")
                || m.get(kResponceCodet).toString().equals("11")) {
            m.put(kResonceReasonText, getResponseCode(m.get(kResponceCodet).toString()));
        } else {
            m.put(kResonceReasonText, getDeclineError(m.get(kResponceCodet).toString()));
        }

        m.put(kAuthCodet, "");
        String x_avs_code = "N";
        /* if (x_avs_code.equalsIgnoreCase("A")) {
            x_avs_code = AvsCodeType.ADDRESS.toString();
        } else if (x_avs_code.equalsIgnoreCase("W") || x_avs_code.equalsIgnoreCase("Z")) {
            x_avs_code = AvsCodeType.ZIP.toString();
        } else if (x_avs_code.equalsIgnoreCase("P") || x_avs_code.equalsIgnoreCase("S")) {
            x_avs_code = AvsCodeType.X.toString();
        } else if (x_avs_code.equalsIgnoreCase("X") || x_avs_code.equalsIgnoreCase("Y")) {
            x_avs_code = AvsCodeType.Y.toString();
        } else {
            x_avs_code = AvsCodeType.N.toString();
        }*/
        m.put(kAVSCodet, x_avs_code);
        m.put(kTransIDt, "");
        m.put(kEpayRefNumt, resp[9]);


    }


    private static Map doCodes() {
        Map m = new TreeMap();
        m.put("AUTH_ONLY", "30");
        m.put("PRIOR_AUTH_CAPTURE", "40");
        m.put("AUTH_CAPTURE", "32");
        m.put("VOID", "61");
        m.put("CREDIT", "20");
        m.put("CHECK", "0");
        //todo match to change in Financial Transaction and change array to have this also
        m.put("REVERSAL", "36");

        return m;
    }

    static Map m = null;
    static Map n = null;
    static {
          n = new TreeMap();
        n.put("00", "Approval");
        n.put("01", "Call-Issuer");
        n.put("02", "Referral-special");
        n.put("03", "Invalid-merchant-number");
        n.put("04", "Pick-up");
        n.put("05", "Declined");
        n.put("06", "General-error");
        n.put("07", "Pick-up-special");
        n.put("08", "Honor with ID (MasterCard)");
        n.put("09", "General Decline");
        n.put("10", "Network Error");
        n.put("11", "Approval");
        n.put("12", "Invalid-transaction-type");
        n.put("13", "Invalid-amount-field");
        n.put("14", "Invalid-card-number");
        n.put("15", "Invalid-issuer");
        n.put("16", "General Decline");
        n.put("17", "General Decline");
        n.put("18", "General Decline");
        n.put("19", "Re-enter");
        n.put("20", "General Decline");
        n.put("21", "No-action-taken");
        n.put("22", "General Decline");
        n.put("23", "General Decline");
        n.put("24", "General Decline");
        n.put("25", "Acct-num-miss");
        n.put("26", "General Decline");
        n.put("27", "General Decline");
        n.put("28", "File-unavailable");
        n.put("29", "General Decline");
        n.put("30", "Format Error - Decline (MC specific)");
        n.put("31", "General Decline");
        n.put("32", "General Decline");
        n.put("33", "General Decline");
        n.put("34", "General Decline");
        n.put("36", "General Decline");
        n.put("37", "General Decline");
        n.put("38", "General Decline");
        n.put("39", "No-card-acct");
        n.put("40", "General Decline");
        n.put("41", "Lost-card");
        n.put("42", "General Decline");
        n.put("43", "Stolen-card");
        n.put("44", "General Decline");
        n.put("45", "General Decline");
        n.put("46", "General Decline");
        n.put("48", "General Decline");
        n.put("49", "General Decline");
        n.put("50", "General Decline");
        n.put("51", "Over limit");
        n.put("52", "No-checking-acct");
        n.put("53", "No-saving-acct");
        n.put("54", "Expired-card");
        n.put("55", "Invalid-pin");
        n.put("56", "General Decline");
        n.put("57", "TXN-not-allowed");
        n.put("58", "TXN-not-allowed-term");
        n.put("59", "TXN  not allowed - Merchant");
        n.put("60", "General Decline");
        n.put("61", "Over-cash-limit");
        n.put("62", "Restricted-card");
        n.put("63", "Security-violate");
        n.put("64", "General Decline");
        n.put("65", "Excessive-authorizations");
        n.put("66", "General Decline");
        n.put("67", "General Decline");
        n.put("69", "General Decline");
        n.put("70", "General Decline");
        n.put("71", "General Decline");
        n.put("72", "General Decline");
        n.put("73", "General Decline");
        n.put("74", "General Decline");
        n.put("75", "Exceeds-pin-entry-tries");
        n.put("76", "Unable-locate-previous-msg (ref# not found)");
        n.put("77", "Mismatched-info");
        n.put("78", "No-account");
        n.put("79", "Already-reversed");
        n.put("80", "Invalid-date");
        n.put("81", "Crypto-error");
        n.put("82", "CVV-failure");
        n.put("83", "Unable-verify-pin");
        n.put("84", "Duplicate trans (Visa)");
        n.put("85", "No-reason-2-decline");
        n.put("86", "Cannot-verify-pin");
        n.put("88", "General Decline");
        n.put("89", "General Decline");
        n.put("90", "General Decline");
        n.put("91", "Issuer-unavailable");
        n.put("92", "Destination-route-not-found");
        n.put("93", "Law-violation");
        n.put("94", "Duplicate trans (MC)");
        n.put("96", "System-malfunction");
        n.put("98", "General Decline");
        n.put("99", "General Decline");
        n.put("0B1", "Surcharge Amount not Permitted on Visa Cards or EBT Food Stamps");
        n.put("0B2", "Surcharge Amount not supported by debit network issuer");
        n.put("0EB", "Check-digit-error");
        n.put("0EC", "Cid-format-error");
        n.put("0N0", "FORCE-STIP");
        n.put("0N3", "Service-not-available");
        n.put("0N4", "Exceeds-limit-issuer");
        n.put("0N5", "Ineligible for re-submission");
        n.put("0N7", "Cvv2-failure");
        n.put("0N8", "Trans amount exceeds preauth amt");
        n.put("0P0", "Approved-pvid-miss");
        n.put("0P1", "Declined-pvid-miss");
        n.put("0P2", "Invalid-bill-info");
        n.put("0Q1", "Card-auth-failed");
        n.put("0R0", "Multipay-stopped");
        n.put("0R1", "Multipay-stopped-merch");
        n.put("0R3", "Revocation of all authorizations order");
        n.put("0XA", "Forward-to-issue1");
        n.put("0XD", "Forward-to-issue2");
        n.put("0VD", "General Decline");
        n.put("0T0", "First-Time Check (POS Check Service)");
        n.put("0T1", "Check is OK, but cannot be converted (POS Check Service)");
        n.put("0T2", "Invalid Routing transit Number or Check belongs to a category that is not eligible for conversion (POS Check Service)");
        n.put("0T3", "Amount greater than established service limit (POS Check Service)");
        n.put("0T4", "Unpaid items, failed negative check (POS Check Service)");
        n.put("0T5", "Duplicate check number (POS Check Service)");
        n.put("0T6", "MICR Error (POS Check Service)");
        n.put("0T7", "Too many checks (over merchant or bank limit) (POS Check Service)");
        n.put("P00", "Accepted");
        n.put("D10", "Declined");
        n.put("E40", "Invalid");
        n.put("E41", "Exceeds Processing Thresholds");
        n.put("E60", "Timeout");
    }
    static
    {
        m = new TreeMap();
        m.put("015", "Transaction declined by card processor");
        m.put("203", "Invalid-merchant-number");
        m.put("212", "Invalid-transaction-type");
        m.put("213", "Invalid-amount-field");
        m.put("214", "Invalid-card-number");
        m.put("254", "Expired-card");
        m.put("257", "Txn-not-allowed");
        m.put("276", "Unable to locate previous msg (Ref # Not Found)");
        m.put("278", "No-account");
        m.put("284", "General Decline");
        m.put("296", "System-malfunction");
        m.put("2Q1", "Card Authorization Failed");
        m.put("300", "Invalid Request Format");
        m.put("301", "Missing file header");
        m.put("303", "Invalid sender ID");
        m.put("306", "Duplicate file number");
        m.put("307", "General Decline");
        m.put("309", "Comm link down");
        m.put("310", "Missing Batch Header");
        m.put("317", "Invalid MOTO ID");
        m.put("338", "General Decline");
        m.put("380", "Missing Batch Trailer");
        m.put("382", "Record count doesn't match number records in batch");
        m.put("383", "Net amount doesn't match batch amount");
        m.put("384", "Duplicate transaction");
        m.put("385", "Invalid request format");
        m.put("394", "record count does not match number records in file");
        m.put("395", "Net amount doesn't match file amount");
        m.put("396", "Declined Post  Reauthorization Attempt (Hard Decline)");
        m.put("317", "Invalid MOTO ID (not = space, 1,2, or 4)");
        m.put("318", "Invalid account data source (not = 0,1,2,3,4,5,9,D,H,T,X,M)");
        m.put("319", "Invalid POS entry mode (not = spaces, 00,01,02,03,04,05,59,06,90)");
        m.put("320", "Auth date invalid (transaction date)");
        m.put("321", "Invalid auth source code ( not = 1,2,3,4,5,0)");
        m.put("322", "Invalid ACI code ( not = A,C,E,F,I,K.M.N.P,R,S,U,V,W,Y)");
        m.put("REJ", "Rejected transaction that has been re-keyed.");
        m.put("3AC", "Invalid authorization code- (must be upper case AN, no special characters.)");
        m.put("3TI", "Invalid tax indicator");
        m.put("3VD", "Voided transaction (only seen on Secure reporting.)");
        m.put("3AD", "AVS Response Code Declined");
        m.put("3AR", "AVS Required / Address Information Not Provided");
        m.put("3BD", "AVS and CVV2 Response Code Declined");
        m.put("3BR", "AVS and CVV2 Required / Information Not Provided");
        m.put("3CD", "CVV2 Response Code Declined");
        m.put("3CR", "CVV2 Required / Information Not Provided");
        m.put("3L5", "No Data Sent");
        m.put("3L6", "Order Number Missing");
        m.put("3M1", "Auth Date  Blank");
        m.put("3M2", "Auth Amount  Blank");
        m.put("3MT", "Managed Transaction");
        m.put("3RV", "Reversed Transaction");
        m.put("3TO", "Time Out");
        m.put("600", "General Decline");
        m.put("990", "Voided");
        m.put("991", "Voided");
        m.put("992", "Voided");
        m.put("993", "Voided");
        m.put("994", "Voided");
        m.put("995", "Voided");
        m.put("996", "Voided");
        m.put("997", "Voided");
        m.put("998", "Voided");
        m.put("999", "Voided");
        m.put("XXX", "General Decline");
        m.put("P00", "Accepted");
        m.put("D10", "Declined");
        m.put("E40", "Invalid");
        m.put("E41", "Exceeds Processing Thresholds");
        m.put("E60", "Timeout");
    }
    private static String getDeclineError(String code) {


        log.debug("cking decline code "+code);
       if (!m.containsKey(code)) {
            if(!n.containsKey(code))
            {
                if(code.startsWith("0") && code.length()>1)
                {
                   return getDeclineError(code.substring(1));
                }else
                {
                    return "";
                }
            }   else
            {
                return n.get(code).toString();
            }

        }  else
        {
           return m.get(code).toString();

        }

    }

    public static String getResponseCode(String code) {


        if (!n.containsKey(code)) {
            if(!m.containsKey(code))
            {
                if(code.startsWith("0") && code.length()>1)
                {
                   return getResponseCode(code.substring(1));
                }else
                {
                    return "";
                }
            }   else
            {
                return m.get(code).toString();
            }

        }  else
        {
           return n.get(code).toString();

        }
    }

    private static void echeckRequest(FinancialTransaction fTrans, String testingMode, String code, String login, String pass, Map m) throws Exception {
        //log.debug("doing echeck");

        WebResource server = new WebResource(kECheckURL, WebResource.kPOSTMethod);
        server.addParameter("ePayAccountNum", login);
        server.addParameter("Password", pass);
        server.addParameter("AccountNumber", fTrans.ck_acct_num);
        //log.debug(fTrans.ck_bank);
        server.addParameter("RoutingNumber", fTrans.ck_bank);
        server.addParameter("CheckNum", fTrans.ck_number);
        server.addParameter("DollarAmount", myFormatter.format(fTrans.getAmount()));
        //log.debug(fTrans.order_fkey);
        server.addParameter("OrderNumber", fTrans.order_fkey);
        server.addParameter("CustomerName", fTrans.fname + " " + fTrans.lname);
        server.addParameter("CustomerAddress", fTrans.address_one + " " + fTrans.address_two);
        server.addParameter("CustomerCity", fTrans.city);
        server.addParameter("CustomerState", fTrans.state);
        server.addParameter("CustomerZip", fTrans.zip);
        server.addParameter("CustomerPhone", fTrans.phone);


        server.addParameter("CustomerNum", fTrans.customer_fkey);
        //  server.addParameter("Misc1", "");
        //  server.addParameter("Misc2", "");
        server.addParameter("CustomerEmail", fTrans.email);
        // server.addParameter("DriversLicense","");
        // server.addParameter("DriversLicenseState","");
        // server.addParameter("BirthDate", "");
        // server.addParameter("SocSecNum","");


        //log.debug(server.url);
        //log.debug("talking to epay server::");
        BufferedReader reader = null;

        try {

            reader = server.getResource();
        } catch (Exception ex) {

            if (reader == null)

                reader = server.getResource();

        }
        String response = reader.readLine();
        //log.debug(response);


        String[] resp = response.split("\\|");
        //log.debug(resp.length);
        for (int i = 0; i < resp.length; i++) {
            //log.debug(i + 1 + ": " + resp[i]);
        }

        m.put(kResponceCodet, resp[0]);
        // String x_response_subcode = resp[];


        m.put(kResponceReasonCodet, resp[0]);
        if (m.get(kResponceCodet).toString().equals("P00")) {
            m.put(kResonceReasonText, getResponseCode(m.get(kResponceCodet).toString()));
        } else {
            m.put(kResonceReasonText, getDeclineError(m.get(kResponceCodet).toString()));
        }

        m.put(kAuthCodet, "");
        String x_avs_code = "N";
        /* if (x_avs_code.equalsIgnoreCase("A")) {
            x_avs_code = AvsCodeType.ADDRESS.toString();
        } else if (x_avs_code.equalsIgnoreCase("W") || x_avs_code.equalsIgnoreCase("Z")) {
            x_avs_code = AvsCodeType.ZIP.toString();
        } else if (x_avs_code.equalsIgnoreCase("P") || x_avs_code.equalsIgnoreCase("S")) {
            x_avs_code = AvsCodeType.X.toString();
        } else if (x_avs_code.equalsIgnoreCase("X") || x_avs_code.equalsIgnoreCase("Y")) {
            x_avs_code = AvsCodeType.Y.toString();
        } else {
            x_avs_code = AvsCodeType.N.toString();
        }*/
        m.put(kAVSCodet, x_avs_code);
        m.put(kTransIDt, resp[2]);
        m.put(kEpayRefNumt, resp[2]);
    }

}
