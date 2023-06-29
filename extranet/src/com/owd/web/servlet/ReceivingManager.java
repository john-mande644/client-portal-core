package com.owd.web.servlet;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;


public class ReceivingManager extends ExtranetManager {
private final static Logger log =  LogManager.getLogger();


    public static final String kParamRcvMgrAction = "rcvmgr";


    public static final String kParamRcvExploreAction = "explore";

    public static final String kParamRcvCreateAction = "new";

    public static final String kParamRcvSaveAction = "save";

    public static final String kParamASNCreateAction = "newasn";

    public static final String kParamRcvEditAction = "edit";

    public static final String kParamRcvFindAction = "find";

    public static final String kParamRcvFindAdvancedAction = "findadv";


    public static final String kCurrentReceiveName = "currentReceiveObjectName";


    public static final String kParamRcvQuickFind = "qf";

    public static final String kparamRcvID = "rid";


    public static final String kReceiveType = "Receive";

    public static final String kNewASNType = "Pending ASN";

    public static final String kReceiveASNType = "Received ASN";

    public static final String kUnknownType = "Unknown";


    public String[] getManagerCreators(HttpServletRequest req) {

        String[] urls = {"", ""};


        urls[0] = "<A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamRcvMgrAction + "=" + kParamRcvCreateAction + "\" >New Receive (no ASN)</A>";

        urls[1] = "<A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamRcvMgrAction + "=" + kParamASNCreateAction + "\" >New Ship Notice (ASN)</A>";


        return urls;

    }


    public String[] getManagerActions(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<A class=\"command\" HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamRcvMgrAction + "=" + kParamRcvExploreAction + "\" >Explore Receives</A>";


        return urls;

    }


    public String[] getManagerQueries(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<A HREF=\"" + ExtranetServlet.kExtranetURLPath + "?" + kParamRcvMgrAction + "=" + kParamASNCreateAction + "\" >Pending ASNs</A>";


        return urls;

    }


    public String[] getManagerReports(HttpServletRequest req) {

        String[] urls = {""};


        urls[0] = "<A HREF=" + ExtranetServlet.kExtranetURLPath + "?xx=fg>Reporting Preferences</A>";


        return urls;

    }


    public String getCurrentAction(HttpServletRequest req) {

        String currAction = ExtranetServlet.getStringParam(req, kParamRcvMgrAction, kParamRcvExploreAction);

        if (currAction.equals(kParamRcvExploreAction)) {

            return "Exploring Receives";

        } else if (currAction.equals(kParamRcvCreateAction)) {

            return "New Receiving Event";

        } else if (currAction.equals(kParamASNCreateAction)) {

            return "Creating New Advance Ship Notice (ASN)";

        } else if (currAction.equals(kParamRcvEditAction)) {

            return "Editing Receive/ASN";

        } else if (currAction.equals(kParamRcvFindAction)) {

            return "Receiving Search Results";

        }


        return "";


    }


    public void getManagerContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        String currAction = ExtranetServlet.getStringParam(req, kParamRcvMgrAction, kParamRcvExploreAction);


        if (currAction.equals(kParamRcvExploreAction) | currAction.equals(kParamRcvFindAction)) {


        }



        //do real work here





        if (currAction.equals(kParamRcvExploreAction)) {
            try {
                req.getRequestDispatcher(ExtranetServlet.kExtranetJSPPath + "findreceive.jsp").include(req, resp);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (currAction.equals(kParamRcvFindAction)) {

            String qfstr = ExtranetServlet.getStringParam(req, kParamRcvQuickFind, "");


            if (qfstr.equals("")) {

                if (false) //is advanced
                {


                } else {

                    //no criteria

                    showTable(req, resp, null, "(No search specified, showing all...)");

                }


            } else {

                String[] crits = {"(supplier like \'%" + qfstr + "%\' OR description like \'%" + qfstr + "%\')"};



                //if quickfind

                showTable(req, resp, crits, "Find items with Reference or Description containing " + qfstr);


            }


        } else if (currAction.equals(kParamRcvEditAction)) {

            Connection cxn = null;


            try {


                cxn = com.owd.core.managers.ConnectionManager.getConnection();

                ////log.debug("getting rcv id="+ExtranetServlet.getIntegerParam(req,kparamRcvID,0));

                com.owd.core.business.order.Receive rcv = com.owd.core.business.order.Receive.getReceiveForID(cxn, ExtranetServlet.getIntegerParam(req, kparamRcvID, 0) + "");

                setEditState(rcv, req);

                getEditForm(rcv, req, resp);

            } catch (Exception ex) {


                ex.printStackTrace();

                //////log.debug(ex.getMessage());

            } finally {

                try {
                    cxn.close();
                } catch (Exception ex) {
                }

            }


        } else if (currAction.equals(kParamRcvCreateAction) || currAction.equals(kParamASNCreateAction)) {

            Connection cxn = null;


            try {

                cxn = com.owd.core.managers.ConnectionManager.getConnection();

                com.owd.core.business.order.Receive rcv = new com.owd.core.business.order.Receive(ExtranetServlet.getSessionString(req, ExtranetServlet.kExtranetClientID));

                if (currAction.equals(kParamASNCreateAction))

                    rcv.type = kNewASNType;

                else

                    rcv.type = kReceiveType;


                rcv.setEditable(true);

                getEditForm(rcv, req, resp);

            } catch (Exception ex) {


                ex.printStackTrace();

                log.debug(ex.getMessage());

            } finally {

                try {
                    cxn.close();
                } catch (Exception ex) {
                }

            }


        }


    }


    private void setEditState(com.owd.core.business.order.Receive rcv, HttpServletRequest req) {

        rcv.setEditable(true);


        if (rcv.is_void.equals("1")) {
            rcv.setEditable(false);
            return;
        } else {

            if (!rcv.type.equals(kNewASNType)) {
                rcv.setEditable(false);
                return;
            }

        }

    }

    public void showTable(HttpServletRequest req, HttpServletResponse resp, String[] criteria, String description) throws IOException {


        WebTable table = new WebTable();

		

	

/*

		table.setSQLDefs(tableColumnNames,tableColumnDefs,tableLinkStarts,tableLinkMids,tableLinkEnds,tableFromStmt);

					

		table.addCriterium("r.client_fkey="+ExtranetServlet.getSessionString(req,ExtranetServlet.kExtranetClientID));

		//table.addCriterium("r.is_void=0 ");

		

		if(criteria!=null)

		{

			for(int i=0;i<criteria.length;i++)

			{

				table.addCriterium(criteria[i]);

			}

		}

		table.setDescription((description==null?"Showing All...":description));

			//check for find criteria

		

		table.setPageNum(ExtranetServlet.getIntegerParam(req,WebTable.kTableShowPage,1));

		table.setOrderCol(ExtranetServlet.getIntegerParam(req,WebTable.kTableSortColumn,-1));

		

		table.getTable(req,resp);

		*/


    }

    public void getManagerHeader(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        PrintWriter out = resp.getWriter();

        out.write("<TD VALIGN=\"TOP\">");

    }

    public void getManagerFooter(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        PrintWriter out = resp.getWriter();

        out.write("</TD></TR>");

        out.write("</TABLE>");

    }


    public void getEditForm(com.owd.core.business.order.Receive receive, HttpServletRequest req, HttpServletResponse resp) throws Exception {


        RequestDispatcher rd = req.getRequestDispatcher("receive_editor.jsp");

        req.setAttribute(kCurrentReceiveName, receive);

        rd.include(req, resp);


    }

}
