package com.owd.dc.packing;

import org.apache.struts.action.ActionForm;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.owd.dc.setup.selectList;

/**
 * Created by IntelliJ IDEA.
 * User: Danny
 * Date: Feb 9, 2008
 * Time: 8:43:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class batchPackForm extends ActionForm {
    private List<selectList> boxes;
    private String boxId;
    private String boxDescription;
    private List<String> doneOrders;
    private String lastOrder;
    private Map<String,Map> boxInfo;
    private String depth;
    private String width;
    private String height;
    private String cost;
    private String orderNum;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }


    public Map<String, Map> getBoxInfo() {
        return boxInfo;
    }

    public void setBoxInfo(Map<String, Map> boxInfo) {
        this.boxInfo = boxInfo;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public List<selectList> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<selectList> boxes) {
        this.boxes = boxes;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getBoxDescription() {
        return boxDescription;
    }

    public void setBoxDescription(String boxDescription) {
        this.boxDescription = boxDescription;
    }

    public List<String> getDoneOrders() {
        return doneOrders;
    }

    public void setDoneOrders(List<String> doneOrders) {
        this.doneOrders = doneOrders;
    }

    public String getLastOrder() {
        return lastOrder;
    }

    public void setLastOrder(String lastOrder) {
        this.lastOrder = lastOrder;
    }
}
