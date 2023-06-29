package com.owd.core.business;

import com.owd.core.business.client.ClientPolicy;
import com.owd.core.business.client.DefaultClientPolicy;
import com.owd.core.managers.ConnectionManager;
import com.owd.core.payment.PaymentProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Client {
private final static Logger log =  LogManager.getLogger();
    //IDs are zero if the package is new

    //Attributes
    public static final String kdbClientPKName = "client_id";
    public String client_id = "0";

    public static final String kdbClientCreatedBy = "created_by";
    public String created_by = "";

    public static final String kdbClientCreatedOn = "created_date";
    public String created_date = "";

    public static final String kdbClientModifiedBy = "modified_by";
    public String modified_by = "";

    public static final String kdbClientModifiedOn = "modified_date";
    public String modified_date = "";


    //Properties
    public static final String kdbClientShipperSymbol = "shipper_symbol";
    public String shipper_symbol = "";

    public static final String kdbClientCompanyName = "company_name";
    public String company_name = "";

    public static final String kdbClientAddressOne = "address_one";
    public String address_one = "";

    public static final String kdbClientAddressTwo = "address_two";
    public String address_two = "";

    public static final String kdbClientCity = "city";
    public String city = "";

    public static final String kdbClientState = "state";
    public String state = "";

    public static final String kdbClientZip = "zip";
    public String zip = "";

    public static final String kdbClientCountry = "country";
    public String country = "";

    public static final String kdbClientCountryRef = "country_ref_num";
    public String country_ref_num = "";


    public static final String kdbClientPhone = "primary_phone_num";
    public String primary_phone_num = "";

    public static final String kdbClientFax = "primary_fax_num";
    public String primary_fax_num = "";

    public static final String kdbClientURL = "url_string";
    public String url_string = "";

    public static final String kdbClientEmail = "primary_email_address";
    public String primary_email_address = "";

    public static final String kdbClientPrintPick = "ship_pick_reqd";
    public String ship_pick_reqd = "0";

    public static final String kdbClientPrintPack = "ship_pack_reqd";
    public String ship_pack_reqd = "1";

    public static final String kdbClientPrintInvoice = "ship_invoice_reqd";
    public String ship_invoice_reqd = "0";

    public static final String kdbClientPrintPrice = "ship_priceonslip_reqd";
    public String ship_priceonslip_reqd = "1";

    public static final String kdbClientShipID = "ship_id";
    public String ship_id = "";

    public static final String kdbClientShipPassword = "ship_id_password";
    public String ship_id_password = "";

    public static final String kdbClientLastInvoice = "last_invoice_num";
    public String last_invoice_num = "1";

    public static final String kdbClientActive = "is_active";
    public String is_active = "0";

//new values

    public static final String kdbClientName = "contact_name";
    public String contact_name = "";

    public static final String kdbClientPPClass = "pp_proc";
    public String pp_proc = "";

    public static final String kdbClientPPLogin = "pp_login";
    public String pp_login = "";

    public static final String kdbClientPPPass = "pp_pass";
    public String pp_pass = "";

    public static final String kdbClientDoEcheck= "do_echeck";
    public String  do_echeck ="0";

    public static final String kdbClientCheckLogin = "check_login";
    public String check_login ="";

    public static final String kdbClientCheckPass ="check_pass";
    public String check_pass = "";

    public static final String kdbClientCheckProc = "check_proc";
    public String check_proc="";


    //email stuff

    public static final String kdbOrderShipEmail = "ship_email";
    public String ship_email = "0";

    public static final String kdbOrderShipEmailFooter = "ship_email_ftr";
    public String ship_email_ftr = "";

    public static final String kdbOrderShipEmailFrom = "ship_email_from";
    public String ship_email_from = "";

    public static final String kdbOrderShipEmailCC = "ship_email_cc";
    public String ship_email_cc = "";

    public static final String kdbOrderShipEmailBCC = "ship_email_bcc";
    public String ship_email_bcc = "";

    //ship notify stuff

    public static final String kdbYahooNotify = "tell_yahoo";
    public String tell_yahoo = "0";

    public static final String kdbOrderTrustNotify = "tell_ordertrust";
    public String tell_ordertrust = "0";

    public static final String kdbYahooTrackPass = "yahoo_pass";
    public String yahoo_pass = "";

    public static final String kdbOrderTrustSourceCode = "otrust_source";
    public String otrust_source = "";

    //autoship backorders stuff

    public static final String kdbAutoShip = "is_backship";
    public String is_backship = "0";

    public static final String kdbAutoShipFullType = "original_ship_type";
    public String original_ship_type = "0";

    public static final String kdbAutoShipFullCarrier = "original_ship_carrier";
    public String original_ship_carrier = "";

    public static final String kdbAutoShipPartType = "partial_ship_type";
    public String partial_ship_type = "0";

    public static final String kdbAutoShipPartCarrier = "partial_ship_carrier";
    public String partial_ship_carrier = "";


    public static final String kdbFedExAcct = "fedex_acct";
    public String fedex_acct = "";

    public static final String kdbUPSAcct = "ups_acct";
    public String ups_acct = "";

    public static final String kdbDefaultCustomsDesc = "default_customs_desc";
    public String default_customs_desc = "";

    public static final String kdbRetAddr1 = "ret_addr_1";
    public String ret_addr_1 = "";


    public static final String kdbRetAddr2 = "ret_addr_2";
    public String ret_addr_2 = "";

    public static final String kdbUseDCForFirstclass = "usedc_firstclass";
    public int usedc_firstclass = 0;


    public static final String kdbPolicyClass = "policyClass";
    public String policyClass = "";

    public static final String kdbDefaultFacilityCode = "default_facility_code";
    public String defaultFacilityCode = "";

    private static final String kdbClientTable = "owd_client";
    private static final String kDeleteClientStatement = "DELETE FROM " + kdbClientTable + " where " + kdbClientPKName + " = ?";

    private boolean needsUpdate = false;
    private String validation_error = "";

  //  private java.util.TreeMap methodMap = new TreeMap();


    private static final String kUpdateClientStatement = "update " + kdbClientTable + " WITH (ROWLOCK) set "
            + kdbClientCreatedBy + "=?,"
            + kdbClientCreatedOn + "=?,"
            + kdbClientModifiedBy + "=?,"
            + kdbClientModifiedOn + "=?,"
            + kdbClientShipperSymbol + "=?,"
            + kdbClientCompanyName + "=?,"
            + kdbClientAddressOne + "=?,"
            + kdbClientAddressTwo + "=?,"
            + kdbClientCity + "=?,"
            + kdbClientState + "=?,"
            + kdbClientZip + "=?,"
            + kdbClientCountry + "=?,"
            + kdbClientCountryRef + "=?,"
            + kdbClientPhone + "=?,"
            + kdbClientFax + "=?,"
            + kdbClientURL + "=?,"
            + kdbClientEmail + "=?,"
            + kdbClientPrintPick + "=?,"
            + kdbClientPrintPack + "=?,"
            + kdbClientPrintInvoice + "=?,"
            + kdbClientPrintPrice + "=?,"
            + kdbClientShipID + "=?,"
            + kdbClientShipPassword + "=?,"
            + kdbClientLastInvoice + "=?,"
            + kdbClientActive + "=?,"
            + kdbClientName + "=?,"
            + kdbClientPPClass + "=?,"
            + kdbClientPPLogin + "=?,"
            + kdbClientPPPass + "=?,"
            + kdbOrderShipEmail + "=?,"
            + kdbOrderShipEmailFrom + "=?,"
            + kdbOrderShipEmailCC + "=?,"
            + kdbOrderShipEmailBCC + "=?,"
            + kdbOrderShipEmailFooter + "=?,"
            + kdbYahooNotify + "=?,"
            + kdbOrderTrustNotify + "=?,"
            + kdbYahooTrackPass + "=?,"
            + kdbOrderTrustSourceCode + "=?,"
            + kdbAutoShip + "=?,"
            + kdbAutoShipFullType + "=?,"
            + kdbAutoShipFullCarrier + "=?,"
            + kdbAutoShipPartType + "=?,"
            + kdbAutoShipPartCarrier + "=?,"
            + kdbFedExAcct + "=?,"
            + kdbUPSAcct + "=?,"
            + kdbDefaultCustomsDesc + "=?,"
            + kdbRetAddr1 + "=?,"
            + kdbRetAddr2 + "=?,"
            + kdbUseDCForFirstclass + "=? "
            + " WHERE " + kdbClientPKName + " = ? ";

    private static final String kInsertClientStatement = "insert into " + kdbClientTable + "("
            + kdbClientCreatedBy + ","
            + kdbClientCreatedOn + ","
            + kdbClientModifiedBy + ","
            + kdbClientModifiedOn + ","
            + kdbClientShipperSymbol + ","
            + kdbClientCompanyName + ","
            + kdbClientAddressOne + ","
            + kdbClientAddressTwo + ","
            + kdbClientCity + ","
            + kdbClientState + ","
            + kdbClientZip + ","
            + kdbClientCountry + ","
            + kdbClientCountryRef + ","
            + kdbClientPhone + ","
            + kdbClientFax + ","
            + kdbClientURL + ","
            + kdbClientEmail + ","
            + kdbClientPrintPick + ","
            + kdbClientPrintPack + ","
            + kdbClientPrintInvoice + ","
            + kdbClientPrintPrice + ","
            + kdbClientShipID + ","
            + kdbClientShipPassword + ","
            + kdbClientLastInvoice + ","
            + kdbClientActive + ","
            + kdbClientName + ","
            + kdbClientPPClass + ","
            + kdbClientPPLogin + ","
            + kdbClientPPPass + ","
            + kdbOrderShipEmail + ","
            + kdbOrderShipEmailFrom + ","
            + kdbOrderShipEmailCC + ","
            + kdbOrderShipEmailBCC + ","
            + kdbOrderShipEmailFooter + ","
            + kdbYahooNotify + ","
            + kdbOrderTrustNotify + ","
            + kdbYahooTrackPass + ","
            + kdbOrderTrustSourceCode + ","
            + kdbAutoShip + ","
            + kdbAutoShipFullType + ","
            + kdbAutoShipFullCarrier + ","
            + kdbAutoShipPartType + ","
            + kdbAutoShipPartCarrier + ","
            + kdbFedExAcct + ","
            + kdbUPSAcct + ","
            + kdbDefaultCustomsDesc + ","
            + kdbRetAddr1 + ","
            + kdbRetAddr2 + ","
            + kdbUseDCForFirstclass + ") VALUES (?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?,?,?,?,?,?)";

    public Client() {
    }

    public Client(String aclient_id,
                  String acreated_by,
                  String acreated_date,
                  String amodified_by,
                  String amodified_date,
                  String ashipper_symbol,
                  String acompany_name,
                  String aaddress_one,
                  String aaddress_two,
                  String acity,
                  String astate,
                  String azip,
                  String acountry,
                  String acountry_ref_num,
                  String aprimary_phone_num,
                  String aprimary_fax_num,
                  String aurl_string,
                  String aprimary_email_address,
                  String aship_pick_reqd,
                  String aship_pack_reqd,
                  String aship_invoice_reqd,
                  String aship_priceonslip_reqd,
                  String aship_id,
                  String aship_id_password,
                  String alast_invoice_num,
                  String ais_active,
                  String acontact_name,
                  String app_proc,
                  String app_login,
                  String app_pass,
                  String aship_email,
                  String aship_email_ftr,
                  String aship_email_from,
                  String aship_email_cc,
                  String aship_email_bcc,
                  String atell_yahoo,
                  String atell_ordertrust,
                  String ayahoo_pass,
                  String aotrust_source,
                  String ais_backship,
                  String aoriginal_ship_type,
                  String aoriginal_ship_carrier,
                  String apartial_ship_type,
                  String apartial_ship_carrier,
                  String afedex_acct,
                  String aups_acct) {

        this(aclient_id,
                acreated_by,
                acreated_date,
                amodified_by,
                amodified_date,
                ashipper_symbol,
                acompany_name,
                aaddress_one,
                aaddress_two,
                acity,
                astate,
                azip,
                acountry,
                acountry_ref_num,
                aprimary_phone_num,
                aprimary_fax_num,
                aurl_string,
                aprimary_email_address,
                aship_pick_reqd,
                aship_pack_reqd,
                aship_invoice_reqd,
                aship_priceonslip_reqd,
                aship_id,
                aship_id_password,
                alast_invoice_num,
                ais_active,
                acontact_name,
                app_proc,
                app_login,
                app_pass,
                aship_email,
                aship_email_ftr,
                aship_email_from,
                aship_email_cc,
                aship_email_bcc,
                atell_yahoo,
                atell_ordertrust,
                ayahoo_pass,
                aotrust_source,
                ais_backship,
                aoriginal_ship_type,
                aoriginal_ship_carrier,
                apartial_ship_type,
                apartial_ship_carrier,
                afedex_acct,
                aups_acct, "", "", "");

    }

    public Client(String aclient_id,
                  String acreated_by,
                  String acreated_date,
                  String amodified_by,
                  String amodified_date,
                  String ashipper_symbol,
                  String acompany_name,
                  String aaddress_one,
                  String aaddress_two,
                  String acity,
                  String astate,
                  String azip,
                  String acountry,
                  String acountry_ref_num,
                  String aprimary_phone_num,
                  String aprimary_fax_num,
                  String aurl_string,
                  String aprimary_email_address,
                  String aship_pick_reqd,
                  String aship_pack_reqd,
                  String aship_invoice_reqd,
                  String aship_priceonslip_reqd,
                  String aship_id,
                  String aship_id_password,
                  String alast_invoice_num,
                  String ais_active,
                  String acontact_name,
                  String app_proc,
                  String app_login,
                  String app_pass,
                  String aship_email,
                  String aship_email_ftr,
                  String aship_email_from,
                  String aship_email_cc,
                  String aship_email_bcc,
                  String atell_yahoo,
                  String atell_ordertrust,
                  String ayahoo_pass,
                  String aotrust_source,
                  String ais_backship,
                  String aoriginal_ship_type,
                  String aoriginal_ship_carrier,
                  String apartial_ship_type,
                  String apartial_ship_carrier,
                  String afedex_acct,
                  String aups_acct,
                  String adefault_customs_desc,
                  String aret_addr_1,
                  String aret_addr_2) {
        client_id = aclient_id;
        created_by = acreated_by;
        created_date = acreated_date;
        modified_by = amodified_by;
        modified_date = amodified_date;
        shipper_symbol = ashipper_symbol;
        company_name = acompany_name;
        address_one = aaddress_one;
        address_two = aaddress_two;
        ret_addr_1 = aret_addr_1;
        ret_addr_2 = aret_addr_2;

        city = acity;
        state = astate;
        zip = azip;
        default_customs_desc = adefault_customs_desc;
        country = acountry;
        country_ref_num = acountry_ref_num;
        primary_phone_num = aprimary_phone_num;
        primary_fax_num = aprimary_fax_num;
        url_string = aurl_string;
        primary_email_address = aprimary_email_address;
        ship_pick_reqd = aship_pick_reqd;
        ship_pack_reqd = aship_pack_reqd;
        ship_invoice_reqd = aship_invoice_reqd;
        ship_priceonslip_reqd = aship_priceonslip_reqd;
        ship_id = aship_id;
        ship_id_password = aship_id_password;
        last_invoice_num = alast_invoice_num;
        is_active = ais_active;
        contact_name = acontact_name;
        pp_proc = app_proc;
        pp_login = app_login;
        pp_pass = app_pass;
        ship_email = aship_email;
        ship_email_ftr = aship_email_ftr;
        ship_email_from = aship_email_from;
        ship_email_cc = aship_email_cc;
        ship_email_bcc = aship_email_bcc;
        tell_yahoo = atell_yahoo;
        tell_ordertrust = atell_ordertrust;
        yahoo_pass = ayahoo_pass;
        otrust_source = aotrust_source;
        is_backship = ais_backship;
        original_ship_type = aoriginal_ship_type;
        original_ship_carrier = aoriginal_ship_carrier;
        partial_ship_type = apartial_ship_type;
        partial_ship_carrier = apartial_ship_carrier;

        fedex_acct = afedex_acct;
        ups_acct = aups_acct;

    }


    public Client(String aclient_id,
                  String acreated_by,
                  String acreated_date,
                  String amodified_by,
                  String amodified_date,
                  String ashipper_symbol,
                  String acompany_name,
                  String aaddress_one,
                  String aaddress_two,
                  String acity,
                  String astate,
                  String azip,
                  String acountry,
                  String acountry_ref_num,
                  String aprimary_phone_num,
                  String aprimary_fax_num,
                  String aurl_string,
                  String aprimary_email_address,
                  String aship_pick_reqd,
                  String aship_pack_reqd,
                  String aship_invoice_reqd,
                  String aship_priceonslip_reqd,
                  String aship_id,
                  String aship_id_password,
                  String alast_invoice_num,
                  String ais_active,
                  String acontact_name,
                  String app_proc,
                  String app_login,
                  String app_pass,
                  String aship_email,
                  String aship_email_ftr,
                  String aship_email_from,
                  String aship_email_cc,
                  String aship_email_bcc,
                  String atell_yahoo,
                  String atell_ordertrust,
                  String ayahoo_pass,
                  String aotrust_source,
                  String ais_backship,
                  String aoriginal_ship_type,
                  String aoriginal_ship_carrier,
                  String apartial_ship_type,
                  String apartial_ship_carrier,
                  String afedex_acct,
                  String aups_acct,
                  String adefault_customs_desc,
                  String aret_addr_1,
                  String aret_addr_2,
                  String ausedc_firstclass,
                  String ado_echeck,
                  String acheck_login,
                  String acheck_pass,
                  String acheck_proc) {
        client_id = aclient_id;
        created_by = acreated_by;
        created_date = acreated_date;
        modified_by = amodified_by;
        modified_date = amodified_date;
        shipper_symbol = ashipper_symbol;
        company_name = acompany_name;
        address_one = aaddress_one;
        address_two = aaddress_two;
        ret_addr_1 = aret_addr_1;
        ret_addr_2 = aret_addr_2;

        city = acity;
        state = astate;
        zip = azip;
        default_customs_desc = adefault_customs_desc;
        country = acountry;
        country_ref_num = acountry_ref_num;
        primary_phone_num = aprimary_phone_num;
        primary_fax_num = aprimary_fax_num;
        url_string = aurl_string;
        primary_email_address = aprimary_email_address;
        ship_pick_reqd = aship_pick_reqd;
        ship_pack_reqd = aship_pack_reqd;
        ship_invoice_reqd = aship_invoice_reqd;
        ship_priceonslip_reqd = aship_priceonslip_reqd;
        ship_id = aship_id;
        ship_id_password = aship_id_password;
        last_invoice_num = alast_invoice_num;
        is_active = ais_active;
        contact_name = acontact_name;
        pp_proc = app_proc;
        pp_login = app_login;
        pp_pass = app_pass;
        ship_email = aship_email;
        ship_email_ftr = aship_email_ftr;
        ship_email_from = aship_email_from;
        ship_email_cc = aship_email_cc;
        ship_email_bcc = aship_email_bcc;
        tell_yahoo = atell_yahoo;
        tell_ordertrust = atell_ordertrust;
        yahoo_pass = ayahoo_pass;
        otrust_source = aotrust_source;
        is_backship = ais_backship;
        original_ship_type = aoriginal_ship_type;
        original_ship_carrier = aoriginal_ship_carrier;
        partial_ship_type = apartial_ship_type;
        partial_ship_carrier = apartial_ship_carrier;

        fedex_acct = afedex_acct;
        ups_acct = aups_acct;
        usedc_firstclass = (new Integer(ausedc_firstclass)).intValue();
       do_echeck = ado_echeck;
        check_login = acheck_login;
        check_pass =  acheck_pass;
         check_proc = acheck_proc;
    }


    public static String getClientIDForUser(String user) throws Exception {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        if (user != null && !("".equals(user))) {
            String client_id = null;
            String sqlStmt = "select client_fkey from owd_users " +
                    "where login = ?";
            try {
                cxn = ConnectionManager.getConnection();
                stmt = cxn.prepareStatement(sqlStmt);
                stmt.setString(1, user);
                boolean hasResult = stmt.execute();
                if (hasResult) {
                    rs = stmt.getResultSet();
                    while (rs.next()) {
                        client_id = rs.getString(1);
                    }
                } else
                    throw new Exception("Access Denied: user doesn't exist.\n");
            } catch (Exception ex) {

                throw ex;
            } finally {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                try {
                    cxn.close();
                } catch (Exception e) {
                }

            }

            return client_id;
        } else
            return null;
    }

      public static boolean isChargeOnShip(int clientID) throws Exception {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
            int shipCharge = 0;

        if (clientID != 0) {
            String sqlStmt = "select charge_on_ship from owd_client (NOLOCK)" +
                    "where client_id = ?";
            try {
                cxn = ConnectionManager.getConnection();
                stmt = cxn.prepareStatement(sqlStmt);
                stmt.setInt(1, clientID);
                boolean hasResult = stmt.execute();
                if (hasResult) {
                    rs = stmt.getResultSet();
                    while (rs.next()) {
                        shipCharge = rs.getInt(1);
                    }
                } else
                    throw new Exception("Access Denied: client doesn't exist.\n");
            } catch (Exception ex) {

                throw ex;
            } finally {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                try {
                    cxn.close();
                } catch (Exception e) {
                }

            }


        }
            return (shipCharge==1);
    }

    public static int getClientIDForOrder(int orderID) throws Exception {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
            int order_id = 0;
        if (orderID != 0) {
            String sqlStmt = "select client_fkey from owd_order " +
                    "where order_id = ?";
            try {
                cxn = ConnectionManager.getConnection();
                stmt = cxn.prepareStatement(sqlStmt);
                stmt.setInt(1, orderID);
                boolean hasResult = stmt.execute();
                if (hasResult) {
                    rs = stmt.getResultSet();
                    while (rs.next()) {
                        order_id = rs.getInt(1);
                    }
                } else
                    throw new Exception("Access Denied: order doesn't exist.\n");
            } catch (Exception ex) {

                throw ex;
            } finally {
                try {
                    rs.close();
                } catch (Exception e) {
                }
                try {
                    stmt.close();
                } catch (Exception e) {
                }
                try {
                    cxn.close();
                } catch (Exception e) {
                }

            }


        }
            return order_id;
    }


    public static Client getClientForUser(String user) throws Exception {
        String client_id = "";
        if (user != null && !("".equals(user))) {
            try {
                client_id = getClientIDForUser(user);
            } catch (Exception e) {
                throw e;
            }
            Client the_client = null;
            if ("0".equals(client_id)) {
                the_client = new Client();
                the_client.client_id = client_id;
            } else {
                try {
                    the_client = getClientForID(client_id);
                    if (the_client == null)
                        throw new Exception("client id " + client_id + "not found.\n");
                } catch (Exception e) {
                    throw e;
                }
            }
            return the_client;
        } else
            return null;
    }

    public static ArrayList getActiveClientList(String user) throws Exception {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList clients = new ArrayList();
        try {
            String client_id = getClientIDForUser(user);

            if ("0".equals(client_id)) {
                client_id = "%";
            }
            if (client_id != null && client_id.length() > 0) {
                cxn = ConnectionManager.getConnection();
                stmt = cxn.prepareStatement("select * from owd_client where is_active=1 order by company_name asc");
                boolean hasResult = stmt.execute();
                if (hasResult) {
                    rs = stmt.getResultSet();
                    while (rs.next()) {
                        ArrayList client = new ArrayList();
                        int column_count = rs.getMetaData().getColumnCount();
                        for (int i = 1; i <= column_count; i++) {
                            client.add(rs.getString(i));
                        }
                        clients.add(client);
                    }
                } else {
                    throw new Exception("No records in the owd_client table\n");
                }

            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }

        }

        return clients;
    }
    public static ArrayList getClientList(String user) throws Exception {
        Connection cxn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList clients = new ArrayList();
        try {
            String client_id = getClientIDForUser(user);

            if ("0".equals(client_id)) {
                client_id = "%";
            }
            if (client_id != null && client_id.length() > 0) {
                cxn = ConnectionManager.getConnection();
                stmt = cxn.prepareStatement("select * from owd_client order by company_name asc");
                boolean hasResult = stmt.execute();
                if (hasResult) {
                    rs = stmt.getResultSet();
                    while (rs.next()) {
                        ArrayList client = new ArrayList();
                        int column_count = rs.getMetaData().getColumnCount();
                        for (int i = 1; i <= column_count; i++) {
                            client.add(rs.getString(i));
                        }
                        clients.add(client);
                    }
                } else {
                    throw new Exception("No records in the owd_client table\n");
                }

            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                rs.close();
            } catch (Exception e) {
            }
            try {
                stmt.close();
            } catch (Exception e) {
            }
            try {
                cxn.close();
            } catch (Exception e) {
            }

        }

        return clients;
    }


    public static Client getClientForID(Connection cxn, String clientID) throws Exception {
        return dbload(cxn, clientID);
    }

    public static Client getClientForID(String clientID) throws Exception {
        Connection cxn = null;
        Client client = null;

        try {
            cxn = ConnectionManager.getConnection();
            client = getClientForID(cxn, clientID);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }

        return client;
    }

    public boolean isModified() {
        return needsUpdate;
    }

    public boolean isNew() {
        if ("0".equals(client_id))
            return true;

        return false;
    }

    public boolean validated() {


        return true;
    }

    private ClientPolicy clientPolicy = null;

    public ClientPolicy getClientPolicy() {
        if (clientPolicy == null) {
            //log.debug("Attempting policy class load for:" + policyClass);
            if (policyClass == null) policyClass = "";

            if (policyClass.length() < 1) {
                clientPolicy = new DefaultClientPolicy(this);

            } else {
                try {
                    clientPolicy = (ClientPolicy) Class.forName(policyClass).newInstance();
                    clientPolicy.setClient(this);
                } catch (Exception ex) {
                    //log.debug("Unable to load client policy class for:" + policyClass);
                    ex.printStackTrace();
                    clientPolicy = new DefaultClientPolicy(this);
                }
            }
        }

        return clientPolicy;
    }

    public void setAddress(Address add) {

    }

    public void setContact(Contact cont) {

    }


    public String[] getValidationErrors() {
        return null;
    }

/*
	public ElementNode toXml(XmlDocument doc) throws IOException
	{

		//return Line Item node
		ElementNode root = (ElementNode) doc.createElement(OrderXMLDoc.kClientTag);

		//attributes
		root.setAttribute(OrderXMLDoc.kClientIDTag, ""+order_track_id);
		root.setAttribute(OrderXMLDoc.kClientOrderIDTag, ""+order_fkey);
		root.setAttribute(OrderXMLDoc.kClientCreatedOnTag,""+createdOn);
		root.setAttribute(OrderXMLDoc.kClientCreatedByTag,""+createdBy);
		root.setAttribute(OrderXMLDoc.kClientModifiedByTag,""+modifiedBy);
		root.setAttribute(OrderXMLDoc.kClientModifiedOnTag,""+modifiedOn);

		//elements
		root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kClientIndexTag,""+line_index));
		root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kClientTrackingNoTag,""+tracking_no));
		root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kClientPoundsWeighTagt,""+weight));
		root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kClientRatedCostTag,""+total_billed));
		root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kClientCostGoodsTag,""+cost_of_goods));
		root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kClientShipDateTag,""+ship_date));
		root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kClientMSNTag,""+msn));
		root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kClientVoidTag,""+is_void));
		root.appendChild(XMLUtils.getXMLTextNode(doc,OrderXMLDoc.kClientReportedTag,""+reported));

		return root;

	}
	*/

    public static PaymentProcessor getPaymentProcessor(Connection cxn, String cID) {
        Statement stmt = null;
        ResultSet rs = null;
        PaymentProcessor pp = null;

        try {
            stmt = cxn.createStatement();
            String isql = "select pp_proc, pp_login, pp_pass from owd_client where client_id = " + cID;


            boolean hasResults = stmt.execute(isql);


            if (hasResults) {
                rs = stmt.getResultSet();

                if (rs.next()) {
                    pp = (PaymentProcessor) Class.forName(rs.getString(1)).newInstance();
                    pp.setLogin(rs.getString(2), rs.getString(3));

                } else {
                    throw new Exception("Client id " + cID + " not found!");
                }

                rs.close();

            }

        } catch (Exception ex) {
            return null;
        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }
            try {
                stmt.close();
            } catch (Exception exc) {
            }

        }

        return pp;


    }

    public void dbsave(Connection cxn) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        created_by = com.owd.core.OWDUtilities.getCurrentUserName();
        created_date = com.owd.core.OWDUtilities.getSQLDateTimeForToday();
        modified_date = created_date;
        modified_by = created_by;

        try {
            stmt = cxn.prepareStatement(kInsertClientStatement);

            stmt.setString(1, created_by);
            stmt.setString(2, created_date);
            stmt.setString(3, modified_by);
            stmt.setString(4, modified_date);
            stmt.setString(5, shipper_symbol);
            stmt.setString(6, company_name);
            stmt.setString(7, address_one);
            stmt.setString(8, address_two);
            stmt.setString(9, city);
            stmt.setString(10, state);
            stmt.setString(11, zip);
            stmt.setString(12, country);
            stmt.setString(13, country_ref_num);
            stmt.setString(14, primary_phone_num);
            stmt.setString(15, primary_fax_num);
            stmt.setString(16, url_string);
            stmt.setString(17, primary_email_address);
            stmt.setInt(18, new Integer(ship_pick_reqd).intValue());
            stmt.setInt(19, new Integer(ship_pack_reqd).intValue());
            stmt.setInt(20, new Integer(ship_invoice_reqd).intValue());
            stmt.setInt(21, new Integer(ship_priceonslip_reqd).intValue());
            stmt.setString(22, ship_id);
            stmt.setString(23, ship_id_password);
            stmt.setInt(24, new Integer(last_invoice_num).intValue());
            stmt.setInt(25, new Integer(is_active).intValue());
            stmt.setString(26, contact_name);
            stmt.setString(27, pp_proc);
            stmt.setString(28, pp_login);
            stmt.setString(29, pp_pass);
            stmt.setInt(30, new Integer(ship_email).intValue());
            stmt.setString(31, ship_email_from);
            stmt.setString(32, ship_email_cc);
            stmt.setString(33, ship_email_bcc);
            stmt.setString(34, ship_email_ftr);
            stmt.setInt(35, new Integer(tell_yahoo).intValue());
            stmt.setInt(36, new Integer(tell_ordertrust).intValue());
            stmt.setString(37, yahoo_pass);
            stmt.setString(38, otrust_source);
            stmt.setInt(39, new Integer(is_backship).intValue());
            stmt.setInt(40, new Integer(original_ship_type).intValue());
            stmt.setString(41, original_ship_carrier);
            stmt.setInt(42, new Integer(partial_ship_type).intValue());
            stmt.setString(43, partial_ship_carrier);
            stmt.setString(44, fedex_acct);
            stmt.setString(45, ups_acct);
            stmt.setString(46, default_customs_desc);
            stmt.setString(47, ret_addr_1);
            stmt.setString(48, ret_addr_2);
            stmt.setInt(49, usedc_firstclass);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated < 1)
                throw new Exception("Could not save client!");
            stmt.close();

            stmt = cxn.prepareStatement("select @@IDENTITY");

            stmt.executeQuery();
            rs = stmt.getResultSet();
            if (rs.next()) {
                client_id = rs.getString(1);
            }
            rs.close();
            stmt.close();

          //  saveMethods(cxn, methodMap);

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }
            try {
                stmt.close();
            } catch (Exception exc) {
            }

        }

    }


    public void dbdelete(Connection cxn) throws Exception {

        if (isNew()) {
            //do nothing
        } else {
            PreparedStatement stmt = null;
            try {
                stmt = cxn.prepareStatement(kDeleteClientStatement);
                stmt.setString(1, client_id);

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated < 1) throw new Exception("Cannot delete client ID " + client_id);

                stmt.close();

            } catch (Exception ex) {
                throw ex;
            } finally {
                try {
                    stmt.close();
                } catch (Exception exc) {
                }
            }

        }
    }

    public static final String kSaveClientMethodStatement = "insert into owd_client_method " +
            "(client_fkey,method_code,discount_pct) VALUES (?,?,?)";
    public static final String kDeleteClientMethodsStatement = "delete from owd_client_method WITH (ROWLOCK) " +
            "where client_fkey = ?";

    public static final String kSelectClientMethodsStatement = "select method_code,discount_pct from owd_client_method (NOLOCK) " +
            "where client_fkey = ?";

/*
    protected void saveMethods(Connection cxn, java.util.TreeMap map) throws Exception {
        if (isNew()) {
            throw new Exception("Can't save methods before client creation.");
        } else {
            PreparedStatement stmt = null;
            try {
                stmt = cxn.prepareStatement(kDeleteClientMethodsStatement);
                stmt.setString(1, client_id);
                int rowsUpdated = stmt.executeUpdate();
                stmt.close();

                stmt = cxn.prepareStatement(kSaveClientMethodStatement);

                Iterator iter = new ArrayList(map.keySet()).iterator();
                while (iter.hasNext()) {
                    String key = (String) iter.next();
                    stmt.setString(1, client_id);
                    stmt.setString(2, key);
                    stmt.setFloat(3, ((Float) map.get(key)).floatValue());
                    rowsUpdated = stmt.executeUpdate();
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                try {
                    stmt.close();
                } catch (Exception exc) {
                }
            }

        }
    }*/
/*

    protected java.util.TreeMap getMethodMap(Connection cxn) throws Exception {

        java.util.TreeMap map = new TreeMap();

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = cxn.prepareStatement(kSelectClientMethodsStatement);
            stmt.setString(1, client_id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                map.put(rs.getString(1), new Float(rs.getFloat(2)));
            }
            rs.close();

            stmt.close();


        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }
            try {
                stmt.close();
            } catch (Exception exc) {
            }


        }
        return map;
    }
*/

/*
    public void setMethodMap(java.util.TreeMap methods) {
        if (methods != null)
            methodMap = methods;
    }

    public java.util.List getMethods() {
        return new ArrayList(methodMap.keySet());
    }

    public java.util.TreeMap getMethodMap() {
        return methodMap;
    }

    public java.util.List getMethodDiscounts() {
        return new ArrayList(methodMap.values());
    }*/

    public void dbupdate(Connection cxn) throws Exception {

        if (isNew()) {
            dbsave(cxn);
        } else {
            PreparedStatement stmt = null;
            modified_by = com.owd.core.OWDUtilities.getCurrentUserName();
            modified_date = com.owd.core.OWDUtilities.getSQLDateTimeForToday();
            try {
                stmt = cxn.prepareStatement(kUpdateClientStatement);


                stmt.setString(1, created_by);
                stmt.setString(2, created_date);
                stmt.setString(3, modified_by);
                stmt.setString(4, modified_date);
                stmt.setString(5, shipper_symbol);
                stmt.setString(6, company_name);
                stmt.setString(7, address_one);
                stmt.setString(8, address_two);
                stmt.setString(9, city);
                stmt.setString(10, state);
                stmt.setString(11, zip);
                stmt.setString(12, country);
                stmt.setString(13, country_ref_num);
                stmt.setString(14, primary_phone_num);
                stmt.setString(15, primary_fax_num);
                stmt.setString(16, url_string);
                stmt.setString(17, primary_email_address);
                stmt.setInt(18, new Integer(ship_pick_reqd).intValue());
                stmt.setInt(19, new Integer(ship_pack_reqd).intValue());
                stmt.setInt(20, new Integer(ship_invoice_reqd).intValue());
                stmt.setInt(21, new Integer(ship_priceonslip_reqd).intValue());
                stmt.setString(22, ship_id);
                stmt.setString(23, ship_id_password);
                stmt.setInt(24, new Integer(last_invoice_num).intValue());
                stmt.setInt(25, new Integer(is_active).intValue());
                stmt.setString(26, contact_name);
                stmt.setString(27, pp_proc);
                stmt.setString(28, pp_login);
                stmt.setString(29, pp_pass);
                stmt.setInt(30, new Integer(ship_email).intValue());
                stmt.setString(31, ship_email_from);
                stmt.setString(32, ship_email_cc);
                stmt.setString(33, ship_email_bcc);
                stmt.setString(34, ship_email_ftr);
                stmt.setInt(35, new Integer(tell_yahoo).intValue());
                stmt.setInt(36, new Integer(tell_ordertrust).intValue());
                stmt.setString(37, yahoo_pass);
                stmt.setString(38, otrust_source);
                stmt.setInt(39, new Integer(is_backship).intValue());
                stmt.setInt(40, new Integer(original_ship_type).intValue());
                stmt.setString(41, original_ship_carrier);
                stmt.setInt(42, new Integer(partial_ship_type).intValue());
                stmt.setString(43, partial_ship_carrier);
                stmt.setString(44, fedex_acct);
                stmt.setString(45, ups_acct);
                stmt.setString(46, default_customs_desc);
                stmt.setString(47, ret_addr_1);
                stmt.setString(48, ret_addr_2);
                stmt.setInt(49, usedc_firstclass);
                stmt.setInt(50, new Integer(client_id).intValue());

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated < 1)
                    throw new Exception("Could not update client " + client_id);

           //     saveMethods(cxn, methodMap);

            } catch (Exception ex) {
                throw ex;
            } finally {
                try {
                    stmt.close();
                } catch (Exception exc) {
                }

            }

        }

    }


    private static Client dbload(Connection cxn, String id) throws Exception {

        Statement stmt = null;
        ResultSet rs = null;
        Client pack = null;

        try {
            stmt = cxn.createStatement();
            String isql = "select * from " + kdbClientTable + " WITH (NOLOCK) where " + kdbClientPKName + " = " + id;

            rs = stmt.executeQuery(isql);

            if (rs != null) {

                if (rs.next()) {
                    pack = new Client(rs.getString(kdbClientPKName),
                            getNullFixString(rs, kdbClientCreatedBy, ""),
                            getNullFixString(rs, kdbClientCreatedOn, ""),
                            getNullFixString(rs, kdbClientModifiedBy, ""),
                            getNullFixString(rs, kdbClientModifiedOn, ""),
                            getNullFixString(rs, kdbClientShipperSymbol, ""),
                            getNullFixString(rs, kdbClientCompanyName, ""),
                            getNullFixString(rs, kdbClientAddressOne, ""),
                            getNullFixString(rs, kdbClientAddressTwo, ""),
                            getNullFixString(rs, kdbClientCity, ""),
                            getNullFixString(rs, kdbClientState, ""),
                            getNullFixString(rs, kdbClientZip, ""),
                            getNullFixString(rs, kdbClientCountry, ""),
                            getNullFixString(rs, kdbClientCountryRef, ""),
                            getNullFixString(rs, kdbClientPhone, ""),
                            getNullFixString(rs, kdbClientFax, ""),
                            getNullFixString(rs, kdbClientURL, ""),
                            getNullFixString(rs, kdbClientEmail, ""),
                            "" + rs.getInt(kdbClientPrintPick),
                            "" + rs.getInt(kdbClientPrintPack),
                            "" + rs.getInt(kdbClientPrintInvoice),
                            "" + rs.getInt(kdbClientPrintPrice),
                            getNullFixString(rs, kdbClientShipID, ""),
                            getNullFixString(rs, kdbClientShipPassword, ""),
                            rs.getString(kdbClientLastInvoice),
                            "" + rs.getInt(kdbClientActive),
                            getNullFixString(rs, kdbClientName, ""),
                            getNullFixString(rs, kdbClientPPClass, ""),
                            getNullFixString(rs, kdbClientPPLogin, ""),
                            getNullFixString(rs, kdbClientPPPass, ""),
                            rs.getString(kdbOrderShipEmail),
                            getNullFixString(rs, kdbOrderShipEmailFooter, ""),
                            getNullFixString(rs, kdbOrderShipEmailFrom, ""),
                            getNullFixString(rs, kdbOrderShipEmailCC, ""),
                            getNullFixString(rs, kdbOrderShipEmailBCC, ""),
                            rs.getString(kdbYahooNotify),
                            rs.getString(kdbOrderTrustNotify),
                            getNullFixString(rs, kdbYahooTrackPass, ""),
                            getNullFixString(rs, kdbOrderTrustSourceCode, ""),
                            rs.getString(kdbAutoShip),
                            rs.getString(kdbAutoShipFullType),
                            getNullFixString(rs, kdbAutoShipFullCarrier, ""),
                            rs.getString(kdbAutoShipPartType),
                            getNullFixString(rs, kdbAutoShipPartCarrier, ""),
                            getNullFixString(rs, kdbFedExAcct, ""),
                            getNullFixString(rs, kdbUPSAcct, ""),
                            getNullFixString(rs, kdbDefaultCustomsDesc, ""),
                            getNullFixString(rs, kdbRetAddr1, ""),
                            getNullFixString(rs, kdbRetAddr2, ""),
                            getNullFixString(rs, kdbUseDCForFirstclass, "0"),
                            getNullFixString(rs, kdbClientDoEcheck,"0"),
                            getNullFixString(rs, kdbClientCheckLogin,""),
                            getNullFixString(rs, kdbClientCheckPass, ""),
                            getNullFixString(rs, kdbClientCheckProc,""));
                    pack.policyClass = getNullFixString(rs, kdbPolicyClass, "");
                    pack.defaultFacilityCode = getNullFixString(rs, kdbDefaultFacilityCode, "");


                } else {
                    throw new Exception("Client id " + id + " not found!");
                }

                rs.close();

            }

            stmt.close();

      //      pack.methodMap = pack.getMethodMap(cxn);

        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                rs.close();
            } catch (Exception exc) {
            }
            try {
                stmt.close();
            } catch (Exception exc) {
            }

        }
        return pack;


    }

    static public String getNullFixString(ResultSet rs, String name, String dfault) throws Exception {
        String result = rs.getString(name);
        if ("null".equalsIgnoreCase(result)) {
            return dfault;
        } else {
            return result;
        }
    }
}
