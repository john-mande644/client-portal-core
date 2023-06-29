package com.owd.web.api;

/**
 * Created by stewartbuskirk1 on 2/19/16.
 */
public class AsnData {
    String asnId;
    String asnRef;
    String poRef;

    public AsnData(String asnId, String asnRef, String poRef) {
        this.asnId = asnId;
        this.asnRef = asnRef;
        this.poRef = poRef;
    }
}
