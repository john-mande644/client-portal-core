package com.owd.extranet.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.Mailer;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;


public class SupplierManager extends ExtranetManager

{
private final static Logger log =  LogManager.getLogger();


    public static final String kParamSupplierMgrAction = "Supmgr";


    public static final String kParamSupplierExploreAction = "explore";


    public static final String kParamSupplierCreateAction = "new";

    public static final String kParamSupplierEditAction = "edit";

    public static final String kParamSupplierFindAction = "find";

    public static final String kParamSupplierSaveAction = "save";

    public static final String kParamSupplierDeleteAction = "delete";
    
    public static final String kParamSupplierVoidAction = "supvoid";


    public static final String kParamSupplierQuickFind = "qf";


    public static final String kparamSupplierID = "oid";

    public static final String kCurrentFinder = "currSupplierFinder";

    public static final String kCurrentSupplier = "currSupplierStatus";


    public String getManagerMenus(HttpServletRequest req)

    {

        StringBuffer sb = new StringBuffer();

        sb.append("<UL><LI><A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamSupplierMgrAction + "=" + kParamSupplierExploreAction + "\" >");

        sb.append("Explore Suppliers");

        sb.append("<LI><A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + kParamSupplierMgrAction + "=" + kParamSupplierExploreAction + "\" >");

        sb.append("New Supplier</UL> ");


        return sb.toString();

    }


    public String getCurrentAction(HttpServletRequest req)

    {

        String currAction = ExtranetServlet.getStringParam(req, kParamSupplierMgrAction, kParamSupplierExploreAction);

        if (currAction.equals(kParamSupplierExploreAction))

        {

            return "Searching Suppliers...";

        } else if (currAction.equals(kParamSupplierCreateAction))

        {

            return "Creating New Supplier";

        } else if (currAction.equals(kParamSupplierEditAction))

        {

            return "Editing Supplier";

        } else if (currAction.equals(kParamSupplierFindAction))

        {

            return "Suppliers Search Results";

        }


        return "";


    }


    public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException

    {


        String currAction = ExtranetServlet.getStringParam(req, kParamSupplierMgrAction, kParamSupplierExploreAction);


        //do real work here


        ////log.debug("supmgr action "+currAction);

        if (currAction.equals(kParamSupplierExploreAction))

        {

            try

            {

                req.setAttribute(kCurrentFinder, SupplierFinder.parseRequest(req, resp));


            } catch (Exception ex)

            {

                ex.printStackTrace();

                Mailer.postMailMessage("Explore Suppliers error", ex.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex), "noop@owd.com", "noop@owd.com");

                req.setAttribute("formerror", ex.getMessage());


            }

            try {
                req.getRequestDispatcher("findsupplier.jsp").include(req, resp);
            } catch (Exception ex2)

            {

                ex2.printStackTrace();

                Mailer.postMailMessage("Explore Suppliers redirect error", ex2.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex2), "noop@owd.com", "noop@owd.com");

            }


        } else if (currAction.equals(kParamSupplierCreateAction))

        {

            try

            {

                req.setAttribute(kCurrentSupplier, new com.owd.core.business.order.Supplier(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID)));


            } catch (Exception ex)

            {

                ex.printStackTrace();

                Mailer.postMailMessage("Create Suppliers error", ex.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex), "noop@owd.com", "noop@owd.com");

                req.setAttribute("formerror", ex.getMessage());


            }

            try {
                req.getRequestDispatcher("editsupplier.jsp").include(req, resp);
            } catch (Exception ex2)

            {

                ex2.printStackTrace();

                Mailer.postMailMessage("Create Suppliers redirect error", ex2.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex2), "noop@owd.com", "noop@owd.com");

            }


        } else if (currAction.equals(kParamSupplierEditAction))

        {

            try

            {

                req.setAttribute(kCurrentSupplier,

                        Supplier.getSupplierForID(ExtranetServlet.getIntegerParam(req, kparamSupplierID, 0) + ""));


            } catch (Exception ex)

            {

                ex.printStackTrace();

                Mailer.postMailMessage("Edit Suppliers error", ex.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex), "noop@owd.com", "noop@owd.com");

                req.setAttribute("formerror", ex.getMessage());


            }

            try {
                req.getRequestDispatcher("editsupplier.jsp").include(req, resp);
            } catch (Exception ex2)

            {

                ex2.printStackTrace();

                Mailer.postMailMessage("Edit Suppliers redirect error", ex2.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex2), "noop@owd.com", "noop@owd.com");

            }


        } else if (currAction.equals(kParamSupplierDeleteAction))

        {

            Connection cxn = null;


            try

            {

                cxn = com.owd.core.managers.ConnectionManager.getConnection();


                Supplier supp = Supplier.getSupplierForID(ExtranetServlet.getIntegerParam(req, kparamSupplierID, 0) + "");
                if (supp != null) {
                    supp.dbdelete(cxn);
                }


                cxn.commit();

                ////log.debug("committed");
                req.setAttribute(kCurrentFinder, SupplierFinder.parseRequest(req, resp));

                try {
                    req.getRequestDispatcher("findsupplier.jsp").include(req, resp);
                } catch (Exception ex2)

                {

                    ex2.printStackTrace();

                    Mailer.postMailMessage("Explore Suppliers redirect error", ex2.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex2), "noop@owd.com", "noop@owd.com");

                }
            } catch (Exception ex)

            {

                try {
                    cxn.rollback();
                } catch (Exception e) {
                }

                ex.printStackTrace();

                Mailer.postMailMessage("Delete Supplier error", ex.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex), "noop@owd.com", "noop@owd.com");

                req.setAttribute("formerror", ex.getMessage());

                try {
                    req.getRequestDispatcher("editsupplier.jsp").include(req, resp);
                } catch (Exception ex2)

                {

                    ex2.printStackTrace();

                    Mailer.postMailMessage("Delete Supplier redirect error", ex2.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex2), "noop@owd.com", "noop@owd.com");

                }


            } finally

            {

                try {
                    cxn.close();
                } catch (Exception e) {
                }

            }


        } else if (currAction.equals(kParamSupplierSaveAction))

        {


            Supplier item = null;

            Connection cxn = null;


            try

            {

                cxn = com.owd.core.managers.ConnectionManager.getConnection();

                ////log.debug("in Supplier save");

                if (ExtranetServlet.getStringParam(req, Supplier.kdbSupplierPKName, "0").equals("0"))

                {

                    //create new and save

                    item = new Supplier(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

                } else

                {

                    //update current and save

                    item = com.owd.core.business.order.Supplier.getSupplierForID(cxn, ExtranetServlet.getStringParam(req, Supplier.kdbSupplierPKName, "0"));

                }

                req.setAttribute(kCurrentSupplier, item);

                ////log.debug("got item");

                item.supp_name = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierName, "");

                item.supp_fax = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierFax, "");

                item.supp_contact = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierContact, "");

                item.supp_email = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierEmail, "");

                item.supp_phone = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierPhone, "");

                item.supp_company = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierCompany, "");

                item.supp_addr_one = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierAddr1, "");

                item.supp_addr_two = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierAddr2, "");

                item.supp_city = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierCity, "");

                item.supp_zip = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierZip, "");

                item.supp_site = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierSite, "");

                item.supp_state = ExtranetServlet.getStringParam(req, Supplier.kdbSupplierState, "");


                item.dbupdate(cxn);

                ////log.debug("saved");

                cxn.commit();

                ////log.debug("committed");

                req.setAttribute("formerror", "Supplier saved!");


            } catch (Exception ex)

            {

                try {
                    cxn.rollback();
                } catch (Exception e) {
                }

                ex.printStackTrace();

                Mailer.postMailMessage("Save Supplier error", ex.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex), "noop@owd.com", "noop@owd.com");

                req.setAttribute("formerror", "Error saving Supplier: " + ex.getMessage());


            } finally

            {

                try {
                    cxn.close();
                } catch (Exception e) {
                }

            }


            try {
                req.getRequestDispatcher("editsupplier.jsp").include(req, resp);
            } catch (Exception ex2)

            {

                ex2.printStackTrace();

                Mailer.postMailMessage("Save Supplier redirect error", ex2.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex2), "noop@owd.com", "noop@owd.com");

            }


        } else

        {

            req.setAttribute("formerror", "Request not recognized");

            try {
                req.getRequestDispatcher("findsupplier.jsp").include(req, resp);
            } catch (Exception ex2)

            {

                ex2.printStackTrace();


                Mailer.postMailMessage("Suppliers catch error", ex2.getMessage() + "\n" + OWDUtilities.getStackTraceAsString(ex2), "noop@owd.com", "noop@owd.com");

            }

        }


    }


    public String[] getManagerActions(HttpServletRequest req)

    {

        String[] urls = {"", ""};


        urls[0] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + ExtranetServlet.kParamAdminAction+"="+ExtranetServlet.kParamChangeMgrAction +"&"+ ExtranetServlet.kParamChangeMgrTo+"="+ ExtranetManager.kSuppliersMgrName+"&"+kParamSupplierMgrAction + "=" + kParamSupplierExploreAction + "\" >Search</A>";

        urls[1] = "<A HREF=\"" + req.getContextPath() + ExtranetServlet.kExtranetURLPath + "?" + ExtranetServlet.kParamAdminAction+"="+ExtranetServlet.kParamChangeMgrAction +"&"+ ExtranetServlet.kParamChangeMgrTo+"="+ ExtranetManager.kSuppliersMgrName+"&"+kParamSupplierMgrAction + "=" + kParamSupplierCreateAction + "\" >Create New Supplier</A>";


        return urls;

    }


    public void getManagerHeader(HttpServletRequest req, HttpServletResponse resp) throws IOException

    {


        PrintWriter out = resp.getWriter();

        out.write("<TD VALIGN=\"TOP\">");

    }

    public void getManagerFooter(HttpServletRequest req, HttpServletResponse resp) throws IOException

    {


        PrintWriter out = resp.getWriter();

        out.write("</TD></TR>");

        out.write("</TABLE>");

    }

}
