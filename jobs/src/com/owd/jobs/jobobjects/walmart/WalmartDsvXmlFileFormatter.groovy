package com.owd.jobs.jobobjects.walmart

import com.owd.core.business.order.LineItem
import com.owd.core.business.order.OrderStatus
import com.owd.core.managers.ManifestingManager
import com.owd.core.managers.PackingManager
import com.owd.hibernate.HibernateSession
import com.owd.hibernate.generated.OwdLineItem
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import org.apache.commons.lang.StringUtils

import java.sql.ResultSet
import java.text.SimpleDateFormat

/**
 * Created by stewartbuskirk1 on 10/29/15.
 */
@groovy.util.logging.Log4j2
class WalmartDsvXmlFileFormatter {



    public enum WalmartXmlFileType {
        INVENTORY, CONFIRMATION, STATUS_RECEIVED, STATUS_SHIPPED, CANCEL, ERROR
    };

    private final String dateStr
    private final String timeStr
    private final String randomStr
    private final String accountId
    private final String accountName
    private final WalmartXmlFileType fileType
    private int clientId;
    private String errorStr
    private String errorDataStr
    private String originalPoXml
    private double stockPercentageForWalmart = 1.0

    double getStockPercentageForWalmart() {
        return stockPercentageForWalmart
    }

    void setStockPercentageForWalmart(double stockPercentageForWalmart) {
        this.stockPercentageForWalmart = stockPercentageForWalmart
    }

    int getClientId() {
        return clientId
    }

    void setClientId(int clientId) {
        this.clientId = clientId
    }

    OrderStatus getCurrentOrderStatus() {
        return currentOrderStatus
    }

    void setCurrentOrderStatus(OrderStatus currentOrderStatus) {
        this.currentOrderStatus = currentOrderStatus
    }
    private OrderStatus currentOrderStatus

    String getOriginalPoXml() {
        return originalPoXml
    }

    void setOriginalPoXml(String originalPoXml) {
        this.originalPoXml = originalPoXml
    }

    public WalmartDsvXmlFileFormatter(String accountId, String accountName, WalmartXmlFileType type) {
        this.accountId = accountId
        this.accountName = accountName

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdt = new SimpleDateFormat("hhmmss");
        Date now = Calendar.getInstance().getTime();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        sdt.setTimeZone(TimeZone.getTimeZone("GMT"));
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);

        dateStr = sdf.format(now)
        timeStr = sdt.format(now)
        randomStr = ("" + n)
        fileType = type

    }

    public String getHeaderFileType() {
        switch (fileType) {
            case WalmartXmlFileType.ERROR:
                return "FFE"

                break;

            case WalmartXmlFileType.CONFIRMATION:
                return "FFC"

                break;
            case WalmartXmlFileType.STATUS_RECEIVED:
            case WalmartXmlFileType.STATUS_SHIPPED:
            case WalmartXmlFileType.CANCEL:
                return "FOS"

                break;
            case WalmartXmlFileType.INVENTORY:
                return "FII"
                break;
            default:
                throw new Exception("Bad filetype : " + fileType)

        }
    }

    public String getFileName() {
        switch (fileType) {
            case WalmartXmlFileType.ERROR:
                return "WMI_Error_" + accountId + "_" + dateStr + "_" + timeStr + "_" + randomStr + ".xml"

                break;

            case WalmartXmlFileType.CONFIRMATION:
                return "WMI_Confirm_" + accountId + "_" + dateStr + "_" + timeStr + "_" + randomStr + ".xml"

                break;
            case WalmartXmlFileType.STATUS_RECEIVED:
            case WalmartXmlFileType.STATUS_SHIPPED:
            case WalmartXmlFileType.CANCEL:
                return "WMI_Order_Status_" + accountId + "_" + dateStr + "_" + timeStr + "_" + randomStr + ".xml"

                break;


            case WalmartXmlFileType.INVENTORY:
                return "WMI_Inventory_" + accountId + "_" + dateStr + "_" + timeStr + "_" + randomStr + ".xml"
                break;
            default:
                throw new Exception("Bad filetype : " + fileType)

        }
    }

    public static void main(String[] args) {
        println Math.round(Math.floor(Double.parseDouble('24')*0.80))

    }

    public setErrorData(String error, String data) {
        errorStr = error
        errorDataStr = data
    }

    public String getFileData() {


        def builder = new StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def fileXml = {
            mkp.xmlDeclaration()
            WMI {
                WMIFILEHEADER(FILEID: accountId + "." + dateStr + "." + timeStr + "." + randomStr, FILETYPE: getHeaderFileType(), VERSION: '4.0.0') {
                    FH_TO(ID: "2677", NAME: "Walmart.com")
                    FH_FROM(ID: accountId, NAME: accountName)
                            {
                                FH_CONTACT(EMAIL: accountName.toLowerCase().replaceAll(" ", "_") + '@owd.com', NAME: accountName + ' Fulfillment', PHONE: '6058457172', PHONEEXT: '000')
                            }
                }

                switch (fileType) {
                    case WalmartXmlFileType.ERROR:

                        def walmartOrder = new XmlSlurper().parseText(getOriginalPoXml());

                        WMIFILEERROR(FILEID: walmartOrder.WMIFILEHEADER.'@FILEID', FILETYPE: walmartOrder.WMIFILEHEADER.'@FILETYPE')
                                {
                                    FE_ERROR('ERRORCODE': '0') {
                                        FE_MESSAGE(errorStr)
                                        FE_DATA(errorDataStr)
                                    }
                                }
                        break;

                    case WalmartXmlFileType.CONFIRMATION:
                        def walmartOrder = new XmlSlurper().parseText(getOriginalPoXml());

                        WMIFILECONFIRM(FILEID: walmartOrder.WMIFILEHEADER.'@FILEID', FILETYPE: walmartOrder.WMIFILEHEADER.'@FILETYPE')

                        break;
                    case WalmartXmlFileType.STATUS_RECEIVED:
                        def walmartOrder = new XmlSlurper().parseText(getOriginalPoXml());

                        WMIORDERSTATUS() {
                            walmartOrder.OR_ORDERLINE.each() { item ->


                                OS_LINESTATUS(REQUESTNUMBER: getCurrentOrderStatus().po_num,
                                        LINENUMBER: item.'@LINENUMBER', QUANTITY: item.OR_ITEM.'@QUANTITY', STATUSCODE: "LI")
                            }
                        }


                        break;
                    case WalmartXmlFileType.CANCEL:
                        //This is not a cancel, this will put it on BackOrder. There is no acceptable Cancel code
                        def walmartOrder = new XmlSlurper().parseText(getOriginalPoXml());

                        String po = walmartOrder.'@REQUESTNUMBER'

                        WMIORDERSTATUS() {
                            walmartOrder.OR_ORDERLINE.each() { item ->

                                OS_LINESTATUS(REQUESTNUMBER: po,
                                        LINENUMBER: item.'@LINENUMBER', QUANTITY: item.OR_ITEM.'@QUANTITY', STATUSCODE: "LB")
                            }
                        }


                        break;
                    case WalmartXmlFileType.STATUS_SHIPPED:
                        def walmartOrder = new XmlSlurper().parseText(getOriginalPoXml());



                        WMIORDERSTATUS() {
                            com.owd.core.business.order.Package.getPackagesForOrderId(Integer.parseInt(getCurrentOrderStatus().order_id)).each { pack ->

                               // println "shipdate:"+pack.ship_date
                                OS_PACKAGEINVOICE(REQUESTNUMBER: getCurrentOrderStatus().po_num, STATUSCODE: 'PS') {

                                    String carrierCode =  walmartOrder.OR_SHIPPING.'@CARRIERMETHODCODE'
                                    if(getCurrentOrderStatus().shipping.carr_service.startsWith("USPS"))
                                    {
                                         carrierCode = '30'
                                    }
                                    if ("1".equals(getCurrentOrderStatus().getTagMap().get('WALMARTDSV_S2S'))) {
                                        String asnRef = pack.getSSCCCodeForPackage(pack.order_track_id);
                                        OS_PACKAGE(PACKAGEID: asnRef, ASNNUMBER: asnRef, CARRIERMETHODCODE: carrierCode, TRACKINGNUMBER: pack.tracking_no, WEIGHT: pack.weight)

                                    } else {
                                        OS_PACKAGE(PACKAGEID: pack.order_track_id, CARRIERMETHODCODE: walmartOrder.OR_SHIPPING.'@CARRIERMETHODCODE', TRACKINGNUMBER: pack.tracking_no, WEIGHT: pack.weight)
                                    }
                                    OS_SHIPDATE(DAY: pack.ship_date.substring(8, 10), MONTH: pack.ship_date.substring(5, 7), YEAR: pack.ship_date.substring(0, 4))
                                    OS_INVOICE() {
                                        OS_SHIPPING(SUPPLIERSHIPPING: '30'.equals(carrierCode)?(''+pack.total_billed):'0.00', THIRDPARTYSHIPPING: '30'.equals(carrierCode)?'0':'1')




                                        Map<OwdLineItem, Integer> itemMap = com.owd.core.business.order.Package.getOwdLineItemListforPackage(pack.order_track_id)




                                        log.debug "Got " + itemMap.keySet().size() + " lines for package"
                                        itemMap.keySet().each { owdLineItem ->

                                            log.debug owdLineItem
                                            String lineRef = owdLineItem.getCustRefnum()
                                            log.debug lineRef
                                            def poLineItem = walmartOrder.OR_ORDERLINE.find {
                                                it.@LINENUMBER.text().trim().equals(lineRef)
                                            }

                                            if (poLineItem) {
                                                if ("1".equals(getCurrentOrderStatus().getTagMap().get('WALMARTDSV_S2S'))) {
                                                    OS_LINECOST(LINENUMBER: poLineItem.@LINENUMBER.text(), QUANTITY: itemMap.get(owdLineItem), ITEMCOST: poLineItem.OR_COST.@AMOUNT.text(), HANDLING: '0.00', STORENUMBER: walmartOrder.OR_SHIPPING.@STORENUMBER)

                                                } else {
                                                    OS_LINECOST(LINENUMBER: poLineItem.@LINENUMBER.text(), QUANTITY: itemMap.get(owdLineItem), ITEMCOST: poLineItem.OR_COST.@AMOUNT.text(), HANDLING: '0.00')
                                                }

                                            }
                                        }


                                    }
                                }

                            }


                        }


                        break;


                    case WalmartXmlFileType.INVENTORY:
                        StringBuilder sb = new StringBuilder();

                        List<List<String>> itemList = new ArrayList<List<String>>();

                        ResultSet rs = HibernateSession.getResultSet("select wmref,vendorref,owdsku,upc,floor(isnull(qty_on_hand,0)) as qty from walmart_itemreference\n" +
                                "                                 join owd_inventory i join owd_inventory_oh h on h.inventory_fkey=inventory_id on client_fkey=walmart_client_fkey and i.inventory_num=owdsku and client_fkey=" + getClientId() + "\n" +
                                "                                 where len(wmref)>5 and inventory_num is not null and len(owdsku)>3");
                        while (rs.next()) {

                            itemList.add(Arrays.asList(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                        }
                        rs.close();



                        WMIITEMINVENTORY() {
                            for (List<String> item : itemList) {
                                if (item.get(0).length() > 3 && item.get(1).length() > 3) {

                                    String upc = item.get(3);

                                    log.debug("The length: "+ upc.length());
                                    if(upc.length()==14){
                                        log.debug("woah, ::  " + item.get(0) + " : "+ item.get(2));
                                    }
                                    if (hasCheckDigit(upc)) {
                                        println upc+":"+"CHECKDIGIT!!!"
                                      //  upc = upc.substring(0, upc.length() - 1)
                                    }

                                    if (upc.length() < 13) {
                                        upc = StringUtils.leftPad(upc, 13, '0')
                                    }
                                    //    log.debug upc
                                    if (upc.length() != 13) {
                                        log.debug("UPC value " + upc + " longer than max 13 characters")
                                        throw new Exception("UPC value " + upc + " longer than max 13 characters")
                                    }
                                    long qty = ((Double)(Math.floor(Double.parseDouble(item.get(4))*getStockPercentageForWalmart()))).toLong()
                                  //  println 'convert '+item.get(0)+':'+item.get(4)+'->'+qty

                                    II_ITEM(ITEMNUMBER: item.get(0), UPC: upc, SKU: item.get(1)) {
                                        II_AVAILABILITY(CODE: 'AC') {
                                            II_ONHANDQTY(qty)
                                            II_DAYS(MIN: '0', MAX: '2')
                                        }
                                    }
                                }
                            }
                        }

                        break;
                    default:
                        throw new Exception("Bad filetype : " + fileType)

                }
            }
        }
        return XmlUtil.serialize(builder.bind(fileXml).toString())


    }

    public boolean hasCheckDigit(String userNumber) {

        /*    //get input from user
            Scanner sc = new Scanner(System.in);
            log.debug("Please enter 11 digit UPC");
            String userNumber = sc.next();*/



        int step6;

        char[] chars = userNumber.chars
        int odd = 0
        int even = 0
        int ckDigitCandidate = Integer.parseInt(String.valueOf(chars[userNumber.length() - 1]))

        for (int pos = 0; pos < (userNumber.length() - 1); pos++) {
            if ((1 + pos) % 2 == 0) {
                //even
                even += Integer.parseInt(String.valueOf(chars[pos]))
            } else {
                //odd
                odd += Integer.parseInt(String.valueOf(chars[pos]))
            }
        }

        //STEP 6:  determine check digit
        step6 = (10 - ((even + (3 * odd)) % 10));
        if (step6 == 10) {
            step6 = 0;
        }
        if (ckDigitCandidate != step6) {
            //  log.debug ((even+(3*odd)) % 10)
            //  log.debug   userNumber
            //   log.debug userNumber.length()

            //   log.debug("The UPC, including check digit is: " + userNumber + "  " + ckDigitCandidate + ":" + step6);
            return false;
        }

        return true;
    }


}
