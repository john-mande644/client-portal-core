package com.owd.web.callcenter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Nov 19, 2007
 * Time: 10:19:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class santaCallForm extends ActionForm {
private final static Logger log =  LogManager.getLogger();
    private String callId;
    private String phoneNumber;
    private String results;
    private RowSetDynaClass searchResults;
    private List columns;


    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }


    public RowSetDynaClass getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(RowSetDynaClass searchResults) {
        this.searchResults = searchResults;
    }

    public List getColumns() {
        return columns;
    }

    public void setColumns(List columns) {
        this.columns = columns;
    }
}
