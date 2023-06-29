/**
 * Sean Created on 2019-02-14
 * using Apache POI XSSF to generate Excel file
 */

package com.owd.core.business.order.downloadUtility;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;

import com.owd.hibernate.generated.OwdOrderTrack;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Restrictions;

public class ExcelDownload {
    private final static Logger log =  LogManager.getLogger();


    public static XSSFWorkbook createWorkbook(int sheets){

        XSSFWorkbook workbook = new XSSFWorkbook();
        workbook.createSheet();

        if(sheets>1) {
            for (int i = 1; i < sheets; i++) {
                workbook.createSheet();
            }
        }
        return workbook;
    }

    private  static final List refFieldName(){
        List <String> col_name = new ArrayList<>();

        col_name.add("Status ");
        col_name.add("Order Reference ");
//        col_name.add("Order ID");
        col_name.add("OWD Reference");
        col_name.add("Backorder Level ");
        col_name.add("");

        return col_name;
    }

    private static List billShipTitle(){
        List <String> col_name = new ArrayList<>();
        col_name.add("Bill To ");
        col_name.add("");
        col_name.add("");
        col_name.add("");
        col_name.add("Ship To ");
        col_name.add("");

        return col_name;
    }

    private static List personalInfoFieldName(){
        List <String> col_name = new ArrayList<>();
        col_name.add("Name");
        col_name.add("Company");
        col_name.add("Address 1");
        col_name.add("Address 2");
        col_name.add("City");
        col_name.add("State");
        col_name.add("Zip");
        col_name.add("Country");
        col_name.add("Phone");
        col_name.add("Email");
        col_name.add("");

        return col_name;
    }

    private static List orderProcessInfoFieldName(){
        List <String> col_name = new ArrayList<>();
        col_name.add("Ship date");
        col_name.add("Carrier");
        col_name.add("Tracking");
        col_name.add("Weight");
        col_name.add("Rate");

        return col_name;
    }

    private static List costFieldName(){
        List <String> col_name = new ArrayList<>();
        col_name.add("Subtotal");
        col_name.add("Discount");
        col_name.add("Tax");
        col_name.add("S/H");
        col_name.add("Total");

        return col_name;
    }

    private static List skuFieldName(){
        List <String> col_name = new ArrayList<>();
        col_name.add("SKU");
        col_name.add("Description");
        col_name.add("Color");
        col_name.add("Size");
        col_name.add("Count");
        col_name.add("BO");
        col_name.add("Cost");
        col_name.add("Line Total");

        return col_name;
    }


    /**
     * Attempt to combine both direction
     * @param sheetIndex
     * @param workbook
     * @param fieldNameList
     * @param rowBeginNum
     * @param col_number
     * @return
     */
    public static XSSFWorkbook setFieldNamesTopDownAll (int sheetIndex, XSSFWorkbook workbook, List<String> fieldNameList,
                                                     Integer rowBeginNum, Integer col_number) {

        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

        try{

            for (int i = 0; i < fieldNameList.size(); i++){

                XSSFRow rowOut;

                if(sheet.getRow(i) != null){
                    rowOut = sheet.getRow(rowBeginNum + i);
                }else {
                    rowOut = sheet.createRow(rowBeginNum + i);
                }

                Map<String, CellStyle> styles = createStyles(workbook);
                XSSFCell cellOut = rowOut.createCell(col_number);
                cellOut.setCellStyle(styles.get("header2"));

                cellOut.setCellValue(fieldNameList.get(i));

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return workbook;
    }

    public static XSSFWorkbook setFieldNamesTopDown (int sheetIndex, XSSFWorkbook workbook, List<String> fieldNameList,
                                              Integer rowBeginNum, Integer col_number) {

        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
        Map<String, CellStyle> styles = createStyles(workbook);

        for (int i = 0; i < fieldNameList.size(); i++){

            XSSFRow rowOut = sheet.createRow(rowBeginNum + i);
            XSSFCell cellOut = rowOut.createCell(col_number);
            cellOut .setCellValue(fieldNameList.get(i));
            cellOut.setCellStyle(styles.get("header2"));

        }
        return workbook;
    }

    // need to cover null case
    public static XSSFWorkbook setFieldNamesLR (int sheetIndex, XSSFWorkbook workbook, List<String> fieldNameList,
                                                     Integer rowStartNum) {

        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

        Map<String, CellStyle> styles = createStyles(workbook);

        for (int i = 0; i < fieldNameList.size(); i++){
            int col_number = i;
            if (i==0){
                XSSFRow rowOut = sheet.createRow(rowStartNum);
                XSSFCell cellOut = rowOut.createCell(col_number);
                cellOut.setCellStyle(styles.get("header"));
                cellOut.setCellValue(fieldNameList.get(i));
            }else {
                XSSFRow rowOut = sheet.getRow(rowStartNum);
                XSSFCell cellOut = rowOut.createCell(col_number);
                cellOut.setCellStyle(styles.get("header"));

                cellOut.setCellValue(fieldNameList.get(i));
            }
            // TODO: dynamic column width
            sheet.setColumnWidth(i,16*256);
        }
        return workbook;
    }

    /**
     * Print header and title
     * @param sheetIndex
     * @param wb
     * @return
     * @throws Exception
     */
    public static XSSFWorkbook setHeaders (int sheetIndex, XSSFWorkbook wb) throws Exception {

        // TODO: universal positioning

        int lEFT_BLOCK_COL_NUM = 0;
        int RIGHT_BLOCK_COL_NUM = 4;

        // Ref fields Block
        int REF_ROW_NUM = 0;
        List<String> refFieldNameList = refFieldName();
        setFieldNamesTopDownAll (sheetIndex, wb, refFieldNameList , REF_ROW_NUM, lEFT_BLOCK_COL_NUM);

        // Title of Bill to and Ship to
        int BILL_SHIP_TITLE_ROW_NUM = refFieldNameList.size();
        List<String> billShipTltle = billShipTitle();
        setFieldNamesLR (sheetIndex, wb, billShipTltle , BILL_SHIP_TITLE_ROW_NUM );

        // Billing Block
        int BILL_SHIP_ROW_NUM = BILL_SHIP_TITLE_ROW_NUM + 1;
        List<String> billInfoList = personalInfoFieldName();
        setFieldNamesTopDown (sheetIndex, wb, billInfoList , BILL_SHIP_ROW_NUM , lEFT_BLOCK_COL_NUM );
//        System.out.println("set biiling");


        // Shipping Block starts from the same row as billing
        List<String> shipInfoList = personalInfoFieldName();
        setFieldNamesTopDownAll (sheetIndex, wb, shipInfoList  , BILL_SHIP_ROW_NUM , RIGHT_BLOCK_COL_NUM);
//        System.out.println("set shipping");

        List<String> skuFieldNameList = skuFieldName();
        int SKU_BEGIN_ROW = BILL_SHIP_ROW_NUM + personalInfoFieldName().size();
        setFieldNamesLR (sheetIndex, wb, skuFieldNameList , SKU_BEGIN_ROW );
        return wb;
    }

    // ===================================== ORDER DATA OUTPUT ===========================================

    /**
     * Retrive order reference info
     * @param orderId
     * @return
     */
    public static List getOrderRefInfoById (int orderId) {
        List<String> orderRefInfo = new ArrayList<>();

        try{
            Session sess = HibernateSession.currentSession();
            OwdOrder order = (OwdOrder) sess.get(OwdOrder.class, orderId);

            if (order!=null){
                orderRefInfo.add(order.getOrderStatus());
                orderRefInfo.add(order.getOrderRefnum());
                orderRefInfo.add(order.getOrderNum());
//                orderRefInfo.add(String.valueOf(orderId));    // order id is not necessary
                orderRefInfo.add(order.getBackorderLevel().toString());
                orderRefInfo.add("");

            }else {
                System.out.println("Null hibernate object");
            }

        } catch (HibernateException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return orderRefInfo;
    }

    /**
     * Retrieve billing info
     * @param orderId
     * @return
     */
    public static List getBillingInfoById (int orderId) {
        List<String> billingInfo = new ArrayList<>();

        try{
            Session sess = HibernateSession.currentSession();
            OwdOrder order = (OwdOrder) sess.get(OwdOrder.class, orderId);

            if (order!=null){

                billingInfo.add(order.getBillFirstName()+" "+order.getBillLastName());
                billingInfo.add(order.getBillCompanyName());
                billingInfo.add(order.getBillAddressOne());
                billingInfo.add(order.getBillAddressTwo());
                billingInfo.add(order.getBillCity());
                billingInfo.add(order.getBillState());
                billingInfo.add(order.getBillZip());
                billingInfo.add(order.getBillCountry());
                billingInfo.add(order.getBillPhoneNum());
                billingInfo.add(order.getBillEmailAddress());
                billingInfo.add("");

            }else {
                System.out.println("Null hibernate object");
            }
        } catch (HibernateException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return billingInfo;
    }

    /**
     * Retrive shipping info
     * @param orderId
     * @return
     */
    public static List getShippingInfoById (int orderId) {
        List<String> shippingInfo = new ArrayList<>();

        try{
            Session sess = HibernateSession.currentSession();
            OwdOrder order = (OwdOrder) sess.get(OwdOrder.class, orderId);

            if (order!=null){

                shippingInfo.add(order.getShipinfo().getShipFirstName()+" "+order.getShipinfo().getShipLastName());
                shippingInfo.add(order.getBillCompanyName());  // only billing, no shipping company ??????
                shippingInfo.add(order.getShipinfo().getShipAddressOne());
                shippingInfo.add(order.getShipinfo().getShipAddressTwo());
                shippingInfo.add(order.getShipinfo().getShipCity());
                shippingInfo.add(order.getShipinfo().getShipState());
                shippingInfo.add(order.getShipinfo().getShipZip());
                shippingInfo.add(order.getShipinfo().getShipCountry());
                shippingInfo.add(order.getShipinfo().getShipPhoneNum());
                shippingInfo.add(order.getShipinfo().getShipEmailAddress());
                shippingInfo.add("");

            }else {
                System.out.println("Null hibernate object");
            }

        } catch (HibernateException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return shippingInfo;
    }

    public static List getOrderTotalById (int orderId) {
        List<String> orderInfo = new ArrayList<>();

        try{
            OwdOrder order = (OwdOrder) HibernateSession.currentSession().get(OwdOrder.class, orderId);

            if (order!=null){
                orderInfo.add(order.getOrderSubTotal().toString());
                orderInfo.add(order.getDiscount().toString());
                orderInfo.add(order.getTaxAmount().toString());
                orderInfo.add(order.getShipHandlingFee().toString());
                orderInfo.add(order.getOrderTotal().toString());
            }else {
                System.out.println("Null hibernate object");
            }

        } catch (HibernateException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return orderInfo;
    }

    // ===========================================================================
    /**
     * Retrieve package item info
     * @param orderId
     * @return
     */
    public static List getProcessStatusById (int orderId) {
        List<Object> packageInfo = new ArrayList<>();
        try{

            Criteria crit = HibernateSession.currentSession().createCriteria(OwdOrderTrack.class);

            crit.add(Restrictions.eq("orderFkey", orderId));
            List<OwdOrderTrack> orderPackages = crit.list();

            for (OwdOrderTrack orderPack : orderPackages) {
                List<String> tmpInfo = new ArrayList<>();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                tmpInfo.add(dateFormat.format(orderPack.getShipDate()));
                tmpInfo.add(orderPack.getCarrierCode());
                tmpInfo.add(orderPack.getTrackingNo());
                tmpInfo.add(orderPack.getWeight().toString());
                tmpInfo.add(orderPack.getTotalBilled().toString());
                packageInfo.add(tmpInfo);
            }

        } catch (HibernateException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * Compared to setCellsLR method, this method prints information on top down direction.
     * @param sheetIndex
     * @param workbook
     * @param orderInfo
     * @param row_begin_num
     * @param col_begin_num
     * @return
     */
    public static XSSFWorkbook setOrderInfo (int sheetIndex, XSSFWorkbook workbook, List<Object> orderInfo,
                                              int row_begin_num, int col_begin_num) {

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("mm/dd/yy"));

        CellStyle moneyStyle = workbook.createCellStyle();
        moneyStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("$#,##0.00"));

        CellStyle decimalStyle = workbook.createCellStyle();
        decimalStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));

        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

        for (short j = 0; j < orderInfo.size(); j ++){
            XSSFRow rowOut = sheet.getRow(row_begin_num +j);

            CellStyle aStyle = moneyStyle;
            XSSFCell cellOut = rowOut.createCell(col_begin_num);
            Object obj = orderInfo.get(j);

            if ((obj instanceof String)){
                if(((String)obj).contains(".")) {
                    boolean letterFound = false;
                    boolean digitFound = false;

                    for (char ch : ((String)obj).toCharArray()) {
                        if (Character.isLetter(ch)) {
                            letterFound = true;
                        }
                        if (Character.isDigit(ch)) {
                            digitFound = true;
                        }
                    }

                    if (! letterFound && digitFound){
                        try {
                            obj = Double.parseDouble((String) obj);
                            aStyle = decimalStyle;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            if ((obj instanceof String)){
                cellOut.setCellValue((String)obj);

            }
            else if ((obj instanceof Integer)){
                cellOut.setCellType(0);
//                    cellOut.setCellStyle(XSSFCell.CELL_TYPE_NUMERIC);
                cellOut.setCellValue(((Integer)obj).intValue());
            }
            else if ((obj instanceof BigInteger)){
                cellOut.setCellType(0);
                cellOut.setCellValue(((BigInteger)obj).intValue());
            }
            else if ( (obj instanceof Double)){
                cellOut.setCellType(5);
                cellOut.setCellStyle(aStyle);
                cellOut.setCellValue(((Double)obj).doubleValue());
            } else if ((obj instanceof Float) ) {
                cellOut.setCellType(5);
                cellOut.setCellStyle(aStyle);
                cellOut.setCellValue(((Float)obj).doubleValue());
            } else if ((obj instanceof BigDecimal)){
                cellOut.setCellType(5);
                cellOut.setCellStyle(aStyle);
                cellOut.setCellValue(((BigDecimal)obj).doubleValue());
            }else if ((obj instanceof Timestamp)){

                cellOut.setCellValue(new Date());
                cellOut.setCellStyle(dateStyle);

                Calendar shipdate = new GregorianCalendar();
                shipdate.setTimeInMillis( ((Timestamp)obj).getTime());

                cellOut.setCellType(0);
                cellOut.setCellValue(shipdate.getTime());
//                System.out.println(shipdate.getTime());

            }else{
                cellOut.setCellValue(""+obj);
            }

        }
        return workbook;
    }

    // ========================== SKU =============================================
    /**
     * Retrieve SKU info
     * @param orderId
     * @return
     * @throws Exception
     */
    public static List getSkuInfoListByOderId(int orderId) throws Exception {

        Session sess = HibernateSession.currentSession();
        OwdOrder order = (OwdOrder) sess.get(OwdOrder.class, orderId);

        List<Object> skuList = new ArrayList<Object>();

        for (int i = 0; i < order.getLineitems().size(); i++) {
            List<String> tmpSkuInfo = new ArrayList<>();
            OwdLineItem item = (OwdLineItem) (order.getLineitems().toArray()[i]);
            tmpSkuInfo.add(item.getInventoryNum());
            tmpSkuInfo.add(item.getDescription());
            tmpSkuInfo.add(item.getItemColor());
            tmpSkuInfo.add(item.getItemSize());
            tmpSkuInfo.add(item.getQuantityRequest().toString());
            tmpSkuInfo.add("");                                         // back order ????
            tmpSkuInfo.add(item.getPrice().toString());
            tmpSkuInfo.add(item.getTotalPrice().toString());

            skuList.add(tmpSkuInfo);
        }
        return skuList;
    }

    /**
     * Compared to setCellsLR method, this method prints information from left to right such as SKU info.
     * @param sheetIndex
     * @param workbook
     * @param skuList
     * @param rowBegin
     */
    public static void setCellsLR(int sheetIndex, XSSFWorkbook workbook, List<List<Object>> skuList, int rowBegin) {

        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("m/d/yy"));

        CellStyle moneyStyle = workbook.createCellStyle();
        moneyStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("$#,##0.00"));

        CellStyle decimalStyle = workbook.createCellStyle();
        decimalStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#,##0.00"));


        for (int i = 0; i < skuList.size(); i++){
            XSSFRow rowOut = sheet.createRow(rowBegin + i);

            List skuInfo = (List)skuList.get(i);

            for (short j = 0; j < skuInfo.size(); j = (short)(j + 1)){
                CellStyle aStyle = moneyStyle;
                XSSFCell cellOut = rowOut.createCell(j);
                Object obj = skuInfo.get(j);

                if ((obj instanceof String)){
                    if(((String)obj).contains(".")) {
                        boolean letterFound = false;

                        for (char ch : ((String)obj).toCharArray()) {
                            if (Character.isLetter(ch)) {
                                letterFound = true;
                            }
                        }

                        if (! letterFound){
                            try {
                                obj = Double.parseDouble((String) obj);
                                aStyle = decimalStyle;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }

                    }
                }
                if ((obj instanceof String)){
                    cellOut.setCellValue((String)obj);

                }
                else if ((obj instanceof Integer)){
                    cellOut.setCellType(0);
//                    cellOut.setCellStyle(XSSFCell.CELL_TYPE_NUMERIC);
                    cellOut.setCellValue(((Integer)obj).intValue());
                }
                else if ((obj instanceof BigInteger)){
                    cellOut.setCellType(0);
                    cellOut.setCellValue(((BigInteger)obj).intValue());
                }
                else if ( (obj instanceof Double)){
                    cellOut.setCellType(5);
                    cellOut.setCellStyle(aStyle);
                    cellOut.setCellValue(((Double)obj).doubleValue());
                } else if ((obj instanceof Float) ) {
                    cellOut.setCellType(5);
                    cellOut.setCellStyle(aStyle);
                    cellOut.setCellValue(((Float)obj).doubleValue());
                } else if ((obj instanceof BigDecimal)){
                    cellOut.setCellType(5);
                    cellOut.setCellStyle(aStyle);
                    cellOut.setCellValue(((BigDecimal)obj).doubleValue());
                }else if ((obj instanceof Timestamp)){

                    cellOut.setCellValue(new Date());
                    cellOut.setCellStyle(dateStyle);

                    Calendar shipdate = new GregorianCalendar();
                    shipdate.setTimeInMillis( ((Timestamp)obj).getTime());

                    cellOut.setCellType(0);
                    cellOut.setCellValue(shipdate.getTime());
                }else{
                    cellOut.setCellValue(""+obj);
                }
            }
        }
    }

    // ============================ Styling ============================================
    /**
     * Styling library: bold, grey background
     * @param wb
     * @return
     */
    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>();
        DataFormat df = wb.createDataFormat();

        //header background: grey
        Font headerFont = wb.createFont();
        headerFont.setBold(true);

        CellStyle style = createBorderedStyle(wb);
        style.setFont(headerFont);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("header", style);

        // header font: bold
        Font headerFont2 = wb.createFont();
        headerFont2.setBold(true);

        CellStyle style2 = createBorderedStyle(wb);
        style2.setFont(headerFont2);
        styles.put("header2", style2);

        return styles;
    }

    /**
     *
     * @param wb
     * @return
     */
    private static CellStyle createBorderedStyle(Workbook wb){
        BorderStyle thin = BorderStyle.THIN;
        short black = IndexedColors.BLACK.getIndex();

        CellStyle style = wb.createCellStyle();
        return style;
    }

    /**
     * (only used for testing).
     * Download Excel locally computer. Make sure the path is accessible in your computer
     * @param wb
     * @param orderId
     * @throws IOException
     */
    public static void writeToExcel(XSSFWorkbook wb, int orderId) throws IOException {
        FileOutputStream outputStream= new FileOutputStream( "/Users/owd/Desktop/" +orderId +".xlsx");
        wb.write(outputStream);
        outputStream.close();
        log.debug(orderId + ".xlsx is successfully saved");
    }

    /**
     * Mein for testing. Download excel locally
     * @param orderId
     * @throws Exception
     */
    public static void generateInvoice_LocalTest(int orderId) throws Exception {

        int SHEET_NUM = 0;
        XSSFWorkbook wb = createWorkbook(SHEET_NUM);

        setHeaders(SHEET_NUM, wb);

        int COL_BEGIN = 1;
        int ROW_BEGIN = 0;
        int bill_info_begin_row = ROW_BEGIN + getOrderRefInfoById(orderId).size() +1;

        int SKU_TITLE_ROW_NUM = 1;
        int sku_info_begin_row = bill_info_begin_row + getBillingInfoById(orderId).size() + SKU_TITLE_ROW_NUM ;

        setOrderInfo(SHEET_NUM, wb, getOrderRefInfoById(orderId), ROW_BEGIN, COL_BEGIN);
        setOrderInfo(SHEET_NUM, wb, getBillingInfoById(orderId), bill_info_begin_row, COL_BEGIN);
        setOrderInfo(SHEET_NUM, wb, getShippingInfoById(orderId), bill_info_begin_row, 5);

        List <List<Object>> skuList = getSkuInfoListByOderId(orderId);
        setCellsLR(SHEET_NUM, wb,skuList, sku_info_begin_row);

        // Set total
        List<String> costFieldNameList = costFieldName();
        setFieldNamesTopDown (SHEET_NUM, wb, costFieldNameList  , sku_info_begin_row +skuList.size() , COL_BEGIN+5);
        setOrderInfo(SHEET_NUM, wb, getOrderTotalById(orderId), sku_info_begin_row +skuList.size() ,COL_BEGIN+6);

        // =================================================================
        // Set package status
        int pro_info_begin_row = sku_info_begin_row + skuList.size()+costFieldNameList.size();
        List<String> orderProcessInfoFieldNameList = orderProcessInfoFieldName();
        setFieldNamesLR (SHEET_NUM, wb, orderProcessInfoFieldNameList  , pro_info_begin_row );

        int process_info_begin_row = sku_info_begin_row +skuList.size()  + getOrderTotalById(orderId).size();
//        setFieldNamesLR(SHEET_NUM, wb, getProcessStatusById(orderId), process_info_begin_row+1);
        setCellsLR(SHEET_NUM, wb, getProcessStatusById(orderId), process_info_begin_row+1);
        writeToExcel(wb, orderId);
    }

    // ============================== Main function to construct the report ========================
    /**
     * Main method passing bytes array to view module
     * @param orderId
     * @return
     * @throws Exception
     */
    public static byte[] generateInvoice(int orderId) throws Exception {

        int SHEET_NUM = 0;
        XSSFWorkbook wb = createWorkbook(SHEET_NUM);

        setHeaders(SHEET_NUM, wb);

        int COL_BEGIN = 1;
        int ROW_BEGIN = 0;
        int bill_info_begin_row = ROW_BEGIN + getOrderRefInfoById(orderId).size() +1;

        int SKU_TITLE_ROW_NUM = 1;
        int sku_info_begin_row = bill_info_begin_row + getBillingInfoById(orderId).size() + SKU_TITLE_ROW_NUM ;

        setOrderInfo(SHEET_NUM, wb, getOrderRefInfoById(orderId), ROW_BEGIN, COL_BEGIN);
        setOrderInfo(SHEET_NUM, wb, getBillingInfoById(orderId), bill_info_begin_row, COL_BEGIN);
        setOrderInfo(SHEET_NUM, wb, getShippingInfoById(orderId), bill_info_begin_row, 5);

        List <List<Object>> skuList = getSkuInfoListByOderId(orderId);
        setCellsLR(SHEET_NUM, wb,skuList, sku_info_begin_row);

        // Set total
        List<String> costFieldNameList = costFieldName();
        setFieldNamesTopDown (SHEET_NUM, wb, costFieldNameList  , sku_info_begin_row +skuList.size() , COL_BEGIN+5);
        setOrderInfo(SHEET_NUM, wb, getOrderTotalById(orderId), sku_info_begin_row +skuList.size() ,COL_BEGIN+6);

        // =================================================================
        // Set package status
        int pro_info_begin_row = sku_info_begin_row + skuList.size()+costFieldNameList.size();
        List<String> orderProcessInfoFieldNameList = orderProcessInfoFieldName();
        setFieldNamesLR (SHEET_NUM, wb, orderProcessInfoFieldNameList  , pro_info_begin_row );

        int process_info_begin_row = sku_info_begin_row +skuList.size()  + getOrderTotalById(orderId).size();
//        setFieldNamesLR(SHEET_NUM, wb, getProcessStatusById(orderId), process_info_begin_row+1);
        setCellsLR(SHEET_NUM, wb, getProcessStatusById(orderId), process_info_begin_row+1);

        //================================================================
        // convert to byte and return to controller
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        wb.write(byteOut);
        byte [] byteArray = byteOut.toByteArray();

        return byteArray;
    }
}

