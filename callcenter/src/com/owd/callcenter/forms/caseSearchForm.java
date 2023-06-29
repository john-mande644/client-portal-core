package com.owd.callcenter.forms;

import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 2, 2006
 * Time: 4:37:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class caseSearchForm extends ActionForm {

    private List clients;
   private String client;
    private String searchText;
    private String clientId;


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String st) {
        this.searchText = st;
    }


    public List getClients() {
        return clients;
    }

    public void setClients(List clients) {
        this.clients = clients;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
