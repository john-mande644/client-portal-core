package com.owd.dc.warehouse.statusScroller;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 5/16/14
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class statusScrollerData {
    private List<statusOrderSummary> orderSummaryByClient;
    private int SumClientCount =0;
    private List<statusOrderSummary> orderSummaryByMethod;
    private int SumMethodCount =0;
    private List<statusOrderSummary> futureOrderSummaryByClient;
    private int futureSumClientCount =0;
    private List<String> announceList;
    private String loadedDate;



    public String getLoadedDate() {
        return loadedDate;
    }

    public void setLoadedDate(String loadedDate) {
        this.loadedDate = loadedDate;
    }

    public List<String> getAnnounceList() {
        return announceList;
    }

    public void setAnnounceList(List<String> announceList) {
        this.announceList = announceList;
    }

    public List<statusOrderSummary> getOrderSummaryByClient() {
        return orderSummaryByClient;
    }

    public void setOrderSummaryByClient(List<statusOrderSummary> orderSummaryByClient) {
        this.orderSummaryByClient = orderSummaryByClient;
    }

    public List<statusOrderSummary> getOrderSummaryByMethod() {
        return orderSummaryByMethod;
    }

    public void setOrderSummaryByMethod(List<statusOrderSummary> orderSummaryByMethod) {
        this.orderSummaryByMethod = orderSummaryByMethod;
    }

    public List<statusOrderSummary> getFutureOrderSummaryByClient() {
        return futureOrderSummaryByClient;
    }

    public void setFutureOrderSummaryByClient(List<statusOrderSummary> futureOrderSummaryByClient) {
        this.futureOrderSummaryByClient = futureOrderSummaryByClient;


    }

    public int getSumClientCount() {
        return SumClientCount;
    }

    public void setSumClientCount(int sumClientCount) {
        SumClientCount = sumClientCount;
    }

    public int getSumMethodCount() {
        return SumMethodCount;
    }

    public void setSumMethodCount(int sumMethodCount) {
        SumMethodCount = sumMethodCount;
    }

    public int getFutureSumClientCount() {
        return futureSumClientCount;
    }

    public void setFutureSumClientCount(int futureSumClientCount) {
        this.futureSumClientCount = futureSumClientCount;
    }
}
