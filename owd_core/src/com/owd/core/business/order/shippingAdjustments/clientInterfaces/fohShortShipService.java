package com.owd.core.business.order.shippingAdjustments.clientInterfaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owd.core.OWDUtilities;
import com.owd.core.business.order.OrderUtilities;
import com.owd.core.business.order.beans.shortShipBean;
import com.owd.core.business.order.shippingAdjustments.clientInterfaces.dataBeans.FOHLine;
import com.owd.core.business.order.shippingAdjustments.clientInterfaces.dataBeans.FOHShortShipData;
import com.owd.core.managers.JasperReportPrintManager;
import com.owd.core.managers.ScanManager;
import com.owd.fedEx.SmartPostReturn;
import com.owd.hibernate.HibUtils;
import com.owd.hibernate.HibernateSession;
import com.owd.hibernate.generated.OwdLineItem;
import com.owd.hibernate.generated.OwdOrder;
import com.owd.hibernate.generated.ScanOrder;
import com.owd.hibernate.generated.Scandoc;
import okhttp3.*;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

/**
 * Created by danny on 9/2/2019.
 */
public class fohShortShipService {

    public static String getJsonBody(List<shortShipBean> items, Integer clientId, Integer orderId)throws Exception{

        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
        FOHShortShipData data = new FOHShortShipData();
        data.setOrder_reference(order.getOrderRefnum());

        data.setIdempotency_key(Calendar.getInstance().getTimeInMillis()+"");
        List<FOHLine> lines = new ArrayList<>();
        for(shortShipBean ss:items){
            OwdLineItem item = (OwdLineItem)HibernateSession.currentSession().load(OwdLineItem.class,ss.getLineItemId());
            FOHLine l = new FOHLine();
            l.setLine_number(Integer.parseInt(item.getCustRefnum()));
            l.setQuantity(ss.getQtyToAdjust());
            l.setSku(item.getInventoryNum());
            l.setDescription(item.getDescription());
            l.setDeclared_value(item.getDecItemValue());
            l.setCustoms_desc(item.getCustomsDesc());
            lines.add(l);
        }
        data.setLine_items(lines);

        GsonBuilder builder = new GsonBuilder();
        // builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(data);

    }


    public static boolean processFOHShortShip(List<shortShipBean> items, Integer clientId, Integer orderId) throws Exception{



            OkHttpClient client = new OkHttpClient();
            String requestBody = getJsonBody(items,clientId,orderId);

            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, requestBody);
            Request request = new Request.Builder()
                    .url("https://api.symphonycommerce.com/v0/partially-shipped/shipments")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", "AIYX5nPpLHPQ9Rd6pNYrANsNi9J9EFQf")
                    .addHeader("cache-control", "no-cache")

                    .build();

            Response response = client.newCall(request).execute();

        if(!response.isSuccessful()){
            String messge = response.message();
            response.close();
            throw new Exception(messge);
        }
        System.out.println(response.message());
        response.close();









        return true;
    }

    public static void main(String[] args){
        try{
          /*  List<shortShipBean> lines = new ArrayList<>();
            shortShipBean s = new shortShipBean();
            s.setLineItemId(40339689);
            s.setReason("stockout");
            s.setQtyToAdjust(1);
            lines.add(s);
            processFOHShortShip(lines,640,19147957);*/
            processPackingSlip(19147957);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String processPackingSlip(Integer orderId) throws Exception{
        String url = "";
        OwdOrder order = (OwdOrder) HibernateSession.currentSession().load(OwdOrder.class,orderId);
        byte[] pdfdata = OrderReleaseAPI.getOrderReleaseApprovalAndPackingSlip("https://manage.symphonycommerce.com/fulfill", orderId, true,
                new LineItemVerifier() {

                    @Override
                    public boolean includeLineItemInRequest(OwdLineItem line) {
                        return line.getCustRefnum().length() > 0;
                    }

                }
        );
        byte[] report = null;
        if(SmartPostReturn.doesOrderNeedReturnLabel(order)==1){
            byte[] returnlabel = SmartPostReturn.getLabelFromAmazonBucket(order);
            if(returnlabel.length>0) {
                report = JasperReportPrintManager.getReturnTemplatePdf(order.getOrderNum(), ImageIO.read(new ByteArrayInputStream(returnlabel)));
            }

        }

        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        DateFormat tf = new SimpleDateFormat("HH:mm:ss.SSS");



        String filename = order.getOrderId() +"_"+df.format(cal.getTime())+"_"+tf.format(cal.getTime())+".pdf";

        if(null != pdfdata){
            if(null!= report && report.length>1){
                PDFMergerUtility mergerUtility = new PDFMergerUtility();

                mergerUtility.addSource(new ByteArrayInputStream(pdfdata));
                mergerUtility.addSource(new ByteArrayInputStream(report));
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                mergerUtility.setDestinationStream(os);
                System.out.println("Merging Documents");
                System.out.println(filename);
                mergerUtility.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
                pdfdata = os.toByteArray();
                ScanManager.addScanToOwdOrder(order, pdfdata, filename, "");

            }else {
                ScanManager.addScanToOwdOrder(order, pdfdata, filename, "");

            }
            String sql = "update owd_print_queue_shortShip set pdf_data = :pdf where order_id = :order_id and pdf_data is null";
            Query q = HibernateSession.currentSession().createSQLQuery(sql);
            q.setParameter("pdf",pdfdata);
            q.setParameter("order_id",orderId);
            q.executeUpdate();

        }

        HibUtils.commit(HibernateSession.currentSession());
        HibernateSession.currentSession().flush();
        HibernateSession.currentSession().refresh(order);

        Criteria criteria = HibernateSession.currentSession().createCriteria(ScanOrder.class);
        criteria.add(Restrictions.eq("order", order));
        criteria.setMaxResults(1);
        criteria.addOrder(Order.desc("name"));

        List results = criteria.list();

        ScanOrder s = (ScanOrder) results.get(0);



            if(s.getName().toUpperCase().endsWith("PDF")) {
               url=  "https://service.owd.com/webapps/extranet/extranet.jsp?ordermgr=get-scan&oid=" + order.getOrderId() + "&auth=" + URLEncoder.encode(OWDUtilities.encryptData(order.getOrderId() + "/" + s.getName() + "/" + order.getClientFkey()),"UTF-8");
            }

        return url;
    }
}
