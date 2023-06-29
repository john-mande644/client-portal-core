package com.owd.alittlePlaying.jsonPlaying;

import java.util.List;

/**
 * Created by danny on 7/16/2019.
 */
public class BatchPackData {
    String status;
    String errerMessage;
    BatchStatus batchStatus;
    List<batchLabel> labels;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrerMessage() {
        return errerMessage;
    }

    public void setErrerMessage(String errerMessage) {
        this.errerMessage = errerMessage;
    }

    public BatchStatus getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(BatchStatus batchStatus) {
        this.batchStatus = batchStatus;
    }

    public List<batchLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<batchLabel> labels) {
        this.labels = labels;
    }
}
