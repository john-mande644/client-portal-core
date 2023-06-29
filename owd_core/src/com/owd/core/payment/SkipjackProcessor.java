/*
 * VerisignProcessor.java
 *
 * Created on August 22, 2003, 11:47 AM
 */

package com.owd.core.payment;

import com.owd.core.CSVReader;
import com.owd.core.OWDUtilities;
import com.owd.core.WebResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liheng
 */
public class SkipjackProcessor implements PaymentProcessor {
private final static Logger log =  LogManager.getLogger();

    private static final String kTestTransactURL = "https://developer.skipjackic.com/scripts/evolvcc.dll?AuthorizeAPI";
    private static final String kTransactURL = "https://www.skipjackic.com/scripts/evolvcc.dll?AuthorizeAPI";


    //HTML Serial Number (client-specific)
    protected String an_password = "";

    //for testing:
//VITAL Serial Number: 000458405006
//NOVA Serial Number: 000293943136

    protected String test_password = "000458405006";

    //Developer Serial Number (OWD general)
    protected String an_login = "100788698836";


    public SkipjackProcessor(String login, String password) {

      //  an_login = login;

        an_password = password;


    }


    public SkipjackProcessor() {


    }


    public void setLogin(String login, String password) {

      //  an_login = login;

        an_password = password;


    }


    public void process(FinancialTransaction fTrans, boolean testingMode) throws Exception {


//construct parameter list
         WebResource sjServer = new WebResource(testingMode?kTestTransactURL:kTransactURL,WebResource.kPOSTMethod);
         sjServer.clearParameters();

         String prefix = "";
         long orderid = 0;
        if(fTrans.order_fkey==null)
        {
           orderid=Calendar.getInstance().getTimeInMillis();
        } else
        {
        try
        {
            orderid = Integer.parseInt(fTrans.order_fkey);
        } catch(Exception exn)
        {
            orderid = Calendar.getInstance().getTimeInMillis();
        }
        }

         DecimalFormat df = new DecimalFormat("####.00");

        switch (fTrans.getType()) {
            case FinancialTransaction.transTypeAuthCaptureRequest:
                  addAuthCustomerInfo(fTrans, sjServer);
                 sjServer.addParameter( "AccountNumber", fTrans.cc_number);
                sjServer.addParameter("OrderNumber",""+orderid);
                sjServer.addParameter("OrderString","Ordered~Items~"+fTrans.amount+"~1~||");
                
                 sjServer.addParameter( "TransactionAmount", ""+df.format((OWDUtilities.roundFloat(fTrans.amount))));
                    sjServer.addParameter( "Month", fTrans.cc_exp.substring(0,2));
                    sjServer.addParameter( "Year", fTrans.cc_exp.substring(2,4));
                sjServer.addParameter("ShipToPhone",fTrans.phone);
                break;
            case FinancialTransaction.transTypeCaptureOnlyRequest:

                throw new Exception("Capture only function not implemented for skipjack processor");
            case FinancialTransaction.transTypeAuthOnlyRequest:
                addAuthCustomerInfo(fTrans, sjServer);
                sjServer.addParameter("OrderString","Ordered~Items~"+fTrans.amount+"~1~||");

                sjServer.addParameter( "TransactionAmount", ""+(OWDUtilities.roundFloat(fTrans.amount)));
                sjServer.addParameter("OrderNumber",""+orderid);
                 sjServer.addParameter( "AccountNumber", fTrans.cc_number);
                    sjServer.addParameter( "Month", fTrans.cc_exp.substring(0,2));
                    sjServer.addParameter( "Year", fTrans.cc_exp.substring(2,4));
                sjServer.addParameter("ShipToPhone",fTrans.phone);
                break;
            case FinancialTransaction.transTypeVoidTransactionRequest:

                throw new Exception("Void function not implemented for skipjack processor");
            case FinancialTransaction.transTypeCreditRequest:
             //   prefix="sz";
            //      sjServer.addParameter( "szTransactionId", fTrans.proc_trans_id);
                throw new Exception("Credit function not implemented for skipjack processor");
               // break;
            case FinancialTransaction.transTypeCheckTransactionRequest:
                throw new Exception("ECheck transactions not supported for skipjack processor");
            case FinancialTransaction.transTypeCaptureOutsideAuthRequest:
                throw new Exception("External or voice authorization captures not supported for skipjack processor");
            default:
                throw new Exception("Unknown Transaction Type");
        }


       sjServer.addParameter( prefix+"SerialNumber", testingMode?test_password:an_password);
       sjServer.addParameter( prefix+"DeveloperSerialNumber", an_login);

//set OWD unique reference number
        sjServer.addParameter(prefix+"Comment", fTrans.description);

//set other optional parameters

        CSVReader resp = null;
        try {

            resp = new CSVReader(sjServer.getResource(false),false);

        } catch (Exception ex) {
            throw ex;
        } finally {


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

            Map<String,String> respMap = new HashMap<String,String>();

                for (int col=0;col<resp.getRowSize(0);col++)
                {
                    respMap.put(resp.getStrValue(col,0,null),resp.getStrValue(col,1,""));
                }

             x_cvv_code = respMap.get("szCVV2ResponseCode");
            x_response_code = respMap.get("szReturnCode");
                    x_response_reason_text = respMap.get("szAuthorizationDeclinedMessage");
                    x_trans_id = respMap.get("szTransactionFileName");
                    x_auth_code = respMap.get("szAuthorizationResponseCode");
                    x_avs_code = respMap.get("szAVSResponseCode");

            log.debug(""+respMap);
       /*         if (p.indexOf(pResult) >= 0) {
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

                                }*/

        //    }


            log.debug("VS:" + x_response_code + ";" + x_response_subcode + ";" + x_response_reason_code + ";" + x_response_reason_text + ";" + x_auth_code + ";" + x_avs_code + ";" + x_trans_id);


            int response_code = -1;
            try {
                response_code = Integer.parseInt(x_response_code);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }


            if (response_code == 1 && x_response_reason_text.trim().equals("") && x_auth_code.trim().length()>1) {

                fTrans.confirmCC(x_trans_id,x_auth_code,x_avs_code,x_response_reason_text,x_cvv_code);

                if(fTrans.getType()==FinancialTransaction.transTypeAuthCaptureRequest)
                {
                   //captureTransaction(fTrans,x_trans_id,testingMode);
                }

            } else {
                fTrans.declineCC(x_response_reason_text);

            }


        } else {

            //log.debug("throwing no response CC processor exception ");

            throw new Exception("No response from the processor server");

        }


    }

    private void captureTransaction(FinancialTransaction ftrans, String transactionid,boolean testing)  throws Exception
    {
              WebResource sjServer = new WebResource(testing?"https://developer.skipjackic.com/scripts/evolvcc.dll?SJAPI_TransactionChangeStatusRequest":"https://www.skipjackic.com/scripts/evolvcc.dll?SJAPI_TransactionChangeStatusRequest",WebResource.kPOSTMethod);
         sjServer.clearParameters();
       sjServer.addParameter( "szSerialNumber", testing?test_password:an_password);
       sjServer.addParameter( "szDeveloperSerialNumber", an_login);
       sjServer.addParameter( "szTransactionId", transactionid);
       sjServer.addParameter( "szDesiredStatus", "SettleEx");
        CSVReader resp = null;
        try {

            resp = new CSVReader(sjServer.getResource(false),false);

        } catch (Exception ex) {
            throw ex;
        } finally {


        }


        for (int col=0;col<resp.getRowSize(0);col++)
        {
            log.debug("rep row 1");
            log.debug(resp.getStrValue(col,0,null));
        }
 for (int col=0;col<resp.getRowSize(1);col++)
        {
            log.debug("rep row 2");
            log.debug(resp.getStrValue(col,1,null));
        }

          if(resp.getIntValue(1,0,-1)==0)
          {
              log.debug("success capture");
          }           else
          {
              throw new Exception("Could not capture funds");
          }
         
    }


    private void addAuthCustomerInfo(FinancialTransaction fTrans, WebResource sjServer) {
        if (fTrans.lname.length() > 0 || fTrans.fname.length() > 0) {
sjServer.addParameter( "SJName", fTrans.fname + " " + fTrans.lname);
}

        if (fTrans.address_one.length() > 0) {
            sjServer.addParameter( "StreetAddress", fTrans.address_one);
        }
        if (fTrans.address_two.length() > 0) {
           sjServer.addParameter( "StreetAddress2", fTrans.address_two);
       }

        if (fTrans.city.length() > 0 || fTrans.city.length() > 0) {
        sjServer.addParameter( "City", fTrans.city);
    }

        if (fTrans.state.length() > 0 || fTrans.state.length() > 0) {
        sjServer.addParameter( "State", fTrans.state);
    }
        if (fTrans.email.length() > 0 || fTrans.email.length() > 0) {
           sjServer.addParameter( "Email", fTrans.email);
       } else
        {
               sjServer.addParameter( "Email", "donotreply@owd.com");  
        }



        if (fTrans.zip.length() > 0) {
            sjServer.addParameter( "ZipCode", fTrans.zip);
        }

        if(fTrans.cvvCode.length()>0)
                {
                    sjServer.addParameter( "CVV2", fTrans.cvvCode);
                }
    }


    static public void main(String[] args) {

        DecimalFormat df = new DecimalFormat("####.00");
        log.debug( ""+df.format((OWDUtilities.roundFloat(260.10f))));
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

                "" + FinancialTransaction.transTypeAuthCaptureRequest,

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

                "6058457172",

                "invoicenum",

                "1.23",

                "3.21",

                "",

                "",

                "",

                "",

                "",

                "1209",

                "4111111111111111",

                "",

                "",

                "",

                "",

                "",

                "", 0, 0, "");


        SkipjackProcessor processor = new SkipjackProcessor("", "000458405006");
        try {

            processor.process(trans, false);
            log.debug(trans.error_reponse);

        } catch (Exception ex) {

            ex.printStackTrace();

        }



        //////////log.debug(trans.toString());


    }


}
