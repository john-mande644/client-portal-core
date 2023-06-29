package com.owd.core.business;

import com.owd.core.managers.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class ChoiceListManager {
private final static Logger log =  LogManager.getLogger();


    private static ChoiceListManager me = null;

    //singleton pattern
    protected static HashMap<String, owdChoiceList> listMap = null;


    public synchronized static ChoiceListManager getChoiceListManager() {
        if (me == null) {
            me = new ChoiceListManager();
            me.init();
        }

        return me;
    }
    //end singleton pattern

    public HashMap getListClone() {
        return (HashMap) listMap.clone();
    }


    //initialize the hashmap of lists
    protected void init() {
        Connection cxn = null;
        Statement stmt = null;
        ResultSet rs = null;

        owdChoiceList rList = null;
        listMap = new HashMap<String, owdChoiceList>();
        String badListName = "thislistnamedoesnotexistandneverwill";

        try {
            cxn = ConnectionManager.getConnection();

            stmt = cxn.createStatement();

            rs = stmt.executeQuery("select list_name,list_value, reference_num, is_default" +
                    " from owd_lists where is_inactive=0" +
                    "order by list_name, list_value");
            String oldListName = badListName;

            while (rs.next()) {
                String currListName = rs.getString("list_name");
                if (!(currListName.equals(oldListName))) {
                    if (!(oldListName.equals(badListName)))
                        listMap.put(oldListName, rList);
                    rList = new owdChoiceList(currListName);
                    oldListName = currListName;

                }
                rList.addElement(rs.getString("list_value"), rs.getString("reference_num"), rs.getBoolean("is_default"));

            }

            if (rList != null & rList.size() > 0) {
                if (!(oldListName.equals(badListName)))
                    listMap.put(oldListName, rList);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Throwable ignore) {
            }
            try {
                stmt.close();
            } catch (Throwable ignore) {
            }
            try {
                cxn.close();
            } catch (Throwable ignore) {
            }
        }
    }


    public owdChoiceList getList(String name) throws Exception {
        if (listMap.containsKey(name)) {
            return listMap.get(name);
        } else {
            throw new Exception("Choice List \"" + name + "\" is not available.");
        }
    }


    public static HashMap<String, String> legacyTranslationMap = new HashMap<String, String>();

    static {
        legacyTranslationMap.put("CHINA", "CHINA PEOPLES REPUBLIC OF");

        legacyTranslationMap.put("USPS Int'l Letter-Post Air", "USPS First-Class Mail International");
        legacyTranslationMap.put("USPS Intl Economy Letter-Post", "USPS First-Class Mail International");
        legacyTranslationMap.put("USPS Int'l Parcel Post Air", "USPS Priority Mail International");
        legacyTranslationMap.put("USPS Int'l Parcel Post Economy", "USPS Priority Mail International");
        legacyTranslationMap.put("USPS Intl Expr Mail-On Demand", "USPS Priority Mail Express International");
        legacyTranslationMap.put("USPS Express Mail International", "USPS Priority Mail Express International");
        legacyTranslationMap.put("USPS Global Priority Mail", "USPS Priority Mail International");
        legacyTranslationMap.put("USPS Express Mail International (EMS)", "USPS Priority Mail Express International");
        // legacyTranslationMap.put("UPS Standard to Canada","UPS Standard Canada");


        legacyTranslationMap.put("USPS Intl Airmail Letter-Post", "USPS First-Class Mail International");
        legacyTranslationMap.put("USPS Intl Economy Letter-Post", "USPS First-Class Mail International");

        legacyTranslationMap.put("USPS Intl Airmail Letter-post", "USPS First-Class Mail International");
        legacyTranslationMap.put("USPS Intl Economy Letter-post", "USPS First-Class Mail International");
        legacyTranslationMap.put("USPS Intl Airmail Parcel Post", "USPS Priority Mail International");
        legacyTranslationMap.put("USPS Intl Economy Parcel Post", "USPS Priority Mail International");
        //Shipping Method references
        legacyTranslationMap.put("First Class", "USPS First-Class Mail");
        legacyTranslationMap.put("USPS Media Mail", "USPS Media Mail Single-Piece");
        legacyTranslationMap.put("Int'l Letter-Post Book/Music Air", "USPS Priority Mail International");
        legacyTranslationMap.put("Int'l Letter-Post/Sm Packet Air", "USPS First-Class Mail International");
        legacyTranslationMap.put("Int'l Parcel Post Air", "USPS Priority Mail International");
        legacyTranslationMap.put("Int'l PM Book/Music Air", "USPS Priority Mail International");
        legacyTranslationMap.put("Int'l PM Book/Music Surface", "USPS Priority Mail International");
        legacyTranslationMap.put("Int'l PM Regular Air", "USPS First-Class Mail International");

        legacyTranslationMap.put("Int'l PM Regular Surface", "USPS Priority Mail International");
        legacyTranslationMap.put("Priority Mail", "USPS Priority Mail");

        legacyTranslationMap.put("UPS Expedited Canada", "UPS Worldwide Expedited");
        legacyTranslationMap.put("UPS Standard to Canada", "UPS Standard Canada");
        legacyTranslationMap.put("UPS Standard", "UPS Standard Canada");
        legacyTranslationMap.put("UPS Express Canada", "UPS Worldwide Express");
        legacyTranslationMap.put("USPS First-Class", "USPS First-Class Mail");
        legacyTranslationMap.put( "USPS Expr Mail-Addressee","USPS Priority Mail Express");
        legacyTranslationMap.put("USPS Express Mail (Addressee)","USPS Priority Mail Express");
        legacyTranslationMap.put("FedEx Ground Guaranteed", "FedEx Ground");
        legacyTranslationMap.put("USPS Int'l Express Mail", "USPS Global Express Mail");
        legacyTranslationMap.put("USPS Int'l Letter", "USPS First-Class Mail International");
        legacyTranslationMap.put("USPS Int'l Letter-Post", "USPS First-Class Mail International");
        legacyTranslationMap.put("USPS Int'l Parcel Post", "USPS Priority Mail International");
        legacyTranslationMap.put("USPS Int'l Printed Matter", "USPS Priority Mail International");

        legacyTranslationMap.put("USPS Global Express Mail", "USPS Priority Mail Express International");
        legacyTranslationMap.put("USPS Intl Global Express Mail", "USPS Priority Mail Express International");
        legacyTranslationMap.put("UPS Worldwide Saver", "UPS Worldwide Express Saver");
/*

        legacyTranslationMap.put("FedEx Ground U.S. to Canada", "UPS Standard Canada");
        legacyTranslationMap.put("FedEx International Economy", "UPS Worldwide Expedited");
        legacyTranslationMap.put("FedEx International First", "UPS Worldwide Express");
        legacyTranslationMap.put("FedEx International Priority", "UPS Worldwide Express Saver");*/

        legacyTranslationMap.put("USPS Parcel Post","USPS Parcel Select Nonpresort");

        legacyTranslationMap.put("TANDATA_USPS.USPS.PARCELPOST", "TANDATA_USPS.USPS.PS_NONPRESORT");
        legacyTranslationMap.put("OWD.AIR.BSM", "OWD_USPS_I_FIRST");
        legacyTranslationMap.put("OWD.AIR.PMATTER", "OWD_USPS_I_FIRST");
        legacyTranslationMap.put("OWD.SFC.BSM", "OWD_USPS_I_PRIORITY");
        legacyTranslationMap.put("OWD.SFC.PMATTER", "OWD_USPS_I_PRIORITY");
        legacyTranslationMap.put("POS.1ST", "OWD.1ST.LETTER");
        legacyTranslationMap.put("POS.4TH", "OWD.4TH.BOOK");
        legacyTranslationMap.put("POS.IAO", "OWD_USPS_I_PRIORITY");
        legacyTranslationMap.put("POS.IPP", "OWD_USPS_I_PRIORITY");
        legacyTranslationMap.put("UPS.EPDCAMX", "UPS.WEPD");
        legacyTranslationMap.put("UPS.EXPCAMX", "UPS.WEXP");

        legacyTranslationMap.put("POS.GPM", "OWD_USPS_I_PRIORITY");
        legacyTranslationMap.put("OWD.SFC.SMP", "OWD_USPS_I_FIRST");
        legacyTranslationMap.put("OWD.AIR.PARCELPOST", "OWD_USPS_I_PRIORITY");
        legacyTranslationMap.put("OWD.SFC.PARCELPOST", "OWD_USPS_I_PRIORITY");
        legacyTranslationMap.put("POS.IEX", "OWD_USPS_I_EXP_DMND");
        legacyTranslationMap.put("OWD.AIR.SMP", "OWD_USPS_I_PRIORITY");


        legacyTranslationMap.put("OWD.AIR.PUP", "OWD_USPS_I_PRIORITY");
        legacyTranslationMap.put("OWD.SFC.PUP", "OWD_USPS_I_PRIORITY");


        legacyTranslationMap.put("TANDATA_USPS.USPS.I_LETTER_AIR", "TANDATA_USPS.USPS.I_FIRST");
        legacyTranslationMap.put("TANDATA_USPS.USPS.I_LETTER_SURF", "TANDATA_USPS.USPS.I_FIRST");
        legacyTranslationMap.put("TANDATA_USPS.USPS.I_PP_AIR", "TANDATA_USPS.USPS.I_PRIORITY");
        legacyTranslationMap.put("TANDATA_USPS.USPS.I_PP_SURF", "TANDATA_USPS.USPS.I_PRIORITY");
        legacyTranslationMap.put("TANDATA_UPS.UPS.3DA","CONNECTSHIP_UPS.UPS.3DA");
        legacyTranslationMap.put("TANDATA_UPS.UPS.2DA","CONNECTSHIP_UPS.UPS.2DA");
        legacyTranslationMap.put("TANDATA_UPS.UPS.2AM","CONNECTSHIP_UPS.UPS.2AM");
        legacyTranslationMap.put("TANDATA_UPS.UPS.GND","CONNECTSHIP_UPS.UPS.GND");
        legacyTranslationMap.put("TANDATA_UPS.UPS.NAM","CONNECTSHIP_UPS.UPS.NAM");
        legacyTranslationMap.put("TANDATA_UPS.UPS.NDA","CONNECTSHIP_UPS.UPS.NDA");
        legacyTranslationMap.put("TANDATA_UPS.UPS.NDS","CONNECTSHIP_UPS.UPS.NDS");
        legacyTranslationMap.put("TANDATA_UPS.UPS.WEPD","CONNECTSHIP_UPS.UPS.EPD");
        legacyTranslationMap.put("TANDATA_UPS.UPS.WEXP","CONNECTSHIP_UPS.UPS.EXP");
        legacyTranslationMap.put("TANDATA_UPS.UPS.WEXPPLS","CONNECTSHIP_UPS.UPS.EXPPLS");
        legacyTranslationMap.put("UPS Worldwide Expedited","UPS Expedited");
        legacyTranslationMap.put("UPS Worldwide Express","UPS Express");
        legacyTranslationMap.put("UPS Worldwide Express Plus","UPS Express Plus");
        legacyTranslationMap.put("UPS Worldwide Express Saver","UPS Expres Saver");

/*
legacyTranslationMap.put("FDX.IPR", "CONNECTSHIP_UPS.UPS.EXPSVR");
legacyTranslationMap.put("FDX.IEC", "CONNECTSHIP_UPS.UPS.EPD");
        legacyTranslationMap.put("TANDATA_FEDEXBOOK.FEDEX.GND", "CONNECTSHIP_UPS.UPS.GND");
legacyTranslationMap.put("TANDATA_FEDEXBOOK.FEDEX.PRI", "CONNECTSHIP_UPS.UPS.NDA");
legacyTranslationMap.put("TANDATA_FEDEXBOOK.FEDEX.STD", "CONNECTSHIP_UPS.UPS.NDS");

        legacyTranslationMap.put("TANDATA_FEDEXBOOK.FEDEX.2DA", "CONNECTSHIP_UPS.UPS.2DA");

legacyTranslationMap.put("TANDATA_FEDEXBOOK.FEDEX.ECO", "CONNECTSHIP_UPS.UPS.2DA");
legacyTranslationMap.put("TANDATA_FEDEXBOOK.FEDEX.ESP", "CONNECTSHIP_UPS.UPS.3DA");
legacyTranslationMap.put("TANDATA_FEDEXBOOK.FEDEX.IPR", "CONNECTSHIP_UPS.UPS.EXPSVR");
legacyTranslationMap.put("TANDATA_FEDEXBOOK.FEDEX.IEC", "CONNECTSHIP_UPS.UPS.EPD");
legacyTranslationMap.put("TANDATA_FEDEXGROUND.FEDEX.CAN", "CONNECTSHIP_UPS.UPS.STD");
legacyTranslationMap.put("TANDATA_FEDEXBOOK.FEDEX.IFO", "CONNECTSHIP_UPS.UPS.EXP");*/
        //USPS Intl Global Express Mail
    }

    public static String getTranslatedString(String oldValue) {

       // log.debug("translating "+oldValue+":"+legacyTranslationMap.get(oldValue));
        String newValue = oldValue;
        if (newValue != null) {
            if (legacyTranslationMap.containsKey(oldValue))
                newValue = legacyTranslationMap.get(oldValue);

        }

        return newValue;

    }
}

/* Legacy values functions */

	
	
	
	
	
	
	
