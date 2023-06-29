package com.owd.dc.manifest.Manifestxml;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Jun 8, 2010
 * Time: 2:39:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class dispatchIdList {

    private List<dispatch> DispatchList = new ArrayList<dispatch>();

    public List<dispatch> getDispatchList() {
        return DispatchList;
    }

    public void setDispatchList(List<dispatch> dispatchList) {
        DispatchList = dispatchList;
    }

    public static class dispatch{
        String dispatchId;
        String createdDate;

        public String getDispatchId() {
            return dispatchId;
        }

        public void setDispatchId(String dispatchId) {
            this.dispatchId = dispatchId;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }
    }
    public static XStream getXStream(){
               XStream x = new XStream();
                                              x.alias("Dispatch",dispatchIdList.dispatch.class);
                                             x.alias("OWDDispatchList",dispatchIdList.class);
        return x;
    }

}
