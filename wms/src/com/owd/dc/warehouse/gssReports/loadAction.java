package com.owd.dc.warehouse.gssReports;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.owd.core.TimeStamp;
import com.owd.dc.manifest.Manifestxml.dispatchIdList;
import com.owd.dc.manifest.gssEndOfDay;
import com.owd.dc.warehouse.orderInfo.*;
import com.owd.hibernate.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: Dec 29, 2010
 * Time: 9:23:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class loadAction extends ActionSupport {
    private String dispatchId;
    private String date;
    private List<String> ReportList = new ArrayList<String>();
    private String facility = "";
    private dispatchIdList DispatchList = new dispatchIdList();
    private Map<String, String>facilityList = new TreeMap<String, String>();

     public String execute(){
          try{
                  System.out.println("in execute");
               if(facility.length()==0||facility.equals("")){
                   System.out.println("llllllllll");
            try{
                System.out.println("setting facility from sessions");
            facility = ActionContext.getContext().getSession().get("owd_default_location").toString();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
              System.out.println("lslsls");
              System.out.println(facility);
                   gssEndOfDay gss = new gssEndOfDay(facility);
              System.out.println("11111111111111111");
             DispatchList = gss.getDispatchIdList(HibernateSession.currentSession());

                       System.out.println("222222222222222");

          } catch(Exception e){
               addActionError(e.getMessage());
          }



         return "success";

     }
public String load(){
    try{
         gssEndOfDay gss = new gssEndOfDay(facility);
        gss.checkForAndCreateFilesIFneeded(dispatchId,HibernateSession.currentSession());
         ReportList = gss.getReportListForDirctory(dispatchId);


    } catch (Exception e){

    }
    return "success";
}
    public String getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(String dispatchId) {
        this.dispatchId = dispatchId;
    }

    public List<String> getReportList() {
        return ReportList;
    }

    public void setReportList(List<String> reportList) {
        ReportList = reportList;
    }

    public dispatchIdList getDispatchList() {
        return DispatchList;
    }

    public void setDispatchList(dispatchIdList dispatchList) {
        DispatchList = dispatchList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFacility() {
         System.out.println("in get faciliy");

        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public Map<String, String> getFacilityList() {

        try{
        if (facilityList.isEmpty()){
            try{
               String sql = "select loc_name,loc_code from owd_facilities  (NOLOCK) where is_active = 1 order by loc_name asc";
                Query q = HibernateSession.currentSession().createSQLQuery(sql);
                List results = q.list();
                if(results.size()>0){
                     for (Object row:results){
                         Object[]data = (Object[]) row;
                         facilityList.put(data[1].toString(),data[0].toString());

                     }
                } else{
                    throw new Exception("nothing found odd");
                }
            }catch(Exception e){
                e.printStackTrace();
                facilityList.put("DC1","DC1");
                facilityList.put("DC6","DC6");

            }
        }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return facilityList;
    }

    public void setFacilityList(Map<String, String> facilityList) {
        this.facilityList = facilityList;
    }
}