package com.owd.alittlePlaying.symphonyAsnIssues;

import java.util.List;

/**
 * Created by danny on 10/7/2016.
 */
public class clientItem {

    public String clientASN;
    public String sku;
    public int expected;
    public int clientTotal;

    public List<owdInfo> receivedList;


    public clientItem(String kClientASN, String ksku, int kexpected, int kclientTotal){
        clientASN = kClientASN;
        sku = ksku;
        expected = kexpected;
        clientTotal = kclientTotal;


    }

    public String getClientASN() {
        return clientASN;
    }

    public void setClientASN(String clientASN) {
        this.clientASN = clientASN;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getExpected() {
        return expected;
    }

    public void setExpected(int expected) {
        this.expected = expected;
    }

    public int getClientTotal() {
        return clientTotal;
    }

    public void setClientTotal(int clientTotal) {
        this.clientTotal = clientTotal;
    }

    public List<owdInfo> getReceivedList() {
        return receivedList;
    }

    public void setReceivedList(List<owdInfo> receivedList) {
        this.receivedList = receivedList;
    }
}
