package com.owd.web.internal.fedexbills;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.DelimitedReader;

import java.util.Map;
import java.util.TreeMap;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 31, 2003
 * Time: 8:41:50 AM
 * To change this template use Options | File Templates.
 */
public class UploadFedexBillData {
private final static Logger log =  LogManager.getLogger();


   

    DelimitedReader dataReader = null;


    public UploadFedexBillData() {

    }

    public void init(DelimitedReader rdr) {
        if (rdr == null) return;

        setDataReader(rdr);
        processReader();


    }


    protected void processReader() {

        if (getDataReader() == null) return;

        for (int row = 0; row < getDataReader().getRowCount(); row++) {
            ////log.debug("DH handling row "+row);

        }
    }


    public DelimitedReader getDataReader() {
        return dataReader;
    }

    public void setDataReader(DelimitedReader dataReader) {
        this.dataReader = dataReader;
    }

}
