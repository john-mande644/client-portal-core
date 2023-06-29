package com.owd.core.payment
/**
 * Created with IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: 12/17/12
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
class FirstDataProcessor implements PaymentProcessor {



  //  protected static final String[] kAuthNetTransTypes = {"AUTH_ONLY", "PRIOR_AUTH_CAPTURE", "AUTH_CAPTURE", "VOID", "CREDIT", "CHECK"};



    protected String an_password = "";

    protected String an_login = "";


    public FirstDataProcessor(String login, String password) {

        an_login = login;

        an_password = password;


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


        FirstDataProcessor processor = new FirstDataProcessor("A22481-01", "83577pc4");




        try {

            processor.process(trans, false);
            //log.debug("got cvv code:" + trans.proc_cvv_code);

        } catch (Exception ex) {

            ex.printStackTrace();

        }



        //////////log.debug(trans.toString());


    }



    public FirstDataProcessor() {

        super();


    }


    void setLogin(String login, String password) {
        //To change body of implemented methods use File | Settings | File Templates.
        an_login=login;
        an_password=password;
    }

    void process(FinancialTransaction fTrans, boolean testingMode) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.


//        server.addParameter("x_Type", kAuthNetTransTypes[fTrans.getType()]);


  //      server.addParameter("x_Method", kAuthNetPaymentTypes[fTrans.getFOP()]);




        Hashtable hMap = new Hashtable();

        hMap.put("ExactID", an_login);
        hMap.put("Password", an_password);
        hMap.put("User_Name", "RobynKellin");
        hMap.put("Transaction_Type", FirstDataSoapRequestBuilder.kFirstDataTransTypes[fTrans.getType()]);
        hMap.put("DollarAmount", "" + fTrans.getAmount());
      //  hMap.put("SurchargeAmount", "0.0");
        hMap.put("Card_Number",  fTrans.cc_number);
      //  hMap.put("Transaction_Tag", "");
        //hMap.put("Track1", "");
        //hMap.put("Track2", "");
        //hMap.put("PAN", "");
        //hMap.put("Authorization_Num", "");
        hMap.put("Expiry_Date", fTrans.cc_exp); // Use expiry date of January, one year from "now"
        hMap.put("CardHoldersName", fTrans.fname+" "+fTrans.lname);
        hMap.put("VerificationStr1", fTrans.getAddress_one()+"|"+fTrans.getZip()+"|"+fTrans.getCity()+"|"+fTrans.getState()+"|USA");
        hMap.put("VerificationStr2", fTrans.getCvvCode());
        hMap.put("CVD_Presence_Ind", (fTrans.getCvvCode().length()>2?"1":"0"));
        hMap.put("ZipCode", fTrans.getZip());
        //hMap.put("Tax1Amount", "");
        //hMap.put("Tax1Number", "");
        //hMap.put("Tax2Amount", "");
        //hMap.put("Tax2Number", "");
        //hMap.put("Secure_AuthRequired", "");
        //hMap.put("Secure_AuthResult", "");
        //hMap.put("Ecommerce_Flag", "");
        //hMap.put("XID", "");
     //   hMap.put("CAVV", "");
    //    hMap.put("CAVV_Algorithm", "");
        hMap.put("Reference_No", fTrans.description);
    //    hMap.put("Customer_Ref", "");
     //   hMap.put("Reference_3", "");
        hMap.put("Language", "en");
     //   hMap.put("Client_IP", "196.1.1.1");
     //   hMap.put("Client_Email", "youremail@company.com");
        try {



        String xmlResponse = FirstDataSoapRequestBuilder.sendRequest(hMap);
        println("RESPONSE");
        println xmlResponse;
        Map rMap = convertURLParamStringToMap(xmlResponse)


            log.debug("got ANet response for "+fTrans.description);

            String x_response_code = "";

            String x_response_subcode = "";

            String x_response_reason_code = "";

            String x_response_reason_text = "";

            String x_auth_code = "";

            String x_avs_code = "";

            String x_trans_id = "";


            String x_cvv_code = "";




            if (rMap.keySet().size()>0) {


                x_response_code = rMap.get("transaction_approved");

                x_response_subcode = rMap.get("bank_resp_code_2");

                x_response_reason_code = rMap.get("bank_resp_code");

                x_response_reason_text = rMap.get("bank_message");

                x_auth_code = rMap.get("authorization_num");

                x_avs_code = rMap.get("avs");

                x_trans_id = rMap.get("transaction_tag");

                x_cvv_code = rMap.get("cvv2");


                log.debug("FIRSTDATA:" + x_response_code + ";" + x_response_subcode + ";" + x_response_reason_code + ";" + x_response_reason_text + ";" + x_auth_code + ";" + x_avs_code + ";" + x_trans_id + ";CVV:" + x_cvv_code);


                if ("1".equals(x_response_code)) {

                    fTrans.confirmCC(x_trans_id, x_auth_code, x_avs_code, x_response_reason_text, x_cvv_code);

                } else {

                    fTrans.declineCC(x_response_reason_code + ":" + x_response_reason_text);

                }

            } else {

                //log.debug("throwing no response ANet exception ");

                throw new Exception("no response");

            }


        } catch (Exception ex) {

            ex.printStackTrace();

            fTrans.reportError("First Data server not responding or timed out");

            throw new Exception("First Data  server not responding or timed out. Check account manually before continuing! If the transaction was completed, void it before trying again from this interface.");

        }



    }


    public static Map convertURLParamStringToMap(String params)
    {
        Map<String,String> map = new TreeMap<String,String>();
        List<String> vars = params.tokenize("&")
        for(String var:vars)
        {
            List<String> pair = var.tokenize("=")
            println pair.get(0)+":"+(pair.size()>1?URLDecoder.decode(pair.get(1)):"")
            map.put(pair.get(0),(pair.size()>1?URLDecoder.decode(pair.get(1)):""))
        }

        return map;

    }


    }
