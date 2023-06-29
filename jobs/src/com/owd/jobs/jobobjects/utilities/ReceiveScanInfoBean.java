package com.owd.jobs.jobobjects.utilities;

/**
 * Created by IntelliJ IDEA.
 * User: stewartbuskirk
 * Date: Jan 16, 2007
 * Time: 10:02:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReceiveScanInfoBean extends ScanInfoBean {

      private String asnId;
    private Integer rcvId;

       public String getAsnId() {
        return asnId;
    }

    public void setAsnId(String asnId) {
        this.asnId = asnId;
    }

    public Integer getRcvId() {
        return rcvId;
    }

    public void setRcvId(Integer rcvId) {
        this.rcvId = rcvId;
    }

    public String getFileName(){
        return  getRcvId() + "_" + getDate() + "_" + getTime() + ".pdf";
    }

}
