package com.owd.web.internal.warehouse.client;

public class flatRateDimBean {

    private Integer dimId;
    private Integer clientFkey;
    private Integer shipServiceId;
    private Integer dim;
    private String levelName;
    private String levelCode;
    private Integer levelId;

    public Integer getDimId() {
        return dimId;
    }

    public void setDimId(Integer dimId) {
        this.dimId = dimId;
    }

    public Integer getClientFkey() {
        return clientFkey;
    }

    public void setClientFkey(Integer clientFkey) {
        this.clientFkey = clientFkey;
    }

    public Integer getShipServiceId() {
        return shipServiceId;
    }

    public void setShipServiceId(Integer shipServiceId) {
        this.shipServiceId = shipServiceId;
    }

    public Integer getDim() {
        return dim;
    }

    public void setDim(Integer dim) {
        this.dim = dim;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }
}
