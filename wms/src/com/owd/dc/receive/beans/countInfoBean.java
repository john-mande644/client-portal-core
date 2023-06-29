package com.owd.dc.receive.beans;

/**
 * Created by danny on 7/21/2015.
 */
public class countInfoBean {
    private Integer qtyReceiveTotal;
    private Integer qtyReceiveEmployee;
    private Integer qtyDamagedTotal;
    private Integer qtyDamagedEmployee;

    public Integer getQtyReceiveTotal() {
        return qtyReceiveTotal;
    }

    public void setQtyReceiveTotal(Integer qtyReceiveTotal) {
        this.qtyReceiveTotal = qtyReceiveTotal;
    }

    public Integer getQtyReceiveEmployee() {
        return qtyReceiveEmployee;
    }

    public void setQtyReceiveEmployee(Integer qtyReceiveEmployee) {
        this.qtyReceiveEmployee = qtyReceiveEmployee;
    }

    public Integer getQtyDamagedTotal() {
        return qtyDamagedTotal;
    }

    public void setQtyDamagedTotal(Integer qtyDamagedTotal) {
        this.qtyDamagedTotal = qtyDamagedTotal;
    }

    public Integer getQtyDamagedEmployee() {
        return qtyDamagedEmployee;
    }

    public void setQtyDamagedEmployee(Integer qtyDamagedEmployee) {
        this.qtyDamagedEmployee = qtyDamagedEmployee;
    }
}
