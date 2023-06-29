
package com.owd.jobs.jobobjects.easypost.tracking;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.owd.hibernate.generated.OwdOrderTrackEvents;
import org.apache.commons.lang.builder.ToStringBuilder;

import static java.util.Objects.isNull;

public class Message {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("previous_attributes")
    @Expose
    private PreviousAttributes previousAttributes;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("pending_urls")
    @Expose
    private List<String> pendingUrls = null;
    @SerializedName("completed_urls")
    @Expose
    private List<Object> completedUrls = null;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("object")
    @Expose
    private String object;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public PreviousAttributes getPreviousAttributes() {
        return previousAttributes;
    }

    public void setPreviousAttributes(PreviousAttributes previousAttributes) {
        this.previousAttributes = previousAttributes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getPendingUrls() {
        return pendingUrls;
    }

    public void setPendingUrls(List<String> pendingUrls) {
        this.pendingUrls = pendingUrls;
    }

    public List<Object> getCompletedUrls() {
        return completedUrls;
    }

    public void setCompletedUrls(List<Object> completedUrls) {
        this.completedUrls = completedUrls;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public ArrayList<OwdOrderTrackEvents> getOwdOrderTrackingEvents(){
        ArrayList<OwdOrderTrackEvents> events = new ArrayList<>();
        DateFormat DF = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss");
        for(int x = 0; x < this.getResult().getTrackingDetails().size(); x++){

            OwdOrderTrackEvents event = new OwdOrderTrackEvents();
            TrackingDetail detail = this.getResult().getTrackingDetails().get(x);
            try {
                if(!isNull(this.getCreatedAt())){
                    event.setCreatedAt(getDateFromInstant(Instant.parse(this.getCreatedAt())));
                }else{
                    event.setCreatedAt(null);
                }
                if(!isNull(this.getUpdatedAt())){
                    event.setUpdatedAt(getDateFromInstant(Instant.parse(this.getUpdatedAt())));
                }else{
                    event.setUpdatedAt(null);
                }
                if(!isNull(detail.getDatetime())){
                    event.setEventAt(getDateFromInstant(Instant.parse(detail.getDatetime())));
                }else{
                    event.setEventAt(null);
                }
                if(!isNull(this.getResult().getEstDeliveryDate())){
                    event.setEstDeliveryDate(getDateFromInstant(Instant.parse(this.getResult().getEstDeliveryDate())));
                }else{
                    event.setEstDeliveryDate(null);
                }
            }catch(Exception e){
                System.out.println("Created At" + this.getCreatedAt());
                System.out.println("Updated At" + this.getUpdatedAt());
                System.out.println("Event At" + detail.getDatetime());
                System.out.println("Estimated DeliveryDate" + this.getResult().getEstDeliveryDate());
                e.printStackTrace();
            }
            event.setTrackingId(this.getResult().getId());
            event.setMessage(detail.getMessage());
            event.setStatus(detail.getStatus());
            event.setSource(detail.getSource());
            event.setCarrierCode(detail.getCarrierCode());
            event.setCity(detail.getTrackingLocation().getCity());
            event.setState(detail.getTrackingLocation().getState());
            event.setCountry(detail.getTrackingLocation().getCountry());
            event.setZip(detail.getTrackingLocation().getZip());
            events.add(event);
        }
        return events;
    }
    private Date getDateFromInstant(Instant instant){
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("result", result).append("description", description).append("mode", mode).append("previousAttributes", previousAttributes).append("createdAt", createdAt).append("pendingUrls", pendingUrls).append("completedUrls", completedUrls).append("updatedAt", updatedAt).append("id", id).append("userId", userId).append("status", status).append("object", object).toString();
    }

}
