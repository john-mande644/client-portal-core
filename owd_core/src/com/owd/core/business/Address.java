package com.owd.core.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Address {
private final static Logger log =  LogManager.getLogger();

    public Address() {
         country="USA";
    }


    public Address(String cname,
                   String add1,
                   String add2,
                   String acity,
                   String astate,
                   String azip,
                   String acountry) {


        if (cname == null) cname = "";
        if (add1 == null) add1 = "";
        if (add2 == null) add2 = "";
        if (acity == null) acity = "";
        if (astate == null) astate = "";
        if (azip == null) azip = "";
        if (acountry == null) acountry = "";

        company_name = (cname != null ? cname.replaceAll("\'", "") : ".");

        if ("".equals(company_name.trim())) {
            company_name = ".";
        }
        address_one = (add1 != null ? add1.replaceAll("\'", "") : "");
        address_two = (add2 != null ? add2.replaceAll("\'", "") : "");
        city = (acity != null ? acity.replaceAll("\'", "") : "");
        state = (astate != null ? astate.replaceAll("\'", "") : "");
        zip = (azip != null ? azip.replaceAll("\'", "") : "");
        country = (acountry != null ? acountry.replaceAll("\'", "") : "");
        country = country.toUpperCase();
        if (country.equalsIgnoreCase("UNITED STATES")) country = "USA";
        if (country.equalsIgnoreCase("US")) country = "USA";
        if (country.equals("")) country = "USA";
    }

    public String company_name = ".";
    public String address_one = "";
    public String address_two = "";
    public String city = "";
    public String state = "";
    public String zip = "";
    public String country = "USA";
    public String verificationError = "";
    public String verificationNote = "";
    public boolean isCorrected = false;
    public boolean isResidential = true;
    String POpattern = "[Pp](\\.*?)\\s{0,2}[Oo]\\.*?\\s{0,2}[Bb][Oo][Xx]";

    public String toString() {
        return "Address::\nCompany Name : " + company_name
                + "\nLine 1 : " + address_one + "\nLine 2 : " + address_two
                + "\nCity : " + city + "\nState : " + state + "\nZip : " + zip + "\nCountry : " + country;


    }

    public void upperCase() {
        company_name = company_name.toUpperCase();
        address_one = address_one.toUpperCase();
        address_two = address_two.toUpperCase();
        city = city.toUpperCase();
        state = state.toUpperCase();
        zip = zip.toUpperCase();
    }

    public String toStorableString() {
        StringBuffer sb = new StringBuffer();
        sb.append(company_name);
        sb.append(" ~");
        sb.append(address_one);
        sb.append(" ~");
        sb.append(address_two);
        sb.append(" ~");
        sb.append(city);
        sb.append(" ~");
        sb.append(state);
        sb.append(" ~");
        sb.append(zip);
        sb.append(" ~");
        sb.append(country);

        return sb.toString();

    }

    public  void fixNulls() {

        if (company_name==null)
            company_name = "";
        if (address_one==null)
            address_one  = "";
        if (address_two==null)
            address_two  = "";
        if (city==null)
            city  = "";
        if (state==null)
            state = "";
        if (zip==null)
            zip  = "";
        if (country==null)
            country = "USA";

    }
    public static Address createFromStorableString(String stored) {
        StringTokenizer tokens = new StringTokenizer(stored, "~");
        Address address = new Address();

        if (tokens.hasMoreTokens())
            address.company_name = tokens.nextToken().trim();
        if (tokens.hasMoreTokens())
            address.address_one = tokens.nextToken().trim();
        if (tokens.hasMoreTokens())
            address.address_two = tokens.nextToken().trim();
        if (tokens.hasMoreTokens())
            address.city = tokens.nextToken().trim();
        if (tokens.hasMoreTokens())
            address.state = tokens.nextToken().trim();
        if (tokens.hasMoreTokens())
            address.zip = tokens.nextToken().trim();
        if (tokens.hasMoreTokens())
            address.country = tokens.nextToken().trim();

        address.country = address.country.toUpperCase();
        if (address.country.equalsIgnoreCase("UNITED STATES")) address.country = "USA";
        if (address.country.equalsIgnoreCase("US")) address.country = "USA";
        if (address.country.equals("")) address.country = "USA";

        return address;

    }


    //throws Exception if state/country combo invalid
    //currently only tests for US/Canada state/province values
    //and presumes state is presented as two-letter abbreviation
    //for those countries
    public boolean validateStateValue() {


        Vector canadaProvinceAbbrs = new Vector();
        canadaProvinceAbbrs.add("AB");
        canadaProvinceAbbrs.add("BC");
        canadaProvinceAbbrs.add("MB");
        canadaProvinceAbbrs.add("NB");
        canadaProvinceAbbrs.add("NF");
        canadaProvinceAbbrs.add("NT");
        canadaProvinceAbbrs.add("NS");
        canadaProvinceAbbrs.add("ON");
        canadaProvinceAbbrs.add("PE");
        canadaProvinceAbbrs.add("QC");
        canadaProvinceAbbrs.add("SK");
        canadaProvinceAbbrs.add("YT");


        if (country.equalsIgnoreCase("USA")) {
            if (!(canadaProvinceAbbrs.contains(state))) {
                return true;
            } else {
                return false;
            }
        } else if (country.equalsIgnoreCase("CANADA")) {


            if (canadaProvinceAbbrs.contains(state)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean isUS() {
        if (country.equalsIgnoreCase("USA")
                || country.equalsIgnoreCase("US") ||
                country.equalsIgnoreCase("UNITED STATES") ||
                country.equalsIgnoreCase("UNITED_STATES"))
            return true;
        else
            return false;

    }

    public boolean isCanada() {
        if (country.equalsIgnoreCase("CANADA"))
            return true;
        else
            return false;
    }

    public boolean isUSOrCanada() {
        if (isUS() || country.equalsIgnoreCase("CANADA"))
            return true;
        else
            return false;
    }

    public boolean isUSLower48() {
        if (!(isUS()) ||
                (state.equalsIgnoreCase("AK") || state.equalsIgnoreCase("ALASKA") ||
                        state.equalsIgnoreCase("HI") || state.equalsIgnoreCase("HAWAII")))
            return false;
        else
            return true;
    }

    public boolean isInternational() {
        if (isUS())
            return false;
        else
            return true;
    }

    public boolean isPONew() {
        if (!isUS())
            return false;

        Pattern p = Pattern.compile(POpattern);
        Matcher m = p.matcher(address_one.trim());
        if (m.find()) {

            return true;
        }
        m = p.matcher(address_two.trim());
        if (m.find()) {

            return true;
        }


        return false;
    }

    public boolean isPOAPONew() {
        if (!isUS())
            return false;

        if (state.equalsIgnoreCase("AE") || state.equalsIgnoreCase("AA") || state.equalsIgnoreCase("AP") || city.equalsIgnoreCase("APO") || city.equalsIgnoreCase("FPO") || city.equalsIgnoreCase("MPO")) {
            return true;
        }

        Pattern p = Pattern.compile(POpattern);
        Matcher m = p.matcher(address_one.trim());
        if (m.find()) {
            //log.debug("Setting Priority for PO box");
            return true;
        }
        m = p.matcher(address_two.trim());
        if (m.find()) {
            //log.debug("Setting Priority for PO box");
            return true;
        }


        return false;
    }

    public boolean isAPO() {

        if (!isUS())
            return false;

        if (state.equalsIgnoreCase("AE") || state.equalsIgnoreCase("AA") || state.equalsIgnoreCase("AP") || city.equalsIgnoreCase("APO") || city.equalsIgnoreCase("FPO") || city.equalsIgnoreCase("MPO")) {
            return true;
        }


        return false;

    }

    public boolean isPOAPO() {

        if (!isUS())
            return false;

        if (state.equalsIgnoreCase("AE") || state.equalsIgnoreCase("AA") || state.equalsIgnoreCase("AP") || city.equalsIgnoreCase("APO") || city.equalsIgnoreCase("FPO") || city.equalsIgnoreCase("MPO")) {
            return true;
        }

        String address = address_one.trim().toUpperCase();
        if (address.startsWith("BOX ") || address.startsWith("POBOX") || address.startsWith("PO BOX") || address.startsWith("P.O.BOX") || address.startsWith("P.O BOX") || address.startsWith("P.O. BOX") || address.startsWith("P O BOX")) {
            return true;
        }
        address = address_two.trim().toUpperCase();
        if ( address.startsWith("POBOX") || address.startsWith("PO BOX") || address.startsWith("P.O.BOX") || address.startsWith("P.O BOX") || address.startsWith("P.O. BOX") || address.startsWith("P O BOX")) {
            return true;
        }

        return false;

    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress_one() {
        return address_one;
    }

    public void setAddress_one(String address_one) {
        this.address_one = address_one;
    }

    public String getAddress_two() {
        return address_two;
    }

    public void setAddress_two(String address_two) {
        this.address_two = address_two;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVerificationError() {
        return verificationError;
    }

    public void setVerificationError(String verificationError) {
        this.verificationError = verificationError;
    }

    public boolean isCorrected() {
        return isCorrected;
    }

    public void setCorrected(boolean corrected) {
        isCorrected = corrected;
    }

    public boolean equals(Object o) {
        if (o instanceof Address) {
            Address a = (Address) o;

            if (!company_name.trim().equalsIgnoreCase(a.company_name.trim())) {
                return false;
            } else if (!address_one.trim().equalsIgnoreCase(a.address_one.trim())) {
                return false;
            } else if (!city.trim().equalsIgnoreCase(a.city.trim())) {
                return false;
            } else if (!state.trim().equalsIgnoreCase(a.state.trim())) {
                return false;
            } else if (!zip.trim().equalsIgnoreCase(a.zip.trim())) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    public static String getIANACountry(String country,String city,Session sess) throws Exception{
        System.out.println("getting countires For "+country);
        //fix funky country stuff
        if(country.equals("KYRGHYZSTAN")) country = "KYRGYZSTAN";
        if(country.equalsIgnoreCase("NETHERLANDS_ANTILLES")){
            if(city.equalsIgnoreCase("Curacao")){
                return "CW";
            }
        }

        String sql = " select country_code from countries where connectship = :name";
        try{

            Query q = sess.createSQLQuery(sql);
            q.setString("name",country.toUpperCase());
            List results = q.list();
            if (results.size()>0){

                country =  (String) results.get(0);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if (country.length()>2) throw new Exception("Unable to find 2 digit country code please contact IT");

        return country;

    }
}
