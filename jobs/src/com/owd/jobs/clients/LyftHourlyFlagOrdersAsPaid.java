package com.owd.jobs.clients;

import java.util.*;

import com.google.gson.Gson;
import com.owd.core.api.shopify.orderResponse.Order;
import com.owd.core.api.shopify.orderResponse.OrderResponse;
import com.owd.core.api.shopify.transationResponse.Transaction;
import com.owd.core.api.shopify.transationResponse.TransactionResponse;
import com.owd.core.api.shopify.transationResponse.TransactionRequest;
import com.owd.jobs.OWDStatefulJob;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by stewartbuskirk1 on 1/4/16.
 */
public class LyftHourlyFlagOrdersAsPaid extends OWDStatefulJob {

    private String transactionUrl = "https://lyftb2b.myshopify.com/admin/orders/:order_id/transactions.json";
    private String orderUrl = "https://lyftb2b.myshopify.com/admin/orders.json?financial_status=pending";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client = new OkHttpClient();

    private String token = Base64.getEncoder().encodeToString("5840d532a81e7371716e56a5e35c7370:8616ec25c0b77a5393472459a6edeb2b".getBytes());

    private final static Logger log =  LogManager.getLogger();
    private Gson gson = new Gson();

    public static void main(String[] args) {
/*
        List<ReportDefinition> jobList = new ArrayList<ReportDefinition>();
        ReportDefinition rd = new DailyFijiCountReportDefinition("DC7");

        jobList.add(rd);

        ExcelUtils.deliverReports(jobList);
       */
        run();
    }

    public void internalExecute() {

        try {
            String respStr = pullUnpaidShopifyOrders();
            log.debug("Lets return some data");
            log.debug(respStr);
            if(respStr != null){
                OrderResponse resp = gson.fromJson(respStr,OrderResponse.class);
                for(Order order: resp.getOrders()){
                    log.debug(gson.toJson(order));
                    updateTransactions(order);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private String pullUnpaidShopifyOrders() {
        try {
            Request request = new Request.Builder()
                    .url(orderUrl)
                    .header("Authorization", "Basic " + token)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();

            return response.body().string();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private void updateTransactions(Order order){
        try {
            Request request = new Request.Builder()
                    .url(transactionUrl.replace(":order_id",String.valueOf(order.getId())))
                    .header("Authorization", "Basic " + token)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            TransactionResponse resp = gson.fromJson(response.body().string(),TransactionResponse.class);
            if(resp.getTransactions().size() > 0) {
                Transaction trans = resp.getTransactions().get(0);
                log.debug(gson.toJson(trans));
                Transaction payment = new Transaction();
                payment.setParentId(trans.getId());
                payment.setCurrency("USD");
                payment.setAuthorization(UUID.randomUUID().toString());
                payment.setKind("capture");
                TransactionRequest transactionRequest = new TransactionRequest();
                transactionRequest.setTransaction(payment);
                log.debug(gson.toJson(transactionRequest));
                Request paymentRequest = new Request.Builder()
                        .url(transactionUrl.replace(":order_id",String.valueOf(order.getId())))
                        .header("Authorization", "Basic " + token)
                        .post(RequestBody.create(JSON, gson.toJson(transactionRequest)))
                        .build();
               Response paymentResponse = client.newCall(paymentRequest).execute();
               log.debug(paymentResponse.body().string());

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
