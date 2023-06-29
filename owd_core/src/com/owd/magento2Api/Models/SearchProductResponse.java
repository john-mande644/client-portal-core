package com.owd.magento2Api.Models;

import java.util.List;

public class SearchProductResponse {
    private List<Product> items;

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
