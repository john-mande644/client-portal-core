package com.owd.core;

/**
 * A class with the method getCountryName(String)
 *to return a full country name given either an IANA code or a country name
 */


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class CountryNames {
private final static Logger log =  LogManager.getLogger();
 //   static final IANACountries iana = new IANACountries();
    static ArrayList countryList = null;

    public static String getCountryName(String codeOrName) {
        try {
            if (codeOrName == null) codeOrName = "";

            codeOrName = codeOrName.trim();

            codeOrName = fixCodeOrName(codeOrName);

            if (codeOrName.equals("") || codeOrName.toUpperCase().equals("UNITED STATES") || codeOrName.toUpperCase().equals("USA") || codeOrName.toUpperCase().equals("US")) {
                return "USA";
            }
            String cn = codeOrName.toUpperCase();
            if (IANACountries.containsCode(cn)) {
                cn = IANACountries.getCountryForCode(cn);
            } else if (!(IANACountries.containsName(cn))) {
                Mailer.sendMail("New Country Name or Code", codeOrName + "\n\n" + OWDUtilities.getStackTraceAsString(new Exception()), "owditadmin@owd.com", "support@owd.com");
                return "USA";
            }


                return cn; //fixed to return a capitalized String instead of codeOrName



        } catch (Exception ex) {
            try{
            Mailer.sendMail("New Country Name or Code", codeOrName + "\n\n" + OWDUtilities.getStackTraceAsString(ex), "owditadmin@owd.com", "support@owd.com");}catch(Exception exx){};
            return "USA";
        }


    }

    public static String fixCodeOrName(String codeOrName)
    {
        if (codeOrName.equalsIgnoreCase("SOUTH KOREA")) return "KOREA SOUTH";
      //  if (codeOrName.equalsIgnoreCase("BRUNEI")) return "BRUNEI DARUSSALAM";
        if (codeOrName.equalsIgnoreCase("SLOVAK REPUBLIC")) return "SLOVAKIA";
        if (codeOrName.equalsIgnoreCase("TRINIDAD & TOBAGO")) return "TRINIDAD TOBAGO";
        if (codeOrName.equalsIgnoreCase("TRINIDAD AND TOBAGO")) return "TRINIDAD TOBAGO";
        if (codeOrName.equalsIgnoreCase("CHINA")) return "CHINA PEOPLES REPUBLIC OF";
        if (codeOrName.equalsIgnoreCase("RUSSIAN FEDERATION")) return "RUSSIA";
        if (codeOrName.equalsIgnoreCase("United States Minor Outlying Islands")) return "US MINOR OUTLYING ISLANDS";

        if (codeOrName.equalsIgnoreCase("ST. LUCIA")) codeOrName = "ST LUCIA";
        if (codeOrName.equalsIgnoreCase("ST. KITTS AND NEVIS")) codeOrName = "ST. KITTS & NEVIS";
        if (codeOrName.equalsIgnoreCase("MACAO")) codeOrName = "MACAU";
        if (codeOrName.equalsIgnoreCase("ANTIGUA AND BARBUDA")) codeOrName = "ANTIGUA";
        if (codeOrName.equalsIgnoreCase("Virgin Islands, British")) codeOrName = "BRITISH VIRGIN ISLANDS";
        if (codeOrName.equalsIgnoreCase("Serbia and Montenegro")) codeOrName = "SERBIA";
        if (codeOrName.equalsIgnoreCase("AX")) codeOrName = "FINLAND";
        return codeOrName;

    }
   	public static String getCountryNameIfRecognized(String codeOrName) throws Exception
	{

			if(codeOrName==null||codeOrName.equals(""))
                        {
                            throw new Exception ("Country name "+codeOrName+" not found!");
                        }


        codeOrName = fixCodeOrName(codeOrName);

        String cn = codeOrName.toUpperCase();

			if(IANACountries.containsCode(cn))
			{
				cn = IANACountries.getCountryForCode(cn);
			}
			else if(!(IANACountries.containsName(cn)))
			{
				throw new Exception ("Country name "+codeOrName+" not found!");
			}


				return cn; //fixed to return a capitalized String instead of codeOrName


		


	}

    public static void main(String[] args)  throws Exception
    {

        log.debug(CountryNames.getCountryNameIfRecognized("CN"));
    }
    public static boolean exists(String codeOrName) {


        try {
            if (codeOrName == null) codeOrName = "";
            codeOrName = codeOrName.trim();


            codeOrName = fixCodeOrName(codeOrName);

            if (codeOrName.equals("") || codeOrName.toUpperCase().equals("UNITED STATES") || codeOrName.toUpperCase().equals("USA") || codeOrName.toUpperCase().equals("US")) {
                return true;
            }
            String cn = codeOrName.toUpperCase();
            if (IANACountries.containsCode(cn)) {
                return true;
            } else if (!(IANACountries.containsName(cn))) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }

    }

}
	
