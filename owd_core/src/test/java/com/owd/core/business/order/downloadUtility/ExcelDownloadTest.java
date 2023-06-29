package com.owd.core.business.order.downloadUtility;

import org.junit.Test;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



public class ExcelDownloadTest {

    private final static Logger log =  LogManager.getLogger();

    @Test
    public void setHeaders() throws Exception {
        String FILE_PATH = "/Users/owd/Desktop/11 Honore POI exporting/";
        String EXCEL_FILE_NAME = "Invoice";

        int SHEET_NUM = 0;
        XSSFWorkbook wb = ExcelDownload.createWorkbook(SHEET_NUM);

        ExcelDownload.setHeaders(SHEET_NUM, wb);
    }

    @Test
    public void getSkuInfoListByOderId() throws Exception {
        int TEST_ORDER6 =  17747764 ;   // 2 packages

        List<Integer> orders = new  ArrayList<>();
        orders.add(TEST_ORDER6);

        for (int ord: orders) {
            ExcelDownload.generateInvoice(ord);

        }
    }

    @Test
    public void generateInvoiceTest() throws Exception {

        int TEST_ORDER1 =  17319702 ;
        int TEST_ORDER2 =  17926445 ;
        int TEST_ORDER3 =  17798785 ;
        int TEST_ORDER4 =  17807261 ;
        int TEST_ORDER5 =  17808043 ;
        int TEST_ORDER6 =  17747764 ;   // 2 packages

        List<Integer> orders = new  ArrayList<>();
//        orders.add(TEST_ORDER1);
//        orders.add(TEST_ORDER2);
//        orders.add(TEST_ORDER3);
//        orders.add(TEST_ORDER4);
//        orders.add(TEST_ORDER5);
        orders.add(TEST_ORDER6);
//        orders.add(TEST_ORDER7);

        for (int ord: orders) {
            byte [] a = ExcelDownload.generateInvoice(ord);
            for(int i=0; i< a.length ; i++) {
                System.out.println(a[i] +" ");
            }
            System.out.println(ExcelDownload.generateInvoice(ord));


        }

    }

    @Test
    public void generateInvoice_LocalTest() throws Exception {
        ExcelDownload.generateInvoice_LocalTest(17747764  );
    }
}