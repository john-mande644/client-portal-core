package com.owd.magento2Api.Models;

import java.util.List;

public class SearchStockSourceLinkResponse {
    private List<StockSourceLink> items;

    public List<StockSourceLink> getItems() {
        return items;
    }

    public void setItems(List<StockSourceLink> items) {
        this.items = items;
    }
}
