package com.owd.core.business.order;

import com.owd.core.OWDUtilities;
import com.owd.core.business.Address;
import com.owd.core.business.ChoiceListManager;
import com.owd.core.business.Contact;
import com.owd.core.business.owdChoiceList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


public class ShippingInfo {
private final static Logger log =  LogManager.getLogger();

    //IDs are zero if the package is new



    //Attributes

    private static final String kdbShipPKName = "order_ship_info_id";

    public String ship_info_id = "0";


    private static final String kdbShipOrderFkey = "order_fkey";

    public String order_fkey = "0";





    //Properties



    public Address shipAddress = new Address();

    public Contact shipContact = new Contact();


    public Address shipperAddress = new Address();

    public Contact shipperContact = new Contact();


    public Address returnAddress = new Address();

    public Contact returnContact = new Contact();


    private static final String kdbShipSchedDate = "scheduled_ship_date";

    public String scheduled_ship_date = OWDUtilities.getSQLDateForToday();


    private static final String kdbShipCarrier = "carr_service";

    public String carr_service = "Priority Mail";


    private static final String kdbShipCarrierRef = "carr_service_ref_num";

    public String carr_service_ref_num = "OWD.1ST.PRIORITY";


    private static final String kdbShipCarrTerms = "carr_freight_terms";

    public String carr_freight_terms = "Prepaid";


    private static final String kdbShipCarrTermsRef = "carr_freight_terms_ref_num";

    public String carr_freight_terms_ref_num = "SHIPPER";


    private static final String kdbShipHasAddlHandling = "ss_addl_hand";

    public String ss_addl_hand = "0";


    private static final String kdbShipHasDeclaredValue = "ss_declared_value";

    public String ss_declared_value = "0";


    private static final String kdbShipDeclaredValue = "declared_value";

    public String declared_value = "0";


    private static final String kdbShipHasProofDelivery = "ss_proof_delivery";

    public String ss_proof_delivery = "0";


    private static final String kdbShipHasCallTag = "ss_call_tag";

    public String ss_call_tag = "0";


    private static final String kdbShipCallTag = "call_tag";

    public String call_tag = "";


    private static final String kdbHasCOD = "ss_cod";

    public String ss_cod = "0";


    private static final String kdbCODCharge = "cod_charge";

    public String cod_charge = "0";


    private static final String kdbHasSaturday = "ss_saturday";

    public String ss_saturday = "0";


    private static final String kdbShipHasTracking = "ss_tracking";

    public String ss_tracking = "0";


    private static final String kdbShipHasOversize = "ss_oversize";

    public String ss_oversize = "0";


    private static final String kdbShipHasHazardous = "ss_hazardous";

    public String ss_hazardous = "0";


    private static final String kdbShipHasResidential = "ss_residential";

    public String ss_residential = "0";


    private static final String kdbAvsOveride = "avs_overide";

    public String avs_overide = "0";

    private static final String kdbShipComments = "comments";

    public String comments = "";


    private static final String kdbShipPackNotes = "whse_notes";

    public String whse_notes = "";


    private static final String kdbShipThirdPartyAcct = "third_party_refnum";

    public String third_party_refnum = "";


    private static final String kdbShipCustomsValue = "customs_value";

    public String customs_value = "0";


    private static final String kdbShipCustomsDesc = "customs_desc";

    public String customs_desc = " ";


    private static final String kdbShipInfoTable = "owd_order_ship_info";

    private static final String kDeleteShipInfoStatement = "DELETE FROM " + kdbShipInfoTable + " where " + kdbShipPKName + " = ?";


    private boolean needsUpdate = false;


    private static final String kUpdateShippingInfoStatement = "update " + kdbShipInfoTable

            + " set " + kdbShipSchedDate + "= ?,"

            + kdbShipCarrier + "= ?,"

            + kdbShipCarrierRef + "= ?,"

            + kdbShipCarrTerms + "= ?,"

            + kdbShipCarrTermsRef + "= ?,"

            + kdbShipHasAddlHandling + "= ?,"

            + kdbShipHasDeclaredValue + "= ?,"

            + kdbShipDeclaredValue + "= ?,"

            + kdbShipHasProofDelivery + "= ?,"

            + kdbShipHasCallTag + "= ?,"

            + kdbShipCallTag + "= ?,"

            + kdbHasCOD + "= ?,"

            + kdbCODCharge + "= ?,"

            + kdbHasSaturday + "= ?,"

            + kdbShipHasTracking + "= ?,"

            + kdbShipHasOversize + "= ?,"

            + kdbShipHasHazardous + "= ?,"

            + kdbShipHasResidential + "= ?,"

            + kdbShipComments + "= ?,"

            + kdbShipPackNotes + "= ?,"

            + "third_party_refnum= ?, " +

            "  ship_last_name= ?, " +

            "  ship_first_name= ?, " +

            "  ship_address_one= ?, " +

            "  ship_address_two= ?, " +

            "  ship_city= ?, " +

            "  ship_state= ?, " +

            "  ship_zip= ?, " +

            "  ship_country= ?, " +

            "  ship_country_ref_num= ?, " +

            "  ship_company_name= ?, " +

            "  ship_title= ?, " +

            "  ship_phone_num= ?, " +

            "  ship_fax_num= ?, " +

            "  ship_email_address= ?, " +

            "  customs_value= ?, " +

            "  customs_desc= ? , avs_overide = ?  where order_ship_info_id = ?";


    private static final String kCreateShippingInfoStatement = "insert into " + kdbShipInfoTable + "("

            + kdbShipSchedDate + ","

            + kdbShipCarrier + ","

            + kdbShipCarrierRef + ","

            + kdbShipCarrTerms + ","

            + kdbShipCarrTermsRef + ","

            + kdbShipHasAddlHandling + ","

            + kdbShipHasDeclaredValue + ","

            + kdbShipDeclaredValue + ","

            + kdbShipHasProofDelivery + ","

            + kdbShipHasCallTag + ","

            + kdbShipCallTag + ","

            + kdbHasCOD + ","

            + kdbCODCharge + ","

            + kdbHasSaturday + ","

            + kdbShipHasTracking + ","

            + kdbShipHasOversize + ","

            + kdbShipHasHazardous + ","

            + kdbShipHasResidential + ","

            + kdbShipComments + ","

            + kdbShipPackNotes + "," +

            "third_party_refnum, " +

            "ship_last_name, " +

            "ship_first_name, " +

            "ship_address_one, " +

            "ship_address_two, " +

            "ship_city, " +

            "ship_state, " +

            "ship_zip, " +

            "ship_country, " +

            "ship_country_ref_num, " +

            "ship_company_name, " +

            "ship_title, " +

            "ship_phone_num, " +

            "ship_fax_num, " +

            "ship_email_address, " +

            "customs_value, " +

            "customs_desc, " + kdbShipOrderFkey + " ) VALUES (	    ?,?,?,?,?,?,?,?,?,?" +

            ",?,?,?,?,?,?,?,?,?,?" +

            ",?,?,?,?,?,?,?,?,?,?" +

            ",?,?,?,?,?,?,?,?)";


    public ShippingInfo() {


    }


    public static void main(String[] args) throws Exception
    {

        owdChoiceList l = ChoiceListManager.getChoiceListManager().getList("Service");
        for (String value: (Vector<String>)l.getValues())
        {
            log.debug("refFromValue:" + value + ":" + OrderUtilities.getServiceList().getRefForValue(value) + ":valueFromRef:" + OrderUtilities.getServiceList().getValueForRef(OrderUtilities.getServiceList().getRefForValue(value)));

        }

        String carrServiceRef = OrderUtilities.getServiceList().getRefForValue("OSM Domestic");
        log.debug("setShipOptions:refFromValue:" + "OSM Domestic" + ":" + carrServiceRef + ":valueFromRef:" + OrderUtilities.getServiceList().getValueForRef(carrServiceRef));

         carrServiceRef = OrderUtilities.getServiceList().getRefForValue("LTL");
        log.debug("setShipOptions:refFromValue:" + "LTL" + ":" + carrServiceRef + ":valueFromRef:" + OrderUtilities.getServiceList().getValueForRef(carrServiceRef));
    }
    public ShippingInfo(String aid,

                        String aorder_fkey,

                        Address anAddress,

                        Contact aContact,

                        String ascheduled_ship_date,

                        String acarr_service,

                        String acarr_service_ref_num,

                        String acarr_freight_terms,

                        String acarr_freight_terms_ref_num,

                        String ass_addl_hand,

                        String ass_declared_value,

                        String adeclared_value,

                        String ass_proof_delivery,

                        String ass_call_tag,

                        String acall_tag,

                        String ass_cod,

                        String acod_charge,

                        String ass_saturday,

                        String ass_tracking,

                        String ass_oversize,

                        String ass_hazardous,

                        String ass_residential,

                        String acomments,

                        String awhse_notes,

                        String athird_party_refnum,

                        String acustoms_value,

                        String acustoms_desc,
                        String aavs_overide) {

        ship_info_id = aid;

        order_fkey = aorder_fkey;

        shipAddress = anAddress;

        shipContact = aContact;

        scheduled_ship_date = ascheduled_ship_date;

        carr_service = acarr_service;

        carr_service_ref_num = acarr_service_ref_num;

        carr_freight_terms = acarr_freight_terms;

        carr_freight_terms_ref_num = acarr_freight_terms_ref_num;

        ss_addl_hand = ass_addl_hand;

        ss_declared_value = ass_declared_value;

        declared_value = adeclared_value;

        ss_proof_delivery = ass_proof_delivery;

        ss_call_tag = ass_call_tag;

        call_tag = acall_tag;

        ss_cod = ass_cod;

        cod_charge = acod_charge;

        ss_saturday = ass_saturday;

        ss_tracking = ass_tracking;

        ss_oversize = ass_oversize;

        ss_hazardous = ass_hazardous;

        ss_residential = ass_residential;

        comments = acomments;

        whse_notes = awhse_notes;

        third_party_refnum = athird_party_refnum;

        customs_value = acustoms_value;

        customs_desc = acustoms_desc;
        avs_overide = aavs_overide;

    }


    public static ShippingInfo getShippingInfoForID(Connection cxn, String shipInfoID) throws Exception {

       // //log.debug("Loading shipping for id " + shipInfoID);

        //load existing order
        ShippingInfo si = dbload(cxn, shipInfoID);
      //  //log.debug("returning ship address 1 = " + si.shipAddress.address_one);

        return si;

    }


    public boolean isModified() {

        return needsUpdate;

    }


    public boolean isNew() {

        if ("0".equals(ship_info_id))

            return true;


        return false;

    }


    public void copyNewSIVars(ShippingInfo si) {

        si.shipContact = Contact.createFromStorableString(shipContact.toStorableString());

        si.shipAddress = Address.createFromStorableString(shipAddress.toStorableString());

        si.shipperContact = Contact.createFromStorableString(shipperContact.toStorableString());

        si.shipperAddress = Address.createFromStorableString(shipperAddress.toStorableString());


        si.returnContact = Contact.createFromStorableString(returnContact.toStorableString());

        si.returnAddress = Address.createFromStorableString(returnAddress.toStorableString());


        si.scheduled_ship_date = scheduled_ship_date;

        si.carr_service = carr_service;

        si.carr_service_ref_num = carr_service_ref_num;

        si.carr_freight_terms = carr_freight_terms;

        si.carr_freight_terms_ref_num = carr_freight_terms_ref_num;

        si.ss_addl_hand = ss_addl_hand;

        si.ss_declared_value = ss_declared_value;

        si.declared_value = declared_value;

        si.ss_proof_delivery = ss_proof_delivery;

        si.ss_call_tag = ss_call_tag;

        si.call_tag = call_tag;

        si.ss_cod = ss_cod;

        si.cod_charge = cod_charge;

        si.ss_saturday = ss_saturday;

        si.ss_tracking = ss_tracking;

        si.ss_oversize = ss_oversize;

        si.ss_hazardous = ss_hazardous;

        si.ss_residential = ss_residential;

        si.comments = comments;

        si.whse_notes = whse_notes;

        si.third_party_refnum = third_party_refnum;

        si.customs_value = customs_value;

        si.customs_desc = customs_desc;


        si.needsUpdate = needsUpdate;


    }


    


    public void setShippingAddress(Address anAddress) {

        shipAddress = anAddress;

    }


    public void setShippingContact(Contact aContact) {

        shipContact = aContact;

    }


    public void dbsave(Connection cxn) throws Exception {


        PreparedStatement stmt = null;

        ResultSet rs = null;


        try {


            if (comments != null) {

                if (comments.length() > 0)

                    comments = comments.replace('\'', ' ');

            }



            /*

            if(	   ("UPS Standard Canada").equals(carr_service)

                || ("UPS Express Canada").equals(carr_service)

                || ("UPS Expedited Canada").equals(carr_service)

                || ("UPS Worldwide Expedited").equals(carr_service)

                || ("UPS Worldwide Express").equals(carr_service))

            {

                carr_freight_terms = "Delivered Duty/Tax Unpaid";

                carr_freight_terms_ref_num = "DDU";

            }

            */



            if (null == shipAddress.company_name || ("").equals(shipAddress.company_name)

                    || (".").equals(shipAddress.company_name) || null == shipAddress.company_name) {

                ss_residential = "1";

            }


            stmt = cxn.prepareStatement(kCreateShippingInfoStatement);


            stmt.setString(1, scheduled_ship_date);

            stmt.setString(2, ChoiceListManager.getTranslatedString(carr_service));

            stmt.setString(3, ChoiceListManager.getTranslatedString(carr_service_ref_num));

            stmt.setString(4, carr_freight_terms);

            stmt.setString(5, carr_freight_terms_ref_num);
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(6, new Integer(ss_addl_hand).intValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(7, new Integer(ss_declared_value).intValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setFloat(8, new Float(declared_value).floatValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(9, new Integer(ss_proof_delivery).intValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(10, new Integer(ss_call_tag).intValue());

            stmt.setString(11, call_tag);
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(12, new Integer(ss_cod).intValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setFloat(13, new Float(cod_charge).floatValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(14, new Integer(ss_saturday).intValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(15, new Integer(ss_tracking).intValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(16, new Integer(ss_oversize).intValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(17, new Integer(ss_hazardous).intValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(18, new Integer(ss_residential).intValue());

            stmt.setString(19, OrderUtilities.limitStr(253, comments));

            stmt.setString(20, OrderUtilities.limitStr(253, whse_notes));

            stmt.setString(21, OrderUtilities.limitStr(40, third_party_refnum));

            stmt.setString(22, OrderUtilities.limitStr(82, OWDUtilities.getLastNameFromWholeName(shipContact.getName())));

            stmt.setString(23, OrderUtilities.limitStr(82, OWDUtilities.getFirstNameFromWholeName(shipContact.getName())));

            stmt.setString(24, OrderUtilities.limitStr(82, shipAddress.address_one));

            stmt.setString(25, OrderUtilities.limitStr(82, shipAddress.address_two));

            stmt.setString(26, OrderUtilities.limitStr(82, shipAddress.city));

            stmt.setString(27, OrderUtilities.limitStr(32, shipAddress.state));

            stmt.setString(28, OrderUtilities.limitStr(13, shipAddress.zip));

            stmt.setString(29, OrderUtilities.limitStr(32, shipAddress.country));

            stmt.setString(30, OrderUtilities.getCountryList().getRefForValue(shipAddress.country));

            stmt.setString(31, OrderUtilities.limitStr(82, shipAddress.company_name));

            stmt.setString(32, "");

            stmt.setString(33, OrderUtilities.limitStr(30, shipContact.getPhone()));

            stmt.setString(34, OrderUtilities.limitStr(30, shipContact.getFax()));

            stmt.setString(35, OrderUtilities.limitStr(82, shipContact.getEmail()));
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setFloat(36, new Float(customs_value).floatValue());

            stmt.setString(37, OrderUtilities.limitStr(30, customs_desc));
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());
            stmt.setInt(38, new Integer(order_fkey).intValue());
                //log.debug("Params:"+stmt.getParameterMetaData().getParameterCount());



            int rowsUpdated = stmt.executeUpdate();


            if (rowsUpdated < 1)

                throw new Exception("Could not save shipping info!");


            stmt.close();


            stmt = cxn.prepareStatement("select @@IDENTITY");


            stmt.executeQuery();

            rs = stmt.getResultSet();

            if (rs.next()) {

                ship_info_id = rs.getString(1);

            }

            rs.close();

            stmt.close();


        } catch (Exception ex) {

            ship_info_id = "0";

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


    public void setShipOptions(String carrier, String terms, String acct) throws Exception {

        if(carrier.indexOf("(Book)")>=0)
        {
            carrier = carrier.substring(0,carrier.indexOf("(Book)")).trim();
        }
        String carrServiceRef = OrderUtilities.getServiceList().getRefForValue(carrier);
        log.debug("setShipOptions:refFromValue:"+carrier+":"+carrServiceRef);
        String carrTermsRef = OrderUtilities.getTermsList().getRefForValue(terms);


        if (carrServiceRef == null)

            throw new Exception("shipping type code |" + carrier + "| not valid");

        if (carrTermsRef == null) {

            if (terms.equals("Third Party Billing"))

                carrTermsRef = "";

            else

                throw new Exception("carrier terms \"" + terms + "\" not valid");

        }

        carr_service = OrderUtilities.getServiceList().getValueForRef(carrServiceRef);//carrier;
        log.debug("setShipOptions:valueFromRef:"+carrServiceRef+":"+carr_service);

        if(carr_service == null)
        {
          throw new Exception("shipping type code |" + carrier + "| not valid");  
        }
        carr_freight_terms = terms;

        carr_service_ref_num = carrServiceRef;

        carr_freight_terms_ref_num = carrTermsRef;

        third_party_refnum = acct;

    }


    public int getDestinationType() {

        if ("USA".equals(shipAddress.country)) {

            if (shipAddress.address_one.toUpperCase().startsWith("BOX") || shipAddress.address_one.toUpperCase().startsWith("PO BOX")

                    || shipAddress.address_one.toUpperCase().startsWith("P.O. BOX")) {

                return kPOAPOFPO;

            } else {

                return kDomestic;

            }

        } else {

            if ("CANADA".equals(shipAddress.country)) {

                return kCanada;

            } else {

                return kInternational;

            }

        }

    }


    public static final int kDomestic = 1;

    public static final int kPOAPOFPO = 2;

    public static final int kCanada = 3;

    public static final int kInternational = 4;


    public void dbdelete(Connection cxn) throws Exception {


        if (isNew()) {

            //do nothing

        } else {

            PreparedStatement stmt = null;

            try {

                stmt = cxn.prepareStatement(kDeleteShipInfoStatement);

                stmt.setString(1, ship_info_id);


                int rowsUpdated = stmt.executeUpdate();


                if (rowsUpdated < 1) throw new Exception("Cannot delete shipping info ID " + ship_info_id);


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


    public void dbupdate(Connection cxn) throws Exception {


        if (isNew()) {

            dbsave(cxn);

        } else {

            PreparedStatement ps = null;


            try {

                /*				if(	   ("UPS Standard Canada").equals(carr_service)

                    || ("UPS Express Canada").equals(carr_service)

                    || ("UPS Expedited Canada").equals(carr_service)

                    || ("UPS Worldwide Expedited").equals(carr_service)

                    || ("UPS Worldwide Express").equals(carr_service))

                {

                    carr_freight_terms = "Delivered Duty/Tax Unpaid";

                    carr_freight_terms_ref_num = "DDU";

                }

                */

                if (null == shipAddress.company_name || ("").equals(shipAddress.company_name)

                        || (".").equals(shipAddress.company_name) || null == shipAddress.company_name) {

                    ss_residential = "1";

                }


                try {

                    float test = new Float(customs_value).floatValue();

                } catch (Exception ex) {

                    customs_value = "0.00";

                }

                try {

                    float test = new Float(cod_charge).floatValue();

                } catch (Exception ex) {

                    cod_charge = "0.00";

                }

                try {

                    float test = new Float(declared_value).floatValue();

                } catch (Exception ex) {

                    declared_value = "0.00";

                }


                ps = cxn.prepareStatement(kUpdateShippingInfoStatement);

                ps.setString(1, scheduled_ship_date);

                ps.setString(2, ChoiceListManager.getTranslatedString(carr_service));

                ps.setString(3, ChoiceListManager.getTranslatedString(carr_service_ref_num));

                ps.setString(4, carr_freight_terms);

                ps.setString(5, carr_freight_terms_ref_num);

                ps.setInt(6, new Integer(ss_addl_hand).intValue());

                ps.setInt(7, new Integer(ss_declared_value).intValue());

                ps.setFloat(8, new Float(declared_value).floatValue());

                ps.setInt(9, new Integer(ss_proof_delivery).intValue());

                ps.setInt(10, new Integer(ss_call_tag).intValue());

                ps.setString(11, call_tag);

                ps.setInt(12, new Integer(ss_cod).intValue());

                ps.setFloat(13, new Float(cod_charge).floatValue());

                ps.setInt(14, new Integer(ss_saturday).intValue());

                ps.setInt(15, new Integer(ss_tracking).intValue());

                ps.setInt(16, new Integer(ss_oversize).intValue());

                ps.setInt(17, new Integer(ss_hazardous).intValue());

                ps.setInt(18, new Integer(ss_residential).intValue());


                ps.setString(19, OrderUtilities.limitStr(253, comments));

                ps.setString(20, OrderUtilities.limitStr(253, whse_notes));

                ps.setString(21, OrderUtilities.limitStr(40, third_party_refnum));

                ps.setString(22, OrderUtilities.limitStr(82, OWDUtilities.getLastNameFromWholeName(shipContact.getName())));

                ps.setString(23, OrderUtilities.limitStr(82, OWDUtilities.getFirstNameFromWholeName(shipContact.getName())));

                ps.setString(24, OrderUtilities.limitStr(82, shipAddress.address_one));

                ps.setString(25, OrderUtilities.limitStr(82, shipAddress.address_two));

                ps.setString(26, OrderUtilities.limitStr(82, shipAddress.city));

                ps.setString(27, OrderUtilities.limitStr(32, shipAddress.state));

                ps.setString(28, OrderUtilities.limitStr(13, shipAddress.zip));

                ps.setString(29, OrderUtilities.limitStr(32, shipAddress.country));

                ps.setString(30, OrderUtilities.getCountryList().getRefForValue(shipAddress.country));

                ps.setString(31, OrderUtilities.limitStr(82, shipAddress.company_name));

                ps.setString(32, "");

                ps.setString(33, OrderUtilities.limitStr(30, shipContact.getPhone()));

                ps.setString(34, OrderUtilities.limitStr(30, shipContact.getFax()));

                ps.setString(35, OrderUtilities.limitStr(82, shipContact.getEmail()));

                ps.setFloat(36, new Float(customs_value).floatValue());

                ps.setString(37, OrderUtilities.limitStr(30, customs_desc));



                ps.setInt(38, new Integer(avs_overide).intValue());

                ps.setInt(39, new Integer(ship_info_id).intValue());



                int rowsUpdated = ps.executeUpdate();


                if (rowsUpdated < 1)

                    throw new Exception("Could not update ship info id " + ship_info_id);


            } catch (Exception ex) {
                ex.printStackTrace();
                throw ex;

            } finally {

                try {
                    ps.close();
                } catch (Exception exc) {
                }


            }


        }


    }


    private static ShippingInfo dbload(Connection cxn, String id) throws Exception {


        Statement stmt = null;

        ResultSet rs = null;

        ShippingInfo pack = null;


        try {

            stmt = cxn.createStatement();

            String isql = "select * from " + kdbShipInfoTable + " (NOLOCK) where " + kdbShipPKName + " = " + id;


            rs = stmt.executeQuery(isql);


            while (rs.next()) {
                //log.debug("found ship info for id " + id + ":" + rs.getString("ship_address_one"));
                pack = new ShippingInfo(id,

                        rs.getString(kdbShipOrderFkey),

                        new Address(rs.getString("ship_company_name"),

                                rs.getString("ship_address_one"),

                                rs.getString("ship_address_two"),

                                rs.getString("ship_city"),

                                rs.getString("ship_state"),

                                rs.getString("ship_zip"),

                                rs.getString("ship_country")),

                        new Contact(rs.getString("ship_first_name") + " " + rs.getString("ship_last_name"),

                                rs.getString("ship_phone_num"),

                                rs.getString("ship_fax_num"),

                                rs.getString("ship_email_address"),

                                ""),

                        rs.getString(kdbShipSchedDate),

                        rs.getString(kdbShipCarrier),

                        rs.getString(kdbShipCarrierRef),

                        rs.getString(kdbShipCarrTerms),

                        rs.getString(kdbShipCarrTermsRef),

                        "" + rs.getInt(kdbShipHasAddlHandling),

                        "" + rs.getInt(kdbShipHasDeclaredValue),

                        rs.getString(kdbShipDeclaredValue),

                        "" + rs.getInt(kdbShipHasProofDelivery),

                        "" + rs.getInt(kdbShipHasCallTag),

                        rs.getString(kdbShipCallTag),

                        "" + rs.getString(kdbHasCOD),

                        rs.getString(kdbCODCharge),

                        "" + rs.getInt(kdbHasSaturday),

                        "" + rs.getInt(kdbShipHasTracking),

                        "" + rs.getInt(kdbShipHasOversize),

                        "" + rs.getInt(kdbShipHasHazardous),

                        "" + rs.getInt(kdbShipHasResidential),

                        rs.getString(kdbShipComments),

                        rs.getString(kdbShipPackNotes),

                        rs.getString(kdbShipThirdPartyAcct),

                        rs.getString(kdbShipCustomsValue),

                        rs.getString(kdbShipCustomsDesc),rs.getInt(kdbAvsOveride)+"");


            }


            rs.close();

            stmt.close();


        } catch (Exception ex) {

            ex.printStackTrace();
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

     //   //log.debug("found pack ship info for id " + id + ":" + pack.shipAddress.address_one);


        return pack;

    }


}
