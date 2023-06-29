package com.owd.magento2Api.Models;
import java.util.List;

public class PostSourceItemsRequest {
    private List<SourceItem> sourceItems;

    public List<SourceItem> getSourceItems() {
        return sourceItems;
    }

    public void setSourceItems(List<SourceItem> sourceItems) {
        this.sourceItems = sourceItems;
    }
}
