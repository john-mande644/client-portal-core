package com.owd.callcenter;

import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.commons.beanutils.RowSetDynaClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.owd.callcenter.forms.caseSearchForm;
import com.owd.callcenter.calls.callUtil;
import com.owd.callcenter.beans.selectList;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdClient;
import org.hibernate.engine.spi.SessionImplementor;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 2, 2006
 * Time: 4:38:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class caseSearchAction extends LookupDispatchAction {


    protected Map getKeyMethodMap() {
        System.out.println("getKeyMethodMap invoked..");
        Map map = new HashMap();
  
        map.put("button.caseSearch", "search");

        return map;
    }



    public ActionForward search(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
            throws Exception {
        try {

            System.out.println(request.getUserPrincipal());
            request.getSession(true).setAttribute("casesearch",null);
            request.getSession(true).setAttribute("columnlist",null);
            caseSearchForm csf = (caseSearchForm) form;
           // csf.setSearchText("");
          //  csf.setClient("");


            System.out.println("in Case Search");
            PreparedStatement stat = null;
            String searchSql = "select top 100 '<A target=\"_case\" HREF=\"http://service.owd.com/casetracker/default.asp?'+sTicket+'\">'+sTicket+'</A>' as link,case when fOpen=0 then 'Closed' else 'Active' end as Status,company_name as 'Client',dtOpened,scustomeremail,stitle as 'Subject',\n" +
                    "slatesttextsummary as 'Last Note' \n" +
                    " from vw_cases where stitle like ?";

            if(!(csf.getClient().equals("all")))
            {
                searchSql += " and company_name=?";
            }
                  searchSql += " order by fOpen desc, dtOpened desc";
            stat =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(searchSql);
            stat.setString(1, "%"+csf.getSearchText() + "%");
             if(!(csf.getClient().equals("all")))
            {
                stat.setString(2, csf.getClient());
            }

            ResultSet rs = stat.executeQuery();

            int cols = rs.getMetaData().getColumnCount();
            List colList = new ArrayList();

            for (int i = 1; i <= cols; i++) {
                selectList sl = new selectList();
                sl.setAction(rs.getMetaData().getColumnName(i).toString());
                colList.add(sl);
            }


            PreparedStatement stat2 =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(" select DISTINCT company_name  from dbo.cc_cl_contacts cc (NOLOCK) join owd_client c (NOLOCK) on\n" +
                    "                     c.client_id=cc.client_id where \n" +
                    "                    is_active=1 order by company_name  ");

            ResultSet rs2 = stat2.executeQuery();
            csf.setClients(callUtil.loadSingleSelctList(rs2));
            ((selectList)(csf.getClients().get(0))).setDisplay("Any Client");

            System.out.println("getting dynaset");
            RowSetDynaClass displayrsc = new RowSetDynaClass(rs, false);
            System.out.println("got dynaset");

            request.getSession(true).setAttribute("casesearch", displayrsc);
            request.getSession(true).setAttribute("columnlist", colList);


            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            return mapping.findForward("error");

        }
    }

    public ActionForward unspecified(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
              throws Exception {
          try {

            request.getSession(true).setAttribute("casesearch",null);
            request.getSession(true).setAttribute("columnlist",null);
                  caseSearchForm csf = (caseSearchForm) form;
                     PreparedStatement stat2 =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(" select DISTINCT company_name  from dbo.cc_cl_contacts cc (NOLOCK) join owd_client c (NOLOCK) on\n" +
                    "                     c.client_id=cc.client_id where \n" +
                    "                    is_active=1 order by company_name  ");

            ResultSet rs2 = stat2.executeQuery();
            csf.setClients(callUtil.loadSingleSelctList(rs2));
            ((selectList)(csf.getClients().get(0))).setDisplay("Any Client");
           if (csf.getClientId() != null){
               try{
                   OwdClient client = (OwdClient) HibernateSession.currentSession().load(OwdClient.class,Integer.valueOf(csf.getClientId()));
                   csf.setClient(client.getCompanyName());
               } catch(Exception e){
                   e.printStackTrace();

               }
           }
              csf.setSearchText("");
          //  csf.setClient("all");

              return (mapping.findForward("success"));
          } catch (Exception e) {
              e.printStackTrace();
              request.setAttribute("error", e.getMessage());
              return mapping.findForward("error");

          }
      }

}
