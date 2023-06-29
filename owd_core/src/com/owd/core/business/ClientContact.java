package com.owd.core.business;

import com.owd.core.managers.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientContact {
private final static Logger log =  LogManager.getLogger();
    //IDs are zero if the object is new

    //Attributes
    public static final String kdbClientContactPKName = "id";
    public String id = "0";

    //Properties
    public static final String kdbClientFkey = "client_fkey";
    public String client_fkey = "";

    public static final String kdbContactName = "name";
    public String name = "";

    public static final String kdbContactCompany = "company";
    public String company = "";

    public static final String kdbContactAddressOne = "address_one";
    public String address_one = "";

    public static final String kdbContactAddressTwo = "address_two";
    public String address_two = "";

    public static final String kdbContactCity = "city";
    public String city = "";

    public static final String kdbContactState = "state";
    public String state = "";

    public static final String kdbContactZip = "zip";
    public String zip = "";

    public static final String kdbContactCountry = "country";
    public String country = "";

    public static final String kdbContactPhone = "phone_num";
    public String phone_num = "";

    public static final String kdbContactFax = "fax_num";
    public String fax_num = "";

    public static final String kdbContactEmail = "email_address";
    public String email_address = "";

    public static final String kdbContactType = "type";
    public int type = 0;
    //

    public static final int kClientContactAMType = 1;
    public static final int kClientContactCustomerServiceType = 2;
    public static final int kClientContactTechnicalType = 4;
    public static final int kClientContactAdminType = 8;

    public static final String kdbContactExtranetLogin = "login";
    public String login = "";


    private static final String kdbContactTable = "owd_client_contact";
    private static final String kDeleteContactStatement = "DELETE FROM " + kdbContactTable + " where " + kdbClientContactPKName + " = ?";

    private boolean needsUpdate = false;
    private String validation_error = "";


    private static final String kUpdateContactStatement = "update " + kdbContactTable + " set "
            + kdbClientContactPKName + "=?,"
            + kdbClientFkey + "=?,"
            + kdbContactName + "=?,"
            + kdbContactCompany + "=?,"
            + kdbContactAddressOne + "=?,"
            + kdbContactAddressTwo + "=?,"
            + kdbContactCity + "=?,"
            + kdbContactState + "=?,"
            + kdbContactZip + "=?,"
            + kdbContactCountry + "=?,"
            + kdbContactPhone + "=?,"
            + kdbContactFax + "=?,"
            + kdbContactEmail + "=?,"
            + kdbContactType + "=?,"
            + kdbContactExtranetLogin + "=? "
            + " WHERE " + kdbClientContactPKName + " = ? ";

    private static final String kInsertContactStatement = "insert into " + kdbContactTable + "("
            + kdbClientFkey + ","
            + kdbContactName + ","
            + kdbContactCompany + ","
            + kdbContactAddressOne + ","
            + kdbContactAddressTwo + ","
            + kdbContactCity + ","
            + kdbContactState + ","
            + kdbContactZip + ","
            + kdbContactCountry + ","
            + kdbContactPhone + ","
            + kdbContactFax + ","
            + kdbContactEmail + ","
            + kdbContactType + ","
            + kdbContactExtranetLogin + ") VALUES (?,?,?,?,?,?,?,?,?,?"
            + ",?,?,?,?)";

    public ClientContact() {
    }


    public ClientContact(String aid,
                         String aclient_fkey,
                         String aname,
                         String acompany,
                         String aaddress_one,
                         String aaddress_two,
                         String acity,
                         String astate,
                         String azip,
                         String acountry,
                         String aphone_num,
                         String afax_num,
                         String aemail_address,
                         String atype,
                         String alogin) {
        id = aid;
        client_fkey = aclient_fkey;
        name = aname;
        company = acompany;
        address_one = aaddress_one;
        address_two = aaddress_two;
        city = acity;
        state = astate;
        zip = azip;
        country = acountry;
        phone_num = aphone_num;
        fax_num = afax_num;
        email_address = aemail_address;
        type = new Integer(atype).intValue();
        login = alogin;

    }

    public static void main(String[] args) throws Exception {
        ClientContact contact = new ClientContact();
        contact.client_fkey = "101";
        contact.type = kClientContactAMType;
        contact.name = "Marlene Darling";
        contact.company = "OWD";
        contact.address_one = "10 1st Ave E";
        contact.address_two = "";
        contact.city = "Mobridge";
        contact.state = "SD";
        contact.zip = "57601";
        contact.country = "USA";
        contact.phone_num = "605-845-7172";
        contact.fax_num = "";
        contact.email_address = "healthfx@owd.com";
        contact.login = "marlene";

        Connection cxn = null;

        try {
            cxn = ConnectionManager.getConnection();
            contact.dbsave(cxn);
            cxn.commit();
        } finally {
            try {
                cxn.close();
            } catch (Exception ex) {
            }
        }

        List lister = ClientContact.getContactsForClientID("101");
        Iterator it = lister.iterator();
        while (it.hasNext()) {
            ClientContact p = (ClientContact) it.next();
            //log.debug("got Client Contact for Client ID 101: ");
            //log.debug(p.toString());
        }
    }

    public String toString() {
        return "ID : " + id + "\nClient ID : " + client_fkey + "\nName : " + name + "\nCompany Name : \n" + company
                + "\nLine 1 : " + address_one + "\nLine 2 : " + address_two
                + "\nCity : " + city + "\nState : " + state + "\nZip : " + zip + "\nCountry : " + country
                + "\nPhone : " + phone_num + "\nFax : " + fax_num + "\nEmail : " + email_address + "\nType : " + type
                + "\nLogin : " + login;


    }

    public static List getContactsForClientID(String clientID) throws Exception {
        Connection cxn = null;
        List contactList = null;

        try {
            cxn = ConnectionManager.getConnection();
            contactList = getContactsForClientID(cxn, clientID);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }

        return contactList;
    }

    public static List getContactsForClientID(Connection cxn, String clientID) throws Exception {

        Statement stmt = null;
        ResultSet rs = null;
        List contactList = new ArrayList();

        try {
            stmt = cxn.createStatement();
            String isql = "select * from " + kdbContactTable + " where " + kdbClientFkey + " = " + clientID;

            rs = stmt.executeQuery(isql);

            if (rs!=null) {

                while (rs.next()) {
                    contactList.add(new ClientContact(rs.getString(kdbClientContactPKName),
                            getNullFixString(rs, kdbClientFkey, ""),
                            getNullFixString(rs, kdbContactName, ""),
                            getNullFixString(rs, kdbContactCompany, ""),
                            getNullFixString(rs, kdbContactAddressOne, ""),
                            getNullFixString(rs, kdbContactAddressTwo, ""),
                            getNullFixString(rs, kdbContactCity, ""),
                            getNullFixString(rs, kdbContactState, ""),
                            getNullFixString(rs, kdbContactZip, ""),
                            getNullFixString(rs, kdbContactCountry, ""),
                            getNullFixString(rs, kdbContactPhone, ""),
                            getNullFixString(rs, kdbContactFax, ""),
                            getNullFixString(rs, kdbContactEmail, ""),
                            rs.getString(kdbContactType),
                            getNullFixString(rs, kdbContactExtranetLogin, "")));


                }

                rs.close();

            }

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
            return contactList;
        }
    }

    public static ClientContact getContactForID(Connection cxn, String contactID) throws Exception {
        return dbload(cxn, contactID);
    }

    public static ClientContact getContactForID(String contactID) throws Exception {
        Connection cxn = null;
        ClientContact contact = null;

        try {
            cxn = ConnectionManager.getConnection();
            contact = getContactForID(cxn, contactID);
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                cxn.close();
            } catch (Exception e) {
            }
        }

        return contact;
    }

    public boolean isModified() {
        return needsUpdate;
    }

    public boolean isNew() {
        if ("0".equals(id))
            return true;

        return false;
    }

    public boolean validated() {


        return true;
    }

    public String[] getValidationErrors() {
        return null;
    }


    public void dbsave(Connection cxn) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = cxn.prepareStatement(kInsertContactStatement);

            stmt.setInt(1, new Integer(client_fkey).intValue());
            stmt.setString(2, name);
            stmt.setString(3, company);
            stmt.setString(4, address_one);
            stmt.setString(5, address_two);
            stmt.setString(6, city);
            stmt.setString(7, state);
            stmt.setString(8, zip);
            stmt.setString(9, country);
            stmt.setString(10, phone_num);
            stmt.setString(11, fax_num);
            stmt.setString(12, email_address);
            stmt.setInt(13, type);
            stmt.setString(14, login);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated < 1)
                throw new Exception("Could not save contact!");
            stmt.close();

            stmt = cxn.prepareStatement("select @@IDENTITY");

            stmt.executeQuery();
            rs = stmt.getResultSet();
            if (rs.next()) {
                id = rs.getString(1);
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

    }


    public void dbdelete(Connection cxn) throws Exception {

        if (isNew()) {
            //do nothing
        } else {
            PreparedStatement stmt = null;
            try {
                stmt = cxn.prepareStatement(kDeleteContactStatement);
                stmt.setString(1, id);

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated < 1) throw new Exception("Cannot delete contact ID " + id);

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
            PreparedStatement stmt = null;
            try {
                stmt = cxn.prepareStatement(kUpdateContactStatement);


                stmt.setInt(1, new Integer(client_fkey).intValue());
                stmt.setString(2, name);
                stmt.setString(3, company);
                stmt.setString(4, address_one);
                stmt.setString(5, address_two);
                stmt.setString(6, city);
                stmt.setString(7, state);
                stmt.setString(8, zip);
                stmt.setString(9, country);
                stmt.setString(10, phone_num);
                stmt.setString(11, fax_num);
                stmt.setString(12, email_address);
                stmt.setInt(13, type);
                stmt.setString(14, login);
                stmt.setInt(15, new Integer(id).intValue());

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated < 1)
                    throw new Exception("Could not update contact " + id);

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


    private static ClientContact dbload(Connection cxn, String id) throws Exception {

        Statement stmt = null;
        ResultSet rs = null;
        ClientContact pack = null;

        try {
            stmt = cxn.createStatement();
            String isql = "select * from " + kdbContactTable + " where " + kdbClientContactPKName + " = " + id;

            boolean hasResultSet = stmt.execute(isql);

            if (hasResultSet) {
                rs = stmt.getResultSet();

                if (rs.next()) {
                    pack = new ClientContact(rs.getString(kdbClientContactPKName),
                            getNullFixString(rs, kdbClientFkey, ""),
                            getNullFixString(rs, kdbContactName, ""),
                            getNullFixString(rs, kdbContactCompany, ""),
                            getNullFixString(rs, kdbContactAddressOne, ""),
                            getNullFixString(rs, kdbContactAddressTwo, ""),
                            getNullFixString(rs, kdbContactCity, ""),
                            getNullFixString(rs, kdbContactState, ""),
                            getNullFixString(rs, kdbContactZip, ""),
                            getNullFixString(rs, kdbContactCountry, ""),
                            getNullFixString(rs, kdbContactPhone, ""),
                            getNullFixString(rs, kdbContactFax, ""),
                            getNullFixString(rs, kdbContactEmail, ""),
                            rs.getString(kdbContactType),
                            getNullFixString(rs, kdbContactExtranetLogin, ""));


                } else {
                    throw new Exception("Contact id " + id + " not found!");
                }

                rs.close();

            }

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
            return pack;
        }


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
