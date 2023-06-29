package com.owd.callcenter.calls;

import com.owd.hibernate.HibernateSession;
import com.owd.callcenter.beans.selectList;
import com.owd.callcenter.forms.calls.callSearchForm;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 2, 2006
 * Time: 4:38:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class callSearchAction extends LookupDispatchAction {


    protected Map getKeyMethodMap() {
        System.out.println("getKeyMethodMap invoked..");
        Map map = new HashMap();
        map.put("button.getCall", "getCall");
        map.put("button.callSearch", "search");
        map.put("button.callFilter", "filter");

        return map;
    }

    public ActionForward getCall(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            callSearchForm csf = (callSearchForm) form;
            System.out.println("lalslsllsallldlalldlldlalldla");
            callUtil.downloadCall(response, csf.getCallId(), HibernateSession.currentSession());
              System.out.println("after xxxxxxxxxxxxxxxxxxxxxxxxxxx");

            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return mapping.findForward("error");

        }
    }

    public ActionForward search(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
            throws Exception {
        try {
         
            System.out.println(request.getUserPrincipal());
            request.getSession(true).setAttribute("callsearch1",null);
            request.getSession(true).setAttribute("columlist1",null);
            callSearchForm csf = (callSearchForm) form;
            csf.setNotes("");
             csf.setAgent("");
                csf.setCampaign("");
            if(!csf.getDate().equals(csf.getPrevDate()) && !("null".equals(csf.getPrevDate())&&(null==request.getSession(true).getAttribute("callsearch"))==false)){
            csf.setPrevDate(csf.getDate());

            System.out.println("inCall Search");
            PreparedStatement stat =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement("select agent_id, campaign, contact_id, contact_minutes, contact_initiated,contact_outcome as 'Outcome',Result,Reason  from dbo.cc_cl_contacts where \n" +
                    "contact_initiated_date = ? and contact_type = 'VOICE' and contact_minutes > 0");
            stat.setString(1, csf.getDate() + " 00:00:00");
            ResultSet rs = stat.executeQuery();

            int cols = rs.getMetaData().getColumnCount();
            List colList = new ArrayList();

            for (int i = 1; i <= cols; i++) {
                selectList sl = new selectList();
                sl.setAction(rs.getMetaData().getColumnName(i).toString());
                colList.add(sl);
            }
            PreparedStatement stat1 =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(" select DISTINCT agent_id  from dbo.cc_cl_contacts where \n" +
                    "contact_initiated_date = ? and contact_type = 'VOICE' and contact_minutes > 0 order by agent_id ");
            stat1.setString(1,csf.getDate() + " 00:00:00");
            ResultSet rs1 = stat1.executeQuery();
            csf.setAgents(callUtil.loadSingleSelctList(rs1));

            PreparedStatement stat2 =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(" select DISTINCT campaign  from dbo.cc_cl_contacts where \n" +
                    "contact_initiated_date = ? and contact_type = 'VOICE' and contact_minutes > 0 order by campaign ");
            stat2.setString(1,csf.getDate() + " 00:00:00");
            ResultSet rs2 = stat2.executeQuery();
            csf.setCampaigns(callUtil.loadSingleSelctList(rs2));

            System.out.println("getting dynaset");
            RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
            System.out.println("got dynaset");
           
            request.getSession(true).setAttribute("callsearch", displayrsc);
            request.getSession(true).setAttribute("columlist", colList);
            } else{

                System.out.println("using previos data");
            }
            //todo rest agent adn campaign
            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return mapping.findForward("error");

        }
    }

   public ActionForward filter(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            callSearchForm csf = (callSearchForm) form;
            System.out.println("Filtering result set");
           StringBuffer sql = new StringBuffer();
                 sql.append( "select agent_id, campaign, contact_id, contact_minutes, contact_initiated, convert(varchar,notes) as Notes, contact_outcome as 'Outcome', Result, Reason from dbo.cc_cl_contacts where \n" +
                   "contact_initiated_date = ?" +
                   " and contact_type = 'VOICE' " +
                   "and contact_minutes > 0 ");
            if(!csf.getCampaign().equals("all")){
                sql.append("and campaign = ?");
            }
            if(!csf.getAgent().equals("all")) {
                sql.append(" and agent_id = ?");
            }
           if(!csf.getNotes().equals("")){
               sql.append(" and notes like '%"+csf.getNotes()+"%'");
           }
            sql.append(" order by contact_id");
            System.out.println(sql);
           PreparedStatement stat =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(sql.toString());
            stat.setString(1, csf.getDate() + " 00:00:00");
            if(!csf.getCampaign().equals("all")){
                stat.setString(2,csf.getCampaign());
            }
            if(!csf.getAgent().equals("all")){
                if(csf.getCampaign().equals("all")){
                    stat.setString(2,csf.getAgent());
                }else{
                    stat.setString(3,csf.getAgent());
                }

            }

            ResultSet rs = stat.executeQuery();

            int cols = rs.getMetaData().getColumnCount();
            List colList = new ArrayList();

            for (int i = 1; i <= cols; i++) {
                selectList sl = new selectList();
                sl.setAction(rs.getMetaData().getColumnName(i).toString());
                colList.add(sl);
            }

            RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
          

            request.getSession(true).setAttribute("callsearch1", displayrsc);
            request.getSession(true).setAttribute("columlist1", colList);


            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return mapping.findForward("error");

        }
    }
}



