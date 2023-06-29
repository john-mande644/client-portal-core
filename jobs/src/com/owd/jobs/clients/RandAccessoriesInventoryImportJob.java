package com.owd.jobs.clients;

import com.owd.core.DelimitedReader;
import com.owd.core.business.order.Inventory;
import com.owd.hibernate.HibernateSession;
import com.owd.jobs.OWDStatefulJob;
import com.owd.jobs.SftpConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.engine.spi.SessionImplementor;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Connection;
import java.util.List;

public class RandAccessoriesInventoryImportJob extends OWDStatefulJob {
    private final static int kClientID = 726;
    private final static Logger log = LogManager.getLogger();

    SftpConnector ftp = new SftpConnector("ftp.owd.com", "rand", "RAND2022ftp!", "ftp", "aes128-ctr,aes128-cbc");

    int kInventory_Number = 0;
    int kPrice = 1;
    int kTitle = 2;
    int kUpc = 3;
    int kKeyword = 4;
    int kDescription = 5;
    int kNotes = 6;
    int kColor = 7;
    int kSize = 8;
    int kReorderLevel = 9;
    int kHarmonizedCode = 10;
    int kSupplierCost = 11;
    int kSupplierName = 12;
    int kWeight = 13;
    int kCustomsDescription = 14;
    int kCustomsValue = 15;
    int kPackSize = 16;

    @Override
    public void internalExecute() {
        String remotePath = "files/skus";
        if (System.getProperty("com.owd.environment").equals("test") ){
            remotePath = "files/testskus";
        }

        try {
            List<String> fileNames = ftp.getFileNames(remotePath);
            Connection cxn = ((SessionImplementor) HibernateSession.currentSession()).getJdbcConnectionAccess().obtainConnection();

            for (String fileName: fileNames) {
                DelimitedReader reader = new DelimitedReader(',', new BufferedReader(new StringReader(ftp.getFileData(fileName).toString())), true);

                for (int i = 0; i < reader.getRowCount(); i++) {
                    Inventory item = new Inventory(kClientID + "");
                    item.inventory_num = reader.getStrValue(kInventory_Number, i,"");
                    item.price = reader.getFloatValue(kPrice, i, 0.00f);
                    item.upcCode = reader.getStrValue(kUpc, i,"");
                    item.keyword = reader.getStrValue(kKeyword, i,"");
                    item.description = reader.getStrValue(kDescription, i, "");
                    item.notes = reader.getStrValue(kNotes, i, "");
                    item.item_color = reader.getStrValue(kColor, i, "");
                    item.item_size = reader.getStrValue(kSize, i, "");
                    item.qty_reorder = reader.getIntValue(kReorderLevel, i, 0);
                    item.harm_code = reader.getStrValue(kHarmonizedCode, i, "");
                    item.supp_cost = reader.getFloatValue(kSupplierCost, i, 0.00f);
                    item.supplierName = reader.getStrValue(kSupplierName, i, "");
                    item.weight_lbs = reader.getFloatValue(kWeight, i, 0.00f);
                    item.customs_desc = reader.getStrValue(kCustomsDescription, i, "");
                    item.customs_value = reader.getFloatValue(kCustomsValue, i, 0.00f);
                    item.packQty = reader.getIntValue(kPackSize, i, 1);

                    item.dbsave(cxn);
                }

                cxn.commit();
            }
        } catch (Exception ex) {
            log.error("Failed to save inventory item", ex);
        }
    }
}
