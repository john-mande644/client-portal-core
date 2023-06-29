package com.owd.core.payment;

import com.owd.core.WebResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Nov 3, 2003
 * Time: 2:06:30 PM
 * To change this template use Options | File Templates.
 */
public class USAePayProcessor implements PaymentProcessor {
private final static Logger log =  LogManager.getLogger();

    // password not used - login is "key" value from usaepay online "sources" setup page

    protected String an_password = "";

    protected String an_login = "";

       protected static final String kTransactURL = "https://www.usaepay.com/gate.php";

    protected static final String[] kUSAEpayTransTypes = {"authonly", "postauth", "sale", "void", "credit", "check", "capture"};




    public USAePayProcessor(String login, String password) {

        an_login = login;

        an_password = password;


    }


    public USAePayProcessor() {


    }

    public void setLogin(String login, String password) {

        an_login = login;

        an_password = password;


    }

    public void process(FinancialTransaction fTrans, boolean testingMode) throws Exception {

         String anTesting = "0";


         if (testingMode)

             anTesting = "1";


         try {


             WebResource server = new WebResource(kTransactURL, WebResource.kPOSTMethod);

             server.addParameter("UMtestmode", anTesting);

             server.addParameter("UMkey", an_login);

             if(an_password.trim().length()>0)
             {
                 MessageDigest m = MessageDigest.getInstance("MD5");
                 m.reset();
                 long hashseed = Calendar.getInstance().getTime().getTime();
                 m.update(((kUSAEpayTransTypes[fTrans.getType()])+":"+an_password+":"+fTrans.getAmount()+":"+fTrans.description+":"+hashseed).getBytes("UTF-8"));
                 byte[] digest = m.digest();
                 BigInteger bigInt = new BigInteger(1,digest);
                 String hashtext = bigInt.toString(16);
// Now we need to zero pad it if you actually want the full 32 chars.
                 while(hashtext.length() < 32 ){
                   hashtext = "0"+hashtext;
                 }
                 server.addParameter("UMhash", "m/"+hashseed+"/"+hashtext);
                 
             }

             server.addParameter("UMinvoice", fTrans.description);

             server.addParameter("UMname", fTrans.fname+" "+fTrans.lname);
             server.addParameter("UMbillfname", fTrans.fname);
             server.addParameter("UMbilllname", fTrans.lname);


             server.addParameter("UMstreet", fTrans.address_one);
             server.addParameter("UMbillstreet", fTrans.address_one);


             server.addParameter("UMamount", "" + fTrans.getAmount());

             server.addParameter("UMcommand", kUSAEpayTransTypes[fTrans.getType()]);

             server.addParameter("UMexpir", fTrans.cc_exp);

             server.addParameter("UMcard", fTrans.cc_number);

             server.addParameter("UMzip", fTrans.zip);

             server.addParameter("UMbillcompany", fTrans.company);

             server.addParameter("UMbillcity", fTrans.city);

             server.addParameter("UMbillcountry", fTrans.country);

             server.addParameter("UMbillstate", fTrans.state);


             server.addParameter("UMemail", fTrans.email);
             server.addParameter("UMcustemail", fTrans.email);

             server.addParameter("UMbillphone", fTrans.phone);

         //    server.addParameter("x_Cust_ID", fTrans.customer_fkey);

             server.addParameter("UMdescription", "Fulfillment");

             server.addParameter("UMshipping", fTrans.shipping);

             server.addParameter("UMtax", fTrans.tax);


             server.addParameter("UMcvv2", fTrans.cvvCode);


             if (fTrans.getType() == FinancialTransaction.transTypeCaptureOutsideAuthRequest)

                 server.addParameter("UMauthCode", fTrans.old_auth_code);


             if ((fTrans.getType() == FinancialTransaction.transTypeVoidTransactionRequest) || (fTrans.getType() == FinancialTransaction.transTypeCreditRequest) || (fTrans.getType() == FinancialTransaction.transTypeCaptureOutsideAuthRequest))

                 server.addParameter("UMrefNum", fTrans.old_trans_id);


             //log.debug("talking to Anet server::" + fTrans.cvvCode);


             BufferedReader reader = null;

             try {

                 reader = server.getResource();

             } catch (Exception ex) {

                 if (reader == null)

                     reader = server.getResource();

             }

             ////////log.debug("done talking to Anet server for "+fTrans.description);

             StringBuffer responseParams = new StringBuffer();
             String inputLine = "";

             while ((inputLine = reader.readLine()) != null)
             {
                //log.debug(inputLine);
                 responseParams.append(inputLine);
             }
             reader.close();


             ////////log.debug("got ANet response for "+fTrans.description);

             String x_response_code = "";

             String x_response_subcode = "";

             String x_response_reason_code = "";

             String x_response_reason_text = "";

             String x_auth_code = "";

             String x_avs_code = "";

             String x_trans_id = "";


             String x_cvv_code = "";



             if (responseParams.length()>0) {


                 x_response_code = getParameterValue(responseParams.toString(),"UMresult");

                 x_response_subcode = getParameterValue(responseParams.toString(),"UMresult");

                 x_response_reason_code = getParameterValue(responseParams.toString(),"UMstatus");

                 x_response_reason_text = URLDecoder.decode(getParameterValue(responseParams.toString(),"UMerror")+":"+getParameterValue(responseParams.toString(),"UMstatus"));

                 x_auth_code = getParameterValue(responseParams.toString(),"UMauthCode");

                 x_avs_code = getParameterValue(responseParams.toString(),"UMavsResultCode");

                 x_trans_id = getParameterValue(responseParams.toString(),"UMrefNum");

                 x_cvv_code = getParameterValue(responseParams.toString(),"UMcvv2ResultCode");


                 log.debug("USAEpay:" + x_response_code + ";" + x_response_subcode + ";" + x_response_reason_code + ";" + x_response_reason_text + ";" + x_auth_code + ";" + x_avs_code + ";" + x_trans_id + ";CVV:" + x_cvv_code);


                 if ("A".equals(x_response_code)) {

                     fTrans.confirmCC(x_trans_id, x_auth_code, x_avs_code, x_response_reason_text, x_cvv_code);

                 } else if ("D".equals(x_response_code)) {

                     fTrans.declineCC(x_response_code + ":" + x_response_reason_text);

                 } else {

                     fTrans.reportError(x_response_code + ":" + x_response_reason_text);

                 }

             } else {

                 //log.debug("throwing no response USAEpay exception ");

                 throw new Exception("no response");

             }


         } catch (Exception ex) {

             ex.printStackTrace();

             fTrans.reportError("USAEpay server not responding or timed out");

             throw new Exception("USAEpay server not responding or timed out. Check account manually before continuing! If the transaction was completed, void it before trying again from this interface.");

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

                "6.23",

                "4.72",

                "",

                "",

                "",

                "",

                "",

                "1209",

           //     "4005562233445564",    //good
                "4111111111111111",    //decline

                "",

                "",

                "",

                "",

                "",

                "", 0, 0, "");


        USAePayProcessor processor = new USAePayProcessor();
        processor.setLogin("fzgu9XTMLbUc7V8f0QIE5n9vAPnWfAEz", "5454");


        try {

            processor.process(trans, true);


        } catch (Exception ex) {

            ex.printStackTrace();

        }



        //////////log.debug(trans.toString());


    }




     public String getParameterValue(String qs, String parameter ){

String param = "";

StringTokenizer st = new StringTokenizer(qs,"&");

String [] list= new String[ st.countTokens()];

int i = 0;

while ( st.hasMoreElements()) {

list[i] = new String(st.nextToken());

i++;

}

for( int n=0 ; n <list.length; n++){

if ( list[n].indexOf(parameter) !=-1 ){

//log.debug(list[n].indexOf(parameter) );

param = list[n].substring( list[n].indexOf("=")+1);

break;

}

}

return param;

}

/* you get the value of any parameter in the querystring:

String anyparameter = getParameterValue( querystring, parametername)

*/
}
