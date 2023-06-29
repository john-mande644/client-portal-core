package com.owd.core.payment;

import com.owd.core.CSVReader;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Dec 29, 2005
 * Time: 8:49:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class IBillProcessor implements PaymentProcessor {
private final static Logger log =  LogManager.getLogger();
    // password not used - login is "key" value from usaepay online "sources" setup page

    protected String an_password = "";

    protected String an_login = "";

    protected static final String kTransactURL = "https://secure.ibill.com/cgi-win/ccard/tpcard.exe";

    protected static final String[] kIBillTransTypes = {"preauth", "ticket", "sale", "", "refund", "", "ticket"};  //no voids or check payments


    public IBillProcessor(String login, String password) {

        an_login = login;

        an_password = password;


    }


    public IBillProcessor() {


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

            //  server.addParameter("UMtestmode", anTesting);

            server.addParameter("account", an_login);
            server.addParameter("password", an_password);

            server.addParameter("reqtype", "authorize");

            server.addParameter("cardnum", fTrans.cc_number);

            server.addParameter("cardexp", fTrans.cc_exp.substring(2) + fTrans.cc_exp.substring(0, 2));

            server.addParameter("noc", fTrans.fname + " " + fTrans.lname);

            server.addParameter("address1", fTrans.address_one);

            server.addParameter("zipcode", fTrans.zip);

            server.addParameter("amount", "" + fTrans.getAmount());
            server.addParameter("saletype", kIBillTransTypes[fTrans.getType()]);


            server.addParameter("crefnum", fTrans.description);


            if ((fTrans.getType() == FinancialTransaction.transTypeVoidTransactionRequest) || (fTrans.getType() == FinancialTransaction.transTypeCreditRequest) || (fTrans.getType() == FinancialTransaction.transTypeCaptureOutsideAuthRequest))
            {
                //log.debug("old transaction id adding:"+fTrans.old_trans_id);
                server.addParameter("transnum", fTrans.old_trans_id);
            }

            //log.debug("talking to IBill server::" + server.url+":"+server.buffer);


            BufferedReader reader = null;

            try {

                reader = server.getResource();

            } catch (Exception ex) {

                if (reader == null)

                    reader = server.getResource();

            }


            CSVReader serverResponse = new CSVReader(reader, false);




            ////////log.debug("got ANet response for "+fTrans.description);

            String x_response_code = "";

            String x_response_subcode = "";

            String x_response_reason_code = "";

            String x_response_reason_text = "";

            String x_auth_code = "";

            String x_avs_code = "";

            String x_trans_id = "";

            String x_processor_network_text = "";



            for(int i=0;i<serverResponse.getRowCount();i++)
            {
                for (int col=0;col<serverResponse.getRowSize(i);col++)
                {
                    //log.debug("Row "+i+", C "+col+" :"+serverResponse.getStrValue(col,i,"BADBADBAD"));
                }
            }

            if (serverResponse.getRowCount() == 1) {


                x_response_code = serverResponse.getStrValue(0, 0, ""); // "authorized" or "declined"

                x_response_subcode = serverResponse.getStrValue(1, 0, "");   //auth code or reason for decline

                x_response_reason_code = serverResponse.getStrValue(2, 0, "");  //date, ignored

                x_response_reason_text = serverResponse.getStrValue(3, 0, "");  //time, ignored


                x_avs_code = serverResponse.getStrValue(6, 0, "");   //avs code



                 if("authorized".equals(x_response_code))
                 {
                     x_auth_code = serverResponse.getStrValue(1, 0, "");  //auth code
                     x_trans_id =  serverResponse.getStrValue(4, 0, "");  //transaction num (for authorized response)
                 }


                x_processor_network_text = serverResponse.getStrValue(7, 0, ""); //processor network code


                //log.debug("IBILL:" + x_response_code + ";" + x_response_subcode + ";" + x_response_reason_code + ";" + x_response_reason_text + ";" + x_auth_code + ";" + x_avs_code + ";" + x_trans_id);


  //  public void confirmCC(String transid, String auth, String AVS, String response)


                if ("authorized".equals(x_response_code)) {

                    fTrans.confirmCC(x_trans_id,x_auth_code, x_avs_code, x_response_subcode);

                } else if ("declined".equals(x_response_code)) {

                    fTrans.declineCC(x_response_code + ":" + x_response_subcode);

                } else {

                    fTrans.reportError(x_response_code + ":" + x_response_subcode);

                }

            } else {

                //log.debug("throwing no response IBill exception ");

                throw new Exception("no response");

            }


        } catch (Exception ex) {

            ex.printStackTrace();

            fTrans.reportError("IBill server not responding or timed out");

            throw new Exception("IBill server not responding or timed out. Check account manually before continuing! If the transaction was completed, void it before trying again from this interface.");

        }


    }


    static public void main(String[] args) {

        FinancialTransaction trans = new FinancialTransaction("0",

                "1549891",

                "0",

                "",

                "",

                "",

                "",

                (float) 149.40,

                "",

                FinancialTransaction.TRANS_NEW, //status

                "" + FinancialTransaction.TRANS_CC,

                0,

                0,

                "" + FinancialTransaction.transTypeAuthCaptureRequest,

                "Sciutto",

                "Carlo",

                "430 Moscow St",

                "",

                "94112-2806",

                "",

                "",

                "USA",

                "",

                "donotreply@owd.com",

                "",

                "invoicenum:"+ Calendar.getInstance().getTimeInMillis(),

                "0.00",

                "0.00",

                "",

                "",

                "",

                "",

                "",

                "0806",

                //     "4005562233445564",    //good
                OWDUtilities.decryptData("x2iB1f3pRxQHIQuN3RqNGQg6d7idI3rv0Qg="), //decline

                "",

                "",

                "",

                "",

                "",

                "", 0, 0, "");


        IBillProcessor processor = new IBillProcessor();
        processor.setLogin("123547109", "databill001");


        try {

            processor.process(trans, false);


        } catch (Exception ex) {

            ex.printStackTrace();

        }



        //////////log.debug(trans.toString());


    }


    public String getParameterValue(String qs, String parameter) {

        String param = "";

        StringTokenizer st = new StringTokenizer(qs, "&");

        String[] list = new String[st.countTokens()];

        int i = 0;

        while (st.hasMoreElements()) {

            list[i] = new String(st.nextToken());

            i++;

        }

        for (int n = 0; n < list.length; n++) {

            if (list[n].indexOf(parameter) != -1) {

                //log.debug(list[n].indexOf(parameter));

                param = list[n].substring(list[n].indexOf("=") + 1);

                break;

            }

        }

        return param;

    }

/* you get the value of any parameter in the querystring:

String anyparameter = getParameterValue( querystring, parametername)

*/

}
