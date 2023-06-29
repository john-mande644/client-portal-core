package com.owd.magento2Api.Models;

import java.util.List;

public class StockExtentionAttributes {
    private List<SalesChannel> sales_channels;

    public List<SalesChannel> getSales_channels() {
        return sales_channels;
    }

    public void setSales_channels(List<SalesChannel> sales_channels) {
        this.sales_channels = sales_channels;
    }
}
