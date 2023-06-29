package com.owd.dc.packing.boxSealerWorkflow;

import com.owd.dc.packing.beans.boxBean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 7/29/2019.
 */
public class boxSealerBoxes {


    String status;
    String error = "";
    List<boxSealerBean> boxes = new ArrayList<>();


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<boxSealerBean> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<boxSealerBean> boxes) {
        this.boxes = boxes;
    }
}
