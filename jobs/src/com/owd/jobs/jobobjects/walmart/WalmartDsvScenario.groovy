package com.owd.jobs.jobobjects.walmart

import com.owd.core.business.order.OrderStatus
import com.owd.core.managers.ManifestingManager
import groovy.xml.XmlUtil

/**
 * Created by stewartbuskirk1 on 11/10/15.
 */
class WalmartDsvScenario {
    public static             WalmartDsvApi api = new WalmartDsvApi("", "", "", "272112", "Gildan")
    public static writeFiles = true;
    public static void main(String[] args) {

        WalmartDsvXmlFileFormatter invFileSource = new WalmartDsvXmlFileFormatter(api.accountId, api.accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.STATUS_SHIPPED)

      //  invFileSource.setStockPercentageForWalmart(0.80)
        invFileSource.setClientId(471)

        OrderStatus os = new OrderStatus(''+10277544)
        invFileSource.setCurrentOrderStatus(os)
        invFileSource.setOriginalPoXml(os.getTagMap().get('WALMARTDSV_PO_XML'))
        println invFileSource.getFileData()
    }

    public static void test6()
    {
      long orderId =9781225;
        OrderStatus os = new OrderStatus(''+orderId)
        confirmOrderReceipt(os,'test6')
        confirmOrderShipment(os,'test6')
    }

    private static void confirmOrderReceipt(OrderStatus os, String folder) {
        WalmartDsvXmlFileFormatter statusFileSource = new WalmartDsvXmlFileFormatter(api.accountId, api.accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.STATUS_RECEIVED)

        statusFileSource.setCurrentOrderStatus(os)
        statusFileSource.setOriginalPoXml(os.getTagMap().get('WALMARTDSV_PO_XML'))
        String data = statusFileSource.getFileData()
        println XmlUtil.serialize(data)
        println statusFileSource.getFileName()
       if(writeFiles) {
           new File('/Users/stewartbuskirk1/Desktop/walmarttestdata/'+folder+'/'+statusFileSource.getFileName()).write(XmlUtil.serialize(data))
       }
        println '******'
    }

    private static void confirmOrderShipment(OrderStatus os, String folder) {
        WalmartDsvXmlFileFormatter confirmFileSource = new WalmartDsvXmlFileFormatter(api.accountId, api.accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.STATUS_SHIPPED)

        confirmFileSource.setCurrentOrderStatus(os)
        confirmFileSource.setOriginalPoXml(os.getTagMap().get('WALMARTDSV_PO_XML'))
        String data = confirmFileSource.getFileData()
        println XmlUtil.serialize(data)
        println confirmFileSource.getFileName()
        if(writeFiles) {
            new File('/Users/stewartbuskirk1/Desktop/walmarttestdata/'+folder+'/'+confirmFileSource.getFileName()).write(XmlUtil.serialize(data))
        }
        println '******'
    }

    public static void test7()
    {
        long orderId =9781231;
        OrderStatus os = new OrderStatus(''+orderId)
        confirmOrderReceipt(os,'test7')
        confirmOrderShipment(os,'test7')

    }


    public static void test12()
    {
        long orderId =9781233;
        OrderStatus os = new OrderStatus(''+orderId)
        confirmOrderReceipt(os,'test12')
        confirmOrderShipment(os,'test12')

    }


    public static void test13()
    {
        long orderId =9781237;
        OrderStatus os = new OrderStatus(''+orderId)
        confirmOrderReceipt(os,'test13')
        confirmOrderShipment(os,'test13')

    }


    public static void test23()
    {
        long orderId =9781240;
        OrderStatus os = new OrderStatus(''+orderId)
        confirmOrderReceipt(os,'test23')
        confirmOrderShipment(os,'test23')

    }


    public static void test24()
    {
        long orderId =9781245;
        OrderStatus os = new OrderStatus(''+orderId)
        WalmartDsvXmlFileFormatter statusFileSource = new WalmartDsvXmlFileFormatter(api.accountId, api.accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.CANCEL)

        statusFileSource.setCurrentOrderStatus(os)
        statusFileSource.setOriginalPoXml(os.getTagMap().get('WALMARTDSV_PO_XML'))

        String data = statusFileSource.getFileData()
        println XmlUtil.serialize(data)
        println statusFileSource.getFileName()
        if(writeFiles) {
            new File('/Users/stewartbuskirk1/Desktop/walmarttestdata/test24/'+statusFileSource.getFileName()).write(XmlUtil.serialize(data))
        }
        println '******'

    }

    public static void test25()
    {
        long orderId =9781248;
        OrderStatus os = new OrderStatus(''+orderId)
        confirmOrderReceipt(os,'test25')
        confirmOrderShipment(os,'test25')


    }

    public static void test26()
    {
        long orderId =9781250;
        OrderStatus os = new OrderStatus(''+orderId)
        confirmOrderReceipt(os,'test26')
        confirmOrderShipment(os,'test26')


    }

    public static void test27()
    {
        long orderId =9781254;
        OrderStatus os = new OrderStatus(''+orderId)
        confirmOrderReceipt(os,'test27')
        confirmOrderShipment(os,'test27')


    }


    public static void getFunctionalAcknowledgment()
    {
        String src = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<WMI>\n" +
                "  <WMIFILEHEADER FILEID=\"272112.20151106.132424.394001\" FILETYPE=\"FOR\" VERSION=\"4.0.0\">\n" +
                "    <FH_TO ID=\"272112\" NAME=\"Gildan USA\"/>\n" +
                "    <FH_FROM ID=\"2677\" NAME=\"Walmart.com\">\n" +
                "      <FH_CONTACT NAME=\"Walmart.com Operations Support\" EMAIL=\"webops@walmart.com\" PHONE=\"6508375465\" PHONEEXT=\"\"/>\n" +
                "    </FH_FROM>\n" +
                "  </WMIFILEHEADER>\n" +
                "  <WMIORDERREQUEST>\n" +
                "    <OR_ORDER REQUESTNUMBER=\"12844128\" ORDERNUMBER=\"8890124017208\">\n" +
                "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "      <OR_SHIPPING METHODCODE=\"MI\" CARRIERMETHODCODE=\"80\" STORENUMBER=\"2280\" TOGETHERCODE=\"SC\">\n" +
                "        <OR_PHONE PRIMARY=\"6509170796\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"Walmart #2280 \" ADDRESS2=\"600 Showers Dr\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"Mountain View\" STATE=\"CA\" POSTALCODE=\"94040\" COUNTRY=\"USA\"/>\n" +
                "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EMAIL/>\n" +
                "      </OR_SHIPPING>\n" +
                "      <OR_BILLING ORDERPRICE=\"15.86\">\n" +
                "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
                "      </OR_BILLING>\n" +
                "      <OR_RETURNS TCNUMBER=\"15979799861042016742\" METHODCODE=\"RC\">\n" +
                "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
                "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
                "      </OR_RETURNS>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"10.57\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314806\" UPC=\"0088309617538\" SKU=\"ST412-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Slash Foot NoShow Sock\" QUANTITY=\"2\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.32\" SHIPPING=\"0.00\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"2.96\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"2\" LINEPRICE=\"5.29\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314810\" UPC=\"0088309617541\" SKU=\"ST413-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.32\" SHIPPING=\"0.00\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"2.77\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_SHIPTOSTORE VENDORID=\"413356050\">\n" +
                "      </OR_SHIPTOSTORE>\n" +
                "    </OR_ORDER>\n" +
                "    <OR_ORDER REQUESTNUMBER=\"12844129\" ORDERNUMBER=\"8890124035222\">\n" +
                "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "      <OR_SHIPPING METHODCODE=\"MI\" CARRIERMETHODCODE=\"79\" STORENUMBER=\"2280\" TOGETHERCODE=\"SC\">\n" +
                "        <OR_PHONE PRIMARY=\"6509170796\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"Walmart #2280 \" ADDRESS2=\"600 Showers Dr\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"Mountain View\" STATE=\"CA\" POSTALCODE=\"94040\" COUNTRY=\"USA\"/>\n" +
                "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EMAIL/>\n" +
                "      </OR_SHIPPING>\n" +
                "      <OR_BILLING ORDERPRICE=\"10.57\">\n" +
                "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
                "      </OR_BILLING>\n" +
                "      <OR_RETURNS TCNUMBER=\"13565955780262268934\" METHODCODE=\"RC\">\n" +
                "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
                "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
                "      </OR_RETURNS>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"10.57\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314820\" UPC=\"0088309617542\" SKU=\"ST513-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Colorblock NoShow Sock\" QUANTITY=\"2\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.32\" SHIPPING=\"0.00\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"3.30\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_SHIPTOSTORE VENDORID=\"413356050\">\n" +
                "      </OR_SHIPTOSTORE>\n" +
                "    </OR_ORDER>\n" +
                "    <OR_ORDER REQUESTNUMBER=\"12844130\" ORDERNUMBER=\"8890124052705\">\n" +
                "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "      <OR_SHIPPING METHODCODE=\"MS\" CARRIERMETHODCODE=\"20\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EMAIL/>\n" +
                "      </OR_SHIPPING>\n" +
                "      <OR_BILLING ORDERPRICE=\"7.39\">\n" +
                "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
                "      </OR_BILLING>\n" +
                "      <OR_RETURNS TCNUMBER=\"76787643125164810\" METHODCODE=\"RC\">\n" +
                "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
                "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
                "      </OR_RETURNS>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314822\" UPC=\"0088309617543\" SKU=\"ST613-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"3.62\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "    </OR_ORDER>\n" +
                "    <OR_ORDER REQUESTNUMBER=\"12844131\" ORDERNUMBER=\"8890124179993\">\n" +
                "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "      <OR_SHIPPING METHODCODE=\"MX\" CARRIERMETHODCODE=\"21\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EMAIL/>\n" +
                "      </OR_SHIPPING>\n" +
                "      <OR_BILLING ORDERPRICE=\"7.39\">\n" +
                "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
                "      </OR_BILLING>\n" +
                "      <OR_RETURNS TCNUMBER=\"24422122138756713079\" METHODCODE=\"RC\">\n" +
                "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
                "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
                "      </OR_RETURNS>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314813\" UPC=\"0088309617544\" SKU=\"ST414-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"2.96\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "    </OR_ORDER>\n" +
                "    <OR_ORDER REQUESTNUMBER=\"12844132\" ORDERNUMBER=\"8890124219323\">\n" +
                "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "      <OR_SHIPPING METHODCODE=\"MS\" CARRIERMETHODCODE=\"02\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EMAIL/>\n" +
                "      </OR_SHIPPING>\n" +
                "      <OR_BILLING ORDERPRICE=\"29.55\">\n" +
                "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
                "      </OR_BILLING>\n" +
                "      <OR_RETURNS TCNUMBER=\"75779899861044046732\" METHODCODE=\"RC\">\n" +
                "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
                "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
                "      </OR_RETURNS>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"14.77\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314806\" UPC=\"0088309617538\" SKU=\"ST412-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Slash Foot NoShow Sock\" QUANTITY=\"2\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"2.96\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"2\" LINEPRICE=\"14.77\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314810\" UPC=\"0088309617541\" SKU=\"ST413-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Colorblock NoShow Sock\" QUANTITY=\"2\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"2.77\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "    </OR_ORDER>\n" +
                "    <OR_ORDER REQUESTNUMBER=\"12844133\" ORDERNUMBER=\"8890124236706\">\n" +
                "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "      <OR_SHIPPING METHODCODE=\"MX\" CARRIERMETHODCODE=\"16\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EMAIL/>\n" +
                "      </OR_SHIPPING>\n" +
                "      <OR_BILLING ORDERPRICE=\"22.16\">\n" +
                "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
                "      </OR_BILLING>\n" +
                "      <OR_RETURNS TCNUMBER=\"06214044017935941225\" METHODCODE=\"RC\">\n" +
                "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
                "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
                "      </OR_RETURNS>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314818\" UPC=\"0088309617537\" SKU=\"ST611-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Slash Foot NoShow Sock\" QUANTITY=\"1\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"3.62\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"2\" LINEPRICE=\"7.39\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314823\" UPC=\"0088309617545\" SKU=\"ST514-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"3.54\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"3\" LINEPRICE=\"7.39\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314827\" UPC=\"0088309617546\" SKU=\"ST614-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"3.89\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "    </OR_ORDER>\n" +
                "    <OR_ORDER REQUESTNUMBER=\"12844134\" ORDERNUMBER=\"8890124249824\">\n" +
                "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "      <OR_SHIPPING METHODCODE=\"MX\" CARRIERMETHODCODE=\"24\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EMAIL/>\n" +
                "      </OR_SHIPPING>\n" +
                "      <OR_BILLING ORDERPRICE=\"7.39\">\n" +
                "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
                "      </OR_BILLING>\n" +
                "      <OR_RETURNS TCNUMBER=\"71153933972480497546\" METHODCODE=\"RC\">\n" +
                "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
                "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
                "      </OR_RETURNS>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314807\" UPC=\"0088309617535\" SKU=\"ST411-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Slash Foot NoShow Sock\" QUANTITY=\"1\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"2.77\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "    </OR_ORDER>\n" +
                "    <OR_ORDER REQUESTNUMBER=\"12844135\" ORDERNUMBER=\"8890124289244\">\n" +
                "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "      <OR_SHIPPING METHODCODE=\"MS\" CARRIERMETHODCODE=\"67\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EMAIL/>\n" +
                "      </OR_SHIPPING>\n" +
                "      <OR_BILLING ORDERPRICE=\"7.39\">\n" +
                "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
                "      </OR_BILLING>\n" +
                "      <OR_RETURNS TCNUMBER=\"63365755780261278924\" METHODCODE=\"RC\">\n" +
                "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
                "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
                "      </OR_RETURNS>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314814\" UPC=\"0088309617536\" SKU=\"ST511-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Slash Foot NoShow Sock\" QUANTITY=\"1\"/>\n" +
                "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"3.30\"/>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "    </OR_ORDER>\n" +
                "    <OR_ORDER REQUESTNUMBER=\"12844136\" ORDERNUMBER=\"8890124311425\">\n" +
                "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "      <OR_SHIPPING METHODCODE=\"MP\" CARRIERMETHODCODE=\"22\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
                "        <OR_EMAIL/>\n" +
                "      </OR_SHIPPING>\n" +
                "      <OR_BILLING ORDERPRICE=\"0\">\n" +
                "        <OR_PAYMENT METHOD=\"Gift Receipt\"/>\n" +
                "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
                "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
                "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
                "      </OR_BILLING>\n" +
                "      <OR_RETURNS TCNUMBER=\"25579899861043086702\" METHODCODE=\"RC\">\n" +
                "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
                "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
                "      </OR_RETURNS>\n" +
                "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"0\">\n" +
                "        <OR_ITEM ITEMNUMBER=\"38314814\" UPC=\"0088309617536\" SKU=\"ST511-7BWAWMC\" DESCRIPTION=\"A Gift for You\" QUANTITY=\"1\"/>\n" +
                "        <OR_PRICE RETAIL=\"0\" TAX=\"0\" SHIPPING=\"0\">\n" +
                "        </OR_PRICE>\n" +
                "        <OR_COST AMOUNT=\"3.30\"/>\n" +
                "        <OR_VAS SEQUENCE=\"1\" VASCODE=\"VGM\">\n" +
                "          <OR_VASDATA NAME=\"LINE1\" VALUE=\"This is sample gift message 1\"/>\n" +
                "          <OR_VASDATA NAME=\"LINE2\" VALUE=\"This is sample gift message 2\"/>\n" +
                "          <OR_VASDATA NAME=\"LINE3\" VALUE=\"This is sample gift message 3\"/>\n" +
                "          <OR_VASDATA NAME=\"LINE4\" VALUE=\"\"/>\n" +
                "        </OR_VAS>\n" +
                "      </OR_ORDERLINE>\n" +
                "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
                "    </OR_ORDER>\n" +
                "  </WMIORDERREQUEST>\n" +
                "</WMI>"
        WalmartDsvApi api = new WalmartDsvApi("", "", "", "272112", "Gildan")
        def walmartOrder = new XmlSlurper().parseText(src);

        String batchRef = walmartOrder.WMIFILEHEADER.'@FILEID'


       // WalmartDsvXmlFileFormatter errorFileSource = new WalmartDsvXmlFileFormatter(api.accountId, api.accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.ERROR)
        WalmartDsvXmlFileFormatter confirmFileSource = new WalmartDsvXmlFileFormatter(api.accountId, api.accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.CONFIRMATION)
       // WalmartDsvXmlFileFormatter statusFileSource = new WalmartDsvXmlFileFormatter(api.accountId, api.accountName, WalmartDsvXmlFileFormatter.WalmartXmlFileType.STATUS_RECEIVED)

      //  errorFileSource.setOriginalPoXml(poData)
        confirmFileSource.setOriginalPoXml(src)
      //  statusFileSource.setOriginalPoXml(poData)

        String data = confirmFileSource.getFileData()
        println XmlUtil.serialize(data)
        println confirmFileSource.getFileName()
        new File('/Users/stewartbuskirk1/Desktop/walmarttestdata/'+confirmFileSource.getFileName()).write(XmlUtil.serialize(data))
        println '*******'

    }
    public static void importTestData()
    {
       String src = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
               "<WMI>\n" +
               "  <WMIFILEHEADER FILEID=\"272112.20151106.132424.394001\" FILETYPE=\"FOR\" VERSION=\"4.0.0\">\n" +
               "    <FH_TO ID=\"272112\" NAME=\"Gildan USA\"/>\n" +
               "    <FH_FROM ID=\"2677\" NAME=\"Walmart.com\">\n" +
               "      <FH_CONTACT NAME=\"Walmart.com Operations Support\" EMAIL=\"webops@walmart.com\" PHONE=\"6508375465\" PHONEEXT=\"\"/>\n" +
               "    </FH_FROM>\n" +
               "  </WMIFILEHEADER>\n" +
               "  <WMIORDERREQUEST>\n" +
               "    <OR_ORDER REQUESTNUMBER=\"12844128\" ORDERNUMBER=\"8890124017208\">\n" +
               "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "      <OR_SHIPPING METHODCODE=\"MI\" CARRIERMETHODCODE=\"80\" STORENUMBER=\"2280\" TOGETHERCODE=\"SC\">\n" +
               "        <OR_PHONE PRIMARY=\"6509170796\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"Walmart #2280 \" ADDRESS2=\"600 Showers Dr\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"Mountain View\" STATE=\"CA\" POSTALCODE=\"94040\" COUNTRY=\"USA\"/>\n" +
               "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EMAIL/>\n" +
               "      </OR_SHIPPING>\n" +
               "      <OR_BILLING ORDERPRICE=\"15.86\">\n" +
               "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
               "      </OR_BILLING>\n" +
               "      <OR_RETURNS TCNUMBER=\"15979799861042016742\" METHODCODE=\"RC\">\n" +
               "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
               "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
               "      </OR_RETURNS>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"10.57\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314806\" UPC=\"0088309617538\" SKU=\"ST412-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Slash Foot NoShow Sock\" QUANTITY=\"2\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.32\" SHIPPING=\"0.00\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"2.96\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"2\" LINEPRICE=\"5.29\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314810\" UPC=\"0088309617541\" SKU=\"ST413-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.32\" SHIPPING=\"0.00\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"2.77\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_SHIPTOSTORE VENDORID=\"413356050\">\n" +
               "      </OR_SHIPTOSTORE>\n" +
               "    </OR_ORDER>\n" +
               "    <OR_ORDER REQUESTNUMBER=\"12844129\" ORDERNUMBER=\"8890124035222\">\n" +
               "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "      <OR_SHIPPING METHODCODE=\"MI\" CARRIERMETHODCODE=\"79\" STORENUMBER=\"2280\" TOGETHERCODE=\"SC\">\n" +
               "        <OR_PHONE PRIMARY=\"6509170796\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"Walmart #2280 \" ADDRESS2=\"600 Showers Dr\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"Mountain View\" STATE=\"CA\" POSTALCODE=\"94040\" COUNTRY=\"USA\"/>\n" +
               "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EMAIL/>\n" +
               "      </OR_SHIPPING>\n" +
               "      <OR_BILLING ORDERPRICE=\"10.57\">\n" +
               "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
               "      </OR_BILLING>\n" +
               "      <OR_RETURNS TCNUMBER=\"13565955780262268934\" METHODCODE=\"RC\">\n" +
               "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
               "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
               "      </OR_RETURNS>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"10.57\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314820\" UPC=\"0088309617542\" SKU=\"ST513-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Colorblock NoShow Sock\" QUANTITY=\"2\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.32\" SHIPPING=\"0.00\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"3.30\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_SHIPTOSTORE VENDORID=\"413356050\">\n" +
               "      </OR_SHIPTOSTORE>\n" +
               "    </OR_ORDER>\n" +
               "    <OR_ORDER REQUESTNUMBER=\"12844130\" ORDERNUMBER=\"8890124052705\">\n" +
               "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "      <OR_SHIPPING METHODCODE=\"MS\" CARRIERMETHODCODE=\"20\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EMAIL/>\n" +
               "      </OR_SHIPPING>\n" +
               "      <OR_BILLING ORDERPRICE=\"7.39\">\n" +
               "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
               "      </OR_BILLING>\n" +
               "      <OR_RETURNS TCNUMBER=\"76787643125164810\" METHODCODE=\"RC\">\n" +
               "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
               "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
               "      </OR_RETURNS>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314822\" UPC=\"0088309617543\" SKU=\"ST613-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"3.62\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "    </OR_ORDER>\n" +
               "    <OR_ORDER REQUESTNUMBER=\"12844131\" ORDERNUMBER=\"8890124179993\">\n" +
               "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "      <OR_SHIPPING METHODCODE=\"MX\" CARRIERMETHODCODE=\"21\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EMAIL/>\n" +
               "      </OR_SHIPPING>\n" +
               "      <OR_BILLING ORDERPRICE=\"7.39\">\n" +
               "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
               "      </OR_BILLING>\n" +
               "      <OR_RETURNS TCNUMBER=\"24422122138756713079\" METHODCODE=\"RC\">\n" +
               "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
               "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
               "      </OR_RETURNS>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314813\" UPC=\"0088309617544\" SKU=\"ST414-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"2.96\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "    </OR_ORDER>\n" +
               "    <OR_ORDER REQUESTNUMBER=\"12844132\" ORDERNUMBER=\"8890124219323\">\n" +
               "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "      <OR_SHIPPING METHODCODE=\"MS\" CARRIERMETHODCODE=\"02\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EMAIL/>\n" +
               "      </OR_SHIPPING>\n" +
               "      <OR_BILLING ORDERPRICE=\"29.55\">\n" +
               "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
               "      </OR_BILLING>\n" +
               "      <OR_RETURNS TCNUMBER=\"75779899861044046732\" METHODCODE=\"RC\">\n" +
               "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
               "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
               "      </OR_RETURNS>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"14.77\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314806\" UPC=\"0088309617538\" SKU=\"ST412-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Slash Foot NoShow Sock\" QUANTITY=\"2\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"2.96\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"2\" LINEPRICE=\"14.77\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314810\" UPC=\"0088309617541\" SKU=\"ST413-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Colorblock NoShow Sock\" QUANTITY=\"2\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"2.77\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "    </OR_ORDER>\n" +
               "    <OR_ORDER REQUESTNUMBER=\"12844133\" ORDERNUMBER=\"8890124236706\">\n" +
               "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "      <OR_SHIPPING METHODCODE=\"MX\" CARRIERMETHODCODE=\"16\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EMAIL/>\n" +
               "      </OR_SHIPPING>\n" +
               "      <OR_BILLING ORDERPRICE=\"22.16\">\n" +
               "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
               "      </OR_BILLING>\n" +
               "      <OR_RETURNS TCNUMBER=\"06214044017935941225\" METHODCODE=\"RC\">\n" +
               "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
               "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
               "      </OR_RETURNS>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314818\" UPC=\"0088309617537\" SKU=\"ST611-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Slash Foot NoShow Sock\" QUANTITY=\"1\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"3.62\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"2\" LINEPRICE=\"7.39\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314823\" UPC=\"0088309617545\" SKU=\"ST514-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"3.54\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"3\" LINEPRICE=\"7.39\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314827\" UPC=\"0088309617546\" SKU=\"ST614-7BBAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free Black Colorblock NoShow Sock\" QUANTITY=\"1\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"3.89\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "    </OR_ORDER>\n" +
               "    <OR_ORDER REQUESTNUMBER=\"12844134\" ORDERNUMBER=\"8890124249824\">\n" +
               "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "      <OR_SHIPPING METHODCODE=\"MX\" CARRIERMETHODCODE=\"24\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EMAIL/>\n" +
               "      </OR_SHIPPING>\n" +
               "      <OR_BILLING ORDERPRICE=\"7.39\">\n" +
               "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
               "      </OR_BILLING>\n" +
               "      <OR_RETURNS TCNUMBER=\"71153933972480497546\" METHODCODE=\"RC\">\n" +
               "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
               "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
               "      </OR_RETURNS>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314807\" UPC=\"0088309617535\" SKU=\"ST411-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Slash Foot NoShow Sock\" QUANTITY=\"1\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"2.77\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "    </OR_ORDER>\n" +
               "    <OR_ORDER REQUESTNUMBER=\"12844135\" ORDERNUMBER=\"8890124289244\">\n" +
               "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "      <OR_SHIPPING METHODCODE=\"MS\" CARRIERMETHODCODE=\"67\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EMAIL/>\n" +
               "      </OR_SHIPPING>\n" +
               "      <OR_BILLING ORDERPRICE=\"7.39\">\n" +
               "        <OR_PAYMENT METHOD=\"Visa-2227\"/>\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
               "      </OR_BILLING>\n" +
               "      <OR_RETURNS TCNUMBER=\"63365755780261278924\" METHODCODE=\"RC\">\n" +
               "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
               "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
               "      </OR_RETURNS>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"7.39\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314814\" UPC=\"0088309617536\" SKU=\"ST511-7BWAWMC\" DESCRIPTION=\"Starter Boys 6-pair+1-pair free White Slash Foot NoShow Sock\" QUANTITY=\"1\"/>\n" +
               "        <OR_PRICE RETAIL=\"4.97\" TAX=\"0.45\" SHIPPING=\"1.97\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"3.30\"/>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "    </OR_ORDER>\n" +
               "    <OR_ORDER REQUESTNUMBER=\"12844136\" ORDERNUMBER=\"8890124311425\">\n" +
               "      <OR_DATEPLACED DAY=\"06\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "      <OR_SHIPPING METHODCODE=\"MP\" CARRIERMETHODCODE=\"22\" STORENUMBER=\"\" TOGETHERCODE=\"SC\">\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_DELIVERYDATE DAY=\"16\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EXPECTEDSHIPDATE DAY=\"09\" MONTH=\"11\" YEAR=\"2015\"/>\n" +
               "        <OR_EMAIL/>\n" +
               "      </OR_SHIPPING>\n" +
               "      <OR_BILLING ORDERPRICE=\"0\">\n" +
               "        <OR_PAYMENT METHOD=\"Gift Receipt\"/>\n" +
               "        <OR_PHONE PRIMARY=\"6507947054\" PRIMARYEXT=\"\" SECOND=\"\" SECONDEXT=\"\"/>\n" +
               "        <OR_POSTAL NAME=\"Ivy Tee\" ADDRESS1=\"850 Cherry Ave\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"San Bruno\" STATE=\"CA\" POSTALCODE=\"94066\" COUNTRY=\"USA\"/>\n" +
               "        <OR_EMAIL>itee@walmartlabs.com</OR_EMAIL>\n" +
               "      </OR_BILLING>\n" +
               "      <OR_RETURNS TCNUMBER=\"25579899861043086702\" METHODCODE=\"RC\">\n" +
               "        <OR_POSTAL NAME=\" \" ADDRESS1=\"\" ADDRESS2=\"\" ADDRESS3=\"\" ADDRESS4=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\" COUNTRY=\"\"/>\n" +
               "        <OR_PERMIT NUMBER=\"\" CITY=\"\" STATE=\"\" POSTALCODE=\"\"/>\n" +
               "      </OR_RETURNS>\n" +
               "      <OR_ORDERLINE LINENUMBER=\"1\" LINEPRICE=\"0\">\n" +
               "        <OR_ITEM ITEMNUMBER=\"38314814\" UPC=\"0088309617536\" SKU=\"ST511-7BWAWMC\" DESCRIPTION=\"A Gift for You\" QUANTITY=\"1\"/>\n" +
               "        <OR_PRICE RETAIL=\"0\" TAX=\"0\" SHIPPING=\"0\">\n" +
               "        </OR_PRICE>\n" +
               "        <OR_COST AMOUNT=\"3.30\"/>\n" +
               "        <OR_VAS SEQUENCE=\"1\" VASCODE=\"VGM\">\n" +
               "          <OR_VASDATA NAME=\"LINE1\" VALUE=\"This is sample gift message 1\"/>\n" +
               "          <OR_VASDATA NAME=\"LINE2\" VALUE=\"This is sample gift message 2\"/>\n" +
               "          <OR_VASDATA NAME=\"LINE3\" VALUE=\"This is sample gift message 3\"/>\n" +
               "          <OR_VASDATA NAME=\"LINE4\" VALUE=\"\"/>\n" +
               "        </OR_VAS>\n" +
               "      </OR_ORDERLINE>\n" +
               "      <OR_LASTDELIVERYMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_MARKETINGMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "      <OR_RETURNSMSG LINE1=\"0\" LINE2=\"0\" LINE3=\"0\" LINE4=\"0\"/>\n" +
               "    </OR_ORDER>\n" +
               "  </WMIORDERREQUEST>\n" +
               "</WMI>"
        WalmartDsvApi api = new WalmartDsvApi("", "", "", "272112", "Gildan")

        api.importOrderFile(src)


    }
}
