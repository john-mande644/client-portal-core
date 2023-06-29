package com.owd.dc.beans;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: 10/20/11
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class jsonLabelMakerInventoryBean {

    private String identifier = "id";
    private String error = "";
    private List<labelMakerInventoryBean> items;

    public List<labelMakerInventoryBean> getItems() {
        return items;
    }

    public void setItems(List<labelMakerInventoryBean> items) {
        this.items = items;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class labelMakerInventoryBean {
        private String id;
        private String sku;
        private String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }
}
