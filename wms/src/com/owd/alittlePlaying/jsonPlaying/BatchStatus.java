package com.owd.alittlePlaying.jsonPlaying;

/**
 * Created by danny on 7/16/2019.
 */
public class BatchStatus {

    private String id;
    private String total;
    private String created;
    private int printed;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getPrinted() {
        return printed;
    }

    public void setPrinted(int printed) {
        this.printed = printed;
    }
}
