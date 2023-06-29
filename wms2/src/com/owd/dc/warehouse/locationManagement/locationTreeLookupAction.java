package com.owd.dc.warehouse.locationManagement;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.dc.beans.jsonTreeBean;
import com.owd.dc.beans.treeBean;
import com.owd.dc.warehouse.locationManagement.Utilities.locationManagementUtils;
import com.owd.hibernate.HibernateSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Mar 5, 2009
 * Time: 10:44:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class locationTreeLookupAction extends ActionSupport {


    private String nodeId;
    private Object jsonModel;

    public Object getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(Object jsonModel) {
        this.jsonModel = jsonModel;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public List<treeBean> getOptions() {
        Logger log = LogManager.getLogger();

        List<treeBean> col = new ArrayList<treeBean>();
        try {
            Session sess = HibernateSession.currentSession();
            log.debug("Loading bean for %s", nodeId);
            col = locationManagementUtils.loadLocationTreeNode(sess, nodeId);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return col;
    }

    public String execute() {

        Map pars = ActionContext.getContext().getParameters();
        Logger log = LogManager.getLogger();
        log.debug(pars);
        try {
            log.debug(pars.get("nodeId"));
            String[] p = (String[]) pars.get("nodeId");

            nodeId = p[0];

        } catch (Exception e) {
            try {
                if (null == nodeId) {
                    log.debug("null nodeId looking for root Id");
                    String[] r = (String[]) pars.get("root");
                    log.debug(r[0]);
                    nodeId = r[0];
                }
            } catch (Exception ex) {
                log.debug("no root Id either");
            }
            log.debug("loading nodeId didn't work");
        }
        jsonTreeBean m = new jsonTreeBean();

        m.setIdentifier("id");
        m.setLabel("label");
        m.setItems(getOptions());

        setJsonModel(m);

        System.out.println(nodeId + "  node");
        return Action.SUCCESS;

    }
}
