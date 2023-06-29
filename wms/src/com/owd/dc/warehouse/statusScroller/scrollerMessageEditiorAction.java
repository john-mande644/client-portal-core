package com.owd.dc.warehouse.statusScroller;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

import java.io.InputStream;
import java.io.StringBufferInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: danny
 * Date: 5/27/14
 * Time: 5:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class scrollerMessageEditiorAction extends ActionSupport{
    private Object jsonModel;
    private String facility;
    private String priority;
    private String StartDate;
    private String EndDate;
    private String message;

    private String value;
    private String id;
    private int columnId;
    private String columnPosition;
    private String rowId;
    private InputStream inputStream;


    public String messages(){
       try{

       } catch (Exception e){

       }

        return "success";
    }
    public String addRecord(){
        try{
            String id = statusScrollerUtils.addMessage(priority,StartDate,EndDate,message,facility);

            inputStream = new StringBufferInputStream(id);

        }  catch (Exception e){
            inputStream = new StringBufferInputStream(e.getMessage());
        }

        return "success";
    }
    public String deleteRecord(){
            try{
                boolean deleted = statusScrollerUtils.deleteMessage(id);

                if(deleted){

                inputStream = new StringBufferInputStream("ok");
                }else{
                    inputStream = new StringBufferInputStream("We were unable to make the edit, try again");
                }
            }catch (Exception e){
                inputStream = new StringBufferInputStream(e.getMessage());
            }

        return "success";
    }
    public String updateTable(){
        try{
             System.out.println(id);
            System.out.println(value);
            System.out.println(columnId);
            System.out.println(columnPosition);
            System.out.println(rowId);
            boolean updated = statusScrollerUtils.updateMessageRecord(id,columnId,value);
            if (updated){
                inputStream = new StringBufferInputStream(value);
            }else{
                inputStream = new StringBufferInputStream("We were unable to make the edit, try again");
            }

        } catch(Exception e){
            inputStream = new StringBufferInputStream(e.getMessage());
        }


        return "success";
    }
     public String loadTable(){
         JSONscrollerMessageBean smb = new JSONscrollerMessageBean();

         try{
              smb = statusScrollerUtils.loadTableData();
             System.out.println("HEllo");
         } catch (Exception e){
             e.printStackTrace();
            smb.setError(e.getMessage());
         }
          setJsonModel(smb);

         return Action.SUCCESS;
     }

    public Object getJsonModel() {
        return jsonModel;
    }

    public void setJsonModel(Object jsonModel) {
        this.jsonModel = jsonModel;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }
    public InputStream getInputStream() {
        return inputStream;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(String columnPosition) {
        this.columnPosition = columnPosition;
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
