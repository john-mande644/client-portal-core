package com.owd.web.internal.zplutils;

public class Carton {
    private final int qty, num;
    private final String itemNum;
    private final boolean partial;
    public Carton(String itemNum, int qty, int num, boolean partial) {
        this.itemNum = itemNum;
        this.qty = qty;
        this.num = num;
        this.partial = partial;
    }

    public int getQty() {
        return qty;
    }

    public int getNum() {
        return num;
    }

    public String getItemNum() {
        return itemNum;
    }

    public boolean isPartial() {
        return partial;
    }
}
