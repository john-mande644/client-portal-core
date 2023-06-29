package com.owd.core.payment;


import com.owd.core.CSVReader;
import com.owd.core.WebResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;


public class AuthorizeNet31Processor extends AuthorizeNetProcessor {
private final static Logger log =  LogManager.getLogger();

    public AuthorizeNet31Processor(String login, String password) {

        super(login, password);


    }


    public AuthorizeNet31Processor() {

        super();


    }



    public void process(FinancialTransaction fTrans, boolean testingMode) throws Exception {

        String anTesting = "FALSE";


        if (testingMode)

            anTesting = "TRUE";


        try {


            WebResource server = new WebResource(kTransactURL, WebResource.kGETMethod);


            server.addParameter("x_Version", "3.1");

            server.addParameter("x_Delim_Data", "TRUE");

            server.addParameter("x_Delim_Char", ",");

            server.addParameter("x_Email_Merchant", "FALSE");

            server.addParameter("x_Email_Customer", "FALSE");

            server.addParameter("x_Encap_Char", "\"");
                        server.addParameter("x_relay_response", "FALSE");

            server.addParameter("x_Test_Request", anTesting);

            server.addParameter("x_login", an_login);

            log.debug(""+an_login);
            log.debug(""+an_password+"::::"+an_password.toUpperCase().startsWith("KEY:"));
            log.debug(""+an_password.substring(4));
            if(an_password.toUpperCase().startsWith("KEY:"))
            {
                log.debug("adding key: "+an_password.substring(4));
              server.addParameter("x_tran_key", an_password.substring(4));
            }   else
            {
              server.addParameter("x_password", an_password);
            }

            server.addParameter("x_Invoice_Num", fTrans.description);

            server.addParameter("x_Last_Name", fTrans.lname);

            server.addParameter("x_First_Name", fTrans.fname);

            server.addParameter("x_Address", fTrans.address_one);


            server.addParameter("x_Amount", "" + fTrans.getAmount());

            server.addParameter("x_Type", kAuthNetTransTypes[fTrans.getType()]);

            server.addParameter("x_Exp_Date", fTrans.cc_exp);

            server.addParameter("x_Card_Num", fTrans.cc_number);

            server.addParameter("x_zip", fTrans.zip);

            server.addParameter("x_Method", kAuthNetPaymentTypes[fTrans.getFOP()]);

            server.addParameter("x_Company", fTrans.company);

            server.addParameter("x_City", fTrans.city);

            server.addParameter("x_Country", fTrans.country);

            server.addParameter("x_state", fTrans.state);


            server.addParameter("x_Email", fTrans.email);

            server.addParameter("x_phone", fTrans.phone);

            server.addParameter("x_Cust_ID", fTrans.customer_fkey);

            server.addParameter("x_Description", "Fulfillment");

            server.addParameter("x_Freight", fTrans.shipping);

            server.addParameter("x_Tax", fTrans.tax);


            server.addParameter("x_card_code", fTrans.cvvCode);


            if (fTrans.getType() == FinancialTransaction.transTypeCaptureOutsideAuthRequest)

                server.addParameter("x_Auth_Code", fTrans.old_auth_code);


            if ((fTrans.getType() == FinancialTransaction.transTypeVoidTransactionRequest) || (fTrans.getType() == FinancialTransaction.transTypeCreditRequest) || (fTrans.getType() == FinancialTransaction.transTypeCaptureOutsideAuthRequest))

                server.addParameter("x_Trans_ID", fTrans.old_trans_id);


            //log.debug("talking to Anet server::" + fTrans.cvvCode);


            BufferedReader reader = null;

            try {

                reader = server.getResource();

            } catch (Exception ex) {

                if (reader == null)

                    reader = server.getResource();

            }

            ////////log.debug("done talking to Anet server for "+fTrans.description);





            CSVReader serverResponse = new CSVReader(reader, false);

    log.debug("got ANet response for "+fTrans.description);

            String x_response_code = "";

            String x_response_subcode = "";

            String x_response_reason_code = "";

            String x_response_reason_text = "";

            String x_auth_code = "";

            String x_avs_code = "";

            String x_trans_id = "";


            String x_cvv_code = "";


            for (int i = 0; i < serverResponse.getRowCount(); i++) {
                for (int col = 0; col < serverResponse.getRowSize(i); col++) {
                    log.debug("Row " + i + ", C " + col + " :" + serverResponse.getStrValue(col, i, "BADBADBAD"));
                }
            }

            if (serverResponse.getRowCount() == 1) {


                x_response_code = serverResponse.getStrValue(0, 0, "");

                x_response_subcode = serverResponse.getStrValue(1, 0, "");

                x_response_reason_code = serverResponse.getStrValue(2, 0, "");

                x_response_reason_text = serverResponse.getStrValue(3, 0, "");

                x_auth_code = serverResponse.getStrValue(4, 0, "");

                x_avs_code = serverResponse.getStrValue(5, 0, "");

                x_trans_id = serverResponse.getStrValue(6, 0, "");

                x_cvv_code = serverResponse.getStrValue(38, 0, "");


                log.debug("AN31:" + x_response_code + ";" + x_response_subcode + ";" + x_response_reason_code + ";" + x_response_reason_text + ";" + x_auth_code + ";" + x_avs_code + ";" + x_trans_id + ";CVV:" + x_cvv_code);


                if ("1".equals(x_response_code)) {

                    fTrans.confirmCC(x_trans_id, x_auth_code, x_avs_code, x_response_reason_text, x_cvv_code);

                } else if ("2".equals(x_response_code)) {

                    fTrans.declineCC(x_response_code + ":" + x_response_reason_text);

                } else {

                    fTrans.reportError(x_response_code + ":" + x_response_reason_text);

                }

            } else {

                //log.debug("throwing no response ANet exception ");

                throw new Exception("no response");

            }


        } catch (Exception ex) {

            ex.printStackTrace();

            fTrans.reportError("AuthorizeNet server not responding or timed out");

            throw new Exception("AuthorizeNet server not responding or timed out. Check account manually before continuing! If the transaction was completed, void it before trying again from this interface.");

        }


    }


    static public void main(String[] args) {

        FinancialTransaction trans = new FinancialTransaction("0",

                    "0",

                    "0",

                    "",

                    "",

                    "",

                    "",

                    (float) 16.71,

                    "",

                    FinancialTransaction.TRANS_NEW, //status

                    "" + FinancialTransaction.TRANS_CC,

                    0,

                    0,

                    "" + FinancialTransaction.transTypeAuthOnlyRequest,

                    "Jo",

                    "Emtage",

                    "10424 marine view Dr SW",

                    "",

                    "98146",

                    "",

                    "",

                    "USA",

                    "",

                    "owditadmin@owd.com",

                    "",

                    "razorama-13573",

                    "0.00",

                    "6.76",

                    "",

                    "",

                    "",

                    "",

                    "",

                    "1212",

                    "4111111111111111",

                    "",

                    "",

                    "",

                    "",

                    "",

                    "", 0, 0, "","123");

        //trans.cvvCode = "123";


       // AuthorizeNet31Processor processor = new AuthorizeNet31Processor("renu837500", "KEY:9U3mmDbu9L8263C9");

      //  AuthorizeNet31Processor processor = new AuthorizeNet31Processor("renu837500", "KEY:9U3mmDbu9L8263C9");


        AuthorizeNet31Processor processor = new AuthorizeNet31Processor("renu837500", "KEY:36t5P72Khf64gHP8");
    //      AuthorizeNet31Processor processor = new AuthorizeNet31Processor("regalbeauty92", "key:1codyjoe");



        try {

            processor.process(trans, false);
            //log.debug("got cvv code:" + trans.proc_cvv_code);

        } catch (Exception ex) {

            ex.printStackTrace();

        }



        //////////log.debug(trans.toString());


    }

}
