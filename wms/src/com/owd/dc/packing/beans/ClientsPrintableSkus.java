package com.owd.dc.packing.beans;

import com.owd.dc.packing.ResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Dec 16, 2010
 * Time: 2:56:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClientsPrintableSkus {

     private ResultBean results = new ResultBean();
    private List<PrintableData> PrintableItems = new ArrayList<PrintableData>();

    public ResultBean getResults() {
        return results;
    }

    public void setResults(ResultBean results) {
        this.results = results;
    }

    public List<PrintableData> getPrintableItems() {
        return PrintableItems;
    }

    public void setPrintableItems(List<PrintableData> items) {
        PrintableItems = items;
    }
}
