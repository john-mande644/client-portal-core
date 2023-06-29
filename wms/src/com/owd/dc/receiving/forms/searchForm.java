package com.owd.dc.receiving.forms;

import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: D
 * Date: Apr 26, 2006
 * Time: 3:11:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class searchForm extends ActionForm{

    private List statuss;
    private String status;
    private String fkey;
    private String asnId;
    private List clients;
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }


    public List getClients() {
        return clients;
    }

    public void setClients(List clients) {
        this.clients = clients;
    }

    public List getStatuss() {
         statuss = new ArrayList();
        statuss.add(0,"Pending");
        statuss.add(1,"All");
        statuss.add(2,"Unreceived");
        statuss.add(3,"Received");
        statuss.add(4,"Partial Receipt");
        return statuss;
    }

    public void setStatuss(List statuss) {
        this.statuss = statuss;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFkey() {
        return fkey;
    }

    public void setFkey(String fkey) {
        this.fkey = fkey;
    }

    public String getAsnId() {
        return asnId;
    }

    public void setAsnId(String asnId) {
        this.asnId = asnId;
    }

}
