import beans.OrderScanInfoBean;
import com.owd.OWDShippingAPI.Returns.ReturnUtilities;
import com.owd.core.managers.JasperReportPrintManager;
import com.owd.core.managers.ScanManager;
import com.owd.fedEx.SmartPostReturn;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdOrder;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.hibernate.Query;

import javax.imageio.ImageIO;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 4/10/14
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class attachPDFtoOrder {
    public static void main(String[] args){
        try{
      /*  FileInputStream in = new FileInputStream("c:\\tmp\\7205212.pdf");
              System.out.println(in.available());
            OrderScanInfoBean rb = new OrderScanInfoBean();
            rb.setFile(in);
            System.out.println(rb.getFileByte());*/
           //   File file = new File("c:\\tmp\\7205212.pdf");
           // file.delete()

                  OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,11121683);
            System.out.println(order.getOrderNum());


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static boolean savePDFtoOrderViaOrderNumByteArray(String orderNum, byte[] data) throws Exception{
      boolean success = true;
           System.out.println("in attach via orderNum bye");
          success = savePDFtoOrderInputStream(orderNum, new ByteArrayInputStream(data));
        return success;
    }
    public static boolean savePDFtoOrderFile(String orderNum, String filePath) throws Exception{

     //   throw new Exception("just testing");
        return savePDFtoOrderInputStream(orderNum, new FileInputStream(filePath));
    }

    public static boolean savePDFtoOrderInputStream(String orderNum,InputStream in) throws Exception{
        OrderScanInfoBean rb = new OrderScanInfoBean();
        boolean success = true;
        rb.setFile(in);
        PreparedStatement ps = HibernateSession.getPreparedStatement("select order_id from owd_order where order_num=?");
        ps.setString(1,orderNum);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            rb.setOrderId(rs.getString(1));
        }
        rs.close();
        HibernateSession.closeSession();
        System.out.println("rb.id="+rb.getOrderId());
        OwdOrder order =null;
        try
        {
            order = (OwdOrder) HibernateSession.currentSession().load((OwdOrder.class), new Integer(rb.getOrderId()));
            if (order==null) {
                throw new Exception("noOrderMatch");

            }

            if (!order.getOrderId().toString().equals(rb.getOrderId())) {
                throw new Exception("noOrderMatch");

            }
        }catch(Exception exn)
        {
            exn.printStackTrace();
            throw new Exception("order ID invalid or not found");
        }

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        DateFormat tf = new SimpleDateFormat("HH:mm:ss.SSS");

        rb.setDate(df.format(cal.getTime()));
        rb.setTime(tf.format(cal.getTime()));
       int newOrders = saveScan(rb, order);
        if(newOrders != 1){
            throw new Exception("Problem saving the PDF to order");
        }
        HibUtils.commit(HibernateSession.currentSession());
       return success;
    }
    public static boolean savePDFtoOrderByteArray(Integer printID,byte[] pdf) throws Exception{
        OrderScanInfoBean rb = new OrderScanInfoBean();
        boolean success = true;
        rb.setPdf(pdf);
        String sql = "SELECT\n" +
                "    dbo.owd_print_queue3.order_id,\n" +
                "    dbo.owd_order.order_num\n" +
                "FROM\n" +
                "    dbo.owd_print_queue3\n" +
                "INNER JOIN dbo.owd_order\n" +
                "ON\n" +
                "    (\n" +
                "        dbo.owd_print_queue3.order_id = dbo.owd_order.order_id\n" +
                "    )\n" +
                "WHERE\n" +
                "    dbo.owd_print_queue3.print_queue_id = :printID ;";
        Query q =  HibernateSession.currentSession().createSQLQuery(sql);
        q.setParameter("printID",printID);
        List results = q.list();
        if(results.size()>0){
            Object data = results.get(0);
            Object[] row = (Object[]) data;

             rb.setOrderId(row[0].toString());

        } else{
            throw new Exception("Unable to lookup order Number via print ID");
        }

       /* PreparedStatement ps = HibernateSession.getPreparedStatement("select order_id from owd_order where order_num=?");
        ps.setString(1,orderNum);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
        {
            rb.setOrderId(rs.getString(1));
        }
        rs.close();*/
        HibernateSession.closeSession();
        System.out.println("rb.id="+rb.getOrderId());
        OwdOrder order =null;
        try
        {
            order = (OwdOrder) HibernateSession.currentSession().load((OwdOrder.class), new Integer(rb.getOrderId()));
            if (order==null) {
                throw new Exception("noOrderMatch");

            }

            if (!order.getOrderId().toString().equals(rb.getOrderId())) {
                throw new Exception("noOrderMatch");

            }
        }catch(Exception exn)
        {
            throw new Exception("order ID invalid or not found");
        }

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        DateFormat tf = new SimpleDateFormat("HH:mm:ss.SSS");

        rb.setDate(df.format(cal.getTime()));
        rb.setTime(tf.format(cal.getTime()));
        int newOrders = saveScan(rb, order);
        if(newOrders != 1){
            throw new Exception("Problem saving the PDF to order");
        }
        HibUtils.commit(HibernateSession.currentSession());
        return success;
    }

    private static int saveScan(OrderScanInfoBean rb, OwdOrder order) throws Exception {
        int success = 0;

        byte[] report = null;
        if(SmartPostReturn.doesOrderNeedReturnLabel(order)==1){
            byte[] returnlabel = SmartPostReturn.getLabelFromAmazonBucket(order);
            if(returnlabel.length>0) {
                report = JasperReportPrintManager.getReturnTemplatePdf(order.getOrderNum(), ImageIO.read(new ByteArrayInputStream(returnlabel)));
            }

        }
        if(ReturnUtilities.doesOrderNeedMIReturnLabel(order)==1){
            byte[] returnlabel = SmartPostReturn.getLabelFromAmazonBucket(order);
            if(returnlabel.length>0) {
                report = JasperReportPrintManager.getMIReturnTemplatePdf(order.getOrderNum(), ImageIO.read(new ByteArrayInputStream(returnlabel)));
            }
        }

        if(null != rb.getPdf()){
            if(null!= report && report.length>1){
                PDFMergerUtility mergerUtility = new PDFMergerUtility();

                mergerUtility.addSource(new ByteArrayInputStream(rb.getPdf()));
                mergerUtility.addSource(new ByteArrayInputStream(report));
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                mergerUtility.setDestinationStream(os);
                System.out.println("Merging Documents");
                mergerUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

                ScanManager.addScanToOwdOrder(order, os.toByteArray(), rb.getFileName(), "");
                success = 1;
                return success;
            }else {
                ScanManager.addScanToOwdOrder(order, rb.getPdf(), rb.getFileName(), "");
                success = 1;
                return success;
            }
        }

        System.out.println("WE are going to do the byte array");
        if(null!= report && report.length>1) {
            PDFMergerUtility mergerUtility = new PDFMergerUtility();

            mergerUtility.addSource(new ByteArrayInputStream(rb.getFileByte()));
            mergerUtility.addSource(new ByteArrayInputStream(report));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            mergerUtility.setDestinationStream(os);
            System.out.println("Merging Documents");
            mergerUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
            ScanManager.addScanToOwdOrder(order, os.toByteArray(), rb.getFileName(), "");
        }else {
            ScanManager.addScanToOwdOrder(order, rb.getFileByte(), rb.getFileName(), "");
        }


        success = 1;
        return success;
    }
}
