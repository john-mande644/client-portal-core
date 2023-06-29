package com.owd.core.business.order;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Supplier {
private final static Logger log =  LogManager.getLogger();

    //IDs are zero if the item is new



    //Attributes

    public static final String kdbSupplierPKName = "supplier_id";

    public int supplier_id = 0;


    public static final String kdbSupplierClientFkey = "client_fkey";

    public int client_fkey = 0;


    public static final String kdbSupplierCreatedBy = "created_by";

    public String created_by = "";


    public static final String kdbSupplierCreatedOn = "created_date";

    public String created_date = "";


    public static final String kdbSupplierModifiedBy = "modified_by";

    public String modified_by = "";


    public static final String kdbSupplierModifiedOn = "modified_date";

    public String modified_date = "";





    //Properties

    public static final String kdbSupplierName = "supp_name";

    public String supp_name = "";


    public static final String kdbSupplierContact = "supp_contact";

    public String supp_contact = "";


    public static final String kdbSupplierEmail = "supp_email";

    public String supp_email = "";


    public static final String kdbSupplierPhone = "supp_phone";

    public String supp_phone = "";


    public static final String kdbSupplierFax = "supp_fax";

    public String supp_fax = "";


    public static final String kdbSupplierCompany = "supp_company";

    public String supp_company = "";


    public static final String kdbSupplierAddr1 = "supp_addr_one";

    public String supp_addr_one = "";


    public static final String kdbSupplierAddr2 = "supp_addr_two";

    public String supp_addr_two = "";


    public static final String kdbSupplierCity = "supp_city";

    public String supp_city = "";


    public static final String kdbSupplierState = "supp_state";

    public String supp_state = "";


    public static final String kdbSupplierZip = "supp_zip";

    public String supp_zip = "";


    public static final String kdbSupplierSite = "supp_site";

    public String supp_site = "";


    private static final String kdbSupplierTable = "owd_supplier";


    private static final String loadStatement = "select * from owd_supplier where supplier_id = ? ";


    private static final String loadByNameStatement = "select * from owd_supplier where supp_name = ? AND client_fkey = ? ";


    private static final String updateStatement1 = "update owd_supplier set modified_by = ?," +

            " modified_date = ?," +

            " supp_name = ?," +

            " supp_contact = ?," +

            " supp_email = ?," +

            " supp_phone = ?," +

            " supp_fax = ?," +

            " supp_company = ?," +

            " supp_addr_one = ?," +

            " supp_addr_two = ?," +

            " supp_city = ?," +

            " supp_state = ?," +

            " supp_zip = ?," +

            " supp_site = ?  where supplier_id = ? and client_fkey = ?";


    private static final String deleteStatement = "delete";


    private static final String createStatement = "insert into owd_supplier (created_by," +

            "created_date," +

            "modified_by," +

            "modified_date," +

            " supp_name," +

            " supp_contact," +

            " supp_email," +

            " supp_phone," +

            " supp_fax," +

            " supp_company," +

            " supp_addr_one," +

            " supp_addr_two," +

            " supp_city," +

            " supp_state, client_fkey," +

            " supp_zip," +

            " supp_site ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    private boolean needsUpdate = false;


    public Supplier(String aclient_id) {

        try {

            client_fkey = (new Integer("0" + aclient_id).intValue());

        } catch (NumberFormatException ex) {
            client_fkey = (int) 0;
        }

    }


    public Supplier(String aid,

                    String aclient_id,

                    String asupp_name,

                    String asupp_contact,

                    String asupp_email,

                    String asupp_phone,

                    String asupp_fax,

                    String asupp_company,

                    String asupp_addr_one,

                    String asupp_addr_two,

                    String asupp_city,

                    String asupp_state,

                    String asupp_zip,

                    String asupp_site,

                    String acreated_by,

                    String acreated_on,

                    String amodified_by,

                    String amodified_on) {

        supp_name = asupp_name;

        supp_contact = asupp_contact;

        supp_email = asupp_email;

        supp_phone = asupp_phone;

        supp_fax = asupp_fax;


        supp_company = asupp_company;

        supp_addr_one = asupp_addr_one;

        supp_addr_two = asupp_addr_two;

        supp_city = asupp_city;

        supp_state = asupp_state;

        supp_zip = asupp_zip;

        supp_site = asupp_site;


        try {

            supplier_id = (new Integer("0" + aid).intValue());

        } catch (NumberFormatException ex) {
            supplier_id = (int) 0;
        }


        try {

            client_fkey = (new Integer("0" + aclient_id).intValue());

        } catch (NumberFormatException ex) {
            client_fkey = (int) 0;
        }


        created_by = acreated_by;

        created_date = acreated_on;

        modified_by = amodified_by;

        modified_date = amodified_on;


    }


    public static String getSupplierIDForName(Connection cxn, String name, String clientID, boolean createIfMissing) throws Exception {

        PreparedStatement ps = null;

        ResultSet rs = null;

        try {

            ps = cxn.prepareStatement("select supplier_id from owd_supplier where supp_name = ? and client_fkey = ?");

            ps.setString(1, name);

            ps.setInt(2, new Integer(clientID).intValue());


            ps.executeQuery();

            rs = ps.getResultSet();

            if (rs.next()) {

                return rs.getString(1);

            } else {

                if (createIfMissing) {

                    Supplier supp = new Supplier(com.owd.core.business.Client.getClientForID(clientID).client_id);

                    supp.supp_name = name;

                    supp.dbsave(cxn);

                    return ("" + supp.supplier_id);

                } else {


                    throw new Exception("Can't find supplier");

                }

            }


        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                rs.close();
            } catch (Exception e) {
            }

            try {
                ps.close();
            } catch (Exception e) {
            }

        }


    }


    public static String getSupplierIDForName(String name, String clientID, boolean createIfMissing) throws Exception {

        //load existing package



        Connection cxn = null;


        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            String idStr = getSupplierIDForName(cxn, name, clientID, createIfMissing);

            cxn.commit();

            return idStr;

        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                cxn.close();
            } catch (Exception e) {
            }

        }


    }


    public static String getSupplierNameForID(Connection cxn, String id, boolean returnBlankIfMissing) throws Exception {

        PreparedStatement ps = null;

        ResultSet rs = null;

        try {

            ps = cxn.prepareStatement("select supp_name from owd_supplier where supplier_id = ?");

            ps.setInt(1, new Integer(id).intValue());


            ps.executeQuery();

            rs = ps.getResultSet();

            if (rs.next()) {

                return rs.getString(1);

            } else {

                if (returnBlankIfMissing)

                    return "";

                else

                    throw new Exception("Can't find supplier");

            }


        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                rs.close();
            } catch (Exception e) {
            }

            try {
                ps.close();
            } catch (Exception e) {
            }

        }


    }


    public static String getSupplierNameForID(String id, boolean blanker) throws Exception {

        //load existing package

        String sql = "select supp_name from owd_supplier where supplier_id = ? and client_fkey = ?";

        Connection cxn = null;


        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            String idStr = getSupplierNameForID(cxn, id, blanker);

            cxn.commit();

            return idStr;

        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                cxn.close();
            } catch (Exception e) {
            }

        }


    }


    public static Supplier getSupplierForID(Connection cxn, String ID) throws Exception {

        //load existing package

        return dbload(cxn, ID);

    }


    public static Supplier getSupplierForID(String ID) throws Exception {

        //load existing package

        Connection cxn = null;


        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            Supplier idStr = getSupplierForID(cxn, ID);

            cxn.commit();

            return idStr;

        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                cxn.close();
            } catch (Exception e) {
            }

        }


    }


    public boolean isModified() {

        return needsUpdate;

    }


    public boolean isNew() {

        if (0 == (supplier_id))

            return true;


        return false;

    }




    public void dbsave(Connection cxn) throws Exception {


        PreparedStatement ps = null;

        ResultSet rs = null;


        try {


            ps = cxn.prepareStatement(createStatement);


            ps.setString(1, com.owd.core.OWDUtilities.getCurrentUserName());

            ps.setString(2, com.owd.core.OWDUtilities.getSQLDateTimeForToday());

            ps.setString(3, com.owd.core.OWDUtilities.getCurrentUserName());

            ps.setString(4, com.owd.core.OWDUtilities.getSQLDateTimeForToday());


            ps.setString(5, supp_name);

            ps.setString(6, supp_contact);

            ps.setString(7, supp_email);

            ps.setString(8, supp_phone);

            ps.setString(9, supp_fax);

            ps.setString(10, supp_company);

            ps.setString(11, supp_addr_one);

            ps.setString(12, supp_addr_two);

            ps.setString(13, supp_city);

            ps.setString(14, supp_state);

            ps.setInt(15, client_fkey);

            ps.setString(16, supp_zip);

            ps.setString(17, supp_site);


            int rowsUpdated = ps.executeUpdate();


            if (rowsUpdated < 1)

                throw new Exception("Could not save supplier!");


            ps.close();


            ps = cxn.prepareStatement("select @@IDENTITY");


            ps.executeQuery();

            rs = ps.getResultSet();

            if (rs.next()) {

                supplier_id = rs.getInt(1);

            }

            rs.close();

            ps.close();


        } catch (Exception ex) {


            ex.printStackTrace();

            throw ex;

        } finally {

            try {
                rs.close();
            } catch (Exception exc) {
            }

            try {
                ps.close();
            } catch (Exception exc) {
            }


        }


    }


    public void dbdelete(Connection cxn) throws Exception {

        /*

            if (isNew())

            {

                //do nothing

            }else{

                PreparedStatement stmt = null;

                ResultSet rs = null;

                try

                {

                    int dependents = 0;

                    stmt = cxn.prepareStatement(deleteCheckStatement);

                    stmt.setInt(1, inventory_id);



                    stmt.executeQuery();



                    rs = stmt.getResultSet();

                    while(rs.next())

                    {

                        dependents+= rs.getInt(1);

                    }



                    rs.close();





                    stmt.close();

                    if (dependents>0)

                        throw new Exception("Can't delete this item; it is being used by other records");







                    stmt = cxn.prepareStatement(deleteStatement1);

                    stmt.setInt(1, inventory_id);



                    int rowsUpdated = stmt.executeUpdate();



                    if (rowsUpdated < 1) throw new Exception("Cannot delete inventory availability ID "+supplier_id);



                    stmt.close();



                    stmt = cxn.prepareStatement(deleteStatement2);

                    stmt.setInt(1, inventory_id);

                    stmt.setInt(2, client_fkey);



                    rowsUpdated = stmt.executeUpdate();



                    if (rowsUpdated < 1) throw new Exception("Cannot delete inventory ID "+supplier_id);



                    stmt.close();



                }catch(Exception ex)

                  {

                      throw ex;

                  }finally

                  {

                      try{ rs.close(); }catch(Exception exc){}

                      try{ stmt.close(); }catch(Exception exc){}

                  }



            }

            */

    }


    public void dbupdate(Connection cxn) throws Exception {

        ////////log.debug("Supplier.dbupdate");

        if (isNew()) {

            dbsave(cxn);

        } else {

            PreparedStatement ps = null;

            try {

                ps = cxn.prepareStatement(updateStatement1);

                ps.setString(1, com.owd.core.OWDUtilities.getCurrentUserName());

                ps.setString(2, com.owd.core.OWDUtilities.getSQLDateTimeForToday());


                ps.setString(3, supp_name);

                ps.setString(4, supp_contact);

                ps.setString(5, supp_email);

                ps.setString(6, supp_phone);

                ps.setString(7, supp_fax);

                ps.setString(8, supp_company);

                ps.setString(9, supp_addr_one);

                ps.setString(10, supp_addr_two);

                ps.setString(11, supp_city);

                ps.setString(12, supp_state);


                ps.setString(13, supp_zip);

                ps.setString(14, supp_site);


                ps.setInt(15, supplier_id);

                ps.setInt(16, client_fkey);


                int rowsUpdated = ps.executeUpdate();


                if (rowsUpdated < 1)

                    throw new Exception("Could not update supplier id " + supplier_id);


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


    private static Supplier dbload(Connection cxn, String id) throws Exception {


        PreparedStatement stmt = null;

        Supplier pack = null;


        try {

            stmt = cxn.prepareStatement(loadStatement);

            stmt.setInt(1, new Integer(id).intValue());

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();

            if (rs.next()) {

                pack = new Supplier(id,

                        rs.getString(kdbSupplierClientFkey),

                        rs.getString(kdbSupplierName),

                        rs.getString(kdbSupplierContact),

                        rs.getString(kdbSupplierEmail),

                        rs.getString(kdbSupplierPhone),

                        rs.getString(kdbSupplierFax),

                        rs.getString(kdbSupplierCompany),

                        rs.getString(kdbSupplierAddr1),

                        rs.getString(kdbSupplierAddr2),

                        rs.getString(kdbSupplierCity),

                        rs.getString(kdbSupplierState),

                        rs.getString(kdbSupplierZip),

                        rs.getString(kdbSupplierSite),

                        rs.getString(kdbSupplierCreatedBy),

                        rs.getString(kdbSupplierCreatedOn),

                        rs.getString(kdbSupplierModifiedBy),

                        rs.getString(kdbSupplierModifiedOn));

            } else {

                throw new Exception("Could not load supplier!");

            }


        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                stmt.close();
            } catch (Exception exc) {
            }


        }


        return pack;

    }


    public static Supplier dbloadByName(String part, String clientID) throws Exception {

        Connection cxn = null;


        try {

            cxn = com.owd.core.managers.ConnectionManager.getConnection();

            return dbloadByName(cxn, part, clientID);

        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                cxn.close();
            } catch (Exception e) {
            }

        }

    }


    public static Supplier dbloadByName(Connection cxn, String name, String clientID) throws Exception {


        PreparedStatement stmt = null;

        Supplier pack = null;


        try {

            stmt = cxn.prepareStatement(loadByNameStatement);

            stmt.setString(1, name);

            stmt.setInt(2, new Integer(clientID).intValue());

            stmt.executeQuery();

            ResultSet rs = stmt.getResultSet();

            if (rs.next()) {

                pack = new Supplier(rs.getString(kdbSupplierPKName),

                        rs.getString(kdbSupplierClientFkey),

                        name,

                        rs.getString(kdbSupplierContact),

                        rs.getString(kdbSupplierEmail),

                        rs.getString(kdbSupplierPhone),

                        rs.getString(kdbSupplierFax),

                        rs.getString(kdbSupplierCompany),

                        rs.getString(kdbSupplierAddr1),

                        rs.getString(kdbSupplierAddr2),

                        rs.getString(kdbSupplierCity),

                        rs.getString(kdbSupplierState),

                        rs.getString(kdbSupplierZip),

                        rs.getString(kdbSupplierSite),

                        rs.getString(kdbSupplierCreatedBy),

                        rs.getString(kdbSupplierCreatedOn),

                        rs.getString(kdbSupplierModifiedBy),

                        rs.getString(kdbSupplierModifiedOn));

            } else {

                throw new Exception("Could not load supplier!");

            }


        } catch (Exception ex) {

            throw ex;

        } finally {

            try {
                stmt.close();
            } catch (Exception exc) {
            }


        }


        return pack;

    }


}
