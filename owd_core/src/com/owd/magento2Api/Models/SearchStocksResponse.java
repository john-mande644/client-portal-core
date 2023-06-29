package com.owd.magento2Api.Models;

import java.util.List;

public class SearchStocksResponse {
    private List<Stock> items = null;

    public List<Stock> getItems() {
        return items;
    }

    public void setItems(List<Stock> items) {
        this.items = items;
    }
}
