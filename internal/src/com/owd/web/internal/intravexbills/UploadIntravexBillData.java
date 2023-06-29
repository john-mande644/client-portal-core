package com.owd.web.internal.intravexbills;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.DelimitedReader;
import com.owd.core.OWDUtilities;
import com.owd.core.TabReader;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 31, 2003
 * Time: 8:41:50 AM
 * To change this template use Options | File Templates.
 */
public class UploadIntravexBillData {
private final static Logger log =  LogManager.getLogger();


    static final int colTypeString = 0;
    static final int colTypeInteger = 1;
    static final int colTypeFloat = 2;
    static final int colTypeDate = 3;


    static public Map<Integer,Integer> importMap = new HashMap<Integer,Integer>();
    static public Map<Integer,Integer> typeMap = new HashMap<Integer,Integer>();
    static public int totalImportColumns = 0;
    {
        importMap.put(2,1);typeMap.put(1,colTypeString);
        importMap.put(3,2);typeMap.put(2,colTypeString);
        importMap.put(4,3);typeMap.put(3,colTypeString);
        importMap.put(5,4);typeMap.put(4,colTypeString);
        importMap.put(7,5);typeMap.put(5,colTypeString);
        importMap.put(8,6);typeMap.put(6,colTypeString);
        importMap.put(9,7);typeMap.put(7,colTypeString);
        importMap.put(10,8);typeMap.put(8,colTypeString);
        importMap.put(11,9);typeMap.put(9,colTypeDate);
        importMap.put(12,10);typeMap.put(10,colTypeDate);
        importMap.put(14,11);typeMap.put(11,colTypeString);
        importMap.put(15,12);typeMap.put(12,colTypeString);
        importMap.put(16,13);typeMap.put(13,colTypeString);
        importMap.put(17,14);typeMap.put(14,colTypeString);
        importMap.put(18,15);typeMap.put(15,colTypeString);
        importMap.put(19,16);typeMap.put(16,colTypeString);
        importMap.put(20,17);typeMap.put(17,colTypeString);
        importMap.put(21,18);typeMap.put(18,colTypeString);
        importMap.put(22,19);typeMap.put(19,colTypeString);
        importMap.put(23,20);typeMap.put(20,colTypeString);
        importMap.put(24,21);typeMap.put(21,colTypeString);
        importMap.put(25,22);typeMap.put(22,colTypeFloat);
        importMap.put(31,23);typeMap.put(23,colTypeFloat);
        importMap.put(32,24);typeMap.put(24,colTypeFloat);
        importMap.put(34,25);typeMap.put(25,colTypeFloat);
        importMap.put(35,26);typeMap.put(26,colTypeFloat);
        importMap.put(36,27);typeMap.put(27,colTypeFloat);
        importMap.put(37,28);typeMap.put(28,colTypeFloat);
        importMap.put(38,29);typeMap.put(29,colTypeFloat);
        importMap.put(39,30);typeMap.put(30,colTypeFloat);
        importMap.put(40,31);typeMap.put(31,colTypeFloat);
        importMap.put(41,32);typeMap.put(32,colTypeFloat);
        importMap.put(42,33);typeMap.put(33,colTypeFloat);
        importMap.put(43,34);typeMap.put(34,colTypeFloat);
        importMap.put(44,35);typeMap.put(35,colTypeFloat);
        importMap.put(45,36);typeMap.put(36,colTypeFloat);
        importMap.put(46,37);typeMap.put(37,colTypeFloat);
        importMap.put(47,38);typeMap.put(38,colTypeFloat);
        importMap.put(53,39);typeMap.put(39,colTypeInteger);
        importMap.put(54,40);typeMap.put(40,colTypeString);
        importMap.put(55,41);typeMap.put(41,colTypeString);
        importMap.put(57,42);typeMap.put(42,colTypeFloat);
        importMap.put(58,43);typeMap.put(43,colTypeFloat);
        

        totalImportColumns=58;
}
    DelimitedReader dataReader = null;
    String userNameImport = "";
    String fileNameImport = "";

    String UPSServerPrefix = "https://epackage1.ups.com";

    static public Map UPSCodeMap = new TreeMap();

    static {
        UPSCodeMap.put("ADC", "Address Correction");
        UPSCodeMap.put("DCS", "DCS Charges Not Billed");
        UPSCodeMap.put("ADJ", "Adjustment");
        UPSCodeMap.put("DCR", "DCR Charges Not Billed");
        UPSCodeMap.put("ARS", "Authorized Return Service");
        UPSCodeMap.put("ANS", "ANS Charges Not Billed ");
        UPSCodeMap.put("ASD", "Air Shipping Document");
        UPSCodeMap.put("DNS", "DNS Charges Not Billed ");
        UPSCodeMap.put("ASR", "ASR Charges Not Billed");
        UPSCodeMap.put("IDD", "Invoice Due Date");
        UPSCodeMap.put("CBS", "Consignee Billing System");
        UPSCodeMap.put("CEF", "Credit Extension Fee");
        UPSCodeMap.put("COD", "Collect On Demand");
        UPSCodeMap.put("CTG", "Call Tag");
        UPSCodeMap.put("CTR", "Call Tag Refund");
        UPSCodeMap.put("CW2", "Hundredweight (Shipping Doc)");
        UPSCodeMap.put("CWT", "Hundredweight (Pickup Book)");
        UPSCodeMap.put("DCO", "Delivery Confirmation");
        UPSCodeMap.put("ESC", "Economy Service Charge");
        UPSCodeMap.put("ISS", "Internet Shipping System");
        UPSCodeMap.put("GCC", "Global Consolidated Clearance");
        UPSCodeMap.put("LPF", "Late Payment Fee");
        UPSCodeMap.put("GSR", "Guarantee Service Refund");
        UPSCodeMap.put("MAN", "Manifest");
        UPSCodeMap.put("HAZ", "Hazardous Material");
        UPSCodeMap.put("MAN", "Manifest Shipment");
        UPSCodeMap.put("MIS", "Miscellaneous Charge ");
        UPSCodeMap.put("MPF", "Manual Processing Fee");
        UPSCodeMap.put("NPB", "Not Previously Billed");
        UPSCodeMap.put("OTP", "One Time Pickup");
        UPSCodeMap.put("POD", "Proof of Delivery");
        UPSCodeMap.put("PRE", "Saturday Service Charge ");
        UPSCodeMap.put("RES", "Residential Adjustment");
        UPSCodeMap.put("RSV", "Return Services");
        UPSCodeMap.put("SAI", "Sonic Air");
        UPSCodeMap.put("SCC", "Shipping Charge Correction");
        UPSCodeMap.put("SPL", "UPS Branded Packaging Supply");
        UPSCodeMap.put("SRB", "Standard Recording Book");
        UPSCodeMap.put("SVC", "Service Charge");
        UPSCodeMap.put("TAX", "Tax");
        UPSCodeMap.put("GST", "Tax");
        UPSCodeMap.put("HST", "Tax");
        UPSCodeMap.put("IBS", "IBS Service");
        UPSCodeMap.put("WWS", "Worldwide Services");
        UPSCodeMap.put("RSL", "Return To Shipper");
        UPSCodeMap.put("SAT", "Saturday Delivery");
        UPSCodeMap.put("", "Inbound/Other");
    }

    public UploadIntravexBillData() {

    }

    public void init(DelimitedReader rdr, String filename, String userName) {
        if (rdr == null) return;

        setDataReader(rdr); 
       userNameImport = userName;
       fileNameImport = filename;

      //  processReader();
    }

    public static void main(String[] args) throws Exception
    {
        //bad - import and then update discount levels

      //good one - restore processor thread and run


      //  processFileName("RemittanceDetail_272_20140905_165004.txt");
      //  processFileName("RemittanceDetail_272_20140912_115312.txt");
      //  processFileName("RemittanceDetail_272_20140919_165018.txt");
        processFileName("RemittanceDetail_272_050616_114526.txt");
       // processFileName("RemittanceDetail_272_20151002_165401.txt");




        HibUtils.commit(HibernateSession.currentSession());



    }

    public static void processFileName(String fileName) throws Exception
    {
        DelimitedReader rdr = new TabReader(new BufferedReader(new FileReader(new File("/Intravex/"+fileName))),false);
        UploadIntravexBillData uploader = new UploadIntravexBillData();
        uploader.init(rdr,fileName,"sbuskirk");

        IntravexProcessorThread processor = new IntravexProcessorThread();
        HibUtils.commit(HibernateSession.currentSession());
        processor.run();
    }

    protected void processReader() {
     processReader(null);
    }
        protected void processReader(final HttpSession theSession) {

        if (getDataReader() == null) return;



                 try {
                     Session sess = HibernateSession.currentSession();

                     sess.doWork(new Work() {
                         @Override
                         public void execute(Connection conn) throws SQLException {
                    PreparedStatement stmt =  conn.prepareStatement("insert into intravexebills\n" +
                             "\n" +
                             "(carrierName,\n" +
                             "reference1,\n" +
                             "reference2,\n" +
                             "accountNumber,\n" +
                             "invoiceNumber,\n" +
                             "serviceType,\n" +
                             "packageType,\n" +
                             "tracking,\n" +
                             "shipDate,\n" +
                             "deliveryDate,\n" +
                             "shipName,\n" +
                             "shipCompany,\n" +
                             "shipStreet,\n" +
                             "shipCSZ,\n" +
                             "shipToName,\n" +
                             "shipToAddress,\n" +
                             "shipToCompany,\n" +
                             "shipToCSZ,\n" +
                             "correctCompany,\n" +
                             "correctCSZ,\n" +
                             "correctAddress,\n" +
                             "weight,\n" +
                             "charges,\n" +
                             "discount,\n" +
                             "adjustmentCorrection,\n" +
                             "deliveryAreaCharge,\n" +
                             "overSizeCharge,\n" +
                             "addressCorrectionCharge,\n" +
                             "serviceCharge,\n" +
                             "handlingCharge,\n" +
                             "returnCharge,\n" +
                             "AMDeliveryCharge,\n" +
                             "CommercialAdjustmentCharge,\n" +
                             "dutyAndTaxes,\n" +
                             "miscCharge,\n" +
                             "totalCharge,\n" +
                             "adjustments,\n" +
                             "netcharge,\n" +
                             "heavyFlag,\n" +
                             "sourceType,\n" +
                             "intravexId,fee,totaldiscount,importUser,importFileName" +
                            ")\n" +
                             "\n" +
                             "VALUES\n" +
                             "\n" +
                             "(?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,\n" +
                             "?,?,?,?,?)");

                             try {
                                 int i = 1;
                     for (int j = 1; j < getDataReader().getRowCount(); j++) {

                         int cols = getDataReader().getRowSize(j);
                         log.debug("col count row " + j + " is " + cols);
                         if("0".equals(getDataReader().getStrValue(46, j, "")))
                         {
                             //skip it
                         } else {
                             for (int col = 1; col <= totalImportColumns; col++) {
                                 //log.debug("col " + col);
                                 if (importMap.get(col) != null) {
                                     int currStmtIndex = importMap.get(col);
                                     int currStmtType = typeMap.get(currStmtIndex);

                                     log.debug("For col " + col + ", target param " + currStmtIndex + ":" + currStmtType + " ::: " + getDataReader().getStrValue(col - 1, j, ""));

                                     switch (currStmtType) {

                                         case (colTypeInteger):
                                             //log.debug("int from " + dataHandler.getDataReader().getIntValue(col - 1, j, 0));
                                             stmt.setInt(currStmtIndex, getDataReader().getIntValue(col - 1, j, 0));

                                             break;
                                         case (colTypeFloat):
                                             //   log.debug("float from " + getDataReader().getFloatValue(col - 1, j, 0));
                                             stmt.setFloat(currStmtIndex, OWDUtilities.roundFloat(getDataReader().getFloatValue(col - 1, j, 0.00f)));

                                             break;
                                         case (colTypeString):
                                             //log.debug("float from " + dataHandler.getDataReader().getFloatValue(col - 1, j, 0));
                                             stmt.setString(currStmtIndex, getDataReader().getStrValue(col - 1, j, ""));

                                             break;

                                         case (colTypeDate):
                                             //  log.debug("date from " + getDataReader().getStrValue(col - 1, j, "1900-1-1"));
                                             String dtstr = getDataReader().getStrValue(col - 1, j, "1900-1-1");
                                             if (dtstr.indexOf(" ") >= 0) {
                                                 dtstr = dtstr.substring(0, dtstr.indexOf(" ")).trim();
                                             }
                                             //  log.debug("Final DateStr==" + dtstr + "==");
                                             if (dtstr.equals("")) {
                                                 //   log.debug("setting null date");
                                                 stmt.setNull(currStmtIndex, Types.DATE);
                                             } else {
                                                 // log.debug("Date string finalized as:" + OWDUtilities.getRawSQLDateStrFrom(getDataReader().getStrValue(col - 1, j, "1900-1-1")));
                                                 stmt.setString(currStmtIndex, getDataReader().getStrValue(col - 1, j, "1900-1-1"));
                                             }
                                             break;

                                         default:
                                             log.debug("Unprocessed column " + col + ":" + getDataReader().getStrValue(col - 1, j, ""));
                                             // stmt.setString(currStmtIndex, getDataReader().getStrValue(col - 1, j, ""));

                                     }

                                 }
                             }

                             stmt.setString(44, userNameImport);
                             stmt.setString(45, fileNameImport);     //-7

                             stmt.addBatch();
                             if ((i % 200) == 0) {
                                 log.debug("batch save");
                                 stmt.executeBatch();
                                 log.debug("batch saved");
                             }
                             i++;
                             theSession.setAttribute("updateMessage", "Importing row "+i);

                         }

                     }
                                 stmt.executeBatch();
                                 log.debug("final batch saved");
                             }catch(Exception ex)
                             {
                                 ex.printStackTrace();
                                 throw new SQLException(ex.getMessage());
                             } finally {

                             }
                         }});

                  //   HibUtils.commit(HibernateSession.currentSession());
                 }catch(Exception ex)
                 {
                     ex.printStackTrace();
                 }finally
                 {

                 //  HibernateSession.closeSession();
                 }
    }


    public DelimitedReader getDataReader() {
        return dataReader;
    }

    public void setDataReader(DelimitedReader dataReader) {
        this.dataReader = dataReader;
    }

}
