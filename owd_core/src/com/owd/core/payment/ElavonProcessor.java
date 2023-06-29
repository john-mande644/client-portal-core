package com.owd.core.payment;


import com.owd.core.CSVReader;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;


public class ElavonProcessor implements PaymentProcessor {
private final static Logger log =  LogManager.getLogger();


    protected static final String kTransactURL = "https://demo.myvirtualmerchant.com/VirtualMerchantDemo/process.do";

    protected static final String[] kElavonTransTypes = {"CCAUTHONLY", "CCFORCE", "CCSALE", "VOID", "CCCREDIT", "ECSPURCHASE"};



    protected String an_password = "";

    protected String an_login = "";


    public ElavonProcessor(String login, String password) {

        an_login = login;

        an_password = password;


    }


    public ElavonProcessor() {


    }


    public void setLogin(String login, String password) {

        an_login = login;

        an_password = password;


    }


       static final  DecimalFormat df = new DecimalFormat("####.00");

    public void process(FinancialTransaction fTrans, boolean testingMode) throws Exception {

        String anTesting = "false";


        if (testingMode)

            anTesting = "true";


        try {


            WebResource server = new WebResource(kTransactURL, WebResource.kGETMethod);


                      server.addParameter("ssl_show_form", "false");
                      server.addParameter("ssl_result_format", "ascii");
                      server.addParameter("ssl_transaction_type", kElavonTransTypes[fTrans.getType()]);
            server.addParameter("ssl_test_mode", anTesting);

            server.addParameter("ssl_merchant_id", an_login);

            server.addParameter("ssl_user_id", an_password.substring(0,an_password.indexOf("|")));
            server.addParameter("ssl_pin", an_password.substring(an_password.indexOf("|")+1));


            server.addParameter("ssl_invoice_number", fTrans.description);

            server.addParameter("ssl_last_name", fTrans.lname);

            server.addParameter("ssl_first_name", fTrans.fname);

            server.addParameter("ssl_avs_address", fTrans.address_one);


            server.addParameter("ssl_amount", "" + df.format((OWDUtilities.roundFloat(fTrans.amount))));


            server.addParameter("ssl_exp_date", fTrans.cc_exp);

            log.debug("CC:"+fTrans.cc_number);
            server.addParameter("ssl_card_number", OWDUtilities.decryptData(fTrans.cc_number));

            server.addParameter("ssl_avs_zip", fTrans.zip);


            server.addParameter("ssl_company", fTrans.company);

            server.addParameter("ssl_city", fTrans.city);

            server.addParameter("ssl_country", fTrans.country);

            server.addParameter("ssl_state", fTrans.state);

            if(fTrans.cvvCode.length()>2)
            {
            server.addParameter("ssl_cvv2cvc2_indicator","1");
            server.addParameter("ssl_cvv2cvc2",fTrans.cvvCode);
            }else
            {

            server.addParameter("ssl_cvv2cvc2_indicator","9");
            }
            server.addParameter("ssl_email", fTrans.email);

            server.addParameter("ssl_phone", fTrans.phone);

            server.addParameter("ssl_customer_code", fTrans.customer_fkey);

            server.addParameter("ssl_description", "Fulfillment");

            server.addParameter("ssl_salestax", fTrans.tax);





            if ((fTrans.getType() == FinancialTransaction.transTypeVoidTransactionRequest) || (fTrans.getType() == FinancialTransaction.transTypeCreditRequest) || (fTrans.getType() == FinancialTransaction.transTypeCaptureOutsideAuthRequest))
            {
                server.addParameter("ssl_txn_id", fTrans.old_trans_id);
            }

            //log.debug("talking to Anet server::");


            BufferedReader reader = null;

            try {

                reader = server.getResource();

            } catch (Exception ex) {

                if (reader == null)

                    reader = server.getResource();

            }

            ////////log.debug("done talking to Anet server for "+fTrans.description);





            CSVReader serverResponse = new CSVReader(reader, false);

            ////////log.debug("got ANet response for "+fTrans.description);

            String x_response_code = "";

            String x_response_subcode = "";

            String x_response_reason_code = "";

            String x_response_reason_text = "";

            String x_auth_code = "";

            String x_avs_code = "";

            String x_trans_id = "";

            Map<String,String> responseMap = new TreeMap<String,String>();


            for(int i=0;i<serverResponse.getRowCount();i++)
            {
                for (int col=0;col<serverResponse.getRowSize(i);col++)
                {
                    log.debug("Row "+i+", C "+col+" :"+serverResponse.getStrValue(col,i,"BADBADBAD"));
                    String[] vals = serverResponse.getStrValue(col,i,"=").split("=");
                    responseMap.put(vals[0],vals[1]);
                }
            }
            log.debug("rows="+serverResponse.getRowCount());
            if (serverResponse.getRowCount() >0) {

                  if(responseMap.get("errorCode") == null)
                {



                x_response_code = responseMap.get("ssl_result");

                x_response_subcode = responseMap.get("ssl_result");

                x_response_reason_code = responseMap.get("ssl_result");

                x_response_reason_text = responseMap.get("ssl_result_message");

                x_auth_code = responseMap.get("ssl_approval_code");

                x_avs_code = responseMap.get("ssl_avs_response");

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

                x_trans_id = responseMap.get("ssl_txn_id");

                  }   else
                  {
                       x_response_code = responseMap.get("errorCode");

                x_response_subcode = responseMap.get("errorCode");

                x_response_reason_code = responseMap.get("errorCode");

                x_response_reason_text = responseMap.get("errorMessage");

                x_auth_code = "";

                x_avs_code = "";

                  }

                log.debug("ELAVON:" + x_response_code + ";" + x_response_subcode + ";" + x_response_reason_code + ";" + x_response_reason_text + ";" + x_auth_code + ";" + x_avs_code + ";" + x_trans_id);


                if ("0".equals(x_response_code)) {

                    fTrans.confirmCC(x_trans_id, x_auth_code, x_avs_code, x_response_reason_text);

                } else  {

                    fTrans.declineCC(x_response_code + ":" + x_response_reason_text);

                }

            } else {

                //log.debug("throwing no response ANet exception ");

                throw new Exception("no response");

            }


        } catch (Exception ex) {

            ex.printStackTrace();

            fTrans.reportError("Elavon server not responding or timed out");

            throw new Exception("Elavon server not responding or timed out. Check account manually before continuing! If the transaction was completed, void it before trying again from this interface.");

        }


    }


    static public void main(String[] args) {

        FinancialTransaction trans = new FinancialTransaction("0",

                "0",

                "0",

                OWDUtilities.getSQLDateTimeForToday(),

                OWDUtilities.getCurrentUserName(),

                OWDUtilities.getCurrentUserName(),

                OWDUtilities.getSQLDateTimeForToday(),

                (float) 55.66,

                OWDUtilities.getSQLDateTimeForToday(),

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

                "0214",

                OWDUtilities.encryptData("4856200220513983x"),

                "",

                "",

                "",

                "",

                "",

                "", 0, 0, OWDUtilities.getSQLDateTimeForToday(),"123");


        ElavonProcessor processor = new ElavonProcessor("000064", "000064|137833");
      //  AuthorizeNetProcessor processor = new AuthorizeNetProcessor("renu837500", "KEY:9U3mmDbu9L8263C9");

        try {

            processor.process(trans, true);


        } catch (Exception ex) {

            ex.printStackTrace();

        }



        //////////log.debug(trans.toString());


    }


}

