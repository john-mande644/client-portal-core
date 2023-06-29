package com.owd.web.internal.intravexbills;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.owd.core.DelimitedReader;


/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Oct 31, 2003
 * Time: 8:41:50 AM
 * To change this template use Options | File Templates.
 */
public class UploadLakevilleBillData {
private final static Logger log =  LogManager.getLogger();


    public static final int kShipperName = 0;
    public static final int kShipperCity = 1;
    public static final int kShipperState = 2;
    public static final int kShipperZip = 3;
    public static final int kConsigneeName = 4;
    public static final int kConsigneeCity = 5;
    public static final int kConsigneeState = 6;
    public static final int kConsigneeZip = 7;
    public static final int kProNumber = 8;
    public static final int kPickupDate = 9;
    public static final int kPaymentType = 10;
    public static final int kPieces = 11;
    public static final int kWeight = 12;
    public static final int kGross = 13;
    public static final int kNet = 14;
    public static final int kDiscount = 15;
    public static final int kFSC = 16;
    public static final int kFSCPct = 17;
    public static final int kSWP = 18;
    public static final int kPallet = 19;
    public static final int kLoose = 20;



    
    DelimitedReader dataReader = null;


    public UploadLakevilleBillData() {

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
