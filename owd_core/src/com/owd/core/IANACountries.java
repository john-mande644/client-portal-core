package com.owd.core;

// $Id: IANACountries.java,v 1.33 2014/07/01 22:26:35 stewart Exp $

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class IANACountries {
private final static Logger log =  LogManager.getLogger();
    static Map<String,String> nations = new TreeMap<String,String>();

    static  {
        addToTable("AFGHANISTAN", "AF");
        addToTable("ALBANIA", "AL");
        addToTable("ALGERIA", "DZ");
        addToTable("AMERICAN SAMOA", "AS");
        addToTable("ANDORRA", "AD");
        addToTable("ANGOLA", "AO");
        addToTable("ANGUILLA ISLANDS", "AI");
        addToTable("ANTIGUA", "AG");
        addToTable("ARGENTINA", "AR");
        addToTable("ARMENIA", "AM");
        addToTable("ARUBA", "AW");
        addToTable("AUSTRALIA", "AU");
        addToTable("AUSTRIA", "AT");
        addToTable("AZERBAIJAN", "AZ");
        addToTable("BAHAMAS", "BS");
        addToTable("BAHRAIN", "BH");
        addToTable("BANGLADESH", "BD");
        addToTable("BARBADOS", "BB");
        addToTable("BELARUS", "BY");
        addToTable("BELGIUM", "BE");
        addToTable("BELIZE", "BZ");
        addToTable("BENIN", "BJ");
        addToTable("BERMUDA", "BM");
        addToTable("BHUTAN", "BT");
        addToTable("BOLIVIA", "BO");
        addToTable("BOSNIA", "BA");
        addToTable("BOTSWANA", "BW");
        addToTable("BRAZIL", "BR");
        addToTable("BRITISH VIRGIN ISLANDS", "VG");
        addToTable("BRUNEI", "BN");
        addToTable("BULGARIA", "BG");
        addToTable("BURKINA FASO", "BF");
        addToTable("BURUNDI", "BI");
        addToTable("CAMBODIA", "KH");
        addToTable("CAMEROON", "CM");
        addToTable("CANADA", "CA");
        addToTable("CAPE VERDE", "CV");
        addToTable("CAYMAN ISLANDS", "KY");
        addToTable("CENTRAL AFRICAN REPUBLIC", "CF");
        addToTable("CHAD", "TD");
        addToTable("CHILE", "CL");
        addToTable("CHINA PEOPLES REPUBLIC OF", "CN");
     //   addToTable("CHINA","CN");
        addToTable("COCOS (KEELING) ISLANDS", "CC");
        addToTable("COLOMBIA", "CO");
        addToTable("COMOROS", "KM");
        addToTable("CONGO", "CG");
        addToTable("COOK ISLANDS", "CK");
        addToTable("COSTA RICA", "CR");
        addToTable("CROATIA", "HR");
        addToTable("CUBA", "CU");
        addToTable("CURACAO", "CW");
        addToTable("CYPRUS", "CY");
        addToTable("CZECH REPUBLIC", "CZ");
        addToTable("DENMARK", "DK");
        addToTable("DJIBOUTI", "DJ");
        addToTable("DOMINICA", "DM");
        addToTable("DOMINICAN REPUBLIC", "DO");
        addToTable("EAST TIMOR", "TP");
        addToTable("ECUADOR", "EC");
        addToTable("EGYPT", "EG");
        addToTable("EL SALVADOR", "SV");
        addToTable("EQUATORIAL GUINEA", "GQ");
        addToTable("ERITREA", "ER");
        addToTable("ESTONIA", "EE");
        addToTable("ETHIOPIA", "ET");
        addToTable("FALKLAND ISLANDS", "FK");
        addToTable("FAROE ISLANDS", "FO");
        addToTable("FIJI", "FJ");
        addToTable("FINLAND", "FI");
        addToTable("FRANCE", "FR");
        addToTable("FRENCH POLYNESIA", "PF");
        addToTable("FRENCH GUIANA", "GF");
        addToTable("GABON", "GA");
        addToTable("GAMBIA", "GM");
        addToTable("GEORGIA", "GE");
        addToTable("GERMANY", "DE");
        addToTable("GHANA", "GH");
        addToTable("GIBRALTAR", "GI");
        addToTable("UNITED KINGDOM", "GB");
        addToTable("UNITED KINGDOM", "EN");
        addToTable("UNITED KINGDOM", "UK");
        addToTable("GREECE", "GR");
        addToTable("GREENLAND", "GL");
        addToTable("GRENADA", "GD");
        addToTable("GUADELOUPE", "GP");
        addToTable("GUAM", "GU");
        addToTable("GUATEMALA", "GT");
        addToTable("GUINEA", "GN");
        addToTable("GUINEA BISSAU", "GW");
        addToTable("GUYANA", "GY");
        addToTable("HAITI", "HT");
        addToTable("HONDURAS", "HN");
        addToTable("HONG KONG", "HK");
        addToTable("HUNGARY", "HU");
        addToTable("ICELAND", "IS");
        addToTable("INDIA", "IN");
        addToTable("INDONESIA", "ID");
        addToTable("IRAN", "IR");
        addToTable("IRAQ", "IQ");
        addToTable("IRELAND", "IE");
        addToTable("ISRAEL", "IL");
        addToTable("ITALY", "IT");
        addToTable("JAMAICA", "JM");
        addToTable("JAPAN", "JP");
        addToTable("JORDAN", "JO");
        addToTable("KAZAKHSTAN", "KZ");
        addToTable("KENYA", "KE");
        addToTable("KIRIBATI", "KI");
        addToTable("KUWAIT", "KW");
        addToTable("KYRGHYZSTAN", "KG");
        addToTable("LAOS", "LA");
        addToTable("LATVIA", "LV");
        addToTable("LEBANON", "LB");
        addToTable("LESOTHO", "LS");
        addToTable("LIBERIA", "LR");
        addToTable("LIBYA", "LY");
        addToTable("LIECHTENSTEIN", "LI");
        addToTable("LITHUANIA", "LT");
        addToTable("LUXEMBOURG", "LU");
        addToTable("MACAU", "MO");
        addToTable("MACEDONIA", "MK");
        addToTable("MADAGASCAR", "MG");
        addToTable("MALAWI", "MW");
        addToTable("MALAYSIA", "MY");
        addToTable("MALDIVES", "MV");
        addToTable("MALI", "ML");
        addToTable("MALTA", "MT");
        addToTable("MARSHALL ISLANDS", "MH");
        addToTable("MARTINIQUE", "MQ");
        addToTable("MAURITANIA", "MR");
        addToTable("MAURITIUS", "MU");
        addToTable("MEXICO", "MX");
        addToTable("MICRONESIA FEDERATED STATES", "FM");
        addToTable("MOLDOVA", "MD");
        addToTable("MONACO", "MC");
        addToTable("MONGOLIAN PEOPLES REP", "MN");
        addToTable("MONTSERRAT", "MS");
        addToTable("MOROCCO", "MA");
        addToTable("MOZAMBIQUE", "MZ");
        addToTable("BURMA", "MM");
        addToTable("NAMIBIA", "NA");
        addToTable("NAURU", "NR");
        addToTable("NEPAL", "NP");
        addToTable("NETHERLANDS", "NL");
        addToTable("NETHERLANDS ANTILLES", "AN");
        addToTable("NEW CALEDONIA", "NC");
        addToTable("NEW ZEALAND", "NZ");
        addToTable("NICARAGUA", "NI");
        addToTable("NIGER", "NE");
        addToTable("NIGERIA", "NG");
        addToTable("NORTHERN MARIANA ISLANDS", "MP");
        addToTable("KOREA NORTH", "KP");
        addToTable("NORWAY", "NO");
        addToTable("OMAN", "OM");
        addToTable("PAKISTAN", "PK");
        addToTable("PALAU", "PW");
        addToTable("PANAMA", "PA");
        addToTable("PAPUA NEW GUINEA", "PG");
        addToTable("PARAGUAY", "PY");
        addToTable("PERU", "PE");
        addToTable("PHILIPPINES", "PH");
        addToTable("PITCAIRN ISLANDS", "PN");
        addToTable("POLAND", "PL");
        addToTable("PORTUGAL", "PT");
        addToTable("PUERTO RICO", "PR");
        addToTable("PALESTINIAN TERRITORY", "PS");
        addToTable("QATAR", "QA");
        addToTable("REUNION", "RE");
        addToTable("ROMANIA", "RO");
        addToTable("RUSSIA", "RU");
        addToTable("RWANDA", "RW");
        addToTable("SAMOA", "WS");
        addToTable("SAN MARINO", "SM");
        addToTable("SAO TOME PRINCIPE", "ST");
        addToTable("SAUDI ARABIA", "SA");
        addToTable("SENEGAL", "SN");
        addToTable("SERBIA", "RS");
        addToTable("MONTENEGRO", "ME");
        addToTable("SEYCHELLES", "SC");
        addToTable("SIERRA LEONE", "SL");
        addToTable("SINGAPORE", "SG");
        addToTable("SLOVAKIA", "SK");
        addToTable("SLOVENIA", "SI");
        addToTable("SOLOMON ISLANDS", "SB");
        addToTable("SOMALIA", "SO");
        addToTable("SOUTH AFRICA", "ZA");
        addToTable("KOREA SOUTH", "KR");
        addToTable("SPAIN", "ES");
        addToTable("SRI LANKA", "LK");
        addToTable("ST HELENA", "SH");
        addToTable("ST LUCIA", "LC");
        addToTable("ST VINCENT GRENADINES", "VC");
        addToTable("ST. KITTS & NEVIS", "KN");
        addToTable("ST PIERRE AND MIQUELON", "PM");
        addToTable("SUDAN", "SD");
        addToTable("SURINAME", "SR");
        addToTable("SWAZILAND", "SZ");
        addToTable("SWEDEN", "SE");
        addToTable("SWITZERLAND", "CH");
        addToTable("SYRIA", "SY");
        addToTable("TAIWAN", "TW");
        addToTable("TAJIKISTAN", "TJ");
        addToTable("TANZANIA", "TZ");
        addToTable("THAILAND", "TH");
        addToTable("TOGO", "TG");
        addToTable("TONGA", "TO");
        addToTable("TRINIDAD TOBAGO", "TT");
        addToTable("TUNISIA", "TN");
        addToTable("TURKEY", "TR");
        addToTable("TURKMENISTAN", "TM");
        addToTable("TURKS CAICOS ISLANDS", "TC");
        addToTable("TUVALU", "TV");
        addToTable("UGANDA", "UG");
        addToTable("UKRAINE", "UA");
        addToTable("UNITED ARAB EMIRATES", "AE");
      //  addToTable("WAKE ISLAND", "UM");
        addToTable("USA", "US");
        addToTable("URUGUAY", "UY");
        addToTable("YEMEN", "YE");
        addToTable("VIRGIN ISLANDS, U.S.", "VI");
        addToTable("US MINOR OUTLYING ISLANDS","UM");
        addToTable("UZBEKISTAN", "UZ");
        addToTable("VANUATU", "VU");
        addToTable("VATICAN CITY STATE", "VA");
        addToTable("VENEZUELA", "VE");
        addToTable("VIETNAM", "VN");
        addToTable("WALLIS FUTUNA ISLANDS", "WF");
        addToTable("ZAMBIA", "ZM");
        addToTable("ZIMBABWE RHODESIA", "ZW");
        addToTable("ZIMBABWE", "ZW");
        addToTable("IVORY COAST", "CI");
        addToTable("JERSEY", "JE");
        addToTable("GUERNSEY", "GG");
    }

    static void addToTable(String name, String abbrev) {

        nations.put(abbrev, name);
    }

    static public String getCountryForCode(String code) {

        return "" + nations.get(code);
    }
  


    static public boolean containsCode(String code) {
        return nations.containsKey(code);
    }

    static public Map<String, String> getNations()
    {
        return nations;
    }

    static public boolean containsName(String countryName) {
        if (countryName.equalsIgnoreCase("UNITED STATES")) countryName = "USA";
        if (countryName.equalsIgnoreCase("US")) countryName = "USA";
        if (countryName.equalsIgnoreCase("CHINA")) countryName = "CHINA PEOPLES REPUBLIC OF";
        if (countryName.equalsIgnoreCase("RUSSIAN FEDERATION")) countryName = "RUSSIA";
        if (countryName.equalsIgnoreCase("ST. LUCIA")) countryName = "ST LUCIA";
        if (countryName.equalsIgnoreCase("ST. KITTS AND NEVIS")) countryName = "ST. KITTS & NEVIS";
        if (countryName.equalsIgnoreCase("MACAO")) countryName = "MACAU";
        if (countryName.equalsIgnoreCase("ANTIGUA AND BARBUDA")) countryName = "ANTIGUA";
        if (countryName.equalsIgnoreCase("Virgin Islands, British")) countryName = "BRITISH VIRGIN ISLANDS";
        if (countryName.equalsIgnoreCase("Serbia and Montenegro")) countryName = "SERBIA";
        return nations.containsValue(countryName);
    }
}
