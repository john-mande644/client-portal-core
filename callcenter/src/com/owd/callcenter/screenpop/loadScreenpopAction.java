package com.owd.callcenter.screenpop;

import com.owd.callcenter.beans.selectList;
import com.owd.callcenter.forms.spForm;
import com.owd.hibernate.HibernateSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.engine.spi.SessionImplementor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Mar 1, 2007
 * Time: 4:14:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class loadScreenpopAction extends Action {
    public ActionForward execute(ActionMapping mapping,
                                 ActionForm form,
                                 HttpServletRequest request,
                                 HttpServletResponse response)
            throws Exception {
        try {
            spForm sf = (spForm) form;
            System.out.println("testeing");
            System.out.println(sf.getAgentfname());
            System.out.println("pritned name");

            //load info
            String sql = "select * from owd_client_callcenter where Mlog_campaign_name=?";
            PreparedStatement stmt =  ((SessionImplementor)HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection().prepareStatement(sql);
            stmt.setString(1, sf.getCampaign());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                sf.setScript(rs.getString("announce_script"));
                // do_entry = rs.getInt("do_entry");
                // do_status = rs.getInt("do_status");
                // do_service = rs.getInt("do_service");
                sf.setClientFkey(rs.getInt("client_fkey") + "");
                sf.setUrlEntry(rs.getString("url_entry"));
                sf.setUrlStatus(rs.getString("url_status"));
                sf.setUrlService(rs.getString("url_service"));
                System.out.println("kb");
                sf.setKb(rs.getString("url_kb_base"));
                System.out.println("qf");
                //   do_orderref_entry = rs.getInt("do_orderref_entry");
                sf.setQuickRef(rs.getString("url_quick_ref"));
                System.out.println("w");
                sf.setDoScript(rs.getInt("script") + "");
                System.out.println("before all");
                sf.setScript(StringUtils.replace(sf.getScript(), "<AGENT_NAME>", sf.getAgentfname()));
                //  sf.setScript(sf.getScript().replaceAll("<AGENT_NAME>",sf.getAgentfname()));
                sf.setScript(StringUtils.replace(sf.getScript(), "<CALL_ID>", sf.getCallId()));
                sf.setScript(StringUtils.replace(sf.getScript(), "<AGENT_ID>", sf.getAgentid()));

                sf.setUrlEntry(StringUtils.replace(sf.getUrlEntry(), "<AGENT_NAME>", sf.getAgentfname()));
                sf.setUrlEntry(StringUtils.replace(sf.getUrlEntry(), "<CALL_ID>", sf.getCallId()));
                if (sf.getUrlEntry().indexOf("members.ordermotion.com/en/hyper_login.asp") >= 0) {
                    sf.setUrlEntry(StringUtils.replace(sf.getUrlEntry(), "<AGENT_ID>", trim(sf.getAgentid())));
                } else {
                    sf.setUrlEntry(StringUtils.replace(sf.getUrlEntry(), "<AGENT_ID>", sf.getAgentid()));
                }
                System.out.println("middle");
                sf.setUrlStatus(StringUtils.replace(sf.getUrlStatus(), "<AGENT_NAME>", sf.getAgentfname()));
                sf.setUrlStatus(StringUtils.replace(sf.getUrlStatus(), "<CALL_ID>", sf.getCallId()));
                if (sf.getUrlService().indexOf("members.ordermotion.com/en/hyper_login.asp") >= 0) {
                    sf.setUrlService(StringUtils.replace(sf.getUrlEntry(), "<AGENT_ID>", trim(sf.getAgentid())));
                } else {
                    sf.setUrlService(StringUtils.replace(sf.getUrlService(), "<AGENT_ID>", sf.getAgentid()));
                }
                sf.setUrlService(StringUtils.replace(sf.getUrlService(), "<AGENT_NAME>", sf.getAgentfname()));
                sf.setUrlService(StringUtils.replace(sf.getUrlService(), "<CALL_ID>", sf.getCallId()));

                System.out.println("where are we");
                String ex[] = sf.getScript().split("<EXTRA\\|");
                System.out.println(ex.length);
                if (ex.length > 1) {
                    System.out.println("Dooing extra stuff, yeah for progrss");
                    sf.setScript(ex[0]);
                    List transfer = new ArrayList();
                    for (int i = 1; i < ex.length; i++) {
                        System.out.println("Doing  " + i + " xxxxxxxxxx");
                        String parts[] = ex[i].split(">");
                        String title[] = parts[0].split(":");
                        System.out.println(parts[0]);
                        parts[1] = StringUtils.replace(parts[1], "&br&", "<br>");
                        System.out.println(parts[1]);
                        selectList sl = new selectList();
                        sl.setAction(title[0]);
                        sl.setDisplay(parts[1]);
                        if (title.length > 1) {
                            sl.setColor(title[1]);
                            System.out.println("success");
                        } else {
                            sl.setColor("black");
                            System.out.println("default to black");
                        }
                        transfer.add(i - 1, sl);
                    }
                    sf.setExtras(transfer);
                }


            } else {
                sf.setScript(sf.getCampaign() + " is not a valid Campaign setup for screenpops");
            }

            return (mapping.findForward("success"));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errors", e.getMessage());
            return mapping.findForward("error");

        }
    }

    public static String trim(String s) {
        if (s.length() > 10) {
            s = s.substring(0, 10);
        }
        return s;
    }
}
